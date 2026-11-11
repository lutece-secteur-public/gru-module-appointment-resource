/*
 * Copyright (c) 2002-2026, City of Paris
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.jupiter.api.Test;

import fr.paris.lutece.plugins.appointment.modules.resource.business.calendar.CalendarAppointmentResourceDTO;
import fr.paris.lutece.plugins.appointment.modules.resource.business.calendar.CalendarDayDTO;
import fr.paris.lutece.portal.service.template.HtmlAttributesMethod;
import fr.paris.lutece.portal.service.template.HtmlMarkup;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.core.HTMLOutputFormat;
import freemarker.template.Configuration;
import freemarker.template.TemplateMethodModelEx;

/**
 * Renders the appointment-resource templates with HTML auto-escaping (LUT-33153), the back office macros of the core
 * (the webapp exploded by lutece:exploded) and the core function htmlAttributes in strict mode: the week calendar and
 * the descriptions of its appointments, HTML rendered in Java, are marked with HtmlMarkup and keep their tags; the data
 * they hold, the page to go back to and the names of the resources are escaped.
 */
public class AppointmentResourceTemplatesRenderTest
{
    private static final String TEMPLATES_ROOT = "webapp/WEB-INF/templates";
    private static final String CORE_TEMPLATES_ROOT = "target/lutece/WEB-INF/templates";
    private static final String COMMONS = "commons_bs5_tabler.html";
    private static final String PATH = "admin/plugins/appointment/modules/resource/";
    private static final String TEMPLATE_VIEW_USER_CALENDAR = PATH + "view_user_calendar.html";
    private static final String TEMPLATE_VIEW_RESOURCE_CALENDAR = PATH + "view_resource_calendar.html";
    private static final String TEMPLATE_DASHBOARD = PATH + "user_calendar_dashboard.html";
    private static final String TEMPLATE_RESOURCE_CALENDAR = PATH + "resource_calendar.html";
    private static final String TEMPLATE_APPOINTMENT_DESCRIPTION = PATH + "appointment_description.html";
    private static final String TEMPLATE_TASK_INFORMATION = PATH + "task_set_appointment_resource_information.html";

    private static final String CALENDAR = "<table class=\"calendar\"><tr><td>Lundi &lt;b&gt;</td></tr></table>";
    private static final String HOSTILE = "Name <b>x</b> \"q\"";
    private static final String HOSTILE_ESCAPED = "Name &lt;b&gt;x&lt;/b&gt; &quot;q&quot;";
    private static final String FROM_URL = "jsp/admin/plugins/appointment/ManageAppointments.jsp?view=a&id_form=1";
    private static final String FROM_URL_ENCODED = "jsp%2Fadmin%2Fplugins%2Fappointment%2FManageAppointments.jsp%3Fview%3Da%26id_form%3D1";

    /**
     * Renders a template of the module with the configuration of the core: HTML output format, auto-escaping, the back
     * office commons included, htmlAttributes strict.
     *
     * @param strTemplate
     *            the template path
     * @param model
     *            the model
     * @return the HTML
     * @throws Exception
     *             if the rendering fails
     */
    private static String render( String strTemplate, Map<String, Object> model ) throws Exception
    {
        File coreTemplates = new File( CORE_TEMPLATES_ROOT );
        assertTrue( new File( coreTemplates, COMMONS ).isFile( ), "core templates not found under " + CORE_TEMPLATES_ROOT + ": run lutece:exploded first" );
        Configuration cfg = new Configuration( Configuration.VERSION_2_3_0 );
        cfg.setOutputFormat( HTMLOutputFormat.INSTANCE );
        cfg.setAutoEscapingPolicy( Configuration.ENABLE_IF_DEFAULT_AUTO_ESCAPING_POLICY );
        cfg.setLocale( Locale.FRENCH );
        cfg.setURLEscapingCharset( "UTF-8" );
        cfg.setNumberFormat( "0.######" );
        cfg.setTemplateLoader( new MultiTemplateLoader( new TemplateLoader [ ] {
                new FileTemplateLoader( new File( TEMPLATES_ROOT ) ), new FileTemplateLoader( coreTemplates )
        } ) );
        cfg.addAutoInclude( COMMONS );
        cfg.setSharedVariable( "htmlAttributes", new HtmlAttributesMethod( true ) );
        cfg.setSharedVariable( "i18n", (TemplateMethodModelEx) arguments -> String.valueOf( arguments.get( 0 ) ) );
        cfg.setSharedVariable( "dskey", (TemplateMethodModelEx) arguments -> "DS" );
        StringWriter writer = new StringWriter( );
        cfg.getTemplate( strTemplate ).process( model, writer );

        return writer.toString( );
    }

    /**
     * The model of the calendar pages, as AppointmentResourceJspBean builds it.
     *
     * @param calendar
     *            the calendar
     * @param strFromUrl
     *            the page to go back to
     * @return the model
     */
    private static Map<String, Object> pageModel( Object calendar, String strFromUrl )
    {
        Map<String, Object> model = new HashMap<>( );
        model.put( "calendar", calendar );
        model.put( "fromUrl", strFromUrl );
        model.put( "offsetWeek", 1 );
        model.put( "resource", Map.of( "idResource", "12", "resourceType", "ADMIN_USER", "resourceName", HOSTILE ) );

        return model;
    }

