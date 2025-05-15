package screens.TaiKhoan;

import DTO.taiKhoanDTO;
import BUS.TaiKhoanBUS;
import DTO.nhanVienDTO;
import BUS.NhanVienBUS;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

public class TaiKhoanPanel extends JPanel {
    private taiKhoanDTO taiKhoan;
    private TaiKhoanBUS taiKhoanBUS;
    private nhanVienDTO nhanVien;
    private NhanVienBUS nhanVienBUS;
    private JLabel lblTenDangNhapTK, lblVaiTroTK, lblTrangThaiTK;
    private JPasswordField txtMatKhauCu, txtMatKhauMoi, txtNhapLaiMatKhau;
    private JButton btnDoiMatKhau, btnLuuThongTinCaNhan;
    private JLabel lblMaNhanVien, lblHoTen, lblGioiTinh, lblNgaySinh, lblChucVu, lblMucLuong, lblNgayVaoLam, lblTrangThaiNV;
    private JTextField txtEmail, txtSoDienThoai, txtDiaChi;
    private JPanel pnlThongTinCaNhan, pnlThongTinTaiKhoan, pnlDoiMatKhau;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public TaiKhoanPanel(taiKhoanDTO taiKhoan) {
        this.taiKhoan = taiKhoan;
        this.taiKhoanBUS = new TaiKhoanBUS();
        this.nhanVienBUS = new NhanVienBUS();
        if (taiKhoan.getMaNhanVien() != null && !taiKhoan.getMaNhanVien().isEmpty()) {
            this.nhanVien = nhanVienBUS.getNhanVienByMa(taiKhoan.getMaNhanVien());
        } else {
            this.nhanVien = new nhanVienDTO();
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhân viên liên kết với tài khoản này.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        initComponents();
        loadNhanVienData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        pnlThongTinCaNhan = new JPanel();
        pnlThongTinCaNhan.setLayout(new GridBagLayout());
        pnlThongTinCaNhan.setBackground(Color.WHITE);
        pnlThongTinCaNhan.setBorder(BorderFactory.createTitledBorder("Thông Tin Cá Nhân"));
        GridBagConstraints gbcNhanVien = new GridBagConstraints();
        gbcNhanVien.insets = new Insets(8, 10, 8, 10);
        gbcNhanVien.anchor = GridBagConstraints.WEST;
        gbcNhanVien.fill = GridBagConstraints.HORIZONTAL;
        gbcNhanVien.weightx = 1.0;

        gbcNhanVien.gridx = 0; gbcNhanVien.gridy = 0;
        pnlThongTinCaNhan.add(new JLabel("Mã nhân viên:"), gbcNhanVien);
        gbcNhanVien.gridx = 1;
        lblMaNhanVien = new JLabel();
        lblMaNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlThongTinCaNhan.add(lblMaNhanVien, gbcNhanVien);

        gbcNhanVien.gridx = 0; gbcNhanVien.gridy = 1;
        pnlThongTinCaNhan.add(new JLabel("Họ tên:"), gbcNhanVien);
        gbcNhanVien.gridx = 1;
        lblHoTen = new JLabel();
        lblHoTen.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlThongTinCaNhan.add(lblHoTen, gbcNhanVien);

        gbcNhanVien.gridx = 0; gbcNhanVien.gridy = 2;
        pnlThongTinCaNhan.add(new JLabel("Email:"), gbcNhanVien);
        gbcNhanVien.gridx = 1;
        txtEmail = new JTextField(20);
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlThongTinCaNhan.add(txtEmail, gbcNhanVien);

        gbcNhanVien.gridx = 0; gbcNhanVien.gridy = 3;
        pnlThongTinCaNhan.add(new JLabel("Số điện thoại:"), gbcNhanVien);
        gbcNhanVien.gridx = 1;
        txtSoDienThoai = new JTextField(20);
        txtSoDienThoai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlThongTinCaNhan.add(txtSoDienThoai, gbcNhanVien);

        gbcNhanVien.gridx = 0; gbcNhanVien.gridy = 4;
        pnlThongTinCaNhan.add(new JLabel("Địa chỉ:"), gbcNhanVien);
        gbcNhanVien.gridx = 1;
        txtDiaChi = new JTextField(20);
        txtDiaChi.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlThongTinCaNhan.add(txtDiaChi, gbcNhanVien);
        
        gbcNhanVien.gridx = 0; gbcNhanVien.gridy = 5;
        pnlThongTinCaNhan.add(new JLabel("Giới tính:"), gbcNhanVien);
        gbcNhanVien.gridx = 1;
        lblGioiTinh = new JLabel();
        lblGioiTinh.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlThongTinCaNhan.add(lblGioiTinh, gbcNhanVien);

        gbcNhanVien.gridx = 0; gbcNhanVien.gridy = 6;
        pnlThongTinCaNhan.add(new JLabel("Ngày sinh:"), gbcNhanVien);
        gbcNhanVien.gridx = 1;
        lblNgaySinh = new JLabel();
        lblNgaySinh.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlThongTinCaNhan.add(lblNgaySinh, gbcNhanVien);

        gbcNhanVien.gridx = 0; gbcNhanVien.gridy = 7;
        pnlThongTinCaNhan.add(new JLabel("Chức vụ:"), gbcNhanVien);
        gbcNhanVien.gridx = 1;
        lblChucVu = new JLabel();
        lblChucVu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlThongTinCaNhan.add(lblChucVu, gbcNhanVien);

        gbcNhanVien.gridx = 0; gbcNhanVien.gridy = 8;
        pnlThongTinCaNhan.add(new JLabel("Mức lương:"), gbcNhanVien);
        gbcNhanVien.gridx = 1;
        lblMucLuong = new JLabel();
        lblMucLuong.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlThongTinCaNhan.add(lblMucLuong, gbcNhanVien);

        gbcNhanVien.gridx = 0; gbcNhanVien.gridy = 9;
        pnlThongTinCaNhan.add(new JLabel("Ngày vào làm:"), gbcNhanVien);
        gbcNhanVien.gridx = 1;
        lblNgayVaoLam = new JLabel();
        lblNgayVaoLam.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlThongTinCaNhan.add(lblNgayVaoLam, gbcNhanVien);
        
        gbcNhanVien.gridx = 0; gbcNhanVien.gridy = 10;
        pnlThongTinCaNhan.add(new JLabel("Trạng thái NV:"), gbcNhanVien);
        gbcNhanVien.gridx = 1;
        lblTrangThaiNV = new JLabel();
        lblTrangThaiNV.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlThongTinCaNhan.add(lblTrangThaiNV, gbcNhanVien);

        gbcNhanVien.gridx = 0; gbcNhanVien.gridy = 11; gbcNhanVien.gridwidth = 2;
        gbcNhanVien.anchor = GridBagConstraints.CENTER;
        btnLuuThongTinCaNhan = new JButton("Lưu Thay Đổi Thông Tin Cá Nhân");
        btnLuuThongTinCaNhan.setBackground(new Color(50, 150, 50));
        btnLuuThongTinCaNhan.setForeground(Color.WHITE);
        btnLuuThongTinCaNhan.setFocusPainted(false);
        btnLuuThongTinCaNhan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLuuThongTinCaNhan.addActionListener(e -> luuThongTinCaNhan());
        pnlThongTinCaNhan.add(btnLuuThongTinCaNhan, gbcNhanVien);

        JScrollPane scrollPaneNhanVien = new JScrollPane(pnlThongTinCaNhan);
        scrollPaneNhanVien.setBorder(null);
        scrollPaneNhanVien.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneNhanVien.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        pnlThongTinTaiKhoan = new JPanel();
        pnlThongTinTaiKhoan.setLayout(new GridBagLayout());
        pnlThongTinTaiKhoan.setBackground(Color.WHITE);
        pnlThongTinTaiKhoan.setBorder(BorderFactory.createTitledBorder("Thông Tin Tài Khoản"));
        GridBagConstraints gbcTaiKhoan = new GridBagConstraints();
        gbcTaiKhoan.insets = new Insets(8, 10, 8, 10);
        gbcTaiKhoan.anchor = GridBagConstraints.WEST;
        gbcTaiKhoan.fill = GridBagConstraints.HORIZONTAL;
        gbcTaiKhoan.weightx = 1.0;

        gbcTaiKhoan.gridx = 0; gbcTaiKhoan.gridy = 0;
        pnlThongTinTaiKhoan.add(new JLabel("Mã tài khoản:"), gbcTaiKhoan);
        gbcTaiKhoan.gridx = 1;
        JLabel lblMaTaiKhoan = new JLabel(taiKhoan.getMaTaiKhoan());
        lblMaTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlThongTinTaiKhoan.add(lblMaTaiKhoan, gbcTaiKhoan);

        gbcTaiKhoan.gridx = 0; gbcTaiKhoan.gridy = 1;
        pnlThongTinTaiKhoan.add(new JLabel("Tên đăng nhập:"), gbcTaiKhoan);
        gbcTaiKhoan.gridx = 1;
        lblTenDangNhapTK = new JLabel(taiKhoan.getTenDangNhap());
        lblTenDangNhapTK.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlThongTinTaiKhoan.add(lblTenDangNhapTK, gbcTaiKhoan);

        gbcTaiKhoan.gridx = 0; gbcTaiKhoan.gridy = 2;
        pnlThongTinTaiKhoan.add(new JLabel("Vai trò:"), gbcTaiKhoan);
        gbcTaiKhoan.gridx = 1;
        lblVaiTroTK = new JLabel(taiKhoan.getVaiTro() != null ? taiKhoan.getVaiTro().getDisplayName() : "N/A");
        lblVaiTroTK.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlThongTinTaiKhoan.add(lblVaiTroTK, gbcTaiKhoan);

        gbcTaiKhoan.gridx = 0; gbcTaiKhoan.gridy = 3;
        pnlThongTinTaiKhoan.add(new JLabel("Trạng thái TK:"), gbcTaiKhoan);
        gbcTaiKhoan.gridx = 1;
        lblTrangThaiTK = new JLabel(taiKhoan.getTrangThai() == 1 ? "Hoạt động" : (taiKhoan.getTrangThai() == 0 ? "Không hoạt động" : "Đang xét duyệt"));
        lblTrangThaiTK.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlThongTinTaiKhoan.add(lblTrangThaiTK, gbcTaiKhoan);
        
        pnlThongTinTaiKhoan.add(Box.createVerticalStrut(20));

        pnlDoiMatKhau = new JPanel();
        pnlDoiMatKhau.setLayout(new GridBagLayout());
        pnlDoiMatKhau.setBackground(Color.WHITE);
        pnlDoiMatKhau.setBorder(BorderFactory.createTitledBorder("Đổi Mật Khẩu"));
        GridBagConstraints gbcDoiMatKhau = new GridBagConstraints();
        gbcDoiMatKhau.insets = new Insets(8, 10, 8, 10);
        gbcDoiMatKhau.anchor = GridBagConstraints.WEST;
        gbcDoiMatKhau.fill = GridBagConstraints.HORIZONTAL;
        gbcDoiMatKhau.weightx = 1.0;

        gbcDoiMatKhau.gridx = 0; gbcDoiMatKhau.gridy = 0;
        pnlDoiMatKhau.add(new JLabel("Mật khẩu cũ:"), gbcDoiMatKhau);
        gbcDoiMatKhau.gridx = 1;
        txtMatKhauCu = new JPasswordField(20);
        txtMatKhauCu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlDoiMatKhau.add(txtMatKhauCu, gbcDoiMatKhau);

        gbcDoiMatKhau.gridx = 0; gbcDoiMatKhau.gridy = 1;
        pnlDoiMatKhau.add(new JLabel("Mật khẩu mới:"), gbcDoiMatKhau);
        gbcDoiMatKhau.gridx = 1;
        txtMatKhauMoi = new JPasswordField(20);
        txtMatKhauMoi.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlDoiMatKhau.add(txtMatKhauMoi, gbcDoiMatKhau);

        gbcDoiMatKhau.gridx = 0; gbcDoiMatKhau.gridy = 2;
        pnlDoiMatKhau.add(new JLabel("Nhập lại mật khẩu:"), gbcDoiMatKhau);
        gbcDoiMatKhau.gridx = 1;
        txtNhapLaiMatKhau = new JPasswordField(20);
        txtNhapLaiMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlDoiMatKhau.add(txtNhapLaiMatKhau, gbcDoiMatKhau);

        gbcDoiMatKhau.gridx = 0; gbcDoiMatKhau.gridy = 3; gbcDoiMatKhau.gridwidth = 2;
        gbcDoiMatKhau.anchor = GridBagConstraints.CENTER;
        gbcDoiMatKhau.fill = GridBagConstraints.NONE;
        btnDoiMatKhau = new JButton("Đổi Mật Khẩu");
        btnDoiMatKhau.setBackground(new Color(70, 130, 180));
        btnDoiMatKhau.setForeground(Color.WHITE);
        btnDoiMatKhau.setFocusPainted(false);
        btnDoiMatKhau.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDoiMatKhau.addActionListener(e -> doiMatKhau());
        pnlDoiMatKhau.add(btnDoiMatKhau, gbcDoiMatKhau);

        pnlDoiMatKhau.add(Box.createVerticalStrut(20));

        JPanel pnlRightSide = new JPanel();
        pnlRightSide.setLayout(new BoxLayout(pnlRightSide, BoxLayout.Y_AXIS));
        pnlRightSide.setBackground(getBackground());
        pnlRightSide.add(pnlThongTinTaiKhoan);
        pnlRightSide.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlRightSide.add(pnlDoiMatKhau);
        pnlRightSide.add(Box.createVerticalGlue());

        JScrollPane scrollPaneRightSide = new JScrollPane(pnlRightSide);
        scrollPaneRightSide.setBorder(null);
        scrollPaneRightSide.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneRightSide.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                                scrollPaneNhanVien, 
                                                scrollPaneRightSide);
        mainSplitPane.setResizeWeight(0.55);
        mainSplitPane.setBorder(null);
        SwingUtilities.invokeLater(() -> mainSplitPane.setDividerLocation(0.55));

        add(mainSplitPane, BorderLayout.CENTER);
    }
    
