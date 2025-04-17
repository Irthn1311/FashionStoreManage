package DTO;

import java.util.Date;

public class khuyenMaiDTO {
    private String maKhuyenMai;
    private String tenChuongTrinh;
    private double giamGia;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String trangThai;
    private double giaMoi;
    private String khac;

    public khuyenMaiDTO() {
    }

    public khuyenMaiDTO(String maKhuyenMai, String tenChuongTrinh, double giamGia, Date ngayBatDau, Date ngayKetThuc,
            String trangThai, double giaMoi, String khac) {
        this.maKhuyenMai = maKhuyenMai;
        this.tenChuongTrinh = tenChuongTrinh;
        this.giamGia = giamGia;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
        this.giaMoi = giaMoi;
        this.khac = khac;
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

    public double getGiaMoi() {
        return giaMoi;
    }

    public void setGiaMoi(double giaMoi) {
        this.giaMoi = giaMoi;
    }

    public String getKhac() {
        return khac;
    }

    public void setKhac(String khac) {
        this.khac = khac;
    }

    @Override
    public String toString() {
        return "KhuyenMai{" +
                "maKhuyenMai='" + maKhuyenMai + '\'' +
                ", tenChuongTrinh='" + tenChuongTrinh + '\'' +
                ", giamGia=" + giamGia +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                ", trangThai='" + trangThai + '\'' +
                ", giaMoi=" + giaMoi +
                ", khac='" + khac + '\'' +
                '}';
    }

}