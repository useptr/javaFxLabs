package com.example.fx2.MainScreen.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Habitat {
    private static Habitat habitat;
    private IBehaviour iBehaviour = new MoveNoWay();
    private boolean simulationRunning = false;
    private static double width, height;
    public VehicleSpawnParameters motoSpawnParameters = new VehicleSpawnParameters(80,1);
    public VehicleSpawnParameters carSpawnParameters = new VehicleSpawnParameters(70,3);
    private ArrayList<Vehicle> vehicles = new ArrayList<>();

//    TextAboutTypeAndNumbers simulationTime = new TextAboutTypeAndNumbers("Время симуляции: ");
    private Map<String, TextAboutTypeAndNumbers> textAboutTypeAndNumbers = new HashMap<>() {{
        put("T", new TextAboutTypeAndNumbers("Время симуляции: "));
        put("carNumbers", new TextAboutTypeAndNumbers("Количество машин: "));
        put("motoNumbers", new TextAboutTypeAndNumbers("Количество мотоциклов: "));
    }};

    public static void initialize(double width, double height) {
        habitat = new Habitat(width, height);
    }
//    Timeline timeline;
    private Habitat(double width, double height) {
        this.width = width;
        this.height = height;
//        timeline = new Timeline(new KeyFrame(Duration.seconds(1), timeEventsController));
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();
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

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }
    public double getHeight() {
        return height;
    }
    public double getWidth() {
        return width;
    }
    public void clear() {
        vehicles.clear();
        for (Map.Entry<String, TextAboutTypeAndNumbers> current : textAboutTypeAndNumbers.entrySet()) {
            current.getValue().setNumbers(0);
        }
    }
    public void Update(int spawnTime) {
        Random random = new Random();
        if (spawnTime%carSpawnParameters.generationTime == 0) {
            int randInt = Math.abs(random.nextInt()%100+1);
            if ( carSpawnParameters.getGenerationProbability() >= randInt) {
                Car car = new Car();
                car.setX(Math.abs(random.nextInt()%(getWidth() - car.getSize())));
                car.setY(Math.abs(random.nextInt()%(getHeight() - car.getSize())));
                addVehicle(car);
                textAboutTypeAndNumbers.get("carNumbers").increaseNumbers();
//                habitatView.addImageView(car);

            }
        }
        if (spawnTime%motoSpawnParameters.getGenerationTime() == 0) {
            int randInt = Math.abs(random.nextInt()%100+1);
            if (motoSpawnParameters.getGenerationProbability() >= randInt) {
                Motorcycle motorcycle = new Motorcycle();
                motorcycle.setX(Math.abs(random.nextInt()%(getWidth() - motorcycle.getSize())));
                motorcycle.setY(Math.abs(random.nextInt()%(getWidth() - motorcycle.getSize())));
                addVehicle(motorcycle);
                textAboutTypeAndNumbers.get("motoNumbers").increaseNumbers();
//                habitatView.addImageView(motorcycle);
            }
        }
    }
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }
}
