/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erp.view;

import com.erp.beans.Product;
import com.erp.beans.ProductQuantity;
import com.erp.beans.Project;
import com.erp.beans.ProjectProduct;
import com.erp.dao.DAOFactory;
import com.erp.tools.Tools;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Jerome
 */
public class ProjectPanel extends javax.swing.JPanel {

    private ProjectProduct projectProduct;
    private Project project;
    private JTable table;
    private boolean newP = false;

    /**
     * Creates new form Projects
     */
    public ProjectPanel() {
        initComponents();
        this.newP = true;
        this.project = new Project();
        this.ProductPanel.setVisible(false);

    }
    
    public ProjectPanel(Project project) {
        initComponents();
        this.jTextField1.setText(project.getName());
        this.jTextArea1.setText(project.getDescription());
        this.table = new JTable(new ProductTableModel((project.getProjectProducts())));
        this.table.setFillsViewportHeight(true);
        this.table.setDefaultRenderer(JButton.class, new TableComponent());
        this.JTablePanel.add(new JScrollPane(table));
        this.JTablePanel.repaint();
        this.JTablePanel.revalidate();
        this.project = project;
    }

    class ProductTableModel extends AbstractTableModel {

        private Object[][] data;
        private String[] columnNames = {"Id", "Name", "Reference", "Serial number", "Batch number", "Quantity"};

        public ProductTableModel(ArrayList<ProjectProduct> products) {
            data = new Object[products.size()][6];
            for (int i = 0; i < products.size(); i++) {
                data[i][0] = products.get(i).getId();
                data[i][1] = products.get(i).getProduct().getName();
                data[i][2] = products.get(i).getProduct().getReference();
                data[i][3] = products.get(i).getSerialNumber();
                data[i][4] = products.get(i).getBatchNumber();
                data[i][5] = products.get(i).getQuantity();
            }
        }

        @Override
        public int getRowCount() {
            return this.data.length;
        }

        @Override
        public int getColumnCount() {
            return this.columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return this.data[rowIndex][columnIndex];
        }

        public String getColumnName(int colIndex) {
            return this.columnNames[colIndex];
        }

        public Class getColumnClass(int c) {
            return this.data[0][c].getClass();
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (getValueAt(0, columnIndex) instanceof JButton) {
                return false;
            }
            return true;
        }

        public void AddRow(ProjectProduct projectProduct) {
            int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();

            Object temp[][] = this.data;
            this.data = new Object[nbRow + 1][nbCol];

            for (Object[] value : temp) {
                this.data[indice++] = value;
            }

            this.data[indice][0] = "";
            this.data[indice][1] = projectProduct.getProduct().getName();
            this.data[indice][2] = projectProduct.getProduct().getReference();
            this.data[indice][3] = projectProduct.getBatchNumber();
            this.data[indice][4] = projectProduct.getSerialNumber();
            this.data[indice][5] = "" + projectProduct.getQuantity();

            this.fireTableDataChanged();
        }
    }

