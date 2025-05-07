package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DTB.ConnectDB;
import DTO.nhapHangDTO;

public class NhapHangDAO {
    public boolean themNhapHang(nhapHangDTO nhapHang) {
        String sql = "INSERT INTO NhapHang (MaPN, MaNhaCungCap, LoaiSP, MaSanPham, TenSanPham, MauSac, KichThuoc, SoLuong, DonGia, ThanhTien, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nhapHang.getMaPN());
            ps.setString(2, nhapHang.getMaNhaCungCap());
            ps.setString(3, nhapHang.getLoaiSP());
            ps.setString(4, nhapHang.getMaSanPham());
            ps.setString(5, nhapHang.getTenSanPham());
            ps.setString(6, nhapHang.getMauSac());
            ps.setString(7, nhapHang.getKichThuoc());
            ps.setInt(8, Integer.parseInt(nhapHang.getSoLuong()));
            ps.setDouble(9, Double.parseDouble(nhapHang.getDonGia()));
            ps.setDouble(10, Double.parseDouble(nhapHang.getThanhTien()));
            ps.setString(11, nhapHang.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e); // So UI can see the error
        }
    }

    public List<nhapHangDTO> getAllNhapHang() {
        List<nhapHangDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NhapHang";
        try (Connection conn = ConnectDB.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                nhapHangDTO nh = new nhapHangDTO();
                nh.setMaPN(rs.getString("MaPN"));
                nh.setMaNhaCungCap(rs.getString("MaNhaCungCap"));
                nh.setLoaiSP(rs.getString("LoaiSP"));
                nh.setMaSanPham(rs.getString("MaSanPham"));
                nh.setTenSanPham(rs.getString("TenSanPham"));
                nh.setMauSac(rs.getString("MauSac"));
                nh.setKichThuoc(rs.getString("KichThuoc"));
                nh.setSoLuong(String.valueOf(rs.getInt("SoLuong")));
                nh.setDonGia(String.valueOf(rs.getDouble("DonGia")));
                nh.setThanhTien(String.valueOf(rs.getDouble("ThanhTien")));
                nh.setThoiGian(rs.getString("ThoiGian"));
                nh.setTrangThai(rs.getString("TrangThai"));
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
}
