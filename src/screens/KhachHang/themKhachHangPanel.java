package screens.KhachHang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
import DAO.KhachHangDAO;
import DTO.khachHangDTO;

public class themKhachHangPanel extends JPanel {
    private JTextField txtHoTen;
    private JTextField txtTenDangNhap;
    private JTextField txtDiaChi;
    private JTextField txtEmail;
    private JTextField txtSoDienThoai;
    private JTextField txtNgaySinh;
    private JComboBox<String> cboGioiTinh;
    private JButton btnThem;
    private JButton btnHuy;
    private KhachHangDAO khachHangDAO;

    public themKhachHangPanel() {
        khachHangDAO = new KhachHangDAO();
        initComponents();
        setupListeners();
    }

    private String generateNextMaKH() {
        List<khachHangDTO> danhSachKH = khachHangDAO.getAllKhachHang();
        System.out.println("Số lượng khách hàng hiện có: " + (danhSachKH != null ? danhSachKH.size() : "null"));
        
        if (danhSachKH == null || danhSachKH.isEmpty()) {
            return "KH1"; // Nếu không có khách hàng nào, bắt đầu từ KH1
        }

        int maxNumber = 0;
        for (khachHangDTO kh : danhSachKH) {
            String maKH = kh.getMaKhachHang();
            if (maKH != null && maKH.startsWith("KH")) {
                try {
                    int number = Integer.parseInt(maKH.substring(2));
                    maxNumber = Math.max(maxNumber, number);
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi parse mã khách hàng: " + maKH + ", " + e.getMessage());
                }
            }
        }
        
        String newMaKH = String.format("KH%d", maxNumber + 1);
        System.out.println("Mã khách hàng mới: " + newMaKH);
        return newMaKH;
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

        // Tên đăng nhập (required)
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblTenDangNhap = new JLabel("Tên đăng nhập: *");
        lblTenDangNhap.setForeground(Color.RED);
        mainPanel.add(lblTenDangNhap, gbc);
        txtTenDangNhap = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtTenDangNhap, gbc);

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

        // Email (required)
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblEmail = new JLabel("Email: *");
        lblEmail.setForeground(Color.RED);
        mainPanel.add(lblEmail, gbc);
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtEmail, gbc);

        // Số điện thoại (required)
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblSoDienThoai = new JLabel("Số điện thoại: *");
        lblSoDienThoai.setForeground(Color.RED);
        mainPanel.add(lblSoDienThoai, gbc);
        txtSoDienThoai = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtSoDienThoai, gbc);

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
            System.out.println("Dữ liệu đầu vào không hợp lệ, dừng thêm khách hàng.");
            return;
        }

        try {
            String maKH = generateNextMaKH();
            System.out.println("Đang thêm khách hàng với mã: " + maKH);

            // Xử lý ngày sinh
            Date ngaySinh = null;
            if (!txtNgaySinh.getText().trim().isEmpty()) {
                java.util.Date parsedDate = parseDate(txtNgaySinh.getText().trim());
                ngaySinh = new Date(parsedDate.getTime());
                System.out.println("Ngày sinh: " + ngaySinh);
            } else {
                System.out.println("Không có ngày sinh được nhập.");
            }

            // Tạo đối tượng khachHangDTO mới
            khachHangDTO khachHang = new khachHangDTO(
                maKH,
                txtHoTen.getText().trim(),
                txtTenDangNhap.getText().trim(),
                txtEmail.getText().trim(),
                txtSoDienThoai.getText().trim(),
                txtDiaChi.getText().trim(),
                cboGioiTinh.getSelectedItem().toString(),
                ngaySinh
            );

            System.out.println("Thông tin khách hàng: " + khachHang.toString());

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
                System.out.println("Thêm khách hàng thất bại trong DAO.");
                JOptionPane.showMessageDialog(this,
                    "Thêm khách hàng thất bại! Kiểm tra dữ liệu nhập vào hoặc cơ sở dữ liệu.",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm khách hàng: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi thêm khách hàng: " + e.getMessage(), 
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
            System.out.println("Lỗi validate: Họ tên trống.");
            JOptionPane.showMessageDialog(this, 
                "Vui lòng nhập họ tên!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra tên đăng nhập (bắt buộc)
        if (txtTenDangNhap.getText().trim().isEmpty()) {
            System.out.println("Lỗi validate: Tên đăng nhập trống.");
            JOptionPane.showMessageDialog(this, 
                "Vui lòng nhập tên đăng nhập!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra ngày sinh (không bắt buộc nhưng phải đúng định dạng nếu có)
        if (!txtNgaySinh.getText().trim().isEmpty()) {
            try {
                parseDate(txtNgaySinh.getText().trim());
            } catch (Exception e) {
                System.out.println("Lỗi validate ngày sinh: " + e.getMessage());
                JOptionPane.showMessageDialog(this,
                    "Ngày sinh không hợp lệ! Vui lòng nhập theo định dạng dd/MM/yyyy",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        // Kiểm tra email (bắt buộc và phải đúng định dạng)
        String email = txtEmail.getText().trim();
        if (email.isEmpty()) {
            System.out.println("Lỗi validate: Email trống.");
            JOptionPane.showMessageDialog(this, 
                "Vui lòng nhập email!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            System.out.println("Lỗi validate: Email không hợp lệ - " + email);
            JOptionPane.showMessageDialog(this, 
                "Email không hợp lệ!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra số điện thoại (bắt buộc và phải đúng định dạng)
        String soDienThoai = txtSoDienThoai.getText().trim();
        if (soDienThoai.isEmpty()) {
            System.out.println("Lỗi validate: Số điện thoại trống.");
            JOptionPane.showMessageDialog(this, 
                "Vui lòng nhập số điện thoại!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!soDienThoai.matches("\\d{10}")) {
            System.out.println("Lỗi validate: Số điện thoại không hợp lệ - " + soDienThoai);
            JOptionPane.showMessageDialog(this, 
                "Số điện thoại không hợp lệ! Vui lòng nhập 10 chữ số.", 
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
        txtTenDangNhap.setText("");
        txtNgaySinh.setText("");
        cboGioiTinh.setSelectedIndex(0);
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtSoDienThoai.setText("");
    }
}