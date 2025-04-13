package model.frontend;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class QuanLyLoaiSanPhamPanel extends JPanel {
    // Khai báo các thành phần UI
    private JPanel headerPanel;
    private JLabel titleLabel;
    
    private JPanel contentPanel;
    private JPanel orderPanel;
    
    // Sản phẩm & Số lượng
    private JPanel productPanel;
    private JTextField productIdField;
    private JComboBox<String> productNameCombo;
    private JTextField quantityField;
    
    // Chi phí
    private JPanel costPanel;
    private JTextField totalCostField;
    private JTextField extraCostField;
    
    // Nhà cung cấp
    private JPanel supplierPanel;
    private JTextField supplierSelectField;
    private JComboBox<String> supplierIdCombo;
    
    // Thời gian
    private JPanel timePanel;
    private JTextField dateField;
    private JTextField dateExtraField;
    private JTextField timeField;
    private JTextField timeExtraField;
    
    // Các nút
    private JButton cancelButton;
    private JButton addButton;
    
    // Bảng dữ liệu
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JScrollPane tableScrollPane;
    
    // Các nút ở dưới
    private JButton deleteButton;
    private JButton confirmButton;
    
    public QuanLyLoaiSanPhamPanel() {
        initComponents();
        setupLayout();
        addComponents();
        setupEventHandlers();
    }
    
    private void initComponents() {
        // Thiết lập panel chính
        setLayout(new BorderLayout());
        setBackground(new Color(173, 216, 230)); // Light blue background
        
        // Header Panel
        headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 128, 128)); // Teal color
        headerPanel.setPreferredSize(new Dimension(800, 60));
        titleLabel = new JLabel("Quản lý loại sản phẩm");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        
        // Content Panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(173, 216, 230));
        
        // Order Panel (Panel đặt hàng)
        orderPanel = new JPanel();
        orderPanel.setLayout(new GridBagLayout());
        orderPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK), "Đặt hàng"));
        orderPanel.setBackground(new Color(173, 216, 230));
        
        // Product Panel
        productPanel = new JPanel();
        productPanel.setLayout(new GridBagLayout());
        productPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK), "Sản phẩm & Số lượng"));
        productPanel.setBackground(new Color(173, 216, 230));
        
        productIdField = new JTextField(10);
        productIdField.setBorder(BorderFactory.createTitledBorder("Mã SP"));
        
        productNameCombo = new JComboBox<>(new String[]{"Item 1", "Item 2", "Item 3"});
        productNameCombo.setBorder(BorderFactory.createTitledBorder("Tên SP"));
        
        quantityField = new JTextField(10);
        quantityField.setBorder(BorderFactory.createTitledBorder("Số Lượng"));
        
        // Cost Panel
        costPanel = new JPanel();
        costPanel.setLayout(new GridBagLayout());
        costPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK), "Chi phí"));
        costPanel.setBackground(new Color(173, 216, 230));
        
        totalCostField = new JTextField(15);
        totalCostField.setBorder(BorderFactory.createTitledBorder("Thành tiền"));
        
        extraCostField = new JTextField(15);
        
        // Supplier Panel
        supplierPanel = new JPanel();
        supplierPanel.setLayout(new GridBagLayout());
        supplierPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK), "Nhà cung cấp"));
        supplierPanel.setBackground(new Color(173, 216, 230));
        
        supplierSelectField = new JTextField(15);
        supplierSelectField.setBorder(BorderFactory.createTitledBorder("Chọn nhà cung cấp"));
        
        supplierIdCombo = new JComboBox<>(new String[]{"Item 1", "Item 2", "Item 3"});
        supplierIdCombo.setBorder(BorderFactory.createTitledBorder("Mã NCC"));
        
        // Time Panel
        timePanel = new JPanel();
        timePanel.setLayout(new GridBagLayout());
        timePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK), "Thời gian"));
        timePanel.setBackground(new Color(173, 216, 230));
        
        dateField = new JTextField(8);
        dateField.setBorder(BorderFactory.createTitledBorder("Ngày"));
        
        dateExtraField = new JTextField(12);
        
        timeField = new JTextField(8);
        timeField.setBorder(BorderFactory.createTitledBorder("Giờ"));
        
        timeExtraField = new JTextField(12);
        
        // Các nút chức năng bên phải
        cancelButton = new JButton("Hủy");
        addButton = new JButton("Thêm");
        
        // Khởi tạo bảng dữ liệu
        String[] columnNames = {"STT", "Mã SP", "Tên SP", "Mã NCC", "Số Lượng", "Thành tiền", "Thời gian", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0);
        dataTable = new JTable(tableModel);
        tableScrollPane = new JScrollPane(dataTable);
        dataTable.setFillsViewportHeight(true);
        
        // Các nút dưới bảng
        deleteButton = new JButton("Xóa");
        confirmButton = new JButton("Xác nhận và Xuất hóa đơn");
    }
    
    private void setupLayout() {
        // Header setup
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    }
    
    private void addComponents() {
        // Add title to header
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Add components to product panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        productPanel.add(productIdField, gbc);
        
        gbc.gridx = 1;
        productPanel.add(productNameCombo, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        productPanel.add(quantityField, gbc);
        
        // Add components to cost panel
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        costPanel.add(totalCostField, gbc);
        
        gbc.gridy = 1;
        costPanel.add(extraCostField, gbc);
        
        // Add components to supplier panel
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        supplierPanel.add(supplierSelectField, gbc);
        
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        supplierPanel.add(supplierIdCombo, gbc);
        
        // Add components to time panel
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        timePanel.add(dateField, gbc);
        
        gbc.gridx = 1;
        timePanel.add(dateExtraField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        timePanel.add(timeField, gbc);
        
        gbc.gridx = 1;
        timePanel.add(timeExtraField, gbc);
        
        // Add all panels to the order panel
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        orderPanel.add(productPanel, gbc);
        
        gbc.gridx = 1;
        orderPanel.add(costPanel, gbc);
        
        gbc.gridx = 2;
        orderPanel.add(cancelButton, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        orderPanel.add(supplierPanel, gbc);
        
        gbc.gridx = 1;
        orderPanel.add(timePanel, gbc);
        
        gbc.gridx = 2;
        orderPanel.add(addButton, gbc);
        
        // Add order panel and table to content panel
        contentPanel.add(orderPanel, BorderLayout.NORTH);
        contentPanel.add(tableScrollPane, BorderLayout.CENTER);
        
        // Add buttons at the bottom
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        bottomPanel.setBackground(new Color(173, 216, 230));
        bottomPanel.add(deleteButton);
        bottomPanel.add(confirmButton);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Add content panel to main panel
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        // Add event handlers for buttons
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProductToTable();
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedRow();
            }
        });
        
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmAndExport();
            }
        });
    }
    
    private void clearFields() {
        productIdField.setText("");
        productNameCombo.setSelectedIndex(0);
        quantityField.setText("");
        totalCostField.setText("");
        extraCostField.setText("");
        supplierSelectField.setText("");
        supplierIdCombo.setSelectedIndex(0);
        dateField.setText("");
        dateExtraField.setText("");
        timeField.setText("");
        timeExtraField.setText("");
    }
    
    private void addProductToTable() {
        // Lấy dữ liệu từ các trường nhập liệu
        String maSP = productIdField.getText();
        String tenSP = (String) productNameCombo.getSelectedItem();
        String soLuong = quantityField.getText();
        String maNCC = (String) supplierIdCombo.getSelectedItem();
        String thanhTien = totalCostField.getText();
        String thoiGian = dateField.getText() + " " + timeField.getText();
        
        // Thêm dòng mới vào bảng
        int rowCount = tableModel.getRowCount();
        tableModel.addRow(new Object[]{
            rowCount + 1, // STT
            maSP,
            tenSP,
            maNCC,
            soLuong,
            thanhTien,
            thoiGian,
            "Mới"  // Trạng thái mặc định
        });
        
        // Clear fields after adding
        clearFields();
    }
    
    private void deleteSelectedRow() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            
            // Cập nhật lại STT
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                tableModel.setValueAt(i + 1, i, 0);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn dòng cần xóa!", 
                "Thông báo", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void confirmAndExport() {
        if (tableModel.getRowCount() > 0) {
            JOptionPane.showMessageDialog(this, 
                "Đã xác nhận và xuất hóa đơn thành công!", 
                "Thông báo", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Không có dữ liệu để xuất hóa đơn!", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // Phương thức lấy panel chính
    public JPanel getMainPanel() {
        return this;
    }
    
    public static void main(String[] args) {
        try {
            // Set look and feel to system default
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Quản Lý Loại Sản Phẩm");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(900, 700);
                frame.setLocationRelativeTo(null);
                
                QuanLyLoaiSanPhamPanel panel = new QuanLyLoaiSanPhamPanel();
                frame.add(panel);
                
                frame.setVisible(true);
            }
        });
    }
}