package com.example.fx2.MainScreen.AI;

import com.example.fx2.MainScreen.models.Habitat;
import com.example.fx2.MainScreen.models.Motorcycle;

public class MotorcycleAI extends BaseAI{
    @Override
    public void updateCoordinates() {
        synchronized (Habitat.getInstance().getVehicles()) {
            for (var vehicle : Habitat.getInstance().getVehicles()) {
                if (vehicle instanceof Motorcycle)
                        vehicle.performBehaviour();
            }
        }
    }
}
