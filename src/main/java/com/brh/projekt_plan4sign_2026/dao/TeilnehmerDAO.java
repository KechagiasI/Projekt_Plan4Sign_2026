package com.brh.projekt_plan4sign_2026.dao;

import com.brh.projekt_plan4sign_2026.model.Teilnehmer;
import com.brh.projekt_plan4sign_2026.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeilnehmerDAO {

    // Holt alle Teilnehmer aus der Datenbank und gibt sie als Liste zurück
    public List<Teilnehmer> getAll() throws SQLException {

        // Liste für die Ergebnisse (Java-Objekte)
        List<Teilnehmer> list = new ArrayList<>();

        // SQL-Abfrage: alle Datensätze aus der Tabelle Teilnehmer
        String sql = "SELECT * FROM Teilnehmer";

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

            // Mapping: DB → Java-Objekt
            list.add(new Teilnehmer(
                    teilnehmerID,
                    firstName,
                    lastName,
                    email,
                    mobilePhone,
                    comment,
                    klasseID,
                    userID
            ));
        }

        // Rückgabe der kompletten Liste
        return list;
    }

    // Holt alle Teilnehmer einer bestimmten Klasse (Filter nach KlasseID)
    public List<Teilnehmer> getByKlasse(int klasseID) throws SQLException {

        // Liste für gefilterte Ergebnisse
        List<Teilnehmer> list = new ArrayList<>();

        // SQL-Abfrage mit WHERE-Bedingung
        String sql = "SELECT * FROM Teilnehmer WHERE KlasseID = ?";

        // Verbindung holen
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement verwenden (sicher + Parameter)
        PreparedStatement statement = connection.prepareStatement(sql);

        // Setzt die KlasseID als Parameter
        statement.setInt(1, klasseID);

        // Führt die Abfrage aus
        ResultSet resultSet = statement.executeQuery();

        // Iteration über die gefilterten Ergebnisse
        while (resultSet.next()) {

            int teilnehmerID = resultSet.getInt("TeilnehmerID");
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");
            String email = resultSet.getString("email");
            String mobilePhone = resultSet.getString("mobilephone");
            String comment = resultSet.getString("comment");

            // Fremdschlüssel: User bleibt gleich
            int userID = resultSet.getInt("UserID");

            // klasseID wird direkt aus Parameter übernommen
            list.add(new Teilnehmer(
                    teilnehmerID,
                    firstName,
                    lastName,
                    email,
                    mobilePhone,
                    comment,
                    klasseID,
                    userID
            ));
        }

        return list;
    }

    // Fügt einen neuen Teilnehmer in die Datenbank ein
    public void insert(Teilnehmer teilnehmer) throws SQLException {

        // SQL-Insert mit mehreren Parametern
        String sql = "INSERT INTO Teilnehmer (firstname, lastname, email, mobilephone, comment, KlasseID, UserID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Verbindung holen
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement erstellen
        PreparedStatement statement = connection.prepareStatement(sql);

        // Werte setzen (Java → DB Mapping)
        statement.setString(1, teilnehmer.getFirstname());
        statement.setString(2, teilnehmer.getLastname());
        statement.setString(3, teilnehmer.getEmail());
        statement.setString(4, teilnehmer.getMobilephone());
        statement.setString(5, teilnehmer.getComment());

        // Fremdschlüssel setzen (Beziehungen!)
        statement.setInt(6, teilnehmer.getKlasseID());
        statement.setInt(7, teilnehmer.getUserID());

        // Datenbankänderung ausführen
        statement.executeUpdate();
    }

    // Löscht einen Teilnehmer anhand seiner ID
    public void delete(int teilnehmerID) throws SQLException {

        // SQL-Delete mit Bedingung
        String sql = "DELETE FROM Teilnehmer WHERE TeilnehmerID = ?";

        // Verbindung holen
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement verwenden
        PreparedStatement statement = connection.prepareStatement(sql);

        // ID setzen
        statement.setInt(1, teilnehmerID);

        // Löschung ausführen
        statement.executeUpdate();
    }

    // Sucht einen Teilnehmer anhand der UserID (für Login-Navigation)
    public Teilnehmer getByUserID(int userID) throws SQLException {

        String sql = "SELECT * FROM Teilnehmer WHERE UserID = ?";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userID);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int teilnehmerID = resultSet.getInt("TeilnehmerID");
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");
            String email = resultSet.getString("email");
            String mobilePhone = resultSet.getString("mobilephone");
            String comment = resultSet.getString("comment");
            int klasseID = resultSet.getInt("KlasseID");

            return new Teilnehmer(teilnehmerID, firstName, lastName,
                    email, mobilePhone, comment, klasseID, userID);
        }

        // Kein Teilnehmer mit dieser UserID gefunden
        return null;
    }
}