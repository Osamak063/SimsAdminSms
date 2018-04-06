package com.example.osamakhalid.simsadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Osama Khalid on 4/3/2018.
 */

public class ClassesList {
    @SerializedName("classes_data")
    @Expose
    private List<Class> classesData = null;

    public List<Class> getClassesData() {
        return classesData;
    }

    public void setClassesData(List<Class> classesData) {
        this.classesData = classesData;
    }
}

