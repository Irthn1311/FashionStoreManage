package DTB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConnectDB {
    private static String envOrDefault(String key, String fallback) {
        String value = System.getenv(key);
        return (value == null || value.trim().isEmpty()) ? fallback : value.trim();
    }

    private static final String URL = envOrDefault(
            "FASHIONSTORE_DB_URL",
            "jdbc:sqlserver://localhost:1433;databaseName=FashionStore;encrypt=true;trustServerCertificate=true;useUnicode=true&characterEncoding=UTF-8"
    );
    private static final String USER = envOrDefault("FASHIONSTORE_DB_USER", "sa");
    private static final String PASSWORD = System.getenv("FASHIONSTORE_DB_PASSWORD");

    public static Connection getConnection() {
        try {
            if (PASSWORD == null || PASSWORD.trim().isEmpty()) {
                throw new SQLException("Missing FASHIONSTORE_DB_PASSWORD environment variable.");
            }
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
