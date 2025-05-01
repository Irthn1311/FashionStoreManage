/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import model.*;

/**
 *
 * @author ASUS
 */
public class xuatHangDTO {
     private String maKhachHang;
    private String hoTen;
    private String maSanPham;
    private String tenSanPham;
    private String loaiSP;
    private String kichThuoc;
    private String mauSac;
    private String soLuong;
    private String thoiGian;
    private String donGia;
    private String thanhTien;
    private String trangThai;

    public xuatHangDTO() {
    }

    public xuatHangDTO(String maKhachHang, String hoTen, String maSanPham, String tenSanPham, String loaiSP, String kichThuoc, String mauSac, String soLuong, String thoiGian, String donGia, String thanhTien, String trangThai) {
        this.maKhachHang = maKhachHang;
        this.hoTen = hoTen;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.loaiSP = loaiSP;
        this.kichThuoc = kichThuoc;
        this.mauSac = mauSac;
        this.soLuong = soLuong;
        this.thoiGian = thoiGian;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
        this.trangThai = trangThai;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(String loaiSP) {
        this.loaiSP = loaiSP;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getDonGia() {
        return donGia;
    }

    public void setDonGia(String donGia) {
        this.donGia = donGia;
    }

    public String getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(String thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    
    
     @Override
    public String toString() {
        return "LichSuMuaHang{" +
                "maKH='" + maKhachHang + '\'' +
                ", tenKH='" + hoTen + '\'' +
                ", maSP='" + maSanPham + '\'' +
                ", tenSP='" + tenSanPham + '\'' +
                ", loaiSP='" + loaiSP + '\'' +
                ", kichThuoc='" + kichThuoc + '\'' +
                ", mauSac='" + mauSac + '\'' +
                ", soLuong='" + soLuong + '\'' +
                ", thoiGian='" + thoiGian + '\'' +
                ", donGia='" + donGia + '\'' +
                ", thanhTien='" + thanhTien + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
