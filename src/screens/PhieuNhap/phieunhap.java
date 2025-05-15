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
import screens.TrangChu.AppColors;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableCellRenderer;

public class phieunhap extends javax.swing.JPanel {
    private PhieuNhapBUS phieuNhapBUS;
    private SimpleDateFormat dateFormat;
    private javax.swing.JPanel pnlBottomButtons;

    public phieunhap() {
        phieuNhapBUS = new PhieuNhapBUS();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        initComponents();
        setupIcons();
        setupSearchComponents();
        loadPhieuNhapTable();
        setupTableListeners();
    }
    
    public javax.swing.JPanel getPhieuNhapPanel() {
        return containerPanel;
    }

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

    private void setupTableListeners() {
        jTable2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int row = jTable2.rowAtPoint(evt.getPoint());
                int col = jTable2.columnAtPoint(evt.getPoint());
                if (col == jTable2.getColumnCount() - 1 && row >=0) {
                    String maPN = jTable2.getValueAt(row, 1).toString();
                    PhieuNhapDTO phieuNhap = phieuNhapBUS.getPhieuNhap(maPN);
                    if (phieuNhap != null) {
                        new ChiTietPhieuNhapDialog(null, phieuNhap).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(phieunhap.this, "Không tìm thấy thông tin phiếu nhập.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        jTable2.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = jTable2.rowAtPoint(e.getPoint());
                int col = jTable2.columnAtPoint(e.getPoint());
                if (col == jTable2.getColumnCount() - 1 && row >=0) {
                    jTable2.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    jTable2.putClientProperty("hoverRow", row);
                } else {
                    jTable2.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    jTable2.putClientProperty("hoverRow", -1);
                }
                jTable2.repaint();
            }
             @Override
            public void mouseExited(MouseEvent e) {
                jTable2.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                jTable2.putClientProperty("hoverRow", -1);
                jTable2.repaint();
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        containerPanel = new javax.swing.JPanel();
        pnlHeader = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
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

        containerPanel.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        containerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader.setBackground(AppColors.NEW_HEADER_PANEL_BG_COLOR);
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabel5.setText("QUẢN LÝ PHIẾU NHẬP");
        jLabel5.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addContainerGap(20, Short.MAX_VALUE))
        );
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
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(
            new String[] { "Tất cả", "Mã PN", "Mã SP", "Mã NCC", "Trạng thái" }));
        jPanel33.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 180, 30));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14));
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
        jButton30.setPreferredSize(new Dimension(120, 30));
        jPanel33.add(jButton30, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 20, 120, 30));

        jButtonReset.setText("Làm mới");
        jButtonReset.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        jButtonReset.setForeground(java.awt.Color.WHITE);
        jButtonReset.setPreferredSize(new Dimension(120, 30));
        jPanel33.add(jButtonReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 120, 30));

        jPanel17.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        javax.swing.border.TitledBorder editBorder = javax.swing.BorderFactory.createTitledBorder(
            javax.swing.BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Chỉnh sửa");
        editBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
        jPanel17.setBorder(editBorder);
        jPanel17.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 23));

        Dimension editButtonSize = new Dimension(120, 34);
        jButton31.setText("Thêm");
        jButton31.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        jButton31.setForeground(java.awt.Color.WHITE);
        jButton31.setPreferredSize(editButtonSize);
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });
        jPanel17.add(jButton31);

        jButton32.setText("Sửa");
        jButton32.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        jButton32.setForeground(java.awt.Color.WHITE);
        jButton32.setPreferredSize(editButtonSize);
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });
        jPanel17.add(jButton32);

        jButton33.setText("Xóa");
        jButton33.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        jButton33.setForeground(java.awt.Color.WHITE);
        jButton33.setPreferredSize(editButtonSize);
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });
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
                "STT", "Mã PN", "Mã SP", "Mã NCC", "Số lượng", "Đơn giá", "Thành tiền", "Thời gian",
                "Trạng thái", "Hình thức TT", "Chi tiết"
            }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        });
        jTable2.setShowGrid(true);
        jTable2.setBackground(java.awt.Color.WHITE);
        jTable2.getTableHeader().setBackground(AppColors.NEW_HEADER_PANEL_BG_COLOR);
        jTable2.getTableHeader().setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        jTable2.setGridColor(AppColors.NEW_BORDER_LINES_COLOR);
        jTable2.setRowHeight(25);

        jTable2.getColumnModel().getColumn(jTable2.getColumnCount() - 1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);
                 if (isSelected) {
                    c.setForeground(table.getSelectionForeground());
                    c.setBackground(table.getSelectionBackground());
                } else {
                    c.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR);
                    c.setBackground(table.getBackground());
                }
                if (table.getClientProperty("hoverRow") != null && (int) table.getClientProperty("hoverRow") == row && column == table.getColumnCount() -1) {
                    c.setForeground(AppColors.NEW_SELECTED_BUTTON_COLOR);
                }
                return c;
            }
        });
        jScrollPane2.setViewportView(jTable2);
        jPanel18.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 940, 292));

        pnlBottomButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        pnlBottomButtons.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        Dimension bottomButtonSize = new Dimension(170, 40);

        btnImport.setText("Nhập dữ liệu");
        btnImport.setBackground(AppColors.NEW_QUICK_ACCESS_BUTTON_BG_COLOR);
        btnImport.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR);
        btnImport.setPreferredSize(bottomButtonSize);
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utils.FileUtils.importFromCSV(jTable2);
                loadPhieuNhapTable();
            }
        });
        pnlBottomButtons.add(btnImport);
        
        btnExport.setText("Xuất dữ liệu");
        btnExport.setBackground(AppColors.NEW_QUICK_ACCESS_BUTTON_BG_COLOR);
        btnExport.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR);
        btnExport.setPreferredSize(bottomButtonSize);
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utils.FileUtils.exportToCSV(jTable2, "Danh sách phiếu nhập");
            }
        });
        pnlBottomButtons.add(btnExport);

        btnPrinter.setText("In ấn");
        btnPrinter.setBackground(AppColors.NEW_QUICK_ACCESS_BUTTON_BG_COLOR);
        btnPrinter.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR);
        btnPrinter.setPreferredSize(bottomButtonSize);
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

        jTable2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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
            @Override public void insertUpdate(DocumentEvent e) { checkEmpty(); }
            @Override public void removeUpdate(DocumentEvent e) { checkEmpty(); }
            @Override public void changedUpdate(DocumentEvent e) { checkEmpty(); }
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
        String maPhieuNhap = jTable2.getValueAt(selectedRow, 1).toString();
        PhieuNhapDTO phieuNhap = phieuNhapBUS.getPhieuNhap(maPhieuNhap);
        if (phieuNhap != null) {
            DAO.SanPhamDAO sanPhamDAO = new DAO.SanPhamDAO();
            java.util.List<String> dsMaSP = sanPhamDAO.getAllMaSanPham();
            DAO.NhaCungCapDAO nhaCungCapDAO = new DAO.NhaCungCapDAO();
            java.util.List<String> dsMaNCC = nhaCungCapDAO.getAllSuppliers();
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
                            case "Mã PN": value = pn.getMaPhieuNhap(); break;
                            case "Mã SP": value = pn.getMaSanPham(); break;
                            case "Mã NCC": value = pn.getMaNhaCungCap(); break;
                            case "Trạng thái": value = pn.getTrangThai(); break;
                        }
                        if (value == null || !value.toLowerCase().contains(keyword.toLowerCase())) {
                            match = false;
                        }
                    }
                    if (!soLuongTu.isEmpty()) {
                        try { int soLuongMin = Integer.parseInt(soLuongTu); match = match && pn.getSoLuong() >= soLuongMin; } 
                        catch (NumberFormatException ex) { match = false; }
                    }
                    if (!soLuongDen.isEmpty()) {
                        try { int soLuongMax = Integer.parseInt(soLuongDen); match = match && pn.getSoLuong() <= soLuongMax; } 
                        catch (NumberFormatException ex) { match = false; }
                    }
                    if (!thanhTienTu.isEmpty()) {
                        try { double thanhTienMin = Double.parseDouble(thanhTienTu.replace(",", "")); match = match && pn.getThanhTien() >= thanhTienMin; } 
                        catch (NumberFormatException ex) { match = false; }
                    }
                    if (!thanhTienDen.isEmpty()) {
                        try { double thanhTienMax = Double.parseDouble(thanhTienDen.replace(",", "")); match = match && pn.getThanhTien() <= thanhTienMax; } 
                        catch (NumberFormatException ex) { match = false; }
                    }
                    if (match) { ketQua.add(pn); }
            }
        }

        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);
        int stt = 1;
        for (PhieuNhapDTO phieuNhap : ketQua) {
                String thoiGianStr = phieuNhap.getThoiGian() != null ? dateFormat.format(phieuNhap.getThoiGian()) : "";
                model.addRow(new Object[] {
                stt++, phieuNhap.getMaPhieuNhap(), phieuNhap.getMaSanPham(), phieuNhap.getMaNhaCungCap(),
                phieuNhap.getSoLuong(), phieuNhap.getDonGia(), phieuNhap.getThanhTien(),
                thoiGianStr, phieuNhap.getTrangThai(), phieuNhap.getHinhThucThanhToan(), "Xem chi tiết"
            });
            }
            if (ketQua.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu nhập nào khớp với tiêu chí tìm kiếm", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm phiếu nhập: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private javax.swing.JPanel containerPanel;
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