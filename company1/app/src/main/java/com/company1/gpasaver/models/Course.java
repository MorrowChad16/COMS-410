package com.company1.gpasaver.models;

public class Course {
    private String course_name;
    private String course_des;
    private int courseId;

    public Course(String course_name, String course_des, int courseId) {
        this.course_name = course_name;
        this.course_des = course_des;
        this.courseId = courseId;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_des() {
        return course_des;
    }

    public void setCourse_des(String course_des) {
        this.course_des = course_des;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
