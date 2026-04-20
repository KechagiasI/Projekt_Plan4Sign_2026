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
**Woche 1: abgeschlossen **

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
**Woche 2: abgeschlossen **

---

# Dokumentation – Woche 3

## Ziel der Woche

Abschluss der JDBC-Anbindung und Implementierung der DAO-Klassen sowie Stabilisierung der Modellklassen.

## 3. Technisches Konzept

### 3.1 Architekturentscheidung

Für die Umsetzung der Anwendung **„DoIT – Planungs- und Organisationssoftware"** wurde die
**Model-View-Controller (MVC)-Architektur** gewählt.

#### Begründung

Aufgrund der klaren Struktur, des rollenbasierten Login-Systems und der notwendigen Trennung von Präsentation, Logik und Datenzugriff wurde MVC als geeignete Architektur gewählt.

Die MVC-Architektur bietet:

- strukturierte Trennung der Anwendungsschichten
- Wartbarkeit und Erweiterbarkeit
- klare Rollen- und Zugriffstrennung
- bessere Testbarkeit
- IHK-konforme Projektstruktur

---

### 3.2 Package-Struktur

Die Anwendung ist in folgende Packages unterteilt:

```
src/main/java/com/brh/
│
├── model/       ← Entity-Klassen (User, Dolmetscher, usw.)
├── view/        ← FXML-Dateien (JavaFX-Oberfläche)
├── controller/  ← Anwendungslogik
├── dao/         ← Datenbankzugriff (JDBC)
└── util/        ← Hilfsmittel (z. B. DatabaseConnection)
```

Die Aufteilung entspricht der MVC-Architektur und sorgt für eine klare Trennung der Verantwortlichkeiten zwischen Datenstruktur, Datenbankzugriff, Anwendungslogik und Benutzeroberfläche.

---

### 3.3 Entity-Klassen

Für jede Entität des ER-Modells wurde eine entsprechende Java-Klasse im Package `model` erstellt.

#### Role.java

Enum-Klasse zur Abbildung der Benutzerrollen.
Entspricht dem `ENUM`-Typ in der Datenbanktabelle `User`.

```
Werte: ADMIN, DOLMETSCHER, TEILNEHMER
```

> **Entscheidung:** Anstelle eines `String`-Feldes wurde ein Java `enum` verwendet,
> da dieser direkt dem MySQL ENUM entspricht und ungültige Werte zur Kompilierzeit verhindert.

---

#### User.java

Attribute: `int userID`, `String username`, `String passwordHash`, `Role role`

---

#### Bereich.java

Attribute: `int bereichID`, `String bereichName`

---

#### Fach.java

Attribute: `int fachID`, `String fachName`, `boolean isInterpreterRelevant`, `int bereichID`

> **Entscheidung:** `bereichID` wird als `int` gespeichert (Foreign Key), nicht als `Bereich`-Objekt,
> da die Daten über JDBC geladen werden und auf Objekt-Referenzen verzichtet wird.

---

#### Klasse.java

Attribute: `int klasseID`, `String klasseName`, `String room`

---

#### Teilnehmer.java

Attribute: `int teilnehmerID`, `String firstName`, `String lastName`, `String email`,
`String mobilePhone`, `String comment`, `int klasseID`, `int userID`

---

#### Dolmetscher.java

Attribute: `int dolmetscherID`, `String firstName`, `String lastName`, `String email`,
`String mobilePhone`, `String comment`, `int userID`

---

#### Unterricht.java

Attribute: `int unterrichtID`, `LocalDate date`, `LocalTime startTime`, `LocalTime endTime`,
`int klasseID`, `int fachID`, `Integer dolmetscherID`

> **Entscheidung:** `dolmetscherID` ist vom Typ `Integer`, da der Wert `NULL` sein kann,
> wenn kein Dolmetscher zugewiesen ist.

> **Entscheidung:** `LocalDate` und `LocalTime` wurden anstelle von `java.sql.Date`
> und `java.sql.Time` verwendet, da diese modernen Typen seit Java 8 empfohlen werden.

---

#### Availability.java

