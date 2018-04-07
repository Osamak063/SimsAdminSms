package com.example.osamakhalid.simsadmin.model;

/**
 * Created by Osama Khalid on 4/7/2018.
 */

public class ClassDataProvider {
    String classId;
    String className;
    String classNameNumeric;
    String classTeacherId;

    public ClassDataProvider(String classId, String className, String classNameNumeric, String classTeacherId) {
        this.classId = classId;
        this.className = className;
        this.classNameNumeric = classNameNumeric;
        this.classTeacherId = classTeacherId;
    }

    public String getClassId() {
        return classId;
    }

    public String getClassName() {
        return className;
    }

    public String getClassNameNumeric() {
        return classNameNumeric;
    }

    public String getClassTeacherId() {
        return classTeacherId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setClassNameNumeric(String classNameNumeric) {
        this.classNameNumeric = classNameNumeric;
    }

    public void setClassTeacherId(String classTeacherId) {
        this.classTeacherId = classTeacherId;
    }
}
