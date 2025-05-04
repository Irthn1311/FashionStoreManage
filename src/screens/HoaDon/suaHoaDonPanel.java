package screens.HoaDon;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.List;
import java.sql.Timestamp;
import BUS.HoaDonBUS;
import BUS.SanPhamBUS;
import BUS.KhachHangBUS;
import DTO.hoaDonDTO;
import DTO.sanPhamDTO;
import DTO.khachHangDTO;

public class suaHoaDonPanel extends JPanel {
    private hoaDonDTO hoaDon;
    private HoaDonBUS hoaDonBUS;
    private SanPhamBUS sanPhamBUS;
    private KhachHangBUS khachHangBUS;
    private SimpleDateFormat dateFormat;
    private JDialog parentDialog;

    private JTextField txtMaHD;
    private JComboBox<String> cboMaSP;
    private JTextField txtTenSP;
    private JTextField txtKichCo;
    private JTextField txtMauSac;
    private JTextField txtSoLuong;
    private JComboBox<String> cboMaKH;
    private JTextField txtTenKH;
    private JTextField txtDonGia;
    private JTextField txtThanhTien;
    private JComboBox<String> cboHinhThucTT;
    private JTextField txtThoiGian; // Thay JFormattedTextField bằng JTextField
    private JComboBox<String> cboTrangThai;
    private JButton btnCapNhat;
    private JButton btnHuy;

