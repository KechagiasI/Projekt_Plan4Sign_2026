module com.brh.projekt_plan4sign_2026 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.brh.projekt_plan4sign_2026 to javafx.fxml;
    exports com.brh.projekt_plan4sign_2026;
}