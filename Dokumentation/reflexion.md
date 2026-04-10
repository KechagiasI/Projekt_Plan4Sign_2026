# Projektauswertung und Reflexion – Projekt_DoIT

---

## 1. Projektzusammenfassung

Ziel dieses Projekts war die Entwicklung einer Desktop-Anwendung
zur strukturierten Planung und Organisation von Dolmetschereinsätzen
im schulischen Umfeld.

Es wurde eine JavaFX-Desktop-Anwendung mit MySQL-Datenbankanbindung entwickelt,
die ein rollenbasiertes Login-System, eine Administrator-Oberfläche zur
Dolmetscher-Zuweisung sowie individuelle Ansichten für Dolmetscher und
Teilnehmer umfasst.

Die Anwendung richtet sich an Administratoren, Gebärdensprachdolmetscher
und hörgeschädigte Teilnehmer einer Berufsschule und ersetzt die bisherige
manuelle Planung durch eine strukturierte, softwaregestützte Lösung.

---

## 2. Was hat gut funktioniert?

- Die Datenbankstruktur war von Anfang an gut durchdacht –
  alle Entitäten, Beziehungen und Constraints wurden sorgfältig modelliert.

- Die MVC-Architektur hat die Entwicklung übersichtlich gehalten
  und eine klare Trennung zwischen Datenzugriff, Logik und Oberfläche ermöglicht.

- Die rollenbasierte Navigation hat reibungslos funktioniert –
  nach dem Login wird der Benutzer automatisch zur richtigen Ansicht weitergeleitet.

- Die Implementierung des BCrypt-Passwort-Hashings verlief problemlos
  und sorgt für eine sichere Speicherung der Anmeldedaten.

- Die DAO-Struktur mit PreparedStatement hat eine saubere und
  sichere Datenbankanbindung gewährleistet.

- Die Verfügbarkeits- und Konfliktprüfung konnten erfolgreich
  in die Zuweisungslogik integriert werden.

---

## 3. Was hätte besser gemacht werden können?

- Die Datenbankstruktur für die Beziehung zwischen Fach und Bereich
  hätte von Anfang an als n:m-Beziehung modelliert werden sollen,
  um doppelte Einträge zu vermeiden. Dies wurde erst im späteren
  Projektverlauf erkannt.

- Die UI-Gestaltung hätte früher geplant werden sollen.
  Das responsive Layout sowie die Abstände wurden erst gegen Ende
  des Projekts optimiert, was zusätzlichen Aufwand verursacht hat.

- Die Verwendung von SceneBuilder hat unerwartete Probleme verursacht –
  automatische Anpassungen im FXML (z.B. eine Änderung der JavaFX-Version
  von 21 auf 25) führten zu Startfehlern der Anwendung, deren Ursache
  erst nach erheblichem Zeitaufwand identifiziert werden konnte.
  Für zukünftige Projekte sollte daher ein alternatives UI-Tool
  evaluiert werden.

- Ein Projekt dieser Komplexität wäre im Team effizienter umsetzbar
  gewesen. Die gleichzeitige Bearbeitung von Datenbankdesign,
  Anwendungslogik, Benutzeroberfläche und Dokumentation durch eine
  einzelne Person erforderte einen hohen Koordinationsaufwand.
  In einem Team hätte jedes Mitglied einen spezifischen Bereich
  übernehmen können – z.B. Datenbankdesign, Dateneingabe oder
  UI-Entwicklung – was die Qualität der einzelnen Bereiche
  erhöht hätte.

- Die manuelle Dateneingabe direkt in die Datenbank war zeitaufwendig
  und fehleranfällig. Eine Administrationsoberfläche zur Datenverwaltung
  innerhalb der Anwendung hätte diesen Prozess erheblich vereinfacht
  und die Komplexität für den Administrator reduziert.

- Die Administratoroberfläche ist in der aktuellen Version auf die
  Dolmetscher-Zuweisung beschränkt. Funktionen wie das Ändern von
  Unterrichtsdaten, das Hinzufügen neuer Klassen oder Fächer sowie
  eine vollständige Benutzerverwaltung wären wünschenswert gewesen,
  hätten jedoch den zeitlichen Rahmen des Projekts überschritten.

- Eine Registrierungsfunktion im Login-Fenster wäre eine sinnvolle
  Erweiterung gewesen, um neue Benutzer (Dolmetscher, Teilnehmer,
  Administratoren) direkt über die Anwendung anlegen zu können,
  ohne die Datenbank manuell bearbeiten zu müssen.

- Jede Änderung an der Anwendung erforderte entsprechende Anpassungen
  an mehreren Stellen (Model, DAO, Controller, View), was den
  Entwicklungsaufwand erhöht hat. Eine bessere Vorplanung der
  Abhängigkeiten hätte diesen Aufwand reduzieren können.

- Die Funktionstests hätten begleitend zur Entwicklung durchgeführt
  werden sollen, anstatt sie am Ende zu konzentrieren.

- Der UNIQUE-Constraint für die Fach-Tabelle hätte bereits beim
  Datenbankdesign berücksichtigt werden sollen, um Duplikate
  von Beginn an zu verhindern.

