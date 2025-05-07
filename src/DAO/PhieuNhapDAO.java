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
        String sql = "INSERT INTO PhieuNhap (MaPhieuNhap, MaNhaCungCap, MaSanPham, TenSanPham, SoLuong, ThoiGian, DonGia, TrangThai, HinhThucThanhToan, ThanhTien) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, phieuNhap.getMaPhieuNhap());
            ps.setString(2, phieuNhap.getMaNhaCungCap());
            ps.setString(3, phieuNhap.getMaSanPham());
            ps.setString(4, phieuNhap.getTenSanPham());
            ps.setInt(5, phieuNhap.getSoLuong());
            ps.setTimestamp(6, new java.sql.Timestamp(phieuNhap.getThoiGian().getTime()));
            ps.setDouble(7, phieuNhap.getDonGia());
            ps.setString(8, phieuNhap.getTrangThai());
            ps.setString(9, phieuNhap.getHinhThucThanhToan());
            ps.setDouble(10, phieuNhap.getThanhTien());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read PhieuNhap by ID
    public PhieuNhapDTO read(String maPhieuNhap) {
        String sql = "SELECT * FROM PhieuNhap WHERE MaPhieuNhap = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, maPhieuNhap);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new PhieuNhapDTO(
                    rs.getString("MaPhieuNhap"),
                    rs.getString("MaNhaCungCap"),
                    rs.getString("MaSanPham"),
                    rs.getString("TenSanPham"),
                    rs.getInt("SoLuong"),
                    rs.getTimestamp("ThoiGian"),
                    rs.getDouble("DonGia"),
                    rs.getString("TrangThai"),
                    rs.getString("HinhThucThanhToan"),
                    rs.getDouble("ThanhTien")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update PhieuNhap
    public boolean update(PhieuNhapDTO phieuNhap) {
        String sql = "UPDATE PhieuNhap SET MaNhaCungCap=?, MaSanPham=?, TenSanPham=?, SoLuong=?, " +
                    "ThoiGian=?, DonGia=?, TrangThai=?, HinhThucThanhToan=?, ThanhTien=? WHERE MaPhieuNhap=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, phieuNhap.getMaNhaCungCap());
            ps.setString(2, phieuNhap.getMaSanPham());
            ps.setString(3, phieuNhap.getTenSanPham());
            ps.setInt(4, phieuNhap.getSoLuong());
            ps.setTimestamp(5, new java.sql.Timestamp(phieuNhap.getThoiGian().getTime()));
            ps.setDouble(6, phieuNhap.getDonGia());
            ps.setString(7, phieuNhap.getTrangThai());
            ps.setString(8, phieuNhap.getHinhThucThanhToan());
            ps.setDouble(9, phieuNhap.getThanhTien());
            ps.setString(10, phieuNhap.getMaPhieuNhap());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete PhieuNhap
    public boolean delete(String maPhieuNhap) {
        String sql = "DELETE FROM PhieuNhap WHERE MaPhieuNhap = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, maPhieuNhap);
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
                    rs.getString("MaPhieuNhap"),
                    rs.getString("MaNhaCungCap"),
                    rs.getString("MaSanPham"),
                    rs.getString("TenSanPham"),
                    rs.getInt("SoLuong"),
                    rs.getTimestamp("ThoiGian"),
                    rs.getDouble("DonGia"),
                    rs.getString("TrangThai"),
                    rs.getString("HinhThucThanhToan"),
                    rs.getDouble("ThanhTien")
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