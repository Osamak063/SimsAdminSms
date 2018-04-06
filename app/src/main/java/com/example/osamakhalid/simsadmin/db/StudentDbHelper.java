package com.example.osamakhalid.simsadmin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Osama Khalid on 4/6/2018.
 */

public class StudentDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "STUDENTSINFO.DB";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_QUERY =
            "CREATE TABLE " + StudentContract.NewStudentInfo.TABLE_NAME + "(" + StudentContract.NewStudentInfo.STUDENT_ID
                    + " INTEGER PRIMARY KEY," + StudentContract.NewStudentInfo.STUDENT_NAME
                    + " TEXT," + StudentContract.NewStudentInfo.STUDENT_MOB + " TEXT," + StudentContract.NewStudentInfo.STUDENT_CLASSID
                    + " TEXT," + StudentContract.NewStudentInfo.STUDENT_SECTIONID + " TEXT);";

    public StudentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.out.println("logg database created");
    }

    public void addInformation(String studentId, String name, String mob, String classId, String sectionId, SQLiteDatabase sqLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentContract.NewStudentInfo.STUDENT_ID, studentId);
        contentValues.put(StudentContract.NewStudentInfo.STUDENT_NAME, name);
        contentValues.put(StudentContract.NewStudentInfo.STUDENT_MOB, mob);
        contentValues.put(StudentContract.NewStudentInfo.STUDENT_CLASSID, classId);
        contentValues.put(StudentContract.NewStudentInfo.STUDENT_SECTIONID, sectionId);
        //  sqLiteDatabase.insert(StudentContract.NewStudentInfo.TABLE_NAME, null, contentValues);
        sqLiteDatabase.insertWithOnConflict(StudentContract.NewStudentInfo.TABLE_NAME, null
                , contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor getInformation(SQLiteDatabase sqLiteDatabase) {
        Cursor cursor;
        String[] projections = {StudentContract.NewStudentInfo.STUDENT_ID, StudentContract.NewStudentInfo.STUDENT_NAME
                , StudentContract.NewStudentInfo.STUDENT_MOB, StudentContract.NewStudentInfo.STUDENT_CLASSID
                , StudentContract.NewStudentInfo.STUDENT_SECTIONID};
        cursor=sqLiteDatabase.query(StudentContract.NewStudentInfo.TABLE_NAME,projections,null,null,null,null,null);
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_QUERY);
        System.out.println("logg table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
