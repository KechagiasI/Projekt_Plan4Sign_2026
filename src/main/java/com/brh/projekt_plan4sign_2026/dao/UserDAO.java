package com.brh.projekt_plan4sign_2026.dao;

import com.brh.projekt_plan4sign_2026.model.Role;
import com.brh.projekt_plan4sign_2026.model.User;
import com.brh.projekt_plan4sign_2026.util.DatabaseConnection;
import com.brh.projekt_plan4sign_2026.util.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO {

    // Holt alle Benutzer aus der Datenbank und gibt sie als Liste zurück
    // Holt alle Benutzer → Mapping DB → Java-Liste
    public List<User> getAll() throws SQLException {

        // Liste für die Ergebnisse (Java-Objekte)
        List<User> list = new ArrayList<>();

        // SQL-Abfrage: alle Datensätze aus der Tabelle User
        String sql = "SELECT * FROM User";

        // try-with-resources: Connection und Statement werden automatisch geschlossen
        // Verbindung zur Datenbank holen (Singleton)
        try (Connection connection = DatabaseConnection.getConnection();

        // Statement zum Ausführen der SQL-Abfrage
        // Hinweis: PreparedStatement wäre auch hier Best Practice
        PreparedStatement statement = connection.prepareStatement(sql);

        // Ergebnis der Abfrage (ResultSet = Tabelle von Daten)
        ResultSet resultSet = statement.executeQuery()) {

            // Alle Datensätze durchlaufen
            while (resultSet.next()) {

                // Werte aus der aktuellen Zeile lesen
                int userID = resultSet.getInt("UserID");
                String username = resultSet.getString("username");
                String passwordHash = resultSet.getString("passwordHash");

                // Umwandlung des Rollen-Strings aus der DB in ein Enum
                // DB-String → Enum (muss exakt übereinstimmen!)
                Role role = Role.valueOf(resultSet.getString("role"));

                // Erstellung eines User-Objekts (Mapping DB → Java)
                list.add(new User(userID, username, passwordHash, role));
            }
        }
        // Rückgabe der kompletten Liste
        return list;
    }
    // Sucht einen Benutzer anhand des Usernames (z.B. für Login)
    // Login-Suche → gibt User oder Optional.empty()
    // Optional<User>: gibt entweder einen User zurück oder Optional.empty() (kein null)
    public Optional<User> getByUsername(String username) throws SQLException {

        // SQL-Abfrage mit WHERE-Bedingung
        String sql = "SELECT * FROM User WHERE username = ?";

        // Verbindung holen
        try (Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement verwenden (sicher + Parameter)
        PreparedStatement statement = connection.prepareStatement(sql)) {

            // Setzt den Username als Parameter
            // setzen → schützt vor SQL-Injection
            statement.setString(1, username);

            // Führt die Abfrage aus
            try (ResultSet resultSet = statement.executeQuery()) {

                // Prüft, ob ein Ergebnis vorhanden ist
                if (resultSet.next()) {
                    int userID = resultSet.getInt("UserID");
                    String passwordHash = resultSet.getString("passwordHash");

                    // String → Enum
                    Role role = Role.valueOf(resultSet.getString("role"));

                    // Rückgabe eines einzelnen User-Objekts
                    return Optional.of(new User(userID, username, passwordHash, role));
                }
            }
        }
        // Kein Benutzer gefunden → Optional.empty() statt null
        return Optional.empty();
    }
    // Fügt einen neuen Benutzer in die Datenbank ein
    // Neuer Benutzer → Passwort wird vorher gehasht!
    public void insert(User user) throws SQLException {

        // SQL-Insert mit drei Parametern
        String sql = "INSERT INTO User (username, passwordHash, role) VALUES (?, ?, ?)";

        // Verbindung holen
        try (Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement erstellen
        PreparedStatement statement = connection.prepareStatement(sql)) {

            // Werte setzen
            statement.setString(1, user.getUsername());

            // WICHTIG: Klartext → Hash (nie Klartext speichern!)
            statement.setString(2, PasswordUtil.hash(user.getPasswordHash()));

            // Enum → String (für Speicherung in DB)
            statement.setString(3, user.getRole().name());

            // Ausführen der Datenbankänderung
            statement.executeUpdate();
        }
    }
    // Löscht einen Benutzer anhand seiner ID
    public void delete(int userID) throws SQLException {

        // SQL-Delete mit Bedingung
        String sql = "DELETE FROM User WHERE UserID = ?";

        // Verbindung holen
        try (Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement verwenden
        PreparedStatement statement = connection.prepareStatement(sql)) {

            // ID setzen
            statement.setInt(1, userID);

            // Löschung ausführen
            statement.executeUpdate();
        }
    }
}
