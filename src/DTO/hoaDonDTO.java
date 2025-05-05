package DTO;

import java.sql.Timestamp;

public class hoaDonDTO {
    private String maHoaDon;
    private String maSanPham;
    private String tenSanPham;
    private String kichCo;
    private String mauSac;
    private int soLuong;
    private String maKhachHang;
    private String tenKhachHang;
    private double thanhTien;
    private double donGia;
    private String hinhThucThanhToan;
    private Timestamp thoiGian; // Đổi từ String sang Timestamp
    private String trangThai;

    public hoaDonDTO() {
    }

    public hoaDonDTO(String maHoaDon, String maSanPham, String tenSanPham, String kichCo, String mauSac, 
                     int soLuong, String maKhachHang, String tenKhachHang, double thanhTien, double donGia, 
                     String hinhThucThanhToan, Timestamp thoiGian, String trangThai) {
        this.maHoaDon = maHoaDon;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.kichCo = kichCo;
        this.mauSac = mauSac;
        this.soLuong = soLuong;
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.thanhTien = thanhTien;
        this.donGia = donGia;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.thoiGian = thoiGian;
        this.trangThai = trangThai;
    }

    // Getters và Setters
    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
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

    public String getKichCo() {
        return kichCo;
    }

    public void setKichCo(String kichCo) {
        this.kichCo = kichCo;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public Timestamp getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Timestamp thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "hoaDonDTO{" +
                "maHoaDon='" + maHoaDon + '\'' +
                ", maSanPham='" + maSanPham + '\'' +
                ", tenSanPham='" + tenSanPham + '\'' +
                ", kichCo='" + kichCo + '\'' +
                ", mauSac='" + mauSac + '\'' +
                ", soLuong=" + soLuong +
                ", maKhachHang='" + maKhachHang + '\'' +
                ", tenKhachHang='" + tenKhachHang + '\'' +
                ", thanhTien=" + thanhTien +
                ", donGia=" + donGia +
                ", hinhThucThanhToan='" + hinhThucThanhToan + '\'' +
                ", thoiGian=" + thoiGian +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}