package DBConnector;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectorGtt {
    Connection conn = null;
    static String adresSerwera = "192.168.90.101";

    public static Connection dbConnector() {
        try {
            String user = "gttuser";
            String pass = "gttpassword";
            String base = "gttdatabase";

            System.out.println(user);
            System.out.println(pass);
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mariadb://" + adresSerwera + "/" + base, user, pass);
            return conn;
        } catch (Exception e) {
            System.err.println("cannot establish database connection" + e);
            return null;
        }
    }
}
