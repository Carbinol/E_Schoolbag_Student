package com.example.studentebag.Class;

import java.util.Date;

public class StudentWorkDetail {
    private long id;

    private Student student;

    private TeacherWorkDetail teacherWork;

    private String content;

    private float score;

    private String remark;

    private Date submitTime;

    public StudentWorkDetail(long id, Student student, TeacherWorkDetail teacherWork, String content, float score, String remark, Date submitTime) {
        this.id = id;
        this.student = student;
        this.teacherWork = teacherWork;
        this.content = content;
        this.score = score;
        this.remark = remark;
        this.submitTime = submitTime;
    }

    public long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public TeacherWorkDetail getTeacherWork() {
        return teacherWork;
    }

    public String getContent() {
        return content;
    }

    public float getScore() {
        return score;
    }

    public String getRemark() {
        return remark;
    }

    public Date getSubmitTime() {
        return submitTime;
    }
}
