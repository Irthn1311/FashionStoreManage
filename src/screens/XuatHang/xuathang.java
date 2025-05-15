package screens.XuatHang;

import javax.swing.UIManager;
import javax.swing.JOptionPane;
import DAO.SanPhamDAO;
import java.util.List;
import screens.TrangChu.AppColors;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.table.JTableHeader;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import java.io.File;

/**
 *
 * @author nson9
 */
public class xuathang extends javax.swing.JPanel {

    // Variables for jPanel5 - KhachHang
    private javax.swing.JLabel lblMaKhachHang;
    private javax.swing.JLabel lblTenKhachHang;

    /**
     * Creates new form hoadon
     */
    public xuathang() {
        initComponents();
        jTextField7.setEditable(false); // Đơn giá không cho chỉnh sửa
        jTextField9.setEditable(false); // Thành tiền không cho chỉnh sửa
        jTextField6.setEditable(false); // Mã khách hàng không cho chỉnh sửa
        jTextField2.setEditable(false); // Tên khách hàng không cho chỉnh sửa
        loadComboBoxData();
        loadXuatHangTable();
        loadKhachHangComboBox();
        javax.swing.event.DocumentListener docListener = new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                calculateThanhTien();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                calculateThanhTien();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                calculateThanhTien();
            }
        };
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int selectedRow = tblXuatHang.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(xuathang.this, "Vui lòng chọn một phiếu xuất để xóa.");
                    return;
                }
                String maPX = tblXuatHang.getValueAt(selectedRow, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(xuathang.this, "Bạn có chắc chắn muốn xóa phiếu xuất này?",
                        "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        java.sql.Connection conn = DTB.ConnectDB.getConnection();
                        String sql = "DELETE FROM XuatHang WHERE MaPX = ?";
                        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setString(1, maPX);
                        int result = ps.executeUpdate();
                        ps.close();
                        conn.close();
                        if (result > 0) {
                            JOptionPane.showMessageDialog(xuathang.this, "Xóa phiếu xuất thành công!");
                            loadXuatHangTable();
                        } else {
                            JOptionPane.showMessageDialog(xuathang.this, "Xóa phiếu xuất thất bại!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(xuathang.this, "Lỗi khi xóa phiếu xuất: " + e.getMessage());
                    }
                }
            }
        });
        jTextField10.getDocument().addDocumentListener(docListener);
        jTextField7.getDocument().addDocumentListener(docListener);
        cbMaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String maSP = (String) cbMaSanPham.getSelectedItem();
                if (maSP != null) {
                    DAO.SanPhamDAO dao = new DAO.SanPhamDAO();
                    DTO.sanPhamDTO sp = dao.getSanPhamByMa(maSP);
                    if (sp != null) {
                        jTextField1.setText(sp.getTenSanPham());
                        jTextField3.setText(sp.getMauSac());
                        jTextField4.setText(sp.getSize());
                        jTextField7.setText(String.valueOf(sp.getGiaBan()));
                        jTextField10.setText("1");
                        calculateThanhTien();
                    } else {
                        jTextField1.setText("");
                        jTextField3.setText("");
                        jTextField4.setText("");
                        jTextField7.setText("");
                        jTextField9.setText("");
                        jTextField10.setText("");
                    }
                }
            }
        });
        cbMaKhachHang.addActionListener(e -> {
            int idx = cbMaKhachHang.getSelectedIndex();
            if (idx >= 0 && idx < listKhachHang.size()) {
                if (cbTenKhachHang.getItemCount() > idx) {
                    cbTenKhachHang.setSelectedIndex(idx);
                }
            } else {
                if (cbTenKhachHang.getItemCount() > 0) {
                    cbTenKhachHang.setSelectedIndex(-1);
                }
            }
        });
        cbTenKhachHang.addActionListener(e -> {
            int idx = cbTenKhachHang.getSelectedIndex();
            if (idx >= 0 && idx < listKhachHang.size()){
                if(cbMaKhachHang.getItemCount() > idx){
                    cbMaKhachHang.setSelectedIndex(idx);
                }
            }
        });
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                java.sql.Connection conn = null; 
                try {
                    conn = DTB.ConnectDB.getConnection();
                    conn.setAutoCommit(false); 

                    if (cbMaKhachHang.getSelectedItem() == null) {
                        JOptionPane.showMessageDialog(xuathang.this, "Vui lòng chọn khách hàng.", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                        if (conn != null) conn.rollback();
                        return;
                    }
                    String currentMaKhachHang = cbMaKhachHang.getSelectedItem().toString();

                    String sqlSelectXuatHang = "SELECT * FROM XuatHang WHERE TrangThai = N'Đang xử lý' AND MaKhachHang = ?";
                    java.sql.PreparedStatement psSelect = conn.prepareStatement(sqlSelectXuatHang);
                    psSelect.setString(1, currentMaKhachHang);
                    java.sql.ResultSet rsXuatHang = psSelect.executeQuery();

                    List<DTO.xuatHangDTO> xuatHangListProcessing = new java.util.ArrayList<>();
                    while (rsXuatHang.next()) {
                        DTO.xuatHangDTO xh = new DTO.xuatHangDTO();
                        xh.setMaPX(rsXuatHang.getString("MaPX"));
                        xh.setMaKhachHang(rsXuatHang.getString("MaKhachHang"));
                        xh.setHoTen(rsXuatHang.getString("HoTen"));
                        xh.setMaSanPham(rsXuatHang.getString("MaSanPham"));
                        xh.setTenSanPham(rsXuatHang.getString("TenSanPham"));
                        xh.setKichThuoc(rsXuatHang.getString("KichThuoc"));
                        xh.setMauSac(rsXuatHang.getString("MauSac"));
                        xh.setSoLuong(String.valueOf(rsXuatHang.getInt("SoLuong")));
                        xh.setDonGia(String.valueOf(rsXuatHang.getDouble("DonGia")));
                        xh.setThanhTien(String.valueOf(rsXuatHang.getDouble("ThanhTien")));
                        xh.setTrangThai(rsXuatHang.getString("TrangThai"));
                        xuatHangListProcessing.add(xh);
                    }
                    rsXuatHang.close();
                    psSelect.close();

                    if (xuatHangListProcessing.isEmpty()) {
                        JOptionPane.showMessageDialog(xuathang.this, "Không có sản phẩm nào trong phiếu xuất để xác nhận.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        if (conn != null) conn.rollback(); 
                        return;
                    }

                    BUS.KhachHangBUS khachHangBUS_service = new BUS.KhachHangBUS();
                    DTO.khachHangDTO selectedKhachHang = khachHangBUS_service.getKhachHangByMa(currentMaKhachHang);

                    if (selectedKhachHang == null) {
                        JOptionPane.showMessageDialog(xuathang.this, "Không tìm thấy thông tin khách hàng: " + currentMaKhachHang, "Lỗi", JOptionPane.ERROR_MESSAGE);
                        if (conn != null) conn.rollback();
                        return;
                    }

                    JCheckBox chkExportPdf = new JCheckBox("Xuất hóa đơn PDF?");
                    JCheckBox chkSendEmail = new JCheckBox("Gửi hóa đơn đến mail?");
                    JCheckBox chkPrintInvoice = new JCheckBox("In hóa đơn trực tiếp?");

                    if (selectedKhachHang.getEmail() == null || selectedKhachHang.getEmail().trim().isEmpty()) {
                        chkSendEmail.setEnabled(false);
                        chkSendEmail.setToolTipText("Khách hàng này chưa có thông tin email.");
                    }

                    JPanel optionPanel = new JPanel(new java.awt.GridLayout(0, 1));
                    optionPanel.add(chkExportPdf);
                    optionPanel.add(chkSendEmail);
                    optionPanel.add(chkPrintInvoice);

                    int dialogResult = JOptionPane.showConfirmDialog(
                        xuathang.this, 
                        optionPanel, 
                        "Xác nhận xuất hàng & Tùy chọn", 
                        JOptionPane.OK_CANCEL_OPTION, 
                        JOptionPane.PLAIN_MESSAGE
                    );

                    if (dialogResult == JOptionPane.OK_OPTION) {
                        boolean pdfSelected = chkExportPdf.isSelected();
                        boolean emailSelected = chkSendEmail.isSelected() && chkSendEmail.isEnabled();
                        boolean printSelected = chkPrintInvoice.isSelected();

                        File generatedPdfFile = null;
                        boolean allAuxiliaryTasksSucceeded = true;

                        if (pdfSelected) {
                            JFileChooser fileChooser = new JFileChooser();
                            fileChooser.setDialogTitle("Lưu hóa đơn PDF");
                            String defaultFileName = "HoaDon_" + selectedKhachHang.getMaKhachHang() + "_" + System.currentTimeMillis() + ".pdf";
                            fileChooser.setSelectedFile(new File(defaultFileName));
                            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF Documents", "pdf"));
                            
                            int userSelection = fileChooser.showSaveDialog(xuathang.this);
                            if (userSelection == JFileChooser.APPROVE_OPTION) {
                                generatedPdfFile = fileChooser.getSelectedFile();
                                boolean pdfGenSuccess = utils.PdfService.generateInvoicePdf(xuatHangListProcessing, selectedKhachHang, generatedPdfFile.getAbsolutePath());
                                if (pdfGenSuccess) {
                                    JOptionPane.showMessageDialog(xuathang.this, "Xuất hóa đơn PDF thành công!\nĐã lưu tại: " + generatedPdfFile.getAbsolutePath(), "Thành công PDF", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(xuathang.this, "Xuất hóa đơn PDF thất bại!", "Lỗi PDF", JOptionPane.ERROR_MESSAGE);
                                    allAuxiliaryTasksSucceeded = false;
                                }
                            } else {
                                pdfSelected = false;
                            }
                        }

                        if (emailSelected && allAuxiliaryTasksSucceeded) {
                            boolean emailSendSuccess = utils.EmailService.sendInvoiceByEmail(selectedKhachHang, xuatHangListProcessing, pdfSelected ? generatedPdfFile : null);
                            if (emailSendSuccess) {
                                JOptionPane.showMessageDialog(xuathang.this, "Gửi email hóa đơn thành công đến " + selectedKhachHang.getEmail(), "Thành công Email", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(xuathang.this, "Gửi email hóa đơn thất bại!\nVui lòng kiểm tra cấu hình và kết nối.", "Lỗi Email", JOptionPane.ERROR_MESSAGE);
                                allAuxiliaryTasksSucceeded = false;
                            }
                        }
                        
                        if (printSelected && allAuxiliaryTasksSucceeded) {
                            boolean printOpSuccess = utils.PrintService.printInvoice(xuatHangListProcessing, selectedKhachHang);
                            if (printOpSuccess) {
                            } else {
                                allAuxiliaryTasksSucceeded = false;
                            }
                        }

                        if (allAuxiliaryTasksSucceeded) {
                            BUS.HoaDonBUS hoaDonBUS_manager = new BUS.HoaDonBUS();
                            DAO.SanPhamDAO sanPhamDAO_manager = new DAO.SanPhamDAO();
                            String hinhThucTT = cbHinhThucThanhToan.getSelectedItem() != null ? cbHinhThucThanhToan.getSelectedItem().toString() : "Tiền Mặt";
                            int savedHoaDonCount = 0;

                            // Determine the series number for this batch of invoices ONCE
                            int currentSeriesNumber = hoaDonBUS_manager.getNewMaHoaDonSeries();
                            String seriesStr = String.format("%05d", currentSeriesNumber);

                            for (DTO.xuatHangDTO xhItem : xuatHangListProcessing) {
                                DTO.hoaDonDTO hd = new DTO.hoaDonDTO();
                                
                                String maHoaDonMoi;
                                String randomPart;
                                do {
                                    // Generate a 5-digit random number from nanoTime, ensuring it's positive
                                    long nanoTimeRandom = System.nanoTime();
                                    randomPart = String.format("%05d", Math.abs(nanoTimeRandom % 100000));
                                    maHoaDonMoi = "HD" + seriesStr + "_" + randomPart;
                                } while(hoaDonBUS_manager.isMaHoaDonExists(maHoaDonMoi));
                                
                                hd.setMaHoaDon(maHoaDonMoi);
                                hd.setMaSanPham(xhItem.getMaSanPham());
                                hd.setTenSanPham(xhItem.getTenSanPham());
                                hd.setKichCo(xhItem.getKichThuoc());
                                hd.setMauSac(xhItem.getMauSac());
                                hd.setSoLuong(Integer.parseInt(xhItem.getSoLuong()));
                                hd.setMaKhachHang(xhItem.getMaKhachHang());
                                hd.setTenKhachHang(selectedKhachHang.getHoTen()); 
                                hd.setThanhTien(Double.parseDouble(xhItem.getThanhTien()));
                                hd.setDonGia(Double.parseDouble(xhItem.getDonGia()));
                                hd.setHinhThucThanhToan(hinhThucTT);
                                hd.setThoiGian(new java.sql.Timestamp(System.currentTimeMillis())); // This will be the same for all items in this exact millisecond, but MaHoaDon is unique
                                hd.setTrangThai("Hoàn thành");
                                
                                boolean addHoaDonSuccess = hoaDonBUS_manager.addHoaDon(hd);
                                if (addHoaDonSuccess) {
                                    sanPhamDAO_manager.giamSoLuongTonKho(xhItem.getMaSanPham(), Integer.parseInt(xhItem.getSoLuong()));
                                    savedHoaDonCount++;
                                } else {
                                    conn.rollback(); 
                                    JOptionPane.showMessageDialog(xuathang.this, "Lỗi khi lưu hóa đơn cho SP: " + xhItem.getTenSanPham() + ". Giao dịch hủy.", "Lỗi Lưu Hóa Đơn", JOptionPane.ERROR_MESSAGE);
                                    return; 
                                }
                            }

                            String deleteSql = "DELETE FROM XuatHang WHERE TrangThai = N'Đang xử lý' AND MaKhachHang = ?";
                            java.sql.PreparedStatement psDelete = conn.prepareStatement(deleteSql);
                            psDelete.setString(1, currentMaKhachHang);
                            int deletedRows = psDelete.executeUpdate();
                            psDelete.close();
                            
                            conn.commit(); 
                            loadXuatHangTable(); 
                            JOptionPane.showMessageDialog(xuathang.this, "Hoàn tất xử lý "+ xuatHangListProcessing.size() +" mục xuất hàng và lưu "+ savedHoaDonCount +" hóa đơn!","Hoàn thành", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(xuathang.this, "Do có lỗi/hủy trong quá trình xử lý tùy chọn (PDF/Email/In), các thay đổi chưa được lưu vào CSDL.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                            if (conn != null) conn.rollback();
                        }

                    } else {
                        JOptionPane.showMessageDialog(xuathang.this, "Đã hủy thao tác xuất hàng.", "Thông báo hủy", JOptionPane.INFORMATION_MESSAGE);
                        if (conn != null) conn.rollback(); 
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(xuathang.this, "Lỗi hệ thống khi xử lý xuất hàng: " + e.getMessage(), "Lỗi Hệ Thống", JOptionPane.ERROR_MESSAGE);
                    if (conn != null) {
                        try { conn.rollback(); } catch (java.sql.SQLException se) { se.printStackTrace(); }
                    }
                } finally {
                    if (conn != null) {
                        try { 
                            conn.setAutoCommit(true); 
                            conn.close(); 
                        } catch (java.sql.SQLException se) { se.printStackTrace(); }
                    }
                }
            }
        });
    }

    public javax.swing.JPanel getXuatHangPanel() {
        return containerPanel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        containerPanel = new javax.swing.JPanel();
        containerPanel.setPreferredSize(new java.awt.Dimension(1000, 700));
        containerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        containerPanel.setBackground(AppColors.NEW_MAIN_BG_COLOR);

        pnlHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        btnXacNhan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblXuatHang = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jTextField6 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        pblBoxSanPhamSoLuong = new javax.swing.JPanel();
        cbMaSanPham = new javax.swing.JComboBox<>();
        jTextField7 = new javax.swing.JTextField();
        lblMaSanPham = new javax.swing.JLabel();
        lblTenSanPham = new javax.swing.JLabel();
        lblMauSac = new javax.swing.JLabel();
        lblKichThuoc = new javax.swing.JLabel();
        lblSoLuong = new javax.swing.JLabel();
        lblDonGia = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        lblHinhThucThanhToan = new javax.swing.JLabel();
        cbHinhThucThanhToan = new javax.swing.JComboBox<>();
        cbMaKhachHang = new javax.swing.JComboBox<>();
        cbTenKhachHang = new javax.swing.JComboBox<>();

        lblTongTien = new javax.swing.JLabel();
        textTongTien = new javax.swing.JTextField();

        pnlHeader.setBackground(AppColors.NEW_HEADER_PANEL_BG_COLOR);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("QUẢN LÝ XUẤT HÀNG");
        jLabel1.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                                .addContainerGap(416, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(391, 391, 391)));
        pnlHeaderLayout.setVerticalGroup(
                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlHeaderLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(14, Short.MAX_VALUE)));

        containerPanel.add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 70));

        pnlContent.setBackground(AppColors.NEW_MAIN_BG_COLOR);

        btnXacNhan.setText("Xác nhận");
        btnXacNhan.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.setBorder(BorderFactory.createLineBorder(AppColors.NEW_BORDER_LINES_COLOR));
        btnXacNhan.setPreferredSize(new java.awt.Dimension(140, 40));

        tblXuatHang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblXuatHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Mã PX", "Mã KH", "Tên KH", "Mã SP", "Tên SP", "Kích thước", "Màu sắc", "Số lượng", "Đơn giá", "Thành tiền", "Hình thức thanh toán", "Trạng thái"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
            }
        });
        tblXuatHang.setShowGrid(true);
        tblXuatHang.setBackground(Color.WHITE);
        tblXuatHang.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        tblXuatHang.setGridColor(AppColors.NEW_BORDER_LINES_COLOR);
        JTableHeader tableHeader = tblXuatHang.getTableHeader();
        tableHeader.setBackground(AppColors.NEW_HEADER_PANEL_BG_COLOR);
        tableHeader.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        tableHeader.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        jScrollPane1.setViewportView(tblXuatHang);

        jPanel7.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        javax.swing.border.Border lineBorder7 = BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR);
        jPanel7.setBorder(BorderFactory.createTitledBorder(lineBorder7, "Xuất hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12), AppColors.NEW_MAIN_TEXT_COLOR));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        javax.swing.border.Border lineBorder5 = BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR);
        jPanel5.setBorder(BorderFactory.createTitledBorder(lineBorder5, "Khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12), AppColors.NEW_MAIN_TEXT_COLOR));
        
        lblMaKhachHang = new javax.swing.JLabel("Mã Khách Hàng");
        lblMaKhachHang.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        lblTenKhachHang = new javax.swing.JLabel("Tên Khách Hàng");
        lblTenKhachHang.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);

        cbMaKhachHang.setBackground(Color.WHITE);
        cbMaKhachHang.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        cbTenKhachHang.setBackground(Color.WHITE);
        cbTenKhachHang.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        
        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMaKhachHang)
                    .addComponent(lblTenKhachHang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbMaKhachHang, 0, 180, Short.MAX_VALUE)
                    .addComponent(cbTenKhachHang, 0, 180, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaKhachHang)
                    .addComponent(cbMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenKhachHang)
                    .addComponent(cbTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 170, 320, 120));

        jPanel9.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        javax.swing.border.Border lineBorder9 = BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR);
        jPanel9.setBorder(BorderFactory.createTitledBorder(lineBorder9, "Chi Phí", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12), AppColors.NEW_MAIN_TEXT_COLOR));
        jTextField8.setText("Thành Tiền");
        jTextField8.setEditable(false);
        jTextField8.setFocusable(false);
        jTextField8.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        jTextField8.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        jTextField8.setBorder(BorderFactory.createEmptyBorder(2,5,2,5));
        jTextField9.setEditable(false);
        jTextField9.setFocusable(false);
        jTextField9.setBackground(Color.WHITE);
        jTextField9.setBorder(BorderFactory.createLineBorder(AppColors.NEW_BORDER_LINES_COLOR));
        jTextField9.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField9)
                    .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField9, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 30, 320, 120));

        jButton15.setText("Hủy");
        jButton15.setBackground(AppColors.NEW_QUICK_ACCESS_BUTTON_BG_COLOR);
        jButton15.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR);
        jButton15.setBorder(BorderFactory.createLineBorder(AppColors.NEW_BORDER_LINES_COLOR));
        jButton15.setPreferredSize(new java.awt.Dimension(120, 50));
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMaSanPham.setSelectedIndex(0);
                jTextField1.setText("");
                jTextField3.setText("");
                jTextField4.setText("");
                jTextField10.setText("1");
                jTextField7.setText("");
                jTextField9.setText("0.00");
                if (cbMaKhachHang.getItemCount() > 0) {
                    cbMaKhachHang.setSelectedIndex(0);
                    if (cbTenKhachHang.getItemCount() > 0) cbTenKhachHang.setSelectedIndex(-1);
                } else {
                    if (cbTenKhachHang.getItemCount() > 0) cbTenKhachHang.setSelectedIndex(-1);
                }
                cbHinhThucThanhToan.setSelectedIndex(0);
            }
        });
        jPanel7.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 205, 120, 50));

        jButton18.setText("Thêm");
        jButton18.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        jButton18.setForeground(Color.WHITE);
        jButton18.setBorder(BorderFactory.createLineBorder(AppColors.NEW_BORDER_LINES_COLOR));
        jButton18.setPreferredSize(new java.awt.Dimension(120, 50));
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    if (cbMaSanPham.getSelectedItem() == null || jTextField10.getText().trim().isEmpty() || cbMaKhachHang.getSelectedItem() == null) {
                        JOptionPane.showMessageDialog(xuathang.this, "Vui lòng điền đầy đủ thông tin Mã SP, Số Lượng và Khách Hàng!");
                        return;
                    }

                    String maSP = cbMaSanPham.getSelectedItem().toString();
                    int soLuong = Integer.parseInt(jTextField10.getText().trim());
                    if (soLuong <= 0) {
                        JOptionPane.showMessageDialog(xuathang.this, "Số lượng phải lớn hơn 0!");
                        return;
                    }

                    SanPhamDAO sanPhamDAO = new SanPhamDAO();
                    if (!sanPhamDAO.kiemTraTonKho(maSP, soLuong)) {
                        JOptionPane.showMessageDialog(xuathang.this, "Không đủ số lượng sản phẩm trong kho!");
                        return;
                    }

                    BUS.XuatHangBUS xuatHangBUS = new BUS.XuatHangBUS();
                    String maPX = xuatHangBUS.generateNextMaPX();
                    String maKH = cbMaKhachHang.getSelectedItem().toString();
                    String tenKH = cbTenKhachHang.getSelectedItem().toString();
                    String tenSP = jTextField1.getText();
                    String kichThuoc = jTextField4.getText();
                    String mauSac = jTextField3.getText();
                    double donGia = Double.parseDouble(jTextField7.getText());
                    double thanhTien = Double.parseDouble(jTextField9.getText());
                    String hinhThucTT = cbHinhThucThanhToan.getSelectedItem().toString();

                    java.sql.Connection conn = DTB.ConnectDB.getConnection();
                    String sql = "INSERT INTO XuatHang (MaPX, MaKhachHang, HoTen, MaSanPham, TenSanPham, KichThuoc, MauSac, SoLuong, DonGia, ThanhTien, HinhThucThanhToan, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, N'Đang xử lý')";
                    java.sql.PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, maPX);
                    ps.setString(2, maKH);
                    ps.setString(3, tenKH);
                    ps.setString(4, maSP);
                    ps.setString(5, tenSP);
                    ps.setString(6, kichThuoc);
                    ps.setString(7, mauSac);
                    ps.setInt(8, soLuong);
                    ps.setDouble(9, donGia);
                    ps.setDouble(10, thanhTien);
                    ps.setString(11, hinhThucTT);
                    
                    int result = ps.executeUpdate();
                    ps.close();
                    conn.close();

                    if (result > 0) {
                        JOptionPane.showMessageDialog(xuathang.this, "Thêm sản phẩm vào phiếu xuất thành công!");
                        loadXuatHangTable();
                        cbMaSanPham.setSelectedIndex(0);
                        jTextField10.setText("1");
                        jTextField9.setText("0.00");
                    } else {
                        JOptionPane.showMessageDialog(xuathang.this, "Thêm sản phẩm vào phiếu xuất thất bại!");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(xuathang.this, "Số lượng hoặc đơn giá không hợp lệ!");
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(xuathang.this, "Lỗi khi thêm sản phẩm vào phiếu xuất: " + e.getMessage());
                }
            }
        });
        jPanel7.add(jButton18, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 65, 120, 50));

        pblBoxSanPhamSoLuong.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        javax.swing.border.Border lineBorderSP = BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR);
        pblBoxSanPhamSoLuong.setBorder(BorderFactory.createTitledBorder(lineBorderSP, "Sản phẩm & Số lượng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12), AppColors.NEW_MAIN_TEXT_COLOR));
        
        lblMaSanPham.setText("Mã Sản Phẩm");
        lblMaSanPham.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        cbMaSanPham.setBackground(Color.WHITE);
        cbMaSanPham.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        cbMaSanPham.setBorder(BorderFactory.createLineBorder(AppColors.NEW_BORDER_LINES_COLOR));

        lblTenSanPham.setText("Tên Sản Phẩm");
        lblTenSanPham.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        jTextField1.setBackground(Color.WHITE);
        jTextField1.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        jTextField1.setBorder(BorderFactory.createLineBorder(AppColors.NEW_BORDER_LINES_COLOR));
        jTextField1.setEditable(false);

        lblMauSac.setText("Màu Sắc");
        lblMauSac.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        jTextField3.setBackground(Color.WHITE);
        jTextField3.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        jTextField3.setBorder(BorderFactory.createLineBorder(AppColors.NEW_BORDER_LINES_COLOR));
        jTextField3.setEditable(false);

        lblKichThuoc.setText("Kích Thước");
        lblKichThuoc.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        jTextField4.setBackground(Color.WHITE);
        jTextField4.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        jTextField4.setBorder(BorderFactory.createLineBorder(AppColors.NEW_BORDER_LINES_COLOR));
        jTextField4.setEditable(false);
        
        lblSoLuong.setText("Số Lượng");
        lblSoLuong.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        jTextField10.setBackground(Color.WHITE);
        jTextField10.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        jTextField10.setBorder(BorderFactory.createLineBorder(AppColors.NEW_BORDER_LINES_COLOR));

        lblDonGia.setText("Đơn Giá");
        lblDonGia.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        jTextField7.setEditable(false);
        jTextField7.setBackground(Color.WHITE);
        jTextField7.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        jTextField7.setBorder(BorderFactory.createLineBorder(AppColors.NEW_BORDER_LINES_COLOR));
        
        lblHinhThucThanhToan.setText("Hình Thức Thanh Toán");
        lblHinhThucThanhToan.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        cbHinhThucThanhToan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền Mặt", "Chuyển Khoản", "Thẻ" }));
        cbHinhThucThanhToan.setBackground(Color.WHITE);
        cbHinhThucThanhToan.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        cbHinhThucThanhToan.setBorder(BorderFactory.createLineBorder(AppColors.NEW_BORDER_LINES_COLOR));

        javax.swing.GroupLayout pblBoxSanPhamSoLuongLayout = new javax.swing.GroupLayout(pblBoxSanPhamSoLuong);
        pblBoxSanPhamSoLuong.setLayout(pblBoxSanPhamSoLuongLayout);
        pblBoxSanPhamSoLuongLayout.setHorizontalGroup(
            pblBoxSanPhamSoLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pblBoxSanPhamSoLuongLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pblBoxSanPhamSoLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMaSanPham)
                    .addComponent(lblTenSanPham)
                    .addComponent(lblMauSac)
                    .addComponent(lblKichThuoc)
                    .addComponent(lblSoLuong)
                    .addComponent(lblDonGia)
                    .addComponent(lblHinhThucThanhToan))
                .addGap(50, 50, 50)
                .addGroup(pblBoxSanPhamSoLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbMaSanPham, 0, 172, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addComponent(jTextField3)
                    .addComponent(jTextField4)
                    .addComponent(jTextField10)
                    .addComponent(jTextField7)
                    .addComponent(cbHinhThucThanhToan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        pblBoxSanPhamSoLuongLayout.setVerticalGroup(
            pblBoxSanPhamSoLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pblBoxSanPhamSoLuongLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pblBoxSanPhamSoLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaSanPham)
                    .addComponent(cbMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pblBoxSanPhamSoLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenSanPham)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pblBoxSanPhamSoLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMauSac)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pblBoxSanPhamSoLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKichThuoc)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(pblBoxSanPhamSoLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSoLuong)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pblBoxSanPhamSoLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDonGia)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pblBoxSanPhamSoLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHinhThucThanhToan)
                    .addComponent(cbHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9))
        );
        jPanel7.add(pblBoxSanPhamSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 440, 280));

        jButton1.setText("Xóa");
        jButton1.setBackground(AppColors.NEW_QUICK_ACCESS_BUTTON_BG_COLOR);
        jButton1.setForeground(AppColors.NEW_QUICK_ACCESS_BUTTON_TEXT_COLOR);
        jButton1.setBorder(BorderFactory.createLineBorder(AppColors.NEW_BORDER_LINES_COLOR));
        jButton1.setPreferredSize(new java.awt.Dimension(140, 40));

        lblTongTien.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblTongTien.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        lblTongTien.setText("Tổng tiền:");

        textTongTien.setEditable(false);
        textTongTien.setBackground(java.awt.Color.WHITE); 
        textTongTien.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        textTongTien.setBorder(javax.swing.BorderFactory.createLineBorder(AppColors.NEW_BORDER_LINES_COLOR));
        textTongTien.setPreferredSize(new java.awt.Dimension(150, 40)); 
        textTongTien.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textTongTien.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        textTongTien.setText("0.00");

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 961, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(pnlContentLayout.createSequentialGroup() 
                .addGap(30, 30, 30) 
                .addComponent(lblTongTien)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) 
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28) 
                .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        containerPanel.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1000, 630));

        jPanel7.setPreferredSize(new java.awt.Dimension(960, 340));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(960, 192));

        setupUI();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String maSP = cbMaSanPham.getSelectedItem().toString();
            int soLuongXuat = Integer.parseInt(jTextField10.getText());

            SanPhamDAO sanPhamDAO = new SanPhamDAO();

            // Bắt buộc kiểm tra tồn kho trước khi xuất
            if (sanPhamDAO.kiemTraTonKho(maSP, soLuongXuat)) {
                // Nếu đủ hàng, giảm tồn kho
                boolean result = sanPhamDAO.giamSoLuongTonKho(maSP, soLuongXuat);

                if (result) {
                    // Kiểm tra cảnh báo tồn kho thấp sau khi xuất thành công
                    boolean canhBao = sanPhamDAO.kiemTraCanhBaoTonKho(maSP);
                    if (canhBao) {
                        JOptionPane.showMessageDialog(this, "Cảnh báo: Sản phẩm sắp hết hàng, hãy nhập thêm!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Xuất hàng thành công, tồn kho đã cập nhật.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Xuất hàng thất bại, lỗi cập nhật tồn kho.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không đủ tồn kho để xuất.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng hợp lệ.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    private void setupUI() {
        // Điều chỉnh kích thước các panel con trong pnlContent
        // These are now set directly in initComponents or through GroupLayout/AbsoluteLayout of jPanel7
        // jPanel7.setPreferredSize(new java.awt.Dimension(960, 317)); // Old value, new one is 340
        // jScrollPane1.setPreferredSize(new java.awt.Dimension(960, 190)); // Old value, new one is 192
    }

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField10ActionPerformed

    private void updateTongTien() {
        double tongTien = 0;
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblXuatHang.getModel();
        int thanhTienColIndex = -1;

        for (int col = 0; col < model.getColumnCount(); col++) {
            if (model.getColumnName(col).equals("Thành tiền")) {
                thanhTienColIndex = col;
                break; // Found the column, no need to continue looping
            }
        }

        if (thanhTienColIndex == -1) {
            System.err.println("updateTongTien: Column 'Thành tiền' not found by name.");
            textTongTien.setText("Error");
            return;
        }

        for (int i = 0; i < model.getRowCount(); i++) {
            Object thanhTienObj = model.getValueAt(i, thanhTienColIndex);
            if (thanhTienObj != null) {
                try {
                    if (thanhTienObj instanceof Number) {
                        tongTien += ((Number) thanhTienObj).doubleValue();
                    } else {
                        tongTien += Double.parseDouble(thanhTienObj.toString());
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Lỗi parse double ở hàng " + i + " cột 'Thành tiền': " + thanhTienObj);
                }
            }
        }
        textTongTien.setText(String.format("%.2f", tongTien));
    }

    private void loadComboBoxData() {
        SanPhamDAO sanPhamDAO = new SanPhamDAO();

        // Load product codes
        List<String> codes = sanPhamDAO.getAllProductCodes();
        cbMaSanPham.removeAllItems();
        for (String code : codes) {
            cbMaSanPham.addItem(code);
        }
        if (!codes.isEmpty()) cbMaSanPham.setSelectedIndex(0); // Select first item if available
    }

    private void calculateThanhTien() {
        try {
            int soLuong = Integer.parseInt(jTextField10.getText().trim());
            double donGia = Double.parseDouble(jTextField7.getText().trim());
            double thanhTien = soLuong * donGia;
            jTextField9.setText(String.format("%.2f", thanhTien)); // Format to 2 decimal places
        } catch (NumberFormatException e) {
            jTextField9.setText("0.00"); 
        }
    }

    private void loadXuatHangTable() {
        try {
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblXuatHang.getModel();
            model.setRowCount(0);

            java.sql.Connection conn = DTB.ConnectDB.getConnection();
            String sql = "SELECT * FROM XuatHang";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                Object[] row = new Object[] {
                    rs.getString("MaPX"),
                    rs.getString("MaKhachHang"),
                    rs.getString("HoTen"),
                    rs.getString("MaSanPham"),
                    rs.getString("TenSanPham"),
                    rs.getString("KichThuoc"),
                    rs.getString("MauSac"),
                    rs.getInt("SoLuong"),
                    rs.getDouble("DonGia"),
                    rs.getDouble("ThanhTien"),
                    rs.getString("HinhThucThanhToan"),
                    rs.getString("TrangThai")
                };
                model.addRow(row);
            }


            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu xuất hàng: " + e.getMessage());
        }
        updateTongTien(); // Call to update total amount after table is loaded
    }

    private void loadKhachHangComboBox() {
        BUS.KhachHangBUS khachHangBUS_loader = new BUS.KhachHangBUS(); // Đổi tên biến
        listKhachHang = khachHangBUS_loader.getAllKhachHang();
        cbMaKhachHang.removeAllItems();
        cbTenKhachHang.removeAllItems();

        for (DTO.khachHangDTO kh : listKhachHang) {
            cbMaKhachHang.addItem(kh.getMaKhachHang());
            cbTenKhachHang.addItem(kh.getHoTen());
        }

        if (!listKhachHang.isEmpty()) {
            cbMaKhachHang.setSelectedIndex(0);
            cbTenKhachHang.setSelectedIndex(0);
        } else {
            // jTextFieldTenKhachHang.setText(""); // Old
            // No items, so combo boxes will be empty
        }
        // listKhachHang is already assigned
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel containerPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JButton jButton18;
    private javax.swing.JComboBox<String> cbMaSanPham;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblMaSanPham;
    private javax.swing.JLabel lblTenSanPham;
    private javax.swing.JLabel lblMauSac;
    private javax.swing.JLabel lblKichThuoc;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblDonGia;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel pblBoxSanPhamSoLuong;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblXuatHang;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JLabel lblHinhThucThanhToan;
    private javax.swing.JComboBox<String> cbHinhThucThanhToan;
    private javax.swing.JComboBox<String> cbMaKhachHang;
    private javax.swing.JComboBox<String> cbTenKhachHang;
    private java.util.List<DTO.khachHangDTO> listKhachHang;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JTextField textTongTien;
    // End of variables declaration//GEN-END:variables
}