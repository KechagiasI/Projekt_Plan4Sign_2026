### Projekt
Projekt_DoIT – Plan4Sign - Stundenplan mit Dolmetscher-Zuweisung

# Dokumentation – Woche 1 

Analyse & Projektvorbereitung (35 h)
---

# Ziel der Woche
## 1. Der ersten Projektwoche war die Schaffung eines fachlichen, technischen und organisatorischen Fundaments für das Gesamtprojekt.

---

## 1.1 Analyse der Problemstellung
- Analyse der aktuellen organisatorischen Situation im schulischen Umfeld
- Untersuchung der bestehenden Probleme bei der Planung von Gebärdensprachdolmetschungen
- Identifikation zentraler Schwachstellen:
    - fehlende Übersicht über Einsatzzeiten
    - manuelle und fehleranfällige Planung
    - unzureichende oder verspätete Informationsweitergabe
    - mangelnde Datenschutztrennung zwischen Beteiligten
- Ableitung des konkreten Bedarfs für eine softwaregestützte Lösung

---

## 1.2 Definition der Projektziele
- Festlegung des Hauptziels:
    - Entwicklung einer Desktop-Anwendung zur strukturierten Planung und Organisation von Dolmetsch-Einsätzen
- Definition funktionaler Ziele:
    - zentrale Verwaltung durch einen Administrator
    - individuelle Einsatzpläne für Dolmetscher*innen
    - gezielte Information gehörloser Schüler*innen
- Definition nicht-funktionaler Ziele:
    - Datenschutz
    - Übersichtlichkeit
    - Wartbarkeit und Erweiterbarkeit

---

## 1.3 Zielgruppenanalyse
- Definition der beteiligten Nutzergruppen:
    - **Administrator**: Planung, Koordination und Verwaltung
    - **Pädagogische Fachkraft**: Einsicht in den eigenen Einsatzplan
    - **Teilnehmer*innen**: Information über eigene Unterrichtsstunden
- Festlegung der jeweiligen Zugriffsrechte (rollenbasierter Zugriff)

---

## 1.4 Projektabgrenzung
- Festlegung klarer Grenzen zur Einhaltung des Zeitrahmens:
    - keine Web-Anwendung
    - kein externes Authentifizierungsverfahren (z. B. OAuth, LDAP)
      Es wird ein eigenes Login-System mit rollenbasierter Zugriffskontrolle implementiert.
    - Fokus auf Desktop-Anwendung mit JavaFX
- Dokumentation der bewussten Abgrenzung zur Reduzierung der Projektkomplexität

---

## 1.5 Erstellung der Projektbeschreibung
- Ausarbeitung einer vollständigen Projektbeschreibung mit:
    - Projekttitel
    - Problemstellung
    - Zielsetzung
    - Zielgruppen
    - Nutzen des Projekts
- Ablage der Projektbeschreibung:
    - im `README.md`
    - im Ordner `/documentation/projektbeschreibung.md`

---

## 1.6 Einrichtung der Entwicklungsumgebung
- Installation von **Java JDK 21 (LTS)**
- Erstellung eines neuen Java-Projekts in **IntelliJ IDEA**
- Auswahl von **Maven** als Build-System
- Überprüfung der Projektstruktur (`src`, `pom.xml`)
- Sicherstellung einer fehlerfreien Projektkonfiguration

---

## 1.7 Einrichtung der Versionsverwaltung
- Initialisierung eines lokalen Git-Repositories
- Erstellung einer `.gitignore` zur Ausblendung nicht relevanter Dateien
- Durchführung des ersten Commits (Checkpoint 1)
- Erstellung eines privaten GitHub-Repositories
- Verknüpfung des lokalen Projekts mit dem Remote-Repository
- Push des initialen Projektstands zu GitHub

---

## 1.8 Aufbau der Dokumentationsstruktur
- Erstellung des Ordners `/documentation`
- Anlegen folgender Dokumentationsdateien:
    - `projektbeschreibung.md`
    - `projektplanung.md`
    - `datenmodell.md`
    - `entscheidungen.md`
- Strukturierte Ablage der bisherigen Projektergebnisse
- Versionierung der Dokumentation (Checkpoint 2)

---

## 1.9 Ergebnis der Woche 1
- Vollständig analysierte Problemstellung
- Klar definierte Projektziele und Zielgruppen
- Abgegrenzter Projektumfang
- Funktionsfähige Entwicklungsumgebung
- Versionskontrolliertes Projekt mit privatem GitHub-Repository
- Strukturierte und archivierte Projektdokumentation

