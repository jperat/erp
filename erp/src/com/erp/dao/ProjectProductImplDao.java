/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erp.dao;

import com.erp.beans.Project;
import com.erp.beans.ProjectProduct;
import static com.erp.dao.DAOUtility.initializePreparedQuery;
import static com.erp.dao.DAOUtility.silentsCloses;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jerome
 */
public class ProjectProductImplDao implements ProjectProductDao {

    private static final String SQL_CREATE = "INSERT INTO project_product (id_project, id_product, batchnumber, serial_number, quantity, date) VALUES (?,?,?,?,?, CURRENT_DATE";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM project_product WHERE id = ?;";
    private static final String SQL_UPDATE = "UPDATE project_product SET id_project = ?, id_product = ?, batchnumber = ?; serial_number = ?, quantity = , date = CURRENT_DATE WHERE id = ?;";
    private static final String SQL_SELECT = "SELECT * FROM project_product;";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM project_product WHERE id = ?;";
    private static final String SQL_SELECT_BY_ID_PROJECT = "SELECT * FROM project_product WHERE id_project = ?;";
    
    private DAOFactory daoFactory;
    
    public ProjectProductImplDao(DAOFactory daoFactory) throws DAOException {
        this.daoFactory = daoFactory;
    }
    
    @Override
    public void create(ProjectProduct projectProduct) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_CREATE, true,
                    projectProduct.getProject().getId(), projectProduct.getProduct().getId(),
                    projectProduct.getBatchNumber(), projectProduct.getSerialNumber(),
                    projectProduct.getQuantity());
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException("Failed to create Project Product");
            }
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet != null) {
                resultSet.next();
                projectProduct.setId(resultSet.getLong(1));
            } else {
                throw new DAOException("Success to create Project Product, but no ID has been create");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public void update(ProjectProduct projectProduct) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_UPDATE, false,
                    projectProduct.getProject().getId(), projectProduct.getProduct().getId(),
                    projectProduct.getBatchNumber(), projectProduct.getSerialNumber(),
                    projectProduct.getQuantity());
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException("Failed to updated Project Product");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public void delete(ProjectProduct projectProduct) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_DELETE_BY_ID, false, projectProduct.getId());
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException("Failed to delete Project Product");
            } else {
                projectProduct.setId(null);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(preparedStatement, connection);
        }
    }

    @Override
    public ProjectProduct find(Long id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ProjectProduct projectProduct = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_SELECT_BY_ID, false, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                projectProduct = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(resultSet, preparedStatement, connection);
        }
        return projectProduct;
    }

    @Override
    public ArrayList<ProjectProduct> findAll() throws DAOException {
        return finds(SQL_SELECT);
    }

    @Override
    public ArrayList<ProjectProduct> findByProject(Project project) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<ProjectProduct> projectProducts = new ArrayList<ProjectProduct>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_SELECT_BY_ID_PROJECT, false, project.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                projectProducts.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(resultSet, preparedStatement, connection);
        }

        return projectProducts;
    }
    
    private ArrayList<ProjectProduct> finds(String sql, Object... objects) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<ProjectProduct> projectProducts = new ArrayList<ProjectProduct>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, sql, false, objects);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProjectProduct projectProduct = new ProjectProduct();
                projectProduct.setId(resultSet.getLong("id"));
                projectProduct.setProject(daoFactory.getProjectDao().find(resultSet.getLong("id_project")));
                projectProduct.setProduct(daoFactory.getProductDAO().find(resultSet.getLong("id_product")));
                projectProduct.setBatchNumber(resultSet.getString("batchnumber"));
                projectProduct.setSerialNumber(resultSet.getString("serialnumber"));
                projectProduct.setQuantity(resultSet.getLong("quantity"));
                projectProduct.setDate(resultSet.getTimestamp("date"));
                projectProducts.add(projectProduct);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(resultSet, preparedStatement, connection);
        }

        return projectProducts;
    }
    
    private ProjectProduct map(ResultSet resultSet) throws SQLException {
        ProjectProduct projectProduct = new ProjectProduct();
        projectProduct.setId(resultSet.getLong("id"));
        projectProduct.setProject(daoFactory.getProjectDao().find(resultSet.getLong("id_project")));
        projectProduct.setProduct(daoFactory.getProductDAO().find(resultSet.getLong("id_product")));
        projectProduct.setBatchNumber(resultSet.getString("batchnumber"));
        projectProduct.setSerialNumber(resultSet.getString("serialnumber"));
        projectProduct.setQuantity(resultSet.getLong("quantity"));
        projectProduct.setDate(resultSet.getTimestamp("date"));
        return projectProduct;
    }
    
}
