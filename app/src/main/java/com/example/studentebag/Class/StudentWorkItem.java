package com.example.studentebag.Class;

public class StudentWorkItem {
    private long teacherWorkId;

    private long studentWorkId;

    private String title;

    private String commitDate;

    private String content;

    private Float score;

    private String remark;

    public StudentWorkItem(long teacherWorkId, long studentWorkId, String title, String commitDate, String content, Float score, String remark) {
        this.teacherWorkId = teacherWorkId;
        this.studentWorkId = studentWorkId;
        this.title = title;
        this.commitDate = commitDate;
        this.content = content;
        this.score = score;
        this.remark = remark;
    }

    public StudentWorkItem(long teacherWorkId, long studentWorkId, String title, String commitDate, String content) {
        this.teacherWorkId = teacherWorkId;
        this.studentWorkId = studentWorkId;
        this.title = title;
        this.commitDate = commitDate;
        this.content = content;
    }

    public long getTeacherWorkId() {
        return teacherWorkId;
    }

    public long getStudentWorkId() {
        return studentWorkId;
    }

    public String getTitle() {
        return title;
    }

    public String getCommitDate() {
        return commitDate;
    }

    public String getContent() {
        return content;
    }

    public Float getScore() {
        return score;
    }

    public String getRemark() {
        return remark;
    }
}
