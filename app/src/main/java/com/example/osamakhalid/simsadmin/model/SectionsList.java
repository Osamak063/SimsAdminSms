package com.example.osamakhalid.simsadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Osama Khalid on 4/4/2018.
 */

public class SectionsList {
    @SerializedName("section_data")
    @Expose
    private List<Section> sectionData = null;

    public List<Section> getSectionData() {
        return sectionData;
    }

    public void setSectionData(List<Section> sectionData) {
        this.sectionData = sectionData;
    }
}
