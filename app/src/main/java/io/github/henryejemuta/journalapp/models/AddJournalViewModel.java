package io.github.henryejemuta.journalapp.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.github.henryejemuta.journalapp.common.Journal;
import io.github.henryejemuta.journalapp.common.Tag;
import io.github.henryejemuta.journalapp.database.AppDatabase;

public class AddJournalViewModel extends ViewModel {

    private LiveData<Journal> mJournal;
    private long mJournalId;
    private AppDatabase mAppDatabase;

    public AddJournalViewModel(AppDatabase database, long journalId) {
        mAppDatabase = database;
        mJournalId = journalId;
        mJournal = database.journalDAO().loadJournalById(journalId);
    }

    public LiveData<Journal> getJournal() {
        return mJournal;
    }

    public LiveData<List<Tag>> getTags() {
        return mAppDatabase.journalDAO().loadTagsByJournalId(mJournalId);
    }
}
