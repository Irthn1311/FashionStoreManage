package model.frontend;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * Panel quản lý xuất hàng với các chức năng nhập thông tin sản phẩm,
 * khách hàng và tạo phiếu xuất hàng.
 */
public class QuanLyXuatHangPanel extends JPanel {
    // Khai báo các thành phần UI
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JPanel formPanel;
    private JPanel tablePanel;
    private JPanel buttonPanel;
    
    // Các component cho form nhập liệu
    private JTextField tfProductId;
    private JComboBox<String> cbProductName;
    private JComboBox<String> cbProductName2;
    private JTextField tfQuantity;
    private JTextField tfTotalAmount;
    private JTextField tfExpenseDetails;
    private JTextField tfCustomerId;
    private JTextField tfCustomerName;
    private JTextField tfDate;
    private JTextField tfTime;
    
    // Các button
    private JButton btnCancel;
    private JButton btnAdd;
    private JButton btnDelete;
    private JButton btnConfirm;
    
    // Bảng dữ liệu
    private JTable tableOrders;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    
    /**
     * Constructor khởi tạo panel quản lý xuất hàng
     */
    public QuanLyXuatHangPanel() {
        initComponents();
    }
    
    /**
     * Khởi tạo và thiết lập các thành phần UI
     */
    private void initComponents() {
        // Thiết lập font mặc định cho toàn bộ ứng dụng
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // Thiết lập font mặc định
            Font defaultFont = new Font("Arial", Font.PLAIN, 12);
            UIManager.put("Label.font", defaultFont);
            UIManager.put("TextField.font", defaultFont);
            UIManager.put("ComboBox.font", defaultFont);
            UIManager.put("Button.font", defaultFont);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setPreferredSize(new Dimension(1000, 700));
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(0, 148, 143));
        
        // Khởi tạo header panel
        createHeaderPanel();
        
        // Khởi tạo content panel
        createContentPanel();
        
        // Thêm các panel vào panel chính
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    /**
     * Tạo panel tiêu đề (header)
     */
    private void createHeaderPanel() {
        headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(0, 148, 143));
        headerPanel.setPreferredSize(new Dimension(1000, 60));
        
