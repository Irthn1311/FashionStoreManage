package BUS;

import DAO.SanPhamDAO;
import DTO.sanPhamDTO;
import java.util.List;

public class SanPhamBUS {
    private SanPhamDAO sanPhamDAO;

    public SanPhamBUS() {
        this.sanPhamDAO = new SanPhamDAO();
    }

    public List<String> getAllMaSanPham() {
        return sanPhamDAO.getAllMaSanPham();
    }

    public sanPhamDTO getSanPhamByMa(String maSanPham) {
        return sanPhamDAO.getSanPhamByMa(maSanPham);
    }

    public boolean updateSanPham(sanPhamDTO sp) {
        return sanPhamDAO.updateSanPham(sp);
    }
} 