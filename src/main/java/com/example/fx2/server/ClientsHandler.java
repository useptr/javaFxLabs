package com.example.fx2.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientsHandler implements Runnable{
    private static ArrayList<ClientsHandler> connections = new ArrayList<>();
    private static int idCount = 0;
    private Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;
    private ObjectOutputStream objectOutputStream;

    private int id;
    public ClientsHandler(Socket socket) {
        try {
            this.clientSocket = socket;
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            connections.add(this);

            idCount++;
            id = idCount;
//        out.write("Hello");
//            broadcastMsg("new client");
            System.out.println("client number " + id + " connected");
            broadcastIds(getCurrentIds());
        } catch (IOException e) {
            close();
        }
    }
    ArrayList<Integer> getCurrentIds() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (ClientsHandler clientsHandler : connections) {
            ids.add(clientsHandler.id);
        }
        return ids;
    }
    public void removeClientHandler() {
        connections.remove(this);
        System.out.println("client number " + id + " left");
        broadcastIds(getCurrentIds());
//        broadcastMsg("left us");
    }
    public void sendMsg(String msg) throws IOException {
        out.write(msg);
        out.newLine();
        out.flush();
    }

    public void broadcastIds(ArrayList<Integer> currentIds) {
        if (currentIds != null) {
            for (ClientsHandler clientsHandler : connections) {
                if (clientsHandler != null) {
                    try {
                        clientsHandler.objectOutputStream.writeObject(currentIds);
                        clientsHandler.objectOutputStream.flush();
                    } catch (IOException e) {
                        close();
                    }
                }
            }
        }
    }
    public void broadcastMsg(String msg) throws IOException {
        if (msg != null) {
            for (ClientsHandler clientsHandler : connections) {
                if (clientsHandler != null) {
                    clientsHandler.sendMsg(msg);
                }
            }
        }
    }
    public void close() {
        removeClientHandler();
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
    @Override
    public void run() {
            String msg;
            while (clientSocket.isConnected()) {
                try {
                    msg = in.readLine();
//                    broadcastMsg(msg);

//                if (msg.startsWith("/new client ")) {
//                    String[] msgSplit = msg.split(" ", 2);
//                    if (msgSplit.length == 2) {
//                        broadcastMsg("new client " + msgSplit[1]);
//                    }
//                } else {
//                    broadcastMsg(msg);
//                }
                } catch (IOException e) {
                    close();
                    break;
                }
            }

    }
}
