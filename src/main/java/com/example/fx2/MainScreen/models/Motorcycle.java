package com.example.fx2.MainScreen.models;

import com.example.fx2.MainScreen.Behaviour.MoveAlongYAxis;
import javafx.scene.image.Image;

import java.util.Random;

public class Motorcycle extends Vehicle{
    private static Image image;
    public static Image getImage() {
        return image;
    }
    public static void setImage(Image image) {
        Motorcycle.image = image;
    }
    private static String imagePath;
    public static String getImagePath() {
        return imagePath;
    }
    public static void setImagePath(String imagePath) {
        Motorcycle.imagePath = imagePath;
    }
    public Motorcycle() {
        super.vehicleBehaviour = new MoveAlongYAxis();
        Random rand = new Random();
        double rangeMin = 1;
        double rangeMax = 4;
        super.step = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
        super.xDirection = 0;
        super.yDirection = 1;
//        imagePath = "/assets/moto.png";
//        image = new Image(getClass().getResource(imagePath).toString(), 50, 50, false, false);
    }

}
