package com.brh.projekt_plan4sign_2026.controller;

import com.brh.projekt_plan4sign_2026.dao.AvailabilityDAO;
import com.brh.projekt_plan4sign_2026.dao.DolmetscherDAO;
import com.brh.projekt_plan4sign_2026.dao.UnterrichtDAO;
import com.brh.projekt_plan4sign_2026.model.Dolmetscher;
import com.brh.projekt_plan4sign_2026.model.Unterricht;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

/**
 * AdminController.java – Controller für AdminView.fxml
 * Verwaltet die Administrator-Oberfläche
 *
 * Funktionen:
 * - Alle Unterrichtseinheiten anzeigen (TableView)
 * - Dolmetscher zuweisen (handleAssign)
 *   → Verfügbarkeitsprüfung (AvailabilityDAO)
 *   → Zeitkonfliktprüfung (UnterrichtDAO.hasConflict)
 * - Zuweisung aufheben (handleRemove)
 * - Abmelden (handleLogout)
 */
public class AdminController {

    // ===== FXML-Verbindungen (verknüpft mit AdminView.fxml) =====
    @FXML
    private TableView<Unterricht> tableUnterricht;          // Haupttabelle

    @FXML
    private TableColumn<Unterricht, String> colDate;        // Spalte: Datum
    @FXML
    private TableColumn<Unterricht, String> colStart;       // Spalte: Startzeit
    @FXML
    private TableColumn<Unterricht, String> colEnd;         // Spalte: Endzeit
    @FXML
    private TableColumn<Unterricht, String> colKlasse;      // Spalte: Klassenname
    @FXML
    private TableColumn<Unterricht, String> colFach;        // Spalte: Fachname
    @FXML
    private TableColumn<Unterricht, String> colDolmetscher; // Spalte: Dolmetschername
    @FXML
    private TableColumn<Unterricht, String> colTeilnehmer;  // Spalte: Teilnehmername

    @FXML
    private ComboBox<String> comboDolmetscher; // Auswahlliste für Dolmetscher-Zuweisung

