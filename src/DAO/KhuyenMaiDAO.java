package DAO;

import DTO.khuyenMaiDTO;
import DTB.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KhuyenMaiDAO {

    public List<khuyenMaiDTO> getAllKhuyenMai() {
        List<khuyenMaiDTO> khuyenMaiList = new ArrayList<>();
        String sql = "SELECT MaKhuyenMai, TenChuongTrinh, GiamGia, NgayBatDau, NgayKetThuc, TrangThai, GiaMoi, Khac FROM KhuyenMai";

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                khuyenMaiDTO km = new khuyenMaiDTO(
                        rs.getString("MaKhuyenMai"),
                        rs.getString("TenChuongTrinh"),
                        rs.getDouble("GiamGia"),
                        rs.getDate("NgayBatDau"),
                        rs.getDate("NgayKetThuc"),
                        rs.getString("TrangThai"),
                        rs.getDouble("GiaMoi"),
                        rs.getString("Khac"));
                khuyenMaiList.add(km);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return khuyenMaiList;
    }

    public List<khuyenMaiDTO> searchKhuyenMai(String keyword) {
        List<khuyenMaiDTO> khuyenMaiList = new ArrayList<>();
        String sql = "SELECT MaKhuyenMai, TenChuongTrinh, GiamGia, NgayBatDau, NgayKetThuc, TrangThai, GiaMoi, Khac " +
                "FROM KhuyenMai WHERE MaKhuyenMai LIKE ? OR TenChuongTrinh LIKE ? OR TrangThai LIKE ?";

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    khuyenMaiDTO km = new khuyenMaiDTO(
                            rs.getString("MaKhuyenMai"),
                            rs.getString("TenChuongTrinh"),
                            rs.getDouble("GiamGia"),
                            rs.getDate("NgayBatDau"),
                            rs.getDate("NgayKetThuc"),
                            rs.getString("TrangThai"),
                            rs.getDouble("GiaMoi"),
                            rs.getString("Khac"));
                    khuyenMaiList.add(km);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return khuyenMaiList;
    }
}