    public suaHoaDonPanel(JDialog parent, hoaDonDTO hoaDon) {
        this.parentDialog = parent;
        if (hoaDon == null) {
            JOptionPane.showMessageDialog(parent, "Hóa đơn không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            parentDialog.dispose();
            return;
        }
        this.hoaDon = hoaDon;
        this.hoaDonBUS = new HoaDonBUS();
        this.sanPhamBUS = new SanPhamBUS();
        this.khachHangBUS = new KhachHangBUS();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        initComponents();
        loadMaSanPham();
        loadMaKhachHang();
        loadHoaDonData();
    }

    private void loadMaSanPham() {
        List<String> maSanPhamList = sanPhamBUS.getAllMaSanPham();
        cboMaSP.addItem(""); // Thêm một tùy chọn trống
        for (String maSanPham : maSanPhamList) {
            cboMaSP.addItem(maSanPham);
        }
    }

    private void loadMaKhachHang() {
        List<String> maKhachHangList = khachHangBUS.getAllMaKhachHang();
        cboMaKH.addItem(""); // Thêm một tùy chọn trống
        for (String maKhachHang : maKhachHangList) {
            cboMaKH.addItem(maKhachHang);
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Sửa Thông Tin Hóa Đơn",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaHD = new JTextField(20);
        cboMaSP = new JComboBox<>();
        cboMaSP.setPreferredSize(new Dimension(200, 25));
        txtTenSP = new JTextField(20);
        txtTenSP.setEditable(false);
        txtKichCo = new JTextField(20);
        txtKichCo.setEditable(false);
        txtMauSac = new JTextField(20);
        txtMauSac.setEditable(false);
        txtSoLuong = new JTextField(20);
        cboMaKH = new JComboBox<>();
        cboMaKH.setPreferredSize(new Dimension(200, 25));
        txtTenKH = new JTextField(20);
        txtTenKH.setEditable(false);
        txtDonGia = new JTextField(20);
        txtDonGia.setEditable(false);
        txtThanhTien = new JTextField(20);
        txtThanhTien.setEditable(false);
        cboHinhThucTT = new JComboBox<>(new String[]{"Tiền mặt", "Chuyển khoản", "Thẻ"});

        txtThoiGian = new JTextField(20); // Sử dụng JTextField thay vì JFormattedTextField
        txtThoiGian.setEditable(true);

        cboTrangThai = new JComboBox<>(new String[]{"Hoàn thành", "Đang xử lý", "Hủy"});

        txtMaHD.setEditable(false);

        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Mã hóa đơn:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtMaHD, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Mã sản phẩm:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(cboMaSP, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Tên sản phẩm:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtTenSP, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Kích cỡ:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtKichCo, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(new JLabel("Màu sắc:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtMauSac, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(new JLabel("Số lượng:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtSoLuong, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        mainPanel.add(new JLabel("Mã khách hàng:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(cboMaKH, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        mainPanel.add(new JLabel("Tên khách hàng:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtTenKH, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        mainPanel.add(new JLabel("Đơn giá:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtDonGia, gbc);

        gbc.gridx = 0; gbc.gridy = 9;
        mainPanel.add(new JLabel("Thành tiền:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtThanhTien, gbc);

        gbc.gridx = 0; gbc.gridy = 10;
        mainPanel.add(new JLabel("Hình thức TT:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(cboHinhThucTT, gbc);

        gbc.gridx = 0; gbc.gridy = 11;
        mainPanel.add(new JLabel("Thời gian:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtThoiGian, gbc);

        gbc.gridx = 0; gbc.gridy = 12;
        mainPanel.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(cboTrangThai, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnCapNhat = new JButton("Cập Nhật");
        btnHuy = new JButton("Hủy");

        btnCapNhat.setPreferredSize(new Dimension(100, 30));
        btnHuy.setPreferredSize(new Dimension(100, 30));

        buttonPanel.add(btnCapNhat);
        buttonPanel.add(btnHuy);

        btnCapNhat.addActionListener(e -> capNhatHoaDon());
        btnHuy.addActionListener(e -> parentDialog.dispose());

        // Sự kiện khi chọn MaSanPham
        cboMaSP.addActionListener(e -> {
            String selectedMaSP = (String) cboMaSP.getSelectedItem();
            if (selectedMaSP != null && !selectedMaSP.isEmpty()) {
                sanPhamDTO sp = sanPhamBUS.getSanPhamByMa(selectedMaSP);
                if (sp != null) {
                    txtTenSP.setText(sp.getTenSanPham());
                    txtKichCo.setText(sp.getSize());
                    txtMauSac.setText(""); // Sản phẩm không có thuộc tính màu sắc
                    txtDonGia.setText(String.format("%.0f", sp.getGiaBan()));
                    calculateThanhTien();
                }
            } else {
                txtTenSP.setText("");
                txtKichCo.setText("");
                txtMauSac.setText("");
                txtDonGia.setText("");
                txtThanhTien.setText("");
            }
        });

        // Sự kiện khi chọn MaKhachHang
        cboMaKH.addActionListener(e -> {
            String selectedMaKH = (String) cboMaKH.getSelectedItem();
            if (selectedMaKH != null && !selectedMaKH.isEmpty()) {
                khachHangDTO kh = khachHangBUS.getKhachHangByMa(selectedMaKH);
                if (kh != null) {
                    txtTenKH.setText(kh.getHoTen());
                }
            } else {
                txtTenKH.setText("");
            }
        });

        // Sự kiện khi thay đổi số lượng để tính thành tiền
        txtSoLuong.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { calculateThanhTien(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { calculateThanhTien(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { calculateThanhTien(); }
        });

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(400, 600));
    }

    private void calculateThanhTien() {
        try {
            int soLuong = txtSoLuong.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtSoLuong.getText().trim());
            double donGia = txtDonGia.getText().trim().isEmpty() ? 0 : Double.parseDouble(txtDonGia.getText().trim());
            double thanhTien = soLuong * donGia;
            txtThanhTien.setText(String.format("%.0f", thanhTien));
        } catch (NumberFormatException e) {
            txtThanhTien.setText("0");
        }
    }

    private void loadHoaDonData() {
        txtMaHD.setText(hoaDon.getMaHoaDon());
        cboMaSP.setSelectedItem(hoaDon.getMaSanPham());
        txtTenSP.setText(hoaDon.getTenSanPham());
        txtKichCo.setText(hoaDon.getKichCo());
        txtMauSac.setText(hoaDon.getMauSac());
        txtSoLuong.setText(String.valueOf(hoaDon.getSoLuong()));
        cboMaKH.setSelectedItem(hoaDon.getMaKhachHang());
        txtTenKH.setText(hoaDon.getTenKhachHang());
        txtDonGia.setText(String.format("%.0f", hoaDon.getDonGia()));
        txtThanhTien.setText(String.format("%.0f", hoaDon.getThanhTien()));
        cboHinhThucTT.setSelectedItem(hoaDon.getHinhThucThanhToan());
        if (hoaDon.getThoiGian() != null) {
            txtThoiGian.setText(dateFormat.format(hoaDon.getThoiGian()));
        }
        cboTrangThai.setSelectedItem(hoaDon.getTrangThai());
    }

    private void capNhatHoaDon() {
        try {
            // Kiểm tra các trường bắt buộc
            if (cboMaSP.getSelectedItem() == null || cboMaSP.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn mã sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (txtTenSP.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên sản phẩm không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cboMaKH.getSelectedItem() == null || cboMaKH.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn mã khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (txtTenKH.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên khách hàng không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra số lượng, đơn giá, thành tiền
            int soLuong;
            double donGia, thanhTien;
            try {
                soLuong = txtSoLuong.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtSoLuong.getText().trim());
                donGia = txtDonGia.getText().trim().isEmpty() ? 0 : Double.parseDouble(txtDonGia.getText().trim());
                thanhTien = txtThanhTien.getText().trim().isEmpty() ? 0 : Double.parseDouble(txtThanhTien.getText().trim());
                if (soLuong <= 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (donGia <= 0) {
                    JOptionPane.showMessageDialog(this, "Đơn giá phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (thanhTien <= 0) {
                    JOptionPane.showMessageDialog(this, "Thành tiền phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số lượng, đơn giá và thành tiền phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra số lượng tồn kho
            String maSanPham = cboMaSP.getSelectedItem().toString();
            sanPhamDTO sp = sanPhamBUS.getSanPhamByMa(maSanPham);
            if (sp == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm với mã: " + maSanPham, "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int soLuongCu = hoaDon.getSoLuong();
            int soLuongMoi = soLuong;
            int soLuongThayDoi = soLuongMoi - soLuongCu;
            if (soLuongThayDoi > 0 && sp.getSoLuongTonKho() < soLuongThayDoi) {
                JOptionPane.showMessageDialog(this, "Số lượng tồn kho không đủ! Hiện tại chỉ còn: " + sp.getSoLuongTonKho(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra và parse thời gian
            Timestamp thoiGian;
            String thoiGianStr = txtThoiGian.getText().trim();
            if (thoiGianStr.isEmpty()) {
                thoiGian = new Timestamp(System.currentTimeMillis());
            } else {
                try {
                    thoiGian = new Timestamp(dateFormat.parse(thoiGianStr).getTime());
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(this, "Định dạng thời gian không hợp lệ! Vui lòng nhập theo định dạng dd/MM/yyyy HH:mm:ss", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Tạo đối tượng hoaDonDTO mới
            hoaDonDTO hd = new hoaDonDTO(
                txtMaHD.getText().trim(),
                cboMaSP.getSelectedItem().toString(),
                txtTenSP.getText().trim(),
                txtKichCo.getText().trim(),
                txtMauSac.getText().trim(),
                soLuong,
                cboMaKH.getSelectedItem().toString(),
                txtTenKH.getText().trim(),
                thanhTien,
                donGia,
                cboHinhThucTT.getSelectedItem().toString(),
                thoiGian,
                cboTrangThai.getSelectedItem().toString()
            );

            // Gọi phương thức updateHoaDon từ HoaDonBUS
            try {
                boolean success = hoaDonBUS.updateHoaDon(hd);
                if (success) {
                    // Cập nhật số lượng tồn kho
                    int newSoLuongTonKho = sp.getSoLuongTonKho() - soLuongThayDoi;
                    sp.setSoLuongTonKho(newSoLuongTonKho);
                    boolean updateSuccess = sanPhamBUS.updateSanPham(sp);
                    if (!updateSuccess) {
                        throw new RuntimeException("Cập nhật số lượng tồn kho thất bại!");
                    }

                    JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    parentDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi không xác định: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}