package form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import tool.connection;

public class jd_prosesAnalisa extends javax.swing.JDialog {

    Date now = new Date();
    SimpleDateFormat date_in = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat date_out = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    String per_next = "";
    String dari = "";
    String sampai = "";

    public jd_prosesAnalisa(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        show_data();
        show_grafik();
        populate_daging();
        jtpHasil.setBackground(new Color(0, 0, 0, 0));
        jdcDari.setDate(now);
        jdcSampai.setDate(now);
        this.setLocationRelativeTo(parent);
    }

    public void show_data() {
        int nomor = 1;
        DefaultTableModel tbl = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        DefaultTableCellRenderer tcr_center = new DefaultTableCellRenderer();
        DefaultTableCellRenderer tcr_right = new DefaultTableCellRenderer();
        tcr_center.setHorizontalAlignment(JLabel.CENTER);
        tcr_right.setHorizontalAlignment(JLabel.RIGHT);
        tbl.addColumn("No.");
        tbl.addColumn("Periode");
        tbl.addColumn("Penjualan");
        tbl.addColumn("Peramalan");
        tbl.addColumn("MAD");
        jtData.setModel(tbl);
        jtData.getColumnModel().getColumn(0).setPreferredWidth(20);
        jtData.getColumnModel().getColumn(1).setPreferredWidth(100);
        jtData.getColumnModel().getColumn(2).setPreferredWidth(50);
        jtData.getColumnModel().getColumn(3).setPreferredWidth(50);
        jtData.getColumnModel().getColumn(4).setPreferredWidth(40);
        jtData.getColumnModel().getColumn(0).setCellRenderer(tcr_center);
        jtData.getColumnModel().getColumn(2).setCellRenderer(tcr_right);
        jtData.getColumnModel().getColumn(3).setCellRenderer(tcr_right);
        jtData.getColumnModel().getColumn(4).setCellRenderer(tcr_right);
    }

    public void show_grafik() {
        String batas = (String) jcbJenis.getSelectedItem();
        String daging = ((String) jcbDaging.getSelectedItem()).substring(5, ((String) jcbDaging.getSelectedItem()).length());
        CategoryPlot plot = new CategoryPlot();

        plot.setDomainAxis(new CategoryAxis("Periode"));
        plot.setRangeAxis(new NumberAxis("Jumlah"));

        JFreeChart chart = new JFreeChart(plot);
        chart.setTitle("Grafik Analisa Jumlah Penjualan Daging");

        plot.setOrientation(PlotOrientation.HORIZONTAL);
        plot.setBackgroundPaint(new Color(254, 242, 215));
        chart.setBackgroundPaint(new Color(254, 242, 215));
        ChartPanel CP = new ChartPanel(chart);
        jp_chart.removeAll();
        jp_chart.setLayout(new java.awt.BorderLayout());
        jp_chart.add(CP, BorderLayout.CENTER);
        jp_chart.validate();
    }

