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

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Class to describe an association between an appointment form and a resource
 * type
 */
public class AppointmentFormResourceType implements Serializable
{
    private static final long serialVersionUID = -6423365056677937203L;
    private int _nId;
    @Min( value = 1, message = "#i18n{module.appointment.resource.model.entity.appointmentFormResourceType.attribute.idAppointmentForm.min}" )
    private int _nIdAppointmentForm;
    @Size( min = 1, max = 255, message = "#i18n{module.appointment.resource.model.entity.appointmentFormResourceType.attribute.description.size}" )
    @NotNull( message = "#i18n{module.appointment.resource.model.entity.appointmentFormResourceType.attribute.description.notNull}" )
    private String _strDescription;
    @Size( min = 1, max = 255, message = "#i18n{module.appointment.resource.model.entity.appointmentFormResourceType.attribute.resourceTypeName.size}" )
    private String _strResourceTypeName;
    private boolean _bIsAppointmentAdminUser;
    private boolean _bIsLocation;

    /**
     * Get the id of the appointment form resource type
     * @return The id of the appointment form resource type
     */
    public int getId( )
    {
        return _nId;
    }

    /**
     * Set the id of the appointment form resource type
     * @param nId The id of the appointment form resource type
     */
    public void setId( int nId )
    {
        this._nId = nId;
    }

    /**
     * Get the id of the appointment form
     * @return The id of the appointment form
     */
    public int getIdAppointmentForm( )
    {
        return _nIdAppointmentForm;
    }

    /**
     * Set the id of the appointment form
     * @param nIdAppointmentForm The id of the appointment form
     */
    public void setIdAppointmentForm( int nIdAppointmentForm )
    {
        this._nIdAppointmentForm = nIdAppointmentForm;
    }

    /**
     * Get the description of the the appointment form resource type
     * @return The description of the the appointment form resource type
     */
    public String getDescription( )
    {
        return _strDescription;
    }

    /**
     * Set the description of the the appointment form resource type
     * @param strDescription The description of the the appointment form
     *            resource type
     */
    public void setDescription( String strDescription )
    {
        this._strDescription = strDescription;
    }

    /**
     * Get the resource type name
     * @return The resource type name
     */
    public String getResourceTypeName( )
    {
        return _strResourceTypeName;
    }

    /**
     * Set the resource type name
     * @param strResourceTypeName The resource type name
     */
    public void setResourceTypeName( String strResourceTypeName )
    {
        this._strResourceTypeName = strResourceTypeName;
    }

    /**
     * Check if this resource should be bound to the admin user id property of
     * the appointment or not
     * @return True if this resource should be bound to the admin user id
     *         property of the appointment, false otherwise
     */
    public boolean getIsAppointmentAdminUser( )
    {
        return _bIsAppointmentAdminUser;
    }

    /**
     * Set whether this resource should be bound to the admin user id property
     * of the appointment
     * @param bIsAppointmentAdminUser True if this resource should be bound to
     *            the admin user id property of the appointment, false otherwise
     */
    public void setIsAppointmentAdminUser( boolean bIsAppointmentAdminUser )
    {
        this._bIsAppointmentAdminUser = bIsAppointmentAdminUser;
    }

    /**
     * Check if this resource defines the location of the appointment
     * @return True if this resource defines the location of the appointment
     */
    public boolean getIsLocation( )
    {
        return _bIsLocation;
    }

    /**
     * Set whether this resource defines the location of the appointment
     * @param bIsLocation True if this resource defines the location of
     *            the appointment, false otherwise
     */
    public void setIsLocation( boolean bIsLocation )
    {
        this._bIsLocation = bIsLocation;
    }
}
