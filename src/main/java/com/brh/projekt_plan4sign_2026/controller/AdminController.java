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

public class AdminController {

    // Verbindung zum TableView aus dem FXML
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
    private TableColumn<Unterricht, String> colDolmetscher;
    @FXML
    private TableColumn<Unterricht, String> colTeilnehmer;
    @FXML
    private ComboBox<String> comboDolmetscher;


    // Wird automatisch aufgerufen wenn View geladen wird
    @FXML
    private void initialize() {

        // Columns verbinden mit Daten (Model -> Getter)
        colDate.setCellValueFactory(data-> new javafx.beans.property.SimpleStringProperty(data.getValue().getDate().toString()));
        colStart.setCellValueFactory(data-> new javafx.beans.property.SimpleStringProperty(data.getValue().getStartTime().toString()));
        colEnd.setCellValueFactory(data-> new javafx.beans.property.SimpleStringProperty(data.getValue().getEndTime().toString()));
        colKlasse.setCellValueFactory(data-> new javafx.beans.property.SimpleStringProperty(data.getValue().getKlassename().toString()));
        colFach.setCellValueFactory(data-> new javafx.beans.property.SimpleStringProperty(data.getValue().getFachname().toString()));
        colDolmetscher.setCellValueFactory(data-> new javafx.beans.property.SimpleStringProperty(data.getValue().getDolmetschername() != null ? data.getValue().getDolmetschername() : " kein "));
        colTeilnehmer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTeilnehmername() != null ? data.getValue().getTeilnehmername() : "kein"));

        tableUnterricht.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        // Daten Load
        loadData();
        loadDolmetscher();
    }
    // Lädt Daten aus der DB und zeigt sie im TableView
    private void loadData() {
        UnterrichtDAO dao = new UnterrichtDAO();
        try {
            List<Unterricht> list = dao.getAllWithDetails();

            // Liste → JavaFX TableView
            tableUnterricht.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDolmetscher() {
        DolmetscherDAO dao = new DolmetscherDAO();

        try {
            comboDolmetscher.getItems().clear();

            List<Dolmetscher> list = dao.getAll();

            for (Dolmetscher d : list) {
                String name = d.getFirstname() + " " + d.getLastname();
                comboDolmetscher.getItems().add(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAssign() {

        Unterricht selected = tableUnterricht.getSelectionModel().getSelectedItem();
        String name = comboDolmetscher.getValue();

        if (selected == null || name == null) {
            System.out.println("Bitte Auswahl treffen!");
            return;
        }

        try {
            DolmetscherDAO dolDAO = new DolmetscherDAO();

            for (Dolmetscher d : dolDAO.getAll()) {
                String fullname = d.getFirstname() + " " + d.getLastname();

                if (fullname.equals(name)) {

                    // 1. Availability prüfen
                    AvailabilityDAO availabilityDAO = new AvailabilityDAO();
                    boolean frei = availabilityDAO.isAvailable(
                            d.getDolmetscherID(),
                            selected.getDate(),
                            selected.getStartTime(),
                            selected.getEndTime()
                    );

                    if (!frei) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warnung");
                        alert.setHeaderText("Dolmetscher nicht verfügbar");
                        alert.setContentText("Der Dolmetscher ist zu diesem Zeitpunkt nicht verfügbar.");
                        alert.showAndWait();
                        return;
                    }

                    // 2. Zeitkonflikt mit anderem Unterricht prüfen
                    UnterrichtDAO uDAO = new UnterrichtDAO();
                    boolean konflikt = uDAO.hasConflict(
                            d.getDolmetscherID(),
                            selected.getUnterrichtID(),
                            selected.getDate(),
                            selected.getStartTime(),
                            selected.getEndTime()
                    );

                    if (konflikt) {
                        // Bestätigungsdialog anzeigen
                        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                        confirm.setTitle("Konflikt erkannt");
                        confirm.setHeaderText("Dolmetscher bereits eingeplant");
                        confirm.setContentText(
                                "Der Dolmetscher " + fullname + " hat zu diesem Zeitpunkt " +
                                        "bereits eine andere Unterrichtseinheit.\n\n" +
                                        "Trotzdem zuweisen?"
                        );

                        Optional<ButtonType> result = confirm.showAndWait();

                        // Wenn der Admin NICHT bestätigt → abbrechen
                        if (result.isEmpty() || result.get() != ButtonType.OK) {
                            return;
                        }
                    }

                    // 3. Zuweisung durchführen
                    uDAO.assignDolmetscher(selected.getUnterrichtID(), d.getDolmetscherID());
                    break;
                }
            }
            loadData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRemove() {

        Unterricht selected = tableUnterricht.getSelectionModel().getSelectedItem();

        // Kein Unterricht ausgewählt
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
            alert.setContentText("Dieser Unterrichtseinheit ist kein Dolmetscher zugewiesen.");
            alert.showAndWait();
            return;
        }

        // Bestätigung einholen
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Dolmetscher entfernen");
        confirm.setHeaderText("Zuweisung aufheben");
        confirm.setContentText("Möchten Sie den Dolmetscher von dieser Unterrichtseinheit entfernen?");

        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }

        try {
            UnterrichtDAO uDAO = new UnterrichtDAO();
            uDAO.removeDolmetscher(selected.getUnterrichtID());
            loadData();
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
