package com.example.fx2.MainScreen.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

public class VehiclesCollections {
    private static VehiclesCollections vehiclesCollections;
    private final HashSet<Integer> vehiclesId = new HashSet<>();
    private final TreeMap<Integer, Integer> vehiclesBirthTime = new TreeMap<>();
    private final ArrayList<Vehicle> vehicles = new ArrayList<>();

    public static synchronized VehiclesCollections getInstance() {
        if (vehiclesCollections == null) {
            vehiclesCollections = new VehiclesCollections();
        }
        return vehiclesCollections;
    }
    public HashSet<Integer> getVehiclesId() {
        return vehiclesId;
    }

    public TreeMap<Integer, Integer> getVehiclesBirthTime() {
        return vehiclesBirthTime;
    }

    public synchronized ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }
}
