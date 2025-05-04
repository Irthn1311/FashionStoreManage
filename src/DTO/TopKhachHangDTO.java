package DTO;

public class TopKhachHangDTO {
    private String maKhachHang;
    private String tenKhachHang;
    private int soLuongGiaoDich;
    private double tongDoanhThu;
    private String lastTransactionDate;

    public TopKhachHangDTO(String maKhachHang, String tenKhachHang, int soLuongGiaoDich, double tongDoanhThu) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soLuongGiaoDich = soLuongGiaoDich;
        this.tongDoanhThu = tongDoanhThu;
        this.lastTransactionDate = null;
    }

    public TopKhachHangDTO(String maKhachHang, String tenKhachHang, int soLuongGiaoDich, double tongDoanhThu, String lastTransactionDate) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soLuongGiaoDich = soLuongGiaoDich;
        this.tongDoanhThu = tongDoanhThu;
        this.lastTransactionDate = lastTransactionDate;
    }

    public String getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang; }
    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String tenKhachHang) { this.tenKhachHang = tenKhachHang; }
    public int getSoLuongGiaoDich() { return soLuongGiaoDich; }
    public void setSoLuongGiaoDich(int soLuongGiaoDich) { this.soLuongGiaoDich = soLuongGiaoDich; }
    public double getTongDoanhThu() { return tongDoanhThu; }
    public void setTongDoanhThu(double tongDoanhThu) { this.tongDoanhThu = tongDoanhThu; }
    public String getLastTransactionDate() { return lastTransactionDate; }
    public void setLastTransactionDate(String lastTransactionDate) { this.lastTransactionDate = lastTransactionDate; }

    @Override
    public String toString() {
        return "TopKhachHangDTO{" +
               "maKhachHang='" + maKhachHang + '\'' +
               ", tenKhachHang='" + tenKhachHang + '\'' +
               ", soLuongGiaoDich=" + soLuongGiaoDich +
               ", tongDoanhThu=" + tongDoanhThu +
               ", lastTransactionDate='" + (lastTransactionDate != null ? lastTransactionDate : "N/A") + '\'' +
               '}';
    }
}