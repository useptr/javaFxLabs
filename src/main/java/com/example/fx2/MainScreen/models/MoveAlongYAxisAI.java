package com.example.fx2.MainScreen.models;

import javafx.geometry.Point2D;

public class MoveAlongYAxisAI extends BaseAI{
    public MoveAlongYAxisAI(int xDirection, int yDirection) {
        super(xDirection, yDirection);
    }

    @Override
    public synchronized Point2D getNewCoordinates(double x, double y) {
        double newX = x;
        double newY = y;
        if (super.getxDirection() == 1) { // left
            if (y - super.getStep() <= 0) {
                newY = 0;
                super.setxDirection(-1);
            } else {
                newY = y - super.getStep();
            }
        } else if (super.getxDirection() == -1) { // right
            if (y + super.getStep() >= super.getMaxY()) {
                newY = super.getMaxY();
                super.setxDirection(1);
            } else {
                newY = y + super.getStep();
            }
        }
        return new Point2D(newX, newY);
    }
}
