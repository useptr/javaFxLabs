package com.example.fx2;

import com.example.fx2.MainScreen.MainScreenController;
import com.example.fx2.MainScreen.models.Vehicle;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.util.Pair;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private MainScreenController mainScreenController;
    private Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;

    private int id;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public Client(Socket socket) {
        try {
            clientSocket = socket;
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            close();
        }
    }
/*    public Client(String serverName, int port) {
        try {
            clientSocket = new Socket(serverName, port);
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            close();
        }
    }*/
    public void sendMsg(String msg) {
        try {
        out.write(msg);
        out.newLine();
        out.flush();
        } catch (IOException e) {
            close();
        }
    }
    public void exchangeVehicles(int exchangeWith) {
        Pair<Integer,Integer> pair = new Pair<>(id, exchangeWith);
        try {
            objectOutputStream.reset();
        objectOutputStream.writeObject(pair);
//        objectOutputStream.flush();
        } catch (IOException e) {
            close();
        }
        System.out.println("send " + mainScreenController.getHabitatModel().getVehicles().size() + " vehicles to " + exchangeWith);
        sendVehiclesTo(exchangeWith, mainScreenController.getHabitatModel().getVehicles());
    }
    public void sendVehiclesTo(int exchangeWith, ArrayList<Vehicle> vehicles) {
        try {
            Pair<Integer,ArrayList<Vehicle>> pair = new Pair<>(exchangeWith, vehicles);
            objectOutputStream.reset();
            objectOutputStream.writeObject(pair);
//            objectOutputStream.flush();
        } catch (IOException e) {
            close();
        }
    }
    public void listenForMsg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Integer> ids = new ArrayList<>();
//                String msgFromServer;
                while (clientSocket.isConnected()) {
                    try {
                        Object object = objectInputStream.readObject();

                        if (object.getClass() == ArrayList.class) {
                            ids = (ArrayList<Integer>) object;
                            ArrayList<Integer> finalIds = ids;
                            Service New_Service = new Service() {
                                @Override
                                protected Task createTask() {
                                    return new Task() {
                                        @Override
                                        protected Object call() throws Exception {
                                            Platform.runLater(() -> {
                                                mainScreenController.updateConnectionsViews(finalIds, id);
                                            });
                                            return null;
                                        }
                                    };
                                }
                            };
                            New_Service.start();
                        }
                        if (object.getClass() == Integer.class) {
                            id = (int) object;
                            System.out.println("my id is " + id);
                        }
                        if (object.getClass() == Pair.class) {
                            if(((Pair<?, ?>) object).getValue().getClass() == Integer.class) { // пришли 2 id

                                Pair<Integer,Integer> pair = (Pair<Integer,Integer>)object;
                                System.out.println("send vehicles from " + pair.getValue() + " to " + pair.getKey());
                                sendVehiclesTo(pair.getKey(), mainScreenController.getHabitatModel().getVehicles());
                            } else {

                                Pair<Integer,ArrayList<Vehicle>> pair = (Pair<Integer,ArrayList<Vehicle>>)object;
                                System.out.println("get " + pair.getValue().size() + " vehicles from another client");
                                mainScreenController.getHabitatModel().setVehiclesFromClient(pair.getValue());
//                                for (Vehicle vehicle : pair.getValue()) {
//                                    System.out.println("Vehicle: "+ vehicle);
//                                }

                                // set new Vehicles
                            }

                        }
//                        mainScreenController.updateConnectionsViews(ids);
//                        System.out.println("update ids");
//                        for (int id : ids) {
//                            System.out.println(id);
//                        }
//                        msgFromServer = in.readLine();
//                        System.out.println(msgFromServer);
                    } catch (IOException e) {
                        close();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }
//    public void listenForMsg() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String msgFromServer;
//                while (clientSocket.isConnected()) {
//                    try {
//                    msgFromServer = in.readLine();
//                        System.out.println(msgFromServer);
//                    } catch (IOException e) {
//                        close();
//                    }
//                }
//            }
//        }).start();
//    }
    public void close() {
        try {
            in.close();
            out.close();
            if (!clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }
}
