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
    private static final String CREATE_QUERY_STUDENTS =
            "CREATE TABLE " + StudentContract.NewStudentInfo.TABLE_NAME + "(" + StudentContract.NewStudentInfo.STUDENT_ID
                    + " INTEGER PRIMARY KEY," + StudentContract.NewStudentInfo.NAME
                    + " TEXT," + StudentContract.NewStudentInfo.DOB + " TEXT," + StudentContract.NewStudentInfo.SEX
                    + " TEXT," + StudentContract.NewStudentInfo.EMAIL + " TEXT," + StudentContract.NewStudentInfo.PHONE
                    + " TEXT," + StudentContract.NewStudentInfo.ADDRESS + " TEXT," + StudentContract.NewStudentInfo.STUDENT_CLASSES_ID
                    + " TEXT," + StudentContract.NewStudentInfo.STUDENT_SECTION_ID + " TEXT," + StudentContract.NewStudentInfo.STUDENT_SECTION_NAME
                    + " TEXT," + StudentContract.NewStudentInfo.STUDENT_PARENT_ID + " TEXT," + StudentContract.NewStudentInfo.STUDENT_ACTIVE
                    + " TEXT);";

    private static final String CREATE_QUERY_CLASSES =
            "CREATE TABLE " + StudentContract.NewClassInfo.TABLE_NAME + "(" + StudentContract.NewClassInfo.CLASSES_ID
                    + " INTEGER PRIMARY KEY," + StudentContract.NewClassInfo.CLASS_NAME
                    + " TEXT," + StudentContract.NewClassInfo.CLASSES_NAME_NUMERIC + " TEXT,"
                    + StudentContract.NewClassInfo.CLASS_TEACHER_ID
                    + " TEXT);";

    private static final String CREATE_QUERY_SECTIONS =
            "CREATE TABLE " + StudentContract.NewSectionInfo.TABLE_NAME + "(" + StudentContract.NewSectionInfo.SECTION_ID
                    + " INTEGER PRIMARY KEY," + StudentContract.NewSectionInfo.SECTION_NAME
                    + " TEXT," + StudentContract.NewSectionInfo.CATEGORY + " TEXT," + StudentContract.NewSectionInfo.SECTION_CLASSES_ID
                    + " TEXT);";


    public StudentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.out.println("logg database created");
    }

    public void addInformationToStudentTable(String studentId, String name, String dob, String sex, String email
            , String phone, String address, String classId, String sectionId, String sectionName
            , String parentId, String studentActive, SQLiteDatabase sqLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentContract.NewStudentInfo.STUDENT_ID, studentId);
        contentValues.put(StudentContract.NewStudentInfo.NAME, name);
        contentValues.put(StudentContract.NewStudentInfo.DOB, dob);
        contentValues.put(StudentContract.NewStudentInfo.SEX, sex);
        contentValues.put(StudentContract.NewStudentInfo.EMAIL, email);
        contentValues.put(StudentContract.NewStudentInfo.PHONE, phone);
        contentValues.put(StudentContract.NewStudentInfo.ADDRESS, address);
        contentValues.put(StudentContract.NewStudentInfo.STUDENT_CLASSES_ID, classId);
        contentValues.put(StudentContract.NewStudentInfo.STUDENT_SECTION_ID, sectionId);
        contentValues.put(StudentContract.NewStudentInfo.STUDENT_SECTION_NAME, sectionName);
        contentValues.put(StudentContract.NewStudentInfo.STUDENT_PARENT_ID, parentId);
        contentValues.put(StudentContract.NewStudentInfo.STUDENT_ACTIVE, studentActive);
        sqLiteDatabase.insertWithOnConflict(StudentContract.NewStudentInfo.TABLE_NAME, null
                , contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void addInformationToClassTable(String classId, String className, String classNameNumeric
            , String teacherId, SQLiteDatabase sqLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentContract.NewClassInfo.CLASSES_ID, classId);
        contentValues.put(StudentContract.NewClassInfo.CLASS_NAME, className);
        contentValues.put(StudentContract.NewClassInfo.CLASSES_NAME_NUMERIC, classNameNumeric);
        contentValues.put(StudentContract.NewClassInfo.CLASS_TEACHER_ID, teacherId);
        sqLiteDatabase.insertWithOnConflict(StudentContract.NewClassInfo.TABLE_NAME, null
                , contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void addInformationToSectionTable(String sectionId, String sectionName, String category
            , String sectionClassId, SQLiteDatabase sqLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentContract.NewSectionInfo.SECTION_ID, sectionId);
        contentValues.put(StudentContract.NewSectionInfo.SECTION_NAME, sectionName);
        contentValues.put(StudentContract.NewSectionInfo.CATEGORY, category);
        contentValues.put(StudentContract.NewSectionInfo.SECTION_CLASSES_ID, sectionClassId);
        sqLiteDatabase.insertWithOnConflict(StudentContract.NewSectionInfo.TABLE_NAME, null
                , contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }


    public Cursor getInformationFromStudentTable(SQLiteDatabase sqLiteDatabase, String classId, String sectionId) {
        Cursor cursor;
        String[] projections = {StudentContract.NewStudentInfo.STUDENT_ID, StudentContract.NewStudentInfo.NAME
                , StudentContract.NewStudentInfo.DOB, StudentContract.NewStudentInfo.SEX
                , StudentContract.NewStudentInfo.EMAIL, StudentContract.NewStudentInfo.PHONE
                , StudentContract.NewStudentInfo.ADDRESS, StudentContract.NewStudentInfo.STUDENT_CLASSES_ID
                , StudentContract.NewStudentInfo.STUDENT_SECTION_ID, StudentContract.NewStudentInfo.STUDENT_SECTION_NAME
                , StudentContract.NewStudentInfo.STUDENT_PARENT_ID, StudentContract.NewStudentInfo.STUDENT_ACTIVE};
        cursor = sqLiteDatabase.query(StudentContract.NewStudentInfo.TABLE_NAME, projections
                , StudentContract.NewStudentInfo.STUDENT_CLASSES_ID + "=" + classId + " AND "
                        + StudentContract.NewStudentInfo.STUDENT_SECTION_ID + "=" + sectionId, null, null, null, null);
        return cursor;
    }

    public Cursor getInformationFromClassTable(SQLiteDatabase sqLiteDatabase) {
        Cursor cursor;
        String[] projections = {StudentContract.NewClassInfo.CLASSES_ID, StudentContract.NewClassInfo.CLASS_NAME
                , StudentContract.NewClassInfo.CLASSES_NAME_NUMERIC, StudentContract.NewClassInfo.CLASS_TEACHER_ID};
        cursor = sqLiteDatabase.query(StudentContract.NewClassInfo.TABLE_NAME, projections, null, null, null, null, null);
        return cursor;
    }

    public Cursor getInformationFromSectionTableWithClassId(SQLiteDatabase sqLiteDatabase, String classId) {
        Cursor cursor;
        String[] projections = {StudentContract.NewSectionInfo.SECTION_ID, StudentContract.NewSectionInfo.SECTION_NAME
                , StudentContract.NewSectionInfo.CATEGORY, StudentContract.NewSectionInfo.SECTION_CLASSES_ID};
        cursor = sqLiteDatabase.query(StudentContract.NewSectionInfo.TABLE_NAME, projections
                , StudentContract.NewSectionInfo.SECTION_CLASSES_ID + "=" + classId
                , null, null, null, null);
        return cursor;
    }

    public Cursor getInformationFromSectionTable(SQLiteDatabase sqLiteDatabase) {
        Cursor cursor;
        String[] projections = {StudentContract.NewSectionInfo.SECTION_ID, StudentContract.NewSectionInfo.SECTION_NAME
                , StudentContract.NewSectionInfo.CATEGORY, StudentContract.NewSectionInfo.SECTION_CLASSES_ID};
        cursor = sqLiteDatabase.query(StudentContract.NewSectionInfo.TABLE_NAME, projections
                , null
                , null, null, null, null);
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_QUERY_STUDENTS);
        sqLiteDatabase.execSQL(CREATE_QUERY_CLASSES);
        sqLiteDatabase.execSQL(CREATE_QUERY_SECTIONS);
        System.out.println("logg table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