Attribute: `int availabilityID`, `String availabilityType`, `LocalDate date`,
`LocalTime startTime`, `LocalTime endTime`, `LocalDate dateFrom`, `LocalDate dateTo`,
`String comment`, `int dolmetscherID`

> **Entscheidung:** Alle optionalen Datums- und Zeitfelder können `null` sein,
> entsprechend der Datenbankdefinition.

---

### 3.4 JDBC-Datenbankanbindung

Für die Verbindung zwischen Java und der MySQL-Datenbank wurde JDBC verwendet.

#### MySQL Connector/J

```XML
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

#### DatabaseConnection.java

Die Klasse `DatabaseConnection` im Package `util` stellt die Verbindung zur Datenbank her
und setzt das Singleton-Prinzip um: Es existiert zu jedem Zeitpunkt nur eine einzige Datenbankverbindung.

Verbindungsparameter:

- URL: `jdbc:mysql://localhost:3324/projekt_doit`
- Port: 3324 (MySQL läuft unter WSL)
- Datenbank: `projekt_doit`

---

### 3.5 DAO-Klassen

Für jede Entität wurde eine DAO-Klasse (Data Access Object) im Package `dao` erstellt.

> **Entscheidung:** `PreparedStatement` wurde verwendet, wo Parameter übergeben werden,
> um SQL-Injection zu verhindern. `NULL`-Werte werden über `getObject()` geprüft,
> um `NullPointerExceptions` zu vermeiden.

#### Basis-CRUD-Methoden

| DAO-Klasse      | Methoden                                          |
|-----------------|---------------------------------------------------|
| BereichDAO      | getAll(), insert(), delete()                      |
| KlasseDAO       | getAll(), insert(), delete()                      |
| FachDAO         | getAll(), getByBereich(), insert(), delete()      |
| UserDAO         | getAll(), getByUsername(), insert(), delete()     |
| DolmetscherDAO  | getAll(), getByID(), insert(), delete()           |
| TeilnehmerDAO   | getAll(), getByKlasse(), insert(), delete()       |
| UnterrichtDAO   | getAll(), insert(), assignDolmetscher(), delete() |
| AvailabilityDAO | insert(), delete()                                |

#### Erweiterte Methoden (JOINs, Filter, Logik)

| DAO-Klasse      | Methoden                                                                                       |
|-----------------|------------------------------------------------------------------------------------------------|
| DolmetscherDAO  | getByUserID()                                                                                  |
| TeilnehmerDAO   | getByUserID()                                                                                  |
| UnterrichtDAO   | getAllWithDetails(), getByDolmetscher(), getByKlasse(), getWithDetailsByDolmetscher(), getWithDetailsByKlasse() |
| AvailabilityDAO | getByDolmetscher(), isAvailable()                                                              |

---

### 3.6 Versionsverwaltung

Das Projekt wurde mit einem privaten GitHub-Repository verknüpft.

- Remote-Repository: `Projekt_Plan4Sign_2026`
- Verbindung über IntelliJ IDEA (Git → Manage Remotes)

Commits dieser Woche:

- Initial commit - Add model entities
- Add DatabaseConnection and DatabaseConnectionTest
- Add BereichDAO with `getAll, insert, delete`
- Fix model classes: `Fach, Unterricht, Availability - Add DAO classes`

---

#### Referenzen
1. `https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.33`
2. `https://stackoverflow.com/questions/2839321/connect-java-to-a-mysql-database`
3. `https://stackoverflow.com/questions/74183544/get-connection-with-singleton-pattern`
4. `https://github.com/mysql/mysql-connector-j`
5. `https://dev.mysql.com/doc/connector-j/en/`

---

### Status
#### Woche 3: abgeschlossen 

---

# Dokumentation – Woche 4

## 4. Woche 4 – Login-System, Rollennavigation & JavaFX-Oberfläche

## Ziel der Woche

Implementierung des Login-Systems mit sicherer Passwortverschlüsselung,
rollenbasierter Navigation und der rollenspezifischen JavaFX-Oberflächen.

---

### 4.1 Projektkonfiguration

#### 4.1.1 pom.xml – Anpassungen

**BCrypt-Dependency hinzugefügt:**
```xml
<dependency>
    <groupId>org.mindrot</groupId>
    <artifactId>jbcrypt</artifactId>
    <version>0.4</version>
</dependency>
```

