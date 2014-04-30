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
package fr.paris.lutece.plugins.appointment.modules.resource.service;

import fr.paris.lutece.plugins.appointment.modules.resource.business.AppointmentFormResourceTypeHome;
import fr.paris.lutece.plugins.appointment.modules.resource.business.AppointmentResource;
import fr.paris.lutece.plugins.appointment.modules.resource.business.AppointmentResourceDTO;
import fr.paris.lutece.plugins.appointment.modules.resource.business.AppointmentResourceHome;
import fr.paris.lutece.plugins.appointment.service.addon.IAppointmentAddonService;
import fr.paris.lutece.plugins.resource.service.ResourceService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Add service for resources of appointments
 */
public class AppointmentResourceAddonService implements IAppointmentAddonService
{
    private static final String MARK_LIST_APP_RESOURCES = "listAppResources";
    private static final String TEMPLATE_APPOINTMENT_RESOURCE_ADDON = "admin/plugins/appointment/modules/resource/appointment_resource_addon.html";

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAppointmentAddOn( int nIdAppointment, Locale locale )
    {
        List<AppointmentResource> listAppResources = AppointmentResourceHome.findByIdAppointment( nIdAppointment );

        List<AppointmentResourceDTO> listAppResourcesDTO = new ArrayList<AppointmentResourceDTO>(
                listAppResources.size( ) );

        for ( AppointmentResource appResource : listAppResources )
        {
            AppointmentResourceDTO appResDTO = new AppointmentResourceDTO( appResource );
            appResDTO.setFormResourceType( AppointmentFormResourceTypeHome.findByPrimaryKey( appResource
                    .getIdAppointmentFormResourceType( ) ) );

            if ( StringUtils.isNotEmpty( appResDTO.getIdResource( ) )
                    && StringUtils.isNotEmpty( appResDTO.getFormResourceType( ).getResourceTypeName( ) ) )
            {
                appResDTO.setResource( ResourceService.getInstance( ).getResource( appResDTO.getIdResource( ),
                        appResDTO.getFormResourceType( ).getResourceTypeName( ) ) );
            }

            listAppResourcesDTO.add( appResDTO );
        }

        Map<String, Object> model = new HashMap<String, Object>( );

        model.put( MARK_LIST_APP_RESOURCES, listAppResourcesDTO );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_APPOINTMENT_RESOURCE_ADDON, locale, model );

        return template.getHtml( );
    }
}
