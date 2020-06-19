package form;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import tool.connection;

public class jd_dataDaging extends javax.swing.JDialog {

    String status = "kosong";

    public jd_dataDaging(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent);
        jtfCari.setText(null);
        show_data();
        manage_form();
    }

    public void show_data() {
        int nomor = 1;
        double beli = 0;
        double jual = 0;
        DefaultTableModel table = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        DefaultTableCellRenderer tcr_center = new DefaultTableCellRenderer();
        DefaultTableCellRenderer tcr_right = new DefaultTableCellRenderer();
        tcr_center.setHorizontalAlignment(JLabel.CENTER);
        tcr_right.setHorizontalAlignment(JLabel.RIGHT);
        table.addColumn("No.");
        table.addColumn("Kode");
        table.addColumn("Jenis");
        try {
            Statement s = (Statement) connection.GetConnection().createStatement();
            Statement s1 = (Statement) connection.GetConnection().createStatement();
            ResultSet r = s.executeQuery("select * from daging order by jenis asc limit 100");
            while (r.next()) {
                table.addRow(new Object[]{
                    nomor,
                    r.getString("kd_daging"),
                    r.getString("jenis")
                });
                nomor++;
            }
            jtData.setModel(table);
            jtData.getColumnModel().getColumn(0).setPreferredWidth(20);
            jtData.getColumnModel().getColumn(1).setPreferredWidth(40);
            jtData.getColumnModel().getColumn(2).setPreferredWidth(680);
            jtData.getColumnModel().getColumn(0).setCellRenderer(tcr_center);
            jtData.getColumnModel().getColumn(1).setCellRenderer(tcr_center);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void show_data(String cari) {
        int nomor = 1;
        double beli = 0;
        double jual = 0;
        DefaultTableModel table = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        DefaultTableCellRenderer tcr_center = new DefaultTableCellRenderer();
        DefaultTableCellRenderer tcr_right = new DefaultTableCellRenderer();
        tcr_center.setHorizontalAlignment(JLabel.CENTER);
        tcr_right.setHorizontalAlignment(JLabel.RIGHT);
        table.addColumn("No.");
        table.addColumn("Kode");
        table.addColumn("Jenis");
        try {
            Statement s = (Statement) connection.GetConnection().createStatement();
            Statement s1 = (Statement) connection.GetConnection().createStatement();
            ResultSet r = s.executeQuery("select * from daging "
                    + "where jenis like'%" + cari + "%' order by jenis asc limit 100");
            while (r.next()) {
                table.addRow(new Object[]{
                    nomor,
                    r.getString("kd_daging"),
                    r.getString("jenis")
                });
                nomor++;
            }
            jtData.setModel(table);
            jtData.getColumnModel().getColumn(0).setPreferredWidth(20);
            jtData.getColumnModel().getColumn(1).setPreferredWidth(40);
            jtData.getColumnModel().getColumn(2).setPreferredWidth(680);
            jtData.getColumnModel().getColumn(0).setCellRenderer(tcr_center);
            jtData.getColumnModel().getColumn(1).setCellRenderer(tcr_center);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void manage_form() {
        if (status.equals("edit")) {
            jbBatal.setEnabled(true);
            jbHapus.setEnabled(true);
            jbSimpan.setEnabled(true);
            jbTambah.setEnabled(true);
        } else if (status.equals("tambah")) {
            jbBatal.setEnabled(true);
            jbHapus.setEnabled(false);
            jbSimpan.setEnabled(true);
            jbTambah.setEnabled(false);
        } else {
            jbBatal.setEnabled(false);
            jbHapus.setEnabled(false);
            jbSimpan.setEnabled(false);
            jbTambah.setEnabled(true);
        }

        if (status.equals("edit") || status.equals("tambah")) {
            jtfJenis.setEnabled(true);
        } else {
            jtfJenis.setEnabled(false);
        }

        jtfKode.setEnabled(false);

        jtfJenis.setText(null);
        jtfCari.setText(null);
        jtfJenis.setText(null);

        populate_code();
        show_data();
    }

    public void populate_code() {
        String code = null;
        int n = 1;
        boolean found = true;
        while (found) {
            if (n < 10) {
                code = "D0" + n;
            } else {
                code = "D" + n;
            }
            try {
                Statement s = (Statement) connection.GetConnection().createStatement();
                Statement s1 = (Statement) connection.GetConnection().createStatement();
                ResultSet r = s.executeQuery("select kd_daging from daging "
                        + "where kd_daging='" + code + "'");
                if (r.next()) {
                    n++;
                } else {
                    jtfKode.setText(code);
                    found = false;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void save_data() {
        String in_kode = jtfKode.getText();
        String in_jenis = jtfJenis.getText();
        if (in_jenis.equals("")) {
            JOptionPane.showMessageDialog(null, "Lengkapi Inputan Data...", "Simpan", 1);
        } else {
            try {
                Statement s = (Statement) connection.GetConnection().createStatement();
                Statement s1 = (Statement) connection.GetConnection().createStatement();
                if (status.equals("edit")) {
                    s.executeUpdate("update daging set jenis='" + in_jenis + "' "
                            + "where kd_daging='" + in_kode + "'");
                    JOptionPane.showMessageDialog(null, "Perubahan data disimpan", "Simpan", 1);
                } else {
                    s.executeUpdate("insert into daging values('" + in_kode + "','" + in_jenis + "')");
                    JOptionPane.showMessageDialog(null, "Data disimpan", "Simpan", 1);
                }
                status = "kosong";
                manage_form();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void delete_data() {
        String in_kode = jtfKode.getText();
        int confirm = JOptionPane.showConfirmDialog(this, "Hapus data terpilih?", "Hapus", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Statement s = (Statement) connection.GetConnection().createStatement();
                Statement s1 = (Statement) connection.GetConnection().createStatement();
                s.executeUpdate("delete from daging where kd_daging = '" + in_kode + "'");
                JOptionPane.showMessageDialog(null, "Data dihapus", "Hapus", 1);
                status = "kosong";
                manage_form();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtData = new javax.swing.JTable();
        jtfCari = new javax.swing.JTextField();
        jtfJenis = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jbBatal = new javax.swing.JButton();
        jbTambah = new javax.swing.JButton();
        jbSimpan = new javax.swing.JButton();
        jbHapus = new javax.swing.JButton();
        jbHome = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jtfKode = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Data Hasil Produksi");
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        jtData.setAutoCreateRowSorter(true);
        jtData.setModel(new javax.swing.table.DefaultTableModel(
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
        jtData.setGridColor(new java.awt.Color(246, 246, 246));
        jtData.setRowHeight(25);
        jtData.getTableHeader().setResizingAllowed(false);
        jtData.getTableHeader().setReorderingAllowed(false);
        jtData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtDataMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtData);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 1140, 350));

        jtfCari.setForeground(new java.awt.Color(92, 76, 61));
        jtfCari.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jtfCari.setDisabledTextColor(new java.awt.Color(92, 76, 61));
        jtfCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfCariKeyReleased(evt);
            }
        });
        jPanel1.add(jtfCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 227, 470, 25));

        jtfJenis.setForeground(new java.awt.Color(92, 76, 61));
        jtfJenis.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jtfJenis.setDisabledTextColor(new java.awt.Color(92, 76, 61));
        jPanel1.add(jtfJenis, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 250, 25));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(92, 76, 61));
        jLabel8.setText("Kode");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 110, 25));

        jbBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_batal.png"))); // NOI18N
        jbBatal.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_over_batal.png"))); // NOI18N
        jbBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBatalActionPerformed(evt);
            }
        });
        jPanel1.add(jbBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 220, 100, 27));

        jbTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_tambah.png"))); // NOI18N
        jbTambah.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_over_tambah.png"))); // NOI18N
        jbTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbTambahActionPerformed(evt);
            }
        });
        jPanel1.add(jbTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 100, 27));

        jbSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_simpan.png"))); // NOI18N
        jbSimpan.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_over_simpan.png"))); // NOI18N
        jbSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSimpanActionPerformed(evt);
            }
        });
        jPanel1.add(jbSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, 100, 27));

        jbHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_hapus.png"))); // NOI18N
        jbHapus.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_over_hapus.png"))); // NOI18N
        jbHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbHapusActionPerformed(evt);
            }
        });
        jPanel1.add(jbHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 220, 100, 27));

        jbHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_kembali.png"))); // NOI18N
        jbHome.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_over_kembali.png"))); // NOI18N
        jbHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbHomeActionPerformed(evt);
            }
        });
        jPanel1.add(jbHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(1155, 10, 27, 27));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(92, 76, 61));
        jLabel13.setText("Jenis");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 110, 25));

        jtfKode.setForeground(new java.awt.Color(92, 76, 61));
        jtfKode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jtfKode.setDisabledTextColor(new java.awt.Color(92, 76, 61));
        jtfKode.setEnabled(false);
        jPanel1.add(jtfKode, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 70, 25));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/frame/frm_daging.png"))); // NOI18N
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

    private void jtfCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCariKeyReleased
        // TODO add your handling code here:
        show_data(jtfCari.getText());
    }//GEN-LAST:event_jtfCariKeyReleased

    private void jtDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtDataMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int i = jtData.getSelectedRow();
            if (i == -1) {
                return;
            }

            status = "edit";
            manage_form();

            String chossen_id = (String) jtData.getValueAt(i, 1);
            try {
                Statement s = (Statement) connection.GetConnection().createStatement();
                Statement s1 = (Statement) connection.GetConnection().createStatement();
                ResultSet r = s.executeQuery("select * from daging "
                        + "where kd_daging = '" + chossen_id + "'");
                if (r.next()) {
                    jtfKode.setText(r.getString("kd_daging"));
                    jtfJenis.setText(r.getString("jenis"));
                }
                ResultSet r1 = s.executeQuery("select * from detail_penjualan where kd_daging='"+chossen_id+"'");
                if(r1.next()){
                    jbHapus.setEnabled(false);
                }else{
                    jbHapus.setEnabled(true);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_jtDataMouseClicked

    private void jbTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbTambahActionPerformed
        // TODO add your handling code here:
        status = "tambah";
        manage_form();
    }//GEN-LAST:event_jbTambahActionPerformed

    private void jbSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSimpanActionPerformed
        // TODO add your handling code here:
        save_data();
    }//GEN-LAST:event_jbSimpanActionPerformed

    private void jbHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbHapusActionPerformed
        // TODO add your handling code here:
        delete_data();
    }//GEN-LAST:event_jbHapusActionPerformed

    private void jbBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBatalActionPerformed
        // TODO add your handling code here:
        status = "kosong";
        manage_form();
    }//GEN-LAST:event_jbBatalActionPerformed

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
            java.util.logging.Logger.getLogger(jd_dataDaging.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jd_dataDaging.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jd_dataDaging.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jd_dataDaging.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jd_dataDaging dialog = new jd_dataDaging(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbBatal;
    private javax.swing.JButton jbHapus;
    private javax.swing.JButton jbHome;
    private javax.swing.JButton jbSimpan;
    private javax.swing.JButton jbTambah;
    private javax.swing.JTable jtData;
    private javax.swing.JTextField jtfCari;
    private javax.swing.JTextField jtfJenis;
    private javax.swing.JTextField jtfKode;
    // End of variables declaration//GEN-END:variables
}
