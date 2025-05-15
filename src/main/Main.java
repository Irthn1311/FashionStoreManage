package main;

import screens.DangNhap.login;

public class Main {
    public static void main(String[] args) {
        // Khởi tạo giao diện đăng nhập
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                java.util.Locale.setDefault(java.util.Locale.US);
                new login().setVisible(true);
            }
        });
    }
}