> **Begründung:** Passwörter werden mit BCrypt gehasht, da der Algorithmus
> speziell für Passwort-Hashing entwickelt wurde und automatisch einen Salt generiert.

**Weitere Anpassungen:**

- JavaFX-Version als zentrale Property (`21.0.6`)
- Java-Compiler-Version auf 21 korrigiert (vorher: 23)
- `mainClass` auf `Launcher` aktualisiert (ersetzt das IntelliJ-Template `HelloApplication`)

---

#### 4.1.2 module-info.java – Anpassungen

```java
module com.brh.projekt_plan4sign_2026 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;

    opens com.brh.projekt_plan4sign_2026 to javafx.fxml;
    opens com.brh.projekt_plan4sign_2026.controller to javafx.fxml;
    opens com.brh.projekt_plan4sign_2026.util to javafx.fxml;

    exports com.brh.projekt_plan4sign_2026;
    exports com.brh.projekt_plan4sign_2026.controller;
    exports com.brh.projekt_plan4sign_2026.util;
}
```

> **Begründung `requires java.sql`:** Der MySQL JDBC-Treiber wird über den
> ServiceLoader-Mechanismus automatisch geladen – ein explizites `requires mysql...`
> ist nicht notwendig.

> **Entscheidung:** Der MySQL Connector wurde bewusst auf Version `8.0.33` belassen,
> da die bestehende Version funktionsfähig war und eine unnötige Änderung vermieden werden sollte.

---

#### 4.1.3 PasswordUtil.java – Passwort-Hashing mit BCrypt

```java
public static String hash(String plainPassword) {
    return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
}

public static boolean verify(String plainPassword, String hashedPassword) {
    return BCrypt.checkpw(plainPassword, hashedPassword);
}
```

> **Entscheidung:** Cost Factor 12 wurde gewählt, da er ein ausgewogenes
> Verhältnis zwischen Sicherheit und Berechnungszeit bietet.

---

#### 4.1.4 UserDAO.java – Überarbeitung

Die bestehende Implementierung von `UserDAO` wurde um folgende Punkte ergänzt:

- **try-with-resources**: Datenbankressourcen werden automatisch geschlossen
- **Optional\<User\>**: `getByUsername()` gibt `Optional` zurück, um explizite Null-Behandlung zu erzwingen
- **Passwort-Hashing in insert()**: Passwörter werden zentral in der DAO-Schicht gehasht

---

#### 4.2 Login-System

##### 4.2.1 Übersicht

| Datei                  | Package            | Aufgabe                               |
|------------------------|--------------------|---------------------------------------|
| `LoginView.fxml`       | resources/.../view | Benutzeroberfläche des Login-Fensters |
| `LoginController.java` | controller         | Verarbeitung der Login-Eingaben       |
| `App.java`             | root               | JavaFX-Einstiegspunkt, lädt LoginView |
| `Launcher.java`        | root               | Startet die JavaFX-Anwendung          |

---

##### 4.2.2 Ablauf des Login-Vorgangs

1. Eingaben aus den FXML-Feldern lesen
2. Prüfen ob Felder leer sind
3. Benutzer über `UserDAO.getByUsername()` in der DB suchen
4. Passwort mit `PasswordUtil.verify()` prüfen
5. Bei Erfolg → Navigation zur rollenbasierten Ansicht
6. Bei Fehler → Fehlermeldung im `errorLabel`

**Rollenbasierte Navigation:**

```java
String fxml = switch (user.getRole()) {
    case ADMIN       -> ".../AdminView.fxml";
    case TEILNEHMER  -> ".../TeilnehmerView.fxml";
    case DOLMETSCHER -> ".../DolmetscherView.fxml";
};
```

> **Entscheidung:** `switch` mit Enum-Pattern prüft alle Rollen zur Kompilierzeit –
> fehlende Fälle führen zu einem Compiler-Fehler.

---

##### 4.2.3 Erweiterung der Unterricht-Entität

Die `Unterricht`-Klasse wurde um Anzeige-Attribute erweitert, da für das UI
lesbare Namen (z. B. Klassenname) statt Fremdschlüssel benötigt werden:

