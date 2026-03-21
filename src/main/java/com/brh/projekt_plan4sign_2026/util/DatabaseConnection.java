package com.brh.projekt_plan4sign_2026.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Adresse der Datenbank(localhost = lokaler Rechner, 3306 = Standard-MySQL-Port)
    private static final String URL = "jdbc:mysql://localhost:3306/projekt_doit";
    // Benutzername für die MySQL-Datenbank
    private static final String USER = "root";
    // Passwort für die MySQL-Datenbank
    private static final String PASSWORD = "1234";
    // Einzelne Verbindungsinstanz (Singleton-Prinzip - existiert nur eine Verbindung)
    private static Connection connection = null;
    // Privater Konstruktor - verhindert, dass die Klasse von außen instanziiert werden kann
    private DatabaseConnection() {}
    // Gibt die aktuelle Verbindung zurück - erstellt sie, falls sie noch nicht existiert
    public static Connection getConnection() {
        if(connection == null || connection.isClosed()) {
            // Verbindung zur Datenbank wird hergestellt
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
    // Schließst die Verbindung zur Datenbank
    public static void closeConnection() {
        try {
            if(connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
