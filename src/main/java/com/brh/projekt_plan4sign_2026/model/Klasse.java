package com.brh.projekt_plan4sign_2026.model;

public class Klasse {
    private int klasseID;
    private String klassename;
    private String room;

    // Constructor zum Erstellen eines Klasse-Objekts
    public Klasse(int klasseID, String klassename, String room) {
        this.klasseID = klasseID;
        this.klassename = klassename;
        this.room = room;
    }

    // Getters
    public int getKlasseID() { return klasseID; }
    public String getKlassename() { return klassename; }
    public String getRoom() { return room; }

    // Setters
    public void setKlasseID(int klasseID) { this.klasseID = klasseID; }
    public void setKlassename(String klassename) { this.klassename = klassename; }
    public void setRoom(String room) { this.room = room; }
}
