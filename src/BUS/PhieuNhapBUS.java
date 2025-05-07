package BUS;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;
import java.util.List;
import java.util.Date;

public class PhieuNhapBUS {
    private PhieuNhapDAO phieuNhapDAO;

    public PhieuNhapBUS() {
        phieuNhapDAO = new PhieuNhapDAO();
    }

    // Validate PhieuNhap data
    private boolean validatePhieuNhap(PhieuNhapDTO phieuNhap) {
        if (phieuNhap == null) return false;
        if (phieuNhap.getMaSanPham() == null || phieuNhap.getMaSanPham().trim().isEmpty()) return false;
        if (phieuNhap.getMaNhaCungCap() == null || phieuNhap.getMaNhaCungCap().trim().isEmpty()) return false;
        if (phieuNhap.getSoLuong() <= 0) return false;
        if (phieuNhap.getDonGia() <= 0) return false;
        if (phieuNhap.getTrangThai() == null || phieuNhap.getTrangThai().trim().isEmpty()) return false;
        return true;
    }

    // Create new PhieuNhap with validation
    public boolean createPhieuNhap(PhieuNhapDTO phieuNhap) {
        if (!validatePhieuNhap(phieuNhap)) {
            return false;
        }
        // Set current date if not provided
        if (phieuNhap.getThoiGian() == null) {
            phieuNhap.setThoiGian(new Date());
        }
        // Calculate total amount
        phieuNhap.setThanhTien(phieuNhap.getSoLuong() * phieuNhap.getDonGia());
        return phieuNhapDAO.create(phieuNhap);
    }

    // Get PhieuNhap by ID
    public PhieuNhapDTO getPhieuNhap(String maPhieuNhap) {
        return phieuNhapDAO.read(maPhieuNhap);
    }

    // Update PhieuNhap with validation
    public boolean updatePhieuNhap(PhieuNhapDTO phieuNhap) {
        if (!validatePhieuNhap(phieuNhap)) {
            return false;
        }
        // Calculate total amount
        phieuNhap.setThanhTien(phieuNhap.getSoLuong() * phieuNhap.getDonGia());
        return phieuNhapDAO.update(phieuNhap);
    }

    // Delete PhieuNhap
    public boolean deletePhieuNhap(String maPhieuNhap) {
        return phieuNhapDAO.delete(maPhieuNhap);
    }

    // Get all PhieuNhap
    public List<PhieuNhapDTO> getAllPhieuNhap() {
        return phieuNhapDAO.getAll();
    }

    // Search PhieuNhap by date range
    public List<PhieuNhapDTO> searchByDateRange(Date startDate, Date endDate) {
        List<PhieuNhapDTO> allPhieuNhap = phieuNhapDAO.getAll();
        return allPhieuNhap.stream()
            .filter(pn -> !pn.getThoiGian().before(startDate) && !pn.getThoiGian().after(endDate))
            .toList();
    }

    // Search PhieuNhap by supplier
    public List<PhieuNhapDTO> searchBySupplier(String maNhaCungCap) {
        List<PhieuNhapDTO> allPhieuNhap = phieuNhapDAO.getAll();
        return allPhieuNhap.stream()
            .filter(pn -> pn.getMaNhaCungCap().equals(maNhaCungCap))
            .toList();
    }

    // Close resources
    public void close() {
        phieuNhapDAO.close();
    }
} 