package screens.NhapHang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import DTO.sanPhamDTO; // Assuming you'll use this or a custom DTO for suggestions
import BUS.SanPhamBUS;
import BUS.HoaDonBUS; // You will need this
import BUS.PhieuNhapBUS; // You will need this
import DTO.nhapHangDTO;
import BUS.NhapHangBUS;
import java.text.DecimalFormat;
import DAO.NhaCungCap_SanPhamDAO; // Added import

// Import the main frame to switch back
import screens.TrangChu.trangchu;


public class SmartImportAdvisorPanel extends JPanel {

    private trangchu mainFrame; // To switch back
    private nhaphang previousPanel; // Changed JPanel to nhaphang

    private JTextField txtBudget;
    private JButton btnLoadSuggestions;
    private JTable tblSuggestions;
    private DefaultTableModel tblModelSuggestions;
    private JButton btnApproveImports;
    private JButton btnCancel;
    private JButton btnBack;
    private JLabel lblRemainingBudget;
    private JLabel lblTotalCost; // To show total cost of suggested items

    private SanPhamBUS sanPhamBUS;
    private HoaDonBUS hoaDonBUS; // To be initialized
    private PhieuNhapBUS phieuNhapBUS; // To be initialized
    private NhapHangBUS nhapHangBUS;

    private double currentBudget = 0.0;
    private List<nhapHangDTO> suggestedItemsList; // To hold items for the table and processing
    private DecimalFormat df = new DecimalFormat("#,##0.00");
    private boolean isInternallyUpdatingTable = false; // Flag to prevent listener recursion

    // Define a target stock level (can be made configurable later)
    private static final int TARGET_STOCK_LEVEL = 50;

    public SmartImportAdvisorPanel(trangchu mainFrame, nhaphang previousPanel) {
        this.mainFrame = mainFrame;
        this.previousPanel = previousPanel;
        this.sanPhamBUS = new SanPhamBUS();
        this.hoaDonBUS = new HoaDonBUS(); // Initialize this properly
        this.phieuNhapBUS = new PhieuNhapBUS(); // Initialize this properly
        this.nhapHangBUS = new NhapHangBUS();
        this.suggestedItemsList = new ArrayList<>();

        initComponents();
        setupEventListeners();
    }

