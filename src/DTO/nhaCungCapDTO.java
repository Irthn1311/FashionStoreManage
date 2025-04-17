package DTO;

public class nhaCungCapDTO {
 private String maNhaCungCap;
    private String tenNhaCungCap;
    private String loaiSP;
    private String maSanPham;
    private String tenSanPham;
    private int namHopTac;
    private String address;
    private String email;
    private String soDienThoai;
    private String trangThai;

    public nhaCungCapDTO() {
    }

    public nhaCungCapDTO(String maNhaCungCap, String tenNhaCungCap, String loaiSP, String maSanPham, String tenSanPham, int namHopTac, String address, String email, String soDienThoai, String trangThai) {
        this.maNhaCungCap = maNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.loaiSP = loaiSP;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.namHopTac = namHopTac;
        this.address = address;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.trangThai = trangThai;
    }

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public String getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(String loaiSP) {
        this.loaiSP = loaiSP;
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

    public int getNamHopTac() {
        return namHopTac;
    }

    public void setNamHopTac(int namHopTac) {
        this.namHopTac = namHopTac;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    
     @Override
    public String toString() {
        return "NhaCungCap{" +
                "maNCC='" + maNhaCungCap + '\'' +
                ", tenNCC='" + tenNhaCungCap + '\'' +
                ", loaiSP='" + loaiSP + '\'' +
                ", maSP='" + maSanPham + '\'' +
                ", tenSP='" + tenSanPham + '\'' +
                ", namHopTac=" + namHopTac +
                ", diaChi='" + address + '\'' +
                ", email='" + email + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
} 