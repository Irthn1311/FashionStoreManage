package BUS;

import DAO.NhanVienDAO;
import DTO.nhanVienDTO;
import java.util.List;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.regex.Pattern;
import java.sql.SQLException;
import DAO.TaiKhoanDAO;
import DTO.taiKhoanDTO;
import DTO.VaiTro;

public class NhanVienBUS {
    private NhanVienDAO nhanVienDAO;
    private TaiKhoanDAO taiKhoanDAO;
    
    public NhanVienBUS() {
        nhanVienDAO = new NhanVienDAO();
        taiKhoanDAO = new TaiKhoanDAO();
    }
    
    /**
     * Lấy danh sách tất cả nhân viên
     * @return Danh sách nhân viên
     */
    public List<nhanVienDTO> getAllNhanVien() {
        return nhanVienDAO.getAllNhanVien();
    }
    
    /**
     * Tìm kiếm nhân viên theo từ khóa
     * @param keyword Từ khóa tìm kiếm
     * @return Danh sách nhân viên thỏa mãn điều kiện
     */
    public List<nhanVienDTO> searchNhanVien(String keyword) {
        return nhanVienDAO.searchNhanVien(keyword);
    }
    
    /**
     * Lấy thông tin nhân viên theo mã nhân viên
     * @param maNhanVien Mã nhân viên cần lấy thông tin
     * @return Thông tin nhân viên
     */
    public nhanVienDTO getNhanVienByMa(String maNhanVien) {
        return nhanVienDAO.getNhanVienByMa(maNhanVien);
    }
    
    /**
     * Thêm nhân viên mới
     * @param nhanVien Thông tin nhân viên cần thêm
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public boolean themNhanVien(nhanVienDTO nhanVien) {
        // Validate NhanVien data before attempting to add
        if (!validateNhanVienData(nhanVien)) {
            // Optionally, log an error or throw an exception if data is invalid
            System.err.println("Invalid NhanVien data provided to NhanVienBUS.themNhanVien.");
            return false;
        }
        // Simply add the NhanVien. Account creation is handled separately by the calling panel.
        return nhanVienDAO.themNhanVien(nhanVien);
    }
    
    /**
     * Cập nhật thông tin nhân viên
     * @param nhanVien Thông tin nhân viên cần cập nhật
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    public boolean capNhatNhanVien(nhanVienDTO nhanVien) {
        // Kiểm tra dữ liệu đầu vào
        if (!validateNhanVienData(nhanVien)) {
            return false;
        }
        
        return nhanVienDAO.capNhatNhanVien(nhanVien);
    }
    
    /**
     * Xóa nhân viên
     * @param maNhanVien Mã nhân viên cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     */
    public boolean xoaNhanVien(String maNhanVien) {
        if (maNhanVien == null || maNhanVien.trim().isEmpty()) {
            return false;
        }
        
        return nhanVienDAO.xoaNhanVien(maNhanVien);
    }
    
    /**
     * Lấy danh sách nhân viên theo chức vụ
     * @param chucVu Chức vụ cần lấy danh sách nhân viên
     * @return Danh sách nhân viên thỏa mãn điều kiện
     */
    public List<nhanVienDTO> getNhanVienByChucVu(String chucVu) {
        return nhanVienDAO.getNhanVienByChucVu(chucVu);
    }
    
    /**
     * Lấy danh sách nhân viên theo trạng thái
     * @param trangThai Trạng thái cần lấy danh sách nhân viên
     * @return Danh sách nhân viên thỏa mãn điều kiện
     */
    public List<nhanVienDTO> getNhanVienByTrangThai(String trangThai) {
        return nhanVienDAO.getNhanVienByTrangThai(trangThai);
    }
    
    /**
     * Kiểm tra email có tồn tại trong hệ thống
     * @param email Email cần kiểm tra
     * @return true nếu email tồn tại, false nếu không tồn tại
     */
    public boolean kiemTraEmailTonTai(String email) {
        return nhanVienDAO.kiemTraEmailTonTai(email);
    }
    
    /**
     * Kiểm tra số điện thoại có tồn tại trong hệ thống
     * @param soDienThoai Số điện thoại cần kiểm tra
     * @return true nếu số điện thoại tồn tại, false nếu không tồn tại
     */
    public boolean kiemTraSoDienThoaiTonTai(String soDienThoai) {
        return nhanVienDAO.kiemTraSoDienThoaiTonTai(soDienThoai);
    }
    
    /**
     * Kiểm tra tính hợp lệ của dữ liệu nhân viên
     * @param nhanVien Thông tin nhân viên cần kiểm tra
     * @return true nếu dữ liệu hợp lệ, false nếu không hợp lệ
     */
    private boolean validateNhanVienData(nhanVienDTO nhanVien) {
        if (nhanVien == null) {
            return false;
        }
        
        // Kiểm tra họ tên
        if (nhanVien.getHoTen() == null || nhanVien.getHoTen().trim().isEmpty()) {
            return false;
        }
        
        // Kiểm tra email
        if (nhanVien.getEmail() != null && !nhanVien.getEmail().trim().isEmpty()) {
            String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
            if (!Pattern.matches(emailPattern, nhanVien.getEmail())) {
                return false;
            }
        }
        
        // Kiểm tra số điện thoại
        if (nhanVien.getSoDienThoai() != null && !nhanVien.getSoDienThoai().trim().isEmpty()) {
            String phonePattern = "^\\d{10,11}$";
            if (!Pattern.matches(phonePattern, nhanVien.getSoDienThoai())) {
                return false;
            }
        }
        
        // Kiểm tra ngày sinh
        if (nhanVien.getNgaySinh() != null) {
            Date currentDate = new Date(System.currentTimeMillis());
            if (nhanVien.getNgaySinh().after(currentDate)) {
                return false;
            }
        }
        
        // Kiểm tra mức lương
        if (nhanVien.getMucLuong() != null && nhanVien.getMucLuong().compareTo(java.math.BigDecimal.ZERO) < 0) {
            return false;
        }
        
        return true;
    }
}
