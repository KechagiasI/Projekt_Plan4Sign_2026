package com.brh.projekt_plan4sign_2026.model;


// Role.java – Enum für die Benutzerrollen
//
// Wird verwendet in:
// User.java          → Attribut "role"
// LoginController    → Rollenbasierte Navigation nach dem Login
// DB-Tabelle User    → Spalte "role" als ENUM('ADMIN','DOLMETSCHER','TEILNEHMER')
//
// Vorteil gegenüber String oder Boolean (isAdmin):
// Typsicher, erweiterbar, keine Tippfehler möglich

public enum Role {

    // Administrator: hat Zugriff auf alle Funktionen
    // → Sieht alle Unterrichtseinheiten, kann Dolmetscher zuweisen/entfernen
    ADMIN,

    // Dolmetscher: eingeschränkter Zugriff
    // → Sieht nur seinen persönlichen Einsatzplan
    DOLMETSCHER,

    // Teilnehmer: eingeschränkter Zugriff
    // → Sieht nur den Stundenplan seiner eigenen Klasse
    TEILNEHMER
}