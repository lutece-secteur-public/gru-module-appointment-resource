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

import java.util.List;

/**
 * Interface for appointment form resource type DAO
 */
public interface IAppointmentFormResourceTypeDAO
{
    /**
     * Name of the bean of the implementation of the DAO
     */
    String BEAN_NAME = "appointment-resource.appointmentFormResourceTypeDAO";

    /**
     * Create an association between an appointment form and a resource type
     * 
     * @param formResourceType
     *            The appointment form resource type
     * @param plugin
     *            the plugin
     */
    void insert( AppointmentFormResourceType formResourceType, Plugin plugin );

    /**
     * Update an existing appointment form resource type
     * 
     * @param formResourceType
     *            the form resource type
     * @param plugin
     *            the plugin
     */
    void update( AppointmentFormResourceType formResourceType, Plugin plugin );

    /**
     * Remove an association between an appointment form and a resource type
     * 
     * @param nId
     *            The id of the appointment form resource type to remove
     * @param plugin
     *            the plugin
     */
    void remove( int nId, Plugin plugin );

    /**
     * Remove every associations between an appointment form and resource types
     * 
     * @param nIdAppointmentForm
     *            The id of the appointment form
     * @param plugin
     *            The plugin
     */
    void removeFromIdAppointmentForm( int nIdAppointmentForm, Plugin plugin );

    /**
     * Find an appointment form resource type from its primary key
     * 
     * @param nId
     *            the id of the appointment form resource type to get
     * @param plugin
     *            The plugin
     * @return The appointment form resource type, or null if no appointment form with the given id was found
     */
    AppointmentFormResourceType findByPrimaryKey( int nId, Plugin plugin );

    /**
     * Get the list of resource types associated with a given form
     * 
     * @param nIdForm
     *            The id of the form
     * @param plugin
     *            The plugin
     * @return The list of resources associated with the given form, or an empty list if no resource is associated with this form
     */
    List<AppointmentFormResourceType> findResourceTypesListFromIdForm( int nIdForm, Plugin plugin );

    /**
     * Reset the form resource type that was declared as containing the admin user of appointments
     * 
     * @param nIdAppointmentForm
     *            The id of the appointment form
     * @param plugin
     *            The plugin
     */
    void resetAppAdminUser( int nIdAppointmentForm, Plugin plugin );

    /**
     * Reset the form resource type that was declared as containing the localization of appointments
     * 
     * @param nIdAppointmentForm
     *            The id of the appointment form
     * @param plugin
     *            The plugin
     */
    void resetLocalization( int nIdAppointmentForm, Plugin plugin );
}
