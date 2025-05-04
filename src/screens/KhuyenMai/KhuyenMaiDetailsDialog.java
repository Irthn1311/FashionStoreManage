package screens.KhuyenMai;

import DTO.khuyenMaiDTO;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.*;

public class KhuyenMaiDetailsDialog extends JDialog {
    private JLabel lblMaKhuyenMai, lblMaSanPham, lblTenSanPham, lblTenChuongTrinh, lblGiamGia,
            lblNgayBatDau, lblNgayKetThuc, lblGiaCu, lblGiaMoi, lblTrangThai;
    private JButton btnClose;

    public KhuyenMaiDetailsDialog(java.awt.Frame parent, khuyenMaiDTO km, SimpleDateFormat dateFormat) {
        super(parent, "THÔNG TIN CHI TIẾT KHUYẾN MÃI", true);
        initComponents(km, dateFormat);
        setSize(400, 480); // Adjusted size to accommodate the new field

        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Calculate the position to center the dialog
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y); // Set the dialog position to the center of the screen

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initComponents(khuyenMaiDTO km, SimpleDateFormat dateFormat) {
        setLayout(new BorderLayout());

        // Panel to hold the content (labels and values)
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Padding between components
        gbc.anchor = GridBagConstraints.WEST; // Align all components to the left

        // Title
        JLabel titleLabel = new JLabel("THÔNG TIN CHI TIẾT KHUYẾN MÃI");
        titleLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across both columns
        gbc.anchor = GridBagConstraints.CENTER; // Center the title
        contentPanel.add(titleLabel, gbc);

        // Reset gridwidth and anchor for the labels
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Labels and values
        int row = 1;

        // Mã khuyến mãi
        JLabel lblMaKhuyenMaiTitle = new JLabel("Mã KM:");
        gbc.gridx = 0;
        gbc.gridy = row;
        contentPanel.add(lblMaKhuyenMaiTitle, gbc);
        lblMaKhuyenMai = new JLabel(km.getMaKhuyenMai());
        gbc.gridx = 1;
        contentPanel.add(lblMaKhuyenMai, gbc);

        // Mã sản phẩm
        row++;
        JLabel lblMaSanPhamTitle = new JLabel("Mã sản phẩm:");
        gbc.gridx = 0;
        gbc.gridy = row;
        contentPanel.add(lblMaSanPhamTitle, gbc);
        lblMaSanPham = new JLabel(km.getMaSanPham());
        gbc.gridx = 1;
        contentPanel.add(lblMaSanPham, gbc);

        // Tên sản phẩm
        row++;
        JLabel lblTenSanPhamTitle = new JLabel("Tên sản phẩm:");
        gbc.gridx = 0;
        gbc.gridy = row;
        contentPanel.add(lblTenSanPhamTitle, gbc);
        lblTenSanPham = new JLabel(km.getTenSanPham());
        gbc.gridx = 1;
        contentPanel.add(lblTenSanPham, gbc);

        // Tên chương trình
        row++;
        JLabel lblTenChuongTrinhTitle = new JLabel("Tên chương trình:");
        gbc.gridx = 0;
        gbc.gridy = row;
        contentPanel.add(lblTenChuongTrinhTitle, gbc);
        lblTenChuongTrinh = new JLabel(km.getTenChuongTrinh());
        gbc.gridx = 1;
        contentPanel.add(lblTenChuongTrinh, gbc);

        // Giảm giá
        row++;
        JLabel lblGiamGiaTitle = new JLabel("Giảm giá:");
        gbc.gridx = 0;
        gbc.gridy = row;
        contentPanel.add(lblGiamGiaTitle, gbc);
        lblGiamGia = new JLabel(String.format("%.2f%%", km.getGiamGia()));
        gbc.gridx = 1;
        contentPanel.add(lblGiamGia, gbc);

        // Ngày bắt đầu
        row++;
        JLabel lblNgayBatDauTitle = new JLabel("Ngày bắt đầu:");
        gbc.gridx = 0;
        gbc.gridy = row;
        contentPanel.add(lblNgayBatDauTitle, gbc);
        lblNgayBatDau = new JLabel(dateFormat.format(km.getNgayBatDau()));
        gbc.gridx = 1;
        contentPanel.add(lblNgayBatDau, gbc);

        // Ngày kết thúc
        row++;
        JLabel lblNgayKetThucTitle = new JLabel("Ngày kết thúc:");
        gbc.gridx = 0;
        gbc.gridy = row;
        contentPanel.add(lblNgayKetThucTitle, gbc);
        lblNgayKetThuc = new JLabel(dateFormat.format(km.getNgayKetThuc()));
        gbc.gridx = 1;
        contentPanel.add(lblNgayKetThuc, gbc);

        // Giá cũ
        row++;
        JLabel lblGiaCuTitle = new JLabel("Giá cũ:");
        gbc.gridx = 0;
        gbc.gridy = row;
        contentPanel.add(lblGiaCuTitle, gbc);
        lblGiaCu = new JLabel(String.valueOf(km.getGiaCu()));
        gbc.gridx = 1;
        contentPanel.add(lblGiaCu, gbc);

        // Giá mới
        row++;
        JLabel lblGiaMoiTitle = new JLabel("Giá mới:");
        gbc.gridx = 0;
        gbc.gridy = row;
        contentPanel.add(lblGiaMoiTitle, gbc);
        lblGiaMoi = new JLabel(String.valueOf(km.getGiaMoi()));
        gbc.gridx = 1;
        contentPanel.add(lblGiaMoi, gbc);

        // Trạng thái
        row++;
        JLabel lblTrangThaiTitle = new JLabel("Trạng thái:");
        gbc.gridx = 0;
        gbc.gridy = row;
        contentPanel.add(lblTrangThaiTitle, gbc);
        lblTrangThai = new JLabel(km.getTrangThai());
        gbc.gridx = 1;
        contentPanel.add(lblTrangThai, gbc);

        // Add the content panel to the center of the dialog
        add(contentPanel, BorderLayout.CENTER);

        // Panel for the Close button (at the bottom)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dispose());
        buttonPanel.add(btnClose);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}