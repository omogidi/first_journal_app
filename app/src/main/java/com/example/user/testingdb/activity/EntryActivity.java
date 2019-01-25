package com.example.user.testingdb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.testingdb.R;
import com.example.user.testingdb.data.DatabaseHandler;
import com.example.user.testingdb.data.Session;
import com.example.user.testingdb.model.Journal;

public class EntryActivity extends AppCompatActivity
{
    EditText content;
    DatabaseHandler db;
    private Journal journal;
    Toolbar toolbar;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        content = findViewById(R.id.txtDescription);
        toolbar = findViewById(R.id.addToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        session = new Session(this);
        db = new DatabaseHandler(this);

        if(getIntent().hasExtra(getString(R.string.data))){
            journal = (Journal) getIntent().getSerializableExtra(getString(R.string.data));
            content.setText(journal.getTheJournal());
        }
    }

    private void updateJournalDB()
    {
        DatabaseHandler db = new DatabaseHandler(this);
        journal.setTheJournal(content.getText().toString());
        if (!content.getText().toString().isEmpty()) {
            db.updateJournal(journal);
            journal.setDateJournalAdded(journal.getDateJournalAdded());
            Toast.makeText(this, "Journal Updated", Toast.LENGTH_SHORT).show();
        } else if (content.getText().toString().isEmpty()) {
            Toast.makeText(this, "Empty Parameters", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void saveJournalToDB()
    {
        Journal journal = new Journal();
        String newJournal = content.getText().toString();
        journal.setTheJournal(newJournal);
        journal.setUserEmail(session.getCurrentSession());

        db.addJournal(journal);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", journal);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

        Toast.makeText(this, "Journal saved Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.home:
                finish();
                break;

            case R.id.check:
                if (!content.getText().toString().isEmpty()) {
                    if(journal == null)
                        saveJournalToDB();
                    else
                        updateJournalDB();
                    finish();
                } else {
                    Toast.makeText(EntryActivity.this, "The Field is empty", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

            case R.id.cancel:
                if (!content.getText().toString().isEmpty()) {
                    if(journal == null)
                        saveJournalToDB();
                    else
                        updateJournalDB();
                    finish();
                }
                else {
                    finish();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_entry, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy()
    {
        finish();
        super.onDestroy();
    }
}
