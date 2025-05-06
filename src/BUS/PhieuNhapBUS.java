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
        if (phieuNhap.getMaNhanVien() == null || phieuNhap.getMaNhanVien().trim().isEmpty()) return false;
        if (phieuNhap.getSoLuongNhap() <= 0) return false;
        return true;
    }

    // Create new PhieuNhap with validation
    public boolean createPhieuNhap(PhieuNhapDTO phieuNhap) {
        if (!validatePhieuNhap(phieuNhap)) {
            return false;
        }
        // Set current date if not provided
        if (phieuNhap.getNgayNhap() == null) {
            phieuNhap.setNgayNhap(new Date());
        }
        return phieuNhapDAO.create(phieuNhap);
    }

    // Get PhieuNhap by ID
    public PhieuNhapDTO getPhieuNhap(int maPhieuNhap) {
        return phieuNhapDAO.read(maPhieuNhap);
    }

    // Update PhieuNhap with validation
    public boolean updatePhieuNhap(PhieuNhapDTO phieuNhap) {
        if (!validatePhieuNhap(phieuNhap)) {
            return false;
        }
        return phieuNhapDAO.update(phieuNhap);
    }

    // Delete PhieuNhap
    public boolean deletePhieuNhap(int maPhieuNhap) {
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
            .filter(pn -> !pn.getNgayNhap().before(startDate) && !pn.getNgayNhap().after(endDate))
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