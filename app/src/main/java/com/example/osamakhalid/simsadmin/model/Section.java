package com.example.osamakhalid.simsadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Osama Khalid on 4/4/2018.
 */

public class Section {

    @SerializedName("sectionID")
    @Expose
    private String sectionID;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("classesID")
    @Expose
    private String classesID;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getClassesID() {
        return classesID;
    }

    public void setClassesID(String classesID) {
        this.classesID = classesID;
    }

}
