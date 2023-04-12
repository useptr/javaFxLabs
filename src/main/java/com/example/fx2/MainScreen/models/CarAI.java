package com.example.fx2.MainScreen.models;

public class CarAI extends BaseAI{


    @Override
    public void updateCoordinates() {
        for (var vehicle : Habitat.getInstance().getVehicles()) {
            if (vehicle instanceof Car)
                vehicle.performBehaviour();
        }
    }

}
