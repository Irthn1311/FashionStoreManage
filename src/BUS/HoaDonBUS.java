package BUS;

import DAO.HoaDonDAO;
import DTO.hoaDonDTO;
import DTO.sanPhamThongKeDTO;
import java.sql.Timestamp;
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

    public List<hoaDonDTO> getHoaDonByKhachHangAndSeries(String maKhachHang, String seriesPart) {
        return hoaDonDAO.getHoaDonByKhachHangAndSeries(maKhachHang, seriesPart);
    }

    public List<sanPhamThongKeDTO> getSanPhamBanChayNhat(Timestamp startDate, Timestamp endDate) {
        return hoaDonDAO.getSanPhamBanChayNhat(startDate, endDate);
    }

    public int getNewMaHoaDonSeries() {
        return hoaDonDAO.getNewMaHoaDonSeries();
    }
} 