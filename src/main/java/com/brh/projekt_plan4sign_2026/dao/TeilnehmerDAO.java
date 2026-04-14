package com.brh.projekt_plan4sign_2026.dao;

import com.brh.projekt_plan4sign_2026.model.Teilnehmer;
import com.brh.projekt_plan4sign_2026.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TeilnehmerDAO.java – Datenzugriffsklasse für die Tabelle "Teilnehmer"
 * Kapselt alle Datenbankoperationen für Teilnehmer
 *
 * Methoden:
 * - getAll()        → Alle Teilnehmer
 * - getByKlasse()   → Gefiltert nach Klasse
 * - getByUserID()   → Für Login-Navigation (Dependency Injection)
 * - insert()        → Neuen Teilnehmer einfügen
 * - delete()        → Teilnehmer löschen
 */
public class TeilnehmerDAO {

    /**
     * Holt alle Teilnehmer aus der Datenbank
     * Rückgabe: Liste aller Teilnehmer-Objekte
     */
    public List<Teilnehmer> getAll() throws SQLException {

        // Ergebnisliste für Java-Objekte
        List<Teilnehmer> list = new ArrayList<>();

        // SQL: alle Datensätze aus der Tabelle Teilnehmer
        String sql = "SELECT * FROM Teilnehmer";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // Statement zum Ausführen der SQL-Abfrage
        Statement statement = connection.createStatement();

        // Abfrage ausführen → gibt ResultSet (Tabelle von Ergebnissen) zurück
        ResultSet resultSet = statement.executeQuery(sql);

        // Alle Zeilen des Ergebnisses durchlaufen
        while (resultSet.next()) {

            // Spaltenwerte der aktuellen Zeile lesen
            int teilnehmerID = resultSet.getInt("TeilnehmerID");
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");
            String email = resultSet.getString("email");
            String mobilePhone = resultSet.getString("mobilephone");
            String comment = resultSet.getString("comment");

            // Fremdschlüssel: Beziehung zur Klasse
            int klasseID = resultSet.getInt("KlasseID");

            // Fremdschlüssel: Beziehung zum User
            int userID = resultSet.getInt("UserID");

            // DB-Zeile → Java-Objekt (Mapping)
            list.add(new Teilnehmer(
                    teilnehmerID, firstName, lastName,
                    email, mobilePhone, comment,
                    klasseID, userID));
        }
        return list;
    }

    /**
     * Holt alle Teilnehmer einer bestimmten Klasse
     * Wird intern verwendet um Teilnehmer einer Klasse zu finden
     * WHERE KlasseID = ? → nur Teilnehmer der gewählten Klasse
     */
    public List<Teilnehmer> getByKlasse(int klasseID) throws SQLException {

        // Ergebnisliste für gefilterte Java-Objekte
        List<Teilnehmer> list = new ArrayList<>();

        // SQL: Suche nach KlasseID
        String sql = "SELECT * FROM Teilnehmer WHERE KlasseID = ?";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement → sicherer als Statement, verhindert SQL-Injection
        PreparedStatement statement = connection.prepareStatement(sql);

        // KlasseID als Parameter setzen
        statement.setInt(1, klasseID);

        // Abfrage ausführen → gibt ResultSet (Tabelle von Ergebnissen) zurück
        ResultSet resultSet = statement.executeQuery();

        // Alle Zeilen des Ergebnisses durchlaufen
        while (resultSet.next()) {

            int teilnehmerID = resultSet.getInt("TeilnehmerID");
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");
            String email = resultSet.getString("email");
            String mobilePhone = resultSet.getString("mobilephone");
            String comment = resultSet.getString("comment");
            int userID = resultSet.getInt("UserID");

            // klasseID kommt direkt aus dem Parameter
            list.add(new Teilnehmer(
                    teilnehmerID, firstName, lastName,
                    email, mobilePhone, comment,
                    klasseID, userID));
        }
        return list;
    }

    /**
     * Fügt einen neuen Teilnehmer in die Datenbank ein
     * KlasseID und UserID sind Pflichtfelder (NOT NULL)
     */
    public void insert(Teilnehmer teilnehmer) throws SQLException {

        // SQL: neuen Datensatz einfügen
        String sql = "INSERT INTO Teilnehmer " +
                "(firstname, lastname, email, mobilephone, comment, KlasseID, UserID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement → sicherer als Statement, verhindert SQL-Injection
        PreparedStatement statement = connection.prepareStatement(sql);

        // Persönliche Daten setzen
        statement.setString(1, teilnehmer.getFirstname());
        statement.setString(2, teilnehmer.getLastname());
        statement.setString(3, teilnehmer.getEmail());
        statement.setString(4, teilnehmer.getMobilephone());
        statement.setString(5, teilnehmer.getComment());

        // Fremdschlüssel setzen (Beziehungen zu Klasse und User)
        statement.setInt(6, teilnehmer.getKlasseID());
        statement.setInt(7, teilnehmer.getUserID());

        // Einfügen ausführen
        statement.executeUpdate();
    }

    /**
     * Löscht einen Teilnehmer anhand seiner ID
     */
    public void delete(int teilnehmerID) throws SQLException {

        // SQL: Datensatz anhand der ID löschen
        String sql = "DELETE FROM Teilnehmer WHERE TeilnehmerID = ?";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement → sicherer als Statement, verhindert SQL-Injection
        PreparedStatement statement = connection.prepareStatement(sql);

        // ID als Parameter setzen
        statement.setInt(1, teilnehmerID);

        // Löschung ausführen
        statement.executeUpdate();
    }

    /**
     * Sucht einen Teilnehmer anhand der UserID
     * Wird nach dem Login aufgerufen (Dependency Injection)
     * LoginController → getByUserID() → TeilnehmerController.setTeilnehmer()
     * Rückgabe: Teilnehmer-Objekt oder null wenn nicht gefunden
     */
    public Teilnehmer getByUserID(int userID) throws SQLException {

        // SQL: Suche nach UserID (UNIQUE → maximal ein Ergebnis)
        String sql = "SELECT * FROM Teilnehmer WHERE UserID = ?";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement → sicherer als Statement, verhindert SQL-Injection
        PreparedStatement statement = connection.prepareStatement(sql);

        // UserID als Parameter setzen
        statement.setInt(1, userID);

        // Abfrage ausführen → gibt ResultSet (Tabelle von Ergebnissen) zurück
        ResultSet resultSet = statement.executeQuery();

        // Prüfen ob ein Ergebnis vorhanden ist
        if (resultSet.next()) {
            int teilnehmerID = resultSet.getInt("TeilnehmerID");
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");
            String email = resultSet.getString("email");
            String mobilePhone = resultSet.getString("mobilephone");
            String comment = resultSet.getString("comment");
            int klasseID = resultSet.getInt("KlasseID");

            // DB-Zeile → Java-Objekt (Mapping)
            return new Teilnehmer(teilnehmerID, firstName, lastName,
                    email, mobilePhone, comment, klasseID, userID);
        }

        // Kein Teilnehmer mit dieser UserID gefunden
        return null;
    }
}