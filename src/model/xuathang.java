/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class xuathang {
    private long phuongThuc;
    private long trangThai;
    private long tongTien;
    private long maDonHang;
    private long maNhanVien;
    private String maKhuyenMai;

    public xuathang() {
    }

    public xuathang(long phuongThuc, long trangThai, long tongTien, long maDonHang, long maNhanVien, String maKhuyenMai) {
        this.phuongThuc = phuongThuc;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
        this.maDonHang = maDonHang;
        this.maNhanVien = maNhanVien;
        this.maKhuyenMai = maKhuyenMai;
    }

    public long getPhuongThuc() {
        return phuongThuc;
    }

    public void setPhuongThuc(long phuongThuc) {
        this.phuongThuc = phuongThuc;
    }

    public long getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(long trangThai) {
        this.trangThai = trangThai;
    }

    public long getTongTien() {
        return tongTien;
    }

    public void setTongTien(long tongTien) {
        this.tongTien = tongTien;
    }

    public long getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(long maDonHang) {
        this.maDonHang = maDonHang;
    }

    public long getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(long maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaKhuyenMai() {
        return maKhuyenMai;
    }

    public void setMaKhuyenMai(String maKhuyenMai) {
        this.maKhuyenMai = maKhuyenMai;
    }
    
    @Override
public String toString() {
    return "Thanh toán {" +
            "Phương thức=" + phuongThuc +
            ", Trạng thái=" + trangThai +
            ", Tổng tiền=" + tongTien +
            ", Mã đơn hàng=" + maDonHang +
            ", Mã nhân viên=" + maNhanVien +
            ", Mã khuyến mãi='" + maKhuyenMai + '\'' +
            '}';
}
}
