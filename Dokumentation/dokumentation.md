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

Abschluss der JDBC-Anbindung und Implementierung der DAO-Klassen sowie Stabilisierung der Modellklassen.

## 3. Technisches Konzept

### 3.1 Architekturentscheidung

Für die Umsetzung der Anwendung **„DoIT – Planungs- und Organisationssoftware"** wurde die  
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

### 3.2 Package-Struktur

Die Anwendung ist in folgende Packages unterteilt:

```
src/main/java/com/brh/
│
├── model/       ← Entity-Klassen (User, Dolmetscher, usw.)
├── view/        ← FXML-Controller (JavaFX-Oberfläche)
├── controller/  ← Anwendungslogik
├── dao/         ← Datenbankzugriff (JDBC)
└── util/        ← Hilfsmittel (z. B. DatabaseConnection)
```

#### Begründung

Die Aufteilung in Packages entspricht der MVC-Architektur und sorgt für eine klare Trennung der Verantwortlichkeiten:

- `model` Entity-Klassen (User, Dolmetscher, usw.)
- `view` FXML-Controller (JavaFX-Oberfläche)
- `controller` Anwendungslogik
- `dao` Datenbankzugriff (JDBC)
- `util` Hilfsmittel (z. B. DatabaseConnection)

---
- model enthält ausschließlich die Datenstruktur
- view enthält ausschließlich die Benutzeroberfläche
- controller enthält die Anwendungslogik
- dao enthält den gesamten Datenbankzugriff
- util enthält gemeinsam genutzte Hilfsmittel


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

Speichert Login-Daten und Rolleninformationen.

Attribute:

- `int userID`
- `String username`
- `String passwordHash`
- `Role role`

---

#### Bereich.java

Repräsentiert einen fachlichen Bereich.

Attribute:

- `int bereichID`
- `String bereichName`

---

#### Fach.java

Repräsentiert ein konkretes Unterrichtsfach.

Attribute:

- `int fachID`
- `String fachName`
- `boolean isInterpreterRelevant`
- `int bereichID`

> **Entscheidung:** Das Attribut `bereichID` wird als `int` gespeichert (Foreign Key),
> nicht als `Bereich`-Objekt. Die Daten werden über JDBC geladen,
> daher wird auf Objekt-Referenzen verzichtet.

---

#### Klasse.java

Repräsentiert eine Schulklasse.

Attribute:

- `int klasseID`
- `String klasseName`
- `String room`

---

#### Teilnehmer.java

Repräsentiert einen hörgeschädigten Teilnehmer.

Attribute:

- `int teilnehmerID`
- `String firstName`
- `String lastName`
- `String email`
- `String mobilePhone`
- `String comment`
- `int klasseID`
- `int userID`

---

#### Dolmetscher.java

Repräsentiert einen Dolmetscher.

Attribute:

- `int dolmetscherID`
- `String firstName`
- `String lastName`
- `String email`
- `String mobilePhone`
- `String comment`
- `int userID`

---

#### Unterricht.java

Repräsentiert eine einzelne Unterrichtseinheit.

Attribute:

- `int unterrichtID`
- `LocalDate date`
- `LocalTime startTime`
- `LocalTime endTime`
- `int klasseID`
- `int fachID`
- `Integer dolmetscherID`

> **Entscheidung:** `dolmetscherID` ist vom Typ `Integer` (nicht `int`),
> da der Wert `NULL` sein kann, wenn kein Dolmetscher zugewiesen ist.

> **Entscheidung:** `LocalDate` und `LocalTime` wurden anstelle von `java.sql.Date`
> und `java.sql.Time` verwendet, da diese modernen Typen seit Java 8 empfohlen werden
> und keine automatische Konvertierung der Datumswerte vornehmen.

---

#### Availability.java

Speichert Zeitblockierungen eines Dolmetschers.

Attribute:

- `int availabilityID`
- `String availabilityType`
- `LocalDate date`
- `LocalTime startTime`
- `LocalTime endTime`
- `LocalDate dateFrom`
- `LocalDate dateTo`
- `String comment`
- `int dolmetscherID`

