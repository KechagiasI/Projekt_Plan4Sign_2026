package com.brh.projekt_plan4sign_2026;

import javafx.application.Application;

/**
 * Launcher.java – Startklasse der Anwendung
 *
 * Notwendig weil JavaFX-Anwendungen nicht direkt über
 * die main()-Methode in App.java gestartet werden können,
 * wenn die Klasse Application erweitert wird.
 * Der Launcher umgeht dieses Problem durch den Aufruf
 * von Application.launch().
 */
public class Launcher {

    public static void main(String[] args) {

        // Startet die JavaFX-Anwendung über App.java
        Application.launch(App.class, args);
    }
}