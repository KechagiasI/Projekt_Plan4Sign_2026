package com.brh.projekt_plan4sign_2026.dao;

import com.brh.projekt_plan4sign_2026.model.Bereich;
import com.brh.projekt_plan4sign_2026.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BereichDAO.java – Datenzugriffsklasse für die Tabelle "Bereich"
 * Kapselt alle Datenbankoperationen für Ausbildungsbereiche
 *
 * Methoden:
 * - getAll()    → Alle Bereiche
 * - insert()    → Neuen Bereich einfügen
 * - delete()    → Bereich löschen
 *
 * Hinweis: ON DELETE RESTRICT verhindert Löschung
 * wenn noch Fächer mit diesem Bereich verknüpft sind
 */
public class BereichDAO {

    /**
     * Holt alle Bereiche aus der Datenbank
     * Rückgabe: Liste aller Bereich-Objekte
     */
    public List<Bereich> getAll() throws SQLException {

        // Ergebnisliste für Java-Objekte
        List<Bereich> list = new ArrayList<>();

        // SQL: alle Datensätze aus der Tabelle Bereich
        String sql = "SELECT * FROM Bereich";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // Statement zum Ausführen der SQL-Abfrage
        Statement statement = connection.createStatement();

        // Abfrage ausführen → gibt ResultSet (Tabelle von Ergebnissen) zurück
        ResultSet resultSet = statement.executeQuery(sql);

        // Alle Zeilen des Ergebnisses durchlaufen
        while (resultSet.next()) {

            // Spaltenwerte der aktuellen Zeile lesen
            int bereichID = resultSet.getInt("BereichID");
            String bereichName = resultSet.getString("bereichname");

            // DB-Zeile → Java-Objekt (Mapping)
            list.add(new Bereich(bereichID, bereichName));
        }
        return list;
    }

    /**
     * Fügt einen neuen Bereich in die Datenbank ein
     */
    public void insert(Bereich bereich) throws SQLException {

        // SQL: neuen Datensatz einfügen
        String sql = "INSERT INTO Bereich (bereichname) VALUES (?)";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement → sicherer als Statement, verhindert SQL-Injection
        PreparedStatement statement = connection.prepareStatement(sql);

        // Bereichsname als Parameter setzen
        statement.setString(1, bereich.getBereichName());

        // Einfügen ausführen
        statement.executeUpdate();
    }

    /**
     * Löscht einen Bereich anhand seiner ID
     * Hinweis: ON DELETE RESTRICT verhindert Löschung
     * wenn noch Fächer mit diesem Bereich verknüpft sind
     */
    public void delete(int bereichID) throws SQLException {

        // SQL: Datensatz anhand der ID löschen
        String sql = "DELETE FROM Bereich WHERE BereichID = ?";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement → sicherer als Statement, verhindert SQL-Injection
        PreparedStatement statement = connection.prepareStatement(sql);

        // ID als Parameter setzen
        statement.setInt(1, bereichID);

        // Löschung ausführen
        statement.executeUpdate();
    }
}