package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDBCon {

    private static final String URL = "jdbc:mariadb://localhost:3306/mydb";
    private static final String USER = "tester";
    private static final String PASSWORD = "qwerty12";

    // Метод для отримання з'єднання
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Завантаження драйвера (опціонально для нових версій JDBC)
            Class.forName("org.mariadb.jdbc.Driver");

            // Встановлення з'єднання
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to MariaDB successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MariaDB Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Connection failed: " + e.getMessage());
        }
        return connection;
    }

}