    private void initComponents() {
        setPreferredSize(new Dimension(1000, 700));
        setLayout(new BorderLayout());

        // --- Header Panel ---
        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlHeader.setBackground(new Color(12, 150, 156));
        JLabel lblTitle = new JLabel("Trợ Lý Nhập Hàng Thông Minh");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        pnlHeader.add(lblTitle);
        pnlHeader.setPreferredSize(new Dimension(1000, 70));
        add(pnlHeader, BorderLayout.NORTH);

        // --- Main Content Panel ---
        JPanel pnlMainContent = new JPanel();
        pnlMainContent.setLayout(new BorderLayout(10, 10));
        pnlMainContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlMainContent.setBackground(new Color(107, 163, 190));

        // Input Panel (Budget & Load Button)
        JPanel pnlInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlInput.setBackground(new Color(107, 163, 190));
        pnlInput.add(new JLabel("Ngân sách (VNĐ):"));
        txtBudget = new JTextField(15);
        pnlInput.add(txtBudget);
        btnLoadSuggestions = new JButton("Tải Gợi Ý");
        pnlInput.add(btnLoadSuggestions);
        pnlMainContent.add(pnlInput, BorderLayout.NORTH);

        // Suggestions Table
        String[] columnNames = {"STT", "Mã SP", "Tên SP", "Tồn Kho", "Giá Nhập Ước Tính", "SL Đề Xuất", "Tổng Chi Phí SP", "Xóa"};
        tblModelSuggestions = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Allow editing only for "SL Đề Xuất" and "Xóa"
                return column == 5 || column == 7;
            }
             @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 7) return JButton.class; // For the delete button
                return super.getColumnClass(columnIndex);
            }
        };
        tblSuggestions = new JTable(tblModelSuggestions);
        tblSuggestions.setRowHeight(25);
        // Apply custom renderer for the Delete button column
        tblSuggestions.getColumnModel().getColumn(7).setCellRenderer(new DeleteButtonRenderer());
        // Custom renderer/editor for delete button if needed later
        JScrollPane scrollPaneSuggestions = new JScrollPane(tblSuggestions);
        pnlMainContent.add(scrollPaneSuggestions, BorderLayout.CENTER);

        // Summary and Actions Panel
        JPanel pnlActionsAndSummary = new JPanel(new BorderLayout(10,10));
        pnlActionsAndSummary.setBackground(new Color(107, 163, 190));

        JPanel pnlSummary = new JPanel(new GridLayout(2,1,5,5));
        pnlSummary.setBackground(new Color(107, 163, 190));
        lblTotalCost = new JLabel("Tổng chi phí dự kiến: 0 VNĐ");
        lblTotalCost.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblRemainingBudget = new JLabel("Ngân sách còn lại: 0 VNĐ");
        lblRemainingBudget.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pnlSummary.add(lblTotalCost);
        pnlSummary.add(lblRemainingBudget);
        pnlActionsAndSummary.add(pnlSummary, BorderLayout.CENTER);


        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pnlButtons.setBackground(new Color(107, 163, 190));
        btnApproveImports = new JButton("Duyệt Tạo Phiếu Đặt Hàng");
        btnCancel = new JButton("Hủy Bỏ");
        btnBack = new JButton("Quay Lại Nhập Hàng");
        pnlButtons.add(btnApproveImports);
        pnlButtons.add(btnCancel);
        pnlButtons.add(btnBack);
        pnlActionsAndSummary.add(pnlButtons, BorderLayout.SOUTH);

        pnlMainContent.add(pnlActionsAndSummary, BorderLayout.SOUTH);

        add(pnlMainContent, BorderLayout.CENTER);
    }

    private void setupEventListeners() {
        btnLoadSuggestions.addActionListener(e -> loadSuggestionsAction());
        btnApproveImports.addActionListener(e -> approveImportsAction());
        btnCancel.addActionListener(e -> cancelAction());
        btnBack.addActionListener(e -> backAction());

        // Listener for table changes to update total cost and remaining budget
        tblModelSuggestions.addTableModelListener(e -> {
            if (!isInternallyUpdatingTable) { // Check the flag here
                if (e.getType() == javax.swing.event.TableModelEvent.UPDATE ||
                    e.getType() == javax.swing.event.TableModelEvent.INSERT ||
                    e.getType() == javax.swing.event.TableModelEvent.DELETE) {
                    updateSummaryCalculations();
                }
            }
        });
        
        // Action for delete button in table
        tblSuggestions.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int column = tblSuggestions.getColumnModel().getColumnIndexAtX(evt.getX());
                int row = evt.getY() / tblSuggestions.getRowHeight();

                if (row < tblSuggestions.getRowCount() && row >= 0 && column < tblSuggestions.getColumnCount() && column >= 0) {
                    Object value = tblSuggestions.getValueAt(row, column);
                    if (value instanceof JButton) {
                        ((JButton)value).doClick(); // Simulate button click
                        tblModelSuggestions.removeRow(row);
                        if(row < suggestedItemsList.size()){ // Ensure index is valid
                             suggestedItemsList.remove(row);
                        }
                        updateTableSTT();
                    }
                }
            }
        });
    }
    
    private void updateTableSTT() {
        for (int i = 0; i < tblModelSuggestions.getRowCount(); i++) {
            tblModelSuggestions.setValueAt(i + 1, i, 0); // Update STT
        }
    }

    private void loadSuggestionsAction() {
        String budgetStr = txtBudget.getText().trim();
        if (budgetStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngân sách.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            currentBudget = Double.parseDouble(budgetStr);
            if (currentBudget <= 0) {
                JOptionPane.showMessageDialog(this, "Ngân sách phải là số dương.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ngân sách không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        isInternallyUpdatingTable = true; // Prevent listener recursion during table population
        try {
            tblModelSuggestions.setRowCount(0); // Clear previous suggestions
            suggestedItemsList.clear();

            List<sanPhamDTO> allProducts = sanPhamBUS.getAll();
            if (allProducts == null || allProducts.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không thể tải danh sách sản phẩm hoặc danh sách rỗng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Filter products that are below target stock level and sort them by current stock (lowest first)
            List<sanPhamDTO> productsToConsider = new ArrayList<>();
            for (sanPhamDTO sp : allProducts) {
                if (sp.getSoLuongTonKho() < TARGET_STOCK_LEVEL) {
                    productsToConsider.add(sp);
                }
            }
            productsToConsider.sort((sp1, sp2) -> Integer.compare(sp1.getSoLuongTonKho(), sp2.getSoLuongTonKho()));

            double remainingBudget = currentBudget;
            int stt = 1;

            for (sanPhamDTO sp : productsToConsider) {
                if (remainingBudget <= 0) {
                    break; // No more budget
                }

                double estimatedPurchasePrice = sp.getGiaBan(); // Using selling price as per previous logic
                if (estimatedPurchasePrice <= 0) {
                    // Fallback if price is invalid, though ideally, products should have valid prices
                    estimatedPurchasePrice = 50000; // Consider a more robust fallback or error handling
                }

                if (remainingBudget < estimatedPurchasePrice) {
                    continue; // Cannot afford even one unit of this product
                }

                int neededQuantity = TARGET_STOCK_LEVEL - sp.getSoLuongTonKho();
                if (neededQuantity <= 0) { // Should not happen due to earlier filter, but as a safeguard
                    continue;
                }

                int affordableQuantity = (int) (remainingBudget / estimatedPurchasePrice);
                int suggestedQuantity = Math.min(neededQuantity, affordableQuantity);

                if (suggestedQuantity > 0) {
                    double itemTotalCost = suggestedQuantity * estimatedPurchasePrice;
                    
                    JButton btnDeleteRow = new JButton("Xóa");
                    tblModelSuggestions.addRow(new Object[]{
                        stt++,
                        sp.getMaSanPham(),
                        sp.getTenSanPham(),
                        sp.getSoLuongTonKho(),
                        df.format(estimatedPurchasePrice), // Format for display
                        suggestedQuantity,
                        df.format(itemTotalCost),       // Format for display
                        btnDeleteRow
                    });

                    nhapHangDTO suggestedItem = new nhapHangDTO();
                    suggestedItem.setMaSanPham(sp.getMaSanPham());
                    suggestedItem.setTenSanPham(sp.getTenSanPham());
                    suggestedItem.setSoLuong(String.valueOf(suggestedQuantity));
                    suggestedItem.setDonGia(String.valueOf(estimatedPurchasePrice)); // Store actual price
                    suggestedItem.setThanhTien(String.valueOf(itemTotalCost));      // Store actual total
                    // Populate other necessary fields for nhapHangDTO when approving
                    suggestedItemsList.add(suggestedItem);

                    remainingBudget -= itemTotalCost;
                }
            }
        } finally {
            isInternallyUpdatingTable = false; // Reset flag
        }
        
        updateSummaryCalculations(); // This will now also run after the flag is reset
        
        if (tblModelSuggestions.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có sản phẩm nào được đề xuất dựa trên ngân sách và tiêu chí hiện tại (tồn kho < " + TARGET_STOCK_LEVEL + ").", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void updateSummaryCalculations() {
        isInternallyUpdatingTable = true; // Set flag before ANY potential table model modifications
        try {
            double totalCost = 0;
            for (int i = 0; i < tblModelSuggestions.getRowCount(); i++) {
                try {
                    // Assuming ThanhTien is stored as a formatted string, parse it back
                    // Or better, recalculate from quantity and unit price
                    int quantity = 0;
                    Object quantityObj = tblModelSuggestions.getValueAt(i, 5); // SL Đề Xuất
                    if (quantityObj != null && !quantityObj.toString().isEmpty()) {
                        quantity = Integer.parseInt(quantityObj.toString());
                    }

                    double unitPrice = 0;
                    Object unitPriceObj = tblModelSuggestions.getValueAt(i, 4); // Giá Nhập Ước Tính
                     if (unitPriceObj != null && !unitPriceObj.toString().isEmpty()) {
                        unitPrice = df.parse(unitPriceObj.toString()).doubleValue();
                    }

                    double itemCost = quantity * unitPrice;
                    tblModelSuggestions.setValueAt(df.format(itemCost), i, 6); // Update total cost for item
                    totalCost += itemCost;
                } catch (Exception e) {
                    // Handle parsing error if necessary, or ensure data is always valid
                    System.err.println("Error calculating total cost for row " + i + ": " + e.getMessage());
                    // Optionally set cell to "Error" or a default value
                    tblModelSuggestions.setValueAt("Error", i, 6);
                }
            }
            lblTotalCost.setText("Tổng chi phí dự kiến: " + df.format(totalCost) + " VNĐ");
            if (currentBudget > 0) {
                lblRemainingBudget.setText("Ngân sách còn lại: " + df.format(currentBudget - totalCost) + " VNĐ");
            } else {
                 lblRemainingBudget.setText("Ngân sách còn lại: 0 VNĐ");
            }
        } finally {
            isInternallyUpdatingTable = false; // ALWAYS reset flag
        }
    }


    private void approveImportsAction() {
        if (suggestedItemsList.isEmpty() && tblModelSuggestions.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có sản phẩm nào để duyệt.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn thêm các sản phẩm này vào danh sách đặt hàng không?\n" +
            "Tổng chi phí: " + lblTotalCost.getText().replace("Tổng chi phí dự kiến: ", "") + "\n" +
            lblRemainingBudget.getText(),
            "Xác nhận duyệt", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean allAddedSuccessfully = true;
            for (int i = 0; i < tblModelSuggestions.getRowCount(); i++) {
                try {
                    String maSP = tblModelSuggestions.getValueAt(i, 1).toString();
                    String tenSP = tblModelSuggestions.getValueAt(i, 2).toString();
                    // int soLuongTonKho = Integer.parseInt(tblModelSuggestions.getValueAt(i, 3).toString());
                    double donGia = df.parse(tblModelSuggestions.getValueAt(i, 4).toString()).doubleValue();
                    int soLuongDat = Integer.parseInt(tblModelSuggestions.getValueAt(i, 5).toString());
                    // double thanhTienSP = df.parse(tblModelSuggestions.getValueAt(i, 6).toString()).doubleValue();

                    if (soLuongDat <= 0) continue; // Skip if quantity is zero or less

                    nhapHangDTO newItem = new nhapHangDTO();
                    newItem.setMaPN(nhapHangBUS.generateNextMaPN()); // Generate new MaPN
                    
                    // Fetch MaNhaCungCap - This needs robust logic
                    // For now, attempt to get the product's default supplier or first available
                    sanPhamDTO productDetails = sanPhamBUS.getSanPhamByMa(maSP);
                    if (productDetails != null && productDetails.getMaNhaCungCap() != null && !productDetails.getMaNhaCungCap().isEmpty()) {
                        newItem.setMaNhaCungCap(productDetails.getMaNhaCungCap());
                    } else {
                        // Fallback: try to find any supplier for this product via NhaCungCap_SanPhamDAO
                        NhaCungCap_SanPhamDAO nccSpDao = new NhaCungCap_SanPhamDAO();
                        List<DTO.NhaCungCap_SanPhamDTO> suppliersForProduct = nccSpDao.getNhaCungCapBySanPham(maSP);
                        if (!suppliersForProduct.isEmpty()) {
                            newItem.setMaNhaCungCap(suppliersForProduct.get(0).getMaNhaCungCap()); // Take the first one
                        } else {
                             JOptionPane.showMessageDialog(this, "Không tìm thấy nhà cung cấp cho sản phẩm: " + maSP + ". Bỏ qua.", "Lỗi NCC", JOptionPane.WARNING_MESSAGE);
                             allAddedSuccessfully = false;
                             continue; 
                        }
                    }

                    newItem.setMaSanPham(maSP);
                    newItem.setTenSanPham(tenSP);
                    newItem.setSoLuong(String.valueOf(soLuongDat));
                    newItem.setDonGia(String.valueOf(donGia));
                    newItem.setThanhTien(String.valueOf(soLuongDat * donGia));
                    newItem.setTrangThai("Đang xử lý"); // Default status for nhaphang items
                    newItem.setThoiGian(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
                    newItem.setHinhThucThanhToan("Tiền mặt"); // Default or allow selection

                    // Populate other fields like MauSac, KichThuoc if available from sanPhamDTO
                    if(productDetails != null){
                        newItem.setMauSac(productDetails.getMauSac());
                        newItem.setKichThuoc(productDetails.getSize());
                    }


                    if (!nhapHangBUS.themNhapHang(newItem)) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi thêm sản phẩm " + maSP + " vào danh sách đặt hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        allAddedSuccessfully = false;
                        // Optionally break or allow continuing with other items
                    }
                } catch (Exception ex) {
                     JOptionPane.showMessageDialog(this, "Lỗi xử lý dữ liệu cho sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                     allAddedSuccessfully = false;
                }
            }

            if (allAddedSuccessfully && tblModelSuggestions.getRowCount() > 0) {
                JOptionPane.showMessageDialog(this, "Các sản phẩm đã được thêm vào danh sách chờ nhập hàng.\nChuyển về màn hình Nhập Hàng để xác nhận.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                // Clear current state
                tblModelSuggestions.setRowCount(0);
                suggestedItemsList.clear();
                txtBudget.setText("");
                currentBudget = 0;
                updateSummaryCalculations();
                // Switch back to nhaphang panel and refresh its table
                if (this.previousPanel != null) { // Check if previousPanel is not null
                    this.previousPanel.refreshImportTable();
                }
                backAction(); // Consolidate switching logic
            } else if (tblModelSuggestions.getRowCount() == 0) {
                 JOptionPane.showMessageDialog(this, "Không có sản phẩm nào được duyệt.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                 JOptionPane.showMessageDialog(this, "Một vài sản phẩm không thể thêm. Vui lòng kiểm tra lại.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void cancelAction() {
        tblModelSuggestions.setRowCount(0);
        suggestedItemsList.clear();
        txtBudget.setText("");
        currentBudget = 0;
        updateSummaryCalculations();
        JOptionPane.showMessageDialog(this, "Đã hủy bỏ các đề xuất.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void backAction() {
        if (mainFrame != null && previousPanel != null) {
            this.previousPanel.refreshImportTable(); // Refresh before switching
            mainFrame.switchPanel(previousPanel.getNhapHangPanel()); // Use getNhapHangPanel() for switching
        } else {
             JOptionPane.showMessageDialog(this, "Không thể quay lại màn hình Nhập Hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Custom TableCellRenderer for the Delete Button
    private class DeleteButtonRenderer extends JButton implements TableCellRenderer {
        public DeleteButtonRenderer() {
            setOpaque(true);
            setText("Xóa");
            setForeground(Color.RED);
            // You can customize other button properties here if needed
            // e.g., setBackground, setBorder, etc.
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, 
                                                       int row, int column) {
            // The 'value' parameter in this case would be the JButton object we added to the model.
            // However, we are overriding its appearance completely here.
            // If you need to vary the button based on the 'value', you can cast it and use its properties.
            return this;
        }
    }
} 