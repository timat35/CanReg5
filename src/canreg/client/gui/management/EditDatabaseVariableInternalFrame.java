/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EditDatabaseVariableInternalFrame.java
 *
 * Created on 06-Jan-2009, 16:19:01
 */
package canreg.client.gui.management;

import canreg.common.DatabaseVariablesListElement;
import canreg.common.Globals;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author ervikm
 */
public class EditDatabaseVariableInternalFrame extends javax.swing.JInternalFrame {

    private Document doc;
    private DOMParser parser;
    private JDesktopPane dtp;
    private TreeMap<String, EditDatabaseVariableJPanel> map;
    private String fileName;

    /** Creates new form EditDatabaseVariableInternalFrame */
    public EditDatabaseVariableInternalFrame() {
        initComponents();
    }

    public void setDesktopPane(JDesktopPane dtp) {
        this.dtp = dtp;
    }

    public boolean loadSystemDefinition(String fileName) throws FileNotFoundException, SAXException, IOException {
        this.fileName = fileName;
        boolean success = false;
        variablesAndTableEditorsPanel.removeAll();
        parser = new DOMParser();
        parser.parse(new InputSource(new FileInputStream(fileName)));
        doc = parser.getDocument();
        map = new TreeMap<String, EditDatabaseVariableJPanel>();
        DatabaseVariablesListElement[] dbles = canreg.common.Tools.getVariableListElements(doc, Globals.NAMESPACE);
        for (DatabaseVariablesListElement dble : dbles) {
            if (dble.getGroupID() > 0) {
                EditDatabaseVariableJPanel edvjp = new EditDatabaseVariableJPanel();
                edvjp.setDble(dble);
                map.put(dble.getShortName(), edvjp);
                variablesAndTableEditorsPanel.add(edvjp);
                edvjp.setVisible(true);
            }
        }
        variablesAndTableEditorsPanel.revalidate();
        variablesAndTableEditorsPanel.repaint();
        return success;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        saveButton = new javax.swing.JButton();
        variablesAndTablesPanel = new javax.swing.JPanel();
        variablesLabel = new javax.swing.JLabel();
        tableLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        variablesAndTableEditorsPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(canreg.client.CanRegClientApp.class).getContext().getResourceMap(EditDatabaseVariableInternalFrame.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(canreg.client.CanRegClientApp.class).getContext().getActionMap(EditDatabaseVariableInternalFrame.class, this);
        saveButton.setAction(actionMap.get("saveAction")); // NOI18N
        saveButton.setText(resourceMap.getString("saveButton.text")); // NOI18N
        saveButton.setName("saveButton"); // NOI18N

        variablesAndTablesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        variablesAndTablesPanel.setName("variablesAndTablesPanel"); // NOI18N

        variablesLabel.setText(resourceMap.getString("variablesLabel.text")); // NOI18N
        variablesLabel.setName("variablesLabel"); // NOI18N

        tableLabel.setText(resourceMap.getString("tableLabel.text")); // NOI18N
        tableLabel.setName("tableLabel"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        variablesAndTableEditorsPanel.setName("variablesAndTableEditorsPanel"); // NOI18N
        variablesAndTableEditorsPanel.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane1.setViewportView(variablesAndTableEditorsPanel);

        javax.swing.GroupLayout variablesAndTablesPanelLayout = new javax.swing.GroupLayout(variablesAndTablesPanel);
        variablesAndTablesPanel.setLayout(variablesAndTablesPanelLayout);
        variablesAndTablesPanelLayout.setHorizontalGroup(
            variablesAndTablesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(variablesAndTablesPanelLayout.createSequentialGroup()
                .addComponent(variablesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addGap(217, 217, 217)
                .addComponent(tableLabel))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
        );
        variablesAndTablesPanelLayout.setVerticalGroup(
            variablesAndTablesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(variablesAndTablesPanelLayout.createSequentialGroup()
                .addGroup(variablesAndTablesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(variablesLabel)
                    .addComponent(tableLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
        );

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(variablesAndTablesPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saveButton)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(variablesAndTablesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void saveAction() {
        //for each variable of the doc update the Table information...

        TreeMap<String, String> variablesToTableMap = new TreeMap<String, String>();

        NodeList nl = doc.getElementsByTagName(Globals.NAMESPACE + "variable");

        for (int i = 0; i < nl.getLength(); i++) {
            Element e = (Element) nl.item(i);
            String shortName = e.getElementsByTagName(Globals.NAMESPACE + "short_name").item(0).getTextContent();
            String tableName = e.getElementsByTagName(Globals.NAMESPACE + "table").item(0).getTextContent();
            Element oldChildElement = null;
            oldChildElement = (Element) e.getElementsByTagName(Globals.NAMESPACE + "table").item(0);
            Element newChildElement = doc.createElement(Globals.NAMESPACE + "table");
            EditDatabaseVariableJPanel panel = map.get(shortName);
            if (panel != null) {
                newChildElement.appendChild(doc.createTextNode(panel.getDble().getDatabaseTableName()));
                e.replaceChild(newChildElement, oldChildElement);
                tableName = panel.getDble().getDatabaseTableName();
            }
            variablesToTableMap.put(shortName.toUpperCase(), tableName);
        }

        // Update the Indexes part
        //
        TreeMap<String, LinkedList<String>> indexMap = canreg.common.Tools.buildIndexMap(Globals.PATIENT_TABLE_NAME, doc, Globals.NAMESPACE);
        indexMap.putAll(canreg.common.Tools.buildIndexMap(Globals.TUMOUR_TABLE_NAME, doc, Globals.NAMESPACE));

        Element parentElement = doc.createElement(Globals.NAMESPACE + "indexes");

        // Split the indexes that needs to be split
        indexMap = canreg.server.management.SystemDefinitionConverter.splitIndexMapInTumourAndPatient(indexMap, variablesToTableMap);

        // then build doc
        Set<String> indexNames = indexMap.keySet();
        Element element;
        // then build doc
        for (String indexName : indexNames) {
            String table = null;

            element = doc.createElement(Globals.NAMESPACE + "index");
            parentElement.appendChild(element);
            Element childElement = createElement(Globals.NAMESPACE + "name", indexName);
            element.appendChild(childElement);

            LinkedList<String> variablesInThisIndex = indexMap.get(indexName);

            String tableOfThisIndex = variablesToTableMap.get(variablesInThisIndex.getFirst());
            childElement = createElement(Globals.NAMESPACE + "table", tableOfThisIndex);
            element.appendChild(childElement);
            for (String variableName : variablesInThisIndex) {
                Element thisElement = doc.createElement(Globals.NAMESPACE + "indexed_variable");
                element.appendChild(thisElement);
                thisElement.appendChild(createElement(Globals.NAMESPACE + "variable_name", variableName));
            }
        }
        // switch the old with the new indexes part
        Element root = doc.getDocumentElement();
        Element oldIndexesElement = (Element) doc.getElementsByTagName(Globals.NAMESPACE + "indexes").item(0);
        root.replaceChild(parentElement, oldIndexesElement);

        // write the xml file
        canreg.server.xml.Tools.writeXmlFile(doc, fileName);
        this.dispose();
    }

    private Element createElement(String variableName, String value) {
        Element childElement = doc.createElement(variableName);
        childElement.appendChild(doc.createTextNode(value));
        return childElement;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton saveButton;
    private javax.swing.JLabel tableLabel;
    private javax.swing.JPanel variablesAndTableEditorsPanel;
    private javax.swing.JPanel variablesAndTablesPanel;
    private javax.swing.JLabel variablesLabel;
    // End of variables declaration//GEN-END:variables
}