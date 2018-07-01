package io.github.henryejemuta.journalapp.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

import io.github.henryejemuta.journalapp.common.Journal;
import io.github.henryejemuta.journalapp.common.Tag;
import io.github.henryejemuta.journalapp.database.AppDatabase;

public class JournalsViewModel extends AndroidViewModel {
    private static final String TAG = JournalsViewModel.class.getSimpleName();

    private AppDatabase mAppDatabase;
    private LiveData<List<Journal>> mJournals;
    private LiveData<List<Tag>> mTags;

    public JournalsViewModel(Application application) {
        super(application);
        mAppDatabase = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the journals from the DataBase");
        mJournals = mAppDatabase.journalDAO().loadAllJournals();
        mTags = mAppDatabase.journalDAO().loadAllTags();
    }

    public LiveData<List<Journal>> getJournals() {
        return mJournals;
    }

    public LiveData<List<Tag>> getTags() {
        return mTags;
    }

    public LiveData<List<Tag>> getTags(Journal journal) {
        return mAppDatabase.journalDAO().loadTagsByJournalId(journal.getId());
    }
}
