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
/*
< * Copyright (c) 2002-2014, Mairie de Paris
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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO to manage history of set appointment resource tasks
 */
public class SetAppointmentResourceHistoryDAO implements ISetAppointmentResourceHistoryDAO
{
    private static final String SQL_QUERY_FIND_BY_PRIMARY_KEY = "SELECT id_history,id_appointment,id_resource,id_form_resource_type FROM workflow_task_set_appointment_resource_history WHERE id=?";
    private static final String SQL_QUERY_FIND_BY_ID_HISTORY = "SELECT id,id_history,id_appointment,id_resource,id_form_resource_type FROM workflow_task_set_appointment_resource_history WHERE id_history=?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO workflow_task_set_appointment_resource_history(id,id_history,id_appointment,id_resource,id_form_resource_type) VALUES (?,?,?,?,?)";
    private static final String SQL_QUERY_DELETE = "DELETE FROM workflow_task_set_appointment_resource_history WHERE id = ? ";
    private static final String SQL_QUERY_DELETE_BY_ID_APPOINTMENT = "DELETE FROM workflow_task_set_appointment_resource_history WHERE id_appointment = ? ";
    private static final String SQL_QUEERY_NEW_PRIMARY_KEY = "SELECT MAX(id) FROM workflow_task_set_appointment_resource_history";

    /**
     * Get a new primary key
     * 
     * @param plugin
     *            The plugin
     * @return The new value of the primary key
     */
    private int newPrimaryKey( Plugin plugin )
    {
        try (DAOUtil daoUtil = new DAOUtil( SQL_QUEERY_NEW_PRIMARY_KEY, plugin )) {
			daoUtil.executeQuery( );

			int nRes = 1;

			if ( daoUtil.next( ) )
			{
			    nRes = daoUtil.getInt( 1 ) + 1;
			}

			daoUtil.free( );

			return nRes;
		}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void create( SetAppointmentResourceHistory history, Plugin plugin )
    {
        history.setId( newPrimaryKey( plugin ) );

        try (DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin )) {
			int nIndex = 1;
			daoUtil.setInt( nIndex++, history.getId( ) );
			daoUtil.setInt( nIndex++, history.getIdHistory( ) );
			daoUtil.setInt( nIndex++, history.getIdAppointment( ) );
			daoUtil.setString( nIndex++, history.getIdResource( ) );
			daoUtil.setInt( nIndex, history.getIdFormResourceType( ) );
			daoUtil.executeUpdate( );
			daoUtil.free( );
		}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SetAppointmentResourceHistory findByPrimaryKey( int nId, Plugin plugin )
    {
        try (DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_PRIMARY_KEY, plugin )) {
			daoUtil.setInt( 1, nId );
			daoUtil.executeQuery( );

			SetAppointmentResourceHistory history;

			if ( daoUtil.next( ) )
			{
			    int nIndex = 1;
			    history = new SetAppointmentResourceHistory( );
			    history.setId( nId );
			    history.setIdHistory( daoUtil.getInt( nIndex++ ) );
			    history.setIdAppointment( daoUtil.getInt( nIndex++ ) );
			    history.setIdResource( daoUtil.getString( nIndex++ ) );
			    history.setIdFormResourceType( daoUtil.getInt( nIndex ) );
			}
			else
			{
			    history = null;
			}

			daoUtil.free( );

			return history;
		}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nIdNotif, Plugin plugin )
    {
        try (DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin )) {
			daoUtil.setInt( 1, nIdNotif );
			daoUtil.executeUpdate( );
			daoUtil.free( );
		}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SetAppointmentResourceHistory> findByIdHistory( int nIdHistory, Plugin plugin )
    {
        try (DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_ID_HISTORY, plugin )) {
			daoUtil.setInt( 1, nIdHistory );
			daoUtil.executeQuery( );

			List<SetAppointmentResourceHistory> listHistory = new ArrayList<>( );

			while ( daoUtil.next( ) )
			{
			    int nIndex = 1;
			    SetAppointmentResourceHistory history = new SetAppointmentResourceHistory( );
			    history.setId( daoUtil.getInt( nIndex++ ) );
			    history.setIdHistory( daoUtil.getInt( nIndex++ ) );
			    history.setIdAppointment( daoUtil.getInt( nIndex++ ) );
			    history.setIdResource( daoUtil.getString( nIndex++ ) );
			    history.setIdFormResourceType( daoUtil.getInt( nIndex ) );
			    listHistory.add( history );
			}

			daoUtil.free( );

			return listHistory;
		}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteByIdAppointment( int nIdAppointment, Plugin plugin )
    {
        try (DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_ID_APPOINTMENT, plugin )) {
			daoUtil.setInt( 1, nIdAppointment );
			daoUtil.executeUpdate( );
			daoUtil.free( );
		}
    }
}
