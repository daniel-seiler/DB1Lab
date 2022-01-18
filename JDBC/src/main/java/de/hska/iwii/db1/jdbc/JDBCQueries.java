package de.hska.iwii.db1.jdbc;

import oracle.jdbc.proxy.annotation.Pre;

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
        System.out.println(sb);
    }

    private void printBody(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
        rs.first();
        do {
            for (int i = 1; i <= numberOfColumns; i++) {
                int columnWidth = rsmd.getColumnDisplaySize(i);
                boolean isInt = rsmd.getColumnTypeName(i).startsWith("int");
                // replace '|' with space when end of line is reached
                char e = (i == numberOfColumns) ? ' ' : '|';
                String template = isInt ? "%" + columnWidth + "s " + e : " %-" + columnWidth + "s" + e;
                System.out.printf(template, rs.getString(i));
            }
            System.out.print("\n");
        } while (rs.next());
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

    /*
    private int getColumnMax(String column, String table, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT ? FROM ? SORT BY ? DESC",
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, column);
        statement.setString(3, column);
        statement.setString(2, table);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
    */

    private int getColumnMax(String column, String table, Connection connection) throws SQLException {
        String query = String.format("SELECT max(%s) FROM %s", column, table);
        Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE
        );
        ResultSet rs = statement.executeQuery(query);
        rs.next();
        return rs.getInt(1);

    }
    public void addNewClientAndContract() throws SQLException {
        int persID = 9; // Azubi macht's
        int partID = 500015;
        int customerID = getColumnMax("nr", "kunde", connection) + 1;
        int orderID = getColumnMax("auftrnr", "auftrag", connection) + 1;
        int orderItemID = getColumnMax("posnr", "auftragsposten", connection) + 1;

        connection.setAutoCommit(false);
        String addClientQuery = "INSERT INTO kunde (nr, name, strasse, plz, ort, sperre)\n" +
                "VALUES (?, 'Paules Bikeshop', 'Fahrradgasse 1', 76135, 'Karlsruhe', '0')";
        PreparedStatement addClient = connection.prepareStatement(addClientQuery);
        addClient.setInt(1, customerID);
        addClient.executeUpdate();

        String addOrderQuery = "INSERT INTO auftrag (auftrnr, datum, kundnr, persnr)\n" +
                "VALUES (?, NOW(), ?, ?)";
        PreparedStatement addOrder = connection.prepareStatement(addOrderQuery);
        addOrder.setInt(1, orderID);
        addOrder.setInt(2, customerID);
        addOrder.setInt(3, persID);
        addOrder.executeUpdate();

        String addOrderItemQuery = "INSERT INTO auftragsposten (posnr, auftrnr, teilnr, anzahl, gesamtpreis)\n" +
                "VALUES (?, ?, 500015, 5, 3000)";
        PreparedStatement addOrderItem = connection.prepareStatement(addOrderItemQuery);
        addOrderItem.setInt(1, orderItemID);
        addOrderItem.setInt(2, orderID);
        addOrderItem.executeUpdate();
        try {
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Transaction is being rolled back");
            connection.rollback();
        }
    }

    public static void main (String[] args) {
        /*
        Task 4.1: Establish database connection
         */
        String url = "jdbc:postgresql://datenbanken1.ddns.net:3690/g20";
        String username = "g20";
        String password = "XfgZfBTstd";

        JDBCFoo pgsql = new JDBCFoo(url, username, password);
        Connection conn = pgsql.getConnection();
        try {
            System.out.println("Connecting...");
            JDBCQueries shop_db = new JDBCQueries(conn);
//            System.out.println("Print Staff:");
//            shop_db.printStaff();
            System.out.println("Add new clients and contracts:");
            shop_db.addNewClientAndContract();
            System.out.println("Print clients with suppliers::");
            shop_db.printClientsWithSuppliers();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
