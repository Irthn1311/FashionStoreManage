package DAO;

import DTO.khuyenMaiDTO;
import DTB.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KhuyenMaiDAO {

    public KhuyenMaiDAO() {
    }

    private boolean isMaKhuyenMaiExists(String maKhuyenMai) {
        String sql = "SELECT COUNT(*) FROM KhuyenMai WHERE MaKhuyenMai = ?";
        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return false;
            }
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, maKhuyenMai);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra mã khuyến mãi: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private int getNextKhuyenMaiNumber() {
        String sql = "SELECT MAX(CAST(SUBSTRING(MaKhuyenMai, 3) AS UNSIGNED)) FROM KhuyenMai WHERE MaKhuyenMai LIKE 'KM%'";
        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return 1;
            }
            try (PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) + 1;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy mã khuyến mãi lớn nhất: " + e.getMessage());
            e.printStackTrace();
        }
        return 1;
    }

    public String generateMaKhuyenMai() {
        int nextNumber = getNextKhuyenMaiNumber();
        String maKhuyenMai;
        do {
            maKhuyenMai = String.format("KM%04d", nextNumber);
            nextNumber++;
        } while (isMaKhuyenMaiExists(maKhuyenMai));
        return maKhuyenMai;
    }

    private String determineStatus(Date ngayBatDau, Date ngayKetThuc) {
        Date currentDate = new Date();
        if (currentDate.before(ngayBatDau)) {
            return "Chưa bắt đầu";
        } else if (!currentDate.before(ngayBatDau) && !currentDate.after(ngayKetThuc)) {
            return "Hoạt động";
        } else {
            return "Hết hạn";
        }
    }

    private void updateProductPrice(String maSanPham, double giaBan) {
        String sql = "UPDATE SanPham SET GiaBan = ? WHERE MaSanPham = ?";
        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return;
            }
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setDouble(1, giaBan);
                ps.setString(2, maSanPham);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Cập nhật giá sản phẩm " + maSanPham + " thành " + giaBan);
                } else {
                    System.err.println("Không tìm thấy sản phẩm " + maSanPham + " để cập nhật giá.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật giá sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean hasActivePromotionForProduct(String maSanPham) {
        String sql = "SELECT NgayBatDau, NgayKetThuc FROM KhuyenMai WHERE MaSanPham = ?";
        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return false;
            }
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, maSanPham);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Date ngayBatDau = rs.getDate("NgayBatDau");
                        Date ngayKetThuc = rs.getDate("NgayKetThuc");
                        String trangThai = determineStatus(ngayBatDau, ngayKetThuc);
                        if (!trangThai.equals("Hết hạn")) {
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra khuyến mãi cho sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<khuyenMaiDTO> getAllKhuyenMai() {
        List<khuyenMaiDTO> khuyenMaiList = new ArrayList<>();
        String sql = "SELECT MaKhuyenMai, MaSanPham, TenSanPham, TenChuongTrinh, GiamGia, NgayBatDau, NgayKetThuc, GiaCu, GiaMoi FROM KhuyenMai";

        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return khuyenMaiList;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Date ngayBatDau = rs.getDate("NgayBatDau");
                    Date ngayKetThuc = rs.getDate("NgayKetThuc");
                    String trangThai = determineStatus(ngayBatDau, ngayKetThuc);

                    khuyenMaiDTO km = new khuyenMaiDTO(
                            rs.getString("MaKhuyenMai"),
                            rs.getString("MaSanPham"),
                            rs.getString("TenSanPham"),
                            rs.getString("TenChuongTrinh"),
                            rs.getDouble("GiamGia"),
                            ngayBatDau,
                            ngayKetThuc,
                            rs.getDouble("GiaCu"),
                            rs.getDouble("GiaMoi"),
                            trangThai);

                    if ("Hoạt động".equals(trangThai)) {
                        updateProductPrice(km.getMaSanPham(), km.getGiaMoi());
                    } else if ("Hết hạn".equals(trangThai)) {
                        updateProductPrice(km.getMaSanPham(), km.getGiaCu());
                    }

                    khuyenMaiList.add(km);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách khuyến mãi: " + e.getMessage());
            e.printStackTrace();
        }

        return khuyenMaiList;
    }

    public List<khuyenMaiDTO> searchKhuyenMai(String keyword) {
        List<khuyenMaiDTO> khuyenMaiList = new ArrayList<>();
        String sql = "SELECT MaKhuyenMai, MaSanPham, TenSanPham, TenChuongTrinh, GiamGia, NgayBatDau, NgayKetThuc, GiaCu, GiaMoi "
                + "FROM KhuyenMai WHERE MaKhuyenMai LIKE ? OR TenChuongTrinh LIKE ? OR MaSanPham LIKE ? OR TenSanPham LIKE ?";

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
