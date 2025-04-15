package DTB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=FashionStore;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";  // Tài khoản SQL Server của bạn
    private static final String PASSWORD = "nhapmatkhau";  // Mật khẩu SQL Server của bạn

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
