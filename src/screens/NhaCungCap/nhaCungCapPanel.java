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

/**
 *
 * @author nson9
 */
public class nhaCungCapPanel extends javax.swing.JPanel {
    private NhaCungCapDAO nhaCungCapDAO;
    private NhaCungCapBUS nhaCungCapBUS;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField txtSearch;

    /**
     * Creates new form ncc
     */
    public nhaCungCapPanel() {
        initComponents();

        // Khởi tạo DAO và BUS
        nhaCungCapDAO = new NhaCungCapDAO();
        nhaCungCapBUS = new NhaCungCapBUS();

        // Thiết lập combobox tìm kiếm
        cboTimKiem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
                "Tất cả", "Mã NCC", "Tên NCC", "Email", "Số điện thoại"
        }));

        // Load dữ liệu nhà cung cấp
        loadData();

        // Thiết lập bảng
        setupTable();

        // Thêm action listener cho nút tìm kiếm
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchNhaCungCap();
            }
        });

        // Thêm action listener cho nút thêm
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showThemNhaCungCapDialog();
            }
        });

        // Thêm action listener cho nút sửa
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showSuaNhaCungCapDialog();
            }
        });

        // Thêm action listener cho nút xóa
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaNhaCungCap();
            }
        });

        // Thêm action listener cho nút export
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utils.FileUtils.showExportOptionsForNhaCungCap(nhaCungCapTable, "DanhSachNhaCungCap");
            }
        });

        // Thêm action listener cho nút import
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utils.FileUtils.importFromExcelForNhaCungCap(nhaCungCapTable);
                loadData(); // Refresh the table after import
            }
        });

        // Thêm action listener cho nút print
        btnPrinter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    List<nhaCungCapDTO> nccList = nhaCungCapBUS.getAllNhaCungCap();

                    if (nccList == null || nccList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Không có dữ liệu nhà cung cấp để in.", "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    StringBuilder htmlContent = new StringBuilder();
                    htmlContent.append("<html><head><style>");
                    htmlContent.append("body { font-family: Arial, sans-serif; margin: 20px; }");
                    htmlContent.append("h1 { text-align: center; color: #333; }");
                    htmlContent.append(
                            ".supplier-record { border: 1px solid #ccc; padding: 10px; margin-bottom: 15px; border-radius: 5px; page-break-inside: avoid; }");
                    htmlContent.append(".field-label { font-weight: bold; color: #555; }");
                    htmlContent.append("p { margin: 5px 0; }");
                    htmlContent.append("</style></head><body>");
                    htmlContent.append("<h1>Danh Sách Chi Tiết Nhà Cung Cấp</h1>");

                    for (nhaCungCapDTO ncc : nccList) {
                        htmlContent.append("<div class='supplier-record'>");
                        htmlContent.append("<p><span class='field-label'>Mã NCC:</span> ")
                                .append(ncc.getMaNhaCungCap() != null ? ncc.getMaNhaCungCap() : "").append("</p>");
                        htmlContent.append("<p><span class='field-label'>Tên NCC:</span> ")
                                .append(ncc.getTenNhaCungCap() != null ? ncc.getTenNhaCungCap() : "").append("</p>");
                        htmlContent.append("<p><span class='field-label'>Loại Sản Phẩm:</span> ")
                                .append(ncc.getLoaiSP() != null ? ncc.getLoaiSP() : "").append("</p>");
                        String namHopTacDisplay = (ncc.getNamHopTac() > 0) ? String.valueOf(ncc.getNamHopTac())
                                : "Chưa cập nhật";
                        htmlContent.append("<p><span class='field-label'>Năm Hợp Tác:</span> ").append(namHopTacDisplay)
                                .append("</p>");
                        htmlContent.append("<p><span class='field-label'>Địa Chỉ:</span> ")
                                .append(ncc.getDiaChi() != null ? ncc.getDiaChi() : "").append("</p>");
                        htmlContent.append("<p><span class='field-label'>Email:</span> ")
                                .append(ncc.getEmail() != null ? ncc.getEmail() : "").append("</p>");
                        htmlContent.append("<p><span class='field-label'>Số Điện Thoại:</span> ")
                                .append(ncc.getSoDienThoai() != null ? ncc.getSoDienThoai() : "").append("</p>");
                        htmlContent.append("<p><span class='field-label'>Trạng Thái:</span> ")
                                .append(ncc.getTrangThai() != null ? ncc.getTrangThai() : "").append("</p>");
                        htmlContent.append("</div>");
                    }
                    htmlContent.append("</body></html>");

                    JEditorPane editorPane = new JEditorPane();
                    editorPane.setContentType("text/html");
                    editorPane.setText(htmlContent.toString());
                    editorPane.setEditable(false);

                    boolean printed = editorPane.print();
                    if (!printed) {
                        // JOptionPane.showMessageDialog(null, "Lệnh in đã bị hủy.", "In Bị Hủy",
                        // JOptionPane.WARNING_MESSAGE);
                    }
                } catch (java.awt.print.PrinterException pe) {
                    JOptionPane.showMessageDialog(null,
                            "Lỗi khi in: Không tìm thấy máy in hoặc lỗi máy in.\\n" + pe.getMessage(), "Lỗi In Ấn",
                            JOptionPane.ERROR_MESSAGE);
                    pe.printStackTrace();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi chuẩn bị dữ liệu để in: " + e.getMessage(), "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });
    }

    public javax.swing.JPanel getNhaCungCapPanel() {
        return containerPanel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Khởi tạo panel chính
        containerPanel = new javax.swing.JPanel();
        containerPanel.setPreferredSize(new java.awt.Dimension(1000, 700));
        containerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // Khởi tạo các components
        pnlHeader = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        pnlContent1 = new javax.swing.JPanel();
        btnExport = new javax.swing.JButton();
        pnlBoxChinhSua = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        pnlBoxTable = new javax.swing.JPanel();
        scTable = new javax.swing.JScrollPane();
        nhaCungCapTable = new javax.swing.JTable();
        pnlBoxTimKiem = new javax.swing.JPanel();
        btnTimKiem = new javax.swing.JButton();
        lblTimKiem = new javax.swing.JLabel();
        cboTimKiem = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        btnImport = new javax.swing.JButton("Import");
        btnPrinter = new javax.swing.JButton("In");

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        containerPanel.setPreferredSize(new java.awt.Dimension(960, 680));
        containerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader.setBackground(new java.awt.Color(12, 150, 156));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblTitle.setText("Quản lý nhà cung cấp");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlHeaderLayout.createSequentialGroup()
                                .addGap(383, 383, 383)
                                .addComponent(lblTitle)
                                .addContainerGap(387, Short.MAX_VALUE)));
        pnlHeaderLayout.setVerticalGroup(
                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlHeaderLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(14, Short.MAX_VALUE)));

        containerPanel.add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 70));

        pnlContent.setBackground(new java.awt.Color(107, 163, 190));
        pnlContent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlContent1.setBackground(new java.awt.Color(107, 163, 190));

        // Setup Export button
        btnExport.setText("Xuất file");
        ImageIcon exportIcon = new ImageIcon("src/icon_img/export_icon.png");
        btnExport.setIcon(new ImageIcon(
                exportIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
        btnExport.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnExport.setPreferredSize(new java.awt.Dimension(340, 40));

        // Setup Import button
        btnImport = new javax.swing.JButton("Import");
        ImageIcon importIcon = new ImageIcon("src/icon_img/import_icon.png");
        btnImport.setIcon(new ImageIcon(
                importIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
        btnImport.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnImport.setPreferredSize(new java.awt.Dimension(100, 40));

        // Setup Print button
        btnPrinter = new javax.swing.JButton("In");
        ImageIcon printIcon = new ImageIcon("src/icon_img/print_icon.png");
        btnPrinter.setIcon(new ImageIcon(
                printIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
        btnPrinter.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnPrinter.setPreferredSize(new java.awt.Dimension(100, 40));

        pnlBoxChinhSua.setBackground(new java.awt.Color(107, 163, 190));
        pnlBoxChinhSua.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chỉnh sửa"));
        pnlBoxChinhSua.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnThem.setText("Thêm ");
        pnlBoxChinhSua.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 24, -1, 34));

        btnSua.setText("Sửa");
        pnlBoxChinhSua.add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(435, 24, -1, 34));

        btnXoa.setText("Xóa");
        pnlBoxChinhSua.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(705, 24, -1, 34));

        pnlBoxTable.setBackground(new java.awt.Color(107, 163, 190));
        pnlBoxTable.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng thông tin"));
        pnlBoxTable.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nhaCungCapTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "STT", "Mã NCC", "Tên NCC", "Loại SP", "Năm hợp tác", "Địa chỉ", "Email", "Số điện thoại",
                        "Trạng thái", "Chi tiết"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        nhaCungCapTable.setShowGrid(true);
        scTable.setViewportView(nhaCungCapTable);

        pnlBoxTable.add(scTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 920, 310));

        pnlBoxTimKiem.setBackground(new java.awt.Color(107, 163, 190));
        pnlBoxTimKiem.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tìm kiếm\n"));
        pnlBoxTimKiem.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchNhaCungCap();
            }
        });
        pnlBoxTimKiem.add(btnTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 40, 90, 36));

        lblTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTimKiem.setText("Tìm kiếm");
        pnlBoxTimKiem.add(lblTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        cboTimKiem.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pnlBoxTimKiem.add(cboTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 300, 30));
        pnlBoxTimKiem.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 50, 320, 30));

        javax.swing.GroupLayout pnlContent1Layout = new javax.swing.GroupLayout(pnlContent1);
        pnlContent1.setLayout(pnlContent1Layout);
        pnlContent1Layout.setHorizontalGroup(
                pnlContent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlContent1Layout.createSequentialGroup()
                                .addGroup(pnlContent1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlContent1Layout.createSequentialGroup()
                                                .addGap(325, 325, 325)
                                                .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnPrinter, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(pnlContent1Layout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addGroup(pnlContent1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(pnlBoxTimKiem,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                961, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(pnlContent1Layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(pnlBoxTable,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 956,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(pnlBoxChinhSua,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 956,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(18, Short.MAX_VALUE)));
        pnlContent1Layout.setVerticalGroup(
                pnlContent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlContent1Layout.createSequentialGroup()
                                .addComponent(pnlBoxTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(pnlBoxChinhSua, javax.swing.GroupLayout.PREFERRED_SIZE, 70,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlBoxTable, javax.swing.GroupLayout.PREFERRED_SIZE, 348,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlContent1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)));

        pnlContent.add(pnlContent1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 600));

        containerPanel.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1000, 630));

        add(containerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 700));
    }

    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) nhaCungCapTable.getModel();
        model.setRowCount(0);

        for (nhaCungCapDTO ncc : nhaCungCapBUS.getAllNhaCungCap()) {
            model.addRow(new Object[] {
                    model.getRowCount() + 1, // STT
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
    }

    private void searchNhaCungCap() {
        String keyword = jTextField1.getText().trim();
        String searchType = (String) cboTimKiem.getSelectedItem();

        DefaultTableModel model = (DefaultTableModel) nhaCungCapTable.getModel();
        model.setRowCount(0);

        for (nhaCungCapDTO ncc : nhaCungCapBUS.searchNhaCungCap(keyword, searchType)) {
            model.addRow(new Object[] {
                    model.getRowCount() + 1, // STT
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
    }

    private void showThemNhaCungCapDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm nhà cung cấp", true);
        dialog.setLayout(new BorderLayout());

        themNhaCungCapPanel panel = new themNhaCungCapPanel();
        dialog.add(panel, BorderLayout.CENTER);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        // Reload data after dialog is closed
        loadData();
    }

    private void showSuaNhaCungCapDialog() {
        int selectedRow = nhaCungCapTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần sửa!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maNCC = (String) nhaCungCapTable.getValueAt(selectedRow, 1);
        nhaCungCapDTO ncc = nhaCungCapBUS.getNhaCungCapByMa(maNCC);
        if (ncc == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhà cung cấp!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sửa nhà cung cấp", true);
        dialog.setLayout(new BorderLayout());

        suaNhaCungCapPanel panel = new suaNhaCungCapPanel(dialog, ncc);
        dialog.add(panel, BorderLayout.CENTER);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        // Reload data after dialog is closed
        loadData();
    }

    private void xoaNhaCungCap() {
        int selectedRow = nhaCungCapTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần xóa!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maNCC = (String) nhaCungCapTable.getValueAt(selectedRow, 1);
        String tenNCC = (String) nhaCungCapTable.getValueAt(selectedRow, 2);

        int initialChoice = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa nhà cung cấp " + tenNCC + " (Mã: " + maNCC + ")?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (initialChoice == JOptionPane.NO_OPTION) {
            return;
        }

        try {
            // Dependency Checks
            SanPhamBUS spBUS = new SanPhamBUS();
            List<sanPhamDTO> primarySuppliedProducts = spBUS.getSanPhamByMaNhaCungCap(maNCC);

            NhaCungCap_SanPhamBUS nccspBUS = new NhaCungCap_SanPhamBUS();
            List<NhaCungCap_SanPhamDTO> manyToManyLinks = nccspBUS.getSanPhamByNhaCungCap(maNCC);

            BUS.PhieuNhapBUS pnBUS = new BUS.PhieuNhapBUS();
            List<DTO.PhieuNhapDTO> phieuNhapLinks = pnBUS.searchBySupplier(maNCC);

            // 1. Handle PhieuNhap dependencies (Highest precedence for this error)
            if (phieuNhapLinks != null && !phieuNhapLinks.isEmpty()) {
                String[] pnOptions = { "Cập nhật Phiếu Nhập sang NCC khác", "Hủy" };
                int pnOption = JOptionPane.showOptionDialog(this,
                        "Nhà cung cấp " + tenNCC + " có " + phieuNhapLinks.size() + " phiếu nhập liên quan.\n" +
                                "Bạn PHẢI chuyển các phiếu nhập này sang một nhà cung cấp khác trước khi xóa.",
                        "Xử lý Phiếu Nhập liên quan",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        pnOptions,
                        pnOptions[1]);

                if (pnOption == 0) { // Cập nhật Phiếu Nhập
                    List<nhaCungCapDTO> otherSuppliers = nhaCungCapBUS.getAllNhaCungCap();
                    otherSuppliers.removeIf(nccDto -> nccDto.getMaNhaCungCap().equals(maNCC));
                    if (otherSuppliers.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không có nhà cung cấp khác để chuyển phiếu nhập sang!",
                                "Không thể cập nhật", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    JComboBox<String> supplierCombo = new JComboBox<>();
                    for (nhaCungCapDTO nccDto : otherSuppliers) {
                        supplierCombo.addItem(nccDto.getMaNhaCungCap() + " - " + nccDto.getTenNhaCungCap());
                    }
                    int result = JOptionPane.showConfirmDialog(this, supplierCombo,
                            "Chọn nhà cung cấp mới cho các phiếu nhập", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        String selectedPnSupplierItem = (String) supplierCombo.getSelectedItem();
                        String newPnMaNCC = selectedPnSupplierItem.split(" - ")[0];
                        if (pnBUS.updateMaNhaCungCapForPhieuNhap(maNCC, newPnMaNCC)) {
                            JOptionPane.showMessageDialog(this, "Đã cập nhật các phiếu nhập sang nhà cung cấp "
                                    + newPnMaNCC + ". Giờ bạn có thể thử xóa lại.");
                            // Clear phieuNhapLinks as they are now handled for subsequent checks
                            phieuNhapLinks.clear();
                        } else {
                            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật phiếu nhập sang nhà cung cấp mới.",
                                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return; // Stop deletion if PhieuNhap update fails
                        }
                    } else {
                        return;
                        /* User cancelled new supplier selection for PhieuNhap */ }
                } else {
                    return;
                    /* User cancelled PhieuNhap handling */ }
            } // End PhieuNhap check

            // 2. Handle SanPham (primary supplier) dependencies
            if (primarySuppliedProducts != null && !primarySuppliedProducts.isEmpty()) {
                String[] spOptions = { "Cập nhật sản phẩm sang NCC khác", "Hủy" };
                int spOption = JOptionPane.showOptionDialog(this,
                        "Nhà cung cấp " + tenNCC + " là nhà cung cấp chính cho " + primarySuppliedProducts.size()
                                + " sản phẩm.\n" +
                                "Bạn PHẢI chuyển các sản phẩm này sang một nhà cung cấp khác trước khi xóa.",
                        "Xử lý sản phẩm phụ thuộc (NCC Chính)",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        spOptions,
                        spOptions[1]);

                if (spOption == 0) { // Cập nhật sản phẩm
                    List<nhaCungCapDTO> otherSuppliers = nhaCungCapBUS.getAllNhaCungCap();
                    otherSuppliers.removeIf(nccDto -> nccDto.getMaNhaCungCap().equals(maNCC));
                    if (otherSuppliers.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không có nhà cung cấp khác để chuyển sản phẩm sang!",
                                "Không thể cập nhật", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    JComboBox<String> supplierCombo = new JComboBox<>();
                    for (nhaCungCapDTO nccDto : otherSuppliers) {
                        supplierCombo.addItem(nccDto.getMaNhaCungCap() + " - " + nccDto.getTenNhaCungCap());
                    }
                    int result = JOptionPane.showConfirmDialog(this, supplierCombo,
                            "Chọn nhà cung cấp mới cho các sản phẩm phụ thuộc", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        String selectedSpSupplierItem = (String) supplierCombo.getSelectedItem();
                        String newSpMaNCC = selectedSpSupplierItem.split(" - ")[0];
                        boolean allProductsUpdated = true;
                        for (sanPhamDTO sp : primarySuppliedProducts) {
                            String currentProductMaSP = sp.getMaSanPham();
                            sp.setMaNhaCungCap(newSpMaNCC);
                            if (!spBUS.updateSanPham(sp)) {
                                allProductsUpdated = false;
                                JOptionPane.showMessageDialog(this,
                                        "Lỗi khi cập nhật sản phẩm " + currentProductMaSP + " sang NCC mới.", "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                            // Explicitly remove old link from NhaCungCap_SanPham if it exists for this
                            // product and oldMaNCC
                            // This is crucial because the product might have been linked via
                            // NhaCungCap_SanPham
                            // in addition to being a primary product.
                            if (nccspBUS.getNhaCungCapBySanPham(currentProductMaSP).stream()
                                    .anyMatch(link -> link.getMaNhaCungCap().equals(maNCC))) {
                                nccspBUS.xoaNhaCungCap_SanPham(maNCC, currentProductMaSP);
                            }
                        }
                        if (!allProductsUpdated)
                            return; // Stop if product update fails
                        // Clear primarySuppliedProducts as they are now handled
                        primarySuppliedProducts.clear();
                    } else {
                        return;
                        /* User cancelled new supplier selection for SanPham */ }
                } else {
                    return;
                    /* User cancelled SanPham handling */ }
            } // End SanPham (primary) check

            // // 3. Handle NhaCungCap_SanPham (many-to-many) links
            // // Re-fetch manyToManyLinks in case primary product updates also affected
            // these
            // manyToManyLinks = nccspBUS.getSanPhamByNhaCungCap(maNCC);
            // if (manyToManyLinks != null && !manyToManyLinks.isEmpty()) {
            // String[] linkOptions = { "Xóa các liên kết và xóa NCC", "Cập nhật liên kết
            // sang NCC khác", "Hủy" };
            // int linkOption = JOptionPane.showOptionDialog(this,
            // "Nhà cung cấp " + tenNCC + " có " + manyToManyLinks.size()
            // + " liên kết cung cấp sản phẩm (không phải NCC chính).\n" +
            // "Bạn muốn xử lý như thế nào?",
            // "Xử lý liên kết sản phẩm (NhaCungCap_SanPham)",
            // JOptionPane.DEFAULT_OPTION,
            // JOptionPane.QUESTION_MESSAGE,
            // null,
            // linkOptions,
            // linkOptions[2]);

            // if (linkOption == 0) { // Xóa các liên kết và xóa NCC
            // for (NhaCungCap_SanPhamDTO link : manyToManyLinks) {
            // nccspBUS.xoaNhaCungCap_SanPham(maNCC, link.getMaSanPham());
            // }
            // } else if (linkOption == 1) { // Cập nhật liên kết sang NCC khác
            // List<nhaCungCapDTO> otherSuppliers = nhaCungCapBUS.getAllNhaCungCap();
            // otherSuppliers.removeIf(nccDto -> nccDto.getMaNhaCungCap().equals(maNCC));
            // if (otherSuppliers.isEmpty()) {
            // JOptionPane.showMessageDialog(this,
            // "Không có nhà cung cấp khác để chuyển liên kết sản phẩm sang!", "Không thể
            // cập nhật",
            // JOptionPane.WARNING_MESSAGE);
            // return;
            // }
            // JComboBox<String> supplierCombo = new JComboBox<>();
            // for (nhaCungCapDTO nccDto : otherSuppliers) {
            // supplierCombo.addItem(nccDto.getMaNhaCungCap() + " - " +
            // nccDto.getTenNhaCungCap());
            // }
            // int result = JOptionPane.showConfirmDialog(this, supplierCombo,
            // "Chọn nhà cung cấp mới cho các liên kết sản phẩm",
            // JOptionPane.OK_CANCEL_OPTION);
            // if (result == JOptionPane.OK_OPTION) {
            // String selectedLinkSupplierItem = (String) supplierCombo.getSelectedItem();
            // String newLinkMaNCC = selectedLinkSupplierItem.split(" - ")[0];
            // boolean allLinksUpdated = true;
            // for (NhaCungCap_SanPhamDTO link : manyToManyLinks) {
            // nccspBUS.xoaNhaCungCap_SanPham(maNCC, link.getMaSanPham());
            // NhaCungCap_SanPhamDTO newLink = new NhaCungCap_SanPhamDTO(newLinkMaNCC,
            // link.getMaSanPham());
            // if (!nccspBUS.themNhaCungCap_SanPham(newLink)) {
            // allLinksUpdated = false;
            // JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật liên kết cho sản phẩm "
            // + link.getMaSanPham() + " sang NCC mới.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            // break;
            // }
            // }
            // if (!allLinksUpdated)
            // return; // Stop if link update fails
            // } else {
            // return;
            // /* User cancelled new supplier selection for links */ }
            // } else {
            // return;
            // /* User cancelled link handling */ }
            // } // End NhaCungCap_SanPham check

            // 4. Final Deletion Attempt (all known dependencies should be handled by now)
            if (nhaCungCapBUS.xoaNhaCungCap(maNCC)) {
                JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp " + tenNCC + " thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Xóa nhà cung cấp " + tenNCC
                                + " thất bại! Kiểm tra lại các ràng buộc dữ liệu hoặc liên hệ quản trị viên.",
                        "Lỗi Xóa", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra trong quá trình xóa nhà cung cấp: " + e.getMessage(),
                    "Lỗi Hệ Thống", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void showChiTietNhaCungCapDialog(nhaCungCapDTO ncc) {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        chiTietNhaCungCapDialog dialog = new chiTietNhaCungCapDialog(parentFrame, ncc);
        dialog.setVisible(true);
    }

    private void setupTable() {
        // Thiết lập chiều rộng cho các cột
        nhaCungCapTable.getColumnModel().getColumn(0).setPreferredWidth(50); // STT
        nhaCungCapTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Mã NCC
        nhaCungCapTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Tên NCC
        nhaCungCapTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Loại SP
        nhaCungCapTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Năm hợp tác
        nhaCungCapTable.getColumnModel().getColumn(5).setPreferredWidth(150); // Địa chỉ
        nhaCungCapTable.getColumnModel().getColumn(6).setPreferredWidth(150); // Email
        nhaCungCapTable.getColumnModel().getColumn(7).setPreferredWidth(100); // Số điện thoại
        nhaCungCapTable.getColumnModel().getColumn(8).setPreferredWidth(100); // Trạng thái
        nhaCungCapTable.getColumnModel().getColumn(9).setPreferredWidth(80); // Chi tiết

        // Thiết lập căn giữa cho một số cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        nhaCungCapTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // STT
        nhaCungCapTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Mã NCC
        nhaCungCapTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Năm hợp tác
        nhaCungCapTable.getColumnModel().getColumn(7).setCellRenderer(centerRenderer); // Số điện thoại
        nhaCungCapTable.getColumnModel().getColumn(9).setCellRenderer(centerRenderer); // Chi tiết

        // Thiết lập màu xanh và gạch chân cho cột "Chi tiết"
        nhaCungCapTable.getColumnModel().getColumn(9).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setForeground(new Color(0, 0, 255)); // Màu xanh cho chữ
                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });

        // Thiết lập row height
        nhaCungCapTable.setRowHeight(25);

        // Tắt chế độ chọn dòng khi click
        nhaCungCapTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Thêm sự kiện click cho cột "Chi tiết"
        nhaCungCapTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = nhaCungCapTable.rowAtPoint(e.getPoint());
                int col = nhaCungCapTable.columnAtPoint(e.getPoint());

                int lastColumnIndex = nhaCungCapTable.getColumnCount() - 1;
                // Chỉ xử lý khi click vào cột "Chi tiết" (cột cuối cùng)
                if (row >= 0 && col == lastColumnIndex) {
                    showChiTietNhaCungCap(row);
                }
            }
        });

        // Thêm cursor hand khi di chuột qua cột Chi Tiết
        nhaCungCapTable.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int col = nhaCungCapTable.columnAtPoint(e.getPoint());
                if (col == nhaCungCapTable.getColumnCount() - 1) {
                    nhaCungCapTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    nhaCungCapTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

    private void showChiTietNhaCungCap(int row) {
        try {
            // Lấy các giá trị từ bảng và xử lý null
            String maNCC = getTableValueAsString(row, 1);
            String tenNCC = getTableValueAsString(row, 2);
            String loaiSP = getTableValueAsString(row, 3);
            String namHopTacStr = getTableValueAsString(row, 4);
            String diaChi = getTableValueAsString(row, 5);
            String email = getTableValueAsString(row, 6);
            String soDienThoai = getTableValueAsString(row, 7);
            String trangThai = getTableValueAsString(row, 8);

            // Chuyển đổi năm hợp tác từ chuỗi sang số
            int namHopTac = 0;
            if (namHopTacStr != null && !namHopTacStr.isEmpty() && !namHopTacStr.equals("Chưa cập nhật")) {
                try {
                    namHopTac = Integer.parseInt(namHopTacStr);
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi chuyển đổi năm hợp tác: " + e.getMessage());
                }
            }

            // Tạo đối tượng nhaCungCapDTO
            nhaCungCapDTO ncc = new nhaCungCapDTO(
                    maNCC,
                    tenNCC,
                    loaiSP,
                    namHopTac,
                    diaChi,
                    email,
                    soDienThoai,
                    trangThai);

            // Hiển thị dialog chi tiết
            Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
            chiTietNhaCungCapDialog dialog = new chiTietNhaCungCapDialog(parentFrame, ncc);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Có lỗi xảy ra khi hiển thị thông tin chi tiết: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Phương thức hỗ trợ để lấy giá trị từ bảng và xử lý null
    private String getTableValueAsString(int row, int column) {
        Object value = nhaCungCapTable.getValueAt(row, column);
        return value != null ? value.toString() : "";
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(nhaCungCapPanel.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(nhaCungCapPanel.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(nhaCungCapPanel.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(nhaCungCapPanel.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new nhaCungCapPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel containerPanel;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnExport;
    private javax.swing.JComboBox<String> cboTimKiem;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JPanel pnlBoxChinhSua;
    private javax.swing.JPanel pnlBoxTable;
    private javax.swing.JPanel pnlBoxTimKiem;
    private javax.swing.JScrollPane scTable;
    private javax.swing.JTable nhaCungCapTable;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlContent1;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnPrinter;
    // End of variables declaration//GEN-END:variables
}