> **Entscheidung:** Alle optionalen Datums- und Zeitfelder sind als `LocalDate` bzw.
> `LocalTime` deklariert und können `null` sein, entsprechend der Datenbankdefinition.

---

### 3.4 JDBC-Datenbankanbindung

Für die Verbindung zwischen Java und der MySQL-Datenbank wurde JDBC verwendet.

#### MySQL Connector/J

Der MySQL JDBC-Treiber wurde als Maven-Dependency in der `pom.xml` eingebunden:

```XML
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```
#### DatabaseConnection.java
Die Klasse DatabaseConnection im Package util stellt die Verbindung zur Datenbank her.
Umgesetzte Konzepte:

- Singleton-Prinzip: Es existiert zu jedem Zeitpunkt nur eine einzige Datenbankverbindung
- Privater Konstruktor: Verhindert die Instanziierung der Klasse von außen
- getConnection() gibt die bestehende Verbindung zurück oder erstellt eine neue
- closeConnection() schließt die Verbindung sicher

#### Verbindungsparameter:

- URL: jdbc:mysql://localhost:3324/projekt_doit
- Port: 3324 (MySQL läuft unter WSL)
- Datenbank: projekt_doit

```java
Verbindung erfolgreich!
```

`CODE in package util -> DatabaseConnection.java`

```java
import java.sql.Connection;
......

public class DatabaseConnection {

    // JDBC-URL: mysql = Treiber, localhost = Server, 3324 = Port, projekt_doit = Datenbankname
    // (Standard-Port ist 3306 -> prüfen, ob 3324 korrekt ist)
    private static final String URL = "jdbc:mysql://localhost:3324/projekt_doit";
    // Datenbank-Benutzername
    private static final String USER = "root";
    // Datenbank-Passwort (Hinweis: in echten Projekten nicht im Code speichern!)
    private static final String PASSWORD = "1234";
    // Singleton-Verbindung: Es existiert nur eine Connection im gesamten Programm
    private static Connection connection = null;
    // Privater Konstruktor verhindert Instanziierung (Utility-/Singleton-Klasse)
    private DatabaseConnection() {}
    
    ...........

```


### 3.5 DAO-Klassen
Für jede Entität wurde eine DAO-Klasse (Data Access Object) im Package dao erstellt.
Jede DAO-Klasse implementiert die grundlegenden CRUD-Operationen:

- Create → INSERT (insert())
- Read → SELECT (getAll(), getByID(), getByKlasse() usw.)
- Update → UPDATE (assignDolmetscher())
- Delete → DELETE (delete())

### Übersicht der DAO-Klassen

| DAO-Klasse       | Methoden                                                                             |
|------------------|--------------------------------------------------------------------------------------|
| BereichDAO       | getAll(), insert(), delete()                                                         |
| KlasseDAO        | getAll(), insert(), delete()                                                         |
| FachDAO          | getAll(), getByBereich(), insert(), delete()                                         |
| UserDAO          | getAll(), getByUsername(), insert(), delete()                                        |
| DolmetscherDAO   | getAll(), getByID(), insert(), delete()                                              |
| TeilnehmerDAO    | getAll(), getByKlasse(), insert(), delete()                                          |
| UnterrichtDAO    | getAll(), getByKlasse(), getByDolmetscher(), insert(), assignDolmetscher(), delete() |
| AvailabilityDAO  | getByDolmetscher(), insert(), delete()                                               |


> Entscheidung: PreparedStatement wurde anstelle von Statement verwendet,
> wo Benutzereingaben in die SQL-Abfrage einfließen, um SQL-Injection zu verhindern.
> Entscheidung: NULL-Werte werden mit getObject() geprüft bevor sie mit
> toLocalDate() oder toLocalTime() konvertiert werden, um NullPointerExceptions zu vermeiden.

---

### 3.6 Versionsverwaltung
Das Projekt wurde mit einem privaten GitHub-Repository verknüpft.

- Lokales Git-Repository war bereits beim Erstellen des Projekts initialisiert
- Remote-Repository wurde auf GitHub unter dem Namen Projekt_Plan4Sign_2026 erstellt
- Verbindung wurde über IntelliJ IDEA (Git → Manage Remotes) hergestellt

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
4. `https://github.com/mysql/mysql-connector-j?utm_source=chatgpt.com`
5. `https://dev.mysql.com/doc/connector-j/en/`
---


