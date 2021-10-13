/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application_test_jdbc;
/**
 *
 * @author didierroth
 */


import database.utilities.AccessBD;
import java.beans.Beans;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

public class Application_Test extends javax.swing.JFrame 
{
    AccessBD bd;
    /**
     * Creates new form Application_Test
     */
    public Application_Test() {
        try 
        {
            this.bd = (AccessBD) Beans.instantiate(null, "database.utilities.AccessBD");
        } 
        catch (IOException ex) {
            Logger.getLogger(Application_Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Application_Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
        
        DefaultComboBoxModel dcm = new DefaultComboBoxModel();
        dcm.addElement(AccessBD.MYSQL);
        dcm.addElement(AccessBD.ORACLE);
        
        this.cob_driver.setModel(dcm);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtf_port = new javax.swing.JTextField();
        txtf_id = new javax.swing.JTextField();
        txtf_host = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtf_db = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cob_driver = new javax.swing.JComboBox<>();
        txtf_set = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtf_where = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtf_table = new javax.swing.JTextField();
        pwdf_pwd = new javax.swing.JPasswordField();
        btn_ok = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tab_result = new javax.swing.JTable();
        rbtn_select = new javax.swing.JRadioButton();
        rbtn_count = new javax.swing.JRadioButton();
        rbtn_update = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Host");

        jLabel2.setText("Port");

        jLabel3.setText("Id");

        txtf_port.setText("3306");

        txtf_id.setText("student");

        txtf_host.setText("localhost");

        jLabel4.setText("Password");

        jLabel5.setText("DataBase");

        txtf_db.setText("bd_airport");
        txtf_db.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtf_dbActionPerformed(evt);
            }
        });

        jLabel6.setText("Driver");

        cob_driver.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cob_driver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cob_driverActionPerformed(evt);
            }
        });

        jLabel7.setText("set");

        jLabel8.setText("where");

        jLabel9.setText("table");

        pwdf_pwd.setText("student1");

        btn_ok.setText("OK");
        btn_ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_okActionPerformed(evt);
            }
        });

        tab_result.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tab_result);

        buttonGroup1.add(rbtn_select);
        rbtn_select.setSelected(true);
        rbtn_select.setText("Select");
        rbtn_select.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_selectActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtn_count);
        rbtn_count.setText("Count");

        buttonGroup1.add(rbtn_update);
        rbtn_update.setText("Update");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(txtf_db))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(txtf_host, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtf_port, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txtf_id, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(pwdf_pwd, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(txtf_table, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtf_where)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(25, 25, 25)
                                        .addComponent(cob_driver, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(txtf_set, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_ok)
                                .addGap(21, 21, 21)))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(rbtn_select)
                .addGap(119, 119, 119)
                .addComponent(rbtn_count)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbtn_update)
                .addGap(48, 48, 48))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtf_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtf_host, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtf_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(pwdf_pwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtf_db, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(cob_driver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtf_where, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtf_table, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtf_set, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ok))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtn_select)
                    .addComponent(rbtn_count)
                    .addComponent(rbtn_update))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtf_dbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtf_dbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtf_dbActionPerformed

    private void cob_driverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cob_driverActionPerformed
                // TODO add your handling code here:
    }//GEN-LAST:event_cob_driverActionPerformed

    private void btn_okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_okActionPerformed
        try 
        {
            this.bd.setHost(this.txtf_host.getText());
            this.bd.setPort(this.txtf_port.getText());
            this.bd.setId(this.txtf_id.getText());
            this.bd.setPasswd(this.pwdf_pwd.getText());
            this.bd.setBd(this.txtf_db.getText());
            this.bd.setDriver((String) this.cob_driver.getSelectedItem());
            this.bd.init();
            
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(Application_Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Application_Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (this.rbtn_select.isSelected())
        {
            try 
            {
                this.bd.setTable(this.txtf_table.getText());
                this.bd.setCondition(this.txtf_where.getText());
                this.bd.select();
                
                int numberOfColumns = this.bd.getResultat().getMetaData().getColumnCount();
                Vector columnNames = new Vector();
                
                for (int column = 0; column < numberOfColumns; column++) 
                {
                    columnNames.addElement(this.bd.getResultat().getMetaData().getColumnLabel(column + 1));
                }
                
                Vector rows = new Vector();

                while (this.bd.getResultat().next())
                {
                    Vector newRow = new Vector();

                    for (int i = 1; i <= numberOfColumns; i++) 
                    {
                        newRow.addElement(this.bd.getResultat().getObject(i));
                    }

                    rows.addElement(newRow);
                }
                DefaultTableModel dtm = new DefaultTableModel(rows, columnNames);
                this.tab_result.setModel(dtm);
            } 
            catch (SQLException ex) 
            {
                Logger.getLogger(Application_Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (this.rbtn_count.isSelected())
        {
            try 
            {
                this.bd.setTable(this.txtf_table.getText());
                this.bd.count();
                
                int numberOfColumns = this.bd.getResultat().getMetaData().getColumnCount();
                Vector columnNames = new Vector();
                
                for (int column = 0; column < numberOfColumns; column++) 
                {
                    columnNames.addElement(this.bd.getResultat().getMetaData().getColumnLabel(column + 1));
                }
                
                Vector rows = new Vector();

                while (this.bd.getResultat().next())
                {
                    Vector newRow = new Vector();

                    for (int i = 1; i <= numberOfColumns; i++) 
                    {
                        newRow.addElement(this.bd.getResultat().getObject(i));
                    }

                    rows.addElement(newRow);
                }
                DefaultTableModel dtm = new DefaultTableModel(rows, columnNames);
                this.tab_result.setModel(dtm);
            } 
            catch (SQLException ex) 
            {
                Logger.getLogger(Application_Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (this.rbtn_update.isSelected())
        {
            try 
            {
                this.bd.setTable(this.txtf_table.getText());
                this.bd.setCondition(this.txtf_where.getText());
                this.bd.setModif(this.txtf_set.getText());
                this.bd.update();
            }   
            catch (SQLException ex) 
            {
                Logger.getLogger(Application_Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_btn_okActionPerformed

    private void rbtn_selectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_selectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtn_selectActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Application_Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Application_Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Application_Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Application_Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Application_Test().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_ok;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cob_driver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPasswordField pwdf_pwd;
    private javax.swing.JRadioButton rbtn_count;
    private javax.swing.JRadioButton rbtn_select;
    private javax.swing.JRadioButton rbtn_update;
    private javax.swing.JTable tab_result;
    private javax.swing.JTextField txtf_db;
    private javax.swing.JTextField txtf_host;
    private javax.swing.JTextField txtf_id;
    private javax.swing.JTextField txtf_port;
    private javax.swing.JTextField txtf_set;
    private javax.swing.JTextField txtf_table;
    private javax.swing.JTextField txtf_where;
    // End of variables declaration//GEN-END:variables
}