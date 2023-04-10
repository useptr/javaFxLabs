package com.example.fx2.MainScreen.models;

import javafx.geometry.Point2D;

public class MoveAlongXAxis implements IBehaviour {
    @Override
    public void updateCoordinates(Vehicle vehicle) {
//        Vehicle newVehicle = vehicle;
        if (vehicle.getxDirection() == 1) { // up
            if (vehicle.getX() - vehicle.getStep() <= 0) {
                vehicle.setX(0);
                vehicle.setxDirection(-1);
            } else {
                vehicle.setX(vehicle.getX() - vehicle.getStep());
            }
        } else if (vehicle.getxDirection() == -1) { // down
            if (vehicle.getX() + vehicle.getStep() >= Vehicle.getMaxX()) {
                vehicle.setX(Vehicle.getMaxX());
                vehicle.setxDirection(1);
            } else {
                vehicle.setX(vehicle.getX() + vehicle.getStep());
            }
        }
//        return newVehicle;
    }

    /*public MoveAlongXAxisAI(int xDirection, int yDirection) {
        super(xDirection, yDirection);
    }

    @Override
    public synchronized Point2D getNewCoordinates(double x, double y) {
        double newX = x;
        double newY = y;
        if (super.getxDirection() == 1) { // up
            if (x - super.getStep() <= 0) {
                newX = 0;
                super.setxDirection(-1);
            } else {
                newX = x - super.getStep();
            }
        } else if (super.getxDirection() == -1) { // down
            if (x + super.getStep() >= super.getMaxX()) {
                newX = super.getMaxX();
                super.setxDirection(1);
            } else {
                newX = x + super.getStep();
            }
        }
        return new Point2D(newX, newY);
    }*/
}
