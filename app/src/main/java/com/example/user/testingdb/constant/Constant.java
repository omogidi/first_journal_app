package com.example.user.testingdb.constant;

/**
 * Created by User on 11/13/2018.
 */

public class Constant
{
    //Database Name and version
    public static final String DATABASE_NAME = "Journal.db";
    public static final int DB_VERSION = 2;

    //Database tables
    public static final String JOURNAL_TABLE_NAME = "Journal";
    public static final String USER_TABLE_NAME = "User";


    //Journal Table columns
    public static final String KEY_ID = "id";
    public static final String KEY_JOURNAL = "journal";
    public static final String KEY_DATEADDED = "dateAdded";

    //User Table columns
    public static final String USER_ID = "id";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";

}
