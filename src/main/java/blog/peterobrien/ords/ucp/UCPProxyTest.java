package blog.peterobrien.ords.ucp;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.OracleConnection;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

public class UCPProxyTest {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Tests that a user can proxy to another schema. Parameters by position ...");
            System.out.println(
                    "\turl      JDBC url to for the database. For example jdbc:oracle:thin:@localhost:1521/orclpdb1");
            System.out.println("\tusername Username for a DB account with connect through privilege");
            System.out.println("\tpassword Password for that DB account");
            System.out.println("\tschema   Name of the schema that the DB account has permission to proxy as");
            System.out.println("");
            System.out.println("Example");
            System.out.println("\tUCPProxyText jdbc:oracle:thin:@localhost:1521/orclpdb1 ORDS_PUBLIC_USER oracle HR");

            return;
        }
        final String url = args[0];
        final String username = args[1];
        final String password = args[2];
        final String schema = args[3];

        try {

            PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();
            pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
            pds.setURL(url);
            pds.setUser(username);
            pds.setPassword(password);
            final Connection conn = pds.getConnection();
            System.out.println("Connection made: " + conn.toString());

            final Properties prop = new Properties();

            System.out.println("Trying to open proxy connection ...");

            prop.put(OracleConnection.PROXY_USER_NAME, schema);

            ((OracleConnection) conn).openProxySession(OracleConnection.PROXYTYPE_USER_NAME, prop);
            final DatabaseMetaData meta = conn.getMetaData();

            System.out.println("Connection user: " + meta.getUserName());
            System.out.println("Product Version: " + meta.getDatabaseProductVersion());

            ((OracleConnection) conn).close(OracleConnection.PROXY_SESSION);
            System.out.println("Succeeded. Proxy connection closed");

        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }
}