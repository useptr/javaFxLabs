package com.example.fx2.MainScreen.models;

public class Motorcycle extends Vehicle{
    public Motorcycle() {
//        super.setGenerationProbability(60);
//        super.setGenerationTime(5);
        String imagePath = "/assets/moto.png";
        super.setImagePath(imagePath);
        super.setVehicleBehaviour(new MoveAlongYAxisAI(1, 0));
//        super.setX(x);
//        super.setY(y);
    }

}
