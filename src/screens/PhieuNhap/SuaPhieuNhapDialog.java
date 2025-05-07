package screens.PhieuNhap;

import DTO.nhapHangDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SuaPhieuNhapDialog extends JDialog {
    private JTextField tfMaPN, tfMaNCC, tfMaSP, tfLoaiSP, tfTenSP, tfMauSac, tfKichThuoc, tfSoLuong, tfDonGia, tfThanhTien, tfThoiGian, tfTrangThai;
    private boolean saved = false;

    public SuaPhieuNhapDialog(Frame parent, nhapHangDTO nh) {
        super(parent, "Sửa Phiếu Nhập", true);
        setLayout(new GridLayout(0,2,10,5));

        add(new JLabel("Mã PN:")); tfMaPN = new JTextField(nh.getMaPN()); tfMaPN.setEditable(false); add(tfMaPN);
        add(new JLabel("Mã NCC:")); tfMaNCC = new JTextField(nh.getMaNhaCungCap()); add(tfMaNCC);
        add(new JLabel("Mã SP:")); tfMaSP = new JTextField(nh.getMaSanPham()); add(tfMaSP);
        add(new JLabel("Loại SP:")); tfLoaiSP = new JTextField(nh.getLoaiSP()); add(tfLoaiSP);
        add(new JLabel("Tên SP:")); tfTenSP = new JTextField(nh.getTenSanPham()); add(tfTenSP);
        add(new JLabel("Màu sắc:")); tfMauSac = new JTextField(nh.getMauSac()); add(tfMauSac);
        add(new JLabel("Kích thước:")); tfKichThuoc = new JTextField(nh.getKichThuoc()); add(tfKichThuoc);
        add(new JLabel("Số lượng:")); tfSoLuong = new JTextField(nh.getSoLuong()); add(tfSoLuong);
        add(new JLabel("Đơn giá:")); tfDonGia = new JTextField(nh.getDonGia()); add(tfDonGia);
        add(new JLabel("Thành tiền:")); tfThanhTien = new JTextField(nh.getThanhTien()); tfThanhTien.setEditable(false); add(tfThanhTien);
        add(new JLabel("Thời gian:")); tfThoiGian = new JTextField(nh.getThoiGian()); add(tfThoiGian);
        add(new JLabel("Trạng thái:")); tfTrangThai = new JTextField(nh.getTrangThai()); add(tfTrangThai);

        JButton btnLuu = new JButton("Lưu");
        JButton btnHuy = new JButton("Hủy");
        add(btnLuu); add(btnHuy);

        btnLuu.addActionListener(e -> {
            // Tính lại thành tiền
            try {
                double soLuong = Double.parseDouble(tfSoLuong.getText());
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

    public nhapHangDTO getUpdatedPhieuNhap() {
        return new nhapHangDTO(
            tfMaPN.getText(),
            tfMaNCC.getText(),
            tfLoaiSP.getText(),
            tfMaSP.getText(),
            tfTenSP.getText(),
            tfMauSac.getText(),
            tfKichThuoc.getText(),
            tfSoLuong.getText(),
            tfDonGia.getText(),
            tfThanhTien.getText(),
            tfThoiGian.getText(),
            tfTrangThai.getText()
        );
    }
}
