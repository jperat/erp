package com.erp.dao;

import com.erp.beans.Order;
import java.util.ArrayList;

public interface OrderDao {

    void create( Order order ) throws DAOException;

    void update( Order order ) throws DAOException;

    void delete( Order order ) throws DAOException;

    Order find( Long id ) throws DAOException;

    ArrayList<Order> findAll() throws DAOException;
}
