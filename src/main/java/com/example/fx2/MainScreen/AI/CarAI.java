package com.example.fx2.MainScreen.AI;

import com.example.fx2.MainScreen.models.Car;
import com.example.fx2.MainScreen.models.Habitat;

public class CarAI extends BaseAI{


    @Override
    public  void updateCoordinates() {
        synchronized (Habitat.getInstance().getVehicles()) {
            for (var vehicle : Habitat.getInstance().getVehicles()) {
                if (vehicle instanceof Car) {
                    synchronized (vehicle) {
                        vehicle.performBehaviour();
                    }
                }
            }
        }
    }

}
