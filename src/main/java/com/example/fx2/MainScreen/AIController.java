package com.example.fx2.MainScreen;

import com.example.fx2.MainScreen.AI.BaseAI;
import com.example.fx2.MainScreen.AI.CarAI;
import com.example.fx2.MainScreen.AI.MotorcycleAI;

public class AIController {
    private static final int SECOND = 1000;
    BaseAI carAI = new CarAI();

    public BaseAI getCarAI() {
        return carAI;
    }

    public void setCarAI(BaseAI carAI) {
        this.carAI = carAI;
    }

    public BaseAI getMotorcycleAI() {
        return motorcycleAI;
    }

    public void setMotorcycleAI(BaseAI motorcycleAI) {
        this.motorcycleAI = motorcycleAI;
    }

    BaseAI motorcycleAI = new MotorcycleAI();

    public AIController() {
        carAI.start();
        motorcycleAI.start();

    }
    public void carAISetRun(boolean state) {
        carAI.setRun(state);
    }
    public void motorcycleAISetRun(boolean state) {
        motorcycleAI.setRun(state);
    }
    public synchronized void notifyCarAI() {
        carAI.notify();
    }
    public synchronized void notifyMotorcycleAI() {
        motorcycleAI.notify();
    }
    public void notifyAI() {
        carAI.notify();
        motorcycleAI.notify();
    }
    public synchronized void waitCarAI() {
        try {
            carAI.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public synchronized void waitMotorcycleAI() {
        try {
            motorcycleAI.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void waitAI() {
        waitCarAI();
        waitMotorcycleAI();
    }
//    public void performBehaviour() {
//        carAI.updateCoordinates();
//        motorcycleAI.updateCoordinates();
//    }
}
