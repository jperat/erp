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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOFactory {

    private final static String DBNAME = "erp_db";
    private final static String JDBC_URL = "jdbc:derby:" + DBNAME + ";create=true";

    private Connection connection = null;

    public static DAOFactory getInstance() throws DAOConfigurationException {
        return new DAOFactory();
    }

    /* package */
    Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
        return connection = DriverManager.getConnection(JDBC_URL);
    }

    public ProductDao getProductDAO() {
        return new ProductDaoImpl(this);
    }

    public ProductQuantityDao getProductQuantityDao() {
        return new ProductQuantityDaoImpl(this);
    }

    public OrderDao getOrderDao() {
        return new OrderDaoImpl(this);
    }

    public OrderProductDao getOrderProductDao() {
        return new OrderProductDaoImpl(this);
    }

    public ProjectDao getProjectDao() {
        return new ProjectDaoImpl(this);
    }

    public ProjectProductDao getProjectProductDao() {
        return new ProjectProductImplDao(this);
    }

    public void createDb() throws SQLException {
        try {
            this.getConnection();
            Statement s = connection.createStatement();
            if (!this.tableExists(connection, "orders")) {
                s.execute("CREATE TABLE orders ( id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), reference VARCHAR(45) DEFAULT NULL, date date DEFAULT NULL)");
            }
            if (!this.tableExists(connection, "order_product")) {
                s.execute("CREATE TABLE order_product ( id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), id_order INT DEFAULT NULL, id_product INT DEFAULT NULL, batch_number VARCHAR(45) DEFAULT NULL, serial_number VARCHAR(45) DEFAULT NULL, quantity INT DEFAULT NULL)");
            }
            if (!this.tableExists(connection, "product")) {
                s.execute("CREATE TABLE product (id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name VARCHAR(128) DEFAULT NULL, reference VARCHAR(128) DEFAULT NULL, description VARCHAR(1024) DEFAULT NULL, location VARCHAR(45) DEFAULT NULL)");
            }
            if (!this.tableExists(connection, "product_quantity")) {
                s.execute("CREATE TABLE product_quantity ( id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), id_product INT NOT NULL, quantity INT NOT NULL, batch_number VARCHAR(45) NOT NULL, serial_number VARCHAR(45) NOT NULL )");
            }
            if (!this.tableExists(connection, "project")) {
                s.execute("CREATE TABLE project ( id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name VARCHAR(45) NOT NULL, decription LONG VARCHAR NOT NULL )");
            }
            if (!this.tableExists(connection, "project_product")) {
                s.execute("CREATE TABLE project_product ( id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), id_project INT NOT NULL, id_product INT NOT NULL, batch_number VARCHAR(45) NOT NULL,  serial_number VARCHAR(45) NOT NULL, quantity INT NOT NULL, date date NOT NULL )");
            }
        } catch (DAOConfigurationException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }


    private boolean tableExists(Connection con, String table) throws SQLException {
        DatabaseMetaData dbmd = con.getMetaData();
        ResultSet rs = dbmd.getTables(null, "APP", table.toUpperCase(), null);
        if (!rs.next()) {
            return false;
        }
        return true;
    }
}