---

## Status
**Woche 1: abgeschlossen ✅**

---

# Dokumentation – Woche 2

# Ziel der Woche

## 2. Fachkonzept & Datenmodell

### 2.1 Fachliches Konzept

Das System dient der organisatorischen Planung und Zuweisung
von Dolmetschern zu einzelnen Unterrichtseinheiten.

Es richtet sich ausschließlich an hörgeschädigte Teilnehmer
mit Unterstützungsbedarf und stellt keine vollständige
Schulverwaltungssoftware dar.

Ziel ist:

- transparente Einsatzplanung der Dolmetscher
- Vermeidung von Doppelbelegungen
- Berücksichtigung von Zeitblockierungen (Availability)
- klare Rollen- und Zugriffstrennung über ein Login-System

Das System unterscheidet folgende Rollen:

- Administrator
- Dolmetscher
- Teilnehmer

Jeder Benutzer meldet sich über ein persönliches Benutzerkonto an.
Die angezeigte Sicht richtet sich nach der zugeordneten Rolle.

---

### 2.2 ER-Modell

Das Entity-Relationship-Modell bildet die fachliche Struktur
des Systems ab und dient als Grundlage für das relationale Datenmodell.

Der Fokus liegt auf der Planung von Unterrichtseinheiten
und der Zuweisung von Dolmetschern unter Berücksichtigung
von Fachbereichen und Verfügbarkeiten.

---

### Entitäten

---

#### User

Speichert Login-Daten und Rolleninformationen.

Attribute:

- UserID (PK)
- username (UNIQUE)
- passwordHash
- role (ENUM: 'ADMIN', 'DOLMETSCHER', 'TEILNEHMER')

> **Änderung:** Das ursprüngliche Attribut `isAdmin (BOOLEAN)` wurde durch `role (ENUM)` ersetzt.
> Ein Boolean-Wert konnte die Rolle eines Benutzers nicht eindeutig abbilden –
> insbesondere war nicht erkennbar, ob ein Benutzer Dolmetscher oder Teilnehmer ist.
> Mit dem ENUM-Typ wird die Rolle direkt und eindeutig im User gespeichert.

Ein User ist entweder einem Teilnehmer
oder einem Dolmetscher eindeutig zugeordnet (1:1).

---

#### Bereich

Repräsentiert einen fachlichen Bereich
(z. B. Informatik, Maschinenbau, Fachschule).

Attribute:

- BereichID (PK)
- bereichName

Ein Bereich besitzt mehrere Fächer (1:n).

---

#### Fach

Repräsentiert ein konkretes Unterrichtsfach.

Attribute:

- FachID (PK)
- fachName
- isInterpreterRelevant (BOOLEAN)
- BereichID (FK, NOT NULL)

Das Attribut `isInterpreterRelevant` kennzeichnet,
ob für dieses Fach grundsätzlich ein Dolmetscher eingeplant werden sollte.

Ein Fach kann in mehreren Unterrichtseinheiten vorkommen (1:n).

---

#### Klasse

Repräsentiert eine Schulklasse.

Attribute:

- KlasseID (PK)
- className
- room

Eine Klasse:

- besitzt mehrere Teilnehmer (1:n)
- besitzt mehrere Unterrichtseinheiten (1:n)

---

#### Teilnehmer

Repräsentiert ausschließlich hörgeschädigte Teilnehmer
mit Unterstützungsbedarf.

Attribute:

- TeilnehmerID (PK)
- firstName
- lastName
- email
- mobilePhone
- comment
- KlasseID (FK, NOT NULL)
- UserID (FK, UNIQUE, NOT NULL)

Ein Teilnehmer:

- gehört genau zu einer Klasse
- besitzt genau ein User-Konto (1:1)
- sieht ausschließlich den Stundenplan seiner eigenen Klasse

---

#### Dolmetscher

Repräsentiert Dolmetscher,
die Unterrichtseinheiten zugewiesen werden können.

Attribute:

- DolmetscherID (PK)
- firstName
- lastName
- email
- mobilePhone
- comment
- UserID (FK, UNIQUE, NOT NULL)

Ein Dolmetscher:

- besitzt genau ein User-Konto (1:1)
- kann mehrere Unterrichtseinheiten betreuen (1:n)

> **Änderung:** Der Punkt „kann Administratorrechte besitzen" wurde entfernt.
> Administratorrechte werden nicht mehr separat am Dolmetscher vergeben,
> sondern ausschließlich über das `role`-Feld im User gesteuert.

