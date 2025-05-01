package DTO;

import java.util.Date;

public class khuyenMaiDTO {
    private String maKhuyenMai;
    private String maSanPham;
    private String tenSanPham;
    private String tenChuongTrinh;
    private double giamGia;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private double giaCu; // New attribute for original price
    private double giaMoi;
    private String trangThai;

    public khuyenMaiDTO() {
    }

    public khuyenMaiDTO(String maKhuyenMai, String maSanPham, String tenSanPham, String tenChuongTrinh,
            double giamGia, Date ngayBatDau, Date ngayKetThuc, double giaCu, double giaMoi, String trangThai) {
        this.maKhuyenMai = maKhuyenMai;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.tenChuongTrinh = tenChuongTrinh;
        this.giamGia = giamGia;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.giaCu = giaCu;
        this.giaMoi = giaMoi;
        this.trangThai = trangThai;
    }

    public String getMaKhuyenMai() {
        return maKhuyenMai;
    }

    public void setMaKhuyenMai(String maKhuyenMai) {
        this.maKhuyenMai = maKhuyenMai;
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

    public double getGiaCu() {
        return giaCu;
    }

    public void setGiaCu(double giaCu) {
        this.giaCu = giaCu;
    }

    public double getGiaMoi() {
        return giaMoi;
    }

    public void setGiaMoi(double giaMoi) {
        this.giaMoi = giaMoi;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "KhuyenMai{" +
                "maKhuyenMai='" + maKhuyenMai + '\'' +
                ", maSanPham='" + maSanPham + '\'' +
                ", tenSanPham='" + tenSanPham + '\'' +
                ", tenChuongTrinh='" + tenChuongTrinh + '\'' +
                ", giamGia=" + giamGia +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                ", giaCu=" + giaCu +
                ", giaMoi=" + giaMoi +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}