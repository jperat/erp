package com.erp.dao;

import com.erp.beans.Product;
import java.util.ArrayList;

public interface ProductDao {

    void create( Product product ) throws DAOException;

    Product find( Long id ) throws DAOException;

    ArrayList<Product> findAll() throws DAOException;
    
    ArrayList<Product> findByReferenceOrName( String value ) throws DAOException;

    void delete( Product product ) throws DAOException;

    void update( Product product ) throws DAOException;

}
