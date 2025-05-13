package BUS;

import DTO.VaiTro;

public class PhanQuyenBUS {
    public static final String QUAN_TRI = "Quản trị";
    public static final String QUAN_LY_KHO = "Quản lý kho";
    public static final String QUAN_LY_NHAN_VIEN = "Quản lý nhân viên";
    public static final String NHAN_VIEN = "Nhân viên";
    
    public static boolean coQuyenTruyCap(VaiTro vaiTro, String chucNang) {
        if (vaiTro == null) return false;
        
        switch (vaiTro) {
            case QUAN_TRI:
                return true; // Quản trị có quyền truy cập tất cả
                
            case QUAN_LY_KHO:
                return chucNang.equals("XuatHang") ||
                       chucNang.equals("NhapHang") ||
                       chucNang.equals("SanPham") ||
                       chucNang.equals("LoaiSanPham") ||
                       chucNang.equals("NhaCungCap");
                       
            case QUAN_LY_NHAN_VIEN:
                return chucNang.equals("NhanVien") ||
                       chucNang.equals("KhachHang") ||
                       chucNang.equals("SanPham") ||
                       chucNang.equals("LoaiSanPham") ||
                       chucNang.equals("KhuyenMai") ||
                       chucNang.equals("HoaDon");
                       
            case NHAN_VIEN:
                return chucNang.equals("KhachHang") ||
                       chucNang.equals("SanPham") ||
                       chucNang.equals("LoaiSanPham") ||
                       chucNang.equals("HoaDon") ||
                       chucNang.equals("XuatHang") ||
                       chucNang.equals("ThongKe");
                       
            default:
                return false;
        }
    }
    
    public static void kiemTraVaVoHieuHoaButton(javax.swing.JButton button, VaiTro vaiTro, String chucNang) {
        if (!coQuyenTruyCap(vaiTro, chucNang)) {
            button.setEnabled(false);
            button.setToolTipText("Bạn không có quyền truy cập chức năng này");
        }
    }
} 