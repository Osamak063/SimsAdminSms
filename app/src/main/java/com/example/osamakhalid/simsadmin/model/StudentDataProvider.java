package com.example.osamakhalid.simsadmin.model;

/**
 * Created by Osama Khalid on 4/6/2018.
 */

public class StudentDataProvider {
    String studentId;
    String studentName;
    String dob;
    String sex;
    String email;
    String mob;
    String address;
    String classId;
    String sectionId;
    String sectionName;
    String parentId;
    String studentActive;

    public StudentDataProvider(String studentId, String studentName, String dob, String sex, String email, String mob, String address, String classId, String sectionId, String sectionName, String parentId, String studentActive) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.dob = dob;
        this.sex = sex;
        this.email = email;
        this.mob = mob;
        this.address = address;
        this.classId = classId;
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.parentId = parentId;
        this.studentActive = studentActive;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setStudentActive(String studentActive) {
        this.studentActive = studentActive;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getDob() {
        return dob;
    }

    public String getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public String getMob() {
        return mob;
    }

    public String getAddress() {
        return address;
    }

    public String getClassId() {
        return classId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getParentId() {
        return parentId;
    }

    public String getStudentActive() {
        return studentActive;
    }
}
