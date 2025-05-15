package screens.NhapHang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import DTO.sanPhamDTO;
import BUS.SanPhamBUS;
import BUS.HoaDonBUS;
import BUS.PhieuNhapBUS;
import DTO.nhapHangDTO;
import BUS.NhapHangBUS;
import java.text.DecimalFormat;
import DAO.NhaCungCap_SanPhamDAO;
import javax.swing.BorderFactory;

import screens.TrangChu.trangchu;
import screens.TrangChu.RoundedCornerButton;
import screens.TrangChu.RoundedBorder;

public class SmartImportAdvisorPanel extends JPanel {

    private trangchu mainFrame;
    private nhaphang previousPanel;

    private JTextField txtBudget;
    private JButton btnLoadSuggestions;
    private JTable tblSuggestions;
    private DefaultTableModel tblModelSuggestions;
    private JButton btnApproveImports;
    private JButton btnCancel;
    private JButton btnBack;
    private JLabel lblRemainingBudget;
    private JLabel lblTotalCost;

    private SanPhamBUS sanPhamBUS;
    private HoaDonBUS hoaDonBUS;
    private PhieuNhapBUS phieuNhapBUS;
    private NhapHangBUS nhapHangBUS;

    private double currentBudget = 0.0;
    private List<nhapHangDTO> suggestedItemsList;
    private DecimalFormat df = new DecimalFormat("#,##0.00");
    private boolean isInternallyUpdatingTable = false;

    private static final int TARGET_STOCK_LEVEL = 50;

    // Color Palette Definition
    private static final Color MAIN_BACKGROUND_COLOR = new Color(0xE3, 0xF2, 0xFD); // #E3F2FD
    private static final Color SIDEBAR_BACKGROUND_COLOR = new Color(0x19, 0x76, 0xD2); // #1976D2
    private static final Color DEFAULT_BUTTON_BG_COLOR = new Color(0x42, 0xA5, 0xF5); // #42A5F5
    private static final Color SELECTED_HOVER_BUTTON_BG_COLOR = new Color(0x1E, 0x88, 0xE5); // #1E88E5
    private static final Color MAIN_TEXT_COLOR = new Color(0x21, 0x21, 0x21); // #212121
    private static final Color TABLE_BACKGROUND_COLOR = Color.WHITE; // #FFFFFF
    private static final Color PANEL_HEADER_BG_TABLE_HEADER_BG_COLOR = new Color(0xBB, 0xDE, 0xFB); // #BBDEFB
    private static final Color BORDER_SEPARATOR_LINE_COLOR = new Color(0x90, 0xCA, 0xF9); // #90CAF9
    private static final Color LINK_TEXT_QUICK_BUTTON_TEXT_COLOR = new Color(0x02, 0x88, 0xD1); // #0288D1
    private static final Color SECONDARY_BUTTON_BG_QUICK_BUTTON_BG_COLOR = new Color(0x81, 0xD4, 0xFA); // #81D4FA

    public SmartImportAdvisorPanel(trangchu mainFrame, nhaphang previousPanel) {
        this.mainFrame = mainFrame;
        this.previousPanel = previousPanel;
        this.sanPhamBUS = new SanPhamBUS();
        this.hoaDonBUS = new HoaDonBUS();
        this.phieuNhapBUS = new PhieuNhapBUS();
        this.nhapHangBUS = new NhapHangBUS();
        this.suggestedItemsList = new ArrayList<>();

        initComponents();
        setupEventListeners();
    }

