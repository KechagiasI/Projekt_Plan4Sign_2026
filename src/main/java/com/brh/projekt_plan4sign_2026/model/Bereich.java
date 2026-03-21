package com.brh.projekt_plan4sign_2026.model;

public class Bereich {
    // Entity Class
    private int bereichID;
    private String bereichname;

    // Constructor zum Erstellen eines Bereich-Objekts
    public Bereich(int bereichID, String bereichname) {
        this.bereichID = bereichID;
        this.bereichname = bereichname;
    }

    // Getters
    public int getBereichID() {return bereichID;}
    public String bereichname() {return bereichname;}

    // Setters
    public void setBereichID(int bereichID) { this.bereichID = bereichID; }
    public void setbereichname(String bereichname) { this.bereichname = bereichname; }
}
