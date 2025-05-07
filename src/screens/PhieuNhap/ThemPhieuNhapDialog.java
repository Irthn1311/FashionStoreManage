package screens.PhieuNhap;

import DTO.PhieuNhapDTO;
import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.*;

public class ThemPhieuNhapDialog extends JDialog {
    private JTextField tfMaPN, tfMaNCC, tfMaSP, tfSoLuong, tfDonGia, tfThanhTien, tfThoiGian, tfTrangThai, tfHinhThucThanhToan;
    private boolean saved = false;

    public ThemPhieuNhapDialog(Frame parent) {
        super(parent, "Thêm Phiếu Nhập", true);
        setLayout(new GridLayout(0,2,10,5));

        add(new JLabel("Mã PN:")); tfMaPN = new JTextField(); add(tfMaPN);
        add(new JLabel("Mã SP:")); tfMaSP = new JTextField(); add(tfMaSP);
        add(new JLabel("Mã NCC:")); tfMaNCC = new JTextField(); add(tfMaNCC);
        add(new JLabel("Số lượng:")); tfSoLuong = new JTextField(); add(tfSoLuong);
        add(new JLabel("Đơn giá:")); tfDonGia = new JTextField(); add(tfDonGia);
        add(new JLabel("Thành tiền:")); tfThanhTien = new JTextField(); tfThanhTien.setEditable(false); add(tfThanhTien);
        add(new JLabel("Thời gian:")); tfThoiGian = new JTextField(); add(tfThoiGian);
        add(new JLabel("Trạng thái:")); tfTrangThai = new JTextField(); add(tfTrangThai);
        add(new JLabel("Hình thức thanh toán:")); tfHinhThucThanhToan = new JTextField(); add(tfHinhThucThanhToan);

        JButton btnLuu = new JButton("Lưu");
        JButton btnHuy = new JButton("Hủy");
        add(btnLuu); add(btnHuy);

        // Add listeners for calculating total amount
        tfSoLuong.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { calculateTotal(); }
            public void removeUpdate(DocumentEvent e) { calculateTotal(); }
            public void insertUpdate(DocumentEvent e) { calculateTotal(); }
        });
        
        tfDonGia.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { calculateTotal(); }
            public void removeUpdate(DocumentEvent e) { calculateTotal(); }
            public void insertUpdate(DocumentEvent e) { calculateTotal(); }
        });

        btnLuu.addActionListener(e -> {
            if (validateInput()) {
                saved = true;
                setVisible(false);
            }
        });
        
        btnHuy.addActionListener(e -> setVisible(false));

        pack();
        setLocationRelativeTo(parent);
    }

    private void calculateTotal() {
        try {
            int soLuong = Integer.parseInt(tfSoLuong.getText());
            double donGia = Double.parseDouble(tfDonGia.getText());
            tfThanhTien.setText(String.valueOf(soLuong * donGia));
        } catch (Exception ex) {
            tfThanhTien.setText("");
        }
    }

    private boolean validateInput() {
        if (tfMaPN.getText().trim().isEmpty() ||
            tfMaNCC.getText().trim().isEmpty() ||
            tfMaSP.getText().trim().isEmpty() ||
            tfSoLuong.getText().trim().isEmpty() ||
            tfDonGia.getText().trim().isEmpty() ||
            tfThoiGian.getText().trim().isEmpty() ||
            tfTrangThai.getText().trim().isEmpty() ||
            tfHinhThucThanhToan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
            return false;
        }

        try {
            int soLuong = Integer.parseInt(tfSoLuong.getText());
            double donGia = Double.parseDouble(tfDonGia.getText());
            if (soLuong <= 0 || donGia <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải lớn hơn 0!");
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải là số!");
            return false;
        }

        return true;
    }

    public boolean isSaved() { return saved; }

    public PhieuNhapDTO getNewPhieuNhap() {
        return new PhieuNhapDTO(
            tfMaPN.getText(),
            tfMaSP.getText(),
            tfMaNCC.getText(),
            Integer.parseInt(tfSoLuong.getText()),
            Double.parseDouble(tfDonGia.getText()),
            Double.parseDouble(tfThanhTien.getText()),
            new java.util.Date(tfThoiGian.getText()),
            tfTrangThai.getText(),
            tfHinhThucThanhToan.getText()
        );
    }
} 