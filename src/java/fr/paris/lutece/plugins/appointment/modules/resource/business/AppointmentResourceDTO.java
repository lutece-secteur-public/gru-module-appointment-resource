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

import fr.paris.lutece.plugins.resource.business.IResource;

/**
 * Appointment resource
 */
public class AppointmentResourceDTO extends AppointmentResource
{
    private static final long serialVersionUID = 7297751790666569071L;
    private IResource _resource;
    private AppointmentFormResourceType _formResourceType;

    /**
     * Creates a new appointment resource DTO with data from an appointment resource
     * 
     * @param appResource
     *            The appointment resource to read data from
     */
    public AppointmentResourceDTO( AppointmentResource appResource )
    {
        setIdAppointment( appResource.getIdAppointment( ) );
        setIdAppointmentFormResourceType( appResource.getIdAppointmentFormResourceType( ) );
        setIdResource( appResource.getIdResource( ) );
    }

    /**
     * Get the resource associated with this appointment resource
     * 
     * @return The resource associated with this appointment resource
     */
    public IResource getResource( )
    {
        return _resource;
    }

    /**
     * Set the resource associated with this appointment resource
     * 
     * @param resource
     *            The resource associated with this appointment resource
     */
    public void setResource( IResource resource )
    {
        this._resource = resource;
    }

    /**
     * Get the form resource type associated with this appointment resource
     * 
     * @return The form resource type associated with this appointment resource
     */
    public AppointmentFormResourceType getFormResourceType( )
    {
        return _formResourceType;
    }

    /**
     * Set the form resource type associated with this appointment resource
     * 
     * @param formResourceType
     *            The form resource type associated with this appointment resource
     */
    public void setFormResourceType( AppointmentFormResourceType formResourceType )
    {
        this._formResourceType = formResourceType;
    }
}
