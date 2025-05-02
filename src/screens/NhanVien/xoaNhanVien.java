/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package screens.NhanVien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import DTO.nhanVienDTO;
import BUS.NhanVienBUS;
import java.text.SimpleDateFormat;

/**
 *
 * @author nson9
 */
public class xoaNhanVien extends JDialog {
    private nhanVienDTO nhanVien;
    private NhanVienBUS nhanVienBUS;
    private JPanel mainPanel;
    private JTextField txtMaNV;
    private JTextField txtHoTen;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtDiaChi;
    private JTextField txtGioiTinh;
    private JTextField txtNgaySinh;
    private JTextField txtChucVu;
    private JTextField txtMucLuong;
    private JButton btnXoa;
    private JButton btnHuy;

    public xoaNhanVien(JFrame parent, nhanVienDTO nhanVien) {
        super(parent, "Xóa Nhân Viên", true);
        this.nhanVien = nhanVien;
        this.nhanVienBUS = new NhanVienBUS();
        
        initComponents();
        loadNhanVienData();
        
        setLocationRelativeTo(parent);
        pack();
        setResizable(false);
    }

    private void initComponents() {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Khởi tạo components
        txtMaNV = new JTextField(20);
        txtHoTen = new JTextField(20);
        txtEmail = new JTextField(20);
        txtPhone = new JTextField(20);
        txtDiaChi = new JTextField(20);
        txtGioiTinh = new JTextField(20);
        txtNgaySinh = new JTextField(20);
        txtChucVu = new JTextField(20);
        txtMucLuong = new JTextField(20);

        // Thiết lập các trường không được sửa
        txtMaNV.setEditable(false);
        txtHoTen.setEditable(false);
        txtEmail.setEditable(false);
        txtPhone.setEditable(false);
        txtDiaChi.setEditable(false);
        txtGioiTinh.setEditable(false);
        txtNgaySinh.setEditable(false);
        txtChucVu.setEditable(false);
        txtMucLuong.setEditable(false);

        // Thêm components vào panel
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Mã nhân viên:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtMaNV, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Họ tên:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtHoTen, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtPhone, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtDiaChi, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(new JLabel("Giới tính:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtGioiTinh, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        mainPanel.add(new JLabel("Ngày sinh:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtNgaySinh, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        mainPanel.add(new JLabel("Chức vụ:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtChucVu, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        mainPanel.add(new JLabel("Mức lương:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtMucLuong, gbc);

        // Panel chứa buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnXoa = new JButton("Xóa");
        btnHuy = new JButton("Hủy");

        btnXoa.setPreferredSize(new Dimension(100, 30));
        btnHuy.setPreferredSize(new Dimension(100, 30));

        buttonPanel.add(btnXoa);
        buttonPanel.add(btnHuy);

        // Thêm sự kiện cho buttons
        btnXoa.addActionListener(e -> xoaNhanVien());
        btnHuy.addActionListener(e -> dispose());

        // Thêm panels vào dialog
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set size
        setPreferredSize(new Dimension(400, 500));
    }

    private void loadNhanVienData() {
        txtMaNV.setText(nhanVien.getMaNhanVien());
        txtHoTen.setText(nhanVien.getHoTen());
        txtEmail.setText(nhanVien.getEmail());
        txtPhone.setText(nhanVien.getSoDienThoai());
        txtDiaChi.setText(nhanVien.getDiaChi());
        txtGioiTinh.setText(nhanVien.getGioiTinh());
        txtChucVu.setText(nhanVien.getChucVu());
        txtMucLuong.setText(nhanVien.getMucLuong() != null ? nhanVien.getMucLuong().toString() : "");
        
        if (nhanVien.getNgaySinh() != null) {
            txtNgaySinh.setText(new SimpleDateFormat("dd/MM/yyyy").format(nhanVien.getNgaySinh()));
        }
    }

    private void xoaNhanVien() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc chắn muốn xóa nhân viên này?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = nhanVienBUS.xoaNhanVien(nhanVien.getMaNhanVien());
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Xóa nhân viên thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Không thể xóa nhân viên!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(xoaNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(xoaNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(xoaNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(xoaNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

    }
}
