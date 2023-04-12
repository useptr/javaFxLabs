package com.example.fx2.MainScreen.models;

import java.util.*;

public class Habitat {
    private static Habitat habitat;
//    private IBehaviour iBehaviour = new MoveNoWay();
    private boolean simulationRunning = false;
    private static double width, height;
    private static VehiclesCollections vehiclesCollections;
    public VehicleSpawnParameters motoSpawnParameters = new VehicleSpawnParameters(100,1, 200);
    public VehicleSpawnParameters carSpawnParameters = new VehicleSpawnParameters(100,1, 400);
    public HashSet<Integer> getVehiclesId() {
        return vehiclesCollections.getVehiclesId();
    }
    private final Map<String, TextAboutTypeAndNumbers> textAboutTypeAndNumbers = new HashMap<>() {{
        put("T", new TextAboutTypeAndNumbers("Время симуляции: "));
        put("carNumbers", new TextAboutTypeAndNumbers("Количество машин: "));
        put("motoNumbers", new TextAboutTypeAndNumbers("Количество мотоциклов: "));
    }};
    public static void setWidthAndHeight(double width, double height) {
        Habitat.width = width;
        Habitat.height = height;
        Vehicle.setMaxXAndMaxY(width - 50, height);
    }
    private Habitat() {
        vehiclesCollections = VehiclesCollections.getInstance();
    }
    public static synchronized Habitat getInstance() {
        if (habitat == null) {
            habitat = new Habitat();
        }
        return habitat;
    }
    public boolean getSimulationRunning() {
        return simulationRunning;
    }

    public void setSimulationRunning(boolean simulationRunning) {
        this.simulationRunning = simulationRunning;
    }

    public VehicleSpawnParameters getCarSpawnParameters() {
        return carSpawnParameters;
    }

    public VehicleSpawnParameters getMotoSpawnParameters() {
        return motoSpawnParameters;
    }

    public Map<String, TextAboutTypeAndNumbers> getTextAboutTypeAndNumbers() {
        return textAboutTypeAndNumbers;
    }

    public TreeMap<Integer, Integer> getVehiclesBirthTime() {
        return vehiclesCollections.getVehiclesBirthTime();
    }

    public double getHeight() {
        return height;
    }
    public double getWidth() {
        return width;
    }
    public void clear() {
        vehiclesCollections.getVehiclesId().clear();
        vehiclesCollections.getVehiclesBirthTime().clear();
        vehiclesCollections.getVehicles().clear();
        for (Map.Entry<String, TextAboutTypeAndNumbers> current : textAboutTypeAndNumbers.entrySet()) {
            current.getValue().setNumbers(0);
        }
    }
    public void addVehicle(Vehicle vehicle, int currentTime) {
        Random random = new Random();
        vehicle.setX(Math.abs(random.nextInt()%(getWidth() - vehicle.getSize())));
        vehicle.setY(Math.abs(random.nextInt()%(getHeight() - vehicle.getSize())));

        final int maxId = 1000;
        int vehicleId = Math.abs(random.nextInt()%maxId);

        while (vehiclesCollections.getVehiclesId().contains(vehicleId))
            vehicleId = Math.abs(random.nextInt()%maxId);
        vehicle.setId(vehicleId);
        vehiclesCollections.getVehiclesId().add(vehicle.getId());
        vehiclesCollections.getVehicles().add(vehicle);
        vehiclesCollections.getVehiclesBirthTime().put(vehicle.getId(), currentTime);
        if (vehicle instanceof Car) {
            textAboutTypeAndNumbers.get("carNumbers").increaseNumbers();
        } else if (vehicle instanceof Motorcycle) {
            textAboutTypeAndNumbers.get("motoNumbers").increaseNumbers();
        }
    }
    public void Update(int currentTime) {
//        removeVehiclesWithElapsedLifetime(currentTime);
        Random random = new Random();
        int randInt = Math.abs(random.nextInt()%100+1);
        if (currentTime %carSpawnParameters.generationTime == 0) {
            if ( carSpawnParameters.getGenerationProbability() >= randInt) {
                Car car = new Car();
                addVehicle(car, currentTime);
            }
        }
        if (currentTime %motoSpawnParameters.getGenerationTime() == 0) {
            if (motoSpawnParameters.getGenerationProbability() >= randInt) {
                Motorcycle motorcycle = new Motorcycle();
                addVehicle(motorcycle, currentTime);
            }
        }
    }
    public void removeVehiclesWithElapsedLifetime(int currentTime) {
        for (int i = 0; i < vehiclesCollections.getVehicles().size(); i++) {
            var vehicle = vehiclesCollections.getVehicles().get(i);
            var lifetime = currentTime - vehiclesCollections.getVehiclesBirthTime().get(vehicle.getId());
            boolean vehicleWithElapsedLifetime = false;
            if (vehicle instanceof Car) {
                if (lifetime > carSpawnParameters.getLifetime()) {
                    vehicleWithElapsedLifetime = true;
                    textAboutTypeAndNumbers.get("carNumbers").decreaseNumbers();
                }
            } else if (vehicle instanceof Motorcycle) {
                if (lifetime > motoSpawnParameters.getLifetime()) {
                    vehicleWithElapsedLifetime = true;
                    textAboutTypeAndNumbers.get("motoNumbers").decreaseNumbers();
                }
            }
            if (vehicleWithElapsedLifetime) {
                vehiclesCollections.getVehiclesBirthTime().remove(vehicle.getId());
                vehiclesCollections.getVehiclesId().remove(vehicle.getId());
                vehiclesCollections.getVehicles().remove(vehicle);
                i--;
            }
        }
    }
    public ArrayList<Vehicle> getVehicles() {
//        return vehicles;
        return vehiclesCollections.getVehicles();
    }
}
