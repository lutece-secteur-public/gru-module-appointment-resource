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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Appointment form resource type DAO
 */
public class AppointmentFormResourceTypeDAO implements IAppointmentFormResourceTypeDAO
{
    private static final String SQL_QUERY_NEW_PRIMARY_KEY = " SELECT MAX(id) FROM appointment_resource_form_rt ";
    private static final String SQL_QUERY_SELECT = " SELECT id, id_appointment_form, resource_type_name, description, is_app_admin_user, is_localization FROM appointment_resource_form_rt ";
    private static final String SQL_QUERY_SELECT_BY_PRIMARY_KEY = SQL_QUERY_SELECT + " WHERE id = ? ";
    private static final String SQL_QUERY_SELECT_BY_ID_APPOINTMENT_FORM = SQL_QUERY_SELECT +
        " WHERE id_appointment_form = ? ";

    // Insert, update, delete
    private static final String SQL_QUERY_INSERT = " INSERT INTO appointment_resource_form_rt ( id, id_appointment_form, resource_type_name, description, is_app_admin_user, is_localization ) VALUES (?,?,?,?,?,?) ";
    private static final String SQL_QUERY_UPDATE = " UPDATE appointment_resource_form_rt SET description = ?, is_app_admin_user = ?, is_localization = ? WHERE id = ? ";
    private static final String SQL_QUERY_RESET_APP_ADMIN_USER = "UPDATE appointment_resource_form_rt SET is_app_admin_user = 0 WHERE id_appointment_form= ? ";
    private static final String SQL_QUERY_RESET_APP_LOCALIZATION = "UPDATE appointment_resource_form_rt SET is_localization = 0 WHERE id_appointment_form= ? ";
    private static final String SQL_QUERY_DELETE = " DELETE FROM appointment_resource_form_rt WHERE id = ? ";
    private static final String SQL_QUERY_DELETE_FROM_ID_FORM = " REMOVE FROM appointment_resource_form_rt WHERE id_appointment_form = ? ";

    /**
     * Get a new primary key
     * @param plugin The plugin
     * @return the new value of the primary key
     */
    private int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PRIMARY_KEY, plugin );
        daoUtil.executeQuery(  );

        int nRes = 1;

        if ( daoUtil.next(  ) )
        {
            nRes = daoUtil.getInt( 1 ) + 1;
        }

        daoUtil.free(  );

        return nRes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void insert( AppointmentFormResourceType formResourceType, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        int nIndex = 1;
        formResourceType.setId( newPrimaryKey( plugin ) );
        daoUtil.setInt( nIndex++, formResourceType.getId(  ) );
        daoUtil.setInt( nIndex++, formResourceType.getIdAppointmentForm(  ) );
        daoUtil.setString( nIndex++, formResourceType.getResourceTypeName(  ) );
        daoUtil.setString( nIndex++, formResourceType.getDescription(  ) );
        daoUtil.setBoolean( nIndex++, formResourceType.getIsAppointmentAdminUser(  ) );
        daoUtil.setBoolean( nIndex, formResourceType.getIsLocation(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update( AppointmentFormResourceType formResourceType, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;
        daoUtil.setString( nIndex++, formResourceType.getDescription(  ) );
        daoUtil.setBoolean( nIndex++, formResourceType.getIsAppointmentAdminUser(  ) );
        daoUtil.setBoolean( nIndex++, formResourceType.getIsLocation(  ) );
        daoUtil.setInt( nIndex, formResourceType.getId(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        int nIndex = 1;
        daoUtil.setInt( nIndex, nId );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeFromIdAppointmentForm( int nIdAppointmentForm, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_FROM_ID_FORM, plugin );
        int nIndex = 1;
        daoUtil.setInt( nIndex, nIdAppointmentForm );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AppointmentFormResourceType findByPrimaryKey( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_PRIMARY_KEY, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery(  );

        AppointmentFormResourceType formResourceType = null;

        if ( daoUtil.next(  ) )
        {
            formResourceType = getFormResourceTypeFromDAO( daoUtil );
        }

        daoUtil.free(  );

        return formResourceType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AppointmentFormResourceType> findResourceTypesListFromIdForm( int nIdAppointmentForm, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID_APPOINTMENT_FORM, plugin );
        daoUtil.setInt( 1, nIdAppointmentForm );
        daoUtil.executeQuery(  );

        List<AppointmentFormResourceType> listFormResourceTypes = new ArrayList<AppointmentFormResourceType>(  );

        while ( daoUtil.next(  ) )
        {
            listFormResourceTypes.add( getFormResourceTypeFromDAO( daoUtil ) );
        }

        daoUtil.free(  );

        return listFormResourceTypes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetAppAdminUser( int nIdAppointmentForm, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_RESET_APP_ADMIN_USER, plugin );
        daoUtil.setInt( 1, nIdAppointmentForm );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetLocalization( int nIdAppointmentForm, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_RESET_APP_LOCALIZATION, plugin );
        daoUtil.setInt( 1, nIdAppointmentForm );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Get an appointment form resource type from a DAOUtil.<br >
     * the call to the DAOUtil.next( ) must have been made before this method is
     * called.<br >
     * The call to the DAOUtil.free( ) will not be made by this method.
     * @param daoUtil The daoUtil
     * @return The AppointmentFormResourceType
     */
    private AppointmentFormResourceType getFormResourceTypeFromDAO( DAOUtil daoUtil )
    {
        AppointmentFormResourceType formResourceType = new AppointmentFormResourceType(  );
        int nIndex = 1;
        formResourceType.setId( daoUtil.getInt( nIndex++ ) );
        formResourceType.setIdAppointmentForm( daoUtil.getInt( nIndex++ ) );
        formResourceType.setResourceTypeName( daoUtil.getString( nIndex++ ) );
        formResourceType.setDescription( daoUtil.getString( nIndex++ ) );
        formResourceType.setIsAppointmentAdminUser( daoUtil.getBoolean( nIndex++ ) );
        formResourceType.setIsLocation( daoUtil.getBoolean( nIndex ) );

        return formResourceType;
    }
}
