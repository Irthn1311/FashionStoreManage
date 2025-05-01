package DTO;

public class loaiSanPhamDTO {
    private String maSanPham;
    private String tenSanPham;
    private String maNhaCungCap;
    private String maDanhMuc;
    private String mauSac;
    private String size;
    private int soLuongTonKho;
    private double giaBan;
    private String imgURL;
    private String trangThai;
    private String maKhoHang;

    // Constructor không tham số
    public loaiSanPhamDTO() {
    }

    // Constructor đầy đủ tham số
    public loaiSanPhamDTO(String maSanPham, String tenSanPham, String maNhaCungCap, String maDanhMuc, String mauSac,
            String size, int soLuongTonKho, double giaBan, String imgURL, String trangThai) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.maNhaCungCap = maNhaCungCap;
        this.maDanhMuc = maDanhMuc;
        this.mauSac = mauSac;
        this.size = size;
        this.soLuongTonKho = soLuongTonKho;
        this.giaBan = giaBan;
        this.imgURL = imgURL;
        this.trangThai = trangThai;
    }

    // Getter và Setter
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

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getSoLuongTonKho() {
        return soLuongTonKho;
    }

    public void setSoLuongTonKho(int soLuongTonKho) {
        this.soLuongTonKho = soLuongTonKho;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaKhoHang() {
        return maKhoHang;
    }

    @Override
    public String toString() {
        return "SanPhamDTO {" +
                "maSanPham='" + maSanPham + '\'' +
                ", tenSanPham='" + tenSanPham + '\'' +
                ", maNhaCungCap='" + maNhaCungCap + '\'' +
                ", maDanhMuc='" + maDanhMuc + '\'' +
                ", mauSac='" + mauSac + '\'' +
                ", size='" + size + '\'' +
                ", soLuongTonKho=" + soLuongTonKho +
                ", giaBan=" + giaBan +
                ", imgURL='" + imgURL + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", maKhoHang='" + maKhoHang + '\'' +
                '}';
    }

}
