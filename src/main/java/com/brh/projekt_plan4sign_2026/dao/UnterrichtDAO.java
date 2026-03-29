package com.brh.projekt_plan4sign_2026.dao;

import com.brh.projekt_plan4sign_2026.model.Unterricht;
import com.brh.projekt_plan4sign_2026.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class UnterrichtDAO {

    public List<Unterricht> getAllWithDetails() throws SQLException {
        // Liste für Ergebnisse
        List<Unterricht> unterrichts = new ArrayList<>();

        // SQL mit JOIN → holt alle Daten für Anzeige (UI)
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

        // Verbindung zur DB
        Connection connection = DatabaseConnection.getConnection();
        // SQL ausführen
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        // Durch alle Ergebnisse gehen
        while (resultSet.next()) {

            // Basisdaten (aus Tabelle Unterricht)
            int unterrichtID = resultSet.getInt("UnterrichtID");
            LocalDate date = resultSet.getDate("date").toLocalDate();
            LocalTime starttime = resultSet.getTime("starttime").toLocalTime();
            LocalTime endtime = resultSet.getTime("endtime").toLocalTime();
            int klasseID = resultSet.getInt("KlasseID");
            int fachID = resultSet.getInt("FachID");

            // Dolmetscher kann NULL sein → deshalb Integer
            Integer dolmetscherID = resultSet.getObject("DolmetscherID")
                    != null ? resultSet.getInt("DolmetscherID") : null;

            // Objekt erstellen (nur Basisdaten)
            Unterricht u = new Unterricht(
                    unterrichtID, date, starttime, endtime, klasseID, fachID, dolmetscherID);

            // Zusatzdaten (für Anzeige im UI)
            u.setKlassename(resultSet.getString("klassename"));
            u.setFachname(resultSet.getString("fachname"));
            u.setDolmetschername(resultSet.getString("dolmetschername"));
            u.setTeilnehmername(resultSet.getString("teilnehmername"));

            // Objekt zur Liste hinzufügen
            unterrichts.add(u);
        }

        // Ergebnis zurückgeben
        return unterrichts;
    }

    // Holt alle Unterrichtseinheiten eines Dolmetschers – mit JOIN-Details für die Anzeige
    public List<Unterricht> getWithDetailsByDolmetscher(int dolmetscherID) throws SQLException {

        List<Unterricht> unterrichts = new ArrayList<>();

        // Gleiche Struktur wie getAllWithDetails(), aber mit WHERE-Filter
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

    // Holt alle Unterrichtseinheiten einer Klasse – mit JOIN-Details für die Anzeige
    public List<Unterricht> getWithDetailsByKlasse(int klasseID) throws SQLException {

        List<Unterricht> unterrichts = new ArrayList<>();

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

    // Holt alle Unterrichtseinheiten aus der Datenbank
    public List<Unterricht> getAll() throws SQLException {

        // Liste für die Ergebnisse (Java-Objekte)
        List<Unterricht> list = new ArrayList<>();

        // SQL-Abfrage: alle Datensätze aus der Tabelle Unterricht
        String sql = "SELECT * FROM Unterricht";

        // Verbindung zur Datenbank holen
        Connection connection = DatabaseConnection.getConnection();

        // Statement zum Ausführen der Abfrage
        // Hinweis: PreparedStatement wäre Best Practice
        Statement statement = connection.createStatement();

        // Ergebnis der Abfrage
        ResultSet resultSet = statement.executeQuery(sql);

        // Iteration über alle Datensätze
        while (resultSet.next()) {

            int unterrichtID = resultSet.getInt("UnterrichtID");

            // Umwandlung von SQL-Date → LocalDate
            LocalDate date = resultSet.getDate("date").toLocalDate();

            // Umwandlung von SQL-Time → LocalTime
            LocalTime startTime = resultSet.getTime("starttime").toLocalTime();
            LocalTime endTime = resultSet.getTime("endtime").toLocalTime();

            // Fremdschlüssel (Beziehungen)
            int klasseID = resultSet.getInt("KlasseID");
            int fachID = resultSet.getInt("FachID");

            // DolmetscherID kann NULL sein → Verwendung von Integer statt int
            Integer dolmetscherID = resultSet.getObject("DolmetscherID") != null
                    ? resultSet.getInt("DolmetscherID")
                    : null;

            // Mapping DB → Java-Objekt
            list.add(new Unterricht(
                    unterrichtID,
                    date,
                    startTime,
                    endTime,
                    klasseID,
                    fachID,
                    dolmetscherID
            ));
        }

        return list;
    }

    // Holt alle Unterrichtseinheiten einer bestimmten Klasse
    public List<Unterricht> getByKlasse(int klasseID) throws SQLException {

        List<Unterricht> list = new ArrayList<>();

        // SQL-Abfrage mit Filter
        String sql = "SELECT * FROM Unterricht WHERE KlasseID = ?";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        // Parameter setzen
        statement.setInt(1, klasseID);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {

            int unterrichtID = resultSet.getInt("UnterrichtID");
            LocalDate date = resultSet.getDate("date").toLocalDate();
            LocalTime startTime = resultSet.getTime("starttime").toLocalTime();
            LocalTime endTime = resultSet.getTime("endtime").toLocalTime();
            int fachID = resultSet.getInt("FachID");

            // NULL-Check für Dolmetscher
            Integer dolmetscherID = resultSet.getObject("DolmetscherID") != null
                    ? resultSet.getInt("DolmetscherID")
                    : null;

            list.add(new Unterricht(
                    unterrichtID,
                    date,
                    startTime,
                    endTime,
                    klasseID,
                    fachID,
                    dolmetscherID
            ));
        }

        return list;
    }

    // Holt alle Unterrichtseinheiten eines bestimmten Dolmetschers
    public List<Unterricht> getByDolmetscher(int dolmetscherID) throws SQLException {

        List<Unterricht> list = new ArrayList<>();

        // SQL-Abfrage nach DolmetscherID
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

            // Hier ist DolmetscherID bekannt (Parameter)
            list.add(new Unterricht(
                    unterrichtID,
                    date,
                    startTime,
                    endTime,
                    klasseID,
                    fachID,
                    dolmetscherID
            ));
        }

        return list;
    }

    // Fügt eine neue Unterrichtseinheit ein
    public void insert(Unterricht unterricht) throws SQLException {

        // SQL-Insert mit mehreren Parametern
        String sql = "INSERT INTO Unterricht (date, starttime, endtime, KlasseID, FachID, DolmetscherID) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        // Java → SQL Typen umwandeln
        statement.setDate(1, Date.valueOf(unterricht.getDate()));
        statement.setTime(2, Time.valueOf(unterricht.getStartTime()));
        statement.setTime(3, Time.valueOf(unterricht.getEndTime()));

        statement.setInt(4, unterricht.getKlasseID());
        statement.setInt(5, unterricht.getFachID());

        // NULL-Wert behandeln (optionaler Dolmetscher)
        if (unterricht.getDolmetscherID() != null) {
            statement.setInt(6, unterricht.getDolmetscherID());
        } else {
            statement.setNull(6, Types.INTEGER);
        }

        statement.executeUpdate();
    }

    // Weist einem Unterricht einen Dolmetscher zu (UPDATE)
    public void assignDolmetscher(int unterrichtID, int dolmetscherID) throws SQLException {

        // SQL-Update
        String sql = "UPDATE Unterricht SET DolmetscherID = ? WHERE UnterrichtID = ?";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        // Parameter setzen
        statement.setInt(1, dolmetscherID);
        statement.setInt(2, unterrichtID);

        // Änderung ausführen
        statement.executeUpdate();
    }

    // Löscht eine Unterrichtseinheit anhand der ID
    public void delete(int unterrichtID) throws SQLException {

        String sql = "DELETE FROM Unterricht WHERE UnterrichtID = ?";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, unterrichtID);

        statement.executeUpdate();
    }
}