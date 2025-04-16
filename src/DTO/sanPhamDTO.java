package DTO;

public class sanPhamDTO {
    private String maSanPham; // Dùng String thay vì int vì MaSanPham là VARCHAR(50)
    private String tenSanPham;
    private String maThuongHieu;
    private String maDanhMuc;
    private double giaBan;
    private int soLuongTonKho;
    private String size;
    private String trangThai;
    private String imgURL;
    private String maKhoHang;

    // Constructor
    public sanPhamDTO(String maSanPham, String tenSanPham, String maThuongHieu, String maDanhMuc, double giaBan,
            int soLuongTonKho, String size, String trangThai, String imgURL, String maKhoHang) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.maThuongHieu = maThuongHieu;
        this.maDanhMuc = maDanhMuc;
        this.giaBan = giaBan;
        this.soLuongTonKho = soLuongTonKho;
        this.size = size;
        this.trangThai = trangThai;
        this.imgURL = imgURL;
        this.maKhoHang = maKhoHang;
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

    public String getMaThuongHieu() {
        return maThuongHieu;
    }

    public void setMaThuongHieu(String maThuongHieu) {
        this.maThuongHieu = maThuongHieu;
    }

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuongTonKho() {
        return soLuongTonKho;
    }

    public void setSoLuongTonKho(int soLuongTonKho) {
        this.soLuongTonKho = soLuongTonKho;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getMaKhoHang() {
        return maKhoHang;
    }

    public void setMaKhoHang(String maKhoHang) {
        this.maKhoHang = maKhoHang;
    }

    public Object getLoaiSP() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLoaiSP'");
    }

    public Object getMaNCC() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMaNCC'");
    }

    public Object getMauSac() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMauSac'");
    }

    public Object getKichCo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getKichCo'");
    }
}
