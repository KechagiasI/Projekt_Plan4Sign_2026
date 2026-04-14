package com.brh.projekt_plan4sign_2026.controller;

import com.brh.projekt_plan4sign_2026.dao.UnterrichtDAO;
import com.brh.projekt_plan4sign_2026.model.Teilnehmer;
import com.brh.projekt_plan4sign_2026.model.Unterricht;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.List;

/**
 * TeilnehmerController.java – Controller für TeilnehmerView.fxml
 * Zeigt den Stundenplan der eigenen Klasse nach dem Login
 *
 * Ablauf:
 * 1. initialize()    → Spalten verknüpfen, ResizePolicy setzen
 * 2. setTeilnehmer() → wird vom LoginController aufgerufen (Dependency Injection)
 * 3. loadData()      → lädt Stundenplan der eigenen Klasse aus der DB
 */
public class TeilnehmerController {

    // ===== FXML-Verbindungen (verknüpft mit TeilnehmerView.fxml) =====
    @FXML
    private Label labelWillkommen;              // Begrüßungstext

    @FXML
    private TableView<Unterricht> tableUnterricht; // Haupttabelle

    @FXML
    private TableColumn<Unterricht, String> colDate;         // Spalte: Datum
    @FXML
    private TableColumn<Unterricht, String> colStart;        // Spalte: Startzeit
    @FXML
    private TableColumn<Unterricht, String> colEnd;          // Spalte: Endzeit
    @FXML
    private TableColumn<Unterricht, String> colFach;         // Spalte: Fachname
    @FXML
    private TableColumn<Unterricht, String> colDolmetscher;  // Spalte: Dolmetscher

    // ===== Eingeloggter Teilnehmer (wird per Dependency Injection übergeben) =====
    private Teilnehmer teilnehmer;

    /**
     * Wird automatisch von JavaFX beim Laden der View aufgerufen
     * Verknüpft die Tabellenspalten mit den Getter-Methoden des Unterricht-Modells
     * WICHTIG: loadData() wird hier NICHT aufgerufen,
     * weil teilnehmer noch nicht gesetzt ist!
     */
    @FXML
    private void initialize() {

        // Spalten mit Daten verknüpfen (Model → Tabelle)
        // Datum als String formatieren
        colDate.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getDate().toString()));

        // Startzeit als String formatieren
        colStart.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStartTime().toString()));

        // Endzeit als String formatieren
        colEnd.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEndTime().toString()));

        // Fachname aus Anzeigefeld (per JOIN befüllt)
        colFach.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getFachname()));

        // Dolmetschername: "–" wenn kein Dolmetscher zugewiesen
        colDolmetscher.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getDolmetschername() != null
                                ? data.getValue().getDolmetschername()
                                : "–"
                ));

        // Spaltenbreiten automatisch auf Fensterbreite anpassen
        // Funktioniert in JavaFX 21 nur im Controller, nicht in FXML
        tableUnterricht.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    /**
     * Dependency Injection – wird vom LoginController aufgerufen
     * Übergibt den eingeloggten Teilnehmer an diesen Controller
     * Erst nach dieser Übergabe kann loadData() aufgerufen werden
     * weil KlasseID des Teilnehmers benötigt wird
     */
    public void setTeilnehmer(Teilnehmer teilnehmer) {
        this.teilnehmer = teilnehmer;

        // Begrüßungstext mit vollem Namen befüllen
        labelWillkommen.setText(
                "Willkommen, " + teilnehmer.getFirstname() + " " + teilnehmer.getLastname()
        );

        // Stundenplan laden – erst jetzt möglich weil KlasseID bekannt ist
        loadData();
    }

    /**
     * Lädt den Stundenplan der eigenen Klasse aus der Datenbank
     * Verwendet getWithDetailsByKlasse() → holt Daten mit JOIN
     * Filtert nach KlasseID des eingeloggten Teilnehmers
     */
    private void loadData() {
        UnterrichtDAO dao = new UnterrichtDAO();
        try {
            // Nur Unterrichtseinheiten der eigenen Klasse laden
            List<Unterricht> list = dao.getWithDetailsByKlasse(teilnehmer.getKlasseID());

            // Liste → JavaFX ObservableList → Tabelle befüllen
            tableUnterricht.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Abmelden: lädt LoginView und schließt die aktuelle Ansicht
     * Wird durch Button "Abmelden" in TeilnehmerView.fxml ausgelöst
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