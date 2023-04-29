package com.example.fx2;

import com.example.fx2.MainScreen.MainScreenController;
import com.example.fx2.MainScreen.models.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Car.setImagePath("/assets/car.png");
//        System.out.println(getClass().getResource(Car.getImagePath()).toString());
        Car.setImage(new Image(getClass().getResource(Car.getImagePath()).toString(), 50, 50, false, false));
        Motorcycle.setImagePath("/assets/moto.png");
        Motorcycle.setImage(new Image(getClass().getResource(Motorcycle.getImagePath()).toString(), 50, 50, false, false));

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("style.css");



        MainScreenController mainScreenController = fxmlLoader.getController();
        mainScreenController.setStage(stage);
        stage.setOnCloseRequest(windowEvent -> {
            try {
                mainScreenController.uploadSimulationSettings();
                Platform.exit();
                System.exit(0);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        scene.setOnKeyPressed(mainScreenController::handle);
        Habitat habitat = Habitat.getInstance();
        mainScreenController.setHabitatModel(habitat);
        habitat.setWidthAndHeight(mainScreenController.getHabitatPane().getWidth(), mainScreenController.getHabitatPane().getHeight());


        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}