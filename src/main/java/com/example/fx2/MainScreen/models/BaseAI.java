package com.example.fx2.MainScreen.models;

import com.example.fx2.MainScreen.views.VehicleImage;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Random;

public abstract class BaseAI extends Thread {
    private volatile boolean isRun = false;
    public abstract void updateCoordinates();
    @Override
    public void run() {
        while (true) {
            System.out.println(getName());
            if (isRun) {
                updateCoordinates();
                try {
                    Thread.sleep(1000 / 120);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public synchronized void startAI() {
        if (!isRun) {
            isRun = true;
            notify();
        }
    }
    public synchronized void stopAI() {
        if (isRun) {
            isRun = false;
        }
    }
    public synchronized boolean isRunning() {
        return isRun;
    }
    public synchronized void setRun(boolean state) {
        isRun = state;
    }

//    int xDirection;
//    int yDirection;
//    double step;
//    private static double maxX;
//    private static double maxY;
//    public BaseAI(int xDirection, int yDirection) {
//        this.xDirection = xDirection;
//        this.yDirection = yDirection;
//
//        Random rand = new Random();
//        double rangeMin = 1;
//        double rangeMax = 8;
//        step = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
//    }
//    public abstract Point2D getNewCoordinates(double x, double y);
//    public static void setMaxXAndY(double x, double y) {
//        maxX = x;
//        maxY = y;
//    }
//
//    public int getxDirection() {
//        return xDirection;
//    }
//
//    public void setxDirection(int xDirection) {
//        this.xDirection = xDirection;
//    }
//
//    public int getyDirection() {
//        return yDirection;
//    }
//
//    public void setyDirection(int yDirection) {
//        this.yDirection = yDirection;
//    }
//
//    public double getStep() {
//        return step;
//    }
//
//    public void setStep(double step) {
//        this.step = step;
//    }
//
//    public static double getMaxX() {
//        return maxX;
//    }
//
//    public static void setMaxX(double maxX) {
//        BaseAI.maxX = maxX;
//    }
//
//    public static double getMaxY() {
//        return maxY;
//    }
//
//    public static void setMaxY(double maxY) {
//        BaseAI.maxY = maxY;
//    }
}
