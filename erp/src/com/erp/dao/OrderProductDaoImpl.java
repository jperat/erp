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

import com.erp.beans.Order;
import static com.erp.dao.DAOUtility.initializePreparedQuery;
import static com.erp.dao.DAOUtility.silentsCloses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.erp.beans.OrderProduct;
import java.util.ArrayList;

public class OrderProductDaoImpl implements OrderProductDao {

    private static final String SQL_CREATE = "INSERT INTO order_product(id_order, id_product, batch_number, serial_number, quantity) VALUES(?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE order_product SET id_order = ?, id_product = ?, batch_number = ?, serial_number = ?, quantity = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE order_product WHERE id = ?";
    private static final String SQL_SELECT = "SELECT * FROM order_product";
    private static final String SQL_SELECT_BY_ID_ORDER = "SELECT * FROM order_product WHERE id_order = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM order_product WHERE id = ?";

    private DAOFactory daoFactory;

    public OrderProductDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void create(OrderProduct orderProduct) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_CREATE, true, orderProduct.getOrder().getId(),
                    orderProduct.getProduct().getId(), orderProduct.getBatchNumber(), orderProduct.getSerialNumber(),
                    orderProduct.getQuantity());
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException("Failed to create Order Product");
            }
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                orderProduct.setId(resultSet.getLong(1));
            } else {
                throw new DAOException("Success to create Product, but no ID has been create");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public void update(OrderProduct orderProduct) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_UPDATE, false, orderProduct.getOrder().getId(),
                    orderProduct.getProduct().getId(), orderProduct.getBatchNumber(), orderProduct.getSerialNumber(),
                    orderProduct.getQuantity(), orderProduct.getId());
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException("Failed to update Order Product");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(preparedStatement, connection);
        }

    }

    @Override
    public void delete(OrderProduct orderProduct) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_DELETE_BY_ID, false, orderProduct.getId());
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException("Failed to delete Order Product");
            } else {
                orderProduct.setId(null);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(preparedStatement, connection);
        }
    }

    @Override
    public OrderProduct find(Long id) throws DAOException {
        OrderProduct orderProduct = new OrderProduct();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_SELECT_BY_ID, false, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                orderProduct = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(resultSet, preparedStatement, connection);
        }

        return orderProduct;
    }

    @Override
    public ArrayList<OrderProduct> findAll() throws DAOException {
        return finds(SQL_SELECT);
    }

    @Override
    public ArrayList<OrderProduct> findByOrder(Order order) throws DAOException {
        ArrayList<OrderProduct> orderProducts = new ArrayList<OrderProduct>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_SELECT_BY_ID_ORDER, false, order.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setId(resultSet.getLong("id"));
                orderProduct.setOrder(order);
                orderProduct.setProduct(daoFactory.getProductDAO().find(resultSet.getLong("id_product")));
                orderProduct.setBatchNumber(resultSet.getString("batch_number"));
                orderProduct.setSerialNumber(resultSet.getString("serial_number"));
                orderProduct.setQuantity(resultSet.getLong("quantity"));
                orderProducts.add(orderProduct);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(resultSet, preparedStatement, connection);
        }
        return orderProducts;
    }

    private ArrayList<OrderProduct> finds(String sql, Object... objects) throws DAOException {
        ArrayList<OrderProduct> orderProducts = new ArrayList<OrderProduct>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, sql, false, objects);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orderProducts.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(resultSet, preparedStatement, connection);
        }
        return orderProducts;
    }

    private OrderProduct map(ResultSet resultSet) throws SQLException {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setId(resultSet.getLong("id"));
        orderProduct.setOrder(daoFactory.getOrderDao().find(resultSet.getLong("id_order")));
        orderProduct.setProduct(daoFactory.getProductDAO().find(resultSet.getLong("id_product")));
        orderProduct.setBatchNumber(resultSet.getString("batch_number"));
        orderProduct.setSerialNumber(resultSet.getString("serial_number"));
        orderProduct.setQuantity(resultSet.getLong("quantity"));
        return orderProduct;
    }
}
