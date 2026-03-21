package com.brh.projekt_plan4sign_2026.dao;

import com.brh.projekt_plan4sign_2026.model.Klasse;
import com.brh.projekt_plan4sign_2026.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KlasseDAO {

    // Holt alle Klassen aus der Datenbank und gibt sie als Liste zurück
    public List<Klasse> getAll() throws SQLException {

        // Liste für die Ergebnisse (Java-Objekte)
        List<Klasse> list = new ArrayList<>();

        // SQL-Abfrage: alle Datensätze aus der Tabelle Klasse
        String sql = "SELECT * FROM Klasse";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // Statement zum Ausführen der SQL-Abfrage
        // Hinweis: PreparedStatement wäre auch hier Best Practice
        Statement statement = connection.createStatement();

        // Ergebnis der Abfrage (ResultSet = Tabelle von Daten)
        ResultSet resultSet = statement.executeQuery(sql);

        // Iteration über alle Datensätze
        while (resultSet.next()) {

            // Werte aus der aktuellen Zeile lesen (Spaltennamen aus DB)
            int klasseID = resultSet.getInt("KlasseID");
            String klasseName = resultSet.getString("klassename");
            String room = resultSet.getString("room");

            // Umwandlung in ein Java-Objekt (Model)
            list.add(new Klasse(klasseID, klasseName, room));
        }

        // Hinweis: ResultSet und Statement sollten in echten Projekten geschlossen werden
        // Rückgabe der kompletten Liste
        return list;
    }

    // Fügt eine neue Klasse in die Datenbank ein
    public void insert(Klasse klasse) throws SQLException {

        // SQL-Insert mit zwei Platzhaltern (?)
        String sql = "INSERT INTO Klasse (klassename, room) VALUES (?, ?)";

        // Verbindung holen
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement schützt vor SQL-Injection und setzt Werte sicher ein
        PreparedStatement statement = connection.prepareStatement(sql);

        // Setzt die Werte für die Platzhalter
        statement.setString(1, klasse.getKlassename());
        statement.setString(2, klasse.getRoom());

        // Führt die Änderung in der Datenbank aus
        statement.executeUpdate();
    }

    // Löscht eine Klasse anhand ihrer ID
    public void delete(int klasseID) throws SQLException {

        // SQL-Delete mit Bedingung
        String sql = "DELETE FROM Klasse WHERE KlasseID = ?";

        // Verbindung holen
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement verwenden
        PreparedStatement statement = connection.prepareStatement(sql);

        // Setzt die ID als Parameter
        statement.setInt(1, klasseID);

        // Führt die Löschung aus
        statement.executeUpdate();
    }
}
