package com.brh.projekt_plan4sign_2026.model;

/**
 * Bereich.java – Entity-Klasse für die Tabelle "Bereich"
 * Repräsentiert einen Ausbildungsbereich der Berufsschule
 * Beispiele: FIA, FIS, KFM, MD, QF, TPD
 * Verknüpft mit: Fach (1:n)
 */
public class Bereich {

    // ===== Attribute (entsprechen den Spalten der DB-Tabelle) =====
    private int bereichID;      // Primärschlüssel (AUTO_INCREMENT)
    private String bereichName; // Name des Bereichs (z.B. "FIA - Fachinformatiker Anwendungsentwickler")

    // ===== Konstruktor =====
    // Wird vom BereichDAO verwendet um ein Objekt aus der DB zu erstellen
    public Bereich(int bereichID, String bereichname) {
        this.bereichID = bereichID;
        this.bereichName = bereichname;
    }

    // ===== Getter – Lesezugriff auf die Attribute =====
    public int getBereichID()       { return bereichID; }
    public String getBereichName()  { return bereichName; }

    // ===== Setter – Schreibzugriff auf die Attribute =====
    public void setBereichID(int bereichID)         { this.bereichID = bereichID; }
    public void setBereichName(String bereichName)  { this.bereichName = bereichName; }

}