package com.brh.projekt_plan4sign_2026.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Unterricht {

    private int unterrichtID;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int klasseID;
    private int fachID;
    private Integer dolmetscherID; // Integer weil NULL erlaubt
    private String klassename;
    private String fachname;
    private String dolmetschername;

    // Constructor
    public Unterricht(int unterrichtID, LocalDate date, LocalTime startTime,
                      LocalTime endTime, int klasseID, int fachID,
                      Integer dolmetscherID) {
        this.unterrichtID = unterrichtID;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.klasseID = klasseID;
        this.fachID = fachID;
        this.dolmetscherID = dolmetscherID;
    }

    // Getters
    public int getUnterrichtID()        { return unterrichtID; }
    public LocalDate getDate()          { return date; }
    public LocalTime getStartTime()     { return startTime; }
    public LocalTime getEndTime()       { return endTime; }
    public int getKlasseID()            { return klasseID; }
    public int getFachID()              { return fachID; }
    public Integer getDolmetscherID()   { return dolmetscherID; }
    public String getKlassename()       { return klassename; }
    public String getFachname()         { return fachname; }
    public String getDolmetschername()  { return dolmetschername; }

    // Setters
    public void setUnterrichtID(int unterrichtID)       { this.unterrichtID = unterrichtID; }
    public void setDate(LocalDate date)                 { this.date = date; }
    public void setStartTime(LocalTime startTime)       { this.startTime = startTime; }
    public void setEndTime(LocalTime endTime)           { this.endTime = endTime; }
    public void setKlasseID(int klasseID)               { this.klasseID = klasseID; }
    public void setFachID(int fachID)                   { this.fachID = fachID; }
    public void setDolmetscherID(Integer dolmetscherID) { this.dolmetscherID = dolmetscherID; }
    public void setKlassename(String klassename)        { this.klassename = klassename; }
    public void setFachname(String fachname)            { this.fachname = fachname; }
    public void setDolmetschername(String dolmetschername) { this.dolmetschername = dolmetschername; }
}