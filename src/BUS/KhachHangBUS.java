package BUS;

import DAO.KhachHangDAO;
import DTO.khachHangDTO;
import java.util.List;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.regex.Pattern;
import java.io.File;

public class KhachHangBUS {
    private KhachHangDAO khachHangDAO;
    
    public KhachHangBUS() {
        this.khachHangDAO = new KhachHangDAO();
    }
    
    /**
     * Lấy danh sách tất cả khách hàng
     * @return Danh sách khách hàng
     */
    public List<khachHangDTO> getAllKhachHang() {
        return khachHangDAO.getAllKhachHang();
    }
    
    /**
     * Tìm kiếm khách hàng theo từ khóa và loại tìm kiếm
     * @param keyword Từ khóa tìm kiếm
     * @param searchType Loại tìm kiếm
     * @return Danh sách khách hàng thỏa mãn điều kiện
     */
    public List<khachHangDTO> searchKhachHang(String keyword, String searchType) {
        return khachHangDAO.searchKhachHang(keyword, searchType);
    }
    
    /**
     * Thêm khách hàng mới
     * @param khachHang Thông tin khách hàng cần thêm
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public boolean themKhachHang(khachHangDTO khachHang) {
        if (!validateKhachHangData(khachHang)) {
            return false;
        }
        return khachHangDAO.themKhachHang(khachHang);
    }
    
    /**
     * Cập nhật thông tin khách hàng
     * @param khachHang Thông tin khách hàng cần cập nhật
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    public boolean capNhatKhachHang(khachHangDTO khachHang) {
        if (!validateKhachHangData(khachHang)) {
            return false;
        }
        return khachHangDAO.capNhatKhachHang(khachHang);
    }
    
    /**
     * Xóa khách hàng
     * @param maKhachHang Mã khách hàng cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     * @throws RuntimeException nếu khách hàng có dữ liệu liên quan không thể xóa
     */
    public boolean xoaKhachHang(String maKhachHang) {
        if (maKhachHang == null || maKhachHang.trim().isEmpty()) {
            throw new RuntimeException("Mã khách hàng không hợp lệ");
        }
        
        boolean result = khachHangDAO.xoaKhachHang(maKhachHang);
        
        if (!result) {
            throw new RuntimeException("Không thể xóa khách hàng vì khách hàng này đã có dữ liệu xuất hàng. " +
                                    "Vui lòng xóa dữ liệu xuất hàng trước khi xóa khách hàng.");
        }
        
        return true;
    }
    
    /**
     * Kiểm tra tính hợp lệ của dữ liệu khách hàng
     * @param khachHang Thông tin khách hàng cần kiểm tra
     * @return true nếu dữ liệu hợp lệ, false nếu không hợp lệ
     */
    private boolean validateKhachHangData(khachHangDTO khachHang) {
        if (khachHang == null) {
            return false;
        }
        
        if (khachHang.getHoTen() == null || khachHang.getHoTen().trim().isEmpty()) {
            return false;
        }
        
        if (khachHang.getEmail() != null && !khachHang.getEmail().trim().isEmpty()) {
            String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
            if (!Pattern.matches(emailPattern, khachHang.getEmail())) {
                return false;
            }
        }
        
        if (khachHang.getSoDienThoai() != null && !khachHang.getSoDienThoai().trim().isEmpty()) {
            String phonePattern = "^\\d{10,11}$";
            if (!Pattern.matches(phonePattern, khachHang.getSoDienThoai())) {
                return false;
            }
        }
        
        if (khachHang.getNgaySinh() != null) {
            Date currentDate = new Date(System.currentTimeMillis());
            if (khachHang.getNgaySinh().after(currentDate)) {
                return false;
            }
        }
        
        return true;
    }

    public List<String> getAllMaKhachHang() {
        return khachHangDAO.getAllMaKhachHang();
    }

    public khachHangDTO getKhachHangByMa(String maKhachHang) {
        return khachHangDAO.getKhachHangByMa(maKhachHang);
    }

    public boolean importKhachHang(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            System.err.println("Đường dẫn file không hợp lệ");
            return false;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("File không tồn tại");
            return false;
        }

        if (!filePath.toLowerCase().endsWith(".csv")) {
            System.err.println("File phải có định dạng CSV");
            return false;
        }

        return khachHangDAO.importKhachHang(filePath);
    }
}
