# Projektauswertung & Reflexion – Projekt_DoIT

---

## 1. Projektzusammenfassung

<!-- 
Schreibe hier 3-5 Sätze über das Projekt:
- Was war das Ziel des Projekts?
Ziel dieses Projekts ist die Entwicklung einer Desktop-Anwendung
zur strukturierten Planung und Organisation von Dolmetschereinsätzen
im schulischen Umfeld.

- Was wurde entwickelt?
- Für wen ist die Anwendung gedacht?
Beispiel: "Ziel des Projekts war die Entwicklung einer Desktop-Anwendung zur 
strukturierten Planung von Dolmetschereinsätzen im schulischen Umfeld..."
-->

---

## 2. Was hat gut funktioniert?

<!-- 
Schreibe hier, was technisch und organisatorisch gut gelaufen ist.
Mögliche Punkte:
- Die Datenbankstruktur war von Anfang an gut durchdacht
- Die MVC-Architektur hat die Entwicklung übersichtlich gehalten
- Die rollenbasierte Navigation hat reibungslos funktioniert
- Die Zusammenarbeit mit dem Betreuer war strukturiert
Sei konkret – nenne spezifische Teile des Projekts.
-->

---

## 3. Was hätte besser gemacht werden können?

<!-- 
Schreibe hier ehrlich, was du anders machen würdest.
Mögliche Punkte:
- Frühere Implementierung der Tests
- Bessere Planung der Datenbankstruktur (z.B. n:m für Fach-Bereich)
- Frühere Überlegung zur UI-Gestaltung
- Mehr Zeit für die Dokumentation eingeplant
Die IHK erwartet hier Selbstreflexion – zeige, dass du aus Fehlern lernst.
-->

---

## 4. Was habe ich gelernt?

<!-- 
Schreibe hier, welche neuen Kenntnisse und Fähigkeiten du erworben hast.
Trenne in technische und methodische Lernziele:

Technisch:
- JavaFX und FXML (MVC-Architektur)
- JDBC und SQL (JOINs, PreparedStatement, NULL-Handling)
- BCrypt-Passwort-Hashing
- Dependency Injection in JavaFX
- Git und GitHub

Methodisch:
- Projektplanung und -strukturierung
- Dokumentation während der Entwicklung
- Fehleranalyse und Debugging
-->

---

## 5. Abweichungen vom Projektplan

<!-- 
Schreibe hier, ob und warum es Abweichungen vom ursprünglichen Plan gab.
Mögliche Punkte:
- Welche Aufgaben haben mehr Zeit gebraucht als geplant?
- Welche Funktionen wurden vereinfacht oder weggelassen?
- Welche unvorhergesehenen Probleme sind aufgetreten?
Beispiel: "Die Implementierung der Konfliktprüfung hat mehr Zeit benötigt 
als ursprünglich geplant, da..."
Falls es keine wesentlichen Abweichungen gab, schreibe das ebenfalls kurz.
-->

---

## 6. Funktionstests

<!-- 
Dokumentiere hier die durchgeführten Tests.
Beschreibe für jede Hauptfunktion:
- Was wurde getestet?
- Was war das erwartete Ergebnis?
- Was war das tatsächliche Ergebnis?
-->

| Testfall                               | Beschreibung                                       | Erwartet                                      | Ergebnis  |
|----------------------------------------|----------------------------------------------------|-----------------------------------------------|-----------|
| Login – gültige Daten                  | Anmeldung mit korrektem Benutzernamen und Passwort | Weiterleitung zur rollenspezifischen View     |           |
| Login – ungültige Daten                | Anmeldung mit falschem Passwort                    | Fehlermeldung wird angezeigt                  |           |
| Login – leere Felder                   | Anmeldung ohne Eingabe                             | Fehlermeldung "Bitte alle Felder ausfüllen"   |           |
| Dolmetscher zuweisen – verfügbar       | Zuweisung eines verfügbaren Dolmetschers           | Zuweisung wird gespeichert                    |           |
| Dolmetscher zuweisen – nicht verfügbar | Zuweisung eines nicht verfügbaren Dolmetschers     | Warnung wird angezeigt                        |           |
| Dolmetscher zuweisen – Zeitkonflikt    | Zuweisung eines bereits eingeplanten Dolmetschers  | Bestätigungsdialog wird angezeigt             |           |
| Dolmetscher entfernen                  | Zuweisung aufheben                                 | DolmetscherID wird auf NULL gesetzt           |           |
| Abmelden                               | Logout-Button drücken                              | Weiterleitung zur Login-Ansicht               |           |
| DolmetscherView                        | Login als Dolmetscher                              | Eigene Unterrichtseinheiten werden angezeigt  |           |
| TeilnehmerView                         | Login als Teilnehmer                               | Stundenplan der eigenen Klasse wird angezeigt |           |

---

## 7. Ausblick – Mögliche Erweiterungen

<!-- 
Schreibe hier, was in einer zukünftigen Version verbessert oder ergänzt werden könnte.
Mögliche Punkte:
- Benachrichtigungssystem (E-Mail bei Zuweisung)
- Kalenderansicht statt TableView
- Exportfunktion (PDF, Excel)
- Vollständige n:m Beziehung zwischen Fach und Bereich
- Administratoroberfläche zur Verwaltung von Benutzern
- Automatische Dolmetscher-Zuweisung basierend auf Verfügbarkeit
-->

---

## 8. Fazit

<!-- 
Schreibe hier ein abschließendes Fazit in 3-5 Sätzen:
- Wurde das Projektziel erreicht?
- Bist du mit dem Ergebnis zufrieden?
- Was nimmst du aus diesem Projekt mit?
Das Fazit sollte positiv aber realistisch sein.
-->