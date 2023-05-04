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
package fr.paris.lutece.plugins.appointment.modules.resource.web;

import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.appointment.business.rule.ReservationRule;
import fr.paris.lutece.plugins.appointment.modules.resource.business.AppointmentResourceHome;
import fr.paris.lutece.plugins.appointment.modules.resource.business.calendar.CalendarAppointmentResourceDTO;
import fr.paris.lutece.plugins.appointment.modules.resource.business.calendar.CalendarDayDTO;
import fr.paris.lutece.plugins.appointment.modules.resource.business.form.FormResourceCalendar;
import fr.paris.lutece.plugins.appointment.modules.resource.service.FormResourceCalendarService;
import fr.paris.lutece.plugins.appointment.service.AppointmentService;
import fr.paris.lutece.plugins.appointment.service.FormService;
import fr.paris.lutece.plugins.appointment.service.ReservationRuleService;
import fr.paris.lutece.plugins.appointment.service.WeekDefinitionService;
import fr.paris.lutece.plugins.appointment.web.AppointmentFormJspBean;
import fr.paris.lutece.plugins.appointment.web.dto.AppointmentDTO;
import fr.paris.lutece.plugins.appointment.web.dto.AppointmentFormDTO;
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

/**
 * Appointment resource JSP Bean
 */
@Controller(controllerJsp = AppointmentResourceJspBean.CONTROLLER_JSP, controllerPath = AppointmentResourceJspBean.CONTROLLER_PATH, right = AppointmentFormJspBean.RIGHT_MANAGEAPPOINTMENTFORM)
public class AppointmentResourceJspBean extends MVCAdminJspBean {
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

	// Default values for calendar time stamps
	private static final int DEFAULT_APPOINTMENT_DURATION = 30;
	
	/**
	 * List of i18n keys of days of week
	 */
	private static final String[] MESSAGE_LIST_DAYS_OF_WEEK = {
			"module.appointment.resource.manageCalendarSlots.labelMonday",
			"module.appointment.resource.manageCalendarSlots.labelTuesday",
			"module.appointment.resource.manageCalendarSlots.labelWednesday",
			"module.appointment.resource.manageCalendarSlots.labelThursday",
			"module.appointment.resource.manageCalendarSlots.labelFriday",
			"module.appointment.resource.manageCalendarSlots.labelSaturday",
			"module.appointment.resource.manageCalendarSlots.labelSunday", };

	/**
	 * Get the page to view the calendar of a user
	 * 
	 * @param request the request
	 * @return The HTML code to display
	 */
	@View(value = VIEW_USER_CALENDAR, defaultView = true)
	public String getViewUserCalendar(HttpServletRequest request) {
		String strFromUrl = request.getParameter(PARAMETER_FROM_URL);

		if (StringUtils.isEmpty(strFromUrl)) {
			strFromUrl = request.getHeader(PARAMETER_REFERER);

			if (StringUtils.isEmpty(strFromUrl)) {
				strFromUrl = AppointmentFormJspBean.getURLManageAppointmentForms(request);
			}
		}

		String strOffsetWeek = request.getParameter(PARAMETER_OFFSET_WEEK);

		int nOffsetWeek = 0;

		if (StringUtils.isNotEmpty(strOffsetWeek)) {
			nOffsetWeek = parseInt(strOffsetWeek);
		}

		AdminUser adminUser = getUser();

		IResource resource = ResourceService.getInstance().getResource(Integer.toString(adminUser.getUserId()),
				AdminUser.RESOURCE_TYPE);

		Map<String, Object> model = getModel();
		model.put(MARK_CALENDAR, getWeekResourceCalendar(resource, nOffsetWeek, getLocale()));
		model.put(MARK_RESOURCE, resource);
		model.put(PARAMETER_OFFSET_WEEK, nOffsetWeek);
		model.put(PARAMETER_FROM_URL, strFromUrl);

		return getPage(MESSAGE_USER_CALENDAR_PAGE_TITLE, TEMPLATE_VIEW_USER_CALENDAR, model);
	}

