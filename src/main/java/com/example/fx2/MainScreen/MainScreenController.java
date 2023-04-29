package com.example.fx2.MainScreen;

import com.example.fx2.MainScreen.AI.BaseAI;
import com.example.fx2.MainScreen.models.*;
import com.example.fx2.MainScreen.views.VehicleImage;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

public class MainScreenController implements EventHandler {
    private static final double SECOND = 1000;
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
    ObservableList <Integer> threadPriorityItems = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10);

    public void initialize() {
        initTerminal();
         motorcycleAIThreadPriorityComboBox.setItems(threadPriorityItems);
        carAIThreadPriorityComboBox.setItems(threadPriorityItems);

        manageAi(aiController.getMotorcycleAI(), false);
        manageAi(aiController.getCarAI(), false);
//        aiController.waitAI();
        stopBtn.setDisable(true);
        stopMenuItem.setDisable(true);

        hideTimeRadioBtn.setToggleGroup(timeToggleGroup);
        showTimeRadioBtn.setToggleGroup(timeToggleGroup);
        showTimeRadioBtn.setSelected(true);
        showTimeMenuItem.setDisable(true);

        motorcycleAICheckBox.setSelected(true);
        carAICheckBox.setSelected(true);

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
    public Pane getHabitatPane() {
        return habitatPane;
    }
    public void setHabitatModel(Habitat habitatModel) {
        this.habitatModel = habitatModel;
        downloadSimulationSettings();
        carChanceSlider.setValue(this.habitatModel.getCarSpawnParameters().getGenerationProbability());
        motocycleChanceSlider.setValue(this.habitatModel.getMotoSpawnParameters().getGenerationProbability());
        motocycleSpawnTimeTextField.setText(this.habitatModel.getMotoSpawnParameters().getGenerationTime()+"");
        carSpawnTimeTextField.setText(this.habitatModel.getCarSpawnParameters().getGenerationTime()+"");
        motorcycleLifeTimeTextField.setText(this.habitatModel.getMotoSpawnParameters().getLifetime()+"");
        carLifeTimeTextField.setText(this.habitatModel.getCarSpawnParameters().getLifetime()+"");
        if (habitatModel.getSimulationRunning())
            startSpawnWithoutReset();
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
    public void startSpawnWithoutReset() {
        startBtn.setDisable(true);
        startMenuItem.setDisable(true);
        stopBtn.setDisable(false);
        stopMenuItem.setDisable(false);
        currentVehicles.setDisable(false);

        habitatModel.setSimulationRunning(true);

        spawnTimeText.setText(habitatModel.getTextAboutTypeAndNumbers().get("T").getFinishedInformation());
        timeline.play();
        animationTimer.start();
    }
    public void startSpawn() {
//        aiController.notifyAI();
        manageAi(aiController.getMotorcycleAI(), true);
        manageAi(aiController.getCarAI(), true);

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
            manageAi(aiController.getMotorcycleAI(), false);
            manageAi(aiController.getCarAI(), false);

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
                manageAi(aiController.getMotorcycleAI(), false);
                manageAi(aiController.getCarAI(), false);
            } else {
                timeline.play();
                animationTimer.start();
                manageAi(aiController.getMotorcycleAI(), true);
                manageAi(aiController.getCarAI(), true);
            }
        } else {
            manageAi(aiController.getMotorcycleAI(), false);
            manageAi(aiController.getCarAI(), false);
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
    public void pause() {
        timeline.stop();
        animationTimer.stop();
        manageAi(aiController.getMotorcycleAI(), false);
        manageAi(aiController.getCarAI(), false);
    }
    public void resume() {
        timeline.play();
        animationTimer.start();
        manageAi(aiController.getMotorcycleAI(), true);
        manageAi(aiController.getCarAI(), true);
    }
    public void showCurrentVehiclesDialogWindow() {
        if (habitatModel.getSimulationRunning()) {
            timeline.stop();
            animationTimer.stop();
            manageAi(aiController.getMotorcycleAI(), false);
            manageAi(aiController.getCarAI(), false);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Current vehicles");
            dialog.setHeaderText("Current vehicles");
            VBox outer = new VBox();
            ListView<Text> inner = new ListView<Text>();
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
                inner.getItems().add(new Text(note));
            }
            outer.getChildren().addAll(inner);
            dialog.getDialogPane().setContent(outer);
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButton);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent()) {
                timeline.play();
                animationTimer.start();
                manageAi(aiController.getMotorcycleAI(), true);
                manageAi(aiController.getCarAI(), true);
            } else {
                timeline.play();
                animationTimer.start();
                manageAi(aiController.getMotorcycleAI(), true);
                manageAi(aiController.getCarAI(), true);
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
    public void setSimulationTime(int time) {
        Habitat.getInstance().getTextAboutTypeAndNumbers().get("T").setNumbers(time);
        spawnTimeText.setText(Habitat.getInstance().getTextAboutTypeAndNumbers().get("T").getFinishedInformation());
    }
    public void updateVehicles(ActionEvent event) {
        Habitat.getInstance().getTextAboutTypeAndNumbers().get("T").increaseNumbers();
        int spawnTime = Habitat.getInstance().getTextAboutTypeAndNumbers().get("T").getNumbers();
        spawnTimeText.setText(Habitat.getInstance().getTextAboutTypeAndNumbers().get("T").getFinishedInformation());
        Habitat.getInstance().Update(spawnTime);
    }
    @Override
    public void handle(Event event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            KeyEvent keyEvent = (KeyEvent)event;
            switch (keyEvent.getCode()) {
                case B -> {
                    if (!Habitat.getInstance().getSimulationRunning()) {
                        startSpawn();
                    }
                }
                case E -> {
                    if (Habitat.getInstance().getSimulationRunning()) {
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
//            try {
//                long step = 1000/120;
//                System.out.println(now + " " + step);
//                Thread.sleep(step);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }
    };
    private void drawVehicles() {
        habitatPane.getChildren().clear();
        ArrayList<Vehicle> vehicles = Habitat.getInstance().getVehicles();
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle vehicle = vehicles.get(i);
//            vehicle.performBehaviour();
            ImageView imageView;
            if (vehicle instanceof Car) {
                imageView = new ImageView(Car.getImage());
            } else {
                imageView = new ImageView(Motorcycle.getImage());
            }
            imageView.setX(vehicle.getX());
            imageView.setY(vehicle.getY());
            habitatPane.getChildren().add(imageView);
        }
    }
    AIController aiController = new AIController();
    @FXML
    CheckBox motorcycleAICheckBox;
    @FXML
    CheckBox carAICheckBox;
    public void motorcycleAICheckBoxSelected() {
        if (motorcycleAICheckBox.isSelected()) {
            manageAi(aiController.getMotorcycleAI(), true);
        } else {
            manageAi(aiController.getMotorcycleAI(), false);
        }
//        if (motorcycleAICheckBox.isSelected()) {
//            synchronized (aiController.getMotorcycleAI()) {
//                aiController.motorcycleAISetRun(true);
//                aiController.notifyMotorcycleAI();
//            }
//
//        } else {
////            aiController.waitMotorcycleAI();
//            aiController.motorcycleAISetRun(false);
//        }
    }
    @FXML
    public void carAICheckBoxSelected() {
        if (carAICheckBox.isSelected()) {
            manageAi(aiController.getCarAI(), true);
        } else {
            manageAi(aiController.getCarAI(), false);
        }

//        if (carAICheckBox.isSelected()) {
//            synchronized (aiController.getCarAI()) {
//                aiController.carAISetRun(true);
//                aiController.notifyCarAI();
//            }
//        } else {
////            aiController.waitCarAI();
//            aiController.carAISetRun(false);
//        }
    }
    public void manageAi(BaseAI vehicleAI, boolean newState) {
        if (newState) {
            synchronized (vehicleAI) {
                vehicleAI.setRun(true);
                vehicleAI.notify();
            }
        } else {
            vehicleAI.setRun(false);
        }
    }
    @FXML
    ComboBox motorcycleAIThreadPriorityComboBox;
    @FXML
    ComboBox carAIThreadPriorityComboBox;

    @FXML
    public void motorcycleAIThreadPriorityComboBoxItemSelected() {
        aiController.getMotorcycleAI().setPriority((int)motorcycleAIThreadPriorityComboBox.getValue());
    }
    @FXML
    public void carAIThreadPriorityComboBoxItemSelected() {
        aiController.getCarAI().setPriority((int)carAIThreadPriorityComboBox.getValue());
//        System.out.println(aiController.getCarAI().getPriority());
    }
    @FXML
    MenuItem downloadMenuItem;
    @FXML
    MenuItem saveMenuItem;
    @FXML
    MenuItem terminalMenuItem;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    Stage stage;
    @FXML
    public void downloadMenuItemSelected(){
        pause();
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Open Document");//Заголовок диалога
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);//Указываем текущую сцену CodeNote.mainStage
        if (file != null) {
            //Open
//            System.out.println("Процесс открытия файла");
            try{
            ObjectInputStream in=new ObjectInputStream(new FileInputStream(file.getPath()));
            habitatModel.readVehiclesFromFile(in);
            //printing the data of the serialized object
            //closing the stream
            in.close();
            }catch(Exception e){System.out.println(e);}
        }
        resume();
    }

    @FXML
    public void saveMenuItemSelected(){
        pause();
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Save Document");//Заголовок диалога
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(stage);//Указываем текущую сцену CodeNote.mainStage
        if (file != null) {
            //Save

//            System.out.println(file.getPath());
//            System.out.println("Процесс открытия файла");
            try{
                FileOutputStream fout=new FileOutputStream(file.getPath());
            ObjectOutputStream out=new ObjectOutputStream(fout);
//            out.writeObject(s1);
                habitatModel.saveVehiclesInFile(out);
            out.flush();
            out.close();
            }catch(Exception e){System.out.println(e);}
        }
        resume();
    }
    @FXML
    public void terminalMenuItemSelected() throws IOException {
        openTextAreaInNewWindow();
    }

    public Integer parseIntOrNull(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    public int checkCorrectInputReduceMoto(String input)  {
//        System.out.println(input);
        if (input.contains("reduce moto ")) {
//            System.out.println("CONTAIN");
            input = input.replace("reduce moto ","");
//            System.out.println(input);
            if (parseIntOrNull(input) != null) {
//                System.out.println(parseIntOrNull(input));
                return parseIntOrNull(input);
            }
        }
        return -1;
    }
    String terminalTextAreaContent = new String("available commands: Reduce number of motorcycles by n%\nexample: reduce moto 10\n");
//    @FXML
//    TextArea terminalTextArea;
//    @FXML
//    TextField terminalTextField;
    public void terminalKeyPressedEventHandler(ActionEvent event) {
        String input = terminalTextField.getText();
        int percent = checkCorrectInputReduceMoto(input);
        if ( percent>= 0 && percent <= 100) {
//            System.out.println(percent);
            Task task = new Task<Void>() {
                @Override public Void call() {
                    habitatModel.reduceNumberOfMotorcyclesByNPrecent(percent);
                    return null;
                }
            };

            ProgressBar bar = new ProgressBar();
            bar.progressProperty().bind(task.progressProperty());
            new Thread(task).start();
//            habitatModel.reduceNumberOfMotorcyclesByNPrecent(percent);
            terminalTextArea.appendText("\nsuccessfully deleted " + percent + "% motorcycle!");
        } else {
            terminalTextArea.appendText("\nError: " + terminalTextField.getText() + ". Enter correct command!");
        }
    }
    TextArea terminalTextArea = new TextArea(terminalTextAreaContent);
    TextField terminalTextField = new TextField("reduce moto ");
    VBox terminalVBox = new VBox();
    Stage primaryStage = new Stage();
    public void initTerminal() {
        double terminalWidth =350;
        double terminalHeight =400;
        Pane terminalPane = new Pane();
        terminalTextArea.setPrefHeight(terminalHeight - 30);
        terminalTextArea.setEditable(false);
        terminalVBox.getChildren().add(terminalTextArea);
        terminalTextField.setOnAction(this::terminalKeyPressedEventHandler);
        terminalVBox.getChildren().add(terminalTextField);
        terminalPane.getChildren().add(terminalVBox);
        Scene scene = new Scene(terminalPane, terminalWidth, terminalHeight);
        primaryStage.setTitle("Terminal");
        primaryStage.setScene(scene);
        primaryStage.initOwner(stage);
        primaryStage.initModality(Modality.NONE);
        primaryStage.setResizable(false);
    }
    public void openTextAreaInNewWindow() {
        if (habitatModel.getSimulationRunning())
            primaryStage.show();
    }
    Properties properties = new Properties();
    String appConfigPath = "/Users/doctypetdmicloud.com/Desktop/config.txt";
    public void downloadSimulationSettings() {

//        String appConfigPath = getClass().getResource("/settings/config.txt").toString();
        try {
//            InputStream inputStream = this.getClass().getResourceAsStream("/config.txt");
//            properties.load(inputStream);
            properties.load(new FileInputStream(appConfigPath));
//            setSimulationTime(Integer.parseInt(properties.getProperty("simulationTime")));
            habitatModel.setSimulationRunning(Boolean.parseBoolean(properties.getProperty("simulationRunning")));
            habitatModel.getCarSpawnParameters().setLifetime(Integer.parseInt(properties.getProperty("carlifetime")));
            habitatModel.getCarSpawnParameters().setGenerationTime(Integer.parseInt(properties.getProperty("cargenerationTime")));
            habitatModel.getCarSpawnParameters().setGenerationProbability(Integer.parseInt(properties.getProperty("carGenerationProbability")));

            habitatModel.getMotoSpawnParameters().setLifetime(Integer.parseInt(properties.getProperty("motolifetime")));
            habitatModel.getMotoSpawnParameters().setGenerationTime(Integer.parseInt(properties.getProperty("motogenerationTime")));
            habitatModel.getMotoSpawnParameters().setGenerationProbability(Integer.parseInt(properties.getProperty("motoGenerationProbability")));

            boolean simulationTimeVisibility = Boolean.parseBoolean(properties.getProperty("simulationTimeVisibility"));
            spawnTimeText.setVisible(!simulationTimeVisibility);
            changeSpawnTimeVisibility();
            boolean isCarAIRun = Boolean.parseBoolean(properties.getProperty("isCarAIRun"));
            carAICheckBox.setSelected(isCarAIRun);
            carAICheckBoxSelected();
            boolean isMotoAIRun = Boolean.parseBoolean(properties.getProperty("isMotoAIRun"));
            motorcycleAICheckBox.setSelected(isMotoAIRun);
            motorcycleAICheckBoxSelected();
            boolean showInfoIsSelected = Boolean.parseBoolean(properties.getProperty("showInfoIsSelected"));
            showInfoCheckBox.setSelected(showInfoIsSelected);
            showInfoCheckBoxSelected();
            boolean isCurrentVehiclesActive = Boolean.parseBoolean(properties.getProperty("currentVehicles"));
            currentVehicles.setDisable(isCurrentVehiclesActive);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    public void uploadSimulationSettings() {
//        properties.setProperty("simulationTime", Integer.toString(Habitat.getInstance().getTextAboutTypeAndNumbers().get("T").getNumbers()));
        properties.setProperty("simulationRunning",Boolean.toString(habitatModel.getSimulationRunning()));
        properties.setProperty("motoGenerationProbability",Integer.toString(habitatModel.getMotoSpawnParameters().getGenerationProbability()));
        properties.setProperty("motogenerationTime",Integer.toString(habitatModel.getMotoSpawnParameters().getGenerationTime()));
        properties.setProperty("motolifetime",Integer.toString(habitatModel.getMotoSpawnParameters().getLifetime()));
        properties.setProperty("carGenerationProbability",Integer.toString(habitatModel.getCarSpawnParameters().getGenerationProbability()));
        properties.setProperty("cargenerationTime",Integer.toString(habitatModel.getCarSpawnParameters().getGenerationTime()));
        properties.setProperty("carlifetime",Integer.toString(habitatModel.getCarSpawnParameters().getLifetime()));
        properties.setProperty("simulationTimeVisibility",Boolean.toString(spawnTimeText.isVisible()));
        properties.setProperty("isCarAIRun",Boolean.toString(aiController.getCarAI().isRunning()));
        properties.setProperty("isMotoAIRun",Boolean.toString(aiController.getMotorcycleAI().isRunning()));
        properties.setProperty("showInfoIsSelected",Boolean.toString(showInfoCheckBox.isSelected()));
        properties.setProperty("currentVehicles",Boolean.toString(currentVehicles.isDisable()));
        try {
            properties.store(new FileWriter(appConfigPath), null);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}