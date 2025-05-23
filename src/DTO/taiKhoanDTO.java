package DTO;

public class taiKhoanDTO {
    private String maTaiKhoan;
    private String tenDangNhap;
    private String matKhau;
    private VaiTro vaiTro;
    private int trangThai;
    private String maNhanVien;

    public taiKhoanDTO() {
    }

    public taiKhoanDTO(String maTaiKhoan, String tenDangNhap, String matKhau, 
                      String vaiTro, int trangThai, String maNhanVien) {
        this.maTaiKhoan = maTaiKhoan;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.vaiTro = VaiTro.fromString(vaiTro);
        this.trangThai = trangThai;
        this.maNhanVien = maNhanVien;
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

    public VaiTro getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(VaiTro vaiTro) {
        this.vaiTro = vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = VaiTro.fromString(vaiTro);
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "maTaiKhoan='" + maTaiKhoan + '\'' +
                ", tenDangNhap='" + tenDangNhap + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", vaiTro='" + (vaiTro != null ? vaiTro.getDisplayName() : "null") + '\'' +
                ", trangThai=" + trangThai +
                ", maNhanVien='" + maNhanVien + '\'' +
                '}';
    }
}
