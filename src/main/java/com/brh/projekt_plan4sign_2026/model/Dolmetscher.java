package com.brh.projekt_plan4sign_2026.model;

/**
 * Dolmetscher.java – Entity-Klasse für die Tabelle "Dolmetscher"
 * Repräsentiert einen Gebärdensprachdolmetscher
 * Verknüpft mit: User (1:1) und Unterricht (1:n) und Availability (1:n)
 */
public class Dolmetscher {

    // ===== Attribute (entsprechen den Spalten der DB-Tabelle) =====
    private int dolmetscherID;  // Primärschlüssel (AUTO_INCREMENT)
    private String firstname;   // Vorname
    private String lastname;    // Nachname
    private String email;       // E-Mail-Adresse
    private String mobilePhone; // Mobilnummer
    private String comment;     // Optionaler Kommentar
    private int userID;         // Fremdschlüssel → Tabelle User (UNIQUE, NOT NULL)

    // ===== Konstruktor =====
    // Wird vom DolmetscherDAO verwendet um ein Objekt aus der DB zu erstellen
    public Dolmetscher(int dolmetscherID, String firstname, String lastname,
                       String email, String mobilephone, String comment,
                       int userID) {
        this.dolmetscherID = dolmetscherID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.mobilePhone = mobilephone;
        this.comment = comment;
        this.userID = userID;
    }

    // ===== Getter – Lesezugriff auf die Attribute =====
    public int getDolmetscherID()   { return dolmetscherID; }
    public String getFirstname()    { return firstname; }
    public String getLastname()     { return lastname; }
    public String getEmail()        { return email; }
    public String getMobilePhone()  { return mobilePhone; }
    public String getComment()      { return comment; }
    public int getUserID()          { return userID; }

    // ===== Setter – Schreibzugriff auf die Attribute =====
    public void setDolmetscherID(int dolmetscherID)     { this.dolmetscherID = dolmetscherID; }
    public void setFirstname(String firstname)           { this.firstname = firstname; } // ✅ korrigiert
    public void setLastname(String lastname)             { this.lastname = lastname; }
    public void setEmail(String email)                   { this.email = email; }
    public void setMobilePhone(String mobilePhone)       { this.mobilePhone = mobilePhone; }
    public void setComment(String comment)               { this.comment = comment; }
    public void setUserID(int userID)                    { this.userID = userID; }

}