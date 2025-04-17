package DAO;

import DTB.ConnectDB;
import java.sql.*;
import java.text.Normalizer;
import java.util.regex.Pattern;
import java.util.Calendar;

public class TaiKhoanDAO {
    
    public synchronized String taoMaTaiKhoanMoi() {
        String prefix = "TK";
        String sql = "SELECT TOP 1 MaTaiKhoan FROM TaiKhoanNguoiDung " +
                    "WHERE MaTaiKhoan LIKE 'TK%' " +
                    "ORDER BY CAST(SUBSTRING(MaTaiKhoan, 3, LEN(MaTaiKhoan)) AS INT) DESC";
        
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                String maCuoi = rs.getString("MaTaiKhoan");
                // Lấy phần số từ mã cuối
                String numberStr = maCuoi.substring(2);
                // Chuyển đổi sang số và tăng lên 1
                int nextNumber = Integer.parseInt(numberStr) + 1;
                // Format số với độ dài cố định là 5 chữ số
                return String.format("%s%05d", prefix, nextNumber);
            } else {
                // Nếu chưa có tài khoản nào, bắt đầu từ TK00001
                return "TK00001";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tạo mã tài khoản mới: " + e.getMessage());
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
        String sql = "SELECT COUNT(*) FROM TaiKhoanNguoiDung WHERE TenDangNhap = ?";
        
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
        
        // Tạo mật khẩu từ ngày sinh (ddMMyyyy)
        String matKhau = String.format("%02d%02d%04d", ngay, thang, nam);

        String sql = "INSERT INTO TaiKhoanNguoiDung (MaTaiKhoan, TenDangNhap, MatKhau, Email, SoDienThoai, VaiTro, TrangThai) " +
                    "VALUES (?, ?, ?, ?, ?, 'USER', 'ACTIVE')";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, maTaiKhoan);
            ps.setString(2, tenDangNhap);
            ps.setString(3, matKhau);
            ps.setString(4, email);
            ps.setString(5, soDienThoai);

            if (ps.executeUpdate() > 0) {
                return maTaiKhoan;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tạo tài khoản: " + e.getMessage());
        }
        
        return null;
    }
} 