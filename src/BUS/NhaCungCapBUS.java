package BUS;

import DAO.NhaCungCapDAO;
import DTO.nhaCungCapDTO;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;

public class NhaCungCapBUS {
    private NhaCungCapDAO nhaCungCapDAO;

    public NhaCungCapBUS() {
        nhaCungCapDAO = new NhaCungCapDAO();
    }

    public List<nhaCungCapDTO> getAllNhaCungCap() {
        return nhaCungCapDAO.getAllNhaCungCap();
    }

    public List<nhaCungCapDTO> searchNhaCungCap(String keyword, String searchType) {
        return nhaCungCapDAO.searchNhaCungCap(keyword, searchType);
    }

    public boolean themNhaCungCap(nhaCungCapDTO ncc) {
        // Kiểm tra dữ liệu đầu vào
        if (ncc.getMaNhaCungCap() == null || ncc.getMaNhaCungCap().trim().isEmpty()) {
            return false;
        }
        if (ncc.getTenNhaCungCap() == null || ncc.getTenNhaCungCap().trim().isEmpty()) {
            return false;
        }
        if (ncc.getSoDienThoai() == null || ncc.getSoDienThoai().trim().isEmpty()) {
            return false;
        }
        if (ncc.getEmail() == null || ncc.getEmail().trim().isEmpty()) {
            return false;
        }

        // Kiểm tra email hợp lệ
        if (!ncc.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return false;
        }

        // Kiểm tra số điện thoại hợp lệ (10 số)
        if (!ncc.getSoDienThoai().matches("\\d{10}")) {
            return false;
        }

        return nhaCungCapDAO.themNhaCungCap(ncc);
    }

    public boolean capNhatNhaCungCap(nhaCungCapDTO ncc) {
        // Kiểm tra dữ liệu đầu vào
        if (ncc.getMaNhaCungCap() == null || ncc.getMaNhaCungCap().trim().isEmpty()) {
            return false;
        }
        if (ncc.getTenNhaCungCap() == null || ncc.getTenNhaCungCap().trim().isEmpty()) {
            return false;
        }
        if (ncc.getSoDienThoai() == null || ncc.getSoDienThoai().trim().isEmpty()) {
            return false;
        }
        if (ncc.getEmail() == null || ncc.getEmail().trim().isEmpty()) {
            return false;
        }

        // Kiểm tra email hợp lệ
        if (!ncc.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return false;
        }

        // Kiểm tra số điện thoại hợp lệ (10 số)
        if (!ncc.getSoDienThoai().matches("\\d{10}")) {
            return false;
        }

        return nhaCungCapDAO.capNhatNhaCungCap(ncc);
    }

    public boolean xoaNhaCungCap(String maNCC) {
        if (maNCC == null || maNCC.trim().isEmpty()) {
            return false;
        }
        return nhaCungCapDAO.xoaNhaCungCap(maNCC);
    }

    public nhaCungCapDTO getNhaCungCapByMa(String maNCC) {
        if (maNCC == null || maNCC.trim().isEmpty()) {
            return null;
        }
        return nhaCungCapDAO.getNhaCungCapByMa(maNCC);
    }

    public boolean xoaNhaCungCapVaSanPham(String maNCC) {
        try {
            return nhaCungCapDAO.xoaNhaCungCapVaSanPham(maNCC);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatSanPhamSangNhaCungCapKhac(String maNCCCu, String maNCCMoi) {
        try {
            return nhaCungCapDAO.capNhatSanPhamSangNhaCungCapKhac(maNCCCu, maNCCMoi);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getAllSuppliers() {
        List<nhaCungCapDTO> suppliers = nhaCungCapDAO.getAllNhaCungCap();
        return suppliers.stream()
                .map(nhaCungCapDTO::getMaNhaCungCap)
                .collect(Collectors.toList());
    }

    public boolean isSupplierActive(String maNhaCungCap) {
        return nhaCungCapDAO.isSupplierActive(maNhaCungCap);
    }

    public List<nhaCungCapDTO> searchNhaCungCapAdvanced(String keyword, String searchType, String namHopTacFilter,
            String trangThaiFilter) {
        // Convert "Tất cả" or empty strings for filters to null or handle them
        // appropriately for DAO
        String finalKeyword = (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim() : null;
        String finalSearchType = (searchType != null && !searchType.trim().isEmpty()
                && !"Tất cả".equalsIgnoreCase(searchType.trim())) ? searchType.trim() : null;
        String finalNamHopTac = (namHopTacFilter != null && !namHopTacFilter.trim().isEmpty()
                && !"Tất cả".equalsIgnoreCase(namHopTacFilter.trim())) ? namHopTacFilter.trim() : null;
        String finalTrangThai = (trangThaiFilter != null && !trangThaiFilter.trim().isEmpty()
                && !"Tất cả".equalsIgnoreCase(trangThaiFilter.trim())) ? trangThaiFilter.trim() : null;

        return nhaCungCapDAO.searchNhaCungCapAdvanced(finalKeyword, finalSearchType, finalNamHopTac, finalTrangThai);
    }

    public boolean capNhatTrangThaiNhaCungCap(String maNCC) {
        if (maNCC == null || maNCC.trim().isEmpty()) {
            return false;
        }
        nhaCungCapDTO ncc = nhaCungCapDAO.getNhaCungCapByMa(maNCC);
        if (ncc == null) {
            return false; // Supplier not found
        }

        String currentTrangThai = ncc.getTrangThai();
        String newTrangThai;

        // Define the toggle logic, e.g., "Hoạt động" <-> "Không hoạt động"
        // You might want more sophisticated logic or more states.
        if ("Đang hợp tác".equalsIgnoreCase(currentTrangThai)) {
            newTrangThai = "Ngừng hợp tác";
        } else if ("Ngừng hợp tác".equalsIgnoreCase(currentTrangThai)) {
            newTrangThai = "Đang hợp tác";
        } else {
            // Default to "Đang hợp tác" if current status is unclear or different
            newTrangThai = "Đang hợp tác";
        }

        return nhaCungCapDAO.capNhatTrangThaiNhaCungCap(maNCC, newTrangThai);
    }

    public String generateNextMaNhaCungCap() {
        List<nhaCungCapDTO> allNhaCungCap = nhaCungCapDAO.getAllNhaCungCap();

        // If no suppliers exist, start with NCC001
        if (allNhaCungCap == null || allNhaCungCap.isEmpty()) {
            return "NCC001";
        }

        // Extract all existing supplier IDs
        List<Integer> existingIds = new ArrayList<>();
        for (nhaCungCapDTO ncc : allNhaCungCap) {
            String maNCC = ncc.getMaNhaCungCap();
            if (maNCC != null && maNCC.matches("NCC\\d{3}")) {
                try {
                    int idNumber = Integer.parseInt(maNCC.substring(3));
                    existingIds.add(idNumber);
                } catch (NumberFormatException e) {
                    // Skip invalid formats
                }
            }
        }

        // Sort the IDs to find gaps
        Collections.sort(existingIds);

        // Look for gaps in the sequence
        int expectedId = 1;
        for (int existingId : existingIds) {
            if (existingId > expectedId) {
                // Found a gap, use the missing ID
                break;
            }
            expectedId = existingId + 1;
        }

        // Format the new ID with leading zeros
        return String.format("NCC%03d", expectedId);
    }
}
