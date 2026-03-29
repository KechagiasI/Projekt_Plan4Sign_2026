# Datenmodell – Projekt_DoIT

## Projektfokus

Das System dient der organisatorischen Planung und Zuweisung  
von Dolmetschern zu einzelnen Unterrichtseinheiten.

Es richtet sich ausschließlich an hörgeschädigte Teilnehmer  
mit Unterstützungsbedarf.

Das System enthält ein rollenbasiertes Login-Konzept  
mit unterschiedlichen Sichten für:

- Administrator
- Dolmetscher
- Teilnehmer

---

## Entitäten des Systems

- User
- Bereich
- Fach
- Klasse
- Teilnehmer
- Dolmetscher
- Unterricht
- Availability

---

## User (Login-Ebene)

**Attribute:**

- `UserID` (PK)
- `username` (UNIQUE)
- `passwordHash`
- `role` (ENUM: 'ADMIN', 'DOLMETSCHER', 'TEILNEHMER')

**Beschreibung:**

Speichert die Login-Daten und die Rolle des Benutzers.

Ein User ist genau einer Person zugeordnet:

- entweder einem Teilnehmer
- oder einem Dolmetscher

Die Rolle wird ausschließlich über das `role`-Feld gesteuert.  
Die fachliche Zuordnung erfolgt über eine 1:1-Beziehung  
zwischen `User` und `Teilnehmer` bzw. `User` und `Dolmetscher`.

> **Änderung:** Das ursprüngliche Attribut `isAdmin (BOOLEAN)` wurde durch `role (ENUM)` ersetzt.
> Ein Boolean-Wert konnte die Rolle eines Benutzers nicht eindeutig abbilden –
> insbesondere war nicht erkennbar, ob ein Benutzer Dolmetscher oder Teilnehmer ist.
> Mit dem ENUM-Typ wird die Rolle direkt und eindeutig im User gespeichert.

---

## Bereich

**Attribute:**

- `BereichID` (PK)
- `bereichName`

**Beschreibung:**

Repräsentiert einen fachlichen Bereich  
(z. B. Informatik, Maschinenbau, Fachschule).

Ein Bereich besitzt mehrere Fächer (1:n).

---

## Fach

**Attribute:**

- `FachID` (PK)
- `fachName`
- `isInterpreterRelevant` (BOOLEAN)
- `BereichID` (FK, NOT NULL)

**Beschreibung:**

Ein Fach gehört genau zu einem Bereich.

Das Attribut `isInterpreterRelevant` kennzeichnet,  
ob für dieses Fach grundsätzlich ein Dolmetscher eingeplant werden sollte.

Ein Fach kann in mehreren Unterrichtseinheiten vorkommen (1:n).

---

## Klasse

**Attribute:**

- `KlasseID` (PK)
- `className`
- `room`

**Beschreibung:**

Repräsentiert eine Schulklasse.

Eine Klasse:

- besitzt mehrere Teilnehmer (1:n)
- besitzt mehrere Unterrichtseinheiten (1:n)

---

## Teilnehmer

**Attribute:**

- `TeilnehmerID` (PK)
- `firstName`
- `lastName`
- `email`
- `mobilePhone`
- `comment`
- `KlasseID` (FK, NOT NULL)
- `UserID` (FK, UNIQUE, NOT NULL)

**Beschreibung:**

Repräsentiert ausschließlich hörgeschädigte Teilnehmer  
mit Unterstützungsbedarf.

Ein Teilnehmer:

- gehört genau zu einer Klasse
- besitzt genau ein User-Konto (1:1)
- sieht ausschließlich den Stundenplan seiner eigenen Klasse

---

## Dolmetscher

**Attribute:**

- `DolmetscherID` (PK)
- `firstName`
- `lastName`
- `email`
- `mobilePhone`
- `comment`
- `UserID` (FK, UNIQUE, NOT NULL)

**Beschreibung:**

Repräsentiert Dolmetscher,  
die Unterrichtseinheiten zugewiesen werden können.

Ein Dolmetscher:

- besitzt genau ein User-Konto (1:1)
- kann mehrere Unterrichtseinheiten betreuen (1:n)

> **Änderung:** Der Punkt „kann Administratorrechte besitzen" wurde entfernt.
> Administratorrechte werden nicht mehr separat am Dolmetscher vergeben,
> sondern ausschließlich über das `role`-Feld im User gesteuert.

---

## Unterricht

**Attribute:**

- `UnterrichtID` (PK)
- `date`
- `startTime`
- `endTime`
- `KlasseID` (FK, NOT NULL)
- `FachID` (FK, NOT NULL)
- `DolmetscherID` (FK, NULL erlaubt)

**Beschreibung:**

Repräsentiert eine einzelne Unterrichtseinheit.

Jede Unterrichtseinheit:

- gehört genau zu einer Klasse
- gehört genau zu einem Fach
- kann optional genau einem Dolmetscher zugewiesen werden

Unterricht ohne Dolmetscher ist möglich.

---

## Availability

**Attribute:**

- `AvailabilityID` (PK)
- `availabilityType`
- `date` (optional)
- `startTime` (optional)
- `endTime` (optional)
- `dateFrom` (optional)
- `dateTo` (optional)
- `comment`
- `DolmetscherID` (FK, NOT NULL)

**Beschreibung:**

Speichert Zeitblockierungen eines Dolmetschers.

**Mögliche Typen:**

- Urlaub
- Krankheit
- Besprechung
- Sonstiges

**Regeln:**

- Sind `startTime` und `endTime` NULL → ganztägige Blockierung
- Sind `dateFrom` und `dateTo` gesetzt → Blockierung über Zeitraum
- Ist nur `date` gesetzt → Einzeltermin

Ein Dolmetscher kann mehrere Availability-Einträge besitzen (1:n).

„Die Availability bildet die Grundlage für die spätere Konflikterkennung (z.B. Doppelbelegung und zeitliche Überschneidungen).“

---

## Beziehungen im Überblick

- Ein Bereich besitzt mehrere Fächer (1:n)
- Eine Klasse besitzt mehrere Teilnehmer (1:n)
- Eine Klasse besitzt mehrere Unterrichtseinheiten (1:n)
- Ein Fach kommt in mehreren Unterrichtseinheiten vor (1:n)
- Ein Dolmetscher kann mehrere Unterrichtseinheiten betreuen (1:n)
- Ein Dolmetscher kann mehrere Availability-Einträge besitzen (1:n)
- Ein Teilnehmer besitzt genau einen User (1:1)
- Ein Dolmetscher besitzt genau einen User (1:1)

---

## Glossar

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