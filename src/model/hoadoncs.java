/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.security.Timestamp;

/**
 *
 * @author ASUS
 */
public class hoadoncs {
     private String maDonHang;
    private String maKhachHang;
    private java.sql.Timestamp ngayDatHang;
    private double tongTien;
    private String phuongThucThanhToan;
    private String trangThaiDonHang;
    private String maSanPham;
    private int soLuong;
    private double donGiaBan;
    private double thanhTien;

    public hoadoncs() {
    }
    
    

    public hoadoncs(String maDonHang, String maKhachHang, java.sql.Timestamp ngayDatHang, double tongTien, String phuongThucThanhToan, String trangThaiDonHang, String maSanPham, int soLuong, double donGiaBan, double thanhTien) {
        this.maDonHang = maDonHang;
        this.maKhachHang = maKhachHang;
        this.ngayDatHang = ngayDatHang;
        this.tongTien = tongTien;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.trangThaiDonHang = trangThaiDonHang;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.donGiaBan = donGiaBan;
        this.thanhTien = thanhTien;
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public java.sql.Timestamp getNgayDatHang() {
        return ngayDatHang;
    }

    public void setNgayDatHang(java.sql.Timestamp ngayDatHang) {
        this.ngayDatHang = ngayDatHang;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public String getTrangThaiDonHang() {
        return trangThaiDonHang;
    }

    public void setTrangThaiDonHang(String trangThaiDonHang) {
        this.trangThaiDonHang = trangThaiDonHang;
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

    public double getDonGiaBan() {
        return donGiaBan;
    }

    public void setDonGiaBan(double donGiaBan) {
        this.donGiaBan = donGiaBan;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }
    
    @Override
    public String toString() {
        return "Hóa đơn {" +
                "Mã đơn hàng='" + maDonHang + '\'' +
                ", Mã khách hàng='" + maKhachHang + '\'' +
                ", Ngày đặt hàng=" + ngayDatHang +
                ", Tổng tiền=" + tongTien +
                ", Phương thức thanh toán='" + phuongThucThanhToan + '\'' +
                ", Trạng thái đơn hàng='" + trangThaiDonHang + '\'' +
                ", Mã sản phẩm='" + maSanPham + '\'' +
                ", Số lượng=" + soLuong +
                ", Đơn giá bán=" + donGiaBan +
                ", Thành tiền=" + thanhTien +
                '}';
    }
}


