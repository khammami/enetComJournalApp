package com.example.journalapp;

import android.app.Application;

import com.example.journalapp.db.JournalRoomDataBase;

public class BasicApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public JournalRoomDataBase getDatabase() {
        return JournalRoomDataBase.getInstance(this);
    }

    public JournalRepository getRepository() {
        return JournalRepository.getInstance(getDatabase(), mAppExecutors);
    }
}
