package BUS;

import DAO.SanPhamDAO;
import DTO.sanPhamDTO;
import java.util.List;
import java.util.stream.Collectors;

public class SanPhamBUS {
    private SanPhamDAO sanPhamDAO;

    public SanPhamBUS() {
        this.sanPhamDAO = new SanPhamDAO();
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
} 