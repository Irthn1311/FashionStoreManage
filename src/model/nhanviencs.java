/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author ASUS
 */
public class nhanviencs {
    private String maNhanVien;
    private String hoTen;
    private String email;
    private String soDienThoai;
    private String diaChi;
    private String gioiTinh;
    private java.sql.Date ngaySinh;
    private java.sql.Timestamp ngayVaoLam;
    private String chucVu;
    private double mucLuong;

    public nhanviencs() {
    }

    public nhanviencs(String maNhanVien, String hoTen, String email, String soDienThoai, String diaChi, String gioiTinh, Date ngaySinh, Timestamp ngayVaoLam, String chucVu, double mucLuong) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.ngayVaoLam = ngayVaoLam;
        this.chucVu = chucVu;
        this.mucLuong = mucLuong;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Timestamp getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(Timestamp ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public double getMucLuong() {
        return mucLuong;
    }

    public void setMucLuong(double mucLuong) {
        this.mucLuong = mucLuong;
    }
    
    @Override
public String toString() {
    return "Nhân viên:\n" +
           "  Mã nhân viên: " + maNhanVien + "\n" +
           "  Họ tên: " + hoTen + "\n" +
           "  Email: " + email + "\n" +
           "  Số điện thoại: " + soDienThoai + "\n" +
           "  Địa chỉ: " + diaChi + "\n" +
           "  Giới tính: " + gioiTinh + "\n" +
           "  Ngày sinh: " + ngaySinh + "\n" +
           "  Ngày vào làm: " + ngayVaoLam + "\n" +
           "  Chức vụ: " + chucVu + "\n" +
           "  Mức lương: " + mucLuong;
}

}