- Die Dokumentation hätte kontinuierlicher während der Entwicklung
  gepflegt werden sollen, anstatt sie nachträglich zu ergänzen.
---

## 4. Was habe ich gelernt?

**Technisch:**

- JavaFX und FXML: Aufbau von Benutzeroberflächen nach dem MVC-Prinzip,
  Verknüpfung von FXML-Elementen mit Java-Controllern über @FXML-Annotationen
  sowie rollenbasierte Navigation zwischen Views.

- JDBC und SQL: Datenbankanbindung über JDBC, Implementierung von
  PreparedStatements zum Schutz vor SQL-Injection, JOIN-Abfragen
  für die UI-Darstellung sowie korrekter Umgang mit NULL-Werten in SQL-Abfragen
  über getObject().

- BCrypt-Passwort-Hashing: Sichere Speicherung von Passwörtern
  durch den Einsatz von BCrypt mit Cost Factor 12 sowie
  Implementierung einer Verifikationsmethode beim Login.

- Dependency Injection in JavaFX: Übergabe von Objekten zwischen
  Controllern über Setter-Methoden nach dem Laden einer View
  durch den FXMLLoader.

- Singleton-Pattern: Implementierung einer einzigen Datenbankverbindung
  für die gesamte Anwendungslaufzeit.

- Git und GitHub: Versionsverwaltung mit regelmäßigen Commits,
  Verknüpfung des lokalen Repositories mit einem Remote-Repository
  sowie strukturierte Commit-Nachrichten.

**Methodisch:**

- Projektplanung und -strukturierung: Aufteilung des Projekts
  in Wochen mit klaren Zielen und Ergebnissen.

- Datenbankdesign: Modellierung eines relationalen Datenmodells
  mit Entitäten, Beziehungen, Foreign Keys und Constraints.

- Dokumentation: Strukturierte technische Dokumentation
  parallel zur Entwicklung, einschließlich Architekturentscheidungen
  und Begründungen.

- Fehleranalyse und Debugging: Systematische Analyse von
  Laufzeitfehlern (z.B. NullPointerException, Konfigurationsfehler)
  und deren gezielte Behebung.

- Selbstständiges Arbeiten: Umsetzung eines vollständigen
  Softwareprojekts von der Analyse bis zur fertigen Anwendung
  als Einzelperson.

---

## 5. Abweichungen vom Projektplan

- Der Zeitaufwand für die Datenbankimplementierung und die Dateneingabe
  war größer als ursprünglich geplant, da alle Testdaten manuell
  in die Datenbank eingegeben werden mussten.

- Die Implementierung der Konfliktprüfung benötigte mehr Zeit als
  vorgesehen, da zwei separate Prüfungen (Verfügbarkeit und
  Zeitüberschneidung) entwickelt und aufeinander abgestimmt
  werden mussten.

- Die UI-Optimierung (responsives Layout, Abstände, Logo) wurde
  ursprünglich nicht als eigenständiger Aufgabenbereich eingeplant
  und verursachte zusätzlichen Zeitaufwand in der fünften Projektwoche.

- Die Behebung von Konfigurationsfehlern durch den SceneBuilder
  (JavaFX-Version 21 → 25) nahm ungeplant fast einen
  gesamten Arbeitstag in Anspruch.

- Die n:m-Beziehung zwischen Fach und Bereich wurde bewusst
  nicht umgesetzt, da die erforderlichen Änderungen an DAO,
  Model und Controller den zeitlichen Rahmen des Projekts
  überschritten hätten. Die aktuelle Lösung mit einem
  UNIQUE-Constraint wurde daher als pragmatischer Kompromiss gewählt.

- Trotz dieser Abweichungen konnten alle Kernfunktionen
  des Projekts innerhalb des geplanten Zeitrahmens umgesetzt werden.

---

## 6. Funktionstests


| Testfall                               | Beschreibung                                       | Erwartet                                      | Ergebnis    |
|----------------------------------------|----------------------------------------------------|-----------------------------------------------|-------------|
| Login – gültige Daten                  | Anmeldung mit korrektem Benutzernamen und Passwort | Weiterleitung zur rollenspezifischen View     | Erfolgreich |
| Login – ungültige Daten                | Anmeldung mit falschem Passwort                    | Fehlermeldung wird angezeigt                  | Erfolgreich |
| Login – leere Felder                   | Anmeldung ohne Eingabe                             | Fehlermeldung "Bitte alle Felder ausfüllen"   | Erfolgreich |
| Dolmetscher zuweisen – verfügbar       | Zuweisung eines verfügbaren Dolmetschers           | Zuweisung wird gespeichert                    | Erfolgreich |
| Dolmetscher zuweisen – nicht verfügbar | Zuweisung eines nicht verfügbaren Dolmetschers     | Warnung wird angezeigt                        | Erfolgreich |
| Dolmetscher zuweisen – Zeitkonflikt    | Zuweisung eines bereits eingeplanten Dolmetschers  | Bestätigungsdialog wird angezeigt             | Erfolgreich |
| Dolmetscher entfernen                  | Zuweisung aufheben                                 | DolmetscherID wird auf NULL gesetzt           | Erfolgreich |
| Abmelden                               | Logout-Button drücken                              | Weiterleitung zur Login-Ansicht               | Erfolgreich |
| DolmetscherView                        | Login als Dolmetscher                              | Eigene Unterrichtseinheiten werden angezeigt  | Erfolgreich |
| TeilnehmerView                         | Login als Teilnehmer                               | Stundenplan der eigenen Klasse wird angezeigt | Erfolgreich |

