package DTO;

public class NhaCungCap_SanPhamDTO {
    private String maNhaCungCap;
    private String maSanPham;

    public NhaCungCap_SanPhamDTO() {
    }

    public NhaCungCap_SanPhamDTO(String maNhaCungCap, String maSanPham) {
        this.maNhaCungCap = maNhaCungCap;
        this.maSanPham = maSanPham;
    }

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }
} 