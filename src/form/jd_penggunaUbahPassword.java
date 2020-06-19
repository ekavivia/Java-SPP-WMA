package form;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import tool.connection;

public class jd_penggunaUbahPassword extends javax.swing.JDialog {

    public jd_penggunaUbahPassword(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent);
        jtfPassLama.setText(null);
        jpfPassBaru.setText(null);
        jpfPassUlang.setText(null);
    }

    public void ubah_pass() {
        String p_lama = jtfPassLama.getText();
        String p_baru = jpfPassBaru.getText();
        String p_ulang = jpfPassUlang.getText();
        if (p_lama.equals("") || p_baru.equals("") || p_ulang.equals("")) {
            JOptionPane.showMessageDialog(null, "Inputan tidak boleh kosong", "Ubah Password", 1);
            jtfPassLama.requestFocus();
        } else {
            try {
                Statement s = (Statement) connection.GetConnection().createStatement();
                Statement s1 = (Statement) connection.GetConnection().createStatement();
                ResultSet r = s.executeQuery("select * from admin where password='" + p_lama + "'");
                if (r.next()) {
                    if (p_baru.equals(p_ulang)) {
                        s1.executeUpdate("update admin set password='" + p_ulang + "'");
                        JOptionPane.showMessageDialog(null, "Password Berbasil diubah", "Ubah Password", 1);
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Password Tidak Sesuai", "Ubah Password", 2);
                        jpfPassUlang.setText(null);
                        jpfPassUlang.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Password Salah", "Ubah Password", 2);
                    jtfPassLama.setText(null);
                    jtfPassLama.requestFocus();
                }
            } catch (Exception e) {
                System.out.println("");
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jfcFile = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        jtfPassLama = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jbSimpan = new javax.swing.JButton();
        jpfPassBaru = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jpfPassUlang = new javax.swing.JPasswordField();
        jbHome = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Data Hasil Produksi");
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtfPassLama.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(jtfPassLama, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 260, 25));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(92, 76, 61));
        jLabel1.setText("Password Baru");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 140, 25));

        jbSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_simpan.png"))); // NOI18N
        jbSimpan.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_over_simpan.png"))); // NOI18N
        jbSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSimpanActionPerformed(evt);
            }
        });
        jPanel1.add(jbSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 100, 27));

        jpfPassBaru.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(jpfPassBaru, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 260, 25));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(92, 76, 61));
        jLabel3.setText("Password Lama");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 140, 25));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(92, 76, 61));
        jLabel4.setText("Ulangi Password");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 140, 25));

        jpfPassUlang.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(jpfPassUlang, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 260, 25));

        jbHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_kembali.png"))); // NOI18N
        jbHome.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_over_kembali.png"))); // NOI18N
        jbHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbHomeActionPerformed(evt);
            }
        });
        jPanel1.add(jbHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(1155, 10, 27, 27));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/frame/frm_password.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 650));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSimpanActionPerformed
        // TODO add your handling code here:
        ubah_pass();
    }//GEN-LAST:event_jbSimpanActionPerformed

    private void jbHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbHomeActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jbHomeActionPerformed

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
            java.util.logging.Logger.getLogger(jd_penggunaUbahPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jd_penggunaUbahPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jd_penggunaUbahPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jd_penggunaUbahPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jd_penggunaUbahPassword dialog = new jd_penggunaUbahPassword(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbHome;
    private javax.swing.JButton jbSimpan;
    private javax.swing.JFileChooser jfcFile;
    private javax.swing.JPasswordField jpfPassBaru;
    private javax.swing.JPasswordField jpfPassUlang;
    private javax.swing.JTextField jtfPassLama;
    // End of variables declaration//GEN-END:variables
}
