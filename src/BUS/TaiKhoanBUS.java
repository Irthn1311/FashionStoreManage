package BUS;

import DAO.TaiKhoanDAO;
import DTO.taiKhoanDTO;
import DTO.VaiTro;
import java.sql.SQLException;
import java.util.regex.Pattern;
import java.util.List;

public class TaiKhoanBUS {
    private TaiKhoanDAO taiKhoanDAO;
    
    public TaiKhoanBUS() {
        taiKhoanDAO = new TaiKhoanDAO();
    }
    
    public taiKhoanDTO dangNhap(String username, String password) {
        try {
            return taiKhoanDAO.kiemTraDangNhap(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean themTaiKhoan(taiKhoanDTO taiKhoan) {
        // Kiểm tra mật khẩu mạnh
        if (!isValidPassword(taiKhoan.getMatKhau())) {
            throw new IllegalArgumentException("Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt!");
        }
        
        try {
            return taiKhoanDAO.themTaiKhoan(taiKhoan);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean themTaiKhoanNhanVien(String maNhanVien) {
        try {
            return taiKhoanDAO.themTaiKhoanNhanVien(maNhanVien);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    
    private boolean isValidPhone(String phone) {
        String phoneRegex = "^[0-9]{10}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        return pattern.matcher(phone).matches();
    }
    
    private boolean isValidPassword(String password) {
        // Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        return pattern.matcher(password).matches();
    }

    public boolean doiMatKhau(String maTaiKhoan, String matKhauCu, String matKhauMoi) throws SQLException {
        // Kiểm tra mật khẩu cũ có đúng không
        taiKhoanDTO taiKhoan = taiKhoanDAO.kiemTraDangNhap(maTaiKhoan, matKhauCu);
        if (taiKhoan == null) {
            return false;
        }

        // Cập nhật mật khẩu mới
        return taiKhoanDAO.capNhatMatKhau(maTaiKhoan, matKhauMoi);
    }

    public List<taiKhoanDTO> getAllTaiKhoan() throws SQLException {
        return taiKhoanDAO.getAllTaiKhoan();
    }

    public taiKhoanDTO getTaiKhoanByMaNhanVien(String maNhanVien) throws SQLException {
        return taiKhoanDAO.getTaiKhoanByMaNhanVien(maNhanVien);
    }

    public boolean suaTaiKhoan(taiKhoanDTO taiKhoan) throws SQLException {
        return taiKhoanDAO.suaTaiKhoan(taiKhoan);
    }

    public boolean xoaTaiKhoan(String maTaiKhoan) throws SQLException {
        return taiKhoanDAO.xoaTaiKhoan(maTaiKhoan);
    }

    public boolean capNhatVaiTro(String maTaiKhoan, String vaiTroMoi) {
        // Kiểm tra tính hợp lệ của vai trò mới
        VaiTro vaiTro = VaiTro.fromString(vaiTroMoi);
        if (vaiTro == null) {
            return false;
        }
        
        // Gọi DAO để cập nhật vai trò
        return taiKhoanDAO.capNhatVaiTro(maTaiKhoan, vaiTroMoi);
    }
}
