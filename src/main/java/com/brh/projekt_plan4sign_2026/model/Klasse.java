package com.brh.projekt_plan4sign_2026.model;

public class Klasse {
    private int klasseID;
    private String klasseName;
    private String room;

    // Constructor zum Erstellen eines Klasse-Objekts
    public Klasse(int klasseID, String klassename, String room) {
        this.klasseID = klasseID;
        this.klasseName = klassename;
        this.room = room;
    }

    // Getters
    public int getKlasseID() { return klasseID; }
    public String getKlasseName() { return klasseName; }
    public String getRoom() { return room; }

    // Setters
    public void setKlasseID(int klasseID) { this.klasseID = klasseID; }
    public void setKlasseName(String klasseName) { this.klasseName = klasseName; }
    public void setRoom(String room) { this.room = room; }
}
