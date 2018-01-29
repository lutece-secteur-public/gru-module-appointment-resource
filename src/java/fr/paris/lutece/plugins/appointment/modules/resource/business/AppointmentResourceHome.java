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

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Home for appointment resource
 */
public final class AppointmentResourceHome
{
    private static IAppointmentResourceDAO _dao = SpringContextService.getBean( IAppointmentResourceDAO.BEAN_NAME );
    private static Plugin _plugin = PluginService.getPlugin( AppointmentResourcePlugin.PLUGIN_NAME );

    /**
     * Default constructor
     */
    private AppointmentResourceHome( )
    {
        // Do nothing
    }

    /**
     * Insert a new appointment resource
     * 
     * @param resource
     *            The appointment resource to insert
     */
    public static void insert( AppointmentResource resource )
    {
        _dao.insert( resource, _plugin );
    }

    /**
     * Find an appointment resource from its primary key
     * 
     * @param nIdAppointment
     *            The id of the appointment
     * @param nIdAppointmentFormResourceType
     *            the id of the appointment form resource type
     * @return The appointment resource, or null if no appointment resource has the given id
     */
    public static AppointmentResource findByPrimaryKey( int nIdAppointment, int nIdAppointmentFormResourceType )
    {
        return _dao.findByPrimaryKey( nIdAppointment, nIdAppointmentFormResourceType, _plugin );
    }

    /**
     * Find appointment resource associated with a given appointment
     * 
     * @param nIdAppointment
     *            the id of the appointment
     * @return The list of appointment resource associated with the given appointment, or an empty list if no appointment resource is associated with the
     *         appointment
     */
    public static List<AppointmentResource> findByIdAppointment( int nIdAppointment )
    {
        return _dao.findByIdAppointment( nIdAppointment, _plugin );
    }

    /**
     * Update an appointment resource
     * 
     * @param resource
     *            The appointment resource to update
     */
    public static void update( AppointmentResource resource )
    {
        _dao.update( resource, _plugin );
    }

    /**
     * Delete an appointment resource
     * 
     * @param nIdAppointment
     *            The id of the appointment of the appointment resource
     * @param nIdAppointmentFormResourceType
     *            The id of the appointment form resource type of the appointment resource
     */
    public static void delete( int nIdAppointment, int nIdAppointmentFormResourceType )
    {
        _dao.delete( nIdAppointment, nIdAppointmentFormResourceType, _plugin );
    }

    /**
     * Delete appointment resource associated with a given appointment
     * 
     * @param nIdAppointment
     *            The id of the appointment
     */
    public static void deleteByIdAppointment( int nIdAppointment )
    {
        _dao.deleteByIdAppointment( nIdAppointment, _plugin );
    }

    /**
     * Delete appointment resource associated with a given appointment form resource type
     * 
     * @param nIdAppointmentFormResourceType
     *            The id of the appointment form resource type
     */
    public static void deleteByIdAppointmentFormResourceType( int nIdAppointmentFormResourceType )
    {
        _dao.deleteByIdAppointmentFormResourceType( nIdAppointmentFormResourceType, _plugin );
    }

    /**
     * Check if a resource is available for a given period, or if it has already been associated with an appointment
     * 
     * @param strIdResource
     *            The id of the resource
     * @param strResourceTypeName
     *            The type of the resource
     * @param nStartingTime
     *            The beginning time
     * @param nEndingTime
     *            the ending time
     * @return True if the resource is available, false otherwise
     */
    public static boolean isResourceAvailable( String strIdResource, String strResourceTypeName, Timestamp nStartingTime, Timestamp nEndingTime )
    {
        return _dao.isResourceAvailable( strIdResource, strResourceTypeName, nStartingTime, nEndingTime, _plugin );
    }

    /**
     * Get the list of id of appointments a resource is associated to between two given dates
     * 
     * @param strIdResource
     *            The id of the resource
     * @param strResourceType
     *            The resource type
     * @param dateMin
     *            The minimum date
     * @param dateMax
     *            the maximum date
     * @return The list of ids of appointments
     */
    public static List<Integer> findIdAppointmentsByResourceAndDate( String strIdResource, String strResourceType, Date dateMin, Date dateMax )
    {
        return _dao.findIdAppointmentsByResourceAndDate( strIdResource, strResourceType, dateMin, dateMax, _plugin );
    }
}
