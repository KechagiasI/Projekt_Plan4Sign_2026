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

Nur Dolmetscher können Administratorrechte besitzen.

Die Entität „User“ wurde zur sauberen Trennung
von Login-Daten und fachlichen Personendaten eingeführt.

---

## Einführung der Entitäten „Bereich“ und „Fach“

Zur besseren fachlichen Strukturierung wurde
eine zusätzliche Hierarchie eingeführt:

Bereich → Fach → Unterricht

Begründung:

- Strukturierung der Unterrichtsfächer
- Gruppierung nach Fachbereichen (z. B. Informatik, Maschinenbau)
- Erweiterbarkeit des Systems
- Vorbereitung für spätere Auswertungen

Das Attribut `isInterpreterRelevant` wurde im Fach ergänzt.

Begründung:

- Kennzeichnung, ob für ein Fach grundsätzlich ein Dolmetscher erforderlich ist
- Unterstützung der Planungslogik
- Grundlage für spätere automatische Prüfungen

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

## Einführung der Entität „Klasse“

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

Möglichkeiten:

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