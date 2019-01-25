package com.example.user.testingdb.data;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.testingdb.R;
import com.example.user.testingdb.activity.Details;
import com.example.user.testingdb.activity.EntryActivity;
import com.example.user.testingdb.model.Journal;

import java.util.List;

/**
 * Created by User on 11/13/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    private Context context;
    private DatabaseHandler db;
    private List<Journal> listjournal;

    public RecyclerViewAdapter (Context context)
    {
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position)
    {
        Journal journal = listjournal.get(position);
        holder.journalTitle.setText(journal.getTheJournal());
        holder.dateTitle.setText(journal.getDateJournalAdded());

    }

    @Override
    public int getItemCount()
    {
        return listjournal == null ? 0 : listjournal.size();
    }

    public void swapData(List<Journal> list)
    {
        listjournal = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView journalTitle, dateTitle, edit, delete;
        private int id;

        public ViewHolder(View itemView, Context ctx)
        {
            super(itemView);
            journalTitle = itemView.findViewById(R.id.journalAdapter);
            dateTitle = itemView.findViewById(R.id.dateAdapter);

            edit = itemView.findViewById(R.id.editButton);
            edit.setOnClickListener(this);
            delete = itemView.findViewById(R.id.deleteButton);
            delete.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int position = getAdapterPosition();
                    Journal journal = listjournal.get(position);
                    Intent intent = new Intent(context, Details.class);
                    intent.putExtra("Journal", journal.getTheJournal());
                    intent.putExtra("Date", journal.getDateJournalAdded());
                    intent.putExtra("id", journal.getId());
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId()) {
                case R.id.editButton:
                    int position = getAdapterPosition();
                    Journal updateJournal = listjournal.get(position);
                    editJournal(updateJournal);

                    break;
                case R.id.deleteButton:
                    int deletePosition = getAdapterPosition();
                    Journal delJournal = listjournal.get(deletePosition);
                    deleteJournal(delJournal.getId());
                    break;
            }
        }

        public void editJournal(final Journal journal) {
            Intent intent = new Intent(context, EntryActivity.class);
            intent.putExtra(context.getString(R.string.data), journal);
            context.startActivity(intent);
        }

        public void deleteJournal(final int id){
            //create an alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirm_dialog, null);

            Button no = view.findViewById(R.id.noBtn);
            Button yes = view.findViewById(R.id.yesBtn);

            final AlertDialog dialog;
            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            no.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    dialog.dismiss();
                }
            });

            yes.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.deleteJournal(id);
                    Toast.makeText(context, "Journal Deleted", Toast.LENGTH_SHORT).show();
                    listjournal.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();
                }
            });
        }
    }
}
