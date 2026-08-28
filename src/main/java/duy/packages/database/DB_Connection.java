package duy.packages.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB_Connection {
    private final String serverName = "localhost";
    private final String dbName = "WebDevelopment";
    private final String portNumber = "1433";
    private final String instance = "";
    private final String userID = "DuyMinh";
    private final String password = "duy12345678";

    public Connection getConnection() throws Exception {
        String url = "jdbc:sqlserver://" + serverName + "\\" + instance + ":" + portNumber +
                ";databaseName=" + dbName + ";encrypt=true;trustServerCertificate=true;";
        
        if (instance == null || instance.trim().isEmpty()) {
            url = "jdbc:sqlserver://" + serverName + ":" + portNumber +
                  ";databaseName=" + dbName + ";encrypt=true;trustServerCertificate=true;";
        }
        
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, userID, password);
    }
    
    public static void main(String[] args) {
        try {
            DB_Connection db = new DB_Connection();
            Connection conn = db.getConnection();
            if (conn != null) {
                System.out.println("Connected to SQL Server successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}