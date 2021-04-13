package com.company1.gpasaver.ui.viewStudent;

public class Student {
    private String name;
    private String className;
    private String email;
    private String picture;
    private String phoneNumber;
    private String id;


    public Student(String id, String  name, String className, String email, String picture, String phoneNumber){
        this.name  = name;
        this.className = className;
        this.email = email;
        this.picture = picture;
        this.phoneNumber = phoneNumber;
        this.id = id;

    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public String getEmail() {
        return email;
    }

    public String getPicture() {
        return picture;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getId() {
        return id;
    }

}
