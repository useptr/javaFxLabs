package com.example.fx2.MainScreen;

import com.example.fx2.MainScreen.models.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;

public class MainScreenController implements EventHandler {

    @FXML
    Button startBtn;
    @FXML
    Button stopBtn;
    @FXML
    CheckBox showInfoCheckBox;
    @FXML
    RadioButton hideTimeRadioBtn;
    @FXML
    RadioButton showTimeRadioBtn;
    @FXML
    TextField carSpawnTimeTextField;
    @FXML
    TextField motocycleSpawnTimeTextField;
    @FXML
    Slider motocycleChanceSlider;
    @FXML
    Slider carChanceSlider;
    @FXML
    Pane habitatPane;
    @FXML
    Text spawnTimeText;
    private ToggleGroup timeToggleGroup = new ToggleGroup();

    private ArrayList<ImageView> vehiclesImages = new ArrayList<>();
    private static Habitat habitatModel;
    private Timer timer = new Timer();

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), this));

    public void initialize() {
        stopBtn.setDisable(true);
        hideTimeRadioBtn.setToggleGroup(timeToggleGroup);
        showTimeRadioBtn.setToggleGroup(timeToggleGroup);
        showTimeRadioBtn.setSelected(true);
        timeline.setCycleCount(Timeline.INDEFINITE);
    }


    public void alert(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(header);
        alert.setContentText(message);

        alert.showAndWait();
    }
@FXML
private void stopMenuItemSelected() {
        if (habitatModel.getSimulationRunning())
            stopSpawn();
}
@FXML
private void startMenuItemSelected() {
    if (!habitatModel.getSimulationRunning())
        startSpawn();
    }
    @FXML
    private void motocycleChanceSliderValueChange() {
        habitatModel.getMotoSpawnParameters().setGenerationProbability((int)motocycleChanceSlider.getValue());
//        System.out.println(habitatModel.getMotoSpawnParameters().getGenerationProbability());
    }
    @FXML
    private void carChanceSliderValueChange() {
        habitatModel.getCarSpawnParameters().setGenerationProbability((int)carChanceSlider.getValue());
//        System.out.println(habitatModel.getCarSpawnParameters().getGenerationProbability());
//        carChanceSlider.getValue();

    }
    private String getVehicleType (Vehicle vehicle) {
        if (vehicle instanceof Car) {
            return "Car";
        } else if (vehicle instanceof Motorcycle) {
            return "Motorcycle";
        }
        return "";
    }
    private void checkTextFieldValue(TextField textField, String vehicleType, VehicleSpawnParameters vehicleSpawnParameters) {
        String spawnTimeText = textField.getText();
        if (!spawnTimeText.isEmpty()) {
            int spawnTime;
            try {
                spawnTime = Integer.parseInt(spawnTimeText);
                if (spawnTime > 0)
                    vehicleSpawnParameters.setGenerationTime(spawnTime);
                else {
                    textField.setText(vehicleSpawnParameters.getGenerationTime()+"");
                    alert("Invalid input", vehicleType + " spawn time cannot be equal: " + spawnTimeText);
                }

            } catch (NumberFormatException e) {
                textField.setText(vehicleSpawnParameters.getGenerationTime()+"");
                alert("Invalid input", vehicleType + " spawn time cannot be equal: " + spawnTimeText);
            }
        } else {
            textField.setText(vehicleSpawnParameters.getGenerationTime()+"");
        }
    }
    @FXML
    private void carSpawnTimeTextFieldMouseReleased() {
        checkTextFieldValue(carSpawnTimeTextField, "Car", habitatModel.getCarSpawnParameters());
        /*String carSpawnTimeText = carSpawnTimeTextField.getText();
        if (!carSpawnTimeText.isEmpty()) {
            int carSpawnTime;
            try {
                carSpawnTime = Integer.parseInt(carSpawnTimeText);
                if (carSpawnTime > 0)
                habitatModel.getCarSpawnParameters().setGenerationTime(carSpawnTime);
                else {
                    carSpawnTimeTextField.setText(habitatModel.getCarSpawnParameters().getGenerationTime()+"");
                    alert("Invalid input", "Motorcycle spawn time cannot be equal: " + carSpawnTimeText);
                }

            } catch (NumberFormatException e) {
                carSpawnTimeTextField.setText(habitatModel.getCarSpawnParameters().getGenerationTime()+"");
                alert("Invalid input", "Motorcycle spawn time cannot be equal: " + carSpawnTimeText);
            }
        } else {
            carSpawnTimeTextField.setText(habitatModel.getCarSpawnParameters().getGenerationTime()+"");
        } */
    }
    @FXML
    private void motocycleSpawnTimeTextFieldMouseReleased() {
        checkTextFieldValue(motocycleSpawnTimeTextField, "Motorcycle", habitatModel.getMotoSpawnParameters());
    }
