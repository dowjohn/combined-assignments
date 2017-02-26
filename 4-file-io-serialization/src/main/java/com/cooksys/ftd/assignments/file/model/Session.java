package com.cooksys.ftd.assignments.file.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Session {
	
	@XmlAttribute( name = "location")
    private String location;
	
	@XmlAttribute( name = "start-date")
    private String startDate;
    
	@XmlElement
    private Instructor instructor;
    
    @XmlElementWrapper
    @XmlElement(name = "student")
    private List<Student> students;

    public Session (){}

    public Session (String location, String startDate, Instructor instructor, List<Student> students) {
        this.location = location;
        this.startDate = startDate;
        this.instructor = instructor;
        this.students = students;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
