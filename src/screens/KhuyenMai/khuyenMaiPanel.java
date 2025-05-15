package screens.KhuyenMai;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import BUS.KhuyenMaiService;
import DTO.khuyenMaiDTO;
import java.util.List;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;
import javax.swing.JEditorPane;
import java.text.DecimalFormat;
import screens.TrangChu.AppColors;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class khuyenMaiPanel extends javax.swing.JPanel {
        private KhuyenMaiService khuyenMaiService;
        private SimpleDateFormat dateFormat;
        
        private javax.swing.JPanel pnlBottomButtons;

        public khuyenMaiPanel() {
                initComponents();
                setupIcons();
                khuyenMaiService = new KhuyenMaiService();
                dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
                                "Tất cả", "Mã KM", "Mã sản phẩm", "Tên sản phẩm", "Tên chương trình", "Trạng thái"
                }));

                loadKhuyenMaiData();
                setupTable();
                setupTableListeners();

                jButton30.addActionListener(e -> searchKhuyenMai());

                jButton31.addActionListener(e -> {
                        List<khuyenMaiDTO> currentList = khuyenMaiService.getAllKhuyenMai();
                        Set<String> existingProductCodes = currentList.stream()
                                        .map(km -> km.getMaSanPham())
                                        .collect(Collectors.toSet());

                        KhuyenMaiEditDialog addDialog = new KhuyenMaiEditDialog(
                                        (java.awt.Frame) javax.swing.SwingUtilities
                                                        .getWindowAncestor(khuyenMaiPanel.this),
                                        null, khuyenMaiService, dateFormat, khuyenMaiTable, false, khuyenMaiPanel.this);
                        addDialog.setExistingProductCodes(existingProductCodes);
                        addDialog.setVisible(true);
                });

                jButton32.addActionListener(e -> {
                        int selectedRow = khuyenMaiTable.getSelectedRow();
                        if (selectedRow == -1) {
                                JOptionPane.showMessageDialog(khuyenMaiPanel.this,
                                                "Vui lòng chọn một chương trình khuyến mãi để sửa!");
                                return;
                        }

                        DefaultTableModel model = (DefaultTableModel) khuyenMaiTable.getModel();
                        String maKhuyenMai = model.getValueAt(selectedRow, 1).toString();
                        List<khuyenMaiDTO> khuyenMaiList = khuyenMaiService.getAllKhuyenMai();
                        khuyenMaiDTO km = khuyenMaiList.stream()
                                        .filter(k -> k.getMaKhuyenMai().equals(maKhuyenMai))
                                        .findFirst()
                                        .orElse(null);

                        if (km != null) {
                                List<khuyenMaiDTO> currentList = khuyenMaiService.getAllKhuyenMai();
                                Set<String> existingProductCodes = currentList.stream()
                                                .filter(k -> !k.getMaKhuyenMai().equals(maKhuyenMai))
                                                .map(k -> k.getMaSanPham())
                                                .collect(Collectors.toSet());

                                KhuyenMaiEditDialog editDialog = new KhuyenMaiEditDialog(
                                                (java.awt.Frame) javax.swing.SwingUtilities
                                                                .getWindowAncestor(khuyenMaiPanel.this),
                                                km, khuyenMaiService, dateFormat, khuyenMaiTable, true,
                                                khuyenMaiPanel.this);
                                editDialog.setExistingProductCodes(existingProductCodes);
                                editDialog.setVisible(true);
                        }
                });

                jButton33.addActionListener(e -> {
                        int selectedRow = khuyenMaiTable.getSelectedRow();
                        if (selectedRow == -1) {
                                JOptionPane.showMessageDialog(khuyenMaiPanel.this,
                                                "Vui lòng chọn một chương trình khuyến mãi để xóa!",
                                                "Thông báo",
                                                JOptionPane.WARNING_MESSAGE);
                                return;
                        }

                        DefaultTableModel model = (DefaultTableModel) khuyenMaiTable.getModel();
                        String maKhuyenMai = model.getValueAt(selectedRow, 1).toString();

                        int confirm = JOptionPane.showConfirmDialog(khuyenMaiPanel.this,
                                        "Bạn có chắc chắn muốn xóa chương trình khuyến mãi " + maKhuyenMai + "?",
                                        "Xác nhận xóa",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.WARNING_MESSAGE);

                        if (confirm == JOptionPane.YES_OPTION) {
                                try {
                                        boolean success = khuyenMaiService.deleteKhuyenMai(maKhuyenMai);
                                        if (success) {
                                                JOptionPane.showMessageDialog(khuyenMaiPanel.this,
                                                                "Xóa khuyến mãi thành công!",
                                                                "Thông báo",
                                                                JOptionPane.INFORMATION_MESSAGE);
                                                loadKhuyenMaiData();
                                        } else {
                                                JOptionPane.showMessageDialog(khuyenMaiPanel.this,
                                                                "Xóa khuyến mãi thất bại! Không tìm thấy khuyến mãi với mã "
                                                                                + maKhuyenMai,
                                                                "Lỗi",
                                                                JOptionPane.ERROR_MESSAGE);
                                        }
                                } catch (Exception ex) {
                                        JOptionPane.showMessageDialog(khuyenMaiPanel.this,
                                                        "Lỗi khi xóa khuyến mãi: " + ex.getMessage(),
                                                        "Lỗi",
                                                        JOptionPane.ERROR_MESSAGE);
                                }
                        }
                });

                jButton35.addActionListener(e -> {
                        loadKhuyenMaiData();
                        jTextField1.setText("");
                        giamGiaTuField.setText("");
                        giamGiaDenField.setText("");
                        giaMoiTuField.setText("");
                        giaMoiDenField.setText("");
                        jComboBox1.setSelectedIndex(0);
                });

                btnExport.setText("Xuất dữ liệu");
                ImageIcon exportIcon = new ImageIcon("src/icon_img/export_icon.png");
                btnExport.setIcon(new ImageIcon(
                                exportIcon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
                btnExport.setHorizontalTextPosition(SwingConstants.RIGHT);
                btnExport.setPreferredSize(new java.awt.Dimension(170, 40));
                btnExport.addActionListener(e -> {
                        if (khuyenMaiTable.getRowCount() == 0) {
                                JOptionPane.showMessageDialog(null, "Không có dữ liệu để xuất!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                                return;
                        }
                        utils.FileUtils.showExportOptionsForKhuyenMai(khuyenMaiTable, "DanhSachKhuyenMai");
                });

                btnImport = new javax.swing.JButton("Nhập dữ liệu");
                ImageIcon importIcon = new ImageIcon("src/icon_img/import_icon.png");
                btnImport.setIcon(new ImageIcon(
                                importIcon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
                btnImport.setHorizontalTextPosition(SwingConstants.RIGHT);
                btnImport.setPreferredSize(new java.awt.Dimension(170, 40));
                btnImport.addActionListener(e -> {
                        utils.FileUtils.importFromExcelForKhuyenMai(khuyenMaiTable);
                        loadKhuyenMaiData(); 
                });

                btnPrinter = new javax.swing.JButton("In ấn");
                ImageIcon printIcon = new ImageIcon("src/icon_img/print_icon.png");
                btnPrinter.setIcon(new ImageIcon(
                                printIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
                btnPrinter.setHorizontalTextPosition(SwingConstants.RIGHT);
                btnPrinter.setPreferredSize(new java.awt.Dimension(100, 40));
                btnPrinter.addActionListener(e -> {
                        try {
                                List<khuyenMaiDTO> khuyenMaiList = khuyenMaiService.getAllKhuyenMai();

                                if (khuyenMaiList == null || khuyenMaiList.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "Không có dữ liệu khuyến mãi để in.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                        return;
                                }

                                StringBuilder htmlContent = new StringBuilder();
                                htmlContent.append("<html><head><style>");
                                htmlContent.append("body { font-family: Arial, sans-serif; margin: 20px; }");
                                htmlContent.append("h1 { text-align: center; color: #333; }");
                                htmlContent.append(".promo-record { border: 1px solid #ccc; padding: 10px; margin-bottom: 15px; border-radius: 5px; page-break-inside: avoid; }");
                                htmlContent.append(".field-label { font-weight: bold; color: #555; }");
                                htmlContent.append("p { margin: 5px 0; }");
                                htmlContent.append("</style></head><body>");
                                htmlContent.append("<h1>Danh Sách Chi Tiết Khuyến Mãi</h1>");

                                DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
                                DecimalFormat percentFormat = new DecimalFormat("#,##0.00'#'");

                                for (khuyenMaiDTO km : khuyenMaiList) {
                                        htmlContent.append("<div class='promo-record'>");
                                        htmlContent.append("<p><span class='field-label'>Mã Khuyến Mãi:</span> ").append(km.getMaKhuyenMai() != null ? km.getMaKhuyenMai() : "").append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Mã Sản Phẩm:</span> ").append(km.getMaSanPham() != null ? km.getMaSanPham() : "").append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Tên Sản Phẩm:</span> ").append(km.getTenSanPham() != null ? km.getTenSanPham() : "").append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Tên Chương Trình:</span> ").append(km.getTenChuongTrinh() != null ? km.getTenChuongTrinh() : "").append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Ngày Bắt Đầu:</span> ").append(km.getNgayBatDau() != null ? dateFormat.format(km.getNgayBatDau()) : "").append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Ngày Kết Thúc:</span> ").append(km.getNgayKetThuc() != null ? dateFormat.format(km.getNgayKetThuc()) : "").append("</p>");
                                        String giamGiaDisplay = percentFormat.format(km.getGiamGia()).replace("#", "%");
                                        htmlContent.append("<p><span class='field-label'>Giảm Giá:</span> ").append(giamGiaDisplay).append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Giá Cũ:</span> ").append(decimalFormat.format(km.getGiaCu())).append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Giá Mới:</span> ").append(decimalFormat.format(km.getGiaMoi())).append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Trạng Thái:</span> ").append(km.getTrangThai() != null ? km.getTrangThai() : "").append("</p>");
                                        htmlContent.append("</div>");
                                }
                                htmlContent.append("</body></html>");

                                JEditorPane editorPane = new JEditorPane();
                                editorPane.setContentType("text/html");
                                editorPane.setText(htmlContent.toString());
                                editorPane.setEditable(false);

                                boolean printed = editorPane.print();
                                // if (!printed) {
                                //     JOptionPane.showMessageDialog(null, "Lệnh in đã bị hủy.", "In Bị Hủy", JOptionPane.WARNING_MESSAGE);
                                // }
                        } catch (java.awt.print.PrinterException pe) {
                                JOptionPane.showMessageDialog(null, "Lỗi khi in: Không tìm thấy máy in hoặc lỗi máy in.\\n" + pe.getMessage(), "Lỗi In Ấn", JOptionPane.ERROR_MESSAGE);
                                pe.printStackTrace();
                        } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Lỗi khi chuẩn bị dữ liệu để in: " + ex.getMessage(), "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                ex.printStackTrace();
                        }
                });

                khuyenMaiTable.getColumnModel().getColumn(11).setCellRenderer(new HyperlinkRenderer());

                khuyenMaiTable.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                int column = khuyenMaiTable.columnAtPoint(e.getPoint());
                                int row = khuyenMaiTable.rowAtPoint(e.getPoint());

                                if (column == 11) {
                                        DefaultTableModel model = (DefaultTableModel) khuyenMaiTable.getModel();
                                        String maKhuyenMai = model.getValueAt(row, 1).toString();
                                        List<khuyenMaiDTO> khuyenMaiList = khuyenMaiService.getAllKhuyenMai();
                                        khuyenMaiDTO km = khuyenMaiList.stream()
                                                        .filter(k -> k.getMaKhuyenMai().equals(maKhuyenMai))
                                                        .findFirst()
                                                        .orElse(null);

                                        if (km != null) {
                                                KhuyenMaiDetailsDialog detailsDialog = new KhuyenMaiDetailsDialog(
                                                                (java.awt.Frame) javax.swing.SwingUtilities
                                                                                .getWindowAncestor(khuyenMaiPanel.this),
                                                                km, dateFormat);
                                                detailsDialog.setVisible(true);
                                        }
                                }
                        }
                });

                // Add KeyListeners for search fields like in HoaDonPanel
                jTextField1.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            searchKhuyenMai();
                        }
                    }
                });
                giamGiaTuField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            searchKhuyenMai();
                        }
                    }
                });
                giamGiaDenField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            searchKhuyenMai();
                        }
                    }
                });
                giaMoiTuField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            searchKhuyenMai();
                        }
                    }
                });
                giaMoiDenField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            searchKhuyenMai();
                        }
                    }
                });
        }

        private void setupIcons() {
                try {
                        // Search button icon (jButton30)
                        ImageIcon searchIcon = new ImageIcon(getClass().getResource("/icon_img/search.png"));
                        if (searchIcon.getImage() != null) {
                                Image scaledSearchIcon = searchIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                                jButton30.setIcon(new ImageIcon(scaledSearchIcon));
                        }

                        // Add button icon (jButton31)
                        ImageIcon addIcon = new ImageIcon(getClass().getResource("/icon_img/add.png"));
                        if (addIcon.getImage() != null) {
                                Image scaledAddIcon = addIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                                jButton31.setIcon(new ImageIcon(scaledAddIcon));
                        }

                        // Edit button icon (jButton32)
                        ImageIcon editIcon = new ImageIcon(getClass().getResource("/icon_img/edit.png"));
                        if (editIcon.getImage() != null) {
                                Image scaledEditIcon = editIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                                jButton32.setIcon(new ImageIcon(scaledEditIcon));
                        }
                        
                        // Delete button icon (jButton33)
                        ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/icon_img/delete.png"));
                        if (deleteIcon.getImage() != null) {
                                Image scaledDeleteIcon = deleteIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                                jButton33.setIcon(new ImageIcon(scaledDeleteIcon));
                        }

                        // Reset button icon (jButton35)
                        ImageIcon resetIcon = new ImageIcon(getClass().getResource("/icon_img/refresh.png"));
                        if (resetIcon.getImage() != null) {
                                Image scaledResetIcon = resetIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                                jButton35.setIcon(new ImageIcon(scaledResetIcon));
                        }

                        // Export button icon (btnExport)
                        ImageIcon exportIcon = new ImageIcon(getClass().getResource("/icon_img/export_icon.png"));
                        if (exportIcon.getImage() != null) {
                                Image scaledExportIcon = exportIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                                btnExport.setIcon(new ImageIcon(scaledExportIcon));
                        }

                        // Import button icon (btnImport)
                        ImageIcon importIcon = new ImageIcon(getClass().getResource("/icon_img/import_icon.png"));
                        if (importIcon.getImage() != null) {
                                Image scaledImportIcon = importIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                                btnImport.setIcon(new ImageIcon(scaledImportIcon));
                        }

                        // Print button icon (btnPrinter)
                        ImageIcon printIcon = new ImageIcon(getClass().getResource("/icon_img/print_icon.png"));
                        if (printIcon.getImage() != null) {
                                Image scaledPrintIcon = printIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                                btnPrinter.setIcon(new ImageIcon(scaledPrintIcon));
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
                // Column widths
                khuyenMaiTable.getColumnModel().getColumn(0).setPreferredWidth(40);  // STT
                khuyenMaiTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // Mã KM
                khuyenMaiTable.getColumnModel().getColumn(2).setPreferredWidth(80);  // Mã SP
                khuyenMaiTable.getColumnModel().getColumn(3).setPreferredWidth(120); // Tên SP
                khuyenMaiTable.getColumnModel().getColumn(4).setPreferredWidth(150); // Tên chương trình
                khuyenMaiTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Ngày bắt đầu
                khuyenMaiTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Ngày kết thúc
                khuyenMaiTable.getColumnModel().getColumn(7).setPreferredWidth(70);  // Giảm giá
                khuyenMaiTable.getColumnModel().getColumn(8).setPreferredWidth(80);  // Giá cũ
                khuyenMaiTable.getColumnModel().getColumn(9).setPreferredWidth(80);  // Giá mới
                khuyenMaiTable.getColumnModel().getColumn(10).setPreferredWidth(80); // Trạng thái
                khuyenMaiTable.getColumnModel().getColumn(11).setPreferredWidth(80); // Chi tiết

                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);

                khuyenMaiTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // STT
                khuyenMaiTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Mã KM
                khuyenMaiTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Mã SP
                khuyenMaiTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); // Ngày bắt đầu
                khuyenMaiTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer); // Ngày kết thúc
                khuyenMaiTable.getColumnModel().getColumn(7).setCellRenderer(centerRenderer); // Giảm giá
                khuyenMaiTable.getColumnModel().getColumn(8).setCellRenderer(centerRenderer); // Giá cũ
                khuyenMaiTable.getColumnModel().getColumn(9).setCellRenderer(centerRenderer); // Giá mới
                khuyenMaiTable.getColumnModel().getColumn(10).setCellRenderer(centerRenderer); // Trạng thái
                
                // Custom renderer for "Chi tiết" column to look like a hyperlink
                khuyenMaiTable.getColumnModel().getColumn(11).setCellRenderer(new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value,
                            boolean isSelected, boolean hasFocus, int row, int column) {
                        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        if (isSelected) {
                            c.setForeground(table.getSelectionForeground());
                            c.setBackground(table.getSelectionBackground());
                        } else {
                            c.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR); // Hyperlink color
                            c.setBackground(table.getBackground());
                        }
                         if (table.getClientProperty("hoverRow") != null && (int) table.getClientProperty("hoverRow") == row && column == 11) {
                            c.setForeground(AppColors.NEW_SELECTED_BUTTON_COLOR);
                        }
                        setHorizontalAlignment(JLabel.CENTER);
                        setText("<html><u>" + value.toString() + "</u></html>"); // Underline text
                        return c;
                    }
                });
                khuyenMaiTable.setRowHeight(25);
        }
        
        private void setupTableListeners() {
                 khuyenMaiTable.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int row = khuyenMaiTable.rowAtPoint(e.getPoint());
                        int col = khuyenMaiTable.columnAtPoint(e.getPoint());

                        int lastColumnIndex = khuyenMaiTable.getColumnCount() - 1;

                        if (row >= 0 && col == lastColumnIndex) { // "Chi tiết" column
                            DefaultTableModel model = (DefaultTableModel) khuyenMaiTable.getModel();
                            String maKhuyenMai = model.getValueAt(row, 1).toString(); // Get MaKM from column 1
                            List<khuyenMaiDTO> khuyenMaiList = khuyenMaiService.getAllKhuyenMai();
                            khuyenMaiDTO km = khuyenMaiList.stream()
                                    .filter(k -> k.getMaKhuyenMai().equals(maKhuyenMai))
                                    .findFirst()
                                    .orElse(null);

                            if (km != null) {
                                    KhuyenMaiDetailsDialog detailsDialog = new KhuyenMaiDetailsDialog(
                                                    (java.awt.Frame) javax.swing.SwingUtilities
                                                            .getWindowAncestor(khuyenMaiPanel.this),
                                                    km, dateFormat);
                                    detailsDialog.setVisible(true);
                            }
                        }
                    }
                });

                khuyenMaiTable.addMouseMotionListener(new MouseAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        int col = khuyenMaiTable.columnAtPoint(e.getPoint());
                         int row = khuyenMaiTable.rowAtPoint(e.getPoint());
                        if (col == khuyenMaiTable.getColumnCount() - 1) { // "Chi tiết" column
                            khuyenMaiTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
                            khuyenMaiTable.putClientProperty("hoverRow", row);
                            khuyenMaiTable.repaint(); 
                        } else {
                            khuyenMaiTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            khuyenMaiTable.putClientProperty("hoverRow", -1); // Reset hover row
                            khuyenMaiTable.repaint();
                        }
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        khuyenMaiTable.putClientProperty("hoverRow", -1);
                         khuyenMaiTable.repaint();
                    }
                });
        }

        public void loadKhuyenMaiData() {
                List<khuyenMaiDTO> khuyenMaiList = khuyenMaiService.getAllKhuyenMai();
                DefaultTableModel model = (DefaultTableModel) khuyenMaiTable.getModel();
                model.setRowCount(0);

                int stt = 1;
                for (khuyenMaiDTO km : khuyenMaiList) {
                        model.addRow(new Object[] {
                                        stt++,
                                        km.getMaKhuyenMai(),
                                        km.getMaSanPham(),
                                        km.getTenSanPham(),
                                        km.getTenChuongTrinh(),
                                        dateFormat.format(km.getNgayBatDau()),
                                        dateFormat.format(km.getNgayKetThuc()),
                                        String.format("%.2f%%", km.getGiamGia()),
                                        km.getGiaCu(),
                                        km.getGiaMoi(),
                                        km.getTrangThai(),
                                        "Xem chi tiết"
                        });
                }
        }

        private void searchKhuyenMai() {
                String keyword = jTextField1.getText().trim();
                String searchField = jComboBox1.getSelectedItem().toString();
                Double giamGiaTu = null, giamGiaDen = null, giaMoiTu = null, giaMoiDen = null;

                List<khuyenMaiDTO> searchResults;
                try {
                        searchResults = !keyword.isEmpty() ? khuyenMaiService.searchKhuyenMai(keyword, searchField)
                                        : khuyenMaiService.getAllKhuyenMai();
                } catch (IllegalArgumentException e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                }

                // Filter by GiamGia range
                try {
                        if (!giamGiaTuField.getText().trim().isEmpty()) {
                                giamGiaTu = Double.parseDouble(giamGiaTuField.getText().trim());
                                if (giamGiaTu < 0 || giamGiaTu > 100)
                                        throw new NumberFormatException();
                        }
                        if (!giamGiaDenField.getText().trim().isEmpty()) {
                                giamGiaDen = Double.parseDouble(giamGiaDenField.getText().trim());
                                if (giamGiaDen < 0 || giamGiaDen > 100)
                                        throw new NumberFormatException();
                        }
                        if (giamGiaTu != null && giamGiaDen != null && giamGiaTu > giamGiaDen) {
                                JOptionPane.showMessageDialog(this, "Giảm giá từ phải nhỏ hơn hoặc bằng Giảm giá đến!",
                                                "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }
                        final Double finalGiamGiaTu = giamGiaTu, finalGiamGiaDen = giamGiaDen;
                        searchResults = searchResults.stream()
                                        .filter(km -> (finalGiamGiaTu == null || km.getGiamGia() >= finalGiamGiaTu) &&
                                                        (finalGiamGiaDen == null || km.getGiamGia() <= finalGiamGiaDen))
                                        .collect(Collectors.toList());
                } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Giảm giá phải là số hợp lệ (0-100%)!", "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                // Filter by GiaMoi range
                try {
                        if (!giaMoiTuField.getText().trim().isEmpty()) {
                                giaMoiTu = Double.parseDouble(giaMoiTuField.getText().trim());
                                if (giaMoiTu < 0)
                                        throw new NumberFormatException();
                        }
                        if (!giaMoiDenField.getText().trim().isEmpty()) {
                                giaMoiDen = Double.parseDouble(giaMoiDenField.getText().trim());
                                if (giaMoiDen < 0)
                                        throw new NumberFormatException();
                        }
                        if (giaMoiTu != null && giaMoiDen != null && giaMoiTu > giaMoiDen) {
                                JOptionPane.showMessageDialog(this, "Giá mới từ phải nhỏ hơn hoặc bằng Giá mới đến!",
                                                "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }
                        final Double finalGiaMoiTu = giaMoiTu, finalGiaMoiDen = giaMoiDen;
                        searchResults = searchResults.stream()
                                        .filter(km -> (finalGiaMoiTu == null || km.getGiaMoi() >= finalGiaMoiTu) &&
                                                        (finalGiaMoiDen == null || km.getGiaMoi() <= finalGiaMoiDen))
                                        .collect(Collectors.toList());
                } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Giá mới phải là số hợp lệ (≥ 0)!", "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                DefaultTableModel model = (DefaultTableModel) khuyenMaiTable.getModel();
                model.setRowCount(0);
                if (searchResults.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy chương trình khuyến mãi nào phù hợp!",
                                        "Thông báo",
                                        JOptionPane.INFORMATION_MESSAGE);
                }

                int stt = 1;
                for (khuyenMaiDTO km : searchResults) {
                        model.addRow(new Object[] {
                                        stt++, km.getMaKhuyenMai(), km.getMaSanPham(), km.getTenSanPham(),
                                        km.getTenChuongTrinh(),
                                        dateFormat.format(km.getNgayBatDau()), dateFormat.format(km.getNgayKetThuc()),
                                        String.format("%.2f%%", km.getGiamGia()), km.getGiaCu(), km.getGiaMoi(),
                                        km.getTrangThai(),
                                        "Xem chi tiết"
                        });
                }
        }

        public javax.swing.JPanel getKhuyenMaiPanel() {
                return containerPanel;
        }

        @SuppressWarnings("unchecked")
        private void initComponents() {
                // Khởi tạo các panel trước
                containerPanel = new javax.swing.JPanel();
                pnlHeader = new javax.swing.JPanel();
                pnlContent = new javax.swing.JPanel();
                jPanel17 = new javax.swing.JPanel();
                jPanel18 = new javax.swing.JPanel();
                jPanel33 = new javax.swing.JPanel();
                pnlBottomButtons = new javax.swing.JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));

                // Khởi tạo các button
                jButton30 = new javax.swing.JButton();
                jButton31 = new javax.swing.JButton();
                jButton32 = new javax.swing.JButton();
                jButton33 = new javax.swing.JButton();
                btnExport = new javax.swing.JButton();
                jButton35 = new javax.swing.JButton();
                btnImport = new javax.swing.JButton();
                btnPrinter = new javax.swing.JButton();

                // Khởi tạo các label
                jLabel1 = new javax.swing.JLabel();
                jLabel2 = new javax.swing.JLabel();

                // Khởi tạo các text field và combo box
                jTextField1 = new javax.swing.JTextField();
                jComboBox1 = new javax.swing.JComboBox<>();
                giamGiaTuField = new javax.swing.JTextField();
                giamGiaDenField = new javax.swing.JTextField();
                giaMoiTuField = new javax.swing.JTextField();
                giaMoiDenField = new javax.swing.JTextField();
                
                // Labels for range filters
                JLabel jLabelGiamGia = new javax.swing.JLabel();
                JLabel jLabelGiamGiaTu = new javax.swing.JLabel();
                JLabel jLabelGiamGiaDen = new javax.swing.JLabel();
                JLabel jLabelGiaMoi = new javax.swing.JLabel();
                JLabel jLabelGiaMoiTu = new javax.swing.JLabel();
                JLabel jLabelGiaMoiDen = new javax.swing.JLabel();

                // Khởi tạo table và scroll pane
                jScrollPane2 = new javax.swing.JScrollPane();
                khuyenMaiTable = new javax.swing.JTable();

                containerPanel.setPreferredSize(new java.awt.Dimension(960, 680));
                containerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                pnlHeader.setBackground(AppColors.NEW_HEADER_PANEL_BG_COLOR);
                jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24));
                jLabel1.setText("QUẢN LÝ KHUYẾN MÃI");
                jLabel1.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);

                javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
                pnlHeader.setLayout(pnlHeaderLayout);
                pnlHeaderLayout.setHorizontalGroup(
                                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(pnlHeaderLayout.createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jLabel1)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
                pnlHeaderLayout.setVerticalGroup(
                                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(pnlHeaderLayout.createSequentialGroup()
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jLabel1)
                                                                .addContainerGap(20, Short.MAX_VALUE)));

                containerPanel.add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 70));

                pnlContent.setBackground(AppColors.NEW_MAIN_BG_COLOR);

                jPanel33.setBackground(AppColors.NEW_MAIN_BG_COLOR);
                javax.swing.border.TitledBorder searchBorder = javax.swing.BorderFactory.createTitledBorder(
                                javax.swing.BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Tìm kiếm");
                searchBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
                jPanel33.setBorder(searchBorder);
                jPanel33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14));
                jLabel2.setText("Tìm kiếm");
                jLabel2.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
                jPanel33.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 30));

                jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
                jPanel33.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 180, 30));
                jPanel33.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, 180, 30));
                
                // Giảm giá filters
                jLabelGiamGia = new javax.swing.JLabel();
                jLabelGiamGia.setFont(new java.awt.Font("Segoe UI", 0, 14));
                jLabelGiamGia.setText("Giảm giá(%):");
                jLabelGiamGia.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
                jPanel33.add(jLabelGiamGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 80, 30));

                jLabelGiamGiaTu = new javax.swing.JLabel();
                jLabelGiamGiaTu.setFont(new java.awt.Font("Segoe UI", 0, 14));
                jLabelGiamGiaTu.setText("Từ:");
                jLabelGiamGiaTu.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
                jPanel33.add(jLabelGiamGiaTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 20, -1, 30));
                jPanel33.add(giamGiaTuField, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, 70, 30));

                jLabelGiamGiaDen = new javax.swing.JLabel();
                jLabelGiamGiaDen.setFont(new java.awt.Font("Segoe UI", 0, 14));
                jLabelGiamGiaDen.setText("Đến:");
                jLabelGiamGiaDen.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
                jPanel33.add(jLabelGiamGiaDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 20, -1, 30));
                jPanel33.add(giamGiaDenField, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, 70, 30));

                // Giá mới filters
                jLabelGiaMoi = new javax.swing.JLabel();
                jLabelGiaMoi.setFont(new java.awt.Font("Segoe UI", 0, 14));
                jLabelGiaMoi.setText("Giá mới:");
                jLabelGiaMoi.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
                jPanel33.add(jLabelGiaMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 60, 80, 30));
                
                jLabelGiaMoiTu = new javax.swing.JLabel();
                jLabelGiaMoiTu.setFont(new java.awt.Font("Segoe UI", 0, 14));
                jLabelGiaMoiTu.setText("Từ:");
                jLabelGiaMoiTu.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
                jPanel33.add(jLabelGiaMoiTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, -1, 30));
                jPanel33.add(giaMoiTuField, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 60, 70, 30));

                jLabelGiaMoiDen = new javax.swing.JLabel();
                jLabelGiaMoiDen.setFont(new java.awt.Font("Segoe UI", 0, 14));
                jLabelGiaMoiDen.setText("Đến:");
                jLabelGiaMoiDen.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
                jPanel33.add(jLabelGiaMoiDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 60, -1, 30));
                jPanel33.add(giaMoiDenField, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 60, 70, 30));

                jButton30.setText("Tìm kiếm");
                jButton30.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
                jButton30.setForeground(java.awt.Color.WHITE);
                jButton30.setPreferredSize(new java.awt.Dimension(120, 30));
                jButton30.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                jButton30.setIconTextGap(5);
                jPanel33.add(jButton30, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 20, 120, 30));

                jButton35.setText("Làm mới");
                jButton35.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
                jButton35.setForeground(java.awt.Color.WHITE);
                jButton35.setHorizontalTextPosition(SwingConstants.RIGHT);
                jButton35.setIconTextGap(5);
                jButton35.setPreferredSize(new java.awt.Dimension(120, 30));
                jPanel33.add(jButton35, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 120, 30));

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
                jButton31.setHorizontalTextPosition(SwingConstants.RIGHT);
                jButton31.setIconTextGap(5);
                jButton31.setPreferredSize(editButtonSize);
                jPanel17.add(jButton31);

                jButton32.setText("Sửa");
                jButton32.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
                jButton32.setForeground(java.awt.Color.WHITE);
                jButton32.setHorizontalTextPosition(SwingConstants.RIGHT);
                jButton32.setIconTextGap(5);
                jButton32.setPreferredSize(editButtonSize);
                jPanel17.add(jButton32);

                jButton33.setText("Xóa");
                jButton33.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
                jButton33.setForeground(java.awt.Color.WHITE);
                jButton33.setHorizontalTextPosition(SwingConstants.RIGHT);
                jButton33.setIconTextGap(5);
                jButton33.setPreferredSize(editButtonSize);
                jPanel17.add(jButton33);

                jPanel18.setBackground(AppColors.NEW_MAIN_BG_COLOR);
                javax.swing.border.TitledBorder tableBorder = javax.swing.BorderFactory.createTitledBorder(
                                javax.swing.BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Bảng thông tin");
                tableBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
                jPanel18.setBorder(tableBorder);
                jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                khuyenMaiTable.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {},
                                new String[] {
                                                "STT", "Mã KM", "Mã sản phẩm", "Tên sản phẩm", "Tên chương trình",
                                                "Ngày bắt đầu", "Ngày kết thúc", "Giảm giá", "Giá cũ", "Giá mới", 
                                                "Trạng thái", "Chi tiết"
                                }) {
                        boolean[] canEdit = new boolean[] {
                                        false, false, false, false, false, false, false, false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit[columnIndex];
                        }
                });
                khuyenMaiTable.setShowGrid(true);
                khuyenMaiTable.setBackground(java.awt.Color.WHITE);
                khuyenMaiTable.getTableHeader().setBackground(AppColors.NEW_HEADER_PANEL_BG_COLOR);
                khuyenMaiTable.getTableHeader().setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
                khuyenMaiTable.setGridColor(AppColors.NEW_BORDER_LINES_COLOR);
                jScrollPane2.setViewportView(khuyenMaiTable);
                jPanel18.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 941, 292));

                pnlBottomButtons.setBackground(AppColors.NEW_MAIN_BG_COLOR);
                pnlBottomButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
                Dimension bottomButtonSize = new Dimension(170, 40);

                btnImport.setText("Nhập dữ liệu");
                btnImport.setBackground(AppColors.NEW_QUICK_ACCESS_BUTTON_BG_COLOR);
                btnImport.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR);
                btnImport.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnImport.setIconTextGap(5);
                btnImport.setPreferredSize(bottomButtonSize);
                btnImport.addActionListener(e -> {
                        utils.FileUtils.importFromExcelForKhuyenMai(khuyenMaiTable);
                        loadKhuyenMaiData(); 
                });
                pnlBottomButtons.add(btnImport);

                btnExport.setText("Xuất dữ liệu");
                btnExport.setBackground(AppColors.NEW_QUICK_ACCESS_BUTTON_BG_COLOR);
                btnExport.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR);
                btnExport.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnExport.setIconTextGap(5);
                btnExport.setPreferredSize(bottomButtonSize);
                btnExport.addActionListener(e -> {
                        if (khuyenMaiTable.getRowCount() == 0) {
                                JOptionPane.showMessageDialog(null, "Không có dữ liệu để xuất!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                                return;
                        }
                        utils.FileUtils.showExportOptionsForKhuyenMai(khuyenMaiTable, "DanhSachKhuyenMai");
                });
                pnlBottomButtons.add(btnExport);

                btnPrinter.setText("In ấn");
                btnPrinter.setBackground(AppColors.NEW_QUICK_ACCESS_BUTTON_BG_COLOR);
                btnPrinter.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR);
                btnPrinter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnPrinter.setIconTextGap(5);
                btnPrinter.setPreferredSize(bottomButtonSize);
                btnPrinter.addActionListener(e -> {
                        try {
                                List<khuyenMaiDTO> khuyenMaiList = khuyenMaiService.getAllKhuyenMai();

                                if (khuyenMaiList == null || khuyenMaiList.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "Không có dữ liệu khuyến mãi để in.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                        return;
                                }

                                StringBuilder htmlContent = new StringBuilder();
                                htmlContent.append("<html><head><style>");
                                htmlContent.append("body { font-family: Arial, sans-serif; margin: 20px; }");
                                htmlContent.append("h1 { text-align: center; color: #333; }");
                                htmlContent.append(".promo-record { border: 1px solid #ccc; padding: 10px; margin-bottom: 15px; border-radius: 5px; page-break-inside: avoid; }");
                                htmlContent.append(".field-label { font-weight: bold; color: #555; }");
                                htmlContent.append("p { margin: 5px 0; }");
                                htmlContent.append("</style></head><body>");
                                htmlContent.append("<h1>Danh Sách Chi Tiết Khuyến Mãi</h1>");

                                DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
                                DecimalFormat percentFormat = new DecimalFormat("#,##0.00'#'");

                                for (khuyenMaiDTO km : khuyenMaiList) {
                                        htmlContent.append("<div class='promo-record'>");
                                        htmlContent.append("<p><span class='field-label'>Mã Khuyến Mãi:</span> ").append(km.getMaKhuyenMai() != null ? km.getMaKhuyenMai() : "").append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Mã Sản Phẩm:</span> ").append(km.getMaSanPham() != null ? km.getMaSanPham() : "").append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Tên Sản Phẩm:</span> ").append(km.getTenSanPham() != null ? km.getTenSanPham() : "").append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Tên Chương Trình:</span> ").append(km.getTenChuongTrinh() != null ? km.getTenChuongTrinh() : "").append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Ngày Bắt Đầu:</span> ").append(km.getNgayBatDau() != null ? dateFormat.format(km.getNgayBatDau()) : "").append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Ngày Kết Thúc:</span> ").append(km.getNgayKetThuc() != null ? dateFormat.format(km.getNgayKetThuc()) : "").append("</p>");
                                        String giamGiaDisplay = percentFormat.format(km.getGiamGia()).replace("#", "%");
                                        htmlContent.append("<p><span class='field-label'>Giảm Giá:</span> ").append(giamGiaDisplay).append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Giá Cũ:</span> ").append(decimalFormat.format(km.getGiaCu())).append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Giá Mới:</span> ").append(decimalFormat.format(km.getGiaMoi())).append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Trạng Thái:</span> ").append(km.getTrangThai() != null ? km.getTrangThai() : "").append("</p>");
                                        htmlContent.append("</div>");
                                }
                                htmlContent.append("</body></html>");

                                JEditorPane editorPane = new JEditorPane();
                                editorPane.setContentType("text/html");
                                editorPane.setText(htmlContent.toString());
                                editorPane.setEditable(false);

                                boolean printed = editorPane.print();
                                // if (!printed) {
                                //     JOptionPane.showMessageDialog(null, "Lệnh in đã bị hủy.", "In Bị Hủy", JOptionPane.WARNING_MESSAGE);
                                // }
                        } catch (java.awt.print.PrinterException pe) {
                                JOptionPane.showMessageDialog(null, "Lỗi khi in: Không tìm thấy máy in hoặc lỗi máy in.\\n" + pe.getMessage(), "Lỗi In Ấn", JOptionPane.ERROR_MESSAGE);
                                pe.printStackTrace();
                        } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Lỗi khi chuẩn bị dữ liệu để in: " + ex.getMessage(), "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                                ex.printStackTrace();
                        }
                });
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

                setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
                add(containerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 700));
                
                // Call setupUI, similar to HoaDonPanel (can be simplified or integrated if preferred sizes are directly set)
                setupUI();
        }

        // setupUI method similar to HoaDonPanel, to set preferred sizes for panels
        private void setupUI() {
            jPanel33.setPreferredSize(new java.awt.Dimension(960, 110)); // Search panel
            jPanel17.setPreferredSize(new java.awt.Dimension(960, 80));  // Edit panel
            jPanel18.setPreferredSize(new java.awt.Dimension(960, 332)); // Table panel
            pnlBottomButtons.setPreferredSize(new java.awt.Dimension(960,60)); // Bottom buttons
        }

        class HyperlinkRenderer extends DefaultTableCellRenderer {
                public HyperlinkRenderer() {
                        setHorizontalAlignment(CENTER);
                }

                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                boolean hasFocus,
                                int row, int column) {
                        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                                        row, column);
                        label.setForeground(new java.awt.Color(0, 102, 204));
                        label.setText(value.toString());
                        label.setBorder(null);
                        return label;
                }
        }

        private javax.swing.JPanel containerPanel;
        private javax.swing.JButton jButton30;
        private javax.swing.JButton jButton31;
        private javax.swing.JButton jButton32;
        private javax.swing.JButton jButton33;
        private javax.swing.JButton btnExport;
        private javax.swing.JButton jButton35;
        private javax.swing.JButton btnImport;
        private javax.swing.JButton btnPrinter;
        private javax.swing.JComboBox<String> jComboBox1;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JPanel jPanel17;
        private javax.swing.JPanel jPanel18;
        private javax.swing.JPanel jPanel33;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JTable khuyenMaiTable;
        private javax.swing.JTextField jTextField1;
        private javax.swing.JPanel pnlContent;
        private javax.swing.JPanel pnlHeader;
        private JTextField giamGiaTuField;
        private JTextField giamGiaDenField;
        private JTextField giaMoiTuField;
        private JTextField giaMoiDenField;
}