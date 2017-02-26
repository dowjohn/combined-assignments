package com.cooksys.ftd.assignments.file.model;

import javax.xml.bind.annotation.XmlRootElement;


public class Student {
    private Contact contact;
    
    public Student (Contact contact) {
    	this.contact = contact;
    }
    public Student() {
		// TODO Auto-generated constructor stub
	}
	public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
