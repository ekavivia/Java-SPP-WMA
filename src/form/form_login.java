package form;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import tool.connection;

public class form_login extends javax.swing.JFrame {

    public form_login() {
        initComponents();
        this.setLocationRelativeTo(this);
    }

    public void login() {
        String in_password = jpfPassword.getText();
        try {
            if (in_password.equals("")) {
                JOptionPane.showMessageDialog(this, "Inputan tidak boleh kosong", "Login", 1);
                jpfPassword.requestFocus();
            } else {
                Statement s = (Statement) connection.GetConnection().createStatement();
                ResultSet r = s.executeQuery("select password from admin "
                        + "where password='" + in_password + "'");
                if (r.next()) {
                    form_menu form = new form_menu();
                    form.setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Password Salah", "Login", 2);
                    jpfPassword.setText(null);
                    jpfPassword.requestFocus();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jbMasuk = new javax.swing.JButton();
        jpfPassword = new javax.swing.JPasswordField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jbMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_masuk.png"))); // NOI18N
        jbMasuk.setBorder(null);
        jbMasuk.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_over_masuk.png"))); // NOI18N
        jbMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbMasukActionPerformed(evt);
            }
        });
        jPanel1.add(jbMasuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(633, 460, 100, 27));

        jpfPassword.setBackground(new java.awt.Color(254, 242, 215));
        jpfPassword.setForeground(new java.awt.Color(92, 76, 61));
        jpfPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jpfPassword.setBorder(null);
        jpfPassword.setOpaque(false);
        jpfPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jpfPasswordActionPerformed(evt);
            }
        });
        jPanel1.add(jpfPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 420, 210, 27));

        jSeparator1.setForeground(new java.awt.Color(92, 76, 61));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 450, 210, 10));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/frame/frm_login.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1366, 768));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 768, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbMasukActionPerformed
        // TODO add your handling code here:
        login();
    }//GEN-LAST:event_jbMasukActionPerformed

    private void jpfPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jpfPasswordActionPerformed
        // TODO add your handling code here:
        login();
    }//GEN-LAST:event_jpfPasswordActionPerformed

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
            java.util.logging.Logger.getLogger(form_login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form 
         java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
         new form_login().setVisible(true);
         }
         });
         try{
         UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
         TextureLookAndFeel.setTheme("Snow","","");
         }catch(Exception e){
            
         }*/
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbMasuk;
    private javax.swing.JPasswordField jpfPassword;
    // End of variables declaration//GEN-END:variables
}
