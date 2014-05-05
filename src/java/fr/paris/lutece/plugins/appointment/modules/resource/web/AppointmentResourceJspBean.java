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

import fr.paris.lutece.plugins.appointment.business.Appointment;
import fr.paris.lutece.plugins.appointment.business.AppointmentForm;
import fr.paris.lutece.plugins.appointment.business.AppointmentFormHome;
import fr.paris.lutece.plugins.appointment.business.AppointmentHome;
import fr.paris.lutece.plugins.appointment.business.calendar.AppointmentSlot;
import fr.paris.lutece.plugins.appointment.business.calendar.AppointmentSlotHome;
import fr.paris.lutece.plugins.appointment.modules.resource.business.AppointmentResourceHome;
import fr.paris.lutece.plugins.appointment.modules.resource.business.calendar.CalendarAppointmentResourceDTO;
import fr.paris.lutece.plugins.appointment.modules.resource.business.calendar.CalendarDayDTO;
import fr.paris.lutece.plugins.appointment.service.AppointmentService;
import fr.paris.lutece.plugins.appointment.web.AppointmentFormJspBean;
import fr.paris.lutece.plugins.resource.business.IResource;
import fr.paris.lutece.plugins.resource.service.ResourceService;
import fr.paris.lutece.plugins.resource.service.provider.IResourceProvider;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.util.mvc.admin.MVCAdminJspBean;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.utils.MVCUtils;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.commons.lang.StringUtils;

import java.sql.Date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;


/**
 * Appointment resource JSP Bean
 */
@Controller( controllerJsp = AppointmentResourceJspBean.CONTROLLER_JSP, controllerPath = AppointmentResourceJspBean.CONTROLLER_PATH, right = AppointmentFormJspBean.RIGHT_MANAGEAPPOINTMENTFORM )
public class AppointmentResourceJspBean extends MVCAdminJspBean
{
    /**
     * The path of the JSP of the controller
     */
    public static final String CONTROLLER_PATH = "jsp/admin/plugins/appointment/modules/resource";

    /**
     * The name of the JSP of the controller
     */
    public static final String CONTROLLER_JSP = "ManageAppointmentResources.jsp";
    private static final long serialVersionUID = 3684357156472596848L;

    // Views
    private static final String VIEW_USER_CALENDAR = "VIEW_USER_CALENDAR";
    private static final String VIEW_RESOURCE_CALENDAR = "viewResourceCalendar";

    // Templates
    private static final String TEMPLATE_RESOURCE_CALENDAR = "admin/plugins/appointment/modules/resource/resource_calendar.html";
    private static final String TEMPLATE_VIEW_RESOURCE_CALENDAR = "admin/plugins/appointment/modules/resource/view_resource_calendar.html";
    private static final String TEMPLATE_VIEW_USER_CALENDAR = "admin/plugins/appointment/modules/resource/view_user_calendar.html";
    private static final String TEMPLATE_APPOINTMENT_DESCRIPTION = "admin/plugins/appointment/modules/resource/appointment_description.html";

    // URL
    private static final String CONTROLLER_JSP_URL = CONTROLLER_PATH + "/" + CONTROLLER_JSP;

    // Marks
    private static final String MARK_RESOURCE = "resource";
    private static final String MARK_LIST_DAYS = "listDays";
    private static final String MARK_LIST_DAYS_OF_WEEK = "list_days_of_week";
    private static final String MARK_STARTING_TIME = "startingTime";
    private static final String MARK_ENDING_TIME = "endingTime";
    private static final String MARK_STARTING_HOUR = "startingHour";
    private static final String MARK_STARTING_MINUTE = "startingMinute";
    private static final String MARK_ENDING_HOUR = "endingHour";
    private static final String MARK_ENDING_MINUTE = "endingMinute";
    private static final String MARK_DURATION = "duration";
    private static final String MARK_CALENDAR = "calendar";
    private static final String MARK_APPOINTMENT = "appointment";

