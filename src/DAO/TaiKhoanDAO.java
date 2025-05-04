package DAO;

import DTB.ConnectDB;
import DTO.taiKhoanDTO;
import java.sql.*;
import java.text.Normalizer;
import java.util.regex.Pattern;
import java.util.Calendar;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanDAO {
    
    private Connection conn;
    
    public TaiKhoanDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=FashionStore;user=sa;password=12345678;trustServerCertificate=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean themTaiKhoan(taiKhoanDTO taiKhoan) throws SQLException {
        String maTaiKhoan = taoMaTaiKhoanMoi();
        if (maTaiKhoan == null) {
            return false;
        }

        String sql = "INSERT INTO TaiKhoan (MaTaiKhoan, TenDangNhap, MatKhau, VaiTro, TrangThai, MaNhanVien) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, maTaiKhoan);
            ps.setString(2, taiKhoan.getTenDangNhap());
            ps.setString(3, taiKhoan.getMatKhau()); // Không hash mật khẩu
            ps.setString(4, taiKhoan.getVaiTro());
            ps.setInt(5, taiKhoan.getTrangThai());
            ps.setString(6, taiKhoan.getMaNhanVien());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean themTaiKhoanNhanVien(String maNhanVien) throws SQLException {
        String maTaiKhoan = taoMaTaiKhoanMoi();
        if (maTaiKhoan == null) {
            return false;
        }

        String sql = "INSERT INTO TaiKhoan (MaTaiKhoan, TenDangNhap, MatKhau, VaiTro, TrangThai, MaNhanVien) " +
                    "VALUES (?, ?, ?, 'Nhân viên', 1, ?)";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, maTaiKhoan);
            ps.setString(2, maNhanVien); // Sử dụng mã nhân viên làm tên đăng nhập
            ps.setString(3, hashPassword("12345678")); // Mật khẩu mặc định
            ps.setString(4, maNhanVien);

            return ps.executeUpdate() > 0;
        }
    }
    
    private boolean kiemTraSoDienThoaiTonTai(String soDienThoai) throws SQLException {
        String sql = "SELECT COUNT(*) FROM TaiKhoan WHERE MaNhanVien = ?";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, soDienThoai);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    public synchronized String taoMaTaiKhoanMoi() {
        String prefix = "TK";
        String sql = "SELECT TOP 1 MaTaiKhoan FROM TaiKhoan " +
                    "WHERE MaTaiKhoan LIKE 'TK%' " +
                    "ORDER BY CAST(SUBSTRING(MaTaiKhoan, 3, LEN(MaTaiKhoan)) AS INT) DESC";
        
        try (Connection conn = ConnectDB.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                String maCuoi = rs.getString("MaTaiKhoan");
                String numberStr = maCuoi.substring(2);
                int nextNumber = Integer.parseInt(numberStr) + 1;
                return String.format("%s%03d", prefix, nextNumber);
            } else {
                return "TK001";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String taoTenDangNhap(String hoTen, int ngaySinh, int thangSinh) {
        // Chuyển họ tên thành chữ thường không dấu
        String tenKhongDau = removeAccent(hoTen.toLowerCase().trim());

        // Loại bỏ khoảng trắng và ký tự đặc biệt
        tenKhongDau = tenKhongDau.replaceAll("[^a-z0-9]", "");

        // Thêm ngày và tháng sinh
        String tenDangNhap = String.format("%s%02d%02d", tenKhongDau, ngaySinh, thangSinh);

        // Kiểm tra xem tên đăng nhập đã tồn tại chưa
        if (kiemTraTenDangNhapTonTai(tenDangNhap)) {
            // Nếu đã tồn tại, thêm số ngẫu nhiên vào cuối
            int soNgauNhien = (int) (Math.random() * 1000);
            tenDangNhap = String.format("%s%03d", tenDangNhap, soNgauNhien);
        }

        return tenDangNhap;
    }

    private boolean kiemTraTenDangNhapTonTai(String tenDangNhap) {
        String sql = "SELECT COUNT(*) FROM TaiKhoan WHERE HoVaTen = ?";

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tenDangNhap);
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

    private String removeAccent(String text) {
        String temp = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public String themTaiKhoan(String hoTen, Date ngaySinh, String email, String soDienThoai) {
        // Tạo mã tài khoản mới
        String maTaiKhoan = taoMaTaiKhoanMoi();
        if (maTaiKhoan == null) {
            throw new RuntimeException("Không thể tạo mã tài khoản mới!");
        }

        // Lấy ngày và tháng từ ngày sinh
        Calendar cal = Calendar.getInstance();
        cal.setTime(ngaySinh);
        int ngay = cal.get(Calendar.DAY_OF_MONTH);
        int thang = cal.get(Calendar.MONTH) + 1;
        int nam = cal.get(Calendar.YEAR);

        // Tạo tên đăng nhập
        String tenDangNhap = taoTenDangNhap(hoTen, ngay, thang);

        String sql = "INSERT INTO TaiKhoan (ID, HoVaTen, Email, SoDienThoai, DiaChi, GioiTinh, Tuoi, ChucVu, NgayThanhLap, TenCongTy) "
                +
                "VALUES (?, ?, ?, ?, NULL, NULL, ?, 'USER', GETDATE(), NULL)";

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maTaiKhoan);
            ps.setString(2, hoTen);
            ps.setString(3, email);
            ps.setString(4, soDienThoai);
            ps.setString(5, String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - nam)); // Tính tuổi

            if (ps.executeUpdate() > 0) {
                return maTaiKhoan;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tạo tài khoản: " + e.getMessage());
        }

        return null;
    }

    public boolean capNhatMatKhau(String maTaiKhoan, String matKhauMoi) throws SQLException {
        String sql = "UPDATE TaiKhoan SET MatKhau = ? WHERE MaTaiKhoan = ?";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, matKhauMoi);
            ps.setString(2, maTaiKhoan);
            
            return ps.executeUpdate() > 0;
        }
    }

    public taiKhoanDTO getTaiKhoanByMaNhanVien(String maNhanVien) throws SQLException {
        String sql = "SELECT * FROM TaiKhoan WHERE MaNhanVien = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maNhanVien);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    taiKhoanDTO taiKhoan = new taiKhoanDTO();
                    taiKhoan.setMaTaiKhoan(rs.getString("MaTaiKhoan"));
                    taiKhoan.setTenDangNhap(rs.getString("TenDangNhap"));
                    taiKhoan.setMatKhau(rs.getString("MatKhau"));
                    taiKhoan.setMaNhanVien(rs.getString("MaNhanVien"));
                    taiKhoan.setVaiTro(rs.getString("VaiTro"));
                    taiKhoan.setTrangThai(rs.getInt("TrangThai"));
                    return taiKhoan;
                }
            }
        }
        return null;
    }

    public void taoTaiKhoanAdmin() throws SQLException {
        // Kiểm tra xem tài khoản admin đã tồn tại chưa
        String sql = "SELECT COUNT(*) FROM TaiKhoan WHERE TenDangNhap = 'admin'";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next() && rs.getInt(1) == 0) {
                // Tạo tài khoản admin nếu chưa tồn tại
                String maTaiKhoan = "TK001";
                String tenDangNhap = "admin";
                String matKhau = "Admin123@"; // Không hash mật khẩu
                String vaiTro = "Quản lý";
                int trangThai = 1;
                
                sql = "INSERT INTO TaiKhoan (MaTaiKhoan, TenDangNhap, MatKhau, VaiTro, TrangThai) " +
                      "VALUES (?, ?, ?, ?, ?)";
                
                try (PreparedStatement psInsert = conn.prepareStatement(sql)) {
                    psInsert.setString(1, maTaiKhoan);
                    psInsert.setString(2, tenDangNhap);
                    psInsert.setString(3, matKhau);
                    psInsert.setString(4, vaiTro);
                    psInsert.setInt(5, trangThai);
                    psInsert.executeUpdate();
                }
            }
        }
    }

    public List<taiKhoanDTO> getAllTaiKhoan() throws SQLException {
        List<taiKhoanDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoan";
        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                taiKhoanDTO taiKhoan = new taiKhoanDTO();
                taiKhoan.setMaTaiKhoan(rs.getString("MaTaiKhoan"));
                taiKhoan.setTenDangNhap(rs.getString("TenDangNhap"));
                taiKhoan.setMatKhau(rs.getString("MatKhau"));
                taiKhoan.setMaNhanVien(rs.getString("MaNhanVien"));
                taiKhoan.setVaiTro(rs.getString("VaiTro"));
                taiKhoan.setTrangThai(rs.getInt("TrangThai"));
                list.add(taiKhoan);
            }
        }
        return list;
    }

    public boolean suaTaiKhoan(taiKhoanDTO taiKhoan) throws SQLException {
        String sql = "UPDATE TaiKhoan SET TenDangNhap = ?, MatKhau = ?, VaiTro = ?, TrangThai = ? WHERE MaTaiKhoan = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, taiKhoan.getTenDangNhap());
            pst.setString(2, taiKhoan.getMatKhau());
            pst.setString(3, taiKhoan.getVaiTro());
            pst.setInt(4, taiKhoan.getTrangThai());
            pst.setString(5, taiKhoan.getMaTaiKhoan());
            return pst.executeUpdate() > 0;
        }
    }

    public boolean xoaTaiKhoan(String maTaiKhoan) throws SQLException {
        String sql = "DELETE FROM TaiKhoan WHERE MaTaiKhoan = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maTaiKhoan);
            return pst.executeUpdate() > 0;
        }
    }

    public taiKhoanDTO kiemTraDangNhap(String tenDangNhap, String matKhau) throws SQLException {
        String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ? AND MatKhau = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, tenDangNhap);
            pst.setString(2, matKhau);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    taiKhoanDTO taiKhoan = new taiKhoanDTO();
                    taiKhoan.setMaTaiKhoan(rs.getString("MaTaiKhoan"));
                    taiKhoan.setTenDangNhap(rs.getString("TenDangNhap"));
                    taiKhoan.setMatKhau(rs.getString("MatKhau"));
                    taiKhoan.setMaNhanVien(rs.getString("MaNhanVien"));
                    taiKhoan.setVaiTro(rs.getString("VaiTro"));
                    taiKhoan.setTrangThai(rs.getInt("TrangThai"));
                    return taiKhoan;
                }
            }
        }
        return null;
    }
} 