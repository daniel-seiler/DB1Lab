package de.hska.iwii.db1.jdbc;

import java.sql.*;

public class JDBCQueries {

    private final Connection connection;

    public JDBCQueries(Connection connection) throws SQLException{
        this.connection = connection;
    }

    private void printHeader(ResultSetMetaData rsmd) throws SQLException {
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

    private void printBody(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
        rs.first();
        while (rs.next()) {
            for (int i = 1; i <= numberOfColumns; i++) {
                int columnWidth = rsmd.getColumnDisplaySize(i);
                boolean isInt = rsmd.getColumnTypeName(i).startsWith("int");
                // replace '|' with space when end of line is reached
                char e = (i == numberOfColumns) ? ' ' : '|';
                String template = isInt ? "%" + columnWidth + "s " + e : " %-" + columnWidth + "s" + e;
                System.out.printf(template, rs.getString(i));
            }
            System.out.print("\n");
        }
    }

    /*
    Task 4.2
    print staff with staff no, place, occupation
     */
    public void printStaff() throws SQLException {
        String query = "SELECT persnr, name, ort, aufgabe FROM personal";
        Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = statement.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        printHeader(rsmd);
        printBody(rs);
    }

    /*
    Task 4.3
    print client <-> supplier relationship with optional client search
     */
    public void printClientsWithSuppliers(String search) throws SQLException {
        String query = "SELECT k.name as kunde, k.nr as knr, lant.name as lieferant, lant.nr as lnr\n" +
                "FROM auftragsposten as ap\n" +
                "INNER JOIN lieferung lung on ap.teilnr = lung.teilnr\n" +
                "INNER JOIN lieferant lant on lung.liefnr = lant.nr\n" +
                "INNER JOIN auftrag a on a.auftrnr = ap.auftrnr\n" +
                "INNER JOIN kunde k on k.nr = a.kundnr\n" +
                "WHERE lower(k.name) LIKE lower(?)";
        PreparedStatement statement = connection.prepareStatement(
                query,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, search);
        ResultSet rs = statement.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        printHeader(rsmd);
        printBody(rs);
    }

    public void printClientsWithSuppliers() throws SQLException {
        printClientsWithSuppliers("%");
    }

    public static void main (String[] args) {
        String url = "jdbc:postgresql://datenbanken1.ddns.net:3690/g20";
        String username = "g20";
        String password = "XfgZfBTstd";

        JDBCFoo pgsql = new JDBCFoo(url, username, password);
        Connection conn = pgsql.getConnection();
        try {
            JDBCQueries shop_db = new JDBCQueries(conn);
            shop_db.printStaff();
            shop_db.printClientsWithSuppliers("%Fahr%");
        } catch (SQLException e) {
            e.getMessage();
        }
    }
}
