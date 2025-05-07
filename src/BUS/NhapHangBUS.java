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

            // Tạo mã sản phẩm mới cho nhà cung cấp này
            String newMaSP = nhapHang.getMaSanPham() + "_" + nhapHang.getMaNhaCungCap();
            
            // Kiểm tra xem sản phẩm với mã mới đã tồn tại chưa
            if (!sanPhamDAO.isProductExists(newMaSP)) {
                // Lấy thông tin sản phẩm gốc
                sanPhamDTO originalProduct = sanPhamDAO.getSanPhamByMa(nhapHang.getMaSanPham());
                if (originalProduct != null) {
                    // Tạo sản phẩm mới với mã mới
                    sanPhamDTO newProduct = new sanPhamDTO();
                    newProduct.setMaSanPham(newMaSP);
                    newProduct.setTenSanPham(originalProduct.getTenSanPham());
                    newProduct.setMaNhaCungCap(nhapHang.getMaNhaCungCap());
                    newProduct.setMaDanhMuc(originalProduct.getMaDanhMuc());
                    newProduct.setMauSac(originalProduct.getMauSac());
                    newProduct.setSize(originalProduct.getSize());
                    newProduct.setSoLuongTonKho(0);
                    newProduct.setGiaBan(originalProduct.getGiaBan());
                    newProduct.setImgURL(originalProduct.getImgURL());
                    newProduct.setTrangThai("Còn hàng");

                    if (!sanPhamDAO.addSanPham(newProduct)) {
                        return false;
                    }
                }
            }

            // Cập nhật mã sản phẩm trong phiếu nhập
            nhapHang.setMaSanPham(newMaSP);

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
}
