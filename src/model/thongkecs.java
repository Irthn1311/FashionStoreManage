/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class thongkecs {
private String maThongKe;
    private String ngay;
    private double doanhThu;
    private int soDonHang;
    private int soSanPhamBan;
    private double loiNhuan;

    public thongkecs() {
    }

    public thongkecs(String maThongKe, String ngay, double doanhThu, int soDonHang, int soSanPhamBan, double loiNhuan) {
        this.maThongKe = maThongKe;
        this.ngay = ngay;
        this.doanhThu = doanhThu;
        this.soDonHang = soDonHang;
        this.soSanPhamBan = soSanPhamBan;
        this.loiNhuan = loiNhuan;
    }

    public String getMaThongKe() {
        return maThongKe;
    }

    public void setMaThongKe(String maThongKe) {
        this.maThongKe = maThongKe;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public double getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(double doanhThu) {
        this.doanhThu = doanhThu;
    }

    public int getSoDonHang() {
        return soDonHang;
    }

    public void setSoDonHang(int soDonHang) {
        this.soDonHang = soDonHang;
    }

    public int getSoSanPhamBan() {
        return soSanPhamBan;
    }

    public void setSoSanPhamBan(int soSanPhamBan) {
        this.soSanPhamBan = soSanPhamBan;
    }

    public double getLoiNhuan() {
        return loiNhuan;
    }

    public void setLoiNhuan(double loiNhuan) {
        this.loiNhuan = loiNhuan;
    }
    @Override
public String toString() {
    return "Thống kê:\n" +
           "  Mã thống kê: " + maThongKe + "\n" +
           "  Ngày: " + ngay + "\n" +
           "  Doanh thu: " + doanhThu + "\n" +
           "  Số đơn hàng: " + soDonHang + "\n" +
           "  Số sản phẩm bán: " + soSanPhamBan + "\n" +
           "  Lợi nhuận: " + loiNhuan;
}

}
