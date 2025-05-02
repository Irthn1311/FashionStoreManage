package DAO;

import DTO.NhaCungCap_SanPhamDTO;
import DTB.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhaCungCap_SanPhamDAO {
    
    public List<NhaCungCap_SanPhamDTO> getAllNhaCungCap_SanPham() {
        List<NhaCungCap_SanPhamDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap_SanPham";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                NhaCungCap_SanPhamDTO nccsp = new NhaCungCap_SanPhamDTO(
                    rs.getString("MaNhaCungCap"),
                    rs.getString("MaSanPham")
                );
                list.add(nccsp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    public boolean themNhaCungCap_SanPham(NhaCungCap_SanPhamDTO nccsp) {
        String sql = "INSERT INTO NhaCungCap_SanPham (MaNhaCungCap, MaSanPham) VALUES (?, ?)";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, nccsp.getMaNhaCungCap());
            pst.setString(2, nccsp.getMaSanPham());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean xoaNhaCungCap_SanPham(String maNhaCungCap, String maSanPham) {
        String sql = "DELETE FROM NhaCungCap_SanPham WHERE MaNhaCungCap = ? AND MaSanPham = ?";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, maNhaCungCap);
            pst.setString(2, maSanPham);
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<NhaCungCap_SanPhamDTO> getSanPhamByNhaCungCap(String maNhaCungCap) {
        List<NhaCungCap_SanPhamDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap_SanPham WHERE MaNhaCungCap = ?";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, maNhaCungCap);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    NhaCungCap_SanPhamDTO nccsp = new NhaCungCap_SanPhamDTO(
                        rs.getString("MaNhaCungCap"),
                        rs.getString("MaSanPham")
                    );
                    list.add(nccsp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    public List<NhaCungCap_SanPhamDTO> getNhaCungCapBySanPham(String maSanPham) {
        List<NhaCungCap_SanPhamDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap_SanPham WHERE MaSanPham = ?";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, maSanPham);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    NhaCungCap_SanPhamDTO nccsp = new NhaCungCap_SanPhamDTO(
                        rs.getString("MaNhaCungCap"),
                        rs.getString("MaSanPham")
                    );
                    list.add(nccsp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
} 