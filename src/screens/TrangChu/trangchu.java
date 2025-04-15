package screens.TrangChu;

import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.UIManager;

import screens.HoaDon.hoadon;
import screens.KhachHang.khachHangPanel;
import screens.KhuyenMai.khuyenMaiPanel;
import screens.LoaiSanPham.loaisanpham;
import screens.NhaCungCap.nhaCungCapPanel;
import screens.NhanVien.nhanVienPanel;
import screens.NhapHang.nhaphang;
import screens.NhapHang.phieunhap;
import screens.SanPham.sanPhamPanel;
import screens.TaiKhoan.taikhoan;
import screens.XuatHang.xuathang;


/**
 *
 * @author nson9
 */
public class trangchu extends javax.swing.JFrame {

    /**
     * Creates new form adminscreen
     */
    public trangchu() {
        initComponents();
        
        // Set kích thước cố định cho frame (thêm 35px cho thanh tiêu đề Windows)
        setSize(1215, 735);
        setLocationRelativeTo(null);
        setResizable(false);
        
        ImageIcon checkIcon = new ImageIcon(getClass().getResource("/icon_img/box.png"));
        Image checkImg = checkIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        btnLogin.setIcon(new ImageIcon(checkImg));

        // Khởi tạo các panel
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        
        // Lưu lại panel chào mừng và main content
        welcomePanel = jPanel3;
        mainContent = jPanel4;

        /*
         * // Them action listener cho btnSetting
         * btnSetting.addActionListener(new java.awt.event.ActionListener() {
         * public void actionPerformed(java.awt.event.ActionEvent evt) {
         * btnSettingActionPerformed(evt);
         * }
         * });
         */

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
        /*
         * // Them action listener cho btnTaiKhoan
         * btnTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
         * public void actionPerformed(java.awt.event.ActionEvent evt) {
         * btnTaiKhoanActionPerformed(evt);
         * }
         * });
         */
    }

    private void switchPanel(javax.swing.JPanel panel) {
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

    // Sửa action listener của btnHoaDon

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
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

        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        jPanel1.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 16, 90, 37));

        btnSetting.setText("Setting");
        btnSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // btnSettingActionPerformed(evt);
            }
        });
        jPanel1.add(btnSetting, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 16, -1, 37));

        jSeparator1.setBackground(new java.awt.Color(10, 112, 117));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 71, 200, 13));

        btnNhapHang.setText("Nhập hàng");
        jPanel1.add(btnNhapHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 90, 188, 36));

        btnXuatHang.setText("Xuất hàng");
        btnXuatHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatHangActionPerformed(evt);
            }
        });
        jPanel1.add(btnXuatHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 138, 188, 36));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 192, 200, 10));

        btnSanPham.setText("Sản phẩm");
        jPanel1.add(btnSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 214, 188, 36));

        btnLoaiSanPham.setText("Loại sản phẩm");
        btnLoaiSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoaiSanPhamActionPerformed(evt);
            }
        });
        jPanel1.add(btnLoaiSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 262, 188, 36));

        btnHoaDon.setText("Hóa đơn");
        jPanel1.add(btnHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 310, 188, 36));

        btnPhieuNhap.setText("Phiếu nhập");
        btnPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhieuNhapActionPerformed(evt);
            }
        });
        jPanel1.add(btnPhieuNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 358, 188, 36));

        btnKhuyenMai.setText("Khuyến mãi");
        jPanel1.add(btnKhuyenMai, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 406, 188, 36));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 454, 200, 10));

        btnNhanVien.setText("Nhân viên");
        btnNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienActionPerformed(evt);
            }
        });
        jPanel1.add(btnNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 470, 188, 36));

        btnKhachHang.setText("Khách hàng");
        jPanel1.add(btnKhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 518, 188, 36));

        btnNhaCungCap.setText("Nhà cung cấp");
        btnNhaCungCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhaCungCapActionPerformed(evt);
            }
        });
        jPanel1.add(btnNhaCungCap, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 566, 188, 36));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 614, 200, 10));

        btnTaiKhoan.setText("Thống kê ");
        jPanel1.add(btnTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 630, 188, 36));

        jPanel4.setBackground(new java.awt.Color(107, 163, 190));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("SHOPSTORE CHÚC QUÝ KHÁCH CÓ MỘT NGÀY TỐT LÀNH");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel3.setText("SHOPSTORE XIN CHÀO");

        jPanel6.setBackground(new java.awt.Color(12, 150, 156));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel5.setText("CHÀO MỪNG QUÝ KHÁCH");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(344, 344, 344)
                                .addComponent(jLabel5)
                                .addContainerGap(372, Short.MAX_VALUE)));
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addContainerGap(21, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addGap(16, 16, 16)));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(359, 359, 359)
                                .addComponent(jLabel3)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(171, 171, 171)));
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(170, 170, 170)
                                .addComponent(jLabel3)
                                .addGap(102, 102, 102)
                                .addComponent(jLabel2)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    /*
     * private void btnSettingActionPerformed(java.awt.event.ActionEvent evt) {
     * setting settingScreen = new setting();
     * switchPanel(settingScreen.getSettingPanel());
     * }
     */
    private void btnNhapHangActionPerformed(java.awt.event.ActionEvent evt) {
        nhaphang nhapHangScreen = new nhaphang();
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
        hoadon hoaDonScreen = new hoadon();
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
        nhanVienPanel nhanVienScreen = new nhanVienPanel();
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
        taikhoan taiKhoanScreen = new taikhoan();
        // switchPanel(taiKhoanScreen.getTaiKhoanPanel());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    // End of variables declaration//GEN-END:variables
}