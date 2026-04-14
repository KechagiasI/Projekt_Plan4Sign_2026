package com.brh.projekt_plan4sign_2026.dao;

import com.brh.projekt_plan4sign_2026.model.Klasse;
import com.brh.projekt_plan4sign_2026.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * KlasseDAO.java – Datenzugriffsklasse für die Tabelle "Klasse"
 * Kapselt alle Datenbankoperationen für Schulklassen
 *
 * Methoden:
 * - getAll()    → Alle Klassen
 * - insert()    → Neue Klasse einfügen
 * - delete()    → Klasse löschen
 */
public class KlasseDAO {

    /**
     * Holt alle Klassen aus der Datenbank
     * Rückgabe: Liste aller Klasse-Objekte
     */
    public List<Klasse> getAll() throws SQLException {

        // Ergebnisliste für Java-Objekte
        List<Klasse> list = new ArrayList<>();

        // SQL: alle Datensätze aus der Tabelle Klasse
        String sql = "SELECT * FROM Klasse";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // Statement zum Ausführen der SQL-Abfrage
        Statement statement = connection.createStatement();

        // Abfrage ausführen → gibt ResultSet (Tabelle von Ergebnissen) zurück
        ResultSet resultSet = statement.executeQuery(sql);

        // Alle Zeilen des Ergebnisses durchlaufen
        while (resultSet.next()) {

            // Spaltenwerte der aktuellen Zeile lesen
            int klasseID = resultSet.getInt("KlasseID");
            String klasseName = resultSet.getString("klassename");
            String room = resultSet.getString("room");

            // DB-Zeile → Java-Objekt (Mapping)
            list.add(new Klasse(klasseID, klasseName, room));
        }
        return list;
    }

    /**
     * Fügt eine neue Klasse in die Datenbank ein
     */
    public void insert(Klasse klasse) throws SQLException {

        // SQL: neuen Datensatz einfügen
        String sql = "INSERT INTO Klasse (klassename, room) VALUES (?, ?)";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement → sicherer als Statement, verhindert SQL-Injection
        PreparedStatement statement = connection.prepareStatement(sql);

        // Werte als Parameter setzen
        statement.setString(1, klasse.getKlasseName());
        statement.setString(2, klasse.getRoom());

        // Einfügen ausführen
        statement.executeUpdate();
    }

    /**
     * Löscht eine Klasse anhand ihrer ID
     * Hinweis: ON DELETE RESTRICT verhindert Löschung
     * wenn noch Teilnehmer oder Unterricht verknüpft sind
     */
    public void delete(int klasseID) throws SQLException {

        // SQL: Datensatz anhand der ID löschen
        String sql = "DELETE FROM Klasse WHERE KlasseID = ?";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement → sicherer als Statement, verhindert SQL-Injection
        PreparedStatement statement = connection.prepareStatement(sql);

        // ID als Parameter setzen
        statement.setInt(1, klasseID);

        // Löschung ausführen
        statement.executeUpdate();
    }
}