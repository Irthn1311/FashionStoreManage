package BUS;

import DAO.NhapHangDAO;
import DAO.SanPhamDAO;
import DAO.NhaCungCapDAO;
import DTO.nhapHangDTO;
import java.util.List;
import java.util.Date;

public class NhapHangBUS {
    private NhapHangDAO nhapHangDAO;
    private SanPhamDAO sanPhamDAO;
    private NhaCungCapDAO nhaCungCapDAO;
    
    // Business rules constants
    private static final int MIN_IMPORT_QUANTITY = 5;
    private static final double MIN_IMPORT_VALUE = 1000000.0; // 1 million VND
    
    public NhapHangBUS() {
        nhapHangDAO = new NhapHangDAO();
        sanPhamDAO = new SanPhamDAO();
        nhaCungCapDAO = new NhaCungCapDAO();
    }
    
    /**
     * Validate import data according to business rules
     * @param nhapHang Import data to validate
     * @return true if valid, false otherwise
     */
    private boolean validateImportData(nhapHangDTO nhapHang) {
        // Check required fields
        if (nhapHang.getMaNhaCungCap() == null || nhapHang.getMaNhaCungCap().trim().isEmpty() ||
            nhapHang.getMaSanPham() == null || nhapHang.getMaSanPham().trim().isEmpty() ||
            nhapHang.getSoLuong() == null || nhapHang.getSoLuong().trim().isEmpty() ||
            nhapHang.getDonGia() == null || nhapHang.getDonGia().trim().isEmpty()) {
            return false;
        }
        
        // Check minimum quantity
        try {
            int soLuong = Integer.parseInt(nhapHang.getSoLuong());
            if (soLuong < MIN_IMPORT_QUANTITY) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        
        // Check minimum import value
        try {
            double donGia = Double.parseDouble(nhapHang.getDonGia());
            int soLuong = Integer.parseInt(nhapHang.getSoLuong());
            if (donGia * soLuong < MIN_IMPORT_VALUE) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        
        // Check if supplier exists and is active
        if (!nhaCungCapDAO.isSupplierActive(nhapHang.getMaNhaCungCap())) {
            return false;
        }
        
        // Check if product exists
        if (!sanPhamDAO.isProductExists(nhapHang.getMaSanPham())) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Add new import record
     * @param nhapHang Import data
     * @return true if successful, false otherwise
     */
    public boolean themNhapHang(nhapHangDTO nhapHang) {
        if (!validateImportData(nhapHang)) {
            return false;
        }
        
        // Generate unique import ID
        nhapHang.setMaPN("PN" + System.currentTimeMillis());
        
        // Set default status
        nhapHang.setTrangThai("Chưa nhập");
        
        // Calculate total amount
        try {
            double donGia = Double.parseDouble(nhapHang.getDonGia());
            int soLuong = Integer.parseInt(nhapHang.getSoLuong());
            nhapHang.setThanhTien(String.valueOf(donGia * soLuong));
        } catch (NumberFormatException e) {
            return false;
        }
        
        return nhapHangDAO.themNhapHang(nhapHang);
    }
    
    /**
     * Get all import records
     * @return List of import records
     */
    public List<nhapHangDTO> getAllNhapHang() {
        return nhapHangDAO.getAllNhapHang();
    }
    
    /**
     * Update import status
     * @param maPN Import ID
     * @param trangThai New status
     * @return true if successful, false otherwise
     */
    public boolean capNhatTrangThai(String maPN, String trangThai) {
        if (maPN == null || maPN.trim().isEmpty() || trangThai == null || trangThai.trim().isEmpty()) {
            return false;
        }
        
        // Validate status transition
        nhapHangDTO nhapHang = getNhapHangByMa(maPN);
        if (nhapHang == null) {
            return false;
        }
        
        // Only allow specific status transitions
        if (nhapHang.getTrangThai().equals("Chưa nhập") && !trangThai.equals("Đã nhập")) {
            return false;
        }
        
        return nhapHangDAO.capNhatTrangThai(maPN, trangThai);
    }
    
    /**
     * Get import record by ID
     * @param maPN Import ID
     * @return Import record or null if not found
     */
    public nhapHangDTO getNhapHangByMa(String maPN) {
        if (maPN == null || maPN.trim().isEmpty()) {
            return null;
        }
        return nhapHangDAO.getNhapHangByMa(maPN);
    }
    
    /**
     * Delete import record
     * @param maPN Import ID
     * @return true if successful, false otherwise
     */
    public boolean xoaNhapHang(String maPN) {
        if (maPN == null || maPN.trim().isEmpty()) {
            return false;
        }
        
        // Only allow deletion of records with "Chưa nhập" status
        nhapHangDTO nhapHang = getNhapHangByMa(maPN);
        if (nhapHang == null || !nhapHang.getTrangThai().equals("Chưa nhập")) {
            return false;
        }
        
        return nhapHangDAO.xoaNhapHang(maPN);
    }
    
    /**
     * Search import records
     * @param keyword Search keyword
     * @param searchType Search type (MaPN, MaNhaCungCap, MaSanPham)
     * @return List of matching import records
     */
    public List<nhapHangDTO> searchNhapHang(String keyword, String searchType) {
        if (keyword == null || keyword.trim().isEmpty() || searchType == null || searchType.trim().isEmpty()) {
            return null;
        }
        return nhapHangDAO.searchNhapHang(keyword, searchType);
    }
}
