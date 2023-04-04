package com.example.fx2.MainScreen.models;

import javafx.geometry.Point2D;

public class MoveAlongXAxisAI extends BaseAI {

    public MoveAlongXAxisAI(int xDirection, int yDirection) {
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
    }
}
