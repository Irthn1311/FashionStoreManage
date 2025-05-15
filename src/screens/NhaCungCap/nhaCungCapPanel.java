/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package screens.NhaCungCap;

import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import DAO.NhaCungCapDAO;
import DTO.nhaCungCapDTO;
import java.util.List;
import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import BUS.NhaCungCapBUS;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.Cursor;
import javax.swing.ListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import utils.FileUtils;
import BUS.NhaCungCap_SanPhamBUS;
import DTO.NhaCungCap_SanPhamDTO;
import javax.swing.JEditorPane;
import java.text.DecimalFormat;
import BUS.SanPhamBUS;
import DTO.sanPhamDTO;
import BUS.PhieuNhapBUS;
import DTO.PhieuNhapDTO;
import screens.TrangChu.AppColors;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author nson9
 */
public class nhaCungCapPanel extends javax.swing.JPanel {
    private NhaCungCapDAO nhaCungCapDAO;
    private NhaCungCapBUS nhaCungCapBUS;
    private DefaultTableModel tableModel;

    // UI Components similar to nhanVienPanel
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JLabel lblHeaderTitle;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlBoxTimKiem;
    private javax.swing.JLabel lblTimKiemTieuChi;
    private javax.swing.JComboBox<String> cboTimKiemTieuChi;
    private javax.swing.JTextField txtTimKiemKeyword;
    private javax.swing.JLabel lblTimKiemNamHopTac;
    private javax.swing.JComboBox<String> cboTimKiemNamHopTac;
    private javax.swing.JLabel lblTimKiemTrangThai;
    private javax.swing.JComboBox<String> cboTimKiemTrangThai;
    private javax.swing.JButton btnTimKiemAction;
    private javax.swing.JButton btnResetTimKiem;
    private javax.swing.JPanel pnlBoxChinhSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnTrangThaiNCC; // Renamed to avoid conflict with NhanVien's btnTrangThai if used elsewhere
    private javax.swing.JPanel pnlBoxTable; // Changed from pnlTableContainer
    private javax.swing.JScrollPane scTable;
    private javax.swing.JTable nhaCungCapTable;
    private javax.swing.JPanel pnlBottomButtons;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnPrinter;

    /**
     * Creates new form ncc
     */
    public nhaCungCapPanel() {
        nhaCungCapBUS = new NhaCungCapBUS(); // Initialize BUS first
        nhaCungCapDAO = new NhaCungCapDAO(); // Initialize DAO if needed directly, though BUS should handle it
        initComponents(); // This will now be a new method
        setupIcons();
        applyColors();
        setupSearchComponents();
        loadData();
        setupTable();
        setupActionListeners();
    }

    private void applyColors() {
        setBackground(AppColors.NEW_MAIN_BG_COLOR);

        if (pnlHeader != null) pnlHeader.setBackground(AppColors.NEW_HEADER_PANEL_BG_COLOR);
        if (lblHeaderTitle != null) lblHeaderTitle.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);

        if (pnlContent != null) pnlContent.setBackground(AppColors.NEW_MAIN_BG_COLOR);

