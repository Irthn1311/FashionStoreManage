package screens.ThongKe;

import DTO.sanPhamThongKeDTO;
import DTO.TopKhachHangDTO;
import DAO.ThongKeDAO;
import screens.TrangChu.AppColors;
import javax.swing.border.TitledBorder;

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
        setBackground(AppColors.NEW_MAIN_BG_COLOR);
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
            cb.setBackground(AppColors.TABLE_BACKGROUND_COLOR);
            cb.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        }
    }

    private void setupTable() {
        tableModel = new DefaultTableModel(
                new Object[][] {},
                new String[] { "STT", "Mã SP", "Tên SP", "Số lượng bán", "Doanh thu" }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tắt khả năng chỉnh sửa cho tất cả các ô
            }
        };
        table.setModel(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(AppColors.NEW_HEADER_PANEL_BG_COLOR);
        table.getTableHeader().setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        table.setBackground(AppColors.TABLE_BACKGROUND_COLOR);
        table.setGridColor(AppColors.NEW_BORDER_LINES_COLOR);
        table.setShowGrid(true);
        table.setBorder(BorderFactory.createLineBorder(AppColors.NEW_BORDER_LINES_COLOR, 1));
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

            chart.setBackgroundPaint(AppColors.NEW_MAIN_BG_COLOR);
            chart.getPlot().setBackgroundPaint(AppColors.NEW_MAIN_BG_COLOR);
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
        panelTieuDe.setBackground(AppColors.NEW_HEADER_PANEL_BG_COLOR);
        panelTieuDe.setPreferredSize(new Dimension(0, 70));
        lblTieuDe = new JLabel("Thống kê");
        lblTieuDe.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        lblTieuDe.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        panelTieuDe.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelTieuDe.add(lblTieuDe);

        // Search Panel
        panelTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelTimKiem.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        javax.swing.border.TitledBorder panelTimKiemBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Tìm kiếm");
        panelTimKiemBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
        panelTimKiem.setBorder(panelTimKiemBorder);
        panelTimKiem.setPreferredSize(new Dimension(0, 100));

        rbSanPhamBanChay = new JRadioButton("Sản phẩm bán chạy");
        rbKhachHangHangDau = new JRadioButton("Khách hàng hàng đầu");
        rbSanPhamBanChay.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rbKhachHangHangDau.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rbSanPhamBanChay.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        rbKhachHangHangDau.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        rbSanPhamBanChay.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        rbKhachHangHangDau.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);

        panelThoiGian = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelThoiGian.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        panelThoiGian.setBorder(null);

        cbNam = new JComboBox<>();
        cbTuThang = new JComboBox<>();
        cbDenThang = new JComboBox<>();

        JLabel lblNam = new JLabel("Năm:");
        lblNam.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        panelThoiGian.add(lblNam);
        panelThoiGian.add(cbNam);
        JLabel lblTuThang = new JLabel("Từ tháng:");
        lblTuThang.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        panelThoiGian.add(lblTuThang);
        panelThoiGian.add(cbTuThang);
        JLabel lblDenThang = new JLabel("Đến tháng:");
        lblDenThang.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        panelThoiGian.add(lblDenThang);
        panelThoiGian.add(cbDenThang);

        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnTimKiem.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setBorder(BorderFactory.createLineBorder(AppColors.NEW_BORDER_LINES_COLOR));
        btnTimKiem.setPreferredSize(new Dimension(100, 30));

        panelTimKiem.add(rbSanPhamBanChay);
        panelTimKiem.add(rbKhachHangHangDau);
        panelTimKiem.add(panelThoiGian);
        panelTimKiem.add(btnTimKiem);

        // Table Panel
        panelBang = new JPanel(new BorderLayout());
        panelBang.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        table = new JTable();
        scrollPane = new JScrollPane(table);
        panelBang.add(scrollPane, BorderLayout.CENTER);

        // Statistics Panel
        panelThongKe = new JPanel(new BorderLayout(10, 10));
        panelThongKe.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        javax.swing.border.TitledBorder panelThongKeBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Thống kê doanh thu");
        panelThongKeBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
        panelThongKe.setBorder(panelThongKeBorder);

        JPanel panelChonThoiGian = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelChonThoiGian.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        cbDanhGiaTuThang = new JComboBox<>();
        cbDanhGiaDenThang = new JComboBox<>();
        cbDanhGiaNam = new JComboBox<>();

        JLabel lblDanhGiaNam = new JLabel("Năm:");
        lblDanhGiaNam.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        panelChonThoiGian.add(lblDanhGiaNam);
        panelChonThoiGian.add(cbDanhGiaNam);
        JLabel lblDanhGiaTuThang = new JLabel("Từ tháng:");
        lblDanhGiaTuThang.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        panelChonThoiGian.add(lblDanhGiaTuThang);
        panelChonThoiGian.add(cbDanhGiaTuThang);
        JLabel lblDanhGiaDenThang = new JLabel("Đến tháng:");
        lblDanhGiaDenThang.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        panelChonThoiGian.add(lblDanhGiaDenThang);
        panelChonThoiGian.add(cbDanhGiaDenThang);

        btnTinhToan = new JButton("Xác nhận");
        btnTinhToan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnTinhToan.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        btnTinhToan.setForeground(Color.WHITE);
        btnTinhToan.setBorder(BorderFactory.createLineBorder(AppColors.NEW_BORDER_LINES_COLOR));
        btnTinhToan.setPreferredSize(new Dimension(100, 30));
        panelChonThoiGian.add(btnTinhToan);

        JPanel panelChiSo = new JPanel(new GridLayout(2, 2, 15, 15));
        panelChiSo.setBackground(AppColors.NEW_MAIN_BG_COLOR);

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
            lbl.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        }
        for (JTextField txt : new JTextField[] { txtTongDoanhThu, txtTongSanPham, txtTongKhachHang, txtDoanhThuNam }) {
            txt.setFont(textFont);
            txt.setEditable(false);
            txt.setBackground(AppColors.TABLE_BACKGROUND_COLOR);
            txt.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
            txt.setBorder(BorderFactory.createLineBorder(AppColors.NEW_BORDER_LINES_COLOR));
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
        panelDanhGia.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        javax.swing.border.TitledBorder panelDanhGiaBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Đánh giá");
        panelDanhGiaBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
        panelDanhGia.setBorder(panelDanhGiaBorder);
        panelDanhGia.setPreferredSize(new Dimension(300, 200));

        chartPanel = new ChartPanel(null); // Khởi tạo ChartPanel
        chartPanel.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        panelDanhGia.add(chartPanel, BorderLayout.CENTER);

        btnXuatExcel = new JButton("Xuất Excel");
        btnXuatExcel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnXuatExcel.setBackground(AppColors.NEW_QUICK_ACCESS_BUTTON_BG_COLOR);
        btnXuatExcel.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR);
        btnXuatExcel.setBorder(BorderFactory.createLineBorder(AppColors.NEW_BORDER_LINES_COLOR));
        btnXuatExcel.setPreferredSize(new Dimension(150, 30));

        JPanel panelDuoi = new JPanel(new BorderLayout());
        panelDuoi.setBackground(AppColors.NEW_MAIN_BG_COLOR);
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