    private void loadNhanVienData() {
        if (nhanVien != null && nhanVien.getMaNhanVien() != null) {
            lblMaNhanVien.setText(nhanVien.getMaNhanVien());
            lblHoTen.setText(nhanVien.getHoTen());
            txtEmail.setText(nhanVien.getEmail());
            txtSoDienThoai.setText(nhanVien.getSoDienThoai());
            txtDiaChi.setText(nhanVien.getDiaChi());
            lblGioiTinh.setText(nhanVien.getGioiTinh());
            lblNgaySinh.setText(nhanVien.getNgaySinh() != null ? dateFormat.format(nhanVien.getNgaySinh()) : "N/A");
            lblChucVu.setText(nhanVien.getChucVu());
            lblMucLuong.setText(nhanVien.getMucLuong() != null ? nhanVien.getMucLuong().toString() + " VND" : "N/A");
            lblNgayVaoLam.setText(nhanVien.getNgayVaoLam() != null ? dateFormat.format(new java.util.Date(nhanVien.getNgayVaoLam().getTime())) : "N/A");
            lblTrangThaiNV.setText(nhanVien.getTrangThai());
        } else {
            lblMaNhanVien.setText("N/A");
            lblHoTen.setText("N/A");
            txtEmail.setText("");
            txtSoDienThoai.setText("");
            txtDiaChi.setText("");
            lblGioiTinh.setText("N/A");
            lblNgaySinh.setText("N/A");
            lblChucVu.setText("N/A");
            lblMucLuong.setText("N/A");
            lblNgayVaoLam.setText("N/A");
            lblTrangThaiNV.setText("N/A");
        }
    }

