# Architektur- & Projektentscheidungen – Projekt_DoIT

---

## Projektfokus und Abgrenzung

Das System richtet sich ausschließlich an hörgeschädigte Teilnehmer
mit Unterstützungsbedarf.

Es handelt sich nicht um eine vollständige Schulverwaltungssoftware.

Der Fokus liegt auf:

- organisatorischer Planung von Unterrichtseinheiten
- Zuweisung von Dolmetschern
- Berücksichtigung von Verfügbarkeiten
- Vermeidung von Doppelbelegungen

---

## Einführung eines rollenbasierten Login-Systems

Es wurde bewusst ein rollenbasiertes Login-System eingeführt.

Begründung:

- Zugriffsschutz sensibler Planungsdaten
- Klare Trennung der Benutzerrollen
- Realitätsnahe Abbildung der Organisationsstruktur

Rollen:

- Teilnehmer → sieht ausschließlich den eigenen Stundenplan
- Dolmetscher → sieht ausschließlich den eigenen Einsatzplan
- Administrator → verwaltet Planung und Zuweisungen

Die Rolle wird ausschließlich über das `role`-Feld (ENUM) im User gesteuert.

Die Entität „User" wurde zur sauberen Trennung
von Login-Daten und fachlichen Personendaten eingeführt.

> **Änderung:** Der Satz „Nur Dolmetscher können Administratorrechte besitzen" wurde entfernt.
> Administratorrechte werden nicht mehr separat am Dolmetscher vergeben,
> sondern ausschließlich über das `role`-Feld im User gesteuert.
> ADMIN ist ein eigenständiger Benutzertyp – kein Dolmetscher mit erweiterten Rechten.

---

## Einführung der Entitäten „Bereich" und „Fach"

Zur besseren fachlichen Strukturierung wurde
eine zusätzliche Hierarchie eingeführt:

Bereich → Fach → Unterricht

Begründung:

- Strukturierung der Unterrichtsfächer
- Gruppierung nach Fachbereichen (z. B. Informatik, Maschinenbau)
- Erweiterbarkeit des Systems
- Vorbereitung für spätere Auswertungen

> **Hinweis:** Die Verknüpfung zwischen Bereich und Fach wird ausschließlich über Foreign Keys abgebildet.
> In den Java-Modellen werden aus Gründen der Entkopplung keine Objekt-Referenzen verwendet (z. B. `bereichID` statt `Bereich`-Objekt).

Das Attribut `isInterpreterRelevant` wurde im Fach ergänzt.

Begründung:

- Kennzeichnung, ob für ein Fach grundsätzlich ein Dolmetscher erforderlich ist
- Unterstützung der Planungslogik
- Grundlage für spätere automatische Prüfungen

---

## Erweiterung des Unterricht-Modells um Anzeige-Felder

Die Entität `Unterricht` wurde um zusätzliche Anzeige-Felder erweitert:

- `klassename`
- `fachname`
- `dolmetschername`
- `teilnehmername`

Begründung:

- Reduzierung der Logik in den Controllern
- Vorbereitung UI-freundlicher Daten direkt im DAO
- Entlastung der View-Schicht
- Einhaltung des MVC-Prinzips: Datenaufbereitung in der Datenzugriffsschicht, nicht im UI

> **Entscheidung:** Die Felder werden über JOINs im DAO befüllt und über Setter-Methoden
> am Objekt gesetzt. Der Konstruktor enthält ausschließlich Pflichtfelder der Datenbankstruktur.

---

## Einheitliche Benennungskonvention für Klassenbezeichnungen

Im System werden Klassenbezeichnungen an drei Stellen verwendet,
die unterschiedliche Konventionen erfordern:

| Ebene       | Bezeichnung   | Konvention                        |
|-------------|---------------|-----------------------------------|
| Datenbank   | `klassename`  | Kleinschreibung (DB-Konvention)   |
| Java-Modell | `klasseName`  | camelCase (Java-Konvention)       |
| ER-Modell   | `className`   | camelCase (konzeptionelle Ebene)  |

