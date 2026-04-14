package com.brh.projekt_plan4sign_2026.dao;

import com.brh.projekt_plan4sign_2026.model.Dolmetscher;
import com.brh.projekt_plan4sign_2026.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DolmetscherDAO.java – Datenzugriffsklasse für die Tabelle "Dolmetscher"
 * Kapselt alle Datenbankoperationen für Dolmetscher
 *
 * Methoden:
 * - getAll()       → Alle Dolmetscher (für ComboBox in AdminView)
 * - getByID()      → Einzelner Dolmetscher nach ID
 * - getByUserID()  → Für Login-Navigation (Dependency Injection)
 * - insert()       → Neuen Dolmetscher einfügen
 * - delete()       → Dolmetscher löschen
 */
public class DolmetscherDAO {

    /**
     * Holt alle Dolmetscher aus der Datenbank
     * Wird in AdminController.loadDolmetscher() verwendet
     * um die ComboBox zu befüllen
     */
    public List<Dolmetscher> getAll() throws SQLException {

        // Ergebnisliste für Java-Objekte
        List<Dolmetscher> list = new ArrayList<>();

        // SQL: alle Datensätze aus der Tabelle Dolmetscher
        String sql = "SELECT * FROM Dolmetscher";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // Statement zum Ausführen der SQL-Abfrage
        Statement statement = connection.createStatement();

        // Abfrage ausführen → gibt ResultSet (Tabelle von Ergebnissen) zurück
        ResultSet resultSet = statement.executeQuery(sql);

        // Alle Zeilen des Ergebnisses durchlaufen
        while (resultSet.next()) {

            // Spaltenwerte der aktuellen Zeile lesen
            int dolmetscherID = resultSet.getInt("DolmetscherID");
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");
            String email = resultSet.getString("email");
            String mobilePhone = resultSet.getString("mobilephone");
            String comment = resultSet.getString("comment");

            // Fremdschlüssel: Verknüpfung zum User
            int userID = resultSet.getInt("UserID");

            // DB-Zeile → Java-Objekt (Mapping)
            list.add(new Dolmetscher(dolmetscherID, firstName, lastName,
                    email, mobilePhone, comment, userID));
        }
        return list;
    }

    /**
     * Sucht einen einzelnen Dolmetscher anhand seiner ID
     * Rückgabe: Dolmetscher-Objekt oder null wenn nicht gefunden
     */
    public Dolmetscher getByID(int dolmetscherID) throws SQLException {

        // SQL: Suche nach Primärschlüssel
        String sql = "SELECT * FROM Dolmetscher WHERE DolmetscherID = ?";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement → sicherer als Statement, verhindert SQL-Injection
        PreparedStatement statement = connection.prepareStatement(sql);

        // ID als Parameter setzen
        statement.setInt(1, dolmetscherID);

        // Abfrage ausführen → gibt ResultSet (Tabelle von Ergebnissen) zurück
        ResultSet resultSet = statement.executeQuery();

        // Prüfen ob ein Ergebnis vorhanden ist
        if (resultSet.next()) {
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");
            String email = resultSet.getString("email");
            String mobilePhone = resultSet.getString("mobilephone");
            String comment = resultSet.getString("comment");

            // Fremdschlüssel zum User
            int userID = resultSet.getInt("UserID");

            // DB-Zeile → Java-Objekt (Mapping)
            return new Dolmetscher(dolmetscherID, firstName, lastName,
                    email, mobilePhone, comment, userID);
        }

        // Kein Dolmetscher mit dieser ID gefunden
        return null;
    }

    /**
     * Fügt einen neuen Dolmetscher in die Datenbank ein
     * UserID ist Pflichtfeld (UNIQUE, NOT NULL)
     */
    public void insert(Dolmetscher dolmetscher) throws SQLException {

        // SQL: neuen Datensatz einfügen
        String sql = "INSERT INTO Dolmetscher " +
                "(firstname, lastname, email, mobilephone, comment, UserID) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement → sicherer als Statement, verhindert SQL-Injection
        PreparedStatement statement = connection.prepareStatement(sql);

        // Persönliche Daten setzen
        statement.setString(1, dolmetscher.getFirstname());
        statement.setString(2, dolmetscher.getLastname());
        statement.setString(3, dolmetscher.getEmail());
        statement.setString(4, dolmetscher.getMobilePhone());
        statement.setString(5, dolmetscher.getComment());

        // Fremdschlüssel setzen (Verknüpfung zum User)
        statement.setInt(6, dolmetscher.getUserID());

        // Einfügen ausführen
        statement.executeUpdate();
    }

    /**
     * Löscht einen Dolmetscher anhand seiner ID
     * Hinweis: ON DELETE SET NULL → DolmetscherID in Unterricht wird NULL
     *          ON DELETE CASCADE  → Availability-Einträge werden gelöscht
     */
    public void delete(int dolmetscherID) throws SQLException {

        // SQL: Datensatz anhand der ID löschen
        String sql = "DELETE FROM Dolmetscher WHERE DolmetscherID = ?";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement → sicherer als Statement, verhindert SQL-Injection
        PreparedStatement statement = connection.prepareStatement(sql);

        // ID als Parameter setzen
        statement.setInt(1, dolmetscherID);

        // Löschung ausführen
        statement.executeUpdate();
    }

    /**
     * Sucht einen Dolmetscher anhand der UserID
     * Wird nach dem Login aufgerufen (Dependency Injection)
     * LoginController → getByUserID() → DolmetscherController.setDolmetscher()
     * Rückgabe: Dolmetscher-Objekt oder null wenn nicht gefunden
     */
    public Dolmetscher getByUserID(int userID) throws SQLException {

        // SQL: Suche nach UserID (UNIQUE → maximal ein Ergebnis)
        String sql = "SELECT * FROM Dolmetscher WHERE UserID = ?";

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
            int dolmetscherID = resultSet.getInt("DolmetscherID");
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");
            String email = resultSet.getString("email");
            String mobilePhone = resultSet.getString("mobilephone");
            String comment = resultSet.getString("comment");

            // DB-Zeile → Java-Objekt (Mapping)
            return new Dolmetscher(dolmetscherID, firstName, lastName,
                    email, mobilePhone, comment, userID);
        }

        // Kein Dolmetscher mit dieser UserID gefunden
        return null;
    }
}