package com.example.fx2;

import com.example.fx2.MainScreen.MainScreenController;
import com.example.fx2.MainScreen.models.Habitat;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 760, 500);
        scene.getStylesheets().add("style.css");

        MainScreenController mainScreenController = fxmlLoader.getController();
        scene.setOnKeyPressed(mainScreenController);
        Habitat.initialize(mainScreenController.getHabitatPane().getPrefWidth(),mainScreenController.getHabitatPane().getPrefHeight());
        mainScreenController.setHabitatModel(Habitat.getHabitat());

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}