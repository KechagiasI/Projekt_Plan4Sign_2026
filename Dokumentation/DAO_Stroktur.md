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

👉 Das Ist Wichtig!!!!

---

# 🔷 3. Pattern für alle Methoden

## 🟢 SELECT (getAll)

```java
Connection conn = DatabaseConnection.getConnection();
String sql = "SELECT * FROM Tabelle";
PreparedStatement stmt = conn.prepareStatement(sql);
ResultSet rs = stmt.executeQuery();

while(rs.next()) {
    // Daten lesen
}
```

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

| Methode | Verwendung |
|--------|----------|
| executeQuery() | SELECT |
| executeUpdate() | INSERT / DELETE / UPDATE |

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

# 🔷 6. Object Mapping

👉 DB → Java Object

```java
list.add(new Entity(id, name));
```

---

# 🔷 7. PreparedStatement (SEHR WICHTIG)

👉 Warum?

✔ Schutz vor SQL-Injection  
✔ Sicherer Code  
✔ Standard in echten Projekten

---

# 🔷 8. Singleton Connection

👉 Nur eine Verbindung im Programm

```java
Connection conn = DatabaseConnection.getConnection();
```

---

# 🔷 9. DAO Template (Copy & Reuse)

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

# 🔷 10. Wie denkst du richtig?

👉 Immer von hier Fängst du an:

```text
Was will ich mit der Datenbank machen?
```

---

# 🔷 11. Fehler vermeiden

❌ SQL im Controller  
❌ 100 Verbindungen öffnen  
❌ Statement statt PreparedStatement  
❌ Kein Mapping zu Objekten

---

# 🔷 12. Merksatz (Gold!)

```text
Connection → SQL → Statement → Parameter → Execute → Result
```



