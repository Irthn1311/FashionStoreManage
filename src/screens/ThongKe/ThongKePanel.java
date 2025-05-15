package screens.ThongKe;

import DTO.sanPhamThongKeDTO;
import DTO.TopKhachHangDTO;
import DAO.ThongKeDAO;
import screens.TrangChu.AppColors;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BasicStroke;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class ThongKePanel extends JPanel {
    private final ThongKeDAO thongKeDAO = new ThongKeDAO();
    private DefaultTableModel tableModelSearch, tableModelTopKH;
    private ButtonGroup radioGroup;

    private JButton btnTimKiem, btnTinhToan, btnXuatExcel;
    private JComboBox<String> cbTuThang, cbDenThang, cbNam, cbDanhGiaTuThang, cbDanhGiaDenThang, cbDanhGiaNam;
    private JLabel lblTieuDe;
    private JPanel panelHeader, panelDoanhThu, panelMiddle, panelChart, panelTopKH, panelTimKiem;
    private JRadioButton rbSanPhamBanChay, rbKhachHangHangDau;
    private JScrollPane scrollPaneSearch, scrollPaneTopKH;
    private JTable tableSearch, tableTopKH;
    private JTextField txtTongDoanhThu, txtTongSanPham, txtTongKhachHang, txtDoanhThuNam;
    private ChartPanel chartPanel;

    private static final Font TITLE_FONT = new Font("Roboto", Font.BOLD, 24);
    private static final Font SUBTITLE_FONT = new Font("Roboto", Font.BOLD, 16);
    private static final Font CONTENT_FONT = new Font("Roboto", Font.PLAIN, 14);
    private static final Font VALUE_FONT = new Font("Roboto", Font.BOLD, 18);
    private static final Font FALLBACK_TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font FALLBACK_SUBTITLE_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font FALLBACK_CONTENT_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FALLBACK_VALUE_FONT = new Font("Segoe UI", Font.BOLD, 18);

    private static final Color MAIN_BG_COLOR = getSafeColor(AppColors.NEW_MAIN_BG_COLOR, new Color(0xFAFAFA));
    private static final Color TEXT_COLOR = getSafeColor(AppColors.NEW_MAIN_TEXT_COLOR, new Color(0x212121));
    private static final Color ACCENT_COLOR = new Color(0x0288D1);
    private static final Color ACCENT_HOVER_COLOR = new Color(0x01579B);
    private static final Color BORDER_COLOR = new Color(0xE0E0E0);
    private static final Color CARD_BG_COLOR = Color.WHITE;
    private static final Color TABLE_HOVER_COLOR = new Color(0xE1F5FE);
    private static final Color DOANH_THU_COLOR = new Color(0x0288D1);
    private static final Color SAN_PHAM_COLOR = new Color(0xD32F2F);
    private static final Color KHACH_HANG_COLOR = new Color(0x388E3C);
    private static final Color DOANH_THU_NAM_COLOR = new Color(0xF57C00);

    public ThongKePanel() {
        setBackground(MAIN_BG_COLOR);
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        initComponents();
        setupComboBoxes();
        setupTables();
        setupListeners();
        loadInitialData();
    }

    private static Color getSafeColor(Color appColor, Color fallback) {
        return appColor != null ? appColor : fallback;
    }

    private void setupComboBoxes() {
        String[] months = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
        String[] years = { "2020", "2021", "2022", "2023", "2024", "2025" };

        for (JComboBox<String> cb : new JComboBox[] { cbTuThang, cbDenThang, cbNam, cbDanhGiaTuThang, cbDanhGiaDenThang,
                cbDanhGiaNam }) {
            cb.setModel(new DefaultComboBoxModel<>(months));
            cb.setFont(CONTENT_FONT);
            cb.setPreferredSize(new Dimension(60, 28));
            cb.setBackground(CARD_BG_COLOR);
            cb.setForeground(TEXT_COLOR);
            cb.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        }
        cbNam.setModel(new DefaultComboBoxModel<>(years));
        cbDanhGiaNam.setModel(new DefaultComboBoxModel<>(years));
    }

    private void setupTables() {
        tableModelSearch = new DefaultTableModel(new Object[][] {},
                new String[] { "STT", "Mã SP", "Tên SP", "Số lượng", "Doanh thu" }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableSearch.setModel(tableModelSearch);
        tableSearch.setRowHeight(30);
        tableSearch.setFont(CONTENT_FONT);
        tableSearch.getTableHeader().setFont(SUBTITLE_FONT);
        tableSearch.getTableHeader().setBackground(CARD_BG_COLOR);
        tableSearch.getTableHeader().setForeground(TEXT_COLOR);
        tableSearch.setBackground(CARD_BG_COLOR);
        tableSearch.setGridColor(BORDER_COLOR);
        tableSearch.setShowGrid(true);
        tableSearch.setIntercellSpacing(new Dimension(1, 1));
        tableSearch.setSelectionBackground(ACCENT_COLOR);
        tableSearch.setSelectionForeground(Color.WHITE);
        tableSearch.setPreferredScrollableViewportSize(new Dimension(0, 90)); // 3 dòng x 30px

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tableSearch.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableSearch.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        tableSearch.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

        tableModelTopKH = new DefaultTableModel(new Object[][] {}, new String[] { "STT", "Tên KH", "Tổng Doanh Thu" }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableTopKH.setModel(tableModelTopKH);
        tableTopKH.setRowHeight(30);
        tableTopKH.setFont(CONTENT_FONT);
        tableTopKH.getTableHeader().setFont(SUBTITLE_FONT);
        tableTopKH.getTableHeader().setBackground(CARD_BG_COLOR);
        tableTopKH.getTableHeader().setForeground(TEXT_COLOR);
        tableTopKH.setBackground(CARD_BG_COLOR);
        tableTopKH.setGridColor(BORDER_COLOR);
        tableTopKH.setShowGrid(false);
        tableTopKH.setIntercellSpacing(new Dimension(0, 0));
        tableTopKH.setSelectionBackground(ACCENT_COLOR);
        tableTopKH.setSelectionForeground(Color.WHITE);
        tableTopKH.setPreferredScrollableViewportSize(new Dimension(0, 30 * 7)); // Tăng lên 7 dòng
        tableTopKH.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableTopKH.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
    }

    private void setupListeners() {
        // radioGroup = new ButtonGroup(); // Initialization moved to initComponents
        // radioGroup.add(rbSanPhamBanChay); // These are now effectively handled in
        // initComponents
        // radioGroup.add(rbKhachHangHangDau); // after radioGroup is initialized and
        // before they are added to a panel.

        btnTimKiem.addActionListener(e -> {
            if (!rbSanPhamBanChay.isSelected() && !rbKhachHangHangDau.isSelected()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn loại thống kê!", "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            String tuThang = cbTuThang.getSelectedItem().toString();
            String denThang = cbDenThang.getSelectedItem().toString();
            String nam = cbNam.getSelectedItem().toString();
            String ngayCuoi = getLastDayOfMonth(Integer.parseInt(nam), Integer.parseInt(denThang));
            String tuNgay = nam + "-" + tuThang + "-01";
            String denNgay = nam + "-" + denThang + "-" + ngayCuoi;
            try {
                if (rbSanPhamBanChay.isSelected())
                    updateTableSearchSanPham(thongKeDAO.getSanPhamThongKe(tuNgay, denNgay));
                else
                    updateTableSearchKhachHang(thongKeDAO.getTopKhachHang(tuNgay, denNgay));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu tìm kiếm: " + ex.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnTinhToan.addActionListener(e -> {
            String tuThang = cbDanhGiaTuThang.getSelectedItem().toString();
            String denThang = cbDanhGiaDenThang.getSelectedItem().toString();
            int nam = Integer.parseInt(cbDanhGiaNam.getSelectedItem().toString());
            String ngayCuoi = getLastDayOfMonth(nam, Integer.parseInt(denThang));
            String tuNgay = nam + "-" + tuThang + "-01";
            String denNgay = nam + "-" + denThang + "-" + ngayCuoi;
            try {
                List<sanPhamThongKeDTO> sanPhams = thongKeDAO.getSanPhamThongKe(tuNgay, denNgay);
                double tongDoanhThu = sanPhams.stream().mapToDouble(sanPhamThongKeDTO::getDoanhThu).sum();
                int tongSanPham = sanPhams.stream().mapToInt(sanPhamThongKeDTO::getSoLuong).sum();
                long tongKhachHang = sanPhams.stream().map(sanPhamThongKeDTO::getMaKhachHang).distinct().count();
                String namBatDau = nam + "-01-01";
                String namKetThuc = nam + "-12-" + getLastDayOfMonth(nam, 12);
                double doanhThuNam = thongKeDAO.getSanPhamThongKe(namBatDau, namKetThuc).stream()
                        .mapToDouble(sanPhamThongKeDTO::getDoanhThu).sum();
                txtTongDoanhThu.setText(String.format("%,.2f VNĐ", tongDoanhThu));
                txtTongSanPham.setText(String.valueOf(tongSanPham));
                txtTongKhachHang.setText(String.valueOf(tongKhachHang));
                txtDoanhThuNam.setText(String.format("%,.2f VNĐ", doanhThuNam));
                updateRevenueChart(nam);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi tính toán thống kê: " + ex.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnXuatExcel.addActionListener(e -> {
            if (tableSearch.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files", "xlsx"));
            fileChooser.setSelectedFile(new File("ThongKe.xlsx"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String path = file.getAbsolutePath();
                if (!path.toLowerCase().endsWith(".xlsx"))
                    path += ".xlsx";
                try (XSSFWorkbook wb = new XSSFWorkbook()) {
                    XSSFSheet sheet = wb.createSheet("Thống kê");
                    XSSFCellStyle headerStyle = wb.createCellStyle();
                    headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    headerStyle.setBorderBottom(BorderStyle.THIN);
                    headerStyle.setBorderTop(BorderStyle.THIN);
                    headerStyle.setBorderLeft(BorderStyle.THIN);
                    headerStyle.setBorderRight(BorderStyle.THIN);
                    XSSFFont headerFont = wb.createFont();
                    headerFont.setBold(true);
                    headerStyle.setFont(headerFont);
                    XSSFCellStyle dataStyle = wb.createCellStyle();
                    dataStyle.setBorderBottom(BorderStyle.THIN);
                    dataStyle.setBorderTop(BorderStyle.THIN);
                    dataStyle.setBorderLeft(BorderStyle.THIN);
                    dataStyle.setBorderRight(BorderStyle.THIN);
                    Row header = sheet.createRow(0);
                    for (int i = 0; i < tableSearch.getColumnCount(); i++) {
                        Cell cell = header.createCell(i);
                        cell.setCellValue(tableSearch.getColumnName(i));
                        cell.setCellStyle(headerStyle);
                    }
                    for (int i = 0; i < tableSearch.getRowCount(); i++) {
                        Row row = sheet.createRow(i + 1);
                        for (int j = 0; j < tableSearch.getColumnCount(); j++) {
                            Cell cell = row.createCell(j);
                            Object value = tableSearch.getValueAt(i, j);
                            if (value instanceof Number)
                                cell.setCellValue(((Number) value).doubleValue());
                            else if (value != null)
                                cell.setCellValue(value.toString());
                            cell.setCellStyle(dataStyle);
                        }
                    }
                    for (int i = 0; i < tableSearch.getColumnCount(); i++)
                        sheet.autoSizeColumn(i);
                    try (FileOutputStream fos = new FileOutputStream(path)) {
                        wb.write(fos);
                    }
                    JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xuất file: " + ex.getMessage(), "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        return String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    private void updateTableSearchSanPham(List<sanPhamThongKeDTO> data) {
        tableModelSearch.setRowCount(0);
        int stt = 1;
        for (sanPhamThongKeDTO sp : data) {
            tableModelSearch.addRow(new Object[] { stt++, sp.getMaSanPham(), sp.getTenSanPham(), sp.getSoLuong(),
                    String.format("%,.2f VNĐ", sp.getDoanhThu()) });
            if (stt > 3)
                break;
        }
    }

    private void updateTableSearchKhachHang(List<TopKhachHangDTO> data) {
        tableModelSearch.setRowCount(0);
        tableModelSearch
                .setColumnIdentifiers(new String[] { "STT", "Mã KH", "Tên KH", "Số giao dịch", "Tổng doanh thu" });
        int stt = 1;
        for (TopKhachHangDTO kh : data) {
            tableModelSearch.addRow(new Object[] { stt++, kh.getMaKhachHang(), kh.getTenKhachHang(),
                    kh.getSoLuongGiaoDich(), String.format("%,.2f VNĐ", kh.getTongDoanhThu()) });
            if (stt > 3)
                break;
        }
    }

    private void updateTableTopKH(List<TopKhachHangDTO> data) {
        tableModelTopKH.setRowCount(0);
        int stt = 1;
        for (TopKhachHangDTO kh : data) {
            tableModelTopKH.addRow(new Object[] { stt++, kh.getTenKhachHang(),
                    String.format("%,.2f VNĐ", kh.getTongDoanhThu()) });
            if (stt > 7)
                break; // Tăng lên 7 dòng
        }
    }

    private void loadInitialData() {
        String start = "2000-01-01", end = "2100-12-31";
        try {
            List<sanPhamThongKeDTO> products = thongKeDAO.getSanPhamThongKe(start, end);
            List<TopKhachHangDTO> customers = thongKeDAO.getTopKhachHang(start, end);
            updateTableSearchSanPham(products);
            updateTableTopKH(customers);
            rbSanPhamBanChay.setSelected(true);
            if (products.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu ban đầu!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            updateRevenueChart(Calendar.getInstance().get(Calendar.YEAR));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu ban đầu: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void updateRevenueChart(int year) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            for (int month = 1; month <= 12; month++) {
                dataset.addValue(thongKeDAO.getDoanhThuTheoThang(year, month), "Doanh thu",
                        String.format("%02d", month));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu biểu đồ doanh thu: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            JFreeChart emptyChart = ChartFactory.createLineChart("Lỗi Tải Dữ Liệu", "Tháng", "Doanh Thu (VNĐ)",
                    new DefaultCategoryDataset(),
                    PlotOrientation.VERTICAL, false, false, false);
            if (chartPanel != null) {
                chartPanel.setChart(emptyChart);
            }
            return;
        }
        JFreeChart chart = ChartFactory.createLineChart("Doanh Thu " + year, "Tháng", "Doanh Thu (VNĐ)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.setBackgroundPaint(MAIN_BG_COLOR);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(CARD_BG_COLOR);
        plot.setDomainGridlinePaint(BORDER_COLOR);
        plot.setRangeGridlinePaint(BORDER_COLOR);
        plot.setOutlineVisible(false);
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, ACCENT_COLOR);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        chartPanel.setChart(chart);
    }

    private void initComponents() {
        radioGroup = new ButtonGroup(); // Moved initialization here

        panelHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelHeader.setBackground(AppColors.NEW_HEADER_PANEL_BG_COLOR);
        panelHeader.setBorder(new EmptyBorder(10, 15, 10, 15));
        panelHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        lblTieuDe = new JLabel("THỐNG KÊ DOANH THU");
        lblTieuDe.setFont(TITLE_FONT.getFontName().equals("Roboto") ? TITLE_FONT : FALLBACK_TITLE_FONT);
        lblTieuDe.setForeground(TEXT_COLOR);
        panelHeader.add(lblTieuDe);

        panelDoanhThu = new JPanel(new GridBagLayout());
        panelDoanhThu.setBackground(MAIN_BG_COLOR);
        panelDoanhThu.setBorder(new EmptyBorder(10, 15, 10, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtTongDoanhThu = new JTextField(10);
        txtTongSanPham = new JTextField(10);
        txtTongKhachHang = new JTextField(10);
        txtDoanhThuNam = new JTextField(10);
        for (JTextField tf : new JTextField[] { txtTongDoanhThu, txtTongSanPham, txtTongKhachHang, txtDoanhThuNam }) {
            tf.setFont(VALUE_FONT.getFontName().equals("Roboto") ? VALUE_FONT : FALLBACK_VALUE_FONT);
            tf.setEditable(false);
            tf.setBorder(new EmptyBorder(5, 0, 5, 0));
        }

        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        timePanel.setBackground(MAIN_BG_COLOR);
        cbDanhGiaTuThang = new JComboBox<>();
        cbDanhGiaDenThang = new JComboBox<>();
        cbDanhGiaNam = new JComboBox<>();
        JLabel lblNam = new JLabel("Năm:"), lblTu = new JLabel("Từ:"), lblDen = new JLabel("Đến:");
        for (JLabel lbl : new JLabel[] { lblNam, lblTu, lblDen })
            styleLabel(lbl);
        btnTinhToan = new JButton("Xác Nhận");
        styleButton(btnTinhToan);
        timePanel.add(lblNam);
        timePanel.add(cbDanhGiaNam);
        timePanel.add(lblTu);
        timePanel.add(cbDanhGiaTuThang);
        timePanel.add(lblDen);
        timePanel.add(cbDanhGiaDenThang);
        timePanel.add(btnTinhToan);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        panelDoanhThu.add(timePanel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.25;
        gbc.gridx = 0;
        panelDoanhThu.add(createInfoCard("Tổng Doanh Thu", txtTongDoanhThu, "money.png", DOANH_THU_COLOR), gbc);
        gbc.gridx = 1;
        panelDoanhThu.add(createInfoCard("Tổng Sản Phẩm", txtTongSanPham, "product.png", SAN_PHAM_COLOR), gbc);
        gbc.gridx = 2;
        panelDoanhThu.add(createInfoCard("Tổng Khách", txtTongKhachHang, "customer.png", KHACH_HANG_COLOR), gbc);
        gbc.gridx = 3;
        panelDoanhThu.add(createInfoCard("Doanh Thu Năm", txtDoanhThuNam, "statistics.png", DOANH_THU_NAM_COLOR), gbc);

        panelMiddle = new JPanel(new GridBagLayout());
        panelMiddle.setBackground(MAIN_BG_COLOR);
        panelMiddle.setBorder(new EmptyBorder(10, 15, 10, 15));
        GridBagConstraints gbcMiddle = new GridBagConstraints();
        gbcMiddle.insets = new Insets(5, 5, 5, 5);
        gbcMiddle.fill = GridBagConstraints.BOTH;

        panelChart = new JPanel(new BorderLayout());
        panelChart.setBackground(CARD_BG_COLOR);
        panelChart.setBorder(createCardBorder("Biểu Đồ Doanh Thu"));
        chartPanel = new ChartPanel(null);
        chartPanel.setBackground(CARD_BG_COLOR);
        panelChart.add(chartPanel, BorderLayout.CENTER);
        gbcMiddle.gridx = 0;
        gbcMiddle.weightx = 0.7;
        gbcMiddle.weighty = 1.0;
        panelMiddle.add(panelChart, gbcMiddle);

        panelTopKH = new JPanel(new BorderLayout());
        panelTopKH.setBackground(CARD_BG_COLOR);
        panelTopKH.setBorder(createCardBorder("Top Khách Hàng"));
        tableTopKH = new JTable();
        scrollPaneTopKH = new JScrollPane(tableTopKH);
        scrollPaneTopKH.setBorder(null);
        panelTopKH.add(scrollPaneTopKH, BorderLayout.CENTER);
        gbcMiddle.gridx = 1;
        gbcMiddle.weightx = 0.3;
        panelMiddle.add(panelTopKH, gbcMiddle);

        // Thiết kế lại panelTimKiem
        panelTimKiem = new JPanel(new GridBagLayout());
        panelTimKiem.setBackground(CARD_BG_COLOR);
        panelTimKiem.setBorder(createCardBorder("Tìm Kiếm"));
        GridBagConstraints gbcTim = new GridBagConstraints();
        gbcTim.insets = new Insets(5, 5, 5, 5);
        gbcTim.fill = GridBagConstraints.HORIZONTAL;

        rbSanPhamBanChay = new JRadioButton("Sản Phẩm");
        rbKhachHangHangDau = new JRadioButton("Khách Hàng");
        styleRadioButton(rbSanPhamBanChay);
        styleRadioButton(rbKhachHangHangDau);
        radioGroup.add(rbSanPhamBanChay);
        radioGroup.add(rbKhachHangHangDau);

        cbTuThang = new JComboBox<>();
        cbDenThang = new JComboBox<>();
        cbNam = new JComboBox<>();
        JLabel lblTuThang = new JLabel("Từ:"), lblDenThang = new JLabel("Đến:");
        styleLabel(lblTuThang);
        styleLabel(lblDenThang);
        btnTimKiem = new JButton("Tìm");
        styleButton(btnTimKiem);

        tableSearch = new JTable();
        scrollPaneSearch = new JScrollPane(tableSearch);
        scrollPaneSearch.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));

        // Dòng 0: Radio buttons, combo boxes và nút Tìm
        JPanel searchOptionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchOptionsPanel.setBackground(CARD_BG_COLOR);
        searchOptionsPanel.add(rbSanPhamBanChay);
        searchOptionsPanel.add(rbKhachHangHangDau);
        searchOptionsPanel.add(new JLabel("Năm:"));
        searchOptionsPanel.add(cbNam);
        searchOptionsPanel.add(lblTuThang);
        searchOptionsPanel.add(cbTuThang);
        searchOptionsPanel.add(lblDenThang);
        searchOptionsPanel.add(cbDenThang);
        searchOptionsPanel.add(btnTimKiem);

        gbcTim.gridx = 0;
        gbcTim.gridy = 0;
        gbcTim.gridwidth = 1;
        gbcTim.weightx = 1.0;
        panelTimKiem.add(searchOptionsPanel, gbcTim);

        // Dòng 1: Bảng kết quả
        gbcTim.gridx = 0;
        gbcTim.gridy = 1;
        gbcTim.weighty = 0.0; // Giảm chiều cao bảng
        panelTimKiem.add(scrollPaneSearch, gbcTim);

        btnXuatExcel = new JButton("Xuất Excel");
        styleButton(btnXuatExcel);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnPanel.setBackground(MAIN_BG_COLOR);
        btnPanel.add(btnXuatExcel);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(MAIN_BG_COLOR);
        southPanel.add(panelTimKiem, BorderLayout.CENTER);
        southPanel.add(btnPanel, BorderLayout.SOUTH);

        add(panelHeader, BorderLayout.NORTH);
        add(panelDoanhThu, BorderLayout.NORTH);
        add(panelMiddle, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    private JPanel createInfoCard(String title, JTextField valueField, String iconName, Color bgColor) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        card.setMinimumSize(new Dimension(220, 80));

        JLabel iconLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icon_img/" + iconName));
            if (icon.getImage() != null) {
                Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                iconLabel.setIcon(new ImageIcon(scaledImage));
            }
        } catch (Exception e) {
            System.err.println("Warning: Failed to load icon: /icon_img/" + iconName);
        }
        iconLabel.setForeground(Color.WHITE);
        card.add(iconLabel, BorderLayout.WEST);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.setBackground(bgColor);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(CONTENT_FONT.getFontName().equals("Roboto") ? CONTENT_FONT : FALLBACK_CONTENT_FONT);
        titleLabel.setForeground(Color.WHITE);

        valueField.setForeground(Color.WHITE);
        valueField.setBackground(bgColor);
        valueField.setHorizontalAlignment(SwingConstants.RIGHT);

        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(valueField, BorderLayout.CENTER);

        card.add(textPanel, BorderLayout.CENTER);
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(bgColor.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(bgColor);
            }
        });
        return card;
    }

    private Border createCardBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                title, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                SUBTITLE_FONT.getFontName().equals("Roboto") ? SUBTITLE_FONT : FALLBACK_SUBTITLE_FONT, TEXT_COLOR);
        return BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5), border);
    }

    private void styleButton(JButton button) {
        button.setFont(SUBTITLE_FONT);
        button.setBackground(ACCENT_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorder(new EmptyBorder(5, 15, 5, 15));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(ACCENT_HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(ACCENT_COLOR);
            }
        });
    }

    private void styleRadioButton(JRadioButton rb) {
        rb.setFont(CONTENT_FONT);
        rb.setBackground(CARD_BG_COLOR);
        rb.setForeground(TEXT_COLOR);
        rb.setFocusPainted(false);
        rb.setBorder(new EmptyBorder(2, 10, 2, 10));
    }

    private void styleLabel(JLabel label) {
        label.setFont(CONTENT_FONT);
        label.setForeground(TEXT_COLOR);
    }

    public JPanel getThongKePanel() {
        return this;
    }
}