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
package fr.paris.lutece.plugins.appointment.modules.resource.service.action;

import fr.paris.lutece.plugins.appointment.modules.resource.web.AppointmentResourceJspBean;
import fr.paris.lutece.plugins.resource.service.action.IResourceAction;
import fr.paris.lutece.portal.service.i18n.I18nService;

import java.util.Locale;


/**
 * Action to display the calendar of resources
 */
public class ResourceCalendarAction implements IResourceAction
{
    private static final String MESSAGE_RESOURCE_CALENDAR_ACTION_TITLE = "module.appointment.resource.resourceCalendarAction.title";
    private static final String RESOURCE_CALENDAR_ACTION_ICON = "fa fa-calendar";

    /**
     * {@inheritDoc}
     * @return Always return <i>true</i>
     */
    @Override
    public boolean canActionBePerformed( String strIdResource, String strResourceType )
    {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getActionUrl( String strIdResource, String strResourceType )
    {
        return AppointmentResourceJspBean.getUrlResourceCalendar( strIdResource, strResourceType );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIcon( String strIdResource, String strResourceType )
    {
        return RESOURCE_CALENDAR_ACTION_ICON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle( String strIdResource, String strResourceType, Locale locale )
    {
        return I18nService.getLocalizedString( MESSAGE_RESOURCE_CALENDAR_ACTION_TITLE, locale );
    }
}