    public void show_hasil() {
        int nomor = 1;
        DefaultTableModel tbl = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        DefaultTableCellRenderer tcr_center = new DefaultTableCellRenderer();
        DefaultTableCellRenderer tcr_right = new DefaultTableCellRenderer();
        tcr_center.setHorizontalAlignment(JLabel.CENTER);
        tcr_right.setHorizontalAlignment(JLabel.RIGHT);
        tbl.addColumn("No.");
        tbl.addColumn("Periode");
        tbl.addColumn("Penjualan");
        tbl.addColumn("Peramalan");
        tbl.addColumn("Akurasi (%)");
        try {
            Statement s = (Statement) connection.GetConnection().createStatement();
            ResultSet r = s.executeQuery("select * from prediksi order by kd_prediksi asc");
            while (r.next()) {
                double akurat = 0;
                if(r.getInt("kd_prediksi") > 1){
                    if(r.getDouble("jual") > 0 && r.getDouble("mad") > 0 ){
                        akurat = 100 - (r.getDouble("jual")/r.getDouble("mad"));
                        akurat = Math.round(akurat * 100.0) / 100.0;
                    }
                }
                tbl.addRow(new Object[]{
                    nomor,
                    r.getString("periode"),
                    r.getString("jual"),
                    r.getString("ramal"),
                    akurat
                });
                nomor++;
            }
            jtData.setModel(tbl);
            jtData.getColumnModel().getColumn(0).setPreferredWidth(20);
            jtData.getColumnModel().getColumn(1).setPreferredWidth(80);
            jtData.getColumnModel().getColumn(2).setPreferredWidth(60);
            jtData.getColumnModel().getColumn(3).setPreferredWidth(60);
            jtData.getColumnModel().getColumn(4).setPreferredWidth(60);
            jtData.getColumnModel().getColumn(0).setCellRenderer(tcr_center);
            jtData.getColumnModel().getColumn(2).setCellRenderer(tcr_right);
            jtData.getColumnModel().getColumn(3).setCellRenderer(tcr_right);
            jtData.getColumnModel().getColumn(4).setCellRenderer(tcr_right);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private DefaultCategoryDataset createDataset() {
        int batas = 0;
        int offset = 0;

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String series1 = "Penjualan";
        String series2 = "Peramalan";
        String series3 = "MAD";

        try {
            Statement s = (Statement) connection.GetConnection().createStatement();
            ResultSet rb = s.executeQuery("select count(*) as off from prediksi");
            if (rb.next()) {
                if (rb.getInt("off") > 13) {
                    batas = rb.getInt("off") - 13;
                }
                offset = rb.getInt("off");
            }
            ResultSet r = s.executeQuery("select * from prediksi order by kd_prediksi asc limit " + batas + "," + offset + "");
            while (r.next()) {
                dataset.addValue(r.getDouble("jual"), series1, r.getString("periode"));
                dataset.addValue(r.getDouble("ramal"), series2, r.getString("periode"));
                dataset.addValue(r.getDouble("mad"), series3, r.getString("periode"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return dataset;
    }

    public void show_chart() {
        String batas = (String) jcbJenis.getSelectedItem();
        String daging = ((String) jcbDaging.getSelectedItem()).substring(5, ((String) jcbDaging.getSelectedItem()).length());
        CategoryPlot plot = new CategoryPlot();

        CategoryItemRenderer line1 = new LineAndShapeRenderer();
        plot.setDataset(0, createDataset());
        plot.setRenderer(0, line1);

        plot.setDomainAxis(new CategoryAxis("Periode"));
        plot.setRangeAxis(new NumberAxis("Jumlah"));

        JFreeChart chart = new JFreeChart(plot);
        chart.setTitle("Grafik Analisa Jumlah Penjualan " + batas + " Daging " + daging);

        plot.setOrientation(PlotOrientation.HORIZONTAL);
        plot.setBackgroundPaint(new Color(254, 242, 215));
        chart.setBackgroundPaint(new Color(254, 242, 215));
        ChartPanel CP = new ChartPanel(chart);
        jp_chart.removeAll();
        jp_chart.setLayout(new java.awt.BorderLayout());
        jp_chart.add(CP, BorderLayout.CENTER);
        jp_chart.validate();
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
            if (rb.next()) {
                batas = rb.getInt("jumlah");
            }
            while (n <= batas) {
                bagi_per = 0;
                ramal = 0;
                mad = 0;
                ResultSet r1 = s.executeQuery("select sum(kd_prediksi) as bagi, sum(kd_prediksi * jual) as ramal "
                        + "from prediksi where kd_prediksi <= " + n + "");
                if (r1.next()) {
                    bagi_per = r1.getDouble("bagi");
                    ramal = r1.getDouble("ramal") / bagi_per;
                }
                ramal = Math.round(ramal * 100.0) / 100.0;
                s.executeUpdate("update prediksi set ramal='" + ramal + "' where kd_prediksi='" + n + "'");
                ResultSet r2 = s.executeQuery("select sum(abs(jual -ramal)) as mad "
                        + "from prediksi where kd_prediksi <= " + n + "");
                if (r2.next()) {
                    mad = r2.getDouble("mad") / n;
                }
                mad = Math.round(mad * 100.0) / 100.0;
                s.executeUpdate("update prediksi set mad='" + mad + "' where kd_prediksi='" + n + "'");
                n++;
            }
            String isi_hasil = "";
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
            jtpHasil.setText("<html><b><p align='center' style='font-family:Tahoma; font-size:12pt;'>" + isi_hasil + "</p></b></html>");
        } catch (Exception e) {
            System.out.println(e);
        }
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
        //int th_sk = Integer.parseInt(dyear.format(now));
        //int bl_sk = Integer.parseInt(dmonth.format(now));
        int th_sk = Integer.parseInt(dyear.format(jdcSampai.getDate()));
        int bl_sk = Integer.parseInt(dmonth.format(jdcSampai.getDate()));
        int th_d = 0;
        int bl_d = 0;
        String bl_df = "";
        boolean count = true;
        String bulan = "";
        try {
            Statement s = (Statement) connection.GetConnection().createStatement();
            Statement s1 = (Statement) connection.GetConnection().createStatement();
            s.executeUpdate("truncate table prediksi");
            /*
            ResultSet r = s.executeQuery("select tanggal from penjualan order by tanggal asc limit 1");
            if (r.next()) {
                th_d = Integer.parseInt(dyear.format(r.getDate("tanggal")));
                bl_d = Integer.parseInt(dmonth.format(r.getDate("tanggal")));
            }*/
            th_d = Integer.parseInt(dyear.format(jdcDari.getDate()));
            bl_d = Integer.parseInt(dmonth.format(jdcDari.getDate()));
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
        //int th_sk = Integer.parseInt(dyear.format(now));
        int th_sk = Integer.parseInt(dyear.format(jdcSampai.getDate()));
        int th_d = 0;
        try {
            Statement s = (Statement) connection.GetConnection().createStatement();
            Statement s1 = (Statement) connection.GetConnection().createStatement();
            s.executeUpdate("truncate table prediksi");
            /*
            ResultSet r = s.executeQuery("select tanggal from penjualan order by tanggal asc limit 1");
            if (r.next()) {
                th_d = Integer.parseInt(dyear.format(r.getDate("tanggal")));
            }*/
            th_d = Integer.parseInt(dyear.format(jdcDari.getDate()));
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jbTampil = new javax.swing.JButton();
        jbHome = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jcbDaging = new javax.swing.JComboBox();
        jlAnalisa = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtData = new javax.swing.JTable();
        jp_chart = new javax.swing.JPanel();
        jtpHasil = new javax.swing.JTextPane();
        jLabel10 = new javax.swing.JLabel();
        jdcDari = new com.toedter.calendar.JDateChooser();
        jdcSampai = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jcbJenis = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Data Hasil Produksi");
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jbTampil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_proses.png"))); // NOI18N
        jbTampil.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/button/btn_over_proses.png"))); // NOI18N
        jbTampil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbTampilActionPerformed(evt);
            }
        });
        jPanel1.add(jbTampil, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 90, 100, 27));

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
        jPanel1.add(jcbDaging, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 170, 25));

        jlAnalisa.setBackground(new java.awt.Color(92, 76, 61));
        jlAnalisa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlAnalisa.setForeground(new java.awt.Color(92, 76, 61));
        jlAnalisa.setText("Hasil Analisa :");
        jPanel1.add(jlAnalisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 70, 270, 25));

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
        jScrollPane1.setViewportView(jtData);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 380, 480));

