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

import com.erp.beans.Product;
import static com.erp.dao.DAOUtility.initializePreparedQuery;
import static com.erp.dao.DAOUtility.silentsCloses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.erp.beans.ProductQuantity;
import java.util.ArrayList;

public class ProductQuantityDaoImpl implements ProductQuantityDao {

    private static final String SQL_CREATE = "INSERT INTO product_quantity (id_product, quantity, batch_number, serial_number) VALUES(?, ?, ?, ?) ";
    private static final String SQL_DELETE_BY_ID = "DELETE FORM product_quantity WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE product_quantity SET id_product = ?, quantity = ?, batch_number = ?, serial_number = ?";
    private static final String SQL_SELECT = "SELECT * FROM product_quantity";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM product_quantity WHERE id = ?";
    private static final String SQL_SELECT_BY_ID_PRODUCT = "SELECT * FROM product_quantity WHERE id_product = ?";

    private DAOFactory daoFactory;

    public ProductQuantityDaoImpl(DAOFactory daoFactory) throws DAOException {
        this.daoFactory = daoFactory;
    }

    @Override
    public void create(ProductQuantity productQuantity) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_CREATE, true,
                    productQuantity.getProduct().getId(), productQuantity.getQuantity(),
                    productQuantity.getBatchNumber(), productQuantity.getSerialNumber());
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException("Failed to create Product Quantity");
            }
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet != null) {
                resultSet.next();
                productQuantity.setId(resultSet.getLong(1));
            } else {
                throw new DAOException("Success to create Product Quantity, but no ID has been create");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public void update(ProductQuantity productQuantity) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_CREATE, true,
                    productQuantity.getProduct().getId(), productQuantity.getQuantity(),
                    productQuantity.getBatchNumber(), productQuantity.getSerialNumber(), productQuantity.getId());
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException("Failes to update Product Quantity");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(preparedStatement, connection);
        }
    }

    @Override
    public void delete(ProductQuantity productQuantity) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_DELETE_BY_ID, false, productQuantity.getId());
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException("Failed to delete Product Quantity");
            } else {
                productQuantity.setId(null);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(preparedStatement, connection);
        }
    }

    @Override
    public ProductQuantity find(Long id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ProductQuantity productQuantity = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_SELECT_BY_ID, false, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                productQuantity = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(resultSet, preparedStatement, connection);
        }
        return productQuantity;
    }

    @Override
    public ArrayList<ProductQuantity> findAll() throws DAOException {
        return finds(SQL_SELECT);
    }

    @Override
    public ArrayList<ProductQuantity> findByProduct(Product product) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<ProductQuantity> productQuantities = new ArrayList<ProductQuantity>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_SELECT_BY_ID_PRODUCT, false, product.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProductQuantity productQuantity = new ProductQuantity();
                productQuantity.setId(resultSet.getLong("id"));
                productQuantity.setProduct(product);
                productQuantity.setQuantity(resultSet.getLong("quantity"));
                productQuantity.setSerialNumber(resultSet.getString("serial_number"));
                productQuantity.setBatchNumber(resultSet.getString("batch_number"));
                productQuantities.add(productQuantity);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(resultSet, preparedStatement, connection);
        }

        return productQuantities;
    }

    private ArrayList<ProductQuantity> finds(String sql, Object... objects) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<ProductQuantity> productQuantities = new ArrayList<ProductQuantity>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, sql, false, objects);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productQuantities.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(resultSet, preparedStatement, connection);
        }

        return productQuantities;
    }

    private ProductQuantity map(ResultSet resultSet) throws SQLException {
        ProductQuantity productQuantity = new ProductQuantity();
        productQuantity.setId(resultSet.getLong("id"));
        productQuantity.setProduct(daoFactory.getProductDAO().find(resultSet.getLong("id_product")));
        productQuantity.setQuantity(resultSet.getLong("quantity"));
        productQuantity.setSerialNumber(resultSet.getString("serial_number"));
        productQuantity.setBatchNumber(resultSet.getString("batch_number"));
        return productQuantity;
    }
}
