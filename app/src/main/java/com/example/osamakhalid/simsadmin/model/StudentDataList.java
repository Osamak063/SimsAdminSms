package com.example.osamakhalid.simsadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Osama Khalid on 4/3/2018.
 */

public class StudentDataList {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("student_data")
    @Expose
    private List<StudentData> studentData = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<StudentData> getStudentData() {
        return studentData;
    }

    public void setStudentData(List<StudentData> studentData) {
        this.studentData = studentData;
    }
}