### Status
#### Woche 3: abgeschlossen ✅

- ✅ Architekturentscheidung (MVC)
- ✅ Package-Struktur
- ✅ Entity-Klassen
- ✅ GitHub-Anbindung
- ✅ JDBC-Datenbankanbindung
- ✅ DAO-Klassen (CRUD)
- ✅ Test der Datenbankverbindung

----
# Dokumentation – Woche 4

## 4. Woche 4 – Login-System, Rollennavigation & JavaFX-Oberfläche

## Ziel der Woche

Implementierung des Login-Systems mit sicherer Passwortverschlüsselung,
rollenbasierter Navigation sowie Vorbereitung der JavaFX-Oberflächen.

---

### 4.1 Projektkonfiguration

#### 4.1.1 pom.xml – Anpassungen

Folgende Änderungen wurden an der `pom.xml` vorgenommen:

**BCrypt-Dependency hinzugefügt:**
```xml
<dependency>
    <groupId>org.mindrot</groupId>
    <artifactId>jbcrypt</artifactId>
    <version>0.4</version>
</dependency>
```

> **Begründung:** Passwörter dürfen niemals im Klartext gespeichert werden.
> BCrypt ist ein bewährter Hashing-Algorithmus mit automatischem Salt,
> der speziell für Passwörter entwickelt wurde.

**JavaFX-Version als Property zentralisiert:**
```xml
<properties>
    <javafx.version>21.0.6</javafx.version>
</properties>
```

> **Begründung:** Durch die Verwendung einer zentralen Property muss
> die Version nur an einer Stelle gepflegt werden.

**Java-Compiler-Version korrigiert:**
```xml
<source>21</source>
<target>21</target>
```

> **Begründung:** Die Compiler-Version muss mit dem installierten JDK übereinstimmen.
> Die vorherige Einstellung (23) führte zu Inkompatibilitäten.

**mainClass im javafx-maven-plugin aktualisiert:**
```xml
<mainClass>
    com.brh.projekt_plan4sign_2026/com.brh.projekt_plan4sign_2026.Launcher
</mainClass>
```

> **Begründung:** `HelloApplication` war ein vom IntelliJ-Template generiertes
> Beispiel und wird durch `Launcher` als echten Einstiegspunkt ersetzt.

---

#### 4.1.2 module-info.java – Anpassungen

Das Java Module System erfordert eine explizite Deklaration aller verwendeten
Module sowie der Packages, auf die andere Module Zugriff erhalten dürfen.
```java
module com.brh.projekt_plan4sign_2026 {

    // JavaFX – wird für UI-Komponenten und FXML-Laden benötigt
    requires javafx.controls;
    requires javafx.fxml;

    // Datenbankzugriff über JDBC
    requires java.sql;

    // BCrypt – für die sichere Passwort-Verschlüsselung
    requires jbcrypt;

    // opens: der FXMLLoader benötigt Reflection-Zugriff auf die Controller-Klassen
    // Ohne diese Zeile → IllegalAccessException zur Laufzeit
    opens com.brh.projekt_plan4sign_2026 to javafx.fxml;
    opens com.brh.projekt_plan4sign_2026.controller to javafx.fxml;

    // exports: macht die Packages für andere Module sichtbar
    exports com.brh.projekt_plan4sign_2026;
    exports com.brh.projekt_plan4sign_2026.controller;
}
```

**Erklärung der Direktiven:**

| Direktive | Bedeutung |
|-----------|-----------|
| `requires` | Deklariert ein externes Modul als Abhängigkeit |
| `opens ... to` | Erlaubt Reflection-Zugriff zur Laufzeit (z. B. für FXMLLoader) |
| `exports` | Macht ein Package für andere Module sichtbar |

> **Begründung `requires java.sql`:** Der MySQL JDBC-Treiber wird über den
> Java ServiceLoader-Mechanismus automatisch zur Laufzeit geladen.
> Ein explizites `requires mysql...` ist daher nicht notwendig.

