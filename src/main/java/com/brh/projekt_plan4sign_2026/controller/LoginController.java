package com.brh.projekt_plan4sign_2026.controller;

import com.brh.projekt_plan4sign_2026.dao.TeilnehmerDAO;
import com.brh.projekt_plan4sign_2026.dao.UserDAO;
import com.brh.projekt_plan4sign_2026.model.Teilnehmer;
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


// LoginController.java – Controller für LoginView.fxml
// Verwaltet die Anmeldemaske und prüft die Zugangsdaten
//
// Ablauf:
// 1. initialize()  → Fokus entfernen damit promptText sichtbar ist
// 2. handleLogin() → Eingaben prüfen, BCrypt-Vergleich, Rolle bestimmen
// 3. navigateTo()  → Rollenbasierte Weiterleitung + Dependency Injection

public class LoginController implements Initializable {

    // ===== FXML-Verbindungen (verknüpft mit LoginView.fxml) =====
    @FXML private TextField usernameField;    // Eingabefeld: Benutzername
    @FXML private PasswordField passwordField; // Eingabefeld: Passwort (verdeckt)
    @FXML private Label errorLabel;            // Fehlermeldung bei falschem Login

    // ===== DAO für Datenbankzugriff =====
    // UserDAO: holt Benutzer aus der DB für den Login-Vergleich
    private final UserDAO userDAO = new UserDAO();


//     Wird automatisch von JavaFX beim Laden der View aufgerufen
//     Entfernt den Fokus vom usernameField damit promptText "Benutzername" sichtbar ist
//     Platform.runLater() → wird nach dem vollständigen Laden der UI ausgeführt

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Fokus auf Parent-Container setzen → kein Feld ist vorausgewählt
        Platform.runLater(() -> usernameField.getParent().requestFocus());
    }

    /**
     * Login-Logik – wird durch Button "Anmelden" ausgelöst
     *
     * Schritte:
     * 1. Eingaben lesen und validieren
     * 2. User aus DB holen (Optional → kein null)
     * 3. BCrypt-Passwortvergleich
     * 4. Bei Erfolg: Weiterleitung zur rollenspezifischen View
     */
    @FXML
    private void handleLogin() {

        // Eingaben lesen (trim() entfernt führende/nachfolgende Leerzeichen)
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Validierung: leere Felder verhindern
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Bitte alle Felder ausfüllen.");
            return;
        }

        try {
            // User aus DB holen → Optional verhindert NullPointerException
            Optional<User> result = userDAO.getByUsername(username);

            // Prüfung 1: Existiert der User?
            // Prüfung 2: Stimmt das Passwort? (BCrypt-Vergleich)
            if (result.isEmpty() ||
                    !PasswordUtil.verify(password, result.get().getPasswordHash())) {

                // Sicherheit: keine genaue Fehlermeldung (kein Hinweis ob User oder Passwort falsch)
                errorLabel.setText("Ungültiger Benutzername oder Passwort.");
                return;
            }

            // Login erfolgreich → User-Objekt holen
            User user = result.get();

            // Weiterleitung zur rollenspezifischen View
            navigateTo(user);

        } catch (Exception e) {
            // Fehlerdetails in der Konsole ausgeben (nur für Entwickler)
            e.printStackTrace();

            // Benutzer sieht nur allgemeine Fehlermeldung (Security Best Practice)
            errorLabel.setText("Fehler bei der Anmeldung.");
        }
    }

    /**
     * Rollenbasierte Navigation nach erfolgreichem Login
     * Lädt die passende FXML-View je nach Rolle des Benutzers
     * Dependency Injection für DOLMETSCHER und TEILNEHMER:
     * → Controller-Objekt wird geholt und eingeloggter User übergeben
     *
     * @param user der erfolgreich eingeloggte Benutzer
     */
    private void navigateTo(User user) {

        // FXML-Pfad basierend auf Rolle bestimmen (Switch Expression – Java 21)
        String fxml = switch (user.getRole()) {
            case ADMIN       -> "/com/brh/projekt_plan4sign_2026/view/AdminView.fxml";
            case TEILNEHMER  -> "/com/brh/projekt_plan4sign_2026/view/TeilnehmerView.fxml";
            case DOLMETSCHER -> "/com/brh/projekt_plan4sign_2026/view/DolmetscherView.fxml";
        };

        try {
            // FXML-Datei laden → erstellt View und Controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

            // View als Parent-Node laden (enthält alle UI-Elemente)
            Parent root = loader.load();

            // ===== Dependency Injection für DOLMETSCHER =====
            // Controller holen und Dolmetscher-Objekt übergeben
            // loadData() wird erst nach setDolmetscher() aufgerufen
            if (user.getRole() == com.brh.projekt_plan4sign_2026.model.Role.DOLMETSCHER) {
                DolmetscherDAO dolDAO = new DolmetscherDAO();
                Dolmetscher dolmetscher = dolDAO.getByUserID(user.getUserID());

                DolmetscherController controller = loader.getController();
                controller.setDolmetscher(dolmetscher);
            }

            // ===== Dependency Injection für TEILNEHMER =====
            // Controller holen und Teilnehmer-Objekt übergeben
            // loadData() wird erst nach setTeilnehmer() aufgerufen
            if (user.getRole() == com.brh.projekt_plan4sign_2026.model.Role.TEILNEHMER) {
                TeilnehmerDAO teilDAO = new TeilnehmerDAO();
                Teilnehmer teilnehmer = teilDAO.getByUserID(user.getUserID());

                TeilnehmerController controller = loader.getController();
                controller.setTeilnehmer(teilnehmer);
            }

            // Aktuelles Fenster holen und Scene wechseln
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            // Fehlerdetails in der Konsole ausgeben (nur für Entwickler)
            e.printStackTrace();

            // Benutzer sieht allgemeine Fehlermeldung
            errorLabel.setText("Fehler beim Laden der Ansicht.");
        }
    }
}