/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.sql.Timestamp;

/**
 *
 * @author ASUS
 */
public class nhapHangDTO {
    private String maPN;
    private String maNhaCungCap;
    private String maSanPham;
    private String tenSanPham;
    private String mauSac;
    private String kichThuoc;
    private String soLuong;
    private String donGia;
    private String thanhTien;
    private String thoiGian;
    private String trangThai;
    private String hinhThucThanhToan;

    public nhapHangDTO() {
    }

    public nhapHangDTO(String maPN, String maNhaCungCap, String maSanPham, 
                      String tenSanPham, String mauSac, String kichThuoc, String soLuong, 
                      String donGia, String thanhTien, String thoiGian, String trangThai,
                      String hinhThucThanhToan) {
        this.maPN = maPN;
        this.maNhaCungCap = maNhaCungCap;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.mauSac = mauSac;
        this.kichThuoc = kichThuoc;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
        this.thoiGian = thoiGian;
        this.trangThai = trangThai;
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public String getMaPN() {
        return maPN;
    }

    public void setMaPN(String maPN) {
        this.maPN = maPN;
    }

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
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

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
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

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    @Override
    public String toString() {
        return "NhapHangDTO{" +
                "maPN='" + maPN + '\'' +
                ", maNhaCungCap='" + maNhaCungCap + '\'' +
                ", maSanPham='" + maSanPham + '\'' +
                ", tenSanPham='" + tenSanPham + '\'' +
                ", mauSac='" + mauSac + '\'' +
                ", kichThuoc='" + kichThuoc + '\'' +
                ", soLuong='" + soLuong + '\'' +
                ", donGia='" + donGia + '\'' +
                ", thanhTien='" + thanhTien + '\'' +
                ", thoiGian='" + thoiGian + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", hinhThucThanhToan='" + hinhThucThanhToan + '\'' +
                '}';
    }
}