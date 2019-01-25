package com.example.user.testingdb.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.user.testingdb.constant.Constant;
import com.example.user.testingdb.model.Journal;
import com.example.user.testingdb.model.User;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 11/13/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper
{
    private Context ctx;

    public DatabaseHandler(Context context)
    {
        super(context, Constant.DATABASE_NAME, null, Constant.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_JOURNAL_TABLE = "create table " + Constant.JOURNAL_TABLE_NAME + "("
                + Constant.KEY_ID + " INTEGER PRIMARY KEY, "
                + Constant.USER_EMAIL + " TEXT, "
                + Constant.KEY_JOURNAL + " TEXT, "
                + Constant.KEY_DATEADDED + " LONG);";
                db.execSQL(CREATE_JOURNAL_TABLE);

        String CREATE_USER_TABLE = "create table " + Constant.USER_TABLE_NAME + "("
                + Constant.USER_ID + " INTEGER PRIMARY KEY, "
                + Constant.USER_FIRST_NAME + " TEXT, "
                + Constant.USER_LAST_NAME + " TEXT, "
                + Constant.USER_EMAIL + " TEXT, "
                + Constant.USER_PASSWORD + " TEXT);";
                db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists " + Constant.JOURNAL_TABLE_NAME);
        db.execSQL("drop table if exists " + Constant.USER_TABLE_NAME);
        onCreate(db);
    }

    /**
     * CRUD OPERATIONS
     */

    //Add user to the database
    public void addUser (User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.USER_FIRST_NAME, user.getFirstName());
        values.put(Constant.USER_LAST_NAME, user.getLastName());
        values.put(Constant.USER_EMAIL, user.getEmail());
        values.put(Constant.USER_PASSWORD, user.getPassword());

        long res =  db.insert(Constant.USER_TABLE_NAME, null, values);
        int s = 0;
    }

    //Check if user exist from Database
    public boolean checkUser(String email, String password) {
        //array of columns to fetch
        String[] columns = {Constant.USER_EMAIL,Constant.USER_PASSWORD};

        SQLiteDatabase db = this.getReadableDatabase();

        //selection Criteria
        String selection = Constant.USER_EMAIL + " = ? and " + Constant.USER_PASSWORD + " = ?";

        //selection argument
        String[] selectionArgs = {email, password};

        //Query user table with conditions
        Cursor cursor = db.query(Constant.USER_TABLE_NAME, null, selection, selectionArgs, null,null,null);

        int cursorCount = cursor.getCount();
        if (cursorCount > 0){
            return true;
        }else {
            return false;
        }
    }

    //Check if email already exists in database
    public boolean checkEmail (String email) {
        //array of column to fetch
        String[] column = {Constant.USER_EMAIL};

        SQLiteDatabase db = this.getReadableDatabase();

        //Selection Criteria
        String selection = Constant.USER_EMAIL + " = ?";

        //Selection args
        String[] selectionArgs = {email};

        //Query Database
        Cursor cursor = db.query(Constant.USER_TABLE_NAME, null, selection, selectionArgs, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        } else
            return false;
    }

    //Update user
    public void updateUser(User  user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.USER_FIRST_NAME, user.getFirstName());
        values.put(Constant.USER_LAST_NAME, user.getLastName());
        values.put(Constant.USER_EMAIL, user.getEmail());
        values.put(Constant.USER_PASSWORD, user.getPassword());

        //Updating row
        db.update(Constant.USER_TABLE_NAME, values, Constant.USER_ID + " = ?", new String[]{String.valueOf(user.getId())});
    }

    //Delete User
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        //delete the row you want to delete
        db.delete(Constant.USER_TABLE_NAME, Constant.USER_ID + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    //Add to journal to the database
    public void addJournal(Journal journal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.KEY_JOURNAL, journal.getTheJournal());
        values.put(Constant.USER_EMAIL,journal.getUserEmail());
        values.put(Constant.KEY_DATEADDED, java.lang.System.currentTimeMillis());

        db.insert(Constant.JOURNAL_TABLE_NAME, null, values);
    }

    //Get The Note
    public Journal getJournal(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Constant.JOURNAL_TABLE_NAME, new String[]{Constant.KEY_ID, Constant.KEY_JOURNAL, Constant.KEY_DATEADDED},
                Constant.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null,
                null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Journal journal = new Journal();
        journal.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constant.KEY_ID))));
        journal.setTheJournal(cursor.getString(cursor.getColumnIndex(Constant.KEY_JOURNAL)));
        DateFormat dateFormat = DateFormat.getDateInstance();
        String formatDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constant.KEY_DATEADDED))).getTime());
        journal.setDateJournalAdded(formatDate);

        return journal;
    }

    //Get all the Note
    public List<Journal> getAllJournal() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Journal> listallJournal = new ArrayList<>();
        Cursor cursor = db.query(Constant.JOURNAL_TABLE_NAME, new String[]{Constant.KEY_ID, Constant.USER_EMAIL, Constant.KEY_JOURNAL, Constant.KEY_DATEADDED},
                null, null, null, null, Constant.KEY_DATEADDED + " DESC");
        if (cursor.moveToFirst()) {
            do {
                Journal journal = new Journal();
                journal.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constant.KEY_ID))));
                journal.setUserEmail(cursor.getString(cursor.getColumnIndex(Constant.USER_EMAIL)));
                journal.setTheJournal(cursor.getString(cursor.getColumnIndex(Constant.KEY_JOURNAL)));
                DateFormat dateFormat = DateFormat.getDateInstance();
                String formatDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constant.KEY_DATEADDED))).getTime());
                journal.setDateJournalAdded(formatDate);

                listallJournal.add(journal);
            } while (cursor.moveToNext());
        }
        return listallJournal;
    }

    //Update Journal
    public int updateJournal(Journal journal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.KEY_JOURNAL, journal.getTheJournal());
        values.put(Constant.KEY_DATEADDED, java.lang.System.currentTimeMillis());

       return db.update(Constant.JOURNAL_TABLE_NAME, values, Constant.KEY_ID + "=?", new String[]{String.valueOf(journal.getId())});
    }

    //Delete Journal
    public void deleteJournal (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constant.JOURNAL_TABLE_NAME, Constant.KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    //Get Count
    public int getCount () {
        String count_journal = "select * from " + Constant.JOURNAL_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(count_journal, null);
        return cursor.getCount();
    }
}
