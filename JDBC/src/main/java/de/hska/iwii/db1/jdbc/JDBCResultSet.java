package de.hska.iwii.db1.jdbc;

import java.sql.*;

public class JDBCResultSet {
    // SELECT persnr, name, ort, aufgabe FROM personal

    static void printCell(String str, int size) {
        System.out.print(str);
    }
    public static void printResult(Connection conn, String query) throws SQLException {
        Statement stmt = conn.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();

        /*
        print first row of table
         */
        int columnWidth;
        for (int i = 1; i <= numberOfColumns; i++) {
            columnWidth = rsmd.getColumnDisplaySize(i);
            System.out.printf("|%-" + columnWidth + "s", " " + rsmd.getColumnLabel(i));
        }
        System.out.print("\n");
        for (int i = 1; i <= numberOfColumns ; i++) {
            columnWidth = rsmd.getColumnDisplaySize(i);
            System.out.printf("|%-" + columnWidth + "s", " " + rsmd.getColumnTypeName(i));
        }
        System.out.print("\n");
        for (int i = 1; i <= numberOfColumns ; i++) {
            columnWidth = rsmd.getColumnDisplaySize(i) + 1;
            String bar = String.format("%-" + columnWidth + "s", "-").replace(' ', '-');
            System.out.print(bar+"+");
        }
        System.out.print("\n");
        /*
        print following rows
         */
        int datatype;
        while (rs.next()){
            for (int i = 1; i <= numberOfColumns ; i++) {
                columnWidth = rsmd.getColumnDisplaySize(i) -1;
                if (rsmd.getColumnTypeName(i).startsWith("int")) {
                    System.out.printf("|%" + columnWidth + "d ", rs.getInt(i));
                } else {
                    System.out.printf("|%-" + columnWidth + "s", " " + rs.getString(i));
                }
            }
            System.out.print("|\n");
        }

    }

    public static void main (String[] args) {
        String url = "jdbc:postgresql://datenbanken1.ddns.net:3690/g20";
        String username = "g20";
        String password = "XfgZfBTstd";

        JDBCFoo pgsql = new JDBCFoo(url, username, password);
        Connection conn = pgsql.getConnection();

        String query = "SELECT persnr, name, ort, aufgabe FROM personal";
        try {
            printResult(conn, query);
        } catch (SQLException e) {
            e.getMessage();
        }
    }
}
