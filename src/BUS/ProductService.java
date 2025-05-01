package BUS;

import DAO.LoaiSanPhamDAO;
import DTO.sanPhamDTO;
import java.util.List;

public class ProductService {
    private LoaiSanPhamDAO loaiSanPhamDAO;

    public ProductService() {
        this.loaiSanPhamDAO = new LoaiSanPhamDAO();
    }

    public boolean addProduct(sanPhamDTO product) throws Exception {
        if (product.getTenSanPham() == null || product.getTenSanPham().trim().isEmpty()) {
            throw new Exception("Tên sản phẩm không được để trống.");
        }
        if (product.getMaNhaCungCap() == null || product.getMaNhaCungCap().trim().isEmpty()) {
            throw new Exception("Mã nhà cung cấp không được để trống.");
        }
        if (product.getMaDanhMuc() == null || product.getMaDanhMuc().trim().isEmpty()) {
            throw new Exception("Loại sản phẩm không được để trống.");
        }
        if (product.getMauSac() == null || product.getMauSac().trim().isEmpty()) {
            throw new Exception("Màu sắc không được để trống.");
        }
        if (product.getSize() == null || product.getSize().trim().isEmpty()) {
            throw new Exception("Kích cỡ không được để trống.");
        }
        if (product.getImgURL() == null || product.getImgURL().trim().isEmpty()) {
            throw new Exception("Hình ảnh không được để trống.");
        }
        if (product.getTrangThai() == null || product.getTrangThai().trim().isEmpty()) {
            throw new Exception("Trạng thái không được để trống.");
        }

        if (!loaiSanPhamDAO.isMaNhaCungCapExists(product.getMaNhaCungCap())) {
            throw new Exception("Mã nhà cung cấp " + product.getMaNhaCungCap() + " không tồn tại.");
        }

        if (product.getSoLuongTonKho() < 0) {
            throw new Exception("Số lượng tồn kho không được âm.");
        }

        if (product.getGiaBan() <= 0) {
            throw new Exception("Giá bán phải lớn hơn 0.");
        }

        String imgURL = product.getImgURL().toLowerCase();
        if (!imgURL.endsWith(".jpg") && !imgURL.endsWith(".jpeg") &&
                !imgURL.endsWith(".png") && !imgURL.endsWith(".gif")) {
            throw new Exception("Hình ảnh phải có định dạng .jpg, .jpeg, .png hoặc .gif.");
        }

        if (product.getSoLuongTonKho() == 0) {
            product.setTrangThai("Hết hàng");
        } else {
            product.setTrangThai("Còn hàng");
        }

        return loaiSanPhamDAO.themLoaiSanPham(product);
    }

    public boolean updateProduct(sanPhamDTO product) throws Exception {
        if (product.getMaSanPham() == null || product.getMaSanPham().trim().isEmpty()) {
            throw new Exception("Mã sản phẩm không được để trống.");
        }
        if (product.getTenSanPham() == null || product.getTenSanPham().trim().isEmpty()) {
            throw new Exception("Tên sản phẩm không được để trống.");
        }
        if (product.getMaNhaCungCap() == null || product.getMaNhaCungCap().trim().isEmpty()) {
            throw new Exception("Mã nhà cung cấp không được để trống.");
        }
        if (product.getMaDanhMuc() == null || product.getMaDanhMuc().trim().isEmpty()) {
            throw new Exception("Loại sản phẩm không được để trống.");
        }
        if (product.getMauSac() == null || product.getMauSac().trim().isEmpty()) {
            throw new Exception("Màu sắc không được để trống.");
        }
        if (product.getSize() == null || product.getSize().trim().isEmpty()) {
            throw new Exception("Kích cỡ không được để trống.");
        }
        if (product.getImgURL() == null || product.getImgURL().trim().isEmpty()) {
            throw new Exception("Hình ảnh không được để trống.");
        }
        if (product.getTrangThai() == null || product.getTrangThai().trim().isEmpty()) {
            throw new Exception("Trạng thái không được để trống.");
        }

        if (product.getSoLuongTonKho() < 0) {
            throw new Exception("Số lượng tồn kho không được âm.");
        }

        if (product.getGiaBan() <= 0) {
            throw new Exception("Giá bán phải lớn hơn 0.");
        }

        String imgURL = product.getImgURL().toLowerCase();
        if (!imgURL.endsWith(".jpg") && !imgURL.endsWith(".jpeg") &&
                !imgURL.endsWith(".png") && !imgURL.endsWith(".gif")) {
            throw new Exception("Hình ảnh phải có định dạng .jpg, .jpeg, .png hoặc .gif.");
        }

        if (product.getSoLuongTonKho() == 0) {
            product.setTrangThai("Hết hàng");
        } else {
            product.setTrangThai("Còn hàng");
        }

        return loaiSanPhamDAO.capNhatLoaiSanPham(product);
    }

    public boolean deleteProduct(String maSanPham) throws Exception {
        if (maSanPham == null || maSanPham.trim().isEmpty()) {
            throw new Exception("Mã sản phẩm không được để trống.");
        }

        if (!loaiSanPhamDAO.isMaSanPhamExists(maSanPham)) {
            throw new Exception("Sản phẩm với mã " + maSanPham + " không tồn tại.");
        }

        return loaiSanPhamDAO.xoaLoaiSanPham(maSanPham);
    }

    public List<sanPhamDTO> searchProducts(String keyword, String searchType) {
        return loaiSanPhamDAO.searchLoaiSanPham(keyword, searchType);
    }

    public List<sanPhamDTO> getAllProducts() {
        return loaiSanPhamDAO.getAllSanPham();
    }

    public sanPhamDTO getProductById(String maSanPham) throws Exception {
        if (maSanPham == null || maSanPham.trim().isEmpty()) {
            throw new Exception("Mã sản phẩm không được để trống.");
        }
        sanPhamDTO product = loaiSanPhamDAO.getLoaiSanPhamByMa(maSanPham);
        if (product == null) {
            throw new Exception("Sản phẩm với mã " + maSanPham + " không tồn tại.");
        }
        return product;
    }

    public boolean isMaNhaCungCapValid(String maNhaCungCap) {
        return loaiSanPhamDAO.isMaNhaCungCapExists(maNhaCungCap);
    }

    public boolean updateStock(String maSanPham, int soLuongMoi) throws Exception {
        if (soLuongMoi < 0) {
            throw new Exception("Số lượng tồn kho không được âm.");
        }
        boolean success = loaiSanPhamDAO.capNhatSoLuong(maSanPham, soLuongMoi);
        if (success) {
            String trangThai = soLuongMoi == 0 ? "Hết hàng" : "Còn hàng";
            loaiSanPhamDAO.capNhatTrangThai(maSanPham, trangThai);
        }
        return success;
    }

    public String generateProductCode() {
        return loaiSanPhamDAO.generateMaSanPham();
    }
}