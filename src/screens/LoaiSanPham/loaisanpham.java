package screens.LoaiSanPham;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import BUS.ProductService;
import DTO.sanPhamDTO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import utils.FileUtils;

public class loaisanpham extends javax.swing.JPanel {

        private ProductService productService;
        private JTextField priceFromField;
        private JTextField priceToField;
        private JTextField quantityFromField;
        private JTextField quantityToField;
        private JRadioButton priceRadioButton;
        private JRadioButton quantityRadioButton;
        private javax.swing.JButton jButton36;
        private javax.swing.JButton jButton37;

        public loaisanpham() {
                productService = new ProductService();
                initComponents();
                loadTableData();
                setupTable();
        }

        public javax.swing.JPanel getLoaiSanPhamPanel() {
                return containerPanel;
        }

        private void loadTableData() {
                List<sanPhamDTO> sanPhamList = productService.getAllProducts();
                updateTable(sanPhamList);
        }

        private void updateTable(List<sanPhamDTO> sanPhamList) {
                DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                model.setRowCount(0);

                for (int i = 0; i < sanPhamList.size(); i++) {
                        sanPhamDTO sp = sanPhamList.get(i);
                        Object[] row = {
                                        i + 1,
                                        sp.getMaSanPham(),
                                        sp.getTenSanPham(),
                                        sp.getMaNhaCungCap(),
                                        sp.getMaDanhMuc(),
                                        sp.getMauSac(),
                                        sp.getSize(),
                                        sp.getSoLuongTonKho(),
                                        sp.getGiaBan(),
                                        sp.getImgURL(),
                                        sp.getTrangThai(),
                                        "Xem chi tiết"
                        };
                        model.addRow(row);
                }
        }

        private void setupTable() {
                jTable2.getColumn("Chi tiết").setCellRenderer(new DefaultTableCellRenderer() {
                        private final java.awt.Color normalColor = new java.awt.Color(0, 102, 204);
                        private final java.awt.Color hoverColor = new java.awt.Color(51, 153, 255);

                        @Override
                        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                        boolean hasFocus, int row, int column) {
                                JLabel label = new JLabel((String) value);
                                label.setForeground(normalColor);
                                label.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
                                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                label.setHorizontalAlignment(SwingConstants.CENTER);

                                if (table.getClientProperty("hoverRow") != null
                                                && (int) table.getClientProperty("hoverRow") == row) {
                                        label.setForeground(hoverColor);
                                } else {
                                        label.setForeground(normalColor);
                                }

                                return label;
                        }
                });

                jTable2.addMouseMotionListener(new MouseAdapter() {
                        @Override
                        public void mouseMoved(MouseEvent e) {
                                int mouseRow = jTable2.rowAtPoint(e.getPoint());
                                int mouseColumn = jTable2.columnAtPoint(e.getPoint());
                                if (mouseColumn == jTable2.getColumnModel().getColumnIndex("Chi tiết")) {
                                        jTable2.putClientProperty("hoverRow", mouseRow);
                                } else {
                                        jTable2.putClientProperty("hoverRow", -1);
                                }
                                jTable2.repaint();
                        }
                });

                jTable2.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                int column = jTable2.columnAtPoint(e.getPoint());
                                int row = jTable2.rowAtPoint(e.getPoint());

