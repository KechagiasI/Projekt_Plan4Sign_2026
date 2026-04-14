package com.brh.projekt_plan4sign_2026.model;

/**
 * Klasse.java – Entity-Klasse für die Tabelle "Klasse"
 * Repräsentiert eine Schulklasse mit Raumangabe
 * Verknüpft mit: Teilnehmer (1:n) und Unterricht (1:n)
 */
public class Klasse {

    // ===== Attribute (entsprechen den Spalten der DB-Tabelle) =====
    private int klasseID;       // Primärschlüssel (AUTO_INCREMENT)
    private String klasseName;  // Name der Klasse (z.B. "2352", "2342")
    private String room;        // Unterrichtsraum (z.B. "BS06-E01")

    // ===== Konstruktor =====
    // Wird vom KlasseDAO verwendet um ein Objekt aus der DB zu erstellen
    public Klasse(int klasseID, String klassename, String room) {
        this.klasseID = klasseID;
        this.klasseName = klassename;
        this.room = room;
    }

    // ===== Getter – Lesezugriff auf die Attribute =====
    public int getKlasseID()        { return klasseID; }
    public String getKlasseName()   { return klasseName; }
    public String getRoom()         { return room; }

    // ===== Setter – Schreibzugriff auf die Attribute =====
    public void setKlasseID(int klasseID)       { this.klasseID = klasseID; }
    public void setKlasseName(String klasseName) { this.klasseName = klasseName; }
    public void setRoom(String room)             { this.room = room; }
}