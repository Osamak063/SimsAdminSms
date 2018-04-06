package com.example.osamakhalid.simsadmin.model;

/**
 * Created by Osama Khalid on 4/6/2018.
 */

public class StudentDataProvider {
    String studentId;
    String studentName;
    String mob;
    String classId;
    String sectionId;

    public StudentDataProvider(String studentId, String studentName, String mob, String classId, String sectionId) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.mob = mob;
        this.classId = classId;
        this.sectionId = sectionId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getMob() {
        return mob;
    }

    public String getClassId() {
        return classId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
}
