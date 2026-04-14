package com.brh.projekt_plan4sign_2026.dao;

import com.brh.projekt_plan4sign_2026.model.Unterricht;
import com.brh.projekt_plan4sign_2026.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * UnterrichtDAO.java – Datenzugriffsklasse für die Tabelle "Unterricht"
 * Kapselt alle Datenbankoperationen für Unterrichtseinheiten
 *
 * Methoden:
 * - getAllWithDetails()              → Alle Einheiten mit JOIN-Daten (Admin)
 * - getWithDetailsByDolmetscher()   → Gefiltert nach Dolmetscher (DolmetscherView)
 * - getWithDetailsByKlasse()        → Gefiltert nach Klasse (TeilnehmerView)
 * - assignDolmetscher()             → Dolmetscher zuweisen
 * - removeDolmetscher()             → Zuweisung aufheben (NULL setzen)
 * - hasConflict()                   → Zeitkonflikt prüfen
 */
public class UnterrichtDAO {

    /**
     * Holt alle Unterrichtseinheiten mit JOIN-Daten für die AdminView
     * JOIN-Tabellen: Klasse, Fach, Dolmetscher, Teilnehmer
     * LEFT JOIN bei Dolmetscher/Teilnehmer → NULL-Werte erlaubt
     */
    public List<Unterricht> getAllWithDetails() throws SQLException {

        List<Unterricht> unterrichts = new ArrayList<>();

        // SQL mit mehreren JOINs → holt alle Anzeigedaten in einer Abfrage
        // LEFT JOIN Dolmetscher → NULL wenn kein Dolmetscher zugewiesen
        // LEFT JOIN Teilnehmer  → NULL wenn kein Teilnehmer in der Klasse
        String sql = "SELECT u.UnterrichtID, u.date, u.starttime, u.endtime, " +
                "u.KlasseID, u.FachID, u.DolmetscherID, " +
                "k.klassename, f.fachname, " +
                "CONCAT(d.firstname, ' ', d.lastname) AS dolmetschername, " +
                "CONCAT(t.firstname, ' ', t.lastname) AS teilnehmername " +
                "FROM Unterricht u " +
                "JOIN Klasse k ON u.KlasseID = k.KlasseID " +
                "JOIN Fach f ON u.FachID = f.FachID " +
                "LEFT JOIN Dolmetscher d ON u.DolmetscherID = d.DolmetscherID " +
                "LEFT JOIN Teilnehmer t ON t.KlasseID = u.KlasseID " +
                "ORDER BY u.date, u.starttime";

        // Verbindung zur Datenbank holen (Singleton)
        Connection connection = DatabaseConnection.getConnection();
        // PreparedStatement → sicherer als Statement, verhindert SQL-Injection
        Statement statement = connection.createStatement();
        // Abfrage ausführen → gibt ResultSet (Tabelle von Ergebnissen) zurück
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {

            // Basisdaten aus der Tabelle Unterricht
            int unterrichtID = resultSet.getInt("UnterrichtID");
            LocalDate date = resultSet.getDate("date").toLocalDate();
            LocalTime starttime = resultSet.getTime("starttime").toLocalTime();
            LocalTime endtime = resultSet.getTime("endtime").toLocalTime();
            int klasseID = resultSet.getInt("KlasseID");
            int fachID = resultSet.getInt("FachID");

            // NULL-Check: DolmetscherID kann NULL sein → Integer statt int
            Integer dolmetscherID = resultSet.getObject("DolmetscherID") != null
                    ? resultSet.getInt("DolmetscherID") : null;

            // Objekt mit Basisdaten erstellen
            Unterricht u = new Unterricht(
                    unterrichtID, date, starttime, endtime, klasseID, fachID, dolmetscherID);

            // Anzeigefelder nachträglich setzen (kommen aus den JOINs)
            u.setKlassename(resultSet.getString("klassename"));
            u.setFachname(resultSet.getString("fachname"));
            u.setDolmetschername(resultSet.getString("dolmetschername"));
            u.setTeilnehmername(resultSet.getString("teilnehmername"));

            unterrichts.add(u);
        }
        return unterrichts;
    }

