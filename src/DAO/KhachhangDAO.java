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
import java.sql.Timestamp;

/**
 *
 * @author nson9
 */
public class KhachhangDAO {

    public List<khachHangDTO> getAllKhachHang() {
        List<khachHangDTO> khachHangList = new ArrayList<>();
        String sql = "SELECT MaKhachHang, HoTen, Email, Phone, DiaChi, GioiTinh, " +
                "CAST(NgaySinh AS DATE) AS NgaySinh, " +
                "CAST(NgayDangKy AS DATETIME) AS NgayDangKy, " +
                "MaTaiKhoan FROM KhachHang";

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Xử lý ngày tháng
                Date ngaySinh = rs.getDate("NgaySinh");
                Timestamp ngayDangKy = rs.getTimestamp("NgayDangKy");

                khachHangDTO kh = new khachHangDTO(
                        rs.getString("MaKhachHang"),
                        rs.getString("HoTen"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("DiaChi"),
                        rs.getString("GioiTinh"),
                        ngaySinh,
                        ngayDangKy,
                        rs.getString("MaTaiKhoan"));
                khachHangList.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return khachHangList;
    }

    // Thêm phương thức tìm kiếm khách hàng theo tên
    public List<khachHangDTO> searchKhachHang(String keyword) {
        List<khachHangDTO> khachHangList = new ArrayList<>();
        String sql = "SELECT MaKhachHang, HoTen, Email, Phone, DiaChi, GioiTinh, " +
                "CAST(NgaySinh AS DATE) AS NgaySinh, " +
                "CAST(NgayDangKy AS DATETIME) AS NgayDangKy, " +
                "MaTaiKhoan FROM KhachHang " +
                "WHERE HoTen LIKE ? OR Phone LIKE ? OR Email LIKE ?";

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Xử lý ngày tháng
                    Date ngaySinh = rs.getDate("NgaySinh");
                    Timestamp ngayDangKy = rs.getTimestamp("NgayDangKy");

                    khachHangDTO kh = new khachHangDTO(
                            rs.getString("MaKhachHang"),
                            rs.getString("HoTen"),
                            rs.getString("Email"),
                            rs.getString("Phone"),
                            rs.getString("DiaChi"),
                            rs.getString("GioiTinh"),
                            ngaySinh,
                            ngayDangKy,
                            rs.getString("MaTaiKhoan"));
                    khachHangList.add(kh);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return khachHangList;
    }
}
