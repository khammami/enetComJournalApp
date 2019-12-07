package com.example.journalapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.journalapp.model.Journal;

@Database(entities = {Journal.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class JournalRoomDataBase extends RoomDatabase {

    public abstract JournalDao journalDao();

    private static JournalRoomDataBase INSTANCE;

    public static JournalRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (JournalRoomDataBase.class) {
                if (INSTANCE == null) {
                    // Create database here.
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            JournalRoomDataBase.class, "journal_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
