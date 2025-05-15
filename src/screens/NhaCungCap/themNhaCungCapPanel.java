package screens.NhaCungCap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import DTO.nhaCungCapDTO;
import BUS.NhaCungCapBUS;

public class themNhaCungCapPanel extends JPanel {
    private JTextField txtMaNCC;
    private JTextField txtTenNCC;
    private JTextField txtNamHopTac;
    private JTextField txtDiaChi;
    private JTextField txtEmail;
    private JTextField txtSoDienThoai;
    private JComboBox<String> cbTrangThai;
    private JButton btnThem;
    private JButton btnHuy;
    private NhaCungCapBUS nhaCungCapBUS;

    public themNhaCungCapPanel() {
        nhaCungCapBUS = new NhaCungCapBUS();
        initComponents();
        setupListeners();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 450));

        // Panel chính
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Tiêu đề
        JLabel lblTitle = new JLabel("Thêm Nhà Cung Cấp Mới", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(lblTitle, gbc);
        gbc.gridwidth = 1;

        // Mã NCC (required)
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblMaNCC = new JLabel("Mã nhà cung cấp: *");
        lblMaNCC.setForeground(Color.RED);
        mainPanel.add(lblMaNCC, gbc);
        txtMaNCC = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtMaNCC, gbc);

        // Tên NCC (required)
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblTenNCC = new JLabel("Tên nhà cung cấp: *");
        lblTenNCC.setForeground(Color.RED);
        mainPanel.add(lblTenNCC, gbc);
        txtTenNCC = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtTenNCC, gbc);

        // Năm hợp tác (required)
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblNamHopTac = new JLabel("Năm hợp tác: *");
        lblNamHopTac.setForeground(Color.RED);
        mainPanel.add(lblNamHopTac, gbc);
        txtNamHopTac = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtNamHopTac, gbc);

        // Địa chỉ (required)
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblDiaChi = new JLabel("Địa chỉ: *");
        lblDiaChi.setForeground(Color.RED);
        mainPanel.add(lblDiaChi, gbc);
        txtDiaChi = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtDiaChi, gbc);

        // Email (required)
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblEmail = new JLabel("Email: *");
        lblEmail.setForeground(Color.RED);
        mainPanel.add(lblEmail, gbc);
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtEmail, gbc);

        // Số điện thoại (required)
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblSoDienThoai = new JLabel("Số điện thoại: *");
        lblSoDienThoai.setForeground(Color.RED);
        mainPanel.add(lblSoDienThoai, gbc);
        txtSoDienThoai = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtSoDienThoai, gbc);

        // Trạng thái
        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Trạng thái:"), gbc);
        cbTrangThai = new JComboBox<>(new String[]{"Đang hợp tác", "Ngừng hợp tác"});
        gbc.gridx = 1;
        mainPanel.add(cbTrangThai, gbc);

        // Panel chứa nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnThem = new JButton("Thêm");
        btnHuy = new JButton("Hủy");
        
        // Tùy chỉnh kích thước nút
        Dimension buttonSize = new Dimension(100, 35);
        btnThem.setPreferredSize(buttonSize);
        btnHuy.setPreferredSize(buttonSize);
        
        buttonPanel.add(btnThem);
        buttonPanel.add(btnHuy);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        mainPanel.add(buttonPanel, gbc);

        // Thêm panel chính vào center
        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupListeners() {
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                themNhaCungCap();
            }
        });

        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                huyThem();
            }
        });
    }

    private void themNhaCungCap() {
        // Lấy dữ liệu từ các trường nhập liệu
        String maNCC = txtMaNCC.getText().trim();
        String tenNCC = txtTenNCC.getText().trim();
        String namHopTac = txtNamHopTac.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        String email = txtEmail.getText().trim();
        String soDienThoai = txtSoDienThoai.getText().trim();
        String trangThai = (String) cbTrangThai.getSelectedItem();

        // Kiểm tra dữ liệu đầu vào
        if (maNCC.isEmpty() || tenNCC.isEmpty() || namHopTac.isEmpty() || diaChi.isEmpty() || email.isEmpty() || soDienThoai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra email hợp lệ
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra số điện thoại hợp lệ (10 số)
        if (!soDienThoai.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải có 10 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tạo đối tượng nhaCungCapDTO
        try {
            int namHopTacInt = Integer.parseInt(namHopTac);
            nhaCungCapDTO ncc = new nhaCungCapDTO(
                maNCC,
                tenNCC,
                namHopTacInt,
                diaChi,
                email,
                soDienThoai,
                trangThai
            );

            // Thêm nhà cung cấp vào cơ sở dữ liệu
            if (nhaCungCapBUS.themNhaCungCap(ncc)) {
                JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window instanceof JDialog) {
                    ((JDialog) window).dispose();
                } else if (window instanceof JFrame && window.getParent() instanceof JDialog) {
                    ((JDialog) window.getParent()).dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp thất bại! Kiểm tra lại mã NCC (không được trùng) hoặc định dạng dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Năm hợp tác phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void huyThem() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn hủy thêm nhà cung cấp?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            // Đóng dialog
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        }
    }

    private void clearFields() {
        txtMaNCC.setText("");
        txtTenNCC.setText("");
        txtNamHopTac.setText("");
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtSoDienThoai.setText("");
        cbTrangThai.setSelectedIndex(0);
    }
} 