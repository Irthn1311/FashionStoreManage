package DAO;

import DTO.sanPhamDTO;
import DTB.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoaiSanPhamDAO {

    public LoaiSanPhamDAO() {
    }

    private int getNextSanPhamNumber() {
        String sql = "SELECT MAX(CAST(SUBSTRING(MaSanPham, 3) AS UNSIGNED)) FROM SanPham WHERE MaSanPham LIKE 'SP%'";
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
            System.err.println("Lỗi khi lấy mã sản phẩm lớn nhất: " + e.getMessage());
            e.printStackTrace();
        }
        return 1;
    }

    public String generateMaSanPham() {
        int nextNumber = getNextSanPhamNumber();
        String maSanPham;
        do {
            maSanPham = String.format("SP%04d", nextNumber);
            nextNumber++;
        } while (isMaSanPhamExists(maSanPham));
        return maSanPham;
    }

    public boolean isMaNhaCungCapExists(String maNhaCungCap) {
        String sql = "SELECT COUNT(*) FROM NhaCungCap WHERE MaNhaCungCap = ?";
        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return false;
            }
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, maNhaCungCap);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra mã nhà cung cấp: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<sanPhamDTO> getAllSanPham() {
        List<sanPhamDTO> sanPhamList = new ArrayList<>();
        String sql = "SELECT * FROM SanPham";

        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return sanPhamList;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sanPhamDTO sp = new sanPhamDTO(
                            rs.getString("MaSanPham"),
                            rs.getString("TenSanPham"),
                            rs.getString("MaNhaCungCap"),
                            rs.getString("MaDanhMuc"),
                            rs.getString("MauSac"),
                            rs.getString("Size"),
                            rs.getInt("SoLuongTonKho"),
                            rs.getDouble("GiaBan"),
                            rs.getString("ImgURL"),
                            rs.getString("TrangThai"));
                    sanPhamList.add(sp);
                }
                System.out.println("Tải thành công " + sanPhamList.size() + " sản phẩm.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }

        return sanPhamList;
    }

    public sanPhamDTO getLoaiSanPhamByMa(String maSanPham) {
        if (maSanPham == null || maSanPham.trim().isEmpty()) {
            System.out.println("Mã sản phẩm không hợp lệ: " + maSanPham);
            return null;
        }

        String sql = "SELECT * FROM SanPham WHERE MaSanPham = ?";

        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return null;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, maSanPham);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        sanPhamDTO sp = new sanPhamDTO(
                                rs.getString("MaSanPham"),
                                rs.getString("TenSanPham"),
                                rs.getString("MaNhaCungCap"),
                                rs.getString("MaDanhMuc"),
                                rs.getString("MauSac"),
                                rs.getString("Size"),
                                rs.getInt("SoLuongTonKho"),
                                rs.getDouble("GiaBan"),
                                rs.getString("ImgURL"),
                                rs.getString("TrangThai"));
                        return sp;
                    } else {
                        System.out.println("Không tìm thấy sản phẩm với mã: " + maSanPham);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy sản phẩm theo mã: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public List<sanPhamDTO> searchLoaiSanPham(String keyword, String searchType) {
        List<sanPhamDTO> sanPhamList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM SanPham WHERE 1=1 ");

        switch (searchType) {
            case "Mã sản phẩm":
                sql.append("AND MaSanPham LIKE ? ");
                break;
            case "Tên sản phẩm":
                sql.append("AND TenSanPham LIKE ? ");
                break;
            case "Mã nhà cung cấp":
                sql.append("AND MaNhaCungCap LIKE ? ");
                break;
            case "Loại sản phẩm":
                sql.append("AND MaDanhMuc LIKE ? ");
                break;
            case "Màu sắc":
                sql.append("AND MauSac LIKE ? ");
                break;
            case "Size":
                sql.append("AND Size LIKE ? ");
                break;
            default:
                sql.append(
                        "AND (MaSanPham LIKE ? OR TenSanPham LIKE ? OR MaNhaCungCap LIKE ? OR MaDanhMuc LIKE ? OR MauSac LIKE ? OR Size LIKE ?) ");
                break;
        }

        String likePattern = "%" + keyword + "%";

        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return sanPhamList;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                if ("Tất cả".equals(searchType)) {
                    for (int i = 1; i <= 6; i++) {
                        ps.setString(i, likePattern);
                    }
                } else {
                    ps.setString(1, likePattern);
                }

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        sanPhamDTO sp = new sanPhamDTO(
                                rs.getString("MaSanPham"),
                                rs.getString("TenSanPham"),
                                rs.getString("MaNhaCungCap"),
                                rs.getString("MaDanhMuc"),
                                rs.getString("MauSac"),
                                rs.getString("Size"),
                                rs.getInt("SoLuongTonKho"),
                                rs.getDouble("GiaBan"),
                                rs.getString("ImgURL"),
                                rs.getString("TrangThai"));
                        sanPhamList.add(sp);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }

        return sanPhamList;
    }

    public boolean themLoaiSanPham(sanPhamDTO sanPham) {
        String maSanPham = generateMaSanPham();
        sanPham.setMaSanPham(maSanPham);

        String sql = "INSERT INTO SanPham (MaSanPham, TenSanPham, MaNhaCungCap, MaDanhMuc, MauSac, Size, SoLuongTonKho, GiaBan, ImgURL, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, sanPham.getMaSanPham());
                ps.setString(2, sanPham.getTenSanPham());
                ps.setString(3, sanPham.getMaNhaCungCap());
                ps.setString(4, sanPham.getMaDanhMuc());
                ps.setString(5, sanPham.getMauSac());
                ps.setString(6, sanPham.getSize());
                ps.setInt(7, sanPham.getSoLuongTonKho());
                ps.setDouble(8, sanPham.getGiaBan());
                ps.setString(9, sanPham.getImgURL());
                ps.setString(10, sanPham.getTrangThai());

                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private boolean kiemTraMaSanPhamTonTai(String maSanPham) {
        String sql = "SELECT COUNT(*) FROM SanPham WHERE MaSanPham = ?";

        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, maSanPham);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra mã sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatLoaiSanPham(sanPhamDTO sanPham) {
        String sql = "UPDATE SanPham SET TenSanPham = ?, MaNhaCungCap = ?, MaDanhMuc = ?, MauSac = ?, Size = ?, SoLuongTonKho = ?, GiaBan = ?, ImgURL = ?, TrangThai = ? WHERE MaSanPham = ?";

        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, sanPham.getTenSanPham());
                ps.setString(2, sanPham.getMaNhaCungCap());
                ps.setString(3, sanPham.getMaDanhMuc());
                ps.setString(4, sanPham.getMauSac());
                ps.setString(5, sanPham.getSize());
                ps.setInt(6, sanPham.getSoLuongTonKho());
                ps.setDouble(7, sanPham.getGiaBan());
                ps.setString(8, sanPham.getImgURL());
                ps.setString(9, sanPham.getTrangThai());
                ps.setString(10, sanPham.getMaSanPham());

                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaLoaiSanPham(String maSanPham) {
        String sqlDeleteHoaDon = "DELETE FROM HoaDon WHERE MaSanPham = ?";
        String sqlDeleteRef = "DELETE FROM NhaCungCap_SanPham WHERE MaSanPham = ?";
        String sqlDeleteSanPham = "DELETE FROM SanPham WHERE MaSanPham = ?";

        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return false;
            }

            conn.setAutoCommit(false);

            // Xóa các bản ghi liên quan trong bảng HoaDon
            try (PreparedStatement psDeleteHoaDon = conn.prepareStatement(sqlDeleteHoaDon)) {
                psDeleteHoaDon.setString(1, maSanPham);
                psDeleteHoaDon.executeUpdate();
            }

            // Xóa các bản ghi liên quan trong bảng NhaCungCap_SanPham
            try (PreparedStatement psDeleteRef = conn.prepareStatement(sqlDeleteRef)) {
                psDeleteRef.setString(1, maSanPham);
                psDeleteRef.executeUpdate();
            }

            // Xóa sản phẩm
            try (PreparedStatement psDeleteSanPham = conn.prepareStatement(sqlDeleteSanPham)) {
                psDeleteSanPham.setString(1, maSanPham);
                int rowsAffected = psDeleteSanPham.executeUpdate();
                
                if (rowsAffected > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa sản phẩm: " + e.getMessage());
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                System.err.println("Lỗi khi rollback transaction: " + rollbackEx.getMessage());
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException closeEx) {
                System.err.println("Lỗi khi đóng kết nối: " + closeEx.getMessage());
                closeEx.printStackTrace();
            }
        }
    }

    public List<sanPhamDTO> getLoaiSanPhamByDanhMuc(String maDanhMuc) {
        List<sanPhamDTO> sanPhamList = new ArrayList<>();
        String sql = "SELECT * FROM SanPham WHERE MaDanhMuc = ?";

        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return sanPhamList;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, maDanhMuc);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        sanPhamDTO sp = new sanPhamDTO(
                                rs.getString("MaSanPham"),
                                rs.getString("TenSanPham"),
                                rs.getString("MaNhaCungCap"),
                                rs.getString("MaDanhMuc"),
                                rs.getString("MauSac"),
                                rs.getString("Size"),
                                rs.getInt("SoLuongTonKho"),
                                rs.getDouble("GiaBan"),
                                rs.getString("ImgURL"),
                                rs.getString("TrangThai"));
                        sanPhamList.add(sp);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy sản phẩm theo danh mục: " + e.getMessage());
            e.printStackTrace();
        }

        return sanPhamList;
    }

    public List<sanPhamDTO> getLoaiSanPhamByNhaCungCap(String maNhaCungCap) {
        List<sanPhamDTO> sanPhamList = new ArrayList<>();
        String sql = "SELECT * FROM SanPham WHERE MaNhaCungCap = ?";

        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return sanPhamList;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, maNhaCungCap);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        sanPhamDTO sp = new sanPhamDTO(
                                rs.getString("MaSanPham"),
                                rs.getString("TenSanPham"),
                                rs.getString("MaNhaCungCap"),
                                rs.getString("MaDanhMuc"),
                                rs.getString("MauSac"),
                                rs.getString("Size"),
                                rs.getInt("SoLuongTonKho"),
                                rs.getDouble("GiaBan"),
                                rs.getString("ImgURL"),
                                rs.getString("TrangThai"));
                        sanPhamList.add(sp);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy sản phẩm theo nhà cung cấp: " + e.getMessage());
            e.printStackTrace();
        }

        return sanPhamList;
    }

    public boolean capNhatSoLuong(String maSanPham, int soLuongMoi) {
        String sql = "UPDATE SanPham SET SoLuongTonKho = ? WHERE MaSanPham = ?";

        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, soLuongMoi);
                ps.setString(2, maSanPham);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật số lượng tồn kho: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatTrangThai(String maSanPham, String trangThai) {
        String sql = "UPDATE SanPham SET TrangThai = ? WHERE MaSanPham = ?";

        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, trangThai);
                ps.setString(2, maSanPham);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật trạng thái sản phẩm: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean isMaSanPhamExists(String maSanPham) {
        String sql = "SELECT COUNT(*) FROM SanPham WHERE MaSanPham = ?";
        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return false;
            }
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, maSanPham);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra mã sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<sanPhamDTO> getLoaiSanPhamAvailable() {
        List<sanPhamDTO> sanPhamList = new ArrayList<>();
        String sql = "SELECT * FROM SanPham WHERE SoLuongTonKho > 0 AND TrangThai = 'ACTIVE'";

        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return sanPhamList;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sanPhamDTO sp = new sanPhamDTO(
                            rs.getString("MaSanPham"),
                            rs.getString("TenSanPham"),
                            rs.getString("MaNhaCungCap"),
                            rs.getString("MaDanhMuc"),
                            rs.getString("MauSac"),
                            rs.getString("Size"),
                            rs.getInt("SoLuongTonKho"),
                            rs.getDouble("GiaBan"),
                            rs.getString("ImgURL"),
                            rs.getString("TrangThai"));
                    sanPhamList.add(sp);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy sản phẩm có sẵn: " + e.getMessage());
            e.printStackTrace();
        }

        return sanPhamList;
    }
}