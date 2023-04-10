package com.example.fx2.MainScreen.models;

import java.util.Random;

public class Motorcycle extends Vehicle{
    public Motorcycle() {
        super.vehicleBehaviour = new MoveAlongYAxis();
        Random rand = new Random();
        double rangeMin = 1;
        double rangeMax = 8;
        super.step = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
        super.xDirection = 0;
        super.yDirection = 1;
        String imagePath = "/assets/moto.png";
        super.setImagePath(imagePath);
//        super.setVehicleBehaviour(new MoveAlongYAxisAI(1, 0));
    }

}
