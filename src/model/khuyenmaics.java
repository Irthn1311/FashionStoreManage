/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class khuyenmaics {
    
    private String maKhuyenMai;
    private String tenChuongTrinh;
    private double giamGia;
    private java.sql.Date ngayBatDau;
    private java.sql.Date ngayKetThuc;
    private String trangThai;

    public khuyenmaics() {
    }

    public khuyenmaics(String maKhuyenMai, String tenChuongTrinh, double giamGia, Date ngayBatDau, Date ngayKetThuc, String trangThai) {
        this.maKhuyenMai = maKhuyenMai;
        this.tenChuongTrinh = tenChuongTrinh;
        this.giamGia = giamGia;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
    }

    public String getMaKhuyenMai() {
        return maKhuyenMai;
    }

    public void setMaKhuyenMai(String maKhuyenMai) {
        this.maKhuyenMai = maKhuyenMai;
    }

    public String getTenChuongTrinh() {
        return tenChuongTrinh;
    }

    public void setTenChuongTrinh(String tenChuongTrinh) {
        this.tenChuongTrinh = tenChuongTrinh;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
  @Override
public String toString() {
    return "Khuyến mãi:\n" +
           "  Mã khuyến mãi: " + maKhuyenMai + "\n" +
           "  Tên chương trình: " + tenChuongTrinh + "\n" +
           "  Giảm giá: " + giamGia + "\n" +
           "  Ngày bắt đầu: " + ngayBatDau + "\n" +
           "  Ngày kết thúc: " + ngayKetThuc + "\n" +
           "  Trạng thái: " + trangThai;
}


}
