package com.example.osamakhalid.simsadmin.db;

/**
 * Created by Osama Khalid on 4/6/2018.
 */

public class StudentContract {
    public static abstract class NewStudentInfo {
        public static final String STUDENT_ID = "studentID";
        public static final String NAME = "name";
        public static final String DOB = "dob";
        public static final String SEX = "sex";
        public static final String EMAIL = "email";
        public static final String PHONE = "phone";
        public static final String ADDRESS = "address";
        public static final String STUDENT_CLASSES_ID = "classesID";
        public static final String STUDENT_SECTION_ID = "sectionID";
        public static final String STUDENT_SECTION_NAME = "section";
        public static final String STUDENT_PARENT_ID = "parentID";
        public static final String STUDENT_ACTIVE = "studentactive";
        public static final String TABLE_NAME = "student_info";
    }

    public static abstract class NewClassInfo {
        public static final String CLASSES_ID = "classesID";
        public static final String CLASS_NAME = "classes";
        public static final String CLASSES_NAME_NUMERIC = "classes_numeric";
        public static final String CLASS_TEACHER_ID = "teacherID";
        public static final String TABLE_NAME = "class_info";
    }

    public static abstract class NewSectionInfo {
        public static final String SECTION_ID = "sectionID";
        public static final String SECTION_NAME = "section";
        public static final String CATEGORY = "category";
        public static final String SECTION_CLASSES_ID = "classesID";
        public static final String TABLE_NAME = "section_info";
    }

}
