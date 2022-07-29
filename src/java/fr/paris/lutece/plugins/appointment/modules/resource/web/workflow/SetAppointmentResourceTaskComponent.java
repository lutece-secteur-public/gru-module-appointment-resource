/*
 * Copyright (c) 2002-2018, Mairie de Paris
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
package fr.paris.lutece.plugins.appointment.modules.resource.web.workflow;

import fr.paris.lutece.plugins.appointment.business.appointment.Appointment;
import fr.paris.lutece.plugins.appointment.business.slot.Slot;
import fr.paris.lutece.plugins.appointment.modules.resource.business.AppointmentFormResourceType;
import fr.paris.lutece.plugins.appointment.modules.resource.business.AppointmentFormResourceTypeHome;
import fr.paris.lutece.plugins.appointment.modules.resource.business.workflow.SetAppointmentResourceHistory;
import fr.paris.lutece.plugins.appointment.modules.resource.business.workflow.SetAppointmentResourceHistoryHome;
import fr.paris.lutece.plugins.appointment.modules.resource.business.workflow.TaskSetAppointmentResourceConfig;
import fr.paris.lutece.plugins.appointment.modules.resource.service.AppointmentResourceService;
import fr.paris.lutece.plugins.appointment.modules.resource.service.workflow.TaskSetAppointmentResource;
import fr.paris.lutece.plugins.appointment.service.AppointmentService;
import fr.paris.lutece.plugins.appointment.service.FormService;
import fr.paris.lutece.plugins.appointment.web.dto.AppointmentFormDTO;
import fr.paris.lutece.plugins.resource.business.IResource;
import fr.paris.lutece.plugins.resource.service.ResourceService;
import fr.paris.lutece.plugins.workflow.web.task.AbstractTaskComponent;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 * UpdateAdminAppointmentTaskComponent
 */
public class SetAppointmentResourceTaskComponent extends AbstractTaskComponent
{
    // TEMPLATES
    private static final String TEMPLATE_TASK_FORM_SET_APPOINTMENT_RESOURCE = "admin/plugins/appointment/modules/resource/task_set_appointment_resource_form.html";
    private static final String TEMPLATE_CONFIG_SET_APPOINTMENT_RESOURCE = "admin/plugins/appointment/modules/resource/task_set_appointment_resource_config.html";

    // MARKS
    private static final String MARK_REF_LIST_FORM_RESOURCE_TYPES = "refListFormResourceTypes";
    private static final String MARK_REF_LIST_APPOINTMENT_FORM = "refListAppointmentForm";
    private static final String MARK_REF_LIST_RESOURCES = "refListResources";
    private static final String MARK_FORM_RESOURCE_TYPE = "formResourceType";

    // PARAMETERS
    private static final String PARAMETER_ID_APPOINTMENT_FORM = "idAppointmentForm";
    private static final String PARAMETER_ID_FORM_RESOURCE_TYPE = "idFormResourceType";
    private static final String PARAMETER_REFRESH_APPOINTMENT_FORM = "refreshAppointmentForm";
    private static final String PARAMETER_ID_TASK = "id_task";
    private static final String PARAMETER_ID_RESOURCE = "idResource_";
    private static final String PARAMETER_IS_MANDATORY = "isMandatory";

    // Jsp URL
    private static final String JSP_URL_MODIFY_TASK = "jsp/admin/plugins/workflow/ModifyTask.jsp";