> **Begründung `opens controller to javafx.fxml`:** Der FXMLLoader verwendet
> Reflection, um `@FXML`-annotierte Felder in Controller-Klassen zu injizieren.
> Ohne `opens` wirft die JVM eine `IllegalAccessException` zur Laufzeit.

---

#### 4.1.3 Role.java – Korrektur

Der Java-Enum `Role` wurde an die MySQL-ENUM-Werte angepasst:
```java
public enum Role {
    ADMIN,
    DOLMETSCHER,
    TEILNEHMER
}
```

> **Begründung:** Die Datenbank ist die einzige Quelle der Wahrheit (Single Source of Truth).
> Der Java-Enum muss die DB-Werte exakt widerspiegeln, da bei `Role.valueOf("ADMIN")`
> ein `IllegalArgumentException` geworfen wird, wenn die Werte nicht übereinstimmen.

---

#### 4.1.4 Debugging – module-info.java (MySQL Module Name)

Bei der Konfiguration der `module-info.java` wurde versucht, den MySQL JDBC-Treiber
explizit als Modul zu deklarieren. Dabei traten folgende Fehler auf:

**Versuch 1:**
```java
requires com.mysql.jdbc;
```
```
java: Modul nicht gefunden: com.mysql.jdbc
```

**Versuch 2:**
```java
requires mysql.connector.java;
```
```
java: Modul nicht gefunden: mysql.connector.java
```

**Lösung:**
Die `requires`-Direktive für MySQL wurde vollständig entfernt.

> **Begründung:** Der MySQL JDBC-Treiber `mysql-connector-java 8.0.33` ist ein
> sogenanntes **Automatic Module** – er besitzt keinen offiziellen Modul-Namen
> und muss daher nicht explizit deklariert werden.
> Java lädt den Treiber automatisch zur Laufzeit über den **ServiceLoader-Mechanismus**
> (`java.sql.Driver`). Die Direktive `requires java.sql` ist ausreichend.

> **Entscheidung:** Der MySQL Connector wurde bewusst nicht auf die neuere Version
> `mysql-connector-j 8.3.0` aktualisiert, da die bestehende Version `8.0.33`
> bereits funktionsfähig war und eine unnötige Änderung vermieden werden sollte.

---

#### 4.1.5 MainController.java – Platzhalter

Da `module-info.java` das Package `controller` mit `opens` und `exports` deklariert,
erwartet der Java-Compiler mindestens eine Klasse in diesem Package.
Da die Controller-Klassen noch nicht implementiert waren, führte dies zu einem
Compile-Fehler.

**Lösung:** Erstellung einer leeren Platzhalter-Klasse:
```java
package com.brh.projekt_plan4sign_2026.controller;

// Platzhalter – wird in der nächsten Aufgabe implementiert
public class MainController {
}
```

> **Begründung:** Das Java Module System validiert beim Kompilieren,
> ob die in `module-info.java` deklarierten Packages tatsächlich existieren.
> Ein leeres Package ohne Klassen wird nicht als gültig erkannt.
> Der Platzhalter wird ersetzt, sobald die echten Controller implementiert sind.

#### 4.1.6 PasswordUtil.java – Passwort-Hashing mit BCrypt

Für die sichere Speicherung von Passwörtern wurde die Klasse `PasswordUtil`
im Package `util` erstellt.
```java
package com.brh.projekt_plan4sign_2026.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Privater Konstruktor – diese Klasse soll nicht instanziiert werden
    private PasswordUtil() {}

    // Erstellt einen sicheren Hash aus dem Klartext-Passwort
    // workload 12 = Stärke des Hashing-Algorithmus (höher = sicherer, aber langsamer)
    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    // Vergleicht ein Klartext-Passwort mit einem gespeicherten Hash
    // Gibt true zurück, wenn das Passwort übereinstimmt
    public static boolean verify(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
```

> **Entscheidung:** Passwörter werden niemals im Klartext gespeichert.
> BCrypt wurde gewählt, da der Algorithmus speziell für Passwort-Hashing
> entwickelt wurde und automatisch einen zufälligen Salt generiert.

