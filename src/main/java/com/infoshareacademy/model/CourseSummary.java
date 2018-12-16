package com.infoshareacademy.model;

import java.util.List;

public class CourseSummary {

    private String name;

    private List<String> attendees;

    private List<String> teachers;

    public CourseSummary(String name, List<String> attendees, List<String> teachers) {
        this.name = name;
        this.attendees = attendees;
        this.teachers = teachers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<String> attendees) {
        this.attendees = attendees;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CourseSummary{");
        sb.append("name='").append(name).append('\'');
        sb.append(", attendees=").append(attendees);
        sb.append(", teachers=").append(teachers);
        sb.append('}');
        return sb.toString();
    }
}
