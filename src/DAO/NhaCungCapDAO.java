package DAO;

import DTO.nhaCungCapDTO;
import DTB.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhaCungCapDAO {
    
    public List<nhaCungCapDTO> getAllNhaCungCap() {
        List<nhaCungCapDTO> nhaCungCapList = new ArrayList<>();
        String sql = "SELECT MaNhaCungCap, TenNhaCungCap, SoDienThoai, Email, Address FROM NhaCungCap";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                nhaCungCapDTO ncc = new nhaCungCapDTO(
                    rs.getString("MaNhaCungCap"),
                    rs.getString("TenNhaCungCap"),
                    rs.getString("SoDienThoai"),
                    rs.getString("Email"),
                    rs.getString("Address")
                );
                nhaCungCapList.add(ncc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return nhaCungCapList;
    }
    
    public List<nhaCungCapDTO> searchNhaCungCap(String keyword) {
        List<nhaCungCapDTO> nhaCungCapList = new ArrayList<>();
        String sql = "SELECT MaNhaCungCap, TenNhaCungCap, SoDienThoai, Email, Address " +
                    "FROM NhaCungCap WHERE TenNhaCungCap LIKE ? OR Email LIKE ? " +
                    "OR SoDienThoai LIKE ? OR MaNhaCungCap LIKE ?";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            ps.setString(4, searchPattern);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    nhaCungCapDTO ncc = new nhaCungCapDTO(
                        rs.getString("MaNhaCungCap"),
                        rs.getString("TenNhaCungCap"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("Address")
                    );
                    nhaCungCapList.add(ncc);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return nhaCungCapList;
    }
} 