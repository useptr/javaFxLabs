package com.example.fx2;

import com.example.fx2.MainScreen.AIController;
import com.example.fx2.MainScreen.MainScreenController;
import com.example.fx2.MainScreen.models.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 760, 550);
        scene.getStylesheets().add("style.css");

        MainScreenController mainScreenController = fxmlLoader.getController();
        scene.setOnKeyPressed(mainScreenController);
        VehiclesCollections.initialize();
        Habitat.initialize(VehiclesCollections.getVehiclesCollections(), mainScreenController.getHabitatPane().getPrefWidth(),mainScreenController.getHabitatPane().getPrefHeight());
        mainScreenController.setHabitatModel(Habitat.getHabitat());
//        BaseAI carAI = new CarAI();
//        carAI.start();
//        carAI.startAI();
        AIController aiController = new AIController();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}