| Element                  | Begründung                                                            |
|--------------------------|-----------------------------------------------------------------------|
| `private PasswordUtil()` | Utility-Klasse – keine Instanziierung notwendig                       |
| `BCrypt.gensalt(12)`     | Cost Factor 12 – jede Erhöhung um 1 verdoppelt die Berechnungszeit    |
| `checkpw()`              | Der Salt ist im Hash enthalten – keine separate Speicherung notwendig |
| `static` Methoden        | Aufruf direkt über `PasswordUtil.hash(...)` ohne `new`                |

---

#### 4.1.7 module-info.java – Erweiterung um util Package

Nach der Erstellung von `PasswordUtil.java` wurde `module-info.java`
um das `util` Package erweitert:
```java
opens com.brh.projekt_plan4sign_2026.util to javafx.fxml;
exports com.brh.projekt_plan4sign_2026.util;
```

> **Begründung:** Das Java Module System erfordert, dass jedes Package,
> das von anderen Klassen verwendet wird, explizit exportiert wird.
> Ohne `exports util` wäre `PasswordUtil` außerhalb des Packages nicht sichtbar.

- UserDAO.java – try-with-resources, Optional<User>, Passwort-Hashing

#### 4.1.8 UserDAO.java – Überarbeitung

Die bestehende Skeleton-Implementierung von `UserDAO` wurde überarbeitet
und um folgende Punkte ergänzt:

**Änderung 1 – try-with-resources**

Alle Datenbankressourcen (`Connection`, `PreparedStatement`, `ResultSet`)
werden jetzt mit `try-with-resources` verwaltet:
```java
try (Connection connection = DatabaseConnection.getConnection();
     PreparedStatement statement = connection.prepareStatement(sql)) {
    ...
}
```

> **Begründung:** Ohne explizites Schließen bleiben Datenbankverbindungen offen
> (Memory Leak). `try-with-resources` schließt alle Ressourcen automatisch –
> auch im Fehlerfall.

**Änderung 2 – Optional\<User\> statt null**

`getByUsername()` gibt jetzt `Optional<User>` zurück:
```java
public Optional<User> getByUsername(String username) throws SQLException
```

> **Begründung:** Die Rückgabe von `null` ist fehleranfällig –
> ein vergessener `null`-Check führt zur `NullPointerException`.
> `Optional<User>` zwingt den Aufrufer, den Fall „kein Benutzer gefunden"
> explizit zu behandeln.

**Änderung 3 – Passwort-Hashing in insert()**

Das Passwort wird vor dem Speichern automatisch gehasht:
```java
statement.setString(2, PasswordUtil.hash(user.getPasswordHash()));
```

> **Begründung:** Passwörter dürfen niemals im Klartext in der Datenbank
> gespeichert werden. Das Hashing erfolgt zentral in der DAO-Schicht,
> sodass es nicht vergessen werden kann.

**Änderung 4 – PreparedStatement in getAll()**

`Statement` wurde durch `PreparedStatement` ersetzt.

> **Begründung:** `PreparedStatement` ist auch ohne Parameter Best Practice,
> da es konsistenter und sicherer ist.


Zusammenfassung 

Im neuen Code wurden mehrere Verbesserungen umgesetzt:
Erstens wurde try-with-resources verwendet, um Ressourcen automatisch zu schließen.
Zweitens wurde Optional statt null eingeführt, um Fehler zu vermeiden.
Drittens werden Passwörter jetzt mit BCrypt gehasht, um die Sicherheit zu erhöhen.
Und viertens wird PreparedStatement verwendet, um SQL-Injection zu verhindern.
--- 

#### 4.2 Login-System

##### 4.2.1 Übersicht

Das Login-System besteht aus folgenden Komponenten:

| Datei                  | Package            | Aufgabe                               |
|------------------------|--------------------|---------------------------------------|
| `LoginView.fxml`       | resources/.../view | Benutzeroberfläche des Login-Fensters |
| `LoginController.java` | controller         | Verarbeitung der Login-Eingaben       |
| `App.java`             | root               | JavaFX-Einstiegspunkt, lädt LoginView |
| `Launcher.java`        | root               | Startet die JavaFX-Anwendung          |

---

##### 4.2.2 LoginView.fxml

Die Login-Oberfläche wurde als FXML-Datei erstellt und enthält folgende Elemente:

