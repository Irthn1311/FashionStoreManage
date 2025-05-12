package DAO;

import DTO.hoaDonDTO;
import DTO.sanPhamThongKeDTO;
import DTB.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {

    // Lấy tất cả mã sản phẩm
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
            System.err.println("Lỗi khi lấy danh sách mã sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
        return maSanPhamList;
    }

    // Kiểm tra xem MaSanPham có tồn tại hay không
    public boolean isMaSanPhamExists(String maSanPham) {
        String sql = "SELECT COUNT(*) FROM SanPham WHERE MaSanPham = ?";

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSanPham);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra mã sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Lấy tất cả hóa đơn
    public List<hoaDonDTO> getAllHoaDon() {
        List<hoaDonDTO> hoaDonList = new ArrayList<>();
        String sql = "SELECT hd.MaHoaDon, hd.MaSanPham, hd.TenSanPham, hd.KichCo, hd.MauSac, hd.SoLuong, " +
                "hd.MaKhachHang, kh.HoTen as TenKhachHang, hd.ThanhTien, hd.DonGia, " +
                "hd.HinhThucThanhToan, CONVERT(datetime, hd.ThoiGian, 120) as ThoiGian, hd.TrangThai " +
                "FROM HoaDon hd " +
                "LEFT JOIN KhachHang kh ON hd.MaKhachHang = kh.MaKhachHang";

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                hoaDonDTO hd = new hoaDonDTO(
                        rs.getString("MaHoaDon"),
                        rs.getString("MaSanPham"),
                        rs.getString("TenSanPham"),
                        rs.getString("KichCo"),
                        rs.getString("MauSac"),
                        rs.getInt("SoLuong"),
                        rs.getString("MaKhachHang"),
                        rs.getString("TenKhachHang"),
                        rs.getDouble("ThanhTien"),
                        rs.getDouble("DonGia"),
                        rs.getString("HinhThucThanhToan"),
                        rs.getTimestamp("ThoiGian"),
                        rs.getString("TrangThai"));
                hoaDonList.add(hd);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }

        return hoaDonList;
    }

    // Kiểm tra xem MaHoaDon có tồn tại hay không
    public boolean isMaHoaDonExists(String maHoaDon) {
        String sql = "SELECT COUNT(*) FROM HoaDon WHERE MaHoaDon = ?";

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHoaDon);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra mã hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Lấy hóa đơn theo MaHoaDon
    public hoaDonDTO getHoaDonByMa(String maHoaDon) {
        String sql = "SELECT hd.MaHoaDon, hd.MaSanPham, hd.TenSanPham, hd.KichCo, hd.MauSac, hd.SoLuong, " +
                "hd.MaKhachHang, kh.HoTen as TenKhachHang, hd.ThanhTien, hd.DonGia, " +
                "hd.HinhThucThanhToan, CONVERT(datetime, hd.ThoiGian, 120) as ThoiGian, hd.TrangThai " +
                "FROM HoaDon hd " +
                "LEFT JOIN KhachHang kh ON hd.MaKhachHang = kh.MaKhachHang " +
                "WHERE hd.MaHoaDon = ?";
        hoaDonDTO hd = null;

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHoaDon);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    hd = new hoaDonDTO(
                            rs.getString("MaHoaDon"),
                            rs.getString("MaSanPham"),
                            rs.getString("TenSanPham"),
                            rs.getString("KichCo"),
                            rs.getString("MauSac"),
                            rs.getInt("SoLuong"),
                            rs.getString("MaKhachHang"),
                            rs.getString("TenKhachHang"),
                            rs.getDouble("ThanhTien"),
                            rs.getDouble("DonGia"),
                            rs.getString("HinhThucThanhToan"),
                            rs.getTimestamp("ThoiGian"),
                            rs.getString("TrangThai"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy hóa đơn theo mã: " + e.getMessage());
            e.printStackTrace();
        }
        return hd;
    }

    // Tìm kiếm hóa đơn
    public List<hoaDonDTO> searchHoaDon(String keyword) {
        List<hoaDonDTO> hoaDonList = new ArrayList<>();
        String sql = "SELECT MaHoaDon, MaSanPham, TenSanPham, KichCo, MauSac, SoLuong, MaKhachHang, TenKhachHang, ThanhTien, DonGia, HinhThucThanhToan, ThoiGian, TrangThai "
                +
                "FROM HoaDon WHERE MaHoaDon LIKE ? OR TenKhachHang LIKE ? OR MaSanPham LIKE ? OR TenSanPham LIKE ?";

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            ps.setString(4, searchPattern);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    hoaDonDTO hd = new hoaDonDTO(
                            rs.getString("MaHoaDon"),
                            rs.getString("MaSanPham"),
                            rs.getString("TenSanPham"),
                            rs.getString("KichCo"),
                            rs.getString("MauSac"),
                            rs.getInt("SoLuong"),
                            rs.getString("MaKhachHang"),
                            rs.getString("TenKhachHang"),
                            rs.getDouble("ThanhTien"),
                            rs.getDouble("DonGia"),
                            rs.getString("HinhThucThanhToan"),
                            rs.getTimestamp("ThoiGian"),
                            rs.getString("TrangThai"));
                    hoaDonList.add(hd);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
        return hoaDonList;
    }

    // Thêm hóa đơn
    public boolean addHoaDon(hoaDonDTO hd) {
        // Kiểm tra xem MaHoaDon đã tồn tại chưa
        if (isMaHoaDonExists(hd.getMaHoaDon())) {
            throw new RuntimeException("Mã hóa đơn " + hd.getMaHoaDon() + " đã tồn tại!");
        }

        String sql = "INSERT INTO HoaDon (MaHoaDon, MaSanPham, TenSanPham, KichCo, MauSac, SoLuong, MaKhachHang, TenKhachHang, ThanhTien, DonGia, HinhThucThanhToan, ThoiGian, TrangThai) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectDB.getConnection();
            conn.setAutoCommit(false); // Bắt đầu giao dịch

            ps = conn.prepareStatement(sql);
            ps.setString(1, hd.getMaHoaDon());
            ps.setString(2, hd.getMaSanPham());
            ps.setString(3, hd.getTenSanPham());
            ps.setString(4, hd.getKichCo());
            ps.setString(5, hd.getMauSac());
            ps.setInt(6, hd.getSoLuong());
            ps.setString(7, hd.getMaKhachHang());
            ps.setString(8, hd.getTenKhachHang());
            ps.setDouble(9, hd.getThanhTien());
            ps.setDouble(10, hd.getDonGia());
            ps.setString(11, hd.getHinhThucThanhToan());
            ps.setTimestamp(12, hd.getThoiGian());
            ps.setString(13, hd.getTrangThai());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                conn.commit(); // Xác nhận giao dịch
                return true;
            } else {
                conn.rollback(); // Hủy giao dịch nếu không thành công
                return false;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Hủy giao dịch nếu có lỗi
                } catch (SQLException ex) {
                    System.err.println("Lỗi khi rollback giao dịch: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
            System.err.println("Lỗi khi thêm hóa đơn: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thêm hóa đơn: " + e.getMessage());
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Cập nhật hóa đơn
    public boolean updateHoaDon(hoaDonDTO hd) {
        String sql = "UPDATE HoaDon SET MaSanPham = ?, TenSanPham = ?, KichCo = ?, MauSac = ?, SoLuong = ?, MaKhachHang = ?, TenKhachHang = ?, ThanhTien = ?, DonGia = ?, HinhThucThanhToan = ?, ThoiGian = ?, TrangThai = ? "
                +
                "WHERE MaHoaDon = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectDB.getConnection();
            conn.setAutoCommit(false); // Bắt đầu giao dịch

            ps = conn.prepareStatement(sql);
            ps.setString(1, hd.getMaSanPham());
            ps.setString(2, hd.getTenSanPham());
            ps.setString(3, hd.getKichCo());
            ps.setString(4, hd.getMauSac());
            ps.setInt(5, hd.getSoLuong());
            ps.setString(6, hd.getMaKhachHang());
            ps.setString(7, hd.getTenKhachHang());
            ps.setDouble(8, hd.getThanhTien());
            ps.setDouble(9, hd.getDonGia());
            ps.setString(10, hd.getHinhThucThanhToan());
            ps.setTimestamp(11, hd.getThoiGian());
            ps.setString(12, hd.getTrangThai());
            ps.setString(13, hd.getMaHoaDon());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                conn.commit(); // Xác nhận giao dịch
                return true;
            } else {
                conn.rollback(); // Hủy giao dịch nếu không thành công
                return false;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Hủy giao dịch nếu có lỗi
                } catch (SQLException ex) {
                    System.err.println("Lỗi khi rollback giao dịch: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
            System.err.println("Lỗi khi cập nhật hóa đơn: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi cập nhật hóa đơn: " + e.getMessage());
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Xóa hóa đơn
    public boolean deleteHoaDon(String maHoaDon) {
        String sql = "DELETE FROM HoaDon WHERE MaHoaDon = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectDB.getConnection();
            conn.setAutoCommit(false); // Bắt đầu giao dịch

            ps = conn.prepareStatement(sql);
            ps.setString(1, maHoaDon);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                conn.commit(); // Xác nhận giao dịch
                return true;
            } else {
                conn.rollback(); // Hủy giao dịch nếu không thành công
                return false;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Hủy giao dịch nếu có lỗi
                } catch (SQLException ex) {
                    System.err.println("Lỗi khi rollback giao dịch: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
            System.err.println("Lỗi khi xóa hóa đơn: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi xóa hóa đơn: " + e.getMessage());
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public List<sanPhamThongKeDTO> getSanPhamDoanhThuCao(Timestamp startDate, Timestamp endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Thời gian bắt đầu và kết thúc không được null");
        }

        List<sanPhamThongKeDTO> result = new ArrayList<>();
        String sql = "SELECT h.MaSanPham, s.TenSanPham, h.MaKhachHang, k.HoTen as TenKhachHang, " +
                "SUM(h.SoLuong) as SoLuong, SUM(h.ThanhTien) as DoanhThu " +
                "FROM HoaDon h " +
                "JOIN SanPham s ON h.MaSanPham = s.MaSanPham " +
                "JOIN KhachHang k ON h.MaKhachHang = k.MaKhachHang " +
                "WHERE h.ThoiGian BETWEEN ? AND ? " +
                "GROUP BY h.MaSanPham, s.TenSanPham, h.MaKhachHang, k.HoTen " +
                "ORDER BY DoanhThu DESC";

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, startDate);
            ps.setTimestamp(2, endDate);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sanPhamThongKeDTO dto = new sanPhamThongKeDTO(
                            rs.getString("MaSanPham"),
                            rs.getString("TenSanPham"),
                            rs.getString("MaKhachHang"),
                            rs.getString("TenKhachHang"),
                            rs.getInt("SoLuong"),
                            rs.getDouble("DoanhThu"));
                    result.add(dto);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách sản phẩm doanh thu cao: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public List<sanPhamThongKeDTO> getSanPhamBanChayNhat(Timestamp startDate, Timestamp endDate) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSanPhamBanChayNhat'");
    }

    public int getSoLuongHoaDon() {
        String sql = "SELECT COUNT(*) FROM HoaDon";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}