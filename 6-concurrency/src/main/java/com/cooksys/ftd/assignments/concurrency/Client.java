package com.cooksys.ftd.assignments.concurrency;

import com.cooksys.ftd.assignments.concurrency.model.config.ClientConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.Config;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Client implements Runnable {
    private ClientConfig config
    public Client(ClientConfig config) {
        this.config = config;
    }

    @Override
    public void run() {
        throw new NotImplementedException();
    }
}