- `TextField` (fx:id="usernameField") – Eingabe des Benutzernamens
- `PasswordField` (fx:id="passwordField") – Eingabe des Passworts
- `Label` (fx:id="errorLabel") – Anzeige von Fehlermeldungen
- `Button` – Auslöser für den Login-Vorgang (`onAction="#handleLogin"`)
```xml
<VBox xmlns:fx="http://javafx.com/fxml"
      fx:controller="com.brh.projekt_plan4sign_2026.controller.LoginController"
      alignment="CENTER"
      spacing="15"
      prefWidth="400"
      prefHeight="350">
    ...
</VBox>
```

> **Entscheidung:** FXML-Dateien werden im `resources`-Verzeichnis abgelegt,
> da sie keine Java-Klassen sind und vom `FXMLLoader` zur Laufzeit geladen werden.
> Der Pfad lautet: `resources/com/brh/projekt_plan4sign_2026/view/`

---

##### 4.2.3 LoginController.java

Der Controller verarbeitet die Login-Eingaben und navigiert zur rollenbasierten Ansicht.

**Ablauf des Login-Vorgangs:**

1. Eingaben aus den FXML-Feldern lesen
2. Prüfen ob Felder leer sind
3. Benutzer über `UserDAO.getByUsername()` in der DB suchen
4. Passwort mit `PasswordUtil.verify()` prüfen
5. Bei Erfolg → Navigation zur rollenbasierten Ansicht
6. Bei Fehler → Fehlermeldung im `errorLabel`
```java
@FXML
private void handleLogin() {
    String username = usernameField.getText().trim();
    String password = passwordField.getText();

    if (username.isEmpty() || password.isEmpty()) {
        errorLabel.setText("Bitte alle Felder ausfüllen.");
        return;
    }

    Optional<User> result = userDAO.getByUsername(username);

    if (result.isEmpty() || !PasswordUtil.verify(password, result.get().getPasswordHash())) {
        errorLabel.setText("Ungültiger Benutzername oder Passwort.");
        return;
    }

    navigateTo(result.get());
}
```

**Rollenbasierte Navigation:**
```java
private void navigateTo(User user) {
    String fxml = switch (user.getRole()) {
        case ADMIN       -> "/com/brh/projekt_plan4sign_2026/view/AdminView.fxml";
        case TEILNEHMER  -> "/com/brh/projekt_plan4sign_2026/view/TeilnehmerView.fxml";
        case DOLMETSCHER -> "/com/brh/projekt_plan4sign_2026/view/DolmetscherView.fxml";
    };
    ...
}
```

> **Entscheidung:** `switch` mit Pattern Matching wurde verwendet,
> da es alle Enum-Werte zur Kompilierzeit prüft – vergessene Rollen
> führen zu einem Compiler-Fehler, nicht zu einem Laufzeitfehler.

> **Entscheidung:** `Optional<User>` aus `getByUsername()` verhindert
> eine `NullPointerException`, wenn kein Benutzer gefunden wird.

---

##### 4.2.4 App.java

`App.java` ersetzt die vom IntelliJ-Template generierte `HelloApplication.java`
und ist der JavaFX-Einstiegspunkt der Anwendung.
```java
public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/com/brh/projekt_plan4sign_2026/view/LoginView.fxml")
        );
        Scene scene = new Scene(loader.load());
        stage.setTitle("Plan4Sign 2026");
        stage.setWidth(400);
        stage.setHeight(350);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
```

> **Entscheidung:** `setResizable(false)` wurde gesetzt, da das Login-Fenster
> eine feste Größe haben soll und nicht skaliert werden muss.

---

##### 4.2.5 Testbenutzer

Für den Test des Login-Systems wurde ein Testbenutzer in der Datenbank angelegt:
```sql
INSERT INTO User (username, passwordHash, role)
VALUES ('admin', '$2a$12$...', 'ADMIN');
```

> Das Passwort `admin123` wurde mit `PasswordUtil.hash()` gehasht
> und als BCrypt-Hash in der Datenbank gespeichert.
> Das Klartext-Passwort wird nirgends gespeichert.

---

##### 4.2.6 Platzhalter-Ansichten

