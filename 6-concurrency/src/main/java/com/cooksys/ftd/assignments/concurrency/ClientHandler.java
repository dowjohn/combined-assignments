package com.cooksys.ftd.assignments.concurrency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    public ClientHandler (Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            BufferedReader readInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String inputted = readInput.readLine();
                if (inputted.equals("Done")) {
                    socket.close();
                    break;
                } else {
                    System.out.println(inputted);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
