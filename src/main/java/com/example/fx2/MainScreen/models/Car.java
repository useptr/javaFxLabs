package com.example.fx2.MainScreen.models;

import javafx.scene.image.Image;

import java.util.Random;

public class Car extends Vehicle{
    private static Image image;
    public static Image getImage() {
        return image;
    }

    public static void setImage(Image image) {
        Car.image = image;
    }
private static String imagePath;
    public static String getImagePath() {
    return imagePath;
}
    public static void setImagePath(String imagePath) {
        Car.imagePath = imagePath;
    }
    public Car () {
        super.vehicleBehaviour = new MoveAlongXAxis();
        Random rand = new Random();
        double rangeMin = 1;
        double rangeMax = 8;
        super.step = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
        super.xDirection = 1;
        super.yDirection = 0;
//        imagePath = "/assets/car.png";
//        image = new Image(getClass().getResource(imagePath).toString(), 50, 50, false, false);
    }
}