> **Entscheidung:** Die unterschiedliche Schreibweise ist bewusst gewählt,
> um den jeweiligen Konventionen der Ebene zu entsprechen.
> Die Datenbankbezeichner folgen der MySQL-Konvention (Kleinschreibung),
> während Java-Felder camelCase verwenden.
> Diese Entscheidung wurde getroffen, um domänenspezifische Eindeutigkeit
> und Konsistenz innerhalb jeder Schicht zu gewährleisten.

---

## Verwendung von JavaFX

JavaFX wurde gewählt, da es sich für Desktop-Anwendungen eignet
und vollständig in Java integriert ist.

Vorteile:

- Klare Trennung von Oberfläche (FXML) und Logik (Controller)
- Gute Strukturierbarkeit im MVC-Prinzip
- Einfache Umsetzung rollenabhängiger Oberflächen

---

## Verwendung von MVC-Architektur

Die Model-View-Controller-Architektur wurde gewählt,
um eine saubere Trennung der Anwendungsschichten zu gewährleisten.

Begründung:

- Trennung von Daten, Logik und Präsentation
- Bessere Wartbarkeit
- Erweiterbarkeit
- Testbarkeit
- IHK-konforme Struktur

---

## Verwendung von Git & GitHub

Git wird zur Versionsverwaltung eingesetzt,
um Änderungen nachvollziehbar zu dokumentieren
und Datenverlust zu vermeiden.

GitHub dient als zentrales Remote-Repository
zur Sicherung und Versionshistorie des Projekts.

---

## Einführung der Entität „Klasse"

Die Klassenbezeichnung wurde bewusst als eigene Entität modelliert
und nicht als Textattribut gespeichert.

Begründung:

- Vermeidung von Redundanz
- Vermeidung von Tippfehlern
- Sicherstellung referenzieller Integrität
- Klare 1:n-Beziehung zwischen Klasse und Teilnehmer
- Klare 1:n-Beziehung zwischen Klasse und Unterricht

---

## Entscheidung gegen n:m-Beziehungen

Es wurde bewusst auf unnötige n:m-Beziehungen verzichtet.

Beispiel:

Eine Unterrichtseinheit kann maximal einem Dolmetscher
zugewiesen werden.

Ein Dolmetscher kann mehrere Unterrichtseinheiten betreuen.

Daher reicht eine 1:n-Beziehung aus.
Eine zusätzliche Zuordnungstabelle ist nicht erforderlich.

Diese Entscheidung reduziert die Komplexität
und entspricht den organisatorischen Gegebenheiten.

---

## Zentrales Element: Unterricht

Das zentrale fachliche Element des Systems ist die Unterrichtseinheit.

Die Planung, Zuweisung und Konfliktprüfung
bezieht sich immer auf konkrete Unterrichtszeiten.

Dolmetscher und Teilnehmer sind unterstützende Entitäten,
während der Unterricht das organisatorische Kernelement darstellt.

---

## Erweiterte Availability-Logik

Die Availability wurde erweitert,
um sowohl Einzeltermine als auch Zeiträume abzubilden.

Unterstützte Availability-Typen:

- Einzeltermin (date)
- Zeitfenster innerhalb eines Tages (startTime / endTime)
- Zeitraum über mehrere Tage (dateFrom / dateTo)
- Ganztägige Blockierung

Begründung:

- Realistische Abbildung von Urlaub, Krankheit, Besprechungen
- Vorbereitung für Konflikterkennung
- Flexiblere Planungslogik

---

## Trennung von Stammdaten und Bewegungsdaten

Stammdaten:

- User
- Dolmetscher
- Teilnehmer
- Klasse
- Bereich
- Fach

Bewegungsdaten:

- Unterricht
- Availability

Diese Trennung erhöht:

- Struktur
- Wartbarkeit
- Übersichtlichkeit
- Datenkonsistenz

---

## Vorbereitung der Konflikterkennung

Die Datenstruktur wurde so modelliert,
dass eine spätere Konflikterkennung möglich ist.

Ziel:

- Verhinderung doppelter Dolmetscher-Zuweisungen
- Berücksichtigung von Availability
- Prüfung zeitlicher Überschneidungen

Die Konfliktlogik wird in der Business-Schicht implementiert
und nicht auf Datenbankebene.

Dadurch ist die Datenstruktur vollständig darauf ausgelegt,
eine spätere automatische Konflikterkennung ohne strukturelle Änderungen zu ermöglichen.