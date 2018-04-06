package com.example.osamakhalid.simsadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Osama Khalid on 4/3/2018.
 */

public class StudentData {
    @SerializedName("studentID")
    @Expose
    private String studentID;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("classesID")
    @Expose
    private String classesID;
    @SerializedName("sectionID")
    @Expose
    private String sectionID;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("roll")
    @Expose
    private String roll;
    @SerializedName("library")
    @Expose
    private String library;
    @SerializedName("hostel")
    @Expose
    private String hostel;
    @SerializedName("transport")
    @Expose
    private String transport;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("totalamount")
    @Expose
    private Object totalamount;
    @SerializedName("paidamount")
    @Expose
    private Object paidamount;
    @SerializedName("photo")
    @Expose
    private Object photo;
    @SerializedName("parentID")
    @Expose
    private Object parentID;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("usertype")
    @Expose
    private String usertype;
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
    @SerializedName("studentactive")
    @Expose
    private String studentactive;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClassesID() {
        return classesID;
    }

    public void setClassesID(String classesID) {
        this.classesID = classesID;
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Object getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(Object totalamount) {
        this.totalamount = totalamount;
    }

    public Object getPaidamount() {
        return paidamount;
    }

    public void setPaidamount(Object paidamount) {
        this.paidamount = paidamount;
    }

    public Object getPhoto() {
        return photo;
    }

    public void setPhoto(Object photo) {
        this.photo = photo;
    }

    public Object getParentID() {
        return parentID;
    }

    public void setParentID(Object parentID) {
        this.parentID = parentID;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
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

    public String getStudentactive() {
        return studentactive;
    }

    public void setStudentactive(String studentactive) {
        this.studentactive = studentactive;
    }
}