    /**
     * Holt alle Unterrichtseinheiten eines bestimmten Dolmetschers
     * Wird in der DolmetscherView verwendet (nach Login gefiltert)
     * WHERE u.DolmetscherID = ? → nur eigene Einheiten
     */
    public List<Unterricht> getWithDetailsByDolmetscher(int dolmetscherID) throws SQLException {

        List<Unterricht> unterrichts = new ArrayList<>();

        // Gleiche JOIN-Struktur wie getAllWithDetails() + WHERE-Filter
        String sql = "SELECT u.UnterrichtID, u.date, u.starttime, u.endtime, " +
                "u.KlasseID, u.FachID, u.DolmetscherID, " +
                "k.klassename, f.fachname, " +
                "CONCAT(d.firstname, ' ', d.lastname) AS dolmetschername, " +
                "CONCAT(t.firstname, ' ', t.lastname) AS teilnehmername " +
                "FROM Unterricht u " +
                "JOIN Klasse k ON u.KlasseID = k.KlasseID " +
                "JOIN Fach f ON u.FachID = f.FachID " +
                "LEFT JOIN Dolmetscher d ON u.DolmetscherID = d.DolmetscherID " +
                "LEFT JOIN Teilnehmer t ON t.KlasseID = u.KlasseID " +
                "WHERE u.DolmetscherID = ? " +
                "ORDER BY u.date, u.starttime";

        Connection connection = DatabaseConnection.getConnection();

        // PreparedStatement → sicherer als Statement, verhindert SQL-Injection
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, dolmetscherID);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int unterrichtID = resultSet.getInt("UnterrichtID");
            LocalDate date = resultSet.getDate("date").toLocalDate();
            LocalTime starttime = resultSet.getTime("starttime").toLocalTime();
            LocalTime endtime = resultSet.getTime("endtime").toLocalTime();
            int klasseID = resultSet.getInt("KlasseID");
            int fachID = resultSet.getInt("FachID");

            Integer dID = resultSet.getObject("DolmetscherID") != null
                    ? resultSet.getInt("DolmetscherID") : null;

            Unterricht u = new Unterricht(unterrichtID, date, starttime, endtime, klasseID, fachID, dID);
            u.setKlassename(resultSet.getString("klassename"));
            u.setFachname(resultSet.getString("fachname"));
            u.setDolmetschername(resultSet.getString("dolmetschername"));
            u.setTeilnehmername(resultSet.getString("teilnehmername"));

