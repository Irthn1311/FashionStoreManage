package screens;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductManagementSystem extends JFrame {
    private JTextField maSpTextField, tenSpTextField, soLuongTextField;
    private JTextField thanhTienTextField;
    private JTextField maNccTextField;
    private JTextField ngayTextField, gioTextField;
    private JComboBox<String> maSpComboBox, tenSpComboBox, maNccComboBox;
    private JTextField searchSupplierTextField;
    private JTable productTable;
    private DefaultTableModel tableModel;

    public ProductManagementSystem() {
        // Set up the JFrame
        setTitle("Quản lý loại sản phẩm");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Set the background color
        getContentPane().setBackground(new Color(51, 153, 153));

        // Create the main header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 128, 128));
        JLabel titleLabel = new JLabel("Quản lý loại sản phẩm");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Create the main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(100, 149, 177));
        
        // Create the order form panel
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new BorderLayout());
        orderPanel.setBackground(new Color(100, 149, 177));
        orderPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), "Đặt hàng",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.PLAIN, 12), Color.BLACK));

        // Create the panel for product & quantity information
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new GridBagLayout());
        productPanel.setBackground(new Color(100, 149, 177));
        productPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), "Sản phẩm & Số lượng",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.PLAIN, 12), Color.BLACK));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // MaSP row
        gbc.gridx = 0;
        gbc.gridy = 0;
        maSpTextField = new JTextField(10);
        productPanel.add(maSpTextField, gbc);

        gbc.gridx = 1;
        String[] maSpItems = {"Item 1"};
        maSpComboBox = new JComboBox<>(maSpItems);
        maSpComboBox.setPreferredSize(new Dimension(150, 25));
        productPanel.add(maSpComboBox, gbc);

        // TenSP row
        gbc.gridx = 0;
        gbc.gridy = 1;
        tenSpTextField = new JTextField("Tên SP", 10);
        productPanel.add(tenSpTextField, gbc);

        gbc.gridx = 1;
        String[] tenSpItems = {"Item 1"};
        tenSpComboBox = new JComboBox<>(tenSpItems);
        tenSpComboBox.setPreferredSize(new Dimension(150, 25));
        productPanel.add(tenSpComboBox, gbc);

        // SoLuong row
        gbc.gridx = 0;
        gbc.gridy = 2;
        soLuongTextField = new JTextField("Số Lượng", 10);
        productPanel.add(soLuongTextField, gbc);

        gbc.gridx = 1;
        JTextField soLuongInputField = new JTextField(15);
        productPanel.add(soLuongInputField, gbc);

        // Create the panel for cost information
        JPanel costPanel = new JPanel();
        costPanel.setLayout(new BorderLayout());
        costPanel.setBackground(new Color(100, 149, 177));
        costPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), "Chi phí",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.PLAIN, 12), Color.BLACK));

        JLabel thanhTienLabel = new JLabel("Thành tiền");
        thanhTienTextField = new JTextField(15);
        costPanel.add(thanhTienLabel, BorderLayout.NORTH);
        costPanel.add(thanhTienTextField, BorderLayout.CENTER);

        // Create the panel for supplier information
        JPanel supplierPanel = new JPanel();
        supplierPanel.setLayout(new GridBagLayout());
        supplierPanel.setBackground(new Color(100, 149, 177));
        supplierPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), "Nhà cung cấp",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.PLAIN, 12), Color.BLACK));

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Search supplier row
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        searchSupplierTextField = new JTextField("Chọn nhà cung cấp", 20);
        supplierPanel.add(searchSupplierTextField, gbc);

        // MaNCC row
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        maNccTextField = new JTextField("Mã NCC", 10);
        supplierPanel.add(maNccTextField, gbc);

        gbc.gridx = 1;
        String[] maNccItems = {"Item 1"};
        maNccComboBox = new JComboBox<>(maNccItems);
        maNccComboBox.setPreferredSize(new Dimension(150, 25));
        supplierPanel.add(maNccComboBox, gbc);

        // Create the panel for time information
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new GridBagLayout());
        timePanel.setBackground(new Color(100, 149, 177));
        timePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), "Thời gian",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.PLAIN, 12), Color.BLACK));

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Ngay row
        gbc.gridx = 0;
        gbc.gridy = 0;
        ngayTextField = new JTextField("Ngày", 10);
        timePanel.add(ngayTextField, gbc);

        gbc.gridx = 1;
        JTextField ngayInputField = new JTextField(15);
        timePanel.add(ngayInputField, gbc);

        // Gio row
        gbc.gridx = 0;
        gbc.gridy = 1;
        gioTextField = new JTextField("Giờ", 10);
        timePanel.add(gioTextField, gbc);

        gbc.gridx = 1;
        JTextField gioInputField = new JTextField(15);
        timePanel.add(gioInputField, gbc);

        // Create buttons
        JButton huyButton = new JButton("Hủy");
        JButton themButton = new JButton("Thêm");
        
        // Create the top panel with all the input sections
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(new Color(100, 149, 177));
        
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Add product panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        topPanel.add(productPanel, gbc);
        
        // Add cost panel and huy button
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        
        JPanel costButtonPanel = new JPanel(new BorderLayout());
        costButtonPanel.setBackground(new Color(100, 149, 177));
        costButtonPanel.add(costPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel1.setBackground(new Color(100, 149, 177));
        buttonPanel1.add(huyButton);
        costButtonPanel.add(buttonPanel1, BorderLayout.SOUTH);
        
        topPanel.add(costButtonPanel, gbc);
        
        // Add supplier panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        topPanel.add(supplierPanel, gbc);
        
        // Add time panel and them button
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        
        JPanel timeButtonPanel = new JPanel(new BorderLayout());
        timeButtonPanel.setBackground(new Color(100, 149, 177));
        timeButtonPanel.add(timePanel, BorderLayout.CENTER);
        
        JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel2.setBackground(new Color(100, 149, 177));
        buttonPanel2.add(themButton);
        timeButtonPanel.add(buttonPanel2, BorderLayout.SOUTH);
        
        topPanel.add(timeButtonPanel, gbc);
        
        orderPanel.add(topPanel, BorderLayout.CENTER);
        
        // Create table for displaying products
        String[] columnNames = {"STT", "Mã SP", "Tên SP", "Mã NCC", "Số Lượng", "Thành tiền", "Thời gian", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);
        
        // Create bottom buttons
        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        bottomButtonPanel.setBackground(new Color(100, 149, 177));
        
        JButton deleteButton = new JButton("Xóa");
        JButton confirmButton = new JButton("Xác nhận và Xuất hóa đơn");
        
        bottomButtonPanel.add(deleteButton);
        bottomButtonPanel.add(confirmButton);
        
        // Add components to the main content panel
        contentPanel.add(orderPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(bottomButtonPanel, BorderLayout.SOUTH);
        
        add(contentPanel, BorderLayout.CENTER);
        
        // Add action listeners
        huyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        
        themButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedProduct();
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
        maSpTextField.setText("");
        tenSpTextField.setText("");
        soLuongTextField.setText("");
        thanhTienTextField.setText("");
        searchSupplierTextField.setText("");
        maNccTextField.setText("");
        ngayTextField.setText("");
        gioTextField.setText("");
    }
    
    private void addProduct() {
        // Get values from fields
        String maSp = maSpComboBox.getSelectedItem().toString();
        String tenSp = tenSpComboBox.getSelectedItem().toString();
        String soLuong = soLuongTextField.getText();
        String thanhTien = thanhTienTextField.getText();
        String maNcc = maNccComboBox.getSelectedItem().toString();
        String thoiGian = ngayTextField.getText() + " " + gioTextField.getText();
        
        // Add row to table
        int stt = tableModel.getRowCount() + 1;
        tableModel.addRow(new Object[]{stt, maSp, tenSp, maNcc, soLuong, thanhTien, thoiGian, "Chờ xử lý"});
        
        // Clear fields
        clearFields();
    }
    
    private void deleteSelectedProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            
            // Update STT for remaining rows
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                tableModel.setValueAt(i + 1, i, 0);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void confirmAndExport() {
        if (tableModel.getRowCount() > 0) {
            JOptionPane.showMessageDialog(this, "Đã xác nhận và xuất hóa đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            // Here you would add code to generate an invoice or export data
            tableModel.setRowCount(0); // Clear the table after export
        } else {
            JOptionPane.showMessageDialog(this, "Không có sản phẩm nào để xuất hóa đơn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ProductManagementSystem system = new ProductManagementSystem();
                system.setVisible(true);
            }
        });
    }
}