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
        setPreferredSize(new Dimension(400, 360));

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
        mainPanel.add(new JLabel("Năm hợp tác:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtNamHopTac, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtDiaChi, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtSoDienThoai, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
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
    }

    private void loadNhaCungCapData() {
        txtMaNCC.setText(nhaCungCap.getMaNhaCungCap());
        txtTenNCC.setText(nhaCungCap.getTenNhaCungCap());
        txtNamHopTac.setText(String.valueOf(nhaCungCap.getNamHopTac()));
        txtDiaChi.setText(nhaCungCap.getDiaChi());
        txtEmail.setText(nhaCungCap.getEmail());
        txtSoDienThoai.setText(nhaCungCap.getSoDienThoai());
        cboTrangThai.setSelectedItem(nhaCungCap.getTrangThai());
    }

    private void capNhatNhaCungCap() {
        if (txtTenNCC.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhà cung cấp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtNamHopTac.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập năm hợp tác!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtDiaChi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập địa chỉ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtEmail.getText().trim().isEmpty() || !txtEmail.getText().trim().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtSoDienThoai.getText().trim().isEmpty() || !txtSoDienThoai.getText().trim().matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải có 10 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int namHopTacInt = Integer.parseInt(txtNamHopTac.getText().trim());
            nhaCungCapDTO nhaCungCapCapNhat = new nhaCungCapDTO(
                txtMaNCC.getText(),
                txtTenNCC.getText().trim(),
                namHopTacInt,
                txtDiaChi.getText().trim(),
                txtEmail.getText().trim(),
                txtSoDienThoai.getText().trim(),
                cboTrangThai.getSelectedItem().toString()
            );

            boolean success = nhaCungCapBUS.capNhatNhaCungCap(nhaCungCapCapNhat);
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Cập nhật thông tin nhà cung cấp thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
                parentDialog.dispose();
                if (parentDialog.getOwner() instanceof JFrame) {
                    Window ownerWindow = SwingUtilities.getWindowAncestor(parentDialog.getParent());
                    if (ownerWindow instanceof JFrame) {
                        Component[] components = ((JFrame) ownerWindow).getContentPane().getComponents();
                        for (Component component : components) {
                            if (component instanceof JTabbedPane) {
                                JTabbedPane tabbedPane = (JTabbedPane) component;
                                for(int i=0; i<tabbedPane.getTabCount(); i++){
                                    if(tabbedPane.getTitleAt(i).equals("Nhà Cung Cấp") && tabbedPane.getComponentAt(i) instanceof nhaCungCapPanel){
                                        ((nhaCungCapPanel)tabbedPane.getComponentAt(i)).refreshTableData();
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Không thể cập nhật thông tin nhà cung cấp!\nCó thể do lỗi kết nối hoặc dữ liệu không hợp lệ.",
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
                "Có lỗi xảy ra trong quá trình cập nhật: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 