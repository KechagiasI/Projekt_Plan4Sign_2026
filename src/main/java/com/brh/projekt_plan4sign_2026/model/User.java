package com.brh.projekt_plan4sign_2026.model;

/**
 * User.java – Entity-Klasse für die Tabelle "User"
 * Repräsentiert einen Benutzer der Anwendung
 * Rollen: ADMIN, DOLMETSCHER, TEILNEHMER
 */
public class User {

    // ===== Attribute (entsprechen den Spalten der DB-Tabelle) =====
    private int userID;          // Primärschlüssel (AUTO_INCREMENT)
    private String username;     // Benutzername für den Login (UNIQUE)
    private String passwordHash; // BCrypt-Hash des Passworts (kein Klartext!)
    private Role role;           // Rolle: ADMIN, DOLMETSCHER oder TEILNEHMER

    // ===== Konstruktor =====
    // Wird vom UserDAO verwendet um ein Objekt aus der DB zu erstellen
    public User(int userID, String username, String passwordHash, Role role) {
        this.userID = userID;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // ===== Getter – Lesezugriff auf die Attribute =====
    public int getUserID() { return userID; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }

    // ===== Setter – Schreibzugriff auf die Attribute =====
    public void setUserID(int userID) { this.userID = userID; }
    public void setUsername(String username) { this.username = username; }
    public void setpasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    private void setRole(Role role) { this.role = role; }

}