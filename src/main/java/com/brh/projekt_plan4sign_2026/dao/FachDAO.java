package com.brh.projekt_plan4sign_2026.dao;

import com.brh.projekt_plan4sign_2026.model.Fach;
import com.brh.projekt_plan4sign_2026.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FachDAO {
    // Holt alle Fächer aus der Datenbank
    public List<Fach> getAll() throws SQLException {

        // Liste für die Ergebnisse (Java-Objekte)
        List<Fach> list = new ArrayList<>();

        // SQL-Abfrage: alle Datensätze aus der Tabelle Fach
        String sql = "SELECT * FROM Fach";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // Statement zum Ausführen der SQL-Abfrage
        // Hinweis: PreparedStatement wäre auch hier Best Practice
        Statement statement = connection.createStatement();

        // Ergebnis der Abfrage (ResultSet = Tabelle von Daten)
        ResultSet resultSet = statement.executeQuery(sql);

        // Iteration über alle Datensätze
        while (resultSet.next()) {

            // Werte aus der aktuellen Zeile lesen
            int fachID = resultSet.getInt("FachID");
            String fachname = resultSet.getString("fachname");
            boolean isInterpreterRelevant = resultSet.getBoolean("isinterpreterRelevant");
            int bereichID = resultSet.getInt("BereichID");

            // Umwandlung in ein Java-Objekt (Model)
            list.add(new Fach(fachID, fachname, isInterpreterRelevant, bereichID));
        }

        // Rückgabe der vollständigen Liste
        return list;

    }

    // Holt alle Fächer zu einem bestimmten Bereich (Filter)
    public List<Fach> getByBereich(int bereichID) throws SQLException {

        // Liste für die gefilterten Ergebnisse
        List<Fach> list = new ArrayList<>();

        // SQL-Abfrage mit Bedingung (WHERE)
        String sql = "SELECT * FROM Fach WHERE BereichID = ?";

        // Verbindung holen
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement (sicher + mit Parameter)
        PreparedStatement statement = connection.prepareStatement(sql);

        // Setzt den Wert für den Platzhalter (BereichID)
        statement.setInt(1, bereichID);

        // Führt die Abfrage aus
        ResultSet resultSet = statement.executeQuery();

        // Durchlaufen der Ergebnisse
        while (resultSet.next()) {
            int fachID = resultSet.getInt("FachID");
            String fachname = resultSet.getString("fachname");
            boolean isInterpreterRelevant = resultSet.getBoolean("isinterpreterRelavant");

            // Objekt erstellen und zur Liste hinzufügen
            list.add(new Fach(fachID, fachname, isInterpreterRelevant, bereichID));
        }
        return list;
    }

    // Löscht ein Fach anhand seiner ID
    public void delete(int fachID) throws SQLException {

        // SQL-Delete mit Bedingung
        String sql = "DELETE FROM Fach WHERE FachID = ?";

        // Verbindung holen
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement verwenden
        PreparedStatement statement = connection.prepareStatement(sql);

        // ID setzen
        statement.setInt(1, fachID);

        // Löschung ausführen
        statement.executeUpdate();
    }

}
