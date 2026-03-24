package com.brh.projekt_plan4sign_2026;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // Lädt die Login-Oberfläche als Startseite
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