            unterrichts.add(u);
        }
        return unterrichts;
    }

    /**
     * Holt alle Unterrichtseinheiten einer bestimmten Klasse
     * Wird in der TeilnehmerView verwendet (nach Login gefiltert)
     * WHERE u.KlasseID = ? → nur Einheiten der eigenen Klasse
     */
    public List<Unterricht> getWithDetailsByKlasse(int klasseID) throws SQLException {

        List<Unterricht> unterrichts = new ArrayList<>();

        // Gleiche JOIN-Struktur wie getAllWithDetails() + WHERE-Filter
        String sql = "SELECT u.UnterrichtID, u.date, u.starttime, u.endtime, " +
                "u.KlasseID, u.FachID, u.DolmetscherID, " +
                "k.klassename, f.fachname, " +
                "CONCAT(d.firstname, ' ', d.lastname) AS dolmetschername, " +
                "CONCAT(t.firstname, ' ', t.lastname) AS teilnehmername " +
                "FROM Unterricht u " +
                "JOIN Klasse k ON u.KlasseID = k.KlasseID " +
                "JOIN Fach f ON u.FachID = f.FachID " +
                "LEFT JOIN Dolmetscher d ON u.DolmetscherID = d.DolmetscherID " +
                "LEFT JOIN Teilnehmer t ON t.KlasseID = u.KlasseID " +
                "WHERE u.KlasseID = ? " +
                "ORDER BY u.date, u.starttime";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, klasseID);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int unterrichtID = resultSet.getInt("UnterrichtID");
            LocalDate date = resultSet.getDate("date").toLocalDate();
            LocalTime starttime = resultSet.getTime("starttime").toLocalTime();
            LocalTime endtime = resultSet.getTime("endtime").toLocalTime();
            int kID = resultSet.getInt("KlasseID");
            int fachID = resultSet.getInt("FachID");

            Integer dID = resultSet.getObject("DolmetscherID") != null
                    ? resultSet.getInt("DolmetscherID") : null;

            Unterricht u = new Unterricht(unterrichtID, date, starttime, endtime, kID, fachID, dID);
            u.setKlassename(resultSet.getString("klassename"));
            u.setFachname(resultSet.getString("fachname"));
            u.setDolmetschername(resultSet.getString("dolmetschername"));
            u.setTeilnehmername(resultSet.getString("teilnehmername"));

            unterrichts.add(u);
        }
        return unterrichts;
    }

    /**
     * Holt alle Unterrichtseinheiten ohne JOIN-Daten (nur Basisdaten)
     * Wird intern verwendet wenn keine Anzeigedaten benötigt werden
     */
    public List<Unterricht> getAll() throws SQLException {

        List<Unterricht> list = new ArrayList<>();
        String sql = "SELECT * FROM Unterricht";

        Connection connection = DatabaseConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int unterrichtID = resultSet.getInt("UnterrichtID");
            // SQL-Date → LocalDate
            LocalDate date = resultSet.getDate("date").toLocalDate();
            // SQL-Time → LocalTime
            LocalTime startTime = resultSet.getTime("starttime").toLocalTime();
            LocalTime endTime = resultSet.getTime("endtime").toLocalTime();
            int klasseID = resultSet.getInt("KlasseID");
            int fachID = resultSet.getInt("FachID");
            // NULL-Check für optionalen Dolmetscher
            Integer dolmetscherID = resultSet.getObject("DolmetscherID") != null
                    ? resultSet.getInt("DolmetscherID") : null;

            list.add(new Unterricht(unterrichtID, date, startTime, endTime,
                    klasseID, fachID, dolmetscherID));
        }
        return list;
    }

    /**
     * Holt alle Unterrichtseinheiten einer bestimmten Klasse (ohne JOIN)
     */
    public List<Unterricht> getByKlasse(int klasseID) throws SQLException {

        List<Unterricht> list = new ArrayList<>();
        String sql = "SELECT * FROM Unterricht WHERE KlasseID = ?";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, klasseID);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int unterrichtID = resultSet.getInt("UnterrichtID");
            LocalDate date = resultSet.getDate("date").toLocalDate();
            LocalTime startTime = resultSet.getTime("starttime").toLocalTime();
            LocalTime endTime = resultSet.getTime("endtime").toLocalTime();
            int fachID = resultSet.getInt("FachID");
            Integer dolmetscherID = resultSet.getObject("DolmetscherID") != null
                    ? resultSet.getInt("DolmetscherID") : null;

            list.add(new Unterricht(unterrichtID, date, startTime, endTime,
                    klasseID, fachID, dolmetscherID));
        }
        return list;
    }

    /**
     * Holt alle Unterrichtseinheiten eines bestimmten Dolmetschers (ohne JOIN)
     */
    public List<Unterricht> getByDolmetscher(int dolmetscherID) throws SQLException {

        List<Unterricht> list = new ArrayList<>();
        String sql = "SELECT * FROM Unterricht WHERE DolmetscherID = ?";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, dolmetscherID);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int unterrichtID = resultSet.getInt("UnterrichtID");
            LocalDate date = resultSet.getDate("date").toLocalDate();
            LocalTime startTime = resultSet.getTime("starttime").toLocalTime();
            LocalTime endTime = resultSet.getTime("endtime").toLocalTime();
            int klasseID = resultSet.getInt("KlasseID");
            int fachID = resultSet.getInt("FachID");

            list.add(new Unterricht(unterrichtID, date, startTime, endTime,
                    klasseID, fachID, dolmetscherID));
        }
        return list;
    }

    /**
     * Fügt eine neue Unterrichtseinheit in die Datenbank ein
     * DolmetscherID kann NULL sein → setNull() verwenden
     */
    public void insert(Unterricht unterricht) throws SQLException {

        String sql = "INSERT INTO Unterricht (date, starttime, endtime, KlasseID, FachID, DolmetscherID) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        // Java-Typen → SQL-Typen umwandeln
        statement.setDate(1, Date.valueOf(unterricht.getDate()));
        statement.setTime(2, Time.valueOf(unterricht.getStartTime()));
        statement.setTime(3, Time.valueOf(unterricht.getEndTime()));
        statement.setInt(4, unterricht.getKlasseID());
        statement.setInt(5, unterricht.getFachID());

        // NULL-Behandlung: Dolmetscher ist optional
        if (unterricht.getDolmetscherID() != null) {
            statement.setInt(6, unterricht.getDolmetscherID());
        } else {
            statement.setNull(6, Types.INTEGER);
        }
        statement.executeUpdate();
    }

    /**
     * Weist einem Unterricht einen Dolmetscher zu
     * UPDATE: setzt DolmetscherID auf den gewählten Wert
     * Wird in AdminController.handleAssign() aufgerufen
     */
    public void assignDolmetscher(int unterrichtID, int dolmetscherID) throws SQLException {

        String sql = "UPDATE Unterricht SET DolmetscherID = ? WHERE UnterrichtID = ?";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, dolmetscherID);
        statement.setInt(2, unterrichtID);
        statement.executeUpdate();
    }

    /**
     * Löscht eine Unterrichtseinheit anhand der ID
     */
    public void delete(int unterrichtID) throws SQLException {

        String sql = "DELETE FROM Unterricht WHERE UnterrichtID = ?";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, unterrichtID);
        statement.executeUpdate();
    }

    /**
     * Entfernt den zugewiesenen Dolmetscher von einer Unterrichtseinheit
     * UPDATE: setzt DolmetscherID auf NULL
     * Wird in AdminController.handleRemove() aufgerufen
     */
    public void removeDolmetscher(int unterrichtID) throws SQLException {

        String sql = "UPDATE Unterricht SET DolmetscherID = NULL WHERE UnterrichtID = ?";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, unterrichtID);
        statement.executeUpdate();
    }

    /**
     * Prüft ob ein Dolmetscher zur gleichen Zeit bereits einen anderen Unterricht hat
     * Zeitkonflikt-Logik: starttime < endTime AND endtime > startTime
     * AND UnterrichtID != ? → aktueller Unterricht wird ausgeschlossen
     * Wird in AdminController.handleAssign() VOR der Zuweisung aufgerufen
     * Rückgabe: true = Konflikt vorhanden, false = kein Konflikt
     */
    public boolean hasConflict(int dolmetscherID, int unterrichtID,
                               java.time.LocalDate date,
                               java.time.LocalTime startTime,
                               java.time.LocalTime endTime) throws SQLException {

        String sql = "SELECT COUNT(*) FROM Unterricht " +
                "WHERE DolmetscherID = ? " +
                "AND UnterrichtID != ? " +  // aktuellen Unterricht ausschließen
                "AND date = ? " +           // gleicher Tag
                "AND starttime < ? " +      // andere Einheit beginnt vor unserem Ende
                "AND endtime > ?";          // andere Einheit endet nach unserem Start

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, dolmetscherID);
        statement.setInt(2, unterrichtID);
        statement.setDate(3, Date.valueOf(date));
        statement.setTime(4, Time.valueOf(endTime));
        statement.setTime(5, Time.valueOf(startTime));

        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        // COUNT(*) > 0 → mindestens ein Konflikt gefunden
        return resultSet.getInt(1) > 0;
    }
}