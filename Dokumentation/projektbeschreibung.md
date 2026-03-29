# Projektbeschreibung – Projekt_DoIT

---

## Projektziel

Ziel dieses Projekts ist die Entwicklung einer Desktop-Anwendung
zur strukturierten Planung und Organisation von Dolmetschereinsätzen
im schulischen Umfeld.

Die Anwendung ermöglicht:

- Verwaltung von Klassen, Fächern und Unterrichtseinheiten
- Zuweisung von Dolmetschern zu einzelnen Unterrichtsstunden
- Berücksichtigung von Verfügbarkeiten
- Rollenbasierte Zugriffskontrolle

---

## Problemstellung

Die Einsatzplanung von Gebärdensprachdolmetschern erfolgt häufig manuell
(z. B. über Tabellen oder E-Mail-Kommunikation).

Dies führt zu:

- fehlender Transparenz
- erhöhter Fehleranfälligkeit
- möglicher Doppelbelegung von Dolmetschern
- unzureichender Berücksichtigung von Verfügbarkeiten
- organisatorischem Mehraufwand

Insbesondere bei kurzfristigen Änderungen
(z. B. Krankheit oder Stundenplananpassungen)
entsteht ein hoher Koordinationsaufwand.

Ziel des Systems ist es, diese Prozesse zu strukturieren,
zu vereinfachen und nachvollziehbar zu dokumentieren.

---

## Zielgruppen

### Administrator

- Verwaltung von Klassen, Fächern und Teilnehmern
- Planung und Zuweisung von Dolmetschern
- Übersicht über ungeplante oder interpreterrelevante Fächer
- Konfliktprüfung bei Zeitüberschneidungen

---

### Dolmetscher

- Einsicht in den persönlichen Einsatzplan
- Übersicht über zugewiesene Klassen und Fachbereiche
- Keine eigenständige Planungsänderung

---

### Teilnehmer

- Einsicht in den eigenen Stundenplan
- Anzeige, ob ein Dolmetscher für die jeweilige Unterrichtseinheit eingeplant ist
- Kein Zugriff auf andere Teilnehmer oder Verwaltungsfunktionen

---

## Projektumfang

Das System umfasst:

- Relationales Datenbanksystem (MySQL)
- Rollenbasiertes Login-System
- Desktop-Oberfläche mit JavaFX
- Umsetzung nach MVC-Architektur
- Konfliktvermeidung bei Dolmetscher-Zuweisungen
- Verwaltung von Availability-Zeitblockierungen

---

## Projektabgrenzung

Nicht Bestandteil des Projekts:

- Web-Anwendung
- Mobile App
- Vollständige Schulverwaltungssoftware
- Automatische Stundenplanerstellung
- Externe Schnittstellen (z. B. zu anderen Schulverwaltungssystemen)

Der Fokus liegt ausschließlich auf der organisatorischen Planung
und Zuweisung von Dolmetschern zu bestehenden Unterrichtseinheiten.

---

## Technologischer Rahmen

- Programmiersprache: Java 21 (LTS)
- GUI: JavaFX 21.0.6
- Architektur: Model-View-Controller (MVC)
- Build-System: Maven
- Datenbank: MySQL (Port 3324 via WSL)
- Datenbankzugriff: JDBC (mysql-connector-java 8.0.33)
- Passwortverschlüsselung: BCrypt (jbcrypt 0.4)
- Versionsverwaltung: Git & GitHub

---

## Zusammenfassung

Projekt_DoIT stellt eine strukturierte, nachvollziehbare
und erweiterbare Lösung zur Dolmetscher-Einsatzplanung dar.

Durch die klare Trennung von Rollen, Datenstruktur und
Anwendungslogik entsteht ein wartbares System,
das reale organisatorische Anforderungen im schulischen Umfeld abbildet.