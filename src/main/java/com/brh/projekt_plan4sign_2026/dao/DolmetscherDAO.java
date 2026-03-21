package com.brh.projekt_plan4sign_2026.dao;

import com.brh.projekt_plan4sign_2026.model.Dolmetscher;
import com.brh.projekt_plan4sign_2026.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DolmetscherDAO {

    // Holt alle Dolmetscher aus der Datenbank und gibt sie als Liste zurück
    public List<Dolmetscher> getAll() throws SQLException {

        // Liste für die Ergebnisse (Java-Objekte)
        List<Dolmetscher> list = new ArrayList<>();

        // SQL-Abfrage: alle Datensätze aus der Tabelle Dolmetscher
        String sql = "SELECT * FROM Dolmetscher";

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
            int dolmetscherID = resultSet.getInt("DolmetscherID");
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");
            String email = resultSet.getString("email");
            String mobilePhone = resultSet.getString("mobilephone");
            String comment = resultSet.getString("comment");

            // Fremdschlüssel: Verknüpfung zum User
            int userID = resultSet.getInt("UserID");

            // Mapping: DB-Daten → Java-Objekt
            list.add(new Dolmetscher(dolmetscherID, firstName, lastName,
                    email, mobilePhone, comment, userID));
        }

        // Rückgabe der kompletten Liste
        return list;
    }

    // Sucht einen Dolmetscher anhand seiner ID (Primärschlüssel)
    public Dolmetscher getByID(int dolmetscherID) throws SQLException {

        // SQL-Abfrage mit WHERE-Bedingung
        String sql = "SELECT * FROM Dolmetscher WHERE DolmetscherID = ?";

        // Verbindung holen
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement verwenden (sicher + Parameter)
        PreparedStatement statement = connection.prepareStatement(sql);

        // Setzt die ID als Parameter
        statement.setInt(1, dolmetscherID);

        // Führt die Abfrage aus
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");
            String email = resultSet.getString("email");
            String mobilePhone = resultSet.getString("mobilephone");
            String comment = resultSet.getString("comment");

            // Fremdschlüssel zum User
            int userID = resultSet.getInt("UserID");

            // Rückgabe eines einzelnen Objekts
            return new Dolmetscher(dolmetscherID, firstName, lastName,
                    email, mobilePhone, comment, userID);
        }

        // Wenn kein Dolmetscher gefunden wurde, wird null zurückgegeben
        return null;
    }

    // Fügt einen neuen Dolmetscher in die Datenbank ein
    public void insert(Dolmetscher dolmetscher) throws SQLException {

        // SQL-Insert mit mehreren Parametern
        String sql = "INSERT INTO Dolmetscher (firstname, lastname, email, mobilephone, comment, UserID) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        // Verbindung holen
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement erstellen
        PreparedStatement statement = connection.prepareStatement(sql);

        // Werte setzen (Java → DB Mapping)
        statement.setString(1, dolmetscher.getFirstname());
        statement.setString(2, dolmetscher.getLastname());
        statement.setString(3, dolmetscher.getEmail());
        statement.setString(4, dolmetscher.getMobilePhone());
        statement.setString(5, dolmetscher.getComment());

        // Fremdschlüssel setzen (User-Verbindung)
        statement.setInt(6, dolmetscher.getUserID());

        // Ausführen der Datenbankänderung
        statement.executeUpdate();
    }

    // Löscht einen Dolmetscher anhand seiner ID
    public void delete(int dolmetscherID) throws SQLException {

        // SQL-Delete mit Bedingung
        String sql = "DELETE FROM Dolmetscher WHERE DolmetscherID = ?";

        // Verbindung holen
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement verwenden
        PreparedStatement statement = connection.prepareStatement(sql);

        // ID setzen
        statement.setInt(1, dolmetscherID);

        // Löschung ausführen
        statement.executeUpdate();
    }
}