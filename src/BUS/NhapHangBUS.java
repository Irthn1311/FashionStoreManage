package BUS;

import DAO.NhapHangDAO;
import DAO.SanPhamDAO;
import DAO.NhaCungCap_SanPhamDAO;
import DTO.nhapHangDTO;
import DTO.sanPhamDTO;
import DTO.NhaCungCap_SanPhamDTO;
import java.util.List;
import java.util.Date;
import java.util.stream.Collectors;
import BUS.PhieuNhapBUS;
import DTO.PhieuNhapDTO;

public class NhapHangBUS {
    private NhapHangDAO nhapHangDAO;
    private SanPhamDAO sanPhamDAO;
    private NhaCungCap_SanPhamDAO nccspDAO;
    
    // Business rules constants
    private static final int MIN_IMPORT_QUANTITY = 5;
    private static final double MIN_IMPORT_VALUE = 1000000.0; // 1 million VND
    
    public NhapHangBUS() {
        nhapHangDAO = new NhapHangDAO();
        sanPhamDAO = new SanPhamDAO();
        nccspDAO = new NhaCungCap_SanPhamDAO();
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
            if (soLuong <= 0) {
                return false;
            }
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
        if (!nccspDAO.isSupplierActive(nhapHang.getMaNhaCungCap())) {
            return false;
        }
        
        // Sửa đoạn này: kiểm tra mã sản phẩm gốc (cắt hậu tố NCC nếu có)
        String maSPGoc = nhapHang.getMaSanPham();
        if (maSPGoc.contains("_")) {
            maSPGoc = maSPGoc.split("_")[0];
        }
        if (!sanPhamDAO.isProductExists(maSPGoc)) {
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
        try {
            // Kiểm tra và thêm mối quan hệ nhà cung cấp-sản phẩm
            List<NhaCungCap_SanPhamDTO> existingRelations = nccspDAO.getNhaCungCapBySanPham(nhapHang.getMaSanPham());
            boolean relationExists = existingRelations.stream()
                .anyMatch(rel -> rel.getMaNhaCungCap().equals(nhapHang.getMaNhaCungCap()));

            if (!relationExists) {
                NhaCungCap_SanPhamDTO newRelation = new NhaCungCap_SanPhamDTO(nhapHang.getMaNhaCungCap(), nhapHang.getMaSanPham());
                if (!nccspDAO.themNhaCungCap_SanPham(newRelation)) {
                    return false;
                }
            }

            

            // Thêm phiếu nhập
            return nhapHangDAO.themNhapHang(nhapHang);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

        // Chỉ cho phép xóa khi trạng thái là "Đang xử lý"
        nhapHangDTO nhapHang = getNhapHangByMa(maPN);
        if (nhapHang == null || !nhapHang.getTrangThai().equals("Đang xử lý")) {
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

    public boolean xuLyPhieuNhap() {
        try {
            boolean success = true;
            List<nhapHangDTO> processingItems = nhapHangDAO.getAllNhapHang().stream()
                .filter(item -> "Đang xử lý".equals(item.getTrangThai()))
                .collect(Collectors.toList());

            for (nhapHangDTO item : processingItems) {
                // Cập nhật số lượng sản phẩm
                if (!sanPhamDAO.updateProductQuantity(item.getMaSanPham(), 
                    Integer.parseInt(item.getSoLuong()))) {
                    success = false;
                    break;
                }

                // Cập nhật trạng thái phiếu nhập
                if (!nhapHangDAO.capNhatTrangThai(item.getMaPN(), "Đã xử lý")) {
                    success = false;
                    break;
                }
            }

            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean chuyenNhapHangSangPhieuNhap() {
        List<nhapHangDTO> list = nhapHangDAO.getAllNhapHang();
        PhieuNhapBUS phieuNhapBUS = new PhieuNhapBUS();
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        boolean allSuccess = true;

        for (nhapHangDTO nh : list) {
            if ("Đang xử lý".equals(nh.getTrangThai())) {
                // Tạo đối tượng PhieuNhapDTO từ nhapHangDTO
                PhieuNhapDTO pn = new PhieuNhapDTO();
                String maPhieuNhap = nh.getMaPN();
                pn.setMaPhieuNhap(maPhieuNhap);
                pn.setMaNhaCungCap(nh.getMaNhaCungCap());
                pn.setMaSanPham(nh.getMaSanPham());
                pn.setTenSanPham(nh.getTenSanPham());
                pn.setSoLuong(Integer.parseInt(nh.getSoLuong()));
                // kiểm tra null/rỗng cho thoiGian
                if (nh.getThoiGian() == null || nh.getThoiGian().trim().isEmpty()) {
                    pn.setThoiGian(new java.util.Date());
                } else {
                    try {
                        pn.setThoiGian(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(nh.getThoiGian()));
                    } catch (Exception e) {
                        pn.setThoiGian(new java.util.Date());
                    }
                }
                pn.setDonGia(Double.parseDouble(nh.getDonGia()));
                pn.setTrangThai("Hoàn thành");
                pn.setHinhThucThanhToan(nh.getHinhThucThanhToan());
                pn.setThanhTien(Double.parseDouble(nh.getThanhTien()));

                // Thêm vào bảng PhieuNhap
                boolean ok = phieuNhapBUS.createPhieuNhap(pn);
                if (ok) {
                    // Cập nhật số lượng tồn kho
                    boolean updateOk = sanPhamDAO.updateProductQuantity(
                        nh.getMaSanPham(), 
                        Integer.parseInt(nh.getSoLuong())
                    );
                    if (!updateOk) {
                        allSuccess = false;
                        // Nếu cập nhật số lượng thất bại, xóa phiếu nhập vừa tạo
                        phieuNhapBUS.deletePhieuNhap(pn.getMaPhieuNhap());
                    }
                } else {
                    allSuccess = false;
                }
            }
        }

        // Xóa sạch bảng NhapHang sau khi chuyển thành công
        if (allSuccess) {
            for (nhapHangDTO nh : list) {
                nhapHangDAO.xoaNhapHang(nh.getMaPN());
            }
        }

        return allSuccess;
    }
}
