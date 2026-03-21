package com.brh.projekt_plan4sign_2026.model;

public class Teilnehmer {

    // Entity Class
    private int teilnehmerID;
    private String firstname;
    private String lastname;
    private String email;
    private String mobilephone;
    private String comment;
    private int klasseID;
    private int userID;

    // Constructor
    public Teilnehmer(int teilnehmerID,  String firstname, String lastname, String email, String mobilephone, String comment, int klasseID, int userID) {
        this.teilnehmerID = teilnehmerID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.mobilephone = mobilephone;
        this.comment = comment;
        this.klasseID = klasseID;
        this.userID = userID;
    }

    // Getters
    public int getTeilnehmerID() {return teilnehmerID;}
    public String getFirstname() {return firstname;}
    public String getLastname() {return lastname;}
    public String getEmail() {return email;}
    public String getMobilephone() {return mobilephone;}
    public String getComment() {return comment;}
    public int getKlasseID() {return klasseID;}
    public int getUserID() {return userID;}

    // Setters
    public void setTeilnehmerID(int teilnehmerID) { this.teilnehmerID = teilnehmerID; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public void setEmail(String email) { this.email = email; }
    public void setMobilephone(String mobilephone) { this.mobilephone = mobilephone; }
    public void setComment(String comment) { this.comment = comment; }
    public void setKlasseID(int klasseID) { this.klasseID = klasseID; }
    public void setUserID(int userID) { this.userID = userID; }

}