    // MESSAGES
    private static final String MESSAGE_ERROR_MANDATORY_FIELDS = "module.appointment.resource.task_set_appointment_resource_config.mandatoryFields";
    private static final String MESSAGE_NO_RESOURCE_TYPE = "module.appointment.resource.task_set_appointment_resource_config.noResourceType";
    private static final String MESSAGE_APPOINTMENT_RESOURCE_SET = "module.appointment.resource.task_set_appointment_resource_config.history.appointmentResourceSet";
    @Inject
    @Named( TaskSetAppointmentResource.CONFIG_SERVICE_BEAN_NAME )
    private ITaskConfigService _taskSetAppointmentResourceConfigService;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayConfigForm( HttpServletRequest request, Locale locale, ITask task )
    {
        Map<String, Object> model = new HashMap<>( );

        TaskSetAppointmentResourceConfig config = _taskSetAppointmentResourceConfigService.findByPrimaryKey( task.getId( ) );

        List<AppointmentFormDTO> listAppointmentform = FormService.buildAllAppointmentFormLight( );
        ReferenceList refListAppointmentForm = new ReferenceList( );

        for ( AppointmentFormDTO form : listAppointmentform )
        {
            refListAppointmentForm.addItem( form.getIdForm( ), form.getTitle( ) );
        }

        String strIdAppointmentForm;

        // We get the only possible id appointment form if there is only one appointment form
        if ( refListAppointmentForm.size( ) == 1 )
        {
            strIdAppointmentForm = refListAppointmentForm.get( 0 ).getCode( );
        }
        else
        {
            strIdAppointmentForm = request.getParameter( PARAMETER_ID_APPOINTMENT_FORM );

            if ( ( config != null ) && ( StringUtils.isEmpty( strIdAppointmentForm ) || !StringUtils.isNumeric( strIdAppointmentForm ) ) )
            {
                AppointmentFormResourceType formResourceType = AppointmentFormResourceTypeHome.findByPrimaryKey( config.getIdFormResourceType( ) );
                strIdAppointmentForm = Integer.toString( formResourceType.getIdAppointmentForm( ) );
            }
        }

        if ( config != null )
        {
            model.put( PARAMETER_ID_FORM_RESOURCE_TYPE, Integer.toString( config.getIdFormResourceType( ) ) );
            model.put( PARAMETER_IS_MANDATORY, config.getIsMandatory( ) );
        }
        else
        {
            model.put( PARAMETER_IS_MANDATORY, Boolean.TRUE );
        }

        ReferenceList refListFormTypes = new ReferenceList( );

        if ( StringUtils.isNotEmpty( strIdAppointmentForm ) && StringUtils.isNumeric( strIdAppointmentForm ) )
        {
            model.put( PARAMETER_ID_APPOINTMENT_FORM, strIdAppointmentForm );

            List<AppointmentFormResourceType> listFormResourceType = AppointmentFormResourceTypeHome.findResourceTypesListFromIdForm( Integer
                    .parseInt( strIdAppointmentForm ) );

            for ( AppointmentFormResourceType formType : listFormResourceType )
            {
                refListFormTypes.addItem( formType.getId( ), formType.getDescription( ) );
            }
        }

        model.put( MARK_REF_LIST_FORM_RESOURCE_TYPES, refListFormTypes );
        model.put( MARK_REF_LIST_APPOINTMENT_FORM, refListAppointmentForm );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CONFIG_SET_APPOINTMENT_RESOURCE, locale, model );

        return template.getHtml( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String doSaveConfig( HttpServletRequest request, Locale locale, ITask task )
    {
        boolean bRefresh = Boolean.parseBoolean( request.getParameter( PARAMETER_REFRESH_APPOINTMENT_FORM ) );

        if ( bRefresh )
        {
            String strIdAppointmentForm = request.getParameter( PARAMETER_ID_APPOINTMENT_FORM );

            return getUrlModifyTask( request, task.getId( ), strIdAppointmentForm );
        }

        String strResourceType = request.getParameter( PARAMETER_ID_FORM_RESOURCE_TYPE );

        if ( StringUtils.isEmpty( strResourceType ) || !StringUtils.isNumeric( strResourceType ) )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_NO_RESOURCE_TYPE, AdminMessage.TYPE_STOP );
        }

        TaskSetAppointmentResourceConfig config = _taskSetAppointmentResourceConfigService.findByPrimaryKey( task.getId( ) );
        boolean bCreate = false;

        if ( config == null )
        {
            bCreate = true;
            config = new TaskSetAppointmentResourceConfig( );
            config.setIdTask( task.getId( ) );
        }

        boolean bIsMandatory = request.getParameter( PARAMETER_IS_MANDATORY ) != null;
        config.setIsMandatory( bIsMandatory );

        config.setIdFormResourceType( Integer.parseInt( strResourceType ) );

