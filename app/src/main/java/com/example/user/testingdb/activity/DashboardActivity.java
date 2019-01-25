package com.example.user.testingdb.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.user.testingdb.R;
import com.example.user.testingdb.data.DatabaseHandler;
import com.example.user.testingdb.data.RecyclerViewAdapter;
import com.example.user.testingdb.data.Session;
import com.example.user.testingdb.model.Journal;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity
{
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private LayoutInflater layoutInflater;
    private FloatingActionButton floatingActionButton;
    private RecyclerViewAdapter recyclerViewAdapter;
    private DatabaseHandler db;
    private List<Journal> journalList;
    private List<Journal> list;
    private RecyclerView recyclerView;
    Session session;

    private android.support.v7.widget.Toolbar dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        session = new Session(this);
        dashboard = findViewById(R.id.dashboardToolbar);
        setSupportActionBar(dashboard);

        dashboard.setTitle(session.getCurrentSession());

        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(DashboardActivity.this, EntryActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        db = new DatabaseHandler(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
    }

    private void loadFromDB()
    {
        list = new ArrayList<>();
        journalList = db.getAllJournal();

        for (Journal j : journalList) {
            Journal journal = new Journal();
            journal.setTheJournal(j.getTheJournal());
            journal.setId(j.getId());
            journal.setDateJournalAdded(j.getDateJournalAdded());

            list.add(journal);
        }
        recyclerViewAdapter.swapData(list);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        loadFromDB();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.about:
                builder = new AlertDialog.Builder(this);
                layoutInflater = LayoutInflater.from(this);
                final View view = layoutInflater.inflate(R.layout.about_dialoog, null);
                builder.setView(view);
                alertDialog = builder.create();
                alertDialog.show();
                break;

            case R.id.signout:
                session.logout();
                finish();
                Toast.makeText(this, "Signed out successfully", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
