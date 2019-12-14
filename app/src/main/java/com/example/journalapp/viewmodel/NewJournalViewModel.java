package com.example.journalapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.journalapp.JournalRepository;
import com.example.journalapp.model.Journal;

public class NewJournalViewModel extends AndroidViewModel {

    private JournalRepository mRepository;

    public NewJournalViewModel(@NonNull Application application) {
        super(application);
        mRepository = new JournalRepository(application);
    }

    public LiveData<Journal> getJournalById(int id) {
        return mRepository.getJournalById(id);
    }

    public void insert(Journal journal) {
        mRepository.insert(journal);
    }

    public void update(Journal journal) {
        mRepository.update(journal);
    }
}
