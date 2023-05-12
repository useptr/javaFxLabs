package com.example.fx2;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
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
                        ids = (ArrayList<Integer>)object;
                        System.out.println("update ids");
                        for (int id : ids) {
                            System.out.println(id);
                        }
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
}
