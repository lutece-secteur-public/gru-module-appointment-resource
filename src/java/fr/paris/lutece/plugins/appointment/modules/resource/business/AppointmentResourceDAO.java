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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Appointment resource DAO
 */
public class AppointmentResourceDAO implements IAppointmentResourceDAO
{
    // Selects
    private static final String SQL_QUERY_SELECT_ALL = " SELECT id_appointment, id_app_form_res_type, id_resource FROM appointment_resource_app_res ";
    private static final String SQL_QUERY_SELECT_BY_ID_APPOINTMENT = SQL_QUERY_SELECT_ALL + " WHERE id_appointment = ? ";
    private static final String SQL_QUERY_SELECT_BY_PRIMARY_KEY = SQL_QUERY_SELECT_ALL + " WHERE id_appointment = ? AND id_app_form_res_type = ? ";
    private static final String SQL_QUERY_IS_RESOURCE_AVAILABLE = " SELECT ar.id_resource FROM appointment_resource_app_res ar "
            + " INNER JOIN appointment_resource_form_rt frt ON ar.id_app_form_res_type = frt.id "
            + " INNER JOIN appointment_appointment a ON ar.id_appointment = a.id_appointment" + " INNER JOIN appointment_slot s ON s.id_slot = a.id_slot"
            + " WHERE ar.id_resource = ?" + " AND frt.resource_type_name = ?" + " AND a.is_cancelled != ?"
            + " AND ( ( ( s.starting_date_time ) < ? && ( s.ending_date_time ) > ? )"
            + "     || ( ( s.starting_date_time ) < ? && ( s.ending_date_time ) > ? )"
            + "     || ( ( s.starting_date_time ) > ? && ( s.ending_date_time ) < ? )"
            + "     || ( ( s.starting_date_time ) = ? && ( s.ending_date_time ) = ? )" + "     )";
    private static final String SQL_QUERY_FIND_ID_APPOINTMENT_BY_RESAOURCE_AND_DATE = "SELECT ar.id_appointment FROM appointment_resource_app_res ar INNER JOIN appointment_appointment a ON ar.id_appointment = a.id_appointment INNER JOIN appointment_resource_form_rt frt ON ar.id_app_form_res_type = frt.id "
            + " INNER JOIN appointment_slot s ON s.id_slot = a.id_slot WHERE ar.id_resource = ? AND frt.resource_type_name = ? AND s.starting_date_time > ? AND s.ending_date_time < ? AND a.is_cancelled != ? ";

    // Insert, update
    private static final String SQL_QUERY_INSERT = " INSERT INTO appointment_resource_app_res (id_appointment,id_app_form_res_type,id_resource) VALUES (?,?,?) ";
    private static final String SQL_QUERY_UPDATE = " UPDATE appointment_resource_app_res SET id_resource=? WHERE id_appointment = ? AND id_app_form_res_type = ?";

