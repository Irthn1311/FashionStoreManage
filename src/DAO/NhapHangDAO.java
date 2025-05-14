package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DTB.ConnectDB;
import DTO.nhapHangDTO;

public class NhapHangDAO {
    public boolean themNhapHang(nhapHangDTO nhapHang) {
        String sql = "INSERT INTO NhapHang (MaPN, MaNhaCungCap, MaSanPham, TenSanPham, MauSac, KichThuoc, SoLuong, DonGia, ThanhTien, HinhThucThanhToan, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nhapHang.getMaPN());
            ps.setString(2, nhapHang.getMaNhaCungCap());
            ps.setString(3, nhapHang.getMaSanPham());
            ps.setString(4, nhapHang.getTenSanPham());
            ps.setString(5, nhapHang.getMauSac());
            ps.setString(6, nhapHang.getKichThuoc());
            ps.setInt(7, Integer.parseInt(nhapHang.getSoLuong()));
            ps.setDouble(8, Double.parseDouble(nhapHang.getDonGia()));
            ps.setDouble(9, Double.parseDouble(nhapHang.getThanhTien()));
            ps.setString(10, nhapHang.getHinhThucThanhToan());
            ps.setString(11, nhapHang.getTrangThai());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<nhapHangDTO> getAllNhapHang() {
        List<nhapHangDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NhapHang ORDER BY MaPN";
        try (Connection conn = ConnectDB.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                nhapHangDTO nh = new nhapHangDTO();
                nh.setMaPN(rs.getString("MaPN"));
                nh.setMaNhaCungCap(rs.getString("MaNhaCungCap"));
                nh.setMaSanPham(rs.getString("MaSanPham"));
                nh.setTenSanPham(rs.getString("TenSanPham"));
                nh.setMauSac(rs.getString("MauSac"));
                nh.setKichThuoc(rs.getString("KichThuoc"));
                nh.setSoLuong(String.valueOf(rs.getInt("SoLuong")));
                nh.setDonGia(String.valueOf(rs.getDouble("DonGia")));
                nh.setThanhTien(String.valueOf(rs.getDouble("ThanhTien")));
                nh.setThoiGian(rs.getString("ThoiGian"));
                nh.setTrangThai(rs.getString("TrangThai"));
                nh.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
                list.add(nh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean capNhatTrangThai(String maPN, String trangThai) {
        String sql = "UPDATE NhapHang SET TrangThai = ? WHERE MaPN = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trangThai);
            ps.setString(2, maPN);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public nhapHangDTO getNhapHangByMa(String maPN) {
        String sql = "SELECT * FROM NhapHang WHERE MaPN = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPN);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nhapHangDTO nh = new nhapHangDTO();
                nh.setMaPN(rs.getString("MaPN"));
                nh.setMaNhaCungCap(rs.getString("MaNhaCungCap"));
                nh.setMaSanPham(rs.getString("MaSanPham"));
                nh.setTenSanPham(rs.getString("TenSanPham"));
                nh.setMauSac(rs.getString("MauSac"));
                nh.setKichThuoc(rs.getString("KichThuoc"));
                nh.setSoLuong(String.valueOf(rs.getInt("SoLuong")));
                nh.setDonGia(String.valueOf(rs.getDouble("DonGia")));
                nh.setThanhTien(String.valueOf(rs.getDouble("ThanhTien")));
                nh.setThoiGian(rs.getString("ThoiGian"));
                nh.setTrangThai(rs.getString("TrangThai"));
                nh.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
                return nh;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean xoaNhapHang(String maPN) {
        String sql = "DELETE FROM NhapHang WHERE MaPN = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPN);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<nhapHangDTO> searchNhapHang(String keyword, String searchType) {
        List<nhapHangDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NhapHang WHERE " + searchType + " LIKE ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nhapHangDTO nh = new nhapHangDTO();
                nh.setMaPN(rs.getString("MaPN"));
                nh.setMaNhaCungCap(rs.getString("MaNhaCungCap"));
                nh.setMaSanPham(rs.getString("MaSanPham"));
                nh.setTenSanPham(rs.getString("TenSanPham"));
                nh.setMauSac(rs.getString("MauSac"));
                nh.setKichThuoc(rs.getString("KichThuoc"));
                nh.setSoLuong(String.valueOf(rs.getInt("SoLuong")));
                nh.setDonGia(String.valueOf(rs.getDouble("DonGia")));
                nh.setThanhTien(String.valueOf(rs.getDouble("ThanhTien")));
                nh.setThoiGian(rs.getString("ThoiGian"));
                nh.setTrangThai(rs.getString("TrangThai"));
                nh.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
                list.add(nh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean capNhatNhapHang(nhapHangDTO nhapHang) {
        String sql = "UPDATE NhapHang SET MaNhaCungCap=?, MaSanPham=?, TenSanPham=?, MauSac=?, KichThuoc=?, SoLuong=?, DonGia=?, ThanhTien=?, ThoiGian=?, TrangThai=?, HinhThucThanhToan=? WHERE MaPN=?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nhapHang.getMaNhaCungCap());
            ps.setString(2, nhapHang.getMaSanPham());
            ps.setString(3, nhapHang.getTenSanPham());
            ps.setString(4, nhapHang.getMauSac());
            ps.setString(5, nhapHang.getKichThuoc());
            ps.setInt(6, Integer.parseInt(nhapHang.getSoLuong()));
            ps.setDouble(7, Double.parseDouble(nhapHang.getDonGia()));
            ps.setDouble(8, Double.parseDouble(nhapHang.getThanhTien()));
            ps.setString(9, nhapHang.getThoiGian());
            ps.setString(10, nhapHang.getTrangThai());
            ps.setString(11, nhapHang.getHinhThucThanhToan());
            ps.setString(12, nhapHang.getMaPN());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getMaxMaPN() {
        String sql = "SELECT MAX(MaPN) AS MaxMaPN FROM NhapHang";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("MaxMaPN");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
