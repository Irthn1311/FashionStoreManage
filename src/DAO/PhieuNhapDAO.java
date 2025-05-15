package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DTB.ConnectDB;
import DTO.PhieuNhapDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhieuNhapDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    private static final Pattern MA_PN_BATCH_PATTERN = Pattern.compile("PN(\\d{5})_\\d{5}");

    public PhieuNhapDAO() {
        // Initialize database connection
        try {
            conn = ConnectDB.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Create new PhieuNhap
    public boolean create(PhieuNhapDTO phieuNhap) {
        String sql = "INSERT INTO PhieuNhap (MaPhieuNhap, MaNhaCungCap, MaSanPham, TenSanPham, SoLuong, " +
                "ThoiGian, DonGia, TrangThai, HinhThucThanhToan, ThanhTien) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            // Kiểm tra số lượng tồn kho trước khi thêm
            String checkSql = "SELECT SoLuongTonKho FROM SanPham WHERE MaSanPham = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setString(1, phieuNhap.getMaSanPham());
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                int soLuongTonKho = rs.getInt("SoLuongTonKho");
                if (soLuongTonKho < 0) {
                    return false; // Không đủ số lượng trong kho
                }
            }

            ps = conn.prepareStatement(sql);
            ps.setString(1, phieuNhap.getMaPhieuNhap());
            ps.setString(2, phieuNhap.getMaNhaCungCap());
            ps.setString(3, phieuNhap.getMaSanPham());
            ps.setString(4, phieuNhap.getTenSanPham());
            ps.setInt(5, phieuNhap.getSoLuong());
            ps.setTimestamp(6, new java.sql.Timestamp(phieuNhap.getThoiGian().getTime()));
            ps.setDouble(7, phieuNhap.getDonGia());
            ps.setString(8, phieuNhap.getTrangThai());
            ps.setString(9, phieuNhap.getHinhThucThanhToan());
            ps.setDouble(10, phieuNhap.getThanhTien());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read PhieuNhap by ID
    public PhieuNhapDTO read(String maPhieuNhap) {
        String sql = "SELECT * FROM PhieuNhap WHERE MaPhieuNhap = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, maPhieuNhap);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new PhieuNhapDTO(
                        rs.getString("MaPhieuNhap"),
                        rs.getString("MaNhaCungCap"),
                        rs.getString("MaSanPham"),
                        rs.getString("TenSanPham"),
                        rs.getInt("SoLuong"),
                        rs.getTimestamp("ThoiGian"),
                        rs.getDouble("DonGia"),
                        rs.getString("TrangThai"),
                        rs.getString("HinhThucThanhToan"),
                        rs.getDouble("ThanhTien"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update PhieuNhap
    public boolean update(PhieuNhapDTO phieuNhap) {
        String sql = "UPDATE PhieuNhap SET MaNhaCungCap=?, MaSanPham=?, TenSanPham=?, SoLuong=?, " +
                "ThoiGian=?, DonGia=?, TrangThai=?, HinhThucThanhToan=?, ThanhTien=? " +
                "WHERE MaPhieuNhap=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, phieuNhap.getMaNhaCungCap());
            ps.setString(2, phieuNhap.getMaSanPham());
            ps.setString(3, phieuNhap.getTenSanPham());
            ps.setInt(4, phieuNhap.getSoLuong());
            ps.setTimestamp(5, new java.sql.Timestamp(phieuNhap.getThoiGian().getTime()));
            ps.setDouble(6, phieuNhap.getDonGia());
            ps.setString(7, phieuNhap.getTrangThai());
            ps.setString(8, phieuNhap.getHinhThucThanhToan());
            ps.setDouble(9, phieuNhap.getThanhTien());
            ps.setString(10, phieuNhap.getMaPhieuNhap());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete PhieuNhap
    public boolean delete(String maPhieuNhap) {
        String sql = "DELETE FROM PhieuNhap WHERE MaPhieuNhap = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, maPhieuNhap);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            if (e.getMessage().contains("FK_") || e.getMessage().contains("foreign key constraint")) {
                throw new RuntimeException(
                        "Không thể xóa phiếu nhập này vì đã được sử dụng trong hóa đơn hoặc bảng khác.");
            }
            e.printStackTrace();
            return false;
        }
    }

    // Get all PhieuNhap
    public List<PhieuNhapDTO> getAll() {
        List<PhieuNhapDTO> danhSachPhieuNhap = new ArrayList<>();
        try {
            String sql = "SELECT * FROM PhieuNhap";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                java.util.Date thoiGian = null;
                try {
                    String thoiGianStr = rs.getString("ThoiGian");
                    if (thoiGianStr != null && !thoiGianStr.trim().isEmpty()) {
                        thoiGian = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(thoiGianStr);
                    } else {
                        thoiGian = new java.util.Date(); // Nếu null thì lấy thời gian hiện tại
                    }
                } catch (Exception e) {
                    thoiGian = new java.util.Date(); // Nếu có lỗi parse thì lấy thời gian hiện tại
                }

                PhieuNhapDTO phieuNhap = new PhieuNhapDTO(
                        rs.getString("MaPhieuNhap"),
                        rs.getString("MaNhaCungCap"),
                        rs.getString("MaSanPham"),
                        rs.getString("TenSanPham"),
                        rs.getInt("SoLuong"),
                        thoiGian,
                        rs.getDouble("DonGia"),
                        rs.getString("TrangThai"),
                        rs.getString("HinhThucThanhToan"),
                        rs.getDouble("ThanhTien"));
                danhSachPhieuNhap.add(phieuNhap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return danhSachPhieuNhap;
    }

    public int getMaxBatchNumber() {
        String sql = "SELECT MaPhieuNhap FROM PhieuNhap";
        int maxBatchNum = 0;
        // Assuming 'this.conn' is the valid connection from the constructor
        try (PreparedStatement stmt = this.conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String maPN = rs.getString("MaPhieuNhap");
                if (maPN != null) {
                    Matcher matcher = MA_PN_BATCH_PATTERN.matcher(maPN);
                    if (matcher.matches()) { // Use matches() for full string match
                        try {
                            // group(1) is the first capturing group: (\d{5})
                            int currentBatchNum = Integer.parseInt(matcher.group(1));
                            if (currentBatchNum > maxBatchNum) {
                                maxBatchNum = currentBatchNum;
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Error parsing batch number from MaPhieuNhap: " + maPN + " - " + e.getMessage());
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log exception
        }
        return maxBatchNum;
    }

    // Update MaNhaCungCap for multiple PhieuNhap records
    public boolean updateMaNhaCungCap(String oldMaNCC, String newMaNCC) {
        String sql = "UPDATE PhieuNhap SET MaNhaCungCap = ? WHERE MaNhaCungCap = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, newMaNCC);
            ps.setString(2, oldMaNCC);
            return ps.executeUpdate() > 0; // Returns true if one or more rows were updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Close resources
    public void close() {
        try {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}