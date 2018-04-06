package com.example.osamakhalid.simsadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Osama Khalid on 4/3/2018.
 */

public class Class {
    @SerializedName("classesID")
    @Expose
    private String classesID;
    @SerializedName("classes")
    @Expose
    private String classes;
    @SerializedName("classes_numeric")
    @Expose
    private String classesNumeric;
    @SerializedName("teacherID")
    @Expose
    private String teacherID;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("modify_date")
    @Expose
    private String modifyDate;
    @SerializedName("create_userID")
    @Expose
    private String createUserID;
    @SerializedName("create_username")
    @Expose
    private String createUsername;
    @SerializedName("create_usertype")
    @Expose
    private String createUsertype;

    public String getClassesID() {
        return classesID;
    }

    public void setClassesID(String classesID) {
        this.classesID = classesID;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getClassesNumeric() {
        return classesNumeric;
    }

    public void setClassesNumeric(String classesNumeric) {
        this.classesNumeric = classesNumeric;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getCreateUserID() {
        return createUserID;
    }

    public void setCreateUserID(String createUserID) {
        this.createUserID = createUserID;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public String getCreateUsertype() {
        return createUsertype;
    }

    public void setCreateUsertype(String createUsertype) {
        this.createUsertype = createUsertype;
    }

}
