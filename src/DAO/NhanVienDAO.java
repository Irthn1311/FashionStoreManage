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
import DTO.VaiTro; // Import VaiTro if using its internal values directly, or rely on display name matching

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
                taiKhoan.setVaiTro(rs.getString("VaiTro")); // This sets the role as a String
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
            switch (taiKhoan.getTrangThai()) {
                case -1: 
                    newStatus = 1;
                    break;
                case 1: 
                    newStatus = 0;
                    break;
                case 0: 
                    newStatus = 1;
                    break;
                default:
                    return false;
            }
            
            pstmt.setInt(1, newStatus);
            pstmt.setString(2, taiKhoan.getMaNhanVien());
            // After updating, update the DTO object's status as well if needed by caller
            // taiKhoan.setTrangThai(newStatus); // This should be done in BUS or Panel if they reuse the DTO instance
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
            conn.setAutoCommit(false);

            String maNhanVien = "NV" + System.currentTimeMillis();
            String maTaiKhoan = "TK" + System.currentTimeMillis();
            
            String sqlNhanVien = "INSERT INTO NhanVien (MaNhanVien, HoTen, Email, SoDienThoai, ChucVu, TrangThai) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstNhanVien = conn.prepareStatement(sqlNhanVien)) {
                pstNhanVien.setString(1, maNhanVien);
                pstNhanVien.setString(2, hoTen);
                pstNhanVien.setString(3, email);
                pstNhanVien.setString(4, soDienThoai);
                pstNhanVien.setString(5, "Chưa có");
                pstNhanVien.setString(6, "Đang xét duyệt");
                
                if (pstNhanVien.executeUpdate() <= 0) {
                    conn.rollback();
                    return false;
                }
            }

            String sqlTaiKhoan = "INSERT INTO TaiKhoan (MaTaiKhoan, TenDangNhap, MatKhau, VaiTro, TrangThai, MaNhanVien) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstTaiKhoan = conn.prepareStatement(sqlTaiKhoan)) {
                pstTaiKhoan.setString(1, maTaiKhoan);
                pstTaiKhoan.setString(2, email);
                pstTaiKhoan.setString(3, matKhau);
                pstTaiKhoan.setString(4, "Nhân viên"); // Default role, perhaps use VaiTro.NHAN_VIEN.name() or getDisplayName()
                pstTaiKhoan.setInt(5, -1);
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

    public List<nhanVienDTO> searchNhanVienAdvanced(String keyword, String tieuChi, String vaiTro, String trangThaiTK) {
        List<nhanVienDTO> list = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT nv.*, tk.TrangThai AS TrangThaiTaiKhoan, tk.TenDangNhap, tk.VaiTro AS VaiTroTaiKhoan FROM NhanVien nv LEFT JOIN TaiKhoan tk ON nv.MaNhanVien = tk.MaNhanVien WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.isEmpty()) {
            if (tieuChi != null && !tieuChi.isEmpty()) {
                switch (tieuChi) {
                    case "Mã nhân viên":
                        sqlBuilder.append(" AND nv.MaNhanVien LIKE ?");
                        params.add("%" + keyword + "%");
                        break;
                    case "Tên nhân viên":
                        sqlBuilder.append(" AND nv.HoTen LIKE ?");
                        params.add("%" + keyword + "%");
                        break;
                    case "Email":
                        sqlBuilder.append(" AND nv.Email LIKE ?");
                        params.add("%" + keyword + "%");
                        break;
                    case "Số điện thoại":
                        sqlBuilder.append(" AND nv.SoDienThoai LIKE ?");
                        params.add("%" + keyword + "%");
                        break;
                    default: 
                        sqlBuilder.append(" AND (nv.MaNhanVien LIKE ? OR nv.HoTen LIKE ? OR nv.Email LIKE ? OR nv.SoDienThoai LIKE ?)");
                        for (int i = 0; i < 4; i++) {
                            params.add("%" + keyword + "%");
                        }
                        break;
                }
            } else { 
                 sqlBuilder.append(" AND (nv.MaNhanVien LIKE ? OR nv.HoTen LIKE ? OR nv.Email LIKE ? OR nv.SoDienThoai LIKE ?)");
                 for (int i = 0; i < 4; i++) {
                    params.add("%" + keyword + "%");
                }
            }
        }

        // Filter by Account Role (tk.VaiTro)
        if (vaiTro != null && !vaiTro.isEmpty() && !"Tất cả".equalsIgnoreCase(vaiTro)) {
            // The `vaiTro` parameter is the display name from the enum.
            // We need to find the corresponding enum constant to get its internal representation if stored differently,
            // or ensure the database stores the display name.
            // Assuming tk.VaiTro stores the display name or a value that matches `vaiTro` directly.
            sqlBuilder.append(" AND tk.VaiTro = ?");
            params.add(vaiTro);
        }

        if (trangThaiTK != null && !trangThaiTK.isEmpty() && !"Tất cả".equalsIgnoreCase(trangThaiTK)) {
            int statusValue;
            switch (trangThaiTK) {
                case "Hoạt động":
                    statusValue = 1;
                    break;
                case "Không hoạt động":
                    statusValue = 0;
                    break;
                case "Đang xét duyệt":
                    statusValue = -1;
                    break;
                default:
                    statusValue = -99;
                    break;
            }
            if (statusValue != -99) {
                 sqlBuilder.append(" AND tk.TrangThai = ?");
                 params.add(statusValue);
            }
        }

        try (PreparedStatement pst = conn.prepareStatement(sqlBuilder.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pst.setObject(i + 1, params.get(i));
            }

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
} 