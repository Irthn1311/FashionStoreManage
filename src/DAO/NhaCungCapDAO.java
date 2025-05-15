package DAO;

import DTO.nhaCungCapDTO;
import DTB.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhaCungCapDAO {

    private static final Logger LOGGER = Logger.getLogger(NhaCungCapDAO.class.getName());
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public NhaCungCapDAO() {
        try {
            conn = ConnectDB.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<nhaCungCapDTO> getAllNhaCungCap() {
        List<nhaCungCapDTO> nhaCungCapList = new ArrayList<>();
        String sql = "SELECT ncc.MaNhaCungCap, ncc.TenNhaCungCap, " +
                "ncc.NamHopTac, ncc.DiaChi, ncc.Email, ncc.SoDienThoai, ncc.TrangThai " +
                "FROM NhaCungCap ncc";

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                nhaCungCapDTO ncc = new nhaCungCapDTO(
                        rs.getString("MaNhaCungCap"),
                        rs.getString("TenNhaCungCap"),
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

    public List<nhaCungCapDTO> searchNhaCungCap(String keyword, String searchType) {
        List<nhaCungCapDTO> nhaCungCapList = new ArrayList<>();
        String sql;

        if (searchType.equals("Tất cả")) {
            sql = "SELECT ncc.MaNhaCungCap, ncc.TenNhaCungCap, " +
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
            sql = "SELECT ncc.MaNhaCungCap, ncc.TenNhaCungCap, " +
                    "ncc.NamHopTac, ncc.DiaChi, ncc.Email, ncc.SoDienThoai, ncc.TrangThai " +
                    "FROM NhaCungCap ncc " +
                    "WHERE ncc." + columnName + " LIKE ?";
        }

        try {
            ps = conn.prepareStatement(sql);
            String searchPattern = "%" + keyword + "%";
            if (searchType.equals("Tất cả")) {
                for (int i = 1; i <= 4; i++) {
                    ps.setString(i, searchPattern);
                }
            } else {
                ps.setString(1, searchPattern);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                nhaCungCapDTO ncc = new nhaCungCapDTO(
                        rs.getString("MaNhaCungCap"),
                        rs.getString("TenNhaCungCap"),
                        rs.getInt("NamHopTac"),
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getString("SoDienThoai"),
                        rs.getString("TrangThai"));
                nhaCungCapList.add(ncc);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi tìm kiếm nhà cung cấp với từ khóa {0}: {1}",
                    new Object[] { keyword, e.getMessage() });
        }

        return nhaCungCapList;
    }

    public boolean themNhaCungCap(nhaCungCapDTO ncc) {
        String sql = "INSERT INTO NhaCungCap (MaNhaCungCap, TenNhaCungCap, " +
                "NamHopTac, DiaChi, Email, SoDienThoai, TrangThai) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, ncc.getMaNhaCungCap());
            ps.setString(2, ncc.getTenNhaCungCap());
            ps.setInt(3, ncc.getNamHopTac());
            ps.setString(4, ncc.getDiaChi());
            ps.setString(5, ncc.getEmail());
            ps.setString(6, ncc.getSoDienThoai());
            ps.setString(7, ncc.getTrangThai());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatNhaCungCap(nhaCungCapDTO ncc) {
        String sql = "UPDATE NhaCungCap SET TenNhaCungCap = ?, " +
                "NamHopTac = ?, DiaChi = ?, Email = ?, SoDienThoai = ?, TrangThai = ? " +
                "WHERE MaNhaCungCap = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, ncc.getTenNhaCungCap());
            ps.setInt(2, ncc.getNamHopTac());
            ps.setString(3, ncc.getDiaChi());
            ps.setString(4, ncc.getEmail());
            ps.setString(5, ncc.getSoDienThoai());
            ps.setString(6, ncc.getTrangThai());
            ps.setString(7, ncc.getMaNhaCungCap());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaNhaCungCap(String maNCC) {
        String sql = "DELETE FROM NhaCungCap WHERE MaNhaCungCap = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, maNCC);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public nhaCungCapDTO getNhaCungCapByMa(String maNCC) {
        String sql = "SELECT ncc.MaNhaCungCap, ncc.TenNhaCungCap, " +
                "ncc.NamHopTac, ncc.DiaChi, ncc.Email, ncc.SoDienThoai, ncc.TrangThai " +
                "FROM NhaCungCap ncc " +
                "WHERE UPPER(ncc.MaNhaCungCap) = UPPER(?)";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, maNCC);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new nhaCungCapDTO(
                        rs.getString("MaNhaCungCap"),
                        rs.getString("TenNhaCungCap"),
                        rs.getInt("NamHopTac"),
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getString("SoDienThoai"),
                        rs.getString("TrangThai"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isSupplierActive(String maNhaCungCap) {
        String sql = "SELECT TrangThai FROM NhaCungCap WHERE MaNhaCungCap = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, maNhaCungCap);
            rs = ps.executeQuery();
            if (rs.next()) {
                return "Hoạt động".equals(rs.getString("TrangThai"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getAllSuppliers() {
        List<String> suppliers = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
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
            ps = conn.prepareStatement(sql);
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
            ps = conn.prepareStatement(sql);
            ps.setString(1, maNCCMoi);
            ps.setString(2, maNCCCu);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getSuppliersByProduct(String productType, String productCode) {
        List<String> suppliers = new ArrayList<>();
        String sql = "SELECT DISTINCT ncc.MaNhaCungCap, ncc.TenNhaCungCap " +
                "FROM NhaCungCap ncc " +
                "JOIN NhaCungCap_SanPham nccsp ON ncc.MaNhaCungCap = nccsp.MaNhaCungCap " +
                "JOIN SanPham sp ON nccsp.MaSanPham = sp.MaSanPham " +
                "WHERE ncc.TrangThai = N'Đang hợp tác' " +
                "AND sp.MaSanPham = ? " +
                "AND (sp.MaDanhMuc = ? OR ncc.LoaiSP = ?)";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, productCode);
            ps.setString(2, productType);
            ps.setString(3, productType);

            rs = ps.executeQuery();
            while (rs.next()) {
                String maNCC = rs.getString("MaNhaCungCap");
                String tenNCC = rs.getString("TenNhaCungCap");
                suppliers.add(maNCC + " - " + tenNCC);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy danh sách nhà cung cấp theo sản phẩm: {0}", e.getMessage());
        }
        return suppliers;
    }

    public List<nhaCungCapDTO> getNhaCungCapByMaSanPham(String maSanPham) {
        List<nhaCungCapDTO> danhSachNhaCungCap = new ArrayList<>();
        String sql = "SELECT DISTINCT ncc.MaNhaCungCap, ncc.TenNhaCungCap, " +
                     "ncc.NamHopTac, ncc.DiaChi, ncc.Email, ncc.SoDienThoai, ncc.TrangThai " +
                     "FROM NhaCungCap ncc " +
                     "JOIN NhaCungCap_SanPham ncc_sp ON ncc.MaNhaCungCap = ncc_sp.MaNhaCungCap " +
                     "WHERE ncc_sp.MaSanPham = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, maSanPham);
            rs = ps.executeQuery();
            while (rs.next()) {
                nhaCungCapDTO ncc = new nhaCungCapDTO(
                        rs.getString("MaNhaCungCap"),
                        rs.getString("TenNhaCungCap"),
                        rs.getInt("NamHopTac"),
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getString("SoDienThoai"),
                        rs.getString("TrangThai"));
                danhSachNhaCungCap.add(ncc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachNhaCungCap;
    }

    public List<nhaCungCapDTO> searchNhaCungCapAdvanced(String keyword, String searchType, String namHopTacFilter,
            String trangThaiFilter) {
        List<nhaCungCapDTO> nhaCungCapList = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder(
                "SELECT ncc.MaNhaCungCap, ncc.TenNhaCungCap, ")
                .append("ncc.NamHopTac, ncc.DiaChi, ncc.Email, ncc.SoDienThoai, ncc.TrangThai ")
                .append("FROM NhaCungCap ncc WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.isEmpty()) {
            if (searchType.equals("Tất cả")) {
                sqlBuilder.append(
                        "AND (ncc.MaNhaCungCap LIKE ? OR ncc.TenNhaCungCap LIKE ? OR ncc.Email LIKE ? OR ncc.SoDienThoai LIKE ?) ");
                String searchPattern = "%" + keyword + "%";
                for (int i = 0; i < 4; i++) {
                    params.add(searchPattern);
                }
            } else {
                String columnName = "";
                switch (searchType) {
                    case "Mã NCC":
                        columnName = "ncc.MaNhaCungCap";
                        break;
                    case "Tên NCC":
                        columnName = "ncc.TenNhaCungCap";
                        break;
                    case "Email":
                        columnName = "ncc.Email";
                        break;
                    case "Số điện thoại":
                        columnName = "ncc.SoDienThoai";
                        break;
                }
                if (!columnName.isEmpty()) {
                    sqlBuilder.append("AND ").append(columnName).append(" LIKE ? ");
                    params.add("%" + keyword + "%");
                }
            }
        }

        if (namHopTacFilter != null && !namHopTacFilter.equals("Tất cả")) {
            sqlBuilder.append("AND ncc.NamHopTac = ? ");
            params.add(Integer.parseInt(namHopTacFilter));
        }

        if (trangThaiFilter != null && !trangThaiFilter.equals("Tất cả")) {
            sqlBuilder.append("AND ncc.TrangThai = ? ");
            params.add(trangThaiFilter);
        }

        try {
            ps = conn.prepareStatement(sqlBuilder.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                nhaCungCapDTO ncc = new nhaCungCapDTO(
                        rs.getString("MaNhaCungCap"),
                        rs.getString("TenNhaCungCap"),
                        rs.getInt("NamHopTac"),
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getString("SoDienThoai"),
                        rs.getString("TrangThai"));
                nhaCungCapList.add(ncc);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi tìm kiếm nhà cung cấp nâng cao: {0}", e.getMessage());
        }
        return nhaCungCapList;
    }

    public boolean capNhatTrangThaiNhaCungCap(String maNCC, String newTrangThai) {
        String sql = "UPDATE NhaCungCap SET TrangThai = ? WHERE MaNhaCungCap = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, newTrangThai);
            ps.setString(2, maNCC);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi cập nhật trạng thái nhà cung cấp {0}: {1}",
                    new Object[] { maNCC, e.getMessage() });
            return false;
        }
    }
}