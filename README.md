# Projekt
**Planungs- und Organisationssoftware für Dolmetschereinsätze im schulischen Umfeld**

---

## Projektübersicht

**Projekt_DoIT** ist eine Desktop-Anwendung zur strukturierten Planung
und Organisation von Dolmetschereinsätzen im schulischen Umfeld.

Das Projekt wird dokumentationsbasiert entwickelt
und orientiert sich an realen organisatorischen Anforderungen.

---

## Ziel des Projekts

Ziel ist die Entwicklung einer Software, die:

- Unterrichtseinheiten strukturiert verwaltet
- Dolmetscher gezielt zuweist
- Verfügbarkeiten berücksichtigt
- Doppelbelegungen verhindert
- eine klare rollenbasierte Zugriffstrennung umsetzt

---

## Ausgangssituation / Problemstellung

Die Einsatzplanung von Dolmetschern erfolgt häufig manuell
(z. B. Tabellen, E-Mail-Absprachen oder mündliche Kommunikation).

Dies führt zu:

- fehlender Transparenz
- hoher Fehleranfälligkeit
- unklarer Zuständigkeit
- Problemen bei kurzfristigen Änderungen
- organisatorischer Mehrbelastung

Das Projekt soll diese Prozesse strukturieren
und nachvollziehbar digital abbilden.

---

## Benutzerrollen

### Administrator

- Verwaltung von:
    - Dolmetschern
    - Teilnehmern
    - Klassen
    - Fachbereichen (Bereich)
    - Fächern (Fach)
    - Unterrichtseinheiten
    - Availability-Zeitblockierungen
- Zuweisung von Dolmetschern
- Übersicht über ungeplante oder interpreterrelevante Fächer
- Konfliktprüfung bei Zeitüberschneidungen

---

### Dolmetscher

- Zugriff ausschließlich auf den eigenen Einsatzplan
- Übersicht über:
    - Klasse
    - Fach
    - Datum und Uhrzeit
- Keine Planungsänderung möglich

---

### Teilnehmer

- Zugriff ausschließlich auf den eigenen Stundenplan
- Anzeige, ob für eine Unterrichtseinheit ein Dolmetscher eingeplant ist
- Kein Zugriff auf andere Teilnehmer oder Verwaltungsdaten

---

## Datenschutz & Zugriffsbeschränkung

Die Anwendung verwendet ein rollenbasiertes Zugriffskonzept:

- Jeder Benutzer meldet sich über ein eigenes Benutzerkonto an
- Teilnehmer sehen nur ihre Daten
- Dolmetscher sehen nur ihren Einsatzplan
- Administratoren besitzen erweiterte Rechte

---

## Technische Umsetzung

- Programmiersprache: **Java**
- Benutzeroberfläche: **JavaFX**
- Architektur: **MVC (Model-View-Controller)**
- Datenbank: **MySQL**
- Datenbankzugriff: **JDBC**
- Versionsverwaltung: **Git / GitHub**

---

## Projektumfang & Zeitplanung

Gesamtumfang:

**25 Arbeitstage × 7 Stunden = 175 Arbeitsstunden**

Entwicklungsphasen:

- Analyse & Projektvorbereitung
- Fach- und Datenmodell
- Technisches Konzept
- Datenbankimplementierung
- Login- und Rollenlogik
- Implementierung der Planungsfunktionen
- Konfliktprüfung
- Test & Optimierung
- Abschlussdokumentation

---

## Dokumentationsansatz

Das Projekt wird von Beginn an dokumentationsorientiert entwickelt.

Dokumentiert werden:

- Projektbeschreibung
- Datenmodell (ER-Modell)
- Architekturentscheidungen
- Implementierungsschritte
- Testergebnisse
- Projektreflexion

---

## Motivation

Das Projekt basiert auf einer realen organisatorischen Problemlage
im schulischen Umfeld und verfolgt das Ziel,
eine strukturierte, nachvollziehbare und erweiterbare
Planungslösung zu entwickeln.
