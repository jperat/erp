/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erp.view;

import com.erp.beans.Product;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jerome
 */
public class ProductsListTableModel extends AbstractTableModel{

    private Object[][] data;
    private String[] title = {"Id", "Name", "Reference", "Location", "", ""};
    
    public ProductsListTableModel(ArrayList<Product> products) {
        this.createData(products);
    }
    
    @Override
    public int getRowCount() {
        return this.data.length;
    }

    @Override
    public int getColumnCount() {
        return this.title.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.data[rowIndex][columnIndex];
    }
    
    private void createData(ArrayList<Product> products) {
        Product product;
        this.data = new Object[products.size()][6];
        for (int i = 0; i < products.size(); i++)
        {
            product = products.get(i);
            this.data[i][0] = product.getId();
            this.data[i][1] = product.getName();
            this.data[i][2] = product.getReference();
            this.data[i][3] = product.getLocation();
            this.data[i][4] = new JButton("Edit");
            this.data[i][5] = new JButton("View");
        }
    }
    
}
