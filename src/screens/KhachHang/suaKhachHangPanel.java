package screens.KhachHang;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.sql.Date;
import DTO.khachHangDTO;
import BUS.KhachHangBUS;

public class suaKhachHangPanel extends JPanel {
    private khachHangDTO khachHang;
    private KhachHangBUS khachHangBUS;
    private SimpleDateFormat dateFormat;
    private JDialog parentDialog;

    // Components
    private JTextField txtMaKH;
    private JTextField txtHoTen;
    private JTextField txtEmail;
    private JTextField txtSoDienThoai;
    private JTextField txtDiaChi;
    private JComboBox<String> cboGioiTinh;
    private JTextField txtNgaySinh;
    private JButton btnCapNhat;
    private JButton btnHuy;

    public suaKhachHangPanel(JDialog parent, khachHangDTO khachHang) {
        this.parentDialog = parent;
        this.khachHang = khachHang;
        this.khachHangBUS = new KhachHangBUS();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        initComponents();
        loadKhachHangData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel chính
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Sửa Thông Tin Khách Hàng",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Khởi tạo components
        txtMaKH = new JTextField(20);
        txtHoTen = new JTextField(20);
        txtEmail = new JTextField(20);
        txtSoDienThoai = new JTextField(20);
        txtDiaChi = new JTextField(20);
        cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        txtNgaySinh = new JTextField(20);

        // Thiết lập các trường không được sửa
        txtMaKH.setEditable(false);

        // Thêm components vào panel
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Mã khách hàng:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtMaKH, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Họ tên:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtHoTen, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Giới tính:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(cboGioiTinh, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtSoDienThoai, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtDiaChi, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        mainPanel.add(new JLabel("Ngày sinh (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtNgaySinh, gbc);

        // Panel chứa buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnCapNhat = new JButton("Cập Nhật");
        btnHuy = new JButton("Hủy");

        btnCapNhat.setPreferredSize(new Dimension(100, 30));
        btnHuy.setPreferredSize(new Dimension(100, 30));

        buttonPanel.add(btnCapNhat);
        buttonPanel.add(btnHuy);

        // Thêm sự kiện cho buttons
        btnCapNhat.addActionListener(e -> capNhatKhachHang());
        btnHuy.addActionListener(e -> parentDialog.dispose());

        // Thêm panels vào panel chính
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set size
        setPreferredSize(new Dimension(400, 400));
    }

    private void loadKhachHangData() {
        txtMaKH.setText(khachHang.getMaKhachHang());
        txtHoTen.setText(khachHang.getHoTen());
        txtEmail.setText(khachHang.getEmail());
        txtSoDienThoai.setText(khachHang.getSoDienThoai());
        txtDiaChi.setText(khachHang.getDiaChi());
        cboGioiTinh.setSelectedItem(khachHang.getGioiTinh());
        
        if (khachHang.getNgaySinh() != null) {
            txtNgaySinh.setText(dateFormat.format(khachHang.getNgaySinh()));
        }
    }

    private void capNhatKhachHang() {
        try {
            // Validate dữ liệu
            if (txtHoTen.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (txtEmail.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập email!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (txtSoDienThoai.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Parse ngày sinh
            Date ngaySinh = null;
            if (!txtNgaySinh.getText().trim().isEmpty()) {
                try {
                    java.util.Date parsedDate = dateFormat.parse(txtNgaySinh.getText().trim());
                    ngaySinh = new Date(parsedDate.getTime());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,
                        "Ngày sinh không hợp lệ! Vui lòng nhập theo định dạng dd/MM/yyyy",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Tạo đối tượng khachHangDTO mới với thông tin đã cập nhật
            khachHangDTO khachHangCapNhat = new khachHangDTO(
                txtMaKH.getText(),
                txtHoTen.getText().trim(),
                cboGioiTinh.getSelectedItem().toString(),
                txtSoDienThoai.getText().trim(),
                txtEmail.getText().trim(),
                txtDiaChi.getText().trim(),
                ngaySinh
            );

            // Gọi BUS để cập nhật
            boolean success = khachHangBUS.capNhatKhachHang(khachHangCapNhat);
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Cập nhật thông tin khách hàng thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
                parentDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Không thể cập nhật thông tin khách hàng!",
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
}