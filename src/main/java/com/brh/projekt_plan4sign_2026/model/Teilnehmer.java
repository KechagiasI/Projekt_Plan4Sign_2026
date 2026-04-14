package com.brh.projekt_plan4sign_2026.model;

/**
 * Teilnehmer.java – Entity-Klasse für die Tabelle "Teilnehmer"
 * Repräsentiert einen hörgeschädigten Teilnehmer der Berufsschule
 * Verknüpft mit: User (1:1) und Klasse (n:1)
 */
public class Teilnehmer {

    // ===== Attribute (entsprechen den Spalten der DB-Tabelle) =====
    private int teilnehmerID;   // Primärschlüssel (AUTO_INCREMENT)
    private String firstname;   // Vorname
    private String lastname;    // Nachname
    private String email;       // E-Mail-Adresse
    private String mobilephone; // Mobilnummer
    private String comment;     // Optionaler Kommentar
    private int klasseID;       // Fremdschlüssel → Tabelle Klasse (NOT NULL)
    private int userID;         // Fremdschlüssel → Tabelle User (UNIQUE, NOT NULL)

    // ===== Konstruktor =====
    // Wird vom TeilnehmerDAO verwendet um ein Objekt aus der DB zu erstellen
    public Teilnehmer(int teilnehmerID, String firstname, String lastname,
                      String email, String mobilephone, String comment,
                      int klasseID, int userID) {
        this.teilnehmerID = teilnehmerID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.mobilephone = mobilephone;
        this.comment = comment;
        this.klasseID = klasseID;
        this.userID = userID;
    }

    // ===== Getter – Lesezugriff auf die Attribute =====
    public int getTeilnehmerID()    { return teilnehmerID; }
    public String getFirstname()    { return firstname; }
    public String getLastname()     { return lastname; }
    public String getEmail()        { return email; }
    public String getMobilephone()  { return mobilephone; }
    public String getComment()      { return comment; }
    public int getKlasseID()        { return klasseID; }
    public int getUserID()          { return userID; }

    // ===== Setter – Schreibzugriff auf die Attribute =====
    public void setTeilnehmerID(int teilnehmerID)   { this.teilnehmerID = teilnehmerID; }
    public void setFirstname(String firstname)       { this.firstname = firstname; }
    public void setLastname(String lastname)         { this.lastname = lastname; }
    public void setEmail(String email)               { this.email = email; }
    public void setMobilephone(String mobilephone)   { this.mobilephone = mobilephone; }
    public void setComment(String comment)           { this.comment = comment; }
    public void setKlasseID(int klasseID)            { this.klasseID = klasseID; }
    public void setUserID(int userID)                { this.userID = userID; }

}