```java
private String klassename;
private String fachname;
private String dolmetschername;
private String teilnehmername;
```

> **Begründung:** Die Daten werden bereits im DAO über JOINs vorbereitet.
> Der Controller bleibt dadurch von Datenaufbereitungslogik frei (MVC-Prinzip).

---

##### 4.2.4 Datenzugriff und Darstellung (AdminView)

Die Daten werden über `getAllWithDetails()` geladen – eine SQL-Abfrage mit JOINs
über die Tabellen `Unterricht`, `Klasse`, `Fach` und optional `Dolmetscher` und `Teilnehmer`,
um alle relevanten Anzeigedaten in einer einzigen Abfrage bereitzustellen.
Zusätzlich wird der Teilnehmername (Vorname + Nachname) über einen JOIN mit der Teilnehmer-Tabelle
geladen, um eine vollständige Anzeige im UI zu ermöglichen.

Die Spalten des `TableView` werden direkt mit den Gettern des `Unterricht`-Modells verknüpft.
Nach jeder Zuweisung wird `loadData()` erneut aufgerufen, um die Ansicht
mit dem aktuellen Datenbankstand zu synchronisieren.

> **Begründung:** Die Aufbereitung der Daten erfolgt vollständig im DAO.
> Der Controller ist ausschließlich für die Darstellung zuständig.

---

#### 4.3 Verfügbarkeitsprüfung bei der Dolmetscher-Zuweisung

Bevor ein Dolmetscher einer Unterrichtseinheit zugewiesen wird,
prüft der `AdminController` dessen Verfügbarkeit über `AvailabilityDAO.isAvailable()`.

**Ablauf:**

1. Administrator wählt Unterrichtseinheit und Dolmetscher aus
2. System prüft Verfügbarkeit für den gewählten Zeitraum
3. Nicht verfügbar → Warnung wird angezeigt, Zuweisung wird abgebrochen
4. Verfügbar → Zuweisung wird gespeichert, TableView wird aktualisiert

> **Begründung:** Die Prüfung schützt vor Doppelbelegungen und stellt sicher,
> dass keine Zuweisung für einen blockierten Zeitraum erfolgt.

---

#### 4.4 Dolmetscher-Ansicht (DolmetscherView)

##### 4.4.1 Übersicht

| Datei                        | Package            | Aufgabe                                         |
|------------------------------|--------------------|-------------------------------------------------|
| `DolmetscherView.fxml`       | resources/.../view | Benutzeroberfläche des Dolmetscher-Einsatzplans |
| `DolmetscherController.java` | controller         | Lädt und zeigt die eigenen Unterrichtseinheiten |

---

##### 4.4.2 Dependency Injection – setDolmetscher()

Da der `DolmetscherController` wissen muss, welcher Dolmetscher eingeloggt ist,
wird das `Dolmetscher`-Objekt nach dem Laden der View vom `LoginController` übergeben.

```java
public void setDolmetscher(Dolmetscher dolmetscher) {
    this.dolmetscher = dolmetscher;
    labelWillkommen.setText("Willkommen, " + dolmetscher.getFirstname() + " " + dolmetscher.getLastname());
    loadData();
}
```

> **Entscheidung:** `loadData()` wird bewusst nicht in `initialize()` aufgerufen,
> da das `Dolmetscher`-Objekt zu diesem Zeitpunkt noch nicht übergeben wurde.
> Die Datenbankabfrage erfolgt erst nach der Übergabe durch den `LoginController`.

**Ablauf der Datenübergabe:**

```
LoginController → loader.getController() → setDolmetscher(dolmetscher) → loadData()
```

Die Daten werden über `getWithDetailsByDolmetscher()` geladen –
eine SQL-Abfrage mit JOINs, gefiltert nach `DolmetscherID`.

**Erweiterte DAO-Methoden für diese Ansicht:**

- `DolmetscherDAO.getByUserID()` – ermittelt den Dolmetscher anhand der eingeloggten `UserID`
- `UnterrichtDAO.getWithDetailsByDolmetscher()` – lädt die zugewiesenen Unterrichtseinheiten mit JOIN-Details