Für jede Benutzerrolle wurden vorläufige FXML-Ansichten und Controller erstellt,
die nach erfolgreichem Login geladen werden:

| Rolle       | FXML                   | Controller                   |
|-------------|------------------------|------------------------------|
| ADMIN       | `AdminView.fxml`       | `AdminController.java`       |
| TEILNEHMER  | `TeilnehmerView.fxml`  | `TeilnehmerController.java`  |
| DOLMETSCHER | `DolmetscherView.fxml` | `DolmetscherController.java` |

> **Begründung:** Die Platzhalter ermöglichen es, das vollständige
> Login-System zu testen, bevor die eigentlichen Ansichten implementiert werden.

---

##### 4.2.7 Erweiterung der Unterricht-Entität

Im Verlauf der Implementierung wurde festgestellt, dass die bestehende
`Unterricht`-Klasse zwar die Datenbankstruktur korrekt abbildet,
jedoch nicht ausreichend für die Darstellung im Frontend ist.

Die ursprüngliche Klasse enthielt ausschließlich technische Fremdschlüssel:

- KlasseID
- FachID
- DolmetscherID

Für die Anzeige in der Benutzeroberfläche werden jedoch
lesbare Informationen benötigt (z. B. Klassenname statt ID).

Daher wurde die Klasse um folgende Attribute erweitert:
```java
private String klassename;
private String fachname;
private String dolmetschername;
```
Zusätzlich wurden entsprechende Getter- und Setter-Methoden ergänzt.

```text
Begründung:
Die Erweiterung ermöglicht eine direkte Darstellung der Daten im UI,
ohne zusätzliche Logik im Controller.
Die Daten werden bereits im DAO vorbereitet (JOIN), wodurch eine klare
Trennung zwischen Datenzugriff und Darstellung eingehalten wird (MVC-Prinzip).
```

---

#### 4.2.8 Erweiterung des UnterrichtDAO (JOIN-Abfrage)

Zur Anzeige der vollständigen Unterrichtsdaten wurde die Methode
`getAllWithDetails()` im `UnterrichtDAO` implementiert.

Dabei wird eine SQL-Abfrage mit mehreren JOINs verwendet:

```sql
SELECT 
u.UnterrichtID,
u.date,
u.starttime,
u.endtime,
u.KlasseID,
u.FachID,
u.DolmetscherID,
k.klassename,
f.fachname,
CONCAT(d.firstname, ' ', d.lastname) AS dolmetschername
FROM Unterricht u
JOIN Klasse k ON u.KlasseID = k.KlasseID
JOIN Fach f ON u.FachID = f.FachID
LEFT JOIN Dolmetscher d ON u.DolmetscherID = d.DolmetscherID
ORDER BY u.date, u.starttime;
```

Die Abfrage verbindet die Tabellen:

- `Unterricht` → Basisdaten
- `Klasse` → Klassenname
- `Fach` → Fachname
- `Dolmetscher` → Name des Dolmetschers (optional)

```text
Begründung:
Durch die Verwendung von JOINs werden alle benötigten Daten in einer
einzigen Abfrage geladen.
Der `LEFT JOIN` stellt sicher, dass auch Unterrichtseinheiten ohne
zugewiesenen Dolmetscher angezeigt werden.
```
--- 

#### 4.2.9 Mapping von Datenbank zu Objekt

Die aus der Datenbank geladenen Daten werden im DAO in Java-Objekte
der Klasse Unterricht überführt.

Dabei erfolgt die Erstellung des Objekts in zwei Schritten:

1. Initialisierung der Basisdaten über den Konstruktor
2. Ergänzung der Anzeige-Daten über Setter-Methoden

```java
Unterricht unterricht = new Unterricht(...);

unterricht.setKlassename(...);
unterricht.setFachname(...);
unterricht.setDolmetschername(...);
```

```text
Begründung:
Der Konstruktor enthält ausschließlich Pflichtfelder (Datenbankstruktur),
während zusätzliche Anzeige-Daten separat gesetzt werden.
Dieses Vorgehen verhindert eine Überladung des Konstruktors und
ermöglicht eine flexible Erweiterung des Modells.
```
---

##### 4.2.10 Trennung von Datenzugriff und Darstellung

