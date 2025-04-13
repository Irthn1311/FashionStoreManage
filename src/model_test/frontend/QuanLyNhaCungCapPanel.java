package model.frontend;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class QuanLyNhaCungCapPanel extends JPanel {
    // Declare components
    private JPanel headerPanel;
    private JLabel titleLabel;
    
    // Search components
    private JPanel searchPanel;
    private JLabel lblMaNCC;
    private JTextField txtMaNCC;
    private JLabel lblTenNCC;
    private JTextField txtTenNCC;
    private JLabel lblMaSP;
    private JTextField txtMaSP;
    private JLabel lblTenSP;
    private JTextField txtTenSP;
    private JButton btnTimKiem;
    
    // Action buttons
    private JPanel actionPanel;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    
    // Table components
    private JPanel tablePanel;
    private JTable tblNhaCungCap;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    
    // Add supplier panel
    private JPanel addPanel;
    private JLabel lblAddMaNCC;
    private JTextField txtAddMaNCC;
    private JLabel lblAddTenNCC;
    private JTextField txtAddTenNCC;
    private JLabel lblAddMaSP;
    private JTextField txtAddMaSP;
    private JLabel lblAddTenSP;
    private JTextField txtAddTenSP;
    private JLabel lblAddNamHopTac;
    private JTextField txtAddNamHopTac;
    private JLabel lblAddDiaChi;
    private JTextField txtAddDiaChi;
    private JLabel lblAddSDT;
    private JTextField txtAddSDT;
    private JButton btnAddHuy;
    private JButton btnAddSubmit;
    
    // Edit supplier panel
    private JPanel editPanel;
    private JLabel lblEditMaNCC;
    private JTextField txtEditMaNCC;
    private JLabel lblEditTenNCC;
    private JTextField txtEditTenNCC;
    private JLabel lblEditMaSP;
    private JTextField txtEditMaSP;
    private JLabel lblEditTenSP;
    private JTextField txtEditTenSP;
    private JLabel lblEditNamHopTac;
    private JTextField txtEditNamHopTac;
    private JLabel lblEditDiaChi;
    private JTextField txtEditDiaChi;
    private JLabel lblEditSDT;
    private JTextField txtEditSDT;
    private JButton btnEditHuy;
    private JButton btnEditSubmit;
    
    // Export button
    private JPanel exportPanel;
    private JButton btnExport;
    
    // Colors
    private Color headerColor = new Color(0, 153, 153);
    private Color bgColor = new Color(153, 204, 204);
    private Color panelColor = new Color(204, 229, 255);
    
    public QuanLyNhaCungCapPanel() {
        initComponents();
        setupLayout();
        registerListeners();
    }
    
    private void initComponents() {
        // Initialize main panel
        setLayout(new BorderLayout());
        setBackground(bgColor);
        
        // Header panel
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(headerColor);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
        
        titleLabel = new JLabel("Quản lý nhà cung cấp");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(bgColor);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Search panel
        searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(panelColor);
        searchPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Tìm kiếm",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION));
        
        JPanel maNccPanel = new JPanel(new BorderLayout());
        maNccPanel.setBackground(panelColor);
        maNccPanel.setBorder(BorderFactory.createTitledBorder("Mã NCC"));
        lblMaNCC = new JLabel("Mã NCC");
        txtMaNCC = new JTextField(15);
        maNccPanel.add(txtMaNCC, BorderLayout.CENTER);
        
        JPanel tenNccPanel = new JPanel(new BorderLayout());
        tenNccPanel.setBackground(panelColor);
        tenNccPanel.setBorder(BorderFactory.createTitledBorder("Tên NCC"));
        lblTenNCC = new JLabel("Tên NCC");
        txtTenNCC = new JTextField(15);
        tenNccPanel.add(txtTenNCC, BorderLayout.CENTER);
        
        JPanel maSpPanel = new JPanel(new BorderLayout());
        maSpPanel.setBackground(panelColor);
        maSpPanel.setBorder(BorderFactory.createTitledBorder("Mã SP"));
        lblMaSP = new JLabel("Mã SP");
        txtMaSP = new JTextField(15);
        maSpPanel.add(txtMaSP, BorderLayout.CENTER);
        
        JPanel tenSpPanel = new JPanel(new BorderLayout());
        tenSpPanel.setBackground(panelColor);
        tenSpPanel.setBorder(BorderFactory.createTitledBorder("Tên SP"));
        lblTenSP = new JLabel("Tên SP");
        txtTenSP = new JTextField(15);
        tenSpPanel.add(txtTenSP, BorderLayout.CENTER);
        
        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setPreferredSize(new Dimension(100, 30));
        
        searchPanel.add(maNccPanel);
        searchPanel.add(tenNccPanel);
        searchPanel.add(maSpPanel);
        searchPanel.add(tenSpPanel);
        searchPanel.add(btnTimKiem);
        
        // Action panel
        actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        actionPanel.setBackground(panelColor);
        actionPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Chỉnh sửa",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION));
        
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        
        btnThem.setPreferredSize(new Dimension(100, 30));
        btnSua.setPreferredSize(new Dimension(100, 30));
        btnXoa.setPreferredSize(new Dimension(100, 30));
        
        actionPanel.add(btnThem);
        actionPanel.add(btnSua);
        actionPanel.add(btnXoa);
        
        // Table panel
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(panelColor);
        tablePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Bảng thông tin",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION));
        
        String[] columnNames = {"STT", "Mã NCC", "Tên NCC", "Mã SP", "TÊN SP", "Năm hợp tác", "Địa chỉ", "Số điện thoại", "Chi tiết"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tblNhaCungCap = new JTable(tableModel);
        scrollPane = new JScrollPane(tblNhaCungCap);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add panel
        addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        addPanel.setBackground(panelColor);
        addPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Bảng thêm",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION));
        
        lblAddMaNCC = new JLabel("Mã NCC");
        txtAddMaNCC = new JTextField(10);
        lblAddTenNCC = new JLabel("Tên NCC");
        txtAddTenNCC = new JTextField(10);
        lblAddMaSP = new JLabel("Mã SP");
        txtAddMaSP = new JTextField(10);
        lblAddTenSP = new JLabel("Tên SP");
        txtAddTenSP = new JTextField(10);
        lblAddNamHopTac = new JLabel("Năm hợp tác");
        txtAddNamHopTac = new JTextField(10);
        lblAddDiaChi = new JLabel("Địa chỉ");
        txtAddDiaChi = new JTextField(10);
        lblAddSDT = new JLabel("SDT");
        txtAddSDT = new JTextField(10);
        btnAddHuy = new JButton("Hủy");
        btnAddSubmit = new JButton("Thêm");
        
        addPanel.add(lblAddMaNCC);
        addPanel.add(txtAddMaNCC);
        addPanel.add(lblAddTenNCC);
        addPanel.add(txtAddTenNCC);
        addPanel.add(lblAddMaSP);
        addPanel.add(txtAddMaSP);
        addPanel.add(lblAddTenSP);
        addPanel.add(txtAddTenSP);
        addPanel.add(lblAddNamHopTac);
        addPanel.add(txtAddNamHopTac);
        addPanel.add(lblAddDiaChi);
        addPanel.add(txtAddDiaChi);
        addPanel.add(lblAddSDT);
        addPanel.add(txtAddSDT);
        addPanel.add(btnAddHuy);
        addPanel.add(btnAddSubmit);
        
        // Edit panel
        editPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        editPanel.setBackground(panelColor);
        editPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Bảng sửa",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION));
        
        lblEditMaNCC = new JLabel("Mã NCC");
        txtEditMaNCC = new JTextField(10);
        lblEditTenNCC = new JLabel("Tên NCC");
        txtEditTenNCC = new JTextField(10);
        lblEditMaSP = new JLabel("Mã SP");
        txtEditMaSP = new JTextField(10);
        lblEditTenSP = new JLabel("Tên SP");
        txtEditTenSP = new JTextField(10);
        lblEditNamHopTac = new JLabel("Năm hợp tác");
        txtEditNamHopTac = new JTextField(10);
        lblEditDiaChi = new JLabel("Địa chỉ");
        txtEditDiaChi = new JTextField(10);
        lblEditSDT = new JLabel("SDT");
        txtEditSDT = new JTextField(10);
        btnEditHuy = new JButton("Hủy");
        btnEditSubmit = new JButton("Thêm");
        
        editPanel.add(lblEditMaNCC);
        editPanel.add(txtEditMaNCC);
        editPanel.add(lblEditTenNCC);
        editPanel.add(txtEditTenNCC);
        editPanel.add(lblEditMaSP);
        editPanel.add(txtEditMaSP);
        editPanel.add(lblEditTenSP);
        editPanel.add(txtEditTenSP);
        editPanel.add(lblEditNamHopTac);
        editPanel.add(txtEditNamHopTac);
        editPanel.add(lblEditDiaChi);
        editPanel.add(txtEditDiaChi);
        editPanel.add(lblEditSDT);
        editPanel.add(txtEditSDT);
        editPanel.add(btnEditHuy);
        editPanel.add(btnEditSubmit);
        
        // Export panel
        exportPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        exportPanel.setBackground(bgColor);
        btnExport = new JButton("Lưu và xuất file");
        btnExport.setPreferredSize(new Dimension(180, 30));
        exportPanel.add(btnExport);
        
        // Add all panels to content panel
        contentPanel.add(searchPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(actionPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(tablePanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(addPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(editPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(exportPanel);
        
        // Add components to main panel
        add(headerPanel, BorderLayout.NORTH);
        add(new JScrollPane(contentPanel), BorderLayout.CENTER);
    }
    
    private void setupLayout() {
        // Set preferred sizes
        searchPanel.setPreferredSize(new Dimension(getWidth() - 40, 100));
        actionPanel.setPreferredSize(new Dimension(getWidth() - 40, 80));
        tablePanel.setPreferredSize(new Dimension(getWidth() - 40, 200));
        addPanel.setPreferredSize(new Dimension(getWidth() - 40, 80));
        editPanel.setPreferredSize(new Dimension(getWidth() - 40, 80));
        exportPanel.setPreferredSize(new Dimension(getWidth() - 40, 60));
        
        // Configure table
        tblNhaCungCap.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblNhaCungCap.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblNhaCungCap.getTableHeader().setReorderingAllowed(false);
        
        // Add some sample data
        addSampleData();
    }
    
    private void registerListeners() {
        // Search button action
        btnTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchSuppliers();
            }
        });
        
        // Add button action
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepareAddSupplier();
            }
        });
        
        // Update button action
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepareEditSupplier();
            }
        });
        
        // Delete button action
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSupplier();
            }
        });
        
        // Add panel buttons
        btnAddSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSupplier();
            }
        });
        
        btnAddHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelAdd();
            }
        });
        
        // Edit panel buttons
        btnEditSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSupplier();
            }
        });
        
        btnEditHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelEdit();
            }
        });
        
        // Export button action
        btnExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportData();
            }
        });
    }
    
    // Sample data for demonstration
    private void addSampleData() {
        Object[][] data = {
            {1, "NCC001", "Công ty A", "SP001", "Sản phẩm A", "2020", "Hà Nội", "0912345678", "Xem"},
            {2, "NCC002", "Công ty B", "SP002", "Sản phẩm B", "2021", "TP HCM", "0987654321", "Xem"},
            {3, "NCC003", "Công ty C", "SP003", "Sản phẩm C", "2019", "Đà Nẵng", "0923456789", "Xem"}
        };
        
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }
    
    // Action methods
    private void searchSuppliers() {
        // Implement search functionality
        JOptionPane.showMessageDialog(this, "Tìm kiếm nhà cung cấp với các tiêu chí đã nhập");
    }
    
    private void prepareAddSupplier() {
        // Clear fields and prepare for adding
        txtAddMaNCC.setText("");
        txtAddTenNCC.setText("");
        txtAddMaSP.setText("");
        txtAddTenSP.setText("");
        txtAddNamHopTac.setText("");
        txtAddDiaChi.setText("");
        txtAddSDT.setText("");
        
        JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin nhà cung cấp mới");
    }
    
    private void prepareEditSupplier() {
        int selectedRow = tblNhaCungCap.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà cung cấp để sửa");
            return;
        }
        
        // Populate edit fields with selected data
        txtEditMaNCC.setText(tblNhaCungCap.getValueAt(selectedRow, 1).toString());
        txtEditTenNCC.setText(tblNhaCungCap.getValueAt(selectedRow, 2).toString());
        txtEditMaSP.setText(tblNhaCungCap.getValueAt(selectedRow, 3).toString());
        txtEditTenSP.setText(tblNhaCungCap.getValueAt(selectedRow, 4).toString());
        txtEditNamHopTac.setText(tblNhaCungCap.getValueAt(selectedRow, 5).toString());
        txtEditDiaChi.setText(tblNhaCungCap.getValueAt(selectedRow, 6).toString());
        txtEditSDT.setText(tblNhaCungCap.getValueAt(selectedRow, 7).toString());
        
        JOptionPane.showMessageDialog(this, "Vui lòng cập nhật thông tin nhà cung cấp");
    }
    
    private void deleteSupplier() {
        int selectedRow = tblNhaCungCap.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà cung cấp để xóa");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn xóa nhà cung cấp này?", 
                "Xác nhận xóa", 
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Đã xóa nhà cung cấp thành công");
        }
    }
    
    private void addSupplier() {
        // Validate inputs
        if (txtAddMaNCC.getText().trim().isEmpty() || 
            txtAddTenNCC.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin bắt buộc");
            return;
        }
        
        // Add new supplier to table
        Object[] rowData = {
            tableModel.getRowCount() + 1,
            txtAddMaNCC.getText(),
            txtAddTenNCC.getText(),
            txtAddMaSP.getText(),
            txtAddTenSP.getText(),
            txtAddNamHopTac.getText(),
            txtAddDiaChi.getText(),
            txtAddSDT.getText(),
            "Xem"
        };
        
        tableModel.addRow(rowData);
        JOptionPane.showMessageDialog(this, "Đã thêm nhà cung cấp thành công");
        
        // Clear fields
        txtAddMaNCC.setText("");
        txtAddTenNCC.setText("");
        txtAddMaSP.setText("");
        txtAddTenSP.setText("");
        txtAddNamHopTac.setText("");
        txtAddDiaChi.setText("");
        txtAddSDT.setText("");
    }
    
    private void updateSupplier() {
        int selectedRow = tblNhaCungCap.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Không có nhà cung cấp nào được chọn để cập nhật");
            return;
        }
        
        // Validate inputs
        if (txtEditMaNCC.getText().trim().isEmpty() || 
            txtEditTenNCC.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin bắt buộc");
            return;
        }
        
        // Update supplier in table
        tblNhaCungCap.setValueAt(txtEditMaNCC.getText(), selectedRow, 1);
        tblNhaCungCap.setValueAt(txtEditTenNCC.getText(), selectedRow, 2);
        tblNhaCungCap.setValueAt(txtEditMaSP.getText(), selectedRow, 3);
        tblNhaCungCap.setValueAt(txtEditTenSP.getText(), selectedRow, 4);
        tblNhaCungCap.setValueAt(txtEditNamHopTac.getText(), selectedRow, 5);
        tblNhaCungCap.setValueAt(txtEditDiaChi.getText(), selectedRow, 6);
        tblNhaCungCap.setValueAt(txtEditSDT.getText(), selectedRow, 7);
        
        JOptionPane.showMessageDialog(this, "Đã cập nhật nhà cung cấp thành công");
        
        // Clear fields
        txtEditMaNCC.setText("");
        txtEditTenNCC.setText("");
        txtEditMaSP.setText("");
        txtEditTenSP.setText("");
        txtEditNamHopTac.setText("");
        txtEditDiaChi.setText("");
        txtEditSDT.setText("");
    }
    
    private void cancelAdd() {
        // Clear add fields
        txtAddMaNCC.setText("");
        txtAddTenNCC.setText("");
        txtAddMaSP.setText("");
        txtAddTenSP.setText("");
        txtAddNamHopTac.setText("");
        txtAddDiaChi.setText("");
        txtAddSDT.setText("");
    }
    
    private void cancelEdit() {
        // Clear edit fields
        txtEditMaNCC.setText("");
        txtEditTenNCC.setText("");
        txtEditMaSP.setText("");
        txtEditTenSP.setText("");
        txtEditNamHopTac.setText("");
        txtEditDiaChi.setText("");
        txtEditSDT.setText("");
    }
    
    private void exportData() {
        JOptionPane.showMessageDialog(this, "Dữ liệu đã được lưu và xuất thành công");
    }
    
    // Method to get the panel for integration
    public JPanel getPanel() {
        return this;
    }
    
    // Main method for standalone testing
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Quản lý nhà cung cấp");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new QuanLyNhaCungCapPanel());
                frame.setSize(1200, 800);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}