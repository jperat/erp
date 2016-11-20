/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erp.dao;

import com.erp.beans.Project;
import com.erp.beans.ProjectProduct;
import java.util.ArrayList;

/**
 *
 * @author Jerome
 */
public interface ProjectProductDao {
    void create (ProjectProduct projectProduct) throws DAOException;
    
    void update (ProjectProduct projectProduct) throws DAOException;
    
    void delete (ProjectProduct projectProduct) throws DAOException;
    
    ProjectProduct find (Long id) throws DAOException;
    
    ArrayList<ProjectProduct> findAll() throws DAOException;
    
    ArrayList<ProjectProduct> findByProject(Project project) throws DAOException;
}
