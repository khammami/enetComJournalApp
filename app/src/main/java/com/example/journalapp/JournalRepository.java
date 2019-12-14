package com.example.journalapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.journalapp.db.JournalDao;
import com.example.journalapp.db.JournalRoomDataBase;
import com.example.journalapp.model.Journal;

import java.util.List;

public class JournalRepository {

    private JournalDao mJournalDao;
    private LiveData<List<Journal>> mAllJournals;

    public JournalRepository(Application application) {
        JournalRoomDataBase db = JournalRoomDataBase.getDatabase(application);
        mJournalDao = db.journalDao();
        mAllJournals = mJournalDao.getAllJournals();
    }

    public LiveData<List<Journal>> getAllJournals() {
        return mAllJournals;
    }

    public void insert(Journal journal) {
        new insertAsyncTask(mJournalDao).execute(journal);
    }

    public void update(Journal journal)  {
        new updateJournalAsyncTask(mJournalDao).execute(journal);
    }

    public void deleteAll()  {
        new deleteAllJournalsAsyncTask(mJournalDao).execute();
    }

    // Must run off main thread
    public void deleteJournal(Journal journal) {
        new deleteJournalAsyncTask(mJournalDao).execute(journal);
    }

    public LiveData<Journal> getJournalById(int id) {
        return mJournalDao.getJournalById(id);
    }

    // Static inner classes below here to run database interactions in the background.

    /**
     * Inserts a journal into the database.
     */
    private static class insertAsyncTask extends AsyncTask<Journal, Void, Void> {

        private JournalDao mAsyncTaskDao;

        insertAsyncTask(JournalDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Journal... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /**
     * Deletes all journals from the database (does not delete the table).
     */
    private static class deleteAllJournalsAsyncTask extends AsyncTask<Void, Void, Void> {
        private JournalDao mAsyncTaskDao;

        deleteAllJournalsAsyncTask(JournalDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    /**
     *  Deletes a single journal from the database.
     */
    private static class deleteJournalAsyncTask extends AsyncTask<Journal, Void, Void> {
        private JournalDao mAsyncTaskDao;

        deleteJournalAsyncTask(JournalDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Journal... params) {
            mAsyncTaskDao.deleteJournal(params[0]);
            return null;
        }
    }

    /**
     *  Updates a journal in the database.
     */
    private static class updateJournalAsyncTask extends AsyncTask<Journal, Void, Void> {
        private JournalDao mAsyncTaskDao;

        updateJournalAsyncTask(JournalDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Journal... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