    /**
     * Wird automatisch von JavaFX beim Laden der View aufgerufen
     * Verknüpft Spalten mit Daten, setzt ResizePolicy
     * und lädt alle Daten aus der Datenbank
     */
    @FXML
    private void initialize() {

        // ===== Spalten mit Daten verknüpfen (Model → Tabelle) =====

        // Datum als String formatieren
        colDate.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getDate().toString()));

        // Startzeit als String formatieren
        colStart.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStartTime().toString()));

        // Endzeit als String formatieren
        colEnd.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEndTime().toString()));

        // Klassenname aus Anzeigefeld (per JOIN befüllt)
        colKlasse.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getKlassename().toString()));

        // Fachname aus Anzeigefeld (per JOIN befüllt)
        colFach.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getFachname().toString()));

        // Dolmetschername: "kein" wenn kein Dolmetscher zugewiesen (NULL)
        colDolmetscher.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getDolmetschername() != null
                                ? data.getValue().getDolmetschername()
                                : " kein "));

        // Teilnehmername: "kein" wenn kein Teilnehmer in der Klasse
        colTeilnehmer.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getTeilnehmername() != null
                                ? data.getValue().getTeilnehmername()
                                : "kein"));

        // Spaltenbreiten automatisch auf Fensterbreite anpassen
        // Funktioniert in JavaFX 21 nur im Controller, nicht in FXML
        tableUnterricht.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        // Alle Unterrichtseinheiten aus der DB laden
        loadData();

        // ComboBox mit allen Dolmetschern befüllen
        loadDolmetscher();
    }

    /**
     * Lädt alle Unterrichtseinheiten mit JOIN-Daten aus der DB
     * Verwendet getAllWithDetails() → holt Klasse, Fach, Dolmetscher, Teilnehmer
     * Wird nach jeder Änderung (Zuweisung/Entfernen) erneut aufgerufen
     */
    private void loadData() {
        UnterrichtDAO dao = new UnterrichtDAO();
        try {
            // Alle Unterrichtseinheiten mit JOIN-Daten holen
            List<Unterricht> list = dao.getAllWithDetails();

            // Liste → JavaFX ObservableList → Tabelle befüllen
            tableUnterricht.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Befüllt die ComboBox mit allen verfügbaren Dolmetschern
     * Format: "Vorname Nachname"
     * Wird beim Laden der View aufgerufen
     */
    private void loadDolmetscher() {
        DolmetscherDAO dao = new DolmetscherDAO();
        try {
            // ComboBox leeren bevor neu befüllt wird
            comboDolmetscher.getItems().clear();

            // Alle Dolmetscher aus der DB holen
            List<Dolmetscher> list = dao.getAll();

            // Jeden Dolmetscher als "Vorname Nachname" zur ComboBox hinzufügen
            for (Dolmetscher d : list) {
                String name = d.getFirstname() + " " + d.getLastname();
                comboDolmetscher.getItems().add(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Dolmetscher zuweisen – wird durch Button "Zuweisen" ausgelöst
     *
     * Ablauf:
     * 1. Auswahl prüfen (Unterricht + Dolmetscher gewählt?)
     * 2. Availability prüfen → nicht verfügbar = Warnung + Abbruch
     * 3. Zeitkonflikt prüfen → Konflikt = Bestätigungsdialog
     * 4. Zuweisung speichern (UPDATE Unterricht SET DolmetscherID)
     * 5. Tabelle aktualisieren
     */
    @FXML
    private void handleAssign() {

        // Ausgewählte Zeile und gewählten Dolmetscher holen
        Unterricht selected = tableUnterricht.getSelectionModel().getSelectedItem();
        String name = comboDolmetscher.getValue();

        // Prüfen ob beides ausgewählt ist
        if (selected == null || name == null) {
            System.out.println("Bitte Auswahl treffen!");
            return;
        }

        try {
            DolmetscherDAO dolDAO = new DolmetscherDAO();

            // Alle Dolmetscher durchsuchen um das passende Objekt zu finden
            for (Dolmetscher d : dolDAO.getAll()) {
                String fullname = d.getFirstname() + " " + d.getLastname();

                // Gewählten Dolmetscher gefunden
                if (fullname.equals(name)) {

                    // ===== Prüfung 1: Availability =====
                    // Ist der Dolmetscher laut Availability-Tabelle verfügbar?
                    AvailabilityDAO availabilityDAO = new AvailabilityDAO();
                    boolean frei = availabilityDAO.isAvailable(
                            d.getDolmetscherID(),
                            selected.getDate(),
                            selected.getStartTime(),
                            selected.getEndTime()
                    );

                    // Nicht verfügbar → Warnung anzeigen und abbrechen
                    if (!frei) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warnung");
                        alert.setHeaderText("Dolmetscher nicht verfügbar");
                        alert.setContentText(
                                "Der Dolmetscher ist zu diesem Zeitpunkt nicht verfügbar.");
                        alert.showAndWait();
                        return;
                    }

                    // ===== Prüfung 2: Zeitkonflikt =====
                    // Hat der Dolmetscher zur gleichen Zeit bereits einen anderen Unterricht?
                    UnterrichtDAO uDAO = new UnterrichtDAO();
                    boolean konflikt = uDAO.hasConflict(
                            d.getDolmetscherID(),
                            selected.getUnterrichtID(),
                            selected.getDate(),
                            selected.getStartTime(),
                            selected.getEndTime()
                    );

                    // Konflikt → Bestätigungsdialog anzeigen
                    if (konflikt) {
                        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                        confirm.setTitle("Konflikt erkannt");
                        confirm.setHeaderText("Dolmetscher bereits eingeplant");
                        confirm.setContentText(
                                "Der Dolmetscher " + fullname + " hat zu diesem Zeitpunkt " +
                                        "bereits eine andere Unterrichtseinheit.\n\n" +
                                        "Trotzdem zuweisen?"
                        );

                        Optional<ButtonType> result = confirm.showAndWait();

                        // Admin hat NICHT bestätigt → abbrechen
                        if (result.isEmpty() || result.get() != ButtonType.OK) {
                            return;
                        }
                    }

                    // ===== Zuweisung durchführen =====
                    // UPDATE Unterricht SET DolmetscherID = ? WHERE UnterrichtID = ?
                    uDAO.assignDolmetscher(selected.getUnterrichtID(), d.getDolmetscherID());
                    break;
                }
            }

            // Tabelle nach Änderung aktualisieren
            loadData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Zuweisung aufheben – wird durch Button "Entfernen" ausgelöst
     * Setzt DolmetscherID auf NULL (ON DELETE SET NULL)
     *
     * Ablauf:
     * 1. Auswahl prüfen
     * 2. Prüfen ob Dolmetscher zugewiesen ist
     * 3. Bestätigungsdialog
     * 4. DolmetscherID auf NULL setzen
     * 5. Tabelle aktualisieren
     */
    @FXML
    private void handleRemove() {

        // Ausgewählte Zeile holen
        Unterricht selected = tableUnterricht.getSelectionModel().getSelectedItem();

        // Kein Unterricht ausgewählt → Warnung
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warnung");
            alert.setHeaderText("Keine Auswahl");
            alert.setContentText("Bitte eine Unterrichtseinheit auswählen.");
            alert.showAndWait();
            return;
        }

        // Kein Dolmetscher zugewiesen → nichts zu entfernen
        if (selected.getDolmetscherID() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hinweis");
            alert.setHeaderText("Kein Dolmetscher zugewiesen");
            alert.setContentText(
                    "Dieser Unterrichtseinheit ist kein Dolmetscher zugewiesen.");
            alert.showAndWait();
            return;
        }

        // Bestätigungsdialog vor dem Entfernen
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Dolmetscher entfernen");
        confirm.setHeaderText("Zuweisung aufheben");
        confirm.setContentText(
                "Möchten Sie den Dolmetscher von dieser Unterrichtseinheit entfernen?");

        Optional<ButtonType> result = confirm.showAndWait();

        // Admin hat NICHT bestätigt → abbrechen
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }

        try {
            UnterrichtDAO uDAO = new UnterrichtDAO();

            // DolmetscherID auf NULL setzen
            // UPDATE Unterricht SET DolmetscherID = NULL WHERE UnterrichtID = ?
            uDAO.removeDolmetscher(selected.getUnterrichtID());

            // Tabelle nach Änderung aktualisieren
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Abmelden: lädt LoginView und schließt die aktuelle Ansicht
     * Wird durch Button "Abmelden" in AdminView.fxml ausgelöst
     */
    @FXML
    private void handleLogout() {
        try {
            // LoginView.fxml laden
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/brh/projekt_plan4sign_2026/view/LoginView.fxml")
            );

            // Aktuelles Fenster holen und Scene wechseln
            Stage stage = (Stage) tableUnterricht.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}