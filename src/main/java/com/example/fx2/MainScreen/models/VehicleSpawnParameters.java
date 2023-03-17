package com.example.fx2.MainScreen.models;

public class VehicleSpawnParameters {
    int generationProbability;
    int generationTime;
    public VehicleSpawnParameters(int generationProbability, int generationTime) {
        this.generationProbability = generationProbability;
        this.generationTime = generationTime;
    }
    public int getGenerationProbability() {
        return generationProbability;
    }

    public int getGenerationTime() {
        return generationTime;
    }

    public void setGenerationProbability(int generationProbability) {
        this.generationProbability = generationProbability;
    }

    public void setGenerationTime(int generationTime) {
        this.generationTime = generationTime;
    }
}
