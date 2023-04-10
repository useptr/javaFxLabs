package com.example.fx2.MainScreen.models;

import com.example.fx2.MainScreen.views.VehicleImage;

import java.util.ArrayList;

public class CarAI extends BaseAI{


    @Override
    public synchronized void updateCoordinates() {
        for (var vehicle : Habitat.getHabitat().getVehicles()) {
            if (vehicle instanceof Car)
                vehicle.performBehaviour();
        }
    }

}
