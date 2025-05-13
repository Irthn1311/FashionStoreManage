package DAO;

import DTO.nhanVienDTO;
import DTB.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.sql.Statement;
import java.util.Calendar;
import DTO.taiKhoanDTO;

public class NhanVienDAO {
    
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    public NhanVienDAO() {
        try {
            conn = ConnectDB.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<nhanVienDTO> getAllNhanVien() {
        List<nhanVienDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        
        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                nhanVienDTO nv = new nhanVienDTO(
                    rs.getString("MaNhanVien"),
                    rs.getString("HoTen"),
                    rs.getString("Email"),
                    rs.getString("SoDienThoai"),
                    rs.getString("DiaChi"),
                    rs.getString("GioiTinh"),
                    rs.getDate("NgaySinh"),
                    rs.getTimestamp("NgayVaoLam"),
                    rs.getString("ChucVu"),
                    rs.getBigDecimal("MucLuong"),
                    rs.getString("MaQuanLy"),
                    rs.getString("TrangThai")
                );
                list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<nhanVienDTO> searchNhanVien(String keyword) {
        List<nhanVienDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE MaNhanVien LIKE ? OR HoTen LIKE ? OR Email LIKE ? OR SoDienThoai LIKE ?";
        
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + keyword + "%");
            pst.setString(2, "%" + keyword + "%");
            pst.setString(3, "%" + keyword + "%");
            pst.setString(4, "%" + keyword + "%");
            
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    nhanVienDTO nv = new nhanVienDTO(
                        rs.getString("MaNhanVien"),
                        rs.getString("HoTen"),
                        rs.getString("Email"),
                        rs.getString("SoDienThoai"),
                        rs.getString("DiaChi"),
                        rs.getString("GioiTinh"),
                        rs.getDate("NgaySinh"),
                        rs.getTimestamp("NgayVaoLam"),
                        rs.getString("ChucVu"),
                        rs.getBigDecimal("MucLuong"),
                        rs.getString("MaQuanLy"),
                        rs.getString("TrangThai")
                    );
                    list.add(nv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public nhanVienDTO getNhanVienByMa(String maNhanVien) {
        if (maNhanVien == null || maNhanVien.trim().isEmpty()) {
            return null;
        }
        
        String sql = "SELECT * FROM NhanVien WHERE MaNhanVien = ?";
        
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maNhanVien);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new nhanVienDTO(
                        rs.getString("MaNhanVien"),
                        rs.getString("HoTen"),
                        rs.getString("Email"),
                        rs.getString("SoDienThoai"),
                        rs.getString("DiaChi"),
                        rs.getString("GioiTinh"),
                        rs.getDate("NgaySinh"),
                        rs.getTimestamp("NgayVaoLam"),
                        rs.getString("ChucVu"),
                        rs.getBigDecimal("MucLuong"),
                        rs.getString("MaQuanLy"),
                        rs.getString("TrangThai")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean themNhanVien(nhanVienDTO nhanVien) {
        String sql = "INSERT INTO NhanVien (MaNhanVien, HoTen, Email, SoDienThoai, DiaChi, NgaySinh, GioiTinh, NgayVaoLam, ChucVu, MucLuong, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, nhanVien.getMaNhanVien());
            pst.setString(2, nhanVien.getHoTen());
            pst.setString(3, nhanVien.getEmail());
            pst.setString(4, nhanVien.getSoDienThoai());
            pst.setString(5, nhanVien.getDiaChi());
            pst.setDate(6, nhanVien.getNgaySinh());
            pst.setString(7, nhanVien.getGioiTinh());
            pst.setTimestamp(8, nhanVien.getNgayVaoLam());
            pst.setString(9, nhanVien.getChucVu());
            pst.setBigDecimal(10, nhanVien.getMucLuong());
            pst.setString(11, nhanVien.getTrangThai());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean capNhatNhanVien(nhanVienDTO nhanVien) {
        String sql = "UPDATE NhanVien SET HoTen = ?, Email = ?, SoDienThoai = ?, DiaChi = ?, NgaySinh = ?, GioiTinh = ?, ChucVu = ?, MucLuong = ?, TrangThai = ?, MaQuanLy = ? WHERE MaNhanVien = ?";
        
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, nhanVien.getHoTen());
            pst.setString(2, nhanVien.getEmail());
            pst.setString(3, nhanVien.getSoDienThoai());
            pst.setString(4, nhanVien.getDiaChi());
            pst.setDate(5, nhanVien.getNgaySinh());
            pst.setString(6, nhanVien.getGioiTinh());
            pst.setString(7, nhanVien.getChucVu());
            pst.setBigDecimal(8, nhanVien.getMucLuong());
            pst.setString(9, nhanVien.getTrangThai());
            pst.setString(10, nhanVien.getMaQuanLy());
            pst.setString(11, nhanVien.getMaNhanVien());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean xoaNhanVien(String maNhanVien) {
        String sql = "DELETE FROM TaiKhoan WHERE MaNhanVien = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maNhanVien);
            pstmt.executeUpdate();
            
            sql = "DELETE FROM NhanVien WHERE MaNhanVien = ?";
            try (PreparedStatement pstmt2 = conn.prepareStatement(sql)) {
                pstmt2.setString(1, maNhanVien);
                return pstmt2.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<nhanVienDTO> getNhanVienByChucVu(String chucVu) {
        List<nhanVienDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE ChucVu = ?";
        
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, chucVu);
            
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    nhanVienDTO nv = new nhanVienDTO(
                        rs.getString("MaNhanVien"),
                        rs.getString("HoTen"),
                        rs.getString("Email"),
                        rs.getString("SoDienThoai"),
                        rs.getString("DiaChi"),
                        rs.getString("GioiTinh"),
                        rs.getDate("NgaySinh"),
                        rs.getTimestamp("NgayVaoLam"),
                        rs.getString("ChucVu"),
                        rs.getBigDecimal("MucLuong"),
                        rs.getString("MaQuanLy"),
                        rs.getString("TrangThai")
                    );
                    list.add(nv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<nhanVienDTO> getNhanVienByTrangThai(String trangThai) {
        List<nhanVienDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE TrangThai = ?";
        
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, trangThai);
            
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    nhanVienDTO nv = new nhanVienDTO(
                        rs.getString("MaNhanVien"),
                        rs.getString("HoTen"),
                        rs.getString("Email"),
                        rs.getString("SoDienThoai"),
                        rs.getString("DiaChi"),
                        rs.getString("GioiTinh"),
                        rs.getDate("NgaySinh"),
                        rs.getTimestamp("NgayVaoLam"),
                        rs.getString("ChucVu"),
                        rs.getBigDecimal("MucLuong"),
                        rs.getString("MaQuanLy"),
                        rs.getString("TrangThai")
                    );
                    list.add(nv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean kiemTraEmailTonTai(String email) {
        String sql = "SELECT COUNT(*) FROM NhanVien WHERE Email = ?";
        
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, email);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean kiemTraSoDienThoaiTonTai(String soDienThoai) {
        String sql = "SELECT COUNT(*) FROM NhanVien WHERE SoDienThoai = ?";
        
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, soDienThoai);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public taiKhoanDTO getTaiKhoanByMaNhanVien(String maNhanVien) {
        taiKhoanDTO taiKhoan = null;
        try {
            String sql = "SELECT * FROM TaiKhoan WHERE MaNhanVien = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maNhanVien);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                taiKhoan = new taiKhoanDTO();
                taiKhoan.setTenDangNhap(rs.getString("TenDangNhap"));
                taiKhoan.setMatKhau(rs.getString("MatKhau"));
                taiKhoan.setVaiTro(rs.getString("VaiTro"));
                taiKhoan.setTrangThai(rs.getInt("TrangThai"));
                taiKhoan.setMaNhanVien(rs.getString("MaNhanVien"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taiKhoan;
    }
    
    public boolean capNhatTrangThaiTaiKhoan(taiKhoanDTO taiKhoan) {
        String sql = "UPDATE TaiKhoan SET TrangThai = ? WHERE MaNhanVien = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            int newStatus;
            // Xác định trạng thái mới dựa trên trạng thái hiện tại
            switch (taiKhoan.getTrangThai()) {
                case -1: // Đang xét duyệt -> Hoạt động
                    newStatus = 1;
                    break;
                case 1: // Hoạt động -> Không hoạt động
                    newStatus = 0;
                    break;
                case 0: // Không hoạt động -> Hoạt động
                    newStatus = 1;
                    break;
                default:
                    return false;
            }
            
            pstmt.setInt(1, newStatus);
            pstmt.setString(2, taiKhoan.getMaNhanVien());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean dangKyTaiKhoanVaNhanVien(String hoTen, String email, String soDienThoai, String matKhau) {
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // Tạo mã nhân viên mới
            String maNhanVien = "NV" + System.currentTimeMillis();
            
            // Tạo mã tài khoản mới
            String maTaiKhoan = "TK" + System.currentTimeMillis();
            
            // Thêm nhân viên mới
            String sqlNhanVien = "INSERT INTO NhanVien (MaNhanVien, HoTen, Email, SoDienThoai, ChucVu, TrangThai) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstNhanVien = conn.prepareStatement(sqlNhanVien)) {
                pstNhanVien.setString(1, maNhanVien);
                pstNhanVien.setString(2, hoTen);
                pstNhanVien.setString(3, email);
                pstNhanVien.setString(4, soDienThoai);
                pstNhanVien.setString(5, "Chưa có"); // Chức vụ mặc định
                pstNhanVien.setString(6, "Đang xét duyệt"); // Trạng thái nhân viên
                
                if (pstNhanVien.executeUpdate() <= 0) {
                    conn.rollback();
                    return false;
                }
            }

            // Tạo tài khoản mới với trạng thái -1 (Đang xét duyệt)
            String sqlTaiKhoan = "INSERT INTO TaiKhoan (MaTaiKhoan, TenDangNhap, MatKhau, VaiTro, TrangThai, MaNhanVien) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstTaiKhoan = conn.prepareStatement(sqlTaiKhoan)) {
                pstTaiKhoan.setString(1, maTaiKhoan);
                pstTaiKhoan.setString(2, email); // Sử dụng email làm tên đăng nhập
                pstTaiKhoan.setString(3, matKhau);
                pstTaiKhoan.setString(4, "Nhân viên");
                pstTaiKhoan.setInt(5, -1); // Trạng thái tài khoản: -1 = Đang xét duyệt
                pstTaiKhoan.setString(6, maNhanVien);
                
                if (pstTaiKhoan.executeUpdate() <= 0) {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public int getSoLuongNhanVien() {
        String sql = "SELECT COUNT(*) FROM NhanVien";
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