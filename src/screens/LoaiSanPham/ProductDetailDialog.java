package screens.LoaiSanPham;

import DTO.sanPhamDTO;
import DTO.khuyenMaiDTO;
import DAO.KhuyenMaiDAO;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

public class ProductDetailDialog extends JDialog {
    private sanPhamDTO product;
    private KhuyenMaiDAO khuyenMaiDAO;
    private SimpleDateFormat dateFormat;

    public ProductDetailDialog(Frame parent, sanPhamDTO product) {
        super(parent, "Thông Tin Chi Tiết Sản Phẩm", true);
        this.product = product;
        this.khuyenMaiDAO = new KhuyenMaiDAO();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if (product != null) {
            System.out.println("Dữ liệu sản phẩm trong ProductDetailDialog: " + product.getMaSanPham());
        } else {
            System.out.println("ProductDetailDialog: Đối tượng sản phẩm là null");
        }

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 20, 5, 20);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("THÔNG TIN CHI TIẾT SẢN PHẨM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        int row = 1;
        int maxWidth = 450;
        Font labelFont = new Font("Arial", Font.PLAIN, 12);
        Font boldLabelFont = new Font("Arial", Font.BOLD, 12);
        FontMetrics fontMetrics = mainPanel.getFontMetrics(labelFont);

        String[][] labelData = {
                { "Mã sản phẩm:", product != null ? product.getMaSanPham() : "N/A" },
                { "Tên sản phẩm:", product != null ? product.getTenSanPham() : "N/A" },
                { "Mã nhà cung cấp:", product != null ? product.getMaNhaCungCap() : "N/A" },
                { "Loại SP:", product != null ? product.getMaDanhMuc() : "N/A" },
                { "Màu sắc:", product != null ? product.getMauSac() : "N/A" },
                { "Kích cỡ:", product != null ? product.getSize() : "N/A" },
                { "Số lượng tồn kho:", product != null ? String.valueOf(product.getSoLuongTonKho()) : "N/A" },
                { "Giá bán:", product != null ? String.valueOf(product.getGiaBan()) : "N/A" },
                { "Hình ảnh:", product != null ? product.getImgURL() : "N/A" },
                { "Trạng thái:", product != null ? product.getTrangThai() : "N/A" },
                { "Khuyến mãi:", "" }
        };

        for (String[] data : labelData) {
            if (!data[0].equals("Khuyến mãi:")) {
                int labelWidth = fontMetrics.stringWidth(data[0] + " " + data[1]);
                maxWidth = Math.max(maxWidth, labelWidth + 100);
            }
        }

        for (String[] data : labelData) {
            if (!data[0].equals("Khuyến mãi:")) {
                gbc.gridx = 0;
                gbc.gridy = row;
                JLabel keyLabel = new JLabel(data[0]);
                keyLabel.setFont(boldLabelFont);
                mainPanel.add(keyLabel, gbc);

                gbc.gridx = 1;
                JLabel valueLabel = new JLabel(data[1]);
                valueLabel.setFont(labelFont);
                mainPanel.add(valueLabel, gbc);
                row++;
            }
        }

        gbc.gridx = 0;
        gbc.gridy = row++;
        JLabel promotionKeyLabel = new JLabel("Khuyến mãi:");
        promotionKeyLabel.setFont(boldLabelFont);
        mainPanel.add(promotionKeyLabel, gbc);

        gbc.gridx = 1;
        JLabel promotionLabel = new JLabel();
        promotionLabel.setFont(labelFont);
        List<khuyenMaiDTO> promotions = khuyenMaiDAO.getAllKhuyenMai() != null ? khuyenMaiDAO.getAllKhuyenMai()
                : new ArrayList<>();
        khuyenMaiDTO activePromotion = null;

        if (product != null) {
            for (khuyenMaiDTO km : promotions) {
                if (km != null && km.getMaSanPham() != null && km.getMaSanPham().equals(product.getMaSanPham())) {
                    if (km.getTrangThai() != null && km.getTrangThai().equals("Hoạt động")) {
                        activePromotion = km;
                        break;
                    } else if (km.getTrangThai() != null
                            && (km.getTrangThai().equals("Chưa bắt đầu") || km.getTrangThai().equals("Hết hạn"))) {
                        activePromotion = km;
                    }
                }
            }
        }

        if (activePromotion != null && activePromotion.getTrangThai() != null
                && activePromotion.getTrangThai().equals("Hoạt động")) {
            String promotionText = "<html>" +
                    "Sản phẩm đang<br>" +
                    "trong chương trình<br>" +
                    "khuyến mãi.<br>" +
                    "Giá trước khuyến mãi: " + activePromotion.getGiaCu() + "<br>" +
                    "Giá sau khuyến mãi: " + activePromotion.getGiaMoi() + "<br>" +
                    "Thời gian: " + dateFormat.format(activePromotion.getNgayBatDau()) + "     " + " - " +
                    "     " + dateFormat.format(activePromotion.getNgayKetThuc()) + "<br>" +
                    "</html>";
            promotionLabel.setText(promotionText);

            String plainText = "Sản phẩm đang trong chương trình khuyến mãi. Giá trước khuyến mãi: " +
                    activePromotion.getGiaCu() + " Giá sau khuyến mãi: " + activePromotion.getGiaMoi() +
                    " Thời gian: " + dateFormat.format(activePromotion.getNgayBatDau()) + " - " +
                    dateFormat.format(activePromotion.getNgayKetThuc());
            int promotionWidth = fontMetrics.stringWidth(plainText);
            maxWidth = Math.max(maxWidth, promotionWidth + 40);
        } else {
            promotionLabel.setText("Sản phẩm không có khuyến mãi.");
            int noPromoWidth = fontMetrics.stringWidth("Sản phẩm không có khuyến mãi.");
            maxWidth = Math.max(maxWidth, noPromoWidth + 40);
        }
        mainPanel.add(promotionLabel, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton btnClose = new JButton("Đóng");
        btnClose.setFont(new Font("Arial", Font.PLAIN, 12));
        btnClose.setPreferredSize(new Dimension(80, 30));
        btnClose.addActionListener(e -> dispose());
        buttonPanel.add(btnClose);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        maxWidth = Math.min(maxWidth, 550);
        setSize(maxWidth, 520);
        setLocationRelativeTo(getParent());
    }
}