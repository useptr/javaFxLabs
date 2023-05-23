package com.example.fx2;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.Scanner;

/**
 * Finds an available port on localhost.
 */
public class PortFinder {

    // the ports below 1024 are system ports
    private static final int MIN_PORT_NUMBER = 1024;

    // the ports above 49151 are dynamic and/or private
    private static final int MAX_PORT_NUMBER = 49151;

    /**
     * Finds a free port between
     * {@link #MIN_PORT_NUMBER} and {@link #MAX_PORT_NUMBER}.
     *
     * @return a free port
     * @throw RuntimeException if a port could not be found
     */
    public static boolean findFreePort( int num) {
/*        Scanner in = new Scanner(System.in);
        System.out.print("Input a port: ");
        int num = in.nextInt();*/
        if (available(num)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Returns true if the specified port is available on this host.
     *
     * @param port the port to check
     * @return true if the port is available, false otherwise
     */
    private static boolean available(final int port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            return true;
        } catch (final IOException e) {
            return false;
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (final IOException e) {
                    // can never happen
                }
            }
        }
    }
}