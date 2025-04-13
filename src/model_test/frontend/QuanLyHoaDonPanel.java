package model.frontend;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.*;
import java.text.*;
import java.io.File;

public class QuanLyHoaDonPanel extends JPanel {
    // Declare components for search panel
    private JPanel pnlTimKiem;
    private JLabel lblMaSP;
    private JLabel lblNgayXH;
    private JLabel lblTrangThai;
    private JTextField txtMaSP;
    private JTextField txtNgayXH;
    private JTextField txtTrangThai;
    private JButton btnTimKiem;
    
    // Declare components for action panel
    private JPanel pnlChinhSua;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    
    // Declare components for data table
    private JPanel pnlBangThongTin;
    private JTable tblHoaDon;
    private JScrollPane scrHoaDon;
    
    // Declare components for add panel
    private JPanel pnlBangThem;
    private JLabel lblMaSPThem;
    private JLabel lblTenSPThem;
    private JLabel lblSoLuongThem;
    private JLabel lblThanhTienThem;
    private JLabel lblNgayXHThem;
    private JLabel lblGioXHThem;
    private JLabel lblTrangThaiThem;
    private JTextField txtMaSPThem;
    private JTextField txtTenSPThem;
    private JTextField txtSoLuongThem;
    private JTextField txtThanhTienThem;
    private JTextField txtNgayXHThem;
    private JTextField txtGioXHThem;
    private JTextField txtTrangThaiThem;
    private JButton btnHuyThem;
    private JButton btnThemConfirm;
    
    // Declare components for edit panel
    private JPanel pnlBangSua;
    private JLabel lblMaSPSua;
    private JLabel lblTenSPSua;
    private JLabel lblSoLuongSua;
    private JLabel lblThanhTienSua;
    private JLabel lblNgayXHSua;
    private JLabel lblGioXHSua;
    private JLabel lblTrangThaiSua;
    private JTextField txtMaSPSua;
    private JTextField txtTenSPSua;
    private JTextField txtSoLuongSua;
    private JTextField txtThanhTienSua;
    private JTextField txtNgayXHSua;
    private JTextField txtGioXHSua;
    private JTextField txtTrangThaiSua;
    private JButton btnHuySua;
    private JButton btnSuaConfirm;
    
    // Declare export button
    private JButton btnXuatFileXuatHang;
    
    // Constructor
    public QuanLyHoaDonPanel() {
        initComponents();
    }
    
    private void initComponents() {
        // Set panel properties
        setLayout(new BorderLayout(0, 5));
        setBackground(new Color(173, 216, 230)); // Light blue background
        
        // Create header panel
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(0, 128, 128)); // Teal color
        pnlHeader.setPreferredSize(new Dimension(0, 60));
        pnlHeader.setLayout(new BorderLayout());
        
        // Add header label
        JLabel lblHeader = new JLabel("QUẢN LÝ HÓA ĐƠN");
        lblHeader.setFont(new Font("Arial", Font.BOLD, 24));
        lblHeader.setForeground(Color.BLACK);
        lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
        pnlHeader.add(lblHeader, BorderLayout.CENTER);
        
        // Add header to main panel
        add(pnlHeader, BorderLayout.NORTH);
        
        // Create content panel
        JPanel pnlContent = new JPanel();
        pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
        pnlContent.setBackground(new Color(173, 216, 230)); // Light blue
        
        // Create search panel
        createSearchPanel();
        pnlContent.add(pnlTimKiem);
        pnlContent.add(Box.createRigidArea(new Dimension(0, 5)));
        
        // Create action panel
        createActionPanel();
        pnlContent.add(pnlChinhSua);
        pnlContent.add(Box.createRigidArea(new Dimension(0, 5)));
        
        // Create table panel
        createTablePanel();
        pnlContent.add(pnlBangThongTin);
        pnlContent.add(Box.createRigidArea(new Dimension(0, 5)));
        
        // Create add panel
        createAddPanel();
        pnlContent.add(pnlBangThem);
        pnlContent.add(Box.createRigidArea(new Dimension(0, 5)));
        
        // Create edit panel
        createEditPanel();
        pnlContent.add(pnlBangSua);
        pnlContent.add(Box.createRigidArea(new Dimension(0, 5)));
        
        // Create export button
        createExportButton();
        JPanel pnlExport = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlExport.setBackground(new Color(173, 216, 230));
        pnlExport.add(btnXuatFileXuatHang);
        pnlContent.add(pnlExport);
        
