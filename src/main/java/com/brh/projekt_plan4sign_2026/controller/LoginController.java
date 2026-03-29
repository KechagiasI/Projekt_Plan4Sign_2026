package com.brh.projekt_plan4sign_2026.controller;

import com.brh.projekt_plan4sign_2026.dao.UserDAO;
import com.brh.projekt_plan4sign_2026.model.User;
import com.brh.projekt_plan4sign_2026.util.PasswordUtil;
import com.brh.projekt_plan4sign_2026.dao.DolmetscherDAO;
import com.brh.projekt_plan4sign_2026.model.Dolmetscher;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

// Verwaltet die Login-Oberfläche und prüft die Anmeldedaten
public class LoginController implements Initializable {

    // FXML Injection → verbindet UI (FXML) mit Java-Code
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    // DAO für DB-Zugriff
    private final UserDAO userDAO= new UserDAO();

    // Wird beim Laden der View automatisch aufgerufen
    // Verhindert, dass das erste Feld sofort den Fokus bekommt → promptText wird sichtbar
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Fokus vom usernameField entfernen → promptText "Benutzername" wird sichtbar
        Platform.runLater(() -> usernameField.getParent().requestFocus());
    }

    @FXML
    private void handleLogin() {

        // Eingaben holen
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Validierung → leere Felder verhindern
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Bitte alle Felder ausfüllen.");
            return;
        }
        try {
            // User aus DB holen (Optional → kein null)
            Optional<User> result = userDAO.getByUsername(username);

            // Login prüfen:
            // 1. User existiert?
            // 2. Passwort korrekt? (Hash-Vergleich)
            if (result.isEmpty() || !PasswordUtil.verify(password, result.get().getPasswordHash())) {

                // Fehlermeldung bei falschen Daten
                errorLabel.setText("Ungültiger Benutzername oder Passwort.");
                return;
            }

            // Erfolgreicher Login → User holen
            User user = result.get();
            // Weiterleitung je nach Rolle (ADMIN / TEILNEHMER / DOLMETSCHER)
            navigateTo(user);

        } catch (Exception e) {
            // Debug → Fehler in Konsole anzeigen (für Entwickler)
            e.printStackTrace();

            // User sieht nur allgemeine Fehlermeldung (Security Best Practice)
            errorLabel.setText("Fehler bei der Anmeldung.");
        }
    }

    private void navigateTo(User user) {

        // View-Auswahl basierend auf Rolle (Switch Expression)
        String fxml = switch (user.getRole()) {
            case ADMIN -> "/com/brh/projekt_plan4sign_2026/view/AdminView.fxml";
            case TEILNEHMER  -> "/com/brh/projekt_plan4sign_2026/view/TeilnehmerView.fxml";
            case DOLMETSCHER -> "/com/brh/projekt_plan4sign_2026/view/DolmetscherView.fxml";
        };
        try {
            // FXML laden (View)
            // Aktuelles Fenster holen
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

            // View wird als Parent-Node geladen
            Parent root = loader.load();

            // Nur für Dolmetscher: Controller holen und Objekt übergeben
            if (user.getRole() == com.brh.projekt_plan4sign_2026.model.Role.DOLMETSCHER) {
                DolmetscherDAO dolDAO = new DolmetscherDAO();
                Dolmetscher dolmetscher = dolDAO.getByUserID(user.getUserID());

                DolmetscherController controller = loader.getController();
                controller.setDolmetscher(dolmetscher);
            }

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Fehler beim Laden der Ansicht.");
        }
    }
}