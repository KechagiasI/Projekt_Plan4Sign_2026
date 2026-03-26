package com.brh.projekt_plan4sign_2026.controller;

import com.brh.projekt_plan4sign_2026.dao.AvailabilityDAO;
import com.brh.projekt_plan4sign_2026.dao.DolmetscherDAO;
import com.brh.projekt_plan4sign_2026.dao.UnterrichtDAO;
import com.brh.projekt_plan4sign_2026.model.Dolmetscher;
import com.brh.projekt_plan4sign_2026.model.Unterricht;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

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

                    UnterrichtDAO uDAO = new UnterrichtDAO();

                    uDAO.assignDolmetscher( selected.getUnterrichtID(), d.getDolmetscherID());

                    break;
                }
            }
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