Alle definierten Testfälle wurden erfolgreich durchgeführt.

---

## 7. Ausblick – Mögliche Erweiterungen

Die aktuelle Version der Anwendung bietet eine solide Grundlage,
die in zukünftigen Versionen erheblich erweitert werden könnte:

**Benutzerverwaltung:**
- Eine Registrierungsfunktion im Login-Fenster, um neue Benutzer
  (Dolmetscher, Teilnehmer, Administratoren) direkt über die
  Anwendung anlegen zu können, ohne die Datenbank manuell
  bearbeiten zu müssen.

**Administratoroberfläche:**
- Vollständige CRUD-Funktionen für alle Entitäten (Klassen,
  Fächer, Unterrichtseinheiten, Benutzer) direkt in der Anwendung,
  um die Abhängigkeit von der direkten Datenbankbearbeitung
  vollständig zu eliminieren.
- Möglichkeit zur Änderung von Unterrichtsdaten wie Datum,
  Uhrzeit und Fach direkt über die Benutzeroberfläche.

**Verfügbarkeit und Benachrichtigungen:**
- Dolmetscher und Teilnehmer könnten ihre Anwesenheit oder
  Abwesenheit selbst über die Anwendung melden, wodurch der
  Administrator automatisch informiert wird und den Einsatzplan
  entsprechend anpassen kann.
- Ein automatisches Benachrichtigungssystem per E-Mail bei
  Zuweisung oder Änderung eines Dolmetschers.

**Datenbankstruktur:**
- Vollständige Implementierung der n:m-Beziehung zwischen Fach
  und Bereich für eine konsistentere und flexiblere Datenstruktur.

**Darstellung:**
- Eine Kalenderansicht anstelle der TableView für eine
  übersichtlichere Darstellung des Stundenplans.
- Eine Exportfunktion (PDF oder Excel) für den Stundenplan
  sowie den Dolmetscher-Einsatzplan.

**Allgemein:**
- Automatische Dolmetscher-Zuweisung basierend auf
  Verfügbarkeit und Qualifikation.
- Die Möglichkeiten zur Weiterentwicklung dieser Anwendung
  sind vielfältig – die aktuelle Version stellt eine
  funktionsfähige Basis dar, die schrittweise ausgebaut
  werden kann.

**Datenbankinfrastruktur und zentrale Serveranbindung:**
- In der aktuellen Version läuft die Datenbank lokal über WSL
  auf dem Entwicklungsrechner. Für einen produktiven Einsatz
  in der Schule wäre eine zentrale Datenbankanbindung an den
  Schulserver erforderlich, sodass alle Benutzer auf dieselbe
  Datenbasis zugreifen können.

- Die Stundenplanung erfolgt derzeit manuell durch direkte
  Datenbankeinträge. Eine Schnittstelle zum zentralen
  Stundenplanungssystem der Schule wäre ideal, sodass jede
  Änderung am Stundenplan automatisch im Einsatzplan der
  Dolmetscher sowie im Stundenplan der Teilnehmer aktualisiert würde.

- Durch die Anbindung an einen zentralen Server wären alle
  Änderungen in Echtzeit für alle Benutzer sichtbar –
  ohne manuelle Eingriffe in die Datenbank. Dies würde die
  Arbeit des Administrators erheblich vereinfachen und die
  Fehleranfälligkeit durch manuelle Dateneingaben eliminieren.


---

## 8. Fazit

Das Projektziel wurde erfolgreich erreicht: Es wurde eine
funktionsfähige Desktop-Anwendung zur strukturierten Planung
und Organisation von Dolmetschereinsätzen im schulischen Umfeld
entwickelt.

Die Anwendung deckt alle geplanten Kernfunktionen ab – ein
rollenbasiertes Login-System, eine Administratoroberfläche mit
Verfügbarkeits- und Konfliktprüfung sowie individuelle Ansichten
für Dolmetscher und Teilnehmer.

Trotz der Herausforderungen, die ein Einzelprojekt dieser
Komplexität mit sich bringt, bin ich mit dem Ergebnis zufrieden.
Die Anwendung ist lauffähig, gut strukturiert und bietet eine
solide Grundlage für zukünftige Erweiterungen.

Aus diesem Projekt nehme ich wertvolle Erfahrungen in den
Bereichen Java, JavaFX, JDBC, Datenbankdesign und
Projektdokumentation mit. Besonders die selbstständige
Umsetzung eines vollständigen Softwareprojekts – von der
Analyse bis zur fertigen Anwendung – hat meine technischen
und methodischen Fähigkeiten erheblich erweitert.