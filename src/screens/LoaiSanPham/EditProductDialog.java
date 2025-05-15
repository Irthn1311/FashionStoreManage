package screens.LoaiSanPham;

import DTO.sanPhamDTO;
import BUS.ProductService;
import DAO.NhaCungCapDAO;
import DTO.nhaCungCapDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class EditProductDialog extends JDialog {
    private JTextField txtMaSanPham;
    private JTextField txtTenSanPham;
    private JTextField txtMaNhaCungCap;
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
    private boolean isEditMode;
    private boolean isSaved = false;

    public EditProductDialog(Frame parent, sanPhamDTO product, boolean isEditMode) {
        super(parent, isEditMode ? "Sửa Sản Phẩm" : "Thêm Sản Phẩm Mới", true);
        this.product = product;
        this.isEditMode = isEditMode;
        this.productService = new ProductService();
        initComponents();
        if (isEditMode && product != null) {
            populateFields();
        } else {
            // Hiển thị mã sản phẩm tự động khi thêm mới
            txtMaSanPham.setText(productService.generateProductCode());
            txtMaSanPham.setEditable(false);
            txtMaSanPham.setForeground(Color.BLACK);
            txtMaSanPham.setBackground(Color.WHITE);
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setSize(500, 480);
        setLocationRelativeTo(getParent());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Mã sản phẩm
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Mã sản phẩm:"), gbc);
        gbc.gridx = 1;
        txtMaSanPham = new JTextField(20);
        txtMaSanPham.setEnabled(false);
        mainPanel.add(txtMaSanPham, gbc);

        // Tên sản phẩm
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Tên sản phẩm:"), gbc);
        gbc.gridx = 1;
        txtTenSanPham = new JTextField(20);
        mainPanel.add(txtTenSanPham, gbc);

        // Mã nhà cung cấp
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Mã nhà cung cấp:"), gbc);
        gbc.gridx = 1;
        txtMaNhaCungCap = new JTextField(20);
        if (isEditMode) {
            txtMaNhaCungCap.setEditable(false);
            txtMaNhaCungCap.setBackground(new Color(220, 220, 220));
        }
        // Add DocumentListener to txtMaNhaCungCap
        txtMaNhaCungCap.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateProductCategory();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateProductCategory();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateProductCategory();
            }

            private void updateProductCategory() {
                String maNhaCungCap = txtMaNhaCungCap.getText().trim();
                if (!maNhaCungCap.isEmpty()) {
                    NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
                    nhaCungCapDTO ncc = nhaCungCapDAO.getNhaCungCapByMa(maNhaCungCap);
                    if (ncc != null) {
                        txtMaDanhMuc.setText(ncc.getLoaiSP());
                    }
                }
            }
        });
        mainPanel.add(txtMaNhaCungCap, gbc);

        // Loại sản phẩm
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Loại sản phẩm:"), gbc);
        gbc.gridx = 1;
        txtMaDanhMuc = new JTextField(20);
        mainPanel.add(txtMaDanhMuc, gbc);

        // Màu sắc
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Màu sắc:"), gbc);
        gbc.gridx = 1;
        txtMauSac = new JTextField(20);
        mainPanel.add(txtMauSac, gbc);

        // Kích cỡ
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(new JLabel("Kích cỡ:"), gbc);
        gbc.gridx = 1;
        cbSize = new JComboBox<>(new String[] { "S", "M", "L", "XL", "XXL" });
        mainPanel.add(cbSize, gbc);

        // Số lượng tồn kho
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(new JLabel("Số lượng tồn kho:"), gbc);
        gbc.gridx = 1;
        txtSoLuongTonKho = new JTextField(20);
        mainPanel.add(txtSoLuongTonKho, gbc);

        // Giá bán
        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(new JLabel("Giá bán:"), gbc);
        gbc.gridx = 1;
        txtGiaBan = new JTextField(20);
        mainPanel.add(txtGiaBan, gbc);

        // Hình ảnh
        gbc.gridx = 0;
        gbc.gridy = 8;
        mainPanel.add(new JLabel("Hình ảnh:"), gbc);
        gbc.gridx = 1;
        txtImgURL = new JTextField(15);
        txtImgURL.setEditable(false);
        mainPanel.add(txtImgURL, gbc);

        // Nút chọn ảnh
        gbc.gridx = 2;
        btnChooseImage = new JButton("Chọn ảnh");
        btnChooseImage.setPreferredSize(new Dimension(120, 25));
        btnChooseImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseImage();
            }
        });
        mainPanel.add(btnChooseImage, gbc);

        // Trạng thái
        gbc.gridx = 0;
        gbc.gridy = 9;
        mainPanel.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx = 1;
        cbTrangThai = new JComboBox<>(new String[] { "Còn hàng", "Hết hàng" });
        mainPanel.add(cbTrangThai, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        btnSave = new JButton(isEditMode ? "Cập Nhật" : "Thêm");
        btnCancel = new JButton("Hủy");

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProduct();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
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
            try {
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }
                if (!destDir.canWrite()) {
                    JOptionPane.showMessageDialog(this, "Không có quyền ghi vào thư mục src/img_product!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (SecurityException e) {
                JOptionPane.showMessageDialog(this, "Không thể tạo thư mục src/img_product: " + e.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String fileName = selectedFile.getName();
            File destFile = new File(destDir, fileName);
            int counter = 1;
            String fileBaseName = fileName.substring(0, fileName.lastIndexOf('.'));
            String fileExtension = fileName.substring(fileName.lastIndexOf('.'));

            while (destFile.exists()) {
                String newFileName = fileBaseName + "_" + counter + fileExtension;
                destFile = new File(destDir, newFileName);
                counter++;
            }

            try {
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                txtImgURL.setText("img_product/" + destFile.getName());
                JOptionPane.showMessageDialog(this, "Hình ảnh đã được sao chép thành công vào src/img_product!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (java.io.IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi sao chép hình ảnh: " + e.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void populateFields() {
        txtMaSanPham.setText(product.getMaSanPham());
        txtTenSanPham.setText(product.getTenSanPham());
        txtMaNhaCungCap.setText(product.getMaNhaCungCap());
        txtMaDanhMuc.setText(product.getMaDanhMuc());
        txtMauSac.setText(product.getMauSac());
        cbSize.setSelectedItem(product.getSize() != null ? product.getSize() : "S");
        txtSoLuongTonKho.setText(String.valueOf(product.getSoLuongTonKho()));
        txtGiaBan.setText(String.valueOf(product.getGiaBan()));
        txtImgURL.setText(product.getImgURL());
        cbTrangThai.setSelectedItem(product.getTrangThai());
    }

    private void saveProduct() {
        try {
            String maNhaCungCap = txtMaNhaCungCap.getText().trim();

            // Validate supplier code format
            if (!maNhaCungCap.matches("NCC\\d+")) {
                JOptionPane.showMessageDialog(this,
                        "Mã nhà cung cấp '" + maNhaCungCap
                                + "' không đúng định dạng. Phải bắt đầu bằng 'NCC' (viết hoa) theo sau là số (ví dụ: NCC001).",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if supplier exists
            if (!isEditMode) {
                if (!productService.isMaNhaCungCapValid(maNhaCungCap)) {
                    JOptionPane.showMessageDialog(this,
                            "Mã nhà cung cấp '" + maNhaCungCap + "' không tồn tại trong hệ thống.",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            sanPhamDTO updatedProduct = new sanPhamDTO();
            updatedProduct.setMaSanPham(txtMaSanPham.getText().trim());
            updatedProduct.setTenSanPham(txtTenSanPham.getText().trim());
            updatedProduct.setMaNhaCungCap(maNhaCungCap);
            updatedProduct.setMaDanhMuc(txtMaDanhMuc.getText().trim());
            updatedProduct.setMauSac(txtMauSac.getText().trim());
            updatedProduct.setSize(cbSize.getSelectedItem().toString());
            updatedProduct.setSoLuongTonKho(Integer.parseInt(txtSoLuongTonKho.getText().trim()));
            updatedProduct.setGiaBan(Double.parseDouble(txtGiaBan.getText().trim()));
            updatedProduct.setImgURL(txtImgURL.getText().trim());
            updatedProduct.setTrangThai(cbTrangThai.getSelectedItem().toString());

            boolean success;
            if (isEditMode) {
                success = productService.updateProduct(updatedProduct);
            } else {
                success = productService.addProduct(updatedProduct);
            }

            if (success) {
                isSaved = true;
                JOptionPane.showMessageDialog(this,
                        isEditMode ? "Cập nhật sản phẩm thành công!" : "Thêm sản phẩm thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        isEditMode ? "Cập nhật sản phẩm thất bại!" : "Thêm sản phẩm thất bại!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho số lượng hoặc giá bán.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return isSaved;
    }
}