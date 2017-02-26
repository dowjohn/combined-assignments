package com.cooksys.ftd.assignments.file;

import com.cooksys.ftd.assignments.file.model.Contact;
import com.cooksys.ftd.assignments.file.model.Instructor;
import com.cooksys.ftd.assignments.file.model.Session;
import com.cooksys.ftd.assignments.file.model.Student;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {
	public static final String STUDENT_DIRECTORY_STRING = "4-file-io-serialization/input/memphis/08-08-2016/students/";
	public static final String INSTRUCTOR_STRING = "4-file-io-serialization/input/memphis/08-08-2016/instructor.xml";
	public static final String SESSION_STRING = "4-file-io-serialization/output/session.xml";

	/**
	 * Creates a {@link Student} object using the given studentContactFile. The
	 * studentContactFile should be an XML file containing the marshaled form of
	 * a {@link Contact} object.
	 *
	 * @param studentContactFile
	 *            the XML file to use
	 * @param jaxb
	 *            the JAXB context to use
	 * @return a {@link Student} object built using the {@link Contact} data in
	 *         the given file
	 * @throws JAXBException
	 */
	public static Student readStudent(File studentContactFile, JAXBContext jaxb) {
		Contact contactObject = new Contact();
		Unmarshaller unmarshaller;
		try {
			unmarshaller = jaxb.createUnmarshaller();
			contactObject = (Contact) unmarshaller.unmarshal(studentContactFile);
			return new Student(contactObject);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			System.out.println("it failed");
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Creates a list of {@link Student} objects using the given directory of
	 * student contact files.
	 *
	 * @param studentDirectory
	 *            the directory of student contact files to use
	 * @param jaxb
	 *            the JAXB context to use
	 * @return a list of {@link Student} objects built using the contact files
	 *         in the given directory
	 */
	public static List<Student> readStudents(File studentDirectory, JAXBContext jaxb) {
		List<Student> studentList = new ArrayList<>();
		File[] studentsDirectories;
		studentsDirectories = studentDirectory.listFiles();
		for (File studentLocation : studentsDirectories) {
			Student newStudent = readStudent(studentLocation, jaxb);
			studentList.add(newStudent);
			System.out.println(newStudent.getContact().getFirstName());
		}
		return studentList;
	}

	/**
	 * Creates an {@link Instructor} object using the given
	 * instructorContactFile. The instructorContactFile should be an XML file
	 * containing the marshaled form of a {@link Contact} object.
	 *
	 * @param instructorContactFile
	 *            the XML file to use
	 * @param jaxb
	 *            the JAXB context to use
	 * @return an {@link Instructor} object built using the {@link Contact} data
	 *         in the given file
	 */
	public static Instructor readInstructor(File instructorContactFile, JAXBContext jaxb) {
		Contact contactObject;
		Unmarshaller unmarshaller;
		try {
			unmarshaller = jaxb.createUnmarshaller();
			contactObject = (Contact) unmarshaller.unmarshal(instructorContactFile);
			return new Instructor(contactObject);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			System.out.println("it failed");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates a {@link Session} object using the given rootDirectory. A
	 * {@link Session} root directory is named after the location of the
	 * {@link Session}, and contains a directory named after the start date of
	 * the {@link Session}. The start date directory in turn contains a
	 * directory named `students`, which contains contact files for the students
	 * in the session. The start date directory also contains an instructor
	 * contact file named `instructor.xml`.
	 *
	 * @param sessionDirectory
	 *            the root directory of the session data, named after the
	 *            session location
	 * @param jaxb
	 *            the JAXB context to use
	 * @return a {@link Session} object built from the data in the given
	 *         directory
	 */
	public static Session readSession(File sessionDirectory, JAXBContext jaxb) {
        try {
            Unmarshaller unmarshaller = jaxb.createUnmarshaller();
            Session session = (Session) unmarshaller.unmarshal(sessionDirectory);
            return session;
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            System.out.println("it failed");
            e.printStackTrace();
        }
		return null;
	}

	/**
	 * Writes a given session to a given XML file
	 *
	 * @param session
	 *            the session to write to the given file
	 * @param sessionFile
	 *            the file to which the session is to be written
	 * @param jaxb
	 *            the JAXB context to use
	 */
	public static void writeSession(Session session, File sessionFile, JAXBContext jaxb) {
        Marshaller marshaller;
        try {
            marshaller = jaxb.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);;
            marshaller.marshal(session, sessionFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
	}

	/**
	 * Main Method Execution Steps: 1. Configure JAXB for the classes in the
	 * com.cooksys.serialization.assignment.model package 2. Read a session
	 * object from the <project-root>/input/memphis/ directory using the methods
	 * defined above 3. Write the session object to the
	 * <project-root>/output/session.xml file.
	 *
	 * JAXB Annotations and Configuration: You will have to add JAXB annotations
	 * to the classes in the com.cooksys.serialization.assignment.model package
	 *
	 * Check the XML files in the <project-root>/input/ directory to determine
	 * how to configure the {@link Contact} JAXB annotations
	 *
	 * The {@link Session} object should marshal to look like the following:
	 * <session location="..." start-date="...">
	 *     <instructor>
	 * 			<contact>...</contact>
	 * 		</instructor>
	 * 		<students> ...
	 * 			<student>
	 *				<contact>...</contact>
	 *			</student> ...
	 *		</students>
	 * </session>
	 *
	 * @throws JAXBException
	 */
	public static void main(String[] args) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Contact.class, Student.class, Session.class);
        ArrayList<Student> students = new ArrayList<>();
        students.addAll(readStudents(new File(STUDENT_DIRECTORY_STRING), jaxbContext));

        Session session = new Session();
        session.setInstructor(readInstructor(new File(INSTRUCTOR_STRING), jaxbContext));
        session.setStudents(readStudents(new File(STUDENT_DIRECTORY_STRING), jaxbContext));
        session.setLocation("Memphis");
        session.setStartDate("1-1-12");
        writeSession(session, new File(SESSION_STRING), jaxbContext);
        readSession(new File(SESSION_STRING), jaxbContext);
    }
}
