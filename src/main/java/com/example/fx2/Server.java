package com.example.fx2;

import com.example.fx2.server.ClientsHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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


            int port = 8000;
/*            PortFinder portFinder = new PortFinder();
            Scanner scanner = new Scanner( System.in );
            System.out.println("enter port: ");
            port = scanner.nextInt();
            while (!portFinder.findFreePort(port)) {
                System.out.println("port "+ port +"  is busy. Enter another port: ");
                port = scanner.nextInt();
            }
            System.out.println("ServerSocket work with port "+ port);*/
            Server server = new Server(port);
            server.start();
        }
}
