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

import java.util.Date;
import java.util.List;

/**
 * DTO to display days in a calendar
 */
public class CalendarDayDTO implements Comparable<CalendarDayDTO>
{
    private List<CalendarAppointmentResourceDTO> _listAppointmentResourceDTO;
    private Date _date;

    /**
     * Get the list of appointment resources DTO
     * 
     * @return The list of appointment resources DTO
     */
    public List<CalendarAppointmentResourceDTO> getListAppointmentResourceDTO( )
    {
        return _listAppointmentResourceDTO;
    }

    /**
     * Set the list of appointment resources DTO
     * 
     * @param listAppointmentResourceDTO
     *            The list of appointment resources DTO
     */
    public void setListAppointmentResourceDTO( List<CalendarAppointmentResourceDTO> listAppointmentResourceDTO )
    {
        this._listAppointmentResourceDTO = listAppointmentResourceDTO;
    }

    /**
     * Get the date of the day
     * 
     * @return The date of the day
     */
    public Date getDate( )
    {
        return _date;
    }

    /**
     * Set the date of the day
     * 
     * @param date
     *            The date of the day
     */
    public void setDate( Date date )
    {
        this._date = date;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo( CalendarDayDTO o )
    {
        if ( getDate( ) == null )
        {
            if ( o.getDate( ) == null )
            {
                return 0;
            }

            return -1;
        }

        if ( o.getDate( ) == null )
        {
            return 1;
        }

        return getDate( ).compareTo( o.getDate( ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals( Object o )
    {
        if ( o instanceof CalendarDayDTO )
        {
            return compareTo( (CalendarDayDTO) o ) == 0;
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
