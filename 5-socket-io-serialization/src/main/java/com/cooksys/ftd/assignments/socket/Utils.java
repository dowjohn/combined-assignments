package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.LocalConfig;
import com.cooksys.ftd.assignments.socket.model.RemoteConfig;
import com.cooksys.ftd.assignments.socket.model.Student;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Shared static methods to be used by both the {@link Client} and {@link Server} classes.
 */
public class Utils {
    /**
     * @return a {@link JAXBContext} initialized with the classes in the
     * com.cooksys.socket.assignment.model package
     */
    public static JAXBContext createJAXBContext() {
        try {
            return JAXBContext.newInstance(Config.class, LocalConfig.class, RemoteConfig.class,Student.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Reads a {@link Config} object from the given file path.
     *
     * @param configFilePath the file path to the config.xml file
     * @param jaxb the JAXBContext to use
     * @return a {@link Config} object that was read from the config.xml file
     */
    public static Config loadConfig(String configFilePath, JAXBContext jaxb) {
        Config config;
        try {
            File file = new File(configFilePath);
            JAXBContext context = JAXBContext.newInstance(Config.class, Student.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            config =(Config) unmarshaller.unmarshal(file);
            return config;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Student readStudent(File studentContactFile, JAXBContext jaxb) {
        Student studentObject;
        Unmarshaller unmarshaller;
        try {
            unmarshaller = jaxb.createUnmarshaller();
            studentObject = (Student) unmarshaller.unmarshal(studentContactFile);
            return studentObject;
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            System.out.println("it failed");
            e.printStackTrace();
        }
        return null;
    }
}
