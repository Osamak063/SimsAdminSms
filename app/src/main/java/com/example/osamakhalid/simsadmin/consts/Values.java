package com.example.osamakhalid.simsadmin.consts;

/**
 * Created by Osama Khalid on 2/15/2018.
 */

public class Values {
    public static final String USER_CURL = "ss_admin";
    public static final String PASSWORD_CURL = "12345";
    public static final String DIALOGUE_MSG = "Loading...";
    public static final String SERVER_ERROR = "Server Error!";
    public static final String WAIT_MSG = "Please wait...";
    public static final String LANGUAGE = "english";
    public static final String Error = "Error while Loding..";
    public static final String DATA_ERROR = "No Data Available";
    public static final String image_path = "http://demo.simsportal.com/uploads/media/";
    public static final String TYPE_STUDENT = "Student";
    public static final String TYPE_PARENT = "Parent";
    public static final String INVALID_USER_PASS = "Incorrect username or passoword";
    public static final String SENDING_MSG = "Sending messages...";
    public static final String DATE_FORMAT = "dd-mm-yyyy";
    public static final String SICK_LEAVE = "Sick Leave";
    public static final String ATTENDANCE_PRESENT_CODE = "P";
    public static final String ATTENDANCE_ABSENT_CODE = "A";
    public static final String ATTENDANCE_PRESENT_VALUE = "Present";
    public static final String ATTENDANCE_ABSENT_VALUE = "Absent";
    public static final String ATTENDANCE_SICK_LEAVE = "Sick Leave";
    public static final String ATTENDANCE_CASUAL_LEAVE = "Casual Leave";
    public static final String ATTENDANCE_EMERGENCY_LEAVE = "Emergency Leave";
    public static final String ATTENDANCE_SHORT_LEAVE = "Short Leave";
    public static final String ERROR_MARKING_ATTENDANCE = "Error in marking attendance";
    public static final String SUCCESS_MARKING_ATTENDANCE = "Sucessfully marked attendace";

    public static String getAttendanceMessage(String studentName, String attendance) {
        if (attendance.equals(SICK_LEAVE)) {
            return studentName + " is on " + attendance + " today.";
        } else {
            return studentName + " is " + attendance + " today.";
        }

    }
}
