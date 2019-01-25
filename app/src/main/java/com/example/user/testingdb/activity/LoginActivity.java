package com.example.user.testingdb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.testingdb.R;
import com.example.user.testingdb.data.DatabaseHandler;
import com.example.user.testingdb.data.Session;

public class LoginActivity extends AppCompatActivity implements  View.OnClickListener
{
    private EditText mEmail, mPassword;
    private TextView newUser;
    private Button mBtnLogin;
    Session session;

    DatabaseHandler myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = findViewById(R.id.txtEmail);
        mPassword = findViewById(R.id.txtPassword);
        newUser = findViewById(R.id.newUser);
        newUser.setOnClickListener(this);

        myDb = new DatabaseHandler(this);
        session = new Session(this);


        Log.d("Count", String.valueOf(myDb.getCount()));

        mBtnLogin = findViewById(R.id.btnLogin);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                attemptLogin();
                break;

            case R.id.newUser:
                startActivity(new Intent(this, RegistrationActivity.class));
        }
    }


    private void attemptLogin() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Cannot accept empty parameters", Toast.LENGTH_SHORT).show();
            return;
        } proceedToLogin(email, password);
    }

    private void proceedToLogin(String email, String password)
    {
        boolean login = myDb.checkUser(email, password);

        if (login) {
            session.createSession(email);
            proceedToDashBoard();
        }
        else {
            Toast.makeText(this, "Failed to login", Toast.LENGTH_SHORT).show();
        }
    }

    private void proceedToDashBoard() {
        Intent intent = new Intent(this, DashboardActivity.class );
        startActivity(intent);
        finish();
    }
}


