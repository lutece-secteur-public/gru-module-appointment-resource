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
package fr.paris.lutece.plugins.appointment.modules.resource.business.calendar;

import java.io.Serializable;

/**
 * DTO to display appointments of resources in a calendar
 */
public class CalendarAppointmentResourceDTO implements Serializable, Comparable<CalendarAppointmentResourceDTO>
{
    private static final long serialVersionUID = 8142423630911536264L;
    private int _nIdApppointment;
    private int _nStartingHour;
    private int _nStartingMinute;
    private int _nEndingHour;
    private int _nEndingMinute;
    private int _nDuration;
    private String _appointmentDescription;
    private int _nIdForm;

    /**
     * Creates a new calendar appointment resource DTO
     * 
     * @param nIdApppointment
     *            The id of the appointment
     * @param nStartingHour
     *            The starting hour
     * @param nStartingMinute
     *            The starting minute
     * @param nEndingHour
     *            The ending hour
     * @param nEndingMinute
     *            The ending minute
     * @param appointmentDescription
     *            The description of the appointment
     * @param nIdForm
     *            The id of the form
     */
    public CalendarAppointmentResourceDTO( int nIdApppointment, int nStartingHour, int nStartingMinute, int nEndingHour, int nEndingMinute,
            String appointmentDescription, int nIdForm )
    {
        this._nIdApppointment = nIdApppointment;
        this._nStartingHour = nStartingHour;
        this._nStartingMinute = nStartingMinute;
        this._nEndingHour = nEndingHour;
        this._nEndingMinute = nEndingMinute;
        this._appointmentDescription = appointmentDescription;
        this._nIdForm = nIdForm;
    }

    /**
     * Get the id of the associated appointment
     * 
     * @return The id of the associated appointment
     */
    public int getIdApppointment( )
    {
        return _nIdApppointment;
    }

    /**
     * Set the id of the associated appointment
     * 
     * @param nIdApppointment
     *            The id of the associated appointment
     */
    public void setIdApppointment( int nIdApppointment )
    {
        this._nIdApppointment = nIdApppointment;
    }

    /**
     * Get the starting hour of the appointment
     * 
     * @return The starting hour of the appointment
     */
    public int getStartingHour( )
    {
        return _nStartingHour;
    }

    /**
     * Set the starting hour of the appointment
     * 
     * @param nStartingHour
     *            The starting hour of the appointment
     */
    public void setStartingHour( int nStartingHour )
    {
        this._nStartingHour = nStartingHour;
    }

    /**
     * Get the starting minute of the appointment
     * 
     * @return The starting minute of the appointment
     */
    public int getStartingMinute( )
    {
        return _nStartingMinute;
    }

    /**
     * Get the starting minute of the appointment
     * 
     * @param nStartingMinute
     *            The starting minute of the appointment
     */
    public void setStartingMinute( int nStartingMinute )
    {
        this._nStartingMinute = nStartingMinute;
    }

    /**
     * Get the ending hour of the appointment
     * 
     * @return The ending hour of the appointment
     */
    public int getEndingHour( )
    {
        return _nEndingHour;
    }

    /**
     * Set the ending hour of the appointment
     * 
     * @param nEndingHour
     *            The ending hour of the appointment
     */
    public void setEndingHour( int nEndingHour )
    {
        this._nEndingHour = nEndingHour;
    }

    /**
     * Get the ending minute of the appointment
     * 
     * @return The ending minute of the appointment
     */
    public int getEndingMinute( )
    {
        return _nEndingMinute;
    }

    /**
     * Set the ending minute of the appointment
     * 
     * @param nEndingMinute
     *            The ending minute of the appointment
     */
    public void setEndingMinute( int nEndingMinute )
    {
        this._nEndingMinute = nEndingMinute;
    }

    /**
     * Get the duration of the appointment in minutes
     * 
     * @return The duration of the appointment in minutes
     */
    public int getDuration( )
    {
        if ( _nDuration == 0 )
        {
            _nDuration = ( ( ( getEndingHour( ) - getStartingHour( ) ) * 60 ) + getEndingMinute( ) ) - getStartingMinute( );
        }

        return _nDuration;
    }

    /**
     * Get the description of the appointment
     * 
     * @return The description of the appointment
     */
    public String getAppointmentDescription( )
    {
        return _appointmentDescription;
    }

    /**
     * Set the description of the appointment
     * 
     * @param appointmentDescription
     *            The description of the appointment
     */
    public void setAppointmentDescription( String appointmentDescription )
    {
        this._appointmentDescription = appointmentDescription;
    }

    /**
     * Get the id of the associated formulaire
     * 
     * @return The id of the associated formulaire
     */
    public int getIdForm( )
    {
        return _nIdForm;
    }

    /**
     * Set the id of the associated form
     * 
     * @param nIdApppointment
     *            The id of the associated form
     */
    public void setIdForm( int nIdForm )
    {
        this._nIdForm = nIdForm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo( CalendarAppointmentResourceDTO o )
    {
        return ( ( getStartingHour( ) * 60 ) + getStartingMinute( ) ) - ( ( o.getStartingHour( ) * 60 ) + o.getStartingMinute( ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals( Object o )
    {
        if ( o instanceof CalendarAppointmentResourceDTO )
        {
            return compareTo( (CalendarAppointmentResourceDTO) o ) == 0;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode( )
    {
        return super.hashCode( );
    }
}
