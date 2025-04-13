package screens;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class ShopStoreManagementSystem extends JFrame {
    // Main panels
    private JPanel mainPanel;
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private JPanel headerPanel;
    
    // Sidebar buttons
    private JButton loginButton;
    private JButton settingButton;
    private JButton importButton;
    private JButton exportButton;
    private JButton productButton;
    private JButton productCategoryButton;
    private JButton invoiceButton;
    private JButton importTicketButton;
    private JButton promotionButton;
    private JButton employeeButton;
    private JButton customerButton;
    private JButton supplierButton;
    private JButton accountButton;
    
    // Current active panel
    private JPanel currentContentPanel;
    
    public ShopStoreManagementSystem() {
        initComponents();
        setupLayout();
        setupEventHandlers();
        
        // Display the welcome panel initially
        showWelcomePanel();
    }
    
    private void initComponents() {
        // Setup main frame
        setTitle("ShopStore Management System");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Initialize main panels
        mainPanel = new JPanel(new BorderLayout());
        sidebarPanel = new JPanel();
        contentPanel = new JPanel(new BorderLayout());
        headerPanel = new JPanel(new BorderLayout());
        
        // Initialize sidebar buttons with specific sizes
        loginButton = createTopButton("Login");
        settingButton = createTopButton("Setting");
        
        // Initialize other sidebar buttons
        importButton = createSidebarButton("Nhập hàng");
        exportButton = createSidebarButton("Xuất hàng");
        productButton = createSidebarButton("Sản phẩm");
        productCategoryButton = createSidebarButton("Loại sản phẩm");
        invoiceButton = createSidebarButton("Hóa đơn");
        importTicketButton = createSidebarButton("Phiếu nhập");
        promotionButton = createSidebarButton("Khuyến mãi");
        employeeButton = createSidebarButton("Nhân viên");
        customerButton = createSidebarButton("Khách hàng");
        supplierButton = createSidebarButton("Nhà cung cấp");
        accountButton = createSidebarButton("Tài khoản");
    }
    
    // Separate method for top buttons (Login and Setting)
    private JButton createTopButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(180, 40));
        button.setMaximumSize(new Dimension(180, 40));
        button.setMinimumSize(new Dimension(180, 40));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        return button;
    }
    
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(180, 40));
        button.setMaximumSize(new Dimension(180, 40));
        button.setMinimumSize(new Dimension(180, 40));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        return button;
    }
    
    private void setupLayout() {
        // Setup sidebar panel
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(0, 102, 102));
        sidebarPanel.setPreferredSize(new Dimension(200, getHeight()));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Add top buttons (Login and Setting) directly to sidebar with proper spacing
        // Each in its own panel for better layout control
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.X_AXIS));
        loginPanel.setMaximumSize(new Dimension(180, 45));
        loginPanel.setBackground(new Color(0, 102, 102));
        loginPanel.add(loginButton);
        sidebarPanel.add(loginPanel);
        
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel settingPanel = new JPanel();
        settingPanel.setLayout(new BoxLayout(settingPanel, BoxLayout.X_AXIS));
        settingPanel.setMaximumSize(new Dimension(180, 45));
        settingPanel.setBackground(new Color(0, 102, 102));
        settingPanel.add(settingButton);
        sidebarPanel.add(settingPanel);
        
        // Add spacing after the top buttons
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Add all other components to sidebar
        addButtonToSidebar(importButton);
        addButtonToSidebar(exportButton);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        addButtonToSidebar(productButton);
        addButtonToSidebar(productCategoryButton);
        addButtonToSidebar(invoiceButton);
        addButtonToSidebar(importTicketButton);
        addButtonToSidebar(promotionButton);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        addButtonToSidebar(employeeButton);
        addButtonToSidebar(customerButton);
        addButtonToSidebar(supplierButton);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        addButtonToSidebar(accountButton);
        
        // Setup header panel
        headerPanel.setBackground(new Color(0, 153, 153));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 80));
        
        // Add components to the main panel
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Add the main panel to the frame
        setContentPane(mainPanel);
    }
    
    private void addButtonToSidebar(JButton button) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setMaximumSize(new Dimension(180, 45));
        buttonPanel.setBackground(new Color(0, 102, 102));
        buttonPanel.add(button);
        sidebarPanel.add(buttonPanel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    }
    
    private void setupEventHandlers() {
        // Add action listeners to sidebar buttons
        loginButton.addActionListener(e -> System.out.println("Login button clicked"));
        settingButton.addActionListener(e -> System.out.println("Setting button clicked"));
        importButton.addActionListener(e -> showImportPanel());
        exportButton.addActionListener(e -> showExportPanel());
        productButton.addActionListener(e -> showProductPanel());
        productCategoryButton.addActionListener(e -> showProductCategoryPanel());
        invoiceButton.addActionListener(e -> showInvoicePanel());
        importTicketButton.addActionListener(e -> showImportTicketPanel());
        promotionButton.addActionListener(e -> showPromotionPanel());
        employeeButton.addActionListener(e -> showEmployeePanel());
        customerButton.addActionListener(e -> showCustomerPanel());
        supplierButton.addActionListener(e -> showSupplierPanel());
        accountButton.addActionListener(e -> showAccountPanel());
    }
    
    // Method to show welcome panel
    private void showWelcomePanel() {
        // Clear previous content
        contentPanel.removeAll();
        
        // Create welcome header
        JPanel welcomeHeaderPanel = new JPanel(new BorderLayout());
        welcomeHeaderPanel.setBackground(new Color(0, 153, 153));
        welcomeHeaderPanel.setPreferredSize(new Dimension(contentPanel.getWidth(), 80));
        
        JLabel welcomeLabel = new JLabel("CHÀO MỪNG QUÝ KHÁCH");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeHeaderPanel.add(welcomeLabel, BorderLayout.CENTER);
        
        // Create welcome content
        JPanel welcomeContentPanel = new JPanel(new GridBagLayout());
        welcomeContentPanel.setBackground(new Color(102, 178, 204));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        
        JLabel shopStoreLabel = new JLabel("SHOPSTORE XIN CHÀO");
        shopStoreLabel.setForeground(Color.BLACK);
        shopStoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeContentPanel.add(shopStoreLabel, gbc);
        
        gbc.gridy = 1;
        gbc.insets = new Insets(30, 0, 10, 0);
        
        JLabel wishLabel = new JLabel("SHOPSTORE CHÚC QUÝ KHÁCH CÓ MỘT NGÀY TỐT LÀNH");
        wishLabel.setForeground(Color.BLACK);
        wishLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeContentPanel.add(wishLabel, gbc);
        
        // Add components to content panel
        contentPanel.add(welcomeHeaderPanel, BorderLayout.NORTH);
        contentPanel.add(welcomeContentPanel, BorderLayout.CENTER);
        
        // Refresh the UI
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    // Methods to show different panels (these would be implemented with actual content in a real application)
    private void showImportPanel() {
        showFunctionalPanel("NHẬP HÀNG", createImportPanel());
    }
    
    private void showExportPanel() {
        showFunctionalPanel("XUẤT HÀNG", createExportPanel());
    }
    
    private void showProductPanel() {
        showFunctionalPanel("QUẢN LÝ SẢN PHẨM", createProductPanel());
    }
    
    private void showProductCategoryPanel() {
        showFunctionalPanel("QUẢN LÝ LOẠI SẢN PHẨM", createProductCategoryPanel());
    }
    
    private void showInvoicePanel() {
        showFunctionalPanel("QUẢN LÝ HÓA ĐƠN", createInvoicePanel());
    }
    
    private void showImportTicketPanel() {
        showFunctionalPanel("QUẢN LÝ PHIẾU NHẬP", createImportTicketPanel());
    }
    
    private void showPromotionPanel() {
        showFunctionalPanel("QUẢN LÝ KHUYẾN MÃI", createPromotionPanel());
    }
    
    private void showEmployeePanel() {
        showFunctionalPanel("QUẢN LÝ NHÂN VIÊN", createEmployeePanel());
    }
    
    private void showCustomerPanel() {
        showFunctionalPanel("QUẢN LÝ KHÁCH HÀNG", createCustomerPanel());
    }
    
    private void showSupplierPanel() {
        showFunctionalPanel("QUẢN LÝ NHÀ CUNG CẤP", createSupplierPanel());
    }
    
    private void showAccountPanel() {
        showFunctionalPanel("QUẢN LÝ TÀI KHOẢN", createAccountPanel());
    }
    
    // Generic method to show a functional panel with header
    private void showFunctionalPanel(String title, JPanel panel) {
        // Clear previous content
        contentPanel.removeAll();
        
        // Create header
        JPanel functionalHeaderPanel = new JPanel(new BorderLayout());
        functionalHeaderPanel.setBackground(new Color(0, 153, 153));
        functionalHeaderPanel.setPreferredSize(new Dimension(contentPanel.getWidth(), 80));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        functionalHeaderPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Add components to content panel
        contentPanel.add(functionalHeaderPanel, BorderLayout.NORTH);
        contentPanel.add(panel, BorderLayout.CENTER);
        
        // Refresh the UI
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    // Create specific functional panels (placeholder implementations)
    private JPanel createImportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Add actual implementation here
        panel.add(new JLabel("Nhập hàng functionality would go here", SwingConstants.CENTER));
        
        return panel;
    }
    
    private JPanel createExportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Add actual implementation here
        panel.add(new JLabel("Xuất hàng functionality would go here", SwingConstants.CENTER));
        
        return panel;
    }
    
    private JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Create product management panel
        JPanel managementPanel = new JPanel(new BorderLayout(10, 10));
        managementPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create search panel
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Tìm kiếm sản phẩm", 
            TitledBorder.LEFT, TitledBorder.TOP));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Add search fields
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(new JLabel("Mã sản phẩm:"), gbc);
        
        gbc.gridx = 1;
        searchPanel.add(new JTextField(15), gbc);
        
        gbc.gridx = 2;
        searchPanel.add(new JLabel("Tên sản phẩm:"), gbc);
        
        gbc.gridx = 3;
        searchPanel.add(new JTextField(15), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        searchPanel.add(new JLabel("Loại sản phẩm:"), gbc);
        
        gbc.gridx = 1;
        searchPanel.add(new JComboBox<>(new String[]{"Tất cả", "Loại 1", "Loại 2"}), gbc);
        
        gbc.gridx = 2;
        searchPanel.add(new JLabel("Trạng thái:"), gbc);
        
        gbc.gridx = 3;
        searchPanel.add(new JComboBox<>(new String[]{"Tất cả", "Còn hàng", "Hết hàng"}), gbc);
        
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        JButton searchButton = new JButton("Tìm kiếm");
        searchButton.setPreferredSize(new Dimension(100, 40));
        searchPanel.add(searchButton, gbc);
        
        // Create table for product listing
        String[] columns = {"Mã SP", "Tên SP", "Loại SP", "Giá", "Số lượng", "Trạng thái"};
        Object[][] data = {
            {"SP001", "Sản phẩm 1", "Loại 1", "100.000 đ", "50", "Còn hàng"},
            {"SP002", "Sản phẩm 2", "Loại 2", "150.000 đ", "30", "Còn hàng"},
            {"SP003", "Sản phẩm 3", "Loại 1", "200.000 đ", "0", "Hết hàng"}
        };
        
        JTable productTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        
        // Create buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonsPanel.add(new JButton("Thêm"));
        buttonsPanel.add(new JButton("Sửa"));
        buttonsPanel.add(new JButton("Xóa"));
        buttonsPanel.add(new JButton("Làm mới"));
        buttonsPanel.add(new JButton("Xuất Excel"));
        
        // Assemble panels
        managementPanel.add(searchPanel, BorderLayout.NORTH);
        managementPanel.add(scrollPane, BorderLayout.CENTER);
        managementPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        panel.add(managementPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createProductCategoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Add actual implementation here
        panel.add(new JLabel("Loại sản phẩm functionality would go here", SwingConstants.CENTER));
        
        return panel;
    }
    
    private JPanel createInvoicePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Add actual implementation here
        panel.add(new JLabel("Hóa đơn functionality would go here", SwingConstants.CENTER));
        
        return panel;
    }
    
    private JPanel createImportTicketPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Add actual implementation here
        panel.add(new JLabel("Phiếu nhập functionality would go here", SwingConstants.CENTER));
        
        return panel;
    }
    
    private JPanel createPromotionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Add actual implementation here
        panel.add(new JLabel("Khuyến mãi functionality would go here", SwingConstants.CENTER));
        
        return panel;
    }
    
    private JPanel createEmployeePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Add actual implementation here
        panel.add(new JLabel("Nhân viên functionality would go here", SwingConstants.CENTER));
        
        return panel;
    }
    
    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Add actual implementation here
        panel.add(new JLabel("Khách hàng functionality would go here", SwingConstants.CENTER));
        
        return panel;
    }
    
    private JPanel createSupplierPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Add actual implementation here
        panel.add(new JLabel("Nhà cung cấp functionality would go here", SwingConstants.CENTER));
        
        return panel;
    }
    
    private JPanel createAccountPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Add actual implementation here
        panel.add(new JLabel("Tài khoản functionality would go here", SwingConstants.CENTER));
        
        return panel;
    }
    
    // Main method to run the application
    public static void main(String[] args) {
        // Use the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create and show the application on the EDT
        SwingUtilities.invokeLater(() -> {
            ShopStoreManagementSystem app = new ShopStoreManagementSystem();
            app.setVisible(true);
        });
    }
    
    // Method to get the main panel for integration
    public JPanel getMainPanel() {
        return mainPanel;
    }
}