---

##### 4.4.3 DolmetscherView.fxml – Angezeigte Spalten

| Spalte      | Inhalt                        |
|-------------|-------------------------------|
| Datum       | Datum der Unterrichtseinheit  |
| Start       | Startzeit                     |
| Ende        | Endzeit                       |
| Klassenname | Name der Klasse               |
| Fachname    | Name des Fachs                |
| Teilnehmer  | Name des Teilnehmers          |

---

#### 4.5 Teilnehmer-Ansicht (TeilnehmerView)

##### 4.5.1 Übersicht

| Datei                       | Package            | Aufgabe                                        |
|-----------------------------|--------------------|-------------------------------------------------|
| `TeilnehmerView.fxml`       | resources/.../view | Benutzeroberfläche des Teilnehmer-Stundenplans |
| `TeilnehmerController.java` | controller         | Lädt und zeigt den Stundenplan der eigenen Klasse |

---

##### 4.5.2 Dependency Injection – setTeilnehmer()

Dieselbe Vorgehensweise wie beim `DolmetscherController` wird angewendet:
Das `Teilnehmer`-Objekt wird nach dem Laden der View vom `LoginController` übergeben,
woraufhin `loadData()` mit der `klasseID` des Teilnehmers aufgerufen wird.

Die Daten werden über `getWithDetailsByKlasse()` geladen –
eine SQL-Abfrage mit JOINs, gefiltert nach `KlasseID`.

**Erweiterte DAO-Methoden für diese Ansicht:**

- `TeilnehmerDAO.getByUserID()` – ermittelt den Teilnehmer anhand der eingeloggten `UserID`
- `UnterrichtDAO.getWithDetailsByKlasse()` – lädt den Stundenplan der Klasse mit JOIN-Details

---

##### 4.5.3 TeilnehmerView.fxml – Angezeigte Spalten

| Spalte      | Inhalt                             |
|-------------|------------------------------------|
| Datum       | Datum der Unterrichtseinheit       |
| Start       | Startzeit                          |
| Ende        | Endzeit                            |
| Fachname    | Name des Fachs                     |
| Dolmetscher | Name des zugewiesenen Dolmetschers |

---

#### 4.6 Erweiterung des LoginController – Datenübergabe an Controller

```java
if (user.getRole() == Role.DOLMETSCHER) {
    Dolmetscher dolmetscher = new DolmetscherDAO().getByUserID(user.getUserID());
    DolmetscherController controller = loader.getController();
    controller.setDolmetscher(dolmetscher);
}

if (user.getRole() == Role.TEILNEHMER) {
    Teilnehmer teilnehmer = new TeilnehmerDAO().getByUserID(user.getUserID());
    TeilnehmerController controller = loader.getController();
    controller.setTeilnehmer(teilnehmer);
}
```

> **Begründung:** Über `loader.getController()` wird die Referenz auf den geladenen Controller
> abgerufen und das jeweilige Objekt übergeben, bevor die View angezeigt wird
> (Dependency Injection Pattern).

---

#### 4.7 Git Commits – Woche 4

```
Implement DolmetscherView and DolmetscherController with login navigation
Implement TeilnehmerView and TeilnehmerController with login navigation
```

---

### Status
#### Woche 4: abgeschlossen 

# Dokumentation – Woche 5

## 5. Woche 5 – UI-Erweiterungen, Konfliktprüfung & Abschluss

## Ziel der Woche

Qualitätssicherung, Erweiterung der Benutzeroberfläche und Abschluss der Anwendung.
 
---

### 5.1 Abmelden-Funktion (Logout)

In allen drei rollenspezifischen Views wurde ein „Abmelden"-Button ergänzt,
der den Benutzer zur Login-Ansicht zurückleitet.

Die Methode `handleLogout()` wurde in `AdminController`, `DolmetscherController`
und `TeilnehmerController` identisch implementiert:

