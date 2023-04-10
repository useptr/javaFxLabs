package com.example.fx2.MainScreen.models;

import java.util.ArrayList;

public class MotorcycleAI extends BaseAI{
    @Override
    public synchronized void updateCoordinates() {
        for (var vehicle : Habitat.getHabitat().getVehicles()) {
            if (vehicle instanceof Motorcycle)
                vehicle.performBehaviour();
        }
    }
}
