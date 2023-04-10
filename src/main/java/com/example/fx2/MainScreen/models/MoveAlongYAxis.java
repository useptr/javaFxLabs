package com.example.fx2.MainScreen.models;

public class MoveAlongYAxis implements IBehaviour {
    @Override
    public void updateCoordinates(Vehicle vehicle) {
//        double newX = x;
//        double newY = y;
        if (vehicle.getyDirection() == 1) { // up
            if (vehicle.getY() - vehicle.getStep() <= 0) {
                vehicle.setY(0);
                vehicle.setyDirection(-1);
            } else {
                vehicle.setY(vehicle.getY() - vehicle.getStep());
            }
        } else if (vehicle.getyDirection() == -1) { // down
            if (vehicle.getY() + vehicle.getStep() >= vehicle.getMaxY()) {
                vehicle.setY(vehicle.getMaxY());
                vehicle.setyDirection(1);
            } else {
                vehicle.setY(vehicle.getY() + vehicle.getStep());
            }
        }
    }

//    public MoveAlongYAxisAI(int xDirection, int yDirection) {
//        super(xDirection, yDirection);
//    }
//
//    @Override
//    public synchronized Point2D getNewCoordinates(double x, double y) {
//        double newX = x;
//        double newY = y;
//        if (super.getxDirection() == 1) { // left
//            if (y - super.getStep() <= 0) {
//                newY = 0;
//                super.setxDirection(-1);
//            } else {
//                newY = y - super.getStep();
//            }
//        } else if (super.getxDirection() == -1) { // right
//            if (y + super.getStep() >= super.getMaxY()) {
//                newY = super.getMaxY();
//                super.setxDirection(1);
//            } else {
//                newY = y + super.getStep();
//            }
//        }
//        return new Point2D(newX, newY);
//    }
}
