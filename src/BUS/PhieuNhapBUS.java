package BUS;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;
import java.util.List;
import java.util.Date;
import java.util.Random;

public class PhieuNhapBUS {
    private PhieuNhapDAO phieuNhapDAO;
    private Random randomGenerator;

    public PhieuNhapBUS() {
        phieuNhapDAO = new PhieuNhapDAO();
        randomGenerator = new Random();
    }

    // Validate PhieuNhap data
    private boolean validatePhieuNhap(PhieuNhapDTO phieuNhap) {
        if (phieuNhap == null)
            return false;
        if (phieuNhap.getMaSanPham() == null || phieuNhap.getMaSanPham().trim().isEmpty())
            return false;
        if (phieuNhap.getMaNhaCungCap() == null || phieuNhap.getMaNhaCungCap().trim().isEmpty())
            return false;
        if (phieuNhap.getSoLuong() <= 0)
            return false;
        if (phieuNhap.getDonGia() <= 0)
            return false;
        if (phieuNhap.getTrangThai() == null || phieuNhap.getTrangThai().trim().isEmpty())
            return false;
        return true;
    }

    public String generateNewBatchPrefix() {
        int maxBatchNo = phieuNhapDAO.getMaxBatchNumber();
        int nextBatchNo = maxBatchNo + 1;
        return String.format("PN%05d", nextBatchNo);
    }

    public String generateMaPhieuNhapForBatch(String batchPrefix) {
        if (batchPrefix == null || !batchPrefix.matches("PN\\d{5}")) {
            System.err.println("Invalid or null batchPrefix provided to generateMaPhieuNhapForBatch: " + batchPrefix + ". Generating a new one as fallback.");
            batchPrefix = generateNewBatchPrefix();
        }
        // Generate a 5-digit random number from nanoTime
        long nanoTimeRandom = System.nanoTime();
        int randomPart = (int) Math.abs(nanoTimeRandom % 100000);
        return String.format("%s_%05d", batchPrefix, randomPart);
    }

    // Create new PhieuNhap with validation
    public boolean createPhieuNhap(PhieuNhapDTO phieuNhap) {
        if (phieuNhap.getMaPhieuNhap() == null || phieuNhap.getMaPhieuNhap().trim().isEmpty()) {
            // This is an indicator of a potential issue in the calling code.
            // For robustness, we could throw an IllegalArgumentException or log a severe error.
            // System.err.println("CRITICAL: MaPhieuNhap is not set before calling createPhieuNhap.");
            // For now, let's proceed assuming the calling code will be updated.
            // If not, this DTO will be saved with a null/empty MaPhieuNhap, which is likely an error.
        }
        if (!validatePhieuNhap(phieuNhap)) {
            return false;
        }
        if (phieuNhap.getThoiGian() == null) {
            phieuNhap.setThoiGian(new Date());
        }
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
                .collect(java.util.stream.Collectors.toList());
    }

    // Close resources
    public void close() {
        phieuNhapDAO.close();
    }

    // Get PhieuNhap by ID (bổ sung cho NhapHangBUS)
    public PhieuNhapDTO getPhieuNhapByMa(String maPhieuNhap) {
        return phieuNhapDAO.read(maPhieuNhap);
    }

    // Update MaNhaCungCap for PhieuNhap records
    public boolean updateMaNhaCungCapForPhieuNhap(String oldMaNCC, String newMaNCC) {
        if (oldMaNCC == null || oldMaNCC.trim().isEmpty() || newMaNCC == null || newMaNCC.trim().isEmpty()) {
            return false;
        }
        return phieuNhapDAO.updateMaNhaCungCap(oldMaNCC, newMaNCC);
    }

    // Get PhieuNhap records by supplier and batch prefix
    public List<PhieuNhapDTO> getPhieuNhapBySupplierAndBatch(String maNhaCungCap, String batchPrefix) {
        List<PhieuNhapDTO> allPhieuNhap = phieuNhapDAO.getAll();
        return allPhieuNhap.stream()
                .filter(pn -> pn.getMaNhaCungCap().equals(maNhaCungCap) && 
                              pn.getMaPhieuNhap() != null &&
                              pn.getMaPhieuNhap().startsWith(batchPrefix))
                .collect(java.util.stream.Collectors.toList());
    }
}