        // Add content panel to main panel
        add(pnlContent, BorderLayout.CENTER);
    }
    
    private void createSearchPanel() {
        pnlTimKiem = new JPanel();
        pnlTimKiem.setLayout(new BoxLayout(pnlTimKiem, BoxLayout.X_AXIS));
        pnlTimKiem.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK), 
            "Tìm kiếm", 
            TitledBorder.DEFAULT_JUSTIFICATION, 
            TitledBorder.DEFAULT_POSITION, 
            new Font("Arial", Font.PLAIN, 12)
        ));
        pnlTimKiem.setBackground(new Color(117, 170, 198)); // Darker blue
        
        // Create panels for each search field
        JPanel pnlMaSP = new JPanel(new BorderLayout());
        pnlMaSP.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Mã SP",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.PLAIN, 12)
        ));
        pnlMaSP.setBackground(new Color(117, 170, 198));
        
        JPanel pnlNgayXH = new JPanel(new BorderLayout());
        pnlNgayXH.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Ngày XH",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.PLAIN, 12)
        ));
        pnlNgayXH.setBackground(new Color(117, 170, 198));
        
        JPanel pnlTrangThai = new JPanel(new BorderLayout());
        pnlTrangThai.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Trạng thái",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.PLAIN, 12)
        ));
        pnlTrangThai.setBackground(new Color(117, 170, 198));
        
        // Create and add components
        lblMaSP = new JLabel("Nhập mã SP");
        txtMaSP = new JTextField(15);
        
        lblNgayXH = new JLabel("Ngày XH");
        txtNgayXH = new JTextField(15);
        
        lblTrangThai = new JLabel("Trạng thái");
        txtTrangThai = new JTextField(15);
        
        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setBackground(Color.WHITE);
        btnTimKiem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });
        
        // Add components to panels
        pnlMaSP.add(txtMaSP, BorderLayout.CENTER);
        pnlNgayXH.add(txtNgayXH, BorderLayout.CENTER);
        pnlTrangThai.add(txtTrangThai, BorderLayout.CENTER);
        
        // Add panels to search panel
        pnlTimKiem.add(Box.createHorizontalStrut(10));
        pnlTimKiem.add(pnlMaSP);
        pnlTimKiem.add(Box.createHorizontalStrut(10));
        pnlTimKiem.add(pnlNgayXH);
        pnlTimKiem.add(Box.createHorizontalStrut(10));
        pnlTimKiem.add(pnlTrangThai);
        pnlTimKiem.add(Box.createHorizontalStrut(20));
        pnlTimKiem.add(btnTimKiem);
        pnlTimKiem.add(Box.createHorizontalStrut(10));
    }
    
    private void createActionPanel() {
        pnlChinhSua = new JPanel();
        pnlChinhSua.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        pnlChinhSua.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Chỉnh sửa",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.PLAIN, 12)
        ));
        pnlChinhSua.setBackground(new Color(117, 170, 198)); // Darker blue
        
        // Create buttons
        btnThem = new JButton("Thêm");
        btnThem.setPreferredSize(new Dimension(100, 40));
        btnThem.setBackground(Color.WHITE);
        btnThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        
        btnSua = new JButton("Sửa");
        btnSua.setPreferredSize(new Dimension(100, 40));
        btnSua.setBackground(Color.WHITE);
        btnSua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        
        btnXoa = new JButton("Xóa");
        btnXoa.setPreferredSize(new Dimension(100, 40));
        btnXoa.setBackground(Color.WHITE);
        btnXoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        
        // Add buttons to panel
        pnlChinhSua.add(btnThem);
        pnlChinhSua.add(btnSua);
        pnlChinhSua.add(btnXoa);
    }
    
    private void createTablePanel() {
        pnlBangThongTin = new JPanel(new BorderLayout());
        pnlBangThongTin.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Bảng thông tin",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.PLAIN, 12)
        ));
        pnlBangThongTin.setBackground(new Color(117, 170, 198)); // Darker blue
        
        // Create table model
        String[] columnNames = {"STT", "Mã SP", "Tên SP", "Số lượng", "Thành tiền", "Ngày XH", "Giờ XH", "Trạng Thái", "Chi tiết"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        // Create table
        tblHoaDon = new JTable(model);
        tblHoaDon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblHoaDon.setGridColor(Color.BLACK);
        tblHoaDon.setRowHeight(25);
        
        // Create scroll pane
        scrHoaDon = new JScrollPane(tblHoaDon);
        scrHoaDon.setPreferredSize(new Dimension(0, 150));
        
        // Add scroll pane to panel
        pnlBangThongTin.add(scrHoaDon, BorderLayout.CENTER);
    }
    
    private void createAddPanel() {
        pnlBangThem = new JPanel();
        pnlBangThem.setLayout(new GridBagLayout());
        pnlBangThem.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Bảng thêm",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.PLAIN, 12)
        ));
        pnlBangThem.setBackground(new Color(117, 170, 198)); // Darker blue
        
        // Create components
        lblMaSPThem = new JLabel("Mã SP");
        lblTenSPThem = new JLabel("Tên SP");
        lblSoLuongThem = new JLabel("Số lượng");
        lblThanhTienThem = new JLabel("Thành tiền");
        lblNgayXHThem = new JLabel("Ngày XH");
        lblGioXHThem = new JLabel("Giờ XH");
        lblTrangThaiThem = new JLabel("Trạng thái");
        
        txtMaSPThem = new JTextField(10);
        txtTenSPThem = new JTextField(10);
        txtSoLuongThem = new JTextField(10);
        txtThanhTienThem = new JTextField(10);
        txtNgayXHThem = new JTextField(10);
        txtGioXHThem = new JTextField(10);
        txtTrangThaiThem = new JTextField(10);
        
        btnHuyThem = new JButton("Hủy");
        btnHuyThem.setBackground(Color.WHITE);
        btnHuyThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnHuyThemActionPerformed(evt);
            }
        });
        
        btnThemConfirm = new JButton("Thêm");
        btnThemConfirm.setBackground(Color.WHITE);
        btnThemConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnThemConfirmActionPerformed(evt);
            }
        });
        
        // Set up GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Add components to panel using GridBagLayout
        // First row - Labels
        gbc.gridy = 0;
        
        gbc.gridx = 0;
        pnlBangThem.add(lblMaSPThem, gbc);
        
        gbc.gridx = 1;
        pnlBangThem.add(lblTenSPThem, gbc);
        
        gbc.gridx = 2;
        pnlBangThem.add(lblSoLuongThem, gbc);
        
        gbc.gridx = 3;
        pnlBangThem.add(lblThanhTienThem, gbc);
        
        gbc.gridx = 4;
        pnlBangThem.add(lblNgayXHThem, gbc);
        
        gbc.gridx = 5;
        pnlBangThem.add(lblGioXHThem, gbc);
        
        gbc.gridx = 6;
        pnlBangThem.add(lblTrangThaiThem, gbc);
        
        // Second row - TextFields and Buttons
        gbc.gridy = 1;
        
        gbc.gridx = 0;
        pnlBangThem.add(txtMaSPThem, gbc);
        
        gbc.gridx = 1;
        pnlBangThem.add(txtTenSPThem, gbc);
        
        gbc.gridx = 2;
        pnlBangThem.add(txtSoLuongThem, gbc);
        
        gbc.gridx = 3;
        pnlBangThem.add(txtThanhTienThem, gbc);
        
        gbc.gridx = 4;
        pnlBangThem.add(txtNgayXHThem, gbc);
        
        gbc.gridx = 5;
        pnlBangThem.add(txtGioXHThem, gbc);
        
        gbc.gridx = 6;
        pnlBangThem.add(txtTrangThaiThem, gbc);
        
        // Third row - Buttons
        gbc.gridy = 0;
        gbc.gridx = 7;
        pnlBangThem.add(btnHuyThem, gbc);
        
        gbc.gridy = 1;
        gbc.gridx = 7;
        pnlBangThem.add(btnThemConfirm, gbc);
    }
    
    private void createEditPanel() {
        pnlBangSua = new JPanel();
        pnlBangSua.setLayout(new GridBagLayout());
        pnlBangSua.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Bảng Sửa",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.PLAIN, 12)
        ));
        pnlBangSua.setBackground(new Color(117, 170, 198)); // Darker blue
        
        // Create components
        lblMaSPSua = new JLabel("Mã SP");
        lblTenSPSua = new JLabel("Tên SP");
        lblSoLuongSua = new JLabel("Số lượng");
        lblThanhTienSua = new JLabel("Thành tiền");
        lblNgayXHSua = new JLabel("Ngày XH");
        lblGioXHSua = new JLabel("Giờ XH");
        lblTrangThaiSua = new JLabel("Trạng thái");
        
        txtMaSPSua = new JTextField(10);
        txtTenSPSua = new JTextField(10);
        txtSoLuongSua = new JTextField(10);
        txtThanhTienSua = new JTextField(10);
        txtNgayXHSua = new JTextField(10);
        txtGioXHSua = new JTextField(10);
        txtTrangThaiSua = new JTextField(10);
        
        btnHuySua = new JButton("Hủy");
        btnHuySua.setBackground(Color.WHITE);
        btnHuySua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnHuySuaActionPerformed(evt);
            }
        });
        
        btnSuaConfirm = new JButton("Sửa");
        btnSuaConfirm.setBackground(Color.WHITE);
        btnSuaConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSuaConfirmActionPerformed(evt);
            }
        });
        
        // Set up GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Add components to panel using GridBagLayout
        // First row - Labels
        gbc.gridy = 0;
        
        gbc.gridx = 0;
        pnlBangSua.add(lblMaSPSua, gbc);
        
        gbc.gridx = 1;
        pnlBangSua.add(lblTenSPSua, gbc);
        
        gbc.gridx = 2;
        pnlBangSua.add(lblSoLuongSua, gbc);
        
        gbc.gridx = 3;
        pnlBangSua.add(lblThanhTienSua, gbc);
        
        gbc.gridx = 4;
        pnlBangSua.add(lblNgayXHSua, gbc);
        
        gbc.gridx = 5;
        pnlBangSua.add(lblGioXHSua, gbc);
        
        gbc.gridx = 6;
        pnlBangSua.add(lblTrangThaiSua, gbc);
        
        // Second row - TextFields and Buttons
        gbc.gridy = 1;
        
        gbc.gridx = 0;
        pnlBangSua.add(txtMaSPSua, gbc);
        
        gbc.gridx = 1;
        pnlBangSua.add(txtTenSPSua, gbc);
        
        gbc.gridx = 2;
        pnlBangSua.add(txtSoLuongSua, gbc);
        
        gbc.gridx = 3;
        pnlBangSua.add(txtThanhTienSua, gbc);
        
        gbc.gridx = 4;
        pnlBangSua.add(txtNgayXHSua, gbc);
        
        gbc.gridx = 5;
        pnlBangSua.add(txtGioXHSua, gbc);
        
        gbc.gridx = 6;
        pnlBangSua.add(txtTrangThaiSua, gbc);
        
        // Third row - Buttons
        gbc.gridy = 0;
        gbc.gridx = 7;
        pnlBangSua.add(btnHuySua, gbc);
        
        gbc.gridy = 1;
        gbc.gridx = 7;
        pnlBangSua.add(btnSuaConfirm, gbc);
    }
    
    private void createExportButton() {
        btnXuatFileXuatHang = new JButton("Xuất file xuất hàng");
        btnXuatFileXuatHang.setPreferredSize(new Dimension(200, 40));
        btnXuatFileXuatHang.setBackground(Color.WHITE);
        btnXuatFileXuatHang.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnXuatFileXuatHangActionPerformed(evt);
            }
        });
    }
    
    // Event handler methods
    private void btnTimKiemActionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Chức năng tìm kiếm được kích hoạt");
        // Implement search functionality
    }
    
    private void btnThemActionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Chức năng thêm được kích hoạt");
        // Show add panel, hide edit panel
        pnlBangThem.setVisible(true);
        pnlBangSua.setVisible(false);
    }
    
    private void btnSuaActionPerformed(ActionEvent evt) {
        int selectedRow = tblHoaDon.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this, "Chức năng sửa được kích hoạt");
        // Show edit panel, hide add panel
        pnlBangSua.setVisible(true);
        pnlBangThem.setVisible(false);
        
        // Load data to edit panel
        // Implementation would depend on actual data model
    }
    
    private void btnXoaActionPerformed(ActionEvent evt) {
        int selectedRow = tblHoaDon.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa dòng này?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
            model.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Đã xóa thành công");
        }
    }
    
    private void btnThemConfirmActionPerformed(ActionEvent evt) {
        // Validate inputs
        if (txtMaSPThem.getText().trim().isEmpty() || 
            txtTenSPThem.getText().trim().isEmpty() ||
            txtSoLuongThem.getText().trim().isEmpty() ||
            txtThanhTienThem.getText().trim().isEmpty() ||
            txtNgayXHThem.getText().trim().isEmpty() ||
            txtGioXHThem.getText().trim().isEmpty() ||
            txtTrangThaiThem.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Add new row to table
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        Object[] rowData = {
            model.getRowCount() + 1, // STT
            txtMaSPThem.getText(),
            txtTenSPThem.getText(),
            txtSoLuongThem.getText(),
            txtThanhTienThem.getText(),
            txtNgayXHThem.getText(),
            txtGioXHThem.getText(),
            txtTrangThaiThem.getText(),
            "Xem chi tiết" // Chi tiết column
        };
        model.addRow(rowData);
        
        // Clear input fields
        txtMaSPThem.setText("");
        txtTenSPThem.setText("");
        txtSoLuongThem.setText("");
        txtThanhTienThem.setText("");
        txtNgayXHThem.setText("");
        txtGioXHThem.setText("");
        txtTrangThaiThem.setText("");
        
        JOptionPane.showMessageDialog(this, "Thêm mới thành công");
    }
    
    private void btnHuyThemActionPerformed(ActionEvent evt) {
        // Clear input fields
        txtMaSPThem.setText("");
        txtTenSPThem.setText("");
        txtSoLuongThem.setText("");
        txtThanhTienThem.setText("");
        txtNgayXHThem.setText("");
        txtGioXHThem.setText("");
        txtTrangThaiThem.setText("");
        
        // Hide add panel
        pnlBangThem.setVisible(false);
    }
    
    private void btnSuaConfirmActionPerformed(ActionEvent evt) {
        int selectedRow = tblHoaDon.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validate inputs
        if (txtMaSPSua.getText().trim().isEmpty() || 
            txtTenSPSua.getText().trim().isEmpty() ||
            txtSoLuongSua.getText().trim().isEmpty() ||
            txtThanhTienSua.getText().trim().isEmpty() ||
            txtNgayXHSua.getText().trim().isEmpty() ||
            txtGioXHSua.getText().trim().isEmpty() ||
            txtTrangThaiSua.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Update row in table
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setValueAt(txtMaSPSua.getText(), selectedRow, 1);
        model.setValueAt(txtTenSPSua.getText(), selectedRow, 2);
        model.setValueAt(txtSoLuongSua.getText(), selectedRow, 3);
        model.setValueAt(txtThanhTienSua.getText(), selectedRow, 4);
        model.setValueAt(txtNgayXHSua.getText(), selectedRow, 5);
        model.setValueAt(txtGioXHSua.getText(), selectedRow, 6);
        model.setValueAt(txtTrangThaiSua.getText(), selectedRow, 7);
        
        // Clear input fields
        txtMaSPSua.setText("");
        txtTenSPSua.setText("");
        txtSoLuongSua.setText("");
        txtThanhTienSua.setText("");
        txtNgayXHSua.setText("");
        txtGioXHSua.setText("");
        txtTrangThaiSua.setText("");
        
        // Hide edit panel
        pnlBangSua.setVisible(false);
        
        JOptionPane.showMessageDialog(this, "Cập nhật thành công");
    }
    
    private void btnHuySuaActionPerformed(ActionEvent evt) {
        // Clear input fields
        txtMaSPSua.setText("");
        txtTenSPSua.setText("");
        txtSoLuongSua.setText("");
        txtThanhTienSua.setText("");
        txtNgayXHSua.setText("");
        txtGioXHSua.setText("");
        txtTrangThaiSua.setText("");
        
        // Hide edit panel
        pnlBangSua.setVisible(false);
    }
    
    private void btnXuatFileXuatHangActionPerformed(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                // Implement export functionality
                JOptionPane.showMessageDialog(this, "Đã xuất file thành công: " + fileToSave.getAbsolutePath());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất file: " + ex.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Method to get the main panel for integration
    public JPanel getPanel() {
        return this;
    }
    
    // Add some sample data to the table
    private void addSampleData() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        
        Object[] row1 = {1, "SP001", "Sản phẩm A", 10, "1,000,000", "01/04/2025", "10:30", "Đã thanh toán", "Xem chi tiết"};
        Object[] row2 = {2, "SP002", "Sản phẩm B", 5, "500,000", "02/04/2025", "14:45", "Chưa thanh toán", "Xem chi tiết"};
        Object[] row3 = {3, "SP003", "Sản phẩm C", 8, "800,000", "03/04/2025", "09:15", "Đang xử lý", "Xem chi tiết"};
        
        model.addRow(row1);
        model.addRow(row2);
        model.addRow(row3);
    }
    
    // Method to initialize panel state
    public void initPanelState() {
        // Hide add and edit panels on startup
        pnlBangThem.setVisible(false);
        pnlBangSua.setVisible(false);
        
        // Add sample data
        addSampleData();
    }
    
    // Main method for standalone testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Set system look and feel
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                // Create frame
                JFrame frame = new JFrame("Quản Lý Hóa Đơn");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                // Create and add panel
                QuanLyHoaDonPanel panel = new QuanLyHoaDonPanel();
                panel.initPanelState();
                frame.getContentPane().add(panel);
                
                // Set frame properties
                frame.pack();
                frame.setSize(1200, 800);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}