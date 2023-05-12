package com.example.fx2;

import com.example.fx2.MainScreen.MainScreenController;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private MainScreenController mainScreenController;
    private Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;

    private ObjectInputStream objectInputStream;

    public Client(String serverName, int port) {
        try {
            clientSocket = new Socket(serverName, port);
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            close();
        }
    }
    public void sendMsg(String msg) {
        try {
        out.write(msg);
        out.newLine();
        out.flush();
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
                                                mainScreenController.updateConnectionsViews(finalIds);
                                            });
                                            return null;
                                        }
                                    };
                                }
                            };
                            New_Service.start();
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
