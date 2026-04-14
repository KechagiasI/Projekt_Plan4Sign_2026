package com.brh.projekt_plan4sign_2026.controller;

import com.brh.projekt_plan4sign_2026.dao.UnterrichtDAO;
import com.brh.projekt_plan4sign_2026.model.Dolmetscher;
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
 * DolmetscherController.java – Controller für DolmetscherView.fxml
 * Zeigt den persönlichen Einsatzplan des eingeloggten Dolmetschers
 *
 * Ablauf:
 * 1. initialize()      → Spalten verknüpfen, ResizePolicy setzen
 * 2. setDolmetscher()  → wird vom LoginController aufgerufen (Dependency Injection)
 * 3. loadData()        → lädt nur die eigenen Unterrichtseinheiten aus der DB
 */
public class DolmetscherController {

    // ===== FXML-Verbindungen (verknüpft mit DolmetscherView.fxml) =====
    @FXML
    private Label labelWillkommen;                 // Begrüßungstext

    @FXML
    private TableView<Unterricht> tableUnterricht; // Haupttabelle

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
    private TableColumn<Unterricht, String> colTeilnehmer;  // Spalte: Teilnehmername

    // ===== Eingeloggter Dolmetscher (wird per Dependency Injection übergeben) =====
    private Dolmetscher dolmetscher;

    /**
     * Wird automatisch von JavaFX beim Laden der View aufgerufen
     * Verknüpft die Tabellenspalten mit den Getter-Methoden des Unterricht-Modells
     * WICHTIG: loadData() wird hier NICHT aufgerufen,
     * weil dolmetscher noch nicht gesetzt ist!
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

        // Klassenname aus Anzeigefeld (per JOIN befüllt)
        colKlasse.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getKlassename()));

        // Fachname aus Anzeigefeld (per JOIN befüllt)
        colFach.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getFachname()));

        // Teilnehmername: "–" wenn kein Teilnehmer in der Klasse
        colTeilnehmer.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getTeilnehmername() != null
                                ? data.getValue().getTeilnehmername()
                                : "–"
                ));

        // Spaltenbreiten automatisch auf Fensterbreite anpassen
        // Funktioniert in JavaFX 21 nur im Controller, nicht in FXML
        tableUnterricht.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    /**
     * Dependency Injection – wird vom LoginController aufgerufen
     * Übergibt den eingeloggten Dolmetscher an diesen Controller
     * Erst nach dieser Übergabe kann loadData() aufgerufen werden
     * weil DolmetscherID benötigt wird
     */
    public void setDolmetscher(Dolmetscher dolmetscher) {
        this.dolmetscher = dolmetscher;

        // Begrüßungstext mit vollem Namen befüllen
        labelWillkommen.setText(
                "Willkommen, " + dolmetscher.getFirstname() + " " + dolmetscher.getLastname()
        );

        // Einsatzplan laden – erst jetzt möglich weil DolmetscherID bekannt ist
        loadData();
    }

    /**
     * Lädt die eigenen Unterrichtseinheiten aus der Datenbank
     * Verwendet getWithDetailsByDolmetscher() → holt Daten mit JOIN
     * Filtert nach DolmetscherID des eingeloggten Dolmetschers
     */
    private void loadData() {
        UnterrichtDAO dao = new UnterrichtDAO();
        try {
            // Nur Unterrichtseinheiten dieses Dolmetschers laden
            List<Unterricht> list = dao.getWithDetailsByDolmetscher(
                    dolmetscher.getDolmetscherID());

            // Liste → JavaFX ObservableList → Tabelle befüllen
            tableUnterricht.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Abmelden: lädt LoginView und schließt die aktuelle Ansicht
     * Wird durch Button "Abmelden" in DolmetscherView.fxml ausgelöst
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