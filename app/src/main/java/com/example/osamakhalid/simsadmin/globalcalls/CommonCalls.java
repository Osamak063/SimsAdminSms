package com.example.osamakhalid.simsadmin.globalcalls;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.example.osamakhalid.simsadmin.model.LoginResponse;
import com.example.osamakhalid.simsadmin.model.StudentData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by HAMI on 19/02/2018.
 */

public class CommonCalls extends AppCompatActivity {

    public static void saveUserData(LoginResponse loginResponse, Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("UserData", 0);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(loginResponse);
        prefsEditor.putString("UserObject", json);
        prefsEditor.commit();
    }

    public static LoginResponse getUserData(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("UserData", 0);
        Gson gson = new Gson();
        String json = mPrefs.getString("UserObject", "");
        LoginResponse userData = gson.fromJson(json, LoginResponse.class);
        return userData;
    }

    public static void saveStudentsList(Context context, String className, List<StudentData> list) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("StudentsList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(className, json);
        editor.commit();
    }

    public static List<StudentData> getStudentsList(Context context, String className) {
        SharedPreferences mPrefs = context.getSharedPreferences("StudentsList", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(className, "");
        Type type = new TypeToken<List<StudentData>>() {
        }.getType();
        List<StudentData> data = gson.fromJson(json, type);
        return data;
    }


    public static String getCurrentDate() {
        Date now = new Date();
        Date alsoNow = Calendar.getInstance().getTime();
        String nowAsString = new SimpleDateFormat("yyyy-MM-dd").format(now);

        return nowAsString;

    }


    public static ProgressDialog createDialouge(Context context, String title, String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(msg);
        progressDialog.show();
        return progressDialog;
    }

    public static void deleteUserData(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("UserData", 0);
        mPrefs.edit().remove("UserObject").apply();
    }

    public static String getMonth(String month) {

        switch (month) {

            case "January":
                return "01";
            case "Feburary":
                return "02";
            case "March":
                return "03";
            case "April":
                return "04";
            case "May":
                return "05";
            case "June":
                return "06";
            case "July":
                return "07";
            case "August":
                return "08";
            case "September":
                return "09";
            case "October":
                return "10";
            case "November":
                return "11";
            case "December":
                return "12";
            default:
                return "null";
        }
    }

}
