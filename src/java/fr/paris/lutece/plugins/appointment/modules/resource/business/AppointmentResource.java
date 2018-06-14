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
package fr.paris.lutece.plugins.appointment.modules.resource.business;

import java.io.Serializable;

/**
 * Appointment resource
 */
public class AppointmentResource implements Serializable
{
    private static final long serialVersionUID = -5425541631701969940L;
    private int _nIdAppointment;
    private int _nIdAppointmentFormResourceType;
    private String _strIdResource;

    /**
     * Get the id of the appointment
     * 
     * @return The id of the appointment
     */
    public int getIdAppointment( )
    {
        return _nIdAppointment;
    }

    /**
     * Set the id of the appointment
     * 
     * @param nIdAppointment
     *            The id of the appointment
     */
    public void setIdAppointment( int nIdAppointment )
    {
        this._nIdAppointment = nIdAppointment;
    }

    /**
     * Get the id of the appointment form resource type
     * 
     * @return The id of the appointment form resource type
     */
    public int getIdAppointmentFormResourceType( )
    {
        return _nIdAppointmentFormResourceType;
    }

    /**
     * Set the id of the appointment form resource type
     * 
     * @param nIdAppointmentFormResourceType
     *            The id of the appointment form resource type
     */
    public void setIdAppointmentFormResourceType( int nIdAppointmentFormResourceType )
    {
        this._nIdAppointmentFormResourceType = nIdAppointmentFormResourceType;
    }

    /**
     * Get the id of the resource
     * 
     * @return the id of the resource
     */
    public String getIdResource( )
    {
        return _strIdResource;
    }

    /**
     * Set the id of the resource
     * 
     * @param strIdResource
     *            The id of the resource
     */
    public void setIdResource( String strIdResource )
    {
        this._strIdResource = strIdResource;
    }
}