    /**
     * The week calendar marked as markup keeps its tags in the user and resource calendar pages and in the dashboard.
     *
     * @throws Exception
     *             if the rendering fails
     */
    @Test
    public void testMarkedCalendarKeepsItsTags( ) throws Exception
    {
        for ( String strTemplate : List.of( TEMPLATE_VIEW_USER_CALENDAR, TEMPLATE_VIEW_RESOURCE_CALENDAR ) )
        {
            String strHtml = render( strTemplate, pageModel( HtmlMarkup.of( CALENDAR ), FROM_URL ) );
            assertTrue( strHtml.contains( CALENDAR ), strHtml );
        }

        Map<String, Object> model = new HashMap<>( );
        model.put( "calendar", HtmlMarkup.of( CALENDAR ) );
        model.put( "url", "jsp/admin/AdminMenu.jsp?plugin_name=appointment-resource" );
        String strHtml = render( TEMPLATE_DASHBOARD, model );
        assertTrue( strHtml.contains( CALENDAR ), strHtml );
    }

    /**
     * A calendar left as a String is escaped: the reason the JspBean and the dashboard component mark it.
     *
     * @throws Exception
     *             if the rendering fails
     */
    @Test
    public void testAStringCalendarIsEscaped( ) throws Exception
    {
        String strHtml = render( TEMPLATE_VIEW_USER_CALENDAR, pageModel( CALENDAR, FROM_URL ) );

        assertTrue( strHtml.contains( "&lt;table class=&quot;calendar&quot;&gt;" ), strHtml );
    }

    /**
     * The page to go back to is the back link, and is passed URL-encoded to the links of the other weeks; the name and
     * the type of the resource are escaped.
     *
     * @throws Exception
     *             if the rendering fails
     */
    @Test
    public void testBackUrlAndResourceAreEscaped( ) throws Exception
    {
        String strHtml = render( TEMPLATE_VIEW_RESOURCE_CALENDAR, pageModel( HtmlMarkup.of( CALENDAR ), FROM_URL ) );

        assertTrue( strHtml.contains( "href=\"jsp/admin/plugins/appointment/ManageAppointments.jsp?view=a&amp;id_form=1\"" ), strHtml );
        assertTrue( strHtml.contains( "&amp;resource_type=ADMIN_USER&amp;offsetWeek=0&amp;fromUrl=" + FROM_URL_ENCODED + "\"" ), strHtml );
        assertTrue( strHtml.contains( "&amp;offsetWeek=2&amp;fromUrl=" + FROM_URL_ENCODED + "\"" ), strHtml );
        assertTrue( strHtml.contains( HOSTILE_ESCAPED ), strHtml );
        assertFalse( strHtml.contains( HOSTILE ), strHtml );

        strHtml = render( TEMPLATE_VIEW_USER_CALENDAR, pageModel( HtmlMarkup.of( CALENDAR ), "javascript:alert(1)" ) );
        assertFalse( strHtml.contains( "href=\"javascript:" ), strHtml );
    }

    /**
     * The week calendar writes the descriptions of its appointments, marked as markup, with their tags, and gives the
     * empty cells their time range as title through params.
     *
     * @throws Exception
     *             if the rendering fails
     */
    @Test
    public void testCalendarWritesTheMarkedDescriptions( ) throws Exception
    {
        Map<String, Object> appointment = new HashMap<>( );
        appointment.put( "user", Map.of( "email", "a@b.fr", "lastName", HOSTILE, "firstName", "Jean" ) );
        appointment.put( "location", "Salle <1>" );
        String strDescription = render( TEMPLATE_APPOINTMENT_DESCRIPTION, Map.of( "appointment", appointment ) );
        assertTrue( strDescription.contains( "<a href=\"mailto:a@b.fr\">" ), strDescription );
        assertTrue( strDescription.contains( HOSTILE_ESCAPED + " Jean" ), strDescription );
        assertTrue( strDescription.contains( "Salle &lt;1&gt;" ), strDescription );

        CalendarDayDTO day = new CalendarDayDTO( );
        day.setDate( new Date( ) );
        day.setListAppointmentResourceDTO( List.of( new CalendarAppointmentResourceDTO( 7, 9, 0, 9, 30, strDescription, 3 ) ) );
        Map<String, Object> model = new HashMap<>( );
        model.put( "listDays", List.of( day ) );
        model.put( "list_days_of_week", new String [ ] {
                "module.appointment.resource.manageCalendarSlots.labelMonday"
        } );
        model.put( "appointmentDescriptions", Map.of( "7", HtmlMarkup.of( strDescription ) ) );
        model.put( "startingTime", 9 * 60 );
        model.put( "endingTime", 10 * 60 );
        model.put( "duration", 30 );
        model.put( "startingHour", 9 );
        model.put( "startingMinute", 0 );
        model.put( "endingHour", 10 );
        model.put( "endingMinute", 0 );

        String strHtml = render( TEMPLATE_RESOURCE_CALENDAR, model );

        assertTrue( strHtml.contains( "<div>" + strDescription + "</div>" ), strHtml );
        assertTrue( strHtml.contains( "09h00 - 09h30" ), strHtml );
        assertTrue( strHtml.contains( "title=\"09h30 - 10h00\"" ), strHtml );
        assertFalse( strHtml.contains( "&lt;a href" ), strHtml );
    }

    /**
     * The information of the task lists its messages as text, one per line.
     *
     * @throws Exception
     *             if the rendering fails
     */
    @Test
    public void testTaskInformationWritesTheMessagesAsText( ) throws Exception
    {
        String strHtml = render( TEMPLATE_TASK_INFORMATION, Map.of( "listResourcesSet", List.of( HOSTILE, "Room" ) ) );

        assertTrue( strHtml.contains( HOSTILE_ESCAPED + "<br />" ), strHtml );
        assertTrue( strHtml.contains( "Room<br />" ), strHtml );
        assertFalse( strHtml.contains( HOSTILE ), strHtml );
    }
}
