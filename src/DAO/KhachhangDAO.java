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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author nson9
 */
public class KhachHangDAO {

    private TaiKhoanDAO taiKhoanDAO;

    public KhachHangDAO() {
        taiKhoanDAO = new TaiKhoanDAO();
    }

    public List<khachHangDTO> getAllKhachHang() {
        List<khachHangDTO> khachHangList = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                khachHangDTO kh = new khachHangDTO(
                    rs.getString("MaKhachHang"),
                    rs.getString("HoTen"),
                    rs.getString("GioiTinh"),
                    rs.getString("SoDienThoai"),
                    rs.getString("Email"),
                    rs.getString("DiaChi"),
                    rs.getDate("NgaySinh")
                );
                khachHangList.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return khachHangList;
    }

    public List<khachHangDTO> searchKhachHang(String keyword, String searchType) {
        List<khachHangDTO> khachHangList = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE 1=1 ";

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
                sql += "AND SoDienThoai LIKE ? ";
                break;
            default:
                sql += "AND (MaKhachHang LIKE ? OR HoTen LIKE ? OR Email LIKE ? OR SoDienThoai LIKE ?) ";
                break;
        }

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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
                    khachHangDTO kh = new khachHangDTO(
                        rs.getString("MaKhachHang"),
                        rs.getString("HoTen"),
                        rs.getString("GioiTinh"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("DiaChi"),
                        rs.getDate("NgaySinh")
                    );
                    khachHangList.add(kh);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return khachHangList;
    }

    private boolean kiemTraTaiKhoanTonTai(String maTaiKhoan) {
        String sql = "SELECT COUNT(*) FROM TaiKhoan WHERE ID = ?";
        
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
        String sql = "INSERT INTO KhachHang (MaKhachHang, HoTen, GioiTinh, SoDienThoai, Email, DiaChi, NgaySinh) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, khachHang.getMaKhachHang());
            ps.setString(2, khachHang.getHoTen());
            ps.setString(3, khachHang.getGioiTinh());
            ps.setString(4, khachHang.getSoDienThoai());
            ps.setString(5, khachHang.getEmail());
            ps.setString(6, khachHang.getDiaChi());
            ps.setDate(7, khachHang.getNgaySinh());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaKhachHang(String maKhachHang) {
        String sqlCheckXuatHang = "SELECT COUNT(*) FROM XuatHang WHERE MaKhachHang = ?";
        String sqlDeleteHoaDon = "DELETE FROM HoaDon WHERE MaKhachHang = ?";
        String sqlDeleteKhachHang = "DELETE FROM KhachHang WHERE MaKhachHang = ?";

        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return false;
            }

            // Kiểm tra xem khách hàng có dữ liệu trong bảng XuatHang không
            try (PreparedStatement psCheckXuatHang = conn.prepareStatement(sqlCheckXuatHang)) {
                psCheckXuatHang.setString(1, maKhachHang);
                ResultSet rs = psCheckXuatHang.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.err.println("Không thể xóa khách hàng vì khách hàng đã có dữ liệu xuất hàng.");
                    throw new SQLException("Khách hàng có dữ liệu trong bảng XuatHang, không thể xóa. Hãy xóa dữ liệu xuất hàng trước.");
                }
            }

            conn.setAutoCommit(false);

            // Xóa các bản ghi liên quan trong bảng HoaDon
            try (PreparedStatement psDeleteHoaDon = conn.prepareStatement(sqlDeleteHoaDon)) {
                psDeleteHoaDon.setString(1, maKhachHang);
                psDeleteHoaDon.executeUpdate();
            }

            // Xóa khách hàng
            try (PreparedStatement psDeleteKhachHang = conn.prepareStatement(sqlDeleteKhachHang)) {
                psDeleteKhachHang.setString(1, maKhachHang);
                int rowsAffected = psDeleteKhachHang.executeUpdate();
                
                if (rowsAffected > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa khách hàng: " + e.getMessage());
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

    public boolean capNhatKhachHang(khachHangDTO khachHang) {
        String sql = "UPDATE KhachHang SET HoTen = ?, GioiTinh = ?, SoDienThoai = ?, " +
                    "Email = ?, DiaChi = ?, NgaySinh = ? WHERE MaKhachHang = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, khachHang.getHoTen());
            ps.setString(2, khachHang.getGioiTinh());
            ps.setString(3, khachHang.getSoDienThoai());
            ps.setString(4, khachHang.getEmail());
            ps.setString(5, khachHang.getDiaChi());
            ps.setDate(6, khachHang.getNgaySinh());
            ps.setString(7, khachHang.getMaKhachHang());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getAllMaKhachHang() {
        List<String> maKhachHangList = new ArrayList<>();
        String sql = "SELECT MaKhachHang FROM KhachHang";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                maKhachHangList.add(rs.getString("MaKhachHang"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maKhachHangList;
    }

    public khachHangDTO getKhachHangByMa(String maKhachHang) {
        String sql = "SELECT * FROM KhachHang WHERE MaKhachHang = ?";
        khachHangDTO kh = null;

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKhachHang);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    kh = new khachHangDTO(
                        rs.getString("MaKhachHang"),
                        rs.getString("HoTen"),
                        rs.getString("GioiTinh"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("DiaChi"),
                        rs.getDate("NgaySinh")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kh;
    }

    public boolean isCustomerExists(String maKhachHang) {
        String sql = "SELECT COUNT(*) FROM KhachHang WHERE MaKhachHang = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKhachHang);
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

    public boolean importKhachHang(String filePath) {
        String sql = "INSERT INTO KhachHang (MaKhachHang, HoTen, GioiTinh, SoDienThoai, Email, DiaChi, NgaySinh) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return false;
            }

            conn.setAutoCommit(false);
            int successCount = 0;
            int failCount = 0;

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                // Bỏ qua dòng header nếu có
                reader.readLine();

                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length >= 7) {
                        try (PreparedStatement ps = conn.prepareStatement(sql)) {
                            ps.setString(1, data[0].trim()); // MaKhachHang
                            ps.setString(2, data[1].trim()); // HoTen
                            ps.setString(3, data[2].trim()); // GioiTinh
                            ps.setString(4, data[3].trim()); // SoDienThoai
                            ps.setString(5, data[4].trim()); // Email
                            ps.setString(6, data[5].trim()); // DiaChi
                            
                            // Xử lý ngày sinh
                            try {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                java.util.Date date = dateFormat.parse(data[6].trim());
                                ps.setDate(7, new java.sql.Date(date.getTime()));
                            } catch (ParseException e) {
                                System.err.println("Lỗi định dạng ngày: " + data[6]);
                                ps.setDate(7, null);
                            }

                            if (ps.executeUpdate() > 0) {
                                successCount++;
                            } else {
                                failCount++;
                            }
                        }
                    } else {
                        failCount++;
                        System.err.println("Dữ liệu không hợp lệ: " + line);
                    }
                }
            }

            if (successCount > 0) {
                conn.commit();
                System.out.println("Import thành công: " + successCount + " bản ghi");
                System.out.println("Import thất bại: " + failCount + " bản ghi");
                return true;
            } else {
                conn.rollback();
                System.out.println("Không có bản ghi nào được import thành công");
                return false;
            }

        } catch (SQLException | IOException e) {
            System.err.println("Lỗi khi import dữ liệu: " + e.getMessage());
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

    public int getSoLuongKhachHang() {
        String sql = "SELECT COUNT(*) FROM KhachHang";
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
