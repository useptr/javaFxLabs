package com.example.fx2.server;

import com.example.fx2.MainScreen.models.Vehicle;
import javafx.util.Pair;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientsHandler implements Runnable{
    private static Map<Integer,ClientsHandler> connections =  new HashMap<>();
    private static int idCount = 0;
    private Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;


    private int id;
    public ClientsHandler(Socket socket) {
        try {
            this.clientSocket = socket;
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

            idCount++;
            id = idCount;
            connections.put(id, this);

            objectOutputStream.reset();
            objectOutputStream.writeObject(id);
            objectOutputStream.flush();
//        out.write("Hello");
//            broadcastMsg("new client");
            System.out.println("client number " + id + " connected");
            broadcastIds(getCurrentIds());
        } catch (IOException e) {
            close();
        }
    }
    public ArrayList<Integer> getCurrentIds() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Map.Entry<Integer, ClientsHandler>  clients : connections.entrySet()) {
            ids.add(clients.getKey());
        }
        return ids;
    }
    public void removeClientHandler() {
        connections.remove(id);
        System.out.println("client number " + id + " left");
        broadcastIds(getCurrentIds());
    }
//    public void sendMsg(String msg) throws IOException {
//        out.write(msg);
//        out.newLine();
//        out.flush();
//    }

    public void broadcastIds(ArrayList<Integer> currentIds) {
        if (currentIds != null) {
            for (Map.Entry<Integer, ClientsHandler>  clients : connections.entrySet()) {
                ClientsHandler clientsHandler = clients.getValue();
                if (clientsHandler != null) {
                    try {
                        clientsHandler.objectOutputStream.reset();
                        clientsHandler.objectOutputStream.writeObject(currentIds);
                        clientsHandler.objectOutputStream.flush();
                    } catch (IOException e) {
                        close();
                    }
                }
            }
        }
    }
//    public void broadcastMsg(String msg) throws IOException {
//        if (msg != null) {
//            for (ClientsHandler clientsHandler : connections) {
//                if (clientsHandler != null) {
//                    clientsHandler.sendMsg(msg);
//                }
//            }
//        }
//    }
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
//                    msg = in.readLine();
                    Object object = objectInputStream.readObject();

                    if (object.getClass() == Pair.class) {
                        if(((Pair<?, ?>) object).getValue().getClass() == Integer.class) { // пришли 2 id

                            Pair<Integer,Integer> pair = (Pair<Integer,Integer>)object;
                            System.out.println("request send vehicles from " + pair.getValue() + " to " + pair.getKey());
                            ClientsHandler exchangeWith = connections.get(pair.getValue());
                            if (exchangeWith != null) {
                                exchangeWith.objectOutputStream.reset();
                                exchangeWith.objectOutputStream.writeObject(pair);
                                exchangeWith.objectOutputStream.flush();
                            }
                        } else {
                            Pair<Integer,ArrayList<Vehicle>> pair = (Pair<Integer,ArrayList<Vehicle>>)object;

                            System.out.println("send " +  pair.getValue().size() + " vehicles to " + pair.getKey());
//                            for (Vehicle vehicle : pair.getValue()) {
//                                System.out.println(vehicle);
//                            }

                            ClientsHandler exchangeWith = connections.get(pair.getKey());
                            if (exchangeWith != null) {
                                exchangeWith.objectOutputStream.reset();
                                exchangeWith.objectOutputStream.writeObject(pair);
                                exchangeWith.objectOutputStream.flush();
                            }

                        }
                    }
//                    objectInputStream.reset();
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
                } catch (ClassNotFoundException e) {
                    close();
                }
            }

    }
}
