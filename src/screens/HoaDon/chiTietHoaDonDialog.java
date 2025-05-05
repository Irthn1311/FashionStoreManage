package screens.HoaDon;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import DTO.hoaDonDTO;

public class chiTietHoaDonDialog extends JDialog {
    private hoaDonDTO hoaDon;
    private JPanel mainPanel;
    private SimpleDateFormat dateFormat;

    public chiTietHoaDonDialog(Frame parent, hoaDonDTO hoaDon) {
        super(parent, "Chi Tiết Hóa Đơn", true);
        if (hoaDon == null) {
            JOptionPane.showMessageDialog(parent, "Hóa đơn không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        this.hoaDon = hoaDon;
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
        JLabel titleLabel = new JLabel("THÔNG TIN CHI TIẾT HÓA ĐƠN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Thông tin hóa đơn
        gbc.gridy++;
        gbc.gridwidth = 1;
        addField("Mã hóa đơn:", hoaDon.getMaHoaDon(), gbc);

        gbc.gridy++;
        addField("Mã sản phẩm:", hoaDon.getMaSanPham(), gbc);

        gbc.gridy++;
        addField("Tên sản phẩm:", hoaDon.getTenSanPham(), gbc);

        gbc.gridy++;
        addField("Kích cỡ:", hoaDon.getKichCo() != null && !hoaDon.getKichCo().trim().isEmpty() ? hoaDon.getKichCo() : "Chưa cập nhật", gbc);

        gbc.gridy++;
        addField("Màu sắc:", hoaDon.getMauSac() != null && !hoaDon.getMauSac().trim().isEmpty() ? hoaDon.getMauSac() : "Chưa cập nhật", gbc);

        gbc.gridy++;
        addField("Số lượng:", String.valueOf(hoaDon.getSoLuong()), gbc);

        gbc.gridy++;
        addField("Mã khách hàng:", hoaDon.getMaKhachHang(), gbc);

        gbc.gridy++;
        addField("Tên khách hàng:", hoaDon.getTenKhachHang(), gbc);

        gbc.gridy++;
        addField("Đơn giá:", String.format("%.0f", hoaDon.getDonGia()), gbc);

        gbc.gridy++;
        addField("Thành tiền:", String.format("%.0f", hoaDon.getThanhTien()), gbc);

        gbc.gridy++;
        addField("Hình thức TT:", hoaDon.getHinhThucThanhToan(), gbc);

        gbc.gridy++;
        addField("Thời gian:", hoaDon.getThoiGian() != null ? dateFormat.format(hoaDon.getThoiGian()) : "Chưa cập nhật", gbc);

        gbc.gridy++;
        addField("Trạng thái:", hoaDon.getTrangThai(), gbc);

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