package com.example.osamakhalid.simsadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Osama Khalid on 4/3/2018.
 */

public class LoginResponse {
    @SerializedName("systemadminID")
    @Expose
    private String systemadminID;
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
    @SerializedName("jod")
    @Expose
    private String jod;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("usertype")
    @Expose
    private String usertype;
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
    @SerializedName("systemadminactive")
    @Expose
    private String systemadminactive;
    @SerializedName("systemadminextra1")
    @Expose
    private String systemadminextra1;
    @SerializedName("systemadminextra2")
    @Expose
    private String systemadminextra2;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    public String getSystemadminID() {
        return systemadminID;
    }

    public void setSystemadminID(String systemadminID) {
        this.systemadminID = systemadminID;
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

    public String getJod() {
        return jod;
    }

    public void setJod(String jod) {
        this.jod = jod;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public String getSystemadminactive() {
        return systemadminactive;
    }

    public void setSystemadminactive(String systemadminactive) {
        this.systemadminactive = systemadminactive;
    }

    public String getSystemadminextra1() {
        return systemadminextra1;
    }

    public void setSystemadminextra1(String systemadminextra1) {
        this.systemadminextra1 = systemadminextra1;
    }

    public String getSystemadminextra2() {
        return systemadminextra2;
    }

    public void setSystemadminextra2(String systemadminextra2) {
        this.systemadminextra2 = systemadminextra2;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
