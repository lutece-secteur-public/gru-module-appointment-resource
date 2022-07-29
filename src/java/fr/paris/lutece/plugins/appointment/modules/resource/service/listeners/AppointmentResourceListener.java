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
package fr.paris.lutece.plugins.appointment.modules.resource.service.listeners;

import fr.paris.lutece.plugins.appointment.modules.resource.business.AppointmentResourceHome;

import fr.paris.lutece.plugins.appointment.service.listeners.IAppointmentListener;
import fr.paris.lutece.portal.service.i18n.I18nService;

import java.util.List;
import java.util.Locale;

/**
 * Listener implementation for appointment resources
 */
public class AppointmentResourceListener implements IAppointmentListener
{
    private static final String MESSAGE_APPOINTMENT_RESOURCE_REMOVED = "module.appointment.resource.messageAppointmentResourceRemoved";

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyAppointmentRemoval( int nIdAppointment )
    {
        AppointmentResourceHome.deleteByIdAppointment( nIdAppointment );
    }

    /**
     * {@inheritDoc}
     */
    public String appointmentDateChanged( int nIdAppointment, int nIdSlot, Locale locale )
    {

        AppointmentResourceHome.deleteByIdAppointment( nIdAppointment );

        return I18nService.getLocalizedString( MESSAGE_APPOINTMENT_RESOURCE_REMOVED, locale );
    }

	@Override
	public void notifyAppointmentCreated(int nIdAppointment) {
		 // Do nothing
		
	}

	@Override
	public void notifyAppointmentUpdated(int nIdAppointment) {
		
		 // Do nothing
	}

	@Override
	public String appointmentDateChanged(int nIdAppointment, List<Integer> listIdSlot, Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}
}
