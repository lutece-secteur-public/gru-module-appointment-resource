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
package fr.paris.lutece.plugins.appointment.modules.resource.service.listeners;

import java.util.Locale;
import java.util.function.BiPredicate;

import org.apache.commons.lang3.math.NumberUtils;

import fr.paris.lutece.plugins.appointment.modules.resource.business.AppointmentResourceHome;
import fr.paris.lutece.plugins.resource.business.database.DatabaseResource;
import fr.paris.lutece.plugins.resource.business.database.DatabaseResourceHome;
import fr.paris.lutece.plugins.resource.service.listeners.IDatabaseResourceRemovalListener;
import fr.paris.lutece.portal.service.i18n.I18nService;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Refuses the removal of a database resource assigned to appointments: the next resource created would get its id and
 * inherit its appointments.
 */
@ApplicationScoped
public class AppointmentDatabaseResourceRemovalListener implements IDatabaseResourceRemovalListener
{
    private static final String MESSAGE_RESOURCE_ASSIGNED = "module.appointment.resource.message.resourceAssignedToAppointments";

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canBeRemoved( String strIdResource )
    {
        DatabaseResource resource = DatabaseResourceHome.findByPrimaryKey( NumberUtils.toInt( strIdResource, -1 ) );

        return canBeRemoved( strIdResource, ( resource != null ) ? resource.getResourceType( ) : null, AppointmentResourceHome::isResourceAssigned );
    }

    /**
     * Tells whether a database resource can be removed: it is not assigned to any appointment.
     *
     * @param strIdResource
     *            the id of the resource
     * @param strResourceType
     *            the resource type of the resource, null when the resource does not exist
     * @param isAssigned
     *            tells whether a resource (id, resource type) is assigned to an appointment
     * @return true if the resource can be removed
     */
    static boolean canBeRemoved( String strIdResource, String strResourceType, BiPredicate<String, String> isAssigned )
    {
        return strResourceType == null || !isAssigned.test( strIdResource, strResourceType );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRemovalRefusedMessage( String strIdResource, Locale locale )
    {
        return I18nService.getLocalizedString( MESSAGE_RESOURCE_ASSIGNED, locale );
    }
}
