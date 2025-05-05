package DTO;

public enum VaiTro {
    QUAN_TRI("Quản trị"),
    QUAN_LY_KHO("Quản lý kho"),
    QUAN_LY_NHAN_VIEN("Quản lý nhân viên"),
    NHAN_VIEN("Nhân viên");

    private final String displayName;

    VaiTro(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static VaiTro fromString(String text) {
        for (VaiTro vaiTro : VaiTro.values()) {
            if (vaiTro.displayName.equals(text)) {
                return vaiTro;
            }
        }
        return null;
    }
} 