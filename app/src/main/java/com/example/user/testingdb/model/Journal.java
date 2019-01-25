package com.example.user.testingdb.model;

import java.io.Serializable;

/**
 * Created by User on 11/13/2018.
 */

public class Journal implements Serializable
{
    public String theJournal;
    public String userEmail;
    public String dateJournalAdded;
    public int id;

    public Journal()
    {
    }

    public Journal(String theJournal, String dateJournalAdded, int id)
    {
        this.theJournal = theJournal;
        this.dateJournalAdded = dateJournalAdded;
        this.id = id;
    }

    public String getUserEmail()
    {
        return userEmail;
    }

    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    public String getTheJournal()
    {
        return theJournal;
    }

    public void setTheJournal(String theJournal)
    {
        this.theJournal = theJournal;
    }

    public String getDateJournalAdded()
    {
        return dateJournalAdded;
    }

    public void setDateJournalAdded(String dateJournalAdded)
    {
        this.dateJournalAdded = dateJournalAdded;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

}

