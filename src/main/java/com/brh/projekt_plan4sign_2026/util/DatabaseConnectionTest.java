package com.brh.projekt_plan4sign_2026.util;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionTest {

    public static void main(String[] args) {
        try {
            // Versuch eine Verbindung zur Datenbank herzustellen
            Connection connection = DatabaseConnection.getConnection();

            // Wenn kein fehler auftritt, war die Verbindung erfolgreich
            System.out.println("Verbindung erfolgreich!");

            // Verbindung wird nach dem Test wieder geschlossen
            DatabaseConnection.closeConnection();
        } catch (Exception e) {
            // Wenn ein Fehler auftritt, wird die Fehlermeldung ausgegeben
            System.out.println("Verbindung fehlgeschlagen: " + e.getMessage());
        }
    }
}
