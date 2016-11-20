/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erp.view;

import com.erp.beans.Order;
import com.erp.beans.OrderProduct;
import com.erp.beans.Product;
import com.erp.dao.DAOException;
import com.erp.dao.DAOFactory;
import com.erp.tools.Tools;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Jerome
 */
public class OrderPanel extends javax.swing.JPanel {

    private Order order;
    private JTable table;
    private OrderProduct orderProduct;
    /**
     * Creates new form OrderPanel
     */
    public OrderPanel() {
   
        initComponents();
        this.order = new Order();
        this.dateField.setText(Tools.currentDate());
        this.createProductTable(new ArrayList<OrderProduct>());
    }
    
    public OrderPanel(Order order) {
        this.order = order;
        initComponents();
        this.ReferenceField.setEditable(false);
        this.dateField.setEditable(false);
        this.SaveButton.setVisible(false);
        this.productField.setVisible(false);
        this.searchScrollPane.setVisible(false);
        this.idAddField.setVisible(false);
        this.referenceAddField.setVisible(false);
        this.nameAddField.setVisible(false);
        this.batchnumberAddField.setVisible(false);
        this.serialnumberAddField.setVisible(false);
        this.quantityAddField.setVisible(false);
        this.addButton.setVisible(false);
        this.createProductTable(this.order.getOrderProducts());
        this.dateField.setText(Tools.sqlDateFormat(this.order.getDate()));
        this.ReferenceField.setText(this.order.getReference());
        System.out.println(""+order.getOrderProducts().size());
        for (OrderProduct op : order.getOrderProducts()) {
            System.out.println(op.getProduct().getName());
        }
    }
    private void createProductTable(ArrayList<OrderProduct> orderProducts){
        this.table = new JTable(new OrderTableModel(orderProducts));
        this.table.setRowHeight(30);
        this.table.getColumnModel().getColumn(0).setMaxWidth(50);
        this.table.setFillsViewportHeight(true);
        this.table.getColumn("Batch number").setCellRenderer(new TextFieldRender());
        this.table.getColumn("Serial number").setCellRenderer(new TextFieldRender());
        this.table.getColumn("Quantity").setCellRenderer(new TextFieldRender());
        
        this.JTablePanel.add(new JScrollPane(this.table));
        this.JTablePanel.repaint();
        this.JTablePanel.revalidate();
        this.dateField.setText(Tools.sqlDateFormat(this.order.getDate()));
        this.ReferenceField.setText(this.order.getReference());
    }
    
    class OrderTableModel extends AbstractTableModel {

        private Object[][] data;
        private String[] columnNames = {"Id", "Reference", "Name", "Batch number", "Serial number", "Quantity"};
        
        public OrderTableModel (ArrayList<OrderProduct> orderProducts) {
            data = new Object[orderProducts.size()][6];
            for (int i = 0; i < orderProducts.size(); i++) {
                data[i][0] = orderProducts.get(i).getId();
                data[i][1] = orderProducts.get(i).getProduct().getReference();
                data[i][2] = orderProducts.get(i).getProduct().getName();
                data[i][3] = orderProducts.get(i).getBatchNumber();
                data[i][4] = orderProducts.get(i).getSerialNumber();
                data[i][5] = orderProducts.get(i).getQuantity();
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
            return getValueAt(0, c).getClass();
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (columnIndex == 5) {
                return true;
            }
            return false;
        }
        
        public void addRow(OrderProduct orderProduct) {
            int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
            
            Object temp[][] = this.data;
            this.data = new Object[nbRow + 1][nbCol];
            
            for (Object[] value : temp)
                this.data[indice++] = value;
            
            this.data[indice][0] = orderProduct.getProduct().getId();
            this.data[indice][1] = orderProduct.getProduct().getName();
            this.data[indice][2] = orderProduct.getProduct().getReference();
            this.data[indice][3] = orderProduct.getBatchNumber();
            this.data[indice][4] = orderProduct.getSerialNumber();
            this.data[indice][5] = ""+orderProduct.getQuantity();
            
            this.fireTableDataChanged();
        }
         
    }
    
