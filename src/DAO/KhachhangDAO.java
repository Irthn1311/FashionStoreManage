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
        String sql = "SELECT MaKhachHang, HoTen, Email, Phone, DiaChi, GioiTinh, " +
                "CAST(NgaySinh AS DATE) AS NgaySinh, " +
                "CAST(NgayDangKy AS DATETIME) AS NgayDangKy " +
                "FROM KhachHang";

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
                        null); // Không lấy thông tin tài khoản
                khachHangList.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return khachHangList;
    }

    // Thêm phương thức tìm kiếm khách hàng theo tên
    public List<khachHangDTO> searchKhachHang(String keyword, String searchType) {
        List<khachHangDTO> khachHangList = new ArrayList<>();
        String sql = "SELECT MaKhachHang, HoTen, Email, Phone, DiaChi, GioiTinh, " +
                "CAST(NgaySinh AS DATE) AS NgaySinh, " +
                "CAST(NgayDangKy AS DATETIME) AS NgayDangKy " +
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
                sql += "AND Phone LIKE ? ";
                break;
            default:
                sql += "AND (MaKhachHang LIKE ? OR HoTen LIKE ? OR Email LIKE ? OR Phone LIKE ?) ";
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
                            null); // Không lấy thông tin tài khoản
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
        taiKhoanDTO taiKhoan = khachHang.getTaiKhoan();
        String maTaiKhoan = null;

        // Nếu không có tài khoản, tạo tài khoản mới
        if (taiKhoan == null || taiKhoan.getMaTaiKhoan() == null || taiKhoan.getMaTaiKhoan().trim().isEmpty()) {
            try {
                maTaiKhoan = taiKhoanDAO.themTaiKhoan(
                    khachHang.getHoTen(),
                    khachHang.getNgaySinh(),
                    khachHang.getEmail(),
                    khachHang.getPhone()
                );
                if (maTaiKhoan == null) {
                    throw new RuntimeException("Không thể tạo tài khoản mới!");
                }
                // Tạo đối tượng taiKhoanDTO mới
                taiKhoan = new taiKhoanDTO(maTaiKhoan, null, null, khachHang.getEmail(), 
                                         khachHang.getPhone(), "USER", "ACTIVE");
                khachHang.setTaiKhoan(taiKhoan);
            } catch (Exception e) {
                throw new RuntimeException("Lỗi khi tạo tài khoản: " + e.getMessage());
            }
        }
        // Nếu có tài khoản, kiểm tra tồn tại
        else if (!kiemTraTaiKhoanTonTai(taiKhoan.getMaTaiKhoan())) {
            throw new IllegalArgumentException("Mã tài khoản không tồn tại trong hệ thống!");
        }

        String sql = "INSERT INTO KhachHang (MaKhachHang, HoTen, Email, Phone, DiaChi, GioiTinh, NgaySinh, MaTaiKhoan) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, khachHang.getMaKhachHang());
            ps.setString(2, khachHang.getHoTen());
            ps.setString(3, khachHang.getEmail());
            ps.setString(4, khachHang.getPhone());
            ps.setString(5, khachHang.getDiaChi());
            ps.setString(6, khachHang.getGioiTinh());
            ps.setDate(7, khachHang.getNgaySinh());
            ps.setString(8, taiKhoan.getMaTaiKhoan());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            // Nếu thêm khách hàng thất bại và đã tạo tài khoản mới, xóa tài khoản đã tạo
            if (maTaiKhoan != null) {
                try {
                    xoaTaiKhoan(maTaiKhoan);
                } catch (Exception ex) {
                    // Log lỗi nhưng không throw exception
                    ex.printStackTrace();
                }
            }
            return false;
        }
    }

    private void xoaTaiKhoan(String maTaiKhoan) {
        String sql = "DELETE FROM TaiKhoanNguoiDung WHERE MaTaiKhoan = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maTaiKhoan);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean xoaKhachHang(String maKhachHang) {
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // Kiểm tra xem khách hàng có đơn hàng đang xử lý không
            String sqlCheckDonHang = "SELECT COUNT(*) FROM DonHang " +
                                   "WHERE MaKhachHang = ? AND TrangThai NOT IN ('Đã giao', 'Đã hủy')";
            try (PreparedStatement psCheck = conn.prepareStatement(sqlCheckDonHang)) {
                psCheck.setString(1, maKhachHang);
                ResultSet rs = psCheck.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("Không thể xóa khách hàng này vì có đơn hàng đang được xử lý!");
                }
            }

            // Lấy mã tài khoản trước khi xóa khách hàng
            String maTaiKhoan = null;
            String sqlGetTaiKhoan = "SELECT MaTaiKhoan FROM KhachHang WHERE MaKhachHang = ?";
            try (PreparedStatement psGet = conn.prepareStatement(sqlGetTaiKhoan)) {
                psGet.setString(1, maKhachHang);
                ResultSet rs = psGet.executeQuery();
                if (rs.next()) {
                    maTaiKhoan = rs.getString("MaTaiKhoan");
                }
            }

            // Xóa các đơn hàng đã hoàn thành hoặc đã hủy
            String sqlDeleteDonHang = "DELETE FROM DonHang WHERE MaKhachHang = ? AND TrangThai IN ('Đã giao', 'Đã hủy')";
            try (PreparedStatement psDeleteDH = conn.prepareStatement(sqlDeleteDonHang)) {
                psDeleteDH.setString(1, maKhachHang);
                psDeleteDH.executeUpdate();
            }

            // Xóa khách hàng
            String sqlDeleteKhachHang = "DELETE FROM KhachHang WHERE MaKhachHang = ?";
            int rowsAffected;
            try (PreparedStatement psDelete = conn.prepareStatement(sqlDeleteKhachHang)) {
                psDelete.setString(1, maKhachHang);
                rowsAffected = psDelete.executeUpdate();
            }

            // Nếu xóa khách hàng thành công và có mã tài khoản, xóa tài khoản
            if (rowsAffected > 0 && maTaiKhoan != null) {
                String sqlDeleteTaiKhoan = "DELETE FROM TaiKhoanNguoiDung WHERE MaTaiKhoan = ?";
                try (PreparedStatement psDeleteTK = conn.prepareStatement(sqlDeleteTaiKhoan)) {
                    psDeleteTK.setString(1, maTaiKhoan);
                    psDeleteTK.executeUpdate();
                }
            }

            conn.commit(); // Commit transaction nếu mọi thứ OK
            return rowsAffected > 0;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback nếu có lỗi
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reset lại auto commit
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean capNhatKhachHang(khachHangDTO khachHang) {
        String sql = "UPDATE KhachHang SET HoTen = ?, Email = ?, Phone = ?, DiaChi = ?, " +
                    "GioiTinh = ?, NgaySinh = ? WHERE MaKhachHang = ?";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, khachHang.getHoTen());
            ps.setString(2, khachHang.getEmail());
            ps.setString(3, khachHang.getPhone());
            ps.setString(4, khachHang.getDiaChi());
            ps.setString(5, khachHang.getGioiTinh());
            ps.setDate(6, khachHang.getNgaySinh());
            ps.setString(7, khachHang.getMaKhachHang());

            int rowsAffected = ps.executeUpdate();
            
            // Cập nhật thông tin tài khoản nếu có
            if (rowsAffected > 0 && khachHang.getTaiKhoan() != null) {
                String sqlUpdateTK = "UPDATE TaiKhoanNguoiDung SET Email = ?, SoDienThoai = ? " +
                                   "WHERE MaTaiKhoan = ?";
                try (PreparedStatement psTK = conn.prepareStatement(sqlUpdateTK)) {
                    psTK.setString(1, khachHang.getEmail());
                    psTK.setString(2, khachHang.getPhone());
                    psTK.setString(3, khachHang.getTaiKhoan().getMaTaiKhoan());
                    psTK.executeUpdate();
                }
            }
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
