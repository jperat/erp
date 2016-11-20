/*
 * Copyright 2015 Jerome PERAT.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.erp.dao;

import static com.erp.dao.DAOUtility.initializePreparedQuery;
import static com.erp.dao.DAOUtility.silentsCloses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.erp.beans.Project;
import java.util.ArrayList;

public class ProjectDaoImpl implements ProjectDao {

    private static final String SQL_CREATE       = "INSERT INTO project(name, decription) VALUES(?, ?)";
    private static final String SQL_UPDATE       = "UPDATE project SET name = ?, decription = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE project WHERE id = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM project WHERE id = ?";
    private static final String SQL_SELECT       = "SELECT * FROM project";

    private DAOFactory          daoFactory;

    public ProjectDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void create( Project project ) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery( connection, SQL_CREATE, true, project.getName(),
                    project.getDescription() );
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Failed to create Project" );
            }
            resultSet = preparedStatement.getGeneratedKeys();
            if ( resultSet.next() ) {
                project.setId( resultSet.getLong( 1 ) );
            } else {
                throw new DAOException( "Success to create Project, but no ID has been create" );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            silentsCloses( resultSet, preparedStatement, connection );
        }
    }

    @Override
    public void update( Project project ) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery( connection, SQL_UPDATE, false, project.getName(),
                    project.getDescription(), project.getId() );
            if ( preparedStatement.executeUpdate() == 0 ) {
                throw new DAOException( "Failed to update Project" );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            silentsCloses( preparedStatement, connection );
        }
    }

    @Override
    public void delete( Project project ) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery( connection, SQL_DELETE_BY_ID, false, project.getId() );
            if ( 0 == preparedStatement.executeUpdate() ) {
                throw new DAOException( "Failled to delete Project" );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            silentsCloses( preparedStatement, connection );
        }

    }

    @Override
    public Project find( Long id ) throws DAOException {
        Project project = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery( connection, SQL_SELECT_BY_ID, false, id );
            resultSet = preparedStatement.executeQuery();

            if ( resultSet.next() ) {
                project = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            silentsCloses( resultSet, preparedStatement, connection );
        }

        return project;
    }

    @Override
    public ArrayList<Project> findAll() throws DAOException {
        return finds( SQL_SELECT );
    }

    private ArrayList<Project> finds( String sql, Object... objects ) {
        ArrayList<Project> projects = new ArrayList<Project>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery( connection, sql, false, objects );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                projects.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            silentsCloses( resultSet, preparedStatement, connection );
        }

        return projects;
    }

    private Project map( ResultSet resultSet ) throws SQLException {
        Project project = new Project();
        project.setId( resultSet.getLong( "id" ) );
        project.setName( resultSet.getString( "name" ) );
        project.setDescription( resultSet.getString( "decription" ) );
        project.setProjectProducts(daoFactory.getInstance().getProjectProductDao().findByProject(project));
        return project;
    }

}