        jp_chart.setBackground(new java.awt.Color(255, 255, 255));
        jp_chart.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jp_chart, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 70, 450, 560));

        jtpHasil.setEditable(false);
        jtpHasil.setBackground(new java.awt.Color(254, 242, 215));
        jtpHasil.setContentType("text/html"); // NOI18N
        jtpHasil.setForeground(new java.awt.Color(92, 76, 61));
        jtpHasil.setDisabledTextColor(new java.awt.Color(92, 76, 61));
        jtpHasil.setEnabled(false);
        jtpHasil.setOpaque(false);
        jPanel1.add(jtpHasil, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 100, 270, 490));

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

        jdcSampai.setBackground(new java.awt.Color(255, 255, 255));
        jdcSampai.setDateFormatString("dd/MM/yyyy");
        jdcSampai.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jdcSampaiPropertyChange(evt);
            }
        });
        jPanel1.add(jdcSampai, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 120, 25));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(92, 76, 61));
        jLabel11.setText("Periode");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 90, 25));

        jLabel2.setBackground(new java.awt.Color(92, 76, 61));
        jLabel2.setForeground(new java.awt.Color(92, 76, 61));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/form/gambar/frame/frm_analisa.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 650));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(92, 76, 61));
        jLabel9.setText("Jenis Analisa");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 90, 25));

        jcbJenis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Harian", "Mingguan", "Bulanan", "Tahunan" }));
        jPanel1.add(jcbJenis, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 170, 25));

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
        analisa();
        show_hasil();
        show_chart();
        /*if (jcbJenis.getSelectedIndex() == 0) {
            int konfirmasi = JOptionPane.showConfirmDialog(this, "<html><body><p style='width: 200px;'> "
                    + "Proses Analisa membutuhkan waktu yang cukup lama jika anda menggunakan Jenis Analisa Harian. "
                    + "Tetap Proses Analisa?</p></body></html>", "Analisa", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                rekap_hari();
                analisa();
                show_hasil();
                show_chart();
            }
        } else if (jcbJenis.getSelectedIndex() == 1) {
            rekap_minggu();
            analisa();
            show_hasil();
            show_chart();
        } else if (jcbJenis.getSelectedIndex() == 2) {
            rekap_bulan();
            analisa();
            show_hasil();
            show_chart();
        } else {
            rekap_tahun();
            analisa();
            show_hasil();
            show_chart();
        }*/
    }//GEN-LAST:event_jbTampilActionPerformed

    private void jbHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbHomeActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jbHomeActionPerformed

    private void jdcDariPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jdcDariPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jdcDariPropertyChange

    private void jdcSampaiPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jdcSampaiPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jdcSampaiPropertyChange

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
            java.util.logging.Logger.getLogger(jd_prosesAnalisa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jd_prosesAnalisa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jd_prosesAnalisa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jd_prosesAnalisa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jd_prosesAnalisa dialog = new jd_prosesAnalisa(new javax.swing.JFrame(), true);
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbHome;
    private javax.swing.JButton jbTampil;
    private javax.swing.JComboBox jcbDaging;
    private javax.swing.JComboBox jcbJenis;
    private com.toedter.calendar.JDateChooser jdcDari;
    private com.toedter.calendar.JDateChooser jdcSampai;
    private com.toedter.calendar.JDateChooser jdcTanggal;
    private com.toedter.calendar.JDateChooser jdcTanggal1;
    private javax.swing.JLabel jlAnalisa;
    private javax.swing.JPanel jp_chart;
    private javax.swing.JTable jtData;
    private javax.swing.JTextPane jtpHasil;
    // End of variables declaration//GEN-END:variables
}
