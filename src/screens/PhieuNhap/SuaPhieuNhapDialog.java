package screens.PhieuNhap;

import DTO.PhieuNhapDTO;
import DAO.SanPhamDAO;
import DAO.NhaCungCapDAO;
import DTO.sanPhamDTO;
import DTO.nhaCungCapDTO;
import java.util.stream.Collectors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentListener;

public class SuaPhieuNhapDialog extends JDialog {
    private JTextField tfMaPN, tfTenSP, tfMaNCC, tfSoLuong, tfDonGia, tfThanhTien, tfThoiGian;
    private JComboBox<String> cbMaSP, cbTrangThai, cbHinhThucThanhToan;
    private JButton btnCapNhat, btnHuy;
    private boolean saved = false;
    private SanPhamDAO sanPhamDAO = new SanPhamDAO();

    public SuaPhieuNhapDialog(Frame parent, PhieuNhapDTO phieuNhap, java.util.List<String> dsMaSP, java.util.List<String> dsMaNCC) {
        super(parent, "Sửa Phiếu Nhập", true);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(420, 600));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Sửa Thông Tin Phiếu Nhập",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        tfMaPN = new JTextField(phieuNhap.getMaPhieuNhap(), 20); tfMaPN.setEditable(false);
        cbMaSP = new JComboBox<>();
        for (sanPhamDTO sp : sanPhamDAO.getAllSanPham()) cbMaSP.addItem(sp.getMaSanPham());
        cbMaSP.setSelectedItem(phieuNhap.getMaSanPham());
        tfTenSP = new JTextField(20); tfTenSP.setEditable(false);
        tfMaNCC = new JTextField(20); tfMaNCC.setEditable(false);
        tfSoLuong = new JTextField(String.valueOf(phieuNhap.getSoLuong()), 20);
        tfDonGia = new JTextField(20);
        tfThanhTien = new JTextField(20); tfThanhTien.setEditable(false); tfThanhTien.setBackground(Color.LIGHT_GRAY);
        tfThoiGian = new JTextField(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(phieuNhap.getThoiGian()), 20);
        cbTrangThai = new JComboBox<>(new String[]{"Hoàn thành", "Chưa hoàn thành", "Đã hủy"});
        cbTrangThai.setSelectedItem(phieuNhap.getTrangThai());
        cbHinhThucThanhToan = new JComboBox<>(new String[]{"Tiền mặt", "Chuyển khoản"});
        cbHinhThucThanhToan.setSelectedItem(phieuNhap.getHinhThucThanhToan());

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; mainPanel.add(new JLabel("Mã phiếu nhập:"), gbc);
        gbc.gridx = 1; mainPanel.add(tfMaPN, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; mainPanel.add(new JLabel("Mã sản phẩm:"), gbc);
        gbc.gridx = 1; mainPanel.add(cbMaSP, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; mainPanel.add(new JLabel("Tên sản phẩm:"), gbc);
        gbc.gridx = 1; mainPanel.add(tfTenSP, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; mainPanel.add(new JLabel("Mã nhà cung cấp:"), gbc);
        gbc.gridx = 1; mainPanel.add(tfMaNCC, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; mainPanel.add(new JLabel("Số lượng:"), gbc);
        gbc.gridx = 1; mainPanel.add(tfSoLuong, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; mainPanel.add(new JLabel("Đơn giá:"), gbc);
        gbc.gridx = 1; mainPanel.add(tfDonGia, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; mainPanel.add(new JLabel("Thành tiền:"), gbc);
        gbc.gridx = 1; mainPanel.add(tfThanhTien, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; mainPanel.add(new JLabel("Thời gian:"), gbc);
        gbc.gridx = 1; mainPanel.add(tfThoiGian, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; mainPanel.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx = 1; mainPanel.add(cbTrangThai, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; mainPanel.add(new JLabel("Hình thức thanh toán:"), gbc);
        gbc.gridx = 1; mainPanel.add(cbHinhThucThanhToan, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnCapNhat = new JButton("Cập Nhật");
        btnHuy = new JButton("Hủy");
        buttonPanel.add(btnCapNhat);
        buttonPanel.add(btnHuy);

        // Sự kiện tự động tính thành tiền
        DocumentListener calcListener = new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { calculateTotal(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { calculateTotal(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { calculateTotal(); }
        };
        tfSoLuong.getDocument().addDocumentListener(calcListener);
        tfDonGia.getDocument().addDocumentListener(calcListener);

        // Sự kiện chọn mã SP để tự động điền tên SP, NCC, Đơn giá
        cbMaSP.addActionListener(e -> {
            String maSP = (String) cbMaSP.getSelectedItem();
            if (maSP != null) {
                sanPhamDTO sp = sanPhamDAO.getSanPhamByMa(maSP);
                if (sp != null) {
                    tfTenSP.setText(sp.getTenSanPham());
                    tfMaNCC.setText(sp.getMaNhaCungCap());
                    tfDonGia.setText(String.valueOf(sp.getGiaBan()));
                } else {
                    tfTenSP.setText("");
                    tfMaNCC.setText("");
                    tfDonGia.setText("");
                }
            } else {
                tfTenSP.setText("");
                tfMaNCC.setText("");
                tfDonGia.setText("");
            }
        });

        btnCapNhat.addActionListener(e -> {
            if (validateInput()) {
                saved = true;
                setVisible(false);
            }
        });
        btnHuy.addActionListener(e -> setVisible(false));

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);

        // Khởi tạo giá trị ban đầu cho các trường tự động
        cbMaSP.setSelectedItem(phieuNhap.getMaSanPham());
        cbMaSP.getActionListeners()[0].actionPerformed(null); // Gọi sự kiện để fill tên SP, NCC, Đơn giá
    }

    private void calculateTotal() {
        try {
            int soLuong = Integer.parseInt(tfSoLuong.getText());
            double donGia = Double.parseDouble(tfDonGia.getText());
            tfThanhTien.setText(String.format("%.0f", soLuong * donGia));
        } catch (NumberFormatException ex) {
            tfThanhTien.setText("0");
        }
    }

    private boolean validateInput() {
        if (tfMaPN.getText().trim().isEmpty() ||
            cbMaSP.getSelectedItem() == null ||
            tfTenSP.getText().trim().isEmpty() ||
            tfMaNCC.getText().trim().isEmpty() ||
            tfSoLuong.getText().trim().isEmpty() ||
            tfDonGia.getText().trim().isEmpty() ||
            tfThoiGian.getText().trim().isEmpty() ||
            cbTrangThai.getSelectedItem() == null ||
            cbHinhThucThanhToan.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
            return false;
        }

        try {
            int soLuong = Integer.parseInt(tfSoLuong.getText());
            double donGia = Double.parseDouble(tfDonGia.getText());
            if (soLuong <= 0 || donGia <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải lớn hơn 0!");
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải là số!");
            return false;
        }

        return true;
    }

    public boolean isSaved() { return saved; }

    public PhieuNhapDTO getUpdatedPhieuNhap() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return new PhieuNhapDTO(
                tfMaPN.getText(),
                tfMaNCC.getText(),
                (String) cbMaSP.getSelectedItem(),
                tfTenSP.getText(),
                Integer.parseInt(tfSoLuong.getText()),
                sdf.parse(tfThoiGian.getText()),
                Double.parseDouble(tfDonGia.getText()),
                (String) cbTrangThai.getSelectedItem(),
                (String) cbHinhThucThanhToan.getSelectedItem(),
                Double.parseDouble(tfThanhTien.getText())
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
} 