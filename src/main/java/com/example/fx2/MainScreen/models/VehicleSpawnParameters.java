package com.example.fx2.MainScreen.models;

public class VehicleSpawnParameters {
    int generationProbability;
    int generationTime;
    int lifetime;
    public VehicleSpawnParameters(int generationProbability, int generationTime, int lifetime) {
        this.generationProbability = generationProbability;
        this.generationTime = generationTime;
        this.lifetime = lifetime;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
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
