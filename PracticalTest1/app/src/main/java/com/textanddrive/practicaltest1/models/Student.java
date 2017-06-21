package com.textanddrive.practicaltest1.models;

/**
 * Created by Niraj on 25-05-2017.
 */

public class Student {
    String id;
    String enrolment;
    String name;
    String birthday;
    boolean attendance;

    public Student() {
    }

    public Student(String id, String enrolment, String name, String birthday, boolean attendance) {
        this.id = id;
        this.enrolment = enrolment;
        this.name = name;
        this.birthday = birthday;
        this.attendance = attendance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnrolment() {
        return enrolment;
    }

    public void setEnrolment(String enrolment) {
        this.enrolment = enrolment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public boolean getAttendance() {
        return attendance;
    }

    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", enrolment='" + enrolment + '\'' +
                ", name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", attendance='" + attendance + '\'' +
                '}';
    }
}
