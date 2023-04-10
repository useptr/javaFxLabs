package com.example.fx2.MainScreen;

import com.example.fx2.MainScreen.models.*;
import com.example.fx2.MainScreen.views.VehicleImage;
import javafx.animation.AnimationTimer;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;

public class MainScreenController implements EventHandler {
    private static final double SECOND = 1000;
//    AIController aiController = new AIController();
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
    @FXML
    MenuItem startMenuItem;
    @FXML
    MenuItem stopMenuItem;
    @FXML
    CheckMenuItem showInfoMenuItem;
    @FXML
    MenuItem showTimeMenuItem;
    @FXML
    MenuItem hideTimeMenuItem;
    @FXML
    TextField motorcycleLifeTimeTextField;
    @FXML
    TextField carLifeTimeTextField;
    private ToggleGroup timeToggleGroup = new ToggleGroup();

    private ArrayList<VehicleImage> vehiclesImages = new ArrayList<>();
    private static Habitat habitatModel;
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(SECOND), this::updateVehicles));

//    AnimationTimer at = new AnimationTimer();

    public void initialize() {

        stopBtn.setDisable(true);
        stopMenuItem.setDisable(true);

        hideTimeRadioBtn.setToggleGroup(timeToggleGroup);
        showTimeRadioBtn.setToggleGroup(timeToggleGroup);
        showTimeRadioBtn.setSelected(true);
        showTimeMenuItem.setDisable(true);

        currentVehicles.setDisable(true);
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
    private void showTimeMenuItemSelected() {
        changeSpawnTimeVisibility();
    }
    @FXML
    private void hideTimeMenuItemSelected() {
        changeSpawnTimeVisibility();
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
    private void startBtnClicked() {
        startSpawn();
    }
    @FXML
    private void stopBtnClicked() {
        stopSpawn();
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
    private void checkLifetimeTextFieldValue(TextField textField, String errorMsg, VehicleSpawnParameters vehicleSpawnParameters) {
        String text = textField.getText();
        if (!text.isEmpty()) {
            int lifetime;
            try {
                lifetime = Integer.parseInt(text);
                if (lifetime > 0)
                    vehicleSpawnParameters.setLifetime(lifetime);
                else {
                    textField.setText(vehicleSpawnParameters.getLifetime()+"");
                    alert("Invalid input", errorMsg + text);
                }
            } catch (NumberFormatException e) {
                textField.setText(vehicleSpawnParameters.getLifetime()+"");
                alert("Invalid input", errorMsg + text);
            }
        } else {
            textField.setText(vehicleSpawnParameters.getLifetime()+"");
        }
    }
    private void checkSpawnTimeTextFieldValue(TextField textField, String vehicleType, VehicleSpawnParameters vehicleSpawnParameters) {
        String text = textField.getText();
        if (!text.isEmpty()) {
            int spawnTime;
            try {
                spawnTime = Integer.parseInt(text);
                if (spawnTime > 0)
                    vehicleSpawnParameters.setGenerationTime(spawnTime);
                else {
                    textField.setText(vehicleSpawnParameters.getGenerationTime()+"");
                    alert("Invalid input", vehicleType + " spawn time cannot be equal: " + text);
                }

            } catch (NumberFormatException e) {
                textField.setText(vehicleSpawnParameters.getGenerationTime()+"");
                alert("Invalid input", vehicleType + " spawn time cannot be equal: " + text);
            }
        } else {
            textField.setText(vehicleSpawnParameters.getGenerationTime()+"");
        }
    }
    @FXML
    private void carSpawnTimeTextFieldMouseReleased() {
        checkSpawnTimeTextFieldValue(carSpawnTimeTextField, "Car", habitatModel.getCarSpawnParameters());
    }
    @FXML
    private void motocycleSpawnTimeTextFieldMouseReleased() {
        checkSpawnTimeTextFieldValue(motocycleSpawnTimeTextField, "Motorcycle", habitatModel.getMotoSpawnParameters());
    }
    @FXML
    private void motocycleLifeTimeTextFieldMouseReleased() {
        checkLifetimeTextFieldValue(motorcycleLifeTimeTextField, "Motorcycle lifetime cannot be equal: ", habitatModel.getMotoSpawnParameters());
    }
    @FXML
    private void carLifeTimeTextFieldMouseReleased() {
        checkLifetimeTextFieldValue(carLifeTimeTextField, "Motorcycle lifetime cannot be equal: ", habitatModel.getCarSpawnParameters());
    }
@FXML
private void showTimeRadioBtnSelected() {
    changeSpawnTimeVisibility();
    }
    @FXML
    private void hideTimeRadioBtnSelected() {
        changeSpawnTimeVisibility();
    }

    @FXML
    private void showInfoCheckBoxSelected() {
        if (showInfoCheckBox.isSelected()) {
            showInfoCheckBox();
        } else {
            hideInfoCheckBox();
        }

    }
    @FXML
    private void showInfoMenuItemCheckBoxSelected() {
        if (showInfoMenuItem.isSelected()) {
            showInfoCheckBox();
        } else {
            hideInfoCheckBox();
        }
    }
    void showInfoCheckBox() {
        showInfoCheckBox.setSelected(true);
        showInfoMenuItem.setSelected(true);
    }
    void hideInfoCheckBox() {
        showInfoCheckBox.setSelected(false);
        showInfoMenuItem.setSelected(false);
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
        motorcycleLifeTimeTextField.setText(this.habitatModel.getMotoSpawnParameters().getLifetime()+"");
        carLifeTimeTextField.setText(this.habitatModel.getCarSpawnParameters().getLifetime()+"");
    }
    public void changeSpawnTimeVisibility() {
        if (spawnTimeText.isVisible()) {
            hideTimeRadioBtn.setSelected(true);
            hideTimeMenuItem.setDisable(true);
            showTimeMenuItem.setDisable(false);
            spawnTimeText.setVisible(false);
        } else {
            showTimeRadioBtn.setSelected(true);
            showTimeMenuItem.setDisable(true);
            hideTimeMenuItem.setDisable(false);
            spawnTimeText.setVisible(true);
        }
    }
    public void startSpawn() {
        startBtn.setDisable(true);
        startMenuItem.setDisable(true);
        stopBtn.setDisable(false);
        stopMenuItem.setDisable(false);
        currentVehicles.setDisable(false);

        habitatModel.setSimulationRunning(true);
        habitatModel.clear();
        habitatPane.getChildren().clear();
        vehiclesImages.clear();

        spawnTimeText.setText(habitatModel.getTextAboutTypeAndNumbers().get("T").getFinishedInformation());
        timeline.play();
        animationTimer.start();
    }
    public void stopSpawn() {
        if (showInfoCheckBox.isSelected()) {
            timeline.stop();
            animationTimer.stop();

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
                startMenuItem.setDisable(false);
                stopBtn.setDisable(true);
                stopMenuItem.setDisable(true);
                habitatModel.setSimulationRunning(false);
                currentVehicles.setDisable(true);
                timeline.stop();
                animationTimer.stop();
            } else {
                timeline.play();
                animationTimer.start();
            }
        } else {
            startBtn.setDisable(false);
            startMenuItem.setDisable(false);
            stopBtn.setDisable(true);
            stopMenuItem.setDisable(true);
            habitatModel.setSimulationRunning(false);
            currentVehicles.setDisable(true);
            timeline.stop();
            animationTimer.stop();
        }
    }
    @FXML
    Button currentVehicles;
    @FXML
    public void currentVehiclesBtnClicked() {
        showCurrentVehiclesDialogWindow();
    }
    public void showCurrentVehiclesDialogWindow() {
        if (habitatModel.getSimulationRunning()) {
            timeline.stop();
            animationTimer.stop();
//            currentVehicles.setDisable(true);
            Dialog<ButtonType> dialog = new Dialog<>();

            dialog.setTitle("Current vehicles");
            dialog.setHeaderText("Current vehicles");
            VBox outer = new VBox();
            ListView<Text> inner = new ListView<Text>();
//            String table = new String();
            for (var vehicle : habitatModel.getVehicles()) {
                String note = new String();
                note += "id: " + vehicle.getId();
                note += " type: ";
                if (vehicle instanceof Car) {
                    note += "car ";
                } else if (vehicle instanceof Motorcycle) {
                    note += "motorcycle ";
                }
                note += " birth time: " + habitatModel.getVehiclesBirthTime().get(vehicle.getId());
//                note += "\n";
                inner.getItems().add(new Text(note));
            }
            outer.getChildren().addAll(inner);
            dialog.getDialogPane().setContent(outer);
//            dialog.setContentText(table);
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButton);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent()) {
                timeline.play();
                animationTimer.start();
            } else {
                timeline.play();
                animationTimer.start();
            }
        }
    }
    private  VehicleImage findVehicleImageById(int id) {
        VehicleImage res = null;
        for(var vehicleImage : vehiclesImages){
            if(vehicleImage.getVehicleId() == id ){
                res = vehicleImage;
                break;
            }
        }
        return res;
    }
    public synchronized void updateVehicles(ActionEvent event) {

        Habitat.getHabitat().getTextAboutTypeAndNumbers().get("T").increaseNumbers();
        int spawnTime = Habitat.getHabitat().getTextAboutTypeAndNumbers().get("T").getNumbers();
        spawnTimeText.setText(Habitat.getHabitat().getTextAboutTypeAndNumbers().get("T").getFinishedInformation());
        Habitat.getHabitat().Update(spawnTime);
    }
    @Override
    public void handle(Event event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            KeyEvent keyEvent = (KeyEvent)event;
            switch (keyEvent.getCode()) {
                case B -> {
                    if (!habitatModel.getSimulationRunning()) {
                        startSpawn();
                    }
                }
                case E -> {
                    if (habitatModel.getSimulationRunning()) {
                        stopSpawn();
                    }
                }
                case T -> {
                    changeSpawnTimeVisibility();
                }
            }
        }
    }

    AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            drawVehicles();
//            System.out.println("drawVehicles");
            try {
                long step = 1;
                Thread.sleep(step);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    };
    private void drawVehicles() {
//        habitatPane.getChildren().clear();

        ArrayList<Vehicle> vehicles = Habitat.getHabitat().getVehicles();
        for (int i = 0; i < vehicles.size(); i++) { // add new
            Vehicle vehicle = vehicles.get(i);
            Image image = new Image(getClass().getResource(vehicle.getImagePath()).toString(), 50, 50, false, false);
            ImageView imageView = new ImageView(image);
            imageView.setX(vehicle.getX());
            imageView.setY(vehicle.getY());
            habitatPane.getChildren().add(imageView);
        }
    }
}