        if ( bCreate )
        {
            _taskSetAppointmentResourceConfigService.create( config );
        }
        else
        {
            _taskSetAppointmentResourceConfigService.update( config );
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayTaskForm( int nIdResource, String strResourceType, HttpServletRequest request, Locale locale, ITask task )
    {
        if ( !StringUtils.equals( Appointment.APPOINTMENT_RESOURCE_TYPE, strResourceType ) )
        {
            return null;
        }

        Appointment appointment = AppointmentService.findAppointmentById( nIdResource );

        if ( appointment == null )
        {
            return null;
        }

        Map<String, Object> model = new HashMap<>( );

        TaskSetAppointmentResourceConfig config = _taskSetAppointmentResourceConfigService.findByPrimaryKey( task.getId( ) );

        if ( config == null )
        {
            return null;
        }

        AppointmentFormResourceType formResourceType = AppointmentFormResourceTypeHome.findByPrimaryKey( config.getIdFormResourceType( ) );

        List<IResource> listResources = ResourceService.getInstance( ).getListResources( formResourceType.getResourceTypeName( ) );
        ReferenceList refListResources = new ReferenceList( );
        AppointmentResourceService appointmentResourceService = AppointmentResourceService.getInstance( );
        List<Slot> slotsList = appointment.getSlot( );

        for( Slot slot : slotsList )
        {
	        for ( IResource resource : listResources )
	        {
	            if ( appointmentResourceService.isResourceAvailableForAppointment( resource.getIdResource( ), resource.getResourceType( ),
	                    config.getIdFormResourceType( ), appointment, slot ) )
	            {
	                refListResources.addItem( resource.getIdResource( ), resource.getResourceName( ) );
	            }
	        }
        }

        model.put( MARK_REF_LIST_RESOURCES, refListResources );
        model.put( MARK_FORM_RESOURCE_TYPE, formResourceType );
        model.put( PARAMETER_IS_MANDATORY, config.getIsMandatory( ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_TASK_FORM_SET_APPOINTMENT_RESOURCE, locale, model );

        return template.getHtml( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String doValidateTask( int nIdResource, String strResourceType, HttpServletRequest request, Locale locale, ITask task )
    {
        TaskSetAppointmentResourceConfig config = _taskSetAppointmentResourceConfigService.findByPrimaryKey( task.getId( ) );

        if ( ( config == null ) || !config.getIsMandatory( ) )
        {
            return null;
        }

        String strIdResource = request.getParameter( PARAMETER_ID_RESOURCE + config.getIdFormResourceType( ) );

        if ( StringUtils.isEmpty( strIdResource ) )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_ERROR_MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        AppointmentFormResourceType formResourceType = AppointmentFormResourceTypeHome.findByPrimaryKey( config.getIdFormResourceType( ) );

        IResource resource = ResourceService.getInstance( ).getResource( strIdResource, formResourceType.getResourceTypeName( ) );

        if ( resource == null )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_ERROR_MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayTaskInformation( int nIdHistory, HttpServletRequest request, Locale locale, ITask task )
    {
        List<SetAppointmentResourceHistory> listHistory = SetAppointmentResourceHistoryHome.findByIdHistory( nIdHistory );
        StringBuilder sbHistory = new StringBuilder( );

        for ( SetAppointmentResourceHistory history : listHistory )
        {
            AppointmentFormResourceType resourceType = AppointmentFormResourceTypeHome.findByPrimaryKey( history.getIdFormResourceType( ) );
            IResource resource = ResourceService.getInstance( ).getResource( history.getIdResource( ), resourceType.getResourceTypeName( ) );

            Object [ ] args = {
                    resource.getResourceName( ), resourceType.getDescription( )
            };
            sbHistory.append( I18nService.getLocalizedString( MESSAGE_APPOINTMENT_RESOURCE_SET, args, locale ) );
            sbHistory.append( "<br />" );
        }

        return sbHistory.toString( );
    }

    /**
     * {@inheritDoc}
     */
    public String getTaskInformationXml( int nIdHistory, HttpServletRequest request, Locale locale, ITask task )
    {
        return null;
    }

    /**
     * Get the URL to modify the task
     * 
     * @param request
     *            The request
     * @param nIdTask
     *            The id of the task
     * @param strIdAppointmentForm
     *            The id of the appointment form
     * @return The requested URL
     */
    private String getUrlModifyTask( HttpServletRequest request, int nIdTask, String strIdAppointmentForm )
    {
        UrlItem urlItem = new UrlItem( AppPathService.getBaseUrl( request ) + JSP_URL_MODIFY_TASK );
        urlItem.addParameter( PARAMETER_ID_TASK, nIdTask );
        urlItem.addParameter( PARAMETER_ID_APPOINTMENT_FORM, strIdAppointmentForm );

        return urlItem.getUrl( );
    }
}
