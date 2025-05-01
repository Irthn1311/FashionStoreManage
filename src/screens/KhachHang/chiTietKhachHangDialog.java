package screens.KhachHang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import DTO.khachHangDTO;
import DTO.taiKhoanDTO;
import java.text.SimpleDateFormat;

public class chiTietKhachHangDialog extends JDialog {
    private khachHangDTO khachHang;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timestampFormat;
    private JPanel mainPanel;

    public chiTietKhachHangDialog(Frame parent, khachHangDTO khachHang) {
        super(parent, "Chi Tiết Khách Hàng", true);
        this.khachHang = khachHang;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.timestampFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        initComponents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Tiêu đề
        JLabel titleLabel = new JLabel("THÔNG TIN CHI TIẾT KHÁCH HÀNG", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Thông tin khách hàng
        gbc.gridy++;
        gbc.gridwidth = 1;
        addField("Mã khách hàng:", khachHang.getMaKhachHang(), gbc);

        gbc.gridy++;
        addField("Họ tên:", khachHang.getHoTen(), gbc);

        // Thông tin tài khoản
        taiKhoanDTO taiKhoan = khachHang.getTaiKhoan();
        if (taiKhoan != null) {
            gbc.gridy++;
            addField("Mã tài khoản:", taiKhoan.getMaTaiKhoan(), gbc);

            gbc.gridy++;
            addField("Tên đăng nhập:", taiKhoan.getTenDangNhap(), gbc);

            gbc.gridy++;
            addField("Vai trò:", taiKhoan.getVaiTro(), gbc);

            gbc.gridy++;
            addField("Trạng thái:", taiKhoan.getTrangThai(), gbc);
        }

        gbc.gridy++;
        addField("Ngày sinh:", khachHang.getNgaySinh() != null ? 
                dateFormat.format(khachHang.getNgaySinh()) : "Chưa cập nhật", gbc);

        gbc.gridy++;
        addField("Giới tính:", khachHang.getGioiTinh(), gbc);

        gbc.gridy++;
        addField("Địa chỉ:", khachHang.getDiaChi() != null && !khachHang.getDiaChi().trim().isEmpty() ? 
                khachHang.getDiaChi() : "Chưa cập nhật", gbc);

        gbc.gridy++;
        addField("Email:", khachHang.getEmail() != null && !khachHang.getEmail().trim().isEmpty() ? 
                khachHang.getEmail() : "Chưa cập nhật", gbc);

        gbc.gridy++;
        addField("Số điện thoại:", khachHang.getPhone() != null && !khachHang.getPhone().trim().isEmpty() ? 
                khachHang.getPhone() : "Chưa cập nhật", gbc);

        gbc.gridy++;
        addField("Ngày đăng ký:", khachHang.getNgayDangKy() != null ? 
                timestampFormat.format(khachHang.getNgayDangKy()) : "Chưa cập nhật", gbc);

        // Nút đóng
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton closeButton = new JButton("Đóng");
        closeButton.setPreferredSize(new Dimension(100, 35));
        closeButton.addActionListener(e -> dispose());
        mainPanel.add(closeButton, gbc);

        // Thêm panel vào dialog
        getContentPane().add(mainPanel);
        pack();
        setResizable(false);
        
        // Đặt kích thước tối thiểu
        setMinimumSize(new Dimension(500, 500));
    }

    private void addField(String label, String value, GridBagConstraints gbc) {
        JLabel lblField = new JLabel(label);
        lblField.setFont(new Font("Segoe UI", Font.BOLD, 12));
        mainPanel.add(lblField, gbc);

        gbc.gridx = 1;
        JLabel lblValue = new JLabel(value != null ? value : "Chưa cập nhật");
        lblValue.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        mainPanel.add(lblValue, gbc);

        gbc.gridx = 0;
    }
} 