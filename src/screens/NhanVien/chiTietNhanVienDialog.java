package screens.NhanVien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import DTO.nhanVienDTO;
import DTO.taiKhoanDTO;
import DAO.NhanVienDAO;
import java.text.SimpleDateFormat;

public class chiTietNhanVienDialog extends JDialog {
    private nhanVienDTO nhanVien;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timestampFormat;
    private JPanel mainPanel;
    private NhanVienDAO nhanVienDAO;

    public chiTietNhanVienDialog(Frame parent, nhanVienDTO nhanVien) {
        super(parent, "Chi Tiết Nhân Viên", true);
        this.nhanVien = nhanVien;
        this.nhanVienDAO = new NhanVienDAO();
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
        JLabel titleLabel = new JLabel("THÔNG TIN CHI TIẾT NHÂN VIÊN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Thông tin nhân viên
        gbc.gridy++;
        gbc.gridwidth = 1;
        addField("Mã nhân viên:", nhanVien.getMaNhanVien(), gbc);

        gbc.gridy++;
        addField("Họ tên:", nhanVien.getHoTen(), gbc);

        gbc.gridy++;
        addField("Ngày sinh:", nhanVien.getNgaySinh() != null ? 
                dateFormat.format(nhanVien.getNgaySinh()) : "Chưa cập nhật", gbc);

        gbc.gridy++;
        addField("Giới tính:", nhanVien.getGioiTinh(), gbc);

        gbc.gridy++;
        addField("Địa chỉ:", nhanVien.getDiaChi() != null && !nhanVien.getDiaChi().trim().isEmpty() ? 
                nhanVien.getDiaChi() : "Chưa cập nhật", gbc);

        gbc.gridy++;
        addField("Email:", nhanVien.getEmail() != null && !nhanVien.getEmail().trim().isEmpty() ? 
                nhanVien.getEmail() : "Chưa cập nhật", gbc);

        gbc.gridy++;
        addField("Số điện thoại:", nhanVien.getSoDienThoai() != null && !nhanVien.getSoDienThoai().trim().isEmpty() ? 
                nhanVien.getSoDienThoai() : "Chưa cập nhật", gbc);

        gbc.gridy++;
        addField("Chức vụ:", nhanVien.getChucVu() != null && !nhanVien.getChucVu().trim().isEmpty() ? 
                nhanVien.getChucVu() : "Chưa cập nhật", gbc);

        gbc.gridy++;
        addField("Mức lương:", nhanVien.getMucLuong() != null ? 
                nhanVien.getMucLuong().toString() : "Chưa cập nhật", gbc);

        gbc.gridy++;
        addField("Ngày vào làm:", nhanVien.getNgayVaoLam() != null ? 
                timestampFormat.format(nhanVien.getNgayVaoLam()) : "Chưa cập nhật", gbc);

        // Thông tin tài khoản
        gbc.gridy++;
        gbc.gridwidth = 2;
        JLabel accountTitle = new JLabel("THÔNG TIN TÀI KHOẢN", SwingConstants.CENTER);
        accountTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mainPanel.add(accountTitle, gbc);

        // Lấy thông tin tài khoản
        taiKhoanDTO taiKhoan = nhanVienDAO.getTaiKhoanByMaNhanVien(nhanVien.getMaNhanVien());
        
        gbc.gridy++;
        gbc.gridwidth = 1;
        if (taiKhoan != null) {
            addField("Tên đăng nhập:", taiKhoan.getTenDangNhap(), gbc);
            
            gbc.gridy++;
            addField("Vai trò:", taiKhoan.getVaiTro().getDisplayName(), gbc);
            
            gbc.gridy++;
            addField("Trạng thái:", taiKhoan.getTrangThai() == 1 ? "Hoạt động" : "Không hoạt động", gbc);
        } else {
            gbc.gridy++;
            gbc.gridwidth = 2;
            JLabel noAccountLabel = new JLabel("Nhân viên chưa có tài khoản", SwingConstants.CENTER);
            noAccountLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
            mainPanel.add(noAccountLabel, gbc);
        }

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
        setMinimumSize(new Dimension(500, 600));
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