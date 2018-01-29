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
package fr.paris.lutece.plugins.appointment.modules.resource.business.workflow;

/**
 * Set appointment resource history
 */
public class SetAppointmentResourceHistory
{
    private int _nId;
    private int _nIdHistory;
    private int _nIdAppointment;
    private String _strIdResource;
    private int _nIdFormResourceType;

    /**
     * Get the primary key of the update
     * 
     * @return The primary key of the update
     */
    public int getId( )
    {
        return _nId;
    }

    /**
     * Set the primary key
     * 
     * @param nId
     *            The primary key
     */
    public void setId( int nId )
    {
        this._nId = nId;
    }

    /**
     * Get the id of the history
     * 
     * @return The id of the history
     */
    public int getIdHistory( )
    {
        return _nIdHistory;
    }

    /**
     * Set the id of the history
     * 
     * @param nIdHistory
     *            The id of the history
     */
    public void setIdHistory( int nIdHistory )
    {
        this._nIdHistory = nIdHistory;
    }

    /**
     * Get the id of the appointment associated with this history
     * 
     * @return The id of the appointment associated with this history
     */
    public int getIdAppointment( )
    {
        return _nIdAppointment;
    }

    /**
     * Set the id of the appointment associated with this history
     * 
     * @param nIdAppointment
     *            The id of the appointment associated with this history
     */
    public void setIdAppointment( int nIdAppointment )
    {
        this._nIdAppointment = nIdAppointment;
    }

    /**
     * Get the id of the resource set
     * 
     * @return The the id of the resource set
     */
    public String getIdResource( )
    {
        return _strIdResource;
    }

    /**
     * Set the id of the resource set
     * 
     * @param strIdResource
     *            The id of the resource set
     */
    public void setIdResource( String strIdResource )
    {
        this._strIdResource = strIdResource;
    }

    /**
     * Get the id of the form resource type
     * 
     * @return The id of the form resource type
     */
    public int getIdFormResourceType( )
    {
        return _nIdFormResourceType;
    }

    /**
     * Set the id of the form resource type
     * 
     * @param nIdFormResourceType
     *            The id of the form resource type
     */
    public void setIdFormResourceType( int nIdFormResourceType )
    {
        this._nIdFormResourceType = nIdFormResourceType;
    }
}
