package com.example.user.testingdb.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.testingdb.R;
import com.example.user.testingdb.data.DatabaseHandler;
import com.example.user.testingdb.model.User;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button register;
    DatabaseHandler myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        myDb = new DatabaseHandler(this);
        firstName = findViewById(R.id.txtFname);
        lastName = findViewById(R.id.txtLname);
        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        confirmPassword = findViewById(R.id.txtCnfmPsswrd);

        register = findViewById(R.id.btnRegister);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        attemptToRegister();
    }

    private void attemptToRegister()
    {
        String fName = firstName.getText().toString();
        String lName = lastName.getText().toString();
        String emaiL = email.getText().toString();
        String passWord = password.getText().toString();
        String cnfirmPassword = confirmPassword.getText().toString();

        if (TextUtils.isEmpty(fName) ||
                TextUtils.isEmpty(lName) ||
                TextUtils.isEmpty(emaiL) ||
                TextUtils.isEmpty(passWord) ||
                TextUtils.isEmpty(cnfirmPassword)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        } else {
           if (!passWord.equals(cnfirmPassword)) {
               Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
           }else {
               proceedToRegister(fName, lName, emaiL, passWord);
           }
        }
    }

    private void proceedToRegister(String fName, String lName, String emaiL, String passWord)
    {
        boolean emailCheck = myDb.checkEmail(emaiL);
        if (emailCheck == true) {
            Toast.makeText(this, "Email already exist", Toast.LENGTH_SHORT).show();
        } else {
            User user = new User(fName, lName, emaiL, passWord, 0);
            myDb.addUser(user);
            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}