    // Delete
    private static final String SQL_QUERY_DELETE_BY_PRIMARY_KEY = " DELETE FROM appointment_resource_app_res WHERE id_appointment = ? AND id_app_form_res_type = ? ";
    private static final String SQL_QUERY_DELETE_BY_ID_APPOINTMENT = " DELETE FROM appointment_resource_app_res WHERE id_appointment = ? ";
    private static final String SQL_QUERY_DELETE_BY_ID_APPOINTMENT_FORM_RESOURCE_TYPE = " DELETE FROM appointment_resource_app_res WHERE id_app_form_res_type = ? ";

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert( AppointmentResource resource, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        int nIndex = 1;
        daoUtil.setInt( nIndex++, resource.getIdAppointment( ) );
        daoUtil.setInt( nIndex++, resource.getIdAppointmentFormResourceType( ) );
        daoUtil.setString( nIndex, resource.getIdResource( ) );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AppointmentResource findByPrimaryKey( int nIdAppointment, int nIdAppointmentFormResourceType, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_PRIMARY_KEY, plugin );
        daoUtil.setInt( 1, nIdAppointment );
        daoUtil.setInt( 2, nIdAppointmentFormResourceType );
        daoUtil.executeQuery( );

        AppointmentResource resource = null;

        if ( daoUtil.next( ) )
        {
            resource = getFromDAOUtil( daoUtil );
        }

        daoUtil.free( );

        return resource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AppointmentResource> findByIdAppointment( int nIdAppointment, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID_APPOINTMENT, plugin );
        daoUtil.setInt( 1, nIdAppointment );
        daoUtil.executeQuery( );

        List<AppointmentResource> listAppointmentResource = new ArrayList<AppointmentResource>( );

        while ( daoUtil.next( ) )
        {
            listAppointmentResource.add( getFromDAOUtil( daoUtil ) );
        }

        daoUtil.free( );

        return listAppointmentResource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update( AppointmentResource resource, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;
        daoUtil.setString( nIndex++, resource.getIdResource( ) );
        daoUtil.setInt( nIndex++, resource.getIdAppointment( ) );
        daoUtil.setInt( nIndex, resource.getIdAppointmentFormResourceType( ) );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nIdAppointment, int nIdAppointmentFormResourceType, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_PRIMARY_KEY, plugin );
        daoUtil.setInt( 1, nIdAppointment );
        daoUtil.setInt( 2, nIdAppointmentFormResourceType );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteByIdAppointment( int nIdAppointment, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_ID_APPOINTMENT, plugin );
        daoUtil.setInt( 1, nIdAppointment );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteByIdAppointmentFormResourceType( int nIdAppointmentFormResourceType, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_ID_APPOINTMENT_FORM_RESOURCE_TYPE, plugin );
        daoUtil.setInt( 1, nIdAppointmentFormResourceType );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isResourceAvailable( String strIdResource, String strResourceTypeName, Timestamp nStartingTime, Timestamp nEndingTime, Plugin plugin )
    {

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_IS_RESOURCE_AVAILABLE, plugin );
        int nIndex = 1;
        daoUtil.setString( nIndex++, strIdResource );
        daoUtil.setString( nIndex++, strResourceTypeName );
        // daoUtil.setDate( nIndex++, dateDay );
        daoUtil.setBoolean( nIndex++, true );
        daoUtil.setTimestamp( nIndex++, nStartingTime );
        daoUtil.setTimestamp( nIndex++, nStartingTime );
        daoUtil.setTimestamp( nIndex++, nEndingTime );
        daoUtil.setTimestamp( nIndex++, nEndingTime );
        daoUtil.setTimestamp( nIndex++, nStartingTime );
        daoUtil.setTimestamp( nIndex++, nEndingTime );
        daoUtil.setTimestamp( nIndex++, nStartingTime );
        daoUtil.setTimestamp( nIndex, nEndingTime );

        daoUtil.executeQuery( );

        boolean bHasResult = true;

        if ( daoUtil.next( ) )
        {
            bHasResult = false;
        }

        daoUtil.free( );

        return bHasResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> findIdAppointmentsByResourceAndDate( String strIdResource, String strResourceType, Date dateMin, Date dateMax, Plugin plugin )
    {

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_ID_APPOINTMENT_BY_RESAOURCE_AND_DATE, plugin );
        daoUtil.setString( 1, strIdResource );
        daoUtil.setString( 2, strResourceType );
        daoUtil.setTimestamp( 3, new Timestamp( dateMin.getTime( ) ) );
        daoUtil.setTimestamp( 4, new Timestamp( dateMax.getTime( ) ) );
        daoUtil.setBoolean( 5, true );

        List<Integer> listIdAppointments = new ArrayList<Integer>( );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            listIdAppointments.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );

        return listIdAppointments;
    }

    /**
     * Get an appointment resource from a DAOUTil.
     * 
     * @param daoUtil
     *            The daoUTil
     * @return The appointment resource
     */
    private AppointmentResource getFromDAOUtil( DAOUtil daoUtil )
    {
        AppointmentResource resource = new AppointmentResource( );
        int nIndex = 1;
        resource.setIdAppointment( daoUtil.getInt( nIndex++ ) );
        resource.setIdAppointmentFormResourceType( daoUtil.getInt( nIndex++ ) );
        resource.setIdResource( daoUtil.getString( nIndex ) );

        return resource;
    }
}
