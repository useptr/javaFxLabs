package com.example.fx2.MainScreen.models;

import com.example.fx2.MainScreen.Behaviour.IBehaviour;

public abstract class Vehicle {
    IBehaviour vehicleBehaviour;
    private static double maxX;
    private static double maxY;
    private double x;
    private double y;
    private int id;
    private int size = 50;
    private int timeOfBirth;
    private int lifetime;
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
    public synchronized void performBehaviour() {
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
    public int getSize() {
        return size;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
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
