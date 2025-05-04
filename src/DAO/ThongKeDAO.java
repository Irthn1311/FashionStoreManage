package DAO;

import DTO.sanPhamThongKeDTO;
import DTO.TopKhachHangDTO;
import DTB.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {

    // Thống kê sản phẩm bán chạy nhất
    public List<sanPhamThongKeDTO> getSanPhamThongKe(String tuNgay, String denNgay) throws Exception {
        List<sanPhamThongKeDTO> result = new ArrayList<>();
        String sql = "SELECT sp.MaSanPham, sp.TenSanPham, kh.MaKhachHang, kh.HoTen AS TenKhachHang, " +
                     "SUM(hd.SoLuong) AS SoLuong, SUM(hd.ThanhTien) AS DoanhThu " +
                     "FROM HoaDon hd " +
                     "JOIN SanPham sp ON hd.MaSanPham = sp.MaSanPham " +
                     "JOIN KhachHang kh ON hd.MaKhachHang = kh.MaKhachHang " +
                     "WHERE hd.ThoiGian BETWEEN ? AND ? " +
                     "GROUP BY sp.MaSanPham, sp.TenSanPham, kh.MaKhachHang, kh.HoTen " +
                     "ORDER BY SoLuong DESC";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tuNgay);
            ps.setString(2, denNgay);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sanPhamThongKeDTO sp = new sanPhamThongKeDTO(
                        rs.getString("MaSanPham"),
                        rs.getString("TenSanPham"),
                        rs.getString("MaKhachHang"),
                        rs.getString("TenKhachHang"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("DoanhThu")
                    );
                    result.add(sp);
                    System.out.println("SanPhamThongKe: " + sp.getMaSanPham() + " - " + sp.getTenSanPham() + " - " + sp.getSoLuong());
                }
            }
        } catch (SQLException e) {
            throw new Exception("Lỗi khi thống kê sản phẩm: " + e.getMessage(), e);
        }
        return result;
    }

    // Thống kê top khách hàng thường xuyên
    public List<TopKhachHangDTO> getTopKhachHang(String tuNgay, String denNgay) throws Exception {
        List<TopKhachHangDTO> result = new ArrayList<>();
        String sql = "SELECT kh.MaKhachHang, kh.HoTen AS TenKhachHang, COUNT(hd.MaHoaDon) AS SoGiaoDich, " +
                     "SUM(hd.ThanhTien) AS TongDoanhThu, MAX(hd.ThoiGian) AS LastTransaction " +
                     "FROM HoaDon hd " +
                     "JOIN KhachHang kh ON hd.MaKhachHang = kh.MaKhachHang " +
                     "WHERE hd.ThoiGian BETWEEN ? AND ? " +
                     "GROUP BY kh.MaKhachHang, kh.HoTen " +
                     "ORDER BY SoGiaoDich DESC, TongDoanhThu DESC";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tuNgay);
            ps.setString(2, denNgay);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TopKhachHangDTO kh = new TopKhachHangDTO(
                        rs.getString("MaKhachHang"),
                        rs.getString("TenKhachHang"),
                        rs.getInt("SoGiaoDich"),
                        rs.getDouble("TongDoanhThu"),
                        rs.getString("LastTransaction")
                    );
                    result.add(kh);
                    System.out.println("TopKhachHang: " + kh.getMaKhachHang() + " - " + kh.getTenKhachHang() + " - " + kh.getSoLuongGiaoDich() + " - " + kh.getLastTransactionDate());
                }
            }
        } catch (SQLException e) {
            throw new Exception("Lỗi khi thống kê top khách hàng: " + e.getMessage(), e);
        }
        return result;
    }

    // Thống kê tổng doanh thu theo tháng
    public double getDoanhThuTheoThang(int year, int month) throws Exception {
        String sql = "SELECT SUM(ThanhTien) AS TongDoanhThu " +
                     "FROM HoaDon " +
                     "WHERE YEAR(ThoiGian) = ? AND MONTH(ThoiGian) = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, year);
            ps.setInt(2, month);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double doanhThu = rs.getDouble("TongDoanhThu");
                    if (rs.wasNull()) {
                        doanhThu = 0.0;
                    }
                    System.out.println("Doanh thu tháng " + month + "/" + year + ": " + doanhThu);
                    return doanhThu;
                }
                System.out.println("Không có dữ liệu doanh thu cho tháng " + month + "/" + year);
                return 0.0;
            }
        } catch (SQLException e) {
            throw new Exception("Lỗi khi tính doanh thu theo tháng: " + e.getMessage(), e);
        }
    }

    // Thống kê doanh thu theo ngày
    public double getDoanhThuTheoNgay(String ngay) throws Exception {
        String sql = "SELECT SUM(ThanhTien) AS TongDoanhThu " +
                     "FROM HoaDon " +
                     "WHERE CONVERT(DATE, ThoiGian) = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ngay);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double doanhThu = rs.getDouble("TongDoanhThu");
                    if (rs.wasNull()) {
                        doanhThu = 0.0;
                    }
                    System.out.println("Doanh thu ngày " + ngay + ": " + doanhThu);
                    return doanhThu;
                }
                System.out.println("Không có dữ liệu doanh thu cho ngày " + ngay);
                return 0.0;
            }
        } catch (SQLException e) {
            throw new Exception("Lỗi khi tính doanh thu theo ngày: " + e.getMessage(), e);
        }
    }

    // Thống kê doanh thu theo năm
    public double getDoanhThuTheoNam(int year) throws Exception {
        String sql = "SELECT SUM(ThanhTien) AS TongDoanhThu " +
                     "FROM HoaDon " +
                     "WHERE YEAR(ThoiGian) = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, year);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double doanhThu = rs.getDouble("TongDoanhThu");
                    if (rs.wasNull()) {
                        doanhThu = 0.0;
                    }
                    System.out.println("Doanh thu năm " + year + ": " + doanhThu);
                    return doanhThu;
                }
                System.out.println("Không có dữ liệu doanh thu cho năm " + year);
                return 0.0;
            }
        } catch (SQLException e) {
            throw new Exception("Lỗi khi tính doanh thu theo năm: " + e.getMessage(), e);
        }
    }
}