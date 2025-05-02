/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.khachHangDTO;
import DTO.taiKhoanDTO;
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

    private TaiKhoanDAO taiKhoanDAO;

    public KhachhangDAO() {
        taiKhoanDAO = new TaiKhoanDAO();
    }

    public List<khachHangDTO> getAllKhachHang() {
        List<khachHangDTO> khachHangList = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                khachHangDTO kh = new khachHangDTO(
                    rs.getString("MaKhachHang"),
                    rs.getString("HoTen"),
                    rs.getString("GioiTinh"),
                    rs.getString("SoDienThoai"),
                    rs.getString("Email"),
                    rs.getString("DiaChi"),
                    rs.getDate("NgaySinh")
                );
                khachHangList.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return khachHangList;
    }

    public List<khachHangDTO> searchKhachHang(String keyword, String searchType) {
        List<khachHangDTO> khachHangList = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE 1=1 ";

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
                    khachHangDTO kh = new khachHangDTO(
                        rs.getString("MaKhachHang"),
                        rs.getString("HoTen"),
                        rs.getString("GioiTinh"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("DiaChi"),
                        rs.getDate("NgaySinh")
                    );
                    khachHangList.add(kh);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return khachHangList;
    }

    private boolean kiemTraTaiKhoanTonTai(String maTaiKhoan) {
        String sql = "SELECT COUNT(*) FROM TaiKhoanNguoiDung WHERE MaTaiKhoan = ?";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, maTaiKhoan);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean themKhachHang(khachHangDTO khachHang) {
        String sql = "INSERT INTO KhachHang (MaKhachHang, HoTen, GioiTinh, SoDienThoai, Email, DiaChi, NgaySinh) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, khachHang.getMaKhachHang());
            ps.setString(2, khachHang.getHoTen());
            ps.setString(3, khachHang.getGioiTinh());
            ps.setString(4, khachHang.getSoDienThoai());
            ps.setString(5, khachHang.getEmail());
            ps.setString(6, khachHang.getDiaChi());
            ps.setDate(7, khachHang.getNgaySinh());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaKhachHang(String maKhachHang) {
        String sql = "DELETE FROM KhachHang WHERE MaKhachHang = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, maKhachHang);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatKhachHang(khachHangDTO khachHang) {
        String sql = "UPDATE KhachHang SET HoTen = ?, GioiTinh = ?, SoDienThoai = ?, " +
                    "Email = ?, DiaChi = ?, NgaySinh = ? WHERE MaKhachHang = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, khachHang.getHoTen());
            ps.setString(2, khachHang.getGioiTinh());
            ps.setString(3, khachHang.getSoDienThoai());
            ps.setString(4, khachHang.getEmail());
            ps.setString(5, khachHang.getDiaChi());
            ps.setDate(6, khachHang.getNgaySinh());
            ps.setString(7, khachHang.getMaKhachHang());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
