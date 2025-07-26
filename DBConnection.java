// This line says this file belongs to the 'db' package (a way to organize code like folders)
package db;

// Import everything from java.sql package (used for database operations like connections, queries, etc.)
import java.sql.*;

// This is the class named DBConnection - it will help connect Java to a MySQL database
public class DBConnection {

    // These are the details needed to connect to the database

    // The URL of your MySQL database.
    // "jdbc:mysql://" tells Java it's a MySQL database
    // "localhost:3306" means the database is running on your computer (port 3306 is default for MySQL)
    // "student_tracker" is the name of the database you're connecting to
    private static final String URL = "jdbc:mysql://localhost:3306/student_tracker";

    // Your MySQL database username
    private static final String USER = "root";

    // Your MySQL password (in this case, it's "Sourav@2005")
    private static final String PASSWORD = "Sourav@2005";


    // This is a public method called getConnection
    // It returns a Connection object and throws an exception if there's a problem
    public static Connection getConnection() throws SQLException {

        // This line tries to connect to the database using the URL, USER, and PASSWORD
        // If successful, it returns the connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

