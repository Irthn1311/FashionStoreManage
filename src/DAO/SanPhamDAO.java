package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DTB.ConnectDB;
import DTO.sanPhamDTO;

public class SanPhamDAO {

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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sanPhamList;
    }

    // Tìm kiếm sản phẩm theo loại tìm kiếm, từ khóa và khoảng giá/số lượng
    public List<sanPhamDTO> searchSanPham(String loaiTimKiem, String tuKhoa, Double giaTu, Double giaDen, Integer slTu,
            Integer slDen) {
        List<sanPhamDTO> sanPhamList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM SanPham WHERE 1=1");

        // Xây dựng câu truy vấn động dựa trên các điều kiện
        List<Object> params = new ArrayList<>();

        // Điều kiện tìm kiếm theo từ khóa
        if (tuKhoa != null && !tuKhoa.isEmpty()) {
            switch (loaiTimKiem) {
                case "Mã sản phẩm":
                    sql.append(" AND MaSanPham LIKE ?");
                    params.add("%" + tuKhoa + "%");
                    break;
                case "Tên sản phẩm":
                    sql.append(" AND TenSanPham LIKE ?");
                    params.add("%" + tuKhoa + "%");
                    break;
                case "Nhà cung cấp":
                    sql.append(" AND MaNhaCungCap LIKE ?");
                    params.add("%" + tuKhoa + "%");
                    break;
                case "Danh mục":
                    sql.append(" AND MaDanhMuc LIKE ?");
                    params.add("%" + tuKhoa + "%");
                    break;
                case "Màu sắc":
                    sql.append(" AND MauSac LIKE ?");
                    params.add("%" + tuKhoa + "%");
                    break;
                case "Kích cỡ":
                    sql.append(" AND Size LIKE ?");
                    params.add("%" + tuKhoa + "%");
                    break;
                case "Trạng thái":
                    sql.append(" AND TrangThai LIKE ?");
                    params.add("%" + tuKhoa + "%");
                    break;
                default:
                    // Tìm trong nhiều trường
                    sql.append(
                            " AND (MaSanPham LIKE ? OR TenSanPham LIKE ? OR MaNhaCungCap LIKE ? OR MaDanhMuc LIKE ?)");
                    String searchPattern = "%" + tuKhoa + "%";
                    params.add(searchPattern);
                    params.add(searchPattern);
                    params.add(searchPattern);
                    params.add(searchPattern);
                    break;
            }
        }

        // Điều kiện khoảng Đơn giá
        if (giaTu != null) {
            sql.append(" AND GiaBan >= ?");
            params.add(giaTu);
        }
        if (giaDen != null) {
            sql.append(" AND GiaBan <= ?");
            params.add(giaDen);
        }

        // Điều kiện khoảng Số lượng
        if (slTu != null) {
            sql.append(" AND SoLuongTonKho >= ?");
            params.add(slTu);
        }
        if (slDen != null) {
            sql.append(" AND SoLuongTonKho <= ?");
            params.add(slDen);
        }

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // Thiết lập tham số cho câu truy vấn
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sanPhamList;
    }

    // Lấy thông tin sản phẩm theo mã sản phẩm
    public sanPhamDTO getSanPhamByMa(String maSanPham) {
        String sql = "SELECT * FROM SanPham WHERE MaSanPham = ?";

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maSanPham);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new sanPhamDTO(
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Thêm sản phẩm mới
    public boolean addSanPham(sanPhamDTO sp) {
        String sql = "INSERT INTO SanPham (MaSanPham, TenSanPham, MaNhaCungCap, MaDanhMuc, MauSac, Size, SoLuongTonKho, GiaBan, ImgURL, TrangThai) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sp.getMaSanPham());
            ps.setString(2, sp.getTenSanPham());
            ps.setString(3, sp.getMaNhaCungCap());
            ps.setString(4, sp.getMaDanhMuc());
            ps.setString(5, sp.getMauSac());
            ps.setString(6, sp.getSize());
            ps.setInt(7, sp.getSoLuongTonKho());
            ps.setDouble(8, sp.getGiaBan());
            ps.setString(9, sp.getImgURL());
            ps.setString(10, sp.getTrangThai());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật thông tin sản phẩm
    public boolean updateSanPham(sanPhamDTO sp) {
        String sql = "UPDATE SanPham SET TenSanPham = ?, MaNhaCungCap = ?, MaDanhMuc = ?, MauSac = ?, Size = ?, " +
                "SoLuongTonKho = ?, GiaBan = ?, ImgURL = ?, TrangThai = ?, MaKhoHang = ? WHERE MaSanPham = ?";

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sp.getTenSanPham());
            ps.setString(2, sp.getMaNhaCungCap());
            ps.setString(3, sp.getMaDanhMuc());
            ps.setString(4, sp.getMauSac());
            ps.setString(5, sp.getSize());
            ps.setInt(6, sp.getSoLuongTonKho());
            ps.setDouble(7, sp.getGiaBan());
            ps.setString(8, sp.getImgURL());
            ps.setString(9, sp.getTrangThai());

            ps.setString(11, sp.getMaSanPham());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa sản phẩm
    public boolean deleteSanPham(String maSanPham) {
        String sql = "DELETE FROM SanPham WHERE MaSanPham = ?";

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maSanPham);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật số lượng sản phẩm
    public boolean capNhatSoLuongSanPham(String maSanPham, int soLuongThem) {
        String sql = "UPDATE SanPham SET SoLuongTonKho = SoLuongTonKho + ? WHERE MaSanPham = ?";

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, soLuongThem);
            ps.setString(2, maSanPham);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Kiểm tra số lượng tồn kho của sản phẩm có đủ để xuất không
    public boolean kiemTraTonKho(String maSanPham, int soLuongCanXuat) {
        String sql = "SELECT SoLuongTonKho FROM SanPham WHERE MaSanPham = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maSanPham);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int soLuongHienTai = rs.getInt("SoLuongTonKho");
                return soLuongHienTai >= soLuongCanXuat;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Giảm số lượng tồn kho khi xuất hàng
    public boolean giamSoLuongTonKho(String maSanPham, int soLuongXuat) {
        String sql = "UPDATE SanPham SET SoLuongTonKho = SoLuongTonKho - ? WHERE MaSanPham = ? AND SoLuongTonKho >= ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, soLuongXuat);
            ps.setString(2, maSanPham);
            ps.setInt(3, soLuongXuat);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Kiểm tra cảnh báo tồn kho thấp
    public boolean kiemTraCanhBaoTonKho(String maSanPham) {
        String sql = "SELECT SoLuongTonKho FROM SanPham WHERE MaSanPham = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maSanPham);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int soLuongHienTai = rs.getInt("SoLuongTonKho");
                return soLuongHienTai <= 10; // Cảnh báo khi dưới hoặc bằng 10
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}