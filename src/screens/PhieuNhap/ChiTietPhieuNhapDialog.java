package screens.PhieuNhap;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import DTO.PhieuNhapDTO;

public class ChiTietPhieuNhapDialog extends JDialog {
    private PhieuNhapDTO phieuNhap;
    private JPanel mainPanel;
    private SimpleDateFormat dateFormat;

    public ChiTietPhieuNhapDialog(Frame parent, PhieuNhapDTO phieuNhap) {
        super(parent, "Chi Tiết Phiếu Nhập", true);
        if (phieuNhap == null) {
            JOptionPane.showMessageDialog(parent, "Phiếu nhập không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        this.phieuNhap = phieuNhap;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
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
        JLabel titleLabel = new JLabel("THÔNG TIN CHI TIẾT PHIẾU NHẬP", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        gbc.gridy++; gbc.gridwidth = 1;
        addField("Mã phiếu nhập:", phieuNhap.getMaPhieuNhap(), gbc);

        gbc.gridy++;
        addField("Mã sản phẩm:", phieuNhap.getMaSanPham(), gbc);

        gbc.gridy++;
        addField("Tên sản phẩm:", phieuNhap.getTenSanPham(), gbc);

        gbc.gridy++;
        addField("Mã nhà cung cấp:", phieuNhap.getMaNhaCungCap(), gbc);

        gbc.gridy++;
        addField("Số lượng:", String.valueOf(phieuNhap.getSoLuong()), gbc);

        gbc.gridy++;
        addField("Đơn giá:", String.format("%.0f", phieuNhap.getDonGia()), gbc);

        gbc.gridy++;
        addField("Thành tiền:", String.format("%.0f", phieuNhap.getThanhTien()), gbc);

        gbc.gridy++;
        addField("Thời gian:", phieuNhap.getThoiGian() != null ? dateFormat.format(phieuNhap.getThoiGian()) : "Chưa cập nhật", gbc);

        gbc.gridy++;
        addField("Trạng thái:", phieuNhap.getTrangThai(), gbc);

        gbc.gridy++;
        addField("Hình thức thanh toán:", phieuNhap.getHinhThucThanhToan(), gbc);

        // Nút đóng
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton closeButton = new JButton("Đóng");
        closeButton.setPreferredSize(new Dimension(100, 35));
        closeButton.addActionListener(e -> dispose());
        mainPanel.add(closeButton, gbc);

        getContentPane().add(mainPanel);
        pack();
        setResizable(false);
        setMinimumSize(new Dimension(500, 500));
    }

    private void addField(String label, String value, GridBagConstraints gbc) {
        JLabel lblField = new JLabel(label);
        lblField.setFont(new Font("Segoe UI", Font.BOLD, 12));
        mainPanel.add(lblField, gbc);

        gbc.gridx = 1;
        JLabel lblValue = new JLabel(value != null && !value.trim().isEmpty() ? value : "Chưa cập nhật");
        lblValue.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        mainPanel.add(lblValue, gbc);

        gbc.gridx = 0;
    }
}
