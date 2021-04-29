package DBConnector;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectorFATDB {
    Connection conn = null;
    static String adresSerwera = "192.168.90.123";

    public static Connection dbConnector() {
        try {
            String user = "Pentaho";
            String pass = "1234";
            String base = "fatdb";

            Class.forName("org.mariadb.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mariadb://" + adresSerwera + "/" + base, user, pass);
            return conn;
        } catch (Exception e) {
            System.err.println("cannot establish database connection" + e);
            return null;
        }
    }
}

