package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import DTB.ConnectDB;
import DTO.PhieuNhapDTO;
import java.util.ArrayList;
import java.util.List;

public class PhieuNhapDAO {

    public boolean themPhieuNhap(PhieuNhapDTO phieuNhap) {
        String sql = "INSERT INTO PhieuNhap (NgayNhap, MaSanPham, SoLuongNhap, MaNhaCungCap, MaNhanVien, LoaiSP, TenSanPham, MauSac, KichThuoc, DonGia, ThanhTien, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new java.sql.Timestamp(phieuNhap.getNgayNhap().getTime()));
            ps.setString(2, phieuNhap.getMaSanPham());
            ps.setInt(3, phieuNhap.getSoLuongNhap());
            ps.setString(4, phieuNhap.getMaNhaCungCap());
            ps.setString(5, phieuNhap.getMaNhanVien());
            ps.setString(6, phieuNhap.getLoaiSP());
            ps.setString(7, phieuNhap.getTenSanPham());
            ps.setString(8, phieuNhap.getMauSac());
            ps.setString(9, phieuNhap.getKichThuoc());
            ps.setDouble(10, phieuNhap.getDonGia());
            ps.setDouble(11, phieuNhap.getThanhTien());
            ps.setString(12, phieuNhap.getTrangThai());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<PhieuNhapDTO> getAllPhieuNhap() {
        List<PhieuNhapDTO> list = new ArrayList<>();
        // JDBC code to SELECT * FROM PhieuNhap and fill the list
        return list;
    }
} 