	/**
	 * Get the calendar of a resource
	 * 
	 * @param request The request
	 * @return The HTML content to display
	 */
	@View(VIEW_RESOURCE_CALENDAR)
	public String getResourceCalendar(HttpServletRequest request) {
		String strIdResource = request.getParameter(PARAMETER_ID_RESOURCE);
		String strResourceType = request.getParameter(PARAMETER_RESOURCE_TYPE);

		if (StringUtils.isEmpty(strIdResource) || StringUtils.isEmpty(strResourceType)) {
			return redirect(request, AppointmentFormJspBean.getURLManageAppointmentForms(request));
		}

		String strFromUrl = request.getParameter(PARAMETER_FROM_URL);

		if (StringUtils.isEmpty(strFromUrl)) {
			strFromUrl = request.getHeader(PARAMETER_REFERER);

			if (StringUtils.isEmpty(strFromUrl)) {
				strFromUrl = AppointmentFormJspBean.getURLManageAppointmentForms(request);
			}
		}

		String strOffsetWeek = request.getParameter(PARAMETER_OFFSET_WEEK);

		int nOffsetWeek = 0;

		if (StringUtils.isNotEmpty(strOffsetWeek)) {
			nOffsetWeek = parseInt(strOffsetWeek);
		}

		IResourceProvider provider = ResourceService.getInstance().getResourceProvider(strResourceType);

		if (provider != null) {
			IResource resource = provider.getResource(strIdResource, strResourceType);

			if (resource != null) {
				Map<String, Object> model = getModel();
				model.put(MARK_CALENDAR, getWeekResourceCalendar(resource, nOffsetWeek, getLocale()));
				model.put(MARK_RESOURCE, resource);
				model.put(PARAMETER_OFFSET_WEEK, nOffsetWeek);
				model.put(PARAMETER_FROM_URL, strFromUrl);

				return getPage(MESSAGE_RESOURCE_CALENDAR_PAGE_TITLE, TEMPLATE_VIEW_RESOURCE_CALENDAR, model);
			}
		}

		if (StringUtils.isNotEmpty(strFromUrl)) {
			return redirect(request, strFromUrl);
		}

		return redirect(request, AppointmentFormJspBean.getURLManageAppointmentForms(request));
	}

