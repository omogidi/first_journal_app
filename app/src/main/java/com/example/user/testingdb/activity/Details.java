package com.example.user.testingdb.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.user.testingdb.R;

public class Details extends AppCompatActivity
{
    private TextView journalDetails;
    private int journalId;
    android.support.v7.widget.Toolbar detTool;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        detTool = findViewById(R.id.detailsToolbar);
        setSupportActionBar(detTool);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        journalDetails = findViewById(R.id.journalTitleDetails);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            journalDetails.setText(bundle.getString("Journal"));
            journalId = bundle.getInt("id");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
