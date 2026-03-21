package com.brh.projekt_plan4sign_2026.dao;

import com.brh.projekt_plan4sign_2026.model.Role;
import com.brh.projekt_plan4sign_2026.model.User;
import com.brh.projekt_plan4sign_2026.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Holt alle Benutzer aus der Datenbank und gibt sie als Liste zurück
    public List<User> getAll() throws SQLException {

        // Liste für die Ergebnisse (Java-Objekte)
        List<User> list = new ArrayList<>();

        // SQL-Abfrage: alle Datensätze aus der Tabelle User
        String sql = "SELECT * FROM User";

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
            int userID = resultSet.getInt("UserID");
            String username = resultSet.getString("username");
            String passwordHash = resultSet.getString("passwordHash");

            // Umwandlung des Rollen-Strings aus der DB in ein Enum
            Role role = Role.valueOf(resultSet.getString("role"));

            // Erstellung eines User-Objekts (Mapping DB → Java)
            list.add(new User(userID, username, passwordHash, role));
        }

        // Rückgabe der kompletten Liste
        return list;
    }

    // Sucht einen Benutzer anhand des Usernames (z.B. für Login)
    public User getByUsername(String username) throws SQLException {

        // SQL-Abfrage mit WHERE-Bedingung
        String sql = "SELECT * FROM User WHERE username = ?";

        // Verbindung holen
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement verwenden (sicher + Parameter)
        PreparedStatement statement = connection.prepareStatement(sql);

        // Setzt den Username als Parameter
        statement.setString(1, username);

        // Führt die Abfrage aus
        ResultSet resultSet = statement.executeQuery();

        // Prüft, ob ein Ergebnis vorhanden ist
        if (resultSet.next()) {
            int userID = resultSet.getInt("UserID");
            String passwordHash = resultSet.getString("passwordHash");

            // String → Enum
            Role role = Role.valueOf(resultSet.getString("role"));

            // Rückgabe eines einzelnen User-Objekts
            return new User(userID, username, passwordHash, role);
        }

        // Wenn kein Benutzer gefunden wurde → null zurückgeben
        return null;
    }

    // Fügt einen neuen Benutzer in die Datenbank ein
    public void insert(User user) throws SQLException {

        // SQL-Insert mit drei Parametern
        String sql = "INSERT INTO User (username, passwordHash, role) VALUES (?, ?, ?)";

        // Verbindung holen
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement erstellen
        PreparedStatement statement = connection.prepareStatement(sql);

        // Werte setzen
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPasswordHash());

        // Enum → String (für Speicherung in DB)
        statement.setString(3, user.getRole().name());

        // Ausführen der Datenbankänderung
        statement.executeUpdate();
    }

    // Löscht einen Benutzer anhand seiner ID
    public void delete(int userID) throws SQLException {

        // SQL-Delete mit Bedingung
        String sql = "DELETE FROM User WHERE UserID = ?";

        // Verbindung holen
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement verwenden
        PreparedStatement statement = connection.prepareStatement(sql);

        // ID setzen
        statement.setInt(1, userID);

        // Löschung ausführen
        statement.executeUpdate();
    }
}