	/**
	 * Get the resource calendar for a whole week
	 * 
	 * @param resource
	 * 			The resource to display in the calendar
	 * @param nOffsetWeek
	 * 			The week to display in the calendar ( -1 == previous, 0 == current, 1 == next, etc. )
	 * @param locale
	 * 			Locale for localization
	 * 
	 * @return an HTML template to display the calendar
	 */
	public static String getWeekResourceCalendar( IResource resource, int nOffsetWeek, Locale locale )
	{

		Map<String, Object> model = new HashMap<>();

		model.put( MARK_RESOURCE, resource );

		// Set the standard week type (nb of days, first day of the week, etc.)
		Date dateMonday = getDateMonday( nOffsetWeek );
		Calendar calendar = new GregorianCalendar();
		calendar.setTime( dateMonday );

		calendar.add( Calendar.DAY_OF_WEEK, 7 );

		Date dateMax = new Date( calendar.getTimeInMillis() );

		// Retrieve the IDs of the appointments tied to a resource, for a specific period
		List<Integer> listIdAppointments = AppointmentResourceHome.findIdAppointmentsByResourceAndDate(
				resource.getIdResource(), resource.getResourceType(), dateMonday, dateMax );

		List<AppointmentDTO> listAppointment = new ArrayList<>( listIdAppointments.size() );

		Map<Integer, List<CalendarAppointmentResourceDTO>> mapCalendarAppointmentResourceByDayOfWeek = new HashMap<>();

		int nStartingHour = 0;
		int nStartingMinute = 0;
		int nEndingHour = 0;
		int nEndingMinute = 0;
		int nMinGlobalStartingTime = 9999;
		int nMaxGlobalEndingTime = 0;
		int nMinDuration = -1;
		
		for ( int i = 1; i < 8; i++ )
		{
			mapCalendarAppointmentResourceByDayOfWeek.put( i, new ArrayList<>() );
		}

		/* Get the minimum starting time, maximum ending time, and minimum duration of
		 * all possible Resources */
		for ( int nIdAppointment : listIdAppointments )
		{
			AppointmentDTO appointment = AppointmentService.buildAppointmentDTOFromIdAppointment( nIdAppointment );
			listAppointment.add( appointment );

			CalendarAppointmentResourceDTO calendarAppointmentResource = new CalendarAppointmentResourceDTO(
					appointment.getIdAppointment(), appointment.getStartingDateTime().getHour(),
					appointment.getStartingDateTime().getMinute(), appointment.getEndingDateTime().getHour(),
					appointment.getEndingDateTime().getMinute(), getAppointmentRecap( appointment, locale ),
					appointment.getIdForm( ) );
			long startThen = appointment.getStartingDateTime().getHour() * 60
					+ appointment.getStartingDateTime().getMinute();
			long endThen = appointment.getEndingDateTime().getHour() * 60 + appointment.getEndingDateTime().getMinute();

			int nStartingTimeSlot = (int) startThen;

			if (nStartingTimeSlot < nMinGlobalStartingTime)
			{
				nMinGlobalStartingTime = nStartingTimeSlot;
				nStartingHour = appointment.getStartingDateTime().getHour();
				nStartingMinute = appointment.getStartingDateTime().getMinute();
			}

			int nEndingTimeSlot = (int) endThen;

			if (nEndingTimeSlot > nMaxGlobalEndingTime)
			{
				nMaxGlobalEndingTime = nEndingTimeSlot;
				nEndingHour = appointment.getEndingDateTime().getHour();
				nEndingMinute = appointment.getEndingDateTime().getMinute();
			}

			if ( ( calendarAppointmentResource.getDuration() < nMinDuration ) || ( nMinDuration == -1 ) )
			{
				nMinDuration = calendarAppointmentResource.getDuration();
			}

			int nDayOfWeek = appointment.getStartingDateTime().getDayOfWeek().getValue();
			List<CalendarAppointmentResourceDTO> listCalendar = mapCalendarAppointmentResourceByDayOfWeek
					.get( nDayOfWeek );
			listCalendar.add( calendarAppointmentResource );
		}

		List<CalendarDayDTO> listDays = new ArrayList<>(7);

		for (Entry<Integer, List<CalendarAppointmentResourceDTO>> entry : mapCalendarAppointmentResourceByDayOfWeek
				.entrySet())
		{
			CalendarDayDTO day = new CalendarDayDTO();
			Calendar calendarDay = new GregorianCalendar();
			calendarDay.setTime( dateMonday );
			calendarDay.add( Calendar.DAY_OF_WEEK, entry.getKey() - 1 );
			day.setDate( calendarDay.getTime() );

			List<CalendarAppointmentResourceDTO> listCalendarApp = entry.getValue();
			Collections.sort( listCalendarApp );
			day.setListAppointmentResourceDTO( listCalendarApp );
			listDays.add( day );
		}

		Collections.sort(listDays);

		List<AppointmentFormDTO> listForm = FormService.buildAllActiveAppointmentForm();

		/* Set the calendar of the resource, and make sure the range of the time / date
		* periods contains all the possible Forms */
		for (AppointmentFormDTO form : listForm)
		{
			FormResourceCalendar resourceTimeRange = getResourceCalendarTimeRange( form.getIdForm( ) );
			
			int nOpeningTime = (resourceTimeRange.getStartingTime().getHour() * 60) + resourceTimeRange.getStartingTime().getMinute();

			if (nOpeningTime < nMinGlobalStartingTime)
			{
				nMinGlobalStartingTime = nOpeningTime;
				nStartingHour = resourceTimeRange.getStartingTime().getHour();
				nStartingMinute = resourceTimeRange.getStartingTime().getMinute();
			}

			int nClosingTime = (resourceTimeRange.getEndingTime().getHour() * 60) + resourceTimeRange.getEndingTime().getMinute();

			if (nClosingTime > nMaxGlobalEndingTime)
			{
				nMaxGlobalEndingTime = nClosingTime;
				nEndingHour = resourceTimeRange.getEndingTime().getHour();
				nEndingMinute = resourceTimeRange.getEndingTime().getMinute();
			}

			// Set the minimum duration for an appointment
			if (( resourceTimeRange.getDuration() < nMinDuration ) || (nMinDuration < 0 ) )
			{
				nMinDuration = resourceTimeRange.getDuration();
			}
		}
		
		model.put( MARK_LIST_DAYS, listDays );
		model.put( PARAMETER_OFFSET_WEEK, nOffsetWeek );
		model.put( MARK_LIST_DAYS_OF_WEEK, MESSAGE_LIST_DAYS_OF_WEEK );
		model.put( MARK_STARTING_TIME, nMinGlobalStartingTime );
		model.put( MARK_ENDING_TIME, nMaxGlobalEndingTime );
		model.put( MARK_DURATION, nMinDuration ); // nMaxDuration
		model.put( MARK_STARTING_HOUR, nStartingHour );
		model.put( MARK_STARTING_MINUTE, nStartingMinute );
		model.put( MARK_ENDING_HOUR, nEndingHour );
		model.put( MARK_ENDING_MINUTE, nEndingMinute );

		HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_RESOURCE_CALENDAR, locale, model );

