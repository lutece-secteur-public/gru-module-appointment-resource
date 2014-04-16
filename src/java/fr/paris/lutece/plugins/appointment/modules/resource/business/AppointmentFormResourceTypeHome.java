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
package fr.paris.lutece.plugins.appointment.modules.resource.business;

import fr.paris.lutece.plugins.appointment.modules.resource.service.AppointmentResourcePlugin;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.List;


/**
 * Home for appointment form resources
 */
public class AppointmentFormResourceTypeHome
{
    private static Plugin _plugin = PluginService.getPlugin( AppointmentResourcePlugin.PLUGIN_NAME );

    private static final IAppointmentFormResourceTypeDAO _dao = SpringContextService
            .getBean( IAppointmentFormResourceTypeDAO.BEAN_NAME );

    /**
     * Create an association between an appointment form and a resource type
     * @param formResourceType The appointment form resource type
     */
    public static void insert( AppointmentFormResourceType formResourceType )
    {
        _dao.insert( formResourceType, _plugin );
    }

    /**
     * Update an association between an appointment form and a resource type
     * @param formResourceType The appointment form resource type
     */
    public static void update( AppointmentFormResourceType formResourceType )
    {
        _dao.update( formResourceType, _plugin );
    }

    /**
     * Remove an association between an appointment form and a resource type
     * @param nId The id of the appointment form resource type to remove
     */
    public static void remove( int nId )
    {
        _dao.remove( nId, _plugin );
    }

    /**
     * Remove every associations between an appointment form and resource types
     * @param nIdAppointmentForm The id of the appointment form
     */
    public static void removeFromIdAppointmentForm( int nIdAppointmentForm )
    {
        _dao.removeFromIdAppointmentForm( nIdAppointmentForm, _plugin );
    }

    /**
     * Find an appointment form resource type from its primary key
     * @param nId the id of the appointment form resource type to get
     * @return The appointment form resource type, or null if no appointment
     *         form with the given id was found
     */
    public static AppointmentFormResourceType findByPrimaryKey( int nId )
    {
        return _dao.findByPrimaryKey( nId, _plugin );
    }

    /**
     * Get the list of resource types associated with a given form
     * @param nIdForm The id of the form
     * @return The list of appointment form resource types associated with the
     *         given form, or an empty list if no resource is associated with
     *         this form
     */
    public static List<AppointmentFormResourceType> findResourceTypesListFromIdForm( int nIdForm )
    {
        return _dao.findResourceTypesListFromIdForm( nIdForm, _plugin );
    }
}
