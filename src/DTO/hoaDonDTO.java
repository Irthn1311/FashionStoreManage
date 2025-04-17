/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;
;

/**
 *
 * @author ASUS
 */
public class hoaDonDTO {
   private String maSanPham;
    private String tenSanPham;
    private String kichCo;
    private String mauSac;
    private int soLuong;
    private String maKhachHang;
    private String hoTen;
    private double thanhTien;
    private double donGia;
    private String hinhThucThanhToan;
    private String gioXuatHang;
    private String thoiGian;
    private String trangThai;

    public hoaDonDTO() {
    }

    public hoaDonDTO(String maSanPham, String tenSanPham, String kichCo, String mauSac, int soLuong, String maKhachHang, String hoTen, double thanhTien, double donGia, String hinhThucThanhToan, String gioXuatHang, String thoiGian, String trangThai) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.kichCo = kichCo;
        this.mauSac = mauSac;
        this.soLuong = soLuong;
        this.maKhachHang = maKhachHang;
        this.hoTen = hoTen;
        this.thanhTien = thanhTien;
        this.donGia = donGia;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.gioXuatHang = gioXuatHang;
        this.thoiGian = thoiGian;
        this.trangThai = trangThai;
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

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
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

    public String getGioXuatHang() {
        return gioXuatHang;
    }

    public void setGioXuatHang(String gioXuatHang) {
        this.gioXuatHang = gioXuatHang;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
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
    return "SanPham{" +
            "maSanPham='" + maSanPham + '\'' +
            ", tenSanPham='" + tenSanPham + '\'' +
            ", kichCo='" + kichCo + '\'' +
            ", mauSac='" + mauSac + '\'' +
            ", soLuong=" + soLuong +
            ", maKhachHang='" + maKhachHang + '\'' +
            ", hoTen='" + hoTen + '\'' +
            ", thanhTien=" + thanhTien +
            ", donGia=" + donGia +
            ", hinhThucThanhToan='" + hinhThucThanhToan + '\'' +
            ", gioXuatHang='" + gioXuatHang + '\'' +
            ", thoiGian='" + thoiGian + '\'' +
            ", trangThai='" + trangThai + '\'' +
            '}';
}

}


