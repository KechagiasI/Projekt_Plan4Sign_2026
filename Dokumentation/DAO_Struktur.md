# 📘 DAO Cheat Sheet – Java JDBC (Für immer merken)

---

# 🔷 1. Was ist ein DAO?

👉 DAO = Data Access Object

➡️ Verbindung zwischen Java und Datenbank

**Aufgabe:**
- Daten holen (SELECT)
- Daten speichern (INSERT)
- Daten löschen (DELETE)
- Daten ändern (UPDATE)

---

# 🔷 2. Grundlogik (IMMER gleich!)

```text
1. Connection holen
2. SQL schreiben
3. PreparedStatement erstellen
4. Werte setzen (Parameter)
5. Query ausführen
6. Ergebnis verarbeiten (nur bei SELECT)
```

👉 Wichtig: Diese Schritte gelten für alle DAO-Methoden.

---

# 🔷 3. Pattern für alle Methoden

## 🟢 SELECT (getAll) – ohne Filter

```java
Connection conn = DatabaseConnection.getConnection();
String sql = "SELECT * FROM Tabelle";
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(sql);

while(rs.next()) {
        // Daten lesen
        }
```

> **Hinweis:** `Statement` kann verwendet werden – im Projekt wird jedoch
> aus Konsistenz- und Sicherheitsgründen **immer** `PreparedStatement` genutzt.

---

## 🟢 SELECT (getByID / getByKlasse) – mit Filter

```java
Connection conn = DatabaseConnection.getConnection();
String sql = "SELECT * FROM Tabelle WHERE id = ?";
PreparedStatement stmt = conn.prepareStatement(sql);

stmt.setInt(1, id);
ResultSet rs = stmt.executeQuery();

while(rs.next()) {
    // Daten lesen
}
```

---

## 🟢 SELECT mit JOIN – für UI-Anzeige

👉 Wenn man lesbare Daten (Namen statt IDs) für die Oberfläche braucht,
verwendet man JOINs direkt im SQL.

```java
String sql = "SELECT u.UnterrichtID, u.date, u.starttime, u.endtime, " +
             "k.klassename, f.fachname, " +
             "CONCAT(d.firstname, ' ', d.lastname) AS dolmetschername, " +
             "CONCAT(t.firstname, ' ', t.lastname) AS teilnehmername " +
             "FROM Unterricht u " +
             "JOIN Klasse k ON u.KlasseID = k.KlasseID " +
             "JOIN Fach f ON u.FachID = f.FachID " +
             "LEFT JOIN Dolmetscher d ON u.DolmetscherID = d.DolmetscherID " +
             "LEFT JOIN Teilnehmer t ON t.KlasseID = u.KlasseID " +
             "WHERE u.DolmetscherID = ? " +
             "ORDER BY u.date, u.starttime";

Connection conn = DatabaseConnection.getConnection();
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setInt(1, dolmetscherID);
ResultSet rs = stmt.executeQuery();

while(rs.next()) {
    Unterricht u = new Unterricht(...);
    u.setKlassename(rs.getString("klassename"));
    u.setFachname(rs.getString("fachname"));
    u.setDolmetschername(rs.getString("dolmetschername"));
    u.setTeilnehmername(rs.getString("teilnehmername"));
}
```

> **Warum LEFT JOIN?**
> Ein `LEFT JOIN` stellt sicher, dass auch Unterrichtseinheiten
> ohne zugewiesenen Dolmetscher (`DolmetscherID = NULL`) angezeigt werden.
> Mit `INNER JOIN` würden diese Zeilen komplett wegfallen.

---

## 🔴 INSERT

```java
Connection conn = DatabaseConnection.getConnection();
String sql = "INSERT INTO Tabelle (name) VALUES (?)";
PreparedStatement stmt = conn.prepareStatement(sql);

stmt.setString(1, value);
stmt.executeUpdate();
```

---

## 🔴 DELETE

```java
Connection conn = DatabaseConnection.getConnection();
String sql = "DELETE FROM Tabelle WHERE id = ?";
PreparedStatement stmt = conn.prepareStatement(sql);

stmt.setInt(1, id);
stmt.executeUpdate();
```

---

# 🔷 4. Unterschied executeQuery vs executeUpdate

| Methode          | Verwendung                  |
|------------------|-----------------------------|
| executeQuery()   | SELECT                      |
| executeUpdate()  | INSERT / DELETE / UPDATE    |

---

# 🔷 5. ResultSet verstehen

