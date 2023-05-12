package com.example.fx2;

import com.example.fx2.server.ClientsHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private  ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }
    public void start() {
        try {
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                ClientsHandler clientsHandler = new ClientsHandler(clientSocket);

                Thread thread = new Thread(clientsHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void close() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
e.printStackTrace();
        }
    }
        public static void main(String[] args) throws IOException {

            Server server = new Server(8000);
            server.start();
        }
}