        if (pnlBoxTimKiem != null) {
            pnlBoxTimKiem.setBackground(AppColors.NEW_MAIN_BG_COLOR);
            javax.swing.border.TitledBorder searchBorder = javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Tìm kiếm");
            searchBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
            pnlBoxTimKiem.setBorder(searchBorder);
        }
        if (lblTimKiemTieuChi != null) lblTimKiemTieuChi.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        if (lblTimKiemNamHopTac != null) lblTimKiemNamHopTac.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        if (lblTimKiemTrangThai != null) lblTimKiemTrangThai.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);

        if (btnTimKiemAction != null) {
            btnTimKiemAction.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
            btnTimKiemAction.setForeground(Color.WHITE);
        }
        if (btnResetTimKiem != null) {
            btnResetTimKiem.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
            btnResetTimKiem.setForeground(Color.WHITE);
        }

        if (pnlBoxChinhSua != null) {
            pnlBoxChinhSua.setBackground(AppColors.NEW_MAIN_BG_COLOR);
            javax.swing.border.TitledBorder editBorder = javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Chỉnh sửa");
            editBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
            pnlBoxChinhSua.setBorder(editBorder);
        }
        Color editButtonBg = AppColors.NEW_DEFAULT_BUTTON_COLOR;
        Color editButtonFg = Color.WHITE;
        if (btnThem != null) { btnThem.setBackground(editButtonBg); btnThem.setForeground(editButtonFg); }
        if (btnSua != null) { btnSua.setBackground(editButtonBg); btnSua.setForeground(editButtonFg); }
        if (btnXoa != null) { btnXoa.setBackground(editButtonBg); btnXoa.setForeground(editButtonFg); }
        if (btnTrangThaiNCC != null) { btnTrangThaiNCC.setBackground(editButtonBg); btnTrangThaiNCC.setForeground(editButtonFg); }

        if (pnlBoxTable != null) {
            pnlBoxTable.setBackground(AppColors.NEW_MAIN_BG_COLOR);
            javax.swing.border.TitledBorder tableBorder = javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Bảng thông tin");
            tableBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
            pnlBoxTable.setBorder(tableBorder);
        }
        if (nhaCungCapTable != null) {
            nhaCungCapTable.setBackground(Color.WHITE);
            nhaCungCapTable.getTableHeader().setBackground(AppColors.NEW_HEADER_PANEL_BG_COLOR);
            nhaCungCapTable.getTableHeader().setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
            nhaCungCapTable.setGridColor(AppColors.NEW_BORDER_LINES_COLOR);
        }

        if (pnlBottomButtons != null) pnlBottomButtons.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        Color bottomButtonBg = AppColors.NEW_QUICK_ACCESS_BUTTON_BG_COLOR;
        Color bottomButtonFg = AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR;
        if (btnImport != null) { btnImport.setBackground(bottomButtonBg); btnImport.setForeground(bottomButtonFg); }
        if (btnExport != null) { btnExport.setBackground(bottomButtonBg); btnExport.setForeground(bottomButtonFg); }
        if (btnPrinter != null) { btnPrinter.setBackground(bottomButtonBg); btnPrinter.setForeground(bottomButtonFg); }
    }

    private void setButtonIcon(javax.swing.JButton button, String iconPath) {
        if (button == null) return;
        URL imgUrl = getClass().getResource(iconPath);
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            Image scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledIcon));
        } else {
            System.err.println("Error loading icon: " + iconPath + " not found.");
        }
    }

    private void setupIcons() {
        try {
            setButtonIcon(btnTimKiemAction, "/icon_img/search.png");
            setButtonIcon(btnResetTimKiem, "/icon_img/refresh.png");
            setButtonIcon(btnThem, "/icon_img/add.png");
            setButtonIcon(btnSua, "/icon_img/edit.png");
            setButtonIcon(btnXoa, "/icon_img/delete.png");
            // Icon for btnTrangThaiNCC will be set dynamically
            setButtonIcon(btnImport, "/icon_img/import_icon.png");
            setButtonIcon(btnExport, "/icon_img/export_icon.png");
            setButtonIcon(btnPrinter, "/icon_img/print_icon.png");

            javax.swing.JButton[] buttonsWithIcons = {
                btnTimKiemAction, btnResetTimKiem, btnThem, btnSua, btnXoa, btnTrangThaiNCC,
                btnImport, btnExport, btnPrinter
            };
            for (javax.swing.JButton btn : buttonsWithIcons) {
                if (btn != null && btn.getIcon() != null) {
                    btn.setHorizontalTextPosition(SwingConstants.RIGHT);
                    btn.setIconTextGap(5);
                }
            }
                } catch (Exception e) {
            System.err.println("An unexpected error occurred during icon setup: " + e.getMessage());
                    e.printStackTrace();
                }
    }

    private void initComponents() {
        setPreferredSize(new java.awt.Dimension(1000, 700));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader = new javax.swing.JPanel();
        lblHeaderTitle = new javax.swing.JLabel();

        pnlHeader.setBackground(AppColors.NEW_HEADER_PANEL_BG_COLOR); // Applied in applyColors
        lblHeaderTitle.setFont(new java.awt.Font("Segoe UI", 1, 24));
        lblHeaderTitle.setText("QUẢN LÝ NHÀ CUNG CẤP");
        // lblHeaderTitle.setForeground(AppColors.NEW_MAIN_TEXT_COLOR); // Applied in applyColors

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap(380, Short.MAX_VALUE) // Adjusted for typical centering
                .addComponent(lblHeaderTitle)
                .addContainerGap(380, Short.MAX_VALUE))
        );
        pnlHeaderLayout.setVerticalGroup(
                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblHeaderTitle)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 70));

        pnlContent = new javax.swing.JPanel();
        pnlContent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // Search Panel (pnlBoxTimKiem)
        pnlBoxTimKiem = new javax.swing.JPanel();
        pnlBoxTimKiem.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTimKiemTieuChi = new javax.swing.JLabel("Tìm theo");
        lblTimKiemTieuChi.setFont(new java.awt.Font("Segoe UI", 0, 14));
        pnlBoxTimKiem.add(lblTimKiemTieuChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 30));

        cboTimKiemTieuChi = new javax.swing.JComboBox<>();
        pnlBoxTimKiem.add(cboTimKiemTieuChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 180, 30));

        txtTimKiemKeyword = new javax.swing.JTextField();
        pnlBoxTimKiem.add(txtTimKiemKeyword, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, 180, 30));

        lblTimKiemNamHopTac = new javax.swing.JLabel("Năm hợp tác");
        lblTimKiemNamHopTac.setFont(new java.awt.Font("Segoe UI", 0, 14));
        pnlBoxTimKiem.add(lblTimKiemNamHopTac, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 80, 30));
        cboTimKiemNamHopTac = new javax.swing.JComboBox<>();
        pnlBoxTimKiem.add(cboTimKiemNamHopTac, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 20, 230, 30));

        lblTimKiemTrangThai = new javax.swing.JLabel("Trạng thái");
        lblTimKiemTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14));
        pnlBoxTimKiem.add(lblTimKiemTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 60, 90, 30));
        cboTimKiemTrangThai = new javax.swing.JComboBox<>();
        pnlBoxTimKiem.add(cboTimKiemTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, 230, 30));

        btnTimKiemAction = new javax.swing.JButton("Tìm kiếm");
        btnTimKiemAction.setPreferredSize(new java.awt.Dimension(120, 30));
        pnlBoxTimKiem.add(btnTimKiemAction, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 20, 120, 30));

        btnResetTimKiem = new javax.swing.JButton("Làm mới");
        btnResetTimKiem.setPreferredSize(new java.awt.Dimension(120, 30));
        pnlBoxTimKiem.add(btnResetTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 120, 30));
        
        pnlContent.add(pnlBoxTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 960, 110));

        // Edit Panel (pnlBoxChinhSua)
        pnlBoxChinhSua = new javax.swing.JPanel();
        pnlBoxChinhSua.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 23));
        Dimension editButtonSize = new java.awt.Dimension(150, 34);

        btnThem = new javax.swing.JButton("Thêm");
        btnThem.setPreferredSize(editButtonSize);
        pnlBoxChinhSua.add(btnThem);

        btnSua = new javax.swing.JButton("Sửa");
        btnSua.setPreferredSize(editButtonSize);
        pnlBoxChinhSua.add(btnSua);

        btnXoa = new javax.swing.JButton("Xóa");
        btnXoa.setPreferredSize(editButtonSize);
        pnlBoxChinhSua.add(btnXoa);

        btnTrangThaiNCC = new javax.swing.JButton("Trạng Thái"); 
        btnTrangThaiNCC.setPreferredSize(editButtonSize);
        pnlBoxChinhSua.add(btnTrangThaiNCC);

        pnlContent.add(pnlBoxChinhSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 960, 80));

        // Table Panel (pnlBoxTable)
        pnlBoxTable = new javax.swing.JPanel();
        pnlBoxTable.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        scTable = new javax.swing.JScrollPane();
        nhaCungCapTable = new javax.swing.JTable();

        tableModel = new DefaultTableModel(
            new Object [][] {},
            new String [] {"STT", "Mã NCC", "Tên NCC", "Loại SP", "Năm hợp tác", "Địa chỉ", "Email", "SĐT", "Trạng thái", "Chi tiết"}
        ) {
            boolean[] canEdit = new boolean [] {false, false, false, false, false, false, false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        nhaCungCapTable.setModel(tableModel);
        nhaCungCapTable.setShowGrid(true);
        scTable.setViewportView(nhaCungCapTable);
        pnlBoxTable.add(scTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 940, 290));
        pnlContent.add(pnlBoxTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 960, 330));

        // Bottom Buttons Panel
        pnlBottomButtons = new javax.swing.JPanel();
        pnlBottomButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        Dimension bottomButtonSize = new Dimension(170, 40);

        btnImport = new javax.swing.JButton("Nhập dữ liệu");
        btnImport.setPreferredSize(bottomButtonSize);
        pnlBottomButtons.add(btnImport);
        
        btnExport = new javax.swing.JButton("Xuất dữ liệu");
        btnExport.setPreferredSize(bottomButtonSize);
        pnlBottomButtons.add(btnExport);

        btnPrinter = new javax.swing.JButton("In ấn");
        btnPrinter.setPreferredSize(bottomButtonSize);
        pnlBottomButtons.add(btnPrinter);
        
        pnlContent.add(pnlBottomButtons, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 560, 960, 60));

        add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1000, 630));
    }

    private void setupSearchComponents() {
        cboTimKiemTieuChi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
            "Tất cả", "Mã NCC", "Tên NCC", "Email", "Số điện thoại"
        }));

        // Populate Năm hợp tác combobox (e.g., last 20 years + "Tất cả")
        ArrayList<String> namOptions = new ArrayList<>();
        namOptions.add("Tất cả");
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 20; i++) {
            namOptions.add(String.valueOf(currentYear - i));
        }
        // You might want to add years from existing data too if they are older
        // For simplicity, just using recent years for now.
        cboTimKiemNamHopTac.setModel(new javax.swing.DefaultComboBoxModel<>(namOptions.toArray(new String[0])));

        cboTimKiemTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
            "Tất cả", "Đang hợp tác", "Ngừng hợp tác"
        }));
    }

    private void setupActionListeners() {
        btnTimKiemAction.addActionListener(evt -> searchNhaCungCap());
        btnResetTimKiem.addActionListener(evt -> resetSearchFields());
        btnThem.addActionListener(evt -> showThemNhaCungCapDialog());
        btnSua.addActionListener(evt -> showSuaNhaCungCapDialog());
        btnXoa.addActionListener(evt -> xoaNhaCungCap());
        btnTrangThaiNCC.addActionListener(evt -> thayDoiTrangThaiNhaCungCap());

        btnImport.addActionListener(evt -> {
            utils.FileUtils.importFromExcelForNhaCungCap(nhaCungCapTable);
            loadData();
        });
        btnExport.addActionListener(evt -> {
             if (nhaCungCapTable.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            utils.FileUtils.showExportOptionsForNhaCungCap(nhaCungCapTable, "DanhSachNhaCungCap");
        });
        btnPrinter.addActionListener(evt -> printNhaCungCapData());

        // Key listeners for search fields
        txtTimKiemKeyword.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_ENTER) searchNhaCungCap(); }
        });
        cboTimKiemTieuChi.addKeyListener(new KeyAdapter() {
             @Override public void keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_ENTER) searchNhaCungCap(); }
        });
        cboTimKiemNamHopTac.addKeyListener(new KeyAdapter() {
             @Override public void keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_ENTER) searchNhaCungCap(); }
        });
        cboTimKiemTrangThai.addKeyListener(new KeyAdapter() {
             @Override public void keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_ENTER) searchNhaCungCap(); }
        });
    }
    
    public javax.swing.JPanel getNhaCungCapPanel() { // Keep this if needed by parent frame
        return this;
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<nhaCungCapDTO> nccList = nhaCungCapBUS.getAllNhaCungCap();
        int stt = 1;
        for (nhaCungCapDTO ncc : nccList) {
            tableModel.addRow(new Object[] {
                    stt++, 
                    ncc.getMaNhaCungCap(),
                    ncc.getTenNhaCungCap(),
                    ncc.getLoaiSP(),
                    ncc.getNamHopTac() > 0 ? ncc.getNamHopTac() : "Chưa cập nhật",
                    ncc.getDiaChi(),
                    ncc.getEmail(),
                    ncc.getSoDienThoai(),
                    ncc.getTrangThai(),
                    "Chi tiết"
            });
        }
        // Update status button based on selection after loading data (if any row is pre-selected)
        updateTrangThaiButtonState(); 
    }

    private void searchNhaCungCap() {
        String keyword = txtTimKiemKeyword.getText().trim();
        String searchType = (String) cboTimKiemTieuChi.getSelectedItem();
        String namHopTacFilter = (String) cboTimKiemNamHopTac.getSelectedItem();
        String trangThaiFilter = (String) cboTimKiemTrangThai.getSelectedItem();

        tableModel.setRowCount(0);
        List<nhaCungCapDTO> results = nhaCungCapBUS.searchNhaCungCapAdvanced(keyword, searchType, namHopTacFilter, trangThaiFilter);
        int stt = 1;
        for (nhaCungCapDTO ncc : results) {
            tableModel.addRow(new Object[] {
                    stt++, 
                    ncc.getMaNhaCungCap(),
                    ncc.getTenNhaCungCap(),
                    ncc.getLoaiSP(),
                    ncc.getNamHopTac() > 0 ? ncc.getNamHopTac() : "Chưa cập nhật",
                    ncc.getDiaChi(),
                    ncc.getEmail(),
                    ncc.getSoDienThoai(),
                    ncc.getTrangThai(),
                    "Chi tiết"
            });
        }
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhà cung cấp nào khớp với tiêu chí tìm kiếm.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        updateTrangThaiButtonState();
    }
    
    private void resetSearchFields() {
        txtTimKiemKeyword.setText("");
        cboTimKiemTieuChi.setSelectedIndex(0);
        cboTimKiemNamHopTac.setSelectedIndex(0);
        cboTimKiemTrangThai.setSelectedIndex(0);
        loadData(); 
    }

    private void showThemNhaCungCapDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm nhà cung cấp", true);
        dialog.setLayout(new BorderLayout());
        themNhaCungCapPanel panel = new themNhaCungCapPanel();
        dialog.add(panel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        loadData();
    }

    private void showSuaNhaCungCapDialog() {
        int selectedRow = nhaCungCapTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String maNCC = (String) nhaCungCapTable.getValueAt(selectedRow, 1); // Column 1 is MaNCC
        nhaCungCapDTO ncc = nhaCungCapBUS.getNhaCungCapByMa(maNCC);
        if (ncc == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhà cung cấp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sửa nhà cung cấp", true);
        dialog.setLayout(new BorderLayout());
        suaNhaCungCapPanel panel = new suaNhaCungCapPanel(dialog, ncc);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        loadData();
    }

    private void xoaNhaCungCap() {
        int selectedRow = nhaCungCapTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String maNCC = (String) nhaCungCapTable.getValueAt(selectedRow, 1);
        String tenNCC = (String) nhaCungCapTable.getValueAt(selectedRow, 2);

        int initialChoice = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa nhà cung cấp " + tenNCC + " (Mã: " + maNCC + ")?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (initialChoice == JOptionPane.NO_OPTION) return;

        try {
            SanPhamBUS spBUS = new SanPhamBUS();
            List<sanPhamDTO> primarySuppliedProducts = spBUS.getSanPhamByMaNhaCungCap(maNCC);
            NhaCungCap_SanPhamBUS nccspBUS = new NhaCungCap_SanPhamBUS();
            List<NhaCungCap_SanPhamDTO> manyToManyLinks = nccspBUS.getSanPhamByNhaCungCap(maNCC);
            PhieuNhapBUS pnBUS = new PhieuNhapBUS();
            List<PhieuNhapDTO> phieuNhapLinks = pnBUS.searchBySupplier(maNCC);

            if (phieuNhapLinks != null && !phieuNhapLinks.isEmpty()) {
                String[] pnOptions = { "Cập nhật Phiếu Nhập sang NCC khác", "Hủy" };
                int pnOption = JOptionPane.showOptionDialog(this,
                        "Nhà cung cấp " + tenNCC + " có " + phieuNhapLinks.size() + " phiếu nhập liên quan.\n" +
                                "Bạn PHẢI chuyển các phiếu nhập này sang một nhà cung cấp khác trước khi xóa.",
                        "Xử lý Phiếu Nhập liên quan", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, pnOptions, pnOptions[1]);
                if (pnOption == 0) {
                    List<nhaCungCapDTO> otherSuppliers = nhaCungCapBUS.getAllNhaCungCap();
                    otherSuppliers.removeIf(nccDto -> nccDto.getMaNhaCungCap().equals(maNCC));
                    if (otherSuppliers.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không có nhà cung cấp khác để chuyển phiếu nhập sang!", "Không thể cập nhật", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    JComboBox<String> supplierCombo = new JComboBox<>();
                    for (nhaCungCapDTO nccDto : otherSuppliers) supplierCombo.addItem(nccDto.getMaNhaCungCap() + " - " + nccDto.getTenNhaCungCap());
                    int result = JOptionPane.showConfirmDialog(this, supplierCombo, "Chọn nhà cung cấp mới cho các phiếu nhập", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        String selectedPnSupplierItem = (String) supplierCombo.getSelectedItem();
                        String newPnMaNCC = selectedPnSupplierItem.split(" - ")[0];
                        if (pnBUS.updateMaNhaCungCapForPhieuNhap(maNCC, newPnMaNCC)) {
                            JOptionPane.showMessageDialog(this, "Đã cập nhật các phiếu nhập sang nhà cung cấp " + newPnMaNCC + ". Giờ bạn có thể thử xóa lại.");
                            phieuNhapLinks.clear();
                        } else {
                            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật phiếu nhập sang nhà cung cấp mới.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                        }
                    } else return;
                } else return;
            }

            if (primarySuppliedProducts != null && !primarySuppliedProducts.isEmpty()) {
                String[] spOptions = { "Cập nhật sản phẩm sang NCC khác", "Hủy" };
                int spOption = JOptionPane.showOptionDialog(this,
                        "Nhà cung cấp " + tenNCC + " là nhà cung cấp chính cho " + primarySuppliedProducts.size() + " sản phẩm.\n" +
                                "Bạn PHẢI chuyển các sản phẩm này sang một nhà cung cấp khác trước khi xóa.",
                        "Xử lý sản phẩm phụ thuộc (NCC Chính)", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, spOptions, spOptions[1]);
                if (spOption == 0) {
                    List<nhaCungCapDTO> otherSuppliers = nhaCungCapBUS.getAllNhaCungCap();
                    otherSuppliers.removeIf(nccDto -> nccDto.getMaNhaCungCap().equals(maNCC));
                    if (otherSuppliers.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không có nhà cung cấp khác để chuyển sản phẩm sang!", "Không thể cập nhật", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    JComboBox<String> supplierCombo = new JComboBox<>();
                    for (nhaCungCapDTO nccDto : otherSuppliers) supplierCombo.addItem(nccDto.getMaNhaCungCap() + " - " + nccDto.getTenNhaCungCap());
                    int result = JOptionPane.showConfirmDialog(this, supplierCombo, "Chọn nhà cung cấp mới cho các sản phẩm phụ thuộc", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        String selectedSpSupplierItem = (String) supplierCombo.getSelectedItem();
                        String newSpMaNCC = selectedSpSupplierItem.split(" - ")[0];
                        boolean allProductsUpdated = true;
                        for (sanPhamDTO sp : primarySuppliedProducts) {
                            String currentProductMaSP = sp.getMaSanPham();
                            sp.setMaNhaCungCap(newSpMaNCC);
                            if (!spBUS.updateSanPham(sp)) {
                                allProductsUpdated = false;
                                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật sản phẩm " + currentProductMaSP + " sang NCC mới.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                            if (nccspBUS.getNhaCungCapBySanPham(currentProductMaSP).stream().anyMatch(link -> link.getMaNhaCungCap().equals(maNCC))) {
                                nccspBUS.xoaNhaCungCap_SanPham(maNCC, currentProductMaSP);
                            }
                        }
                        if (!allProductsUpdated) return;
                        primarySuppliedProducts.clear();
                    } else return;
                } else return;
            }
            
            // Delete NhaCungCap_SanPham links if not handled by product updates
            manyToManyLinks = nccspBUS.getSanPhamByNhaCungCap(maNCC); // Re-fetch
            if (manyToManyLinks != null && !manyToManyLinks.isEmpty()) {
                int confirmLinks = JOptionPane.showConfirmDialog(this, 
                    "Nhà cung cấp " + tenNCC + " có " + manyToManyLinks.size() + " liên kết cung cấp sản phẩm (không phải NCC chính).\n"+
                    "Xóa các liên kết này và tiếp tục xóa nhà cung cấp?", 
                    "Xác nhận xóa liên kết sản phẩm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirmLinks == JOptionPane.YES_OPTION) {
                    for (NhaCungCap_SanPhamDTO link : manyToManyLinks) {
                        nccspBUS.xoaNhaCungCap_SanPham(maNCC, link.getMaSanPham());
                    }
                    } else {
                    return; // User chose not to delete links, so stop deletion of NCC
                }
            }

            if (nhaCungCapBUS.xoaNhaCungCap(maNCC)) {
                JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp " + tenNCC + " thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp " + tenNCC + " thất bại!", "Lỗi Xóa", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra trong quá trình xóa nhà cung cấp: " + e.getMessage(), "Lỗi Hệ Thống", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void thayDoiTrangThaiNhaCungCap() {
        int selectedRow = nhaCungCapTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp để thay đổi trạng thái!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String maNCC = (String) nhaCungCapTable.getValueAt(selectedRow, 1); // Column 1 is MaNCC
        String currentTrangThai = (String) nhaCungCapTable.getValueAt(selectedRow, 8); // Column 8 is TrangThai

        if (nhaCungCapBUS.capNhatTrangThaiNhaCungCap(maNCC)) {
            JOptionPane.showMessageDialog(this, "Cập nhật trạng thái nhà cung cấp thành công!");
            loadData(); // Reload to reflect changes and update button text/icon
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật trạng thái nhà cung cấp thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTrangThaiButtonState() {
        int selectedRow = nhaCungCapTable.getSelectedRow();
        if (selectedRow >= 0 && btnTrangThaiNCC != null) {
            String trangThai = getTableValueAsString(selectedRow, 8); // Column 8 is TrangThai
            String buttonText = "Trạng Thái";
            String iconPath = "";

            if ("Đang hợp tác".equalsIgnoreCase(trangThai)) {
                buttonText = "Ngừng HT"; 
                iconPath = "/icon_img/lock_icon.png"; 
            } else if ("Ngừng hợp tác".equalsIgnoreCase(trangThai)) {
                buttonText = "Hợp tác";
                iconPath = "/icon_img/unlock_icon.png"; 
            } else {
                buttonText = "Trạng Thái"; // Default for any other status
                iconPath = null; // No icon for undefined or other statuses
            }
            btnTrangThaiNCC.setText(buttonText);
            if (iconPath != null && !iconPath.isEmpty()) {
                setButtonIcon(btnTrangThaiNCC, iconPath);
            } else {
                btnTrangThaiNCC.setIcon(null);
            }
        } else if (btnTrangThaiNCC != null) {
            // No row selected or button not initialized
            btnTrangThaiNCC.setText("Trạng Thái");
            btnTrangThaiNCC.setIcon(null); // No default icon
        }
    }

    private void setupTable() {
        nhaCungCapTable.getColumnModel().getColumn(0).setPreferredWidth(40);  // STT
        nhaCungCapTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // Mã NCC
        nhaCungCapTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Tên NCC
        nhaCungCapTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Loại SP
        nhaCungCapTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Năm hợp tác
        nhaCungCapTable.getColumnModel().getColumn(5).setPreferredWidth(180); // Địa chỉ
        nhaCungCapTable.getColumnModel().getColumn(6).setPreferredWidth(150); // Email
        nhaCungCapTable.getColumnModel().getColumn(7).setPreferredWidth(90);  // SĐT
        nhaCungCapTable.getColumnModel().getColumn(8).setPreferredWidth(90);  // Trạng thái
        nhaCungCapTable.getColumnModel().getColumn(9).setPreferredWidth(70);  // Chi tiết

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        nhaCungCapTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // STT
        nhaCungCapTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Mã NCC
        nhaCungCapTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Năm hợp tác
        nhaCungCapTable.getColumnModel().getColumn(7).setCellRenderer(centerRenderer); // SĐT
        nhaCungCapTable.getColumnModel().getColumn(8).setCellRenderer(centerRenderer); // Trạng thái

        nhaCungCapTable.getColumnModel().getColumn(9).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setForeground(table.getSelectionForeground());
                    c.setBackground(table.getSelectionBackground());
                } else {
                    c.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR); // Blue color for link
                    c.setBackground(table.getBackground());
                }
                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });

        nhaCungCapTable.setRowHeight(25);
        nhaCungCapTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        nhaCungCapTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = nhaCungCapTable.rowAtPoint(e.getPoint());
                int col = nhaCungCapTable.columnAtPoint(e.getPoint());
                if (row >= 0 && col == 9) { // Column 9 is "Chi tiết"
                    showChiTietNhaCungCap(row);
                }
            }
        });

        nhaCungCapTable.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int col = nhaCungCapTable.columnAtPoint(e.getPoint());
                if (col == 9) {
                    nhaCungCapTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    nhaCungCapTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
        
        // Listener to update status button when selection changes
        nhaCungCapTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateTrangThaiButtonState();
            }
        });
    }

    private void showChiTietNhaCungCap(int row) {
        try {
            String maNCC = getTableValueAsString(row, 1);
            nhaCungCapDTO ncc = nhaCungCapBUS.getNhaCungCapByMa(maNCC);
            if (ncc == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhà cung cấp đã chọn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
            chiTietNhaCungCapDialog dialog = new chiTietNhaCungCapDialog(parentFrame, ncc);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi hiển thị thông tin chi tiết: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getTableValueAsString(int row, int column) {
        Object value = nhaCungCapTable.getValueAt(row, column);
        return value != null ? value.toString() : "";
    }

    private void printNhaCungCapData() {
        try {
            List<nhaCungCapDTO> nccList = nhaCungCapBUS.getAllNhaCungCap();
            if (nccList == null || nccList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu nhà cung cấp để in.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><head><style>");
            htmlContent.append("body { font-family: Arial, sans-serif; margin: 20px; }");
            htmlContent.append("h1 { text-align: center; color: #333; }");
            htmlContent.append(".supplier-record { border: 1px solid #ccc; padding: 10px; margin-bottom: 15px; border-radius: 5px; page-break-inside: avoid; }");
            htmlContent.append(".field-label { font-weight: bold; color: #555; }");
            htmlContent.append("p { margin: 5px 0; }");
            htmlContent.append("</style></head><body>");
            htmlContent.append("<h1>Danh Sách Chi Tiết Nhà Cung Cấp</h1>");

            for (nhaCungCapDTO ncc : nccList) {
                htmlContent.append("<div class='supplier-record'>");
                htmlContent.append("<p><span class='field-label'>Mã NCC:</span> ").append(ncc.getMaNhaCungCap() != null ? ncc.getMaNhaCungCap() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Tên NCC:</span> ").append(ncc.getTenNhaCungCap() != null ? ncc.getTenNhaCungCap() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Loại Sản Phẩm:</span> ").append(ncc.getLoaiSP() != null ? ncc.getLoaiSP() : "").append("</p>");
                String namHopTacDisplay = (ncc.getNamHopTac() > 0) ? String.valueOf(ncc.getNamHopTac()) : "Chưa cập nhật";
                htmlContent.append("<p><span class='field-label'>Năm Hợp Tác:</span> ").append(namHopTacDisplay).append("</p>");
                htmlContent.append("<p><span class='field-label'>Địa Chỉ:</span> ").append(ncc.getDiaChi() != null ? ncc.getDiaChi() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Email:</span> ").append(ncc.getEmail() != null ? ncc.getEmail() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Số Điện Thoại:</span> ").append(ncc.getSoDienThoai() != null ? ncc.getSoDienThoai() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Trạng Thái:</span> ").append(ncc.getTrangThai() != null ? ncc.getTrangThai() : "").append("</p>");
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
            JOptionPane.showMessageDialog(this, "Lỗi khi in: Không tìm thấy máy in hoặc lỗi máy in.\n" + pe.getMessage(), "Lỗi In Ấn", JOptionPane.ERROR_MESSAGE);
            pe.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi chuẩn bị dữ liệu để in: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
