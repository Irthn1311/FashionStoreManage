package main;

import screens.TrangChu.trangchu;

public class Main {
    public static void main(String[] args) {
        // Khởi tạo giao diện adminscreen
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new trangchu().setVisible(true);
            }
        });
    }
}
