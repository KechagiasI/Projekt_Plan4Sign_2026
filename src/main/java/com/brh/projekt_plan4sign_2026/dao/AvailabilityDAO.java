package com.brh.projekt_plan4sign_2026.dao;

import com.brh.projekt_plan4sign_2026.model.Availability;
import com.brh.projekt_plan4sign_2026.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AvailabilityDAO {

    // Holt alle Availability-Einträge eines bestimmten Dolmetschers
    public List<Availability> getByDolmetscher(int dolmetscherID) throws SQLException {

        // Liste für die Ergebnisse
        List<Availability> list = new ArrayList<>();

        // SQL-Abfrage mit Filter nach DolmetscherID
        String sql = "SELECT * FROM Availability WHERE DolmetscherID = ?";

        // Verbindung holen
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement verwenden (sicher + Parameter)
        PreparedStatement statement = connection.prepareStatement(sql);

        // Parameter setzen
        statement.setInt(1, dolmetscherID);

        // Abfrage ausführen
        ResultSet resultSet = statement.executeQuery();

        // Iteration über alle Ergebnisse
        while (resultSet.next()) {

            int availabilityID = resultSet.getInt("AvailabilityID");

            // Typ der Verfügbarkeit (z.B. verfügbar, krank, Urlaub)
            String availabilityType = resultSet.getString("availabilitytype");

            // Alle Datums- und Zeitfelder können NULL sein → daher vorher prüfen
            LocalDate date = resultSet.getObject("date") != null
                    ? resultSet.getDate("date").toLocalDate()
                    : null;

            LocalTime startTime = resultSet.getObject("starttime") != null
                    ? resultSet.getTime("starttime").toLocalTime()
                    : null;

            LocalTime endTime = resultSet.getObject("endtime") != null
                    ? resultSet.getTime("endtime").toLocalTime()
                    : null;

            // Zeitraum (von-bis)
            LocalDate dateFrom = resultSet.getObject("datefrom") != null
                    ? resultSet.getDate("datefrom").toLocalDate()
                    : null;

            LocalDate dateTo = resultSet.getObject("dateto") != null
                    ? resultSet.getDate("dateto").toLocalDate()
                    : null;

            // Optionaler Kommentar
            String comment = resultSet.getString("comment");

            // Mapping DB → Java-Objekt
            list.add(new Availability(
                    availabilityID,
                    availabilityType,
                    date,
                    startTime,
                    endTime,
                    dateFrom,
                    dateTo,
                    comment,
                    dolmetscherID
            ));
        }

        return list;
    }

    // Fügt einen neuen Availability-Eintrag in die Datenbank ein
    public void insert(Availability availability) throws SQLException {

        // SQL-Insert mit mehreren (optionalen) Parametern
        String sql = "INSERT INTO Availability (availabilitytype, date, starttime, endtime, " +
                "datefrom, dateto, comment, DolmetscherID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Verbindung holen
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement erstellen
        PreparedStatement statement = connection.prepareStatement(sql);

        // Pflichtfeld: Typ der Verfügbarkeit
        statement.setString(1, availability.getAvailabilityType());

        // NULL-Werte korrekt behandeln (Date)
        if (availability.getDate() != null) {
            statement.setDate(2, Date.valueOf(availability.getDate()));
        } else {
            statement.setNull(2, Types.DATE);
        }

        // NULL-Werte korrekt behandeln (Startzeit)
        if (availability.getStartTime() != null) {
            statement.setTime(3, Time.valueOf(availability.getStartTime()));
        } else {
            statement.setNull(3, Types.TIME);
        }

        // NULL-Werte korrekt behandeln (Endzeit)
        if (availability.getEndTime() != null) {
            statement.setTime(4, Time.valueOf(availability.getEndTime()));
        } else {
            statement.setNull(4, Types.TIME);
        }

        // Zeitraum von
        if (availability.getDateFrom() != null) {
            statement.setDate(5, Date.valueOf(availability.getDateFrom()));
        } else {
            statement.setNull(5, Types.DATE);
        }

        // Zeitraum bis
        if (availability.getDateTo() != null) {
            statement.setDate(6, Date.valueOf(availability.getDateTo()));
        } else {
            statement.setNull(6, Types.DATE);
        }

        // Optionaler Kommentar
        statement.setString(7, availability.getComment());

        // Fremdschlüssel: Dolmetscher
        statement.setInt(8, availability.getDolmetscherID());

        // Datenbankänderung ausführen
        statement.executeUpdate();
    }

    // Löscht einen Availability-Eintrag anhand seiner ID
    public void delete(int availabilityID) throws SQLException {

        // SQL-Delete mit Bedingung
        String sql = "DELETE FROM Availability WHERE AvailabilityID = ?";

        Connection connection = DatabaseConnection.getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);

        // ID setzen
        statement.setInt(1, availabilityID);

        // Löschung ausführen
        statement.executeUpdate();
    }
}