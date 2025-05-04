package screens.TaiKhoan;

import DTO.taiKhoanDTO;
import BUS.TaiKhoanBUS;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TaiKhoanPanel extends JPanel {
    private taiKhoanDTO taiKhoan;
    private TaiKhoanBUS taiKhoanBUS;
    private JLabel lblTenDangNhap, lblVaiTro, lblTrangThai;
    private JPasswordField txtMatKhauCu, txtMatKhauMoi, txtNhapLaiMatKhau;
    private JButton btnDoiMatKhau;
    private JPanel pnlThongTin, pnlDoiMatKhau;

    public TaiKhoanPanel(taiKhoanDTO taiKhoan) {
        this.taiKhoan = taiKhoan;
        this.taiKhoanBUS = new TaiKhoanBUS();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Panel chính chứa thông tin tài khoản
        pnlThongTin = new JPanel();
        pnlThongTin.setLayout(new BoxLayout(pnlThongTin, BoxLayout.Y_AXIS));
        pnlThongTin.setBackground(Color.WHITE);
        pnlThongTin.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Tiêu đề
        JLabel lblTieuDe = new JLabel("THÔNG TIN TÀI KHOẢN");
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTieuDe.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlThongTin.add(lblTieuDe);
        pnlThongTin.add(Box.createVerticalStrut(30));

        // Thông tin tài khoản
        JPanel pnlInfo = new JPanel();
        pnlInfo.setLayout(new GridBagLayout());
        pnlInfo.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Mã tài khoản
        gbc.gridx = 0; gbc.gridy = 0;
        pnlInfo.add(new JLabel("Mã tài khoản:"), gbc);
        gbc.gridx = 1;
        JLabel lblMaTaiKhoan = new JLabel(taiKhoan.getMaTaiKhoan());
        lblMaTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlInfo.add(lblMaTaiKhoan, gbc);

        // Tên đăng nhập
        gbc.gridx = 0; gbc.gridy = 1;
        pnlInfo.add(new JLabel("Tên đăng nhập:"), gbc);
        gbc.gridx = 1;
        lblTenDangNhap = new JLabel(taiKhoan.getTenDangNhap());
        lblTenDangNhap.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlInfo.add(lblTenDangNhap, gbc);

        // Vai trò
        gbc.gridx = 0; gbc.gridy = 2;
        pnlInfo.add(new JLabel("Vai trò:"), gbc);
        gbc.gridx = 1;
        lblVaiTro = new JLabel(taiKhoan.getVaiTro().getDisplayName());
        lblVaiTro.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlInfo.add(lblVaiTro, gbc);

        // Trạng thái
        gbc.gridx = 0; gbc.gridy = 3;
        pnlInfo.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx = 1;
        lblTrangThai = new JLabel(taiKhoan.getTrangThai() == 1 ? "Hoạt động" : "Không hoạt động");
        lblTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlInfo.add(lblTrangThai, gbc);

        pnlThongTin.add(pnlInfo);
        pnlThongTin.add(Box.createVerticalStrut(30));

        // Panel đổi mật khẩu
        pnlDoiMatKhau = new JPanel();
        pnlDoiMatKhau.setLayout(new BoxLayout(pnlDoiMatKhau, BoxLayout.Y_AXIS));
        pnlDoiMatKhau.setBackground(Color.WHITE);
        pnlDoiMatKhau.setBorder(BorderFactory.createTitledBorder("Đổi mật khẩu"));

        // Mật khẩu cũ
        JPanel pnlMatKhauCu = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlMatKhauCu.setBackground(Color.WHITE);
        pnlMatKhauCu.add(new JLabel("Mật khẩu cũ:"));
        txtMatKhauCu = new JPasswordField(20);
        pnlMatKhauCu.add(txtMatKhauCu);
        pnlDoiMatKhau.add(pnlMatKhauCu);

        // Mật khẩu mới
        JPanel pnlMatKhauMoi = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlMatKhauMoi.setBackground(Color.WHITE);
        pnlMatKhauMoi.add(new JLabel("Mật khẩu mới:"));
        txtMatKhauMoi = new JPasswordField(20);
        pnlMatKhauMoi.add(txtMatKhauMoi);
        pnlDoiMatKhau.add(pnlMatKhauMoi);

        // Nhập lại mật khẩu mới
        JPanel pnlNhapLai = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlNhapLai.setBackground(Color.WHITE);
        pnlNhapLai.add(new JLabel("Nhập lại mật khẩu:"));
        txtNhapLaiMatKhau = new JPasswordField(20);
        pnlNhapLai.add(txtNhapLaiMatKhau);
        pnlDoiMatKhau.add(pnlNhapLai);

        // Nút đổi mật khẩu
        btnDoiMatKhau = new JButton("Đổi mật khẩu");
        btnDoiMatKhau.setBackground(new Color(70, 130, 180));
        btnDoiMatKhau.setForeground(Color.WHITE);
        btnDoiMatKhau.setFocusPainted(false);
        btnDoiMatKhau.addActionListener(e -> doiMatKhau());
        pnlDoiMatKhau.add(Box.createVerticalStrut(10));
        pnlDoiMatKhau.add(btnDoiMatKhau);

        pnlThongTin.add(pnlDoiMatKhau);

        add(pnlThongTin, BorderLayout.CENTER);
    }

    private void doiMatKhau() {
        String matKhauCu = new String(txtMatKhauCu.getPassword());
        String matKhauMoi = new String(txtMatKhauMoi.getPassword());
        String nhapLaiMatKhau = new String(txtNhapLaiMatKhau.getPassword());

        if (matKhauCu.isEmpty() || matKhauMoi.isEmpty() || nhapLaiMatKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (!matKhauMoi.equals(nhapLaiMatKhau)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu mới không khớp!");
            return;
        }

        try {
            if (taiKhoanBUS.doiMatKhau(taiKhoan.getMaTaiKhoan(), matKhauCu, matKhauMoi)) {
                JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
                txtMatKhauCu.setText("");
                txtMatKhauMoi.setText("");
                txtNhapLaiMatKhau.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Mật khẩu cũ không đúng!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }
} 