package BUS;

import DAO.NhaCungCapDAO;
import DTO.nhaCungCapDTO;
import java.util.List;

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
}
