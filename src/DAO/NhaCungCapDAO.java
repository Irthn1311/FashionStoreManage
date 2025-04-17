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
        String sql = "SELECT ncc.MaNhaCungCap, ncc.TenNhaCungCap, ncc.MaSanPham, ncc.LoaiSP, " +
                    "ncc.NamHopTac, ncc.Address, ncc.Email, ncc.SoDienThoai, ncc.TrangThai, " +
                    "sp.TenSanPham " +
                    "FROM NhaCungCap ncc " +
                    "LEFT JOIN SanPham sp ON ncc.MaSanPham = sp.MaSanPham";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                nhaCungCapDTO ncc = new nhaCungCapDTO(
                    rs.getString("MaNhaCungCap"),
                    rs.getString("TenNhaCungCap"),
                    rs.getString("MaSanPham"),
                    rs.getString("LoaiSP"),
                    rs.getString("TenSanPham"),
                    rs.getInt("NamHopTac"),
                    rs.getString("Address"),
                    rs.getString("Email"),
                    rs.getString("SoDienThoai"),
                    rs.getString("TrangThai")
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
        String sql = "SELECT ncc.MaNhaCungCap, ncc.TenNhaCungCap, ncc.MaSanPham, ncc.LoaiSP, " +
                    "ncc.NamHopTac, ncc.Address, ncc.Email, ncc.SoDienThoai, ncc.TrangThai, " +
                    "sp.TenSanPham " +
                    "FROM NhaCungCap ncc " +
                    "LEFT JOIN SanPham sp ON ncc.MaSanPham = sp.MaSanPham " +
                    "WHERE ncc.TenNhaCungCap LIKE ? OR ncc.Email LIKE ? OR ncc.SoDienThoai LIKE ? OR ncc.MaNhaCungCap LIKE ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            for (int i = 1; i <= 4; i++) {
                ps.setString(i, searchPattern);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    nhaCungCapDTO ncc = new nhaCungCapDTO(
                        rs.getString("MaNhaCungCap"),
                        rs.getString("TenNhaCungCap"),
                        rs.getString("MaSanPham"),
                        rs.getString("LoaiSP"),
                        rs.getString("TenSanPham"),
                        rs.getInt("NamHopTac"),
                        rs.getString("Address"),
                        rs.getString("Email"),
                        rs.getString("SoDienThoai"),
                        rs.getString("TrangThai")
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
