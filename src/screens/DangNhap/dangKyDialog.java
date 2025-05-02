package screens.DangNhap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import DAO.NhanVienDAO;
import javax.swing.border.EmptyBorder;

public class dangKyDialog extends JDialog {
    private JTextField txtHoTen;
    private JTextField txtEmail;
    private JTextField txtSoDienThoai;
    private JPasswordField txtMatKhau;
    private JPasswordField txtXacNhanMatKhau;
    private JButton btnDangKy;
    private JButton btnHuy;
    private NhanVienDAO nhanVienDAO;

    public dangKyDialog(JFrame parent) {
        super(parent, "Đăng Ký Tài Khoản", true);
        this.nhanVienDAO = new NhanVienDAO();
        
        initComponents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel chứa form đăng ký
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tiêu đề
        JLabel titleLabel = new JLabel("ĐĂNG KÝ TÀI KHOẢN MỚI", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);

        // Họ tên
        gbc.gridy++;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Họ và tên:"), gbc);
        gbc.gridx = 1;
        txtHoTen = new JTextField(20);
        formPanel.add(txtHoTen, gbc);

        // Email
        gbc.gridy++;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        formPanel.add(txtEmail, gbc);

        // Số điện thoại
        gbc.gridy++;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        txtSoDienThoai = new JTextField(20);
        formPanel.add(txtSoDienThoai, gbc);

        // Mật khẩu
        gbc.gridy++;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        txtMatKhau = new JPasswordField(20);
        formPanel.add(txtMatKhau, gbc);

        // Xác nhận mật khẩu
        gbc.gridy++;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Xác nhận mật khẩu:"), gbc);
        gbc.gridx = 1;
        txtXacNhanMatKhau = new JPasswordField(20);
        formPanel.add(txtXacNhanMatKhau, gbc);

        // Panel chứa nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnDangKy = new JButton("Đăng ký");
        btnHuy = new JButton("Hủy");
        buttonPanel.add(btnDangKy);
        buttonPanel.add(btnHuy);

        // Thêm các panel vào mainPanel
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Thêm sự kiện cho các nút
        btnDangKy.addActionListener(e -> dangKy());
        btnHuy.addActionListener(e -> dispose());

        // Thêm mainPanel vào dialog
        getContentPane().add(mainPanel);
        pack();
        setResizable(false);
        setMinimumSize(new Dimension(400, 350));
    }

    private void dangKy() {
        // Lấy thông tin từ form
        String hoTen = txtHoTen.getText().trim();
        String email = txtEmail.getText().trim();
        String soDienThoai = txtSoDienThoai.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword());
        String xacNhanMatKhau = new String(txtXacNhanMatKhau.getPassword());

        // Kiểm tra thông tin
        if (hoTen.isEmpty() || email.isEmpty() || soDienThoai.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!matKhau.equals(xacNhanMatKhau)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Thực hiện đăng ký
        if (nhanVienDAO.dangKyTaiKhoanVaNhanVien(hoTen, email, soDienThoai, matKhau)) {
            JOptionPane.showMessageDialog(this, "Đăng ký thành công! Vui lòng đợi quản trị viên xét duyệt.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Đăng ký thất bại! Email hoặc số điện thoại đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
} 