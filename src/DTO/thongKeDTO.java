/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import model.*;

/**
 *
 * @author ASUS
 */
public class thongKeDTO {
 private String maSanPham;
    private String tenSanPham;
    private String soSPBanRa;
    private String doanhThu;

    public thongKeDTO() {
    }

    public thongKeDTO(String maSanPham, String tenSanPham, String soSPBanRa, String doanhThu) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.soSPBanRa = soSPBanRa;
        this.doanhThu = doanhThu;
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

    public String getSoSPBanRa() {
        return soSPBanRa;
    }

    public void setSoSPBanRa(String soSPBanRa) {
        this.soSPBanRa = soSPBanRa;
    }

    public String getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(String doanhThu) {
        this.doanhThu = doanhThu;
    }

    

    @Override
    public String toString() {
        return "ThongKeSanPham{" +
                "maSP='" + maSanPham + '\'' +
                ", tenSP='" + tenSanPham + '\'' +
                ", soSPBanRa='" + soSPBanRa + '\'' +
                ", doanhThu='" + doanhThu + '\'' +
                '}';
    }
}
