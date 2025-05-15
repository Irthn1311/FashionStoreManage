package screens.HoaDon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import DAO.HoaDonDAO;
import DTO.hoaDonDTO;
import BUS.HoaDonBUS;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.sql.Timestamp;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import java.io.File;
import utils.FileUtils;
import javax.swing.JEditorPane;
import screens.TrangChu.AppColors;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Calendar;
import java.awt.Font;
import javax.swing.JSeparator;

public class HoaDonPanel extends javax.swing.JPanel {
    private HoaDonDAO hoaDonDAO;
    private SimpleDateFormat dateFormat;
    private DecimalFormat decimalFormat;

    // Added for row coloring
    private List<Color> rowColors;
    private Map<String, Color> maHoaDonPrefixColorMap;
    private int nextColorIndex = 0;

    // Added for Tong Tien Ban Hang
    private javax.swing.JLabel lblTongTienBanHang;
    private javax.swing.JTextField txtTongTienBanHang;

    public HoaDonPanel() {
        initComponents();
        setupUI();

        // Khởi tạo DAO và định dạng
        hoaDonDAO = new HoaDonDAO();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        decimalFormat = new DecimalFormat("#,##0.00");

        // Initialize color fields
        rowColors = new ArrayList<>(Arrays.asList(
            new Color(245, 245, 245), // Very Light Gray
            new Color(235, 240, 250), // Lightest Blue
            new Color(235, 250, 235)  // Lightest Green
        ));
        maHoaDonPrefixColorMap = new HashMap<>();

        setupSearchComponents();
        setupTable();
        // Tải dữ liệu hóa đơn từ cơ sở dữ liệu
        loadHoaDonData();
        setupListeners();
        setupTableListeners();
        setupIcons();
    }

