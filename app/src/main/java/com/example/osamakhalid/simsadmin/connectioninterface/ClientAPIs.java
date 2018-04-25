package com.example.osamakhalid.simsadmin.connectioninterface;

import com.example.osamakhalid.simsadmin.apis.ConnectionURLS;
import com.example.osamakhalid.simsadmin.model.ClassesList;
import com.example.osamakhalid.simsadmin.model.LoginResponse;
import com.example.osamakhalid.simsadmin.model.MarkAttendance;
import com.example.osamakhalid.simsadmin.model.MarkAttendanceResponse;
import com.example.osamakhalid.simsadmin.model.SectionsList;
import com.example.osamakhalid.simsadmin.model.StudentAttendanceList;
import com.example.osamakhalid.simsadmin.model.StudentDataList;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ClientAPIs {

    @FormUrlEncoded
    @POST(ConnectionURLS.LOGIN_URL)
    Call<LoginResponse> loginUser(@Field("username") String userName, @Field("password") String password
            , @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.CLASSES_LIST_URL)
    Call<ClassesList> getClasses(@Header("Authorization") String authHeader);

    @GET(ConnectionURLS.STUDENTS_BY_CLASS_URL)
    Call<StudentDataList> getStudents(@Query("classesID") String classId, @Query("sectionID") String sectionId
            , @Query("limit") int limit, @Query("offset") int offset, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.SECTIONS_LIST_URL)
    Call<SectionsList> getSections(@Query("classID") String classId, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.STUDENT_ATTENDANCE_URL)
    Call<StudentAttendanceList> getAttendance(@Query("classesID") String classId, @Query("date") String date
            , @Query("username") String userName, @Query("sectionID") String sectionId
            , @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST(ConnectionURLS.STUDENT_MARK_ATTENDANCE)
    Call<MarkAttendanceResponse> markAttendance(@Field("attendance") String attendance
            , @Header("Authorization") String authHeader);

}

