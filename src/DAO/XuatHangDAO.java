package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DTB.ConnectDB;
import DTO.xuatHangDTO;

public class XuatHangDAO {
    public boolean themXuatHang(xuatHangDTO xuatHang) {
        String sql = "INSERT INTO XuatHang (MaPX, MaKhachHang, HoTen, MaSanPham, TenSanPham, LoaiSP, KichThuoc, MauSac, SoLuong, ThoiGian, DonGia, ThanhTien, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, xuatHang.getMaPX());
            ps.setString(2, xuatHang.getMaKhachHang());
            ps.setString(3, xuatHang.getHoTen());
            ps.setString(4, xuatHang.getMaSanPham());
            ps.setString(5, xuatHang.getTenSanPham());
            ps.setString(6, xuatHang.getLoaiSP());
            ps.setString(7, xuatHang.getKichThuoc());
            ps.setString(8, xuatHang.getMauSac());
            ps.setInt(9, Integer.parseInt(xuatHang.getSoLuong()));
            ps.setString(10, xuatHang.getThoiGian());
            ps.setDouble(11, Double.parseDouble(xuatHang.getDonGia()));
            ps.setDouble(12, Double.parseDouble(xuatHang.getThanhTien()));
            ps.setString(13, xuatHang.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<xuatHangDTO> getAllXuatHang() {
        List<xuatHangDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM XuatHang";
        try (Connection conn = ConnectDB.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                xuatHangDTO xh = new xuatHangDTO();
                xh.setMaPX(rs.getString("MaPX"));
                xh.setMaKhachHang(rs.getString("MaKhachHang"));
                xh.setHoTen(rs.getString("HoTen"));
                xh.setMaSanPham(rs.getString("MaSanPham"));
                xh.setTenSanPham(rs.getString("TenSanPham"));
                xh.setLoaiSP(rs.getString("LoaiSP"));
                xh.setKichThuoc(rs.getString("KichThuoc"));
                xh.setMauSac(rs.getString("MauSac"));
                xh.setSoLuong(String.valueOf(rs.getInt("SoLuong")));
                xh.setThoiGian(rs.getString("ThoiGian"));
                xh.setDonGia(String.valueOf(rs.getDouble("DonGia")));
                xh.setThanhTien(String.valueOf(rs.getDouble("ThanhTien")));
                xh.setTrangThai(rs.getString("TrangThai"));
                list.add(xh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean capNhatTrangThai(String maPX, String trangThai) {
        String sql = "UPDATE XuatHang SET TrangThai = ? WHERE MaPX = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trangThai);
            ps.setString(2, maPX);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public xuatHangDTO getXuatHangByMa(String maPX) {
        String sql = "SELECT * FROM XuatHang WHERE MaPX = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPX);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                xuatHangDTO xh = new xuatHangDTO();
                xh.setMaPX(rs.getString("MaPX"));
                xh.setMaKhachHang(rs.getString("MaKhachHang"));
                xh.setHoTen(rs.getString("HoTen"));
                xh.setMaSanPham(rs.getString("MaSanPham"));
                xh.setTenSanPham(rs.getString("TenSanPham"));
                xh.setLoaiSP(rs.getString("LoaiSP"));
                xh.setKichThuoc(rs.getString("KichThuoc"));
                xh.setMauSac(rs.getString("MauSac"));
                xh.setSoLuong(String.valueOf(rs.getInt("SoLuong")));
                xh.setThoiGian(rs.getString("ThoiGian"));
                xh.setDonGia(String.valueOf(rs.getDouble("DonGia")));
                xh.setThanhTien(String.valueOf(rs.getDouble("ThanhTien")));
                xh.setTrangThai(rs.getString("TrangThai"));
                return xh;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean xoaXuatHang(String maPX) {
        String sql = "DELETE FROM XuatHang WHERE MaPX = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPX);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<xuatHangDTO> searchXuatHang(String keyword, String searchType) {
        List<xuatHangDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM XuatHang WHERE " + searchType + " LIKE ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                xuatHangDTO xh = new xuatHangDTO();
                xh.setMaPX(rs.getString("MaPX"));
                xh.setMaKhachHang(rs.getString("MaKhachHang"));
                xh.setHoTen(rs.getString("HoTen"));
                xh.setMaSanPham(rs.getString("MaSanPham"));
                xh.setTenSanPham(rs.getString("TenSanPham"));
                xh.setLoaiSP(rs.getString("LoaiSP"));
                xh.setKichThuoc(rs.getString("KichThuoc"));
                xh.setMauSac(rs.getString("MauSac"));
                xh.setSoLuong(String.valueOf(rs.getInt("SoLuong")));
                xh.setThoiGian(rs.getString("ThoiGian"));
                xh.setDonGia(String.valueOf(rs.getDouble("DonGia")));
                xh.setThanhTien(String.valueOf(rs.getDouble("ThanhTien")));
                xh.setTrangThai(rs.getString("TrangThai"));
                list.add(xh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean kiemTraTonKho(String maSanPham, int soLuongXuat) {
        String sql = "SELECT SoLuongTon FROM SanPham WHERE MaSanPham = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSanPham);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int soLuongTon = rs.getInt("SoLuongTon");
                return soLuongTon >= soLuongXuat;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean giamSoLuongTonKho(String maSanPham, int soLuongXuat) {
        String sql = "UPDATE SanPham SET SoLuongTon = SoLuongTon - ? WHERE MaSanPham = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soLuongXuat);
            ps.setString(2, maSanPham);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean kiemTraCanhBaoTonKho(String maSanPham) {
        String sql = "SELECT SoLuongTon, SoLuongCanhBao FROM SanPham WHERE MaSanPham = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSanPham);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int soLuongTon = rs.getInt("SoLuongTon");
                int soLuongCanhBao = rs.getInt("SoLuongCanhBao");
                return soLuongTon <= soLuongCanhBao;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Object[]> getAllExports() {
        List<Object[]> list = new ArrayList<>();
        // Connect to DB and fetch export records
        // Each Object[] should match the columns in your JTable
        // Example: {stt, maKH, tenKH, maSP, tenSP, loaiSP, kichThuoc, mauSac, soLuong, thoiGian, donGia, thanhTien, trangThai}
        // Add each row to the list
        return list;
    }
} 