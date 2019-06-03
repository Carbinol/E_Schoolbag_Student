package com.example.studentebag.Class;

public class TeacherWorkItem {

    private Long teacherWorkId;

    private String title;

    private String courseName;

    private String decContent;

    public TeacherWorkItem(Long teacherWorkId, String title, String courseName, String decContent) {
        this.title = title;
        this.courseName = courseName;
        this.decContent = decContent;
        this.teacherWorkId = teacherWorkId;
    }

    public String getTitle() {
        return title;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDecContent() {
        return decContent;
    }

    public Long getTeacherWorkId() {
        return teacherWorkId;
    }
}
