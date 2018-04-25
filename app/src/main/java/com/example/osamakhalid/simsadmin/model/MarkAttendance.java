package com.example.osamakhalid.simsadmin.model;

/**
 * Created by Osama Khalid on 4/13/2018.
 */

public class MarkAttendance {
    String attendanceID;
    String day;
    String att_status;

    public MarkAttendance(String attendanceID, String day, String att_status) {
        this.attendanceID = attendanceID;
        this.day = day;
        this.att_status = att_status;
    }


}
