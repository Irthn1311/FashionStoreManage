package DTB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    public static Connection connect() {
        Connection connection = null;
        try {
            // Load driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // JDBC URL
            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=FashionStore;encrypt=true;trustServerCertificate=true";

            // Credentials
            String username = "sa";
            String password = "nhapmatkhau"; // Thay bằng mật khẩu thật của bạn

            // Kết nối
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Kết nối thành công!");

            // Test SQL Query
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT GETDATE()");
            while (rs.next()) {
                System.out.println("Thời gian từ SQL Server: " + rs.getString(1));
            }

            // Query and display employee list
            rs = stmt.executeQuery("SELECT MaNhanVien, HoTen, ChucVu, MucLuong FROM NhanVien");

            System.out.println("\n📋 Danh sách nhân viên:");
            while (rs.next()) {
                String ma = rs.getString("MaNhanVien");
                String ten = rs.getString("HoTen");
                String chucVu = rs.getString("ChucVu");
                double luong = rs.getDouble("MucLuong");

                System.out.println("Mã: " + ma + " | Họ tên: " + ten + " | Chức vụ: " + chucVu + " | Lương: " + luong);
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy driver JDBC.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại.");
            e.printStackTrace();
        }

        return connection;
    }

    public static void main(String[] args) {
        connect();
    }
}