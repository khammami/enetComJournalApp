package com.example.journalapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.journalapp.db.JournalDao;
import com.example.journalapp.db.JournalRoomDataBase;
import com.example.journalapp.model.Journal;

import java.util.List;

public class JournalRepository {

    private static JournalRepository sInstance;
    private final AppExecutors mExecutors;

    private final JournalRoomDataBase db;
    private LiveData<List<Journal>> mAllJournals;

    public JournalRepository(JournalRoomDataBase database, AppExecutors executors) {
        db = database;
        mExecutors = executors;
        mAllJournals = db.journalDao().getAllJournals();
    }

    public static JournalRepository getInstance(final JournalRoomDataBase database,
                                             final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (JournalRepository.class) {
                if (sInstance == null) {
                    sInstance = new JournalRepository(database, executors);
                }
            }
        }
        return sInstance;
    }


    public LiveData<List<Journal>> getAllJournals() {
        return mAllJournals;
    }

    public void insert(final Journal journal) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.journalDao().insert(journal);
            }
        });
    }

    public void update(final Journal journal) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.journalDao().update(journal);
            }
        });
    }

    public void deleteAll() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.journalDao().deleteAll();
            }
        });
    }

    // Must run off main thread
    public void deleteJournal(final Journal journal) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.journalDao().deleteJournal(journal);
            }
        });
    }

    public LiveData<Journal> getJournalById(int id) {
        return db.journalDao().getJournalById(id);
    }
}
