package model_test.frontend;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuanLyNhanVienPanel extends JPanel {
    // Main components
    private JPanel headerPanel;
    private JPanel searchPanel;
    private JPanel actionPanel;
    private JPanel tablePanel;
    private JPanel addPanel;
    private JPanel editPanel;
    private JPanel footerPanel;
    
    // Search components
    private JTextField txtCustomerId;
    private JTextField txtPhone;
    private JTextField txtBirthYear;
    private JTextField txtCustomerName;
    private JButton btnSearch;
    
    // Action components
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    
    // Table components
    private JTable tblCustomers;
    private JScrollPane scrollPane;
    
    // Add form components
    private JTextField txtAddCustomerId;
    private JTextField txtAddCustomerName;
    private JTextField txtAddAccountId;
    private JTextField txtAddBirthYear;
    private JTextField txtAddGender;
    private JTextField txtAddPhone;
    private JButton btnAddSubmit;
    private JButton btnAddCancel;
    
    // Edit form components
    private JTextField txtEditCustomerId;
    private JTextField txtEditCustomerName;
    private JTextField txtEditAccountId;
    private JTextField txtEditBirthYear;
    private JTextField txtEditGender;
    private JTextField txtEditPhone;
    private JButton btnEditSubmit;
    private JButton btnEditCancel;
    
    // Footer components
    private JButton btnExport;

    /**
     * Constructor for the CustomerManagementPanel
     */
    public QuanLyNhanVienPanel() {
        initComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    /**
     * Initialize all components
     */
    private void initComponents() {
        // Set panel properties
        setLayout(new BorderLayout());
        
        // Initialize header panel
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 153, 153));
        headerPanel.setPreferredSize(new Dimension(1000, 70));
        JLabel lblTitle = new JLabel("Quản lý Nhân Viên", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.CENTER);
        
        // Create main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(137, 186, 211));
        contentPanel.setPreferredSize(new Dimension(1000, 630));
        
        // Initialize search panel
        searchPanel = createSearchPanel();
        
        // Initialize action panel
        actionPanel = createActionPanel();
        
        // Initialize table panel
        tablePanel = createTablePanel();
        
        // Initialize add panel
        addPanel = createAddPanel();
        
        // Initialize edit panel
        editPanel = createEditPanel();
        
        // Initialize footer panel
        footerPanel = createFooterPanel();
        
        // Add panels to content panel
        contentPanel.add(searchPanel);
        contentPanel.add(actionPanel);
        contentPanel.add(tablePanel);
        contentPanel.add(addPanel);
        contentPanel.add(editPanel);
        contentPanel.add(footerPanel);
        
        // Add panels to main panel
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    /**
     * Create search panel with all search fields
     */
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Tìm kiếm",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.BOLD, 12)));        
        JPanel fieldsPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        
        // Customer ID field
        JPanel idPanel = new JPanel(new BorderLayout());
        idPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Mã Nhân Viên",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.BOLD, 10)
        ));
        idPanel.setPreferredSize(new Dimension(200, 50));
        txtCustomerId = new JTextField();
        JPanel idFieldPanel = new JPanel(new BorderLayout());
        idFieldPanel.add(txtCustomerId, BorderLayout.CENTER);
        idPanel.add(idFieldPanel, BorderLayout.CENTER);
        
        // Phone field
        JPanel phonePanel = new JPanel(new BorderLayout());
        phonePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Số điện thoại",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.BOLD, 10)
        ));
        phonePanel.setPreferredSize(new Dimension(200, 50));
        txtPhone = new JTextField();
        JPanel phoneFieldPanel = new JPanel(new BorderLayout());
        phoneFieldPanel.add(txtPhone, BorderLayout.CENTER);
        phonePanel.add(phoneFieldPanel, BorderLayout.CENTER);
        
        // Birth year field
        JPanel birthYearPanel = new JPanel(new BorderLayout());
        birthYearPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Năm sinh",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.BOLD, 10)
        ));
        birthYearPanel.setPreferredSize(new Dimension(200, 50));


        txtBirthYear = new JTextField();
        JPanel birthYearFieldPanel = new JPanel(new BorderLayout());
        birthYearFieldPanel.add(txtBirthYear, BorderLayout.CENTER);
        birthYearPanel.add(birthYearFieldPanel, BorderLayout.CENTER);
        
        // Customer name field
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Tên Nhân Viên",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.BOLD, 10)
        ));
        namePanel.setPreferredSize(new Dimension(200, 50));
        txtCustomerName = new JTextField();
        JPanel nameFieldPanel = new JPanel(new BorderLayout());
        // JLabel lblName = new JLabel("Tên KH");
        // nameFieldPanel.add(lblName, BorderLayout.NORTH);
        nameFieldPanel.add(txtCustomerName, BorderLayout.CENTER);
        namePanel.add(nameFieldPanel, BorderLayout.CENTER);
        
        // Add fields to panel
        fieldsPanel.add(idPanel);
        fieldsPanel.add(phonePanel);
        fieldsPanel.add(birthYearPanel);
        fieldsPanel.add(namePanel);
        
        // Search button
        btnSearch = new JButton("Tìm Kiếm");
        btnSearch.setPreferredSize(new Dimension(100, 30));
        btnSearch.setBackground(new Color(12, 150, 156));
        btnSearch.setForeground(Color.BLACK);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnSearch);
        
        // Add components to panel
        panel.add(fieldsPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    /**
     * Create action panel with add, edit, delete buttons
     */
    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Chỉnh sửa",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 12)
                ));
        
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        
        btnAdd.setPreferredSize(new Dimension(100, 30));
        btnEdit.setPreferredSize(new Dimension(100, 30));
        btnDelete.setPreferredSize(new Dimension(100, 30));
        
        panel.add(btnAdd);
        panel.add(btnEdit);
        panel.add(btnDelete);
        
        return panel;
    }
    
    /**
     * Create table panel with customer data
     */
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Bảng thông tin",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 12)
                ));
        
        // Create table model
        String[] columnNames = {"STT", "Mã Khách Hàng", "Tên Khách Hàng", "Mã Tài Khoản", "Năm Sinh", "Giới Tính", "Địa Chỉ", "Số Điện Thoại", "Chi Tiết"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        // Create table
        tblCustomers = new JTable(model);

    
        tblCustomers.getColumnModel().getColumn(0).setPreferredWidth(40);  // STT
        tblCustomers.getColumnModel().getColumn(1).setPreferredWidth(120); // Mã Khách Hàng
        tblCustomers.getColumnModel().getColumn(2).setPreferredWidth(150); // Tên Khách Hàng
        tblCustomers.getColumnModel().getColumn(3).setPreferredWidth(100); // Mã Tài Khoản
        tblCustomers.getColumnModel().getColumn(4).setPreferredWidth(80);  // Năm Sinh
        tblCustomers.getColumnModel().getColumn(5).setPreferredWidth(80);  // Giới Tính
        tblCustomers.getColumnModel().getColumn(6).setPreferredWidth(200); // Địa Chỉ
        tblCustomers.getColumnModel().getColumn(7).setPreferredWidth(120); // Số Điện Thoại
        tblCustomers.getColumnModel().getColumn(8).setPreferredWidth(90); // Chi Tiết

        tblCustomers.setShowGrid(true);
        tblCustomers.setGridColor(Color.GRAY);

        scrollPane = new JScrollPane(tblCustomers);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create add panel with form fields
     */
    private JPanel createAddPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Bảng Thêm",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 12)
                ));
        
        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(1, 6, 10, 0));
        
        // Customer ID
        JPanel idPanel = new JPanel(new BorderLayout());
        JLabel lblId = new JLabel("Mã Khách Hàng");
        txtAddCustomerId = new JTextField();
        idPanel.add(lblId, BorderLayout.NORTH);
        idPanel.add(txtAddCustomerId, BorderLayout.CENTER);
        
        // Customer name
        JPanel namePanel = new JPanel(new BorderLayout());
        JLabel lblName = new JLabel("Tên Khách Hàng");
        txtAddCustomerName = new JTextField();
        namePanel.add(lblName, BorderLayout.NORTH);
        namePanel.add(txtAddCustomerName, BorderLayout.CENTER);
        
        // Account ID
        JPanel accountPanel = new JPanel(new BorderLayout());
        JLabel lblAccount = new JLabel("Mã Tài Khoản");
        txtAddAccountId = new JTextField();
        accountPanel.add(lblAccount, BorderLayout.NORTH);
        accountPanel.add(txtAddAccountId, BorderLayout.CENTER);
        
        // Birth year
        JPanel birthYearPanel = new JPanel(new BorderLayout());
        JLabel lblBirthYear = new JLabel("Năm Sinh");
        txtAddBirthYear = new JTextField();
        birthYearPanel.add(lblBirthYear, BorderLayout.NORTH);
        birthYearPanel.add(txtAddBirthYear, BorderLayout.CENTER);
        
        // Gender
        JPanel genderPanel = new JPanel(new BorderLayout());
        JLabel lblGender = new JLabel("Giới tính");
        txtAddGender = new JTextField();
        genderPanel.add(lblGender, BorderLayout.NORTH);
        genderPanel.add(txtAddGender, BorderLayout.CENTER);
        
        // Phone
        JPanel phonePanel = new JPanel(new BorderLayout());
        JLabel lblPhone = new JLabel("Số Điện Thoại");
        txtAddPhone = new JTextField();
        phonePanel.add(lblPhone, BorderLayout.NORTH);
        phonePanel.add(txtAddPhone, BorderLayout.CENTER);
        
        // Add fields to form panel
        formPanel.add(idPanel);
        formPanel.add(namePanel);
        formPanel.add(accountPanel);
        formPanel.add(birthYearPanel);
        formPanel.add(genderPanel);
        formPanel.add(phonePanel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAddCancel = new JButton("Hủy");
        btnAddSubmit = new JButton("Thêm");
        buttonPanel.add(btnAddCancel);
        buttonPanel.add(btnAddSubmit);
        
        // Add components to panel
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Create edit panel with form fields
     */
    private JPanel createEditPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Bảng Sửa",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION));
        
        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(1, 6, 10, 0));
        
        // Customer ID
        JPanel idPanel = new JPanel(new BorderLayout());
        JLabel lblId = new JLabel("Mã KH");
        txtEditCustomerId = new JTextField();
        idPanel.add(lblId, BorderLayout.NORTH);
        idPanel.add(txtEditCustomerId, BorderLayout.CENTER);
        
        // Customer name
        JPanel namePanel = new JPanel(new BorderLayout());
        JLabel lblName = new JLabel("Tên KH");
        txtEditCustomerName = new JTextField();
        namePanel.add(lblName, BorderLayout.NORTH);
        namePanel.add(txtEditCustomerName, BorderLayout.CENTER);
        
        // Account ID
        JPanel accountPanel = new JPanel(new BorderLayout());
        JLabel lblAccount = new JLabel("Mã TK");
        txtEditAccountId = new JTextField();
        accountPanel.add(lblAccount, BorderLayout.NORTH);
        accountPanel.add(txtEditAccountId, BorderLayout.CENTER);
        
        // Birth year
        JPanel birthYearPanel = new JPanel(new BorderLayout());
        JLabel lblBirthYear = new JLabel("Năm sinh");
        txtEditBirthYear = new JTextField();
        birthYearPanel.add(lblBirthYear, BorderLayout.NORTH);
        birthYearPanel.add(txtEditBirthYear, BorderLayout.CENTER);
        
        // Gender
        JPanel genderPanel = new JPanel(new BorderLayout());
        JLabel lblGender = new JLabel("Giới tính");
        txtEditGender = new JTextField();
        genderPanel.add(lblGender, BorderLayout.NORTH);
        genderPanel.add(txtEditGender, BorderLayout.CENTER);
        
        // Phone
        JPanel phonePanel = new JPanel(new BorderLayout());
        JLabel lblPhone = new JLabel("SĐT");
        txtEditPhone = new JTextField();
        phonePanel.add(lblPhone, BorderLayout.NORTH);
        phonePanel.add(txtEditPhone, BorderLayout.CENTER);
        
        // Add fields to form panel
        formPanel.add(idPanel);
        formPanel.add(namePanel);
        formPanel.add(accountPanel);
        formPanel.add(birthYearPanel);
        formPanel.add(genderPanel);
        formPanel.add(phonePanel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnEditCancel = new JButton("Hủy");
        btnEditSubmit = new JButton("Sửa");
        buttonPanel.add(btnEditCancel);
        buttonPanel.add(btnEditSubmit);
        
        // Add components to panel
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Create footer panel with export button
     */
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setPreferredSize(new Dimension(800, 50));
        
        btnExport = new JButton("Lưu và xuất file");
        btnExport.setPreferredSize(new Dimension(200, 30));
        
        panel.add(btnExport);
        
        return panel;
    }
    
    /**
     * Setup panel layout
     */
    private void setupLayout() {
        // Set padding
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Set preferred size
        setPreferredSize(new Dimension(800, 700));
    }
    
    /**
     * Set up event handlers for all interactive components
     */
    private void setupEventHandlers() {
        // Search button action
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchCustomers();
            }
        });
        
        // Add button action
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddPanel();
            }
        });
        
        // Edit button action
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEditPanel();
            }
        });
        
        // Delete button action
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCustomer();
            }
        });
        
        // Add submit button action
        btnAddSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });
        
        // Add cancel button action
        btnAddCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAddForm();
            }
        });
        
        // Edit submit button action
        btnEditSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCustomer();
            }
        });
        
        // Edit cancel button action
        btnEditCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearEditForm();
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
    
    /**
     * Get the main panel for integration
     */
    public JPanel getMainPanel() {
        return this;
    }
    
    /**
     * Search customers based on search criteria
     */
    private void searchCustomers() {
        String customerId = txtCustomerId.getText().trim();
        String phone = txtPhone.getText().trim();
        String birthYear = txtBirthYear.getText().trim();
        String customerName = txtCustomerName.getText().trim();
        
        // TODO: Implement actual search logic
        JOptionPane.showMessageDialog(this, 
                "Tìm kiếm khách hàng với:\nMã KH: " + customerId + 
                "\nSĐT: " + phone + 
                "\nNăm sinh: " + birthYear + 
                "\nTên KH: " + customerName,
                "Kết quả tìm kiếm", 
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Show add panel and hide edit panel
     */
    private void showAddPanel() {
        addPanel.setVisible(true);
        editPanel.setVisible(false);
        clearAddForm();
    }
    
    /**
     * Show edit panel and hide add panel
     */
    private void showEditPanel() {
        // Check if a row is selected
        int selectedRow = tblCustomers.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                    "Vui lòng chọn khách hàng để sửa",
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Populate form with selected customer data
        // TODO: Add actual data loading logic
        
        addPanel.setVisible(false);
        editPanel.setVisible(true);
    }
    
    /**
     * Delete selected customer
     */
    private void deleteCustomer() {
        // Check if a row is selected
        int selectedRow = tblCustomers.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                    "Vui lòng chọn khách hàng để xóa",
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa khách hàng này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // TODO: Implement actual delete logic
            DefaultTableModel model = (DefaultTableModel) tblCustomers.getModel();
            model.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, 
                    "Đã xóa khách hàng thành công",
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Add new customer from form data
     */
    private void addCustomer() {
        // Validate form
        if (!validateAddForm()) {
            return;
        }
        
        // Get form data
        String customerId = txtAddCustomerId.getText().trim();
        String customerName = txtAddCustomerName.getText().trim();
        String accountId = txtAddAccountId.getText().trim();
        String birthYear = txtAddBirthYear.getText().trim();
        String gender = txtAddGender.getText().trim();
        String phone = txtAddPhone.getText().trim();
        
        // Add customer to table
        DefaultTableModel model = (DefaultTableModel) tblCustomers.getModel();
        int rowCount = model.getRowCount();
        model.addRow(new Object[]{
            rowCount + 1,
            customerId,
            customerName,
            accountId,
            birthYear,
            gender,
            "",  // Address (not in form)
            phone,
            ""   // Details (not in form)
        });
        
        // Clear form
        clearAddForm();
        
        JOptionPane.showMessageDialog(this, 
                "Đã thêm khách hàng mới thành công",
                "Thông báo", 
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Update existing customer from form data
     */
    private void updateCustomer() {
        // Check if a row is selected
        int selectedRow = tblCustomers.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                    "Không tìm thấy khách hàng để cập nhật",
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate form
        if (!validateEditForm()) {
            return;
        }
        
        // Get form data
        String customerId = txtEditCustomerId.getText().trim();
        String customerName = txtEditCustomerName.getText().trim();
        String accountId = txtEditAccountId.getText().trim();
        String birthYear = txtEditBirthYear.getText().trim();
        String gender = txtEditGender.getText().trim();
        String phone = txtEditPhone.getText().trim();
        
        // Update customer in table
        DefaultTableModel model = (DefaultTableModel) tblCustomers.getModel();
        model.setValueAt(customerId, selectedRow, 1);
        model.setValueAt(customerName, selectedRow, 2);
        model.setValueAt(accountId, selectedRow, 3);
        model.setValueAt(birthYear, selectedRow, 4);
        model.setValueAt(gender, selectedRow, 5);
        model.setValueAt(phone, selectedRow, 7);
        
        // Clear form
        clearEditForm();
        
        JOptionPane.showMessageDialog(this, 
                "Đã cập nhật khách hàng thành công",
                "Thông báo", 
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Export data to a file
     */
    private void exportData() {
        // TODO: Implement actual export logic
        JOptionPane.showMessageDialog(this, 
                "Đã lưu và xuất dữ liệu ra file thành công",
                "Thông báo", 
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Validate add form data
     */
    private boolean validateAddForm() {
        if (txtAddCustomerId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Vui lòng nhập Mã KH",
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            txtAddCustomerId.requestFocus();
            return false;
        }
        
        if (txtAddCustomerName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Vui lòng nhập Tên KH",
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            txtAddCustomerName.requestFocus();
            return false;
        }
        
        // Add more validation as needed
        
        return true;
    }
    
    /**
     * Validate edit form data
     */
    private boolean validateEditForm() {
        if (txtEditCustomerId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Vui lòng nhập Mã KH",
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            txtEditCustomerId.requestFocus();
            return false;
        }
        
        if (txtEditCustomerName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Vui lòng nhập Tên KH",
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            txtEditCustomerName.requestFocus();
            return false;
        }
        
        // Add more validation as needed
        
        return true;
    }
    
    /**
     * Clear add form fields
     */
    private void clearAddForm() {
        txtAddCustomerId.setText("");
        txtAddCustomerName.setText("");
        txtAddAccountId.setText("");
        txtAddBirthYear.setText("");
        txtAddGender.setText("");
        txtAddPhone.setText("");
    }
    
    /**
     * Clear edit form fields
     */
    private void clearEditForm() {
        txtEditCustomerId.setText("");
        txtEditCustomerName.setText("");
        txtEditAccountId.setText("");
        txtEditBirthYear.setText("");
        txtEditGender.setText("");
        txtEditPhone.setText("");
    }
    
    /**
     * Main method to run the application
     */
    public static void main(String[] args) {
        try {
            // Set look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create and display the form
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Quản lý khách hàng");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                QuanLyNhanVienPanel panel = new QuanLyNhanVienPanel();
                frame.getContentPane().add(panel);
                
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}