/**
 * CanReg5 - a tool to input, store, check and analyse cancer registry data.
 * Copyright (C) 2008-2015  International Agency for Research on Cancer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author Morten Johannes Ervik, CSU/IARC, ervikm@iarc.fr
 */


/*
 * VariablesChooserPanel.java
 *
 * Created on 23 June 2008, 10:49
 */
package canreg.client.gui.components;

import canreg.common.DatabaseVariablesListElement;
import canreg.common.Globals;
import canreg.common.database.Dictionary;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.jdesktop.application.Action;

/**
 *
 * @author  ervikm
 */
public class VariablesChooserPanel extends javax.swing.JPanel {

    private Map<String, VariablesExportDetailsPanel> panelMap;
    private DatabaseVariablesListElement[] variablesInTable;
    private String tableName = Globals.TUMOUR_AND_PATIENT_JOIN_TABLE_NAME;

    /** Creates new form VariablesChooserPanel */
    public VariablesChooserPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        variablesPanel = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        allVariablesCheckBox = new javax.swing.JCheckBox();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(canreg.client.CanRegClientApp.class).getContext().getResourceMap(VariablesChooserPanel.class);
        variablesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("variablesPanel.border.title"))); // NOI18N
        variablesPanel.setName("variablesPanel"); // NOI18N

        scrollPane.setName("scrollPane"); // NOI18N

        panel.setName("panel"); // NOI18N
        panel.setLayout(new java.awt.GridLayout(0, 1));
        scrollPane.setViewportView(panel);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(canreg.client.CanRegClientApp.class).getContext().getActionMap(VariablesChooserPanel.class, this);
        allVariablesCheckBox.setAction(actionMap.get("exportAllVariablesAction")); // NOI18N
        allVariablesCheckBox.setName("allVariablesCheckBox"); // NOI18N

        javax.swing.GroupLayout variablesPanelLayout = new javax.swing.GroupLayout(variablesPanel);
        variablesPanel.setLayout(variablesPanelLayout);
        variablesPanelLayout.setHorizontalGroup(
            variablesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(allVariablesCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
        );
        variablesPanelLayout.setVerticalGroup(
            variablesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, variablesPanelLayout.createSequentialGroup()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(allVariablesCheckBox))
        );

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(variablesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(variablesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Initialize the variable panel, i.e. fill it with VariablesExportDetailsPanels.
     * @param dictionary
     */
    public void initPanel(Map<Integer, Dictionary> dictionary) {
        panelMap = new TreeMap<String, VariablesExportDetailsPanel>();
        // Remove all variable mappings
        panel.removeAll();
        // DatabaseVariablesListElement[] patientVariablesInDB = canreg.common.Tools.getVariableListElements(CanRegClientApp.getApplication().getDatabseDescription(), Globals.NAMESPACE, Globals.PATIENT_TABLE_NAME);
        // DatabaseVariablesListElement[] tumourVariablesInDB = canreg.common.Tools.getVariableListElements(CanRegClientApp.getApplication().getDatabseDescription(), Globals.NAMESPACE, Globals.TUMOUR_TABLE_NAME);
        // DatabaseVariablesListElement[] sourceVariablesInDB = canreg.common.Tools.getVariableListElements(CanRegClientApp.getApplication().getDatabseDescription(), Globals.NAMESPACE, Globals.SOURCE_TABLE_NAME);

        // Add the panels
        for (DatabaseVariablesListElement variable : variablesInTable) {
            VariablesExportDetailsPanel ved = new VariablesExportDetailsPanel();
            ved.setDictionary(dictionary.get(variable.getDictionaryID()));
            panelMap.put(canreg.common.Tools.toUpperCaseStandardized(variable.getDatabaseVariableName()), ved);
            ved.setVariable(variable);
            // ved.setVariableType(variable.getVariableType());
            panel.add(ved);
            ved.setVisible(true);
        }

        revalidate();
        repaint();

        exportAllVariablesAction();
    }

    /**
     * Export all variables 
     */
    @Action
    public void exportAllVariablesAction() {
        for (VariablesExportDetailsPanel ved : panelMap.values()) {
            ved.setDataCheckBox(allVariablesCheckBox.isSelected());
        }
    }

    /**
     * 
     * @return
     */
    public Set<DatabaseVariablesListElement> getSelectedVariables() {
        Set<DatabaseVariablesListElement> variables = new LinkedHashSet<DatabaseVariablesListElement>();
        for (VariablesExportDetailsPanel ved : panelMap.values()) {
            if (ved.getCheckboxes()[0]) {
                variables.add(ved.getVariable());
            }
        }
        return variables;
    }

    /**
     * 
     * @param tableName
     * @return
     */
    public LinkedList<String> getSelectedVariableNames(String tableName) {
        LinkedList<String> variables = new LinkedList<String>();
        DatabaseVariablesListElement element;
        for (VariablesExportDetailsPanel ved : panelMap.values()) {
            boolean[] checkBoxes = ved.getCheckboxes();
            //Take into account all the checkboxes when returning the checked variables.
            if (checkBoxes[0] || checkBoxes[1] || checkBoxes[2]) {
                element = ved.getVariable();
                if (canreg.common.Tools.toUpperCaseStandardized(tableName).contains(canreg.common.Tools.toUpperCaseStandardized(element.getDatabaseTableName()))) {
                    variables.add(canreg.common.Tools.toUpperCaseStandardized(element.getDatabaseVariableName()));
                }
            }
        }
        return variables;
    }

    public VariablesExportDetailsPanel getVariablesExportDetailsPanelByName(String name) {
        return panelMap.get(canreg.common.Tools.toUpperCaseStandardized(name));
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox allVariablesCheckBox;
    private javax.swing.JPanel panel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JPanel variablesPanel;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the table
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName the table to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setVariablesInTable(DatabaseVariablesListElement[] arrayOfVariablesInSelectedTables) {
        variablesInTable = arrayOfVariablesInSelectedTables;
    }

    public void setVariableDataSelected(String variableName, boolean selected) {
        VariablesExportDetailsPanel ved = panelMap.get(canreg.common.Tools.toUpperCaseStandardized(variableName));
        if (ved!=null){
            ved.setDataCheckBox(selected);
        }
    }
    
    public boolean isVariableDataSelected(String variableName) {
        VariablesExportDetailsPanel ved = panelMap.get(canreg.common.Tools.toUpperCaseStandardized(variableName));
        return ved.isDataCheckBoxSelected();
    }

    // useful for the frequency by year tool... for now.
    public void showOnlyDataColumn() {
        for (VariablesExportDetailsPanel ved : panelMap.values()) {
            ved.showOnlyDataColumn();
        }
    }
}
