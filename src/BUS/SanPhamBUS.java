package BUS;

import DAO.SanPhamDAO;
import DTO.sanPhamDTO;
import java.util.List;
import java.util.stream.Collectors;
import DAO.KhuyenMaiDAO;

public class SanPhamBUS {
    private SanPhamDAO sanPhamDAO;

    public SanPhamBUS() {
        this.sanPhamDAO = new SanPhamDAO();
    }

    public List<sanPhamDTO> getAll() {
        return sanPhamDAO.getAllSanPham();
    }

    public List<String> getAllProductCodes() {
        List<sanPhamDTO> products = sanPhamDAO.getAll();
        return products.stream()
                .map(sanPhamDTO::getMaSanPham)
                .collect(Collectors.toList());
    }

    public sanPhamDTO getSanPhamByMa(String maSanPham) {
        return sanPhamDAO.getSanPhamByMa(maSanPham);
    }

    public boolean updateSanPham(sanPhamDTO sp) {
        return sanPhamDAO.updateSanPham(sp);
    }

    public void updateProductStatus() {
        sanPhamDAO.updateProductStatus();
    }

    public boolean isProductExists(String maSanPham) {
        return sanPhamDAO.isProductExists(maSanPham);
    }

    public boolean updateProductQuantity(String maSanPham, int quantity) {
        return sanPhamDAO.updateProductQuantity(maSanPham, quantity);
    }

    public boolean checkProductQuantity(String maSanPham, int soLuong) {
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        return sanPhamDAO.checkProductQuantity(maSanPham, soLuong);
    }

    public List<sanPhamDTO> getSanPhamByMaNhaCungCap(String maNhaCungCap) {
        return sanPhamDAO.getSanPhamByMaNhaCungCap(maNhaCungCap);
    }

    public double getGiaGocSanPham(String maSanPham) {
        KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();
        double giaGoc = khuyenMaiDAO.getGiaGocSanPham(maSanPham);
        if (giaGoc <= 0) {
            sanPhamDTO sp = getSanPhamByMa(maSanPham);
            if (sp != null) {
                giaGoc = sp.getGiaBan();
                System.out.println("Info: Không tìm thấy giá gốc trong KhuyenMai cho " + maSanPham
                        + ", sử dụng giá bán: " + giaGoc);
            } else {
                System.out.println("Warning: Không tìm thấy sản phẩm " + maSanPham);
            }
        } else {
            System.out.println("Info: Đã tìm thấy giá gốc trong KhuyenMai cho " + maSanPham + ": " + giaGoc);
        }
        return giaGoc;
    }
}