package com.brh.projekt_plan4sign_2026.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Availability.java – Entity-Klasse für die Tabelle "Availability"
 * Repräsentiert eine Zeitblockierung eines Dolmetschers
 * Beispiele: Krank, Urlaub, Fortbildung
 * Verknüpft mit: Dolmetscher (n:1) – ON DELETE CASCADE
 *
 * Zwei Arten von Blockierungen:
 * 1. Einzeltermin: date + startTime + endTime
 * 2. Zeitraum:     dateFrom + dateTo
 */
public class Availability {

    // ===== Attribute (entsprechen den Spalten der DB-Tabelle) =====
    private int availabilityID;         // Primärschlüssel (AUTO_INCREMENT)
    private String availabilityType;    // Art der Blockierung (z.B. "Krank", "Urlaub")
    private LocalDate date;             // Datum des Einzeltermins
    private LocalTime startTime;        // Startzeit der Blockierung
    private LocalTime endTime;          // Endzeit der Blockierung
    private LocalDate dateFrom;         // Startdatum eines Zeitraums
    private LocalDate dateTo;           // Enddatum eines Zeitraums
    private String comment;             // Optionaler Kommentar
    private int dolmetscherID;          // Fremdschlüssel → Tabelle Dolmetscher (CASCADE)

    // ===== Konstruktor =====
    // Wird vom AvailabilityDAO verwendet um ein Objekt aus der DB zu erstellen
    public Availability(int availabilityID, String availabilityType,
                        LocalDate date, LocalTime startTime, LocalTime endTime,
                        LocalDate dateFrom, LocalDate dateTo,
                        String comment, int DolmetscherID) {
        this.availabilityID = availabilityID;
        this.availabilityType = availabilityType;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.comment = comment;
        this.dolmetscherID = DolmetscherID;
    }

    // ===== Getter – Lesezugriff auf die Attribute =====
    public int getAvailabilityID()          { return availabilityID; }
    public String getAvailabilityType()     { return availabilityType; }
    public LocalDate getDate()              { return date; }
    public LocalTime getStartTime()         { return startTime; }
    public LocalTime getEndTime()           { return endTime; }
    public LocalDate getDateFrom()          { return dateFrom; }
    public LocalDate getDateTo()            { return dateTo; }
    public String getComment()              { return comment; }
    public int getDolmetscherID()           { return dolmetscherID; }

    // ===== Setter – Schreibzugriff auf die Attribute =====
    public void setAvailabilityID(int availabilityID)           { this.availabilityID = availabilityID; }
    public void setAvailabilityType(String availabilityType)    { this.availabilityType = availabilityType; }
    public void setDate(LocalDate date)                         { this.date = date; }
    public void setStartTime(LocalTime startTime)               { this.startTime = startTime; }
    public void setEndTime(LocalTime endTime)                   { this.endTime = endTime; }
    public void setDateFrom(LocalDate dateFrom)                 { this.dateFrom = dateFrom; }
    public void setDateTo(LocalDate dateTo)                     { this.dateTo = dateTo; }
    public void setComment(String comment)                      { this.comment = comment; }
    public void setDolmetscherID(int dolmetscherID)             { this.dolmetscherID = dolmetscherID; }
}