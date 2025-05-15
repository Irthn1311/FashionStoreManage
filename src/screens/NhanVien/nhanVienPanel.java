package screens.NhanVien;

import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import DAO.NhanVienDAO;
import DTO.nhanVienDTO;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Cursor;
import java.util.ArrayList;
import BUS.TaiKhoanBUS;
import DTO.taiKhoanDTO;
import java.awt.Dimension;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import BUS.PhanQuyenBUS;
import java.util.EnumSet;
import BUS.NhanVienBUS;
import DTO.VaiTro;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import utils.FileUtils;
import javax.swing.JEditorPane;
import screens.TrangChu.AppColors;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import java.net.URL;

/**
 *
 * @author nson9
 */
public class nhanVienPanel extends javax.swing.JPanel {
    private NhanVienDAO nhanVienDAO;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timestampFormat;
    private DecimalFormat decimalFormat;
    private DefaultTableModel tableModel;
    private TaiKhoanBUS taiKhoanBUS;
    private taiKhoanDTO taiKhoan;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnPrinter;
    private NhanVienBUS nhanVienBUS;

    private javax.swing.JPanel pnlHeader;
    private javax.swing.JLabel lblHeaderTitle;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlBoxTimKiem;
    private javax.swing.JLabel lblTimKiemTieuChi;
    private javax.swing.JComboBox<String> cboTimKiemTieuChi;
    private javax.swing.JTextField txtTimKiemKeyword;
    private javax.swing.JLabel lblTimKiemVaiTro;
    private javax.swing.JComboBox<String> cboTimKiemVaiTro;
    private javax.swing.JLabel lblTimKiemTrangThai;
    private javax.swing.JComboBox<String> cboTimKiemTrangThai;
    private javax.swing.JButton btnTimKiemAction;
    private javax.swing.JButton btnResetTimKiem;
    private javax.swing.JPanel pnlBoxChinhSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnTrangThai;
    private javax.swing.JPanel pnlTableContainer;
    private javax.swing.JScrollPane jScrollPaneNhanVienTable;
    private javax.swing.JTable nhanVienTable;
    private javax.swing.JPanel pnlBottomButtons;
    private javax.swing.JButton btnExport;

