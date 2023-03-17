package com.example.fx2.MainScreen.models;

public abstract class Vehicle {
    IBehaviour iBehaviour;
    private double x;
    private double y;
    private int size;
    private String imagePath;
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public void performBehaviour() {
        iBehaviour.moveBehaviour();
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
    public void setIBehaviour(IBehaviour iBehaviour) {
        this.iBehaviour =iBehaviour;
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
    public String getImagePath() {
        return imagePath;
    }
}
