package model_test.frontend;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Panel quản lý và thống kê doanh thu sản phẩm
 */
public class QuanLyThongKePanel extends JPanel {
    // Components declaration
    private JPanel pnlHeader, pnlContent, pnlSearch, pnlTable, pnlTotal, pnlTotalDetail;
    private JLabel lblTitle, lblThoiGian, lblTu, lblDen;
    private JLabel lblTongDoanhThu, lblDoanhThuTuThang, lblDenThang;
    private JLabel lblDoanhThu, lblSoSanPham, lblSoKhachHang;
    private JTextField txtDoanhThu, txtSoSanPham, txtSoKhachHang;
    private JTable tblSanPham;
    private JScrollPane scrTable;
    private JRadioButton radBanChayNhat, radDoanhThuCaoNhat;
    private ButtonGroup btnGroupFilter;
    private JComboBox<String> cboTu, cboDen, cboThang, cboTuThang, cboDenThang;
    private JButton btnTimKiem, btnXacNhan, btnXuatExcel;
    private JTextArea txtDanhGia;
    private DefaultTableModel tblModel;
    
    /**
     * Constructor của QuanLyThongKePanel
     */
    public QuanLyThongKePanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(130, 180, 200));
        
        initComponents();
        setupLayouts();
        setupEvents();
    }
    
    /**
     * Khởi tạo các thành phần giao diện
     */
    private void initComponents() {
        // Header panel
        pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(0, 153, 153));
        lblTitle = new JLabel("Thống kê");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        
        // Content panel
        pnlContent = new JPanel();
        pnlContent.setBackground(new Color(130, 180, 200));
        
        // Search panel
        pnlSearch = new JPanel();
        pnlSearch.setLayout(null);
        pnlSearch.setBackground(new Color(130, 180, 200));
        pnlSearch.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY), "Tìm kiếm"));
        
        radBanChayNhat = new JRadioButton("Bán chạy nhất");
        radBanChayNhat.setBackground(new Color(130, 180, 200));
        radDoanhThuCaoNhat = new JRadioButton("Doanh thu cao nhất");
        radDoanhThuCaoNhat.setBackground(new Color(130, 180, 200));
        btnGroupFilter = new ButtonGroup();
        btnGroupFilter.add(radBanChayNhat);
        btnGroupFilter.add(radDoanhThuCaoNhat);
        
        // Time panel
        JPanel pnlTime = new JPanel();
        pnlTime.setLayout(null);
        pnlTime.setBackground(new Color(130, 180, 200));
        pnlTime.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY), "Thời gian"));
        
        lblTu = new JLabel("Từ");
        lblDen = new JLabel("Đến");
        
        cboTu = new JComboBox<>(new String[]{"Item 1"});
        cboDen = new JComboBox<>(new String[]{"Item 1"});
        
        btnTimKiem = new JButton("Tìm kiếm");
        
        // Table panel
        pnlTable = new JPanel();
        pnlTable.setBackground(Color.WHITE);
        
        // Table
        String[] columnNames = {"STT", "Mã SP", "Tên SP", "Số SP bán ra", "Doanh thu"};
        tblModel = new DefaultTableModel(columnNames, 0);
        tblSanPham = new JTable(tblModel);
        tblSanPham.setRowHeight(30);
        tblSanPham.setGridColor(Color.LIGHT_GRAY);
        
        // Set custom cell renderer to align text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblSanPham.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        
        // Set column widths to match the image
        tblSanPham.getColumnModel().getColumn(0).setPreferredWidth(50);  // STT
        tblSanPham.getColumnModel().getColumn(1).setPreferredWidth(150); // Mã SP
        tblSanPham.getColumnModel().getColumn(2).setPreferredWidth(150); // Tên SP
        tblSanPham.getColumnModel().getColumn(3).setPreferredWidth(150); // Số SP bán ra
        tblSanPham.getColumnModel().getColumn(4).setPreferredWidth(150); // Doanh thu
        
        scrTable = new JScrollPane(tblSanPham);
        scrTable.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Total revenue panel
        pnlTotal = new JPanel();
        pnlTotal.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY), "Tổng doanh thu"));
        pnlTotal.setBackground(new Color(130, 180, 200));
        
        lblTongDoanhThu = new JLabel("Tổng doanh thu tháng");
        cboThang = new JComboBox<>(new String[]{"Item 1", "Item 2", "Item 3"});
        
        lblDoanhThuTuThang = new JLabel("Doanh thu từ tháng");
        cboTuThang = new JComboBox<>(new String[]{"Item 1", "Item 2", "Item 3"});
        
        lblDenThang = new JLabel("đến tháng");
        cboDenThang = new JComboBox<>(new String[]{"Item 1", "Item 2", "Item 3"});
        
        btnXacNhan = new JButton("Xác nhận");
        
        // Total detail panel
        pnlTotalDetail = new JPanel();
        pnlTotalDetail.setBackground(new Color(130, 180, 200));
        
        lblDoanhThu = new JLabel("Doanh thu");
        lblSoSanPham = new JLabel("Số sản phẩm bán ra");
        lblSoKhachHang = new JLabel("Số khách hàng");
        
        txtDoanhThu = new JTextField();
        txtDoanhThu.setEditable(false);
        txtDoanhThu.setBackground(Color.WHITE);
        
        txtSoSanPham = new JTextField();
        txtSoSanPham.setEditable(false);
        txtSoSanPham.setBackground(Color.WHITE);
        
        txtSoKhachHang = new JTextField();
        txtSoKhachHang.setEditable(false);
        txtSoKhachHang.setBackground(Color.WHITE);
        
        // Đánh giá text area with border
        txtDanhGia = new JTextArea();
        txtDanhGia.setEditable(true);
        txtDanhGia.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Export button
        btnXuatExcel = new JButton("Xuất file excel");
        btnXuatExcel.setBackground(Color.WHITE);
        btnXuatExcel.setPreferredSize(new Dimension(400, 50));
    }
    
    /**
     * Thiết lập layouts và sắp xếp các thành phần
     */
    private void setupLayouts() {
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(130, 180, 200));

        // Title
        lblTitle = new JLabel("Thống kê");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(0, 153, 153));
        titlePanel.setPreferredSize(new Dimension(1000, 50));
        titlePanel.add(lblTitle);
        this.add(titlePanel, BorderLayout.NORTH);

        // Content Panel
        pnlContent = new JPanel();
        pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
        pnlContent.setBackground(new Color(130, 180, 200));

        // Search panel
        pnlSearch = new JPanel();
        pnlSearch.setLayout(null);
        pnlSearch.setBackground(new Color(130, 180, 200));
        pnlSearch.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY), "Tìm kiếm"));
        pnlSearch.setPreferredSize(new Dimension(980, 150));
        pnlSearch.setMaximumSize(new Dimension(980, 150));

        // Radio buttons
        radBanChayNhat.setBounds(30, 40, 150, 30);
        radDoanhThuCaoNhat.setBounds(200, 40, 200, 30);
        
        // Time panel
        JPanel timePanel = new JPanel();
        timePanel.setLayout(null);
        timePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY), "Thời gian"));
        timePanel.setBackground(new Color(130, 180, 200));
        timePanel.setBounds(500, 30, 380, 80);
        
        lblTu.setBounds(20, 35, 30, 25);
        cboTu.setBounds(50, 35, 120, 25);
        lblDen.setBounds(190, 35, 40, 25);
        cboDen.setBounds(230, 35, 120, 25);
        
        timePanel.add(lblTu);
        timePanel.add(cboTu);
        timePanel.add(lblDen);
        timePanel.add(cboDen);
        
        btnTimKiem.setBounds(900, 40, 80, 30);
        btnTimKiem.setBackground(Color.WHITE);
        
        pnlSearch.add(radBanChayNhat);
        pnlSearch.add(radDoanhThuCaoNhat);
        pnlSearch.add(timePanel);
        pnlSearch.add(btnTimKiem);

        // Add search panel to content with padding
        JPanel searchWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        searchWrapper.setBackground(new Color(130, 180, 200));
        searchWrapper.add(pnlSearch);
        pnlContent.add(searchWrapper);

        // Table panel
        pnlTable = new JPanel(new BorderLayout());
        pnlTable.setPreferredSize(new Dimension(980, 200));
        pnlTable.setMaximumSize(new Dimension(980, 200));
        pnlTable.add(new JScrollPane(tblSanPham), BorderLayout.CENTER);

        JPanel tableWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        tableWrapper.setBackground(new Color(130, 180, 200));
        tableWrapper.add(pnlTable);
        pnlContent.add(tableWrapper);

        // Total revenue panel
        pnlTotal = new JPanel();
        pnlTotal.setLayout(null);
        pnlTotal.setBackground(new Color(130, 180, 200));
        pnlTotal.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY),
            "Tổng doanh thu"));
        pnlTotal.setPreferredSize(new Dimension(980, 250));
        pnlTotal.setMaximumSize(new Dimension(980, 250));

        // Add components to total panel
        lblDoanhThu = new JLabel("Doanh thu");
        lblDoanhThu.setBounds(20, 70, 100, 25);
        txtDoanhThu = new JTextField();
        txtDoanhThu.setBounds(130, 70, 300, 25);

        lblSoSanPham = new JLabel("Số sản phẩm bán ra");
        lblSoSanPham.setBounds(20, 110, 120, 25);
        txtSoSanPham = new JTextField();
        txtSoSanPham.setBounds(130, 110, 300, 25);

        lblSoKhachHang = new JLabel("Số khách hàng");
        lblSoKhachHang.setBounds(20, 150, 100, 25);
        txtSoKhachHang = new JTextField();
        txtSoKhachHang.setBounds(130, 150, 300, 25);

        // Đánh giá panel
        JPanel pnlDanhGia = new JPanel();
        pnlDanhGia.setBorder(BorderFactory.createTitledBorder("Đánh giá"));
        pnlDanhGia.setBounds(700, 50, 250, 150);
        pnlDanhGia.setLayout(new BorderLayout());
        pnlDanhGia.add(new JScrollPane(txtDanhGia), BorderLayout.CENTER);

        pnlTotal.add(lblDoanhThu);
        pnlTotal.add(txtDoanhThu);
        pnlTotal.add(lblSoSanPham);
        pnlTotal.add(txtSoSanPham);
        pnlTotal.add(lblSoKhachHang);
        pnlTotal.add(txtSoKhachHang);
        pnlTotal.add(pnlDanhGia);

        JPanel totalWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        totalWrapper.setBackground(new Color(130, 180, 200));
        totalWrapper.add(pnlTotal);
        pnlContent.add(totalWrapper);

        // Export button
        JPanel pnlExport = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlExport.setBackground(new Color(130, 180, 200));
        btnXuatExcel.setPreferredSize(new Dimension(400, 40));
        btnXuatExcel.setBackground(Color.WHITE);
        pnlExport.add(btnXuatExcel);
        pnlContent.add(pnlExport);

        // Add content panel to scroll pane
        JScrollPane scrollPane = new JScrollPane(pnlContent);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Thiết lập xử lý sự kiện cho các thành phần
     */
    private void setupEvents() {
        // Tìm kiếm button
        btnTimKiem.addActionListener(e -> timKiemThongKe());
        
        // Xác nhận button
        btnXacNhan.addActionListener(e -> tinhTongDoanhThu());
        
        // Xuất excel button
        btnXuatExcel.addActionListener(e -> xuatFileExcel());
    }
    
    /**
     * Xử lý sự kiện tìm kiếm thống kê
     */
    private void timKiemThongKe() {
        // Clear table
        tblModel.setRowCount(0);
        
        // Kiểm tra lựa chọn
        boolean isBestSeller = radBanChayNhat.isSelected();
        String tuThang = cboTu.getSelectedItem().toString();
        String denThang = cboDen.getSelectedItem().toString();
        
        // Mock data - thêm dữ liệu mẫu vào bảng
        String[][] data = {
            {"1", "SP001", "Sản phẩm A", "120", "12,000,000 đ"},
            {"2", "SP002", "Sản phẩm B", "85", "8,500,000 đ"},
            {"3", "SP003", "Sản phẩm C", "60", "9,000,000 đ"}
        };
        
        for (String[] row : data) {
            tblModel.addRow(row);
        }
        
        // Hiển thị thông báo
        JOptionPane.showMessageDialog(this, "Đã tìm kiếm thống kê từ " + tuThang + " đến " + denThang);
    }
    
    /**
     * Xử lý sự kiện tính tổng doanh thu
     */
    private void tinhTongDoanhThu() {
        // Lấy thông tin
        String thang = cboThang.getSelectedItem().toString();
        String tuThang = cboTuThang.getSelectedItem().toString();
        String denThang = cboDenThang.getSelectedItem().toString();
        
        // Cập nhật hiển thị
        txtDoanhThu.setText("29,500,000 đ");
        txtSoSanPham.setText("265");
        txtSoKhachHang.setText("142");
        
        // Hiển thị thông báo
        JOptionPane.showMessageDialog(this, "Đã tính tổng doanh thu từ " + tuThang + " đến " + denThang);
    }
    
    /**
     * Xử lý sự kiện xuất file Excel
     */
    private void xuatFileExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file Excel");
        
        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Xử lý xuất file Excel
            JOptionPane.showMessageDialog(this, "Đã xuất file Excel thành công!");
        }
    }
    
    /**
     * Phương thức trả về panel chính để tích hợp vào ứng dụng
     * @return JPanel chính
     */
    public JPanel getMainPanel() {
        return this;
    }
    
    /**
     * Phương thức main để kiểm tra giao diện
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản Lý Thống Kê");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);
            frame.setLocationRelativeTo(null);
            
            QuanLyThongKePanel panel = new QuanLyThongKePanel();
            frame.getContentPane().add(panel);
            
            frame.setVisible(true);
        });
    }
}