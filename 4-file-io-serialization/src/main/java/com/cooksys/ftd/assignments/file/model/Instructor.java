package com.cooksys.ftd.assignments.file.model;

import javax.xml.bind.annotation.XmlRootElement;

public class Instructor {
    private Contact contact;
    
    public Instructor (Contact contact) {
    	this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
