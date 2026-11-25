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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.BiPredicate;

import org.junit.jupiter.api.Test;

/**
 * A database resource assigned to an appointment cannot be removed: its id would be given again to the next resource.
 */
public class AppointmentDatabaseResourceRemovalListenerTest
{
    /** Resource 7 of type "room" is assigned to an appointment */
    private static final BiPredicate<String, String> ASSIGNED = ( strId, strType ) -> "7".equals( strId ) && "room".equals( strType );

    @Test
    public void testAssignedResourceCannotBeRemoved( )
    {
        assertFalse( AppointmentDatabaseResourceRemovalListener.canBeRemoved( "7", "room", ASSIGNED ) );
    }

    @Test
    public void testFreeResourceCanBeRemoved( )
    {
        assertTrue( AppointmentDatabaseResourceRemovalListener.canBeRemoved( "8", "room", ASSIGNED ) );
        assertTrue( AppointmentDatabaseResourceRemovalListener.canBeRemoved( "7", "car", ASSIGNED ), "same id, other resource type" );
        assertTrue( AppointmentDatabaseResourceRemovalListener.canBeRemoved( "9", null, ASSIGNED ), "unknown resource" );
    }
}
