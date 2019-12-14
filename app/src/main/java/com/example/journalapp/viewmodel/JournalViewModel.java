package com.example.journalapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.journalapp.BasicApp;
import com.example.journalapp.JournalRepository;
import com.example.journalapp.model.Journal;

import java.util.List;

public class JournalViewModel extends AndroidViewModel {

    private JournalRepository mRepository;

    private LiveData<List<Journal>> mAllJournals;

    public JournalViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((BasicApp) application).getRepository();
        mAllJournals = mRepository.getAllJournals();
    }

    public LiveData<List<Journal>> getAllJournals() {
        return mAllJournals;
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deleteJournal(Journal journal) {
        mRepository.deleteJournal(journal);
    }
}