👉 ResultSet = Tabelle von Daten

```java
while(resultSet.next()) {
    int id = resultSet.getInt("ID");
    String name = resultSet.getString("name");
}
```

---

# 🔷 6. NULL-Werte sicher lesen (SEHR WICHTIG)

👉 Wenn ein Datenbankfeld NULL sein kann (z. B. `DolmetscherID`),
darf man es nicht direkt mit `getInt()` lesen – das würde 0 zurückgeben.

**Richtiger Weg:**

```java
Integer dolmetscherID = resultSet.getObject("DolmetscherID") != null
        ? resultSet.getInt("DolmetscherID")
        : null;
```

> **Warum `Integer` statt `int`?**
> `int` kann keinen `null`-Wert speichern.
> `Integer` (Wrapper-Klasse) kann `null` sein –
> was dem NULL-Wert in der Datenbank entspricht.

---

# 🔷 7. Object Mapping

👉 DB → Java Object

**Einfaches Mapping (nur Basisdaten):**
```java
list.add(new Entity(id, name));
```

**Erweitertes Mapping (Basisdaten + Anzeige-Daten über Setter):**
```java
Unterricht u = new Unterricht(unterrichtID, date, starttime, endtime, klasseID, fachID, dolmetscherID);
u.setKlassename(resultSet.getString("klassename"));
u.setFachname(resultSet.getString("fachname"));
u.setDolmetschername(resultSet.getString("dolmetschername"));
u.setTeilnehmername(resultSet.getString("teilnehmername"));
```

> **Warum zwei Schritte?**
> Der Konstruktor enthält nur Pflichtfelder (Datenbankstruktur).
> Zusätzliche Anzeige-Daten (aus JOINs) werden separat über Setter gesetzt.
> So bleibt der Konstruktor übersichtlich und das Modell flexibel erweiterbar.

---

# 🔷 8. PreparedStatement (SEHR WICHTIG)

👉 Warum?

✔ Schutz vor SQL-Injection  
✔ Sicherer Code  
✔ Standard in echten Projekten

---

# 🔷 9. Singleton Connection

👉 Nur eine Verbindung im Programm

```java
Connection conn = DatabaseConnection.getConnection();
```

> **Hinweis:** Das Singleton-Muster wird **projektintern** verwendet.
> In größeren Anwendungen wird ein Connection Pool (z. B. HikariCP) empfohlen.

---

# 🔷 10. DAO Template (Copy & Reuse)

```java
public class EntityDAO {

    public List<Entity> getAll() throws SQLException {
        List<Entity> list = new ArrayList<>();

        String sql = "SELECT * FROM TABLE";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while(rs.next()) {
            list.add(new Entity(...));
        }

        return list;
    }

    public Entity getByID(int id) throws SQLException {
        String sql = "SELECT * FROM TABLE WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if(rs.next()) {
            return new Entity(...);
        }

        return null;
    }

    public void insert(Entity entity) throws SQLException {
        String sql = "INSERT INTO TABLE (...) VALUES (?)";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.set...
        stmt.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM TABLE WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}
```

---

# 🔷 11. Wie denkst du richtig?

👉 Immer von hier fängst du an:

```text
Was will ich mit der Datenbank machen?
```

---

# 🔷 12. Fehler vermeiden

❌ SQL im Controller  
❌ 100 Verbindungen öffnen  
❌ Statement statt PreparedStatement (bei Parametern!)  
❌ Kein Mapping zu Objekten  
❌ `getInt()` direkt auf NULL-fähige Felder → immer `getObject()` prüfen  
❌ JOIN-Daten direkt im Controller aufbereiten → gehört ins DAO

---

# 🔷 13. Merksatz (Gold!)

```text
Connection → SQL → Statement → Parameter → Execute → Result
```

---

#### Referenzen

1. `https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html` – Oracle Java Tutorials: Using Prepared Statements
2. `https://docs.oracle.com/javase/8/docs/api/java/sql/PreparedStatement.html` – Oracle JavaDoc: PreparedStatement (Java SE 8)
3. `https://jenkov.com/tutorials/jdbc/preparedstatement.html` – Jenkov Tutorials: Java JDBC PreparedStatement
4. `https://stackoverflow.com/questions/2839321/connect-java-to-a-mysql-database` – Stack Overflow: Connect Java to a MySQL Database
5. `https://stackoverflow.com/questions/5881834/getting-integer-object-from-resultset` – Stack Overflow: Getting Integer (nullable) from ResultSet mit getObject()