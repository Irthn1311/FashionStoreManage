package screens.KhachHang;

import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import DAO.KhachhangDAO;
import DTO.khachHangDTO;
import DTO.taiKhoanDTO;
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
import java.sql.Timestamp;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class khachHangPanel extends javax.swing.JPanel {
    private KhachhangDAO khachHangDAO;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timestampFormat;

    public khachHangPanel() {
        initComponents();
        setupUI();

        // Khởi tạo DAO và định dạng ngày tháng
        khachHangDAO = new KhachhangDAO();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        timestampFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        setupSearchComponents();
        setupTable();
        // Load dữ liệu khách hàng
        loadKhachHangData();
        setupListeners();
        setupTableListeners();
    }

    private void setupTable() {
        // Thiết lập chiều rộng cho các cột
        khachHangTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // STT
        khachHangTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Mã KH
        khachHangTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Tên KH
        khachHangTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Tên đăng nhập
        khachHangTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Năm sinh
        khachHangTable.getColumnModel().getColumn(5).setPreferredWidth(70);  // Giới tính
        khachHangTable.getColumnModel().getColumn(6).setPreferredWidth(150); // Email
        khachHangTable.getColumnModel().getColumn(7).setPreferredWidth(100); // Số điện thoại
        khachHangTable.getColumnModel().getColumn(8).setPreferredWidth(150); // Địa chỉ
        khachHangTable.getColumnModel().getColumn(9).setPreferredWidth(80);  // Chi tiết

        // Thiết lập căn giữa cho một số cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        khachHangTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);  // STT
        khachHangTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);  // Mã KH
        khachHangTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);  // Năm sinh
        khachHangTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);  // Giới tính
        khachHangTable.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);  // Số điện thoại
        khachHangTable.getColumnModel().getColumn(9).setCellRenderer(centerRenderer);  // Chi tiết

        // Thiết lập màu nền cho cột "Chi tiết"
        khachHangTable.getColumnModel().getColumn(9).setCellRenderer(new DefaultTableCellRenderer() {
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
        khachHangTable.setRowHeight(25);
    }

    private void setupTableListeners() {
        khachHangTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = khachHangTable.rowAtPoint(e.getPoint());
                int col = khachHangTable.columnAtPoint(e.getPoint());
                
                // Lấy chỉ số cột cuối cùng (cột Chi Tiết)
                int lastColumnIndex = khachHangTable.getColumnCount() - 1;
                
                if (row >= 0 && col == lastColumnIndex) {
                    showChiTietKhachHang(row);
                }
            }
        });

        // Thêm cursor hand khi di chuột qua cột Chi Tiết
        khachHangTable.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int col = khachHangTable.columnAtPoint(e.getPoint());
                // Sử dụng chỉ số cột cuối cùng thay vì tìm theo tên
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
            // Parse ngày sinh từ chuỗi trong bảng
            Object ngaySinhObj = khachHangTable.getValueAt(row, 4);
            Date ngaySinh = null;
            if (ngaySinhObj != null && !ngaySinhObj.toString().isEmpty() && !ngaySinhObj.toString().equals("Chưa cập nhật")) {
                java.util.Date parsedDate = dateFormat.parse(ngaySinhObj.toString());
                ngaySinh = new Date(parsedDate.getTime());
            }

            // Lấy các giá trị từ bảng và xử lý null
            String tenDangNhap = getTableValueAsString(row, 3);
            String email = getTableValueAsString(row, 6);
            String phone = getTableValueAsString(row, 7);
            String diaChi = getTableValueAsString(row, 8);

            // Tạo đối tượng taiKhoanDTO
            taiKhoanDTO taiKhoan = new taiKhoanDTO(
                null, // maTaiKhoan
                tenDangNhap,
                null, // matKhau
                email,
                phone,
                "USER", // vaiTro
                "ACTIVE" // trangThai
            );

            // Lấy thông tin khách hàng từ row được chọn
            khachHangDTO kh = new khachHangDTO(
                getTableValueAsString(row, 1), // maKhachHang
                getTableValueAsString(row, 2), // hoTen
                email,
                phone,
                diaChi,
                getTableValueAsString(row, 5), // gioiTinh
                ngaySinh,
                new Timestamp(System.currentTimeMillis()),
                taiKhoan
            );

            // Hiển thị dialog chi tiết
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

    // Phương thức hỗ trợ để lấy giá trị từ bảng và xử lý null
    private String getTableValueAsString(int row, int column) {
        Object value = khachHangTable.getValueAt(row, column);
        return value != null ? value.toString() : "";
    }

    private void loadKhachHangData() {
        List<khachHangDTO> khachHangList = khachHangDAO.getAllKhachHang();
        updateTableData(khachHangList);
    }

    private void updateTableData(List<khachHangDTO> khachHangList) {
        DefaultTableModel model = (DefaultTableModel) khachHangTable.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        int stt = 1;
        for (khachHangDTO kh : khachHangList) {
            String ngaySinhStr = "";

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
                    kh.getTenDangNhap(),
                    ngaySinhStr,
                    kh.getGioiTinh(),
                    kh.getEmail(),
                    kh.getPhone(),
                    kh.getDiaChi() != null && !kh.getDiaChi().trim().isEmpty() ? kh.getDiaChi() : "Chưa cập nhật",
                    "Xem chi tiết"
            });
        }
    }

    private void searchKhachHang() {
        String keyword = jTextField1.getText().trim();
        String searchType = jComboBox1.getSelectedItem().toString();
        
        if (keyword.isEmpty()) {
            loadKhachHangData(); // Nếu từ khóa trống, load lại tất cả dữ liệu
            return;
        }

        List<khachHangDTO> searchResults = khachHangDAO.searchKhachHang(keyword, searchType);
        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Không tìm thấy khách hàng nào!",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
        }
        updateTableData(searchResults);
    }

    private void setupSearchComponents() {
        // Thiết lập combobox tìm kiếm
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
            "Tất cả", "Mã khách hàng", "Tên khách hàng", "Email", "Số điện thoại"
        }));

        // Thêm action listener cho nút tìm kiếm
        jButton30.addActionListener(e -> searchKhachHang());

        // Thêm action listener cho textfield để tìm kiếm khi nhấn Enter
        jTextField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchKhachHang();
                }
            }
        });
    }

    private void setupListeners() {
        // Nút Thêm
        jButton31.addActionListener(new ActionListener() {
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

        // Nút Sửa
        jButton32.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = khachHangTable.getSelectedRow();
                if (selectedRow >= 0) {
                    try {
                        // Parse ngày sinh từ chuỗi trong bảng
                        Object ngaySinhObj = khachHangTable.getValueAt(selectedRow, 4);
                        Date ngaySinh = null;
                        if (ngaySinhObj != null && !ngaySinhObj.toString().isEmpty() && !ngaySinhObj.toString().equals("Chưa cập nhật")) {
                            try {
                                java.util.Date parsedDate = dateFormat.parse(ngaySinhObj.toString());
                                ngaySinh = new Date(parsedDate.getTime());
                            } catch (Exception ex) {
                                System.out.println("Lỗi parse ngày sinh: " + ex.getMessage());
                            }
                        }

                        // Lấy các giá trị từ bảng và xử lý null
                        String maKH = getTableValueAsString(selectedRow, 1);
                        String hoTen = getTableValueAsString(selectedRow, 2);
                        String tenDangNhap = getTableValueAsString(selectedRow, 3);
                        String gioiTinh = getTableValueAsString(selectedRow, 5);
                        String email = getTableValueAsString(selectedRow, 6);
                        String phone = getTableValueAsString(selectedRow, 7);
                        String diaChi = getTableValueAsString(selectedRow, 8);

                        // Kiểm tra các giá trị bắt buộc
                        if (maKH.isEmpty() || hoTen.isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                "Không thể lấy thông tin khách hàng. Vui lòng thử lại.",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Tạo đối tượng taiKhoanDTO
                        taiKhoanDTO taiKhoan = new taiKhoanDTO(
                            null, // maTaiKhoan
                            tenDangNhap,
                            null, // matKhau
                            email,
                            phone,
                            "USER", // vaiTro
                            "ACTIVE" // trangThai
                        );

                        // Tạo đối tượng khachHangDTO
                        khachHangDTO kh = new khachHangDTO(
                            maKH,
                            hoTen,
                            email,
                            phone,
                            diaChi,
                            gioiTinh,
                            ngaySinh,
                            new Timestamp(System.currentTimeMillis()),
                            taiKhoan
                        );

                        // Hiển thị dialog sửa thông tin
                        JDialog dialog = new JDialog();
                        dialog.setTitle("Sửa Thông Tin Khách Hàng");
                        dialog.setModal(true);
                        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        
                        suaKhachHangPanel suaPanel = new suaKhachHangPanel(dialog, kh);
                        dialog.add(suaPanel);
                        
                        dialog.pack();
                        dialog.setLocationRelativeTo(null);
                        dialog.setVisible(true);
                        
                        // Sau khi dialog đóng, cập nhật lại bảng
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

        // Nút Xóa
        jButton33.addActionListener(new ActionListener() {
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
                            boolean success = khachHangDAO.xoaKhachHang(maKH);
                            if (success) {
                                JOptionPane.showMessageDialog(null,
                                    "Đã xóa khách hàng thành công!",
                                    "Thông báo",
                                    JOptionPane.INFORMATION_MESSAGE);
                                // Cập nhật lại bảng
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

        // Nút Xuất file
        jButton34.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Implement xuất file
                JOptionPane.showMessageDialog(null, "Chức năng đang được phát triển");
            }
        });
    }

    public javax.swing.JPanel getKhachHangPanel() {
        return containerPanel;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        containerPanel = new javax.swing.JPanel();
        containerPanel.setPreferredSize(new java.awt.Dimension(1000, 700));
        containerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        khachHangTable = new javax.swing.JTable();
        jButton34 = new javax.swing.JButton();
        jPanel33 = new javax.swing.JPanel();
        jButton30 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();

        pnlHeader.setBackground(new java.awt.Color(12, 150, 156));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Quản lý khách hàng");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                                .addContainerGap(404, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(386, 386, 386)));
        pnlHeaderLayout.setVerticalGroup(
                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlHeaderLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(14, Short.MAX_VALUE)));

        containerPanel.add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 70));

        pnlContent.setBackground(new java.awt.Color(107, 163, 190));

        jPanel17.setBackground(new java.awt.Color(107, 163, 190));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chỉnh sửa"));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton31.setText("Thêm ");
        jPanel17.add(jButton31, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 24, -1, 34));

        jButton32.setText("Sửa");
        jPanel17.add(jButton32, new org.netbeans.lib.awtextra.AbsoluteConstraints(392, 24, -1, 34));

        jButton33.setText("Xóa");
        jPanel17.add(jButton33, new org.netbeans.lib.awtextra.AbsoluteConstraints(646, 24, -1, 34));

        jPanel18.setBackground(new java.awt.Color(107, 163, 190));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng thông tin"));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        khachHangTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "STT", "Mã Khách Hàng", "Tên Khách Hàng", "Tên Đăng Nhập", "Năm Sinh", "Giới Tính",
                        "Email", "Số Điện Thoại", "Địa Chỉ", "Chi Tiết"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        khachHangTable.setShowGrid(true);
        jScrollPane2.setViewportView(khachHangTable);

        jPanel18.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 910, 270));

        jButton34.setText("Lưu và xuất file");

        jPanel33.setBackground(new java.awt.Color(107, 163, 190));
        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tìm kiếm\n"));
        jPanel33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton30.setText("Tìm kiếm");
        jPanel33.add(jButton30, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 40, 90, 36));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Tìm kiếm");
        jPanel33.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jComboBox1.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel33.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 300, 30));
        jPanel33.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 50, 320, 30));

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
                pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlContentLayout.createSequentialGroup()
                                .addContainerGap(22, Short.MAX_VALUE)
                                .addGroup(pnlContentLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                pnlContentLayout.createSequentialGroup()
                                                        .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(313, 313, 313))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout
                                                .createSequentialGroup()
                                                .addGroup(pnlContentLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                961, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(pnlContentLayout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                                                        false)
                                                                .addComponent(jPanel17,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE)
                                                                .addComponent(jPanel18,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE, 958,
                                                                        Short.MAX_VALUE)))
                                                .addGap(17, 17, 17)))));
        pnlContentLayout.setVerticalGroup(
                pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlContentLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 80,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 342,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28,
                                        Short.MAX_VALUE)
                                .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)));

        containerPanel.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1000, 630));

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        add(containerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 700));
    }

    private void setupUI() {
        // Điều chỉnh kích thước các panel con trong pnlContent
        jPanel33.setPreferredSize(new java.awt.Dimension(960, 110));
        jPanel17.setPreferredSize(new java.awt.Dimension(960, 80));
        jPanel18.setPreferredSize(new java.awt.Dimension(960, 342));
    }

    // Variables declaration - do not modify
    private javax.swing.JPanel containerPanel;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable khachHangTable;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlHeader;
    // End of variables declaration
}
