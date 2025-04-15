package DTO;

import java.util.Date;

public class KhuyenMai {
    private String maKhuyenMai;
    private String tenChuongTrinh;
    private double giamGia;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String trangThai;

    public KhuyenMai(String maKhuyenMai, String tenChuongTrinh, double giamGia, 
                     Date ngayBatDau, Date ngayKetThuc, String trangThai) {
        this.maKhuyenMai = maKhuyenMai;
        this.tenChuongTrinh = tenChuongTrinh;
        this.giamGia = giamGia;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
    }

    // Getters
    public String getMaKhuyenMai() {
        return maKhuyenMai;
    }

    public String getTenChuongTrinh() {
        return tenChuongTrinh;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    // Setters
    public void setMaKhuyenMai(String maKhuyenMai) {
        this.maKhuyenMai = maKhuyenMai;
    }

    public void setTenChuongTrinh(String tenChuongTrinh) {
        this.tenChuongTrinh = tenChuongTrinh;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
} 