package com.brh.projekt_plan4sign_2026.dao;

import com.brh.projekt_plan4sign_2026.model.Bereich;
import com.brh.projekt_plan4sign_2026.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BereichDAO {
    // Holt alle Bereiche aus der Datenbank und gibt sie als Liste zurück
    public List<Bereich> getAll() throws SQLException {

        // Liste für die Ergebnisse (Java-Objekte)
        List<Bereich> list = new ArrayList<>();

        // SQL-Abfrage: alle Datensätze aus der Tabelle BEREICH
        String sql = "SELECT * FROM BEREICH";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();
        // Statement zum Ausführen der SQL-Abfrage
        // Hinweis: Besser wäre PreparedStatement (auch ohne Parameter → Best Practice)
        Statement statement = connection.createStatement();
        // Ergebnis der Abfrage (Tabelle von Datensätzen)
        ResultSet resultSet = statement.executeQuery(sql);

        // Iteration über alle Datensätze im ResultSet
        while (resultSet.next()) {
            // Werte aus der aktuellen Zeile lesen (Spaltennamen aus DB!)
            int bereichID = resultSet.getInt("BereichID");
            String bereich = resultSet.getString("bereichname");
            // Umwandlung in ein Java-Objekt (Model)
            list.add(new Bereich(bereichID, bereich));
        }
        // Hinweis: ResultSet und Statement sollten in echten Projekten geschlossen werden (Resource Management)
        // Rückgabe der kompletten Liste
        return list;
    }
    // Fügt einen neuen Bereich in die Datenbank ein
    public void insert(Bereich bereich) throws SQLException {
        // SQL-Insert mit Platzhalter (?)
        String sql = "INSERT INTO Bereich (bereichname) VALUES (?)";
        // Verbindung holen
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement schützt vor SQL-Injection und setzt Werte sicher ein
        PreparedStatement statement = connection.prepareStatement(sql);
        // Setzt den Wert für den Platzhalter (1. Parameter)
        statement.setString(1, bereich.getBereichname());
        // Führt die Änderung in der Datenbank aus (INSERT, UPDATE, DELETE)
        statement.executeUpdate();
    }
    // Löscht einen Bereich anhand seiner ID
    public void delete(int bereichID) throws SQLException {
        // SQL-Delete mit Bedingung
        String sql = "DELETE FROM BEREICH WHERE bereichID = ?";
        // Verbindung holen
        Connection connection = DatabaseConnection.getConnection();
        // PreparedStatement verwenden (sicher und flexibel)
        PreparedStatement statement = connection.prepareStatement(sql);
        // Setzt die ID als Parameter
        statement.setInt(1, bereichID);
        // Führt die Löschung aus
        statement.executeUpdate();
    }
}
