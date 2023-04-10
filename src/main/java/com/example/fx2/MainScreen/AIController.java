package com.example.fx2.MainScreen;

import com.example.fx2.MainScreen.models.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.util.ArrayList;

public class AIController {
    private static final int SECOND = 1000;
    BaseAI carAI = new CarAI();
    BaseAI motorcycleAI = new MotorcycleAI();

    public AIController() {
        carAI.start();
        motorcycleAI.start();
        carAI.startAI();
        motorcycleAI.startAI();
    }
//    public void performBehaviour() {
//        carAI.updateCoordinates();
//        motorcycleAI.updateCoordinates();
//    }
}
