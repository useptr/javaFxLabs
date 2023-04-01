package com.example.fx2.MainScreen.models;

import java.util.*;

public class Habitat {
    private static Habitat habitat;
    private IBehaviour iBehaviour = new MoveNoWay();
    private boolean simulationRunning = false;
    private static double width, height;
    private static VehiclesCollections vehiclesCollections;
//    private final HashSet<Integer> vehiclesId = new HashSet<>();
//    private final TreeMap<Integer, Integer> vehiclesBirthTime = new TreeMap<>(); // id => birthTime
    public VehicleSpawnParameters motoSpawnParameters = new VehicleSpawnParameters(80,1, 2);
    public VehicleSpawnParameters carSpawnParameters = new VehicleSpawnParameters(100,3, 4);
//    private final ArrayList<Vehicle> vehicles = new ArrayList<>();

    public HashSet<Integer> getVehiclesId() {
//        return vehiclesId;
        return vehiclesCollections.getVehiclesId();
    }

//    TextAboutTypeAndNumbers simulationTime = new TextAboutTypeAndNumbers("Время симуляции: ");
    private final Map<String, TextAboutTypeAndNumbers> textAboutTypeAndNumbers = new HashMap<>() {{
        put("T", new TextAboutTypeAndNumbers("Время симуляции: "));
        put("carNumbers", new TextAboutTypeAndNumbers("Количество машин: "));
        put("motoNumbers", new TextAboutTypeAndNumbers("Количество мотоциклов: "));
    }};

    public static void initialize(VehiclesCollections vehiclesCollections, double width, double height) {
        habitat = new Habitat(vehiclesCollections, width, height);
    }
//    Timeline timeline;
    private Habitat(VehiclesCollections vehiclesCollections,double width, double height) {
        Habitat.width = width;
        Habitat.height = height;
        Habitat.vehiclesCollections = vehiclesCollections;
    }

    public static synchronized Habitat getHabitat() {
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
//        return vehiclesBirthTime;
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
//        vehiclesId.clear();
//        vehiclesBirthTime.clear();
//        vehicles.clear();
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

//        while (vehiclesId.contains(vehicleId))
//            vehicleId = Math.abs(random.nextInt()%maxId);
//        vehicle.setId(vehicleId);
//        vehiclesId.add(vehicle.getId());
//        vehicles.add(vehicle);
//        vehiclesBirthTime.put(vehicle.getId(), currentTime);
        if (vehicle instanceof Car) {
            textAboutTypeAndNumbers.get("carNumbers").increaseNumbers();
        } else if (vehicle instanceof Motorcycle) {
            textAboutTypeAndNumbers.get("motoNumbers").increaseNumbers();
        }

    }
    public void Update(int currentTime) {
        removeVehiclesWithElapsedLifetime(currentTime);
        Random random = new Random();
        if (currentTime %carSpawnParameters.generationTime == 0) {
            int randInt = Math.abs(random.nextInt()%100+1);
            if ( carSpawnParameters.getGenerationProbability() >= randInt) {
                Car car = new Car();
                addVehicle(car, currentTime);
            }
        }
        if (currentTime %motoSpawnParameters.getGenerationTime() == 0) {
            int randInt = Math.abs(random.nextInt()%100+1);
            if (motoSpawnParameters.getGenerationProbability() >= randInt) {
                Motorcycle motorcycle = new Motorcycle();
                addVehicle(motorcycle, currentTime);
            }
        }
    }
    public void removeVehiclesWithElapsedLifetime(int currentTime) {
//        for (int i = 0; i < vehicles.size(); i++) {
//            var vehicle = vehicles.get(i);
//            var lifetime = currentTime - vehiclesBirthTime.get(vehicle.getId());
        for (int i = 0; i < vehiclesCollections.getVehicles().size(); i++) {
            var vehicle = vehiclesCollections.getVehicles().get(i);
//            var lifetime = currentTime - vehiclesBirthTime.get(vehicle.getId());
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
//                vehiclesBirthTime.remove(vehicle.getId());
//                vehiclesId.remove(vehicle.getId());
//                vehicles.remove(vehicle);
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
