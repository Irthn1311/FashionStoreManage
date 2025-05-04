package DTB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConnectDB {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=FashionStore2;encrypt=true;trustServerCertificate=true;useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "sa"; // Tài khoản SQL Server của bạn
    private static final String PASSWORD = "12345678"; // vaicaten01Mật khẩu SQL Server của bạn

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy SQL Server Driver", "Lỗi kết nối",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu\nLỗi: " + e.getMessage(),
                    "Lỗi kết nối", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }
}
