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
package fr.paris.lutece.plugins.appointment.modules.resource.service;

import fr.paris.lutece.plugins.appointment.business.appointment.Appointment;
import fr.paris.lutece.plugins.appointment.business.slot.Slot;
import fr.paris.lutece.plugins.appointment.modules.resource.business.AppointmentResource;
import fr.paris.lutece.plugins.appointment.modules.resource.business.AppointmentResourceHome;
import fr.paris.lutece.plugins.appointment.service.SlotService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import org.apache.commons.lang.StringUtils;


/**
 * Service to manage appointment resources
 */
public class AppointmentResourceService
{
    private static final String BEAN_NAME = "appointment-resource.appointmentResourceService";
    private static volatile AppointmentResourceService _instance;

    /**
     * Get the instance of the service
     * @return The instance of the service
     */
    public static AppointmentResourceService getInstance(  )
    {
        if ( _instance == null )
        {
            _instance = SpringContextService.getBean( BEAN_NAME );
        }

        return _instance;
    }

    /**
     * Check if the resource is available for a given appointment
     * @param strIdResource The id of the resource
     * @param strResourceTypeName The resource type
     * @param nIdFormResourceType The id of the form resource type
     * @param appointment The appointment
     * @return true if the resource is available for the given appointment,
     *         false if it is already associated with another resource during
     *         the time of the appointment
     */
    public boolean isResourceAvailableForAppointment( String strIdResource, String strResourceTypeName,
        int nIdFormResourceType, Appointment appointment )
    {
        AppointmentResource appResource = AppointmentResourceHome.findByPrimaryKey( appointment.getIdAppointment(  ),
                nIdFormResourceType );

        if ( ( appResource != null ) && ( !appointment.getIsCancelled() ) &&
                StringUtils.equals( appResource.getIdResource(  ), strIdResource ) )
        {
            // The resource is already associated with this appointment for this form RT, so we allow it to be re-associated
            return true;
        }

        // We now have to check that the resource is not associated to another appointment during the time of this one
        Slot slot = SlotService.findSlotById(appointment.getIdSlot(  ) );

        return AppointmentResourceHome.isResourceAvailable( strIdResource, strResourceTypeName,
             slot.getStartingTimestampDate(  ), slot.getEndingTimestampDate() );
    }
}