    // Phương thức để thiết lập các biểu tượng
    private void setupIcons() {
        try {

            ImageIcon searchIcon = new ImageIcon(getClass().getResource("/icon_img/search.png"));
            if (searchIcon.getImage() != null) {
                Image scaledSearchIcon = searchIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                jButton30.setIcon(new ImageIcon(scaledSearchIcon));
                jButton30.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                jButton30.setIconTextGap(5);
            }

            ImageIcon addIcon = new ImageIcon(getClass().getResource("/icon_img/add.png"));
            if (addIcon.getImage() != null) {
                Image scaledAddIcon = addIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                btnThem.setIcon(new ImageIcon(scaledAddIcon));
                btnThem.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnThem.setIconTextGap(5);
            }

            ImageIcon editIcon = new ImageIcon(getClass().getResource("/icon_img/edit.png"));
            if (editIcon.getImage() != null) {
                Image scaledEditIcon = editIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                btnSua.setIcon(new ImageIcon(scaledEditIcon));
                btnSua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnSua.setIconTextGap(5);
            }

            ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/icon_img/delete.png"));
            if (deleteIcon.getImage() != null) {
                Image scaledDeleteIcon = deleteIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                btnXoa.setIcon(new ImageIcon(scaledDeleteIcon));
                btnXoa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnXoa.setIconTextGap(5);
            }

            ImageIcon exportIcon = new ImageIcon(getClass().getResource("/icon_img/export_icon.png"));
            if (exportIcon.getImage() != null) {
                Image scaledExportIcon = exportIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                btnExport.setIcon(new ImageIcon(scaledExportIcon));
                btnExport.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnExport.setIconTextGap(5);
            }

            ImageIcon importIcon = new ImageIcon(getClass().getResource("/icon_img/import_icon.png"));
            if (importIcon.getImage() != null) {
                Image scaledImportIcon = importIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                btnImport.setIcon(new ImageIcon(scaledImportIcon));
                btnImport.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnImport.setIconTextGap(5);
            }

            ImageIcon printIcon = new ImageIcon(getClass().getResource("/icon_img/print_icon.png"));
            if (printIcon.getImage() != null) {
                Image scaledPrintIcon = printIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                btnPrinter.setIcon(new ImageIcon(scaledPrintIcon));
                btnPrinter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnPrinter.setIconTextGap(5);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Không thể tải biểu tượng: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupTable() {
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(50); // STT
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(80); // Mã HĐ
        jTable2.getColumnModel().getColumn(2).setPreferredWidth(80); // Mã SP
        jTable2.getColumnModel().getColumn(3).setPreferredWidth(120); // Tên SP
        jTable2.getColumnModel().getColumn(4).setPreferredWidth(80); // Mã KH
        jTable2.getColumnModel().getColumn(5).setPreferredWidth(120); // Tên KH
        jTable2.getColumnModel().getColumn(6).setPreferredWidth(60); // Kích cỡ
        jTable2.getColumnModel().getColumn(7).setPreferredWidth(60); // Màu sắc
        jTable2.getColumnModel().getColumn(8).setPreferredWidth(60); // Số lượng
        jTable2.getColumnModel().getColumn(9).setPreferredWidth(80); // Đơn giá
        jTable2.getColumnModel().getColumn(10).setPreferredWidth(80); // Thành tiền
        jTable2.getColumnModel().getColumn(11).setPreferredWidth(100); // Thời gian
        jTable2.getColumnModel().getColumn(12).setPreferredWidth(80); // Hình thức TT
        jTable2.getColumnModel().getColumn(13).setPreferredWidth(80); // Trạng Thái
        jTable2.getColumnModel().getColumn(14).setPreferredWidth(60); // Chi tiết

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        jTable2.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // STT
        jTable2.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Mã HĐ
        jTable2.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Mã SP
        jTable2.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Mã KH
        jTable2.getColumnModel().getColumn(6).setCellRenderer(centerRenderer); // Kích cỡ
        jTable2.getColumnModel().getColumn(7).setCellRenderer(centerRenderer); // Màu sắc
        jTable2.getColumnModel().getColumn(8).setCellRenderer(centerRenderer); // Số lượng
        jTable2.getColumnModel().getColumn(9).setCellRenderer(centerRenderer); // Đơn giá
        jTable2.getColumnModel().getColumn(10).setCellRenderer(centerRenderer); // Thành tiền
        jTable2.getColumnModel().getColumn(13).setCellRenderer(centerRenderer); // Trạng Thái
        jTable2.getColumnModel().getColumn(14).setCellRenderer(centerRenderer); // Chi tiết

        // Apply CustomRowRenderer to all columns
        CustomRowRenderer customRenderer = new CustomRowRenderer();
        for (int i = 0; i < jTable2.getColumnCount(); i++) {
            jTable2.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }
        // The specific renderer for column 14 with hover effect is now part of CustomRowRenderer logic
        /*
        jTable2.getColumnModel().getColumn(14).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setForeground(table.getSelectionForeground());
                    c.setBackground(table.getSelectionBackground());
                } else {
                    c.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR);
                    c.setBackground(table.getBackground());
                }
                if (table.getClientProperty("hoverRow") != null && (int) table.getClientProperty("hoverRow") == row && column == 14) {
                    c.setForeground(AppColors.NEW_SELECTED_BUTTON_COLOR);
                }
                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });
        */

        jTable2.setRowHeight(25);
    }

    private void setupTableListeners() {
        jTable2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = jTable2.rowAtPoint(e.getPoint());
                int col = jTable2.columnAtPoint(e.getPoint());

                int lastColumnIndex = jTable2.getColumnCount() - 1;

                if (row >= 0 && col == lastColumnIndex) {
                    showChiTietHoaDon(row);
                }
            }
        });

        jTable2.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int col = jTable2.columnAtPoint(e.getPoint());
                if (col == jTable2.getColumnCount() - 1) {
                    jTable2.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    jTable2.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

    private void showChiTietHoaDon(int row) {
        try {
            String maHoaDon = getTableValueAsString(row, 1);
            hoaDonDTO hd = hoaDonDAO.getHoaDonByMa(maHoaDon); // Lấy trực tiếp từ cơ sở dữ liệu
            if (hd == null) {
                JOptionPane.showMessageDialog(this,
                        "Hóa đơn không tồn tại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            chiTietHoaDonDialog dialog = new chiTietHoaDonDialog(null, hd);
            dialog.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Có lỗi xảy ra khi hiển thị thông tin chi tiết: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getTableValueAsString(int row, int column) {
        Object value = jTable2.getValueAt(row, column);
        return value != null ? value.toString() : "";
    }

    private void loadHoaDonData() {
        try {
            List<hoaDonDTO> hoaDonList = hoaDonDAO.getAllHoaDon();
            updateTableData(hoaDonList);
            if (hoaDonList.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Không có dữ liệu hóa đơn trong cơ sở dữ liệu!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu từ cơ sở dữ liệu: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTableData(List<hoaDonDTO> hoaDonList) {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);

        Set<String> processedPrefixesThisLoad = new HashSet<>();
        Color lastAssignedColorForNewPrefix = null;

        // First pass to assign colors to new prefixes in this list if they don't have one globally
        for (hoaDonDTO hd : hoaDonList) {
            String maHoaDonFull = hd.getMaHoaDon();
            String currentPrefix = "";
            if (maHoaDonFull != null && maHoaDonFull.contains("_")) {
                currentPrefix = maHoaDonFull.substring(0, maHoaDonFull.indexOf('_'));
            } else {
                currentPrefix = maHoaDonFull != null ? maHoaDonFull : "";
            }

            if (!currentPrefix.isEmpty() && !maHoaDonPrefixColorMap.containsKey(currentPrefix) && !processedPrefixesThisLoad.contains(currentPrefix)) {
                Color colorToAssign = rowColors.get(nextColorIndex % rowColors.size());
                if (lastAssignedColorForNewPrefix != null && lastAssignedColorForNewPrefix.equals(colorToAssign) && rowColors.size() > 1) {
                    nextColorIndex++; // Try next color to avoid immediate repetition for *newly assigned* groups in this load
                    colorToAssign = rowColors.get(nextColorIndex % rowColors.size());
                }
                maHoaDonPrefixColorMap.put(currentPrefix, colorToAssign);
                lastAssignedColorForNewPrefix = colorToAssign;
                nextColorIndex++; // Increment for the next *new distinct* prefix group
                processedPrefixesThisLoad.add(currentPrefix);
            }
        }
        
        int stt = 1;
        for (hoaDonDTO hd : hoaDonList) {
            String thoiGianStr = hd.getThoiGian() != null ? dateFormat.format(hd.getThoiGian()) : "";
            model.addRow(new Object[] {
                    stt++,
                    hd.getMaHoaDon(),
                    hd.getMaSanPham(),
                    hd.getTenSanPham(),
                    hd.getMaKhachHang(),
                    hd.getTenKhachHang(),
                    hd.getKichCo(),
                    hd.getMauSac(),
                    hd.getSoLuong(),
                    decimalFormat.format(hd.getDonGia()),
                    decimalFormat.format(hd.getThanhTien()),
                    thoiGianStr,
                    hd.getHinhThucThanhToan(),
                    hd.getTrangThai(),
                    "Xem chi tiết"
            });
        }
        updateTongTienBanHang(); // Update total after table data is set/changed
    }

    // Cập nhật STT sau khi xóa dòng
    private void updateTableSTT() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(i + 1, i, 0); // Cập nhật cột STT
        }
    }

    private void searchHoaDon() {
        String keyword = jTextField1.getText().trim();
        String searchType = jComboBox1.getSelectedItem().toString();
        String soLuongTu = jTextFieldSoLuongTu.getText().trim();
        String soLuongDen = jTextFieldSoLuongDen.getText().trim();
        String thanhTienTu = jTextFieldThanhTienTu.getText().trim();
        String thanhTienDen = jTextFieldThanhTienDen.getText().trim();

        try {
            // Kiểm tra ô nhập liệu trống khi tìm kiếm theo tiêu chí cụ thể
            if (!searchType.equals("Tất cả") && keyword.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập dữ liệu để tìm kiếm",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                jTextField1.requestFocus();
                return;
            }

            // Nếu không có điều kiện tìm kiếm, tải lại toàn bộ dữ liệu
            if (keyword.isEmpty() && soLuongTu.isEmpty() && soLuongDen.isEmpty() && thanhTienTu.isEmpty()
                    && thanhTienDen.isEmpty()) {
                loadHoaDonData(); // This will call updateTableData which calls updateTongTienBanHang
                return;
            }

            // Lấy danh sách hóa đơn từ DAO
            List<hoaDonDTO> searchResults = hoaDonDAO.searchHoaDon(keyword);

            // Lọc thêm theo số lượng và thành tiền nếu có
            if (!soLuongTu.isEmpty() || !soLuongDen.isEmpty() || !thanhTienTu.isEmpty() || !thanhTienDen.isEmpty()) {
                searchResults = searchResults.stream().filter(hd -> {
                    boolean match = true;

                    // Lọc theo số lượng
                    if (!soLuongTu.isEmpty()) {
                        try {
                            int soLuongMin = Integer.parseInt(soLuongTu);
                            match = match && hd.getSoLuong() >= soLuongMin;
                        } catch (NumberFormatException ex) {
                            match = false;
                        }
                    }
                    if (!soLuongDen.isEmpty()) {
                        try {
                            int soLuongMax = Integer.parseInt(soLuongDen);
                            match = match && hd.getSoLuong() <= soLuongMax;
                        } catch (NumberFormatException ex) {
                            match = false;
                        }
                    }

                    // Lọc theo thành tiền
                    if (!thanhTienTu.isEmpty()) {
                        try {
                            double thanhTienMin = Double.parseDouble(thanhTienTu.replace(",", ""));
                            match = match && hd.getThanhTien() >= thanhTienMin;
                        } catch (NumberFormatException ex) {
                            match = false;
                        }
                    }
                    if (!thanhTienDen.isEmpty()) {
                        try {
                            double thanhTienMax = Double.parseDouble(thanhTienDen.replace(",", ""));
                            match = match && hd.getThanhTien() <= thanhTienMax;
                        } catch (NumberFormatException ex) {
                            match = false;
                        }
                    }

                    return match;
                }).collect(java.util.stream.Collectors.toList());
            }

            updateTableData(searchResults);
            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy hóa đơn nào khớp với tiêu chí tìm kiếm",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            updateTongTienBanHang(); // Update total after filtering by date
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tìm kiếm hóa đơn: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void resetSearchFields() {
        jTextField1.setText("");
        jComboBox1.setSelectedIndex(0);
        jTextFieldSoLuongTu.setText("");
        jTextFieldSoLuongDen.setText("");
        jTextFieldThanhTienTu.setText("");
        jTextFieldThanhTienDen.setText("");
        loadHoaDonData();
    }

    private void setupSearchComponents() {
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
                "Tất cả", "Mã hóa đơn", "Tên khách hàng", "Mã sản phẩm", "Tên sản phẩm"
        }));

        jButton30.addActionListener(e -> searchHoaDon());

        jButtonReset.addActionListener(e -> resetSearchFields());

        jTextField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchHoaDon();
                }
            }
        });

        jTextFieldSoLuongTu.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchHoaDon();
                }
            }
        });

        jTextFieldSoLuongDen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchHoaDon();
                }
            }
        });

        jTextFieldThanhTienTu.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchHoaDon();
                }
            }
        });

        jTextFieldThanhTienDen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchHoaDon();
                }
            }
        });
    }

    private void exportToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file CSV");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV files", "csv"));
        fileChooser.setSelectedFile(new File("DanhSachHoaDon.csv"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".csv")) {
                filePath += ".csv";
            }

            try (FileWriter writer = new FileWriter(filePath)) {
                DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                // Ghi tiêu đề cột
                for (int i = 0; i < model.getColumnCount(); i++) {
                    writer.write("\"" + model.getColumnName(i) + "\"");
                    if (i < model.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.write("\n");

                // Ghi dữ liệu dòng
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Object value = model.getValueAt(i, j);
                        String cellValue = value == null ? "" : value.toString();
                        // Thoát các ký tự đặc biệt trong CSV
                        cellValue = "\"" + cellValue.replace("\"", "\"\"") + "\"";
                        writer.write(cellValue);
                        if (j < model.getColumnCount() - 1) {
                            writer.write(",");
                        }
                    }
                    writer.write("\n");
                }

                JOptionPane.showMessageDialog(this,
                        "Xuất file CSV thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi xuất file CSV: " + ex.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void setupListeners() {
        // Nút Thêm
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog();
                dialog.setTitle("Thêm Hóa Đơn Mới");
                dialog.setModal(true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                themHoaDonPanel themPanel = new themHoaDonPanel();
                dialog.add(themPanel);

                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);

                loadHoaDonData();
            }
        });

        // Nút Sửa
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = jTable2.getSelectedRow();
                if (selectedRow >= 0) {
                    try {
                        String maHoaDon = getTableValueAsString(selectedRow, 1);
                        hoaDonDTO hd = hoaDonDAO.getHoaDonByMa(maHoaDon);
                        if (hd == null) {
                            JOptionPane.showMessageDialog(null,
                                    "Hóa đơn không tồn tại!",
                                    "Lỗi",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        JDialog dialog = new JDialog();
                        dialog.setTitle("Sửa Thông Tin Hóa Đơn");
                        dialog.setModal(true);
                        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                        suaHoaDonPanel suaPanel = new suaHoaDonPanel(dialog, hd);
                        dialog.add(suaPanel);

                        dialog.pack();
                        dialog.setLocationRelativeTo(null);
                        dialog.setVisible(true);

                        loadHoaDonData();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,
                                "Có lỗi xảy ra khi hiển thị form sửa thông tin: " + ex.getMessage(),
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Vui lòng chọn hóa đơn cần sửa",
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Nút Xóa
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = jTable2.getSelectedRow();
                if (selectedRow >= 0) {
                    // Vô hiệu hóa nút Xóa để tránh nhấp chuột lặp lại
                    btnXoa.setEnabled(false);

                    try {
                        String maHoaDon = getTableValueAsString(selectedRow, 1);
                        hoaDonDTO hd = hoaDonDAO.getHoaDonByMa(maHoaDon);
                        if (hd == null) {
                            JOptionPane.showMessageDialog(null,
                                    "Hóa đơn không tồn tại!",
                                    "Lỗi",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Hiển thị hộp thoại xác nhận
                        int confirm = JOptionPane.showConfirmDialog(null,
                                "Bạn có chắc chắn muốn xóa hóa đơn này?",
                                "Xác nhận xóa",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE);

                        if (confirm == JOptionPane.YES_OPTION) {
                            // Gọi xoaHoaDonPanel để xóa
                            new xoaHoaDonPanel(hd);

                            // Xóa dòng khỏi bảng thay vì tải lại toàn bộ dữ liệu
                            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                            model.removeRow(selectedRow);
                            updateTableSTT(); // Cập nhật lại STT
                            updateTongTienBanHang(); // Update total after row deletion
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,
                                "Lỗi khi xóa hóa đơn: " + ex.getMessage(),
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    } finally {
                        // Kích hoạt lại nút Xóa
                        btnXoa.setEnabled(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Vui lòng chọn hóa đơn cần xóa",
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Nút Xuất file
        btnExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jTable2.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Không có dữ liệu để xuất!",
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Tạo dialog lựa chọn kiểu xuất
                JDialog exportOptionDialog = new JDialog();
                exportOptionDialog.setTitle("Lựa chọn xuất dữ liệu");
                exportOptionDialog.setSize(400, 300);
                exportOptionDialog.setLocationRelativeTo(null);
                exportOptionDialog.setLayout(new BorderLayout());
                exportOptionDialog.setModal(true);
                
                JPanel optionPanel = new JPanel();
                optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
                
                // Radio buttons cho việc lựa chọn
                JRadioButton rbExportAll = new JRadioButton("Xuất toàn bộ hóa đơn");
                JRadioButton rbExportByCustomer = new JRadioButton("Xuất hóa đơn theo khách hàng và thời gian");
                ButtonGroup group = new ButtonGroup();
                group.add(rbExportAll);
                group.add(rbExportByCustomer);
                rbExportAll.setSelected(true);
                
                optionPanel.add(Box.createVerticalStrut(20));
                optionPanel.add(rbExportAll);
                optionPanel.add(Box.createVerticalStrut(10));
                optionPanel.add(rbExportByCustomer);
                optionPanel.add(Box.createVerticalStrut(20));
                
                // Panel cho lựa chọn khách hàng và thời gian
                JPanel customerTimePanel = new JPanel();
                customerTimePanel.setLayout(new GridLayout(2, 2, 10, 10));
                customerTimePanel.setBorder(BorderFactory.createTitledBorder("Lựa chọn khách hàng và thời gian"));
                
                JComboBox<String> customerComboBox = new JComboBox<>();
                JComboBox<String> timeComboBox = new JComboBox<>();
                
                // Disable ban đầu
                customerComboBox.setEnabled(false);
                timeComboBox.setEnabled(false);
                
                // Lấy danh sách khách hàng và thời gian từ dữ liệu hiện có
                List<String> uniqueCustomers = new ArrayList<>();
                List<Timestamp> uniqueTimestamps = new ArrayList<>();
                DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                
                for (int i = 0; i < model.getRowCount(); i++) {
                    String maKhachHang = model.getValueAt(i, 4).toString(); // Cột 4 là mã khách hàng
                    String tenKhachHang = model.getValueAt(i, 5).toString(); // Cột 5 là tên khách hàng
                    String customerInfo = maKhachHang + " - " + tenKhachHang;
                    
                    if (!uniqueCustomers.contains(customerInfo)) {
                        uniqueCustomers.add(customerInfo);
                        customerComboBox.addItem(customerInfo);
                    }
                }
                
                // Cập nhật combo box đợt xuất khi chọn khách hàng
                customerComboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        timeComboBox.removeAllItems(); // Conceptually seriesComboBox
                        
                        if (customerComboBox.getSelectedItem() != null) {
                            String selectedCustomer = customerComboBox.getSelectedItem().toString();
                            String maKhachHang = selectedCustomer.split(" - ")[0];
                            
                            Set<String> uniqueSeriesParts = new HashSet<>();
                            
                            for (int i = 0; i < model.getRowCount(); i++) {
                                if (model.getValueAt(i, 4).toString().equals(maKhachHang)) { // MaKhachHang is at index 4
                                    String maHoaDon = model.getValueAt(i, 1).toString(); // MaHoaDon is at index 1
                                    if (maHoaDon != null && maHoaDon.contains("_")) {
                                        String series = maHoaDon.substring(0, maHoaDon.indexOf('_'));
                                        if (!uniqueSeriesParts.contains(series)) {
                                            uniqueSeriesParts.add(series);
                                            timeComboBox.addItem(series); // Add unique series to the combo box
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
                
                customerTimePanel.add(new JLabel("Khách hàng:"));
                customerTimePanel.add(customerComboBox);
                customerTimePanel.add(new JLabel("Đợt xuất (Mã HD series):")); // Changed Label
                customerTimePanel.add(timeComboBox); // This is now seriesComboBox
                
                optionPanel.add(customerTimePanel);
                
                // Enable/disable panel dựa trên lựa chọn radio button
                rbExportByCustomer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        customerComboBox.setEnabled(true);
                        timeComboBox.setEnabled(true);
                    }
                });
                
                rbExportAll.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        customerComboBox.setEnabled(false);
                        timeComboBox.setEnabled(false);
                    }
                });
                
                // Panel nút
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                JButton btnOK = new JButton("Xác nhận");
                JButton btnCancel = new JButton("Hủy");
                
                btnOK.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        exportOptionDialog.dispose();
                        
                        List<hoaDonDTO> hoaDonList;
                        HoaDonBUS hoaDonBUS = new HoaDonBUS();
                        
                        if (rbExportAll.isSelected()) {
                            // Xuất toàn bộ hóa đơn
                            hoaDonList = hoaDonBUS.getAllHoaDon();
                        } else {
                            // Xuất theo khách hàng và thời gian
                            if (customerComboBox.getSelectedItem() == null || timeComboBox.getSelectedItem() == null) {
                                JOptionPane.showMessageDialog(null, 
                                        "Vui lòng chọn khách hàng và thời gian", 
                                        "Thông báo", 
                                        JOptionPane.WARNING_MESSAGE);
                                return;
                            }
                            
                            String selectedCustomer = customerComboBox.getSelectedItem().toString();
                            String maKhachHang = selectedCustomer.split(" - ")[0];
                            String seriesPart = timeComboBox.getSelectedItem().toString(); // Selected series part
                            
                            // No longer parsing timestamp, directly use seriesPart
                            hoaDonList = hoaDonBUS.getHoaDonByKhachHangAndSeries(maKhachHang, seriesPart); 
                        }
                        
                        if (hoaDonList.isEmpty()) {
                            JOptionPane.showMessageDialog(null, 
                                    "Không có dữ liệu hóa đơn để xuất", 
                                    "Thông báo", 
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        
                        // Hiển thị lựa chọn định dạng xuất
                        String[] options = { "Excel (.xlsx)", "Word (.docx)" };
                        int choice = JOptionPane.showOptionDialog(null, 
                                "Chọn định dạng file xuất cho Hóa Đơn:", 
                                "Xuất File Hóa Đơn",
                                JOptionPane.DEFAULT_OPTION, 
                                JOptionPane.QUESTION_MESSAGE, 
                                null, 
                                options, 
                                options[0]);
                        
                        // Chuẩn bị tệp tạm thời nếu cần
                        String title = "DanhSachHoaDon";
                        if (rbExportByCustomer.isSelected()) {
                            String selectedCustomer = customerComboBox.getSelectedItem().toString();
                            String tenKhachHang = selectedCustomer.split(" - ")[1];
                            title = "HoaDon_" + tenKhachHang.replace(" ", "_");
                        }
                        
                        // Tiến hành xuất file theo định dạng đã chọn
                        if (choice == 0) {
                            // Xuất Excel
                            JFileChooser fileChooser = new JFileChooser();
                            fileChooser.setDialogTitle("Chọn nơi lưu file Excel Hóa Đơn");
                            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));
                            fileChooser.setSelectedFile(new File(title + ".xlsx"));

                            int userSelection = fileChooser.showSaveDialog(null);
                            if (userSelection == JFileChooser.APPROVE_OPTION) {
                                File fileToSave = fileChooser.getSelectedFile();
                                String filePath = fileToSave.getAbsolutePath();
                                if (!filePath.toLowerCase().endsWith(".xlsx")) {
                                    filePath += ".xlsx";
                                }
                                
                                exportHoaDonToExcel(hoaDonList, filePath);
                            }
                        } else if (choice == 1) {
                            // Xuất Word
                            JFileChooser fileChooser = new JFileChooser();
                            fileChooser.setDialogTitle("Chọn nơi lưu file Word Hóa Đơn");
                            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Word files (*.docx)", "docx"));
                            fileChooser.setSelectedFile(new File(title + ".docx"));

                            int userSelection = fileChooser.showSaveDialog(null);
                            if (userSelection == JFileChooser.APPROVE_OPTION) {
                                File fileToSave = fileChooser.getSelectedFile();
                                String filePath = fileToSave.getAbsolutePath();
                                if (!filePath.toLowerCase().endsWith(".docx")) {
                                    filePath += ".docx";
                                }
                                
                                exportHoaDonToWord(hoaDonList, filePath);
                            }
                        }
                    }
                });
                
                btnCancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        exportOptionDialog.dispose();
                    }
                });
                
                buttonPanel.add(btnOK);
                buttonPanel.add(btnCancel);
                
                exportOptionDialog.add(optionPanel, BorderLayout.CENTER);
                exportOptionDialog.add(buttonPanel, BorderLayout.SOUTH);
                exportOptionDialog.setVisible(true);
            }
        });

        // Nút Import
        btnImport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                utils.FileUtils.importFromExcelForHoaDon(jTable2);
                loadHoaDonData(); // This will call updateTableData which calls updateTongTienBanHang
            }
        });

        // Nút In
        btnPrinter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (jTable2.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Không có dữ liệu để in!",
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Tạo dialog lựa chọn kiểu in
                JDialog printOptionDialog = new JDialog();
                printOptionDialog.setTitle("Lựa chọn in dữ liệu");
                printOptionDialog.setSize(400, 300);
                printOptionDialog.setLocationRelativeTo(null);
                printOptionDialog.setLayout(new BorderLayout());
                printOptionDialog.setModal(true);

                JPanel optionPanel = new JPanel();
                optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));

                JRadioButton rbPrintAll = new JRadioButton("In toàn bộ hóa đơn");
                JRadioButton rbPrintByCustomer = new JRadioButton("In hóa đơn theo khách hàng và đợt xuất");
                ButtonGroup group = new ButtonGroup();
                group.add(rbPrintAll);
                group.add(rbPrintByCustomer);
                rbPrintAll.setSelected(true);

                optionPanel.add(Box.createVerticalStrut(20));
                optionPanel.add(rbPrintAll);
                optionPanel.add(Box.createVerticalStrut(10));
                optionPanel.add(rbPrintByCustomer);
                optionPanel.add(Box.createVerticalStrut(20));

                JPanel customerSeriesPanel = new JPanel();
                customerSeriesPanel.setLayout(new GridLayout(2, 2, 10, 10));
                customerSeriesPanel.setBorder(BorderFactory.createTitledBorder("Lựa chọn khách hàng và đợt xuất"));

                JComboBox<String> customerComboBox = new JComboBox<>();
                JComboBox<String> seriesComboBox = new JComboBox<>();

                customerComboBox.setEnabled(false);
                seriesComboBox.setEnabled(false);

                DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                List<String> uniqueCustomers = new ArrayList<>();
                for (int i = 0; i < model.getRowCount(); i++) {
                    String maKhachHang = model.getValueAt(i, 4).toString();
                    String tenKhachHang = model.getValueAt(i, 5).toString();
                    String customerInfo = maKhachHang + " - " + tenKhachHang;
                    if (!uniqueCustomers.contains(customerInfo)) {
                        uniqueCustomers.add(customerInfo);
                        customerComboBox.addItem(customerInfo);
                    }
                }

                customerComboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        seriesComboBox.removeAllItems();
                        if (customerComboBox.getSelectedItem() != null) {
                            String selectedCustomer = customerComboBox.getSelectedItem().toString();
                            String maKhachHang = selectedCustomer.split(" - ")[0];
                            Set<String> uniqueSeriesParts = new HashSet<>();
                            for (int i = 0; i < model.getRowCount(); i++) {
                                if (model.getValueAt(i, 4).toString().equals(maKhachHang)) {
                                    String maHoaDon = model.getValueAt(i, 1).toString();
                                    if (maHoaDon != null && maHoaDon.contains("_")) {
                                        String series = maHoaDon.substring(0, maHoaDon.indexOf('_'));
                                        if (!uniqueSeriesParts.contains(series)) {
                                            uniqueSeriesParts.add(series);
                                            seriesComboBox.addItem(series);
                                        }
                                    }
                                }
                            }
                        }
                    }
                });

                customerSeriesPanel.add(new JLabel("Khách hàng:"));
                customerSeriesPanel.add(customerComboBox);
                customerSeriesPanel.add(new JLabel("Đợt xuất (Mã HD series):"));
                customerSeriesPanel.add(seriesComboBox);
                optionPanel.add(customerSeriesPanel);

                rbPrintByCustomer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        customerComboBox.setEnabled(true);
                        seriesComboBox.setEnabled(true);
                    }
                });

                rbPrintAll.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        customerComboBox.setEnabled(false);
                        seriesComboBox.setEnabled(false);
                    }
                });

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                JButton btnOK = new JButton("Xác nhận In");
                JButton btnCancel = new JButton("Hủy");

                btnOK.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        printOptionDialog.dispose();
                        List<hoaDonDTO> hoaDonListToPrint;
                        HoaDonBUS hoaDonBUS = new HoaDonBUS();

                        if (rbPrintAll.isSelected()) {
                            hoaDonListToPrint = hoaDonBUS.getAllHoaDon();
                        } else {
                            if (customerComboBox.getSelectedItem() == null || seriesComboBox.getSelectedItem() == null) {
                                JOptionPane.showMessageDialog(null,
                                        "Vui lòng chọn khách hàng và đợt xuất",
                                        "Thông báo",
                                        JOptionPane.WARNING_MESSAGE);
                                return;
                            }
                            String selectedCustomer = customerComboBox.getSelectedItem().toString();
                            String maKhachHang = selectedCustomer.split(" - ")[0];
                            String seriesPart = seriesComboBox.getSelectedItem().toString();
                            hoaDonListToPrint = hoaDonBUS.getHoaDonByKhachHangAndSeries(maKhachHang, seriesPart);
                        }

                        if (hoaDonListToPrint == null || hoaDonListToPrint.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Không có dữ liệu hóa đơn để in.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                        printSelectedHoaDon(hoaDonListToPrint);
                    }
                });

                btnCancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        printOptionDialog.dispose();
                    }
                });

                buttonPanel.add(btnOK);
                buttonPanel.add(btnCancel);

                printOptionDialog.add(optionPanel, BorderLayout.CENTER);
                printOptionDialog.add(buttonPanel, BorderLayout.SOUTH);
                printOptionDialog.setVisible(true);
            }
        });
    }

    private void printSelectedHoaDon(List<hoaDonDTO> hoaDonList) {
        try {
            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><head><style>");
            htmlContent.append("body { font-family: Arial, sans-serif; margin: 20px; }");
            htmlContent.append("h1 { text-align: center; color: #333; }");
            htmlContent.append(".invoice-record { border: 1px solid #ccc; padding: 10px; margin-bottom: 15px; border-radius: 5px; page-break-inside: avoid; }");
            htmlContent.append(".field-label { font-weight: bold; color: #555; }");
            htmlContent.append("p { margin: 5px 0; }");
            htmlContent.append("</style></head><body>");
            htmlContent.append("<h1>Danh Sách Chi Tiết Hóa Đơn</h1>");

            for (hoaDonDTO hd : hoaDonList) {
                htmlContent.append("<div class='invoice-record'>");
                htmlContent.append("<p><span class='field-label'>Mã Hóa Đơn:</span> ").append(hd.getMaHoaDon() != null ? hd.getMaHoaDon() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Mã Sản Phẩm:</span> ").append(hd.getMaSanPham() != null ? hd.getMaSanPham() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Tên Sản Phẩm:</span> ").append(hd.getTenSanPham() != null ? hd.getTenSanPham() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Mã Khách Hàng:</span> ").append(hd.getMaKhachHang() != null ? hd.getMaKhachHang() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Tên Khách Hàng:</span> ").append(hd.getTenKhachHang() != null ? hd.getTenKhachHang() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Kích Cỡ:</span> ").append(hd.getKichCo() != null ? hd.getKichCo() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Màu Sắc:</span> ").append(hd.getMauSac() != null ? hd.getMauSac() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Số Lượng:</span> ").append(decimalFormat.format(hd.getSoLuong())).append("</p>");
                htmlContent.append("<p><span class='field-label'>Đơn Giá:</span> ").append(decimalFormat.format(hd.getDonGia())).append("</p>");
                htmlContent.append("<p><span class='field-label'>Thành Tiền:</span> ").append(decimalFormat.format(hd.getThanhTien())).append("</p>");
                htmlContent.append("<p><span class='field-label'>Thời Gian:</span> ").append(hd.getThoiGian() != null ? dateFormat.format(hd.getThoiGian()) : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Hình Thức Thanh Toán:</span> ").append(hd.getHinhThucThanhToan() != null ? hd.getHinhThucThanhToan() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Trạng Thái:</span> ").append(hd.getTrangThai() != null ? hd.getTrangThai() : "").append("</p>");
                htmlContent.append("</div>");
            }
            htmlContent.append("</body></html>");

            JEditorPane editorPane = new JEditorPane();
            editorPane.setContentType("text/html");
            editorPane.setText(htmlContent.toString());
            editorPane.setEditable(false);

            boolean printed = editorPane.print();
            if (!printed) {
                // JOptionPane.showMessageDialog(null, "Lệnh in đã bị hủy.", "In Bị Hủy", JOptionPane.WARNING_MESSAGE);
            }
        } catch (java.awt.print.PrinterException pe) {
            JOptionPane.showMessageDialog(null, "Lỗi khi in: Không tìm thấy máy in hoặc lỗi máy in.\n" + pe.getMessage(), "Lỗi In Ấn", JOptionPane.ERROR_MESSAGE);
            pe.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi chuẩn bị dữ liệu để in: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public javax.swing.JPanel getHoaDonPanel() {
        return containerPanel;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        containerPanel = new javax.swing.JPanel();
        containerPanel.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        containerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jButton30 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jLabelSoLuong = new javax.swing.JLabel();
        jTextFieldSoLuongTu = new javax.swing.JTextField();
        jTextFieldSoLuongDen = new javax.swing.JTextField();
        jLabelSoLuongTu = new javax.swing.JLabel();
        jLabelSoLuongDen = new javax.swing.JLabel();
        jLabelThanhTien = new javax.swing.JLabel();
        jTextFieldThanhTienTu = new javax.swing.JTextField();
        jTextFieldThanhTienDen = new javax.swing.JTextField();
        jLabelThanhTienTu = new javax.swing.JLabel();
        jLabelThanhTienDen = new javax.swing.JLabel();
        jButtonReset = new javax.swing.JButton();
        pnlBoxChinhSua = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        pnlBoxBangThongTin = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        btnExport = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        btnPrinter = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader.setBackground(AppColors.NEW_HEADER_PANEL_BG_COLOR);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabel5.setText("QUẢN LÝ HÓA ĐƠN");
        jLabel5.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlHeaderLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        pnlHeaderLayout.setVerticalGroup(
                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlHeaderLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addContainerGap(20, Short.MAX_VALUE)));

        containerPanel.add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 70));

        pnlContent.setBackground(AppColors.NEW_MAIN_BG_COLOR);

        jPanel33.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        javax.swing.border.TitledBorder searchBorder = javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Tìm kiếm");
        searchBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
        jPanel33.setBorder(searchBorder);
        jPanel33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel1.setText("Tìm kiếm");
        jLabel1.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        jPanel33.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 30));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
                "Tất cả", "Mã hóa đơn", "Tên khách hàng", "Mã sản phẩm", "Tên sản phẩm"
        }));
        jPanel33.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 180, 30));
        jPanel33.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, 180, 30));

        jLabelThanhTien.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelThanhTien.setText("Thành tiền:");
        jLabelThanhTien.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        jPanel33.add(jLabelThanhTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 80, 30));

        jLabelThanhTienTu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelThanhTienTu.setText("Từ:");
        jLabelThanhTienTu.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        jPanel33.add(jLabelThanhTienTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, -1, 30));
        jPanel33.add(jTextFieldThanhTienTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, 80, 30));

        jLabelThanhTienDen.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelThanhTienDen.setText("Đến:");
        jLabelThanhTienDen.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        jPanel33.add(jLabelThanhTienDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 20, -1, 30));
        jPanel33.add(jTextFieldThanhTienDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, 80, 30));

        jLabelSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelSoLuong.setText("Số lượng:");
        jLabelSoLuong.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        jPanel33.add(jLabelSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 60, 80, 30));

        jLabelSoLuongTu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelSoLuongTu.setText("Từ:");
        jLabelSoLuongTu.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        jPanel33.add(jLabelSoLuongTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, -1, 30));
        jPanel33.add(jTextFieldSoLuongTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, 80, 30));

        jLabelSoLuongDen.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelSoLuongDen.setText("Đến:");
        jLabelSoLuongDen.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        jPanel33.add(jLabelSoLuongDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 60, -1, 30));
        jPanel33.add(jTextFieldSoLuongDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 60, 80, 30));

        jButton30.setText("Tìm kiếm");
        jButton30.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        jButton30.setForeground(java.awt.Color.WHITE);
        jButton30.setPreferredSize(new java.awt.Dimension(120, 30));
        jPanel33.add(jButton30, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 20, 120, 30));

        jButtonReset.setText("Làm mới");
        jButtonReset.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        jButtonReset.setForeground(java.awt.Color.WHITE);
        ImageIcon resetIcon = new ImageIcon(getClass().getResource("/icon_img/refresh.png"));
        if (resetIcon.getImage() != null) {
            Image scaledResetIcon = resetIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            jButtonReset.setIcon(new ImageIcon(scaledResetIcon));
        }
        jButtonReset.setHorizontalTextPosition(SwingConstants.RIGHT);
        jButtonReset.setPreferredSize(new java.awt.Dimension(120, 30));
        jPanel33.add(jButtonReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 120, 30));

        pnlBoxChinhSua.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        javax.swing.border.TitledBorder editBorder = javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Bộ lọc thời gian");
        editBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlBoxChinhSua.setBorder(editBorder);
        pnlBoxChinhSua.setLayout(new BorderLayout());

        // Panel chính chứa các thành phần lọc
        JPanel mainFilterPanel = new JPanel();
        mainFilterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 10)); // Giảm padding xuống 2
        mainFilterPanel.setBackground(AppColors.NEW_MAIN_BG_COLOR);

        // Nút lọc (bên trái)
        JButton btnFilter = new JButton("Lọc");
        btnFilter.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        btnFilter.setForeground(Color.WHITE);
        btnFilter.setPreferredSize(new Dimension(100, 30));
        btnFilter.setFont(new Font("Segoe UI", Font.BOLD, 12));
        mainFilterPanel.add(btnFilter);
        mainFilterPanel.add(Box.createHorizontalStrut(8)); // Giảm xuống 8

        // Panel cho ngày bắt đầu
        JPanel fromDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0)); // Giảm padding xuống 2
        fromDatePanel.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        JLabel lblFromDate = new JLabel("Từ ngày:");
        lblFromDate.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        lblFromDate.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // ComboBox cho ngày bắt đầu
        JComboBox<Integer> cbFromYear = new JComboBox<>(new Integer[]{2020, 2021, 2022, 2023, 2024, 2025});
        JComboBox<Integer> cbFromMonth = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        JComboBox<Integer> cbFromDay = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31});

        // Thiết lập kích thước cho các combo box
        Dimension cbSize = new Dimension(80, 30);
        cbFromYear.setPreferredSize(cbSize);
        cbFromMonth.setPreferredSize(cbSize);
        cbFromDay.setPreferredSize(cbSize);

        fromDatePanel.add(lblFromDate);
        fromDatePanel.add(Box.createHorizontalStrut(3)); // Giảm xuống 3
        fromDatePanel.add(cbFromDay);
        fromDatePanel.add(new JLabel("/"));
        fromDatePanel.add(cbFromMonth);
        fromDatePanel.add(new JLabel("/"));
        fromDatePanel.add(cbFromYear);
        mainFilterPanel.add(fromDatePanel);

        mainFilterPanel.add(Box.createHorizontalStrut(10)); // Giảm xuống 10

        // Panel cho ngày kết thúc
        JPanel toDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0)); // Giảm padding xuống 2
        toDatePanel.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        JLabel lblToDate = new JLabel("Đến ngày:");
        lblToDate.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        lblToDate.setFont(new Font("Segoe UI", Font.BOLD, 12));

        // ComboBox cho ngày kết thúc
        JComboBox<Integer> cbToYear = new JComboBox<>(new Integer[]{2020, 2021, 2022, 2023, 2024, 2025});
        JComboBox<Integer> cbToMonth = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        JComboBox<Integer> cbToDay = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31});

        // Thiết lập kích thước cho các combo box
        cbToYear.setPreferredSize(cbSize);
        cbToMonth.setPreferredSize(cbSize);
        cbToDay.setPreferredSize(cbSize);

        toDatePanel.add(lblToDate);
        toDatePanel.add(Box.createHorizontalStrut(3)); // Giảm xuống 3
        toDatePanel.add(cbToDay);
        toDatePanel.add(new JLabel("/"));
        toDatePanel.add(cbToMonth);
        toDatePanel.add(new JLabel("/"));
        toDatePanel.add(cbToYear);
        mainFilterPanel.add(toDatePanel);

        // Tạo panel riêng cho nút xóa để căn phải
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        
        // Thêm JSeparator
        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        separator.setPreferredSize(new Dimension(1, 25));
        separator.setForeground(AppColors.NEW_BORDER_LINES_COLOR);
        rightPanel.add(Box.createHorizontalStrut(10));
        rightPanel.add(separator);
        rightPanel.add(Box.createHorizontalStrut(10));
        
        // Nút xóa (bên phải)
        btnXoa.setText("Xóa");
        btnXoa.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setPreferredSize(new Dimension(100, 30));
        btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 12));
        rightPanel.add(btnXoa);

        // Panel wrapper để chứa cả mainFilterPanel và rightPanel
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        wrapperPanel.add(mainFilterPanel, BorderLayout.WEST);
        wrapperPanel.add(rightPanel, BorderLayout.EAST);

        // Thêm action listener cho nút lọc
        btnFilter.addActionListener(e -> {
            Integer fromYear = (Integer) cbFromYear.getSelectedItem();
            Integer fromMonth = (Integer) cbFromMonth.getSelectedItem();
            Integer fromDay = (Integer) cbFromDay.getSelectedItem();
            Integer toYear = (Integer) cbToYear.getSelectedItem();
            Integer toMonth = (Integer) cbToMonth.getSelectedItem();
            Integer toDay = (Integer) cbToDay.getSelectedItem();

            filterByDateRange(fromYear, fromMonth, fromDay, toYear, toMonth, toDay);
        });

        pnlBoxChinhSua.add(wrapperPanel, BorderLayout.CENTER);

        pnlBoxBangThongTin.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        javax.swing.border.TitledBorder tableBorder = javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Bảng thông tin");
        tableBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlBoxBangThongTin.setBorder(tableBorder);
        pnlBoxBangThongTin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] {
                        "STT", "Mã HD", "Mã SP", "Tên SP", "Mã KH", "Tên KH", "Kích cỡ", "Màu sắc", "Số lượng",
                        "Đơn giá", "Thành tiền", "Thời gian", "Hình thức TT", "Trạng Thái", "Chi tiết"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false, false, false, false, false, false, false, false, false,
                    false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jTable2.setShowGrid(true);
        jTable2.setBackground(java.awt.Color.WHITE);
        jTable2.getTableHeader().setBackground(AppColors.NEW_HEADER_PANEL_BG_COLOR);
        jTable2.getTableHeader().setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        jTable2.setGridColor(AppColors.NEW_BORDER_LINES_COLOR);
        jScrollPane2.setViewportView(jTable2);
        pnlBoxBangThongTin.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 941, 292));

        pnlBottomButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 10)); // Matches phieunhap.java
        pnlBottomButtons.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        Dimension bottomButtonSize = new Dimension(170, 40); // Matches phieunhap.java

        // Add Tong Tien Ban Hang display FIRST
        lblTongTienBanHang = new JLabel("Tổng tiền bán hàng:");
        lblTongTienBanHang.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTongTienBanHang.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlBottomButtons.add(lblTongTienBanHang);

        txtTongTienBanHang = new JTextField("0.00");
        txtTongTienBanHang.setEditable(false);
        txtTongTienBanHang.setPreferredSize(new Dimension(150, 30)); // Matches phieunhap.java
        txtTongTienBanHang.setHorizontalAlignment(JTextField.RIGHT);
        txtTongTienBanHang.setBackground(java.awt.Color.WHITE);
        txtTongTienBanHang.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        txtTongTienBanHang.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pnlBottomButtons.add(txtTongTienBanHang);
        
        // THEN add the buttons, maintaining original spacing logic from phieunhap.java if any
        // In phieunhap, buttons are added directly after the total display.
        // The FlowLayout with hgap=30 will handle spacing between (txtTongTienBanHang) and (btnImport)

        btnImport.setText("Nhập dữ liệu");
        btnImport.setBackground(AppColors.NEW_QUICK_ACCESS_BUTTON_BG_COLOR);
        btnImport.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR);
        btnImport.setPreferredSize(bottomButtonSize);
        pnlBottomButtons.add(btnImport);

        btnExport.setText("Xuất dữ liệu");
        btnExport.setBackground(AppColors.NEW_QUICK_ACCESS_BUTTON_BG_COLOR);
        btnExport.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR);
        btnExport.setPreferredSize(bottomButtonSize);
        pnlBottomButtons.add(btnExport);

        btnPrinter.setText("In ấn");
        btnPrinter.setBackground(AppColors.NEW_QUICK_ACCESS_BUTTON_BG_COLOR);
        btnPrinter.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR);
        btnPrinter.setPreferredSize(bottomButtonSize);
        pnlBottomButtons.add(btnPrinter);

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE)
                    .addComponent(pnlBoxChinhSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlBoxBangThongTin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlBottomButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBoxChinhSua, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBoxBangThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlBottomButtons, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        containerPanel.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1000, 630));

        add(containerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 700));
    }

    private void setupUI() {
        jPanel33.setPreferredSize(new java.awt.Dimension(961, 120));
        pnlBoxChinhSua.setPreferredSize(new java.awt.Dimension(961, 90));
        pnlBoxBangThongTin.setPreferredSize(new java.awt.Dimension(961, 352));
    }

    // Khai báo biến
    private javax.swing.JPanel containerPanel;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelSoLuong;
    private javax.swing.JLabel jLabelSoLuongTu;
    private javax.swing.JLabel jLabelSoLuongDen;
    private javax.swing.JLabel jLabelThanhTien;
    private javax.swing.JLabel jLabelThanhTienTu;
    private javax.swing.JLabel jLabelThanhTienDen;
    private javax.swing.JPanel pnlBoxChinhSua;
    private javax.swing.JPanel pnlBoxBangThongTin;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextFieldSoLuongTu;
    private javax.swing.JTextField jTextFieldSoLuongDen;
    private javax.swing.JTextField jTextFieldThanhTienTu;
    private javax.swing.JTextField jTextFieldThanhTienDen;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnPrinter;
    private javax.swing.JPanel pnlBottomButtons;

    // Phương thức xuất hóa đơn sang Excel
    private void exportHoaDonToExcel(List<hoaDonDTO> hoaDonList, String filePath) {
        String[] headers = {
            "Mã Hóa Đơn", "Mã Sản Phẩm", "Tên Sản Phẩm", "Kích Cỡ", "Màu Sắc", "Số Lượng",
            "Mã Khách Hàng", "Tên Khách Hàng", "Thành Tiền", "Đơn Giá",
            "Hình Thức Thanh Toán", "Thời Gian", "Trạng Thái"
        };
        
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Hóa Đơn");
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            
            int rowNum = 1;
            for (hoaDonDTO hd : hoaDonList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(hd.getMaHoaDon());
                row.createCell(1).setCellValue(hd.getMaSanPham());
                row.createCell(2).setCellValue(hd.getTenSanPham());
                row.createCell(3).setCellValue(hd.getKichCo());
                row.createCell(4).setCellValue(hd.getMauSac());
                row.createCell(5).setCellValue(hd.getSoLuong());
                row.createCell(6).setCellValue(hd.getMaKhachHang());
                row.createCell(7).setCellValue(hd.getTenKhachHang());
                row.createCell(8).setCellValue(hd.getThanhTien());
                row.createCell(9).setCellValue(hd.getDonGia());
                row.createCell(10).setCellValue(hd.getHinhThucThanhToan());
                row.createCell(11).setCellValue(hd.getThoiGian() != null ? dateFormat.format(hd.getThoiGian()) : "");
                row.createCell(12).setCellValue(hd.getTrangThai());
            }
            
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                JOptionPane.showMessageDialog(null, "Xuất file Excel Hóa Đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel Hóa Đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Phương thức xuất hóa đơn sang Word
    private void exportHoaDonToWord(List<hoaDonDTO> hoaDonList, String filePath) {
        try (XWPFDocument document = new XWPFDocument()) {
            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setBold(true);
            titleRun.setFontSize(16);
            titleRun.setText("DANH SÁCH HÓA ĐƠN");
            titleRun.addBreak();
            
            for (hoaDonDTO hd : hoaDonList) {
                addWordParagraph(document, "Mã Hóa Đơn: " + hd.getMaHoaDon());
                addWordParagraph(document, "Mã Sản Phẩm: " + hd.getMaSanPham());
                addWordParagraph(document, "Tên Sản Phẩm: " + hd.getTenSanPham());
                addWordParagraph(document, "Kích Cỡ: " + hd.getKichCo());
                addWordParagraph(document, "Màu Sắc: " + hd.getMauSac());
                addWordParagraph(document, "Số Lượng: " + hd.getSoLuong());
                addWordParagraph(document, "Mã Khách Hàng: " + hd.getMaKhachHang());
                addWordParagraph(document, "Tên Khách Hàng: " + hd.getTenKhachHang());
                addWordParagraph(document, "Thành Tiền: " + decimalFormat.format(hd.getThanhTien()));
                addWordParagraph(document, "Đơn Giá: " + decimalFormat.format(hd.getDonGia()));
                addWordParagraph(document, "Hình Thức Thanh Toán: " + hd.getHinhThucThanhToan());
                addWordParagraph(document, "Thời Gian: " + (hd.getThoiGian() != null ? dateFormat.format(hd.getThoiGian()) : ""));
                addWordParagraph(document, "Trạng Thái: " + hd.getTrangThai());
                document.createParagraph().createRun().addBreak();
            }
            
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                document.write(fileOut);
                JOptionPane.showMessageDialog(null, "Xuất file Word Hóa Đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Word Hóa Đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Method for adding paragraphs to Word document
    private void addWordParagraph(XWPFDocument document, String text) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(text);
    }

    // Custom Renderer for Row Coloring and Cell Alignment
    private class CustomRowRenderer extends DefaultTableCellRenderer {
        private Set<Integer> centeredColumns;

        public CustomRowRenderer() {
            super();
            // Columns to be centered: STT, Mã HĐ, Mã SP, Mã KH, Kích cỡ, Màu sắc, Số lượng, Đơn giá, Thành tiền, Trạng Thái, Chi tiết
            centeredColumns = new HashSet<>(Arrays.asList(0, 1, 2, 4, 6, 7, 8, 9, 10, 13, 14));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (!isSelected) {
                Object maHoaDonObj = table.getModel().getValueAt(row, 1); // MaHoaDon is at column 1
                String maHoaDonFull = maHoaDonObj != null ? maHoaDonObj.toString() : "";
                
                String prefix = "";
                if (!maHoaDonFull.isEmpty() && maHoaDonFull.contains("_")) {
                    prefix = maHoaDonFull.substring(0, maHoaDonFull.indexOf('_'));
                } else {
                    prefix = maHoaDonFull; // Use full MaHoaDon as prefix if no underscore
                }

                if (!prefix.isEmpty() && maHoaDonPrefixColorMap.containsKey(prefix)) {
                    c.setBackground(maHoaDonPrefixColorMap.get(prefix));
                } else {
                    c.setBackground(table.getBackground()); // Default background for rows without a group color
                }
                c.setForeground(table.getForeground()); // Default foreground
            } else {
                c.setBackground(table.getSelectionBackground());
                c.setForeground(table.getSelectionForeground());
            }

            if (centeredColumns.contains(column)) {
                setHorizontalAlignment(JLabel.CENTER);
            } else {
                setHorizontalAlignment(JLabel.LEFT); // Default for other columns like Tên SP, Tên KH, Hình thức TT
            }

            // Special styling for "Xem chi tiết" column (column 14)
            if (column == 14) { // "Xem chi tiết" column
                if (!isSelected) {
                     c.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR);
                }
                // Apply hover effect (assuming hoverRow property is set by mouse motion listener)
                if (table.getClientProperty("hoverRow") != null && (int) table.getClientProperty("hoverRow") == row) {
                     c.setForeground(AppColors.NEW_SELECTED_BUTTON_COLOR); // Hover color
                }
            }
            return c;
        }
    }

    private void filterByDateRange(Integer fromYear, Integer fromMonth, Integer fromDay,
                                 Integer toYear, Integer toMonth, Integer toDay) {
        try {
            List<hoaDonDTO> allHoaDon = hoaDonDAO.getAllHoaDon();
            List<hoaDonDTO> filteredList = new ArrayList<>();

            // Nếu tất cả các tham số đều null, hiển thị tất cả hóa đơn
            if (fromYear == null && fromMonth == null && fromDay == null &&
                toYear == null && toMonth == null && toDay == null) {
                updateTableData(allHoaDon);
                return;
            }

            // Tạo đối tượng Calendar cho ngày bắt đầu và kết thúc
            Calendar fromDate = Calendar.getInstance();
            Calendar toDate = Calendar.getInstance();
            Calendar rowDate = Calendar.getInstance();

            // Thiết lập ngày bắt đầu
            if (fromYear != null) {
                fromDate.set(Calendar.YEAR, fromYear);
                if (fromMonth != null) {
                    fromDate.set(Calendar.MONTH, fromMonth - 1); // Calendar.MONTH bắt đầu từ 0
                    if (fromDay != null) {
                        fromDate.set(Calendar.DAY_OF_MONTH, fromDay);
                    } else {
                        fromDate.set(Calendar.DAY_OF_MONTH, 1);
                    }
                } else {
                    fromDate.set(Calendar.MONTH, 0);
                    fromDate.set(Calendar.DAY_OF_MONTH, 1);
                }
            } else {
                fromDate.set(Calendar.YEAR, 1900);
                fromDate.set(Calendar.MONTH, 0);
                fromDate.set(Calendar.DAY_OF_MONTH, 1);
            }

            // Thiết lập ngày kết thúc
            if (toYear != null) {
                toDate.set(Calendar.YEAR, toYear);
                if (toMonth != null) {
                    toDate.set(Calendar.MONTH, toMonth - 1);
                    if (toDay != null) {
                        toDate.set(Calendar.DAY_OF_MONTH, toDay);
                    } else {
                        toDate.set(Calendar.DAY_OF_MONTH, toDate.getActualMaximum(Calendar.DAY_OF_MONTH));
                    }
                } else {
                    toDate.set(Calendar.MONTH, 11);
                    toDate.set(Calendar.DAY_OF_MONTH, 31);
                }
            } else {
                toDate.set(Calendar.YEAR, 9999);
                toDate.set(Calendar.MONTH, 11);
                toDate.set(Calendar.DAY_OF_MONTH, 31);
            }

            // Set time to start and end of day
            fromDate.set(Calendar.HOUR_OF_DAY, 0);
            fromDate.set(Calendar.MINUTE, 0);
            fromDate.set(Calendar.SECOND, 0);
            fromDate.set(Calendar.MILLISECOND, 0);

            toDate.set(Calendar.HOUR_OF_DAY, 23);
            toDate.set(Calendar.MINUTE, 59);
            toDate.set(Calendar.SECOND, 59);
            toDate.set(Calendar.MILLISECOND, 999);

            // Lọc danh sách hóa đơn
            for (hoaDonDTO hd : allHoaDon) {
                if (hd.getThoiGian() != null) {
                    rowDate.setTimeInMillis(hd.getThoiGian().getTime());
                    if (rowDate.after(fromDate) && rowDate.before(toDate) || 
                        rowDate.equals(fromDate) || rowDate.equals(toDate)) {
                        filteredList.add(hd);
                    }
                }
            }

            updateTableData(filteredList);

            if (filteredList.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Không tìm thấy hóa đơn nào trong khoảng thời gian đã chọn",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            updateTongTienBanHang(); // Update total after filtering by date
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi lọc dữ liệu: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updateTongTienBanHang() {
        double tongTien = 0;
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        int thanhTienColIndex = -1;

        // Find the "Thành tiền" column index dynamically
        for (int col = 0; col < model.getColumnCount(); col++) {
            if (model.getColumnName(col).equals("Thành tiền")) {
                thanhTienColIndex = col;
                break;
            }
        }

        if (thanhTienColIndex == -1) {
            System.err.println("updateTongTienBanHang: Column 'Thành tiền' not found.");
            if (txtTongTienBanHang != null) {
                txtTongTienBanHang.setText("Error");
            }
            return;
        }

        for (int i = 0; i < model.getRowCount(); i++) {
            Object thanhTienObj = model.getValueAt(i, thanhTienColIndex);
            if (thanhTienObj != null) {
                try {
                    Number parsedNumber = decimalFormat.parse(thanhTienObj.toString());
                    tongTien += parsedNumber.doubleValue();
                } catch (java.text.ParseException e) {
                    System.err.println("Lỗi parse double ở hàng " + i + " cột 'Thành tiền' trong Hóa Đơn: " + thanhTienObj + " Error: " + e.getMessage());
                }
            }
        }
        if (txtTongTienBanHang != null) {
            txtTongTienBanHang.setText(decimalFormat.format(tongTien));
        }
    }
}