/*
 * VariablesChooserPanel.java
 *
 * Created on 23 June 2008, 10:49
 */
package canreg.client.gui.components;

import canreg.client.gui.components.VariablesExportDetailsPanel;
import canreg.client.CanRegClientApp;
import canreg.common.DatabaseVariablesListElement;
import canreg.common.Globals;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import org.jdesktop.application.Action;

/**
 *
 * @author  ervikm
 */
public class VariablesChooserPanel extends javax.swing.JPanel {

    private LinkedList<VariablesExportDetailsPanel> panelList;
    private DatabaseVariablesListElement[] variablesInDB;

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
     */
    public void initPanel() {
        panelList = (LinkedList<VariablesExportDetailsPanel>) new LinkedList();
        // Remove all variable mappings
        panel.removeAll();
        variablesInDB = canreg.common.Tools.getVariableListElements(CanRegClientApp.getApplication().getDatabseDescription(), Globals.NAMESPACE);

        // Add the panels
        for (DatabaseVariablesListElement variable : variablesInDB) {
            VariablesExportDetailsPanel ved = new VariablesExportDetailsPanel();
            panelList.add(ved);
            ved.setVariable(variable);
            // ved.setVariableType(variable.getVariableType());
            panel.add(ved);
            ved.setVisible(true);
        }

        revalidate();
        repaint();
    }

    /**
     * Export all variables 
     */
    @Action
    public void exportAllVariablesAction() {
        for (VariablesExportDetailsPanel ved : panelList) {
            ved.setDataCheckBox(allVariablesCheckBox.isSelected());
        }
    }

    public Set<DatabaseVariablesListElement> getSelectedVariables() {
        Set<DatabaseVariablesListElement> variables = new LinkedHashSet<DatabaseVariablesListElement>();
        for (VariablesExportDetailsPanel ved : panelList) {
            if (ved.getCheckboxes()[0]){
                variables.add(ved.getVariable());
            }
        }
        return variables;
    }
    
    public LinkedList<String> getSelectedVariableNames(String tableName){
        LinkedList<String> variables = new LinkedList<String>();
        DatabaseVariablesListElement element = null;
        for (VariablesExportDetailsPanel ved : panelList) {
            if (ved.getCheckboxes()[0]){
                element = ved.getVariable();
                if ("both".equalsIgnoreCase(tableName)||element.getDatabaseTableName().equalsIgnoreCase(tableName)){
                    variables.add(element.getDatabaseVariableName().toUpperCase());
                }
            }
        }
        return variables;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox allVariablesCheckBox;
    private javax.swing.JPanel panel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JPanel variablesPanel;
    // End of variables declaration//GEN-END:variables
}
