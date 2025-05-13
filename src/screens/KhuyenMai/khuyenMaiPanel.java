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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import utils.FileUtils;
import java.text.MessageFormat;
import java.util.Set;
import java.util.HashSet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.awt.print.PrinterJob;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

public class khuyenMaiPanel extends javax.swing.JPanel {
        private KhuyenMaiService khuyenMaiService;
        private SimpleDateFormat dateFormat;
        private JTextField giamGiaTuField;
        private JTextField giamGiaDenField;
        private JTextField giaMoiTuField;
        private JTextField giaMoiDenField;
        private javax.swing.JButton jButton36;
        private javax.swing.JButton btnPrinter;

        public khuyenMaiPanel() {
                initComponents();
                khuyenMaiService = new KhuyenMaiService();
                dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
                                "Tất cả", "Mã KM", "Mã sản phẩm", "Tên sản phẩm", "Tên chương trình", "Trạng thái"
                }));

                loadKhuyenMaiData();

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

                jButton34.setText("Xuất file");
                ImageIcon exportIcon = new ImageIcon("src/icon_img/export_icon.png");
                jButton34.setIcon(new ImageIcon(
                                exportIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
                jButton34.setHorizontalTextPosition(SwingConstants.RIGHT);
                jButton34.setPreferredSize(new java.awt.Dimension(340, 40));
                jButton34.addActionListener(e -> {
                        utils.FileUtils.showExportOptions(khuyenMaiTable, "Danh sách khuyến mãi");
                });

                jButton36 = new javax.swing.JButton("Import");
                ImageIcon importIcon = new ImageIcon("src/icon_img/import_icon.png");
                jButton36.setIcon(new ImageIcon(
                                importIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
                jButton36.setHorizontalTextPosition(SwingConstants.RIGHT);
                jButton36.setPreferredSize(new java.awt.Dimension(100, 40));
                jButton36.addActionListener(e -> {
                        utils.FileUtils.importFromFileForKhuyenMai(khuyenMaiTable);
                        loadKhuyenMaiData(); // Refresh the table after import
                });

                btnPrinter = new javax.swing.JButton("In");
                ImageIcon printIcon = new ImageIcon("src/icon_img/print_icon.png");
                btnPrinter.setIcon(new ImageIcon(
                                printIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
                btnPrinter.setHorizontalTextPosition(SwingConstants.RIGHT);
                btnPrinter.setPreferredSize(new java.awt.Dimension(100, 40));
                btnPrinter.addActionListener(e -> {
                        try {
                                khuyenMaiTable.print();
                        } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Lỗi khi in: " + ex.getMessage(), "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
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
                pnlContent1 = new javax.swing.JPanel();
                jPanel17 = new javax.swing.JPanel();
                jPanel18 = new javax.swing.JPanel();
                jPanel33 = new javax.swing.JPanel();

                // Khởi tạo các button
                jButton30 = new javax.swing.JButton("Tìm kiếm");
                jButton31 = new javax.swing.JButton("Thêm");
                jButton32 = new javax.swing.JButton("Sửa");
                jButton33 = new javax.swing.JButton("Xóa");
                jButton34 = new javax.swing.JButton("Xuất file");
                jButton35 = new javax.swing.JButton("Làm mới");
                jButton36 = new javax.swing.JButton("Import");
                btnPrinter = new javax.swing.JButton("In");

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

                // Khởi tạo table và scroll pane
                jScrollPane2 = new javax.swing.JScrollPane();
                khuyenMaiTable = new javax.swing.JTable();

                containerPanel.setPreferredSize(new java.awt.Dimension(960, 680));
                containerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                pnlHeader.setBackground(new java.awt.Color(12, 150, 156));
                jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24));
                jLabel1.setText("Quản lý Hàng Khuyến Mãi");

                javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
                pnlHeader.setLayout(pnlHeaderLayout);
                pnlHeaderLayout.setHorizontalGroup(
                                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                pnlHeaderLayout.createSequentialGroup()
                                                                                .addContainerGap(373, Short.MAX_VALUE)
                                                                                .addComponent(jLabel1)
                                                                                .addGap(351, 351, 351)));
                pnlHeaderLayout.setVerticalGroup(
                                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(pnlHeaderLayout.createSequentialGroup()
                                                                .addGap(14, 14, 14)
                                                                .addComponent(jLabel1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                42,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(14, Short.MAX_VALUE)));

                containerPanel.add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 70));

                pnlContent.setBackground(new java.awt.Color(107, 163, 190));

                jPanel17.setBackground(new java.awt.Color(107, 163, 190));
                jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(
                                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chỉnh sửa"));
                jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jButton31.setText("Thêm");
                ImageIcon addIcon = new ImageIcon("src/icon_img/add.png");
                jButton31.setIcon(new ImageIcon(
                                addIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
                jButton31.setHorizontalTextPosition(SwingConstants.RIGHT);
                jButton31.setPreferredSize(new java.awt.Dimension(100, 34));
                jPanel17.add(jButton31, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 24, 100, 34));

                jButton32.setText("Sửa");
                ImageIcon editIcon = new ImageIcon("src/icon_img/edit.png");
                jButton32.setIcon(new ImageIcon(
                                editIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
                jButton32.setHorizontalTextPosition(SwingConstants.RIGHT);
                jButton32.setPreferredSize(new java.awt.Dimension(100, 34));
                jPanel17.add(jButton32, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 24, 100, 34));

                jButton33.setText("Xóa");
                ImageIcon deleteIcon = new ImageIcon("src/icon_img/delete.png");
                jButton33.setIcon(new ImageIcon(
                                deleteIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
                jButton33.setHorizontalTextPosition(SwingConstants.RIGHT);
                jButton33.setPreferredSize(new java.awt.Dimension(100, 34));
                jPanel17.add(jButton33, new org.netbeans.lib.awtextra.AbsoluteConstraints(495, 24, 100, 34));

                jButton35.setText("Làm mới");
                ImageIcon refreshIcon = new ImageIcon("src/icon_img/refresh.png");
                jButton35.setIcon(new ImageIcon(
                                refreshIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
                jButton35.setHorizontalTextPosition(SwingConstants.RIGHT);
                jButton35.setPreferredSize(new java.awt.Dimension(100, 34));
                jPanel17.add(jButton35, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 24, 120, 34));

                jPanel18.setBackground(new java.awt.Color(107, 163, 190));
                jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(
                                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                                "Bảng thông tin"));
                jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                khuyenMaiTable.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {},
                                new String[] {
                                                "STT", "Mã KM", "Mã sản phẩm", "Tên sản phẩm", "Tên chương trình",
                                                "Ngày bắt đầu",
                                                "Ngày kết thúc", "Giảm giá", "Giá cũ", "Giá mới", "Trạng thái",
                                                "Chi tiết"
                                }) {
                        boolean[] canEdit = new boolean[] {
                                        false, false, false, false, false, false, false, false, false, false, false,
                                        false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit[columnIndex];
                        }
                });
                khuyenMaiTable.setShowGrid(true);
                jScrollPane2.setViewportView(khuyenMaiTable);

                jPanel18.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 24, 920, 290));

                jButton34.setText("Xuất file");
                ImageIcon exportIcon = new ImageIcon("src/icon_img/export_icon.png");
                jButton34.setIcon(new ImageIcon(
                                exportIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
                jButton34.setHorizontalTextPosition(SwingConstants.RIGHT);
                jButton34.setPreferredSize(new java.awt.Dimension(340, 40));
                jButton34.addActionListener(e -> {
                        utils.FileUtils.showExportOptions(khuyenMaiTable, "Danh sách khuyến mãi");
                });

                jButton36 = new javax.swing.JButton("Import");
                ImageIcon importIcon = new ImageIcon("src/icon_img/import_icon.png");
                jButton36.setIcon(new ImageIcon(
                                importIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
                jButton36.setHorizontalTextPosition(SwingConstants.RIGHT);
                jButton36.setPreferredSize(new java.awt.Dimension(100, 40));
                jButton36.addActionListener(e -> {
                        utils.FileUtils.importFromFileForKhuyenMai(khuyenMaiTable);
                        loadKhuyenMaiData(); // Refresh the table after import
                });

                btnPrinter = new javax.swing.JButton("In");
                ImageIcon printIcon = new ImageIcon("src/icon_img/print_icon.png");
                btnPrinter.setIcon(new ImageIcon(
                                printIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
                btnPrinter.setHorizontalTextPosition(SwingConstants.RIGHT);
                btnPrinter.setPreferredSize(new java.awt.Dimension(100, 40));
                btnPrinter.addActionListener(e -> {
                        try {
                                khuyenMaiTable.print();
                        } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Lỗi khi in: " + ex.getMessage(), "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                        }
                });

                jPanel33.setBackground(new java.awt.Color(107, 163, 190));
                jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder(
                                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tìm kiếm"));
                jPanel33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jButton30.setText("Tìm kiếm");
                ImageIcon searchIcon = new ImageIcon("src/icon_img/search.png");
                jButton30.setIcon(new ImageIcon(
                                searchIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
                jButton30.setHorizontalTextPosition(SwingConstants.RIGHT);
                jButton30.setPreferredSize(new java.awt.Dimension(120, 30));
                jPanel33.add(jButton30, new org.netbeans.lib.awtextra.AbsoluteConstraints(832, 40, 120, 30));

                jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14));
                jLabel2.setText("Tìm kiếm");
                jPanel33.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 30));

                jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
                jPanel33.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 180, 30));

                jTextField1.setPreferredSize(new java.awt.Dimension(180, 30));
                jPanel33.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, 180, 30));

                JLabel giamGiaLabel = new JLabel("Giảm giá:");
                giamGiaLabel.setFont(new java.awt.Font("Segoe UI", 0, 14));
                jPanel33.add(giamGiaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 80, 30));

                JLabel giamGiaFromLabel = new JLabel("Từ:");
                giamGiaFromLabel.setFont(new java.awt.Font("Segoe UI", 0, 14));
                jPanel33.add(giamGiaFromLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, -1, 30));

                giamGiaTuField = new JTextField();
                giamGiaTuField.setPreferredSize(new java.awt.Dimension(80, 30));
                giamGiaTuField.setToolTipText("Nhập giá trị giảm giá từ (0-100%)");
                jPanel33.add(giamGiaTuField, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, 80, 30));

                JLabel giamGiaToLabel = new JLabel("Đến:");
                giamGiaToLabel.setFont(new java.awt.Font("Segoe UI", 0, 14));
                jPanel33.add(giamGiaToLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 20, -1, 30));

                giamGiaDenField = new JTextField();
                giamGiaDenField.setPreferredSize(new java.awt.Dimension(80, 30));
                giamGiaDenField.setToolTipText("Nhập giá trị giảm giá đến (0-100%)");
                jPanel33.add(giamGiaDenField, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, 80, 30));

                JLabel giaMoiLabel = new JLabel("Giá mới:");
                giaMoiLabel.setFont(new java.awt.Font("Segoe UI", 0, 14));
                jPanel33.add(giaMoiLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 60, 80, 30));

                JLabel giaMoiFromLabel = new JLabel("Từ:");
                giaMoiFromLabel.setFont(new java.awt.Font("Segoe UI", 0, 14));
                jPanel33.add(giaMoiFromLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, -1, 30));

                giaMoiTuField = new JTextField();
                giaMoiTuField.setPreferredSize(new java.awt.Dimension(80, 30));
                giaMoiTuField.setToolTipText("Nhập giá mới từ (số dương)");
                jPanel33.add(giaMoiTuField, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, 80, 30));

                JLabel giaMoiToLabel = new JLabel("Đến:");
                giaMoiToLabel.setFont(new java.awt.Font("Segoe UI", 0, 14));
                jPanel33.add(giaMoiToLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 60, -1, 30));

                giaMoiDenField = new JTextField();
                giaMoiDenField.setPreferredSize(new java.awt.Dimension(80, 30));
                giaMoiDenField.setToolTipText("Nhập giá mới đến (số dương)");
                jPanel33.add(giaMoiDenField, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 60, 80, 30));

                javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
                pnlContent.setLayout(pnlContentLayout);
                pnlContentLayout.setHorizontalGroup(
                                pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(pnlContentLayout.createSequentialGroup()
                                                                .addContainerGap(22, Short.MAX_VALUE)
                                                                .addGroup(pnlContentLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                pnlContentLayout.createSequentialGroup()
                                                                                                                .addComponent(jButton36,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                100,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addGap(18, 18, 18)
                                                                                                                .addComponent(jButton34,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                340,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addGap(18, 18, 18)
                                                                                                                .addComponent(btnPrinter,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                100,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addGap(313, 313,
                                                                                                                                313))
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                pnlContentLayout
                                                                                                                .createSequentialGroup()
                                                                                                                .addGroup(pnlContentLayout
                                                                                                                                .createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                .addComponent(jPanel33,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                961,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addGroup(pnlContentLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                false)
                                                                                                                                                .addComponent(jPanel17,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(jPanel18,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                958,
                                                                                                                                                                Short.MAX_VALUE)))
                                                                                                                .addGap(17, 17, 17)))));
                pnlContentLayout.setVerticalGroup(
                                pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(pnlContentLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jPanel33,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                110,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(4, 4, 4)
                                                                .addComponent(jPanel17,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                80,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(4, 4, 4)
                                                                .addComponent(jPanel18,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                342,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                28,
                                                                                Short.MAX_VALUE)
                                                                .addGroup(pnlContentLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jButton36,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                40,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jButton34,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                40,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(btnPrinter,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                40,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(16, 16, 16)));

                containerPanel.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1000, 630));

                setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
                add(containerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 700));
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
        private javax.swing.JButton jButton34;
        private javax.swing.JButton jButton35;

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
        private javax.swing.JPanel pnlContent1;
        private javax.swing.JPanel pnlHeader;
}