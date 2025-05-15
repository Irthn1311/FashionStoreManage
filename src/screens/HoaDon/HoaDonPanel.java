package screens.HoaDon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import DAO.HoaDonDAO;
import DTO.hoaDonDTO;
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

public class HoaDonPanel extends javax.swing.JPanel {
    private HoaDonDAO hoaDonDAO;
    private SimpleDateFormat dateFormat;
    private DecimalFormat decimalFormat;

    public HoaDonPanel() {
        initComponents();
        setupUI();

        // Khởi tạo DAO và định dạng
        hoaDonDAO = new HoaDonDAO();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        decimalFormat = new DecimalFormat("#,###.##");

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
                jButton31.setIcon(new ImageIcon(scaledAddIcon));
                jButton31.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                jButton31.setIconTextGap(5);
            }

            ImageIcon editIcon = new ImageIcon(getClass().getResource("/icon_img/edit.png"));
            if (editIcon.getImage() != null) {
                Image scaledEditIcon = editIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                jButton32.setIcon(new ImageIcon(scaledEditIcon));
                jButton32.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                jButton32.setIconTextGap(5);
            }

            ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/icon_img/delete.png"));
            if (deleteIcon.getImage() != null) {
                Image scaledDeleteIcon = deleteIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                jButton33.setIcon(new ImageIcon(scaledDeleteIcon));
                jButton33.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                jButton33.setIconTextGap(5);
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
                loadHoaDonData();
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tìm kiếm hóa đơn: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
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
        jButton31.addActionListener(new ActionListener() {
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
        jButton32.addActionListener(new ActionListener() {
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
        jButton33.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = jTable2.getSelectedRow();
                if (selectedRow >= 0) {
                    // Vô hiệu hóa nút Xóa để tránh nhấp chuột lặp lại
                    jButton33.setEnabled(false);

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
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,
                                "Lỗi khi xóa hóa đơn: " + ex.getMessage(),
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    } finally {
                        // Kích hoạt lại nút Xóa
                        jButton33.setEnabled(true);
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
                utils.FileUtils.showExportOptionsForHoaDon(jTable2, "DanhSachHoaDon");
            }
        });

        // Nút Import
        btnImport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                utils.FileUtils.importFromExcelForHoaDon(jTable2);
                loadHoaDonData();
            }
        });

        // Nút In
        btnPrinter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    List<hoaDonDTO> hoaDonList = hoaDonDAO.getAllHoaDon();

                    if (hoaDonList == null || hoaDonList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Không có dữ liệu hóa đơn để in.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

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
                    JOptionPane.showMessageDialog(null, "Lỗi khi in: Không tìm thấy máy in hoặc lỗi máy in.\\n" + pe.getMessage(), "Lỗi In Ấn", JOptionPane.ERROR_MESSAGE);
                    pe.printStackTrace();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi chuẩn bị dữ liệu để in: " + e.getMessage(), "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });
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
        jPanel17 = new javax.swing.JPanel();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
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

        jPanel17.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        javax.swing.border.TitledBorder editBorder = javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Chỉnh sửa");
        editBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
        jPanel17.setBorder(editBorder);
        jPanel17.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 23));

        Dimension editButtonSize = new java.awt.Dimension(120, 34);

        jButton31.setText("Thêm");
        jButton31.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        jButton31.setForeground(java.awt.Color.WHITE);
        jButton31.setPreferredSize(editButtonSize);
        jPanel17.add(jButton31);

        jButton32.setText("Sửa");
        jButton32.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        jButton32.setForeground(java.awt.Color.WHITE);
        jButton32.setPreferredSize(editButtonSize);
        jPanel17.add(jButton32);

        jButton33.setText("Xóa");
        jButton33.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        jButton33.setForeground(java.awt.Color.WHITE);
        jButton33.setPreferredSize(editButtonSize);
        jPanel17.add(jButton33);

        jPanel18.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        javax.swing.border.TitledBorder tableBorder = javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Bảng thông tin");
        tableBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
        jPanel18.setBorder(tableBorder);
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel18.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 941, 292));

        pnlBottomButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        pnlBottomButtons.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        Dimension bottomButtonSize = new Dimension(170, 40);

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
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlBottomButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlBottomButtons, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        containerPanel.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1000, 630));

        add(containerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 700));
    }

    private void setupUI() {
        jPanel33.setPreferredSize(new java.awt.Dimension(961, 120));
        jPanel17.setPreferredSize(new java.awt.Dimension(961, 90));
        jPanel18.setPreferredSize(new java.awt.Dimension(961, 352));
    }

    // Khai báo biến
    private javax.swing.JPanel containerPanel;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
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
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
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
}