package screens.TrangChu;

import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.UIManager;
import DTO.taiKhoanDTO;
import BUS.PhanQuyenBUS;
import DTO.VaiTro;

import screens.HoaDon.HoaDonPanel;
import screens.KhachHang.khachHangPanel;
import screens.KhuyenMai.khuyenMaiPanel;
import screens.LoaiSanPham.loaisanpham;
import screens.NhaCungCap.nhaCungCapPanel;
import screens.NhanVien.nhanVienPanel;
import screens.NhapHang.nhaphang;
import screens.PhieuNhap.phieunhap;
import screens.SanPham.sanPhamPanel;
import screens.ThongKe.ThongKePanel;
import screens.XuatHang.xuathang;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import DAO.HoaDonDAO;
import DAO.KhachHangDAO;
import DAO.SanPhamDAO;
import DAO.NhanVienDAO;

/**
 *
 * @author nson9
 */
public class trangchu extends javax.swing.JFrame {
    private taiKhoanDTO taiKhoan;

    /**
     * Creates new form adminscreen
     */
    public trangchu(taiKhoanDTO taiKhoan) {
        this.taiKhoan = taiKhoan;
        initComponents();

        // Set kích thước cố định cho frame (thêm 35px cho thanh tiêu đề Windows)
        setSize(1215, 735);
        setLocationRelativeTo(null);
        setResizable(false);

        // Add icons to buttons with error handling
        setButtonIcon(btnLogin, "/icon_img/box.png");
        setButtonIcon(btnNhapHang, "/icon_img/import.png");
        setButtonIcon(btnXuatHang, "/icon_img/export.png");
        setButtonIcon(btnSanPham, "/icon_img/product.png");
        setButtonIcon(btnLoaiSanPham, "/icon_img/category.png");
        setButtonIcon(btnHoaDon, "/icon_img/invoice.png");
        setButtonIcon(btnPhieuNhap, "/icon_img/receipt.png");
        setButtonIcon(btnKhuyenMai, "/icon_img/discount.png");
        setButtonIcon(btnNhanVien, "/icon_img/employee.png");
        setButtonIcon(btnKhachHang, "/icon_img/customer.png");
        setButtonIcon(btnNhaCungCap, "/icon_img/supplier.png");
        setButtonIcon(btnTaiKhoan, "/icon_img/statistics.png");
        setButtonIcon(btnSetting, "/icon_img/setting.png");

        // Khởi tạo các panel
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();

        // Lưu lại panel chào mừng và main content
        welcomePanel = jPanel3;
        mainContent = jPanel4;

        // Cập nhật thông tin người dùng
        jLabel3.setText("Xin chào, " + taiKhoan.getTenDangNhap());

        // Áp dụng phân quyền cho các button
        VaiTro vaiTro = taiKhoan.getVaiTro();
        
        // Kiểm tra và vô hiệu hóa các button dựa trên vai trò
        PhanQuyenBUS.kiemTraVaVoHieuHoaButton(btnNhapHang, vaiTro, "NhapHang");
        PhanQuyenBUS.kiemTraVaVoHieuHoaButton(btnXuatHang, vaiTro, "XuatHang");
        PhanQuyenBUS.kiemTraVaVoHieuHoaButton(btnSanPham, vaiTro, "SanPham");
        PhanQuyenBUS.kiemTraVaVoHieuHoaButton(btnLoaiSanPham, vaiTro, "LoaiSanPham");
        PhanQuyenBUS.kiemTraVaVoHieuHoaButton(btnHoaDon, vaiTro, "HoaDon");
        PhanQuyenBUS.kiemTraVaVoHieuHoaButton(btnPhieuNhap, vaiTro, "NhapHang");
        PhanQuyenBUS.kiemTraVaVoHieuHoaButton(btnKhuyenMai, vaiTro, "KhuyenMai");
        PhanQuyenBUS.kiemTraVaVoHieuHoaButton(btnNhanVien, vaiTro, "NhanVien");
        PhanQuyenBUS.kiemTraVaVoHieuHoaButton(btnKhachHang, vaiTro, "KhachHang");
        PhanQuyenBUS.kiemTraVaVoHieuHoaButton(btnNhaCungCap, vaiTro, "NhaCungCap");
        PhanQuyenBUS.kiemTraVaVoHieuHoaButton(btnTaiKhoan, vaiTro, "ThongKe");


        // Them action listener cho btnNhapHang
        btnNhapHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapHangActionPerformed(evt);
            }
        });

        // Them action listener cho btnXuatHang
        btnXuatHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatHangActionPerformed(evt);
            }
        });

        // Them action listner cho btnSanPham
        btnSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSanPhamActionPerformed(evt);
            }
        });

        // Them action listener cho btnLoaiSanPham
        btnLoaiSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoaiSanPhamActionPerformed(evt);
            }
        });

        // Thêm action listener cho btnHoaDon
        btnHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonActionPerformed(evt);
            }
        });

        // Them action listener cho btnPhieuNhap
        btnPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhieuNhapActionPerformed(evt);
            }
        });

        // Them action listener cho btnKhuyenMai
        btnKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhuyenMaiActionPerformed(evt);
            }
        });
        // Them action listener cho btnNhanVien
        btnNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienActionPerformed(evt);
            }
        });
        // Them action listener cho btnKhachHang
        btnKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhachHangActionPerformed(evt);
            }
        });
        // Them action listener cho btnNhaCungCap
        btnNhaCungCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhaCungCapActionPerformed(evt);
            }
        });

        // Them action listener cho btnTaiKhoan
        btnTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaiKhoanActionPerformed(evt);
            }
        });
    }

    // Helper method to safely load and set button icons
    private void setButtonIcon(javax.swing.JButton button, String iconPath) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            if (icon.getImage() != null) {
                Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaledImage));
            } else {
                System.err.println("Warning: Icon image is null for path: " + iconPath);
            }
        } catch (Exception e) {
            System.err.println("Warning: Failed to load icon at path: " + iconPath + " - " + e.getMessage());
        }
    }

    public void switchPanel(javax.swing.JPanel panel) {
        if (panel == null) {
            System.out.println("Error: Panel mới là null");
            return;
        }

        // Xóa tất cả các component hiện tại
        getContentPane().removeAll();

        // Thêm lại panel menu bên trái (jPanel1)
        jPanel1.setBackground(new java.awt.Color(10, 112, 117));
        jPanel1.setBounds(0, 0, 200, 700);
        getContentPane().add(jPanel1);

        // Thiết lập thuộc tính cho panel mới
        panel.setBackground(new java.awt.Color(107, 163, 190));
        panel.setBounds(200, 0, 1000, 700);
        getContentPane().add(panel);
        mainContent = panel;

        // Cập nhật giao diện
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    // Thêm phương thức để quay lại màn hình chính (nếu cần)
    private void showWelcomeScreen() {
        // Xóa panel hiện tại
        mainContent.setVisible(false);
        getContentPane().remove(mainContent);

        // Hiển thị lại panel chào mừng
        welcomePanel.setVisible(true);
        jPanel4.setVisible(true);
        welcomePanel.setBounds(200, 0, 960, 70);
        jPanel4.setBounds(200, 70, 960, 610);
        getContentPane().add(welcomePanel);
        getContentPane().add(jPanel4);
        mainContent = jPanel4;

        // Cập nhật giao diện
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnLogin = new javax.swing.JButton();
        btnSetting = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnNhapHang = new javax.swing.JButton();
        btnXuatHang = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        btnSanPham = new javax.swing.JButton();
        btnLoaiSanPham = new javax.swing.JButton();
        btnHoaDon = new javax.swing.JButton();
        btnPhieuNhap = new javax.swing.JButton();
        btnKhuyenMai = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        btnNhanVien = new javax.swing.JButton();
        btnKhachHang = new javax.swing.JButton();
        btnNhaCungCap = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        btnTaiKhoan = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(10, 112, 117));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnLogin.setText("Đăng Xuất");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        jPanel1.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 16, 90, 37));

        btnSetting.setText("Setting");
        btnSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettingActionPerformed(evt);
            }
        });
        jPanel1.add(btnSetting, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 16, -1, 37));

        jSeparator1.setBackground(new java.awt.Color(10, 112, 117));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 71, 200, 13));

        btnNhapHang.setText("Nhập hàng");
        jPanel1.add(btnNhapHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 90, 188, 36));

        btnXuatHang.setText("Xuất hàng");
        jPanel1.add(btnXuatHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 138, 188, 36));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 192, 200, 10));

        btnSanPham.setText("Sản phẩm");
        jPanel1.add(btnSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 214, 188, 36));

        btnLoaiSanPham.setText("Quản lý sản phẩm");
        btnLoaiSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoaiSanPhamActionPerformed(evt);
            }
        });
        jPanel1.add(btnLoaiSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 262, 188, 36));

        btnHoaDon.setText("Hóa đơn");
        jPanel1.add(btnHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 310, 188, 36));

        btnPhieuNhap.setText("Phiếu nhập");
        jPanel1.add(btnPhieuNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 358, 188, 36));

        btnKhuyenMai.setText("Khuyến mãi");
        jPanel1.add(btnKhuyenMai, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 406, 188, 36));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 454, 200, 10));

        btnNhanVien.setText("Nhân viên");
        jPanel1.add(btnNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 470, 188, 36));

        btnKhachHang.setText("Khách hàng");
        jPanel1.add(btnKhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 518, 188, 36));

        btnNhaCungCap.setText("Nhà cung cấp");
        jPanel1.add(btnNhaCungCap, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 566, 188, 36));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 614, 200, 10));

        btnTaiKhoan.setText("Thống kê");
        jPanel1.add(btnTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 630, 188, 36));

        jPanel4.setBackground(new java.awt.Color(107, 163, 190));
        jPanel4.setLayout(new BorderLayout());

        // Tạo panel tiêu đề dashboard
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new java.awt.Color(12, 150, 156));
        headerPanel.setPreferredSize(new Dimension(960, 60));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("DASHBOARD");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Tạo panel chính cho nội dung dashboard
        JPanel dashboardContent = new JPanel();
        dashboardContent.setBackground(new java.awt.Color(107, 163, 190));
        dashboardContent.setLayout(new BorderLayout());

        // Panel hiển thị thông tin cá nhân và ngày giờ
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new java.awt.Color(107, 163, 190));
        infoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        
        JLabel dateTimeLabel = new JLabel();
        dateTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateTimeLabel.setText(sdf.format(new Date()));
        
        // Cập nhật thời gian mỗi giây
        Timer timer = new Timer(1000, e -> {
            dateTimeLabel.setText(sdf.format(new Date()));
        });
        timer.start();
        
        infoPanel.add(dateTimeLabel);
        infoPanel.add(Box.createHorizontalStrut(20));
        infoPanel.add(jLabel3); // Sử dụng label chào mừng đã có sẵn
        
        // Panel chứa các thẻ thông tin thống kê
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayout(2, 2, 15, 15));
        cardPanel.setBackground(new java.awt.Color(107, 163, 190));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Tạo các thẻ thông tin
        cardPanel.add(createInfoCard("Tổng sản phẩm", getSanPhamCount(), "product.png", new Color(41, 128, 185)));
        cardPanel.add(createInfoCard("Tổng đơn hàng", getHoaDonCount(), "invoice.png", new Color(39, 174, 96)));
        cardPanel.add(createInfoCard("Tổng khách hàng", getKhachHangCount(), "customer.png", new Color(211, 84, 0)));
        cardPanel.add(createInfoCard("Tổng nhân viên", getNhanVienCount(), "employee.png", new Color(142, 68, 173)));
        
        // Tạo panel chứa các nút truy cập nhanh
        JPanel quickAccessPanel = new JPanel();
        quickAccessPanel.setBackground(new java.awt.Color(107, 163, 190));
        quickAccessPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(12, 150, 156), 2),
            "Truy cập nhanh",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(12, 150, 156)
        ));
        quickAccessPanel.setLayout(new GridLayout(1, 4, 10, 10));
        quickAccessPanel.setBorder(BorderFactory.createCompoundBorder(
            quickAccessPanel.getBorder(),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        quickAccessPanel.add(createQuickButton("Hóa đơn", "invoice.png", e -> btnHoaDonActionPerformed(e)));
        quickAccessPanel.add(createQuickButton("Sản phẩm", "product.png", e -> btnSanPhamActionPerformed(e)));
        quickAccessPanel.add(createQuickButton("Khách hàng", "customer.png", e -> btnKhachHangActionPerformed(e)));
        quickAccessPanel.add(createQuickButton("Thống kê", "statistics.png", e -> btnTaiKhoanActionPerformed(e)));
        
        // Panel hiển thị thông báo hoặc ghi chú
        JPanel notePanel = new JPanel();
        notePanel.setBackground(Color.WHITE);
        notePanel.setLayout(new BorderLayout());
        notePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(12, 150, 156), 2),
            "Thông báo",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(12, 150, 156)
        ));
        
        JTextArea noteArea = new JTextArea();
        noteArea.setText("- Chào mừng bạn đến với hệ thống quản lý cửa hàng thời trang\n"
                        + "- Vui lòng kiểm tra các chức năng được phân quyền ở menu bên trái\n"
                        + "- Liên hệ quản trị viên nếu bạn cần thêm quyền truy cập\n"
                        + "- Bảo mật thông tin đăng nhập của bạn");
        noteArea.setEditable(false);
        noteArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        noteArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        notePanel.add(new JScrollPane(noteArea), BorderLayout.CENTER);
        
        // Kết hợp tất cả các thành phần vào dashboard
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(new java.awt.Color(107, 163, 190));
        centerPanel.add(cardPanel, BorderLayout.NORTH);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBackground(new java.awt.Color(107, 163, 190));
        bottomPanel.add(quickAccessPanel, BorderLayout.NORTH);
        bottomPanel.add(notePanel, BorderLayout.CENTER);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));
        
        centerPanel.add(bottomPanel, BorderLayout.CENTER);
        
        dashboardContent.add(infoPanel, BorderLayout.NORTH);
        dashboardContent.add(centerPanel, BorderLayout.CENTER);
        
        jPanel4.add(headerPanel, BorderLayout.NORTH);
        jPanel4.add(dashboardContent, BorderLayout.CENTER);

        // Xóa bỏ layout và component cũ
        jLabel2 = null;
        jLabel5 = null;
        jPanel6 = null;
        
        // Thêm lại layout chính
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        
        pack();
    }// </editor-fold>

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
        // Hiển thị hộp thoại xác nhận đăng xuất
        int option = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn đăng xuất?", 
            "Xác nhận đăng xuất", 
            JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            // Đóng cửa sổ hiện tại
            this.dispose();
            // Mở lại màn hình đăng nhập
            new screens.DangNhap.login().setVisible(true);
        }
    }

    private void btnSettingActionPerformed(java.awt.event.ActionEvent evt) {
        // Mở màn hình cài đặt tài khoản
        screens.TaiKhoan.TaiKhoanPanel taiKhoanPanel = new screens.TaiKhoan.TaiKhoanPanel(taiKhoan);
        switchPanel(taiKhoanPanel);
    }

    private void btnNhapHangActionPerformed(java.awt.event.ActionEvent evt) {
        nhaphang nhapHangScreen = new nhaphang(this);
        switchPanel(nhapHangScreen.getNhapHangPanel());
    }

    private void btnXuatHangActionPerformed(java.awt.event.ActionEvent evt) {
        xuathang xuatHangScreen = new xuathang();
        switchPanel(xuatHangScreen.getXuatHangPanel());
    }

    private void btnSanPhamActionPerformed(java.awt.event.ActionEvent evt) {
        sanPhamPanel sanPhamScreen = new sanPhamPanel();
        switchPanel(sanPhamScreen.getSanPhamPanel());
    }

    private void btnLoaiSanPhamActionPerformed(java.awt.event.ActionEvent evt) {
        loaisanpham loaiSanPhamScreen = new loaisanpham();
        switchPanel(loaiSanPhamScreen.getLoaiSanPhamPanel());
    }

    private void btnHoaDonActionPerformed(java.awt.event.ActionEvent evt) {
        HoaDonPanel hoaDonScreen = new HoaDonPanel();
        switchPanel(hoaDonScreen.getHoaDonPanel());
    }

    private void btnPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {
        phieunhap phieuNhapScreen = new phieunhap();
        switchPanel(phieuNhapScreen.getPhieuNhapPanel());
    }

    private void btnKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {
        khuyenMaiPanel khuyenMaiScreen = new khuyenMaiPanel();
        switchPanel(khuyenMaiScreen.getKhuyenMaiPanel());
    }

    private void btnNhanVienActionPerformed(java.awt.event.ActionEvent evt) {
        nhanVienPanel nhanVienScreen = new nhanVienPanel(taiKhoan);
        switchPanel(nhanVienScreen.getNhanVienPanel());
    }

    private void btnKhachHangActionPerformed(java.awt.event.ActionEvent evt) {
        khachHangPanel khachHangScreen = new khachHangPanel();
        switchPanel(khachHangScreen.getKhachHangPanel());
    }

    private void btnNhaCungCapActionPerformed(java.awt.event.ActionEvent evt) {
        nhaCungCapPanel nhaCungCapScreen = new nhaCungCapPanel();
        switchPanel(nhaCungCapScreen.getNhaCungCapPanel());
    }

    private void btnTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {
        ThongKePanel thongKeScreen = new ThongKePanel();
        switchPanel(thongKeScreen.getThongKePanel());
    }

    // Variables declaration - do not modify
    private javax.swing.JButton btnHoaDon;
    private javax.swing.JButton btnKhachHang;
    private javax.swing.JButton btnKhuyenMai;
    private javax.swing.JButton btnLoaiSanPham;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnNhaCungCap;
    private javax.swing.JButton btnNhanVien;
    private javax.swing.JButton btnNhapHang;
    private javax.swing.JButton btnPhieuNhap;
    private javax.swing.JButton btnSanPham;
    private javax.swing.JButton btnSetting;
    private javax.swing.JButton btnTaiKhoan;
    private javax.swing.JButton btnXuatHang;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel mainContent;
    private javax.swing.JPanel welcomePanel;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    // End of variables declaration

    // Phương thức tạo thẻ thông tin
    private JPanel createInfoCard(String title, String value, String iconName, Color bgColor) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(Color.WHITE);
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.setBackground(bgColor);
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(valueLabel, BorderLayout.CENTER);
        
        card.add(textPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    // Phương thức tạo nút truy cập nhanh
    private JButton createQuickButton(String text, String iconName, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(255, 255, 255));
        button.setForeground(new Color(12, 150, 156));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.addActionListener(listener);
        return button;
    }
    
    // Phương thức lấy số lượng sản phẩm
    private String getSanPhamCount() {
        try {
            SanPhamDAO sanPhamDAO = new SanPhamDAO();
            return String.valueOf(sanPhamDAO.getSoLuongSanPham());
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }
    
    // Phương thức lấy số lượng đơn hàng
    private String getHoaDonCount() {
        try {
            HoaDonDAO hoaDonDAO = new HoaDonDAO();
            return String.valueOf(hoaDonDAO.getSoLuongHoaDon());
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }
    
    // Phương thức lấy số lượng khách hàng
    private String getKhachHangCount() {
        try {
            KhachHangDAO khachHangDAO = new KhachHangDAO();
            return String.valueOf(khachHangDAO.getSoLuongKhachHang());
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }
    
    // Phương thức lấy số lượng nhân viên
    private String getNhanVienCount() {
        try {
            NhanVienDAO nhanVienDAO = new NhanVienDAO();
            return String.valueOf(nhanVienDAO.getSoLuongNhanVien());
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }
}