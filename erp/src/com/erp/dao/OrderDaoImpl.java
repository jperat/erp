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

import com.erp.beans.Order;
import com.erp.beans.OrderProduct;
import com.erp.beans.ProductQuantity;
import java.util.ArrayList;

public class OrderDaoImpl implements OrderDao {

    private static final String SQL_CREATE = "INSERT INTO orders(reference, date) VALUES(?, CURRENT_DATE)";
    private static final String SQL_UPDATE = "UPDATE order SET reference = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE order WHERE id = ?";
    private static final String SQL_SELECT = "SELECT * FROM orders";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM orders WHERE id = ?";

    private DAOFactory daoFactory;

    public OrderDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void create(Order order) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_CREATE, true, order.getReference());
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException("Failed to create Order");
            }
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                order.setId(resultSet.getLong(1));
                for (OrderProduct orderProduct : order.getOrderProducts()) {
                    orderProduct.setOrder(order);
                    DAOFactory.getInstance().getOrderProductDao().create(orderProduct);
                    DAOFactory.getInstance().getProductQuantityDao().create(new ProductQuantity(orderProduct));
                }
            } else {
                throw new DAOException("Success to create Order, but no ID has been create");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            silentsCloses(resultSet, preparedStatement, connection);
        }

    }

    @Override
    public void update(Order order) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_UPDATE, false, order.getReference(),
                    order.getId());

            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException("Failed to update Order");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(preparedStatement, connection);
        }

    }

    @Override
    public void delete(Order order) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_DELETE_BY_ID, false, order.getId());
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException("Failed to delete Order");
            } else {
                order.setId(null);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(preparedStatement, connection);
        }

    }

    @Override
    public Order find(Long id) throws DAOException {
        Order order = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, SQL_SELECT_BY_ID, false, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(resultSet, preparedStatement, connection);
        }
        return order;
    }

    @Override
    public ArrayList<Order> findAll() throws DAOException {
        return finds(SQL_SELECT);
    }

    private ArrayList<Order> finds(String sql, Object... objects) throws DAOException {
        ArrayList<Order> orders = new ArrayList<Order>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initializePreparedQuery(connection, sql, false, objects);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentsCloses(resultSet, preparedStatement, connection);
        }

        return orders;
    }

    private static Order map(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getLong("id"));
        order.setOrderProducts((ArrayList<OrderProduct>) DAOFactory.getInstance().getOrderProductDao().findByOrder(order));
        order.setReference(resultSet.getString("reference"));
        order.setDate(resultSet.getTimestamp("date"));
        return order;
    }

}
