package com.example.fx2.MainScreen.models;

public class TextAboutTypeAndNumbers {
    private String text;
    private int numbers = 0;
    private boolean visibility = false;
    public TextAboutTypeAndNumbers(String text) {
        this.text = text;
    }
    public boolean getVisibility() {
        return visibility;
    }
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
    public String getFinishedInformation() {
        return text + Integer.toString(numbers);
    }
    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }
    public int getNumbers() {
        return numbers;
    }
    public void increaseNumbers() {
        numbers++;
    }
}
