package com.brh.projekt_plan4sign_2026.model;

public class User {

    // Entity Class
    private int userID;             // Eindeutige ID des Benutzers (Primärschlüssel)
    private String username;        // Benutzername für den Login (muss eindeutig sein)
    private String passwordHash;    // Gespeicherter Passwort-Hash (kein Klartext!)
    private Role role;              // Rolle des Benutzers (ADMIN, DOLMETSCHER, TEILNEHMER)

    // Constructor zum Erstellen eines User-Objekts
    public User(int userID, String username, String passwordHash, Role role) {
        this.userID = userID;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // Getters
    public int getUserID() { return userID; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }

    // Setters
    public void setUserID(int userID){ this.userID = userID; }
    public void setUsername(String username) { this.username = username; }
    public void setpasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    private void setRole(Role role) { this.role = role; }

}
