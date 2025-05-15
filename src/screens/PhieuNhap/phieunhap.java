package screens.PhieuNhap;

import javax.swing.*;
import java.util.List;
import DTO.PhieuNhapDTO;
import BUS.PhieuNhapBUS;
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
import java.awt.Font;
import javax.swing.JSeparator;
import java.util.Calendar;
import java.text.DecimalFormat;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;
import java.awt.GridLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class phieunhap extends javax.swing.JPanel {
    private PhieuNhapBUS phieuNhapBUS;
    private SimpleDateFormat dateFormat;
    private DecimalFormat decimalFormat;
    private javax.swing.JPanel pnlBottomButtons;

    private javax.swing.JButton btnFilterDate;
    private javax.swing.JComboBox<Integer> cbFromDay, cbFromMonth, cbFromYear;
    private javax.swing.JComboBox<Integer> cbToDay, cbToMonth, cbToYear;
    private javax.swing.JButton btnXoa;

    // Added for row coloring
    private List<Color> rowColors;
    private Map<String, Color> maBatchPrefixColorMap;
    private int nextColorIndex = 0;

    public phieunhap() {
        phieuNhapBUS = new PhieuNhapBUS();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        decimalFormat = new DecimalFormat("#,##0.00");
        
        // Initialize color fields
        rowColors = new ArrayList<>(Arrays.asList(
            new Color(245, 245, 245), // Very Light Gray
            new Color(235, 240, 250), // Lightest Blue
            new Color(235, 250, 235), // Lightest Green
            new Color(255, 240, 240), // Lightest Pink
            new Color(255, 250, 230)  // Lightest Yellow
        ));
        maBatchPrefixColorMap = new HashMap<>();
        
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

            ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/icon_img/delete.png"));
            if (deleteIcon.getImage() != null && btnXoa != null) {
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
        
        // Apply custom cell renderer for color-coding
        PhieuNhapTableCellRenderer customRenderer = new PhieuNhapTableCellRenderer();
        for (int i = 0; i < jTable2.getColumnCount(); i++) {
            jTable2.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }
    }
    
    // Custom cell renderer for color-coding rows by batch prefix
    private class PhieuNhapTableCellRenderer extends DefaultTableCellRenderer {
        private Set<Integer> centeredColumns;
        
        public PhieuNhapTableCellRenderer() {
            super();
            // Define which columns to center (STT, Mã PN, Mã SP, Mã NCC, Số lượng, etc.)
            centeredColumns = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 8, 9, 10));
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (!isSelected) {
                Object maPNObj = table.getModel().getValueAt(row, 1); // Mã phiếu nhập is at column 1
                String maPNFull = maPNObj != null ? maPNObj.toString() : "";
                
                String batchPrefix = "";
                if (!maPNFull.isEmpty() && maPNFull.contains("_")) {
                    batchPrefix = maPNFull.substring(0, maPNFull.indexOf('_'));
                } else {
                    batchPrefix = maPNFull; // Use full PN as prefix if no underscore
                }
                
                if (!batchPrefix.isEmpty()) {
                    // If this batch prefix hasn't been assigned a color yet, assign one
                    if (!maBatchPrefixColorMap.containsKey(batchPrefix)) {
                        Color colorToAssign = rowColors.get(nextColorIndex % rowColors.size());
                        
                        // Check if this color was used for the previous batch to avoid adjacent similar colors
                        if (nextColorIndex > 0 && maBatchPrefixColorMap.size() > 0) {
                            // Use a different color than the last one
                            Color lastColor = null;
                            for (Color c2 : maBatchPrefixColorMap.values()) {
                                lastColor = c2;
                            }
                            if (lastColor != null && lastColor.equals(colorToAssign)) {
                                nextColorIndex++;
                                colorToAssign = rowColors.get(nextColorIndex % rowColors.size());
                            }
                        }
                        
                        maBatchPrefixColorMap.put(batchPrefix, colorToAssign);
                        nextColorIndex++;
                    }
                    
                    // Apply the color for this batch
                    c.setBackground(maBatchPrefixColorMap.get(batchPrefix));
                } else {
                    c.setBackground(table.getBackground()); // Default color for rows without a batch prefix
                }
                
                c.setForeground(table.getForeground()); // Keep default text color
            } else {
                c.setBackground(table.getSelectionBackground());
                c.setForeground(table.getSelectionForeground());
            }
            
            // Apply text alignment based on column
            if (centeredColumns.contains(column)) {
                setHorizontalAlignment(JLabel.CENTER);
            } else {
                setHorizontalAlignment(JLabel.LEFT);
            }
            
            // Special styling for "Chi tiết" column
            if (column == table.getColumnCount() - 1) {
                if (!isSelected) {
                    c.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR);
                }
                // Apply hover effect
                if (table.getClientProperty("hoverRow") != null && 
                    (int) table.getClientProperty("hoverRow") == row) {
                    c.setForeground(AppColors.NEW_SELECTED_BUTTON_COLOR);
                }
            }
            
            return c;
        }
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
        pnlBoxChinhSua = new javax.swing.JPanel();
        btnXoa = new javax.swing.JButton();
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

        pnlBoxChinhSua.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        javax.swing.border.TitledBorder filterBorder = javax.swing.BorderFactory.createTitledBorder(
            javax.swing.BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Bộ lọc thời gian");
        filterBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlBoxChinhSua.setBorder(filterBorder);
        pnlBoxChinhSua.setLayout(new java.awt.BorderLayout());

        JPanel mainFilterPanel = new JPanel();
        mainFilterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 10));
        mainFilterPanel.setBackground(AppColors.NEW_MAIN_BG_COLOR);

        btnFilterDate = new JButton("Lọc");
        btnFilterDate.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        btnFilterDate.setForeground(java.awt.Color.WHITE);
        btnFilterDate.setPreferredSize(new Dimension(100, 30));
        btnFilterDate.setFont(new Font("Segoe UI", Font.BOLD, 12));
        mainFilterPanel.add(btnFilterDate);
        mainFilterPanel.add(Box.createHorizontalStrut(8));

        JPanel fromDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        fromDatePanel.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        JLabel lblFromDate = new JLabel("Từ ngày:");
        lblFromDate.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        lblFromDate.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        cbFromYear = new JComboBox<>(new Integer[]{2020, 2021, 2022, 2023, 2024, 2025});
        cbFromMonth = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        cbFromDay = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31});

        Dimension cbSize = new Dimension(80, 30);
        cbFromYear.setPreferredSize(cbSize);
        cbFromMonth.setPreferredSize(cbSize);
        cbFromDay.setPreferredSize(cbSize);

        fromDatePanel.add(lblFromDate);
        fromDatePanel.add(Box.createHorizontalStrut(3));
        fromDatePanel.add(cbFromDay);
        fromDatePanel.add(new JLabel("/"));
        fromDatePanel.add(cbFromMonth);
        fromDatePanel.add(new JLabel("/"));
        fromDatePanel.add(cbFromYear);
        mainFilterPanel.add(fromDatePanel);

        mainFilterPanel.add(Box.createHorizontalStrut(10));

        JPanel toDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        toDatePanel.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        JLabel lblToDate = new JLabel("Đến ngày:");
        lblToDate.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        lblToDate.setFont(new Font("Segoe UI", Font.BOLD, 12));

        cbToYear = new JComboBox<>(new Integer[]{2020, 2021, 2022, 2023, 2024, 2025});
        cbToMonth = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        cbToDay = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31});

        cbToYear.setPreferredSize(cbSize);
        cbToMonth.setPreferredSize(cbSize);
        cbToDay.setPreferredSize(cbSize);

        toDatePanel.add(lblToDate);
        toDatePanel.add(Box.createHorizontalStrut(3));
        toDatePanel.add(cbToDay);
        toDatePanel.add(new JLabel("/"));
        toDatePanel.add(cbToMonth);
        toDatePanel.add(new JLabel("/"));
        toDatePanel.add(cbToYear);
        mainFilterPanel.add(toDatePanel);

        JPanel wrapperPanel = new JPanel(new java.awt.BorderLayout());
        wrapperPanel.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        wrapperPanel.add(mainFilterPanel, java.awt.BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        
        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        separator.setPreferredSize(new Dimension(1, 25));
        separator.setForeground(AppColors.NEW_BORDER_LINES_COLOR);
        rightPanel.add(Box.createHorizontalStrut(10));
        rightPanel.add(separator);
        rightPanel.add(Box.createHorizontalStrut(10));
        
        btnXoa.setText("Xóa");
        btnXoa.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        btnXoa.setForeground(java.awt.Color.WHITE);
        btnXoa.setPreferredSize(new Dimension(100, 30));
        btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        rightPanel.add(btnXoa);
        wrapperPanel.add(rightPanel, java.awt.BorderLayout.EAST);
        
        pnlBoxChinhSua.add(wrapperPanel, java.awt.BorderLayout.CENTER);

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

        pnlBottomButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 10));
        pnlBottomButtons.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        Dimension bottomButtonSize = new Dimension(170, 40);

        lblTongTienPhieuNhap = new JLabel("Tổng tiền nhập hàng:");
        lblTongTienPhieuNhap.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTongTienPhieuNhap.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlBottomButtons.add(lblTongTienPhieuNhap);

        txtTongTienPhieuNhap = new JTextField("0.00");
        txtTongTienPhieuNhap.setEditable(false);
        txtTongTienPhieuNhap.setPreferredSize(new Dimension(150, 30));
        txtTongTienPhieuNhap.setHorizontalAlignment(JTextField.RIGHT);
        txtTongTienPhieuNhap.setBackground(java.awt.Color.WHITE);
        txtTongTienPhieuNhap.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlBottomButtons.add(txtTongTienPhieuNhap);
        
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
                showExportDialog();
            }
        });
        pnlBottomButtons.add(btnExport);

        btnPrinter.setText("In ấn");
        btnPrinter.setBackground(AppColors.NEW_QUICK_ACCESS_BUTTON_BG_COLOR);
        btnPrinter.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR);
        btnPrinter.setPreferredSize(bottomButtonSize);
        btnPrinter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPrintDialog();
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
                    .addComponent(pnlBoxChinhSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(pnlBoxChinhSua, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        
        btnFilterDate.addActionListener(e -> {
            Integer fromYear = (Integer) cbFromYear.getSelectedItem();
            Integer fromMonth = (Integer) cbFromMonth.getSelectedItem();
            Integer fromDay = (Integer) cbFromDay.getSelectedItem();
            Integer toYear = (Integer) cbToYear.getSelectedItem();
            Integer toMonth = (Integer) cbToMonth.getSelectedItem();
            Integer toDay = (Integer) cbToDay.getSelectedItem();
            filterByDateRangePhieuNhap(fromYear, fromMonth, fromDay, toYear, toMonth, toDay);
        });

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
        displayPhieuNhapList(danhSachPhieuNhap);
    }

    private void displayPhieuNhapList(List<PhieuNhapDTO> danhSachPhieuNhap) {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);
        
        int stt = 1;
        if (danhSachPhieuNhap != null) {
            for (PhieuNhapDTO phieuNhap : danhSachPhieuNhap) {
                String thoiGianStr = phieuNhap.getThoiGian() != null ? dateFormat.format(phieuNhap.getThoiGian()) : "";
                model.addRow(new Object[] {
                    stt++,
                    phieuNhap.getMaPhieuNhap(),
                    phieuNhap.getMaSanPham(),
                    phieuNhap.getMaNhaCungCap(),
                    phieuNhap.getSoLuong(),
                    decimalFormat.format(phieuNhap.getDonGia()),
                    decimalFormat.format(phieuNhap.getThanhTien()),
                    thoiGianStr,
                    phieuNhap.getTrangThai(),
                    phieuNhap.getHinhThucThanhToan(),
                    "Xem chi tiết"
                });
            }
        }
        if (model.getRowCount() == 0) {
            // Optionally, show a message if the list is empty after filtering or loading
            // JOptionPane.showMessageDialog(this, "Không có dữ liệu phiếu nhập.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        updateTongTienPhieuNhap();
    }

    private void filterByDateRangePhieuNhap(Integer fromYear, Integer fromMonth, Integer fromDay,
                                         Integer toYear, Integer toMonth, Integer toDay) {
        try {
            List<PhieuNhapDTO> allPhieuNhap = phieuNhapBUS.getAllPhieuNhap();
            List<PhieuNhapDTO> filteredList = new ArrayList<>();

            if (fromYear == null && fromMonth == null && fromDay == null &&
                toYear == null && toMonth == null && toDay == null) {
                displayPhieuNhapList(allPhieuNhap);
                return;
            }

            Calendar fromDate = Calendar.getInstance();
            Calendar toDate = Calendar.getInstance();
            Calendar rowDateCal = Calendar.getInstance();

            // Thiết lập ngày bắt đầu
            if (fromYear != null) {
                fromDate.set(Calendar.YEAR, fromYear);
                if (fromMonth != null) {
                    fromDate.set(Calendar.MONTH, fromMonth - 1); // Calendar.MONTH starts from 0
                    if (fromDay != null) {
                        fromDate.set(Calendar.DAY_OF_MONTH, fromDay);
                    } else {
                        fromDate.set(Calendar.DAY_OF_MONTH, 1); // Default to first day of month
                    }
                } else {
                    fromDate.set(Calendar.MONTH, 0); // Default to January
                    fromDate.set(Calendar.DAY_OF_MONTH, 1); // Default to first day of month
                }
            } else { // No start year, effectively no lower bound on date
                fromDate.set(Calendar.YEAR, 1900); // A very early year
                fromDate.set(Calendar.MONTH, 0);
                fromDate.set(Calendar.DAY_OF_MONTH, 1);
            }
            fromDate.set(Calendar.HOUR_OF_DAY, 0);
            fromDate.set(Calendar.MINUTE, 0);
            fromDate.set(Calendar.SECOND, 0);
            fromDate.set(Calendar.MILLISECOND, 0);

            // Thiết lập ngày kết thúc
            if (toYear != null) {
                toDate.set(Calendar.YEAR, toYear);
                if (toMonth != null) {
                    toDate.set(Calendar.MONTH, toMonth - 1);
                    if (toDay != null) {
                        toDate.set(Calendar.DAY_OF_MONTH, toDay);
                    } else {
                        // Default to last day of month
                        toDate.set(Calendar.DAY_OF_MONTH, toDate.getActualMaximum(Calendar.DAY_OF_MONTH));
                    }
                } else {
                    toDate.set(Calendar.MONTH, 11); // Default to December
                    // Default to last day of month
                    toDate.set(Calendar.DAY_OF_MONTH, toDate.getActualMaximum(Calendar.DAY_OF_MONTH));
                }
            } else { // No end year, effectively no upper bound on date
                toDate.set(Calendar.YEAR, 9999); // A very late year
                toDate.set(Calendar.MONTH, 11);
                toDate.set(Calendar.DAY_OF_MONTH, 31);
            }
            toDate.set(Calendar.HOUR_OF_DAY, 23);
            toDate.set(Calendar.MINUTE, 59);
            toDate.set(Calendar.SECOND, 59);
            toDate.set(Calendar.MILLISECOND, 999);
            
            // Lọc danh sách hóa đơn
            for (PhieuNhapDTO pn : allPhieuNhap) {
                if (pn.getThoiGian() != null) {
                    Date pnDate = pn.getThoiGian();
                    rowDateCal.setTime(pnDate);

                    // Check if rowDateCal is within [fromDate, toDate]
                    if (!rowDateCal.before(fromDate) && !rowDateCal.after(toDate)) {
                        filteredList.add(pn);
                    }
                }
            }

            displayPhieuNhapList(filteredList);

            if (filteredList.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Không tìm thấy phiếu nhập nào trong khoảng thời gian đã chọn",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi lọc dữ liệu theo ngày: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {
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
        String keyword = jTextField1.getText().trim().toLowerCase(); // Convert keyword to lowercase for case-insensitive search
        String soLuongTuStr = jTextFieldSoLuongTu.getText().trim();
        String soLuongDenStr = jTextFieldSoLuongDen.getText().trim();
        String thanhTienTuStr = jTextFieldThanhTienTu.getText().trim();
        String thanhTienDenStr = jTextFieldThanhTienDen.getText().trim();

        try {
            // Check if all search fields are empty
            if (keyword.isEmpty() && soLuongTuStr.isEmpty() && soLuongDenStr.isEmpty() 
                && thanhTienTuStr.isEmpty() && thanhTienDenStr.isEmpty()) {
                loadPhieuNhapTable(); // Load all data if no criteria
                return;
            }
            
            // If a specific search type (not "Tất cả") is chosen but keyword is empty,
            // and no other range filters are present, it's an invalid search.
            if (!truong.equals("Tất cả") && keyword.isEmpty() && 
                soLuongTuStr.isEmpty() && soLuongDenStr.isEmpty() && 
                thanhTienTuStr.isEmpty() && thanhTienDenStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập dữ liệu để tìm kiếm cho mục đã chọn",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                jTextField1.requestFocus();
                return;
            }

            List<PhieuNhapDTO> allPhieuNhap = phieuNhapBUS.getAllPhieuNhap();
            List<PhieuNhapDTO> filteredResults = new ArrayList<>();

            for (PhieuNhapDTO pn : allPhieuNhap) {
                boolean match = true;

                // 1. Keyword Filtering
                if (!keyword.isEmpty()) {
                    if (truong.equals("Tất cả")) {
                        boolean keywordMatch = (pn.getMaPhieuNhap() != null && pn.getMaPhieuNhap().toLowerCase().contains(keyword)) ||
                                             (pn.getMaSanPham() != null && pn.getMaSanPham().toLowerCase().contains(keyword)) ||
                                             (pn.getMaNhaCungCap() != null && pn.getMaNhaCungCap().toLowerCase().contains(keyword)) ||
                                             (pn.getTrangThai() != null && pn.getTrangThai().toLowerCase().contains(keyword));
                        if (!keywordMatch) {
                            match = false;
                        }
                    } else {
                        String valueToTest = "";
                        switch (truong) {
                            case "Mã PN": valueToTest = pn.getMaPhieuNhap(); break;
                            case "Mã SP": valueToTest = pn.getMaSanPham(); break;
                            case "Mã NCC": valueToTest = pn.getMaNhaCungCap(); break;
                            case "Trạng thái": valueToTest = pn.getTrangThai(); break;
                        }
                        if (valueToTest == null || !valueToTest.toLowerCase().contains(keyword)) {
                            match = false;
                        }
                    }
                }

                // 2. Số lượng Filtering (only if keyword match is still true)
                if (match && (!soLuongTuStr.isEmpty() || !soLuongDenStr.isEmpty())) {
                    int soLuong = pn.getSoLuong();
                    if (!soLuongTuStr.isEmpty()) {
                        try {
                            int soLuongMin = Integer.parseInt(soLuongTuStr);
                            if (soLuong < soLuongMin) match = false;
                        } catch (NumberFormatException ex) { /* Ignore if unparseable, or treat as no filter */ }
                    }
                    if (match && !soLuongDenStr.isEmpty()) {
                        try {
                            int soLuongMax = Integer.parseInt(soLuongDenStr);
                            if (soLuong > soLuongMax) match = false;
                        } catch (NumberFormatException ex) { /* Ignore */ }
                    }
                }

                // 3. Thành tiền Filtering (only if still a match)
                if (match && (!thanhTienTuStr.isEmpty() || !thanhTienDenStr.isEmpty())) {
                    double thanhTien = pn.getThanhTien();
                    if (!thanhTienTuStr.isEmpty()) {
                        try {
                            double thanhTienMin = Double.parseDouble(thanhTienTuStr.replace(",", ""));
                            if (thanhTien < thanhTienMin) match = false;
                        } catch (NumberFormatException ex) { /* Ignore */ }
                    }
                    if (match && !thanhTienDenStr.isEmpty()) {
                        try {
                            double thanhTienMax = Double.parseDouble(thanhTienDenStr.replace(",", ""));
                            if (thanhTien > thanhTienMax) match = false;
                        } catch (NumberFormatException ex) { /* Ignore */ }
                    }
                }

                if (match) {
                    filteredResults.add(pn);
                }
            }

            displayPhieuNhapList(filteredResults);

            if (filteredResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                                "Không tìm thấy phiếu nhập nào khớp với tiêu chí tìm kiếm", 
                                "Thông báo", 
                                JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm phiếu nhập: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // For debugging
        }
    }

    private void updateTongTienPhieuNhap() {
        double tongTien = 0;
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable2.getModel();
        int thanhTienColIndex = -1; // Initialize to -1

        // Find the "Thành tiền" column index dynamically
        for (int col = 0; col < model.getColumnCount(); col++) {
            if (model.getColumnName(col).equals("Thành tiền")) {
                thanhTienColIndex = col;
                break;
            }
        }

        if (thanhTienColIndex == -1) {
            System.err.println("updateTongTienPhieuNhap: Column 'Thành tiền' not found.");
            if (txtTongTienPhieuNhap != null) { // Check if txtTongTienPhieuNhap is initialized
                txtTongTienPhieuNhap.setText("Error");
            }
            return;
        }

        for (int i = 0; i < model.getRowCount(); i++) {
            Object thanhTienObj = model.getValueAt(i, thanhTienColIndex);
            if (thanhTienObj != null) {
                try {
                    // Parse the formatted string back to double for calculation
                    Number parsedNumber = decimalFormat.parse(thanhTienObj.toString());
                    tongTien += parsedNumber.doubleValue();
                } catch (java.text.ParseException e) {
                    System.err.println("Lỗi parse double ở hàng " + i + " cột 'Thành tiền' trong Phiếu Nhập: " + thanhTienObj + " Error: " + e.getMessage());
                }
            }
        }
        if (txtTongTienPhieuNhap != null) { // Check if txtTongTienPhieuNhap is initialized
            txtTongTienPhieuNhap.setText(decimalFormat.format(tongTien));
        }
    }

    private void showExportDialog() {
        if (jTable2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null,
                    "Không có dữ liệu để xuất!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Tạo dialog lựa chọn kiểu xuất
        JDialog exportOptionDialog = new JDialog();
        exportOptionDialog.setTitle("Lựa chọn xuất dữ liệu phiếu nhập");
        exportOptionDialog.setSize(400, 300);
        exportOptionDialog.setLocationRelativeTo(null);
        exportOptionDialog.setLayout(new BorderLayout());
        exportOptionDialog.setModal(true);
        
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
        
        // Radio buttons cho việc lựa chọn
        JRadioButton rbExportAll = new JRadioButton("Xuất toàn bộ phiếu nhập");
        JRadioButton rbExportBySupplier = new JRadioButton("Xuất phiếu nhập theo nhà cung cấp và đợt nhập");
        ButtonGroup group = new ButtonGroup();
        group.add(rbExportAll);
        group.add(rbExportBySupplier);
        rbExportAll.setSelected(true);
        
        optionPanel.add(Box.createVerticalStrut(20));
        optionPanel.add(rbExportAll);
        optionPanel.add(Box.createVerticalStrut(10));
        optionPanel.add(rbExportBySupplier);
        optionPanel.add(Box.createVerticalStrut(20));
        
        // Panel cho lựa chọn nhà cung cấp và đợt nhập
        JPanel supplierBatchPanel = new JPanel();
        supplierBatchPanel.setLayout(new GridLayout(2, 2, 10, 10));
        supplierBatchPanel.setBorder(BorderFactory.createTitledBorder("Lựa chọn nhà cung cấp và đợt nhập"));
        
        JComboBox<String> supplierComboBox = new JComboBox<>();
        JComboBox<String> batchComboBox = new JComboBox<>();
        
        // Disable ban đầu
        supplierComboBox.setEnabled(false);
        batchComboBox.setEnabled(false);
        
        // Lấy danh sách nhà cung cấp và đợt nhập từ dữ liệu hiện có
        List<String> uniqueSuppliers = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        
        for (int i = 0; i < model.getRowCount(); i++) {
            String maNCC = model.getValueAt(i, 3).toString(); // Cột 3 là mã NCC
            if (!uniqueSuppliers.contains(maNCC)) {
                uniqueSuppliers.add(maNCC);
                supplierComboBox.addItem(maNCC);
            }
        }
        
        // Cập nhật combo box đợt nhập khi chọn nhà cung cấp
        supplierComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                batchComboBox.removeAllItems();
                
                if (supplierComboBox.getSelectedItem() != null) {
                    String selectedSupplier = supplierComboBox.getSelectedItem().toString();
                    Set<String> uniqueBatchPrefixes = new HashSet<>();
                    
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if (model.getValueAt(i, 3).toString().equals(selectedSupplier)) { // MaNCC is at index 3
                            String maPN = model.getValueAt(i, 1).toString(); // MaPN is at index 1
                            if (maPN != null && maPN.contains("_")) {
                                String batchPrefix = maPN.substring(0, maPN.indexOf('_'));
                                if (!uniqueBatchPrefixes.contains(batchPrefix)) {
                                    uniqueBatchPrefixes.add(batchPrefix);
                                    batchComboBox.addItem(batchPrefix);
                                }
                            }
                        }
                    }
                }
            }
        });
        
        supplierBatchPanel.add(new JLabel("Nhà cung cấp:"));
        supplierBatchPanel.add(supplierComboBox);
        supplierBatchPanel.add(new JLabel("Đợt nhập (Mã PN):"));
        supplierBatchPanel.add(batchComboBox);
        
        optionPanel.add(supplierBatchPanel);
        
        // Enable/disable panel dựa trên lựa chọn radio button
        rbExportBySupplier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supplierComboBox.setEnabled(true);
                batchComboBox.setEnabled(true);
            }
        });
        
        rbExportAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supplierComboBox.setEnabled(false);
                batchComboBox.setEnabled(false);
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
                
                List<PhieuNhapDTO> phieuNhapList;
                
                if (rbExportAll.isSelected()) {
                    // Xuất toàn bộ phiếu nhập
                    phieuNhapList = phieuNhapBUS.getAllPhieuNhap();
                } else {
                    // Xuất theo nhà cung cấp và đợt nhập
                    if (supplierComboBox.getSelectedItem() == null || batchComboBox.getSelectedItem() == null) {
                        JOptionPane.showMessageDialog(null, 
                                "Vui lòng chọn nhà cung cấp và đợt nhập", 
                                "Thông báo", 
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    String selectedSupplier = supplierComboBox.getSelectedItem().toString();
                    String batchPrefix = batchComboBox.getSelectedItem().toString();
                    
                    phieuNhapList = phieuNhapBUS.getPhieuNhapBySupplierAndBatch(selectedSupplier, batchPrefix);
                }
                
                if (phieuNhapList.isEmpty()) {
                    JOptionPane.showMessageDialog(null, 
                            "Không có dữ liệu phiếu nhập để xuất", 
                            "Thông báo", 
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Hiển thị lựa chọn định dạng xuất
                String[] options = { "Excel (.xlsx)", "Word (.docx)" };
                int choice = JOptionPane.showOptionDialog(null, 
                        "Chọn định dạng file xuất cho Phiếu Nhập:", 
                        "Xuất File Phiếu Nhập",
                        JOptionPane.DEFAULT_OPTION, 
                        JOptionPane.QUESTION_MESSAGE, 
                        null, 
                        options, 
                        options[0]);
                
                // Chuẩn bị tên file
                String title = "DanhSachPhieuNhap";
                if (rbExportBySupplier.isSelected()) {
                    String selectedSupplier = supplierComboBox.getSelectedItem().toString();
                    String batchPrefix = batchComboBox.getSelectedItem().toString();
                    title = "PhieuNhap_" + selectedSupplier + "_" + batchPrefix;
                }
                
                // Tiến hành xuất file theo định dạng đã chọn
                if (choice == 0) {
                    // Xuất Excel
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Chọn nơi lưu file Excel Phiếu Nhập");
                    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));
                    fileChooser.setSelectedFile(new File(title + ".xlsx"));

                    int userSelection = fileChooser.showSaveDialog(null);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        String filePath = fileToSave.getAbsolutePath();
                        if (!filePath.toLowerCase().endsWith(".xlsx")) {
                            filePath += ".xlsx";
                        }
                        
                        exportPhieuNhapToExcel(phieuNhapList, filePath);
                    }
                } else if (choice == 1) {
                    // Xuất Word
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Chọn nơi lưu file Word Phiếu Nhập");
                    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Word files (*.docx)", "docx"));
                    fileChooser.setSelectedFile(new File(title + ".docx"));

                    int userSelection = fileChooser.showSaveDialog(null);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        String filePath = fileToSave.getAbsolutePath();
                        if (!filePath.toLowerCase().endsWith(".docx")) {
                            filePath += ".docx";
                        }
                        
                        exportPhieuNhapToWord(phieuNhapList, filePath);
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
    
    // Phương thức xuất phiếu nhập sang Excel
    private void exportPhieuNhapToExcel(List<PhieuNhapDTO> phieuNhapList, String filePath) {
        String[] headers = {
            "Mã Phiếu Nhập", "Mã Nhà Cung Cấp", "Mã Sản Phẩm", "Tên Sản Phẩm", 
            "Số Lượng", "Đơn Giá", "Thành Tiền", "Thời Gian",
            "Trạng Thái", "Hình Thức Thanh Toán"
        };
        
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Phiếu Nhập");
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            
            int rowNum = 1;
            for (PhieuNhapDTO pn : phieuNhapList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(pn.getMaPhieuNhap());
                row.createCell(1).setCellValue(pn.getMaNhaCungCap());
                row.createCell(2).setCellValue(pn.getMaSanPham());
                row.createCell(3).setCellValue(pn.getTenSanPham());
                row.createCell(4).setCellValue(pn.getSoLuong());
                row.createCell(5).setCellValue(pn.getDonGia());
                row.createCell(6).setCellValue(pn.getThanhTien());
                row.createCell(7).setCellValue(pn.getThoiGian() != null ? dateFormat.format(pn.getThoiGian()) : "");
                row.createCell(8).setCellValue(pn.getTrangThai());
                row.createCell(9).setCellValue(pn.getHinhThucThanhToan());
            }
            
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                JOptionPane.showMessageDialog(null, "Xuất file Excel Phiếu Nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel Phiếu Nhập: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Phương thức xuất phiếu nhập sang Word
    private void exportPhieuNhapToWord(List<PhieuNhapDTO> phieuNhapList, String filePath) {
        try (XWPFDocument document = new XWPFDocument()) {
            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setBold(true);
            titleRun.setFontSize(16);
            titleRun.setText("DANH SÁCH PHIẾU NHẬP");
            titleRun.addBreak();
            
            for (PhieuNhapDTO pn : phieuNhapList) {
                addWordParagraph(document, "Mã Phiếu Nhập: " + pn.getMaPhieuNhap());
                addWordParagraph(document, "Mã Nhà Cung Cấp: " + pn.getMaNhaCungCap());
                addWordParagraph(document, "Mã Sản Phẩm: " + pn.getMaSanPham());
                addWordParagraph(document, "Tên Sản Phẩm: " + pn.getTenSanPham());
                addWordParagraph(document, "Số Lượng: " + pn.getSoLuong());
                addWordParagraph(document, "Đơn Giá: " + decimalFormat.format(pn.getDonGia()));
                addWordParagraph(document, "Thành Tiền: " + decimalFormat.format(pn.getThanhTien()));
                addWordParagraph(document, "Thời Gian: " + (pn.getThoiGian() != null ? dateFormat.format(pn.getThoiGian()) : ""));
                addWordParagraph(document, "Trạng Thái: " + pn.getTrangThai());
                addWordParagraph(document, "Hình Thức Thanh Toán: " + pn.getHinhThucThanhToan());
                document.createParagraph().createRun().addBreak();
            }
            
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                document.write(fileOut);
                JOptionPane.showMessageDialog(null, "Xuất file Word Phiếu Nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Word Phiếu Nhập: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Method for adding paragraphs to Word document
    private void addWordParagraph(XWPFDocument document, String text) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(text);
    }

    private void showPrintDialog() {
        if (jTable2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null,
                    "Không có dữ liệu để in!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Tạo dialog lựa chọn kiểu in
        JDialog printOptionDialog = new JDialog();
        printOptionDialog.setTitle("Lựa chọn dữ liệu để in");
        printOptionDialog.setSize(400, 300);
        printOptionDialog.setLocationRelativeTo(null);
        printOptionDialog.setLayout(new BorderLayout());
        printOptionDialog.setModal(true);
        
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
        
        // Radio buttons cho việc lựa chọn
        JRadioButton rbPrintAll = new JRadioButton("In toàn bộ phiếu nhập");
        JRadioButton rbPrintBySupplier = new JRadioButton("In phiếu nhập theo nhà cung cấp và đợt nhập");
        ButtonGroup group = new ButtonGroup();
        group.add(rbPrintAll);
        group.add(rbPrintBySupplier);
        rbPrintAll.setSelected(true);
        
        optionPanel.add(Box.createVerticalStrut(20));
        optionPanel.add(rbPrintAll);
        optionPanel.add(Box.createVerticalStrut(10));
        optionPanel.add(rbPrintBySupplier);
        optionPanel.add(Box.createVerticalStrut(20));
        
        // Panel cho lựa chọn nhà cung cấp và đợt nhập
        JPanel supplierBatchPanel = new JPanel();
        supplierBatchPanel.setLayout(new GridLayout(2, 2, 10, 10));
        supplierBatchPanel.setBorder(BorderFactory.createTitledBorder("Lựa chọn nhà cung cấp và đợt nhập"));
        
        JComboBox<String> supplierComboBox = new JComboBox<>();
        JComboBox<String> batchComboBox = new JComboBox<>();
        
        // Disable ban đầu
        supplierComboBox.setEnabled(false);
        batchComboBox.setEnabled(false);
        
        // Lấy danh sách nhà cung cấp và đợt nhập từ dữ liệu hiện có
        List<String> uniqueSuppliers = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        
        for (int i = 0; i < model.getRowCount(); i++) {
            String maNCC = model.getValueAt(i, 3).toString(); // Cột 3 là mã NCC
            if (!uniqueSuppliers.contains(maNCC)) {
                uniqueSuppliers.add(maNCC);
                supplierComboBox.addItem(maNCC);
            }
        }
        
        // Cập nhật combo box đợt nhập khi chọn nhà cung cấp
        supplierComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                batchComboBox.removeAllItems();
                
                if (supplierComboBox.getSelectedItem() != null) {
                    String selectedSupplier = supplierComboBox.getSelectedItem().toString();
                    Set<String> uniqueBatchPrefixes = new HashSet<>();
                    
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if (model.getValueAt(i, 3).toString().equals(selectedSupplier)) { // MaNCC is at index 3
                            String maPN = model.getValueAt(i, 1).toString(); // MaPN is at index 1
                            if (maPN != null && maPN.contains("_")) {
                                String batchPrefix = maPN.substring(0, maPN.indexOf('_'));
                                if (!uniqueBatchPrefixes.contains(batchPrefix)) {
                                    uniqueBatchPrefixes.add(batchPrefix);
                                    batchComboBox.addItem(batchPrefix);
                                }
                            }
                        }
                    }
                }
            }
        });
        
        supplierBatchPanel.add(new JLabel("Nhà cung cấp:"));
        supplierBatchPanel.add(supplierComboBox);
        supplierBatchPanel.add(new JLabel("Đợt nhập (Mã PN):"));
        supplierBatchPanel.add(batchComboBox);
        
        optionPanel.add(supplierBatchPanel);
        
        // Enable/disable panel dựa trên lựa chọn radio button
        rbPrintBySupplier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supplierComboBox.setEnabled(true);
                batchComboBox.setEnabled(true);
            }
        });
        
        rbPrintAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supplierComboBox.setEnabled(false);
                batchComboBox.setEnabled(false);
            }
        });
        
        // Panel nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnOK = new JButton("Xác nhận In");
        JButton btnCancel = new JButton("Hủy");
        
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printOptionDialog.dispose();
                
                List<PhieuNhapDTO> phieuNhapList;
                
                if (rbPrintAll.isSelected()) {
                    // In toàn bộ phiếu nhập
                    phieuNhapList = phieuNhapBUS.getAllPhieuNhap();
                } else {
                    // In theo nhà cung cấp và đợt nhập
                    if (supplierComboBox.getSelectedItem() == null || batchComboBox.getSelectedItem() == null) {
                        JOptionPane.showMessageDialog(null, 
                                "Vui lòng chọn nhà cung cấp và đợt nhập", 
                                "Thông báo", 
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    String selectedSupplier = supplierComboBox.getSelectedItem().toString();
                    String batchPrefix = batchComboBox.getSelectedItem().toString();
                    
                    phieuNhapList = phieuNhapBUS.getPhieuNhapBySupplierAndBatch(selectedSupplier, batchPrefix);
                }
                
                if (phieuNhapList.isEmpty()) {
                    JOptionPane.showMessageDialog(null, 
                            "Không có dữ liệu phiếu nhập để in", 
                            "Thông báo", 
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Tiến hành in dữ liệu đã chọn
                printPhieuNhapData(phieuNhapList);
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
    
    // Phương thức để in dữ liệu phiếu nhập
    private void printPhieuNhapData(List<PhieuNhapDTO> phieuNhapList) {
        try {
            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><head><style>");
            htmlContent.append("body { font-family: Arial, sans-serif; margin: 20px; }");
            htmlContent.append("h1 { text-align: center; color: #333; }");
            htmlContent.append(".phieunhap-record { border: 1px solid #ccc; padding: 10px; margin-bottom: 15px; border-radius: 5px; page-break-inside: avoid; }");
            htmlContent.append(".field-label { font-weight: bold; color: #555; }");
            htmlContent.append("p { margin: 5px 0; }");
            htmlContent.append("</style></head><body>");
            htmlContent.append("<h1>Danh Sách Chi Tiết Phiếu Nhập</h1>");

            for (PhieuNhapDTO pn : phieuNhapList) {
                htmlContent.append("<div class='phieunhap-record'>");
                htmlContent.append("<p><span class='field-label'>Mã Phiếu Nhập:</span> ").append(pn.getMaPhieuNhap() != null ? pn.getMaPhieuNhap() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Mã Nhà Cung Cấp:</span> ").append(pn.getMaNhaCungCap() != null ? pn.getMaNhaCungCap() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Mã Sản Phẩm:</span> ").append(pn.getMaSanPham() != null ? pn.getMaSanPham() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Tên Sản Phẩm:</span> ").append(pn.getTenSanPham() != null ? pn.getTenSanPham() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Số Lượng:</span> ").append(pn.getSoLuong()).append("</p>");
                htmlContent.append("<p><span class='field-label'>Đơn Giá:</span> ").append(decimalFormat.format(pn.getDonGia())).append("</p>");
                htmlContent.append("<p><span class='field-label'>Thành Tiền:</span> ").append(decimalFormat.format(pn.getThanhTien())).append("</p>");
                htmlContent.append("<p><span class='field-label'>Thời Gian:</span> ").append(pn.getThoiGian() != null ? dateFormat.format(pn.getThoiGian()) : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Trạng Thái:</span> ").append(pn.getTrangThai() != null ? pn.getTrangThai() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Hình Thức Thanh Toán:</span> ").append(pn.getHinhThucThanhToan() != null ? pn.getHinhThucThanhToan() : "").append("</p>");
                htmlContent.append("</div>");
            }
            htmlContent.append("</body></html>");

            JEditorPane editorPane = new JEditorPane();
            editorPane.setContentType("text/html");
            editorPane.setText(htmlContent.toString());
            editorPane.setEditable(false);

            boolean printed = editorPane.print();
            if (!printed) {
                // Không hiển thị thông báo khi người dùng hủy lệnh in
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

    private javax.swing.JPanel containerPanel;
    private javax.swing.JButton jButton30;
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
    private javax.swing.JPanel pnlBoxChinhSua;
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
    private javax.swing.JTextField txtTongTienPhieuNhap;
    private javax.swing.JLabel lblTongTienPhieuNhap;
}