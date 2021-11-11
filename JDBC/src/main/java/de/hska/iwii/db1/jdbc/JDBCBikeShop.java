package de.hska.iwii.db1.jdbc;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Diese Klasse ist die Basis für Ihre Lösung. Mit Hilfe der
 * Methode reInitializeDB können Sie die beim Testen veränderte
 * Datenbank wiederherstellen.
 */
public class JDBCBikeShop {

    /**
     * Stellt die Datenbank aus der SQL-Datei wieder her.
     * - Alle Tabllen mit Inhalt ohne Nachfrage löschen.
     * - Alle Tabellen wiederherstellen.
     * - Tabellen mit Daten füllen.
     * <p>
     * Getestet mit MsSQL 12, MySql 8.0.8, Oracle 11g, Oracle 18 XE, PostgreSQL 11.
     * <p>
     * Das entsprechende Sql-Skript befindet sich im Ordner ./sql im Projekt.
     * @param connection Geöffnete Verbindung zu dem DBMS, auf dem die
     * 					Bike-Datenbank wiederhergestellt werden soll. 
     */
    public void reInitializeDB(Connection connection) {
        try {
            System.out.println("\nInitializing DB.");
            connection.setAutoCommit(true);
            String productName = connection.getMetaData().getDatabaseProductName();
            boolean isMsSql = productName.equals("Microsoft SQL Server");
            Statement statement = connection.createStatement();
            int numStmts = 0;
            
            // Liest den Inhalt der Datei ein.
            String[] fileContents = new String(Files.readAllBytes(Paths.get("sql/hska_bike.sql")),
					StandardCharsets.UTF_8).split(";");
            
            for (String sqlString : fileContents) {
                try {
                	// Microsoft kenn den DATE-Operator nicht.
                    if (isMsSql) {
                        sqlString = sqlString.replace(", DATE '", ", '");
                    }
                    statement.execute(sqlString);
                    System.out.print((++numStmts % 80 == 0 ? "/\n" : "."));
                } catch (SQLException e) {
                    System.out.print("\n" + sqlString.replace('\n', ' ').trim() + ": ");
                    System.out.println(e.getMessage());
                }
            }
            statement.close();
            System.out.println("\nBike database is reinitialized on " + productName +
                    "\nat URL " + connection.getMetaData().getURL()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
