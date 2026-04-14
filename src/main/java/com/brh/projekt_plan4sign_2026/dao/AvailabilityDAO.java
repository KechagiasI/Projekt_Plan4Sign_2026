package com.brh.projekt_plan4sign_2026.dao;

import com.brh.projekt_plan4sign_2026.model.Availability;
import com.brh.projekt_plan4sign_2026.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * AvailabilityDAO.java – Datenzugriffsklasse für die Tabelle "Availability"
 * Kapselt alle Datenbankoperationen für Dolmetscher-Verfügbarkeiten
 *
 * Methoden:
 * - getByDolmetscher() → Alle Einträge eines Dolmetschers
 * - insert()           → Neuen Eintrag hinzufügen
 * - delete()           → Eintrag löschen
 * - isAvailable()      → Verfügbarkeitsprüfung vor Zuweisung
 *
 * Zwei Arten von Blockierungen:
 * 1. Einzeltermin: date + starttime + endtime
 * 2. Zeitraum:     datefrom + dateto
 */
public class AvailabilityDAO {

    /**
     * Holt alle Availability-Einträge eines bestimmten Dolmetschers
     * WHERE DolmetscherID = ? → nur Einträge des gewählten Dolmetschers
     */
    public List<Availability> getByDolmetscher(int dolmetscherID) throws SQLException {

        // Ergebnisliste für Java-Objekte
        List<Availability> list = new ArrayList<>();

        // SQL: alle Blockierungen des Dolmetschers
        String sql = "SELECT * FROM Availability WHERE DolmetscherID = ?";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement → sicherer als Statement, verhindert SQL-Injection
        PreparedStatement statement = connection.prepareStatement(sql);

        // DolmetscherID als Parameter setzen
        statement.setInt(1, dolmetscherID);

        // Abfrage ausführen → gibt ResultSet (Tabelle von Ergebnissen) zurück
        ResultSet resultSet = statement.executeQuery();

        // Alle Zeilen des Ergebnisses durchlaufen
        while (resultSet.next()) {

            int availabilityID = resultSet.getInt("AvailabilityID");

            // Art der Blockierung (z.B. "Krank", "Urlaub", "Vorbildung")
            String availabilityType = resultSet.getString("availabilitytype");

            // NULL-Check für alle Datums- und Zeitfelder
            // Einzeltermin: date, starttime, endtime
            LocalDate date = resultSet.getObject("date") != null
                    ? resultSet.getDate("date").toLocalDate() : null;

            LocalTime startTime = resultSet.getObject("starttime") != null
                    ? resultSet.getTime("starttime").toLocalTime() : null;

            LocalTime endTime = resultSet.getObject("endtime") != null
                    ? resultSet.getTime("endtime").toLocalTime() : null;

            // Zeitraum: datefrom, dateto
            LocalDate dateFrom = resultSet.getObject("datefrom") != null
                    ? resultSet.getDate("datefrom").toLocalDate() : null;

            LocalDate dateTo = resultSet.getObject("dateto") != null
                    ? resultSet.getDate("dateto").toLocalDate() : null;

            // Optionaler Kommentar
            String comment = resultSet.getString("comment");

            // DB-Zeile → Java-Objekt (Mapping)
            list.add(new Availability(
                    availabilityID, availabilityType,
                    date, startTime, endTime,
                    dateFrom, dateTo,
                    comment, dolmetscherID));
        }
        return list;
    }

