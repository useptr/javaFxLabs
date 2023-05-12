package com.example.fx2.MainScreen.models;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class Habitat {
    private static Habitat habitat;
//    private IBehaviour iBehaviour = new MoveNoWay();
    private boolean simulationRunning = false;
    private static double width, height;

    private static int currentTime;
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
    public void saveVehiclesInFile(ObjectOutputStream out) {
//        for (Vehicle vehicle : vehiclesCollections.getVehicles()) {
            try{
            out.writeObject(vehiclesCollections.getVehicles());
//                System.out.println("vehicle saved");
            }catch(Exception e){System.out.println(e);}
//        }
    }
    public void reduceNumberOfMotorcyclesByNPrecent(int n) {
        synchronized (vehiclesCollections.getVehicles()) {
            int size = vehiclesCollections.getVehicles().size();
            if (n == 100) {
//                System.out.println(n);
                for (int i = 0; i < size; i++) {
                    var vehicle = vehiclesCollections.getVehicles().get(i);
                    if (vehicle instanceof Motorcycle) {
                        removeVehicle(vehicle);
                        textAboutTypeAndNumbers.get("motoNumbers").decreaseNumbers();
                        size = vehiclesCollections.getVehicles().size();
                        i--;
                    }
                }
            } else {
                int motoCount = textAboutTypeAndNumbers.get("motoNumbers").getNumbers();
                int count = motoCount * n / 100;
//                System.out.println(motoCount + " " + count);
                for (int i = 0; i < size; i++) {
                    var vehicle = vehiclesCollections.getVehicles().get(i);
                    if (count == 0)
                        break;
                    if (vehicle instanceof Motorcycle) {
                        removeVehicle(vehicle);
                        textAboutTypeAndNumbers.get("motoNumbers").decreaseNumbers();
                        size = vehiclesCollections.getVehicles().size();
                        i--;
                        count--;
                    }
                }
            }
        }
    }
    public void removeVehicle(Vehicle vehicle) {
        synchronized (vehiclesCollections.getVehiclesBirthTime()) {
            vehiclesCollections.getVehiclesBirthTime().remove(vehicle.getId());
        }
        synchronized (vehiclesCollections.getVehiclesId()) {
            vehiclesCollections.getVehiclesId().remove(vehicle.getId());
        }
//        synchronized (vehiclesCollections.getVehicles()) {
            vehiclesCollections.getVehicles().remove(vehicle);
//        }
    }
    public void setVehiclesFromClient(ArrayList<Vehicle> vehicles) {
        try{
            clearWithoutSimulationTime();
            for (Vehicle vehicle : vehicles) {
                vehicle.setTimeOfBirth(textAboutTypeAndNumbers.get("T").getNumbers());
                addVehicle(vehicle);
            }
        }catch(Exception e){System.out.println(e);}
    }
    public void readVehiclesFromFile(ObjectInputStream in) {
        try{
            ArrayList<Vehicle> vehicles;
            vehicles = (ArrayList) in.readObject();
            clearWithoutSimulationTime();
            for (Vehicle vehicle : vehicles) {
                vehicle.setTimeOfBirth(textAboutTypeAndNumbers.get("T").getNumbers());
//                System.out.println(vehicle);
                addVehicle(vehicle);
            }
        }catch(Exception e){System.out.println(e);}
    }
    public static void setWidthAndHeight(double width, double height) {
        height = 626 - 50;
        width =460 - 50;
        Habitat.width = width;
        Habitat.height = height;
        Vehicle.setMaxXAndMaxY(width, height);
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
    public void clearWithoutSimulationTime() {
        vehiclesCollections.getVehiclesId().clear();
        vehiclesCollections.getVehiclesBirthTime().clear();
        vehiclesCollections.getVehicles().clear();
        for (Map.Entry<String, TextAboutTypeAndNumbers> current : textAboutTypeAndNumbers.entrySet()) {
            if (current.getKey() != "T")
                current.getValue().setNumbers(0);
        }
    }
    public void clear() {
        vehiclesCollections.getVehiclesId().clear();
        vehiclesCollections.getVehiclesBirthTime().clear();
        vehiclesCollections.getVehicles().clear();
        for (Map.Entry<String, TextAboutTypeAndNumbers> current : textAboutTypeAndNumbers.entrySet()) {
            current.getValue().setNumbers(0);
        }
    }
    public void addVehicle(Vehicle vehicle) {
        vehicle.setTimeOfBirth(textAboutTypeAndNumbers.get("T").getNumbers());
        if (vehicle instanceof Car) {
            textAboutTypeAndNumbers.get("carNumbers").increaseNumbers();
        } else if (vehicle instanceof Motorcycle) {
            textAboutTypeAndNumbers.get("motoNumbers").increaseNumbers();
        }
        synchronized (vehiclesCollections.getVehiclesId()) {
            vehiclesCollections.getVehiclesId().add(vehicle.getId());
        }
        synchronized (vehiclesCollections.getVehicles()) {
            vehiclesCollections.getVehicles().add(vehicle);
        }
        synchronized (vehiclesCollections.getVehiclesBirthTime()) {
            vehiclesCollections.getVehiclesBirthTime().put(vehicle.getId(), vehicle.getId());
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
        vehicle.setTimeOfBirth(currentTime);
        if (vehicle instanceof Car) {
            textAboutTypeAndNumbers.get("carNumbers").increaseNumbers();
        } else if (vehicle instanceof Motorcycle) {
            textAboutTypeAndNumbers.get("motoNumbers").increaseNumbers();
        }
        vehiclesCollections.getVehiclesId().add(vehicle.getId());
        vehiclesCollections.getVehicles().add(vehicle);
        vehiclesCollections.getVehiclesBirthTime().put(vehicle.getId(), currentTime);

    }
    public void Update(int currentTime) {
        removeVehiclesWithElapsedLifetime(currentTime);
        Random random = new Random();
        int randInt = Math.abs(random.nextInt()%100+1);
        if (currentTime %carSpawnParameters.generationTime == 0) {
            if ( carSpawnParameters.getGenerationProbability() >= randInt) {
                Car car = new Car();
                synchronized (getVehicles()) {
                    addVehicle(car, currentTime);
                }

            }
        }
        if (currentTime %motoSpawnParameters.getGenerationTime() == 0) {
            if (motoSpawnParameters.getGenerationProbability() >= randInt) {
                Motorcycle motorcycle = new Motorcycle();
                synchronized (getVehicles()) {
                    addVehicle(motorcycle, currentTime);
                }
            }
        }
    }

    public void removeVehiclesWithElapsedLifetime(int currentTime) {
        for (int i = 0; i < vehiclesCollections.getVehicles().size(); i++) {
            var vehicle = vehiclesCollections.getVehicles().get(i);
            var lifetime = currentTime - vehicle.getTimeOfBirth();
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
