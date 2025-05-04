package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DTB.ConnectDB;
import DTO.sanPhamDTO;

public class SanPhamDAO {

    // Lấy sản phẩm theo mã sản phẩm
    public sanPhamDTO getSanPhamByMa(String maSanPham) {
        String sql = "SELECT * FROM SanPham WHERE MaSanPham = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maSanPham);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new sanPhamDTO(
                            rs.getString("MaSanPham"),
                            rs.getString("TenSanPham"),
                            rs.getString("MaThuongHieu"),
                            rs.getString("MaDanhMuc"),
                            rs.getDouble("GiaBan"),
                            rs.getInt("SoLuongTonKho"),
                            rs.getString("Size"),
                            rs.getString("TrangThai"),
                            rs.getString("ImgURL"),
                            rs.getString("MaKhoHang")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Trả về null nếu không tìm thấy sản phẩm
    }

    // Lấy danh sách tất cả sản phẩm
    public List<sanPhamDTO> getAllSanPham() {
        List<sanPhamDTO> sanPhamList = new ArrayList<>();
        String sql = "SELECT * FROM SanPham";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                sanPhamDTO sp = new sanPhamDTO(
                        rs.getString("MaSanPham"),
                        rs.getString("TenSanPham"),
                        rs.getString("MaThuongHieu"),
                        rs.getString("MaDanhMuc"),
                        rs.getDouble("GiaBan"),
                        rs.getInt("SoLuongTonKho"),
                        rs.getString("Size"),
                        rs.getString("TrangThai"),
                        rs.getString("ImgURL"),
                        rs.getString("MaKhoHang")
                );
                sanPhamList.add(sp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sanPhamList;
    }

    // Lấy danh sách tất cả MaSanPham
    public List<String> getAllMaSanPham() {
        List<String> maSanPhamList = new ArrayList<>();
        String sql = "SELECT MaSanPham FROM SanPham";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                maSanPhamList.add(rs.getString("MaSanPham"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maSanPhamList;
    }

    // Thêm sản phẩm mới
    public boolean addSanPham(sanPhamDTO sp) {
        String sql = "INSERT INTO [FashionStore].[dbo].[SanPham] (MaSanPham, TenSanPham, MaThuongHieu, MaDanhMuc, " +
                     "GiaBan, SoLuongTonKho, Size, TrangThai, ImgURL, MaKhoHang) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sp.getMaSanPham());
            ps.setString(2, sp.getTenSanPham());
            ps.setString(3, sp.getMaThuongHieu());
            ps.setString(4, sp.getMaDanhMuc());
            ps.setDouble(5, sp.getGiaBan());
            ps.setInt(6, sp.getSoLuongTonKho());
            ps.setString(7, sp.getSize());
            ps.setString(8, sp.getTrangThai());
            ps.setString(9, sp.getImgURL());
            ps.setString(10, sp.getMaKhoHang());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật thông tin sản phẩm
    public boolean updateSanPham(sanPhamDTO sp) {
        String sql = "UPDATE [FashionStore].[dbo].[SanPham] SET TenSanPham = ?, MaThuongHieu = ?, MaDanhMuc = ?, " +
                     "GiaBan = ?, SoLuongTonKho = ?, Size = ?, TrangThai = ?, ImgURL = ?, MaKhoHang = ? WHERE MaSanPham = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sp.getTenSanPham());
            ps.setString(2, sp.getMaThuongHieu());
            ps.setString(3, sp.getMaDanhMuc());
            ps.setDouble(4, sp.getGiaBan());
            ps.setInt(5, sp.getSoLuongTonKho());
            ps.setString(6, sp.getSize());
            ps.setString(7, sp.getTrangThai());
            ps.setString(8, sp.getImgURL());
            ps.setString(9, sp.getMaKhoHang());
            ps.setString(10, sp.getMaSanPham());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa sản phẩm
    public boolean deleteSanPham(String maSanPham) {
        String sql = "DELETE FROM [FashionStore].[dbo].[SanPham] WHERE MaSanPham = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maSanPham);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}