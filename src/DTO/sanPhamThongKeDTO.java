package DTO;

public class sanPhamThongKeDTO {
    private String maSanPham;
    private String tenSanPham;
    private String maKhachHang;
    private String tenKhachHang;
    private int soLuong;
    private double doanhThu;

    public sanPhamThongKeDTO(String maSanPham, String tenSanPham, String maKhachHang, String tenKhachHang, int soLuong, double doanhThu) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soLuong = soLuong;
        this.doanhThu = doanhThu;
    }

    public String getMaSanPham() { return maSanPham; }
    public void setMaSanPham(String maSanPham) { this.maSanPham = maSanPham; }
    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
    public String getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang; }
    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String tenKhachHang) { this.tenKhachHang = tenKhachHang; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public double getDoanhThu() { return doanhThu; }
    public void setDoanhThu(double doanhThu) { this.doanhThu = doanhThu; }

    @Override
    public String toString() {
        return "sanPhamThongKeDTO{" +
               "maSanPham='" + maSanPham + '\'' +
               ", tenSanPham='" + tenSanPham + '\'' +
               ", maKhachHang='" + maKhachHang + '\'' +
               ", tenKhachHang='" + tenKhachHang + '\'' +
               ", soLuong=" + soLuong +
               ", doanhThu=" + doanhThu +
               '}';
    }
}