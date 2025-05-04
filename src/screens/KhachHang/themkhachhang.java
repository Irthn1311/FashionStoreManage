package screens.KhachHang;

import DAO.KhachHangDAO;
import DTO.khachHangDTO;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

public class themkhachhang extends javax.swing.JPanel {
    private KhachHangDAO khachHangDAO;

    public themkhachhang() {
        khachHangDAO = new KhachHangDAO();
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel21 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextField26 = new javax.swing.JTextField();
        jTextField27 = new javax.swing.JTextField();
        jTextField28 = new javax.swing.JTextField();
        jComboBoxGioiTinh = new javax.swing.JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        jTextField30 = new javax.swing.JTextField();
        jButton38 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jTextField14 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();

        jPanel21.setBackground(new java.awt.Color(107, 163, 190));
        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng thêm"));
        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setText("Mã KH");
        jPanel21.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 37, -1));

        jLabel14.setText("Ngày sinh");
        jPanel21.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 51, -1));

        jLabel16.setText("Tên KH");
        jPanel21.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 40, 26));

        jTextField26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField26ActionPerformed(evt);
            }
        });
        jPanel21.add(jTextField26, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 220, -1));

        jTextField27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField27ActionPerformed(evt);
            }
        });
        jPanel21.add(jTextField27, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 220, -1));
        jPanel21.add(jTextField28, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, 220, -1));

        jComboBoxGioiTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxGioiTinhActionPerformed(evt);
            }
        });
        jPanel21.add(jComboBoxGioiTinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 300, 220, -1));
        jPanel21.add(jTextField30, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, 240, -1));

        jButton38.setText("Hủy");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });
        jPanel21.add(jButton38, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 370, -1, -1));

        jButton39.setText("Thêm");
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });
        jPanel21.add(jButton39, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 370, -1, -1));
        jPanel21.add(jTextField14, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 220, -1));

        jLabel1.setText("Giới tính");
        jPanel21.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 62, -1));

        jLabel2.setText("Email");
        jPanel21.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, -1));

        jLabel4.setText("Địa chỉ");
        jPanel21.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, -1, -1));

        jLabel5.setText("Tên đăng nhập");
        jPanel21.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 120, -1, 20));

        jLabel6.setText("Số điện thoại");
        jPanel21.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 180, -1, -1));
        jPanel21.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 120, 240, -1));

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel21.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 180, 239, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, 854, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }

    private void jTextField26ActionPerformed(java.awt.event.ActionEvent evt) {
        // Có thể thêm kiểm tra mã khách hàng nếu cần
    }

    private void jTextField27ActionPerformed(java.awt.event.ActionEvent evt) {
        // Có thể thêm kiểm tra tên khách hàng nếu cần
    }

    private void jComboBoxGioiTinhActionPerformed(java.awt.event.ActionEvent evt) {
        // Không cần xử lý
    }

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {
        // Có thể thêm kiểm tra số điện thoại nếu cần
    }

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {
        // Nút Hủy: Đóng giao diện
        java.awt.Container parent = this.getParent();
        while (!(parent instanceof javax.swing.JDialog) && parent != null) {
            parent = parent.getParent();
        }
        if (parent instanceof javax.swing.JDialog) {
            ((javax.swing.JDialog) parent).dispose();
        }
    }

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {
        // Nút Thêm: Lấy dữ liệu và lưu khách hàng mới
        String maKH = jTextField26.getText().trim();
        String tenKH = jTextField27.getText().trim();
        String email = jTextField14.getText().trim();
        String soDienThoai = jTextField2.getText().trim();
        String diaChi = jTextField30.getText().trim();
        String gioiTinh = (String) jComboBoxGioiTinh.getSelectedItem();
        String ngaySinhStr = jTextField28.getText().trim();

        // Kiểm tra các trường bắt buộc
        if (maKH.isEmpty() || tenKH.isEmpty() || email.isEmpty() || soDienThoai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ các trường bắt buộc (Mã KH, Tên KH, Email, Số điện thoại)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Chuyển đổi ngày sinh
        Date ngaySinh = null;
        if (!ngaySinhStr.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                ngaySinh = new Date(sdf.parse(ngaySinhStr).getTime());
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Định dạng ngày sinh không hợp lệ! Vui lòng nhập theo định dạng yyyy-MM-dd", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Tạo đối tượng khachHangDTO
        String tenDangNhap = jTextField1.getText().trim();
        khachHangDTO kh = new khachHangDTO(maKH, tenKH, tenDangNhap, email, soDienThoai, diaChi, gioiTinh, ngaySinh);

        // Thêm khách hàng vào cơ sở dữ liệu
        try {
            boolean success = khachHangDAO.themKhachHang(kh);
            if (success) {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                // Đóng giao diện sau khi thêm thành công
                java.awt.Container parent = this.getParent();
                while (!(parent instanceof javax.swing.JDialog) && parent != null) {
                    parent = parent.getParent();
                }
                if (parent instanceof javax.swing.JDialog) {
                    ((javax.swing.JDialog) parent).dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại! Kiểm tra dữ liệu nhập vào.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(themkhachhang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new themkhachhang().setVisible(true);
        });
    }

    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JComboBox<String> jComboBoxGioiTinh;
    private javax.swing.JTextField jTextField30;
}