    public class TableComponent extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            //Si la valeur de la cellule est un JButton, on transtype cette valeur
            if (value instanceof JButton) {
                return (JButton) value;
            } else {
                return this;
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        HeadPanel1 = new javax.swing.JPanel();
        TitleLabel = new javax.swing.JLabel();
        SaveButton = new javax.swing.JButton();
        PropertyPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        ProductPanel = new javax.swing.JPanel();
        ProductLabel = new javax.swing.JLabel();
        ProductField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        QuantityField = new javax.swing.JTextField();
        AddButton = new javax.swing.JButton();
        JTablePanel = new javax.swing.JPanel();

        HeadPanel1.setBackground(new java.awt.Color(204, 204, 204));

        TitleLabel.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        TitleLabel.setText("Project");

        SaveButton.setText("Save");
        SaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout HeadPanel1Layout = new javax.swing.GroupLayout(HeadPanel1);
        HeadPanel1.setLayout(HeadPanel1Layout);
        HeadPanel1Layout.setHorizontalGroup(
            HeadPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeadPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 659, Short.MAX_VALUE)
                .addComponent(SaveButton)
                .addGap(117, 117, 117))
        );
        HeadPanel1Layout.setVerticalGroup(
            HeadPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeadPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HeadPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SaveButton))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel1.setText("Name");

        jLabel2.setText("Description");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout PropertyPanelLayout = new javax.swing.GroupLayout(PropertyPanel);
        PropertyPanel.setLayout(PropertyPanelLayout);
        PropertyPanelLayout.setHorizontalGroup(
            PropertyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PropertyPanelLayout.createSequentialGroup()
                .addGap(157, 157, 157)
                .addGroup(PropertyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(86, 86, 86)
                .addGroup(PropertyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(292, Short.MAX_VALUE))
        );
        PropertyPanelLayout.setVerticalGroup(
            PropertyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PropertyPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PropertyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(PropertyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(163, 163, 163))
        );

        ProductLabel.setText("Enter product");

        ProductField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ProductFieldKeyReleased(evt);
            }
        });

        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jList2);

        QuantityField.setText("Quantity");
        QuantityField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                QuantityFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                QuantityFieldFocusLost(evt);
            }
        });

        AddButton.setText("Add");
        AddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddButtonActionPerformed(evt);
            }
        });

        JTablePanel.setLayout(new javax.swing.BoxLayout(JTablePanel, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout ProductPanelLayout = new javax.swing.GroupLayout(ProductPanel);
        ProductPanel.setLayout(ProductPanelLayout);
        ProductPanelLayout.setHorizontalGroup(
            ProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProductPanelLayout.createSequentialGroup()
                .addGroup(ProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ProductPanelLayout.createSequentialGroup()
                        .addGroup(ProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ProductPanelLayout.createSequentialGroup()
                                .addGap(157, 157, 157)
                                .addComponent(ProductLabel)
                                .addGap(73, 73, 73)
                                .addComponent(ProductField, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ProductPanelLayout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(ProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(QuantityField, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(AddButton))))
                        .addGap(0, 109, Short.MAX_VALUE))
                    .addGroup(ProductPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(JTablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ProductPanelLayout.setVerticalGroup(
            ProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProductPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(ProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProductLabel)
                    .addComponent(ProductField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ProductPanelLayout.createSequentialGroup()
                        .addComponent(QuantityField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(AddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addComponent(JTablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ProductPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(HeadPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PropertyPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(HeadPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PropertyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ProductPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void SaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveButtonActionPerformed
        this.project.setName(this.jTextField1.getText());
        this.project.setDescription(this.jTextArea1.getText());
        
        if (this.newP) {
            DAOFactory.getInstance().getProjectDao().create(this.project);
            this.ProductPanel.setVisible(true);
            this.table = new JTable(new ProductTableModel((this.project.getProjectProducts())));
            this.table.setFillsViewportHeight(true);
            this.table.setDefaultRenderer(JButton.class, new TableComponent());
            this.JTablePanel.add(new JScrollPane(this.table));
            this.JTablePanel.repaint();
            this.JTablePanel.revalidate();
            this.newP = false;
        } else {
            DAOFactory.getInstance().getProjectDao().update(project);
        }
    }//GEN-LAST:event_SaveButtonActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void ProductFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProductFieldKeyReleased
        ArrayList<Product> products;
        if (this.ProductField.getText().length() > 0) {
            products = DAOFactory.getInstance().getProductDAO().findByReferenceOrName(this.ProductField.getText());
        } else {
            products = new ArrayList<Product>();
        }
        DefaultListModel<Product> productListModel = new DefaultListModel<Product>();
        for (int i = 0; i < products.size(); i++) {
            productListModel.addElement(products.get(i));
        }
        this.jList1.setModel(productListModel);
    }//GEN-LAST:event_ProductFieldKeyReleased

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        int index = this.jList1.getSelectedIndex();
        Product product = (Product) this.jList1.getSelectedValue();
        DefaultListModel<ProductQuantity> productQuantityListModel = new DefaultListModel<ProductQuantity>();
        if (index >= 0) {
            if (!product.getProductQuantitys().isEmpty()) {
                for (ProductQuantity productQuantity : product.getProductQuantitys()) {
                    productQuantityListModel.addElement(productQuantity);
                }
            }
            this.jList2.setModel(productQuantityListModel);
        }
    }//GEN-LAST:event_jList1ValueChanged

    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged
        int index = this.jList2.getSelectedIndex();
        ProductQuantity productQuantity = (ProductQuantity) this.jList2.getSelectedValue();
        if (index >= 0) {
            this.projectProduct = new ProjectProduct();
            this.projectProduct.setProject(project);
            this.projectProduct.setProduct(productQuantity.getProduct());
            this.projectProduct.setBatchNumber(productQuantity.getBatchNumber());
            this.projectProduct.setSerialNumber(productQuantity.getSerialNumber());
        }
    }//GEN-LAST:event_jList2ValueChanged

    private void QuantityFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_QuantityFieldFocusGained
        if (this.QuantityField.getText().equals("Quantity")) {
            this.QuantityField.setText("");
        }
    }//GEN-LAST:event_QuantityFieldFocusGained

    private void QuantityFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_QuantityFieldFocusLost
        if (this.QuantityField.getText().length() == 0) {
            this.QuantityField.setText("Quantity");
        }
    }//GEN-LAST:event_QuantityFieldFocusLost

    private void AddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButtonActionPerformed
        if (this.jList2.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(null, "You need to select a product");
        } else {
            long quantity = Long.parseLong(this.QuantityField.getText());
            ProductQuantity productQuantity = (ProductQuantity) this.jList2.getSelectedValue();
            if (!Tools.isInteger(this.QuantityField.getText())) {
                JOptionPane.showMessageDialog(null, "Quantity must be an integer");
            } else if (quantity > productQuantity.getQuantity()) {
                JOptionPane.showMessageDialog(null, "The quantity can't be higher to the available quantity");
            } else {
                this.projectProduct.setQuantity(quantity);
                this.project.addProjectProduct(this.projectProduct);
                ((ProductTableModel) this.table.getModel()).AddRow(projectProduct);
                DefaultListModel<Product> productListModel = new DefaultListModel<Product>();
                DefaultListModel<ProductQuantity> productQuantityListModel = new DefaultListModel<ProductQuantity>();
                this.QuantityField.setText("Quantity");
                this.jList2.setModel(productQuantityListModel);
                this.jList1.setModel(productListModel);
                DAOFactory.getInstance().getProjectProductDao().create(projectProduct);
                productQuantity.setQuantity(productQuantity.getQuantity() - projectProduct.getQuantity());
                DAOFactory.getInstance().getProductQuantityDao().update(productQuantity);
            }
        }
    }//GEN-LAST:event_AddButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddButton;
    private javax.swing.JPanel HeadPanel1;
    private javax.swing.JPanel JTablePanel;
    private javax.swing.JTextField ProductField;
    private javax.swing.JLabel ProductLabel;
    private javax.swing.JPanel ProductPanel;
    private javax.swing.JPanel PropertyPanel;
    private javax.swing.JTextField QuantityField;
    private javax.swing.JButton SaveButton;
    private javax.swing.JLabel TitleLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
