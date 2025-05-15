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
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    // UI Components
    private JButton btnTimKiem, btnTinhToan, btnXuatExcel;
    private JComboBox<String> cbTuThang, cbDenThang, cbNam, cbDanhGiaTuThang, cbDanhGiaDenThang, cbDanhGiaNam;
    private JLabel lblTongDoanhThu, lblTongSanPham, lblTongKhachHang, lblDoanhThuNam, lblTieuDe;
    private JPanel panelHeader, panelDoanhThu, panelMiddle, panelChart, panelTopKH, panelTimKiem, panelThoiGian;
    private JRadioButton rbSanPhamBanChay, rbKhachHangHangDau;
    private JScrollPane scrollPaneSearch, scrollPaneTopKH;
    private JTable tableSearch, tableTopKH;
    private JTextField txtTongDoanhThu, txtTongSanPham, txtTongKhachHang, txtDoanhThuNam;
    private ChartPanel chartPanel;

    // Font và màu
    private static final java.awt.Font TITLE_FONT = new java.awt.Font("Roboto", java.awt.Font.BOLD, 26);
    private static final java.awt.Font SUBTITLE_FONT = new java.awt.Font("Roboto", java.awt.Font.BOLD, 16);
    private static final java.awt.Font CONTENT_FONT = new java.awt.Font("Roboto", java.awt.Font.PLAIN, 14);
    private static final java.awt.Font FALLBACK_TITLE_FONT = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 26);
    private static final java.awt.Font FALLBACK_SUBTITLE_FONT = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16);
    private static final java.awt.Font FALLBACK_CONTENT_FONT = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14);

    private static final java.awt.Color MAIN_BG_COLOR = getSafeColor(AppColors.NEW_MAIN_BG_COLOR,
            new java.awt.Color(0xFAFAFA));
    private static final java.awt.Color TEXT_COLOR = getSafeColor(AppColors.NEW_MAIN_TEXT_COLOR,
            new java.awt.Color(0x212121));
    private static final java.awt.Color ACCENT_COLOR = new java.awt.Color(0x0288D1);
    private static final java.awt.Color ACCENT_HOVER_COLOR = new java.awt.Color(0x01579B);
    private static final java.awt.Color BORDER_COLOR = new java.awt.Color(0xE0E0E0);
    private static final java.awt.Color CARD_BG_COLOR = java.awt.Color.WHITE;
    private static final java.awt.Color TABLE_HOVER_COLOR = new java.awt.Color(0xE1F5FE);

    public ThongKePanel() {
        setBackground(MAIN_BG_COLOR);
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        initComponents();
        setupComboBoxes();
        setupTables();
        setupListeners();
        loadAllData();
    }

    private static java.awt.Color getSafeColor(java.awt.Color appColor, java.awt.Color fallback) {
        return appColor != null ? appColor : fallback;
    }

    private void setupComboBoxes() {
        String[] months = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
        String[] years = { "2020", "2021", "2022", "2023", "2024", "2025" };

        for (JComboBox<String> cb : new JComboBox[] { cbTuThang, cbDenThang, cbNam, cbDanhGiaTuThang,
                cbDanhGiaDenThang, cbDanhGiaNam }) {
            cb.setModel(new DefaultComboBoxModel<>(months));
            cb.setFont(CONTENT_FONT);
            cb.setPreferredSize(new Dimension(90, 32));
            cb.setBackground(CARD_BG_COLOR);
            cb.setForeground(TEXT_COLOR);
            cb.setBorder(new EmptyBorder(5, 5, 5, 5));
            cb.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
                @Override
                protected JButton createArrowButton() {
                    JButton button = super.createArrowButton();
                    button.setBackground(CARD_BG_COLOR);
                    button.setBorder(new EmptyBorder(0, 0, 0, 0));
                    return button;
                }
            });
            cb.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    setFont(CONTENT_FONT);
                    setBackground(isSelected ? ACCENT_COLOR : CARD_BG_COLOR);
                    setForeground(isSelected ? java.awt.Color.WHITE : TEXT_COLOR);
                    return this;
                }
            });
        }
        cbNam.setModel(new DefaultComboBoxModel<>(years));
        cbDanhGiaNam.setModel(new DefaultComboBoxModel<>(years));
    }

    private void setupTables() {
        // Bảng tìm kiếm
        tableModelSearch = new DefaultTableModel(
                new Object[][] {},
                new String[] { "STT", "Mã SP", "Tên SP", "Số lượng bán", "Doanh thu" }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableSearch.setModel(tableModelSearch);
        tableSearch.setRowHeight(36);
        tableSearch.setFont(CONTENT_FONT);
        tableSearch.getTableHeader().setFont(SUBTITLE_FONT);
        tableSearch.getTableHeader().setBackground(CARD_BG_COLOR);
        tableSearch.getTableHeader().setForeground(TEXT_COLOR);
        tableSearch.getTableHeader().setBorder(new EmptyBorder(10, 10, 10, 10));
        tableSearch.setBackground(CARD_BG_COLOR);
        tableSearch.setGridColor(BORDER_COLOR);
        tableSearch.setShowGrid(false);
        tableSearch.setIntercellSpacing(new Dimension(0, 0));
        tableSearch.setSelectionBackground(ACCENT_COLOR);
        tableSearch.setSelectionForeground(java.awt.Color.WHITE);
        tableSearch.setPreferredScrollableViewportSize(new Dimension(0, 36 * 7)); // Giới hạn 7 dòng

        // Hover effect cho bảng tìm kiếm
        tableSearch.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = tableSearch.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    tableSearch.setRowSelectionInterval(row, row);
                }
            }
        });
        tableSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                tableSearch.clearSelection();
            }
        });

        // Căn chỉnh bảng tìm kiếm
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tableSearch.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableSearch.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        tableSearch.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

        // Bảng top khách hàng
        tableModelTopKH = new DefaultTableModel(
                new Object[][] {},
                new String[] { "STT", "Tên KH", "Tổng Doanh Thu" }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableTopKH.setModel(tableModelTopKH);
        tableTopKH.setRowHeight(36);
        tableTopKH.setFont(CONTENT_FONT);
        tableTopKH.getTableHeader().setFont(SUBTITLE_FONT);
        tableTopKH.getTableHeader().setBackground(CARD_BG_COLOR);
        tableTopKH.getTableHeader().setForeground(TEXT_COLOR);
        tableTopKH.getTableHeader().setBorder(new EmptyBorder(10, 10, 10, 10));
        tableTopKH.setBackground(CARD_BG_COLOR);
        tableTopKH.setGridColor(BORDER_COLOR);
        tableTopKH.setShowGrid(false);
        tableTopKH.setIntercellSpacing(new Dimension(0, 0));
        tableTopKH.setSelectionBackground(ACCENT_COLOR);
        tableTopKH.setSelectionForeground(java.awt.Color.WHITE);
        tableTopKH.setPreferredScrollableViewportSize(new Dimension(0, 36 * 5)); // Giới hạn 5 dòng

        // Hover effect cho bảng top khách hàng
        tableTopKH.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = tableTopKH.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    tableTopKH.setRowSelectionInterval(row, row);
                }
            }
        });
        tableTopKH.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                tableTopKH.clearSelection();
            }
        });

        // Căn chỉnh bảng top khách hàng
        tableTopKH.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableTopKH.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
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
                    updateTableSearchSanPham(sanPhams);
                } else {
                    List<TopKhachHangDTO> khachHangs = thongKeDAO.getTopKhachHang(tuNgay, denNgay);
                    updateTableSearchKhachHang(khachHangs);
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

                txtTongDoanhThu.setText(String.format("%,.2f VNĐ", tongDoanhThu));
                txtTongSanPham.setText(String.valueOf(tongSanPham));
                txtTongKhachHang.setText(String.valueOf(tongKhachHang));
                txtDoanhThuNam.setText(String.format("%,.2f VNĐ", doanhThuNam));

                updateRevenueChart(nam);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tính toán: " + ex.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
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
                    for (int i = 0; i < tableSearch.getColumnCount(); i++) {
                        Cell cell = headerRow.createCell(i);
                        cell.setCellValue(tableSearch.getColumnName(i));
                        cell.setCellStyle(headerStyle);
                    }

                    for (int i = 0; i < tableSearch.getRowCount(); i++) {
                        Row row = sheet.createRow(i + 1);
                        for (int j = 0; j < tableSearch.getColumnCount(); j++) {
                            Cell cell = row.createCell(j);
                            Object value = tableSearch.getValueAt(i, j);
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

                    for (int i = 0; i < tableSearch.getColumnCount(); i++) {
                        sheet.autoSizeColumn(i);
                    }

                    try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                        workbook.write(fileOut);
                    }
                    workbook.close();

                    JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi",
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

    private void updateTableSearchSanPham(List<sanPhamThongKeDTO> sanPhams) {
        tableModelSearch.setRowCount(0);
        tableModelSearch.setColumnIdentifiers(new String[] { "STT", "Mã SP", "Tên SP", "Số lượng bán", "Doanh thu" });
        int stt = 1;
        for (sanPhamThongKeDTO sp : sanPhams) {
            tableModelSearch.addRow(new Object[] {
                    stt++,
                    sp.getMaSanPham(),
                    sp.getTenSanPham(),
                    sp.getSoLuong(),
                    String.format("%,.2f VNĐ", sp.getDoanhThu())
            });
            if (stt > 7)
                break; // Giới hạn 7 dòng
        }
    }

    private void updateTableSearchKhachHang(List<TopKhachHangDTO> khachHangs) {
        tableModelSearch.setRowCount(0);
        tableModelSearch.setColumnIdentifiers(
                new String[] { "STT", "Mã KH", "Tên KH", "Số giao dịch", "Tổng doanh thu" });
        int stt = 1;
        for (TopKhachHangDTO kh : khachHangs) {
            tableModelSearch.addRow(new Object[] {
                    stt++,
                    kh.getMaKhachHang(),
                    kh.getTenKhachHang(),
                    kh.getSoLuongGiaoDich(),
                    String.format("%,.2f VNĐ", kh.getTongDoanhThu())
            });
            if (stt > 7)
                break; // Giới hạn 7 dòng
        }
    }

    private void updateTableTopKH(List<TopKhachHangDTO> khachHangs) {
        tableModelTopKH.setRowCount(0);
        int stt = 1;
        for (TopKhachHangDTO kh : khachHangs) {
            tableModelTopKH.addRow(new Object[] {
                    stt++,
                    kh.getTenKhachHang(),
                    String.format("%,.2f VNĐ", kh.getTongDoanhThu())
            });
            if (stt > 5)
                break; // Giới hạn 5 dòng
        }
    }

    private void loadAllData() {
        try {
            String tuNgay = "2000-01-01";
            String denNgay = "2100-12-31";
            List<sanPhamThongKeDTO> sanPhams = thongKeDAO.getSanPhamThongKe(tuNgay, denNgay);
            List<TopKhachHangDTO> khachHangs = thongKeDAO.getTopKhachHang(tuNgay, denNgay);
            updateTableSearchSanPham(sanPhams);
            updateTableTopKH(khachHangs);

            rbSanPhamBanChay.setSelected(true);

            if (sanPhams.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu thống kê trong cơ sở dữ liệu!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            updateRevenueChart(Calendar.getInstance().get(Calendar.YEAR));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateRevenueChart(int year) {
        try {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (int month = 1; month <= 12; month++) {
                double doanhThu = thongKeDAO.getDoanhThuTheoThang(year, month);
                dataset.addValue(doanhThu, "Doanh thu", String.format("%02d", month));
            }

            JFreeChart chart = ChartFactory.createLineChart(
                    "Doanh Thu Theo Tháng - " + year,
                    "Tháng",
                    "Doanh Thu (VNĐ)",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false);

            chart.setBackgroundPaint(MAIN_BG_COLOR);
            chart.setBorderVisible(false);
            CategoryPlot plot = chart.getCategoryPlot();
            plot.setBackgroundPaint(CARD_BG_COLOR);
            plot.setDomainGridlinePaint(BORDER_COLOR);
            plot.setRangeGridlinePaint(BORDER_COLOR);
            plot.setOutlineVisible(false);

            LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
            renderer.setSeriesPaint(0, ACCENT_COLOR);
            renderer.setSeriesShapesVisible(0, true);
            renderer.setSeriesStroke(0, new BasicStroke(3.0f));

            chartPanel.setChart(chart);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tạo biểu đồ: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        // Header Panel
        panelHeader = new JPanel();
        panelHeader.setBackground(CARD_BG_COLOR);
        panelHeader.setBorder(new EmptyBorder(15, 20, 15, 20));
        panelHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));

        lblTieuDe = new JLabel("Thống Kê Doanh Thu");
        lblTieuDe.setFont(TITLE_FONT.getFontName().equals("Roboto") ? TITLE_FONT : FALLBACK_TITLE_FONT);
        lblTieuDe.setForeground(TEXT_COLOR);
        panelHeader.add(lblTieuDe);

        // Doanh Thu Panel (Top)
        panelDoanhThu = new JPanel(new GridBagLayout());
        panelDoanhThu.setBackground(CARD_BG_COLOR);
        panelDoanhThu.setBorder(createCardBorder("Thống Kê Doanh Thu"));
        GridBagConstraints gbcDoanhThu = new GridBagConstraints();
        gbcDoanhThu.insets = new Insets(10, 10, 10, 10);
        gbcDoanhThu.fill = GridBagConstraints.HORIZONTAL;

        JPanel panelChonThoiGian = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelChonThoiGian.setBackground(CARD_BG_COLOR);
        cbDanhGiaTuThang = new JComboBox<>();
        cbDanhGiaDenThang = new JComboBox<>();
        cbDanhGiaNam = new JComboBox<>();

        JLabel lblDanhGiaNam = new JLabel("Năm:");
        JLabel lblDanhGiaTuThang = new JLabel("Từ Tháng:");
        JLabel lblDanhGiaDenThang = new JLabel("Đến Tháng:");
        styleLabel(lblDanhGiaNam);
        styleLabel(lblDanhGiaTuThang);
        styleLabel(lblDanhGiaDenThang);

        panelChonThoiGian.add(lblDanhGiaNam);
        panelChonThoiGian.add(cbDanhGiaNam);
        panelChonThoiGian.add(lblDanhGiaTuThang);
        panelChonThoiGian.add(cbDanhGiaTuThang);
        panelChonThoiGian.add(lblDanhGiaDenThang);
        panelChonThoiGian.add(cbDanhGiaDenThang);

        btnTinhToan = new JButton("Xác Nhận");
        styleButton(btnTinhToan);
        panelChonThoiGian.add(btnTinhToan);

        JPanel panelChiSo = new JPanel(new GridLayout(2, 2, 20, 20));
        panelChiSo.setBackground(CARD_BG_COLOR);
        panelChiSo.setBorder(new EmptyBorder(10, 10, 10, 10));

        lblTongDoanhThu = new JLabel("Tổng Doanh Thu:");
        lblTongSanPham = new JLabel("Tổng Sản Phẩm:");
        lblTongKhachHang = new JLabel("Tổng Khách Hàng:");
        lblDoanhThuNam = new JLabel("Doanh Thu Năm:");
        styleLabel(lblTongDoanhThu);
        styleLabel(lblTongSanPham);
        styleLabel(lblTongKhachHang);
        styleLabel(lblDoanhThuNam);

        txtTongDoanhThu = new JTextField(15);
        txtTongSanPham = new JTextField(15);
        txtTongKhachHang = new JTextField(15);
        txtDoanhThuNam = new JTextField(15);
        styleTextField(txtTongDoanhThu);
        styleTextField(txtTongSanPham);
        styleTextField(txtTongKhachHang);
        styleTextField(txtDoanhThuNam);

        panelChiSo.add(lblTongDoanhThu);
        panelChiSo.add(txtTongDoanhThu);
        panelChiSo.add(lblTongSanPham);
        panelChiSo.add(txtTongSanPham);
        panelChiSo.add(lblTongKhachHang);
        panelChiSo.add(txtTongKhachHang);
        panelChiSo.add(lblDoanhThuNam);
        panelChiSo.add(txtDoanhThuNam);

        gbcDoanhThu.gridx = 0;
        gbcDoanhThu.gridy = 0;
        panelDoanhThu.add(panelChonThoiGian, gbcDoanhThu);
        gbcDoanhThu.gridy = 1;
        panelDoanhThu.add(panelChiSo, gbcDoanhThu);

        // Middle Panel (Chart + Top KH)
        panelMiddle = new JPanel(new GridBagLayout());
        panelMiddle.setBackground(MAIN_BG_COLOR);
        panelMiddle.setBorder(new EmptyBorder(15, 20, 15, 20));
        GridBagConstraints gbcMiddle = new GridBagConstraints();
        gbcMiddle.insets = new Insets(10, 10, 10, 10);
        gbcMiddle.fill = GridBagConstraints.BOTH;

        // Chart Panel
        panelChart = new JPanel(new BorderLayout());
        panelChart.setBackground(CARD_BG_COLOR);
        panelChart.setBorder(createCardBorder("Biểu Đồ Doanh Thu"));
        chartPanel = new ChartPanel(null);
        chartPanel.setBackground(CARD_BG_COLOR);
        panelChart.add(chartPanel, BorderLayout.CENTER);
        gbcMiddle.gridx = 0;
        gbcMiddle.gridy = 0;
        gbcMiddle.weightx = 0.7;
        gbcMiddle.weighty = 1.0;
        panelMiddle.add(panelChart, gbcMiddle);

        // Top Khách Hàng Panel
        panelTopKH = new JPanel(new BorderLayout());
        panelTopKH.setBackground(CARD_BG_COLOR);
        panelTopKH.setBorder(createCardBorder("Top Khách Hàng"));
        tableTopKH = new JTable();
        scrollPaneTopKH = new JScrollPane(tableTopKH);
        scrollPaneTopKH.setBorder(null);
        scrollPaneTopKH.setBackground(CARD_BG_COLOR);
        panelTopKH.add(scrollPaneTopKH, BorderLayout.CENTER);
        gbcMiddle.gridx = 1;
        gbcMiddle.gridy = 0;
        gbcMiddle.weightx = 0.3;
        panelMiddle.add(panelTopKH, gbcMiddle);

        // Tìm Kiếm Panel (Bottom)
        panelTimKiem = new JPanel(new GridBagLayout());
        panelTimKiem.setBackground(CARD_BG_COLOR);
        panelTimKiem.setBorder(createCardBorder("Tìm Kiếm"));
        GridBagConstraints gbcTimKiem = new GridBagConstraints();
        gbcTimKiem.insets = new Insets(10, 10, 10, 10);
        gbcTimKiem.fill = GridBagConstraints.HORIZONTAL;

        rbSanPhamBanChay = new JRadioButton("Sản Phẩm Bán Chạy");
        rbKhachHangHangDau = new JRadioButton("Khách Hàng Hàng Đầu");
        styleRadioButton(rbSanPhamBanChay);
        styleRadioButton(rbKhachHangHangDau);

        panelThoiGian = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelThoiGian.setBackground(CARD_BG_COLOR);
        cbNam = new JComboBox<>();
        cbTuThang = new JComboBox<>();
        cbDenThang = new JComboBox<>();

        JLabel lblNam = new JLabel("Năm:");
        JLabel lblTuThang = new JLabel("Từ Tháng:");
        JLabel lblDenThang = new JLabel("Đến Tháng:");
        styleLabel(lblNam);
        styleLabel(lblTuThang);
        styleLabel(lblDenThang);

        panelThoiGian.add(lblNam);
        panelThoiGian.add(cbNam);
        panelThoiGian.add(lblTuThang);
        panelThoiGian.add(cbTuThang);
        panelThoiGian.add(lblDenThang);
        panelThoiGian.add(cbDenThang);

        btnTimKiem = new JButton("Tìm Kiếm");
        styleButton(btnTimKiem);

        tableSearch = new JTable();
        scrollPaneSearch = new JScrollPane(tableSearch);
        scrollPaneSearch.setBorder(null);
        scrollPaneSearch.setBackground(CARD_BG_COLOR);

        gbcTimKiem.gridx = 0;
        gbcTimKiem.gridy = 0;
        panelTimKiem.add(rbSanPhamBanChay, gbcTimKiem);
        gbcTimKiem.gridx = 1;
        panelTimKiem.add(rbKhachHangHangDau, gbcTimKiem);
        gbcTimKiem.gridx = 0;
        gbcTimKiem.gridy = 1;
        gbcTimKiem.gridwidth = 2;
        panelTimKiem.add(panelThoiGian, gbcTimKiem);
        gbcTimKiem.gridy = 2;
        gbcTimKiem.gridwidth = 1;
        gbcTimKiem.anchor = GridBagConstraints.CENTER;
        panelTimKiem.add(btnTimKiem, gbcTimKiem);
        gbcTimKiem.gridy = 3;
        gbcTimKiem.gridwidth = 2;
        gbcTimKiem.fill = GridBagConstraints.BOTH;
        panelTimKiem.add(scrollPaneSearch, gbcTimKiem);

        // Export Button
        btnXuatExcel = new JButton("Xuất Excel");
        styleButton(btnXuatExcel);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelButtons.setBackground(MAIN_BG_COLOR);
        panelButtons.add(btnXuatExcel);

        // Main layout
        add(panelHeader, BorderLayout.NORTH);
        add(panelDoanhThu, BorderLayout.NORTH);
        add(panelMiddle, BorderLayout.CENTER);
        add(panelTimKiem, BorderLayout.SOUTH);
        add(panelButtons, BorderLayout.SOUTH);
    }

    private Border createCardBorder(String title) {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                title, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                SUBTITLE_FONT.getFontName().equals("Roboto") ? SUBTITLE_FONT : FALLBACK_SUBTITLE_FONT,
                TEXT_COLOR);
        return BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(5, 5, 5, 5),
                        titledBorder),
                BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void styleButton(JButton button) {
        button.setFont(SUBTITLE_FONT);
        button.setBackground(ACCENT_COLOR);
        button.setForeground(java.awt.Color.WHITE);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(ACCENT_HOVER_COLOR);
                button.setBorder(new EmptyBorder(8, 18, 8, 18));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(ACCENT_COLOR);
                button.setBorder(new EmptyBorder(10, 20, 10, 20));
            }
        });
    }

    private void styleRadioButton(JRadioButton radioButton) {
        radioButton.setFont(CONTENT_FONT);
        radioButton.setBackground(CARD_BG_COLOR);
        radioButton.setForeground(TEXT_COLOR);
        radioButton.setFocusPainted(false);
        radioButton.setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    private void styleLabel(JLabel label) {
        label.setFont(CONTENT_FONT);
        label.setForeground(TEXT_COLOR);
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(CONTENT_FONT);
        textField.setEditable(false);
        textField.setBackground(CARD_BG_COLOR);
        textField.setForeground(TEXT_COLOR);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(8, 8, 8, 8)));
    }

    public JPanel getThongKePanel() {
        return this;
    }
}