Die Aufbereitung der Daten erfolgt vollständig im DAO,
während der Controller ausschließlich für die Darstellung zuständig ist.

Datenfluss:

```textmate
Datenbank → DAO → Model (Unterricht) → Controller → UI
```

```text
Begründung:
Diese klare Trennung entspricht dem MVC-Architekturprinzip und verhindert,
dass Geschäftslogik im Controller implementiert wird.
Dadurch bleibt der Code wartbar, übersichtlich und erweiterbar.
```
---

##### 4.2.11 Darstellung der Daten im TableView

Im `AdminController` werden die Daten aus der Datenbank im `TableView`
visualisiert.

Dazu werden die Spalten (`TableColumn`) mit den entsprechenden Werten
aus dem Model (`Unterricht`) verknüpft.

```java
colDate.setCellValueFactory(data ->
    new SimpleStringProperty(data.getValue().getDate().toString()));

colStart.setCellValueFactory(data ->
    new SimpleStringProperty(data.getValue().getStartTime().toString()));

colKlasse.setCellValueFactory(data ->
    new SimpleStringProperty(data.getValue().getKlassename()));
```

`Begründung:
Die TableColumn greift direkt auf die Getter des Modells zu.
Dadurch bleibt der Controller schlank und enthält keine eigene Logik
zur Datenverarbeitung.
Die Darstellung wird vollständig über das Model gesteuert.`

---

#### 4.2.12 Laden der Daten in die Benutzeroberfläche

Die Daten werden über die Methode `loadData()` aus dem DAO geladen
und anschließend dem `TableView` übergeben.

```java
List<Unterricht> list = dao.getAllWithDetails();
tableUnterricht.setItems(FXCollections.observableArrayList(list));
```

```text
Begründung:
Die Verwendung von ObservableList ist notwendig, damit JavaFX
Änderungen in der Liste automatisch erkennt und die UI aktualisiert.
Dadurch wird eine dynamische Darstellung ermöglicht.
```
---

#### 4.2.13 Auswahl eines Dolmetschers über ComboBox

Für die Zuweisung eines Dolmetschers wird eine `ComboBox` verwendet,
die alle verfügbaren Dolmetscher anzeigt.

```text
Begründung:
Die Anzeige erfolgt über den vollständigen Namen (Vorname + Nachname),
da dies für den Benutzer verständlich ist.
Die Speicherung erfolgt jedoch weiterhin über die ID,
was eine saubere Trennung zwischen Anzeige und Datenstruktur ermöglicht.
```
---

#### 4.2.14 Zuweisung eines Dolmetschers zu einer Unterrichtseinheit

Der Administrator kann einen Dolmetscher auswählen und diesem eine
Unterrichtseinheit zuweisen.

```java
Unterricht selected = tableUnterricht.getSelectionModel().getSelectedItem();
String name = comboDolmetscher.getValue();
```
Anschließend wird der passende Dolmetscher anhand des Namens ermittelt
und die Zuweisung in der Datenbank gespeichert.

```java
dao.assignDolmetscher(selected.getUnterrichtID(), dolmetscherID);
```

```text
Begründung:
Die Auswahl erfolgt über die Benutzeroberfläche (TableView + ComboBox),
während die eigentliche Zuweisung über das DAO durchgeführt wird.
Dies stellt sicher, dass alle Datenbankoperationen zentral im DAO bleiben
und nicht im Controller implementiert werden.
```
---

#### 4.2.15 Aktualisierung der Ansicht nach Änderungen

Nach der Zuweisung wird die Methode `loadData()` erneut aufgerufen,
um die aktualisierten Daten im TableView darzustellen.

```java
loadData();
```

``` text
Begründung:
Durch das erneute Laden der Daten wird sichergestellt,
dass die Benutzeroberfläche immer den aktuellen Zustand der Datenbank
widerspiegelt.
Dies verhindert Inkonsistenzen zwischen UI und Datenbank.`
```
---






### Status
#### Woche 4: in Bearbeitung 🔄

- 🔄 Rollenbasierte Navigation
- 🔄 Administrator-Oberfläche
- 🔄 Teilnehmer-Sicht
- 🔄 Dolmetscher-Sicht