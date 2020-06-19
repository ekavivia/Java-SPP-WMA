package form;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import tool.connection;

public class jd_laporanPenjualan extends javax.swing.JDialog {

    Date now = new Date();
    SimpleDateFormat date_in = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat date_out = new SimpleDateFormat("dd/MM/yyyy");

    public jd_laporanPenjualan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent);
        jdcTanggalDari.setDate(now);
        jdcTanggalSampai.setDate(now);
    }

    public void proses_data() {
        String dari = date_in.format(jdcTanggalDari.getDate());
        String sampai = date_in.format(jdcTanggalSampai.getDate());
        try {
            Statement s = (Statement) connection.GetConnection().createStatement();
            Statement s1 = (Statement) connection.GetConnection().createStatement();
            s.executeUpdate("truncate table laporan");
            ResultSet r = s.executeQuery("select * from penjualan where tanggal "
                    + "between '" + dari + "' and '" + sampai + "' order by tanggal asc");
            while (r.next()) {
                String tgl = r.getString("tanggal");
                String sp = "";
                String bi = "";
                ResultSet rs = s1.executeQuery("select (ifnull(jumlah,0)) as jumlah "
                        + "from detail_penjualan where kd_daging='D01' and kd_jual='" + r.getString("kd_jual") + "'");
                if (rs.next()) {
                    sp = rs.getString("jumlah");
                }
                ResultSet rb = s1.executeQuery("select (ifnull(jumlah,0)) as jumlah "
                        + "from detail_penjualan where kd_daging='D02' and kd_jual='" + r.getString("kd_jual") + "'");
                if (rb.next()) {
                    bi = rb.getString("jumlah");
                }
                s1.executeUpdate("insert into laporan values('" + tgl + "','" + sp + "','" + bi + "')");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void tampil_laporan() {
        proses_data();
        String laporan = "src/laporan/rptPenjualan.jasper";
        String a = date_out.format(jdcTanggalDari.getDate());
        String b = date_out.format(jdcTanggalSampai.getDate());
        String periode = "Periode " + a + " s.d " + b;
        String dari = date_in.format(jdcTanggalDari.getDate());
        String sampai = date_in.format(jdcTanggalSampai.getDate());
        try {
            HashMap p = new HashMap();
            p.put("dari", dari);
            p.put("sampai", sampai);
            p.put("periode", periode);
            JasperPrint cetak = JasperFillManager.fillReport(laporan, p, connection.GetConnection());
            this.dispose();
            JasperViewer.viewReport(cetak, false);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jbTampil = new javax.swing.JButton();
        jdcTanggalDari = new com.toedter.calendar.JDateChooser();
        jdcTanggalSampai = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jbHome = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Data Hasil Produksi");
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(92, 76, 61));
        jLabel1.setText("Periode");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 80, 25));

        jbTampil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_tampil.png"))); // NOI18N
        jbTampil.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_over_tampil.png"))); // NOI18N
        jbTampil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbTampilActionPerformed(evt);
            }
        });
        jPanel1.add(jbTampil, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 100, 27));

        jdcTanggalDari.setBackground(new java.awt.Color(255, 255, 255));
        jdcTanggalDari.setDateFormatString("dd/MM/yyyy");
        jdcTanggalDari.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jdcTanggalDariPropertyChange(evt);
            }
        });
        jPanel1.add(jdcTanggalDari, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 170, 25));

        jdcTanggalSampai.setBackground(new java.awt.Color(255, 255, 255));
        jdcTanggalSampai.setDateFormatString("dd/MM/yyyy");
        jdcTanggalSampai.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jdcTanggalSampaiPropertyChange(evt);
            }
        });
        jPanel1.add(jdcTanggalSampai, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 170, 25));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(92, 76, 61));
        jLabel3.setText("Sampai");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 80, 25));

        jbHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_kembali.png"))); // NOI18N
        jbHome.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_over_kembali.png"))); // NOI18N
        jbHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbHomeActionPerformed(evt);
            }
        });
        jPanel1.add(jbHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(1155, 10, 27, 27));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(92, 76, 61));
        jLabel4.setText("Dari");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 80, 25));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/frame/frm_lap_penjualan.png"))); // NOI18N
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

    private void jbTampilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbTampilActionPerformed
        // TODO add your handling code here:
        tampil_laporan();
    }//GEN-LAST:event_jbTampilActionPerformed

    private void jdcTanggalDariPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jdcTanggalDariPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jdcTanggalDariPropertyChange

    private void jdcTanggalSampaiPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jdcTanggalSampaiPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jdcTanggalSampaiPropertyChange

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
            java.util.logging.Logger.getLogger(jd_laporanPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jd_laporanPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jd_laporanPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jd_laporanPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jd_laporanPenjualan dialog = new jd_laporanPenjualan(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jbTampil;
    private com.toedter.calendar.JDateChooser jdcTanggalDari;
    private com.toedter.calendar.JDateChooser jdcTanggalSampai;
    // End of variables declaration//GEN-END:variables
}
