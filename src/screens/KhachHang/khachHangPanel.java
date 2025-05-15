package screens.KhachHang;

import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import BUS.KhachHangBUS;
import DTO.khachHangDTO;
import java.util.List;
import java.text.SimpleDateFormat;
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
import java.sql.Date;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import utils.FileUtils;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.JEditorPane;
import java.text.DecimalFormat;
import screens.TrangChu.AppColors;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

public class khachHangPanel extends javax.swing.JPanel {
    private KhachHangBUS khachHangBUS;
    private SimpleDateFormat dateFormat;

    public khachHangPanel() {
        initComponents();
        setupUI();

        // Khởi tạo BUS và định dạng ngày tháng
        khachHangBUS = new KhachHangBUS();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        setupIcons();
        setupSearchComponents();
        setupTable();
        // Load dữ liệu khách hàng
        loadKhachHangData();
        setupListeners();
        setupTableListeners();
    }

    private void setupIcons() {
        try {
            // Search Icon for btnTimKiem
            ImageIcon searchIcon = new ImageIcon(getClass().getResource("/icon_img/search.png"));
            if (searchIcon.getImage() != null) {
                Image scaledSearchIcon = searchIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                btnTimKiem.setIcon(new ImageIcon(scaledSearchIcon));
                btnTimKiem.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnTimKiem.setIconTextGap(5);
            }

            // Reset Icon for btnReset
            ImageIcon resetIcon = new ImageIcon(getClass().getResource("/icon_img/refresh.png"));
            if (resetIcon.getImage() != null) {
                Image scaledResetIcon = resetIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                btnReset.setIcon(new ImageIcon(scaledResetIcon));
                btnReset.setHorizontalTextPosition(SwingConstants.RIGHT);
                btnReset.setIconTextGap(5);
            }

            // Add Icon for btnThem
            ImageIcon addIcon = new ImageIcon(getClass().getResource("/icon_img/add.png"));
            if (addIcon.getImage() != null) {
                Image scaledAddIcon = addIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                btnThem.setIcon(new ImageIcon(scaledAddIcon));
                btnThem.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnThem.setIconTextGap(5);
            }

            // Edit Icon for btnSua
            ImageIcon editIcon = new ImageIcon(getClass().getResource("/icon_img/edit.png"));
            if (editIcon.getImage() != null) {
                Image scaledEditIcon = editIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                btnSua.setIcon(new ImageIcon(scaledEditIcon));
                btnSua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnSua.setIconTextGap(5);
            }

            // Delete Icon for btnXoa
            ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/icon_img/delete.png"));
            if (deleteIcon.getImage() != null) {
                Image scaledDeleteIcon = deleteIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                btnXoa.setIcon(new ImageIcon(scaledDeleteIcon));
                btnXoa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnXoa.setIconTextGap(5);
            }

            // Import Icon for btnImport
            ImageIcon importIcon = new ImageIcon(getClass().getResource("/icon_img/import_icon.png"));
            if (importIcon.getImage() != null) {
                Image scaledImportIcon = importIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                btnImport.setIcon(new ImageIcon(scaledImportIcon));
                btnImport.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnImport.setIconTextGap(5);
            }
            
            // Export Icon for btnExport
            ImageIcon exportIcon = new ImageIcon(getClass().getResource("/icon_img/export_icon.png"));
            if (exportIcon.getImage() != null) {
                Image scaledExportIcon = exportIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                btnExport.setIcon(new ImageIcon(scaledExportIcon));
                btnExport.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnExport.setIconTextGap(5);
            }

            // Print Icon for btnPrinter
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        containerPanel = new javax.swing.JPanel();
        containerPanel.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        containerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader = new javax.swing.JPanel();
        lblTittle = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        pnlBoxChinhSua = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        pnlBoxTable = new javax.swing.JPanel();
        scTable = new javax.swing.JScrollPane();
        khachHangTable = new javax.swing.JTable();
        pnlBoxTimKiem = new javax.swing.JPanel();
        btnTimKiem = new javax.swing.JButton();
        lblTimKiem = new javax.swing.JLabel();
        cboTimKiem = new javax.swing.JComboBox<>();
        textTimKiem = new javax.swing.JTextField();
        btnReset = new javax.swing.JButton();

        pnlBottomActions = new javax.swing.JPanel();
        btnImport = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        btnPrinter = new javax.swing.JButton();

        pnlHeader.setBackground(AppColors.NEW_HEADER_PANEL_BG_COLOR);

        lblTittle.setFont(new java.awt.Font("Segoe UI", 1, 24));
        lblTittle.setText("QUẢN LÝ KHÁCH HÀNG");
        lblTittle.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlHeaderLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTittle)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        pnlHeaderLayout.setVerticalGroup(
                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlHeaderLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblTittle)
                                .addContainerGap(20, Short.MAX_VALUE)));

        containerPanel.add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 70));

        pnlContent.setBackground(AppColors.NEW_MAIN_BG_COLOR);

        pnlBoxTimKiem.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        javax.swing.border.TitledBorder searchBorder = javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Tìm kiếm");
        searchBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlBoxTimKiem.setBorder(searchBorder);
        pnlBoxTimKiem.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); 
        lblTimKiem.setText("Tìm kiếm");
        lblTimKiem.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlBoxTimKiem.add(lblTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 30));

        cboTimKiem.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Mã khách hàng", "Tên khách hàng", "Email", "Số điện thoại" }));
        pnlBoxTimKiem.add(cboTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 150, 30));
        pnlBoxTimKiem.add(textTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 180, 30));
        
        // Gender filter components positioning
        lblTKGioiTinh = new javax.swing.JLabel();
        cboGioiTinh = new javax.swing.JComboBox<>();
        lblTKGioiTinh.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblTKGioiTinh.setText("Giới tính");
        lblTKGioiTinh.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlBoxTimKiem.add(lblTKGioiTinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, -1, 30));

        cboGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Nam", "Nữ" }));
        pnlBoxTimKiem.add(cboGioiTinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 30, 150, 30));

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        btnTimKiem.setForeground(java.awt.Color.WHITE);
        btnTimKiem.setPreferredSize(new java.awt.Dimension(120, 30));
        pnlBoxTimKiem.add(btnTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 25, 120, 30));

        btnReset.setText("Làm mới");
        btnReset.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        btnReset.setForeground(java.awt.Color.WHITE);
        btnReset.setPreferredSize(new java.awt.Dimension(120, 30));
        pnlBoxTimKiem.add(btnReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 65, 120, 30));

        pnlBoxChinhSua.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        javax.swing.border.TitledBorder editBorder = javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Chỉnh sửa");
        editBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlBoxChinhSua.setBorder(editBorder);
        pnlBoxChinhSua.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 23));

        Dimension editButtonSize = new java.awt.Dimension(120, 34);

        btnThem.setText("Thêm");
        btnThem.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        btnThem.setForeground(java.awt.Color.WHITE);
        btnThem.setPreferredSize(editButtonSize);
        pnlBoxChinhSua.add(btnThem);

        btnSua.setText("Sửa");
        btnSua.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        btnSua.setForeground(java.awt.Color.WHITE);
        btnSua.setPreferredSize(editButtonSize);
        pnlBoxChinhSua.add(btnSua);

        btnXoa.setText("Xóa");
        btnXoa.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        btnXoa.setForeground(java.awt.Color.WHITE);
        btnXoa.setPreferredSize(editButtonSize);
        pnlBoxChinhSua.add(btnXoa);
        
        pnlBoxTable.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        javax.swing.border.TitledBorder tableBorder = javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Bảng thông tin");
        tableBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlBoxTable.setBorder(tableBorder);
        pnlBoxTable.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        khachHangTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "STT", "Mã KH", "Tên KH", "Giới tính", "Số điện thoại", "Email", "Địa chỉ", "Ngày sinh", "Chi tiết"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        khachHangTable.setShowGrid(true);
        khachHangTable.setBackground(java.awt.Color.WHITE);
        khachHangTable.getTableHeader().setBackground(AppColors.NEW_HEADER_PANEL_BG_COLOR);
        khachHangTable.getTableHeader().setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        khachHangTable.setGridColor(AppColors.NEW_BORDER_LINES_COLOR);
        scTable.setViewportView(khachHangTable);
        pnlBoxTable.add(scTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 941, 292));

        pnlBottomActions.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        pnlBottomActions.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        Dimension bottomButtonSize = new Dimension(170, 40);

        btnImport.setText("Nhập dữ liệu");
        btnImport.setBackground(AppColors.NEW_QUICK_ACCESS_BUTTON_BG_COLOR);
        btnImport.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR);
        btnImport.setPreferredSize(bottomButtonSize);
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utils.FileUtils.importFromExcelForKhachHang(khachHangTable);
                loadKhachHangData();
            }
        });
        pnlBottomActions.add(btnImport);
        
        btnExport.setText("Xuất dữ liệu");
        btnExport.setBackground(AppColors.NEW_QUICK_ACCESS_BUTTON_BG_COLOR);
        btnExport.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR);
        btnExport.setPreferredSize(bottomButtonSize);
        btnExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 if (khachHangTable.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Không có dữ liệu để xuất!",
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                utils.FileUtils.showExportOptionsForKhachHang(khachHangTable, "DanhSachKhachHang");
            }
        });
        pnlBottomActions.add(btnExport);

        btnPrinter.setText("In ấn");
        btnPrinter.setBackground(AppColors.NEW_QUICK_ACCESS_BUTTON_BG_COLOR);
        btnPrinter.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR);
        btnPrinter.setPreferredSize(bottomButtonSize);
        btnPrinter.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                        try {
                                List<khachHangDTO> khachHangList = khachHangBUS.getAllKhachHang();

                                if (khachHangList == null || khachHangList.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "Không có dữ liệu khách hàng để in.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                        return;
                                }

                                StringBuilder htmlContent = new StringBuilder();
                                htmlContent.append("<html><head><style>");
                                htmlContent.append("body { font-family: Arial, sans-serif; margin: 20px; }");
                                htmlContent.append("h1 { text-align: center; color: #333; }");
                                htmlContent.append(".customer-record { border: 1px solid #ccc; padding: 10px; margin-bottom: 15px; border-radius: 5px; page-break-inside: avoid; }");
                                htmlContent.append(".field-label { font-weight: bold; color: #555; }");
                                htmlContent.append("p { margin: 5px 0; }");
                                htmlContent.append("</style></head><body>");
                                htmlContent.append("<h1>Danh Sách Chi Tiết Khách Hàng</h1>");

                                for (khachHangDTO kh : khachHangList) {
                                        htmlContent.append("<div class='customer-record'>");
                                        htmlContent.append("<p><span class='field-label'>Mã Khách Hàng:</span> ").append(kh.getMaKhachHang() != null ? kh.getMaKhachHang() : "").append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Họ Tên:</span> ").append(kh.getHoTen() != null ? kh.getHoTen() : "").append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Giới Tính:</span> ").append(kh.getGioiTinh() != null ? kh.getGioiTinh() : "").append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Số Điện Thoại:</span> ").append(kh.getSoDienThoai() != null && !kh.getSoDienThoai().trim().isEmpty() ? kh.getSoDienThoai() : "Chưa cập nhật").append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Email:</span> ").append(kh.getEmail() != null && !kh.getEmail().trim().isEmpty() ? kh.getEmail() : "Chưa cập nhật").append("</p>");
                                        htmlContent.append("<p><span class='field-label'>Địa Chỉ:</span> ").append(kh.getDiaChi() != null && !kh.getDiaChi().trim().isEmpty() ? kh.getDiaChi() : "Chưa cập nhật").append("</p>");
                                        String ngaySinhDisplay = "Chưa cập nhật";
                                        if (kh.getNgaySinh() != null) {
                                            try {
                                                ngaySinhDisplay = dateFormat.format(kh.getNgaySinh());
                                            } catch (Exception ex) { /* ignore, keep default */ }
                                        }
                                        htmlContent.append("<p><span class='field-label'>Ngày Sinh:</span> ").append(ngaySinhDisplay).append("</p>");
                                        htmlContent.append("</div>");
                                }
                                htmlContent.append("</body></html>");

                                JEditorPane editorPane = new JEditorPane();
                                editorPane.setContentType("text/html");
                                editorPane.setText(htmlContent.toString());
                                editorPane.setEditable(false);

                                boolean printed = editorPane.print();
                        } catch (java.awt.print.PrinterException pe) {
                            JOptionPane.showMessageDialog(null, "Lỗi khi in: Không tìm thấy máy in hoặc lỗi máy in.\\n" + pe.getMessage(), "Lỗi In Ấn", JOptionPane.ERROR_MESSAGE);
                            pe.printStackTrace();
                        } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, "Lỗi khi chuẩn bị dữ liệu để in: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                                e.printStackTrace();
                        }
                }
        });
        pnlBottomActions.add(btnPrinter);

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlBoxTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE)
                    .addComponent(pnlBoxChinhSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlBoxTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlBottomActions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBoxTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBoxChinhSua, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBoxTable, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlBottomActions, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        containerPanel.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1000, 630));

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        add(containerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 700));
    }// </editor-fold>//GEN-END:initComponents

    private void setupUI() {
        pnlBoxTimKiem.setPreferredSize(new java.awt.Dimension(960, 120));
        pnlBoxChinhSua.setPreferredSize(new java.awt.Dimension(960, 80));
        pnlBoxTable.setPreferredSize(new java.awt.Dimension(960, 322));
        pnlBottomActions.setPreferredSize(new java.awt.Dimension(960, 60));
    }
    
    private void setupTable() {
        khachHangTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        khachHangTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        khachHangTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        khachHangTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        khachHangTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        khachHangTable.getColumnModel().getColumn(5).setPreferredWidth(150);
        khachHangTable.getColumnModel().getColumn(6).setPreferredWidth(150);
        khachHangTable.getColumnModel().getColumn(7).setPreferredWidth(80);
        khachHangTable.getColumnModel().getColumn(8).setPreferredWidth(80);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        khachHangTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        khachHangTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        khachHangTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        khachHangTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        khachHangTable.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        khachHangTable.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);

        khachHangTable.getColumnModel().getColumn(8).setCellRenderer(new DefaultTableCellRenderer() {
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
                if (table.getClientProperty("hoverRow") != null && (int) table.getClientProperty("hoverRow") == row && column == 8) {
                    c.setForeground(AppColors.NEW_SELECTED_BUTTON_COLOR);
                }
                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });

        khachHangTable.setRowHeight(25);
    }

    private void setupTableListeners() {
        khachHangTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = khachHangTable.rowAtPoint(e.getPoint());
                int col = khachHangTable.columnAtPoint(e.getPoint());
                
                int lastColumnIndex = khachHangTable.getColumnCount() - 1;
                
                if (row >= 0 && col == lastColumnIndex) {
                    showChiTietKhachHang(row);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                khachHangTable.putClientProperty("hoverRow", -1);
                khachHangTable.repaint();
            }
        });

        khachHangTable.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = khachHangTable.rowAtPoint(e.getPoint());
                int col = khachHangTable.columnAtPoint(e.getPoint());
                khachHangTable.putClientProperty("hoverRow", row);
                khachHangTable.repaint();

                if (col == khachHangTable.getColumnCount() - 1) {
                    khachHangTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    khachHangTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

    private void showChiTietKhachHang(int row) {
        try {
            Object ngaySinhObj = khachHangTable.getValueAt(row, 7);
            Date ngaySinh = null;
            if (ngaySinhObj != null && !ngaySinhObj.toString().isEmpty() && !ngaySinhObj.toString().equals("Chưa cập nhật")) {
                java.util.Date parsedDate = dateFormat.parse(ngaySinhObj.toString());
                ngaySinh = new Date(parsedDate.getTime());
            }

            khachHangDTO kh = new khachHangDTO(
                getTableValueAsString(row, 1),
                getTableValueAsString(row, 2),
                getTableValueAsString(row, 3),
                getTableValueAsString(row, 4),
                getTableValueAsString(row, 5),
                getTableValueAsString(row, 6),
                ngaySinh
            );

            chiTietKhachHangDialog dialog = new chiTietKhachHangDialog(null, kh);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Có lỗi xảy ra khi hiển thị thông tin chi tiết: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getTableValueAsString(int row, int column) {
        Object value = khachHangTable.getValueAt(row, column);
        return value != null ? value.toString() : "";
    }

    private void loadKhachHangData() {
        List<khachHangDTO> khachHangList = khachHangBUS.getAllKhachHang();
        updateTableData(khachHangList);
    }

    private void updateTableData(List<khachHangDTO> khachHangList) {
        DefaultTableModel model = (DefaultTableModel) khachHangTable.getModel();
        model.setRowCount(0);

        int stt = 1;
        for (khachHangDTO kh : khachHangList) {
            String ngaySinhStr = "Chưa cập nhật";

            try {
                if (kh.getNgaySinh() != null) {
                    ngaySinhStr = dateFormat.format(kh.getNgaySinh());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            model.addRow(new Object[] {
                    stt++,
                    kh.getMaKhachHang(),
                    kh.getHoTen(),
                    kh.getGioiTinh(),
                    kh.getSoDienThoai() != null && !kh.getSoDienThoai().trim().isEmpty() ? kh.getSoDienThoai() : "Chưa cập nhật",
                    kh.getEmail() != null && !kh.getEmail().trim().isEmpty() ? kh.getEmail() : "Chưa cập nhật",
                    kh.getDiaChi() != null && !kh.getDiaChi().trim().isEmpty() ? kh.getDiaChi() : "Chưa cập nhật",
                    ngaySinhStr,
                    "Xem chi tiết"
            });
        }
    }

    private void searchKhachHang() {
        String keyword = textTimKiem.getText().trim();
        String searchType = cboTimKiem.getSelectedItem().toString();
        String gioiTinh = cboGioiTinh.getSelectedItem().toString();
        
        if (keyword.isEmpty() && "Tất cả".equals(searchType) && "Tất cả".equals(gioiTinh)) {
            loadKhachHangData();
            return;
        }
        if (!"Tất cả".equals(searchType) && keyword.isEmpty() && "Tất cả".equals(gioiTinh)) {
             JOptionPane.showMessageDialog(this,
                "Vui lòng nhập từ khóa để tìm kiếm theo " + searchType + ".",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            textTimKiem.requestFocus();
            return;
        }

        List<khachHangDTO> searchResults = khachHangBUS.searchKhachHang(keyword, searchType, gioiTinh);
        updateTableData(searchResults);
        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Không tìm thấy khách hàng nào khớp với tiêu chí tìm kiếm",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void resetSearchFields() {
        textTimKiem.setText("");
        cboTimKiem.setSelectedIndex(0);
        cboGioiTinh.setSelectedIndex(0);
        loadKhachHangData();
    }

    private void setupSearchComponents() {
        btnTimKiem.addActionListener(e -> searchKhachHang());
        btnReset.addActionListener(e -> resetSearchFields());

        textTimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchKhachHang();
                }
            }
        });
    }

    private void setupListeners() {
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog();
                dialog.setTitle("Thêm Khách Hàng Mới");
                dialog.setModal(true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                
                themKhachHangPanel themPanel = new themKhachHangPanel();
                dialog.add(themPanel);
                
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                
                loadKhachHangData();
            }
        });

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = khachHangTable.getSelectedRow();
                if (selectedRow >= 0) {
                    try {
                        Object ngaySinhObj = khachHangTable.getValueAt(selectedRow, 7);
                        Date ngaySinh = null;
                        if (ngaySinhObj != null && !ngaySinhObj.toString().isEmpty() && !ngaySinhObj.toString().equals("Chưa cập nhật")) {
                            try {
                                java.util.Date parsedDate = dateFormat.parse(ngaySinhObj.toString());
                                ngaySinh = new Date(parsedDate.getTime());
                            } catch (Exception ex) {
                                System.out.println("Lỗi parse ngày sinh: " + ex.getMessage());
                            }
                        }

                        String maKH = getTableValueAsString(selectedRow, 1);
                        String hoTen = getTableValueAsString(selectedRow, 2);
                        String gioiTinh = getTableValueAsString(selectedRow, 3);
                        String soDienThoai = getTableValueAsString(selectedRow, 4);
                        String email = getTableValueAsString(selectedRow, 5);
                        String diaChi = getTableValueAsString(selectedRow, 6);

                        if (maKH.isEmpty() || hoTen.isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                "Không thể lấy thông tin khách hàng. Vui lòng thử lại.",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        khachHangDTO kh = new khachHangDTO(
                            maKH,
                            hoTen,
                            gioiTinh,
                            soDienThoai,
                            email,
                            diaChi,
                            ngaySinh
                        );

                        JDialog dialog = new JDialog();
                        dialog.setTitle("Sửa Thông Tin Khách Hàng");
                        dialog.setModal(true);
                        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        
                        suaKhachHangPanel suaPanel = new suaKhachHangPanel(dialog, kh);
                        dialog.add(suaPanel);
                        
                        dialog.pack();
                        dialog.setLocationRelativeTo(null);
                        dialog.setVisible(true);
                        
                        loadKhachHangData();
                        
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null,
                            "Có lỗi xảy ra khi hiển thị form sửa thông tin: " + ex.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                        "Vui lòng chọn khách hàng cần sửa",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = khachHangTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String maKH = khachHangTable.getValueAt(selectedRow, 1).toString();
                    String tenKH = khachHangTable.getValueAt(selectedRow, 2).toString();
                    
                    int confirm = JOptionPane.showConfirmDialog(null,
                            "Bạn có chắc chắn muốn xóa khách hàng " + tenKH + " không?",
                            "Xác nhận xóa",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            boolean success = khachHangBUS.xoaKhachHang(maKH);
                            if (success) {
                                JOptionPane.showMessageDialog(null,
                                    "Đã xóa khách hàng thành công!",
                                    "Thông báo",
                                    JOptionPane.INFORMATION_MESSAGE);
                                loadKhachHangData();
                            }
                        } catch (RuntimeException ex) {
                            JOptionPane.showMessageDialog(null,
                                ex.getMessage(),
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                        "Vui lòng chọn khách hàng cần xóa",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    public javax.swing.JPanel getKhachHangPanel() {
        return containerPanel;
    }

    // Variables declaration - do not modify
    private javax.swing.JPanel containerPanel;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnExport;
    private javax.swing.JComboBox<String> cboTimKiem;
    private javax.swing.JLabel lblTittle;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JPanel pnlBoxChinhSua;
    private javax.swing.JPanel pnlBoxTable;
    private javax.swing.JPanel pnlBoxTimKiem;
    private javax.swing.JScrollPane scTable;
    private javax.swing.JTable khachHangTable;
    private javax.swing.JTextField textTimKiem;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnPrinter;
    private javax.swing.JButton btnReset;
    private javax.swing.JPanel pnlBottomActions;
    private javax.swing.JLabel lblTKGioiTinh;
    private javax.swing.JComboBox<String> cboGioiTinh;
    // End of variables declaration
}