    private void initComponents() {
        setPreferredSize(new Dimension(1000, 700));
        setLayout(new BorderLayout());

        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(PANEL_HEADER_BG_TABLE_HEADER_BG_COLOR);
        JLabel lblTitle = new JLabel("TRỢ LÝ NHẬP HÀNG THÔNG MINH");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(MAIN_TEXT_COLOR);
        pnlHeader.setPreferredSize(new Dimension(1000, 70));

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(18)
                .addComponent(lblTitle)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        add(pnlHeader, BorderLayout.NORTH);

        JPanel pnlMainContent = new JPanel();
        pnlMainContent.setLayout(new BorderLayout(10, 10));
        pnlMainContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlMainContent.setBackground(MAIN_BACKGROUND_COLOR);

        JPanel pnlInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlInput.setBackground(MAIN_BACKGROUND_COLOR);
        JLabel lblBudget = new JLabel("Ngân sách (VNĐ):");
        lblBudget.setForeground(MAIN_TEXT_COLOR);
        pnlInput.add(lblBudget);
        txtBudget = new JTextField(15);
        txtBudget.setBackground(TABLE_BACKGROUND_COLOR);
        txtBudget.setForeground(MAIN_TEXT_COLOR);
        txtBudget.setBorder(BorderFactory.createLineBorder(BORDER_SEPARATOR_LINE_COLOR));
        pnlInput.add(txtBudget);
        btnLoadSuggestions = new RoundedCornerButton("Tải Gợi Ý");
        btnLoadSuggestions.setBackground(DEFAULT_BUTTON_BG_COLOR);
        btnLoadSuggestions.setForeground(Color.WHITE);
        btnLoadSuggestions.setBorder(new RoundedBorder(15, BORDER_SEPARATOR_LINE_COLOR, 1, 8));
        btnLoadSuggestions.setPreferredSize(new Dimension(120, 35));
        pnlInput.add(btnLoadSuggestions);
        pnlMainContent.add(pnlInput, BorderLayout.NORTH);

        String[] columnNames = {"STT", "Mã SP", "Tên SP", "Tồn Kho", "Giá Nhập Ước Tính", "SL Đề Xuất", "Tổng Chi Phí SP", "Xóa"};
        tblModelSuggestions = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5 || column == 7;
            }
             @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 7) return JButton.class;
                return super.getColumnClass(columnIndex);
            }
        };
        tblSuggestions = new JTable(tblModelSuggestions);
        tblSuggestions.setRowHeight(25);
        tblSuggestions.setBackground(TABLE_BACKGROUND_COLOR);
        tblSuggestions.setForeground(MAIN_TEXT_COLOR);
        tblSuggestions.getTableHeader().setBackground(PANEL_HEADER_BG_TABLE_HEADER_BG_COLOR);
        tblSuggestions.getTableHeader().setForeground(MAIN_TEXT_COLOR);
        tblSuggestions.setGridColor(BORDER_SEPARATOR_LINE_COLOR);
        tblSuggestions.getColumnModel().getColumn(7).setCellRenderer(new DeleteButtonRenderer());
        JScrollPane scrollPaneSuggestions = new JScrollPane(tblSuggestions);
        pnlMainContent.add(scrollPaneSuggestions, BorderLayout.CENTER);

        JPanel pnlActionsAndSummary = new JPanel(new BorderLayout(10,10));
        pnlActionsAndSummary.setBackground(MAIN_BACKGROUND_COLOR);

        JPanel pnlSummary = new JPanel(new GridLayout(2,1,5,5));
        pnlSummary.setBackground(MAIN_BACKGROUND_COLOR);
        lblTotalCost = new JLabel("Tổng chi phí dự kiến: 0 VNĐ");
        lblTotalCost.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotalCost.setForeground(MAIN_TEXT_COLOR);
        lblRemainingBudget = new JLabel("Ngân sách còn lại: 0 VNĐ");
        lblRemainingBudget.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblRemainingBudget.setForeground(MAIN_TEXT_COLOR);
        pnlSummary.add(lblTotalCost);
        pnlSummary.add(lblRemainingBudget);
        pnlActionsAndSummary.add(pnlSummary, BorderLayout.CENTER);


        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pnlButtons.setBackground(MAIN_BACKGROUND_COLOR);
        btnApproveImports = new RoundedCornerButton("Duyệt Tạo Phiếu Đặt Hàng");
        btnApproveImports.setBackground(DEFAULT_BUTTON_BG_COLOR);
        btnApproveImports.setForeground(Color.WHITE);
        btnApproveImports.setBorder(new RoundedBorder(15, BORDER_SEPARATOR_LINE_COLOR, 1, 8));
        btnApproveImports.setPreferredSize(new Dimension(220, 35));

        btnCancel = new RoundedCornerButton("Hủy Bỏ");
        btnCancel.setBackground(SECONDARY_BUTTON_BG_QUICK_BUTTON_BG_COLOR);
        btnCancel.setForeground(LINK_TEXT_QUICK_BUTTON_TEXT_COLOR);
        btnCancel.setBorder(new RoundedBorder(15, BORDER_SEPARATOR_LINE_COLOR, 1, 8));
        btnCancel.setPreferredSize(new Dimension(100, 35));

        btnBack = new RoundedCornerButton("Quay Lại Nhập Hàng");
        btnBack.setBackground(SECONDARY_BUTTON_BG_QUICK_BUTTON_BG_COLOR);
        btnBack.setForeground(LINK_TEXT_QUICK_BUTTON_TEXT_COLOR);
        btnBack.setBorder(new RoundedBorder(15, BORDER_SEPARATOR_LINE_COLOR, 1, 8));
        btnBack.setPreferredSize(new Dimension(180, 35));

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

        tblModelSuggestions.addTableModelListener(e -> {
            if (!isInternallyUpdatingTable) {
                if (e.getType() == javax.swing.event.TableModelEvent.UPDATE ||
                    e.getType() == javax.swing.event.TableModelEvent.INSERT ||
                    e.getType() == javax.swing.event.TableModelEvent.DELETE) {
                    updateSummaryCalculations();
                }
            }
        });
        
        tblSuggestions.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int column = tblSuggestions.getColumnModel().getColumnIndexAtX(evt.getX());
                int row = evt.getY() / tblSuggestions.getRowHeight();

                if (row < tblSuggestions.getRowCount() && row >= 0 && column < tblSuggestions.getColumnCount() && column >= 0) {
                    Object value = tblSuggestions.getValueAt(row, column);
                    if (value instanceof JButton) {
                        ((JButton)value).doClick();
                        tblModelSuggestions.removeRow(row);
                        if(row < suggestedItemsList.size()){
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
            tblModelSuggestions.setValueAt(i + 1, i, 0);
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

        isInternallyUpdatingTable = true;
        try {
            tblModelSuggestions.setRowCount(0);
            suggestedItemsList.clear();

            List<sanPhamDTO> allProducts = sanPhamBUS.getAll();
            if (allProducts == null || allProducts.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không thể tải danh sách sản phẩm hoặc danh sách rỗng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

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
                    break;
                }

                double estimatedPurchasePrice = sp.getGiaBan();
                if (estimatedPurchasePrice <= 0) {
                    estimatedPurchasePrice = 50000;
                }

                if (remainingBudget < estimatedPurchasePrice) {
                    continue;
                }

                int neededQuantity = TARGET_STOCK_LEVEL - sp.getSoLuongTonKho();
                if (neededQuantity <= 0) {
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
                        df.format(estimatedPurchasePrice),
                        suggestedQuantity,
                        df.format(itemTotalCost),
                        btnDeleteRow
                    });

                    nhapHangDTO suggestedItem = new nhapHangDTO();
                    suggestedItem.setMaSanPham(sp.getMaSanPham());
                    suggestedItem.setTenSanPham(sp.getTenSanPham());
                    suggestedItem.setSoLuong(String.valueOf(suggestedQuantity));
                    suggestedItem.setDonGia(String.valueOf(estimatedPurchasePrice));
                    suggestedItem.setThanhTien(String.valueOf(itemTotalCost));
                    suggestedItemsList.add(suggestedItem);

                    remainingBudget -= itemTotalCost;
                }
            }
        } finally {
            isInternallyUpdatingTable = false;
        }
        
        updateSummaryCalculations();
        
        if (tblModelSuggestions.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có sản phẩm nào được đề xuất dựa trên ngân sách và tiêu chí hiện tại (tồn kho < " + TARGET_STOCK_LEVEL + ").", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void updateSummaryCalculations() {
        isInternallyUpdatingTable = true;
        try {
            double totalCost = 0;
            for (int i = 0; i < tblModelSuggestions.getRowCount(); i++) {
                try {
                    int quantity = 0;
                    Object quantityObj = tblModelSuggestions.getValueAt(i, 5);
                    if (quantityObj != null && !quantityObj.toString().isEmpty()) {
                        quantity = Integer.parseInt(quantityObj.toString());
                    }

                    double unitPrice = 0;
                    Object unitPriceObj = tblModelSuggestions.getValueAt(i, 4);
                     if (unitPriceObj != null && !unitPriceObj.toString().isEmpty()) {
                        unitPrice = df.parse(unitPriceObj.toString()).doubleValue();
                    }

                    double itemCost = quantity * unitPrice;
                    tblModelSuggestions.setValueAt(df.format(itemCost), i, 6);
                    totalCost += itemCost;
                } catch (Exception e) {
                    System.err.println("Error calculating total cost for row " + i + ": " + e.getMessage());
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
            isInternallyUpdatingTable = false;
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
                    double donGia = df.parse(tblModelSuggestions.getValueAt(i, 4).toString()).doubleValue();
                    int soLuongDat = Integer.parseInt(tblModelSuggestions.getValueAt(i, 5).toString());

                    if (soLuongDat <= 0) continue;

                    nhapHangDTO newItem = new nhapHangDTO();
                    newItem.setMaPN(nhapHangBUS.generateNextMaPN());
                    
                    sanPhamDTO productDetails = sanPhamBUS.getSanPhamByMa(maSP);
                    if (productDetails != null && productDetails.getMaNhaCungCap() != null && !productDetails.getMaNhaCungCap().isEmpty()) {
                        newItem.setMaNhaCungCap(productDetails.getMaNhaCungCap());
                    } else {
                        NhaCungCap_SanPhamDAO nccSpDao = new NhaCungCap_SanPhamDAO();
                        List<DTO.NhaCungCap_SanPhamDTO> suppliersForProduct = nccSpDao.getNhaCungCapBySanPham(maSP);
                        if (!suppliersForProduct.isEmpty()) {
                            newItem.setMaNhaCungCap(suppliersForProduct.get(0).getMaNhaCungCap());
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
                    newItem.setTrangThai("Đang xử lý");
                    newItem.setThoiGian(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
                    newItem.setHinhThucThanhToan("Tiền mặt");

                    if(productDetails != null){
                        newItem.setMauSac(productDetails.getMauSac());
                        newItem.setKichThuoc(productDetails.getSize());
                    }


                    if (!nhapHangBUS.themNhapHang(newItem)) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi thêm sản phẩm " + maSP + " vào danh sách đặt hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        allAddedSuccessfully = false;
                    }
                } catch (Exception ex) {
                     JOptionPane.showMessageDialog(this, "Lỗi xử lý dữ liệu cho sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                     allAddedSuccessfully = false;
                }
            }

            if (allAddedSuccessfully && tblModelSuggestions.getRowCount() > 0) {
                JOptionPane.showMessageDialog(this, "Các sản phẩm đã được thêm vào danh sách chờ nhập hàng.\nChuyển về màn hình Nhập Hàng để xác nhận.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                tblModelSuggestions.setRowCount(0);
                suggestedItemsList.clear();
                txtBudget.setText("");
                currentBudget = 0;
                updateSummaryCalculations();
                if (this.previousPanel != null) {
                    this.previousPanel.refreshImportTable();
                }
                backAction();
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
            this.previousPanel.refreshImportTable();
            mainFrame.switchPanel(previousPanel.getNhapHangPanel(), mainFrame.getBtnNhapHang());
        } else {
             JOptionPane.showMessageDialog(this, "Không thể quay lại màn hình Nhập Hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class DeleteButtonRenderer extends JButton implements TableCellRenderer {
        public DeleteButtonRenderer() {
            setOpaque(true);
            setText("Xóa");
            setForeground(Color.RED);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, 
                                                       int row, int column) {
            return this;
        }
    }
} 