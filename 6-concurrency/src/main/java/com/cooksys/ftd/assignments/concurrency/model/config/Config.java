package com.cooksys.ftd.assignments.concurrency.model.config;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.nio.file.Path;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Config {
    private Config config;
    private ServerConfig server;
    private ClientConfig client;

    /**
     * Loads a {@link Config} object from the given xml file path
     *
     * @param path the path at which an xml configuration can be found
     * @return the unmarshalled {@link Config} object
     */
    public static Config load(Path path) {
        Config config = null;
        try {
            JAXBContext context = JAXBContext.newInstance(Config.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            config = (Config) unmarshaller.unmarshal(path.toFile());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return config;
    }
    public Config() {
        //default constructor;
    }
    public Config (Path path) {
        this.config = load(path);
        this.server = config.getServer();
        this.client = config.getClient();
    }

    public ServerConfig getServer() {
        return server;
    }

    public void setServer(ServerConfig server) {
        this.server = server;
    }

    public ClientConfig getClient() {
        return client;
    }

    public void setClient(ClientConfig client) {
        this.client = client;
    }
}
