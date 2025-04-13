package screens;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class QuanLyKhuyenMaiPanel extends JPanel {
    // Components declaration
    private JPanel headerPanel;
    private JLabel titleLabel;
    
    private JPanel searchPanel;
    private JLabel maSPLabel, maKHLabel;
    private JTextField maSPField, maKHField;
    
    private JPanel timePanel;
    private JLabel timeLabel, tuTimeLabel, denTimeLabel;
    private JTextField tuTimeField, denTimeField;
    
    private JPanel pricePanel;
    private JLabel priceLabel, tuPriceLabel, denPriceLabel;
    private JTextField tuPriceField, denPriceField;
    
    private JButton searchButton;
    
    private JPanel actionPanel;
    private JButton addButton, editButton, deleteButton;
    
    private JTable promotionTable;
    private DefaultTableModel tableModel;
    private JScrollPane tableScrollPane;
    
    private JPanel addFormPanel1;
    private JPanel addFormPanel2;
    private JTextField maSPAddField1, tenSPField1, timeStartField1, timeEndField1, giaOldField1, giaNewField1, khacField1;
    private JTextField maSPAddField2, tenSPField2, timeStartField2, timeEndField2, giaOldField2, giaNewField2, khacField2;
    private JButton cancelButton1, addItemButton1, cancelButton2, addItemButton2;
    
    private JButton saveExportButton;
    
    // Constructor
    public QuanLyKhuyenMaiPanel() {
        initComponents();
        layoutComponents();
        setupEventListeners();
    }
    
    // Initialize components
    private void initComponents() {
        // Set panel properties
        setLayout(new BorderLayout(0, 5));
        
        // Header panel
        headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 153, 153));
        headerPanel.setPreferredSize(new Dimension(800, 60));
        titleLabel = new JLabel("Quản lý Hàng Khuyến Mãi");
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        
        // Search panel components
        searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK), "Tìm kiếm",
            TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.PLAIN, 12)
        ));
        searchPanel.setBackground(new Color(120, 180, 210));
        
        maSPLabel = new JLabel("Mã SP");
        maSPField = new JTextField(15);
        maKHLabel = new JLabel("Mã KH");
        maKHField = new JTextField(15);
        
        // Time panel
        timePanel = new JPanel();
        timePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        timeLabel = new JLabel("Thời gian");
        tuTimeLabel = new JLabel("Từ");
        denTimeLabel = new JLabel("Đến");
        tuTimeField = new JTextField(10);
        denTimeField = new JTextField(10);
        
        // Price panel
        pricePanel = new JPanel();
        pricePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        priceLabel = new JLabel("Đơn giá");
        tuPriceLabel = new JLabel("Từ");
        denPriceLabel = new JLabel("Đến");
        tuPriceField = new JTextField(10);
        denPriceField = new JTextField(10);
        
        // Search button
        searchButton = new JButton("Tìm kiếm");
        searchButton.setBackground(Color.WHITE);
        
        // Action panel
        actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        actionPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK), "Chỉnh sửa",
            TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.PLAIN, 12)
        ));
        actionPanel.setBackground(new Color(120, 180, 210));
        
        addButton = new JButton("Thêm");
        editButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");
        addButton.setBackground(Color.WHITE);
        editButton.setBackground(Color.WHITE);
        deleteButton.setBackground(Color.WHITE);
        
        // Table
        String[] columnNames = {"STT", "Mã SP", "Tên SP", "Time start", "Time end", "Giá cũ", "Giá mới", "Khác(nhập vào)", "Chi tiết"};
        tableModel = new DefaultTableModel(columnNames, 0);
        promotionTable = new JTable(tableModel);
        tableScrollPane = new JScrollPane(promotionTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        // Add form panels
        addFormPanel1 = createAddFormPanel();
        addFormPanel2 = createAddFormPanel();
        
        // Save and export button
        saveExportButton = new JButton("Lưu và xuất file");
        saveExportButton.setBackground(Color.WHITE);
        saveExportButton.setPreferredSize(new Dimension(300, 30));
    }
    
    private JPanel createAddFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK), "Bảng thêm",
            TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.PLAIN, 12)
        ));
        panel.setBackground(new Color(120, 180, 210));
        
        JLabel maSPLabel = new JLabel("Mã SP");
        JLabel tenSPLabel = new JLabel("Tên SP");
        JLabel timeStartLabel = new JLabel("Time start");
        JLabel timeEndLabel = new JLabel("Time end");
        JLabel giaOldLabel = new JLabel("Giá cũ");
        JLabel giaNewLabel = new JLabel("Giá mới");
        JLabel khacLabel = new JLabel("Khác");
        
        JTextField maSPField = new JTextField(10);
        JTextField tenSPField = new JTextField(10);
        JTextField timeStartField = new JTextField(10);
        JTextField timeEndField = new JTextField(10);
        JTextField giaOldField = new JTextField(10);
        JTextField giaNewField = new JTextField(10);
        JTextField khacField = new JTextField(10);
        
        JButton cancelButton = new JButton("Hủy");
        JButton addItemButton = new JButton("Thêm");
        cancelButton.setBackground(Color.WHITE);
        addItemButton.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // First row - labels
        gbc.gridy = 0;
        gbc.gridx = 0;
        panel.add(maSPLabel, gbc);
        
        gbc.gridx = 1;
        panel.add(tenSPLabel, gbc);
        
        gbc.gridx = 2;
        panel.add(timeStartLabel, gbc);
        
        gbc.gridx = 3;
        panel.add(timeEndLabel, gbc);
        
        gbc.gridx = 4;
        panel.add(giaOldLabel, gbc);
        
        gbc.gridx = 5;
        panel.add(giaNewLabel, gbc);
        
        gbc.gridx = 6;
        panel.add(khacLabel, gbc);
        
        // Second row - text fields
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(maSPField, gbc);
        
        gbc.gridx = 1;
        panel.add(tenSPField, gbc);
        
        gbc.gridx = 2;
        panel.add(timeStartField, gbc);
        
        gbc.gridx = 3;
        panel.add(timeEndField, gbc);
        
        gbc.gridx = 4;
        panel.add(giaOldField, gbc);
        
        gbc.gridx = 5;
        panel.add(giaNewField, gbc);
        
        gbc.gridx = 6;
        panel.add(khacField, gbc);
        
        // Buttons
        gbc.gridx = 7;
        gbc.gridy = 0;
        panel.add(cancelButton, gbc);
        
        gbc.gridy = 1;
        panel.add(addItemButton, gbc);
        
        return panel;
    }
    
    // Layout components
    private void layoutComponents() {
        // Set main layout
        setBackground(new Color(100, 160, 190));
        
        // Add components to header panel
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(100, 160, 190));
        
        // Search panel layout
        JPanel searchContainerPanel = new JPanel(new GridBagLayout());
        searchContainerPanel.setBackground(new Color(100, 160, 190));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // ID fields
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        idPanel.setBackground(new Color(120, 180, 210));
        idPanel.add(maSPLabel);
        idPanel.add(maSPField);
        idPanel.add(Box.createHorizontalStrut(10));
        idPanel.add(maKHLabel);
        idPanel.add(maKHField);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        searchPanel.add(idPanel, gbc);
        
        // Time panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        timePanel.add(timeLabel);
        timePanel.add(Box.createHorizontalStrut(10));
        timePanel.add(tuTimeLabel);
        timePanel.add(tuTimeField);
        timePanel.add(Box.createHorizontalStrut(10));
        timePanel.add(denTimeLabel);
        timePanel.add(denTimeField);
        searchPanel.add(timePanel, gbc);
        
        // Price panel
        gbc.gridx = 0;
        gbc.gridy = 2;
        pricePanel.add(priceLabel);
        pricePanel.add(Box.createHorizontalStrut(10));
        pricePanel.add(tuPriceLabel);
        pricePanel.add(tuPriceField);
        pricePanel.add(Box.createHorizontalStrut(10));
        pricePanel.add(denPriceLabel);
        pricePanel.add(denPriceField);
        pricePanel.add(Box.createHorizontalStrut(30));
        pricePanel.add(searchButton);
        searchPanel.add(pricePanel, gbc);
        
        contentPanel.add(searchPanel);
        contentPanel.add(Box.createVerticalStrut(5));
        
        // Action panel
        actionPanel.add(addButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);
        contentPanel.add(actionPanel);
        contentPanel.add(Box.createVerticalStrut(5));
        
        // Table
        contentPanel.add(tableScrollPane);
        contentPanel.add(Box.createVerticalStrut(5));
        
        // Add form panels
        contentPanel.add(addFormPanel1);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(addFormPanel2);
        contentPanel.add(Box.createVerticalStrut(10));
        
        // Save & Export button
        JPanel savePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        savePanel.setBackground(new Color(100, 160, 190));
        savePanel.add(saveExportButton);
        contentPanel.add(savePanel);
        
        // Add content panel to main panel
        add(contentPanel, BorderLayout.CENTER);
    }
    
    // Setup event listeners
    private void setupEventListeners() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPromotion();
            }
        });
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepareAddForm();
            }
        });
        
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSelectedPromotion();
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedPromotion();
            }
        });
        
        saveExportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAndExport();
            }
        });
    }
    
    // Handle search action
    private void searchPromotion() {
        String maSP = maSPField.getText();
        String maKH = maKHField.getText();
        String tuTime = tuTimeField.getText();
        String denTime = denTimeField.getText();
        String tuPrice = tuPriceField.getText();
        String denPrice = denPriceField.getText();
        
        // Implement search logic
        JOptionPane.showMessageDialog(this, "Đang tìm kiếm với các thông số:\n" +
            "Mã SP: " + maSP + "\nMã KH: " + maKH + "\n" +
            "Thời gian từ: " + tuTime + " đến: " + denTime + "\n" +
            "Giá từ: " + tuPrice + " đến: " + denPrice);
            
        // Clear current table data and add search results
        tableModel.setRowCount(0);
        // Sample data
        tableModel.addRow(new Object[]{1, "SP001", "Sản phẩm mẫu 1", "01/01/2025", "31/01/2025", "100,000đ", "80,000đ", "10%", "Chi tiết"});
        tableModel.addRow(new Object[]{2, "SP002", "Sản phẩm mẫu 2", "01/02/2025", "28/02/2025", "200,000đ", "150,000đ", "25%", "Chi tiết"});
    }
    
    // Prepare add form
    private void prepareAddForm() {
        // Clear form fields
        JOptionPane.showMessageDialog(this, "Mở form thêm mới khuyến mãi");
    }
    
    // Edit promotion
    private void editSelectedPromotion() {
        int selectedRow = promotionTable.getSelectedRow();
        if (selectedRow != -1) {
            // Get data from selected row and fill form
            String maSP = tableModel.getValueAt(selectedRow, 1).toString();
            JOptionPane.showMessageDialog(this, "Đang sửa khuyến mãi cho sản phẩm: " + maSP);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // Delete promotion
    private void deleteSelectedPromotion() {
        int selectedRow = promotionTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc muốn xóa khuyến mãi này?", 
                "Xác nhận xóa", 
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Đã xóa khuyến mãi thành công");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // Save and export
    private void saveAndExport() {
        JOptionPane.showMessageDialog(this, "Đang lưu và xuất dữ liệu ra file");
    }
    
    // Get the main panel
    public JPanel getMainPanel() {
        return this;
    }
    
    // Main method for testing
    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Quản lý Hàng Khuyến Mãi");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1200, 800);
                frame.setLocationRelativeTo(null);
                
                QuanLyKhuyenMaiPanel panel = new QuanLyKhuyenMaiPanel();
                frame.add(panel);
                
                frame.setVisible(true);
            }
        });
    }
}