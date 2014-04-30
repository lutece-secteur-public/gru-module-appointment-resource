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

import fr.paris.lutece.portal.service.plugin.Plugin;

import java.sql.Date;
import java.util.List;


/**
 * Interface of appointment resource DAO
 */
public interface IAppointmentResourceDAO
{
    /**
     * NAme of the bean of the implementation of the DAO
     */
    String BEAN_NAME = "appointment-resource.appointmentResourceDAO";

    /**
     * Insert a new appointment resource
     * @param resource The appointment resource to insert
     * @param plugin The plugin
     */
    void insert( AppointmentResource resource, Plugin plugin );

    /**
     * Find an appointment resource from its primary key
     * @param nIdAppointment The id of the appointment
     * @param nIdAppointmentFormResourceType the id of the appointment form
     *            resource type
     * @param plugin The plugin
     * @return The appointment resource, or null if no appointment resource has
     *         the given id
     */
    AppointmentResource findByPrimaryKey( int nIdAppointment, int nIdAppointmentFormResourceType, Plugin plugin );

    /**
     * Find appointment resource associated with a given appointment
     * @param nIdAppointment the id of the appointment
     * @param plugin The plugin
     * @return The list of appointment resource associated with the given
     *         appointment, or an empty list if no appointment resource is
     *         associated with the appointment
     */
    List<AppointmentResource> findByIdAppointment( int nIdAppointment, Plugin plugin );

    /**
     * Update an appointment resource
     * @param resource The appointment resource to update
     * @param plugin The plugin
     */
    void update( AppointmentResource resource, Plugin plugin );

    /**
     * Delete an appointment resource
     * @param nIdAppointment The id of the appointment of the appointment
     *            resource
     * @param nIdAppointmentFormResourceType The id of the appointment form
     *            resource type of the appointment resource
     * @param plugin The plugin
     */
    void delete( int nIdAppointment, int nIdAppointmentFormResourceType, Plugin plugin );

    /**
     * Delete appointment resource associated with a given appointment
     * @param nIdAppointment The id of the appointment
     * @param plugin The plugin
     */
    void deleteByIdAppointment( int nIdAppointment, Plugin plugin );

    /**
     * Delete appointment resource associated with a given appointment form
     * resource type
     * @param nIdAppointmentFormResourceType The id of the appointment form
     *            resource type
     * @param plugin The plugin
     */
    void deleteByIdAppointmentFormResourceType( int nIdAppointmentFormResourceType, Plugin plugin );

    /**
     * Check if a resource is available for a given period, or if it has already
     * been associated with an appointment
     * @param strIdResource The id of the resource
     * @param strResourceTypeName The type of the resource
     * @param dateDay The date of the day to check for availability
     * @param nHourBegin The beginning hour
     * @param nMinuteBegin The beginning minute
     * @param nHourEnd the ending hour
     * @param nMinuteEnd The ending minute
     * @param plugin the plugin
     * @return True if the resource is available, false otherwise
     */
    boolean isResourceAvailable( String strIdResource, String strResourceTypeName, Date dateDay, int nHourBegin,
            int nMinuteBegin, int nHourEnd, int nMinuteEnd, Plugin plugin );

    /**
     * Get the list of id of appointments a resource is associated to between
     * two given dates
     * @param strIdResource The id of the resource
     * @param strResourceType The resource type
     * @param dateMin The minimum date
     * @param dateMax the maximum date
     * @param plugin The plugin
     * @return The list of ids of appointments
     */
    List<Integer> findIdAppointmentsByResourceAndDate( String strIdResource, String strResourceType, Date dateMin,
            Date dateMax, Plugin plugin );
}
