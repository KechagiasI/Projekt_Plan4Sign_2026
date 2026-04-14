package com.brh.projekt_plan4sign_2026.model;

/**
 * Fach.java – Entity-Klasse für die Tabelle "Fach"
 * Repräsentiert ein Unterrichtsfach eines bestimmten Bereichs
 * Verknüpft mit: Bereich (n:1) und Unterricht (1:n)
 * Constraint: UNIQUE(fachname, BereichID) – kein doppeltes Fach im gleichen Bereich
 */
public class Fach {

    // ===== Attribute (entsprechen den Spalten der DB-Tabelle) =====
    private int fachID;                     // Primärschlüssel (AUTO_INCREMENT)
    private String fachName;                // Name des Fachs (z.B. "JAVA", "Deutsch")
    private boolean isInterpreterRelevant;  // true = Dolmetscher grundsätzlich erforderlich
    // false = kein Dolmetscher nötig (z.B. Englisch)
    private int bereichID;                  // Fremdschlüssel → Tabelle Bereich (NOT NULL)

    // ===== Konstruktor =====
    // Wird vom FachDAO verwendet um ein Objekt aus der DB zu erstellen
    public Fach(int fachID, String fachName, boolean isInterpreterRelevant, int bereichID) {
        this.fachID = fachID;
        this.fachName = fachName;
        this.isInterpreterRelevant = isInterpreterRelevant;
        this.bereichID = bereichID;
    }

    // ===== Getter – Lesezugriff auf die Attribute =====
    public int getFachID()                  { return fachID; }
    public String getFachName()             { return fachName; }
    public boolean isInterpreterRelevant()  { return isInterpreterRelevant; }
    public int getBereichID()               { return bereichID; }

    // ===== Setter – Schreibzugriff auf die Attribute =====
    public void setFachID(int fachID)                           { this.fachID = fachID; }
    public void setFachName(String fachName)                    { this.fachName = fachName; }
    public void setInterpreterRelevant(boolean isInterpreterRelevant) { this.isInterpreterRelevant = isInterpreterRelevant; }
    public void setBereichID(int bereichID)                     { this.bereichID = bereichID; }
}