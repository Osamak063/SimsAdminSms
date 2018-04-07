package com.example.osamakhalid.simsadmin.model;

/**
 * Created by Osama Khalid on 4/7/2018.
 */

public class SectionDataProvider {
    String sectionId;
    String sectionName;
    String category;
    String sectionClassId;

    public SectionDataProvider(String sectionId, String sectionName, String category, String sectionClassId) {

        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.category = category;
        this.sectionClassId = sectionClassId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSectionClassId(String sectionClassId) {
        this.sectionClassId = sectionClassId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getCategory() {
        return category;
    }

    public String getSectionClassId() {
        return sectionClassId;
    }


}
