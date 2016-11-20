/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erp.view;

import com.erp.beans.Product;
import com.erp.dao.DAOFactory;
import com.erp.tools.Tools;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Jerome
 */
public class ProductsPanel extends javax.swing.JPanel {

    private Erp erp;
    private JPanel ContentPanel;
    private JTable table;
    private JButton edit = new JButton("Edit");

    /**
     * Creates new form ProductsPanel
     */
    public ProductsPanel(JPanel ContentPanel) {
        initComponents();
        this.ContentPanel = ContentPanel;
        this.createTableProduct(new ProductListTableModel(DAOFactory.getInstance().getProductDAO().findAll()));
    }

    public ProductsPanel() {
        initComponents();
        this.createTableProduct(new ProductListTableModel(DAOFactory.getInstance().getProductDAO().findAll()));
    }

    public void createTableProduct(ProductListTableModel productListTableModel) {
        this.table = new JTable(productListTableModel);
//        if (this.JTablePanel != null)
        this.JTablePanel.removeAll();
        this.table.setFillsViewportHeight(true);
        this.table.getColumn("View").setCellRenderer(new ViewButtonRender());
        this.table.getColumn("View").setCellEditor(new ProductsViewButtonEditor(new JCheckBox(), this));
        this.table.getColumn("Edit").setCellRenderer(new EditButtonRender());
        this.table.getColumn("Edit").setCellEditor(new ProductsEditButtonEditor(new JCheckBox(), this));

        this.table.setDefaultRenderer(JButton.class, new TableComponent());

        this.table.setRowHeight(30);
        this.table.getColumnModel().getColumn(0).setMaxWidth(50);
        this.JTablePanel.add(new JScrollPane(this.table));
        this.JTablePanel.repaint();
        this.JTablePanel.revalidate();
    }

    class ProductListTableModel extends AbstractTableModel {

        private Object[][] data;
        private String[] columnNames = {"Id", "Name", "Reference", "Location", "View", "Edit"};

        private ProductListTableModel(List<Product> productList) {
            if (productList == null) {
                data = new Object[0][6];
            } else {
                data = new Object[productList.size()][6];
                for (int i = 0; i < productList.size(); i++) {
                    data[i][0] = productList.get(i).getId();
                    data[i][1] = productList.get(i).getName();
                    data[i][2] = productList.get(i).getReference();
                    data[i][3] = productList.get(i).getLocation();
                    data[i][4] = productList.get(i).getId();
                    data[i][5] = productList.get(i).getId();
                }
            }
        }

        public int getRowCount() {
            return this.data.length;
        }

        public int getColumnCount() {
            return this.columnNames.length;
        }

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
//            if (getValueAt(0, columnIndex) instanceof JButton) {
//                return false;
//            }
            if (columnIndex == 4 || columnIndex == 5) {
                return true;
            }
            return false;
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

    public class EditButtonRender extends JButton implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Edit");
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

        HeadPanel = new javax.swing.JPanel();
        TitleLabel = new javax.swing.JLabel();
        IdTextField = new javax.swing.JTextField();
        NameOrReferenceTextField = new javax.swing.JTextField();
        SearchButton = new javax.swing.JButton();
        NewButton = new javax.swing.JButton();
        JTablePanel = new javax.swing.JPanel();

        HeadPanel.setBackground(new java.awt.Color(204, 204, 204));

        TitleLabel.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        TitleLabel.setText("Products");

        IdTextField.setText("ID");
        IdTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                IdTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                IdTextFieldFocusLost(evt);
            }
        });
        IdTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IdTextFieldActionPerformed(evt);
            }
        });

        NameOrReferenceTextField.setText("Name Or Reference");
        NameOrReferenceTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                NameOrReferenceTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                NameOrReferenceTextFieldFocusLost(evt);
            }
        });

        SearchButton.setText("Search");
        SearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchButtonActionPerformed(evt);
            }
        });

        NewButton.setText("New");
        NewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout HeadPanelLayout = new javax.swing.GroupLayout(HeadPanel);
        HeadPanel.setLayout(HeadPanelLayout);
        HeadPanelLayout.setHorizontalGroup(
            HeadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeadPanelLayout.createSequentialGroup()
                .addGroup(HeadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HeadPanelLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(IdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addComponent(NameOrReferenceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(HeadPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(TitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 387, Short.MAX_VALUE)
                .addGroup(HeadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NewButton)
                    .addComponent(SearchButton))
                .addGap(107, 107, 107))
        );
        HeadPanelLayout.setVerticalGroup(
            HeadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeadPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HeadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NewButton))
                .addGap(18, 18, 18)
                .addGroup(HeadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NameOrReferenceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchButton))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        JTablePanel.setLayout(new javax.swing.BoxLayout(JTablePanel, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JTablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(HeadPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(HeadPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JTablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void IdTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IdTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IdTextFieldActionPerformed

    private void NewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewButtonActionPerformed
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
    }//GEN-LAST:event_NewButtonActionPerformed

    private void IdTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdTextFieldFocusGained
        if (this.IdTextField.getText().equals("ID")) {
            this.IdTextField.setText("");
        }
    }//GEN-LAST:event_IdTextFieldFocusGained

    private void IdTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdTextFieldFocusLost
        if (this.IdTextField.getText().length() == 0) {
            this.IdTextField.setText("ID");
        }
    }//GEN-LAST:event_IdTextFieldFocusLost

    private void NameOrReferenceTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NameOrReferenceTextFieldFocusGained
        if (this.NameOrReferenceTextField.getText().equals("Name Or Reference")) {
            this.NameOrReferenceTextField.setText("");
        }
    }//GEN-LAST:event_NameOrReferenceTextFieldFocusGained

    private void NameOrReferenceTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NameOrReferenceTextFieldFocusLost
        if (this.NameOrReferenceTextField.getText().length() == 0) {
            this.NameOrReferenceTextField.setText("Name Or Reference");
        }
    }//GEN-LAST:event_NameOrReferenceTextFieldFocusLost

    private void SearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchButtonActionPerformed
        if (!IdTextField.getText().equalsIgnoreCase("id")) {
            if (Tools.isLong(IdTextField.getText())) {
                Product product = DAOFactory.getInstance().getProductDAO().find(Long.parseLong(IdTextField.getText()));
                if (product == null) {
                    JOptionPane.showMessageDialog(null, "No result for this id");
                } else {
                    ArrayList<Product> products = new ArrayList<Product>();
                    products.add(product);
                     this.createTableProduct(new ProductListTableModel(products));
                }
            } else {
                JOptionPane.showMessageDialog(null, "The id field must be an Integer");
            }
        } else {
            this.createTableProduct(new ProductListTableModel(DAOFactory.getInstance().getProductDAO().findByReferenceOrName(this.NameOrReferenceTextField.getText())));
        }
    }//GEN-LAST:event_SearchButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel HeadPanel;
    private javax.swing.JTextField IdTextField;
    private javax.swing.JPanel JTablePanel;
    private javax.swing.JTextField NameOrReferenceTextField;
    private javax.swing.JButton NewButton;
    private javax.swing.JButton SearchButton;
    private javax.swing.JLabel TitleLabel;
    // End of variables declaration//GEN-END:variables
}
