package screens.LoaiSanPham;

import DTO.sanPhamDTO;
import BUS.ProductService;
import BUS.NhaCungCapBUS;
import BUS.NhaCungCap_SanPhamBUS;
import DTO.nhaCungCapDTO;
import DTO.NhaCungCap_SanPhamDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class themSanPham extends JDialog {
    private JTextField txtMaSanPham;
    private JTextField txtTenSanPham;
    private JComboBox<String> cboMaNhaCungCap;
    private JTextField txtMaDanhMuc;
    private JTextField txtMauSac;
    private JComboBox<String> cbSize;
    private JTextField txtSoLuongTonKho;
    private JTextField txtGiaBan;
    private JTextField txtImgURL;
    private JButton btnChooseImage;
    private JComboBox<String> cbTrangThai;
    private JButton btnSave;
    private JButton btnCancel;

    private sanPhamDTO product;
    private ProductService productService;
    private NhaCungCapBUS nhaCungCapBUS;
    private NhaCungCap_SanPhamBUS nhaCungCapSanPhamBUS;
    private boolean isEditMode;
    private boolean isSaved = false;

    public themSanPham(Frame parent, sanPhamDTO product, boolean isEditMode) {
        super(parent, isEditMode ? "Sửa Sản Phẩm" : "Thêm Sản Phẩm Mới", true);
        this.product = product;
        this.isEditMode = isEditMode;
        this.productService = new ProductService();
        this.nhaCungCapBUS = new NhaCungCapBUS();
        this.nhaCungCapSanPhamBUS = new NhaCungCap_SanPhamBUS();
        initComponents();
        if (isEditMode && product != null) {
            populateFields();
        } else {
            txtMaSanPham.setText(productService.generateProductCode());
            txtMaSanPham.setEditable(false);
            txtMaSanPham.setBackground(new Color(220, 220, 220));
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setSize(500, 500);
        setLocationRelativeTo(getParent());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Mã sản phẩm:"), gbc);
        gbc.gridx = 1;
        txtMaSanPham = new JTextField(20);
        txtMaSanPham.setEditable(false);
        txtMaSanPham.setBackground(new Color(220, 220, 220));
        mainPanel.add(txtMaSanPham, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Tên sản phẩm: *"), gbc);
        gbc.gridx = 1;
        txtTenSanPham = new JTextField(20);
        mainPanel.add(txtTenSanPham, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Mã nhà cung cấp: *"), gbc);
        gbc.gridx = 1;
        cboMaNhaCungCap = new JComboBox<>();
        List<String> supplierCodes = nhaCungCapBUS.getAllSuppliers();
        if (supplierCodes != null) {
            for (String code : supplierCodes) {
                cboMaNhaCungCap.addItem(code);
            }
        }
        mainPanel.add(cboMaNhaCungCap, gbc);
        if (isEditMode) {
            cboMaNhaCungCap.setEnabled(false);
        }

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Loại sản phẩm (Mã DM): *"), gbc);
        gbc.gridx = 1;
        txtMaDanhMuc = new JTextField(20);
        mainPanel.add(txtMaDanhMuc, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Màu sắc: *"), gbc);
        gbc.gridx = 1;
        txtMauSac = new JTextField(20);
        mainPanel.add(txtMauSac, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(new JLabel("Kích cỡ:"), gbc);
        gbc.gridx = 1;
        cbSize = new JComboBox<>(new String[] { "S", "M", "L", "XL", "XXL", "Free size", "Khác" });
        mainPanel.add(cbSize, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(new JLabel("Số lượng tồn kho: *"), gbc);
        gbc.gridx = 1;
        txtSoLuongTonKho = new JTextField(20);
        mainPanel.add(txtSoLuongTonKho, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(new JLabel("Giá bán: *"), gbc);
        gbc.gridx = 1;
        txtGiaBan = new JTextField(20);
        mainPanel.add(txtGiaBan, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        mainPanel.add(new JLabel("Hình ảnh:"), gbc);
        JPanel imagePanel = new JPanel(new BorderLayout(5,0));
        txtImgURL = new JTextField(15);
        txtImgURL.setEditable(false);
        imagePanel.add(txtImgURL, BorderLayout.CENTER);
        btnChooseImage = new JButton("Chọn");
        btnChooseImage.addActionListener(e -> chooseImage());
        imagePanel.add(btnChooseImage, BorderLayout.EAST);
        gbc.gridx = 1;
        mainPanel.add(imagePanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        mainPanel.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx = 1;
        cbTrangThai = new JComboBox<>(new String[] { "Còn hàng", "Hết hàng" });
        mainPanel.add(cbTrangThai, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnSave = new JButton(isEditMode ? "Cập Nhật" : "Thêm");
        btnCancel = new JButton("Hủy");
        Dimension btnDim = new Dimension(100,30);
        btnSave.setPreferredSize(btnDim);
        btnCancel.setPreferredSize(btnDim);

        btnSave.addActionListener(e -> saveProduct());
        btnCancel.addActionListener(e -> dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser("src/img_product");
        fileChooser.setFileFilter(
                new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            long fileSizeInBytes = selectedFile.length();
            long fileSizeInMB = fileSizeInBytes / (1024 * 1024);
            if (fileSizeInMB > 5) {
                JOptionPane.showMessageDialog(this, "File hình ảnh quá lớn! Vui lòng chọn file nhỏ hơn 5MB.", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            File destDir = new File("src/img_product");
            if (!destDir.exists()) {
                if (!destDir.mkdirs()) {
                     JOptionPane.showMessageDialog(this, "Không thể tạo thư mục src/img_product!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                     return;
                }
            }
            
            Path sourcePath = selectedFile.toPath();
            String relativeDestPath = "img_product/" + selectedFile.getName();
            Path destPath = new File(relativeDestPath).toPath(); 
            Path actualDestPathInSrc = new File("src/" + relativeDestPath).toPath();

            try {
                Files.copy(sourcePath, actualDestPathInSrc, StandardCopyOption.REPLACE_EXISTING);
                txtImgURL.setText(relativeDestPath);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu hình ảnh: " + ex.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void populateFields() {
        if (product == null) return;
        txtMaSanPham.setText(product.getMaSanPham());
        txtTenSanPham.setText(product.getTenSanPham());
        cboMaNhaCungCap.setSelectedItem(product.getMaNhaCungCap());
        txtMaDanhMuc.setText(product.getMaDanhMuc());
        txtMauSac.setText(product.getMauSac());
        cbSize.setSelectedItem(product.getSize());
        txtSoLuongTonKho.setText(String.valueOf(product.getSoLuongTonKho()));
        txtGiaBan.setText(String.valueOf(product.getGiaBan()));
        txtImgURL.setText(product.getImgURL());
        cbTrangThai.setSelectedItem(product.getTrangThai());
    }

    private void saveProduct() {
        String maSanPham = txtMaSanPham.getText().trim();
        String tenSanPham = txtTenSanPham.getText().trim();
        String maNhaCungCap = (cboMaNhaCungCap.getSelectedItem() != null) ? cboMaNhaCungCap.getSelectedItem().toString() : null;
        String maDanhMuc = txtMaDanhMuc.getText().trim();
        String mauSac = txtMauSac.getText().trim();
        String size = cbSize.getSelectedItem().toString();
        String soLuongStr = txtSoLuongTonKho.getText().trim();
        String giaBanStr = txtGiaBan.getText().trim();
        String imgURL = txtImgURL.getText().trim();
        String trangThai = cbTrangThai.getSelectedItem().toString();

        if (tenSanPham.isEmpty() || maNhaCungCap == null || maNhaCungCap.isEmpty() || maDanhMuc.isEmpty() || mauSac.isEmpty() || 
            soLuongStr.isEmpty() || giaBanStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ các trường bắt buộc (*).", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int soLuongTonKho;
        double giaBan;
        try {
            soLuongTonKho = Integer.parseInt(soLuongStr);
            if (soLuongTonKho < 0) throw new NumberFormatException("Số lượng không thể âm");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng tồn kho không hợp lệ: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            giaBan = Double.parseDouble(giaBanStr);
            if (giaBan < 0) throw new NumberFormatException("Giá bán không thể âm");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá bán không hợp lệ: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        sanPhamDTO newProduct = new sanPhamDTO(
            maSanPham, tenSanPham, maNhaCungCap, maDanhMuc, mauSac, 
            size, soLuongTonKho, giaBan, imgURL, trangThai
        );

        boolean success = false;
        try {
            if (isEditMode) {
                success = productService.updateProduct(newProduct);
            } else {
                success = productService.addProduct(newProduct);
                if (success) {
                    NhaCungCap_SanPhamDTO nccspLink = new NhaCungCap_SanPhamDTO(maNhaCungCap, maSanPham);
                    if (!nhaCungCapSanPhamBUS.themNhaCungCap_SanPham(nccspLink)) {
                        System.err.println("Lỗi: Không thể thêm liên kết Nhà Cung Cấp - Sản Phẩm cho " + maSanPham + " và " + maNhaCungCap);
                    }
                }
            }

            if (success) {
                isSaved = true;
                JOptionPane.showMessageDialog(this, 
                    (isEditMode ? "Cập nhật" : "Thêm") + " sản phẩm thành công!", 
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    (isEditMode ? "Cập nhật" : "Thêm") + " sản phẩm thất bại!\nKiểm tra lại Mã SP (không trùng nếu thêm mới) và các dữ liệu khác.", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi lưu sản phẩm: " + e.getMessage(), 
                "Lỗi Hệ Thống", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public boolean isSaved() {
        return isSaved;
    }
}