package form;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import tool.connection;

public class jd_laporanPeramalan extends javax.swing.JDialog {

    Date now = new Date();
    SimpleDateFormat date_in = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat date_out = new SimpleDateFormat("dd/MM/yyyy");
    String isi_hasil = "";
    int limit = 0;
    int offset = 0;
    Calendar cal = Calendar.getInstance();

    public jd_laporanPeramalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        populate_daging();
        jdcDari.setDate(now);
        jdcSampai.setDate(now);
        this.setLocationRelativeTo(parent);
    }
    
    public void populate_daging() {
        jcbDaging.removeAllItems();;
        try {
            Statement s = (Statement) connection.GetConnection().createStatement();
            ResultSet r = s.executeQuery("select * from daging order by kd_daging asc");
            while (r.next()) {
                jcbDaging.addItem(r.getString("kd_daging") + " - " + r.getString("jenis"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void rekap_hari() {
        SimpleDateFormat f_tgl = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat f_out = new SimpleDateFormat("dd/MM/yyyy");
        String daging = ((String) jcbDaging.getSelectedItem()).substring(0, 3);
        Date batas = new Date();
        Date mulai = new Date();
        try {
            //batas = f_tgl.parse(f_tgl.format(now));
            Statement s = (Statement) connection.GetConnection().createStatement();
            Statement s1 = (Statement) connection.GetConnection().createStatement();
            s.executeUpdate("truncate table prediksi");
            /*
            ResultSet r = s.executeQuery("select tanggal from penjualan order by tanggal asc limit 1");
            if (r.next()) {
                mulai = f_tgl.parse(r.getString("tanggal"));
                cal.setTime(mulai);
            }*/
            mulai = f_tgl.parse(f_tgl.format(jdcDari.getDate()));
            batas = f_tgl.parse(f_tgl.format(jdcSampai.getDate()));
            cal.setTime(mulai);
            while (mulai.compareTo(batas) < 0) {
                double jumlah = 0;
                mulai = f_tgl.parse(f_tgl.format(cal.getTime()));
                ResultSet r1 = s.executeQuery("select kd_jual from penjualan where tanggal='" + f_tgl.format(mulai) + "'");
                while (r1.next()) {
                    ResultSet r2 = s1.executeQuery("select jumlah from detail_penjualan where "
                            + "kd_jual='" + r1.getString("kd_jual") + "' and kd_daging='" + daging + "'");
                    if (r2.next()) {
                        jumlah += r2.getDouble("jumlah");
                    }
                }
                s.executeUpdate("insert into prediksi values(null,'" + jumlah + "','0','0','" + f_out.format(mulai) + "')");
                cal.add(Calendar.DATE, 1);
            }
            mulai = f_tgl.parse(f_tgl.format(now));
            cal.setTime(mulai);
            cal.add(Calendar.DATE, 1);
            mulai = f_tgl.parse(f_tgl.format(cal.getTime()));
            s.executeUpdate("insert into prediksi values(null,'0','0','0','" + f_out.format(mulai) + "')");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void rekap_minggu() {
        int mg = 1;
        SimpleDateFormat f_tgl = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat f_out = new SimpleDateFormat("dd/MM/yyyy");
        String daging = ((String) jcbDaging.getSelectedItem()).substring(0, 3);
        Date batas = new Date();
        Date mulai = new Date();
        Date sampai = new Date();
        try {
            batas = f_tgl.parse(f_tgl.format(now));
            Statement s = (Statement) connection.GetConnection().createStatement();
            Statement s1 = (Statement) connection.GetConnection().createStatement();
            s.executeUpdate("truncate table prediksi");
            ResultSet r = s.executeQuery("select tanggal from penjualan order by tanggal asc limit 1");
            if (r.next()) {
                mulai = f_tgl.parse(r.getString("tanggal"));
                cal.setTime(mulai);
            }
            while (mulai.compareTo(batas) < 0) {
                double jumlah = 0;
                mulai = f_tgl.parse(f_tgl.format(cal.getTime()));
                cal.add(Calendar.DATE, 7);
                sampai = f_tgl.parse(f_tgl.format(cal.getTime()));
                ResultSet r1 = s.executeQuery("select kd_jual from penjualan where tanggal "
                        + "between '" + f_tgl.format(mulai) + "' and '" + f_tgl.format(sampai) + "'");
                while (r1.next()) {
                    ResultSet r2 = s1.executeQuery("select jumlah from detail_penjualan where "
                            + "kd_jual='" + r1.getString("kd_jual") + "' and kd_daging='" + daging + "'");
                    if (r2.next()) {
                        jumlah += r2.getDouble("jumlah");
                    }
                }
                s.executeUpdate("insert into prediksi values(null,'" + jumlah + "','0','0','" + "Minggu ke-" + mg + "')");
                mg++;
            }
            s.executeUpdate("insert into prediksi values(null,'0','0','0','" + "Minggu ke-" + mg + "')");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void rekap_bulan() {
        SimpleDateFormat dyear = new SimpleDateFormat("yyyy");
        SimpleDateFormat dmonth = new SimpleDateFormat("MM");
        SimpleDateFormat dfm = new SimpleDateFormat("MMMM yyyy");
        SimpleDateFormat f_tgl = new SimpleDateFormat("yyyy-MM-dd");
        String daging = ((String) jcbDaging.getSelectedItem()).substring(0, 3);
        int th_sk = Integer.parseInt(dyear.format(now));
        int bl_sk = Integer.parseInt(dmonth.format(now));
        int th_d = 0;
        int bl_d = 0;
        String bl_df = "";
        boolean count = true;
        String bulan = "";
        try {
            Statement s = (Statement) connection.GetConnection().createStatement();
            Statement s1 = (Statement) connection.GetConnection().createStatement();
            s.executeUpdate("truncate table prediksi");
            ResultSet r = s.executeQuery("select tanggal from penjualan order by tanggal asc limit 1");
            if (r.next()) {
                th_d = Integer.parseInt(dyear.format(r.getDate("tanggal")));
                bl_d = Integer.parseInt(dmonth.format(r.getDate("tanggal")));
            }
            while (th_d <= th_sk) {
                while (bl_d <= 12) {
                    double jumlah = 0;
                    if ((th_d == th_sk) && bl_d > bl_sk) {
                        count = false;
                    }
                    if (count) {
                        if (bl_d < 10) {
                            bulan = th_d + "-0" + bl_d;
                            bl_df = dfm.format(f_tgl.parse(th_d + "-0" + bl_d + "-01"));
                        } else {
                            bulan = th_d + "-" + bl_d;
                            bl_df = dfm.format(f_tgl.parse(th_d + "-" + bl_d + "-01"));
                        }
                        ResultSet r1 = s.executeQuery("select kd_jual from penjualan where tanggal like'" + bulan + "%'");
                        while (r1.next()) {
                            ResultSet r2 = s1.executeQuery("select jumlah from detail_penjualan where "
                                    + "kd_jual='" + r1.getString("kd_jual") + "' and kd_daging='" + daging + "'");
                            if (r2.next()) {
                                jumlah += r2.getDouble("jumlah");
                            }
                        }
                        s.executeUpdate("insert into prediksi values(null,'" + jumlah + "','0','0','" + bl_df + "')");
                    }
                    bl_d++;
                }
                bl_d = 1;
                th_d++;
            }
            bl_df = dfm.format(f_tgl.parse(th_d + "-" + bl_d + "-01"));
            s.executeUpdate("insert into prediksi values(null,'0','0','0','" + bl_df + "')");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void rekap_tahun() {
        SimpleDateFormat dyear = new SimpleDateFormat("yyyy");
        SimpleDateFormat dmonth = new SimpleDateFormat("MM");
        SimpleDateFormat dfm = new SimpleDateFormat("MMMM yyyy");
        SimpleDateFormat f_tgl = new SimpleDateFormat("yyyy-MM-dd");
        String daging = ((String) jcbDaging.getSelectedItem()).substring(0, 3);
        int th_sk = Integer.parseInt(dyear.format(now));
        int th_d = 0;
        try {
            Statement s = (Statement) connection.GetConnection().createStatement();
            Statement s1 = (Statement) connection.GetConnection().createStatement();
            s.executeUpdate("truncate table prediksi");
            ResultSet r = s.executeQuery("select tanggal from penjualan order by tanggal asc limit 1");
            if (r.next()) {
                th_d = Integer.parseInt(dyear.format(r.getDate("tanggal")));
            }
            while (th_d <= th_sk) {
                double jumlah = 0;
                ResultSet r1 = s.executeQuery("select kd_jual from penjualan where tanggal like'" + th_d + "%'");
                while (r1.next()) {
                    ResultSet r2 = s1.executeQuery("select jumlah from detail_penjualan where "
                            + "kd_jual='" + r1.getString("kd_jual") + "' and kd_daging='" + daging + "'");
                    if (r2.next()) {
                        jumlah += r2.getDouble("jumlah");
                    }
                }
                s.executeUpdate("insert into prediksi values(null,'" + jumlah + "','0','0','" + th_d + "')");
                th_d++;
            }
            s.executeUpdate("insert into prediksi values(null,'0','0','0','" + th_d + "')");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void analisa() {
        DecimalFormat f_angka = new DecimalFormat("#,##");
        double bagi_per = 0;
        double ramal = 0;
        double mad = 0;
        double bobot = 1;
        int batas = 0;
        int n = 1;
        try {
            Statement s = (Statement) connection.GetConnection().createStatement();
            Statement s1 = (Statement) connection.GetConnection().createStatement();
            Statement s2 = (Statement) connection.GetConnection().createStatement();
            ResultSet rb = s.executeQuery("select count(*) as jumlah from prediksi");
            if(rb.next()){
                batas = rb.getInt("jumlah");
            }
            while(n <= batas){
                bagi_per = 0;
                ramal = 0;
                mad = 0;
                ResultSet r1 = s.executeQuery("select sum(kd_prediksi) as bagi, sum(kd_prediksi * jual) as ramal "
                        + "from prediksi where kd_prediksi <= "+n+"");
                if(r1.next()){
                    bagi_per = r1.getDouble("bagi");
                    ramal = r1.getDouble("ramal")/bagi_per;
                }
                ramal = Math.round(ramal * 100.0) / 100.0;
                s.executeUpdate("update prediksi set ramal='"+ramal+"' where kd_prediksi='"+n+"'");
                ResultSet r2 = s.executeQuery("select sum(abs(jual -ramal)) as mad "
                        + "from prediksi where kd_prediksi <= "+n+"");
                if(r2.next()){
                    mad = r2.getDouble("mad")/n;
                }
                mad = Math.round(mad * 100.0) / 100.0;
                s.executeUpdate("update prediksi set mad='"+mad+"' where kd_prediksi='"+n+"'");
                n++;
            }
            ResultSet rh = s.executeQuery("select * from prediksi order by kd_prediksi desc limit 1");
            if (rh.next()) {
                ResultSet rr = s1.executeQuery("select avg(jual) as jual from prediksi");
                if(rr.next()){
                    double akurat = 100 - (rr.getDouble("jual")/rh.getDouble("mad"));
                    akurat = Math.round(akurat * 100.0) / 100.0;
                    isi_hasil = "Dapat disimpulkan bahwa, pada periode berikutnya (" + rh.getString("periode") + ") "
                            + "akan terjadi penjualan sekitar " + rh.getInt("ramal") + " Kg, dengan tingkat keakuratan "
                            + "sebesar "+akurat+"%. Hasil Analisa Peramalan menggunakan MAD (Mean Absolute Deviation) "
                            + "sebesar " + rh.getInt("mad")+", yang berarti bahwa hasil peramalan memiliki range "
                            + "antara "+(rh.getInt("ramal") - rh.getInt("mad"))+"Kg - "+(rh.getInt("ramal") + rh.getInt("mad"))+"Kg.";
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void tampil_laporan() {
        String laporan = "src/laporan/rptPeramalan.jasper";
        //String a = "";
        //String b = date_out.format(now);
        String a = date_out.format(jdcDari.getDate());
        String b = date_out.format(jdcSampai.getDate());
        String judul = "Laporan Hasil Analisa "+(String) jcbJenis.getSelectedItem();
        String daging = "Daging "+((String) jcbDaging.getSelectedItem()).substring(5, ((String) jcbDaging.getSelectedItem()).length());
        try {
            Statement s = (Statement)connection.GetConnection().createStatement();
            /*
            ResultSet r = s.executeQuery("select tanggal from penjualan order by tanggal asc limit 1");
            if(r.next()){
                a = date_out.format(r.getDate("tanggal"));
            }
            */
            ResultSet rb = s.executeQuery("select count(*) as off from prediksi");
            if (rb.next()) {
                if (rb.getInt("off") > 13) {
                    limit = rb.getInt("off") - 13;
                }
                offset = rb.getInt("off");
            }
            HashMap p = new HashMap();
            p.put("judul", judul);
            p.put("daging", daging);
            p.put("periode", "Periode " + a + " s.d " + b);
            p.put("hasil", isi_hasil);
            p.put("limit", limit);
            p.put("offset", offset);
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
        jbTampil = new javax.swing.JButton();
        jbHome = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jcbDaging = new javax.swing.JComboBox();
        jdcSampai = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jdcDari = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jcbJenis = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Data Hasil Produksi");
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jbTampil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_tampil.png"))); // NOI18N
        jbTampil.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_over_tampil.png"))); // NOI18N
        jbTampil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbTampilActionPerformed(evt);
            }
        });
        jPanel1.add(jbTampil, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 100, 27));

        jbHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_kembali.png"))); // NOI18N
        jbHome.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_over_kembali.png"))); // NOI18N
        jbHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbHomeActionPerformed(evt);
            }
        });
        jPanel1.add(jbHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(1155, 10, 27, 27));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(92, 76, 61));
        jLabel8.setText("Jenis Daging");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 90, 25));

        jcbDaging.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Harian", "Mingguan", "Bulanan", "Tahunan" }));
        jPanel1.add(jcbDaging, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 280, 25));

        jdcSampai.setBackground(new java.awt.Color(255, 255, 255));
        jdcSampai.setDateFormatString("dd/MM/yyyy");
        jdcSampai.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jdcSampaiPropertyChange(evt);
            }
        });
        jPanel1.add(jdcSampai, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 120, 25));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(92, 76, 61));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("s.d");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, 40, 25));

        jdcDari.setBackground(new java.awt.Color(255, 255, 255));
        jdcDari.setDateFormatString("dd/MM/yyyy");
        jdcDari.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jdcDariPropertyChange(evt);
            }
        });
        jPanel1.add(jdcDari, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 120, 25));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(92, 76, 61));
        jLabel11.setText("Periode");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 90, 25));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/frame/frm_lap_prediksi.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 650));

        jcbJenis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Harian", "Mingguan", "Bulanan", "Tahunan" }));
        jPanel1.add(jcbJenis, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 170, 25));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(92, 76, 61));
        jLabel9.setText("Jenis Analisa");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 90, 25));

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
        rekap_hari();
        /*if (jcbJenis.getSelectedIndex() == 0) {
            rekap_hari();
        } else if (jcbJenis.getSelectedIndex() == 1) {
            rekap_minggu();
        } else if (jcbJenis.getSelectedIndex() == 2) {
            rekap_bulan();
        } else {
            rekap_tahun();
        }*/
        analisa();
        tampil_laporan();
    }//GEN-LAST:event_jbTampilActionPerformed

    private void jbHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbHomeActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jbHomeActionPerformed

    private void jdcSampaiPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jdcSampaiPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jdcSampaiPropertyChange

    private void jdcDariPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jdcDariPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jdcDariPropertyChange

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
            java.util.logging.Logger.getLogger(jd_laporanPeramalan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jd_laporanPeramalan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jd_laporanPeramalan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jd_laporanPeramalan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jd_laporanPeramalan dialog = new jd_laporanPeramalan(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbHome;
    private javax.swing.JButton jbTampil;
    private javax.swing.JComboBox jcbDaging;
    private javax.swing.JComboBox jcbJenis;
    private com.toedter.calendar.JDateChooser jdcDari;
    private com.toedter.calendar.JDateChooser jdcSampai;
    // End of variables declaration//GEN-END:variables
}
