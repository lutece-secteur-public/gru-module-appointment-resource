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
package fr.paris.lutece.plugins.appointment.modules.resource.web;

import fr.paris.lutece.plugins.appointment.web.AppointmentFormJspBean;
import fr.paris.lutece.plugins.resource.business.IResource;
import fr.paris.lutece.plugins.resource.service.ResourceService;
import fr.paris.lutece.plugins.resource.service.provider.IResourceProvider;
import fr.paris.lutece.portal.util.mvc.admin.MVCAdminJspBean;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;

import org.apache.commons.lang.StringUtils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * Appointment resource JSP Bean
 */
@Controller( controllerJsp = "ManageAppointmentResource.jsp", controllerPath = "jsp/admin/plugins/appointment/modules/resource", right = AppointmentFormJspBean.RIGHT_MANAGEAPPOINTMENTFORM )
public class AppointmentResourceJspBean extends MVCAdminJspBean
{
    private static final long serialVersionUID = 3684357156472596848L;

    // Views
    private static final String VIEW_RESOURCE_CALENDAR = "viewResourceCalendar";

    // Templates
    private static final String TEMPLATE_RESOURCE_CALENDAR = "admin/plugins/appointment/modules/resource/resource_calendar.html";

    // Marks
    private static final String MARK_RESOURCE = "resource";

    // Parameters
    private static final String PARAMETER_ID_RESOURCE = "id_resource";
    private static final String PARAMETER_RESOURCE_TYPE = "resource_type";

    // Messages
    private static final String MESSAGE_RESOURCE_CALENDAR_PAGE_TITLE = "module.appointment.resource.resource_calendar.pageTitle";

    /**
     * Get the calendar of a resource
     * @param request The request
     * @return The HTML content to display
     */
    @View( VIEW_RESOURCE_CALENDAR )
    public String getResourceCalendar( HttpServletRequest request )
    {
        String strIdResource = request.getParameter( PARAMETER_ID_RESOURCE );
        String strResourceType = request.getParameter( PARAMETER_RESOURCE_TYPE );
        if ( StringUtils.isEmpty( strIdResource ) || StringUtils.isEmpty( strResourceType ) )
        {
            return redirect( request, AppointmentFormJspBean.getURLManageAppointmentForms( request ) );
        }

        IResourceProvider provider = ResourceService.getInstance( ).getResourceProvider( strResourceType );

        IResource resource = provider.getResource( strIdResource, strResourceType );

        Map<String, Object> model = getModel( );
        model.put( MARK_RESOURCE, resource );

        return getPage( MESSAGE_RESOURCE_CALENDAR_PAGE_TITLE, TEMPLATE_RESOURCE_CALENDAR, model );
    }
}
