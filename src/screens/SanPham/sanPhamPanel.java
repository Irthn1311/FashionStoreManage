package screens.SanPham;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import DAO.SanPhamDAO;
import DTO.sanPhamDTO;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import BUS.SanPhamBUS;
import java.util.Vector;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import screens.TrangChu.AppColors;

public class sanPhamPanel extends javax.swing.JPanel {
    private SanPhamDAO sanPhamDAO;

    public sanPhamPanel() {
        sanPhamDAO = new SanPhamDAO();
        initComponents();
        loadSanPhamData();
        setupSearchFunction();
        setupTableSelection();
    }

    private void loadSanPhamData() {
        // Cập nhật trạng thái sản phẩm trước khi tải dữ liệu
        SanPhamBUS sanPhamBUS = new SanPhamBUS();
        sanPhamBUS.updateProductStatus();

        List<sanPhamDTO> sanPhamList = sanPhamDAO.getAllSanPham();
        populateTable(sanPhamList);
    }

    private void populateTable(List<sanPhamDTO> sanPhamList) {
        DefaultTableModel model = (DefaultTableModel) sanPhamTable.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        int stt = 1;
        for (sanPhamDTO sp : sanPhamList) {
            model.addRow(new Object[] {
                    stt++,
                    sp.getMaSanPham(),
                    sp.getTenSanPham(),
                    sp.getMaNhaCungCap(),
                    sp.getMaDanhMuc(),
                    sp.getMauSac(),
                    sp.getSize(),
                    sp.getSoLuongTonKho(),
                    sp.getGiaBan(),
                    sp.getImgURL(),
                    sp.getTrangThai()
            });
        }
    }