    // Parameters
    private static final String PARAMETER_ID_RESOURCE = "id_resource";
    private static final String PARAMETER_RESOURCE_TYPE = "resource_type";
    private static final String PARAMETER_REFERER = "referer";
    private static final String PARAMETER_FROM_URL = "fromUrl";
    private static final String PARAMETER_OFFSET_WEEK = "offsetWeek";

    // Messages
    private static final String MESSAGE_RESOURCE_CALENDAR_PAGE_TITLE = "module.appointment.resource.resource_calendar.pageTitle";
    private static final String MESSAGE_USER_CALENDAR_PAGE_TITLE = "module.appointment.resource.user_calendar.pageTitle";
    private static final String[] MESSAGE_LIST_DAYS_OF_WEEK = AppointmentService.getListDaysOfWeek(  );

    /**
     * Get the page to view the calendar of a user
     * @param request the request
     * @return The HTML code to display
     */
    @View( value = VIEW_USER_CALENDAR, defaultView = true )
    public String getViewUserCalendar( HttpServletRequest request )
    {
        String strFromUrl = request.getParameter( PARAMETER_FROM_URL );

        if ( StringUtils.isEmpty( strFromUrl ) )
        {
            strFromUrl = request.getHeader( PARAMETER_REFERER );

            if ( StringUtils.isEmpty( strFromUrl ) )
            {
                strFromUrl = AppointmentFormJspBean.getURLManageAppointmentForms( request );
            }
        }

        String strOffsetWeek = request.getParameter( PARAMETER_OFFSET_WEEK );

        int nOffsetWeek = 0;

        if ( StringUtils.isNotEmpty( strOffsetWeek ) )
        {
            nOffsetWeek = AppointmentService.getService(  ).parseInt( strOffsetWeek );
        }

        AdminUser adminUser = getUser(  );

        IResource resource = ResourceService.getInstance(  )
                                            .getResource( Integer.toString( adminUser.getUserId(  ) ),
                AdminUser.RESOURCE_TYPE );

        Map<String, Object> model = getModel(  );
        model.put( MARK_CALENDAR, getResourceCalendar( request, resource, nOffsetWeek, getLocale(  ) ) );
        model.put( MARK_RESOURCE, resource );
        model.put( PARAMETER_OFFSET_WEEK, nOffsetWeek );
        model.put( PARAMETER_FROM_URL, strFromUrl );

        return getPage( MESSAGE_USER_CALENDAR_PAGE_TITLE, TEMPLATE_VIEW_USER_CALENDAR, model );
    }

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

        String strFromUrl = request.getParameter( PARAMETER_FROM_URL );

        if ( StringUtils.isEmpty( strFromUrl ) )
        {
            strFromUrl = request.getHeader( PARAMETER_REFERER );

            if ( StringUtils.isEmpty( strFromUrl ) )
            {
                strFromUrl = AppointmentFormJspBean.getURLManageAppointmentForms( request );
            }
        }

        String strOffsetWeek = request.getParameter( PARAMETER_OFFSET_WEEK );

        int nOffsetWeek = 0;

        if ( StringUtils.isNotEmpty( strOffsetWeek ) )
        {
            nOffsetWeek = AppointmentService.getService(  ).parseInt( strOffsetWeek );
        }

        IResourceProvider provider = ResourceService.getInstance(  ).getResourceProvider( strResourceType );

        if ( provider != null )
        {
            IResource resource = provider.getResource( strIdResource, strResourceType );

            if ( resource != null )
            {
                Map<String, Object> model = getModel(  );
                model.put( MARK_CALENDAR, getResourceCalendar( request, resource, nOffsetWeek, getLocale(  ) ) );
                model.put( MARK_RESOURCE, resource );
                model.put( PARAMETER_OFFSET_WEEK, nOffsetWeek );
                model.put( PARAMETER_FROM_URL, strFromUrl );

                return getPage( MESSAGE_RESOURCE_CALENDAR_PAGE_TITLE, TEMPLATE_VIEW_RESOURCE_CALENDAR, model );
            }
        }

        if ( StringUtils.isNotEmpty( strFromUrl ) )
        {
            return redirect( request, strFromUrl );
        }