    /**
     * Creates new form nhanvien
     */
    public nhanVienPanel(taiKhoanDTO taiKhoan) {
        this.taiKhoan = taiKhoan;
        nhanVienBUS = new NhanVienBUS();
        initComponents();
        setupIcons();
        applyColors();

        nhanVienDAO = new NhanVienDAO();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        timestampFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        decimalFormat = new DecimalFormat("#,###.##");
        
        cboTimKiemTieuChi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
            "Tất cả", "Mã nhân viên", "Tên nhân viên", "Email", "Số điện thoại"
        }));
        
        ArrayList<String> vaiTroOptions = new ArrayList<>();
        vaiTroOptions.add("Tất cả");
        for (VaiTro vt : VaiTro.values()) {
            vaiTroOptions.add(vt.getDisplayName());
        }
        cboTimKiemVaiTro.setModel(new javax.swing.DefaultComboBoxModel<>(vaiTroOptions.toArray(new String[0])));
        
        cboTimKiemTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
            "Tất cả", "Hoạt động", "Không hoạt động", "Đang xét duyệt"
        }));
        
        loadNhanVienData();
        
        btnTimKiemAction.addActionListener(evt -> searchNhanVien());
        btnResetTimKiem.addActionListener(evt -> resetSearchFields());
        btnThem.addActionListener(evt -> themNhanVien());
        btnSua.addActionListener(evt -> suaNhanVien());
        btnXoa.addActionListener(evt -> xoaNhanVien());
        btnTrangThai.addActionListener(evt -> thayDoiTrangThai());
        btnImport.addActionListener(evt -> {
            utils.FileUtils.importFromExcelForNhanVien(nhanVienTable);
            loadNhanVienData();
        });
        btnExport.addActionListener(e -> {
            if (nhanVienTable.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Không có dữ liệu để xuất!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            utils.FileUtils.showExportOptionsForNhanVien(nhanVienTable, "DanhSachNhanVien");
        });
        btnPrinter.addActionListener(evt -> printNhanVienData());

        txtTimKiemKeyword.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_ENTER) searchNhanVien(); }
        });
        cboTimKiemTieuChi.addKeyListener(new KeyAdapter() {
             @Override public void keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_ENTER) searchNhanVien(); }
        });
        cboTimKiemVaiTro.addKeyListener(new KeyAdapter() {
             @Override public void keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_ENTER) searchNhanVien(); }
        });
        cboTimKiemTrangThai.addKeyListener(new KeyAdapter() {
             @Override public void keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_ENTER) searchNhanVien(); }
        });
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
        if (lblTimKiemVaiTro != null) lblTimKiemVaiTro.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
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
        if (btnTrangThai != null) { btnTrangThai.setBackground(editButtonBg); btnTrangThai.setForeground(editButtonFg); }

        if (pnlTableContainer != null) {
            pnlTableContainer.setBackground(AppColors.NEW_MAIN_BG_COLOR);
            javax.swing.border.TitledBorder tableBorder = javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Bảng thông tin");
            tableBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
            pnlTableContainer.setBorder(tableBorder);
        }
        if (nhanVienTable != null) {
            nhanVienTable.setBackground(Color.WHITE);
            nhanVienTable.getTableHeader().setBackground(AppColors.NEW_HEADER_PANEL_BG_COLOR);
            nhanVienTable.getTableHeader().setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
            nhanVienTable.setGridColor(AppColors.NEW_BORDER_LINES_COLOR);
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
            setButtonIcon(btnTrangThai, "/icon_img/duyet_icon.png");
            setButtonIcon(btnImport, "/icon_img/import_icon.png");
            setButtonIcon(btnExport, "/icon_img/export_icon.png");
            setButtonIcon(btnPrinter, "/icon_img/print_icon.png");

            javax.swing.JButton[] buttonsWithIcons = {
                btnTimKiemAction, btnResetTimKiem, btnThem, btnSua, btnXoa, btnTrangThai,
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

    public javax.swing.JPanel getNhanVienPanel() {
        return this;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        setPreferredSize(new java.awt.Dimension(1000, 700));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader = new javax.swing.JPanel();
        lblHeaderTitle = new javax.swing.JLabel();

        pnlHeader.setBackground(AppColors.NEW_HEADER_PANEL_BG_COLOR);
        lblHeaderTitle.setFont(new java.awt.Font("Segoe UI", 1, 24));
        lblHeaderTitle.setText("QUẢN LÝ NHÂN VIÊN");
        lblHeaderTitle.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap(388, Short.MAX_VALUE)
                .addComponent(lblHeaderTitle)
                .addContainerGap(388, Short.MAX_VALUE))
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
        pnlContent.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        pnlContent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlBoxTimKiem = new javax.swing.JPanel();
        lblTimKiemTieuChi = new javax.swing.JLabel();
        cboTimKiemTieuChi = new javax.swing.JComboBox<>();
        txtTimKiemKeyword = new javax.swing.JTextField();
        lblTimKiemVaiTro = new javax.swing.JLabel();
        cboTimKiemVaiTro = new javax.swing.JComboBox<>();
        lblTimKiemTrangThai = new javax.swing.JLabel();
        cboTimKiemTrangThai = new javax.swing.JComboBox<>();
        btnTimKiemAction = new javax.swing.JButton();
        btnResetTimKiem = new javax.swing.JButton();

        pnlBoxTimKiem.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTimKiemTieuChi.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblTimKiemTieuChi.setText("Tìm theo");
        pnlBoxTimKiem.add(lblTimKiemTieuChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 30));
        pnlBoxTimKiem.add(cboTimKiemTieuChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 180, 30));
        pnlBoxTimKiem.add(txtTimKiemKeyword, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, 180, 30));
        
        lblTimKiemVaiTro.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblTimKiemVaiTro.setText("Vai trò");
        pnlBoxTimKiem.add(lblTimKiemVaiTro, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 80, 30));
        pnlBoxTimKiem.add(cboTimKiemVaiTro, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 20, 230, 30));

        lblTimKiemTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblTimKiemTrangThai.setText("Trạng thái");
        pnlBoxTimKiem.add(lblTimKiemTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 60, 90, 30));
        pnlBoxTimKiem.add(cboTimKiemTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, 230, 30));

        btnTimKiemAction.setText("Tìm kiếm");
        btnTimKiemAction.setPreferredSize(new java.awt.Dimension(120, 30));
        pnlBoxTimKiem.add(btnTimKiemAction, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 20, 120, 30));

        btnResetTimKiem.setText("Làm mới");
        btnResetTimKiem.setPreferredSize(new java.awt.Dimension(120, 30));
        pnlBoxTimKiem.add(btnResetTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 120, 30));
        
        pnlContent.add(pnlBoxTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 960, 110));

        pnlBoxChinhSua = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnTrangThai = new javax.swing.JButton();

        pnlBoxChinhSua.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 23));

        Dimension editButtonSize = new java.awt.Dimension(150, 34);
        btnThem.setText("Thêm");
        btnThem.setPreferredSize(editButtonSize);
        pnlBoxChinhSua.add(btnThem);

        btnSua.setText("Sửa");
        btnSua.setPreferredSize(editButtonSize);
        pnlBoxChinhSua.add(btnSua);

        btnXoa.setText("Xóa");
        btnXoa.setPreferredSize(editButtonSize);
        pnlBoxChinhSua.add(btnXoa);

        btnTrangThai.setText("Trạng Thái");
        btnTrangThai.setPreferredSize(editButtonSize);
        pnlBoxChinhSua.add(btnTrangThai);

        pnlContent.add(pnlBoxChinhSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 960, 80));

        pnlTableContainer = new javax.swing.JPanel();
        jScrollPaneNhanVienTable = new javax.swing.JScrollPane();
        nhanVienTable = new javax.swing.JTable();

        pnlTableContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableModel.addColumn("Mã nhân viên");
        tableModel.addColumn("Họ tên");
        tableModel.addColumn("Số điện thoại");
        tableModel.addColumn("Email");
        tableModel.addColumn("Địa chỉ");
        tableModel.addColumn("Chức vụ");
        tableModel.addColumn("Tên đăng nhập");
        tableModel.addColumn("Trạng thái tài khoản");
        tableModel.addColumn("Chi tiết");
        
        nhanVienTable.setModel(tableModel);
        nhanVienTable.setShowGrid(true);
        jScrollPaneNhanVienTable.setViewportView(nhanVienTable);
        pnlTableContainer.add(jScrollPaneNhanVienTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 940, 290));

        pnlContent.add(pnlTableContainer, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 960, 330));

        pnlBottomButtons = new javax.swing.JPanel();
        btnImport = new javax.swing.JButton("Nhập dữ liệu");
        btnExport = new javax.swing.JButton("Xuất dữ liệu");
        btnPrinter = new javax.swing.JButton("In ấn");

        pnlBottomButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));

        Dimension bottomButtonSize = new Dimension(170, 40);
        btnImport.setPreferredSize(bottomButtonSize);
        pnlBottomButtons.add(btnImport);
        
        btnExport.setPreferredSize(bottomButtonSize);
        pnlBottomButtons.add(btnExport);

        btnPrinter.setPreferredSize(bottomButtonSize);
        pnlBottomButtons.add(btnPrinter);

        pnlContent.add(pnlBottomButtons, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 560, 960, 60));

        add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1000, 630));
    }

    private void loadNhanVienData() {
        List<nhanVienDTO> nhanVienList = nhanVienDAO.getAllNhanVien();
        tableModel.setRowCount(0);
        
        for (nhanVienDTO nv : nhanVienList) {
            taiKhoanDTO taiKhoan = nhanVienDAO.getTaiKhoanByMaNhanVien(nv.getMaNhanVien());
            String tenDangNhap = taiKhoan != null ? taiKhoan.getTenDangNhap() : "Chưa có";
            String trangThai = "Chưa có";
            if (taiKhoan != null) {
                switch (taiKhoan.getTrangThai()) {
                    case 1: trangThai = "Hoạt động"; break;
                    case 0: trangThai = "Không hoạt động"; break;
                    case -1: trangThai = "Đang xét duyệt"; break;
                    default: trangThai = "Không xác định"; break;
                }
            }
            
            tableModel.addRow(new Object[]{
                nv.getMaNhanVien(),
                nv.getHoTen(),
                nv.getSoDienThoai(),
                nv.getEmail(),
                nv.getDiaChi(),
                nv.getChucVu(),
                tenDangNhap,
                trangThai,
                "Xem chi tiết"
            });
        }
        
        setupTable();
    }
    
    private void setupTable() {
        nhanVienTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        nhanVienTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        nhanVienTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        nhanVienTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        nhanVienTable.getColumnModel().getColumn(4).setPreferredWidth(180);
        nhanVienTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        nhanVienTable.getColumnModel().getColumn(6).setPreferredWidth(120);
        nhanVienTable.getColumnModel().getColumn(7).setPreferredWidth(120);
        nhanVienTable.getColumnModel().getColumn(8).setPreferredWidth(80);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        nhanVienTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        nhanVienTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        nhanVienTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        nhanVienTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        nhanVienTable.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);

        nhanVienTable.getColumnModel().getColumn(8).setCellRenderer(new DefaultTableCellRenderer() {
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
                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });

        nhanVienTable.setRowHeight(25);

        nhanVienTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = nhanVienTable.rowAtPoint(evt.getPoint());
                int col = nhanVienTable.columnAtPoint(evt.getPoint());
                
                if (row >= 0 && col == 8) {
                    String maNV = nhanVienTable.getValueAt(row, 0).toString();
                    xemChiTietNhanVien(maNV);
                }
            }
        });

        nhanVienTable.addMouseMotionListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                int col = nhanVienTable.columnAtPoint(evt.getPoint());
                if (col == 8) {
                    nhanVienTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    nhanVienTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });

        nhanVienTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = nhanVienTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String trangThai = nhanVienTable.getValueAt(selectedRow, 7).toString();
                    String buttonText = "Trạng thái";
                    String iconPath = "";

                    if (trangThai.equals("Hoạt động")) {
                        buttonText = "Khóa";
                        iconPath = "/icon_img/lock_icon.png";
                    } else if (trangThai.equals("Không hoạt động")) {
                        buttonText = "Mở Khóa";
                        iconPath = "/icon_img/unlock_icon.png";
                    } else if (trangThai.equals("Đang xét duyệt")) {
                        buttonText = "Duyệt";
                        iconPath = "/icon_img/duyet_icon.png";
                    }
                    btnTrangThai.setText(buttonText);
                    if (!iconPath.isEmpty()) {
                        setButtonIcon(btnTrangThai, iconPath);
                    } else {
                        btnTrangThai.setIcon(null); // Clear icon if not applicable
                    }
                }
            }
        });
    }
    
    private void searchNhanVien() {
        String keyword = txtTimKiemKeyword.getText().trim();
        String tieuChi = cboTimKiemTieuChi.getSelectedItem().toString();
        String vaiTro = cboTimKiemVaiTro.getSelectedItem().toString();
        String trangThaiTK = cboTimKiemTrangThai.getSelectedItem().toString();

        if ("Tất cả".equals(tieuChi)) tieuChi = "";
        if ("Tất cả".equals(vaiTro)) vaiTro = "";
        if ("Tất cả".equals(trangThaiTK)) trangThaiTK = "";

        List<nhanVienDTO> searchResults = nhanVienBUS.searchNhanVienAdvanced(keyword, tieuChi, vaiTro, trangThaiTK);

        tableModel.setRowCount(0);
            
        for (nhanVienDTO nv : searchResults) {
            taiKhoanDTO taiKhoan = nhanVienDAO.getTaiKhoanByMaNhanVien(nv.getMaNhanVien());
            String tenDangNhap = taiKhoan != null ? taiKhoan.getTenDangNhap() : "Chưa có";
            String trangThaiDisplay = "Chưa có";
            if (taiKhoan != null) {
                switch (taiKhoan.getTrangThai()) {
                    case 1: trangThaiDisplay = "Hoạt động"; break;
                    case 0: trangThaiDisplay = "Không hoạt động"; break;
                    case -1: trangThaiDisplay = "Đang xét duyệt"; break;
                    default: trangThaiDisplay = "Không xác định"; break;
                }
            }
            
            tableModel.addRow(new Object[]{
                nv.getMaNhanVien(),
                nv.getHoTen(),
                nv.getSoDienThoai(),
                nv.getEmail(),
                nv.getDiaChi(),
                nv.getChucVu(),
                tenDangNhap,
                trangThaiDisplay,
                "Xem chi tiết"
            });
        }
    }

    private void themNhanVien() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Thêm Nhân Viên", true);
        themNhanVienPanel panel = new themNhanVienPanel(this.taiKhoan.getVaiTro());
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        loadNhanVienData();
    }

    private void suaNhanVien() {
        int selectedRow = nhanVienTable.getSelectedRow();
        if (selectedRow >= 0) {
            String maNV = nhanVienTable.getValueAt(selectedRow, 0).toString();
            try {
                nhanVienDTO nhanVien = nhanVienDAO.getNhanVienByMa(maNV);
                if (nhanVien != null) {
                    JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Sửa Nhân Viên", true);
                    suaNhanVienPanel panel = new suaNhanVienPanel(dialog, nhanVien, this.taiKhoan.getVaiTro());
                    dialog.add(panel);
                    dialog.pack();
                    dialog.setLocationRelativeTo(this);
                    dialog.setVisible(true);
                    loadNhanVienData();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi lấy thông tin nhân viên: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn nhân viên cần sửa!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void xoaNhanVien() {
        int selectedRow = nhanVienTable.getSelectedRow();
        if (selectedRow >= 0) {
            String maNV = nhanVienTable.getValueAt(selectedRow, 0).toString();
            String tenNV = nhanVienTable.getValueAt(selectedRow, 1).toString();
            
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa nhân viên " + tenNV + " không?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    boolean success = nhanVienDAO.xoaNhanVien(maNV);
                    if (success) {
                        JOptionPane.showMessageDialog(this,
                            "Đã xóa nhân viên thành công!",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                        loadNhanVienData();
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "Không thể xóa nhân viên!",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                        "Lỗi khi xóa nhân viên: " + ex.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn nhân viên cần xóa",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void xemChiTietNhanVien(String maNV) {
        try {
            nhanVienDTO nhanVien = nhanVienDAO.getNhanVienByMa(maNV);
            if (nhanVien != null) {
                chiTietNhanVienDialog dialog = new chiTietNhanVienDialog((JFrame) SwingUtilities.getWindowAncestor(this), nhanVien);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Không tìm thấy thông tin nhân viên!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi lấy thông tin chi tiết: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void thayDoiTrangThai() {
        int selectedRow = nhanVienTable.getSelectedRow();
        if (selectedRow >= 0) {
            String maNhanVien = (String) nhanVienTable.getValueAt(selectedRow, 0);
            taiKhoanDTO currentTaiKhoan = nhanVienDAO.getTaiKhoanByMaNhanVien(maNhanVien);
            
            if (currentTaiKhoan != null) {
                boolean success = nhanVienDAO.capNhatTrangThaiTaiKhoan(currentTaiKhoan); 
                
                if (success) {
                    taiKhoanDTO updatedTaiKhoan = nhanVienDAO.getTaiKhoanByMaNhanVien(maNhanVien);
                    String trangThaiDisplay = "Không xác định";
                    String buttonText = "Trạng thái";
                    String iconPath = "";

                    if(updatedTaiKhoan != null){
                        switch (updatedTaiKhoan.getTrangThai()) {
                            case 1: 
                                trangThaiDisplay = "Hoạt động"; 
                                buttonText = "Khóa"; 
                                iconPath = "/icon_img/lock_icon.png";
                                break;
                            case 0: 
                                trangThaiDisplay = "Không hoạt động"; 
                                buttonText = "Mở Khóa"; 
                                iconPath = "/icon_img/unlock_icon.png";
                                break;
                            case -1: 
                                trangThaiDisplay = "Đang xét duyệt"; 
                                buttonText = "Duyệt"; 
                                iconPath = "/icon_img/duyet_icon.png";
                                break;
                        }
                        nhanVienTable.setValueAt(trangThaiDisplay, selectedRow, 7);
                        if (nhanVienTable.getSelectedRow() == selectedRow) { 
                             btnTrangThai.setText(buttonText);
                             if (!iconPath.isEmpty()) {
                                 setButtonIcon(btnTrangThai, iconPath);
                             } else {
                                 btnTrangThai.setIcon(null); // Clear icon if not applicable
                             }
                        }
                    } else {
                         loadNhanVienData(); // Reload data if updatedTaiKhoan is null for some reason
                    }
                    JOptionPane.showMessageDialog(this, "Cập nhật trạng thái thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật trạng thái thất bại!");
                }
            } else {
                 JOptionPane.showMessageDialog(this, "Nhân viên này chưa có tài khoản để thay đổi trạng thái.");
            }
        }
    }

    private String getSelectedMaTaiKhoan() {
        int selectedRow = nhanVienTable.getSelectedRow();
        if (selectedRow >= 0) {
            return nhanVienTable.getValueAt(selectedRow, 0).toString();
        }
        return null;
    }
    
    private void resetSearchFields() {
        txtTimKiemKeyword.setText("");
        cboTimKiemTieuChi.setSelectedIndex(0);
        cboTimKiemVaiTro.setSelectedIndex(0);
        cboTimKiemTrangThai.setSelectedIndex(0);
        loadNhanVienData();
    }

    private void printNhanVienData() {
        try {
            List<nhanVienDTO> nhanVienList = nhanVienDAO.getAllNhanVien();

            if (nhanVienList == null || nhanVienList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu nhân viên để in.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><head><style>");
            htmlContent.append("body { font-family: Arial, sans-serif; margin: 20px; }");
            htmlContent.append("h1 { text-align: center; color: #333; }");
            htmlContent.append(".employee-record { border: 1px solid #ccc; padding: 10px; margin-bottom: 15px; border-radius: 5px; page-break-inside: avoid; }");
            htmlContent.append(".field-label { font-weight: bold; color: #555; }");
            htmlContent.append("p { margin: 5px 0; }");
            htmlContent.append("hr { border: 0; border-top: 1px dashed #ccc; margin: 10px 0; }");
            htmlContent.append("</style></head><body>");
            htmlContent.append("<h1>Danh Sách Chi Tiết Nhân Viên</h1>");

            SimpleDateFormat localDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat localTimestampFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            DecimalFormat localDecimalFormat = new DecimalFormat("#,###.##");

            for (nhanVienDTO nv : nhanVienList) {
                htmlContent.append("<div class='employee-record'>");
                htmlContent.append("<p><span class='field-label'>Mã Nhân Viên:</span> ").append(nv.getMaNhanVien() != null ? nv.getMaNhanVien() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Họ Tên:</span> ").append(nv.getHoTen() != null ? nv.getHoTen() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Giới Tính:</span> ").append(nv.getGioiTinh() != null ? nv.getGioiTinh() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Ngày Sinh:</span> ").append(nv.getNgaySinh() != null ? localDateFormat.format(nv.getNgaySinh()) : "Chưa cập nhật").append("</p>");
                htmlContent.append("<p><span class='field-label'>Số Điện Thoại:</span> ").append(nv.getSoDienThoai() != null ? nv.getSoDienThoai() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Email:</span> ").append(nv.getEmail() != null ? nv.getEmail() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Địa Chỉ:</span> ").append(nv.getDiaChi() != null ? nv.getDiaChi() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Chức Vụ:</span> ").append(nv.getChucVu() != null ? nv.getChucVu() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Mức Lương:</span> ").append(nv.getMucLuong() != null ? localDecimalFormat.format(nv.getMucLuong()) : "Chưa cập nhật").append("</p>");
                htmlContent.append("<p><span class='field-label'>Ngày Vào Làm:</span> ").append(nv.getNgayVaoLam() != null ? localTimestampFormat.format(nv.getNgayVaoLam()) : "Chưa cập nhật").append("</p>");
                htmlContent.append("<p><span class='field-label'>Mã Quản Lý:</span> ").append(nv.getMaQuanLy() != null ? nv.getMaQuanLy() : "").append("</p>");
                htmlContent.append("<p><span class='field-label'>Trạng Thái Nhân Viên:</span> ").append(nv.getTrangThai() != null ? nv.getTrangThai() : "").append("</p>");
                
                taiKhoanDTO tk = nhanVienDAO.getTaiKhoanByMaNhanVien(nv.getMaNhanVien());
                if (tk != null) {
                    htmlContent.append("<hr>");
                    htmlContent.append("<p><span class='field-label'>Tên Đăng Nhập (TK):</span> ").append(tk.getTenDangNhap() != null ? tk.getTenDangNhap() : "").append("</p>");
                    htmlContent.append("<p><span class='field-label'>Vai Trò (TK):</span> ").append(tk.getVaiTro() != null ? tk.getVaiTro().getDisplayName() : "").append("</p>");
                    String trangThaiTKStr;
                    switch (tk.getTrangThai()) {
                        case 1: trangThaiTKStr = "Hoạt động"; break;
                        case 0: trangThaiTKStr = "Không hoạt động"; break;
                        case -1: trangThaiTKStr = "Đang xét duyệt"; break;
                        default: trangThaiTKStr = "Không xác định";
                    }
                    htmlContent.append("<p><span class='field-label'>Trạng Thái (TK):</span> ").append(trangThaiTKStr).append("</p>");
                } else {
                    htmlContent.append("<hr>");
                    htmlContent.append("<p><em>Nhân viên này chưa có tài khoản.</em></p>");
                }
                htmlContent.append("</div>");
            }
            htmlContent.append("</body></html>");

            JEditorPane editorPane = new JEditorPane();
            editorPane.setContentType("text/html");
            editorPane.setText(htmlContent.toString());
            editorPane.setEditable(false);
            
            boolean printed = editorPane.print();
            if (!printed) {
                // JOptionPane.showMessageDialog(this, "Lệnh in đã bị hủy.", "In Bị Hủy", JOptionPane.WARNING_MESSAGE);
            }
        } catch (java.awt.print.PrinterException pe) {
            JOptionPane.showMessageDialog(this, "Lỗi khi in: Không tìm thấy máy in hoặc lỗi máy in.\n" + pe.getMessage(), "Lỗi In Ấn", JOptionPane.ERROR_MESSAGE);
             pe.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi chuẩn bị dữ liệu để in: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
