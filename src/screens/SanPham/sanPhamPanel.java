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
                Image image = imageIcon.getImage().getScaledInstance(258, 176, Image.SCALE_SMOOTH);
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
        pnlHeader.setBackground(new java.awt.Color(12, 150, 156));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24));
        jLabel1.setText("Thông tin sản phẩm");

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
        pnlContent.setBackground(new java.awt.Color(107, 163, 190));

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
        sanPhamTable.setShowGrid(true);
        jScrollPane1.setViewportView(sanPhamTable);

        // Thiết lập panel chi tiết sản phẩm
        pnlDetail.setBackground(new java.awt.Color(153, 153, 255));
        pnlDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chi tiết sản phẩm"));
        pnlDetail.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImage.setText("Hình ảnh sản phẩm");
        lblImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlDetail.add(lblImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 258, 176));

        lblMaSP.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblMaSP.setText("Mã sản phẩm: ");
        pnlDetail.add(lblMaSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, 250, 30));

        lblTenSP.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblTenSP.setText("Tên sản phẩm: ");
        pnlDetail.add(lblTenSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 250, 30));

        lblSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblSoLuong.setText("Số lượng: ");
        pnlDetail.add(lblSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 110, 250, 30));

        lblDonGia.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblDonGia.setText("Đơn giá: ");
        pnlDetail.add(lblDonGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 150, 250, 30));

        lblImgURL.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblImgURL.setText("Hình ảnh: ");
        pnlDetail.add(lblImgURL, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, 350, 30));

        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblTrangThai.setText("Trạng thái: ");
        pnlDetail.add(lblTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 70, 350, 30));

        lblMauSac.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblMauSac.setText("Màu sắc: ");
        pnlDetail.add(lblMauSac, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 110, 350, 30));

        lblSize.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblSize.setText("Kích cỡ: ");
        pnlDetail.add(lblSize, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 150, 350, 30));

        // Thiết lập panel tìm kiếm
        pnlSearch.setBackground(new java.awt.Color(107, 163, 190));
        pnlSearch.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tìm kiếm"));
        pnlSearch.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // Search Label
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel2.setText("Tìm kiếm");
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
        pnlSearch.add(donGiaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 80, 30));

        // Price range
        jLabelDonGiaTu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelDonGiaTu.setText("Từ:");
        pnlSearch.add(jLabelDonGiaTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, -1, 30));

        jTextFieldDonGiaTu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTextFieldDonGiaTu.setPreferredSize(new java.awt.Dimension(80, 30));
        jTextFieldDonGiaTu.setToolTipText("Nhập giá bán từ (số dương)");
        pnlSearch.add(jTextFieldDonGiaTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, 80, 30));

        jLabelDonGiaDen.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelDonGiaDen.setText("Đến:");
        pnlSearch.add(jLabelDonGiaDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 20, -1, 30));

        jTextFieldDonGiaDen.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTextFieldDonGiaDen.setPreferredSize(new java.awt.Dimension(80, 30));
        jTextFieldDonGiaDen.setToolTipText("Nhập giá bán đến (số dương)");
        pnlSearch.add(jTextFieldDonGiaDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, 80, 30));

        // Quantity Label
        JLabel soLuongLabel = new JLabel("Số lượng:");
        soLuongLabel.setFont(new java.awt.Font("Segoe UI", 0, 14));
        pnlSearch.add(soLuongLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 60, 80, 30));

        // Quantity range
        jLabelSoLuongTu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelSoLuongTu.setText("Từ:");
        pnlSearch.add(jLabelSoLuongTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, -1, 30));

        jTextFieldSoLuongTu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTextFieldSoLuongTu.setPreferredSize(new java.awt.Dimension(80, 30));
        jTextFieldSoLuongTu.setToolTipText("Nhập số lượng tồn kho từ (số dương)");
        pnlSearch.add(jTextFieldSoLuongTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, 80, 30));

        jLabelSoLuongDen.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelSoLuongDen.setText("Đến:");
        pnlSearch.add(jLabelSoLuongDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 60, -1, 30));

        jTextFieldSoLuongDen.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTextFieldSoLuongDen.setPreferredSize(new java.awt.Dimension(80, 30));
        jTextFieldSoLuongDen.setToolTipText("Nhập số lượng tồn kho đến (số dương)");
        pnlSearch.add(jTextFieldSoLuongDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 60, 80, 30));

        // Search Button
        jButton30.setText("Tìm kiếm");
        ImageIcon searchIcon = new ImageIcon("src/icon_img/search.png");
        jButton30.setIcon(new ImageIcon(searchIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
        jButton30.setHorizontalTextPosition(SwingConstants.RIGHT);
        jButton30.setPreferredSize(new java.awt.Dimension(120, 30));
        pnlSearch.add(jButton30, new org.netbeans.lib.awtextra.AbsoluteConstraints(832, 40, 120, 30));

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
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(20, Short.MAX_VALUE)));
        pnlContentLayout.setVerticalGroup(
                pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlContentLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pnlSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31,
                                        Short.MAX_VALUE)
                                .addComponent(pnlDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 230,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17)));

        containerPanel.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1000, 630));

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        add(containerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 700));
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