package DTO;

import java.sql.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;

public class nhanVienDTO {
    private String maNhanVien;
    private String hoTen;
    private String email;
    private String soDienThoai;
    private String diaChi;
    private String gioiTinh;
    private Date ngaySinh;
    private Timestamp ngayVaoLam;
    private String chucVu;
    private BigDecimal mucLuong;
    private String maQuanLy;
    private String trangThai;

    public nhanVienDTO() {
    }

    public nhanVienDTO(String maNhanVien, String hoTen, String email, String soDienThoai, 
                      String diaChi, String gioiTinh, Date ngaySinh, Timestamp ngayVaoLam,
                      String chucVu, BigDecimal mucLuong, String maQuanLy, String trangThai) {
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
        this.maQuanLy = maQuanLy;
        this.trangThai = trangThai;
    }

    // Getters
    public String getMaNhanVien() {
        return maNhanVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getEmail() {
        return email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public Timestamp getNgayVaoLam() {
        return ngayVaoLam;
    }

    public String getChucVu() {
        return chucVu;
    }

    public BigDecimal getMucLuong() {
        return mucLuong;
    }

    public String getMaQuanLy() {
        return maQuanLy;
    }

    public String getTrangThai() {
        return trangThai;
    }

    // Setters
    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public void setNgayVaoLam(Timestamp ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public void setNgayVaoLam(Date ngayVaoLam) {
        this.ngayVaoLam = new Timestamp(ngayVaoLam.getTime());
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public void setMucLuong(BigDecimal mucLuong) {
        this.mucLuong = mucLuong;
    }

    public void setMaQuanLy(String maQuanLy) {
        this.maQuanLy = maQuanLy;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "NhanVien {" +
                "maNhanVien='" + maNhanVien + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", email='" + email + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", ngaySinh=" + ngaySinh +
                ", ngayVaoLam=" + ngayVaoLam +
                ", chucVu='" + chucVu + '\'' +
                ", mucLuong=" + mucLuong +
                ", maQuanLy='" + maQuanLy + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
} 