		return template.getHtml( );
	}

	/**
	 * Get the time range and duration during which a resource is used / can be used for an appointment 
	 * 
	 * @param idForm
	 * 			ID of the form used to retrieve the specific time range, appointment duration, etc.
	 * @return a FormResourceCalendar object which contains the time range and duration for a used resource
	 */
	private static FormResourceCalendar getResourceCalendarTimeRange( int idForm )
	{
		List<ReservationRule> listReservationRules = ReservationRuleService.findListReservationRule( idForm );

		LocalTime maxEndingTime = WeekDefinitionService.getMaxEndingTimeOfAListOfWeekDefinition( listReservationRules );
		LocalTime minStartingTime = WeekDefinitionService
				.getMinStartingTimeOfAListOfWeekDefinition( listReservationRules );

		int maxAppointmentDuration = DEFAULT_APPOINTMENT_DURATION;

		Optional<ReservationRule> reservationRuleWithMaxDuration = listReservationRules.stream( )
			      .max( Comparator.comparingInt( ReservationRule::getDurationAppointments ) );
		
		if ( reservationRuleWithMaxDuration.isPresent( ) ) {
			maxAppointmentDuration = reservationRuleWithMaxDuration.get( ).getDurationAppointments( );
		}
		return new FormResourceCalendar( idForm, minStartingTime, maxEndingTime, maxAppointmentDuration );
	}

	/**
	 * Get the description of an appointment
	 * 
	 * @param appointment The appointment
	 * @param locale      The locale
	 * @return The HTML code of the description of the appointment
	 */
	private static String getAppointmentRecap(AppointmentDTO appointment, Locale locale) {
		Map<String, Object> model = new HashMap<>();
		model.put(MARK_APPOINTMENT, appointment);

		HtmlTemplate template = AppTemplateService.getTemplate(TEMPLATE_APPOINTMENT_DESCRIPTION, locale, model);

		return template.getHtml();
	}

	/**
	 * Get the URL of the calendar of a resource
	 * 
	 * @param strIdResource   The id of the resource
	 * @param strResourceType the resource type
	 * @return The URL of the calendar of the resource. Note that the base URL is
	 *         not prefixed to the URL.
	 */
	public static String getUrlResourceCalendar(String strIdResource, String strResourceType) {
		UrlItem urlItem = new UrlItem(CONTROLLER_JSP_URL);
		urlItem.addParameter(MVCUtils.PARAMETER_VIEW, VIEW_RESOURCE_CALENDAR);
		urlItem.addParameter(PARAMETER_ID_RESOURCE, strIdResource);
		urlItem.addParameter(PARAMETER_RESOURCE_TYPE, strResourceType);

		return urlItem.getUrl();
	}

	/**
	 * Get the date of a Monday.
	 * 
	 * @param nOffsetWeek The offset of the week (0 for the current week, 1 for the
	 *                    next one, ...)
	 * @return The date of the Monday of the requested week
	 */
	private static Date getDateMonday(int nOffsetWeek) {
		Date date = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance(Locale.FRANCE);
		calendar.setTime(date);
		// We set the week to the requested one
		calendar.add(Calendar.DAY_OF_MONTH, 7 * nOffsetWeek);

		// We get the current day of the week
		int nCurrentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		// We add the day of the week to Monday on the calendar
		calendar.add(Calendar.DAY_OF_WEEK, Calendar.MONDAY - nCurrentDayOfWeek);

		return new Date(calendar.getTimeInMillis());
	}

	/**
	 * Parse a string representing a positive or negative integer
	 * 
	 * @param strNumber The string to parse
	 * @return The integer value of the number represented by the string, or 0 if
	 *         the string could not be parsed
	 */
	private int parseInt(String strNumber) {
		int nNumber = 0;

		if (StringUtils.isEmpty(strNumber)) {
			return nNumber;
		}

		if (strNumber.startsWith("-")) {
			String strParseableNumber = strNumber.substring(1);

			if (StringUtils.isNumeric(strParseableNumber)) {
				nNumber = Integer.parseInt(strParseableNumber) * -1;
			}
		} else if (StringUtils.isNumeric(strNumber)) {
			nNumber = Integer.parseInt(strNumber);
		}

		return nNumber;
	}
}
