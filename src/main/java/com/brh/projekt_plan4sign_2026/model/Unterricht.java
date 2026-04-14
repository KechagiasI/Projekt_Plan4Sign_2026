package com.brh.projekt_plan4sign_2026.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Unterricht.java – Entity-Klasse für die Tabelle "Unterricht"
 * Repräsentiert eine einzelne Unterrichtseinheit
 * Enthält Basisdaten + Anzeigefelder (werden per JOIN aus der DB befüllt)
 */
public class Unterricht {

    // ===== Basisdaten (entsprechen den Spalten der DB-Tabelle) =====
    private int unterrichtID;       // Primärschlüssel (AUTO_INCREMENT)
    private LocalDate date;         // Datum der Unterrichtseinheit
    private LocalTime startTime;    // Startzeit
    private LocalTime endTime;      // Endzeit
    private int klasseID;           // Fremdschlüssel → Tabelle Klasse
    private int fachID;             // Fremdschlüssel → Tabelle Fach
    private Integer dolmetscherID;  // Fremdschlüssel → Tabelle Dolmetscher
    // Integer (nicht int) weil NULL erlaubt
    // NULL = kein Dolmetscher zugewiesen

    // ===== Anzeigefelder (nicht in DB gespeichert) =====
    // Werden per JOIN in getAllWithDetails() befüllt
    private String klassename;       // Name der Klasse (z.B. "2352")
    private String fachname;         // Name des Fachs (z.B. "JAVA")
    private String dolmetschername;  // Vor- und Nachname des Dolmetschers
    private String teilnehmername;   // Vor- und Nachname des Teilnehmers

    // ===== Konstruktor =====
    // Nur Basisdaten – Anzeigefelder werden per Setter nachträglich gesetzt
    public Unterricht(int unterrichtID, LocalDate date, LocalTime startTime,
                      LocalTime endTime, int klasseID, int fachID,
                      Integer dolmetscherID) {
        this.unterrichtID = unterrichtID;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.klasseID = klasseID;
        this.fachID = fachID;
        this.dolmetscherID = dolmetscherID;
    }

    // ===== Getter – Lesezugriff auf die Attribute =====
    public int getUnterrichtID()        { return unterrichtID; }
    public LocalDate getDate()          { return date; }
    public LocalTime getStartTime()     { return startTime; }
    public LocalTime getEndTime()       { return endTime; }
    public int getKlasseID()            { return klasseID; }
    public int getFachID()              { return fachID; }
    public Integer getDolmetscherID()   { return dolmetscherID; }
    public String getKlassename()       { return klassename; }
    public String getFachname()         { return fachname; }
    public String getDolmetschername()  { return dolmetschername; }
    public String getTeilnehmername()   { return teilnehmername; }

    // ===== Setter – Schreibzugriff auf die Attribute =====
    public void setUnterrichtID(int unterrichtID)           { this.unterrichtID = unterrichtID; }
    public void setDate(LocalDate date)                     { this.date = date; }
    public void setStartTime(LocalTime startTime)           { this.startTime = startTime; }
    public void setEndTime(LocalTime endTime)               { this.endTime = endTime; }
    public void setKlasseID(int klasseID)                   { this.klasseID = klasseID; }
    public void setFachID(int fachID)                       { this.fachID = fachID; }
    public void setDolmetscherID(Integer dolmetscherID)     { this.dolmetscherID = dolmetscherID; }
    public void setKlassename(String klassename)            { this.klassename = klassename; }
    public void setFachname(String fachname)                { this.fachname = fachname; }
    public void setDolmetschername(String dolmetschername)  { this.dolmetschername = dolmetschername; }
    public void setTeilnehmername(String teilnehmername)    { this.teilnehmername = teilnehmername; }
}