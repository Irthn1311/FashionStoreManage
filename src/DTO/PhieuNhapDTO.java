package DTO;

import java.util.Date;

public class PhieuNhapDTO {
    private String maPhieuNhap;
    private String maNhaCungCap;
    private String maSanPham;
    private String tenSanPham;
    private int soLuong;
    private Date thoiGian;
    private double donGia;
    private String trangThai;
    private String hinhThucThanhToan;
    private double thanhTien;

    public PhieuNhapDTO() {}

    public PhieuNhapDTO(String maPhieuNhap, String maNhaCungCap, String maSanPham, String tenSanPham,
                       int soLuong, Date thoiGian, double donGia, String trangThai,
                       String hinhThucThanhToan, double thanhTien) {
        this.maPhieuNhap = maPhieuNhap;
        this.maNhaCungCap = maNhaCungCap;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.thoiGian = thoiGian;
        this.donGia = donGia;
        this.trangThai = trangThai;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.thanhTien = thanhTien;
    }

    // Getters and Setters
    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
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

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
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

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    @Override
    public String toString() {
        return "PhieuNhapDTO{" +
                "maPhieuNhap='" + maPhieuNhap + '\'' +
                ", maNhaCungCap='" + maNhaCungCap + '\'' +
                ", maSanPham='" + maSanPham + '\'' +
                ", tenSanPham='" + tenSanPham + '\'' +
                ", soLuong=" + soLuong +
                ", thoiGian=" + thoiGian +
                ", donGia=" + donGia +
                ", trangThai='" + trangThai + '\'' +
                ", hinhThucThanhToan='" + hinhThucThanhToan + '\'' +
                ", thanhTien=" + thanhTien +
                '}';
    }
} 