```java
@FXML
private void handleLogout() {
    try {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/com/brh/projekt_plan4sign_2026/view/LoginView.fxml")
        );
        Stage stage = (Stage) tableUnterricht.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

> **Begründung:** Ohne Abmelde-Funktion musste die Anwendung vollständig beendet
> und neu gestartet werden, um einen anderen Benutzer anzumelden.
> Die Funktion verbessert die Benutzerführung erheblich.
 
---

### 5.2 Konfliktprüfung – Zeitüberschneidung bei Dolmetscher-Zuweisung

#### 5.2.1 Neue Methode: `UnterrichtDAO.hasConflict()`

Vor der Zuweisung eines Dolmetschers wird geprüft, ob dieser zur gleichen Zeit
bereits einer anderen Unterrichtseinheit zugewiesen ist.

```java
public boolean hasConflict(int dolmetscherID, int unterrichtID,
                            LocalDate date,
                            LocalTime startTime,
                            LocalTime endTime) throws SQLException
```

Die SQL-Abfrage sucht nach Unterrichtseinheiten desselben Dolmetschers
am gleichen Datum, deren Zeitfenster sich mit dem gewählten überschneidet —
ausgenommen die aktuell gewählte Unterrichtseinheit selbst:

```sql
SELECT COUNT(*) FROM Unterricht
WHERE DolmetscherID = ?
AND UnterrichtID != ?
AND date = ?
AND starttime < ?
AND endtime > ?
```

> **Begründung:** Die Filterung erfolgt direkt in der Datenbank,
> um eine effiziente Konfliktprüfung ohne clientseitiges Filtern zu ermöglichen.
 
---

#### 5.2.2 Ablauf der Konfliktprüfung im AdminController

Der `AdminController` führt bei jeder Zuweisung zwei Prüfungen durch:

1. **Availability-Prüfung** — ist der Dolmetscher laut Availability-Tabelle verfügbar?
2. **Konfliktprüfung** — hat der Dolmetscher zur gleichen Zeit bereits einen anderen Unterricht?

Bei einem Konflikt wird ein Bestätigungsdialog angezeigt:

```java
if (konflikt) {
    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    confirm.setTitle("Konflikt erkannt");
    confirm.setHeaderText("Dolmetscher bereits eingeplant");
    confirm.setContentText(
        "Der Dolmetscher " + fullname + " hat zu diesem Zeitpunkt " +
        "bereits eine andere Unterrichtseinheit.\n\nTrotzdem zuweisen?"
    );
    Optional<ButtonType> result = confirm.showAndWait();
    if (result.isEmpty() || result.get() != ButtonType.OK) return;
}
```

> **Begründung:** Der Administrator behält die Entscheidungshoheit.
> Eine Warnung wird angezeigt, die Zuweisung kann jedoch bei Bedarf
> trotzdem durchgeführt werden.
 
---

### 5.3 Entfernen eines Dolmetschers (Zuweisung aufheben)

#### 5.3.1 Neue Methode: `UnterrichtDAO.removeDolmetscher()`

```java
public void removeDolmetscher(int unterrichtID) throws SQLException {
    String sql = "UPDATE Unterricht SET DolmetscherID = NULL WHERE UnterrichtID = ?";
    ...
}
```

> **Begründung:** `ON DELETE SET NULL` ist bereits in der Datenbankstruktur definiert.
> Die Methode setzt `DolmetscherID` auf NULL, ohne die Unterrichtseinheit zu löschen.

#### 5.3.2 Ablauf im AdminController

```java
@FXML
private void handleRemove() {
    // 1. Prüfen ob Unterricht ausgewählt
    // 2. Prüfen ob Dolmetscher zugewiesen
    // 3. Bestätigung einholen
    // 4. removeDolmetscher() aufrufen
    // 5. loadData() → UI aktualisieren
}
```
 
---

### 5.4 UI-Verbesserungen

#### 5.4.1 Responsive Layout

In allen Views wurde `VBox.vgrow="ALWAYS"` am `TableView` gesetzt,
sowie `setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS)`
in den jeweiligen `initialize()`-Methoden.

> **Begründung:** Die Spalten verteilen sich nun gleichmäßig auf die verfügbare Breite
> und passen sich bei Größenänderung des Fensters automatisch an.
 
---

#### 5.4.2 Abstände (Padding)

In allen Views wurden einheitliche Abstände gesetzt:

```xml
<padding>
    <Insets top="20" right="30" bottom="20" left="30"/>
