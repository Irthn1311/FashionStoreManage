package DAO;

import DTO.nhaCungCapDTO;
import DTB.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhaCungCapDAO {

    private static final Logger LOGGER = Logger.getLogger(NhaCungCapDAO.class.getName());

    public List<nhaCungCapDTO> getAllNhaCungCap() {
        List<nhaCungCapDTO> nhaCungCapList = new ArrayList<>();
        String sql = "SELECT ncc.MaNhaCungCap, ncc.TenNhaCungCap, ncc.MaSanPham, ncc.LoaiSP, " +
                "ncc.NamHopTac, ncc.DiaChi, ncc.Email, ncc.SoDienThoai, ncc.TrangThai, " +
                "sp.TenSanPham " +
                "FROM NhaCungCap ncc " +
                "LEFT JOIN SanPham sp ON ncc.MaSanPham = sp.MaSanPham";

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String tenSanPham = rs.getString("TenSanPham");
                if (tenSanPham == null) {
                    tenSanPham = "Không có sản phẩm"; // Xử lý trường hợp null
                }

                nhaCungCapDTO ncc = new nhaCungCapDTO(
                        rs.getString("MaNhaCungCap"),
                        rs.getString("TenNhaCungCap"),
                        rs.getString("MaSanPham"),
                        rs.getString("LoaiSP"),
                        tenSanPham,
                        rs.getInt("NamHopTac"),
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getString("SoDienThoai"),
                        rs.getString("TrangThai"));
                nhaCungCapList.add(ncc);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy danh sách nhà cung cấp: {0}", e.getMessage());
        }

        return nhaCungCapList;
    }

    public List<nhaCungCapDTO> searchNhaCungCap(String keyword) {
        List<nhaCungCapDTO> nhaCungCapList = new ArrayList<>();
        String sql = "SELECT ncc.MaNhaCungCap, ncc.TenNhaCungCap, ncc.MaSanPham, ncc.LoaiSP, " +
                "ncc.NamHopTac, ncc.DiaChi, ncc.Email, ncc.SoDienThoai, ncc.TrangThai, " +
                "sp.TenSanPham " +
                "FROM NhaCungCap ncc " +
                "LEFT JOIN SanPham sp ON ncc.MaSanPham = sp.MaSanPham " +
                "WHERE ncc.TenNhaCungCap LIKE ? OR ncc.Email LIKE ? OR ncc.SoDienThoai LIKE ? OR ncc.MaNhaCungCap LIKE ?";

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            for (int i = 1; i <= 4; i++) {
                ps.setString(i, searchPattern);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String tenSanPham = rs.getString("TenSanPham");
                    if (tenSanPham == null) {
                        tenSanPham = "Không có sản phẩm"; // Xử lý trường hợp null
                    }

                    nhaCungCapDTO ncc = new nhaCungCapDTO(
                            rs.getString("MaNhaCungCap"),
                            rs.getString("TenNhaCungCap"),
                            rs.getString("MaSanPham"),
                            rs.getString("LoaiSP"),
                            tenSanPham,
                            rs.getInt("NamHopTac"),
                            rs.getString("DiaChi"),
                            rs.getString("Email"),
                            rs.getString("SoDienThoai"),
                            rs.getString("TrangThai"));
                    nhaCungCapList.add(ncc);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi tìm kiếm nhà cung cấp với từ khóa {0}: {1}",
                    new Object[] { keyword, e.getMessage() });
        }

        return nhaCungCapList;
    }
}