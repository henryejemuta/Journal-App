package io.github.henryejemuta.journalapp.repo;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import io.github.henryejemuta.journalapp.common.Journal;

public interface JournalsRepository {
    interface LoadJournalsCallback {

        void onJournalsLoaded(LiveData<List<Journal>> journals);
    }

    interface GetJournalCallback {

        void onJournalLoaded(Journal journal);
    }

    void getJournals(@NonNull LoadJournalsCallback callback);

    void getJournal(@NonNull String journalId, @NonNull GetJournalCallback callback);

    void saveJournal(@NonNull Journal journal);

    void refreshData();
}
