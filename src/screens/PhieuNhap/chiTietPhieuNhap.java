package screens.PhieuNhap;

import BUS.PhieuNhapBUS;
import DTO.PhieuNhapDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class chiTietPhieuNhap extends JFrame {
    private PhieuNhapBUS phieuNhapBUS;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaSanPham, txtSoLuong, txtMaNhaCungCap, txtDonGia, txtThanhTien, txtTrangThai, txtHinhThucThanhToan;
    private JDateChooser dateChooser;
    private JButton btnAdd, btnUpdate, btnDelete, btnSearch, btnClear;
    private JComboBox<String> cboSearchType;
    private JTextField txtSearch;

    public chiTietPhieuNhap() {
        phieuNhapBUS = new PhieuNhapBUS();
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Quản lý Phiếu Nhập");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create input panel
        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin Phiếu Nhập"));

        inputPanel.add(new JLabel("Mã Sản Phẩm:"));
        txtMaSanPham = new JTextField();
        inputPanel.add(txtMaSanPham);

        inputPanel.add(new JLabel("Số Lượng:"));
        txtSoLuong = new JTextField();
        inputPanel.add(txtSoLuong);

        inputPanel.add(new JLabel("Mã Nhà Cung Cấp:"));
        txtMaNhaCungCap = new JTextField();
        inputPanel.add(txtMaNhaCungCap);

        inputPanel.add(new JLabel("Đơn Giá:"));
        txtDonGia = new JTextField();
        inputPanel.add(txtDonGia);

        inputPanel.add(new JLabel("Thành Tiền:"));
        txtThanhTien = new JTextField();
        txtThanhTien.setEditable(false);
        inputPanel.add(txtThanhTien);

        inputPanel.add(new JLabel("Trạng Thái:"));
        txtTrangThai = new JTextField();
        inputPanel.add(txtTrangThai);

        inputPanel.add(new JLabel("Hình Thức Thanh Toán:"));
        txtHinhThucThanhToan = new JTextField();
        inputPanel.add(txtHinhThucThanhToan);

        inputPanel.add(new JLabel("Thời Gian:"));
        dateChooser = new JDateChooser();
        dateChooser.setDate(new Date());
        inputPanel.add(dateChooser);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Cập Nhật");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Xóa Form");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        // Create search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        cboSearchType = new JComboBox<>(new String[]{"Mã Sản Phẩm", "Mã Nhà Cung Cấp", "Trạng Thái"});
        txtSearch = new JTextField(20);
        btnSearch = new JButton("Tìm Kiếm");

        searchPanel.add(new JLabel("Tìm theo:"));
        searchPanel.add(cboSearchType);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        // Create table
        String[] columns = {"Mã Phiếu Nhập", "Mã Sản Phẩm", "Mã Nhà Cung Cấp", "Số Lượng", 
                          "Đơn Giá", "Thành Tiền", "Thời Gian", "Trạng Thái", "Hình Thức Thanh Toán"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add components to main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(searchPanel, BorderLayout.SOUTH);
        mainPanel.add(scrollPane, BorderLayout.EAST);

        // Add event listeners
        btnAdd.addActionListener(e -> addPhieuNhap());
        btnUpdate.addActionListener(e -> updatePhieuNhap());
        btnDelete.addActionListener(e -> deletePhieuNhap());
        btnClear.addActionListener(e -> clearForm());
        btnSearch.addActionListener(e -> searchPhieuNhap());
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    fillFormFromTable(row);
                }
            }
        });

        // Add listeners for calculating total amount
        txtSoLuong.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { calculateTotal(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { calculateTotal(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { calculateTotal(); }
        });
        
        txtDonGia.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { calculateTotal(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { calculateTotal(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { calculateTotal(); }
        });

        add(mainPanel);
    }

    private void calculateTotal() {
        try {
            int soLuong = Integer.parseInt(txtSoLuong.getText());
            double donGia = Double.parseDouble(txtDonGia.getText());
            txtThanhTien.setText(String.valueOf(soLuong * donGia));
        } catch (Exception ex) {
            txtThanhTien.setText("");
        }
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<PhieuNhapDTO> list = phieuNhapBUS.getAllPhieuNhap();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for (PhieuNhapDTO pn : list) {
            Object[] row = {
                pn.getMaPhieuNhap(),
                pn.getMaSanPham(),
                pn.getMaNhaCungCap(),
                pn.getSoLuong(),
                pn.getDonGia(),
                pn.getThanhTien(),
                sdf.format(pn.getThoiGian()),
                pn.getTrangThai(),
                pn.getHinhThucThanhToan()
            };
            tableModel.addRow(row);
        }
    }

    private void addPhieuNhap() {
        try {
            PhieuNhapDTO pn = new PhieuNhapDTO();
            pn.setMaSanPham(txtMaSanPham.getText());
            pn.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
            pn.setMaNhaCungCap(txtMaNhaCungCap.getText());
            pn.setDonGia(Double.parseDouble(txtDonGia.getText()));
            pn.setThanhTien(Double.parseDouble(txtThanhTien.getText()));
            pn.setThoiGian(dateChooser.getDate());
            pn.setTrangThai(txtTrangThai.getText());
            pn.setHinhThucThanhToan(txtHinhThucThanhToan.getText());

            if (phieuNhapBUS.createPhieuNhap(pn)) {
                JOptionPane.showMessageDialog(this, "Thêm phiếu nhập thành công!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm phiếu nhập thất bại!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng và đơn giá hợp lệ!");
        }
    }

    private void updatePhieuNhap() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập cần cập nhật!");
            return;
        }

        try {
            PhieuNhapDTO pn = new PhieuNhapDTO();
            pn.setMaPhieuNhap(table.getValueAt(row, 0).toString());
            pn.setMaSanPham(txtMaSanPham.getText());
            pn.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
            pn.setMaNhaCungCap(txtMaNhaCungCap.getText());
            pn.setDonGia(Double.parseDouble(txtDonGia.getText()));
            pn.setThanhTien(Double.parseDouble(txtThanhTien.getText()));
            pn.setThoiGian(dateChooser.getDate());
            pn.setTrangThai(txtTrangThai.getText());
            pn.setHinhThucThanhToan(txtHinhThucThanhToan.getText());

            if (phieuNhapBUS.updatePhieuNhap(pn)) {
                JOptionPane.showMessageDialog(this, "Cập nhật phiếu nhập thành công!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật phiếu nhập thất bại!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng và đơn giá hợp lệ!");
        }
    }

    private void deletePhieuNhap() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa phiếu nhập này?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            String maPhieuNhap = table.getValueAt(row, 0).toString();
            if (phieuNhapBUS.deletePhieuNhap(maPhieuNhap)) {
                JOptionPane.showMessageDialog(this, "Xóa phiếu nhập thành công!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa phiếu nhập thất bại!");
            }
        }
    }

    private void clearForm() {
        txtMaSanPham.setText("");
        txtSoLuong.setText("");
        txtMaNhaCungCap.setText("");
        txtDonGia.setText("");
        txtThanhTien.setText("");
        txtTrangThai.setText("");
        txtHinhThucThanhToan.setText("");
        dateChooser.setDate(new Date());
        table.clearSelection();
    }

    private void fillFormFromTable(int row) {
        txtMaSanPham.setText(table.getValueAt(row, 1).toString());
        txtSoLuong.setText(table.getValueAt(row, 3).toString());
        txtMaNhaCungCap.setText(table.getValueAt(row, 2).toString());
        txtDonGia.setText(table.getValueAt(row, 4).toString());
        txtThanhTien.setText(table.getValueAt(row, 5).toString());
        txtTrangThai.setText(table.getValueAt(row, 7).toString());
        txtHinhThucThanhToan.setText(table.getValueAt(row, 8).toString());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            dateChooser.setDate(sdf.parse(table.getValueAt(row, 6).toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchPhieuNhap() {
        String searchText = txtSearch.getText().trim();
        if (searchText.isEmpty()) {
            loadData();
            return;
        }

        tableModel.setRowCount(0);
        List<PhieuNhapDTO> list = phieuNhapBUS.getAllPhieuNhap();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        String searchType = cboSearchType.getSelectedItem().toString();
        for (PhieuNhapDTO pn : list) {
            boolean match = false;
            switch (searchType) {
                case "Mã Sản Phẩm":
                    match = pn.getMaSanPham().contains(searchText);
                    break;
                case "Mã Nhà Cung Cấp":
                    match = pn.getMaNhaCungCap().contains(searchText);
                    break;
                case "Trạng Thái":
                    match = pn.getTrangThai().contains(searchText);
                    break;
            }
            
            if (match) {
                Object[] row = {
                    pn.getMaPhieuNhap(),
                    pn.getMaSanPham(),
                    pn.getMaNhaCungCap(),
                    pn.getSoLuong(),
                    pn.getDonGia(),
                    pn.getThanhTien(),
                    sdf.format(pn.getThoiGian()),
                    pn.getTrangThai(),
                    pn.getHinhThucThanhToan()
                };
                tableModel.addRow(row);
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new chiTietPhieuNhap().setVisible(true);
        });
    }
} 