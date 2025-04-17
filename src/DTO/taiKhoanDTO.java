/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author ASUS
 */
public class taiKhoanDTO {
   private String id;
    private String hoVaTen;
    private String email;
    private String soDienThoai;
    private String diaChi;
    private String gioiTinh;
    private String tuoi;
    private String chucVu;
    private String ngayThanhLap;
    private String tenCongTy;

    public taiKhoanDTO() {
    }

    public taiKhoanDTO(String id, String hoVaTen, String email, String soDienThoai, String diaChi, String gioiTinh, String tuoi, String chucVu, String ngayThanhLap, String tenCongTy) {
        this.id = id;
        this.hoVaTen = hoVaTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.tuoi = tuoi;
        this.chucVu = chucVu;
        this.ngayThanhLap = ngayThanhLap;
        this.tenCongTy = tenCongTy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
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

    public String getTuoi() {
        return tuoi;
    }

    public void setTuoi(String tuoi) {
        this.tuoi = tuoi;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getNgayThanhLap() {
        return ngayThanhLap;
    }

    public void setNgayThanhLap(String ngayThanhLap) {
        this.ngayThanhLap = ngayThanhLap;
    }

    public String getTenCongTy() {
        return tenCongTy;
    }

    public void setTenCongTy(String tenCongTy) {
        this.tenCongTy = tenCongTy;
    }
    
    @Override
    public String toString() {
        return "TaiKhoan{" +
                "id='" + id + '\'' +
                ", hoVaTen='" + hoVaTen + '\'' +
                ", email='" + email + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", tuoi='" + tuoi + '\'' +
                ", chucVu='" + chucVu + '\'' +
                ", ngayThanhLap='" + ngayThanhLap + '\'' +
                ", tenCongTy='" + tenCongTy + '\'' +
                '}';
    }
}
