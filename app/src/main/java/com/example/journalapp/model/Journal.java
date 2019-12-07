package com.example.journalapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "journal_table")
public class Journal {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "content")
    private String mContent;

    @NonNull
    @ColumnInfo(name = "published_on")
    private Date mJournalDate;

    public Journal( String title, String content, @NonNull Date journalDate){
        this.mTitle = title;
        this.mContent = content;
        this.mJournalDate = journalDate;
    }

    @Ignore
    public Journal(int id, String title, String content, @NonNull Date journalDate){
        this.id = id;
        this.mTitle = title;
        this.mContent = content;
        this.mJournalDate = journalDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public Date getJournalDate() {
        return mJournalDate;
    }

    public void setJournalDate(Date mJournalDate) {
        this.mJournalDate = mJournalDate;
    }

    public void setId(int id) {
        this.id = id;
    }
}
