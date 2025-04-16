package main;
import screens.TrangChu.trangChu;

public class Main {
    public static void main(String[] args) {
        // Khởi tạo giao diện adminscreen
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new trangChu().setVisible(true);
            }
        });
    }
}
