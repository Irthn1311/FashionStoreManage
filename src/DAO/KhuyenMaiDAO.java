package DAO;

import DTO.KhuyenMai;
import DTB.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KhuyenMaiDAO {
    
    public List<KhuyenMai> getAllKhuyenMai() {
        List<KhuyenMai> khuyenMaiList = new ArrayList<>();
        String sql = "SELECT MaKhuyenMai, TenChuongTrinh, GiamGia, NgayBatDau, NgayKetThuc, TrangThai FROM KhuyenMai";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai(
                    rs.getString("MaKhuyenMai"),
                    rs.getString("TenChuongTrinh"),
                    rs.getDouble("GiamGia"),
                    rs.getDate("NgayBatDau"),
                    rs.getDate("NgayKetThuc"),
                    rs.getString("TrangThai")
                );
                khuyenMaiList.add(km);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return khuyenMaiList;
    }
    
    public List<KhuyenMai> searchKhuyenMai(String keyword) {
        List<KhuyenMai> khuyenMaiList = new ArrayList<>();
        String sql = "SELECT MaKhuyenMai, TenChuongTrinh, GiamGia, NgayBatDau, NgayKetThuc, TrangThai " +
                    "FROM KhuyenMai WHERE MaKhuyenMai LIKE ? OR TenChuongTrinh LIKE ? " +
                    "OR TrangThai LIKE ?";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    KhuyenMai km = new KhuyenMai(
                        rs.getString("MaKhuyenMai"),
                        rs.getString("TenChuongTrinh"),
                        rs.getDouble("GiamGia"),
                        rs.getDate("NgayBatDau"),
                        rs.getDate("NgayKetThuc"),
                        rs.getString("TrangThai")
                    );
                    khuyenMaiList.add(km);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return khuyenMaiList;
    }
} 