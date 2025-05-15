package screens.NhaCungCap;

import javax.swing.*;
import java.awt.*;
import DTO.nhaCungCapDTO;

public class chiTietNhaCungCapDialog extends JDialog {
    private nhaCungCapDTO nhaCungCap;
    private JPanel mainPanel;

    public chiTietNhaCungCapDialog(Frame parent, nhaCungCapDTO nhaCungCap) {
        super(parent, "Chi Tiết Nhà Cung Cấp", true);
        this.nhaCungCap = nhaCungCap;
        
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
        JLabel titleLabel = new JLabel("THÔNG TIN CHI TIẾT NHÀ CUNG CẤP", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        // Thông tin nhà cung cấp
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        addField("Mã nhà cung cấp:", nhaCungCap.getMaNhaCungCap(), gbc);

        gbc.gridy++;
        addField("Tên nhà cung cấp:", nhaCungCap.getTenNhaCungCap(), gbc);

        gbc.gridy++;
        String namHopTacDisplay = (nhaCungCap.getNamHopTac() > 0) ? String.valueOf(nhaCungCap.getNamHopTac()) : "Chưa cập nhật";
        addField("Năm hợp tác:", namHopTacDisplay, gbc);

        gbc.gridy++;
        addField("Địa chỉ:", nhaCungCap.getDiaChi(), gbc);

        gbc.gridy++;
        addField("Email:", nhaCungCap.getEmail(), gbc);

        gbc.gridy++;
        addField("Số điện thoại:", nhaCungCap.getSoDienThoai(), gbc);

        gbc.gridy++;
        addField("Trạng thái:", nhaCungCap.getTrangThai(), gbc);

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
        setMinimumSize(new Dimension(400, 400));
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