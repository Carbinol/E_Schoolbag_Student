package com.example.studentebag.Class;

public class TeacherWork {
    private long teacherWorkId;

    private String teacherId;

    private String policy;

    private String courseId;

    private String title;

    private String content;

    private String deadline;

    public TeacherWork(String teacherId, String policy, String courseId, String title, String content, String deadline) {
        this.teacherId = teacherId;
        this.policy = policy;
        this.courseId = courseId;
        this.title = title;
        this.content = content;
        this.deadline = deadline;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getPolicy() {
        return policy;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDeadline() {
        return deadline;
    }

    public long getTeacherWorkId() {
        return teacherWorkId;
    }
}
