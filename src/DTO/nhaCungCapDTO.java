package DTO;

public class nhaCungCapDTO {
    private String maNhaCungCap;
    private String tenNhaCungCap;
    private int namHopTac;
    private String diaChi;
    private String email;
    private String soDienThoai;
    private String trangThai;

    public nhaCungCapDTO() {
    }

    public nhaCungCapDTO(String maNhaCungCap, String tenNhaCungCap, 
                        int namHopTac, String diaChi, String email, 
                        String soDienThoai, String trangThai) {
        this.maNhaCungCap = maNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.namHopTac = namHopTac;
        this.diaChi = diaChi;
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

    public int getNamHopTac() {
        return namHopTac;
    }

    public void setNamHopTac(int namHopTac) {
        this.namHopTac = namHopTac;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
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
                "maNhaCungCap='" + maNhaCungCap + '\'' +
                ", tenNhaCungCap='" + tenNhaCungCap + '\'' +
                ", namHopTac=" + namHopTac +
                ", diaChi='" + diaChi + '\'' +
                ", email='" + email + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
} 