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

        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return khuyenMaiList;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                String searchPattern = "%" + keyword + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
                ps.setString(4, searchPattern);

                try (ResultSet rs = ps.executeQuery()) {
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
                        khuyenMaiList.add(km);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm khuyến mãi: " + e.getMessage());
            e.printStackTrace();
        }
        return khuyenMaiList;
    }

    public boolean updateKhuyenMai(khuyenMaiDTO km) {
        String sql = "UPDATE KhuyenMai SET MaSanPham = ?, TenSanPham = ?, TenChuongTrinh = ?, GiamGia = ?, NgayBatDau = ?, NgayKetThuc = ?, GiaCu = ?, GiaMoi = ?, TrangThai = ? WHERE MaKhuyenMai = ?";
        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, km.getMaSanPham());
                ps.setString(2, km.getTenSanPham());
                ps.setString(3, km.getTenChuongTrinh());
                ps.setDouble(4, km.getGiamGia());
                ps.setDate(5, new java.sql.Date(km.getNgayBatDau().getTime()));
                ps.setDate(6, new java.sql.Date(km.getNgayKetThuc().getTime()));
                ps.setDouble(7, km.getGiaCu());
                ps.setDouble(8, km.getGiaMoi());
                ps.setString(9, determineStatus(km.getNgayBatDau(), km.getNgayKetThuc()));
                ps.setString(10, km.getMaKhuyenMai());

                boolean success = ps.executeUpdate() > 0;
                if (success) {
                    String trangThai = determineStatus(km.getNgayBatDau(), km.getNgayKetThuc());
                    if ("Hoạt động".equals(trangThai)) {
                        updateProductPrice(km.getMaSanPham(), km.getGiaMoi());
                    } else if ("Hết hạn".equals(trangThai)) {
                        updateProductPrice(km.getMaSanPham(), km.getGiaCu());
                    }
                }
                return success;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật khuyến mãi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteKhuyenMai(String maKhuyenMai) {
        String sqlSelect = "SELECT MaSanPham, GiaCu FROM KhuyenMai WHERE MaKhuyenMai = ?";
        String maSanPham = null;
        double giaCu = 0;

        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return false;
            }

            try (PreparedStatement psSelect = conn.prepareStatement(sqlSelect)) {
                psSelect.setString(1, maKhuyenMai);
                try (ResultSet rs = psSelect.executeQuery()) {
                    if (rs.next()) {
                        maSanPham = rs.getString("MaSanPham");
                        giaCu = rs.getDouble("GiaCu");
                    }
                }
            }

            String sqlDelete = "DELETE FROM KhuyenMai WHERE MaKhuyenMai = ?";
            try (PreparedStatement psDelete = conn.prepareStatement(sqlDelete)) {
                psDelete.setString(1, maKhuyenMai);
                boolean success = psDelete.executeUpdate() > 0;
                if (success && maSanPham != null) {
                    updateProductPrice(maSanPham, giaCu);
                }
                return success;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa khuyến mãi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean addKhuyenMai(khuyenMaiDTO km) {
        if (hasActivePromotionForProduct(km.getMaSanPham())) {
            System.err.println("Sản phẩm " + km.getMaSanPham() + " đã có khuyến mãi đang hoạt động hoặc chưa bắt đầu.");
            return false;
        }

        String sql = "INSERT INTO KhuyenMai (MaKhuyenMai, MaSanPham, TenSanPham, TenChuongTrinh, GiamGia, NgayBatDau, NgayKetThuc, GiaCu, GiaMoi, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, km.getMaKhuyenMai());
                ps.setString(2, km.getMaSanPham());
                ps.setString(3, km.getTenSanPham());
                ps.setString(4, km.getTenChuongTrinh());
                ps.setDouble(5, km.getGiamGia());
                ps.setDate(6, new java.sql.Date(km.getNgayBatDau().getTime()));
                ps.setDate(7, new java.sql.Date(km.getNgayKetThuc().getTime()));
                ps.setDouble(8, km.getGiaCu());
                ps.setDouble(9, km.getGiaMoi());
                ps.setString(10, determineStatus(km.getNgayBatDau(), km.getNgayKetThuc()));

                boolean success = ps.executeUpdate() > 0;
                if (success) {
                    String trangThai = determineStatus(km.getNgayBatDau(), km.getNgayKetThuc());
                    if ("Hoạt động".equals(trangThai)) {
                        updateProductPrice(km.getMaSanPham(), km.getGiaMoi());
                    }
                }
                return success;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm khuyến mãi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}