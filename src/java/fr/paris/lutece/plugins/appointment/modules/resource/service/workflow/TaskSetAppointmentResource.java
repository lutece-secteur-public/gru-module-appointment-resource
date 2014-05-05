/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.appointment.modules.resource.service.workflow;

import fr.paris.lutece.plugins.appointment.business.Appointment;
import fr.paris.lutece.plugins.appointment.business.AppointmentHome;
import fr.paris.lutece.plugins.appointment.modules.resource.business.AppointmentFormResourceType;
import fr.paris.lutece.plugins.appointment.modules.resource.business.AppointmentFormResourceTypeHome;
import fr.paris.lutece.plugins.appointment.modules.resource.business.AppointmentResource;
import fr.paris.lutece.plugins.appointment.modules.resource.business.AppointmentResourceHome;
import fr.paris.lutece.plugins.appointment.modules.resource.business.workflow.SetAppointmentResourceHistory;
import fr.paris.lutece.plugins.appointment.modules.resource.business.workflow.SetAppointmentResourceHistoryHome;
import fr.paris.lutece.plugins.appointment.modules.resource.business.workflow.TaskSetAppointmentResourceConfig;
import fr.paris.lutece.plugins.resource.business.IResource;
import fr.paris.lutece.plugins.resource.service.ResourceService;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.portal.service.i18n.I18nService;

import org.apache.commons.lang.StringUtils;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import javax.servlet.http.HttpServletRequest;


/**
 * Workflow task to update a resource associated with an appointment
 */
public class TaskSetAppointmentResource extends SimpleTask
{
    /**
     * Name of the bean of the config service of this task
     */
    public static final String CONFIG_SERVICE_BEAN_NAME = "appointment-resource.taskSetAppointmentResourceConfigService";

    // MESSAGES
    private static final String MESSAGE_SET_APPOINTMENT_RESOURCE_TASK_DESCRIPTION = "module.appointment.resource.task_set_appointment_resource_config.taskDescription";

    // PARAMETERS
    private static final String PARAMETER_ID_RESOURCE = "idResource_";

    // SERVICES
    @Inject
    private IResourceHistoryService _resourceHistoryService;
    @Inject
    @Named( CONFIG_SERVICE_BEAN_NAME )
    private ITaskConfigService _taskSetAppointmentResourceConfigService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        TaskSetAppointmentResourceConfig config = _taskSetAppointmentResourceConfigService.findByPrimaryKey( this.getId(  ) );

        if ( config == null )
        {
            return;
        }

        String strIdResource = request.getParameter( PARAMETER_ID_RESOURCE + config.getIdFormResourceType(  ) );

        if ( StringUtils.isNotEmpty( strIdResource ) )
        {
            Appointment appointment = AppointmentHome.findByPrimaryKey( resourceHistory.getIdResource(  ) );

            AppointmentResource appResource = AppointmentResourceHome.findByPrimaryKey( appointment.getIdAppointment(  ),
                    config.getIdFormResourceType(  ) );

            if ( appResource != null )
            {
                appResource.setIdResource( strIdResource );
                AppointmentResourceHome.update( appResource );
            }
            else
            {
                appResource = new AppointmentResource(  );
                appResource.setIdAppointment( appointment.getIdAppointment(  ) );
                appResource.setIdAppointmentFormResourceType( config.getIdFormResourceType(  ) );
                appResource.setIdResource( strIdResource );
                AppointmentResourceHome.insert( appResource );
            }

            AppointmentFormResourceType formResourceType = AppointmentFormResourceTypeHome.findByPrimaryKey( config.getIdFormResourceType(  ) );

            if ( formResourceType.getIsAppointmentAdminUser(  ) && StringUtils.isNumeric( strIdResource ) )
            {
                int nIdAdminUser = Integer.parseInt( strIdResource );

                if ( appointment.getIdAppointment(  ) != nIdAdminUser )
                {
                    appointment.setIdAdminUser( nIdAdminUser );
                    AppointmentHome.update( appointment );
                }
            }

            if ( formResourceType.getIsLocation(  ) )
            {
                IResource resource = ResourceService.getInstance(  )
                                                    .getResource( strIdResource,
                        formResourceType.getResourceTypeName(  ) );

                if ( ( resource != null ) && StringUtils.isNotEmpty( resource.getResourceName(  ) ) )
                {
                    appointment.setLocation( resource.getResourceName(  ) );
                    AppointmentHome.update( appointment );
                }
            }

            SetAppointmentResourceHistory history = new SetAppointmentResourceHistory(  );
            history.setIdHistory( nIdResourceHistory );
            history.setIdAppointment( appResource.getIdAppointment(  ) );
            history.setIdFormResourceType( appResource.getIdAppointmentFormResourceType(  ) );
            history.setIdResource( appResource.getIdResource(  ) );
            SetAppointmentResourceHistoryHome.create( history );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle( Locale locale )
    {
        TaskSetAppointmentResourceConfig config = _taskSetAppointmentResourceConfigService.findByPrimaryKey( getId(  ) );

        if ( config == null )
        {
            return StringUtils.EMPTY;
        }

        AppointmentFormResourceType resourceType = AppointmentFormResourceTypeHome.findByPrimaryKey( config.getIdFormResourceType(  ) );
        Object[] args = { ( resourceType != null ) ? resourceType.getDescription(  ) : StringUtils.EMPTY };

        return I18nService.getLocalizedString( MESSAGE_SET_APPOINTMENT_RESOURCE_TASK_DESCRIPTION, args, locale );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doRemoveConfig(  )
    {
        _taskSetAppointmentResourceConfigService.remove( getId(  ) );
    }
}
