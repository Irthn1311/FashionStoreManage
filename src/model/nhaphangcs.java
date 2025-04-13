/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author ASUS
 */
public class nhaphangcs {
    private String maNhapHang;
    private String maNhaCungCap;
    private java.sql.Timestamp ngayNhapHang;
    private double tongTienNhap;
    private String maSanPham;
    private int soLuong;
    private double donGiaNhap;

    public nhaphangcs() {
    }

    public nhaphangcs(String maNhapHang, String maNhaCungCap, Timestamp ngayNhapHang, double tongTienNhap, String maSanPham, int soLuong, double donGiaNhap) {
        this.maNhapHang = maNhapHang;
        this.maNhaCungCap = maNhaCungCap;
        this.ngayNhapHang = ngayNhapHang;
        this.tongTienNhap = tongTienNhap;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.donGiaNhap = donGiaNhap;
    }

    public String getMaNhapHang() {
        return maNhapHang;
    }

    public void setMaNhapHang(String maNhapHang) {
        this.maNhapHang = maNhapHang;
    }

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public Timestamp getNgayNhapHang() {
        return ngayNhapHang;
    }

    public void setNgayNhapHang(Timestamp ngayNhapHang) {
        this.ngayNhapHang = ngayNhapHang;
    }

    public double getTongTienNhap() {
        return tongTienNhap;
    }

    public void setTongTienNhap(double tongTienNhap) {
        this.tongTienNhap = tongTienNhap;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGiaNhap() {
        return donGiaNhap;
    }

    public void setDonGiaNhap(double donGiaNhap) {
        this.donGiaNhap = donGiaNhap;
    }
    
    @Override
public String toString() {
    return "Phiếu nhập:\n" +
           "  Mã nhập hàng: " + maNhapHang + "\n" +
           "  Mã nhà cung cấp: " + maNhaCungCap + "\n" +
           "  Ngày nhập hàng: " + ngayNhapHang + "\n" +
           "  Tổng tiền nhập: " + tongTienNhap + "\n" +
           "  Mã sản phẩm: " + maSanPham + "\n" +
           "  Số lượng: " + soLuong + "\n" +
           "  Đơn giá nhập: " + donGiaNhap;
}

    
}
