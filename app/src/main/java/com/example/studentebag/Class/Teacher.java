package com.example.studentebag.Class;

import java.util.List;

public class Teacher {
    private Long id;

    private String teacherId;

    private String name;

    private String sex;

    private String tel;

    private List<Course> courses;

    public Long getId() {
        return id;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getTel() {
        return tel;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
