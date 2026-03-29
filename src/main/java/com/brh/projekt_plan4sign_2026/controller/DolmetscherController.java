package com.brh.projekt_plan4sign_2026.controller;

import com.brh.projekt_plan4sign_2026.dao.UnterrichtDAO;
import com.brh.projekt_plan4sign_2026.model.Dolmetscher;
import com.brh.projekt_plan4sign_2026.model.Unterricht;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class DolmetscherController {

    // Verbindung zu den UI-Elementen aus dem FXML
    @FXML
    private Label labelWillkommen;

    @FXML
    private TableView<Unterricht> tableUnterricht;

    @FXML
    private TableColumn<Unterricht, String> colDate;

    @FXML
    private TableColumn<Unterricht, String> colStart;

    @FXML
    private TableColumn<Unterricht, String> colEnd;

    @FXML
    private TableColumn<Unterricht, String> colKlasse;

    @FXML
    private TableColumn<Unterricht, String> colFach;

    @FXML
    private TableColumn<Unterricht, String> colTeilnehmer;

    // Der eingeloggte Dolmetscher – wird nach dem Laden der View gesetzt
    private Dolmetscher dolmetscher;

    // Wird automatisch aufgerufen wenn die View geladen wird
    @FXML
    private void initialize() {

        // Spalten mit Daten aus dem Unterricht-Objekt verbinden
        colDate.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getDate().toString()));

        colStart.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStartTime().toString()));

        colEnd.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEndTime().toString()));

        colKlasse.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getKlassename()));

        colFach.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getFachname()));

        colTeilnehmer.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getTeilnehmername() != null
                                ? data.getValue().getTeilnehmername()
                                : "–"
                ));
    }

    // Wird vom LoginController aufgerufen – übergibt den eingeloggten Dolmetscher
    public void setDolmetscher(Dolmetscher dolmetscher) {
        this.dolmetscher = dolmetscher;

        // Begrüßungstext mit Name des Dolmetschers
        labelWillkommen.setText(
                "Willkommen, " + dolmetscher.getFirstname() + " " + dolmetscher.getLastname()
        );

        // Daten laden – erst jetzt, weil wir die ID brauchen
        loadData();
    }

    // Holt die Unterrichtseinheiten dieses Dolmetschers aus der DB
    private void loadData() {
        UnterrichtDAO dao = new UnterrichtDAO();
        try {
            List<Unterricht> list = dao.getWithDetailsByDolmetscher(dolmetscher.getDolmetscherID());
            tableUnterricht.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}