package screens.KhachHang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import DAO.KhachhangDAO;
import DTO.khachHangDTO;
import DTO.taiKhoanDTO;

public class themKhachHangPanel extends JPanel {
    private JTextField txtHoTen;
    private JTextField txtDiaChi;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtNgaySinh;
    private JComboBox<String> cboGioiTinh;
    private JButton btnThem;
    private JButton btnHuy;
    private KhachhangDAO khachHangDAO;

    public themKhachHangPanel() {
        khachHangDAO = new KhachhangDAO();
        initComponents();
        setupListeners();
    }

    private String generateNextMaKH() {
        List<khachHangDTO> danhSachKH = khachHangDAO.getAllKhachHang();
        int maxNumber = 0;
        
        for (khachHangDTO kh : danhSachKH) {
            String maKH = kh.getMaKhachHang();
            if (maKH != null && maKH.startsWith("KH")) {
                try {
                    int number = Integer.parseInt(maKH.substring(2));
                    maxNumber = Math.max(maxNumber, number);
                } catch (NumberFormatException e) {
                    // Bỏ qua nếu không parse được số
                }
            }
        }
        
        return String.format("KH%d", maxNumber + 1);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 450));

        // Panel chính
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Tiêu đề
        JLabel lblTitle = new JLabel("Thêm Khách Hàng Mới", SwingConstants.CENTER);
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

    private void themKhachHang() {
        // Kiểm tra dữ liệu nhập
        if (!validateInput()) {
            return;
        }

        try {
            String maKH = generateNextMaKH();
            
            // Xử lý ngày sinh
            Date ngaySinh = null;
            if (!txtNgaySinh.getText().trim().isEmpty()) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(parseDate(txtNgaySinh.getText().trim()));
                ngaySinh = new Date(cal.getTimeInMillis());
            }
            
            // Lấy thời điểm hiện tại làm ngày đăng ký
            Timestamp ngayDangKy = new Timestamp(System.currentTimeMillis());

            // Tạo đối tượng taiKhoanDTO mới (sẽ được tạo trong DAO)
            taiKhoanDTO taiKhoan = new taiKhoanDTO(
                null, // maTaiKhoan sẽ được tạo tự động
                null, // tenDangNhap sẽ được tạo tự động
                null, // matKhau sẽ được tạo tự động
                txtEmail.getText().trim(),
                txtPhone.getText().trim(),
                "USER",
                "ACTIVE"
            );
            
            // Tạo đối tượng khachHangDTO mới
            khachHangDTO khachHang = new khachHangDTO(
                maKH,
                txtHoTen.getText().trim(),
                txtEmail.getText().trim(),
                txtPhone.getText().trim(),
                txtDiaChi.getText().trim(),
                cboGioiTinh.getSelectedItem().toString(),
                ngaySinh,
                ngayDangKy,
                taiKhoan
            );

            // Thêm khách hàng vào database
            boolean success = khachHangDAO.themKhachHang(khachHang);
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Thêm khách hàng thành công!\nMã khách hàng: " + maKH,
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                // Đóng dialog
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Thêm khách hàng thất bại!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private java.util.Date parseDate(String dateStr) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        return sdf.parse(dateStr);
    }

    private boolean validateInput() {
        // Kiểm tra họ tên (bắt buộc)
        if (txtHoTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng nhập họ tên!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra ngày sinh (không bắt buộc nhưng phải đúng định dạng nếu có)
        if (!txtNgaySinh.getText().trim().isEmpty()) {
            try {
                parseDate(txtNgaySinh.getText().trim());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Ngày sinh không hợp lệ! Vui lòng nhập theo định dạng dd/MM/yyyy",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        // Kiểm tra email (không bắt buộc nhưng phải đúng định dạng nếu có)
        String email = txtEmail.getText().trim();
        if (!email.isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, 
                "Email không hợp lệ!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra số điện thoại (không bắt buộc nhưng phải đúng định dạng nếu có)
        String phone = txtPhone.getText().trim();
        if (!phone.isEmpty() && !phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, 
                "Số điện thoại không hợp lệ!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void setupListeners() {
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                themKhachHang();
            }
        });

        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                huyThem();
            }
        });
    }

    private void huyThem() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn hủy thêm khách hàng?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            // Đóng dialog
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        }
    }

    private void clearForm() {
        txtHoTen.setText("");
        txtNgaySinh.setText("");
        cboGioiTinh.setSelectedIndex(0);
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
    }
} 