        JLabel lblTitle = new JLabel("Quản lý xuất hàng");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        headerPanel.add(lblTitle, BorderLayout.CENTER);
    }
    
    /**
     * Tạo panel nội dung chính
     */
    private void createContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(0, 10));
        contentPanel.setBackground(new Color(0, 148, 143));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Tạo form panel
        createFormPanel();
        
        // Tạo table panel
        createTablePanel();
        
        // Tạo button panel
        createButtonPanel();
        
        // Thêm các panel con vào content panel
        contentPanel.add(formPanel, BorderLayout.NORTH);
        contentPanel.add(tablePanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Tạo panel form nhập liệu
     */
    private void createFormPanel() {
        formPanel = new JPanel();
        formPanel.setLayout(new BorderLayout());
        formPanel.setBackground(new Color(0, 148, 143));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Xuất hàng",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.PLAIN, 12)
        ));
        
        // Panel chính chứa 4 panel con và 2 button
        JPanel mainFormPanel = new JPanel();
        mainFormPanel.setLayout(null);  // Sử dụng null layout
        mainFormPanel.setPreferredSize(new Dimension(980, 310));
        mainFormPanel.setBackground(new Color(0, 148, 143));
        
        // Panel sản phẩm
        JPanel productPanel = createProductPanel();
        productPanel.setBounds(35, 30, 350, 125);
        
        // Panel chi phí
        JPanel expensePanel = createExpensePanel();
        expensePanel.setBounds(595, 30, 350, 125);
        
        // Panel khách hàng
        JPanel customerPanel = createCustomerPanel();
        customerPanel.setBounds(35, 165, 350, 125);
        
        // Panel thời gian
        JPanel timePanel = createTimePanel();
        timePanel.setBounds(595, 165, 350, 125);
        
        // Button Hủy và Thêm
        JPanel buttonContainerRight = new JPanel();
        buttonContainerRight.setLayout(null);
        buttonContainerRight.setBackground(new Color(0, 148, 143));
        buttonContainerRight.setPreferredSize(new Dimension(100, 310));
        
        btnCancel = new JButton("Hủy");
        btnCancel.setBounds(10, 75, 80, 35);
        btnCancel.addActionListener(e -> clearForm());
        
        btnAdd = new JButton("Thêm");
        btnAdd.setBounds(10, 210, 80, 35);
        btnAdd.addActionListener(e -> addOrder());
        
        buttonContainerRight.add(btnCancel);
        buttonContainerRight.add(btnAdd);
        
        // Thêm các panel vào main form panel
        mainFormPanel.add(productPanel);
        mainFormPanel.add(expensePanel);
        mainFormPanel.add(customerPanel);
        mainFormPanel.add(timePanel);
        
        formPanel.add(mainFormPanel, BorderLayout.CENTER);
        formPanel.add(buttonContainerRight, BorderLayout.EAST);
    }
    
    /**
     * Tạo panel sản phẩm
     */
    private JPanel createProductPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0, 148, 143));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Sản phẩm & Số lượng",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.PLAIN, 12)
        ));
        
        // Mã SP
        JLabel lblProductId = new JLabel("Mã SP");
        lblProductId.setFont(new Font("Arial", Font.PLAIN, 12));
        lblProductId.setBounds(15, 25, 50, 25);
        
        tfProductId = new JTextField();
        tfProductId.setFont(new Font("Arial", Font.PLAIN, 12));
        tfProductId.setBounds(15, 25, 140, 25);
        
        cbProductName = new JComboBox<>(new String[]{"Item 1"});
        cbProductName.setFont(new Font("Arial", Font.PLAIN, 12));
        cbProductName.setBounds(165, 25, 170, 25);
        
        // Tên SP
        JLabel lblProductName = new JLabel("Tên SP");
        lblProductName.setFont(new Font("Arial", Font.PLAIN, 12));
        lblProductName.setBounds(15, 60, 50, 25);
        
        cbProductName2 = new JComboBox<>(new String[]{"Item 1"});
        cbProductName2.setFont(new Font("Arial", Font.PLAIN, 12));
        cbProductName2.setBounds(165, 60, 170, 25);
        
        // Số lượng
        JLabel lblQuantity = new JLabel("Số Lượng");
        lblQuantity.setFont(new Font("Arial", Font.PLAIN, 12));
        lblQuantity.setBounds(15, 95, 80, 25);
        
        tfQuantity = new JTextField();
        tfQuantity.setFont(new Font("Arial", Font.PLAIN, 12));
        tfQuantity.setBounds(165, 95, 170, 25);
        
        panel.add(lblProductId);
        panel.add(tfProductId);
        panel.add(cbProductName);
        panel.add(lblProductName);
        panel.add(cbProductName2);
        panel.add(lblQuantity);
        panel.add(tfQuantity);
        
        return panel;
    }
    
    /**
     * Tạo panel chi phí
     */
    private JPanel createExpensePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0, 148, 143));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Chi phí",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.PLAIN, 12)
        ));
        
        JLabel lblTotalAmount = new JLabel("Thành tiền");
        lblTotalAmount.setBounds(130, 25, 80, 25);
        
        tfTotalAmount = new JTextField();
        tfTotalAmount.setBounds(90, 50, 170, 25);
        
        tfExpenseDetails = new JTextField();
        tfExpenseDetails.setBounds(15, 85, 320, 25);
        
        panel.add(lblTotalAmount);
        panel.add(tfTotalAmount);
        panel.add(tfExpenseDetails);
        
        return panel;
    }
    
    /**
     * Tạo panel khách hàng
     */
    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0, 148, 143));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Khách hàng",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.PLAIN, 12)
        ));
        
        JLabel lblCustomerId = new JLabel("Mã khách hàng");
        lblCustomerId.setBounds(15, 25, 100, 25);
        tfCustomerId = new JTextField();
        tfCustomerId.setBounds(120, 25, 215, 25);
        
        JLabel lblCustomerName = new JLabel("Tên khách hàng");
        lblCustomerName.setBounds(15, 60, 100, 25);
        tfCustomerName = new JTextField();
        tfCustomerName.setBounds(120, 60, 215, 25);
        
        panel.add(lblCustomerId);
        panel.add(tfCustomerId);
        panel.add(lblCustomerName);
        panel.add(tfCustomerName);
        
        return panel;
    }
    
    /**
     * Tạo panel thời gian
     */
    private JPanel createTimePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0, 148, 143));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Thời gian",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.PLAIN, 12)
        ));
        
        JLabel lblDate = new JLabel("Ngày");
        lblDate.setBounds(15, 25, 50, 25);
        tfDate = new JTextField();
        tfDate.setBounds(70, 25, 265, 25);
        
        JLabel lblTime = new JLabel("Giờ");
        lblTime.setBounds(15, 60, 50, 25);
        tfTime = new JTextField();
        tfTime.setBounds(70, 60, 265, 25);
        
        panel.add(lblDate);
        panel.add(tfDate);
        panel.add(lblTime);
        panel.add(tfTime);
        
        return panel;
    }
    
    /**
     * Tạo panel bảng dữ liệu
     */
    private void createTablePanel() {
        tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBackground(new Color(0, 148, 143));
        
        String[] columnNames = {"STT", "Mã SP", "Tên SP", "Mã KH", "Số Lượng", "Thành tiền", "Thời gian", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableOrders = new JTable(tableModel);
        tableOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableOrders.setGridColor(Color.LIGHT_GRAY);
        tableOrders.setRowHeight(25);
        
        tableOrders.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableOrders.getColumnModel().getColumn(1).setPreferredWidth(100);
        tableOrders.getColumnModel().getColumn(2).setPreferredWidth(150);
        tableOrders.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableOrders.getColumnModel().getColumn(4).setPreferredWidth(100);
        tableOrders.getColumnModel().getColumn(5).setPreferredWidth(150);
        tableOrders.getColumnModel().getColumn(6).setPreferredWidth(150);
        tableOrders.getColumnModel().getColumn(7).setPreferredWidth(150);
        
        scrollPane = new JScrollPane(tableOrders);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Tạo panel chứa các nút chức năng phía dưới
     */
    private void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBackground(new Color(0, 148, 143));
        buttonPanel.setPreferredSize(new Dimension(1000, 60));
        
        btnDelete = new JButton("Xóa");
        btnDelete.setBounds(370, 10, 110, 40);
        btnDelete.addActionListener(e -> deleteSelectedOrder());
        
        btnConfirm = new JButton("Xác nhận và Xuất hóa đơn");
        btnConfirm.setBounds(620, 10, 210, 40);
        btnConfirm.addActionListener(e -> confirmAndPrintInvoice());
        
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnConfirm);
    }
    
    /**
     * Xử lý sự kiện nhấn nút Thêm
     */
    private void addOrder() {
        // Kiểm tra thông tin nhập
        if (validateInputs()) {
            // Lấy thông tin từ form
            String productId = tfProductId.getText().trim();
            String productName = cbProductName.getSelectedItem().toString();
            String customerId = tfCustomerId.getText().trim();
            String customerName = tfCustomerName.getText().trim();
            String quantity = tfQuantity.getText().trim();
            String totalAmount = tfTotalAmount.getText().trim();
            String date = tfDate.getText().trim();
            String time = tfTime.getText().trim();
            
            // Thêm dữ liệu vào bảng
            Object[] rowData = {
                tableModel.getRowCount() + 1,
                productId,
                productName,
                customerId,
                quantity,
                totalAmount,
                date + " " + time,
                "Chờ xác nhận"
            };
            tableModel.addRow(rowData);
            
            // Xóa form sau khi thêm
            clearForm();
            
            // Hiển thị thông báo
            JOptionPane.showMessageDialog(this, "Đã thêm sản phẩm vào danh sách xuất hàng!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Xử lý sự kiện nhấn nút Xóa
     */
    private void deleteSelectedOrder() {
        int selectedRow = tableOrders.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            // Cập nhật lại STT
            updateOrderNumbers();
            JOptionPane.showMessageDialog(this, "Đã xóa dòng đã chọn!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Xử lý sự kiện nhấn nút Xác nhận và Xuất hóa đơn
     */
    private void confirmAndPrintInvoice() {
        if (tableModel.getRowCount() > 0) {
            JOptionPane.showMessageDialog(this, "Đã xác nhận và xuất hóa đơn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            // Xóa toàn bộ dữ liệu trong bảng
            tableModel.setRowCount(0);
        } else {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất hóa đơn!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Xóa thông tin trong form
     */
    private void clearForm() {
        tfProductId.setText("");
        cbProductName.setSelectedIndex(0);
        cbProductName2.setSelectedIndex(0);
        tfQuantity.setText("");
        tfCustomerId.setText("");
        tfCustomerName.setText("");
        tfDate.setText("");
        tfTime.setText("");
        tfTotalAmount.setText("");
        tfExpenseDetails.setText("");
    }
    
    /**
     * Cập nhật lại STT sau khi xóa
     */
    private void updateOrderNumbers() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tableModel.setValueAt(i + 1, i, 0);
        }
    }
    
    /**
     * Kiểm tra tính hợp lệ của dữ liệu nhập
     */
    private boolean validateInputs() {
        // Kiểm tra mã sản phẩm
        if (tfProductId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sản phẩm!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            tfProductId.requestFocus();
            return false;
        }
        
        // Kiểm tra số lượng
        try {
            int quantity = Integer.parseInt(tfQuantity.getText().trim());
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                tfQuantity.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            tfQuantity.requestFocus();
            return false;
        }
        
        // Kiểm tra mã khách hàng
        if (tfCustomerId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã khách hàng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            tfCustomerId.requestFocus();
            return false;
        }
        
        // Kiểm tra ngày
        if (tfDate.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            tfDate.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     * Phương thức main để chạy và kiểm tra giao diện
     */
    public static void main(String[] args) {
        try {
            // Set Look and Feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý xuất hàng");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new QuanLyXuatHangPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}