                                if (column == jTable2.getColumnModel().getColumnIndex("Chi tiết")) {
                                        String maSanPham = jTable2.getValueAt(row, 1).toString();
                                        try {
                                                sanPhamDTO product = productService.getProductById(maSanPham);
                                                ProductDetailDialog dialog = new ProductDetailDialog(null, product);
                                                dialog.setVisible(true);
                                        } catch (Exception ex) {
                                                JOptionPane.showMessageDialog(null, ex.getMessage(), "Lỗi",
                                                                JOptionPane.ERROR_MESSAGE);
                                        }
                                }
                        }
                });
        }

        @SuppressWarnings("unchecked")
        private void initComponents() {
                containerPanel = new javax.swing.JPanel();
                containerPanel.setPreferredSize(new java.awt.Dimension(960, 680));
                containerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                pnlHeader = new javax.swing.JPanel();
                jLabel1 = new javax.swing.JLabel();
                pnlContent = new javax.swing.JPanel();
                jPanel15 = new javax.swing.JPanel();

                searchLabel = new javax.swing.JLabel();
                searchComboBox = new javax.swing.JComboBox<>();
                searchTextField = new javax.swing.JTextField();
                jButton30 = new javax.swing.JButton();

                jPanel17 = new javax.swing.JPanel();
                jButton31 = new javax.swing.JButton();
                jButton32 = new javax.swing.JButton();
                jButton33 = new javax.swing.JButton();
                jButton35 = new javax.swing.JButton();
                jPanel18 = new javax.swing.JPanel();
                jScrollPane2 = new javax.swing.JScrollPane();
                jTable2 = new javax.swing.JTable();
                jButton34 = new javax.swing.JButton();

                containerPanel.setPreferredSize(new java.awt.Dimension(960, 680));

                pnlHeader.setBackground(new java.awt.Color(12, 150, 156));

                jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24));
                jLabel1.setText("Quản lý sản phẩm");

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

                jPanel15.setBackground(new java.awt.Color(107, 163, 190));
                jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(
                                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tìm kiếm"));
                jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                searchLabel.setFont(new java.awt.Font("Segoe UI", 0, 14));
                searchLabel.setText("Tìm kiếm");
                jPanel15.add(searchLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 30));

                searchComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
                                "Tất cả", "Mã sản phẩm", "Tên sản phẩm", "Mã nhà cung cấp", "Màu sắc", "Size"
                }));
                jPanel15.add(searchComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 180, 30));

                jPanel15.add(searchTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, 180, 30));

                priceRadioButton = new JRadioButton("Đơn giá:");
                priceRadioButton.setFont(new java.awt.Font("Segoe UI", 0, 14));
                priceRadioButton.setBackground(new java.awt.Color(107, 163, 190));
                priceRadioButton.setIcon(new ImageIcon("src/icon_img/round_unchecked.png"));
                priceRadioButton.setSelectedIcon(new ImageIcon("src/icon_img/round_checked.png"));
                jPanel15.add(priceRadioButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 80, 30));

                JLabel priceFromLabel = new JLabel("Từ:");
                priceFromLabel.setFont(new java.awt.Font("Segoe UI", 0, 14));
                jPanel15.add(priceFromLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, -1, 30));

                priceFromField = new JTextField();
                jPanel15.add(priceFromField, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, 80, 30));

                JLabel priceToLabel = new JLabel("Đến:");
                priceToLabel.setFont(new java.awt.Font("Segoe UI", 0, 14));
                jPanel15.add(priceToLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 20, -1, 30));

                priceToField = new JTextField();
                jPanel15.add(priceToField, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, 80, 30));

                quantityRadioButton = new JRadioButton("Số lượng:");
                quantityRadioButton.setFont(new java.awt.Font("Segoe UI", 0, 14));
                quantityRadioButton.setBackground(new java.awt.Color(107, 163, 190));
                quantityRadioButton.setIcon(new ImageIcon("src/icon_img/round_unchecked.png"));
                quantityRadioButton.setSelectedIcon(new ImageIcon("src/icon_img/round_checked.png"));
                jPanel15.add(quantityRadioButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 60, 80, 30));

                JLabel quantityFromLabel = new JLabel("Từ:");
                quantityFromLabel.setFont(new java.awt.Font("Segoe UI", 0, 14));
                jPanel15.add(quantityFromLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, -1, 30));

                quantityFromField = new JTextField();
                jPanel15.add(quantityFromField, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, 80, 30));

                JLabel quantityToLabel = new JLabel("Đến:");
                quantityToLabel.setFont(new java.awt.Font("Segoe UI", 0, 14));
                jPanel15.add(quantityToLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 60, -1, 30));

                quantityToField = new JTextField();
                jPanel15.add(quantityToField, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 60, 80, 30));

                jButton30.setText("Tìm kiếm");
                ImageIcon searchIcon = new ImageIcon("src/icon_img/search.png");
                jButton30.setIcon(new ImageIcon(
                                searchIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
                jButton30.setHorizontalTextPosition(SwingConstants.RIGHT);
                jButton30.setPreferredSize(new java.awt.Dimension(120, 30));
                jButton30.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton30ActionPerformed(evt);
                        }
                });
                jPanel15.add(jButton30, new org.netbeans.lib.awtextra.AbsoluteConstraints(832, 40, 120, 30));

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
                jButton31.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton31ActionPerformed(evt);
                        }
                });
                jPanel17.add(jButton31, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 14, 100, 34));

                jButton32.setText("Sửa");
                ImageIcon editIcon = new ImageIcon("src/icon_img/edit.png");
                jButton32.setIcon(new ImageIcon(
                                editIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
                jButton32.setHorizontalTextPosition(SwingConstants.RIGHT);
                jButton32.setPreferredSize(new java.awt.Dimension(100, 34));
                jButton32.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton32ActionPerformed(evt);
                        }
                });
                jPanel17.add(jButton32, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 14, 100, 34));

                jButton33.setText("Xóa");
                ImageIcon deleteIcon = new ImageIcon("src/icon_img/delete.png");
                jButton33.setIcon(new ImageIcon(
                                deleteIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
                jButton33.setHorizontalTextPosition(SwingConstants.RIGHT);
                jButton33.setPreferredSize(new java.awt.Dimension(100, 34));
                jButton33.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton33ActionPerformed(evt);
                        }
                });
                jPanel17.add(jButton33, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 14, 100, 34));

                jButton35.setText("Làm mới");
                ImageIcon refreshIcon = new ImageIcon("src/icon_img/refresh.png");
                jButton35.setIcon(new ImageIcon(
                                refreshIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
                jButton35.setHorizontalTextPosition(SwingConstants.RIGHT);
                jButton35.setPreferredSize(new java.awt.Dimension(100, 34));
                jButton35.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                loadTableData();
                                searchTextField.setText("");
                                priceFromField.setText("");
                                priceToField.setText("");
                                quantityFromField.setText("");
                                quantityToField.setText("");
                                priceRadioButton.setSelected(false);
                                quantityRadioButton.setSelected(false);
                                searchComboBox.setSelectedIndex(0);
                        }
                });
                jPanel17.add(jButton35, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 14, 120, 34));

                jPanel18.setBackground(new java.awt.Color(107, 163, 190));
                jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(
                                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                                "Bảng thông tin"));
                jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jTable2.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {},
                                new String[] {
                                                "STT", "Mã SP", "Tên SP", "Mã NCC", "Loại SP", "Màu sắc", "Kích cỡ",
                                                "Số lượng", "Đơn giá", "Hình ảnh", "Trạng thái", "Chi tiết"
                                }) {
                        boolean[] canEdit = new boolean[] {
                                        false, false, false, false, false, false, false, false, false, false, false,
                                        false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit[columnIndex];
                        }
                });
                jTable2.setShowGrid(true);
                jScrollPane2.setViewportView(jTable2);

                jPanel18.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 940, 270));

                jButton34.setText("Xuất file");
                ImageIcon exportIcon = new ImageIcon("src/icon_img/export_icon.png");
                jButton34.setIcon(new ImageIcon(
                                exportIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
                jButton34.setHorizontalTextPosition(SwingConstants.RIGHT);
                jButton34.setPreferredSize(new java.awt.Dimension(340, 40));
                jButton34.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton34ActionPerformed(evt);
                        }
                });

                // Add Import button
                jButton36 = new javax.swing.JButton("Import");
                ImageIcon importIcon = new ImageIcon("src/icon_img/import_icon.png");
                jButton36.setIcon(new ImageIcon(
                                importIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
                jButton36.setHorizontalTextPosition(SwingConstants.RIGHT);
                jButton36.setPreferredSize(new java.awt.Dimension(100, 40));
                jButton36.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                utils.FileUtils.importFromFile(jTable2);
                        }
                });

                // Add Print button
                jButton37 = new javax.swing.JButton("In");
                ImageIcon printIcon = new ImageIcon("src/icon_img/print_icon.png");
                jButton37.setIcon(new ImageIcon(
                                printIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
                jButton37.setHorizontalTextPosition(SwingConstants.RIGHT);
                jButton37.setPreferredSize(new java.awt.Dimension(100, 40));
                jButton37.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                try {
                                        jTable2.print();
                                } catch (Exception e) {
                                        JOptionPane.showMessageDialog(null, "Lỗi khi in: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                                }
                        }
                });

                javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
                pnlContent.setLayout(pnlContentLayout);
                pnlContentLayout.setHorizontalGroup(
                                pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(pnlContentLayout.createSequentialGroup()
                                                                .addContainerGap(22, Short.MAX_VALUE)
                                                                .addGroup(pnlContentLayout
                                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                        pnlContentLayout.createSequentialGroup()
                                                                                                .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                        100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(18, 18, 18)
                                                                                                .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                        340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(18, 18, 18)
                                                                                                .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                        100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(313, 313, 313))
                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout
                                                                        .createSequentialGroup()
                                                                        .addGroup(pnlContentLayout
                                                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                961, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addGroup(pnlContentLayout
                                                                                                .createParallelGroup(
                                                                                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                        false)
                                                                                                .addComponent(jPanel17,
                                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                        Short.MAX_VALUE)
                                                                                                .addComponent(jPanel18,
                                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE, 958,
                                                                                                        Short.MAX_VALUE)))
                                                                .addGap(17, 17, 17)))));
                pnlContentLayout.setVerticalGroup(
                                pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(pnlContentLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(4, 4, 4)
                                                                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 80,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(4, 4, 4)
                                                                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 342,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28,
                                                                        Short.MAX_VALUE)
                                                                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(16, 16, 16)));

                containerPanel.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1000, 630));
        }

        private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {
                Workbook workbook = new HSSFWorkbook();
                Sheet sheet = workbook.createSheet("Danh sách sản phẩm");

                Row headerRow = sheet.createRow(0);
                String[] headers = { "STT", "Mã SP", "Tên SP", "Mã NCC", "Loại SP", "Màu sắc", "Kích cỡ", "Số lượng",
                                "Đơn giá", "Hình ảnh", "Trạng thái" };
                for (int i = 0; i < headers.length; i++) {
                        Cell cell = headerRow.createCell(i);
                        cell.setCellValue(headers[i]);
                        CellStyle headerStyle = workbook.createCellStyle();
                        Font font = workbook.createFont();
                        font.setBold(true);
                        headerStyle.setFont(font);
                        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
                        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        cell.setCellStyle(headerStyle);
                }

                DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                        Row row = sheet.createRow(i + 1);
                        for (int j = 0; j < headers.length; j++) {
                                Cell cell = row.createCell(j);
                                Object value = model.getValueAt(i, j);
                                if (value != null) {
                                        cell.setCellValue(value.toString());
                                }
                        }
                }

                for (int i = 0; i < headers.length; i++) {
                        sheet.autoSizeColumn(i);
                }

                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName = "DanhSachSanPham_" + timestamp + ".xls";

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
                fileChooser.setSelectedFile(new java.io.File(fileName));
                fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                        @Override
                        public boolean accept(java.io.File f) {
                                return f.isDirectory() || f.getName().toLowerCase().endsWith(".xls");
                        }

                        @Override
                        public String getDescription() {
                                return "Excel Files (*.xls)";
                        }
                });

                int userSelection = fileChooser.showSaveDialog(this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                        java.io.File fileToSave = fileChooser.getSelectedFile();
                        String filePath = fileToSave.getAbsolutePath();
                        if (!filePath.toLowerCase().endsWith(".xls")) {
                                filePath += ".xls";
                                fileToSave = new java.io.File(filePath);
                        }

                        try (FileOutputStream fileOut = new FileOutputStream(fileToSave)) {
                                workbook.write(fileOut);
                                JOptionPane.showMessageDialog(this,
                                                "Xuất file Excel thành công: " + fileToSave.getAbsolutePath(),
                                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException e) {
                                JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel: " + e.getMessage(), "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                        } finally {
                                try {
                                        workbook.close();
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }
                }
        }

        private List<sanPhamDTO> filterByPriceRange(List<sanPhamDTO> list, Double priceFrom, Double priceTo) {
                if (priceFrom == null && priceTo == null) {
                        return list;
                }
                return list.stream()
                                .filter(sp -> {
                                        double price = sp.getGiaBan();
                                        boolean matches = true;
                                        if (priceFrom != null) {
                                                matches = matches && price >= priceFrom;
                                        }
                                        if (priceTo != null) {
                                                matches = matches && price <= priceTo;
                                        }
                                        return matches;
                                })
                                .collect(Collectors.toList());
        }

        private List<sanPhamDTO> filterByQuantityRange(List<sanPhamDTO> list, Integer quantityFrom,
                        Integer quantityTo) {
                if (quantityFrom == null && quantityTo == null) {
                        return list;
                }
                return list.stream()
                                .filter(sp -> {
                                        int quantity = sp.getSoLuongTonKho();
                                        boolean matches = true;
                                        if (quantityFrom != null) {
                                                matches = matches && quantity >= quantityFrom;
                                        }
                                        if (quantityTo != null) {
                                                matches = matches && quantity <= quantityTo;
                                        }
                                        return matches;
                                })
                                .collect(Collectors.toList());
        }

        private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {
                String keyword = searchTextField.getText().trim();
                String searchType = searchComboBox.getSelectedItem().toString();
                Double priceFrom = null;
                Double priceTo = null;
                Integer quantityFrom = null;
                Integer quantityTo = null;

                List<sanPhamDTO> sanPhamList = productService.searchProducts(keyword, searchType);

                if (priceRadioButton.isSelected() || !priceFromField.getText().trim().isEmpty()
                                || !priceToField.getText().trim().isEmpty()) {
                        try {
                                if (!priceFromField.getText().trim().isEmpty()) {
                                        priceFrom = Double.parseDouble(priceFromField.getText().trim());
                                }
                                if (!priceToField.getText().trim().isEmpty()) {
                                        priceTo = Double.parseDouble(priceToField.getText().trim());
                                }
                        } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá trị hợp lệ cho khoảng giá!",
                                                "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        if (priceFrom != null && priceTo != null && priceFrom > priceTo) {
                                JOptionPane.showMessageDialog(this, "Giá từ không được lớn hơn giá đến!", "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        sanPhamList = filterByPriceRange(sanPhamList, priceFrom, priceTo);
                }

                if (quantityRadioButton.isSelected() || !quantityFromField.getText().trim().isEmpty()
                                || !quantityToField.getText().trim().isEmpty()) {
                        try {
                                if (!quantityFromField.getText().trim().isEmpty()) {
                                        quantityFrom = Integer.parseInt(quantityFromField.getText().trim());
                                }
                                if (!quantityToField.getText().trim().isEmpty()) {
                                        quantityTo = Integer.parseInt(quantityToField.getText().trim());
                                }
                        } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá trị hợp lệ cho khoảng số lượng!",
                                                "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        if (quantityFrom != null && quantityTo != null && quantityFrom > quantityTo) {
                                JOptionPane.showMessageDialog(this, "Số lượng từ không được lớn hơn số lượng đến!",
                                                "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        sanPhamList = filterByQuantityRange(sanPhamList, quantityFrom, quantityTo);
                }

                if (sanPhamList.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm nào phù hợp!", "Thông báo",
                                        JOptionPane.INFORMATION_MESSAGE);
                }

                updateTable(sanPhamList);
        }

        private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {
                EditProductDialog dialog = new EditProductDialog(null, null, false);
                dialog.setVisible(true);
                if (dialog.isSaved()) {
                        loadTableData();
                }
        }

        private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {
                int selectedRow = jTable2.getSelectedRow();
                if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để sửa!", "Thông báo",
                                        JOptionPane.WARNING_MESSAGE);
                        return;
                }

                String maSanPham = jTable2.getValueAt(selectedRow, 1).toString();
                try {
                        sanPhamDTO product = productService.getProductById(maSanPham);
                        EditProductDialog dialog = new EditProductDialog(null, product, true);
                        dialog.setVisible(true);
                        if (dialog.isSaved()) {
                                loadTableData();
                        }
                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
        }

        private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {
                int selectedRow = jTable2.getSelectedRow();
                if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để xóa!", "Thông báo",
                                        JOptionPane.WARNING_MESSAGE);
                        return;
                }

                String maSanPham = jTable2.getValueAt(selectedRow, 1).toString();
                int confirm = JOptionPane.showConfirmDialog(this,
                                "Bạn có chắc chắn muốn xóa sản phẩm " + maSanPham + "?", "Xác nhận xóa",
                                JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) {
                        return;
                }

                try {
                        boolean success = productService.deleteProduct(maSanPham);
                        if (success) {
                                JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
                                loadTableData();
                        } else {
                                JOptionPane.showMessageDialog(this, "Xóa sản phẩm thất bại!", "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                        }
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
        }

        private javax.swing.JPanel containerPanel;
        private javax.swing.JButton jButton30;
        private javax.swing.JButton jButton31;
        private javax.swing.JButton jButton32;
        private javax.swing.JButton jButton33;
        private javax.swing.JButton jButton34;
        private javax.swing.JButton jButton35;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JPanel jPanel15;
        private javax.swing.JPanel jPanel17;
        private javax.swing.JPanel jPanel18;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JTable jTable2;
        private javax.swing.JPanel pnlContent;
        private javax.swing.JPanel pnlHeader;
        private javax.swing.JLabel searchLabel;
        private javax.swing.JComboBox<String> searchComboBox;
        private javax.swing.JTextField searchTextField;
}