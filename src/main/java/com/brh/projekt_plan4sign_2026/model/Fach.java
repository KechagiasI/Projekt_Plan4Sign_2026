package com.brh.projekt_plan4sign_2026.model;

public class Fach {

    // Entity Class
    private int fachID;
    private String fachname;
    private boolean isInterpreterRelevant;
    private int bereichID;

    // Constructor zum Erstellen eines Fach-Objekts
    public Fach(int fachID, String fachname, boolean isInterpreterRelevant, int bereichID) {
        this.fachID = fachID;
        this.fachname = fachname;
        this.isInterpreterRelevant = isInterpreterRelevant;
        this.bereichID = bereichID;
    }

    // Getters
    public int getFachID() { return fachID; }
    public String getFachname() { return fachname; }
    public boolean getIsInterpreterRelevant() { return isInterpreterRelevant; }
    public int getBereichID () { return bereichID; }

    // Setters
    public void setFachID(int fachID) { this.fachID = fachID; }
    public void setFachname(String fachname) { this.fachname = fachname; }
    public void setIsInterpreterRelevant(boolean isInterpreterRelevant) { this.isInterpreterRelevant = isInterpreterRelevant; }
    public void setBereichID(int bereichID) { this.bereichID = bereichID; }
}