    private void luuThongTinCaNhan() {
        if (nhanVien == null || nhanVien.getMaNhanVien() == null) {
            JOptionPane.showMessageDialog(this, "Không có thông tin nhân viên để cập nhật.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String email = txtEmail.getText().trim();
        String soDienThoai = txtSoDienThoai.getText().trim();
        String diaChi = txtDiaChi.getText().trim();

        if (email.isEmpty() || soDienThoai.isEmpty() || diaChi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email, số điện thoại và địa chỉ không được để trống.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Địa chỉ email không hợp lệ.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!soDienThoai.matches("^\\d{10,11}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ. Phải có 10 hoặc 11 chữ số.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        nhanVien.setEmail(email);
        nhanVien.setSoDienThoai(soDienThoai);
        nhanVien.setDiaChi(diaChi);

        boolean success = nhanVienBUS.capNhatNhanVien(nhanVien);

        if (success) {
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin cá nhân thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin cá nhân thất bại. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void doiMatKhau() {
        String matKhauCu = new String(txtMatKhauCu.getPassword());
        String matKhauMoi = new String(txtMatKhauMoi.getPassword());
        String nhapLaiMatKhau = new String(txtNhapLaiMatKhau.getPassword());

        if (matKhauCu.isEmpty() || matKhauMoi.isEmpty() || nhapLaiMatKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin mật khẩu!");
            return;
        }

        if (!matKhauMoi.equals(nhapLaiMatKhau)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu mới không khớp!");
            return;
        }

        if (matKhauMoi.length() < 6) {
            JOptionPane.showMessageDialog(this, "Mật khẩu mới phải có ít nhất 6 ký tự.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (taiKhoanBUS.doiMatKhau(taiKhoan.getMaTaiKhoan(), matKhauCu, matKhauMoi)) {
                JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
                txtMatKhauCu.setText("");
                txtMatKhauMoi.setText("");
                txtNhapLaiMatKhau.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Mật khẩu cũ không đúng hoặc có lỗi xảy ra!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đổi mật khẩu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
} 