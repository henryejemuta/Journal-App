package io.github.henryejemuta.journalapp.journals;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import io.github.henryejemuta.journalapp.R;
import io.github.henryejemuta.journalapp.common.Journal;
import io.github.henryejemuta.journalapp.database.AppDatabase;

public class JournalsPresenter implements JournalsContract.UserActionsListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{
    private JournalsContract.View mJournalsView;
    private AppDatabase mDB;

    public JournalsPresenter(AppDatabase db, JournalsContract.View journalsFragment) {
        mJournalsView = journalsFragment;
        mDB = db;
    }

    @Override
    public void loadJournals(boolean loadJournal) {
        if(loadJournal) mJournalsView.showAddJournal();
    }

    @Override
    public void addNewJournal() {

    }

    @Override
    public void openJournalDetails(@NonNull Journal requestedJournal) {

    }

    @Override
    public View.OnClickListener getOnClickListener() {
        return this;
    }

    @Override
    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return this;
    }

    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        switch (viewID) {
            case R.id.fab_add_journals:  mJournalsView.showAddJournal();
                break;
            default: ;
                break;
        }
    }

    @Override
    public void onRefresh() {

    }
}
