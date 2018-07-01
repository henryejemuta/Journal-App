package io.github.henryejemuta.journalapp.repo;

import android.arch.lifecycle.LiveData;

import java.util.List;

import io.github.henryejemuta.journalapp.common.Journal;

public interface JournalsServiceApi {
    interface JournalsServiceCallback<T> {
        void onLoaded(T notes);
    }

    void getAllJournals(JournalsServiceCallback<LiveData<List<Journal>>> callback);

    void getJournal(long journalId, JournalsServiceCallback<Journal> callback);

    void saveJournal(Journal journal);
}
