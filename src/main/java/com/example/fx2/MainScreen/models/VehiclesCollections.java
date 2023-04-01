package com.example.fx2.MainScreen.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

public class VehiclesCollections {
    private static VehiclesCollections vehiclesCollections;
    private final HashSet<Integer> vehiclesId = new HashSet<>();
    private final TreeMap<Integer, Integer> vehiclesBirthTime = new TreeMap<>();
    private final ArrayList<Vehicle> vehicles = new ArrayList<>();
    private VehiclesCollections() {

    }
    public static void initialize() {
        vehiclesCollections = new VehiclesCollections();
    }

    public static synchronized VehiclesCollections getVehiclesCollections() {
        return vehiclesCollections;
    }

    public static synchronized void setVehiclesCollections(VehiclesCollections vehiclesCollections) {
        VehiclesCollections.vehiclesCollections = vehiclesCollections;
    }

    public HashSet<Integer> getVehiclesId() {
        return vehiclesId;
    }

    public TreeMap<Integer, Integer> getVehiclesBirthTime() {
        return vehiclesBirthTime;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }
}
