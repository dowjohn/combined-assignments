package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.LocalConfig;
import com.cooksys.ftd.assignments.socket.model.RemoteConfig;
import com.cooksys.ftd.assignments.socket.model.Student;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.net.Socket;

public class Client {

    //helper method for setting config.xml
    private static void writeToConfig() {
        Config config = new Config();
        config.setLocal(new LocalConfig());
        config.setRemote(new RemoteConfig());
        config.getLocal().setPort(8100);
        config.getRemote().setPort(8100);
        config.getRemote().setHost("Hostess");
        config.setStudentFilePath("5-socket-io-serialization/config/student.xml");
        try {
            File file = new File("5-socket-io-serialization/config/config.xml");
            JAXBContext context = JAXBContext.newInstance(Config.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(config, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeToStudent() {
        try {
            Student student = new Student();
            student.setFavoriteIDE("Intellij");
            student.setFavoriteLanguage("Javascript");
            student.setFavoriteParadigm("Functional");
            student.setFirstName("John");
            student.setLastName("Dow");
            File file = new File("5-socket-io-serialization/config/student.xml");
            JAXBContext context = JAXBContext.newInstance(Student.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(student, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void requestAndReceiveData() {
        int bytesRead;
        int current = 0;

        Config config = Utils.loadConfig("5-socket-io-serialization/config/config.xml", Utils.createJAXBContext());
        String serverIP = config.getRemote().getHost();
        int serverPort = config.getRemote().getPort();
        File newFile = new File("5-socket-io-serialization/config/new-student.xml");

        Socket clientSocket = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;



        try {
            clientSocket = new Socket( serverIP , serverPort );
            System.out.println("connecting...");
            byte [] mybytearray  = new byte [6500000];
            inputStream = clientSocket.getInputStream();
            fileOutputStream = new FileOutputStream(newFile);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bytesRead = inputStream.read(mybytearray,0,mybytearray.length);
            current = bytesRead;

            do {
                bytesRead = inputStream.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);

            bufferedOutputStream.write(mybytearray, 0 , current);
            bufferedOutputStream.flush();
            System.out.println("File "
                    + " downloaded (" + current + " bytes read)");
            bufferedOutputStream.close();
            clientSocket.close();
        } catch (IOException ex) {
            // Do exception handling
        }

    }

    /**
     * The client should load a Config object from the
     * <project-root>/config/config.xml path, using the "port" and "host" properties of the embedded
     * RemoteConfig object to create a socket that connects to
     * a Server listening on the given host and port.
     *
     * The client should expect the server to send a {@link com.cooksys.ftd.assignments.socket.model.Student} object
     * over the socket as xml, and should unmarshal that object before printing its details to the console.
     */
    public static void main(String[] args) {
        System.out.println("Begin transfer...client");
        requestAndReceiveData();
        Student student = Utils.readStudent(new File("5-socket-io-serialization/config/new-student.xml"), Utils.createJAXBContext());
        System.out.println(student.toString());
    }
}