    public class TableComponent extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            //Si la valeur de la cellule est un JButton, on transtype cette valeur
            if (value instanceof JTextField) {
                return (JTextField) value;
            } else {
                return this;
            }
        }
    }
    
    public class ButtonRender extends JButton implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value != null) ? value.toString() : "");
            return this;
        }
        
    }
    
    public class TextFieldRender extends JTextField implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value != null) ? value.toString() : "");
            return this;
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
        ReferenceField = new javax.swing.JTextField();
        dateField = new javax.swing.JTextField();
        ProductPanel = new javax.swing.JPanel();
        JTablePanel = new javax.swing.JPanel();
        productField = new javax.swing.JTextField();
        searchScrollPane = new javax.swing.JScrollPane();
        searchList = new javax.swing.JList();
        idAddField = new javax.swing.JTextField();
        referenceAddField = new javax.swing.JTextField();
        nameAddField = new javax.swing.JTextField();
        batchnumberAddField = new javax.swing.JTextField();
        serialnumberAddField = new javax.swing.JTextField();
        quantityAddField = new javax.swing.JTextField();
        addButton = new javax.swing.JToggleButton();

        HeadPanel1.setBackground(new java.awt.Color(204, 204, 204));

        TitleLabel.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        TitleLabel.setText("Order");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jLabel1.setText("Reference");

        jLabel2.setText("Date");

        ReferenceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReferenceFieldActionPerformed(evt);
            }
        });

        dateField.setEditable(false);

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
                .addGroup(PropertyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ReferenceField, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(dateField))
                .addContainerGap(306, Short.MAX_VALUE))
        );
        PropertyPanelLayout.setVerticalGroup(
            PropertyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PropertyPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PropertyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ReferenceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(PropertyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(265, 265, 265))
        );

        JTablePanel.setLayout(new javax.swing.BoxLayout(JTablePanel, javax.swing.BoxLayout.LINE_AXIS));

        productField.setText("Enter Product");
        productField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                productFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                productFieldFocusLost(evt);
            }
        });
        productField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                productFieldKeyReleased(evt);
            }
        });

        searchList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                searchListValueChanged(evt);
            }
        });
        searchScrollPane.setViewportView(searchList);

        idAddField.setEditable(false);
        idAddField.setText("ID");

        referenceAddField.setEditable(false);
        referenceAddField.setText("Reference");

        nameAddField.setEditable(false);
        nameAddField.setText("Name");

        batchnumberAddField.setText("Batchnumber");
        batchnumberAddField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                batchnumberAddFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                batchnumberAddFieldFocusLost(evt);
            }
        });

        serialnumberAddField.setText("Serialnumber");
        serialnumberAddField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                serialnumberAddFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                serialnumberAddFieldFocusLost(evt);
            }
        });

        quantityAddField.setText("Quantity");
        quantityAddField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                quantityAddFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                quantityAddFieldFocusLost(evt);
            }
        });

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ProductPanelLayout = new javax.swing.GroupLayout(ProductPanel);
        ProductPanel.setLayout(ProductPanelLayout);
        ProductPanelLayout.setHorizontalGroup(
            ProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JTablePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(ProductPanelLayout.createSequentialGroup()
                .addGroup(ProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ProductPanelLayout.createSequentialGroup()
                        .addGap(304, 304, 304)
                        .addGroup(ProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(productField, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                            .addComponent(searchScrollPane)))
                    .addGroup(ProductPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(idAddField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(referenceAddField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(nameAddField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(batchnumberAddField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(serialnumberAddField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(quantityAddField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(addButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ProductPanelLayout.setVerticalGroup(
            ProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ProductPanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(productField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(searchScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(ProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idAddField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(referenceAddField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameAddField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(batchnumberAddField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(serialnumberAddField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantityAddField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addButton))
                .addGap(18, 18, 18)
                .addComponent(JTablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(HeadPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ProductPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(PropertyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(HeadPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123)
                .addComponent(ProductPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(61, 61, 61)
                    .addComponent(PropertyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(340, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void SaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveButtonActionPerformed
        order.setReference(ReferenceField.getText());
        order.setDate(null);
        
        String error = "";
        if (order.getReference().isEmpty()) {
            error += "Reference is required!";
        }
        
        if (error.isEmpty()) {
            try {
                DAOFactory.getInstance().getOrderDao().create(this.order);
            } catch (DAOException e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, error);
        }
        JTable table = new JTable(new OrderTableModel(this.order.getOrderProducts()));
        table.setFillsViewportHeight(true);
        this.JTablePanel.add(new JScrollPane(table));
        this.JTablePanel.repaint();
        this.JTablePanel.revalidate();
        
        Order order = new Order();
        order.setReference(TOOL_TIP_TEXT_KEY);
        
        this.removeAll();
        ProductPanel panel = new ProductPanel();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        this.repaint();
    }//GEN-LAST:event_SaveButtonActionPerformed

    private void ReferenceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReferenceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReferenceFieldActionPerformed

    private void productFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_productFieldFocusGained
        if (productField.getText().equals("Enter Product")) {
            this.productField.setText("");
        }
    }//GEN-LAST:event_productFieldFocusGained

    private void productFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_productFieldFocusLost
        if (this.productField.getText().equals("")) {
            this.productField.setText("Enter Product");
        }
    }//GEN-LAST:event_productFieldFocusLost

    private void productFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_productFieldKeyReleased
        ArrayList<Product> products = DAOFactory.getInstance().getProductDAO().findByReferenceOrName(this.productField.getText());
        DefaultListModel<Product> productListModel = new DefaultListModel<Product>();
        for (int i = 0; i < products.size(); i++) {
            productListModel.addElement(products.get(i));
        }
        this.searchList.setModel(productListModel);
    }//GEN-LAST:event_productFieldKeyReleased

    private void batchnumberAddFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_batchnumberAddFieldFocusGained
        if (this.batchnumberAddField.getText().equals("Batchnumber")) {
            this.batchnumberAddField.setText("");
        }
    }//GEN-LAST:event_batchnumberAddFieldFocusGained

    private void batchnumberAddFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_batchnumberAddFieldFocusLost
        if (this.batchnumberAddField.getText().equals("")) {
            this.batchnumberAddField.setText("Batchnumber");
        }
    }//GEN-LAST:event_batchnumberAddFieldFocusLost

    private void serialnumberAddFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_serialnumberAddFieldFocusGained
        if (this.serialnumberAddField.getText().equals("Serialnumber")) {
            this.serialnumberAddField.setText("");
        }
    }//GEN-LAST:event_serialnumberAddFieldFocusGained

    private void serialnumberAddFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_serialnumberAddFieldFocusLost
        if (this.serialnumberAddField.getText().equals("")) {
            this.serialnumberAddField.setText("Serialnumber");
        }
    }//GEN-LAST:event_serialnumberAddFieldFocusLost

    private void quantityAddFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_quantityAddFieldFocusGained
        if (this.quantityAddField.getText().equals("Quantity")) {
            this.quantityAddField.setText("");
        }
    }//GEN-LAST:event_quantityAddFieldFocusGained

    private void quantityAddFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_quantityAddFieldFocusLost
        if (this.quantityAddField.getText().equals("")) {
            this.quantityAddField.setText("Quantity");
        }
    }//GEN-LAST:event_quantityAddFieldFocusLost

    private void searchListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_searchListValueChanged
        int index = this.searchList.getSelectedIndex();
        Product product = (Product) this.searchList.getSelectedValue();
        if (index >= 0) {
            this.orderProduct = new OrderProduct();
            this.orderProduct.setProduct(product);
            this.idAddField.setText(""+product.getId());
            this.referenceAddField.setText(product.getReference());
            this.nameAddField.setText(product.getName());
        }
    }//GEN-LAST:event_searchListValueChanged

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        String errors = "";
        if ((serialnumberAddField.getText().equals("")
                || serialnumberAddField.getText().equals("Serialnumber"))
                && (batchnumberAddField.getText().equals("")
                || batchnumberAddField.getText().equals("Batchnumber"))) {
            errors += "Batchnumber or Serialnumber are required \n";
        }
        if (!Tools.isInteger(quantityAddField.getText())) {
            errors += "Quantity must be an integer";
        }
        if (errors != "") {
            JOptionPane.showMessageDialog(null, errors);
        } else {
            this.orderProduct.setBatchNumber(batchnumberAddField.getText());
            this.orderProduct.setSerialNumber(serialnumberAddField.getText());
            this.orderProduct.setQuantity(Long.parseLong(quantityAddField.getText()));
            order.addOrderProduct(orderProduct);
            ((OrderTableModel) this.table.getModel()).addRow(this.orderProduct);
            DefaultListModel<Product> productListModel = new DefaultListModel<Product>();
            this.searchList.setModel(productListModel);
            this.productField.setText("Enter Product");
        }
    }//GEN-LAST:event_addButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel HeadPanel1;
    private javax.swing.JPanel JTablePanel;
    private javax.swing.JPanel ProductPanel;
    private javax.swing.JPanel PropertyPanel;
    private javax.swing.JTextField ReferenceField;
    private javax.swing.JButton SaveButton;
    private javax.swing.JLabel TitleLabel;
    private javax.swing.JToggleButton addButton;
    private javax.swing.JTextField batchnumberAddField;
    private javax.swing.JTextField dateField;
    private javax.swing.JTextField idAddField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField nameAddField;
    private javax.swing.JTextField productField;
    private javax.swing.JTextField quantityAddField;
    private javax.swing.JTextField referenceAddField;
    private javax.swing.JList searchList;
    private javax.swing.JScrollPane searchScrollPane;
    private javax.swing.JTextField serialnumberAddField;
    // End of variables declaration//GEN-END:variables
}
