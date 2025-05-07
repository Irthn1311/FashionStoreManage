package DAO;

import DTO.nhaCungCapDTO;
import DTB.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhaCungCapDAO {

    private static final Logger LOGGER = Logger.getLogger(NhaCungCapDAO.class.getName());

    public List<nhaCungCapDTO> getAllNhaCungCap() {
        List<nhaCungCapDTO> nhaCungCapList = new ArrayList<>();
        String sql = "SELECT ncc.MaNhaCungCap, ncc.TenNhaCungCap, ncc.LoaiSP, " +
                    "ncc.NamHopTac, ncc.DiaChi, ncc.Email, ncc.SoDienThoai, ncc.TrangThai " +
                    "FROM NhaCungCap ncc";

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                nhaCungCapDTO ncc = new nhaCungCapDTO(
                    rs.getString("MaNhaCungCap"),
                    rs.getString("TenNhaCungCap"),
                    rs.getString("LoaiSP"),
                    rs.getInt("NamHopTac"),
                    rs.getString("DiaChi"),
                    rs.getString("Email"),
                    rs.getString("SoDienThoai"),
                    rs.getString("TrangThai")
                );
                nhaCungCapList.add(ncc);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy danh sách nhà cung cấp: {0}", e.getMessage());
        }

        return nhaCungCapList;
    }

    public List<nhaCungCapDTO> searchNhaCungCap(String keyword, String searchType) {
        List<nhaCungCapDTO> nhaCungCapList = new ArrayList<>();
        String sql;
        
        if (searchType.equals("Tất cả")) {
            sql = "SELECT ncc.MaNhaCungCap, ncc.TenNhaCungCap, ncc.LoaiSP, " +
                  "ncc.NamHopTac, ncc.DiaChi, ncc.Email, ncc.SoDienThoai, ncc.TrangThai " +
                  "FROM NhaCungCap ncc " +
                  "WHERE ncc.MaNhaCungCap LIKE ? OR ncc.TenNhaCungCap LIKE ? " +
                  "OR ncc.Email LIKE ? OR ncc.SoDienThoai LIKE ?";
        } else {
            String columnName;
            switch (searchType) {
                case "Mã NCC":
                    columnName = "MaNhaCungCap";
                    break;
                case "Tên NCC":
                    columnName = "TenNhaCungCap";
                    break;
                case "Email":
                    columnName = "Email";
                    break;
                case "Số điện thoại":
                    columnName = "SoDienThoai";
                    break;
                default:
                    return getAllNhaCungCap();
            }
            sql = "SELECT ncc.MaNhaCungCap, ncc.TenNhaCungCap, ncc.LoaiSP, " +
                  "ncc.NamHopTac, ncc.DiaChi, ncc.Email, ncc.SoDienThoai, ncc.TrangThai " +
                  "FROM NhaCungCap ncc " +
                  "WHERE ncc." + columnName + " LIKE ?";
        }

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            if (searchType.equals("Tất cả")) {
                for (int i = 1; i <= 4; i++) {
                    ps.setString(i, searchPattern);
                }
            } else {
                ps.setString(1, searchPattern);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    nhaCungCapDTO ncc = new nhaCungCapDTO(
                        rs.getString("MaNhaCungCap"),
                        rs.getString("TenNhaCungCap"),
                        rs.getString("LoaiSP"),
                        rs.getInt("NamHopTac"),
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getString("SoDienThoai"),
                        rs.getString("TrangThai")
                    );
                    nhaCungCapList.add(ncc);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi tìm kiếm nhà cung cấp với từ khóa {0}: {1}",
                    new Object[] { keyword, e.getMessage() });
        }

        return nhaCungCapList;
    }

    public boolean themNhaCungCap(nhaCungCapDTO ncc) {
        String sql = "INSERT INTO NhaCungCap (MaNhaCungCap, TenNhaCungCap, LoaiSP, " +
                    "NamHopTac, DiaChi, Email, SoDienThoai, TrangThai) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ncc.getMaNhaCungCap());
            ps.setString(2, ncc.getTenNhaCungCap());
            ps.setString(3, ncc.getLoaiSP());
            ps.setInt(4, ncc.getNamHopTac());
            ps.setString(5, ncc.getDiaChi());
            ps.setString(6, ncc.getEmail());
            ps.setString(7, ncc.getSoDienThoai());
            ps.setString(8, ncc.getTrangThai());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatNhaCungCap(nhaCungCapDTO ncc) {
        String sql = "UPDATE NhaCungCap SET TenNhaCungCap = ?, LoaiSP = ?, " +
                    "NamHopTac = ?, DiaChi = ?, Email = ?, SoDienThoai = ?, TrangThai = ? " +
                    "WHERE MaNhaCungCap = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ncc.getTenNhaCungCap());
            ps.setString(2, ncc.getLoaiSP());
            ps.setInt(3, ncc.getNamHopTac());
            ps.setString(4, ncc.getDiaChi());
            ps.setString(5, ncc.getEmail());
            ps.setString(6, ncc.getSoDienThoai());
            ps.setString(7, ncc.getTrangThai());
            ps.setString(8, ncc.getMaNhaCungCap());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaNhaCungCap(String maNCC) {
        String sql = "DELETE FROM NhaCungCap WHERE MaNhaCungCap = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNCC);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public nhaCungCapDTO getNhaCungCapByMa(String maNCC) {
        String sql = "SELECT ncc.MaNhaCungCap, ncc.TenNhaCungCap, ncc.LoaiSP, " +
                    "ncc.NamHopTac, ncc.DiaChi, ncc.Email, ncc.SoDienThoai, ncc.TrangThai " +
                    "FROM NhaCungCap ncc " +
                    "WHERE ncc.MaNhaCungCap = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNCC);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new nhaCungCapDTO(
                        rs.getString("MaNhaCungCap"),
                        rs.getString("TenNhaCungCap"),
                        rs.getString("LoaiSP"),
                        rs.getInt("NamHopTac"),
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getString("SoDienThoai"),
                        rs.getString("TrangThai")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean isSupplierActive(String maNhaCungCap) {
        String sql = "SELECT COUNT(*) FROM NhaCungCap WHERE MaNhaCungCap = ? AND TrangThai = N'Đang hợp tác'";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNhaCungCap);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getAllSuppliers() {
        List<String> suppliers = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT MaNhaCungCap FROM NhaCungCap")) {
            while (rs.next()) {
                suppliers.add(rs.getString("MaNhaCungCap"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    public boolean xoaNhaCungCapVaSanPham(String maNCC) {
        String sql = "DELETE FROM SanPham WHERE MaNhaCungCap = ?; " +
                    "DELETE FROM NhaCungCap WHERE MaNhaCungCap = ?";
        try {
            Connection conn = ConnectDB.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maNCC);
            ps.setString(2, maNCC);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatSanPhamSangNhaCungCapKhac(String maNCCCu, String maNCCMoi) {
        String sql = "UPDATE SanPham SET MaNhaCungCap = ? WHERE MaNhaCungCap = ?";
        try {
            Connection conn = ConnectDB.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maNCCMoi);
            ps.setString(2, maNCCCu);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
