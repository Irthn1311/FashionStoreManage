package screens.NhanVien;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;
import DTO.nhanVienDTO;
import BUS.NhanVienBUS;
import BUS.TaiKhoanBUS;
import DTO.taiKhoanDTO;
import java.sql.SQLException;

public class suaNhanVienPanel extends JPanel {
    private nhanVienDTO nhanVien;
    private NhanVienBUS nhanVienBUS;
    private TaiKhoanBUS taiKhoanBUS;
    private SimpleDateFormat dateFormat;
    private JDialog parentDialog;
    private taiKhoanDTO taiKhoan;

    // Components
    private JTextField txtMaNV;
    private JTextField txtHoTen;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtDiaChi;
    private JComboBox<String> cboGioiTinh;
    private JTextField txtNgaySinh;
    private JTextField txtChucVu;
    private JTextField txtMucLuong;
    private JTextField txtTenDangNhap;
    private JPasswordField txtMatKhau;
    private JButton btnLuu;
    private JButton btnHuy;

    public suaNhanVienPanel(JDialog parent, nhanVienDTO nhanVien) {
        this.parentDialog = parent;
        this.nhanVien = nhanVien;
        this.nhanVienBUS = new NhanVienBUS();
        this.taiKhoanBUS = new TaiKhoanBUS();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.taiKhoan = taiKhoanBUS.getTaiKhoanByMaNhanVien(nhanVien.getMaNhanVien());
        } catch (SQLException e) {
            e.printStackTrace();
            this.taiKhoan = null;
        }

        initComponents();
        loadNhanVienData();
        setupListeners();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel chính
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Sửa Thông Tin Nhân Viên",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Khởi tạo components
        txtMaNV = new JTextField(20);
        txtHoTen = new JTextField(20);
        txtEmail = new JTextField(20);
        txtPhone = new JTextField(20);
        txtDiaChi = new JTextField(20);
        cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        txtNgaySinh = new JTextField(20);
        txtChucVu = new JTextField(20);
        txtMucLuong = new JTextField(20);
        txtTenDangNhap = new JTextField(20);
        txtMatKhau = new JPasswordField(20);

        // Thiết lập các trường không được sửa
        txtMaNV.setEditable(false);

        // Thêm components vào panel
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Mã nhân viên:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtMaNV, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Họ tên:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtHoTen, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtPhone, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtDiaChi, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(new JLabel("Giới tính:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(cboGioiTinh, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        mainPanel.add(new JLabel("Ngày sinh (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtNgaySinh, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        mainPanel.add(new JLabel("Chức vụ:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtChucVu, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        mainPanel.add(new JLabel("Mức lương:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtMucLuong, gbc);

        gbc.gridx = 0; gbc.gridy = 9;
        mainPanel.add(new JLabel("Tên đăng nhập:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtTenDangNhap, gbc);

        gbc.gridx = 0; gbc.gridy = 10;
        mainPanel.add(new JLabel("Mật khẩu (để trống nếu không đổi):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtMatKhau, gbc);

        // Panel chứa buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");

        btnLuu.setPreferredSize(new Dimension(100, 30));
        btnHuy.setPreferredSize(new Dimension(100, 30));

        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);

        // Thêm panels vào panel chính
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set size
        setPreferredSize(new Dimension(400, 500));
    }

    private void loadNhanVienData() {
        if (nhanVien != null) {
            txtMaNV.setText(nhanVien.getMaNhanVien());
            txtHoTen.setText(nhanVien.getHoTen());
            txtEmail.setText(nhanVien.getEmail());
            txtPhone.setText(nhanVien.getSoDienThoai());
            txtDiaChi.setText(nhanVien.getDiaChi());
            cboGioiTinh.setSelectedItem(nhanVien.getGioiTinh());
            if (nhanVien.getNgaySinh() != null) {
                txtNgaySinh.setText(dateFormat.format(nhanVien.getNgaySinh()));
            }
            txtChucVu.setText(nhanVien.getChucVu());
            txtMucLuong.setText(nhanVien.getMucLuong().toString());
        }

        if (taiKhoan != null) {
            txtTenDangNhap.setText(taiKhoan.getTenDangNhap());
        }
    }

    private void setupListeners() {
        btnLuu.addActionListener(e -> suaNhanVien());
        btnHuy.addActionListener(e -> parentDialog.dispose());
    }

    private void suaNhanVien() {
        try {
            // Validate input
            if (txtHoTen.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập họ tên!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (txtTenDangNhap.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập tên đăng nhập!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Parse ngày sinh
            Date ngaySinh = null;
            if (!txtNgaySinh.getText().trim().isEmpty()) {
                ngaySinh = new Date(dateFormat.parse(txtNgaySinh.getText().trim()).getTime());
            }

            // Parse mức lương
            BigDecimal mucLuong = new BigDecimal(txtMucLuong.getText().trim());

            // Cập nhật đối tượng nhanVienDTO
            nhanVien.setHoTen(txtHoTen.getText().trim());
            nhanVien.setEmail(txtEmail.getText().trim());
            nhanVien.setSoDienThoai(txtPhone.getText().trim());
            nhanVien.setDiaChi(txtDiaChi.getText().trim());
            nhanVien.setGioiTinh(cboGioiTinh.getSelectedItem().toString());
            nhanVien.setNgaySinh(ngaySinh);
            nhanVien.setChucVu(txtChucVu.getText().trim());
            nhanVien.setMucLuong(mucLuong);

            // Cập nhật thông tin tài khoản
            if (taiKhoan != null) {
                taiKhoan.setTenDangNhap(txtTenDangNhap.getText().trim());
                if (txtMatKhau.getPassword().length > 0) {
                    taiKhoan.setMatKhau(new String(txtMatKhau.getPassword()));
                }
            } else {
                // Nếu chưa có tài khoản, tạo mới
                taiKhoan = new taiKhoanDTO(
                    null,
                    txtTenDangNhap.getText().trim(),
                    new String(txtMatKhau.getPassword()),
                    "Nhân viên",
                    1,
                    nhanVien.getMaNhanVien()
                );
            }

            // Gọi phương thức cập nhật từ BUS
            if (nhanVienBUS.capNhatNhanVien(nhanVien) &&
                (taiKhoan.getMaTaiKhoan() == null ? 
                    taiKhoanBUS.themTaiKhoan(taiKhoan) : 
                    taiKhoanBUS.suaTaiKhoan(taiKhoan))) {
                JOptionPane.showMessageDialog(this,
                    "Cập nhật thông tin nhân viên và tài khoản thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
                parentDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Cập nhật thông tin nhân viên và tài khoản thất bại!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Đã xảy ra lỗi: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 