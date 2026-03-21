package com.brh.projekt_plan4sign_2026.model;

public class Bereich {
    // Entity Class
    private int bereichID;
    private String bereichName;

    // Constructor zum Erstellen eines Bereich-Objekts
    public Bereich(int bereichID, String bereichname) {
        this.bereichID = bereichID;
        this.bereichName = bereichname;
    }

    // Getters
    public int getBereichID() {return bereichID;}
    public String getBereichName() {return bereichName;}

    // Setters
    public void setBereichID(int bereichID) { this.bereichID = bereichID; }
    public void setBereichName(String bereichName) { this.bereichName = bereichName; }

}
