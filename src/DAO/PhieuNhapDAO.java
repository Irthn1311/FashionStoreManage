package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DTB.ConnectDB;
import DTO.PhieuNhapDTO;
import java.util.ArrayList;
import java.util.List;

public class PhieuNhapDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public PhieuNhapDAO() {
        // Initialize database connection
        try {
            conn = ConnectDB.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Create new PhieuNhap
    public boolean create(PhieuNhapDTO phieuNhap) {
        String sql = "INSERT INTO PhieuNhap (NgayNhap, MaSanPham, SoLuongNhap, MaNhaCungCap, MaNhanVien) " +
                    "VALUES (?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, new java.sql.Timestamp(phieuNhap.getNgayNhap().getTime()));
            ps.setString(2, phieuNhap.getMaSanPham());
            ps.setInt(3, phieuNhap.getSoLuongNhap());
            ps.setString(4, phieuNhap.getMaNhaCungCap());
            ps.setString(5, phieuNhap.getMaNhanVien());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read PhieuNhap by ID
    public PhieuNhapDTO read(int maPhieuNhap) {
        String sql = "SELECT * FROM PhieuNhap WHERE MaPhieuNhap = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maPhieuNhap);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new PhieuNhapDTO(
                    rs.getInt("MaPhieuNhap"),
                    rs.getTimestamp("NgayNhap"),
                    rs.getString("MaSanPham"),
                    rs.getInt("SoLuongNhap"),
                    rs.getString("MaNhaCungCap"),
                    rs.getString("MaNhanVien")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update PhieuNhap
    public boolean update(PhieuNhapDTO phieuNhap) {
        String sql = "UPDATE PhieuNhap SET NgayNhap = ?, MaSanPham = ?, SoLuongNhap = ?, " +
                    "MaNhaCungCap = ?, MaNhanVien = ? WHERE MaPhieuNhap = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, new java.sql.Timestamp(phieuNhap.getNgayNhap().getTime()));
            ps.setString(2, phieuNhap.getMaSanPham());
            ps.setInt(3, phieuNhap.getSoLuongNhap());
            ps.setString(4, phieuNhap.getMaNhaCungCap());
            ps.setString(5, phieuNhap.getMaNhanVien());
            ps.setInt(6, phieuNhap.getMaPhieuNhap());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete PhieuNhap
    public boolean delete(int maPhieuNhap) {
        String sql = "DELETE FROM PhieuNhap WHERE MaPhieuNhap = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maPhieuNhap);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all PhieuNhap
    public List<PhieuNhapDTO> getAll() {
        List<PhieuNhapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM PhieuNhap";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new PhieuNhapDTO(
                    rs.getInt("MaPhieuNhap"),
                    rs.getTimestamp("NgayNhap"),
                    rs.getString("MaSanPham"),
                    rs.getInt("SoLuongNhap"),
                    rs.getString("MaNhaCungCap"),
                    rs.getString("MaNhanVien")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Close resources
    public void close() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 