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

public class TeilnehmerController {

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
    private TableColumn<Unterricht, String> colFach;

    @FXML
    private TableColumn<Unterricht, String> colDolmetscher;

    // Der eingeloggte Teilnehmer – wird nach dem Laden gesetzt
    private Teilnehmer teilnehmer;

    @FXML
    private void initialize() {

        // Spalten mit Daten verbinden
        colDate.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getDate().toString()));

        colStart.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStartTime().toString()));

        colEnd.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEndTime().toString()));

        colFach.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getFachname()));

        colDolmetscher.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getDolmetschername() != null
                                ? data.getValue().getDolmetschername()
                                : "–"
                ));

        tableUnterricht.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    // Wird vom LoginController aufgerufen
    public void setTeilnehmer(Teilnehmer teilnehmer) {
        this.teilnehmer = teilnehmer;

        labelWillkommen.setText(
                "Willkommen, " + teilnehmer.getFirstname() + " " + teilnehmer.getLastname()
        );

        // Daten laden – erst jetzt, weil wir die KlasseID brauchen
        loadData();
    }

    private void loadData() {
        UnterrichtDAO dao = new UnterrichtDAO();
        try {
            List<Unterricht> list = dao.getWithDetailsByKlasse(teilnehmer.getKlasseID());
            tableUnterricht.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/brh/projekt_plan4sign_2026/view/LoginView.fxml")
            );
            Stage stage = (Stage) tableUnterricht.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}