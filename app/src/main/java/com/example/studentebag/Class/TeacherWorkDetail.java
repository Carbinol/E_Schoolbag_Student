package com.example.studentebag.Class;

import java.util.Date;
import java.util.List;

public class TeacherWorkDetail {
    private long id;

    private Teacher teacher;

    private String policy;

    private Course course;

    private String title;

    private String encContent;

    private Date deadline;

    public TeacherWorkDetail(long id, Teacher teacher, String policy, Course course, String title, String encContent, Date deadline) {
        this.id = id;
        this.teacher = teacher;
        this.policy = policy;
        this.course = course;
        this.title = title;
        this.encContent = encContent;
        this.deadline = deadline;
    }

    public long getId() {
        return id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public String getPolicy() {
        return policy;
    }

    public Course getCourse() {
        return course;
    }

    public String getTitle() {
        return title;
    }

    public String getEncContent() {
        return encContent;
    }

    public Date getDeadline() {
        return deadline;
    }
}
