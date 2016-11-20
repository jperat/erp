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

import com.erp.beans.Product;
import java.util.ArrayList;

public class ProductDaoImpl implements ProductDao {

    private static final String SQL_CREATE = "INSERT INTO product (name, reference, description, location) VALUES( ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE product SET name = ?, reference = ?, description = ?, location = ? WHERE id = ?";
    private static final String SQL_SELECT = "SELECT * FROM product";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM product WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM product WHERE id = ?";
    private static final String SQL_SELECT_BY_REFERENCE_OR_NAME = "SELECT * FROM product where name like ? OR reference like ?";

    private DAOFactory daoFactoty;

    public ProductDaoImpl(DAOFactory daoFactory) {
        this.daoFactoty = daoFactory;
    }

    @Override
    public void create(Product product) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactoty.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_CREATE, true, product.getName(),
                    product.getReference(), product.getDescription(), product.getLocation());
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException("Failed to create Product");
            } 
            else {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    product.setId(resultSet.getLong(1));
                } else {
                    throw new DAOException("Success to created Product, but no ID has been created");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public Product find(Long id) throws DAOException {
        Product product = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactoty.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_SELECT_BY_ID, false, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(resultSet, preparedStatement, connection);
        }

        return product;
    }

    @Override
    public ArrayList<Product> findAll() throws DAOException {
        return finds(SQL_SELECT);
    }

    @Override
    public ArrayList<Product> findByReferenceOrName(String value) throws DAOException {
        value = "%" + value + "%";
        return finds(SQL_SELECT_BY_REFERENCE_OR_NAME, value, value);
    }

    @Override
    public void delete(Product product) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = daoFactoty.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_DELETE_BY_ID, false, product.getId());
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException("Failed to delete Product");
            } else {
                product.setId(null);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(preparedStatement, connection);
        }
    }

    @Override
    public void update(Product product) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = daoFactoty.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_UPDATE, false, product.getName(),
                    product.getReference(), product.getDescription(), product.getLocation(), product.getId());
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException("Failed to update Product");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(preparedStatement, connection);
        }
    }

    private ArrayList<Product> finds(String sql, Object... objects) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Product> products = new ArrayList<Product>();

        try {
            connection = daoFactoty.getConnection();
            preparedStatement = initializePreparedQuery(connection, sql, false, objects);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products.add(map(resultSet));
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(resultSet, preparedStatement, connection);
        }
        return products;
    }

    private static Product map(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setProductQuantitys(DAOFactory.getInstance().getProductQuantityDao().findByProduct(product));
        product.setName(resultSet.getString("name"));
        product.setReference(resultSet.getString("reference"));
        product.setDescription(resultSet.getString("description"));
        product.setLocation(resultSet.getString("location"));
        return product;
    }
}
