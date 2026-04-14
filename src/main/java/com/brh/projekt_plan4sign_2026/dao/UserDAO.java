package com.brh.projekt_plan4sign_2026.dao;

import com.brh.projekt_plan4sign_2026.model.Role;
import com.brh.projekt_plan4sign_2026.model.User;
import com.brh.projekt_plan4sign_2026.util.DatabaseConnection;
import com.brh.projekt_plan4sign_2026.util.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UserDAO.java – Datenzugriffsklasse für die Tabelle "User"
 * Kapselt alle Datenbankoperationen für Benutzer
 * Verwendet: PreparedStatement (Schutz vor SQL-Injection)
 * Verbindung über: DatabaseConnection (Singleton)
 */
public class UserDAO {

    /**
     * Holt alle Benutzer aus der Datenbank
     * Rückgabe: Liste aller User-Objekte
     */
    public List<User> getAll() throws SQLException {

        // Ergebnisliste für Java-Objekte
        List<User> list = new ArrayList<>();

        // SQL: alle Datensätze aus der Tabelle User
        String sql = "SELECT * FROM User";

        // try-with-resources: Verbindung wird automatisch geschlossen
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            // Alle Zeilen des Ergebnisses durchlaufen
            while (resultSet.next()) {

                // Spaltenwerte der aktuellen Zeile lesen
                int userID = resultSet.getInt("UserID");
                String username = resultSet.getString("username");
                String passwordHash = resultSet.getString("passwordHash");

                // DB-String → Java-Enum (muss exakt übereinstimmen!)
                Role role = Role.valueOf(resultSet.getString("role"));

                // DB-Zeile → Java-Objekt (Mapping)
                list.add(new User(userID, username, passwordHash, role));
            }
        }
        return list;
    }

    /**
     * Sucht einen Benutzer anhand des Benutzernamens
     * Wird beim Login verwendet
     * Rückgabe: Optional<User> – verhindert NullPointerException
     * Optional.of(user)    → Benutzer gefunden
     * Optional.empty()     → Benutzer nicht gefunden
     */
    public Optional<User> getByUsername(String username) throws SQLException {

        // SQL: Suche nach Username (eindeutig durch UNIQUE-Constraint)
        String sql = "SELECT * FROM User WHERE username = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Parameter setzen → schützt vor SQL-Injection
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {

                // Prüfen ob ein Ergebnis vorhanden ist
                if (resultSet.next()) {
                    int userID = resultSet.getInt("UserID");
                    String passwordHash = resultSet.getString("passwordHash");

                    // DB-String → Java-Enum
                    Role role = Role.valueOf(resultSet.getString("role"));

                    // Benutzer gefunden → in Optional verpacken
                    return Optional.of(new User(userID, username, passwordHash, role));
                }
            }
        }
        // Kein Benutzer gefunden → Optional.empty() statt null
        return Optional.empty();
    }

    /**
     * Fügt einen neuen Benutzer in die Datenbank ein
     * WICHTIG: Passwort wird vor dem Speichern gehasht (BCrypt)
     * Klartext-Passwörter werden NIEMALS gespeichert
     */
    public void insert(User user) throws SQLException {

        // SQL: neuen Datensatz einfügen
        String sql = "INSERT INTO User (username, passwordHash, role) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Benutzername setzen
            statement.setString(1, user.getUsername());

            // Klartext → BCrypt-Hash (sicheres Speichern!)
            statement.setString(2, PasswordUtil.hash(user.getPasswordHash()));

            // Enum → String (für DB-Spalte ENUM)
            statement.setString(3, user.getRole().name());

            // Einfügen ausführen
            statement.executeUpdate();
        }
    }

    /**
     * Löscht einen Benutzer anhand seiner ID
     * Hinweis: Durch ON DELETE CASCADE/SET NULL werden
     * verknüpfte Dolmetscher/Teilnehmer-Einträge angepasst
     */
    public void delete(int userID) throws SQLException {

        // SQL: Datensatz anhand der ID löschen
        String sql = "DELETE FROM User WHERE UserID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // ID als Parameter setzen
            statement.setInt(1, userID);

            // Löschung ausführen
            statement.executeUpdate();
        }
    }
}