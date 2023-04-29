package com.example.fx2.MainScreen.Behaviour;

import com.example.fx2.MainScreen.models.Vehicle;
import javafx.geometry.Point2D;

import java.io.Serializable;

public interface IBehaviour extends Serializable {
    public void updateCoordinates(Vehicle vehicle);
}
