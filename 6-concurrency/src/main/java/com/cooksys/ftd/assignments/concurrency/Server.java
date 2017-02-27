package com.cooksys.ftd.assignments.concurrency;

import com.cooksys.ftd.assignments.concurrency.model.config.ServerConfig;

import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private ServerConfig config;

    public Server(ServerConfig config) {
        this.config = config;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(config.getPort());
            Socket openSocket = serverSocket.accept();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("server failed");
        }
    }

    public ServerConfig getConfig() {
        return config;
    }

    public void setConfig(ServerConfig config) {
        this.config = config;
    }
}
