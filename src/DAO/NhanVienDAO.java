package DAO;

import DTO.nhanVienDTO;
import DTB.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;

public class NhanVienDAO {
    
    public List<nhanVienDTO> getAllNhanVien() {
        List<nhanVienDTO> nhanVienList = new ArrayList<>();
        String sql = "SELECT MaNhanVien, HoTen, Email, SoDienThoai, DiaChi, GioiTinh, " +
                    "CAST(NgaySinh AS DATE) AS NgaySinh, " +
                    "CAST(NgayVaoLam AS DATETIME) AS NgayVaoLam, " +
                    "ChucVu, MucLuong, MaTaiKhoan, MaQuanLy FROM NhanVien";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                nhanVienDTO nv = new nhanVienDTO(
                    rs.getString("MaNhanVien"),
                    rs.getString("HoTen"),
                    rs.getString("Email"),
                    rs.getString("SoDienThoai"),
                    rs.getString("DiaChi"),
                    rs.getString("GioiTinh"),
                    rs.getDate("NgaySinh"),
                    rs.getTimestamp("NgayVaoLam"),
                    rs.getString("ChucVu"),
                    rs.getBigDecimal("MucLuong"),
                    rs.getString("MaTaiKhoan"),
                    rs.getLong("MaQuanLy")
                );
                nhanVienList.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return nhanVienList;
    }
    
    public List<nhanVienDTO> searchNhanVien(String keyword) {
        List<nhanVienDTO> nhanVienList = new ArrayList<>();
        String sql = "SELECT MaNhanVien, HoTen, Email, SoDienThoai, DiaChi, GioiTinh, " +
                    "CAST(NgaySinh AS DATE) AS NgaySinh, " +
                    "CAST(NgayVaoLam AS DATETIME) AS NgayVaoLam, " +
                    "ChucVu, MucLuong, MaTaiKhoan, MaQuanLy FROM NhanVien " +
                    "WHERE HoTen LIKE ? OR Email LIKE ? OR SoDienThoai LIKE ? OR MaNhanVien LIKE ?";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            ps.setString(4, searchPattern);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    nhanVienDTO nv = new nhanVienDTO(
                        rs.getString("MaNhanVien"),
                        rs.getString("HoTen"),
                        rs.getString("Email"),
                        rs.getString("SoDienThoai"),
                        rs.getString("DiaChi"),
                        rs.getString("GioiTinh"),
                        rs.getDate("NgaySinh"),
                        rs.getTimestamp("NgayVaoLam"),
                        rs.getString("ChucVu"),
                        rs.getBigDecimal("MucLuong"),
                        rs.getString("MaTaiKhoan"),
                        rs.getLong("MaQuanLy")
                    );
                    nhanVienList.add(nv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return nhanVienList;
    }
} 