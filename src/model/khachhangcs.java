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
public class khachhangcs {
    private String maKhachHang;
    private String hoTen;
    private String email;
    private String phone;
    private String diaChi;
    private String gioiTinh;
    private java.sql.Date ngaySinh;
    private java.sql.Timestamp ngayDangKy;
    private String maTaiKhoan;

    public khachhangcs() {
    }

    public khachhangcs(String maKhachHang, String hoTen, String email, String phone, String diaChi, String gioiTinh, Date ngaySinh, Timestamp ngayDangKy, String maTaiKhoan) {
        this.maKhachHang = maKhachHang;
        this.hoTen = hoTen;
        this.email = email;
        this.phone = phone;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.ngayDangKy = ngayDangKy;
        this.maTaiKhoan = maTaiKhoan;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Timestamp getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(Timestamp ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }

    public String getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(String maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }
    
    @Override
    public String toString() {
        return "Khách hàng {" +
                "Mã KH='" + maKhachHang + '\'' +
                ", Họ tên='" + hoTen + '\'' +
                ", Email='" + email + '\'' +
                ", SĐT='" + phone + '\'' +
                ", Địa chỉ='" + diaChi + '\'' +
                ", Giới tính='" + gioiTinh + '\'' +
                ", Ngày sinh=" + ngaySinh +
                ", Ngày đăng ký=" + ngayDangKy +
                ", Mã tài khoản='" + maTaiKhoan + '\'' +
                '}';
    }
    
}
