package com.brh.projekt_plan4sign_2026.dao;

import com.brh.projekt_plan4sign_2026.model.Fach;
import com.brh.projekt_plan4sign_2026.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FachDAO.java – Datenzugriffsklasse für die Tabelle "Fach"
 * Kapselt alle Datenbankoperationen für Unterrichtsfächer
 *
 * Methoden:
 * - getAll()        → Alle Fächer
 * - getByBereich()  → Gefiltert nach Bereich
 * - delete()        → Fach löschen
 *
 * Hinweis: UNIQUE(fachname, BereichID) verhindert doppelte Einträge
 */
public class FachDAO {

    /**
     * Holt alle Fächer aus der Datenbank
     * Rückgabe: Liste aller Fach-Objekte
     */
    public List<Fach> getAll() throws SQLException {

        // Ergebnisliste für Java-Objekte
        List<Fach> list = new ArrayList<>();

        // SQL: alle Datensätze aus der Tabelle Fach
        String sql = "SELECT * FROM Fach";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // Statement zum Ausführen der SQL-Abfrage
        Statement statement = connection.createStatement();

        // Abfrage ausführen → gibt ResultSet (Tabelle von Ergebnissen) zurück
        ResultSet resultSet = statement.executeQuery(sql);

        // Alle Zeilen des Ergebnisses durchlaufen
        while (resultSet.next()) {

            // Spaltenwerte der aktuellen Zeile lesen
            int fachID = resultSet.getInt("FachID");
            String fachname = resultSet.getString("fachname");

            // BOOLEAN aus DB lesen: true = Dolmetscher erforderlich
            boolean isInterpreterRelevant = resultSet.getBoolean("isinterpreterRelevant");
            int bereichID = resultSet.getInt("BereichID");

            // DB-Zeile → Java-Objekt (Mapping)
            list.add(new Fach(fachID, fachname, isInterpreterRelevant, bereichID));
        }
        return list;
    }

    /**
     * Holt alle Fächer eines bestimmten Bereichs
     * WHERE BereichID = ? → nur Fächer des gewählten Bereichs
     * Beispiel: BereichID=2 → alle FIA-Fächer
     */
    public List<Fach> getByBereich(int bereichID) throws SQLException {

        // Ergebnisliste für gefilterte Java-Objekte
        List<Fach> list = new ArrayList<>();

        // SQL: Suche nach BereichID
        String sql = "SELECT * FROM Fach WHERE BereichID = ?";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement → sicherer als Statement, verhindert SQL-Injection
        PreparedStatement statement = connection.prepareStatement(sql);

        // BereichID als Parameter setzen
        statement.setInt(1, bereichID);

        // Abfrage ausführen → gibt ResultSet (Tabelle von Ergebnissen) zurück
        ResultSet resultSet = statement.executeQuery();

        // Alle Zeilen des Ergebnisses durchlaufen
        while (resultSet.next()) {
            int fachID = resultSet.getInt("FachID");
            String fachname = resultSet.getString("fachname");

            // BOOLEAN aus DB lesen: true = Dolmetscher erforderlich
            boolean isInterpreterRelevant = resultSet.getBoolean("isinterpreterRelevant");

            // bereichID kommt direkt aus dem Parameter
            list.add(new Fach(fachID, fachname, isInterpreterRelevant, bereichID));
        }
        return list;
    }

    /**
     * Löscht ein Fach anhand seiner ID
     * Hinweis: ON DELETE RESTRICT verhindert Löschung
     * wenn noch Unterrichtseinheiten mit diesem Fach verknüpft sind
     */
    public void delete(int fachID) throws SQLException {

        // SQL: Datensatz anhand der ID löschen
        String sql = "DELETE FROM Fach WHERE FachID = ?";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement → sicherer als Statement, verhindert SQL-Injection
        PreparedStatement statement = connection.prepareStatement(sql);

        // ID als Parameter setzen
        statement.setInt(1, fachID);

        // Löschung ausführen
        statement.executeUpdate();
    }
}