</padding>
```

> **Begründung:** Einheitliche Abstände sorgen für ein professionelles
> und übersichtliches Erscheinungsbild.
 
---

#### 5.4.3 Logo in der Login-Ansicht

Das Projekt-Logo (`Plan4Sign_Logo.png`) wurde in die `LoginView` eingebunden.
Der Titel-Label wurde durch das Logo ersetzt.

```xml
<ImageView fitHeight="200.0" fitWidth="200.0" preserveRatio="true">
    <image>
        <Image url="@Plan4Sign_Logo.png"/>
    </image>
</ImageView>
```

> **Begründung:** Das Logo erhöht den Wiedererkennungswert der Anwendung
> und ersetzt den einfachen Texttitel durch eine professionelle visuelle Darstellung.
 
---

#### 5.4.4 Zentrierung der Login-Ansicht

Die `LoginView` wurde in ein `StackPane` eingebettet, damit der Inhalt
bei jeder Fenstergröße zentriert bleibt:

```xml
<StackPane>
    <VBox alignment="CENTER" fillWidth="false" ...>
        ...
    </VBox>
</StackPane>
```

> **Begründung:** Das `StackPane` füllt immer das gesamte Fenster und
> zentriert den VBox-Inhalt automatisch — unabhängig von der Fenstergröße.
 
---

#### 5.4.5 Datenbankstruktur – UNIQUE Constraint für Fach

Um doppelte Einträge desselben Fachs im gleichen Bereich zu verhindern,
wurde ein `UNIQUE`-Constraint in der Tabelle `Fach` ergänzt:

```sql
UNIQUE (fachname, BereichID)
```

> **Begründung:** Ohne diesen Constraint war es möglich, dasselbe Fach
> mehrfach demselben Bereich zuzuordnen. Der Constraint verhindert
> Duplikate auf Datenbankebene.
 
---

### 5.5 Git Commits – Woche 5

```
Add logout button, conflict check, remove dolmetscher, responsive layout, logo and padding
```
 
---

### 5.6 Erstellung eines ausführbaren JAR (Fat JAR)

Um die Anwendung ohne IntelliJ IDEA starten zu können, wurde ein
ausführbares Fat JAR erstellt. Ein Fat JAR enthält alle benötigten
Bibliotheken (JavaFX, MySQL Connector, jBCrypt) in einer einzigen Datei.

#### 5.6.1 Konfiguration: maven-shade-plugin

Das `maven-shade-plugin` wurde in der `pom.xml` ergänzt:

```xml
<!-- Fat JAR – enthält alle Bibliotheken -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.5.0</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <transformers>
                    <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                        <mainClass>com.brh.projekt_plan4sign_2026.Launcher</mainClass>
                    </transformer>
                </transformers>
            </configuration>
        </execution>
    </executions>
</plugin>
```

> **Begründung:** Das `maven-shade-plugin` packt alle Abhängigkeiten
> in eine einzige JAR-Datei. Die `mainClass` definiert den Einstiegspunkt
> der Anwendung beim Start.

#### 5.6.2 Build-Vorgang

Das Fat JAR wird durch folgenden Maven-Befehl erstellt:

```
Maven Panel → Lifecycle → package
```

Das fertige JAR befindet sich unter:

```
target\Projekt_Plan4Sign_2026-1.0-SNAPSHOT.jar
```

#### 5.6.3 Starten der Anwendung

Die Anwendung kann direkt über das Terminal gestartet werden:

```bash
java -jar Projekt_Plan4Sign_2026-1.0-SNAPSHOT.jar
```

Für einen einfacheren Start wurde eine `Plan4Sign.bat` Datei
im gleichen Verzeichnis erstellt:

```batch
java -jar Projekt_Plan4Sign_2026-1.0-SNAPSHOT.jar
```

Durch Doppelklick auf `Plan4Sign.bat` startet die Anwendung
ohne IntelliJ IDEA.

> **Voraussetzung:** Java 21 muss auf dem Zielrechner installiert sein
> und die MySQL-Datenbank muss über WSL auf Port 3324 erreichbar sein.
 
---

### Status
#### Woche 5: abgeschlossen 