package screens.NhanVien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import BUS.NhanVienBUS;
import BUS.TaiKhoanBUS;
import DTO.nhanVienDTO;
import DTO.taiKhoanDTO;
import DTO.VaiTro;
import java.math.BigDecimal;

public class themNhanVienPanel extends JPanel {
    private JTextField txtHoTen;
    private JTextField txtDiaChi;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtNgaySinh;
    private JComboBox<String> cboGioiTinh;
    private JTextField txtChucVu;
    private JTextField txtMucLuong;
    private JComboBox<String> cboVaiTro;
    private JTextField txtTenDangNhap;
    private JPasswordField txtMatKhau;
    private JButton btnThem;
    private JButton btnHuy;
    private NhanVienBUS nhanVienBUS;
    private TaiKhoanBUS taiKhoanBUS;
    private VaiTro currentUserLoginVaiTro;

    public themNhanVienPanel(VaiTro currentUserLoginVaiTro) {
        this.currentUserLoginVaiTro = currentUserLoginVaiTro;
        nhanVienBUS = new NhanVienBUS();
        taiKhoanBUS = new TaiKhoanBUS();
        initComponents();
        setupListeners();
        populateVaiTroComboBox();
    }

    private String generateNextMaNV() {
        List<nhanVienDTO> danhSachNV = nhanVienBUS.getAllNhanVien();
        int maxNumber = 0;
        
        for (nhanVienDTO nv : danhSachNV) {
            String maNV = nv.getMaNhanVien();
            if (maNV != null && maNV.startsWith("NV")) {
                try {
                    int number = Integer.parseInt(maNV.substring(2));
                    maxNumber = Math.max(maxNumber, number);
                } catch (NumberFormatException e) {
                    // Bỏ qua nếu không parse được số
                }
            }
        }
        
        return String.format("NV%d", maxNumber + 1);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 600));

        // Panel chính
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Tiêu đề
        JLabel lblTitle = new JLabel("Thêm Nhân Viên Mới", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(lblTitle, gbc);
        gbc.gridwidth = 1;

        // Họ tên (required)
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblHoTen = new JLabel("Họ tên: *");
        lblHoTen.setForeground(Color.RED);
        mainPanel.add(lblHoTen, gbc);
        txtHoTen = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtHoTen, gbc);

        // Ngày sinh
        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Ngày sinh (dd/MM/yyyy):"), gbc);
        txtNgaySinh = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtNgaySinh, gbc);

        // Giới tính
        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Giới tính:"), gbc);
        cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        gbc.gridx = 1;
        mainPanel.add(cboGioiTinh, gbc);

        // Địa chỉ
        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Địa chỉ:"), gbc);
        txtDiaChi = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtDiaChi, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtEmail, gbc);

        // Số điện thoại
        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Số điện thoại:"), gbc);
        txtPhone = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtPhone, gbc);

        // Chức vụ
        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Chức vụ:"), gbc);
        txtChucVu = new JTextField(20);
        txtChucVu.setEditable(false);
        gbc.gridx = 1;
        mainPanel.add(txtChucVu, gbc);

        // Mức lương
        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Mức lương:"), gbc);
        txtMucLuong = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtMucLuong, gbc);

        // Vai trò tài khoản
        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Vai trò tài khoản: *"), gbc);
        cboVaiTro = new JComboBox<>();
        gbc.gridx = 1;
        mainPanel.add(cboVaiTro, gbc);

        // Tên đăng nhập
        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Tên đăng nhập:"), gbc);
        txtTenDangNhap = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtTenDangNhap, gbc);

        // Mật khẩu
        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Mật khẩu:"), gbc);
        txtMatKhau = new JPasswordField(20);
        gbc.gridx = 1;
        mainPanel.add(txtMatKhau, gbc);

        // Panel chứa nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnThem = new JButton("Thêm");
        btnHuy = new JButton("Hủy");
        
        // Tùy chỉnh kích thước nút
        Dimension buttonSize = new Dimension(100, 35);
        btnThem.setPreferredSize(buttonSize);
        btnHuy.setPreferredSize(buttonSize);
        
        buttonPanel.add(btnThem);
        buttonPanel.add(btnHuy);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        mainPanel.add(buttonPanel, gbc);

        // Thêm panel chính vào center
        add(mainPanel, BorderLayout.CENTER);
    }

    private void populateVaiTroComboBox() {
        cboVaiTro.removeAllItems();
        if (currentUserLoginVaiTro == null) {
            cboVaiTro.setEnabled(false);
            return;
        }

        switch (currentUserLoginVaiTro) {
            case QUAN_TRI:
                for (VaiTro vt : VaiTro.values()) {
                    cboVaiTro.addItem(vt.getDisplayName());
                }
                cboVaiTro.setSelectedItem(VaiTro.NHAN_VIEN.getDisplayName());
                txtChucVu.setText(VaiTro.NHAN_VIEN.getDisplayName());
                cboVaiTro.setEnabled(true);
                break;
            case QUAN_LY_KHO:
            case QUAN_LY_NHAN_VIEN:
                cboVaiTro.addItem(VaiTro.NHAN_VIEN.getDisplayName());
                cboVaiTro.setSelectedItem(VaiTro.NHAN_VIEN.getDisplayName());
                txtChucVu.setText(VaiTro.NHAN_VIEN.getDisplayName());
                cboVaiTro.setEnabled(true);
                break;
            case NHAN_VIEN:
                cboVaiTro.addItem(VaiTro.NHAN_VIEN.getDisplayName());
                cboVaiTro.setSelectedItem(VaiTro.NHAN_VIEN.getDisplayName());
                txtChucVu.setText(VaiTro.NHAN_VIEN.getDisplayName());
                cboVaiTro.setEnabled(false);
                break;
            default:
                cboVaiTro.setEnabled(false);
                break;
        }
        if (cboVaiTro.getSelectedItem() != null) {
            txtChucVu.setText((String) cboVaiTro.getSelectedItem());
        }
    }

    private void setupListeners() {
        btnThem.addActionListener(e -> themNhanVien());
        btnHuy.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        });

        cboVaiTro.addActionListener(e -> {
            String selectedDisplayName = (String) cboVaiTro.getSelectedItem();
            if (selectedDisplayName != null) {
                txtChucVu.setText(selectedDisplayName);
            }
        });
    }

    private void themNhanVien() {
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

            if (txtMatKhau.getPassword().length == 0) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập mật khẩu!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            String selectedVaiTroDisplayName = (String) cboVaiTro.getSelectedItem();
            if (selectedVaiTroDisplayName == null) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn vai trò tài khoản!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            VaiTro selectedVaiTro = VaiTro.fromString(selectedVaiTroDisplayName);
            if (selectedVaiTro == null) {
                JOptionPane.showMessageDialog(this,
                    "Vai trò tài khoản không hợp lệ!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Parse ngày sinh
            Date ngaySinh = null;
            if (!txtNgaySinh.getText().trim().isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                ngaySinh = new Date(dateFormat.parse(txtNgaySinh.getText().trim()).getTime());
            }

            // Parse mức lương
            BigDecimal mucLuong = new BigDecimal(txtMucLuong.getText().trim());

            // Tạo mã nhân viên mới
            String maNV = generateNextMaNV();

            // Tạo đối tượng nhân viên
            nhanVienDTO nhanVien = new nhanVienDTO(
                maNV,
                txtHoTen.getText().trim(),
                txtEmail.getText().trim(),
                txtPhone.getText().trim(),
                txtDiaChi.getText().trim(),
                cboGioiTinh.getSelectedItem().toString(),
                ngaySinh,
                new Timestamp(System.currentTimeMillis()),
                selectedVaiTro.getDisplayName(),
                mucLuong,
                "QL001",
                "Đang làm việc"
            );

            // Tạo đối tượng tài khoản
            taiKhoanDTO taiKhoan = new taiKhoanDTO(
                null,
                txtTenDangNhap.getText().trim(),
                new String(txtMatKhau.getPassword()),
                selectedVaiTro.getDisplayName(),
                1,
                maNV
            );

            // Thêm nhân viên và tài khoản
            if (nhanVienBUS.themNhanVien(nhanVien) && taiKhoanBUS.themTaiKhoan(taiKhoan)) {
                JOptionPane.showMessageDialog(this,
                    "Thêm nhân viên và tài khoản thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Thêm nhân viên thất bại!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Đã xảy ra lỗi: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        txtHoTen.setText("");
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtNgaySinh.setText("");
        txtChucVu.setText("");
        txtMucLuong.setText("");
        txtTenDangNhap.setText("");
        txtMatKhau.setText("");
        cboGioiTinh.setSelectedIndex(0);
        populateVaiTroComboBox();
        if (cboVaiTro.getItemCount() > 0 && cboVaiTro.isEnabled()) {
            if (currentUserLoginVaiTro == VaiTro.QUAN_TRI) {
                 cboVaiTro.setSelectedItem(VaiTro.NHAN_VIEN.getDisplayName());
            }
        }
        if (cboVaiTro.getSelectedItem() != null) {
            txtChucVu.setText((String) cboVaiTro.getSelectedItem());
        } else {
            txtChucVu.setText("");
        }
    }
} 