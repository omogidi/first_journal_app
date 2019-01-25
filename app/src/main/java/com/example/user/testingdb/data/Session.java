package com.example.user.testingdb.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.user.testingdb.activity.DashboardActivity;
import com.example.user.testingdb.activity.LoginActivity;

/**
 * Created by User on 1/22/2019.
 */

public class Session
{
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Activity context;

    int PRIVATE_MODE = 0;
    public static final String PREF_NAME = "journal";
    public static final String IS_USER_LOG_IN = "is_logging";
    public static final String USER_EMAIL = "email";

    public Session(Activity context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String email){
        editor.putBoolean(IS_USER_LOG_IN,true);
        editor.putString(USER_EMAIL,email);
        editor.commit();
    }

    public String getCurrentSession(){

        return sharedPreferences.getString(USER_EMAIL,null);
    }

    public void checkLogin(){

        if (this.isLoggedIn()){

            Intent intent = new Intent(context, DashboardActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(intent);
            context.finish();
        }
    }

    public void logout(){
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_USER_LOG_IN, false);
    }
}