        return redirect( request, AppointmentFormJspBean.getURLManageAppointmentForms( request ) );
    }

    /**
     * Get the resource calendar for a week
     * @param request The request
     * @param resource The resource
     * @param nOffsetWeek The week offset
     * @param locale The locale
     *            ...)
     * @return The HTML content to display
     */
    public static String getResourceCalendar( HttpServletRequest request, IResource resource, int nOffsetWeek,
        Locale locale )
    {
        AppointmentService appointmentService = AppointmentService.getService(  );

        Map<String, Object> model = new HashMap<String, Object>(  );

        model.put( MARK_RESOURCE, resource );

        Date dateMonday = appointmentService.getDateMonday( nOffsetWeek );
        Calendar calendar = new GregorianCalendar(  );
        calendar.setTime( dateMonday );

        calendar.add( Calendar.DAY_OF_WEEK, 7 );

        Date dateMax = new Date( calendar.getTimeInMillis(  ) );

        List<Integer> listIdAppointments = AppointmentResourceHome.findIdAppointmentsByResourceAndDate( resource.getIdResource(  ),
                resource.getResourceType(  ), dateMonday, dateMax );

        List<Appointment> listAppointment = new ArrayList<Appointment>( listIdAppointments.size(  ) );

        Map<Integer, List<CalendarAppointmentResourceDTO>> mapCalendarAppointmentResourceByDayOfWeek = new HashMap<Integer, List<CalendarAppointmentResourceDTO>>(  );

        int nStartingHour = 0;
        int nStartingMinute = 0;
        int nEndingHour = 0;
        int nEndingMinute = 0;
        int nMinGlobalStartingTime = 9999;
        int nMaxGlobalEndingTime = 0;
        int nMinDuration = -1;

        for ( int i = 1; i < 8; i++ )
        {
            mapCalendarAppointmentResourceByDayOfWeek.put( i, new ArrayList<CalendarAppointmentResourceDTO>(  ) );
        }

        for ( int nIdAppointment : listIdAppointments )
        {
            Appointment appointment = AppointmentHome.findByPrimaryKey( nIdAppointment );
            listAppointment.add( appointment );

            AppointmentSlot slot = AppointmentSlotHome.findByPrimaryKey( appointment.getIdSlot(  ) );
            CalendarAppointmentResourceDTO calendarAppointmentResource = new CalendarAppointmentResourceDTO( appointment.getIdAppointment(  ),
                    slot.getStartingHour(  ), slot.getStartingMinute(  ), slot.getEndingHour(  ),
                    slot.getEndingMinute(  ), getAppointmentRecap( appointment, locale ) );

            int nStartingTimeSlot = ( slot.getStartingHour(  ) * 60 ) + slot.getStartingMinute(  );

            if ( nStartingTimeSlot < nMinGlobalStartingTime )
            {
                nMinGlobalStartingTime = nStartingTimeSlot;
                nStartingHour = slot.getStartingHour(  );
                nStartingMinute = slot.getStartingMinute(  );
            }

            int nEndingTimeSlot = ( slot.getEndingHour(  ) * 60 ) + slot.getEndingMinute(  );

            if ( nEndingTimeSlot > nMaxGlobalEndingTime )
            {
                nMaxGlobalEndingTime = nEndingTimeSlot;
                nEndingHour = slot.getEndingHour(  );
                nEndingMinute = slot.getEndingMinute(  );
            }

            if ( ( calendarAppointmentResource.getDuration(  ) < nMinDuration ) || ( nMinDuration == -1 ) )
            {
                nMinDuration = calendarAppointmentResource.getDuration(  );
            }

            int nDayOfWeek = appointmentService.getDayOfWeek( appointment.getDateAppointment(  ) );
            List<CalendarAppointmentResourceDTO> listCalendar = mapCalendarAppointmentResourceByDayOfWeek.get( nDayOfWeek );
            listCalendar.add( calendarAppointmentResource );
        }

        List<CalendarDayDTO> listDays = new ArrayList<CalendarDayDTO>( 7 );

        for ( Entry<Integer, List<CalendarAppointmentResourceDTO>> entry : mapCalendarAppointmentResourceByDayOfWeek.entrySet(  ) )
        {
            CalendarDayDTO day = new CalendarDayDTO(  );
            Calendar calendarDay = new GregorianCalendar(  );
            calendarDay.setTime( dateMonday );
            calendarDay.add( Calendar.DAY_OF_WEEK, entry.getKey(  ) - 1 );
            day.setDate( calendarDay.getTime(  ) );

            List<CalendarAppointmentResourceDTO> listCalendarApp = entry.getValue(  );
            Collections.sort( listCalendarApp );
            day.setListAppointmentResourceDTO( listCalendarApp );
            listDays.add( day );
        }

        Collections.sort( listDays );

        List<AppointmentForm> listAppointmentForm = AppointmentFormHome.getActiveAppointmentFormsList(  );

        for ( AppointmentForm appointmentForm : listAppointmentForm )
        {
            int nOpeningTime = ( appointmentForm.getOpeningHour(  ) * 60 ) + appointmentForm.getOpeningMinutes(  );

            if ( nOpeningTime < nMinGlobalStartingTime )
            {
                nMinGlobalStartingTime = nOpeningTime;
                nStartingHour = appointmentForm.getOpeningHour(  );
                nStartingMinute = appointmentForm.getOpeningMinutes(  );
            }

            int nClosingTime = ( appointmentForm.getClosingHour(  ) * 60 ) + appointmentForm.getClosingMinutes(  );

            if ( nClosingTime > nMaxGlobalEndingTime )
            {
                nMaxGlobalEndingTime = nClosingTime;
                nEndingHour = appointmentForm.getClosingHour(  );
                nEndingMinute = appointmentForm.getClosingMinutes(  );
            }

            if ( ( appointmentForm.getDurationAppointments(  ) < nMinDuration ) || ( nMinDuration < 0 ) )
            {
                nMinDuration = appointmentForm.getDurationAppointments(  );
            }
        }

        model.put( MARK_LIST_DAYS, listDays );
        model.put( PARAMETER_OFFSET_WEEK, nOffsetWeek );
        model.put( MARK_LIST_DAYS_OF_WEEK, MESSAGE_LIST_DAYS_OF_WEEK );
        model.put( MARK_STARTING_TIME, nMinGlobalStartingTime );
        model.put( MARK_ENDING_TIME, nMaxGlobalEndingTime );
        model.put( MARK_DURATION, nMinDuration );
        model.put( MARK_STARTING_HOUR, nStartingHour );
        model.put( MARK_STARTING_MINUTE, nStartingMinute );
        model.put( MARK_ENDING_HOUR, nEndingHour );
        model.put( MARK_ENDING_MINUTE, nEndingMinute );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_RESOURCE_CALENDAR, locale, model );

        return template.getHtml(  );
    }

    /**
     * Get the description of an appointment
     * @param appointment The appointment
     * @param locale The locale
     * @return The HTML code of the description of the appointment
     */
    private static String getAppointmentRecap( Appointment appointment, Locale locale )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_APPOINTMENT, appointment );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_APPOINTMENT_DESCRIPTION, locale, model );

        return template.getHtml(  );
    }

    /**
     * Get the URL of the calendar of a resource
     * @param strIdResource The id of the resource
     * @param strResourceType the resource type
     * @return The URL of the calendar of the resource. Note that the base URL
     *         is not prefixed to the URL.
     */
    public static String getUrlResourceCalendar( String strIdResource, String strResourceType )
    {
        UrlItem urlItem = new UrlItem( CONTROLLER_JSP_URL );
        urlItem.addParameter( MVCUtils.PARAMETER_VIEW, VIEW_RESOURCE_CALENDAR );
        urlItem.addParameter( PARAMETER_ID_RESOURCE, strIdResource );
        urlItem.addParameter( PARAMETER_RESOURCE_TYPE, strResourceType );

        return urlItem.getUrl(  );
    }
}
