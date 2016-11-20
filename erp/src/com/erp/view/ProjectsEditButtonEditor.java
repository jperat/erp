/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erp.view;

import com.erp.beans.Project;
import com.erp.dao.DAOFactory;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Jerome
 */
public class ProjectsEditButtonEditor extends DefaultCellEditor{

    protected JButton button;
    protected ButtonListener buttonListener = new ButtonListener();
    private ProjectsPanel projectsPanel;

    public ProjectsEditButtonEditor(JCheckBox checkBox, ProjectsPanel projectsPanel) {
        super(checkBox);
        this.projectsPanel = projectsPanel;
        button = new JButton("Edit");
        button.addActionListener(buttonListener);
        button.setOpaque(true);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        buttonListener.setId((Long)value);
        return button;
    }
    
    
    
    public class ButtonListener implements ActionListener {

        private Long id;
        
        public void setId(Long id) {
            this.id = id;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Project project = DAOFactory.getInstance().getProjectDao().find(id);
            
            if (project != null) {
                ProjectPanel projectPanel = new ProjectPanel(project);
                projectsPanel.removeAll();
                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(projectsPanel);
                projectsPanel.setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(projectPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(projectPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                projectsPanel.repaint();
            }
        }
        
    }
    
}
