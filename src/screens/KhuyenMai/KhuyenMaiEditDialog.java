package screens.KhuyenMai;

import DTO.khuyenMaiDTO;
import BUS.KhuyenMaiService;
import DTO.sanPhamDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class KhuyenMaiEditDialog extends JDialog {
    private khuyenMaiDTO km;
    private KhuyenMaiService khuyenMaiService;
    private SimpleDateFormat dateFormat;
    private JTable khuyenMaiTable;
    private boolean isEditMode;
    private khuyenMaiPanel parentPanel;

    private JTextField maKmField;
    private JTextField maSanPhamField;
    private JTextField tenSanPhamField;
    private JTextField tenChuongTrinhField;
    private JTextField giamGiaField;
    private JTextField ngayBatDauField;
    private JTextField ngayKetThucField;
    private JTextField giaCuField;
    private JTextField giaMoiField;
    private JLabel trangThaiLabel;

    public KhuyenMaiEditDialog(Frame parent, khuyenMaiDTO km, KhuyenMaiService khuyenMaiService,
            SimpleDateFormat dateFormat,
            JTable khuyenMaiTable, boolean isEditMode, khuyenMaiPanel parentPanel) {
        super(parent, isEditMode ? "Sửa Thông Tin Khuyến Mãi" : "Thêm Khuyến Mãi", true);
        this.km = km;
        this.khuyenMaiService = khuyenMaiService;
        this.dateFormat = dateFormat;
        this.khuyenMaiTable = khuyenMaiTable;
        this.isEditMode = isEditMode;
        this.parentPanel = parentPanel;

        initComponents();
        if (isEditMode && km != null) {
            populateFields();
        } else {
            String newMaKhuyenMai = khuyenMaiService.generateMaKhuyenMai();
            maKmField.setText(newMaKhuyenMai);
            maKmField.setEditable(false);
            maKmField.setBackground(new Color(220, 220, 220));
        }
    }

    private void initComponents() {
        setSize(400, 500);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(238, 238, 238));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(238, 238, 238));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 12);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 12);

        // Mã khuyến mãi
        JLabel maKmLabel = new JLabel("Mã khuyến mãi:");
        maKmLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(maKmLabel, gbc);

        maKmField = new JTextField(15);
        maKmField.setFont(fieldFont);
        maKmField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        maKmField.setEditable(false);
        maKmField.setBackground(new Color(220, 220, 220));
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(maKmField, gbc);

        // Mã sản phẩm
        JLabel maSanPhamLabel = new JLabel("Mã sản phẩm:");
        maSanPhamLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(maSanPhamLabel, gbc);

        maSanPhamField = new JTextField(15);
        maSanPhamField.setFont(fieldFont);
        maSanPhamField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        if (isEditMode) {
            maSanPhamField.setEditable(false);
            maSanPhamField.setBackground(new Color(220, 220, 220));
        } else {
            maSanPhamField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateTenSanPham();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateTenSanPham();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    updateTenSanPham();
                }
            });
        }
        gbc.gridx = 1;
        formPanel.add(maSanPhamField, gbc);

        // Tên sản phẩm
        JLabel tenSanPhamLabel = new JLabel("Tên sản phẩm:");
        tenSanPhamLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(tenSanPhamLabel, gbc);

        tenSanPhamField = new JTextField(15);
        tenSanPhamField.setFont(fieldFont);
        tenSanPhamField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        if (isEditMode) {
            tenSanPhamField.setEditable(false);
            tenSanPhamField.setBackground(new Color(220, 220, 220));
        }
        gbc.gridx = 1;
        formPanel.add(tenSanPhamField, gbc);

        // Tên chương trình
        JLabel tenChuongTrinhLabel = new JLabel("Tên chương trình:");
        tenChuongTrinhLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(tenChuongTrinhLabel, gbc);

        tenChuongTrinhField = new JTextField(15);
        tenChuongTrinhField.setFont(fieldFont);
        tenChuongTrinhField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        gbc.gridx = 1;
        formPanel.add(tenChuongTrinhField, gbc);

        // Giảm giá
        JLabel giamGiaLabel = new JLabel("Giảm giá (%):");
        giamGiaLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(giamGiaLabel, gbc);

        giamGiaField = new JTextField(15);
        giamGiaField.setFont(fieldFont);
        giamGiaField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        gbc.gridx = 1;
        formPanel.add(giamGiaField, gbc);

        // Ngày bắt đầu
        JLabel ngayBatDauLabel = new JLabel("Ngày bắt đầu:");
        ngayBatDauLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(ngayBatDauLabel, gbc);

        JPanel ngayBatDauPanel = new JPanel(new BorderLayout());
        ngayBatDauField = new JTextField(15);
        ngayBatDauField.setFont(fieldFont);
        ngayBatDauField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        JButton calendarButton1 = new JButton("📅");
        calendarButton1.setPreferredSize(new Dimension(30, 20));
        calendarButton1.addActionListener(e -> showDatePicker(ngayBatDauField));
        ngayBatDauPanel.add(ngayBatDauField, BorderLayout.CENTER);
        ngayBatDauPanel.add(calendarButton1, BorderLayout.EAST);
        gbc.gridx = 1;
        formPanel.add(ngayBatDauPanel, gbc);

        // Ngày kết thúc
        JLabel ngayKetThucLabel = new JLabel("Ngày kết thúc:");
        ngayKetThucLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(ngayKetThucLabel, gbc);

        JPanel ngayKetThucPanel = new JPanel(new BorderLayout());
        ngayKetThucField = new JTextField(15);
        ngayKetThucField.setFont(fieldFont);
        ngayKetThucField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        JButton calendarButtonKetThuc = new JButton("📅");
        calendarButtonKetThuc.setPreferredSize(new Dimension(30, 20));
        calendarButtonKetThuc.addActionListener(e -> showDatePicker(ngayKetThucField));
        ngayKetThucPanel.add(ngayKetThucField, BorderLayout.CENTER);
        ngayKetThucPanel.add(calendarButtonKetThuc, BorderLayout.EAST);
        gbc.gridx = 1;
        formPanel.add(ngayKetThucPanel, gbc);

        // Giá cũ
        JLabel giaCuLabel = new JLabel("Giá cũ:");
        giaCuLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(giaCuLabel, gbc);

        giaCuField = new JTextField(15);
        giaCuField.setFont(fieldFont);
        giaCuField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        gbc.gridx = 1;
        formPanel.add(giaCuField, gbc);

        // Giá mới
        JLabel giaMoiLabel = new JLabel("Giá mới:");
        giaMoiLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(giaMoiLabel, gbc);

        giaMoiField = new JTextField(15);
        giaMoiField.setFont(fieldFont);
        giaMoiField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        giaMoiField.setEditable(false);
        gbc.gridx = 1;
        formPanel.add(giaMoiField, gbc);

        // Trạng thái
        JLabel trangThaiTitleLabel = new JLabel("Trạng thái:");
        trangThaiTitleLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(trangThaiTitleLabel, gbc);

        trangThaiLabel = new JLabel("Chưa xác định");
        trangThaiLabel.setFont(fieldFont);
        gbc.gridx = 1;
        formPanel.add(trangThaiLabel, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(238, 238, 238));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));

        JButton actionButton = new JButton(isEditMode ? "Cập nhật" : "Thêm");
        actionButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        actionButton.setBackground(new Color(0, 120, 215));
        actionButton.setForeground(Color.WHITE);
        actionButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JButton cancelButton = new JButton("Hủy");
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cancelButton.setBackground(new Color(200, 200, 200));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        buttonPanel.add(actionButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        DocumentListener priceUpdater = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateGiaMoi();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateGiaMoi();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateGiaMoi();
            }
        };
        giamGiaField.getDocument().addDocumentListener(priceUpdater);
        giaCuField.getDocument().addDocumentListener(priceUpdater);

        DocumentListener statusUpdater = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateStatus();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateStatus();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateStatus();
            }
        };
        ngayBatDauField.getDocument().addDocumentListener(statusUpdater);
        ngayKetThucField.getDocument().addDocumentListener(statusUpdater);

        actionButton.addActionListener(e -> {
            if (isEditMode) {
                handleUpdate();
            } else {
                handleAdd();
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }

    private void updateTenSanPham() {
        String maSanPham = maSanPhamField.getText().trim();
        if (!maSanPham.isEmpty()) {
            try {
                sanPhamDTO sp = khuyenMaiService.getProductByMa(maSanPham);
                if (sp != null) {
                    tenSanPhamField.setText(sp.getTenSanPham());
                    giaCuField.setText(String.valueOf(sp.getGiaBan()));
                    updateGiaMoi();
                } else {
                    tenSanPhamField.setText("");
                    giaCuField.setText("");
                    giaMoiField.setText("");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            tenSanPhamField.setText("");
            giaCuField.setText("");
            giaMoiField.setText("");
        }
    }

    private void updateGiaMoi() {
        try {
            String giaCuText = giaCuField.getText().trim();
            String giamGiaText = giamGiaField.getText().trim();

            if (giaCuText.isEmpty() || giamGiaText.isEmpty()) {
                giaMoiField.setText("");
                return;
            }

            double giaCu = Double.parseDouble(giaCuText);
            double giamGia = Double.parseDouble(giamGiaText);

            if (giaCu < 0 || giamGia < 0 || giamGia > 100) {
                giaMoiField.setText("");
                return;
            }

            double giaMoi = giaCu * (1 - giamGia / 100);
            giaMoiField.setText(String.format("%.2f", giaMoi));
        } catch (NumberFormatException e) {
            giaMoiField.setText("");
        }
    }

    private String determineStatus(Date ngayBatDau, Date ngayKetThuc) {
        Date currentDate = new Date();
        if (currentDate.before(ngayBatDau)) {
            return "Chưa bắt đầu";
        } else if (!currentDate.before(ngayBatDau) && !currentDate.after(ngayKetThuc)) {
            return "Hoạt động";
        } else {
            return "Hết hạn";
        }
    }

    private void updateStatus() {
        try {
            Date ngayBatDau = dateFormat.parse(ngayBatDauField.getText());
            Date ngayKetThuc = dateFormat.parse(ngayKetThucField.getText());
            trangThaiLabel.setText(determineStatus(ngayBatDau, ngayKetThuc));
        } catch (ParseException e) {
            trangThaiLabel.setText("Chưa xác định");
        }
    }

    private void showDatePicker(JTextField dateField) {
        JDialog dateDialog = new JDialog(this, "Chọn ngày", true);
        dateDialog.setLayout(new BorderLayout());
        dateDialog.setSize(300, 150);
        dateDialog.setLocationRelativeTo(this);

        Calendar cal = Calendar.getInstance();
        try {
            Date currentDate = dateFormat.parse(dateField.getText());
            cal.setTime(currentDate);
        } catch (ParseException e) {
            // Nếu không parse được, sử dụng ngày hiện tại
        }

        SpinnerNumberModel dayModel = new SpinnerNumberModel(cal.get(Calendar.DAY_OF_MONTH), 1, 31, 1);
        SpinnerNumberModel monthModel = new SpinnerNumberModel(cal.get(Calendar.MONTH) + 1, 1, 12, 1);
        SpinnerNumberModel yearModel = new SpinnerNumberModel(cal.get(Calendar.YEAR), 2000, 2100, 1);

        JSpinner daySpinner = new JSpinner(dayModel);
        JSpinner monthSpinner = new JSpinner(monthModel);
        JSpinner yearSpinner = new JSpinner(yearModel);

        JPanel datePanel = new JPanel(new FlowLayout());
        datePanel.add(new JLabel("Ngày:"));
        datePanel.add(daySpinner);
        datePanel.add(new JLabel("Tháng:"));
        datePanel.add(monthSpinner);
        datePanel.add(new JLabel("Năm:"));
        datePanel.add(yearSpinner);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            int day = (int) daySpinner.getValue();
            int month = (int) monthSpinner.getValue();
            int year = (int) yearSpinner.getValue();

            try {
                cal.set(year, month - 1, day);
                Date selectedDate = cal.getTime();
                dateField.setText(dateFormat.format(selectedDate));
                dateDialog.dispose();
                updateStatus();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ngày không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = new JButton("Hủy");
        cancelButton.addActionListener(e -> dateDialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        dateDialog.add(datePanel, BorderLayout.CENTER);
        dateDialog.add(buttonPanel, BorderLayout.SOUTH);
        dateDialog.setVisible(true);
    }

    private void populateFields() {
        maKmField.setText(km.getMaKhuyenMai());
        maSanPhamField.setText(km.getMaSanPham());
        tenSanPhamField.setText(km.getTenSanPham());
        tenChuongTrinhField.setText(km.getTenChuongTrinh());
        giamGiaField.setText(String.valueOf(km.getGiamGia()));
        ngayBatDauField.setText(dateFormat.format(km.getNgayBatDau()));
        ngayKetThucField.setText(dateFormat.format(km.getNgayKetThuc()));
        giaCuField.setText(String.valueOf(km.getGiaCu()));
        giaMoiField.setText(String.valueOf(km.getGiaMoi()));
        trangThaiLabel.setText(km.getTrangThai());
    }

    private String validateFields() {
        if (maSanPhamField.getText().trim().isEmpty()) {
            return "Mã sản phẩm không được để trống.";
        }
        if (tenSanPhamField.getText().trim().isEmpty()) {
            return "Tên sản phẩm không được để trống.";
        }
        if (tenChuongTrinhField.getText().trim().isEmpty()) {
            return "Tên chương trình không được để trống.";
        }
        if (giamGiaField.getText().trim().isEmpty()) {
            return "Giảm giá (%) không được để trống.";
        }
        if (ngayBatDauField.getText().trim().isEmpty()) {
            return "Ngày bắt đầu không được để trống.";
        }
        if (ngayKetThucField.getText().trim().isEmpty()) {
            return "Ngày kết thúc không được để trống.";
        }
        if (giaCuField.getText().trim().isEmpty()) {
            return "Giá cũ không được để trống.";
        }
        if (giaMoiField.getText().trim().isEmpty()) {
            return "Giá mới không được để trống.";
        }
        return null; // Return null if no fields are empty
    }

    private void handleAdd() {
        // Validate fields for empty values
        String errorMessage = validateFields();
        if (errorMessage != null) {
            JOptionPane.showMessageDialog(this, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            khuyenMaiDTO newKm = new khuyenMaiDTO();
            newKm.setMaKhuyenMai(maKmField.getText().trim());
            newKm.setMaSanPham(maSanPhamField.getText().trim());
            newKm.setTenSanPham(tenSanPhamField.getText().trim());
            newKm.setTenChuongTrinh(tenChuongTrinhField.getText().trim());
            newKm.setGiamGia(Double.parseDouble(giamGiaField.getText().trim()));
            newKm.setNgayBatDau(dateFormat.parse(ngayBatDauField.getText().trim()));
            newKm.setNgayKetThuc(dateFormat.parse(ngayKetThucField.getText().trim()));
            newKm.setGiaCu(Double.parseDouble(giaCuField.getText().trim()));
            newKm.setGiaMoi(Double.parseDouble(giaMoiField.getText().trim()));
            newKm.setTrangThai(trangThaiLabel.getText());

            boolean success = khuyenMaiService.addKhuyenMai(newKm);
            if (success) {
                JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!", "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                parentPanel.loadKhuyenMaiData();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Thêm khuyến mãi thất bại! Kiểm tra dữ liệu hoặc kết nối cơ sở dữ liệu.", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdate() {
        // Validate fields for empty values
        String errorMessage = validateFields();
        if (errorMessage != null) {
            JOptionPane.showMessageDialog(this, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            khuyenMaiDTO updatedKm = new khuyenMaiDTO();
            updatedKm.setMaKhuyenMai(maKmField.getText().trim());
            updatedKm.setMaSanPham(maSanPhamField.getText().trim());
            updatedKm.setTenSanPham(tenSanPhamField.getText().trim());
            updatedKm.setTenChuongTrinh(tenChuongTrinhField.getText().trim());
            updatedKm.setGiamGia(Double.parseDouble(giamGiaField.getText().trim()));
            updatedKm.setNgayBatDau(dateFormat.parse(ngayBatDauField.getText().trim()));
            updatedKm.setNgayKetThuc(dateFormat.parse(ngayKetThucField.getText().trim()));
            updatedKm.setGiaCu(Double.parseDouble(giaCuField.getText().trim()));
            updatedKm.setGiaMoi(Double.parseDouble(giaMoiField.getText().trim()));
            updatedKm.setTrangThai(trangThaiLabel.getText());

            boolean success = khuyenMaiService.updateKhuyenMai(updatedKm);
            if (success) {
                JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thành công!", "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                parentPanel.loadKhuyenMaiData();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Cập nhật khuyến mãi thất bại! Kiểm tra dữ liệu hoặc kết nối cơ sở dữ liệu.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable() {
        if (!(khuyenMaiTable.getModel() instanceof DefaultTableModel)) {
            JOptionPane.showMessageDialog(this, "Bảng khuyến mãi không sử dụng DefaultTableModel!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) khuyenMaiTable.getModel();
        model.setRowCount(0);
        java.util.List<khuyenMaiDTO> khuyenMaiList = khuyenMaiService.getAllKhuyenMai();
        for (khuyenMaiDTO km : khuyenMaiList) {
            model.addRow(new Object[] {
                    km.getMaKhuyenMai(),
                    km.getMaSanPham(),
                    km.getTenSanPham(),
                    km.getTenChuongTrinh(),
                    km.getGiamGia(),
                    dateFormat.format(km.getNgayBatDau()),
                    dateFormat.format(km.getNgayKetThuc()),
                    km.getGiaCu(),
                    km.getGiaMoi(),
                    km.getTrangThai()
            });
        }
    }
}