/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.khachHangDTO;
import DTB.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

/**
 *
 * @author nson9
 */
public class KhachHangDAO {

    public khachHangDTO getKhachHangByMa(String maKhachHang) {
        String sql = "SELECT MaKhachHang, HoTen, TenDangNhap, Email, SoDienThoai, DiaChi, GioiTinh, " +
                     "CAST(NgaySinh AS DATE) AS NgaySinh " +
                     "FROM KhachHang WHERE MaKhachHang = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maKhachHang);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Xử lý ngày tháng
                    Date ngaySinh = rs.getDate("NgaySinh");

                    return new khachHangDTO(
                            rs.getString("MaKhachHang"),
                            rs.getString("HoTen"),
                            rs.getString("TenDangNhap"),
                            rs.getString("Email"),
                            rs.getString("SoDienThoai"),
                            rs.getString("DiaChi"),
                            rs.getString("GioiTinh"),
                            ngaySinh);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy khách hàng theo mã: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null; // Trả về null nếu không tìm thấy khách hàng
    }

    public List<khachHangDTO> getAllKhachHang() {
        List<khachHangDTO> khachHangList = new ArrayList<>();
        String sql = "SELECT MaKhachHang, HoTen, TenDangNhap, Email, SoDienThoai, DiaChi, GioiTinh, " +
                     "CAST(NgaySinh AS DATE) AS NgaySinh " +
                     "FROM KhachHang";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Xử lý ngày tháng
                Date ngaySinh = rs.getDate("NgaySinh");

                khachHangDTO kh = new khachHangDTO(
                        rs.getString("MaKhachHang"),
                        rs.getString("HoTen"),
                        rs.getString("TenDangNhap"),
                        rs.getString("Email"),
                        rs.getString("SoDienThoai"),
                        rs.getString("DiaChi"),
                        rs.getString("GioiTinh"),
                        ngaySinh);
                khachHangList.add(kh);
            }
            System.out.println("Số lượng khách hàng lấy được: " + khachHangList.size());
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách khách hàng: " + e.getMessage());
            e.printStackTrace();
        }

        return khachHangList;
    }

    // Lấy danh sách tất cả mã khách hàng
    public List<String> getAllMaKhachHang() {
        List<String> maKhachHangList = new ArrayList<>();
        String sql = "SELECT MaKhachHang FROM KhachHang";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                maKhachHangList.add(rs.getString("MaKhachHang"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách mã khách hàng: " + e.getMessage());
            e.printStackTrace();
        }

        return maKhachHangList;
    }

    // Thêm phương thức tìm kiếm khách hàng theo tên
    public List<khachHangDTO> searchKhachHang(String keyword, String searchType) {
        List<khachHangDTO> khachHangList = new ArrayList<>();
        String sql = "SELECT MaKhachHang, HoTen, TenDangNhap, Email, SoDienThoai, DiaChi, GioiTinh, " +
                     "CAST(NgaySinh AS DATE) AS NgaySinh " +
                     "FROM KhachHang " +
                     "WHERE 1=1 ";

        // Thêm điều kiện tìm kiếm dựa vào loại tìm kiếm
        switch (searchType) {
            case "Mã khách hàng":
                sql += "AND MaKhachHang LIKE ? ";
                break;
            case "Tên khách hàng":
                sql += "AND HoTen LIKE ? ";
                break;
            case "Email":
                sql += "AND Email LIKE ? ";
                break;
            case "Số điện thoại":
                sql += "AND SoDienThoai LIKE ? ";
                break;
            default:
                sql += "AND (MaKhachHang LIKE ? OR HoTen LIKE ? OR Email LIKE ? OR SoDienThoai LIKE ?) ";
                break;
        }

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set tham số cho câu truy vấn
            if ("Tất cả".equals(searchType)) {
                String likePattern = "%" + keyword + "%";
                ps.setString(1, likePattern);
                ps.setString(2, likePattern);
                ps.setString(3, likePattern);
                ps.setString(4, likePattern);
            } else {
                ps.setString(1, "%" + keyword + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Xử lý ngày tháng
                    Date ngaySinh = rs.getDate("NgaySinh");

                    khachHangDTO kh = new khachHangDTO(
                            rs.getString("MaKhachHang"),
                            rs.getString("HoTen"),
                            rs.getString("TenDangNhap"),
                            rs.getString("Email"),
                            rs.getString("SoDienThoai"),
                            rs.getString("DiaChi"),
                            rs.getString("GioiTinh"),
                            ngaySinh);
                    khachHangList.add(kh);
                }
                System.out.println("Số lượng khách hàng tìm thấy: " + khachHangList.size());
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm khách hàng: " + e.getMessage());
            e.printStackTrace();
        }

        return khachHangList;
    }

    public boolean themKhachHang(khachHangDTO khachHang) {
        String sql = "INSERT INTO KhachHang (MaKhachHang, HoTen, TenDangNhap, Email, SoDienThoai, DiaChi, GioiTinh, NgaySinh) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, khachHang.getMaKhachHang());
            ps.setString(2, khachHang.getHoTen());
            ps.setString(3, khachHang.getTenDangNhap());
            ps.setString(4, khachHang.getEmail());
            ps.setString(5, khachHang.getSoDienThoai());
            ps.setString(6, khachHang.getDiaChi());
            ps.setString(7, khachHang.getGioiTinh());
            ps.setDate(8, khachHang.getNgaySinh());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm khách hàng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaKhachHang(String maKhachHang) {
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // Kiểm tra xem khách hàng có hóa đơn đang xử lý không
            String sqlCheckHoaDon = "SELECT COUNT(*) FROM HoaDon " +
                                    "WHERE MaKhachHang = ? AND TrangThai NOT IN ('Hoàn thành', 'Đã hủy')";
            try (PreparedStatement psCheck = conn.prepareStatement(sqlCheckHoaDon)) {
                psCheck.setString(1, maKhachHang);
                ResultSet rs = psCheck.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("Không thể xóa khách hàng này vì có hóa đơn đang được xử lý!");
                }
            }

            // Xóa các hóa đơn đã hoàn thành hoặc đã hủy
            String sqlDeleteHoaDon = "DELETE FROM HoaDon WHERE MaKhachHang = ? AND TrangThai IN ('Hoàn thành', 'Đã hủy')";
            try (PreparedStatement psDeleteHD = conn.prepareStatement(sqlDeleteHoaDon)) {
                psDeleteHD.setString(1, maKhachHang);
                psDeleteHD.executeUpdate();
            }

            // Xóa khách hàng
            String sqlDeleteKhachHang = "DELETE FROM KhachHang WHERE MaKhachHang = ?";
            int rowsAffected;
            try (PreparedStatement psDelete = conn.prepareStatement(sqlDeleteKhachHang)) {
                psDelete.setString(1, maKhachHang);
                rowsAffected = psDelete.executeUpdate();
            }

            conn.commit(); // Commit transaction nếu mọi thứ OK
            return rowsAffected > 0;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback nếu có lỗi
                } catch (SQLException ex) {
                    System.err.println("Lỗi khi rollback: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException("Lỗi khi xóa khách hàng: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reset lại auto commit
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean capNhatKhachHang(khachHangDTO khachHang) {
        String sql = "UPDATE KhachHang SET HoTen = ?, TenDangNhap = ?, Email = ?, SoDienThoai = ?, DiaChi = ?, " +
                     "GioiTinh = ?, NgaySinh = ? WHERE MaKhachHang = ?";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, khachHang.getHoTen());
            ps.setString(2, khachHang.getTenDangNhap());
            ps.setString(3, khachHang.getEmail());
            ps.setString(4, khachHang.getSoDienThoai());
            ps.setString(5, khachHang.getDiaChi());
            ps.setString(6, khachHang.getGioiTinh());
            ps.setDate(7, khachHang.getNgaySinh());
            ps.setString(8, khachHang.getMaKhachHang());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật khách hàng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}