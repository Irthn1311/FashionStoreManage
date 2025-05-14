package screens.PhieuNhap;

import javax.swing.*;
import java.util.List;
import DTO.PhieuNhapDTO;
import BUS.PhieuNhapBUS;
import screens.PhieuNhap.ThemPhieuNhapDialog;
import java.text.SimpleDateFormat;
import java.util.Date;
import DAO.SanPhamDAO;
import DAO.NhaCungCapDAO;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingUtilities;

public class phieunhap extends javax.swing.JPanel {
    private PhieuNhapBUS phieuNhapBUS;
    private SimpleDateFormat dateFormat;

    public phieunhap() {
        phieuNhapBUS = new PhieuNhapBUS();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        initComponents();
        setupIcons();
        setupSearchComponents();
        loadPhieuNhapTable();
    }
    
    public javax.swing.JPanel getPhieuNhapPanel() {
        return containerPanel;
    }

    private void setupIcons() {
        try {
            ImageIcon invoiceIcon = new ImageIcon(getClass().getResource("/icon_img/invoice.png"));
            if (invoiceIcon.getImage() != null) {
                Image scaledInvoiceIcon = invoiceIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                jLabel5.setIcon(new ImageIcon(scaledInvoiceIcon));
                jLabel5.setHorizontalTextPosition(JLabel.RIGHT);
                jLabel5.setIconTextGap(10);
            }

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

            ImageIcon resetIcon = new ImageIcon(getClass().getResource("/icon_img/refresh.png"));
            if (resetIcon.getImage() != null) {
                Image scaledResetIcon = resetIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                jButtonReset.setIcon(new ImageIcon(scaledResetIcon));
                jButtonReset.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                jButtonReset.setIconTextGap(5);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Không thể tải biểu tượng: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        containerPanel = new javax.swing.JPanel();
        containerPanel.setPreferredSize(new java.awt.Dimension(1000, 700));
        containerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1 = new javax.swing.JButton();
        pnlHeader = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jButtonReset = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        btnExport = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        btnPrinter = new javax.swing.JButton();
        jPanel33 = new javax.swing.JPanel();
        jButton30 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
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

        jButton1.setText("jButton1");

        pnlHeader.setBackground(new java.awt.Color(12, 150, 156));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24));
        jLabel5.setText("Chi tiết phiếu nhập");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                .addContainerGap(411, Short.MAX_VALUE)
                .addComponent(jLabel5)
                                .addGap(387, 387, 387)));
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(14, Short.MAX_VALUE)));

        containerPanel.add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 70));

        pnlContent.setBackground(new java.awt.Color(107, 163, 190));

        jPanel17.setBackground(new java.awt.Color(107, 163, 190));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chỉnh sửa"));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton31.setText("Thêm");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });
        jPanel17.add(jButton31, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 120, 34));

        jButton32.setText("Sửa");
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });
        jPanel17.add(jButton32, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 120, 34));

        jButton33.setText("Xóa");
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });
        jPanel17.add(jButton33, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, 120, 34));

        jButtonReset.setText("Làm mới");
        jPanel17.add(jButtonReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 20, 120, 34));

        jPanel18.setBackground(new java.awt.Color(107, 163, 190));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng thông tin"));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] {
                        "STT", "Mã PN", "Mã SP", "Mã NCC", "Số lượng", "Đơn giá", "Thành tiền", "Thời gian",
                        "Trạng thái", "Hình thức thanh toán", "Chi tiết"
                }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable edit for all cells
            }
        });
        jTable2.setShowGrid(true);
        jScrollPane2.setViewportView(jTable2);
        // Đặt renderer cho cột "Chi tiết" (cột cuối cùng)
        jTable2.getColumnModel().getColumn(jTable2.getColumnCount() - 1).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if ("Xem chi tiết".equals(value)) {
                    c.setForeground(new java.awt.Color(0, 51, 153)); // Xanh đậm
                    c.setFont(c.getFont().deriveFont(java.awt.Font.BOLD));
                } else {
                    c.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
                    c.setFont(c.getFont().deriveFont(java.awt.Font.PLAIN));
                }
                return c;
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 948,
                                Short.MAX_VALUE));
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 289,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));

        btnExport.setText("Xuất file phiếu nhập");
        btnExport.setPreferredSize(new java.awt.Dimension(120, 34));
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utils.FileUtils.exportToCSV(jTable2, "Danh sách phiếu nhập");
            }
        });

        btnImport = new javax.swing.JButton("Import");
        btnImport.setPreferredSize(new java.awt.Dimension(120, 34));
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utils.FileUtils.importFromCSV(jTable2);
                loadPhieuNhapTable();
            }
        });

        btnPrinter = new javax.swing.JButton("In");
        btnPrinter.setPreferredSize(new java.awt.Dimension(120, 34));
        btnPrinter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    jTable2.print();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi in: " + e.getMessage(), "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        jPanel33.setBackground(new java.awt.Color(107, 163, 190));
        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tìm kiếm"));
        jPanel33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton30.setText("Tìm kiếm");
        jPanel33.add(jButton30, new org.netbeans.lib.awtextra.AbsoluteConstraints(832, 40, 120, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel2.setText("Tìm kiếm");
        jPanel33.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 30));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "Tất cả", "Mã PN", "Mã SP", "Mã NCC", "Trạng thái" }));
        jPanel33.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 180, 30));

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jPanel33.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, 180, 30));

        jLabelThanhTien.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelThanhTien.setText("Thành tiền:");
        jPanel33.add(jLabelThanhTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 80, 30));

        jLabelThanhTienTu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelThanhTienTu.setText("Từ:");
        jPanel33.add(jLabelThanhTienTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, -1, 30));
        jPanel33.add(jTextFieldThanhTienTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, 80, 30));

        jLabelThanhTienDen.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelThanhTienDen.setText("Đến:");
        jPanel33.add(jLabelThanhTienDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 20, -1, 30));
        jPanel33.add(jTextFieldThanhTienDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, 80, 30));

        jLabelSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelSoLuong.setText("Số lượng:");
        jPanel33.add(jLabelSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 60, 80, 30));

        jLabelSoLuongTu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelSoLuongTu.setText("Từ:");
        jPanel33.add(jLabelSoLuongTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, -1, 30));
        jPanel33.add(jTextFieldSoLuongTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, 80, 30));

        jLabelSoLuongDen.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelSoLuongDen.setText("Đến:");
        jPanel33.add(jLabelSoLuongDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 60, -1, 30));
        jPanel33.add(jTextFieldSoLuongDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 60, 80, 30));

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                                .addGroup(pnlContentLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, 960,
                                                Short.MAX_VALUE))
                                .addContainerGap(20, Short.MAX_VALUE))
                        .addGroup(pnlContentLayout.createSequentialGroup()
                                .addGap(300, 300, 300)
                                .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(btnPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 90,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                                .addGroup(pnlContentLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 34,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 34,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, 34,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(16, 16, 16)));

        containerPanel.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1000, 630));

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        add(containerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 700));

        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jTable2.rowAtPoint(evt.getPoint());
                int col = jTable2.columnAtPoint(evt.getPoint());
                if (col == 10) {
                    String maPN = jTable2.getValueAt(row, 1).toString();
                    PhieuNhapDTO phieuNhap = phieuNhapBUS.getPhieuNhap(maPN);
                    new ChiTietPhieuNhapDialog(null, phieuNhap).setVisible(true);
                }
            }
        });

        jTable2.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    private void setupSearchComponents() {
        jButton30.addActionListener(e -> timKiemPhieuNhap());

        jButtonReset.addActionListener(e -> resetSearchFields());

        jTextField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    timKiemPhieuNhap();
                }
            }
        });

        jTextFieldSoLuongTu.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    timKiemPhieuNhap();
                }
            }
        });

        jTextFieldSoLuongDen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    timKiemPhieuNhap();
                }
            }
        });

        jTextFieldThanhTienTu.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    timKiemPhieuNhap();
                }
            }
        });

        jTextFieldThanhTienDen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                timKiemPhieuNhap();
                }
            }
        });

        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkEmpty();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkEmpty();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkEmpty();
            }

            private void checkEmpty() {
                if (jTextField1.getText().trim().isEmpty() && jTextFieldSoLuongTu.getText().trim().isEmpty()
                        && jTextFieldSoLuongDen.getText().trim().isEmpty()
                        && jTextFieldThanhTienTu.getText().trim().isEmpty()
                        && jTextFieldThanhTienDen.getText().trim().isEmpty()) {
                    loadPhieuNhapTable();
                }
            }
        });
    }

    private void resetSearchFields() {
        jTextField1.setText("");
        jComboBox1.setSelectedIndex(0);
        jTextFieldSoLuongTu.setText("");
        jTextFieldSoLuongDen.setText("");
        jTextFieldThanhTienTu.setText("");
        jTextFieldThanhTienDen.setText("");
        loadPhieuNhapTable();
    }

    private void loadPhieuNhapTable() {
        List<PhieuNhapDTO> danhSachPhieuNhap = phieuNhapBUS.getAllPhieuNhap();
        
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);
        
        int stt = 1;
        for (PhieuNhapDTO phieuNhap : danhSachPhieuNhap) {
            String thoiGianStr = phieuNhap.getThoiGian() != null ? dateFormat.format(phieuNhap.getThoiGian()) : "";
            model.addRow(new Object[] {
                stt++,
                phieuNhap.getMaPhieuNhap(),
                phieuNhap.getMaSanPham(),
                phieuNhap.getMaNhaCungCap(),
                phieuNhap.getSoLuong(),
                phieuNhap.getDonGia(),
                phieuNhap.getThanhTien(),
                    thoiGianStr,
                phieuNhap.getTrangThai(),
                phieuNhap.getHinhThucThanhToan(),
                "Xem chi tiết"
            });
        }
    }

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {
        // Lấy JFrame cha từ phiếu nhập hiện tại
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        ThemPhieuNhapDialog dialog = new ThemPhieuNhapDialog(parentFrame);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            PhieuNhapDTO newPhieuNhap = dialog.getNewPhieuNhap();
            if (phieuNhapBUS.createPhieuNhap(newPhieuNhap)) {
                javax.swing.JOptionPane.showMessageDialog(null, "Thêm phiếu nhập thành công!");
                loadPhieuNhapTable();
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Thêm phiếu nhập thất bại!");
            }
        }
    }

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập cần sửa!");
            return;
        }

        // Lấy mã phiếu nhập từ dòng được chọn
        String maPhieuNhap = jTable2.getValueAt(selectedRow, 1).toString();
        PhieuNhapDTO phieuNhap = phieuNhapBUS.getPhieuNhap(maPhieuNhap);
        if (phieuNhap != null) {
            // Lấy danh sách mã sản phẩm và mã nhà cung cấp
            DAO.SanPhamDAO sanPhamDAO = new DAO.SanPhamDAO();
            java.util.List<String> dsMaSP = sanPhamDAO.getAllMaSanPham();
            DAO.NhaCungCapDAO nhaCungCapDAO = new DAO.NhaCungCapDAO();
            java.util.List<String> dsMaNCC = nhaCungCapDAO.getAllSuppliers();

            // Mở dialog sửa phiếu nhập
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            SuaPhieuNhapDialog dialog = new SuaPhieuNhapDialog(parentFrame, phieuNhap, dsMaSP, dsMaNCC);
            dialog.setVisible(true);

            if (dialog.isSaved()) {
                PhieuNhapDTO updated = dialog.getUpdatedPhieuNhap();
                if (phieuNhapBUS.updatePhieuNhap(updated)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật phiếu nhập thành công!");
                    loadPhieuNhapTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật phiếu nhập thất bại!");
                }
            }
        }
    }

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(null, "Vui lòng chọn dữ liệu cần xóa!");
            return;
        }
        String maPN = jTable2.getValueAt(selectedRow, 1).toString();
        int confirm = javax.swing.JOptionPane.showConfirmDialog(null, 
            "Bạn có chắc chắn muốn xóa phiếu nhập này?", 
            "Xác nhận xóa", 
            javax.swing.JOptionPane.YES_NO_OPTION);

        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            try {
                if (phieuNhapBUS.deletePhieuNhap(maPN)) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Xóa thành công!");
                    loadPhieuNhapTable();
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "Xóa thất bại!");
                }
            } catch (RuntimeException ex) {
                javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    private void timKiemPhieuNhap() {
        String truong = jComboBox1.getSelectedItem().toString();
        String keyword = jTextField1.getText().trim();
        String soLuongTu = jTextFieldSoLuongTu.getText().trim();
        String soLuongDen = jTextFieldSoLuongDen.getText().trim();
        String thanhTienTu = jTextFieldThanhTienTu.getText().trim();
        String thanhTienDen = jTextFieldThanhTienDen.getText().trim();

        try {
            if (!truong.equals("Tất cả") && keyword.isEmpty() && soLuongTu.isEmpty() && soLuongDen.isEmpty()
                    && thanhTienTu.isEmpty() && thanhTienDen.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập dữ liệu để tìm kiếm",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                jTextField1.requestFocus();
                return;
            }

        List<PhieuNhapDTO> danhSachPhieuNhap = phieuNhapBUS.getAllPhieuNhap();
        List<PhieuNhapDTO> ketQua = new ArrayList<>();

            if (keyword.isEmpty() && soLuongTu.isEmpty() && soLuongDen.isEmpty() && thanhTienTu.isEmpty()
                    && thanhTienDen.isEmpty()) {
            ketQua = danhSachPhieuNhap;
        } else {
            for (PhieuNhapDTO pn : danhSachPhieuNhap) {
                    boolean match = true;

                    if (!truong.equals("Tất cả") && !keyword.isEmpty()) {
                String value = "";
                        switch (truong) {
                            case "Mã PN":
                                value = pn.getMaPhieuNhap();
                                break;
                            case "Mã SP":
                                value = pn.getMaSanPham();
                                break;
                            case "Mã NCC":
                                value = pn.getMaNhaCungCap();
                                break;
                            case "Trạng thái":
                                value = pn.getTrangThai();
                                break;
                        }
                        if (value == null || !value.toLowerCase().contains(keyword.toLowerCase())) {
                            match = false;
                        }
                    }

                    if (!soLuongTu.isEmpty()) {
                        try {
                            int soLuongMin = Integer.parseInt(soLuongTu);
                            match = match && pn.getSoLuong() >= soLuongMin;
                        } catch (NumberFormatException ex) {
                            match = false;
                        }
                    }
                    if (!soLuongDen.isEmpty()) {
                        try {
                            int soLuongMax = Integer.parseInt(soLuongDen);
                            match = match && pn.getSoLuong() <= soLuongMax;
                        } catch (NumberFormatException ex) {
                            match = false;
                        }
                    }

                    if (!thanhTienTu.isEmpty()) {
                        try {
                            double thanhTienMin = Double.parseDouble(thanhTienTu.replace(",", ""));
                            match = match && pn.getThanhTien() >= thanhTienMin;
                        } catch (NumberFormatException ex) {
                            match = false;
                        }
                    }
                    if (!thanhTienDen.isEmpty()) {
                        try {
                            double thanhTienMax = Double.parseDouble(thanhTienDen.replace(",", ""));
                            match = match && pn.getThanhTien() <= thanhTienMax;
                        } catch (NumberFormatException ex) {
                            match = false;
                        }
                    }

                    if (match) {
                    ketQua.add(pn);
                }
            }
        }

        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);
        int stt = 1;
        for (PhieuNhapDTO phieuNhap : ketQua) {
                String thoiGianStr = phieuNhap.getThoiGian() != null ? dateFormat.format(phieuNhap.getThoiGian()) : "";
                model.addRow(new Object[] {
                stt++,
                phieuNhap.getMaPhieuNhap(),
                phieuNhap.getMaSanPham(),
                phieuNhap.getMaNhaCungCap(),
                phieuNhap.getSoLuong(),
                phieuNhap.getDonGia(),
                phieuNhap.getThanhTien(),
                        thoiGianStr,
                phieuNhap.getTrangThai(),
                phieuNhap.getHinhThucThanhToan(),
                "Xem chi tiết"
            });
            }

            if (ketQua.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy phiếu nhập nào khớp với tiêu chí tìm kiếm",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tìm kiếm phiếu nhập: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(phieunhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new phieunhap().setVisible(true);
            }
        });
    }

    private javax.swing.JPanel containerPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnPrinter;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JButton jButtonReset;
}