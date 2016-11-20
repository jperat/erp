/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erp.view;

import com.erp.beans.Product;
import com.erp.dao.DAOFactory;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author Jerome
 */
public class ProductsEditButtonEditor extends DefaultCellEditor{
    
    protected JButton button;
    private ButtonListener bListener = new ButtonListener();
    private ProductsPanel productsPanel;

    public ProductsEditButtonEditor(JCheckBox checkBox, ProductsPanel productsPanel) {
        super(checkBox);
        button = new JButton("Edit");
        button.addActionListener(bListener);
        button.setOpaque(true);
        this.productsPanel = productsPanel;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        bListener.setId((Long)value);
        return button;
    }
    
    class ButtonListener implements ActionListener {

        private Long id;
        
        public void setId (Long id) {
            this.id = id;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Product product = DAOFactory.getInstance().getProductDAO().find(id);
            
            if (product != null) {
                ProductPanel productPanel = new ProductPanel(product, true);
                productsPanel.removeAll();
                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(productsPanel);
                productsPanel.setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(productPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(productPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                productsPanel.repaint();
            }
        }
        
    }
    
}
