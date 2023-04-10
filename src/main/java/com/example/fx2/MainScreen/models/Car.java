package com.example.fx2.MainScreen.models;

import java.util.Random;

public class Car extends Vehicle{
    public Car () {
        super.vehicleBehaviour = new MoveAlongXAxis();
        Random rand = new Random();
        double rangeMin = 1;
        double rangeMax = 8;
        super.step = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
        super.xDirection = 1;
        super.yDirection = 0;

        String imagePath = "/assets/car.png";
        super.setImagePath(imagePath);
    }
}
