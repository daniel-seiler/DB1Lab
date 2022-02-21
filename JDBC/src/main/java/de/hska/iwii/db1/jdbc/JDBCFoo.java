package de.hska.iwii.db1.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JDBCFoo {
    private Connection connection = null;

    public JDBCFoo(String url, String username, String password) {
        try {
            this.connection = this.connectDB(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection connectDB(String url, String username, String password) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);
        // props.setProperty("ssl", "true");
        return DriverManager.getConnection(url, props);
    }

    private void disconnectDB() throws SQLException {
        connection.close();
    }

    public Connection getConnection() {
        return connection;
    }

    private String getDBfromURL(String url) {
        // TODO: fix regex
        Pattern pattern = Pattern.compile("jdbc:\\w+\\:\\/{2}[\\w\\d\\.\\:]+\\/");
        Matcher matcher = pattern.matcher(url);
        return matcher.group();
    }
    public void getDBinfo() throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        String dbProduct = metaData.getDatabaseProductName();
        String dbURL = metaData.getURL();
        // String dbName = getDBfromURL(dbURL);
        String jdbcDriverName = metaData.getDriverName();
        String jdbcDriverVersion = metaData.getDriverVersion();

        System.out.println("Database product: " + dbProduct);
        System.out.println("Database name: " + dbURL);
        System.out.println("JDBC driver name: " + jdbcDriverName);
        System.out.println("JDBC driver version: " + jdbcDriverVersion);
    }

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://datenbanken1.ddns.net:3690/g20";
        String username = "g20";
        String password = "XfgZfBTstd";

        System.out.println("[+] open database session");
        JDBCFoo pgsql = new JDBCFoo(url, username, password);
        System.out.println("[+] get database info");
        pgsql.getDBinfo();
        System.out.println("[+] close database session");
        pgsql.disconnectDB();
    }
}
