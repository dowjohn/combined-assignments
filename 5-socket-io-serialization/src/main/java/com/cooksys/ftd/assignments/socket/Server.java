package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Utils {

    /**
     * Reads a {@link Student} object from the given file path
     *
     * @param studentFilePath the file path from which to read the student config file
     * @param jaxb the JAXB context to use during unmarshalling
     * @return a {@link Student} object unmarshalled from the given file path
     */
    public static Student loadStudent(String studentFilePath, JAXBContext jaxb) {
        Student student;
        try {
            File file = new File(studentFilePath);
            JAXBContext context = JAXBContext.newInstance(Config.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            student =(Student) unmarshaller.unmarshal(file);
            return student;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void listenAndSend() {
        Config config = Utils.loadConfig("./config/config.xml", Utils.createJAXBContext());
        Thread openSocket = new Thread(() -> {
            FileInputStream fileInputStream = null;
            BufferedInputStream bufferedInputStream = null;
            OutputStream outputStream = null;
            ServerSocket serverSocket = null;
            Socket connectionSocket = null;

            while(true) {
            	System.out.println("test");
                try {
                    serverSocket = new ServerSocket(config.getLocal().getPort());
                    connectionSocket = serverSocket.accept();
                    System.out.println("Socket Established");
                    outputStream = new BufferedOutputStream(connectionSocket.getOutputStream());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (outputStream != null) {
                    try {
                        System.out.println("outputstream is not null");
                        File file = new File(config.getStudentFilePath());
                        byte[] fileBytes = new byte[(int) file.length()];
                        fileInputStream = new FileInputStream(file);
                        bufferedInputStream = new BufferedInputStream(fileInputStream);
                        bufferedInputStream.read(fileBytes,0, fileBytes.length);
                        outputStream = connectionSocket.getOutputStream();
                        System.out.println("its doin somthing");
                        outputStream.write(fileBytes, 0, fileBytes.length);
                        outputStream.flush();
                        outputStream.close();
                        connectionSocket.close();
                        System.out.println("k it closed i gues");
                        //file sent
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("error caught");
                    } finally {
                       //idk
                    }
                }
            }
        });
        openSocket.start();
    }

    /**
     * The server should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
     * <project-root>/config/config.xml path, using the "port" property of the embedded
     * {@link com.cooksys.ftd.assignments.socket.model.LocalConfig} object to create a server socket that
     * listens for connections on the configured port.
     *
     * Upon receiving a connection, the server should unmarshal a {@link Student} object from a file location
     * specified by the config's "studentFilePath" property. It should then re-marshal the object to xml over the
     * socket's output stream, sending the object to the client.
     *
     * Following this transaction, the server may shut down or listen for more connections.
     */
    public static void main(String[] args) {
        System.out.println("Begin transfer...server");
        listenAndSend();

    }
}
