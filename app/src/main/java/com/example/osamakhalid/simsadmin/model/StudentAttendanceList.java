package com.example.osamakhalid.simsadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Osama Khalid on 4/11/2018.
 */

public class StudentAttendanceList {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("student_data")
    @Expose
    private List<StudentAttendance> studentData = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<StudentAttendance> getStudentData() {
        return studentData;
    }

    public void setStudentData(List<StudentAttendance> studentData) {
        this.studentData = studentData;
    }
}
