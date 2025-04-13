/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class taikhoancs {
    private String maTaiKhoan;
    private String tenDangNhap;
    private String matKhau;
    private String email;
    private String soDienThoai;
    private String vaiTro;
    private String trangThai;

    public taikhoancs() {
    }

    public taikhoancs(String maTaiKhoan, String tenDangNhap, String matKhau, String email, String soDienThoai, String vaiTro, String trangThai) {
        this.maTaiKhoan = maTaiKhoan;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
    }

    public String getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(String maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
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

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    @Override
public String toString() {
    return "Tài khoản:\n" +
           "  Mã tài khoản: " + maTaiKhoan + "\n" +
           "  Tên đăng nhập: " + tenDangNhap + "\n" +
           "  Mật khẩu: " + matKhau + "\n" +
           "  Email: " + email + "\n" +
           "  Số điện thoại: " + soDienThoai + "\n" +
           "  Vai trò: " + vaiTro + "\n" +
           "  Trạng thái: " + trangThai;
}

    
}