    private void setupSearchFunction() {
        // Thiết lập các lựa chọn cho combobox tìm kiếm
        jComboBox1.removeAllItems();
        jComboBox1.addItem("Tất cả");
        jComboBox1.addItem("Mã sản phẩm");
        jComboBox1.addItem("Tên sản phẩm");
        jComboBox1.addItem("Nhà cung cấp");
        jComboBox1.addItem("Danh mục");
        jComboBox1.addItem("Màu sắc");
        jComboBox1.addItem("Kích cỡ");
        jComboBox1.addItem("Trạng thái");

        // Xử lý sự kiện cho nút tìm kiếm
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchSanPham();
            }
        });
    }

    private void searchSanPham() {
        String loaiTimKiem = jComboBox1.getSelectedItem().toString();
        String tuKhoa = jTextField1.getText().trim();
        Double giaTu = null, giaDen = null;
        Integer slTu = null, slDen = null;

        // Get search results based on keyword and search type
        List<sanPhamDTO> ketQua = sanPhamDAO.searchSanPham(loaiTimKiem, tuKhoa, null, null, null, null);

        // Filter by price range
        try {
            if (!jTextFieldDonGiaTu.getText().trim().isEmpty()) {
                giaTu = Double.parseDouble(jTextFieldDonGiaTu.getText().trim());
                if (giaTu < 0)
                    throw new NumberFormatException();
            }
            if (!jTextFieldDonGiaDen.getText().trim().isEmpty()) {
                giaDen = Double.parseDouble(jTextFieldDonGiaDen.getText().trim());
                if (giaDen < 0)
                    throw new NumberFormatException();
            }
            if (giaTu != null && giaDen != null && giaTu > giaDen) {
                JOptionPane.showMessageDialog(this, "Giá từ không được lớn hơn giá đến!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            final Double priceFrom = giaTu;
            final Double priceTo = giaDen;
            ketQua = ketQua.stream()
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
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá phải là số hợp lệ (≥ 0)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Filter by quantity range
        try {
            if (!jTextFieldSoLuongTu.getText().trim().isEmpty()) {
                slTu = Integer.parseInt(jTextFieldSoLuongTu.getText().trim());
                if (slTu < 0)
                    throw new NumberFormatException();
            }
            if (!jTextFieldSoLuongDen.getText().trim().isEmpty()) {
                slDen = Integer.parseInt(jTextFieldSoLuongDen.getText().trim());
                if (slDen < 0)
                    throw new NumberFormatException();
            }
            if (slTu != null && slDen != null && slTu > slDen) {
                JOptionPane.showMessageDialog(this, "Số lượng từ không được lớn hơn số lượng đến!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            final Integer quantityFrom = slTu;
            final Integer quantityTo = slDen;
            ketQua = ketQua.stream()
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
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số hợp lệ (≥ 0)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // If no keyword and no filters, load all data
        if (tuKhoa.isEmpty() && giaTu == null && giaDen == null && slTu == null && slDen == null) {
            loadSanPhamData();
            return;
        }

        // Display results
        if (ketQua.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm phù hợp!", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        populateTable(ketQua);
    }

    private void setupTableSelection() {
        // Xử lý sự kiện khi chọn một dòng trong bảng
        sanPhamTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = sanPhamTable.getSelectedRow();
                if (selectedRow >= 0) {
                    displayProductDetails(selectedRow);
                }
            }
        });
    }

    private void displayProductDetails(int row) {
        String maSP = sanPhamTable.getValueAt(row, 1).toString();
        String tenSP = sanPhamTable.getValueAt(row, 2).toString();
        String maNCC = sanPhamTable.getValueAt(row, 3).toString();
        String maDM = sanPhamTable.getValueAt(row, 4).toString();
        String mauSac = sanPhamTable.getValueAt(row, 5).toString();
        String size = sanPhamTable.getValueAt(row, 6).toString();
        int soLuong = Integer.parseInt(sanPhamTable.getValueAt(row, 7).toString());
        double donGia = Double.parseDouble(sanPhamTable.getValueAt(row, 8).toString());
        String imgURL = sanPhamTable.getValueAt(row, 9).toString();
        String trangThai = sanPhamTable.getValueAt(row, 10).toString();

        // Hiển thị thông tin sản phẩm trong các trường
        lblMaSP.setText("Mã sản phẩm: " + maSP);
        lblTenSP.setText("Tên sản phẩm: " + tenSP);
        lblSoLuong.setText("Số lượng: " + soLuong);
        lblDonGia.setText("Đơn giá: " + donGia);
        lblImgURL.setText("Hình ảnh: " + imgURL);
        lblTrangThai.setText("Trạng thái: " + trangThai);
        lblMauSac.setText("Màu sắc: " + mauSac);
        lblSize.setText("Kích cỡ: " + size);

        // Kiểm tra và hiển thị thông tin khuyến mãi
        BUS.KhuyenMaiService khuyenMaiService = new BUS.KhuyenMaiService();
        DTO.khuyenMaiDTO khuyenMai = khuyenMaiService.getKhuyenMaiByMaSanPham(maSP);

        if (khuyenMai != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            String ngayBatDau = khuyenMai.getNgayBatDau() != null ? sdf.format(khuyenMai.getNgayBatDau()) : "";
            String ngayKetThuc = khuyenMai.getNgayKetThuc() != null ? sdf.format(khuyenMai.getNgayKetThuc()) : "";
            String khuyenMaiInfo = String.format(
                    "<b>Chương trình:</b> %s<br>" +
                            "Thời gian: %s - %s<br>" +
                            "Giá cũ: %.2f<br>" +
                            "(Giảm %.2f%%) - Giá mới: %.2f",
                    khuyenMai.getTenChuongTrinh(),
                    ngayBatDau,
                    ngayKetThuc,
                    khuyenMai.getGiaCu(),
                    khuyenMai.getGiamGia(),
                    khuyenMai.getGiaMoi());
            lblKhuyenMai.setText("<html><div style='width:500px;'>" + khuyenMaiInfo + "</div></html>");
        } else {
            lblKhuyenMai.setText("Khuyến mãi: Không có khuyến mãi");
        }

        // Hiển thị hình ảnh sản phẩm
        displayProductImage(imgURL);
    }

    private void displayProductImage(String imgURL) {
        try {
            // Thêm tiền tố "src/" để truy cập đúng thư mục img_product trong dự án
            String fullPath = "src/" + imgURL;
            // Kiểm tra đường dẫn ảnh có tồn tại không
            File file = new File(fullPath);
            if (file.exists()) {
                ImageIcon imageIcon = new ImageIcon(fullPath);
                Image image = imageIcon.getImage().getScaledInstance(258, 200, Image.SCALE_SMOOTH);
                lblImage.setIcon(new ImageIcon(image));
                lblImage.setText(""); // Xóa text để hiển thị ảnh
            } else {
                lblImage.setIcon(null);
                lblImage.setText("Không tìm thấy ảnh");
            }
        } catch (Exception e) {
            lblImage.setIcon(null);
            lblImage.setText("Lỗi hiển thị ảnh: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public javax.swing.JPanel getSanPhamPanel() {
        return containerPanel;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Khởi tạo panel chính
        containerPanel = new javax.swing.JPanel();
        containerPanel.setPreferredSize(new java.awt.Dimension(1000, 700));
        containerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        containerPanel.setBackground(AppColors.NEW_MAIN_BG_COLOR);

        // Khởi tạo các components
        pnlHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sanPhamTable = new javax.swing.JTable();

        // Panel chi tiết sản phẩm
        pnlDetail = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        lblMaSP = new javax.swing.JLabel();
        lblTenSP = new javax.swing.JLabel();
        lblSoLuong = new javax.swing.JLabel();
        lblDonGia = new javax.swing.JLabel();
        lblImgURL = new javax.swing.JLabel();
        lblTrangThai = new javax.swing.JLabel();
        lblMauSac = new javax.swing.JLabel();
        lblSize = new javax.swing.JLabel();
        lblKhuyenMai = new javax.swing.JLabel();

        // Panel tìm kiếm
        pnlSearch = new javax.swing.JPanel();
        jButton30 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jLabelDonGiaTu = new javax.swing.JLabel();
        jTextFieldDonGiaTu = new javax.swing.JTextField();
        jLabelDonGiaDen = new javax.swing.JLabel();
        jTextFieldDonGiaDen = new javax.swing.JTextField();
        jLabelSoLuongTu = new javax.swing.JLabel();
        jTextFieldSoLuongTu = new javax.swing.JTextField();
        jLabelSoLuongDen = new javax.swing.JLabel();
        jTextFieldSoLuongDen = new javax.swing.JTextField();

        containerPanel.setPreferredSize(new java.awt.Dimension(960, 680));
        containerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // Thiết lập header
        pnlHeader.setBackground(AppColors.NEW_HEADER_PANEL_BG_COLOR);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabel1.setText("THÔNG TIN SẢN PHẨM");
        jLabel1.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                                .addContainerGap(430, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(379, 379, 379)));
        pnlHeaderLayout.setVerticalGroup(
                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlHeaderLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(14, Short.MAX_VALUE)));

        containerPanel.add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 70));

        // Thiết lập phần nội dung
        pnlContent.setBackground(AppColors.NEW_MAIN_BG_COLOR);

        // Thiết lập bảng sản phẩm
        sanPhamTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] {
                        "STT", "Mã SP", "Tên SP", "Mã NCC", "Mã DM", "Màu sắc", "Kích cỡ", "Số Lượng", "Đơn Giá",
                        "Hình Ảnh", "Trạng Thái"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        // Thêm renderer tùy chỉnh cho bảng
        sanPhamTable.setBackground(Color.WHITE);
        sanPhamTable.getTableHeader().setBackground(AppColors.NEW_HEADER_PANEL_BG_COLOR);
        sanPhamTable.getTableHeader().setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        sanPhamTable.setGridColor(AppColors.NEW_BORDER_LINES_COLOR);

        sanPhamTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);
                c.setFont(new java.awt.Font("Segoe UI", 0, 12));

                // Lấy số lượng từ cột "Số Lượng" (cột 7)
                int soLuong = 0;
                try {
                    Object soLuongValue = table.getValueAt(row, 7);
                    if (soLuongValue != null) {
                        soLuong = Integer.parseInt(soLuongValue.toString());
                    }
                } catch (NumberFormatException e) {
                    // Nếu không thể chuyển đổi thành số, giữ nguyên màu mặc định
                    return c;
                }

                // Nếu số lượng <= 10, hiển thị màu đỏ
                if (soLuong <= 10) {
                    c.setBackground(java.awt.Color.RED);
                    c.setForeground(java.awt.Color.WHITE);
                } else {
                    if (isSelected) {
                        c.setBackground(AppColors.NEW_SELECTED_BUTTON_COLOR);
                        c.setForeground(Color.WHITE);
                    } else {
                        c.setBackground(Color.WHITE);
                        c.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
                    }
                }
                return c;
            }
        });

        sanPhamTable.setShowGrid(true);
        jScrollPane1.setViewportView(sanPhamTable);

        // Thiết lập panel chi tiết sản phẩm
        pnlDetail.setBackground(AppColors.NEW_QUICK_ACCESS_BUTTON_BG_COLOR);
        javax.swing.border.TitledBorder detailBorder = javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Chi tiết sản phẩm");
        detailBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlDetail.setBorder(detailBorder);
        pnlDetail.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImage.setText("Hình ảnh sản phẩm");
        lblImage.setBorder(javax.swing.BorderFactory.createLineBorder(AppColors.NEW_BORDER_LINES_COLOR));
        lblImage.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlDetail.add(lblImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 258, 220));

        lblMaSP.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblMaSP.setText("Mã sản phẩm: ");
        lblMaSP.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlDetail.add(lblMaSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, 250, 20));

        lblTenSP.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblTenSP.setText("Tên sản phẩm: ");
        lblTenSP.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlDetail.add(lblTenSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 45, 250, 20));

        lblSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblSoLuong.setText("Số lượng: ");
        lblSoLuong.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlDetail.add(lblSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 250, 20));

        lblDonGia.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblDonGia.setText("Đơn giá: ");
        lblDonGia.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlDetail.add(lblDonGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 95, 250, 20));

        lblKhuyenMai = new javax.swing.JLabel();
        lblKhuyenMai.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblKhuyenMai.setText("Khuyến mãi: ");
        lblKhuyenMai.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlDetail.add(lblKhuyenMai, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, 500, 80));

        lblImgURL.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblImgURL.setText("Hình ảnh: ");
        lblImgURL.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlDetail.add(lblImgURL, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, 350, 20));

        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblTrangThai.setText("Trạng thái: ");
        lblTrangThai.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlDetail.add(lblTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 45, 350, 20));

        lblMauSac.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblMauSac.setText("Màu sắc: ");
        lblMauSac.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlDetail.add(lblMauSac, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 70, 350, 20));

        lblSize.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblSize.setText("Kích cỡ: ");
        lblSize.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlDetail.add(lblSize, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 95, 350, 20));

        // Thiết lập panel tìm kiếm
        pnlSearch.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        javax.swing.border.TitledBorder searchBorder = javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(AppColors.NEW_HEADER_PANEL_BG_COLOR), "Tìm kiếm");
        searchBorder.setTitleColor(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlSearch.setBorder(searchBorder);
        pnlSearch.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // Search Label
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel2.setText("Tìm kiếm");
        jLabel2.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlSearch.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 30));

        // Search ComboBox
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
                "Tất cả", "Mã sản phẩm", "Tên sản phẩm", "Nhà cung cấp", "Danh mục", "Màu sắc", "Kích cỡ", "Trạng thái"
        }));
        pnlSearch.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 180, 30));

        // Search TextField
        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTextField1.setPreferredSize(new java.awt.Dimension(180, 30));
        pnlSearch.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, 180, 30));

        // Price Label
        JLabel donGiaLabel = new JLabel("Đơn giá:");
        donGiaLabel.setFont(new java.awt.Font("Segoe UI", 0, 14));
        donGiaLabel.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlSearch.add(donGiaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 80, 30));

        // Price range
        jLabelDonGiaTu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelDonGiaTu.setText("Từ:");
        jLabelDonGiaTu.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlSearch.add(jLabelDonGiaTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, -1, 30));

        jTextFieldDonGiaTu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTextFieldDonGiaTu.setPreferredSize(new java.awt.Dimension(80, 30));
        jTextFieldDonGiaTu.setToolTipText("Nhập giá bán từ (số dương)");
        pnlSearch.add(jTextFieldDonGiaTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, 80, 30));

        jLabelDonGiaDen.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelDonGiaDen.setText("Đến:");
        jLabelDonGiaDen.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlSearch.add(jLabelDonGiaDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 20, -1, 30));

        jTextFieldDonGiaDen.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTextFieldDonGiaDen.setPreferredSize(new java.awt.Dimension(80, 30));
        jTextFieldDonGiaDen.setToolTipText("Nhập giá bán đến (số dương)");
        pnlSearch.add(jTextFieldDonGiaDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, 80, 30));

        // Quantity Label
        JLabel soLuongLabel = new JLabel("Số lượng:");
        soLuongLabel.setFont(new java.awt.Font("Segoe UI", 0, 14));
        soLuongLabel.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlSearch.add(soLuongLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 60, 80, 30));

        // Quantity range
        jLabelSoLuongTu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelSoLuongTu.setText("Từ:");
        jLabelSoLuongTu.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlSearch.add(jLabelSoLuongTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, -1, 30));

        jTextFieldSoLuongTu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTextFieldSoLuongTu.setPreferredSize(new java.awt.Dimension(80, 30));
        jTextFieldSoLuongTu.setToolTipText("Nhập số lượng tồn kho từ (số dương)");
        pnlSearch.add(jTextFieldSoLuongTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, 80, 30));

        jLabelSoLuongDen.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelSoLuongDen.setText("Đến:");
        jLabelSoLuongDen.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        pnlSearch.add(jLabelSoLuongDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 60, -1, 30));

        jTextFieldSoLuongDen.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTextFieldSoLuongDen.setPreferredSize(new java.awt.Dimension(80, 30));
        jTextFieldSoLuongDen.setToolTipText("Nhập số lượng tồn kho đến (số dương)");
        pnlSearch.add(jTextFieldSoLuongDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 60, 80, 30));

        // Search Button
        jButton30.setText("Tìm kiếm");
        jButton30.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        jButton30.setForeground(Color.WHITE);
        ImageIcon searchIcon = new ImageIcon("src/icon_img/search.png");
        jButton30.setIcon(new ImageIcon(searchIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
        jButton30.setHorizontalTextPosition(SwingConstants.RIGHT);
        jButton30.setPreferredSize(new java.awt.Dimension(120, 30));
        pnlSearch.add(jButton30, new org.netbeans.lib.awtextra.AbsoluteConstraints(832, 40, 120, 30));

        // Thêm panel cho radio buttons
        JPanel pnlSort = new JPanel();
        pnlSort.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        pnlSort.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // Tạo nhóm radio buttons
        ButtonGroup sortGroup = new ButtonGroup();

        // Radio button sắp xếp giảm dần
        JRadioButton rdoHighToLow = new JRadioButton("Sắp xếp số lượng từ cao tới thấp");
        rdoHighToLow.setFont(new java.awt.Font("Segoe UI", 0, 14));
        rdoHighToLow.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        rdoHighToLow.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        sortGroup.add(rdoHighToLow);
        pnlSort.add(rdoHighToLow, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 250, 30));

        // Radio button sắp xếp tăng dần
        JRadioButton rdoLowToHigh = new JRadioButton("Sắp xếp số lượng từ thấp tới cao");
        rdoLowToHigh.setFont(new java.awt.Font("Segoe UI", 0, 14));
        rdoLowToHigh.setBackground(AppColors.NEW_MAIN_BG_COLOR);
        rdoLowToHigh.setForeground(AppColors.NEW_MAIN_TEXT_COLOR);
        sortGroup.add(rdoLowToHigh);
        pnlSort.add(rdoLowToHigh, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 250, 30));

        // Nút làm mới
        JButton btnRefresh = new JButton("Làm mới");
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnRefresh.setPreferredSize(new java.awt.Dimension(120, 30));
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBackground(AppColors.NEW_DEFAULT_BUTTON_COLOR);
        btnRefresh.setForeground(Color.WHITE);
        // Thêm icon cho nút làm mới
        ImageIcon refreshIcon = new ImageIcon("src/icon_img/refresh.png");
        btnRefresh
                .setIcon(new ImageIcon(refreshIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
        btnRefresh.setHorizontalTextPosition(SwingConstants.RIGHT);
        pnlSort.add(btnRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, 130, 30));

        // Sự kiện làm mới
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Reset tìm kiếm
                jComboBox1.setSelectedIndex(0);
                jTextField1.setText("");
                jTextFieldDonGiaTu.setText("");
                jTextFieldDonGiaDen.setText("");
                jTextFieldSoLuongTu.setText("");
                jTextFieldSoLuongDen.setText("");
                // Reset radio button
                sortGroup.clearSelection();
                // Load lại dữ liệu
                loadSanPhamData();
            }
        });

        // Thêm sự kiện cho radio buttons
        rdoHighToLow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (rdoHighToLow.isSelected()) {
                    sortTableByQuantity(true);
                }
            }
        });

        rdoLowToHigh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (rdoLowToHigh.isSelected()) {
                    sortTableByQuantity(false);
                }
            }
        });

        // Thiết lập layout cho phần nội dung
        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
                pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlContentLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(pnlContentLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane1)
                                        .addComponent(pnlDetail, javax.swing.GroupLayout.DEFAULT_SIZE, 962,
                                                Short.MAX_VALUE)
                                        .addComponent(pnlSearch, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pnlSort, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(20, Short.MAX_VALUE)));
        pnlContentLayout.setVerticalGroup(
                pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlContentLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pnlSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(pnlSort, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31,
                                        Short.MAX_VALUE)
                                .addComponent(pnlDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 290,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17)));

        containerPanel.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1000, 630));

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        add(containerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 700));
    }

    // Thêm phương thức sắp xếp bảng
    private void sortTableByQuantity(boolean descending) {
        DefaultTableModel model = (DefaultTableModel) sanPhamTable.getModel();
        List<Vector> data = new ArrayList<>();

        // Lấy dữ liệu từ model
        for (int i = 0; i < model.getRowCount(); i++) {
            Vector row = new Vector();
            for (int j = 0; j < model.getColumnCount(); j++) {
                row.add(model.getValueAt(i, j));
            }
            data.add(row);
        }

        // Sắp xếp dữ liệu theo số lượng (cột 7)
        Collections.sort(data, new Comparator<Vector>() {
            @Override
            public int compare(Vector o1, Vector o2) {
                int qty1 = Integer.parseInt(o1.get(7).toString());
                int qty2 = Integer.parseInt(o2.get(7).toString());
                return descending ? qty2 - qty1 : qty1 - qty2;
            }
        });

        // Xóa dữ liệu cũ và thêm dữ liệu đã sắp xếp
        model.setRowCount(0);
        for (Vector row : data) {
            model.addRow(row);
        }
    }

    // Variables declaration
    private javax.swing.JPanel containerPanel;
    private javax.swing.JButton jButton30;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlDetail;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable sanPhamTable;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblMaSP;
    private javax.swing.JLabel lblTenSP;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblDonGia;
    private javax.swing.JLabel lblImgURL;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JLabel lblMauSac;
    private javax.swing.JLabel lblSize;
    private javax.swing.JLabel lblKhuyenMai;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JLabel jLabelDonGiaTu;
    private javax.swing.JTextField jTextFieldDonGiaTu;
    private javax.swing.JLabel jLabelDonGiaDen;
    private javax.swing.JTextField jTextFieldDonGiaDen;
    private javax.swing.JLabel jLabelSoLuongTu;
    private javax.swing.JTextField jTextFieldSoLuongTu;
    private javax.swing.JLabel jLabelSoLuongDen;
    private javax.swing.JTextField jTextFieldSoLuongDen;
    // End of variables declaration
}