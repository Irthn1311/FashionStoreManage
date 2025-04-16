package model_test.frontend;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class QuanLySanPhamPanel extends JPanel {
    // Main components
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JPanel searchPanel;
    private JPanel tablePanel;
    private JPanel detailPanel;
    
    // Search components
    private JPanel productSearchPanel;
    private JPanel quantitySearchPanel;
    private JPanel priceSearchPanel;
    private JComboBox<String> productComboBox;
    private JTextField productSearchField;
    private JComboBox<String> quantityFromComboBox;
    private JComboBox<String> quantityToComboBox;
    private JComboBox<String> priceFromComboBox;
    private JComboBox<String> priceToComboBox;
    private JButton searchButton;
    
    // Table components
    private JTable productTable;
    private JScrollPane tableScrollPane;
    private DefaultTableModel tableModel;
    
    // Detail components
    private JPanel productImagePanel;
    private JLabel productImageLabel;
    private JTextField productCodeField;
    private JTextField productNameField;
    private JTextField productQuantityField;
    private JTextField productPriceField;
    private JTextField productImageField;
    private JTextField productStatusField;
    
    public QuanLySanPhamPanel() {
        setPreferredSize(new Dimension(1000, 700));
        setLayout(new BorderLayout());
        
        initComponents();
        layoutComponents();
        setupEventHandlers();
    }
    
    private void initComponents() {
        // Initialize panels
        headerPanel = createHeaderPanel();
        contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Search section
        searchPanel = createSearchPanel();
        
        // Table section
        tablePanel = createTablePanel();
        
        // Detail section
        detailPanel = createDetailPanel();
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 153, 153));
        panel.setPreferredSize(new Dimension(1000, 60));
        
        JLabel titleLabel = new JLabel("Quản lý sản phẩm");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        
        panel.add(titleLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), 
                "Tìm kiếm", 
                TitledBorder.DEFAULT_JUSTIFICATION, 
                TitledBorder.DEFAULT_POSITION));
        panel.setBackground(new Color(173, 216, 230));
        
        JPanel searchContentPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        searchContentPanel.setOpaque(false);
        
        // Product search
        productSearchPanel = new JPanel(new BorderLayout(5, 5));
        productSearchPanel.setOpaque(false);
        productSearchPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), 
                "Sản phẩm", 
                TitledBorder.DEFAULT_JUSTIFICATION, 
                TitledBorder.DEFAULT_POSITION));
        
        productComboBox = new JComboBox<>(new String[]{"Item 1"});
        productSearchField = new JTextField();
        
        JPanel productSearchInnerPanel = new JPanel(new BorderLayout(5, 5));
        productSearchInnerPanel.setOpaque(false);
        productSearchInnerPanel.add(productComboBox, BorderLayout.WEST);
        productSearchInnerPanel.add(productSearchField, BorderLayout.CENTER);
        
        productSearchPanel.add(productSearchInnerPanel);
        
        // Quantity search
        quantitySearchPanel = new JPanel(new BorderLayout(5, 5));
        quantitySearchPanel.setOpaque(false);
        quantitySearchPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), 
                "Số lượng", 
                TitledBorder.DEFAULT_JUSTIFICATION, 
                TitledBorder.DEFAULT_POSITION));
        
        JPanel quantityFromPanel = new JPanel(new BorderLayout());
        quantityFromPanel.setOpaque(false);
        quantityFromPanel.add(new JLabel("Từ"), BorderLayout.NORTH);
        quantityFromComboBox = new JComboBox<>(new String[]{"Item 1"});
        quantityFromPanel.add(quantityFromComboBox, BorderLayout.CENTER);
        
        JPanel quantityToPanel = new JPanel(new BorderLayout());
        quantityToPanel.setOpaque(false);
        quantityToPanel.add(new JLabel("Đến"), BorderLayout.NORTH);
        quantityToComboBox = new JComboBox<>(new String[]{"Item 1"});
        quantityToPanel.add(quantityToComboBox, BorderLayout.CENTER);
        
        JPanel quantityInnerPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        quantityInnerPanel.setOpaque(false);
        quantityInnerPanel.add(quantityFromPanel);
        quantityInnerPanel.add(quantityToPanel);
        
        quantitySearchPanel.add(quantityInnerPanel);
        
        // Price search
        priceSearchPanel = new JPanel(new BorderLayout(5, 5));
        priceSearchPanel.setOpaque(false);
        priceSearchPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), 
                "Đơn giá", 
                TitledBorder.DEFAULT_JUSTIFICATION, 
                TitledBorder.DEFAULT_POSITION));
        
        JPanel priceFromPanel = new JPanel(new BorderLayout());
        priceFromPanel.setOpaque(false);
        priceFromPanel.add(new JLabel("Từ"), BorderLayout.NORTH);
        priceFromComboBox = new JComboBox<>(new String[]{"Item 1"});
        priceFromPanel.add(priceFromComboBox, BorderLayout.CENTER);
        
        JPanel priceToPanel = new JPanel(new BorderLayout());
        priceToPanel.setOpaque(false);
        priceToPanel.add(new JLabel("Đến"), BorderLayout.NORTH);
        priceToComboBox = new JComboBox<>(new String[]{"Item 1"});
        priceToPanel.add(priceToComboBox, BorderLayout.CENTER);
        
        JPanel priceInnerPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        priceInnerPanel.setOpaque(false);
        priceInnerPanel.add(priceFromPanel);
        priceInnerPanel.add(priceToPanel);
        
        priceSearchPanel.add(priceInnerPanel);
        
        // Search button
        searchButton = new JButton("Tìm kiếm");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(searchButton);
        
        // Add all to search content panel
        searchContentPanel.add(productSearchPanel);
        searchContentPanel.add(quantitySearchPanel);
        searchContentPanel.add(priceSearchPanel);
        searchContentPanel.add(buttonPanel);
        
        panel.add(searchContentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Create table model and table
        String[] columnNames = {"STT", "Mã SP", "Tên SP", "Số Lượng", "Đơn Giá", "Hình Ảnh", "Trạng Thái"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        productTable = new JTable(tableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.setRowHeight(25);
        
        // Set equal column widths
        TableColumnModel columnModel = productTable.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(100);
        }
        
        tableScrollPane = new JScrollPane(productTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        panel.add(tableScrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createDetailPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(178, 178, 255));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Left side - Image panel
        productImagePanel = new JPanel(new BorderLayout());
        productImagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        productImagePanel.setOpaque(false);
        
        productImageLabel = new JLabel("Img SP");
        productImageLabel.setHorizontalAlignment(JLabel.CENTER);
        productImageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        productImageLabel.setBackground(Color.WHITE);
        productImageLabel.setOpaque(true);
        productImageLabel.setPreferredSize(new Dimension(300, 220));
        
        productImagePanel.add(productImageLabel, BorderLayout.CENTER);
        
        // Right side - Fields
        JPanel fieldsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        fieldsPanel.setOpaque(false);
        
        productCodeField = createDetailTextField("Mã SP");
        productNameField = createDetailTextField("Tên Sp");
        productQuantityField = createDetailTextField("Số Lượng");
        productPriceField = createDetailTextField("Đơn giá");
        productImageField = createDetailTextField("Hình Ảnh");
        productStatusField = createDetailTextField("Trạng Thái");
        
        fieldsPanel.add(productCodeField);
        fieldsPanel.add(productPriceField);
        fieldsPanel.add(productNameField);
        fieldsPanel.add(productImageField);
        fieldsPanel.add(productQuantityField);
        fieldsPanel.add(productStatusField);
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.add(fieldsPanel, BorderLayout.CENTER);
        
        // Add components to main panel
        panel.add(productImagePanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JTextField createDetailTextField(String label) {
        JTextField textField = new JTextField(20);
        textField.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                label,
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION));
        return textField;
    }
    
    private void layoutComponents() {
        // Add panels to content panel
        contentPanel.add(searchPanel, BorderLayout.NORTH);
        contentPanel.add(tablePanel, BorderLayout.CENTER);
        contentPanel.add(detailPanel, BorderLayout.SOUTH);
        
        // Add main components to this panel
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        // Search button action
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        
        // Table selection listener
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow >= 0) {
                    updateDetailPanel(selectedRow);
                }
            }
        });
    }
    
    private void performSearch() {
        // This would be implemented to actually search for products
        JOptionPane.showMessageDialog(this, "Tìm kiếm sản phẩm với các điều kiện đã chọn", 
                "Tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void updateDetailPanel(int rowIndex) {
        // This would update the detail panel with data from the selected row
        if (rowIndex >= 0 && rowIndex < tableModel.getRowCount()) {
            productCodeField.setText(tableModel.getValueAt(rowIndex, 1).toString());
            productNameField.setText(tableModel.getValueAt(rowIndex, 2).toString());
            productQuantityField.setText(tableModel.getValueAt(rowIndex, 3).toString());
            productPriceField.setText(tableModel.getValueAt(rowIndex, 4).toString());
            productImageField.setText(tableModel.getValueAt(rowIndex, 5).toString());
            productStatusField.setText(tableModel.getValueAt(rowIndex, 6).toString());
        }
    }
    
    // Utility method to add sample data for demonstration
    public void addSampleData() {
        for (int i = 1; i <= 10; i++) {
            Object[] row = {
                i,
                "SP" + i,
                "Sản phẩm " + i,
                i * 10,
                i * 100000,
                "image" + i + ".jpg",
                i % 2 == 0 ? "Còn hàng" : "Hết hàng"
            };
            tableModel.addRow(row);
        }
    }
    
    // Method to get the main panel for integration
    public JPanel getPanel() {
        return this;
    }
    
    // Main method for standalone testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản Lý Sản Phẩm");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            QuanLySanPhamPanel panel = new QuanLySanPhamPanel();
            panel.addSampleData(); // Add sample data for testing
            
            frame.getContentPane().add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
