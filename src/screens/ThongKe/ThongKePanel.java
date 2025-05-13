package screens.ThongKe;

import DTO.sanPhamThongKeDTO;
import DTO.TopKhachHangDTO;
import DAO.ThongKeDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Calendar;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ThongKePanel extends JPanel {
    private final ThongKeDAO thongKeDAO = new ThongKeDAO();
    private DefaultTableModel tableModel;
    private ButtonGroup radioGroup;

    // UI Components
    private JButton btnTimKiem, btnTinhToan, btnXuatExcel;
    private JComboBox<String> cbTuThang, cbDenThang, cbNam, cbDanhGiaTuThang, cbDanhGiaDenThang, cbDanhGiaNam;
    private JLabel lblTongDoanhThu, lblTongSanPham, lblTongKhachHang, lblDoanhThuNam, lblTieuDe;
    private JPanel panelTieuDe, panelTimKiem, panelThoiGian, panelBang, panelThongKe, panelDanhGia;
    private JRadioButton rbSanPhamBanChay, rbKhachHangHangDau;
    private JScrollPane scrollPane;
    private JTable table;
    private JTextField txtTongDoanhThu, txtTongSanPham, txtTongKhachHang, txtDoanhThuNam;
    private ChartPanel chartPanel; // Thêm ChartPanel để chứa biểu đồ

    public ThongKePanel() {
        setBackground(new Color(107, 163, 190));
        setLayout(new BorderLayout(10, 10));
        initComponents();
        setupComboBoxes();
        setupTable();
        setupListeners();
        loadAllData();
    }

    private void setupComboBoxes() {
        String[] months = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
        String[] years = { "2020", "2021", "2022", "2023", "2024", "2025" };

        cbTuThang.setModel(new DefaultComboBoxModel<>(months));
        cbDenThang.setModel(new DefaultComboBoxModel<>(months));
        cbDanhGiaTuThang.setModel(new DefaultComboBoxModel<>(months));
        cbDanhGiaDenThang.setModel(new DefaultComboBoxModel<>(months));
        cbNam.setModel(new DefaultComboBoxModel<>(years));
        cbDanhGiaNam.setModel(new DefaultComboBoxModel<>(years));

        Font comboFont = new Font("Segoe UI", Font.PLAIN, 14);
        for (JComboBox<?> cb : new JComboBox<?>[] { cbTuThang, cbDenThang, cbNam, cbDanhGiaTuThang,
                cbDanhGiaDenThang, cbDanhGiaNam }) {
            cb.setFont(comboFont);
            cb.setPreferredSize(new Dimension(100, 30));
        }
    }

    private void setupTable() {
        tableModel = new DefaultTableModel(
                new Object[][] {},
                new String[] { "STT", "Mã SP", "Tên SP", "Số lượng bán", "Doanh thu" });
        table.setModel(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setGridColor(new Color(150, 150, 150));
        table.setShowGrid(true);
        table.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    private void setupListeners() {
        radioGroup = new ButtonGroup();
        radioGroup.add(rbSanPhamBanChay);
        radioGroup.add(rbKhachHangHangDau);

        btnTimKiem.addActionListener(e -> {
            if (!rbSanPhamBanChay.isSelected() && !rbKhachHangHangDau.isSelected()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn loại thống kê!", "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                String tuThang = cbTuThang.getSelectedItem().toString();
                String denThang = cbDenThang.getSelectedItem().toString();
                String nam = cbNam.getSelectedItem().toString();
                String ngayCuoi = getLastDayOfMonth(Integer.parseInt(nam), Integer.parseInt(denThang));
                String tuNgay = nam + "-" + tuThang + "-01";
                String denNgay = nam + "-" + denThang + "-" + ngayCuoi;

                if (rbSanPhamBanChay.isSelected()) {
                    List<sanPhamThongKeDTO> sanPhams = thongKeDAO.getSanPhamThongKe(tuNgay, denNgay);
                    updateTableSanPham(sanPhams);
                } else {
                    List<TopKhachHangDTO> khachHangs = thongKeDAO.getTopKhachHang(tuNgay, denNgay);
                    updateTableKhachHang(khachHangs);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu: " + ex.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnTinhToan.addActionListener(e -> {
            try {
                String tuThang = cbDanhGiaTuThang.getSelectedItem().toString();
                String denThang = cbDanhGiaDenThang.getSelectedItem().toString();
                int nam = Integer.parseInt(cbDanhGiaNam.getSelectedItem().toString());
                String ngayCuoi = getLastDayOfMonth(nam, Integer.parseInt(denThang));
                String tuNgay = nam + "-" + tuThang + "-01";
                String denNgay = nam + "-" + denThang + "-" + ngayCuoi;

                List<sanPhamThongKeDTO> sanPhams = thongKeDAO.getSanPhamThongKe(tuNgay, denNgay);
                double tongDoanhThu = sanPhams.stream().mapToDouble(sanPhamThongKeDTO::getDoanhThu).sum();
                int tongSanPham = sanPhams.stream().mapToInt(sanPhamThongKeDTO::getSoLuong).sum();
                long tongKhachHang = sanPhams.stream().map(sanPhamThongKeDTO::getMaKhachHang).distinct().count();

                String namBatDau = nam + "-01-01";
                String namKetThuc = nam + "-12-" + getLastDayOfMonth(nam, 12);
                List<sanPhamThongKeDTO> duLieuNam = thongKeDAO.getSanPhamThongKe(namBatDau, namKetThuc);
                double doanhThuNam = duLieuNam.stream().mapToDouble(sanPhamThongKeDTO::getDoanhThu).sum();

                txtTongDoanhThu.setText(String.format("%.2f", tongDoanhThu));
                txtTongSanPham.setText(String.valueOf(tongSanPham));
                txtTongKhachHang.setText(String.valueOf(tongKhachHang));
                txtDoanhThuNam.setText(String.format("%.2f", doanhThuNam));

                // Cập nhật biểu đồ
                updateRevenueChart(nam);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tính toán: " + ex.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnXuatExcel.addActionListener(e -> {
            if (table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                        "Không có dữ liệu để xuất!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files", "xlsx"));
            fileChooser.setSelectedFile(new File("ThongKe.xlsx"));

            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }

                try {
                    XSSFWorkbook workbook = new XSSFWorkbook();
                    XSSFSheet sheet = workbook.createSheet("Thống kê");

                    XSSFCellStyle headerStyle = workbook.createCellStyle();
                    headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    headerStyle.setBorderBottom(BorderStyle.THIN);
                    headerStyle.setBorderTop(BorderStyle.THIN);
                    headerStyle.setBorderLeft(BorderStyle.THIN);
                    headerStyle.setBorderRight(BorderStyle.THIN);

                    XSSFFont headerFont = workbook.createFont();
                    headerFont.setBold(true);
                    headerStyle.setFont(headerFont);

                    XSSFCellStyle dataStyle = workbook.createCellStyle();
                    dataStyle.setBorderBottom(BorderStyle.THIN);
                    dataStyle.setBorderTop(BorderStyle.THIN);
                    dataStyle.setBorderLeft(BorderStyle.THIN);
                    dataStyle.setBorderRight(BorderStyle.THIN);

                    Row headerRow = sheet.createRow(0);
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        Cell cell = headerRow.createCell(i);
                        cell.setCellValue(table.getColumnName(i));
                        cell.setCellStyle(headerStyle);
                    }

                    for (int i = 0; i < table.getRowCount(); i++) {
                        Row row = sheet.createRow(i + 1);
                        for (int j = 0; j < table.getColumnCount(); j++) {
                            Cell cell = row.createCell(j);
                            Object value = table.getValueAt(i, j);
                            if (value != null) {
                                if (value instanceof Number) {
                                    cell.setCellValue(((Number) value).doubleValue());
                                } else {
                                    cell.setCellValue(value.toString());
                                }
                            }
                            cell.setCellStyle(dataStyle);
                        }
                    }

                    for (int i = 0; i < table.getColumnCount(); i++) {
                        sheet.autoSizeColumn(i);
                    }

                    try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                        workbook.write(fileOut);
                    }
                    workbook.close();

                    JOptionPane.showMessageDialog(this,
                            "Xuất file Excel thành công!",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Lỗi khi xuất file Excel: " + ex.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private String getLastDayOfMonth(int nam, int thang) {
        Calendar cal = Calendar.getInstance();
        cal.set(nam, thang - 1, 1);
        return String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    private void updateTableSanPham(List<sanPhamThongKeDTO> sanPhams) {
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new String[] { "STT", "Mã SP", "Tên SP", "Số lượng bán", "Doanh thu" });
        int stt = 1;
        for (sanPhamThongKeDTO sp : sanPhams) {
            tableModel.addRow(new Object[] {
                    stt++,
                    sp.getMaSanPham(),
                    sp.getTenSanPham(),
                    sp.getSoLuong(),
                    String.format("%.2f", sp.getDoanhThu())
            });
        }
    }

    private void updateTableKhachHang(List<TopKhachHangDTO> khachHangs) {
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(
                new String[] { "STT", "Mã KH", "Tên KH", "Số giao dịch", "Tổng doanh thu", "Giao dịch gần nhất" });
        int stt = 1;
        for (TopKhachHangDTO kh : khachHangs) {
            tableModel.addRow(new Object[] {
                    stt++,
                    kh.getMaKhachHang(),
                    kh.getTenKhachHang(),
                    kh.getSoLuongGiaoDich(),
                    String.format("%.2f", kh.getTongDoanhThu()),
                    kh.getLastTransactionDate() != null ? kh.getLastTransactionDate() : "N/A"
            });
        }
    }

    private void loadAllData() {
        try {
            String tuNgay = "2000-01-01";
            String denNgay = "2100-12-31";
            List<sanPhamThongKeDTO> sanPhams = thongKeDAO.getSanPhamThongKe(tuNgay, denNgay);
            updateTableSanPham(sanPhams);

            rbSanPhamBanChay.setSelected(true);

            if (sanPhams.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu thống kê trong cơ sở dữ liệu!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            // Hiển thị biểu đồ mặc định cho năm hiện tại
            updateRevenueChart(Calendar.getInstance().get(Calendar.YEAR));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateRevenueChart(int year) {
        try {
            // Tạo dataset cho biểu đồ
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (int month = 1; month <= 12; month++) {
                double doanhThu = thongKeDAO.getDoanhThuTheoThang(year, month);
                dataset.addValue(doanhThu, "Doanh thu", String.format("%02d", month));
            }

            // Tạo biểu đồ
            JFreeChart chart = ChartFactory.createLineChart(
                    "Xu hướng doanh thu năm " + year,
                    "Tháng",
                    "Doanh thu",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false);

            // Cập nhật ChartPanel
            chartPanel.setChart(chart);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tạo biểu đồ: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        // Title Panel
        panelTieuDe = new JPanel();
        panelTieuDe.setBackground(new Color(12, 150, 156));
        panelTieuDe.setPreferredSize(new Dimension(0, 70));
        lblTieuDe = new JLabel("Thống kê");
        lblTieuDe.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        lblTieuDe.setForeground(Color.WHITE);
        panelTieuDe.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelTieuDe.add(lblTieuDe);

        // Search Panel
        panelTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelTimKiem.setBackground(new Color(107, 163, 190));
        panelTimKiem.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        panelTimKiem.setPreferredSize(new Dimension(0, 100));

        rbSanPhamBanChay = new JRadioButton("Sản phẩm bán chạy");
        rbKhachHangHangDau = new JRadioButton("Khách hàng hàng đầu");
        rbSanPhamBanChay.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rbKhachHangHangDau.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rbSanPhamBanChay.setBackground(new Color(107, 163, 190));
        rbKhachHangHangDau.setBackground(new Color(107, 163, 190));

        panelThoiGian = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelThoiGian.setBackground(new Color(107, 163, 190));
        panelThoiGian.setBorder(null);

        cbNam = new JComboBox<>();
        cbTuThang = new JComboBox<>();
        cbDenThang = new JComboBox<>();

        panelThoiGian.add(new JLabel("Năm:"));
        panelThoiGian.add(cbNam);
        panelThoiGian.add(new JLabel("Từ tháng:"));
        panelThoiGian.add(cbTuThang);
        panelThoiGian.add(new JLabel("Đến tháng:"));
        panelThoiGian.add(cbDenThang);

        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnTimKiem.setBackground(new Color(60, 179, 113));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setPreferredSize(new Dimension(100, 30));

        panelTimKiem.add(rbSanPhamBanChay);
        panelTimKiem.add(rbKhachHangHangDau);
        panelTimKiem.add(panelThoiGian);
        panelTimKiem.add(btnTimKiem);

        // Table Panel
        panelBang = new JPanel(new BorderLayout());
        panelBang.setBackground(new Color(107, 163, 190));
        table = new JTable();
        scrollPane = new JScrollPane(table);
        panelBang.add(scrollPane, BorderLayout.CENTER);

        // Statistics Panel
        panelThongKe = new JPanel(new BorderLayout(10, 10));
        panelThongKe.setBackground(new Color(107, 163, 190));
        panelThongKe.setBorder(BorderFactory.createTitledBorder("Thống kê doanh thu"));

        JPanel panelChonThoiGian = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelChonThoiGian.setBackground(new Color(107, 163, 190));
        cbDanhGiaTuThang = new JComboBox<>();
        cbDanhGiaDenThang = new JComboBox<>();
        cbDanhGiaNam = new JComboBox<>();

        panelChonThoiGian.add(new JLabel("Năm:"));
        panelChonThoiGian.add(cbDanhGiaNam);
        panelChonThoiGian.add(new JLabel("Từ tháng:"));
        panelChonThoiGian.add(cbDanhGiaTuThang);
        panelChonThoiGian.add(new JLabel("Đến tháng:"));
        panelChonThoiGian.add(cbDanhGiaDenThang);

        btnTinhToan = new JButton("Xác nhận");
        btnTinhToan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnTinhToan.setBackground(new Color(60, 179, 113));
        btnTinhToan.setForeground(Color.WHITE);
        btnTinhToan.setPreferredSize(new Dimension(100, 30));
        panelChonThoiGian.add(btnTinhToan);

        JPanel panelChiSo = new JPanel(new GridLayout(2, 2, 15, 15));
        panelChiSo.setBackground(new Color(107, 163, 190));

        lblTongDoanhThu = new JLabel("Tổng doanh thu:");
        lblTongSanPham = new JLabel("Tổng sản phẩm:");
        lblTongKhachHang = new JLabel("Tổng khách hàng:");
        lblDoanhThuNam = new JLabel("Doanh thu năm:");

        txtTongDoanhThu = new JTextField(15);
        txtTongSanPham = new JTextField(15);
        txtTongKhachHang = new JTextField(15);
        txtDoanhThuNam = new JTextField(15);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 14);

        for (JLabel lbl : new JLabel[] { lblTongDoanhThu, lblTongSanPham, lblTongKhachHang, lblDoanhThuNam }) {
            lbl.setFont(labelFont);
        }
        for (JTextField txt : new JTextField[] { txtTongDoanhThu, txtTongSanPham, txtTongKhachHang, txtDoanhThuNam }) {
            txt.setFont(textFont);
            txt.setEditable(false);
        }

        panelChiSo.add(lblTongDoanhThu);
        panelChiSo.add(txtTongDoanhThu);
        panelChiSo.add(lblTongSanPham);
        panelChiSo.add(txtTongSanPham);
        panelChiSo.add(lblTongKhachHang);
        panelChiSo.add(txtTongKhachHang);
        panelChiSo.add(lblDoanhThuNam);
        panelChiSo.add(txtDoanhThuNam);

        // Evaluation Panel
        panelDanhGia = new JPanel(new BorderLayout());
        panelDanhGia.setBackground(new Color(107, 163, 190));
        panelDanhGia.setBorder(BorderFactory.createTitledBorder("Đánh giá"));
        panelDanhGia.setPreferredSize(new Dimension(300, 200));

        chartPanel = new ChartPanel(null); // Khởi tạo ChartPanel
        panelDanhGia.add(chartPanel, BorderLayout.CENTER);

        btnXuatExcel = new JButton("Xuất Excel");
        btnXuatExcel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnXuatExcel.setBackground(new Color(60, 179, 113));
        btnXuatExcel.setForeground(Color.WHITE);
        btnXuatExcel.setPreferredSize(new Dimension(150, 30));

        JPanel panelDuoi = new JPanel(new BorderLayout());
        panelDuoi.setBackground(new Color(107, 163, 190));
        panelDuoi.add(panelChiSo, BorderLayout.CENTER);
        panelDuoi.add(btnXuatExcel, BorderLayout.SOUTH);

        panelThongKe.add(panelChonThoiGian, BorderLayout.NORTH);
        panelThongKe.add(panelDuoi, BorderLayout.CENTER);
        panelThongKe.add(panelDanhGia, BorderLayout.EAST);

        // Add to main panel
        add(panelTieuDe, BorderLayout.NORTH);
        add(panelTimKiem, BorderLayout.NORTH);
        add(panelBang, BorderLayout.CENTER);
        add(panelThongKe, BorderLayout.SOUTH);
    }

    public JPanel getThongKePanel() {
        return this;
    }
}