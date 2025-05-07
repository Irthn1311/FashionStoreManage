package screens.KhachHang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
import BUS.KhachHangBUS;
import DTO.khachHangDTO;
import java.util.Calendar;
import java.text.ParseException;
import javax.swing.SpinnerNumberModel;

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
    private KhachHangBUS khachHangBUS;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public themKhachHangPanel() {
        khachHangBUS = new KhachHangBUS();
        initComponents();
        setupListeners();
    }

    private String generateNextMaKH() {
        List<khachHangDTO> danhSachKH = khachHangBUS.getAllKhachHang();
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
        JPanel ngaySinhPanel = new JPanel(new BorderLayout());
        txtNgaySinh = new JTextField(20);
        JButton calendarButton = new JButton("📅");
        calendarButton.setPreferredSize(new Dimension(30, 20));
        calendarButton.addActionListener(e -> showDatePicker(txtNgaySinh));
        ngaySinhPanel.add(txtNgaySinh, BorderLayout.CENTER);
        ngaySinhPanel.add(calendarButton, BorderLayout.EAST);
        gbc.gridx = 1;
        mainPanel.add(ngaySinhPanel, gbc);

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
        mainPanel.add(new JLabel("Số điện thoại:"), gbc);
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

    private void showDatePicker(JTextField dateField) {
        JDialog dateDialog = new JDialog((Window) SwingUtilities.getWindowAncestor(this), "Chọn ngày", Dialog.ModalityType.APPLICATION_MODAL);
        dateDialog.setLayout(new BorderLayout());
        dateDialog.setSize(300, 150);
        dateDialog.setLocationRelativeTo(this);

        Calendar cal = Calendar.getInstance();
        try {
            java.util.Date currentDate = dateFormat.parse(dateField.getText());
            cal.setTime(currentDate);
        } catch (ParseException e) {
            // Nếu không parse được, sử dụng ngày hiện tại
        }

        SpinnerNumberModel dayModel = new SpinnerNumberModel(cal.get(Calendar.DAY_OF_MONTH), 1, 31, 1);
        SpinnerNumberModel monthModel = new SpinnerNumberModel(cal.get(Calendar.MONTH) + 1, 1, 12, 1);
        SpinnerNumberModel yearModel = new SpinnerNumberModel(cal.get(Calendar.YEAR), 1900, 2100, 1);

        JSpinner daySpinner = new JSpinner(dayModel);
        JSpinner monthSpinner = new JSpinner(monthModel);
        JSpinner yearSpinner = new JSpinner(yearModel);

        JPanel datePanel = new JPanel(new FlowLayout());
        datePanel.add(new JLabel("Ngày:"));
        datePanel.add(daySpinner);
        datePanel.add(new JLabel("Tháng:"));
        datePanel.add(monthSpinner);
        datePanel.add(new JLabel("Năm:"));
        datePanel.add(yearSpinner);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            int day = (int) daySpinner.getValue();
            int month = (int) monthSpinner.getValue();
            int year = (int) yearSpinner.getValue();

            try {
                cal.set(year, month - 1, day);
                java.util.Date utilDate = cal.getTime();
                Date sqlDate = new Date(utilDate.getTime());
                dateField.setText(dateFormat.format(sqlDate));
                dateDialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ngày không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = new JButton("Hủy");
        cancelButton.addActionListener(e -> dateDialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        dateDialog.add(datePanel, BorderLayout.CENTER);
        dateDialog.add(buttonPanel, BorderLayout.SOUTH);
        dateDialog.setVisible(true);
    }

    private void themKhachHang() {
        if (!validateInput()) {
            System.out.println("Dữ liệu đầu vào không hợp lệ, dừng thêm khách hàng.");
            return;
        }

        try {
            // Parse ngày sinh
            Date ngaySinh = null;
            if (!txtNgaySinh.getText().trim().isEmpty()) {
                try {
                    java.util.Date parsedDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtNgaySinh.getText().trim());
                    ngaySinh = new Date(parsedDate.getTime());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,
                        "Ngày sinh không hợp lệ! Vui lòng nhập theo định dạng dd/MM/yyyy",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Tạo đối tượng khachHangDTO
            khachHangDTO khachHang = new khachHangDTO(
                generateNextMaKH(),
                txtHoTen.getText().trim(),
                cboGioiTinh.getSelectedItem().toString(),
                txtSoDienThoai.getText().trim(),
                txtEmail.getText().trim(),
                txtDiaChi.getText().trim(),
                ngaySinh
            );

            // Gọi BUS để thêm khách hàng
            boolean success = khachHangBUS.themKhachHang(khachHang);
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Thêm khách hàng thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } else {
                System.out.println("Thêm khách hàng thất bại trong DAO.");
                JOptionPane.showMessageDialog(this,
                    "Không thể thêm khách hàng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Có lỗi xảy ra: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
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
                new SimpleDateFormat("dd/MM/yyyy").parse(txtNgaySinh.getText().trim());
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

        // Kiểm tra số điện thoại (không bắt buộc nhưng phải đúng định dạng nếu có)
        String soDienThoai = txtSoDienThoai.getText().trim();
        if (!soDienThoai.isEmpty() && !soDienThoai.matches("\\d{10}")) {
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