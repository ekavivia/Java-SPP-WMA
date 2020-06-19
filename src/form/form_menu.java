package form;

import javax.swing.JOptionPane;
import tool.manage;

public class form_menu extends javax.swing.JFrame {

    public form_menu() {
        initComponents();
        this.setLocationRelativeTo(this);
        manage.setMenu("aturan");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jm_pengaturan = new javax.swing.JMenu();
        jmi_penggunaUbahPassword = new javax.swing.JMenuItem();
        jmi_penggunaTutup = new javax.swing.JMenuItem();
        jm_data = new javax.swing.JMenu();
        jmi_dataPengguna = new javax.swing.JMenuItem();
        jm_transaksi = new javax.swing.JMenu();
        jmi_transaksiPenjualan = new javax.swing.JMenuItem();
        jm_proses = new javax.swing.JMenu();
        jmi_prosesAnalisa = new javax.swing.JMenuItem();
        jm_laporan = new javax.swing.JMenu();
        jmi_laporanPenjualan = new javax.swing.JMenuItem();
        jmi_laporanPeramalan = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("SISTEM PERAMALAN PENJUALAN DAGING KIOS PAK HADI");
        setPreferredSize(new java.awt.Dimension(1366, 768));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/frame/frm_menu.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 750));

        jMenuBar1.setBackground(null);

        jm_pengaturan.setBackground(null);
        jm_pengaturan.setText("Pengaturan");

        jmi_penggunaUbahPassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/icon/icon_password.png"))); // NOI18N
        jmi_penggunaUbahPassword.setText("Ubah Password");
        jmi_penggunaUbahPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_penggunaUbahPasswordActionPerformed(evt);
            }
        });
        jm_pengaturan.add(jmi_penggunaUbahPassword);

        jmi_penggunaTutup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/icon/icon_keluar.png"))); // NOI18N
        jmi_penggunaTutup.setText("Tutup");
        jmi_penggunaTutup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_penggunaTutupActionPerformed(evt);
            }
        });
        jm_pengaturan.add(jmi_penggunaTutup);

        jMenuBar1.add(jm_pengaturan);

        jm_data.setBackground(null);
        jm_data.setText("Data");

        jmi_dataPengguna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/icon/icon_daging.png"))); // NOI18N
        jmi_dataPengguna.setText("Jenis Daging");
        jmi_dataPengguna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_dataPenggunaActionPerformed(evt);
            }
        });
        jm_data.add(jmi_dataPengguna);

        jMenuBar1.add(jm_data);

        jm_transaksi.setBackground(null);
        jm_transaksi.setText("Transaksi");

        jmi_transaksiPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/icon/icon_penjualan.png"))); // NOI18N
        jmi_transaksiPenjualan.setText("Penjualan");
        jmi_transaksiPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_transaksiPenjualanActionPerformed(evt);
            }
        });
        jm_transaksi.add(jmi_transaksiPenjualan);

        jMenuBar1.add(jm_transaksi);

        jm_proses.setText("Proses");

        jmi_prosesAnalisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/icon/icon_pembelian.png"))); // NOI18N
        jmi_prosesAnalisa.setText("Analisa");
        jmi_prosesAnalisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_prosesAnalisaActionPerformed(evt);
            }
        });
        jm_proses.add(jmi_prosesAnalisa);

        jMenuBar1.add(jm_proses);

        jm_laporan.setBackground(null);
        jm_laporan.setText("Laporan");

        jmi_laporanPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/icon/icon_lap_penjualan.png"))); // NOI18N
        jmi_laporanPenjualan.setText("Penjualan");
        jmi_laporanPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_laporanPenjualanActionPerformed(evt);
            }
        });
        jm_laporan.add(jmi_laporanPenjualan);

        jmi_laporanPeramalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/icon/icon_lap_prediksi.png"))); // NOI18N
        jmi_laporanPeramalan.setText("Prediksi Penjualan");
        jmi_laporanPeramalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_laporanPeramalanActionPerformed(evt);
            }
        });
        jm_laporan.add(jmi_laporanPeramalan);

        jMenuBar1.add(jm_laporan);

        setJMenuBar(jMenuBar1);

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

    private void jmi_dataPenggunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_dataPenggunaActionPerformed
        // TODO add your handling code here:
        manage.setMenu("data");
        new jd_dataDaging(this, true).show();
    }//GEN-LAST:event_jmi_dataPenggunaActionPerformed

    private void jmi_laporanPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_laporanPenjualanActionPerformed
        // TODO add your handling code here:
        manage.setMenu("laporan");
        new jd_laporanPenjualan(this, true).show();
    }//GEN-LAST:event_jmi_laporanPenjualanActionPerformed

    private void jmi_transaksiPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_transaksiPenjualanActionPerformed
        // TODO add your handling code here:
        manage.setMenu("transaksi");
        new jd_transaksiPenjualan(this, true).show();
    }//GEN-LAST:event_jmi_transaksiPenjualanActionPerformed

    private void jmi_penggunaUbahPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_penggunaUbahPasswordActionPerformed
        // TODO add your handling code here:
        manage.setMenu("aturan");
        new jd_penggunaUbahPassword(this, true).show();
    }//GEN-LAST:event_jmi_penggunaUbahPasswordActionPerformed

    private void jmi_laporanPeramalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_laporanPeramalanActionPerformed
        // TODO add your handling code here:
        manage.setMenu("laporan");
        new jd_laporanPeramalan(this, true).show();
    }//GEN-LAST:event_jmi_laporanPeramalanActionPerformed

    private void jmi_penggunaTutupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_penggunaTutupActionPerformed
        // TODO add your handling code here:
        manage.setMenu("aturan");
        int konfirmasi = JOptionPane.showConfirmDialog(this, "Tutup Program?", "Tutup", JOptionPane.YES_NO_OPTION);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_jmi_penggunaTutupActionPerformed

    private void jmi_prosesAnalisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_prosesAnalisaActionPerformed
        // TODO add your handling code here:
        manage.setMenu("proses");
        new jd_prosesAnalisa(this, true).show();
    }//GEN-LAST:event_jmi_prosesAnalisaActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        int konfirmasi = JOptionPane.showConfirmDialog(this, "Tutup Program?", "Tutup", JOptionPane.YES_NO_OPTION);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*try {
         for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
         if ("Nimbus".equals(info.getName())) {
         javax.swing.UIManager.setLookAndFeel(info.getClassName());
         break;
         }
         }
         } catch (ClassNotFoundException ex) {
         java.util.logging.Logger.getLogger(form_menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(form_menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(form_menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(form_menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         }
         //</editor-fold>
         }*/
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenu jm_data;
    private javax.swing.JMenu jm_laporan;
    private javax.swing.JMenu jm_pengaturan;
    private javax.swing.JMenu jm_proses;
    private javax.swing.JMenu jm_transaksi;
    private javax.swing.JMenuItem jmi_dataPengguna;
    private javax.swing.JMenuItem jmi_laporanPenjualan;
    private javax.swing.JMenuItem jmi_laporanPeramalan;
    private javax.swing.JMenuItem jmi_penggunaTutup;
    private javax.swing.JMenuItem jmi_penggunaUbahPassword;
    private javax.swing.JMenuItem jmi_prosesAnalisa;
    private javax.swing.JMenuItem jmi_transaksiPenjualan;
    // End of variables declaration//GEN-END:variables
}
