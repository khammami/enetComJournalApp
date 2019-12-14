package com.example.journalapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.journalapp.model.Journal;

import java.util.List;

@Dao
public interface JournalDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Journal journal);

    @Query("DELETE FROM journal_table")
    void deleteAll();

    @Delete
    void deleteJournal(Journal journal);

    @Query("SELECT * from journal_table ORDER BY published_on DESC")
    LiveData<List<Journal>> getAllJournals();

    @Update
    void update(Journal... journal);

    @Query("SELECT * from journal_table WHERE id = :id")
    LiveData<Journal> getJournalById(int id);
}
