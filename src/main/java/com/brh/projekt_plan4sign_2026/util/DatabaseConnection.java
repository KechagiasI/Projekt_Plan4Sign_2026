package com.brh.projekt_plan4sign_2026.util;

import java.sql.Connection;     // Repräsentiert eine Verbindung zur Datenbank
import java.sql.DriverManager;  // Erstellt/vermittelt die Verbindung zur Datenbank
import java.sql.SQLException;   // Ausnahme bei Datenbankfehlern

public class DatabaseConnection {

    // JDBC-URL: mysql = Treiber, localhost = Server, 3324 = Port, projekt_doit = Datenbankname
    // (Standard-Port ist 3306 -> prüfen, ob 3324 korrekt ist)
    private static final String URL = "jdbc:mysql://localhost:3324/projekt_doit";
    // Datenbank-Benutzername
    private static final String USER = "root";
    // Datenbank-Passwort (Hinweis: in echten Projekten nicht im Code speichern!)
    private static final String PASSWORD = "1234";
    // Singleton-Verbindung: Es existiert nur eine Connection im gesamten Programm
    private static Connection connection = null;
    // Privater Konstruktor verhindert Instanziierung (Utility-/Singleton-Klasse)
    private DatabaseConnection() {}
    // Liefert eine gültige Connection zurück (erstellt sie nur, wenn nötig)
    public static Connection getConnection() throws SQLException {
        // Falls noch keine Verbindung existiert oder sie geschlossen wurde → neue erstellen
        if(connection == null || connection.isClosed()) {
            // Aufbau der Verbindung zur MySQL-Datenbank
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        // Rückgabe der bestehenden oder neu erstellten Verbindung
        return connection;
    }
    // Schließt die bestehende Datenbankverbindung sauber
    public static void closeConnection() {
        try {
            // Nur schließen, wenn Verbindung existiert und noch offen ist
            if(connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            // Fehlerausgabe (für Debugging; in echten Projekten Logging verwenden)
            e.printStackTrace();
        }
    }
}
