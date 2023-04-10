package com.example.fx2.MainScreen.models;

import javafx.geometry.Point2D;

import java.util.Random;

public abstract class Vehicle {
    IBehaviour vehicleBehaviour;
//    BaseAI vehicleBehaviour;
    private static double maxX;
    private static double maxY;
    private double x;
    private double y;
    private int id;
    private int size = 50;
    private int timeOfBirth;
    private int lifetime;
    private String imagePath;
    int xDirection;
    int yDirection;
    public double getStep() {
        return step;
    }
    double step;
    public int getxDirection() {
        return xDirection;
    }

    public void setxDirection(int xDirection) {
        this.xDirection = xDirection;
    }

    public int getyDirection() {
        return yDirection;
    }

    public void setyDirection(int yDirection) {
        this.yDirection = yDirection;
    }
    public static double getMaxX() {
        return maxX;
    }

    public static void setMaxXAndMaxY(double maxX, double maxY) {
        Vehicle.maxX = maxX;
        Vehicle.maxY = maxY;
    }

    public static double getMaxY() {
        return maxY;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

//    public void setVehicleBehaviour(BaseAI vehicleBehaviour) {
//        this.vehicleBehaviour = vehicleBehaviour;
//    }

    public void performBehaviour() {
        vehicleBehaviour.updateCoordinates(this);
    }

    public void setSize(int size) {
        this.size = size;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setX(double x) {
        this.x = x;
    }
//    public void setIBehaviour(IBehaviour iBehaviour) {
//        this.iBehaviour =iBehaviour;
//    }
    public int getSize() {
        return size;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public String getImagePath() {
        return imagePath;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public int getTimeOfBirth() {
        return timeOfBirth;
    }

    public void setTimeOfBirth(int timeOfBirth) {
        this.timeOfBirth = timeOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
