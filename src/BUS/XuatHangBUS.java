package BUS;

import DAO.XuatHangDAO;
import DAO.SanPhamDAO;
import DAO.KhachHangDAO;
import DTO.xuatHangDTO;
import java.util.List;
import java.util.Date;

public class XuatHangBUS {
    private XuatHangDAO xuatHangDAO;
    private SanPhamDAO sanPhamDAO;
    private KhachHangDAO khachHangDAO;
    
    // Business rule constants
    private static final int MIN_EXPORT_QUANTITY = 1;
    private static final double MIN_EXPORT_VALUE = 100000; // 100,000 VND
    
    public XuatHangBUS() {
        this.xuatHangDAO = new XuatHangDAO();
        this.sanPhamDAO = new SanPhamDAO();
        this.khachHangDAO = new KhachHangDAO();
    }
    
    public boolean validateExportData(xuatHangDTO xuatHang) {
        // Validate required fields
        if (xuatHang.getMaKhachHang() == null || xuatHang.getMaKhachHang().trim().isEmpty() ||
            xuatHang.getMaSanPham() == null || xuatHang.getMaSanPham().trim().isEmpty() ||
            xuatHang.getSoLuong() == null || xuatHang.getSoLuong().trim().isEmpty() ||
            xuatHang.getDonGia() == null || xuatHang.getDonGia().trim().isEmpty()) {
            return false;
        }
        
        // Validate customer exists
        if (!khachHangDAO.isCustomerExists(xuatHang.getMaKhachHang())) {
            return false;
        }
        
        // Validate product exists
        if (!sanPhamDAO.isProductExists(xuatHang.getMaSanPham())) {
            return false;
        }
        
        // Validate quantity
        int soLuong = Integer.parseInt(xuatHang.getSoLuong());
        if (soLuong < MIN_EXPORT_QUANTITY) {
            return false;
        }
        
        // Validate stock availability
        if (!sanPhamDAO.kiemTraTonKho(xuatHang.getMaSanPham(), soLuong)) {
            return false;
        }
        
        // Validate total value
        double donGia = Double.parseDouble(xuatHang.getDonGia());
        double thanhTien = soLuong * donGia;
        if (thanhTien < MIN_EXPORT_VALUE) {
            return false;
        }
        
        return true;
    }
    
    public boolean themXuatHang(xuatHangDTO xuatHang) {
        if (!validateExportData(xuatHang)) {
            return false;
        }
        
        // Calculate total amount
        int soLuong = Integer.parseInt(xuatHang.getSoLuong());
        double donGia = Double.parseDouble(xuatHang.getDonGia());
        double thanhTien = soLuong * donGia;
        xuatHang.setThanhTien(String.valueOf(thanhTien));
        
        // Set initial status
        xuatHang.setTrangThai("Chưa xuất");
        
        // Add export record
        if (xuatHangDAO.themXuatHang(xuatHang)) {
            // Update product quantity
            return sanPhamDAO.giamSoLuongTonKho(xuatHang.getMaSanPham(), soLuong);
        }
        
        return false;
    }
    
    public List<xuatHangDTO> getAllXuatHang() {
        return xuatHangDAO.getAllXuatHang();
    }
    
    public boolean capNhatTrangThai(String maPX, String trangThai) {
        xuatHangDTO xuatHang = xuatHangDAO.getXuatHangByMa(maPX);
        if (xuatHang == null) {
            return false;
        }
        
        // Validate status transition
        if (!xuatHang.getTrangThai().equals("Chưa xuất") && trangThai.equals("Đã xuất")) {
            return false;
        }
        
        return xuatHangDAO.capNhatTrangThai(maPX, trangThai);
    }
    
    public xuatHangDTO getXuatHangByMa(String maPX) {
        return xuatHangDAO.getXuatHangByMa(maPX);
    }
    
    public boolean xoaXuatHang(String maPX) {
        xuatHangDTO xuatHang = xuatHangDAO.getXuatHangByMa(maPX);
        if (xuatHang == null || !xuatHang.getTrangThai().equals("Chưa xuất")) {
            return false;
        }
        
        // Restore product quantity
        int soLuong = Integer.parseInt(xuatHang.getSoLuong());
        if (sanPhamDAO.capNhatSoLuongSanPham(xuatHang.getMaSanPham(), soLuong)) {
            return xuatHangDAO.xoaXuatHang(maPX);
        }
        
        return false;
    }
    
    public List<xuatHangDTO> searchXuatHang(String keyword, String searchType) {
        return xuatHangDAO.searchXuatHang(keyword, searchType);
    }
} 