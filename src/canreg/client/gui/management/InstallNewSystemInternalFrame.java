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
 * InstallNewSystemInternalFrame.java
 *
 * Created on 01 July 2008, 16:59
 */
package canreg.client.gui.management;

import canreg.client.CanRegClientApp;
import canreg.client.LocalSettings;
import canreg.client.gui.tools.WaitFrame;
import canreg.common.Globals;
import canreg.exceptions.WrongCanRegVersionException;
import canreg.server.management.SystemDescription;
import java.io.File;
// import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author  ervikm
 */
public class InstallNewSystemInternalFrame extends javax.swing.JInternalFrame {

    SystemDescription systemDescription;
    LocalSettings localSettings;
    private String[] backupFilePatterns = new String[]{"seg0", "log"};

    /** Creates new form InstallNewSystemInternalFrame */
    public InstallNewSystemInternalFrame() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileNameTextField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setClosable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(canreg.client.CanRegClientApp.class).getContext().getResourceMap(InstallNewSystemInternalFrame.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setFrameIcon(resourceMap.getIcon("Form.frameIcon")); // NOI18N
        setName("Form"); // NOI18N

        fileNameTextField.setText(resourceMap.getString("fileNameTextField.text")); // NOI18N
        fileNameTextField.setName("fileNameTextField"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(canreg.client.CanRegClientApp.class).getContext().getActionMap(InstallNewSystemInternalFrame.class, this);
        jButton1.setAction(actionMap.get("browseAction")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jButton2.setAction(actionMap.get("installAction")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        jButton3.setAction(actionMap.get("cancelAction")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fileNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel1)
                    .addComponent(fileNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * 
     */
    @Action
    public void installAction() {
        String fileNameWithPath = fileNameTextField.getText().trim();
        File file = new File(fileNameWithPath);
        String test;
        boolean backupTried = false;
        if (file.exists()) {
            this.dispose();
            try {
                localSettings = CanRegClientApp.getApplication().getLocalSettings();

                String document = Globals.CANREG_SERVER_SYSTEM_CONFIG_FOLDER + Globals.FILE_SEPARATOR + file.getName();

                // Create the directories if they don't exist.
                File systemDir = new File(Globals.CANREG_SERVER_FOLDER);
                systemDir.mkdir();
                systemDir = new File(Globals.CANREG_SERVER_SYSTEM_CONFIG_FOLDER);
                systemDir.mkdir();
                // copy the document to the CanReg system folder...
                if (!fileNameWithPath.equalsIgnoreCase(document)) {
                    canreg.common.Tools.fileCopy(fileNameWithPath, document);
                } else {
                    JOptionPane.showInternalMessageDialog(CanRegClientApp.getApplication().getMainFrame().getContentPane(), fileNameWithPath + java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/InstallNewSystemInternalFrame").getString(" IS ALREADY IN THE SYSTEM FOLDER. NO NEED TO COPY."), java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/InstallNewSystemInternalFrame").getString("MESSAGE."), JOptionPane.WARNING_MESSAGE);
                }
                // load the document
                loadDocument(document);
                // Add this new server to the list of favourite servers
                int i = localSettings.addServerToServerList(systemDescription.getSystemName(), Globals.DEFAULT_SERVER_ADDRESS, Globals.DEFAULT_PORT, systemDescription.getSystemCode());
                // Set it as default start up server
                localSettings.setProperty(LocalSettings.LAST_SERVER_ID_KEY, "" + i);
                // Set it to autostart
                localSettings.setProperty(LocalSettings.AUTO_START_SERVER_KEY, LocalSettings.TRUE_PROPERTY);
                // Look for a backup in the same folder
                File backupFolder = new File(file.getParent()
                        + Globals.FILE_SEPARATOR
                        + systemDescription.getSystemCode());
                if (backupFolder.isDirectory()) {
                    File[] files = backupFolder.listFiles();
                    int found = 0;
                    for (File tempFile : files) {
                        for (String backupFilePattern : backupFilePatterns) {
                            if (backupFilePattern.equals(tempFile.getName())) {
                                found++;
                            }
                        }
                    }
                    if (found == backupFilePatterns.length) {
                        int answer = JOptionPane.showConfirmDialog(CanRegClientApp.getApplication().getMainFrame().getContentPane(), java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/InstallNewSystemInternalFrame").getString("THERE SEEM TO BE A BACKUP ASSOCIATED WITH THIS SYSTEM DEFINITION FILE.")
                                + java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/InstallNewSystemInternalFrame").getString("DO YOU WANT TO TRY TO RESTORE THIS?"), java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/InstallNewSystemInternalFrame").getString("BACKUP FOUND"), JOptionPane.YES_NO_OPTION);
                        if (answer == JOptionPane.YES_OPTION) {
                            backupTried = true;
                            // first launch the server
                            Task launchTask = launchCanRegServerAction();
                            launchTask.execute();
                        }
                    }
                }

                // and show confirmation
                if (!backupTried) {
                    JOptionPane.showInternalMessageDialog(CanRegClientApp.getApplication().getMainFrame().getContentPane(), systemDescription.getSystemName() + java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/InstallNewSystemInternalFrame").getString(" IS NOW READY. PLEASE LOG IN."), java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/InstallNewSystemInternalFrame").getString("MESSAGE."), JOptionPane.INFORMATION_MESSAGE);
                    CanRegClientApp.getApplication().showLogginFrame();
                }
                // this.dispose();
            } catch (IOException ex) {
                JOptionPane.showInternalMessageDialog(CanRegClientApp.getApplication().getMainFrame().getContentPane(), java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/InstallNewSystemInternalFrame").getString("ERROR COPYING FILE."), java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/InstallNewSystemInternalFrame").getString("ERROR."), JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(InstallNewSystemInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showInternalMessageDialog(CanRegClientApp.getApplication().getMainFrame().getContentPane(), java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/InstallNewSystemInternalFrame").getString("PLEASE SPECIFY FILE NAME."), java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/InstallNewSystemInternalFrame").getString("ERROR."), JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 
     */
    @Action
    public void cancelAction() {
        this.dispose();
    }

    /**
     * 
     */
    @Action
    public void browseAction() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter(java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/InstallNewSystemInternalFrame").getString("XML_FILES"), "xml");

        JFileChooser chooser = new JFileChooser(".");
        chooser.addChoosableFileFilter(filter);

        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                //set the file name
                fileNameTextField.setText(chooser.getSelectedFile().getCanonicalPath());
                // changeFile();
            } catch (IOException ex) {
                Logger.getLogger(InstallNewSystemInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void loadDocument(String document) {
        systemDescription = new SystemDescription(document);
    }

    @Action()
    public Task launchCanRegServerAction() {
        return new LaunchCanRegServerActionTask(org.jdesktop.application.Application.getInstance(canreg.client.CanRegClientApp.class));
    }

    private class LaunchCanRegServerActionTask extends org.jdesktop.application.Task<Object, Void> {

        WaitFrame waitFrame;

        LaunchCanRegServerActionTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to LaunchCanRegServerActionTask fields, here.
            super(app);
            // launchServerButton.setEnabled(false);
            // feedbackLabel.setText(java.util.ResourceBundle.getBundle("canreg/client/gui/resources/LoginInternalFrame").getString("LAUNCHING_SERVER..."));
            JDesktopPane desktopPane = CanRegClientApp.getApplication().getDesktopPane();
            waitFrame = new WaitFrame();
            waitFrame.setLabel(java.util.ResourceBundle.getBundle("canreg/client/gui/resources/LoginInternalFrame").getString("LAUNCHING_SERVER..."));
            waitFrame.setIndeterminate(true);
            desktopPane.add(waitFrame, javax.swing.JLayeredPane.POPUP_LAYER);
            waitFrame.setVisible(true);
            waitFrame.setLocation((desktopPane.getWidth() - waitFrame.getWidth()) / 2, (desktopPane.getHeight() - waitFrame.getHeight()) / 2);
        }

        @Override
        protected Object doInBackground() {
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here...
            String result = "stopped";
            try {
                if (canreg.common.ServerLauncher.start(Globals.DEFAULT_SERVER_ADDRESS, systemDescription.getSystemCode(), Globals.DEFAULT_PORT)) {
                    result = "started";
                }
            } catch (AlreadyBoundException ex) {
                result = "running";
                Logger.getLogger(InstallNewSystemInternalFrame.class.getName()).log(Level.INFO, null, ex);
            }
            // Return your result...
            return result;
        }

        @Override
        protected void succeeded(Object resultObject) {
            waitFrame.dispose();
            String resultString = (String) resultObject;
            if (resultString.equalsIgnoreCase("running")) {
                // feedbackLabel.setText(java.util.ResourceBundle.getBundle("canreg/client/gui/resources/LoginInternalFrame").getString("SERVER_ALREADY_RUNNING."));
                JOptionPane.showInternalMessageDialog(CanRegClientApp.getApplication().getMainFrame().getContentPane(), java.util.ResourceBundle.getBundle("canreg/client/gui/resources/LoginInternalFrame").getString("SERVER_ALREADY_RUNNING."), java.util.ResourceBundle.getBundle("canreg/client/gui/resources/LoginInternalFrame").getString("MESSAGE"), JOptionPane.INFORMATION_MESSAGE);
            } else if (resultString.equalsIgnoreCase("stopped")) {
                // feedbackLabel.setText(java.util.ResourceBundle.getBundle("canreg/client/gui/resources/LoginInternalFrame").getString("SERVER_FAILED_TO_START."));
                JOptionPane.showInternalMessageDialog(CanRegClientApp.getApplication().getMainFrame().getContentPane(), java.util.ResourceBundle.getBundle("canreg/client/gui/resources/LoginInternalFrame").getString("SERVER_FAILED_TO_START."), java.util.ResourceBundle.getBundle("canreg/client/gui/resources/LoginInternalFrame").getString("MESSAGE"), JOptionPane.ERROR_MESSAGE);
            } else if (resultString != null) {
                // feedbackLabel.setText(java.util.ResourceBundle.getBundle("canreg/client/gui/resources/LoginInternalFrame").getString("SERVER_STARTED."));
                CanRegClientApp.getApplication().setCanregServerRunningInThisThread(true);
                JOptionPane.showInternalMessageDialog(CanRegClientApp.getApplication().getMainFrame().getContentPane(), java.util.ResourceBundle.getBundle("canreg/client/gui/resources/LoginInternalFrame").getString("SERVER_STARTED."), java.util.ResourceBundle.getBundle("canreg/client/gui/resources/LoginInternalFrame").getString("MESSAGE"), JOptionPane.INFORMATION_MESSAGE);
                // launchServerButton.setEnabled(false);
                // call restore from backup
                Task restoreTask = restoreAction();
                restoreTask.execute();
            } else {
                // feedbackLabel.setText(java.util.ResourceBundle.getBundle("canreg/client/gui/resources/LoginInternalFrame").getString("SERVER_FAILED_TO_START."));
                JOptionPane.showInternalMessageDialog(CanRegClientApp.getApplication().getMainFrame().getContentPane(), java.util.ResourceBundle.getBundle("canreg/client/gui/resources/LoginInternalFrame").getString("SERVER_FAILED_TO_START."), java.util.ResourceBundle.getBundle("canreg/client/gui/resources/LoginInternalFrame").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     *
     * @return
     */
    @Action
    public Task restoreAction() {
        return new RestoreActionTask(org.jdesktop.application.Application.getInstance(canreg.client.CanRegClientApp.class));
    }

    private class RestoreActionTask extends org.jdesktop.application.Task<Object, Void> {

        String folderName = null;
        WaitFrame waitFrame;

        RestoreActionTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to OkActionTask fields, here.

            super(app);
            String fileNameWithPath = fileNameTextField.getText().trim();
            File file = new File(fileNameWithPath);
            folderName = file.getParent();
            JDesktopPane desktopPane = CanRegClientApp.getApplication().getDesktopPane();
            waitFrame = new WaitFrame();
            waitFrame.setLabel(java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/InstallNewSystemInternalFrame").getString("RESTORING DATABASE... PLEASE WAIT..."));
            waitFrame.setIndeterminate(true);
            desktopPane.add(waitFrame, javax.swing.JLayeredPane.POPUP_LAYER);
            waitFrame.setVisible(true);
            waitFrame.setLocation((desktopPane.getWidth() - waitFrame.getWidth()) / 2, (desktopPane.getHeight() - waitFrame.getHeight()) / 2);

            // log in as default user
            // String serverObjectString = "rmi://" + Globals.DEFAULT_SERVER_ADDRESS + ":" + Globals.DEFAULT_PORT + "/CanRegLogin" + systemDescription.getSystemCode();
            try {
                String canRegSystemName = CanRegClientApp.getApplication().loginDirect(systemDescription.getSystemCode(), "morten", new char[]{'e', 'r', 'v', 'i', 'k'});
            } catch (LoginException ex) {
                Logger.getLogger(InstallNewSystemInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex) {
                Logger.getLogger(InstallNewSystemInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(InstallNewSystemInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(InstallNewSystemInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(InstallNewSystemInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownHostException ex) {
                Logger.getLogger(InstallNewSystemInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (WrongCanRegVersionException ex) {
                Logger.getLogger(InstallNewSystemInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        @Override
        protected String doInBackground() {
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            String success = "";

            if (folderName.length() > 0) {
                try {
                    success = canreg.client.CanRegClientApp.getApplication().restoreBackup(folderName);
                } catch (SecurityException ex) {
                    Logger.getLogger(RestoreInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
                    return "security exception";
                } catch (RemoteException ex) {
                    Logger.getLogger(RestoreInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
                    return "remote exception";
                }
            } else {
                return "no name";
            }
            return success;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
            waitFrame.dispose();
            String resultString = (String) result;

            if (resultString.equals("security exception")) {
                JOptionPane.showInternalMessageDialog(CanRegClientApp.getApplication().getMainFrame().getContentPane(), java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/RestoreInternalFrame").getString("YOU DON'T HAVE THE USER LEVEL TO DO THIS."), java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/RestoreInternalFrame").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
            } else if (resultString.equals("remote exception")) {
                // You don't have the user level to do this...
                JOptionPane.showInternalMessageDialog(CanRegClientApp.getApplication().getMainFrame().getContentPane(), java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/RestoreInternalFrame").getString("SERVER ERROR."), java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/RestoreInternalFrame").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
            } else if (resultString.equals("no name")) {
                JOptionPane.showInternalMessageDialog(CanRegClientApp.getApplication().getMainFrame().getContentPane(), java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/RestoreInternalFrame").getString("PLEASE SPECIFY A FOLDER NAME."), java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/RestoreInternalFrame").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
            } else if (resultString.equals("shutdown failed")) {
                JOptionPane.showInternalMessageDialog(CanRegClientApp.getApplication().getMainFrame().getContentPane(), java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/RestoreInternalFrame").getString("COULD NOT SHUT DOWN THE DATABASE."), java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/RestoreInternalFrame").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
            } else if (resultString.equals("failed")) {
                // Restore not successfull
                JOptionPane.showInternalMessageDialog(CanRegClientApp.getApplication().getMainFrame().getContentPane(), java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/RestoreInternalFrame").getString("RESTORE NOT SUCCESSFULL."), java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/RestoreInternalFrame").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
            } else {
                // All went well
                try {
                    CanRegClientApp.getApplication().logOut();
                } catch (RemoteException ex) {
                    Logger.getLogger(InstallNewSystemInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showInternalMessageDialog(CanRegClientApp.getApplication().getMainFrame().getContentPane(), java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/RestoreInternalFrame").getString("RESTORE SUCCESSFULL.") + "\n" + java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/RestoreInternalFrame").getString("PLEASE RESTART YOUR CANREG SYSTEM."), java.util.ResourceBundle.getBundle("canreg/client/gui/management/resources/RestoreInternalFrame").getString("MESSAGE"), JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField fileNameTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