@FXML
private void showTimeRadioBtnSelected() {
    spawnTimeText.setVisible(true);
    }
    @FXML
    private void hideTimeRadioBtnSelected() {
        spawnTimeText.setVisible(false);
    }
    @FXML
    private void startBtnClicked() {
        startSpawn();
    }
    @FXML
    private void stopBtnClicked() {
        stopSpawn();
    }
    @FXML
    private void showInfoCheckBoxSelected() {
    }
    public ArrayList<ImageView> getVehiclesImages() {
        return vehiclesImages;
    }

    public Button getStartBtn() {
        return startBtn;
    }
    public Button getStopBtn() {
        return stopBtn;
    }
    public CheckBox getShowInfoCheckBox() {
        return showInfoCheckBox;
    }

    public static synchronized Habitat getHabitatModel() {
        return habitatModel;
    }

    public Pane getHabitatPane() {
        return habitatPane;
    }
    public RadioButton getHideTimeRadioBtn() {
        return hideTimeRadioBtn;
    }
    public RadioButton getShowTimeRadioBtn() {
        return showTimeRadioBtn;
    }
    public Slider getCarChanceSlider() {
        return carChanceSlider;
    }
    public Slider getMotocycleChanceSlider() {
        return motocycleChanceSlider;
    }
    public TextField getCarSpawnTimeTextField() {
        return carSpawnTimeTextField;
    }
    public TextField getMotocycleSpawnTimeTextField() {
        return motocycleSpawnTimeTextField;
    }
    public void setHabitatModel(Habitat habitatModel) {
        this.habitatModel = habitatModel;
        carChanceSlider.setValue(this.habitatModel.getCarSpawnParameters().getGenerationProbability());
        motocycleChanceSlider.setValue(this.habitatModel.getMotoSpawnParameters().getGenerationProbability());
        motocycleSpawnTimeTextField.setText(this.habitatModel.getMotoSpawnParameters().getGenerationTime()+"");
        carSpawnTimeTextField.setText(this.habitatModel.getCarSpawnParameters().getGenerationTime()+"");
    }
    public void setVehiclesImages(ArrayList<ImageView> vehiclesImages) {
        this.vehiclesImages = vehiclesImages;
    }
    public void changeSpawnTimeVisibility() {
        if (showTimeRadioBtn.isSelected()) {
            hideTimeRadioBtn.setSelected(true);
            spawnTimeText.setVisible(false);
        } else {
            showTimeRadioBtn.setSelected(true);
            spawnTimeText.setVisible(true);
        }
    }
    public void startSpawn() {
        startBtn.setDisable(true);
        stopBtn.setDisable(false);

        habitatModel.setSimulationRunning(true);
        habitatModel.clear();
        habitatPane.getChildren().clear();
        vehiclesImages.clear();

        spawnTimeText.setText(habitatModel.getTextAboutTypeAndNumbers().get("T").getFinishedInformation());

        timeline.play();
    }
    public void stopSpawn() {
        if (showInfoCheckBox.isSelected()) {
            timeline.stop();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Stop simulation?");
            dialog.setHeaderText("Simulation info");
            dialog.setContentText(habitatModel.getTextAboutTypeAndNumbers().get("T").getFinishedInformation() + "\n" +
                    habitatModel.getTextAboutTypeAndNumbers().get("carNumbers").getFinishedInformation() + "\n" +
                    habitatModel.getTextAboutTypeAndNumbers().get("motoNumbers").getFinishedInformation());
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == okButton) {
                startBtn.setDisable(false);
                stopBtn.setDisable(true);
                habitatModel.setSimulationRunning(false);
                timeline.stop();
            } else {
                timeline.play();
            }
        } else {
            startBtn.setDisable(false);
            stopBtn.setDisable(true);
            habitatModel.setSimulationRunning(false);
            timeline.stop();
        }
    }
    @Override
    public void handle(Event event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            KeyEvent keyEvent = (KeyEvent)event;
            switch (keyEvent.getCode()) {
                case B: {
                    if (!habitatModel.getSimulationRunning()) {
                        startSpawn();
                    }
                    break;
                }
                case E: {
                    if (habitatModel.getSimulationRunning()) {
                        stopSpawn();
                    }
                    break;
                }
                case T: {
                    changeSpawnTimeVisibility();
//                    if (habitatModel.getSimulationRunning()) {
//                        changeSpawnTimeVisibility();
//                    }
                    break;
                }
            }
        } else if (event.getEventType() == ActionEvent.ACTION) {
            habitatModel.getTextAboutTypeAndNumbers().get("T").increaseNumbers();
            int spawnTime = habitatModel.getTextAboutTypeAndNumbers().get("T").getNumbers();
            spawnTimeText.setText(habitatModel.getTextAboutTypeAndNumbers().get("T").getFinishedInformation());
            habitatModel.Update(spawnTime);

            for (int i = vehiclesImages.size(); i < habitatModel.getVehicles().size(); i++) {
                Vehicle vehicle = habitatModel.getVehicles().get(i);
                Image image = new Image(getClass().getResource(vehicle.getImagePath()).toString(), 50, 50, false, false);
                ImageView imageView = new ImageView(image);
                imageView.setX(vehicle.getX());
                imageView.setY(vehicle.getY());
                vehiclesImages.add(imageView);
                habitatPane.getChildren().add(imageView);
            }
        }
    }
}