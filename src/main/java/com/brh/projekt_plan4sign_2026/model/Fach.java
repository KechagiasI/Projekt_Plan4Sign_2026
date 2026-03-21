package com.brh.projekt_plan4sign_2026.model;

public class Fach {

    private int fachID;
    private String fachName;
    private boolean isInterpreterRelevant;
    private int bereichID;

    // Constructor
    public Fach(int fachID, String fachName, boolean isInterpreterRelevant, int bereichID) {
        this.fachID = fachID;
        this.fachName = fachName;
        this.isInterpreterRelevant = isInterpreterRelevant;
        this.bereichID = bereichID;
    }

    // Getters
    public int getFachID(){ return fachID; }
    public String getFachName(){ return fachName; }
    public boolean isInterpreterRelevant(){ return isInterpreterRelevant; }
    public int getBereichID(){ return bereichID; }

    // Setters
    public void setFachID(int fachID){ this.fachID = fachID; }
    public void setFachName(String fachName){ this.fachName = fachName; }
    public void setInterpreterRelevant(boolean isInterpreterRelevant){ this.isInterpreterRelevant = isInterpreterRelevant; }
    public void setBereichID(int bereichID){ this.bereichID = bereichID; }
}