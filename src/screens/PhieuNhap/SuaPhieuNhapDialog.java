package screens.PhieuNhap;

import DTO.PhieuNhapDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SuaPhieuNhapDialog extends JDialog {
    private JTextField tfMaPN, tfMaNCC, tfMaSP, tfSoLuong, tfDonGia, tfThanhTien, tfThoiGian, tfTrangThai, tfHinhThucThanhToan;
    private boolean saved = false;

    public SuaPhieuNhapDialog(Frame parent, PhieuNhapDTO phieuNhap) {
        super(parent, "Sửa Phiếu Nhập", true);
        setLayout(new GridLayout(0,2,10,5));

        add(new JLabel("Mã PN:")); tfMaPN = new JTextField(phieuNhap.getMaPhieuNhap()); tfMaPN.setEditable(false); add(tfMaPN);
        add(new JLabel("Mã SP:")); tfMaSP = new JTextField(phieuNhap.getMaSanPham()); add(tfMaSP);
        add(new JLabel("Mã NCC:")); tfMaNCC = new JTextField(phieuNhap.getMaNhaCungCap()); add(tfMaNCC);
        add(new JLabel("Số lượng:")); tfSoLuong = new JTextField(String.valueOf(phieuNhap.getSoLuong())); add(tfSoLuong);
        add(new JLabel("Đơn giá:")); tfDonGia = new JTextField(String.valueOf(phieuNhap.getDonGia())); add(tfDonGia);
        add(new JLabel("Thành tiền:")); tfThanhTien = new JTextField(String.valueOf(phieuNhap.getThanhTien())); tfThanhTien.setEditable(false); add(tfThanhTien);
        add(new JLabel("Thời gian:")); tfThoiGian = new JTextField(phieuNhap.getThoiGian().toString()); add(tfThoiGian);
        add(new JLabel("Trạng thái:")); tfTrangThai = new JTextField(phieuNhap.getTrangThai()); add(tfTrangThai);
        add(new JLabel("Hình thức thanh toán:")); tfHinhThucThanhToan = new JTextField(phieuNhap.getHinhThucThanhToan()); add(tfHinhThucThanhToan);

        JButton btnLuu = new JButton("Lưu");
        JButton btnHuy = new JButton("Hủy");
        add(btnLuu); add(btnHuy);

        btnLuu.addActionListener(e -> {
            try {
                int soLuong = Integer.parseInt(tfSoLuong.getText());
                double donGia = Double.parseDouble(tfDonGia.getText());
                tfThanhTien.setText(String.valueOf(soLuong * donGia));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải là số!");
                return;
            }
            saved = true;
            setVisible(false);
        });
        btnHuy.addActionListener(e -> setVisible(false));

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isSaved() { return saved; }

    public PhieuNhapDTO getUpdatedPhieuNhap() {
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
