package com.example.fx2.MainScreen.models;

public class Car extends Vehicle{
    public Car () {
        String imagePath = "/assets/car.png";
        super.setImagePath(imagePath);
        super.setVehicleBehaviour(new MoveAlongXAxisAI(1, 0));
//        super.setX(x);
//        super.setY(y);
    }
}
