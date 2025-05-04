package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import DTB.ConnectDB;
import DTO.PhieuNhapDTO;

public class PhieuNhapDAO {

    public boolean themPhieuNhap(PhieuNhapDTO phieuNhap) {
        String sql = "INSERT INTO PhieuNhap (NgayNhap, MaSanPham, SoLuongNhap, MaNhaCungCap, MaNhanVien) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new java.sql.Timestamp(phieuNhap.getNgayNhap().getTime()));
            ps.setString(2, phieuNhap.getMaSanPham());
            ps.setInt(3, phieuNhap.getSoLuongNhap());
            ps.setString(4, phieuNhap.getMaNhaCungCap());
            ps.setString(5, phieuNhap.getMaNhanVien());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
} 