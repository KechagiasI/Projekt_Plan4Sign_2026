package com.brh.projekt_plan4sign_2026;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * App.java – Einstiegspunkt der JavaFX-Anwendung
 * Lädt die LoginView und öffnet das Hauptfenster
 */
public class App extends Application {

    /**
     * Wird automatisch von JavaFX beim Start aufgerufen
     * Initialisiert das Hauptfenster mit der Login-Ansicht
     */
    @Override
    public void start(Stage stage) throws IOException {

        // LoginView.fxml laden – Startansicht der Anwendung
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/com/brh/projekt_plan4sign_2026/view/LoginView.fxml")
        );

        // Scene erstellen aus dem geladenen FXML
        Scene scene = new Scene(loader.load());

        // Fenstertitel setzen
        stage.setTitle("Plan4Sign 2026");

        // Fenstergröße festlegen
        stage.setWidth(500);
        stage.setHeight(500);

        // Scene dem Fenster zuweisen
        stage.setScene(scene);

        // Fenster anzeigen
        stage.show();
    }
}