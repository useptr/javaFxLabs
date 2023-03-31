package com.example.fx2.MainScreen.models;

public class Car extends Vehicle{
    public Car (double x, double y) {
        String imagePath = "/assets/car.png";
        super.setImagePath(imagePath);
        super.setX(x);
        super.setY(y);
		System.out.println("loooooh");
    }
}
