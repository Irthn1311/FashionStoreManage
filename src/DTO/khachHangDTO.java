package DTO;

import java.sql.Date;
import java.sql.Timestamp;

public class khachHangDTO {
    private String maKhachHang;
    private String hoTen;
    private String email;
    private String phone;
    private String diaChi;
    private String gioiTinh;
    private Date ngaySinh;
    private Timestamp ngayDangKy;
    private taiKhoanDTO taiKhoan;

    public khachHangDTO(String maKhachHang, String hoTen, String email, String phone, 
                     String diaChi, String gioiTinh, Date ngaySinh, 
                     Timestamp ngayDangKy, taiKhoanDTO taiKhoan) {
        this.maKhachHang = maKhachHang;
        this.hoTen = hoTen;
        this.email = email;
        this.phone = phone;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.ngayDangKy = ngayDangKy;
        this.taiKhoan = taiKhoan;
    }

    // Getters
    public String getMaKhachHang() {
        return maKhachHang;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
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

    public Timestamp getNgayDangKy() {
        return ngayDangKy;
    }

    public taiKhoanDTO getTaiKhoan() {
        return taiKhoan;
    }

    // Các phương thức tiện ích để lấy thông tin từ taiKhoan
    public String getMaTaiKhoan() {
        return taiKhoan != null ? taiKhoan.getMaTaiKhoan() : null;
    }

    public String getTenDangNhap() {
        return taiKhoan != null ? taiKhoan.getTenDangNhap() : null;
    }

    // Setters
    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public void setNgayDangKy(Timestamp ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }

    public void setTaiKhoan(taiKhoanDTO taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    @Override
public String toString() {
    return "KhachHang {" +
            "maKhachHang='" + maKhachHang + '\'' +
            ", hoTen='" + hoTen + '\'' +
            ", email='" + email + '\'' +
            ", phone='" + phone + '\'' +
            ", diaChi='" + diaChi + '\'' +
            ", gioiTinh='" + gioiTinh + '\'' +
            ", ngaySinh=" + ngaySinh +
            ", ngayDangKy=" + ngayDangKy +
            '}';
}

} 