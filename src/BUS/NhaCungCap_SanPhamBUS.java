package BUS;

import DAO.NhaCungCap_SanPhamDAO;
import DTO.NhaCungCap_SanPhamDTO;
import java.util.List;

public class NhaCungCap_SanPhamBUS {
    private NhaCungCap_SanPhamDAO nhaCungCap_SanPhamDAO;
    
    public NhaCungCap_SanPhamBUS() {
        nhaCungCap_SanPhamDAO = new NhaCungCap_SanPhamDAO();
    }
    
    public List<NhaCungCap_SanPhamDTO> getAllNhaCungCap_SanPham() {
        return nhaCungCap_SanPhamDAO.getAllNhaCungCap_SanPham();
    }
    
    public boolean themNhaCungCap_SanPham(NhaCungCap_SanPhamDTO nccsp) {
        // Kiểm tra dữ liệu đầu vào
        if (nccsp.getMaNhaCungCap() == null || nccsp.getMaNhaCungCap().trim().isEmpty() ||
            nccsp.getMaSanPham() == null || nccsp.getMaSanPham().trim().isEmpty()) {
            return false;
        }
        
        return nhaCungCap_SanPhamDAO.themNhaCungCap_SanPham(nccsp);
    }
    
    public boolean xoaNhaCungCap_SanPham(String maNhaCungCap, String maSanPham) {
        // Kiểm tra dữ liệu đầu vào
        if (maNhaCungCap == null || maNhaCungCap.trim().isEmpty() ||
            maSanPham == null || maSanPham.trim().isEmpty()) {
            return false;
        }
        
        return nhaCungCap_SanPhamDAO.xoaNhaCungCap_SanPham(maNhaCungCap, maSanPham);
    }
    
    public List<NhaCungCap_SanPhamDTO> getSanPhamByNhaCungCap(String maNhaCungCap) {
        // Kiểm tra dữ liệu đầu vào
        if (maNhaCungCap == null || maNhaCungCap.trim().isEmpty()) {
            return null;
        }
        
        return nhaCungCap_SanPhamDAO.getSanPhamByNhaCungCap(maNhaCungCap);
    }
    
    public List<NhaCungCap_SanPhamDTO> getNhaCungCapBySanPham(String maSanPham) {
        // Kiểm tra dữ liệu đầu vào
        if (maSanPham == null || maSanPham.trim().isEmpty()) {
            return null;
        }
        
        return nhaCungCap_SanPhamDAO.getNhaCungCapBySanPham(maSanPham);
    }
} 