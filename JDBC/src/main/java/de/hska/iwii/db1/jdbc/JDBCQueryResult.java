package de.hska.iwii.db1.jdbc;

import java.sql.*;
/*
    TASK 4.2
 */
public class JDBCQueryResult {
    private ResultSet rs;
    private final Statement statement;
    private final String query;

    public JDBCQueryResult(Connection connection, String query) throws SQLException{
        this.statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        this.query = query;
    }

    private void execute() {
        try {
            this.rs = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void printHeader() throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
        StringBuilder sb = new StringBuilder();
        String cell;
        int columnWidth;
        /*
            Print column labels
         */
        for (int i = 1; i <= numberOfColumns; i++) {
            columnWidth = rsmd.getColumnDisplaySize(i);
            // replace '|' with space when end of line is reached
            char e = (i == numberOfColumns) ? ' ' : '|';
            cell = String.format(" %-" + columnWidth + "s" + e, rsmd.getColumnLabel(i));
            sb.append(cell);
        }
        sb.append("\n");
        /*
            Print column types
         */
        for (int i = 1; i <= numberOfColumns; i++) {
            columnWidth = rsmd.getColumnDisplaySize(i);
            // replace '|' with space when end of line is reached
            char e = (i == numberOfColumns) ? ' ' : '|';
            cell = String.format(" %-" + columnWidth + "s" + e, rsmd.getColumnTypeName(i));
            sb.append(cell);
        }
        /*
            print bar
         */
        sb.append("\n");
        for (int i = 1; i <= numberOfColumns ; i++) {
            columnWidth = rsmd.getColumnDisplaySize(i)+1;
            String bar = String.format("%-" + columnWidth + "s", "-").replace(' ', '-');
            sb.append(bar);
            if (i != numberOfColumns) { sb.append("+"); }
        }
        sb.append("-");
        System.out.println(sb.toString());
    }

    private void printBody() throws SQLException {
        ResultSetMetaData rdms = rs.getMetaData();
        int numberOfColumns = rdms.getColumnCount();
        rs.first();
        while (rs.next()) {
            for (int i = 1; i <= numberOfColumns; i++) {
                int columnWidth = rdms.getColumnDisplaySize(i);
                boolean isInt = rdms.getColumnTypeName(i).startsWith("int");
                // replace '|' with space when end of line is reached
                char e = (i == numberOfColumns) ? ' ' : '|';
                String template = isInt ? "%" + columnWidth + "s " + e : " %-" + columnWidth + "s" + e;
                System.out.printf(template, rs.getString(i));
            }
            System.out.print("\n");
        }
    }

    public void printResult() throws SQLException {
        printHeader();
        printBody();
    }

    public static void main (String[] args) {
        String url = "jdbc:postgresql://datenbanken1.ddns.net:3690/g20";
        String username = "g20";
        String password = "XfgZfBTstd";

        JDBCFoo pgsql = new JDBCFoo(url, username, password);
        Connection conn = pgsql.getConnection();

        String query = "SELECT persnr, name, ort, aufgabe FROM personal";
        try {
            JDBCQueryResult result = new JDBCQueryResult(conn, query);
            result.execute();
            result.printResult();
            result.execute();
            result.printResult();
        } catch (SQLException e) {
            e.getMessage();
        }
    }
}