---

#### Unterricht

Repräsentiert eine einzelne Unterrichtseinheit.

Attribute:

- UnterrichtID (PK)
- date
- startTime
- endTime
- KlasseID (FK, NOT NULL)
- FachID (FK, NOT NULL)
- DolmetscherID (FK, NULL erlaubt)

Jede Unterrichtseinheit:

- gehört genau zu einer Klasse
- gehört genau zu einem Fach
- kann optional genau einem Dolmetscher zugewiesen werden

Unterricht ohne Dolmetscher ist möglich.

> **Änderung:** Die Löschregel für `DolmetscherID` wurde von `ON DELETE RESTRICT`
> auf `ON DELETE SET NULL` geändert.
> Wird ein Dolmetscher gelöscht, bleibt die Unterrichtseinheit erhalten –
> der Dolmetscher wird lediglich auf NULL gesetzt.
> So gehen keine Unterrichtsdaten verloren.

---

#### Availability

Speichert Zeitblockierungen eines Dolmetschers.

Attribute:

- AvailabilityID (PK)
- availabilityType
- date (optional)
- startTime (optional)
- endTime (optional)
- dateFrom (optional)
- dateTo (optional)
- comment
- DolmetscherID (FK, NOT NULL)

Mögliche Typen:

- Urlaub
- Krankheit
- Besprechung
- Sonstiges

Regeln:

- Sind startTime und endTime NULL → ganztägige Blockierung
- Sind dateFrom und dateTo gesetzt → Blockierung über Zeitraum
- Ist nur date gesetzt → Einzeltermin

Ein Dolmetscher kann mehrere Availability-Einträge besitzen (1:n).

> **Änderung:** Die Löschregel für `DolmetscherID` wurde von `ON DELETE RESTRICT`
> auf `ON DELETE CASCADE` geändert.
> Wird ein Dolmetscher gelöscht, werden seine Availability-Einträge
> automatisch mitgelöscht, da sie ohne den zugehörigen Dolmetscher
> keine fachliche Bedeutung mehr haben.

---

### 2.3 Beziehungen

- Ein Bereich besitzt mehrere Fächer (1:n)
- Eine Klasse besitzt mehrere Teilnehmer (1:n)
- Eine Klasse besitzt mehrere Unterrichtseinheiten (1:n)
- Ein Fach kommt in mehreren Unterrichtseinheiten vor (1:n)
- Ein Dolmetscher kann mehrere Unterrichtseinheiten betreuen (1:n)
- Ein Dolmetscher kann mehrere Availability-Einträge besitzen (1:n)
- Ein Teilnehmer besitzt genau einen User (1:1)
- Ein Dolmetscher besitzt genau einen User (1:1)

---

### 2.4 Glossar

**User**  
Speichert Login-Daten und Rolleninformationen.

**Bereich**  
Fachlicher Themenbereich.

**Fach**  
Konkretes Unterrichtsfach mit Interpreter-Relevanz.

**Klasse**  
Verbindet Teilnehmer und Unterrichtseinheiten.

**Teilnehmer**  
Hörgeschädigte Teilnehmer mit Unterstützungsbedarf.

**Dolmetscher**  
Personen, die Unterrichtseinheiten zugewiesen bekommen können.

**Unterricht**  
Konkrete Unterrichtseinheit mit Datum und Uhrzeit.

**Availability**  
Zeitblockierungen eines Dolmetschers.

---

## Status
**Woche 2: abgeschlossen ✅**

---


# Dokumentation – Woche 3

## Ziel der Woche


## 3. Technisches Konzept

### 3.1 Architekturentscheidung

Für die Umsetzung der Anwendung **„DoIT – Planungs- und Organisationssoftware“** wurde die  
**Model-View-Controller (MVC)-Architektur** gewählt.

#### Begründung

Die Anwendung verarbeitet folgende fachliche Entitäten:

- User
- Bereich
- Fach
- Klasse
- Teilnehmer
- Dolmetscher
- Unterricht
- Availability

Aufgrund der klaren Struktur, des rollenbasierten Login-Systems und der notwendigen Trennung von Präsentation, Logik und Datenzugriff wurde MVC als geeignete Architektur gewählt.

Die MVC-Architektur bietet:

- strukturierte Trennung der Anwendungsschichten
- Wartbarkeit und Erweiterbarkeit
- klare Rollen- und Zugriffstrennung
- bessere Testbarkeit
- IHK-konforme Projektstruktur

---
