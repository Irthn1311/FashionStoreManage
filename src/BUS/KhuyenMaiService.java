package BUS;

import DAO.KhuyenMaiDAO;
import DTO.khuyenMaiDTO;
import DTO.sanPhamDTO;
import DAO.LoaiSanPhamDAO;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class KhuyenMaiService {
    private KhuyenMaiDAO khuyenMaiDAO;
    private LoaiSanPhamDAO loaiSanPhamDAO;

    public KhuyenMaiService() {
        this.khuyenMaiDAO = new KhuyenMaiDAO();
        this.loaiSanPhamDAO = new LoaiSanPhamDAO();
    }

    public List<khuyenMaiDTO> getAllKhuyenMai() {
        return khuyenMaiDAO.getAllKhuyenMai();
    }

    public List<khuyenMaiDTO> searchKhuyenMai(String keyword, String searchField) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Từ khóa tìm kiếm không được để trống.");
        }
        if (searchField == null || searchField.trim().isEmpty()) {
            throw new IllegalArgumentException("Trường tìm kiếm không được để trống.");
        }

        List<khuyenMaiDTO> allPromotions = khuyenMaiDAO.getAllKhuyenMai();
        String keywordLower = keyword.toLowerCase();

        switch (searchField.toLowerCase()) {
            case "mã km":
                return allPromotions.stream()
                        .filter(km -> km.getMaKhuyenMai().toLowerCase().contains(keywordLower))
                        .collect(Collectors.toList());
            case "mã sản phẩm":
                return allPromotions.stream()
                        .filter(km -> km.getMaSanPham().toLowerCase().contains(keywordLower))
                        .collect(Collectors.toList());
            case "tên sản phẩm":
                return allPromotions.stream()
                        .filter(km -> km.getTenSanPham().toLowerCase().contains(keywordLower))
                        .collect(Collectors.toList());
            case "tên chương trình":
                return allPromotions.stream()
                        .filter(km -> km.getTenChuongTrinh().toLowerCase().contains(keywordLower))
                        .collect(Collectors.toList());
            case "trạng thái":
                return allPromotions.stream()
                        .filter(km -> km.getTrangThai().toLowerCase().contains(keywordLower))
                        .collect(Collectors.toList());
            case "tất cả":
                return allPromotions.stream()
                        .filter(km -> km.getMaKhuyenMai().toLowerCase().contains(keywordLower) ||
                                km.getMaSanPham().toLowerCase().contains(keywordLower) ||
                                km.getTenSanPham().toLowerCase().contains(keywordLower) ||
                                km.getTenChuongTrinh().toLowerCase().contains(keywordLower) ||
                                km.getTrangThai().toLowerCase().contains(keywordLower))
                        .collect(Collectors.toList());
            default:
                throw new IllegalArgumentException("Trường tìm kiếm không hợp lệ.");
        }
    }

    public boolean addKhuyenMai(khuyenMaiDTO km) throws Exception {
        validateKhuyenMai(km);

        // Check if the product exists
        sanPhamDTO product = loaiSanPhamDAO.getLoaiSanPhamByMa(km.getMaSanPham());
        if (product == null) {
            throw new Exception("Sản phẩm với mã " + km.getMaSanPham() + " không tồn tại.");
        }

        // Update product information in the promotion
        km.setTenSanPham(product.getTenSanPham());
        km.setGiaCu(product.getGiaBan());
        km.setGiaMoi(calculateNewPrice(km.getGiaCu(), km.getGiamGia()));

        return khuyenMaiDAO.addKhuyenMai(km);
    }

    public boolean updateKhuyenMai(khuyenMaiDTO km) throws Exception {
        validateKhuyenMai(km);

        // Check if the promotion exists
        List<khuyenMaiDTO> existingPromotions = khuyenMaiDAO.getAllKhuyenMai();
        boolean exists = existingPromotions.stream()
                .anyMatch(p -> p.getMaKhuyenMai().equals(km.getMaKhuyenMai()));
        if (!exists) {
            throw new Exception("Khuyến mãi với mã " + km.getMaKhuyenMai() + " không tồn tại.");
        }

        // Recalculate GiaMoi based on GiaCu and GiamGia
        km.setGiaMoi(calculateNewPrice(km.getGiaCu(), km.getGiamGia()));

        return khuyenMaiDAO.updateKhuyenMai(km);
    }

    public boolean deleteKhuyenMai(String maKhuyenMai) throws Exception {
        if (maKhuyenMai == null || maKhuyenMai.trim().isEmpty()) {
            throw new Exception("Mã khuyến mãi không được để trống.");
        }

        // Check if the promotion exists
        List<khuyenMaiDTO> existingPromotions = khuyenMaiDAO.getAllKhuyenMai();
        boolean exists = existingPromotions.stream()
                .anyMatch(p -> p.getMaKhuyenMai().equals(maKhuyenMai));
        if (!exists) {
            throw new Exception("Khuyến mãi với mã " + maKhuyenMai + " không tồn tại.");
        }

        return khuyenMaiDAO.deleteKhuyenMai(maKhuyenMai);
    }

    public String generateMaKhuyenMai() {
        return khuyenMaiDAO.generateMaKhuyenMai();
    }

    private void validateKhuyenMai(khuyenMaiDTO km) throws Exception {
        if (km.getMaKhuyenMai() == null || km.getMaKhuyenMai().trim().isEmpty()) {
            throw new Exception("Mã khuyến mãi không được để trống.");
        }
        if (km.getMaSanPham() == null || km.getMaSanPham().trim().isEmpty()) {
            throw new Exception("Mã sản phẩm không được để trống.");
        }
        if (km.getTenSanPham() == null || km.getTenSanPham().trim().isEmpty()) {
            throw new Exception("Tên sản phẩm không được để trống.");
        }
        if (km.getTenChuongTrinh() == null || km.getTenChuongTrinh().trim().isEmpty()) {
            throw new Exception("Tên chương trình không được để trống.");
        }
        if (km.getGiamGia() < 0 || km.getGiamGia() > 100) {
            throw new Exception("Giảm giá phải nằm trong khoảng 0-100%.");
        }
        if (km.getNgayBatDau() == null) {
            throw new Exception("Ngày bắt đầu không được để trống.");
        }
        if (km.getNgayKetThuc() == null) {
            throw new Exception("Ngày kết thúc không được để trống.");
        }
        if (km.getNgayKetThuc().before(km.getNgayBatDau()) || km.getNgayKetThuc().equals(km.getNgayBatDau())) {
            throw new Exception("Ngày kết thúc phải sau ngày bắt đầu.");
        }
        if (km.getGiaCu() <= 0) {
            throw new Exception("Giá cũ phải lớn hơn 0.");
        }
        if (km.getGiaMoi() <= 0) {
            throw new Exception("Giá mới phải lớn hơn 0.");
        }
    }

    private double calculateNewPrice(double giaCu, double giamGia) {
        return giaCu * (1 - giamGia / 100);
    }

    public sanPhamDTO getProductByMa(String maSanPham) throws Exception {
        if (maSanPham == null || maSanPham.trim().isEmpty()) {
            throw new Exception("Mã sản phẩm không được để trống.");
        }
        return loaiSanPhamDAO.getLoaiSanPhamByMa(maSanPham);
    }
}