    /**
     * Fügt einen neuen Availability-Eintrag in die Datenbank ein
     * Alle Datums- und Zeitfelder sind optional → NULL-Behandlung erforderlich
     */
    public void insert(Availability availability) throws SQLException {

        // SQL: neuen Datensatz einfügen
        String sql = "INSERT INTO Availability " +
                "(availabilitytype, date, starttime, endtime, datefrom, dateto, comment, DolmetscherID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement → sicherer als Statement, verhindert SQL-Injection
        PreparedStatement statement = connection.prepareStatement(sql);

        // Pflichtfeld: Art der Blockierung
        statement.setString(1, availability.getAvailabilityType());

        // NULL-Behandlung: Einzeltermin (date)
        if (availability.getDate() != null) {
            statement.setDate(2, Date.valueOf(availability.getDate()));
        } else {
            statement.setNull(2, Types.DATE);
        }

        // NULL-Behandlung: Startzeit
        if (availability.getStartTime() != null) {
            statement.setTime(3, Time.valueOf(availability.getStartTime()));
        } else {
            statement.setNull(3, Types.TIME);
        }

        // NULL-Behandlung: Endzeit
        if (availability.getEndTime() != null) {
            statement.setTime(4, Time.valueOf(availability.getEndTime()));
        } else {
            statement.setNull(4, Types.TIME);
        }

        // NULL-Behandlung: Zeitraum von (datefrom)
        if (availability.getDateFrom() != null) {
            statement.setDate(5, Date.valueOf(availability.getDateFrom()));
        } else {
            statement.setNull(5, Types.DATE);
        }

        // NULL-Behandlung: Zeitraum bis (dateto)
        if (availability.getDateTo() != null) {
            statement.setDate(6, Date.valueOf(availability.getDateTo()));
        } else {
            statement.setNull(6, Types.DATE);
        }

        // Optionaler Kommentar
        statement.setString(7, availability.getComment());

        // Fremdschlüssel: Verknüpfung zum Dolmetscher
        statement.setInt(8, availability.getDolmetscherID());

        // Einfügen ausführen
        statement.executeUpdate();
    }

    /**
     * Löscht einen Availability-Eintrag anhand seiner ID
     */
    public void delete(int availabilityID) throws SQLException {

        // SQL: Datensatz anhand der ID löschen
        String sql = "DELETE FROM Availability WHERE AvailabilityID = ?";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement → sicherer als Statement, verhindert SQL-Injection
        PreparedStatement statement = connection.prepareStatement(sql);

        // ID als Parameter setzen
        statement.setInt(1, availabilityID);

        // Löschung ausführen
        statement.executeUpdate();
    }

    /**
     * Prüft ob ein Dolmetscher zum gewünschten Zeitpunkt verfügbar ist
     * Wird in AdminController.handleAssign() VOR der Zuweisung aufgerufen
     *
     * Logik: Sucht nach einer Blockierung die den Zeitraum überschneidet
     * Einzeltermin: date = ? AND starttime <= start AND endtime >= end
     * Zeitraum:     datefrom <= date AND dateto >= date
     *
     * Rückgabe: true  = kein Eintrag gefunden = Dolmetscher ist VERFÜGBAR
     *           false = Eintrag gefunden      = Dolmetscher ist NICHT VERFÜGBAR
     */
    public boolean isAvailable(int dolmetscherID, LocalDate date,
                               LocalTime start, LocalTime end) throws SQLException {

        // SQL: sucht nach Blockierungen die den gewünschten Zeitraum überschneiden
        // OR-Bedingung: Einzeltermin ODER Zeitraum
        String sql = "SELECT * FROM Availability " +
                "WHERE DolmetscherID = ? " +
                "AND availabilitytype != 'Verfügbar' " +
                "AND (" +
                "  (date = ? AND starttime <= ? AND endtime >= ?) " +   // Einzeltermin
                "  OR (datefrom <= ? AND dateto >= ?)" +                // Zeitraum
                ")";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement → sicherer als Statement, verhindert SQL-Injection
        PreparedStatement statement = connection.prepareStatement(sql);

        // Parameter setzen
        statement.setInt(1, dolmetscherID);     // Welcher Dolmetscher?
        statement.setDate(2, Date.valueOf(date)); // Einzeltermin: Datum
        statement.setTime(3, Time.valueOf(start)); // Einzeltermin: Startzeit
        statement.setTime(4, Time.valueOf(end));   // Einzeltermin: Endzeit
        statement.setDate(5, Date.valueOf(date));  // Zeitraum: liegt Datum im Zeitraum?
        statement.setDate(6, Date.valueOf(date));  // Zeitraum: liegt Datum im Zeitraum?

        // Abfrage ausführen → gibt ResultSet (Tabelle von Ergebnissen) zurück
        ResultSet rs = statement.executeQuery();

        // rs.next() = true  → Blockierung gefunden → NICHT verfügbar → false zurückgeben
        // rs.next() = false → keine Blockierung    → verfügbar       → true zurückgeben
        return !rs.next();
    }
}