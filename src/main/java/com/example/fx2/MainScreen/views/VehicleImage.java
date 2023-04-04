package com.example.fx2.MainScreen.views;

import com.example.fx2.MainScreen.models.Vehicle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VehicleImage {
    private int vehicleId;
    private ImageView imageView;
    public VehicleImage(Vehicle vehicle, Image image) {
        vehicleId = vehicle.getId();
        imageView = new ImageView(image);
        imageView.setX(vehicle.getX());
        imageView.setY(vehicle.getY());

    }
    public void updateXAndYCoordinates(double x, double y) {
        imageView.setX(x);
        imageView.setY(y);
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
