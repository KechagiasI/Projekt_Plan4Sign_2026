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
    opens com.brh.projekt_plan4sign_2026.util to javafx.fxml;

    // exports: macht die Packages für andere Module sichtbar
    exports com.brh.projekt_plan4sign_2026;
    exports com.brh.projekt_plan4sign_2026.controller;
    exports com.brh.projekt_plan4sign_2026.util;
}