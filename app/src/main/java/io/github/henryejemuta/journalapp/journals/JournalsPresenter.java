package io.github.henryejemuta.journalapp.journals;

import android.support.annotation.NonNull;

import io.github.henryejemuta.journalapp.common.Journal;
import io.github.henryejemuta.journalapp.database.AppDatabase;

public class JournalsPresenter implements JournalsContract.UserActionsListener {
    private JournalsContract.View mJournalsView;
    private AppDatabase mDB;

    public JournalsPresenter(AppDatabase db, JournalsContract.View journalsFragment) {
        mJournalsView = journalsFragment;
        mDB = db;
    }

    @Override
    public void loadJournals(boolean b) {
        mJournalsView.showAddJournal();
    }

    @Override
    public void addNewJournal() {

    }

    @Override
    public void openJournalDetails(@NonNull Journal requestedJournal) {

    }
}
