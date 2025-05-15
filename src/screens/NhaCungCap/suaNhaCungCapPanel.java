package screens.NhaCungCap;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import DTO.nhaCungCapDTO;
import BUS.NhaCungCapBUS;

public class suaNhaCungCapPanel extends JPanel {
    private nhaCungCapDTO nhaCungCap;
    private NhaCungCapBUS nhaCungCapBUS;
    private JDialog parentDialog;

    // Components
    private JTextField txtMaNCC;
    private JTextField txtTenNCC;
    private JTextField txtLoaiSP;
    private JTextField txtNamHopTac;
    private JTextField txtDiaChi;
    private JTextField txtEmail;
    private JTextField txtSoDienThoai;
    private JComboBox<String> cboTrangThai;
    private JButton btnCapNhat;
    private JButton btnHuy;

    public suaNhaCungCapPanel(JDialog parent, nhaCungCapDTO nhaCungCap) {
        this.parentDialog = parent;
        this.nhaCungCap = nhaCungCap;
        this.nhaCungCapBUS = new NhaCungCapBUS();

        initComponents();
        loadNhaCungCapData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel chính
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Sửa Thông Tin Nhà Cung Cấp",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Khởi tạo components
        txtMaNCC = new JTextField(20);
        txtTenNCC = new JTextField(20);
        txtLoaiSP = new JTextField(20);
        txtNamHopTac = new JTextField(20);
        txtDiaChi = new JTextField(20);
        txtEmail = new JTextField(20);
        txtSoDienThoai = new JTextField(20);
        cboTrangThai = new JComboBox<>(new String[]{"Đang hợp tác", "Ngừng hợp tác"});

        // Thiết lập các trường không được sửa
        txtMaNCC.setEditable(false);

        // Thêm components vào panel
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Mã nhà cung cấp:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtMaNCC, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Tên nhà cung cấp:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtTenNCC, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Loại sản phẩm:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtLoaiSP, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Năm hợp tác:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtNamHopTac, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtDiaChi, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        mainPanel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtSoDienThoai, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        mainPanel.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(cboTrangThai, gbc);

        // Panel chứa buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnCapNhat = new JButton("Cập Nhật");
        btnHuy = new JButton("Hủy");

        btnCapNhat.setPreferredSize(new Dimension(100, 30));
        btnHuy.setPreferredSize(new Dimension(100, 30));

        buttonPanel.add(btnCapNhat);
        buttonPanel.add(btnHuy);

        // Thêm sự kiện cho buttons
        btnCapNhat.addActionListener(e -> capNhatNhaCungCap());
        btnHuy.addActionListener(e -> parentDialog.dispose());

        // Thêm panels vào panel chính
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set size
        setPreferredSize(new Dimension(400, 400));
    }

    private void loadNhaCungCapData() {
        txtMaNCC.setText(nhaCungCap.getMaNhaCungCap());
        txtTenNCC.setText(nhaCungCap.getTenNhaCungCap());
        txtLoaiSP.setText(nhaCungCap.getLoaiSP());
        txtNamHopTac.setText(String.valueOf(nhaCungCap.getNamHopTac()));
        txtDiaChi.setText(nhaCungCap.getDiaChi());
        txtEmail.setText(nhaCungCap.getEmail());
        txtSoDienThoai.setText(nhaCungCap.getSoDienThoai());
        cboTrangThai.setSelectedItem(nhaCungCap.getTrangThai());
    }

    private void capNhatNhaCungCap() {
        try {
            // Validate dữ liệu
            if (txtTenNCC.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhà cung cấp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (txtEmail.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập email!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (txtSoDienThoai.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Parse năm hợp tác
            String namHopTac = txtNamHopTac.getText().trim();
            if (namHopTac.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập năm hợp tác!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo đối tượng nhaCungCapDTO mới với thông tin đã cập nhật
            try {
                int namHopTacInt = Integer.parseInt(namHopTac);
                nhaCungCapDTO nhaCungCapCapNhat = new nhaCungCapDTO(
                    txtMaNCC.getText(),
                    txtTenNCC.getText().trim(),
                    txtLoaiSP.getText().trim(),
                    namHopTacInt,
                    txtDiaChi.getText().trim(),
                    txtEmail.getText().trim(),
                    txtSoDienThoai.getText().trim(),
                    cboTrangThai.getSelectedItem().toString()
                );

                // Gọi BUS để cập nhật
                boolean success = nhaCungCapBUS.capNhatNhaCungCap(nhaCungCapCapNhat);
                if (success) {
                    JOptionPane.showMessageDialog(this,
                        "Cập nhật thông tin nhà cung cấp thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                    parentDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Không thể cập nhật thông tin nhà cung cấp!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                    "Năm hợp tác phải là số nguyên!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                    "Có lỗi xảy ra: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Có lỗi xảy ra: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 