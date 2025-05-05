package BUS;

import DAO.HoaDonDAO;
import DTO.hoaDonDTO;
import java.util.List;

public class HoaDonBUS {
    private HoaDonDAO hoaDonDAO;

    public HoaDonBUS() {
        this.hoaDonDAO = new HoaDonDAO();
    }

    public List<hoaDonDTO> getAllHoaDon() {
        return hoaDonDAO.getAllHoaDon();
    }

    public boolean isMaHoaDonExists(String maHoaDon) {
        return hoaDonDAO.isMaHoaDonExists(maHoaDon);
    }

    public boolean addHoaDon(hoaDonDTO hd) {
        return hoaDonDAO.addHoaDon(hd);
    }

    public boolean updateHoaDon(hoaDonDTO hd) {
        return hoaDonDAO.updateHoaDon(hd);
    }
} 