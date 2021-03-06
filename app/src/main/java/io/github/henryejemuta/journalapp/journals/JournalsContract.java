package io.github.henryejemuta.journalapp.journals;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import java.util.List;

import io.github.henryejemuta.journalapp.common.Journal;

public interface JournalsContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showJournals(List<Journal> journals);

        void showAddJournal();

        void showJournalDetailUi(String journalId);
    }

    interface UserActionsListener {

        void loadJournals(boolean forceUpdate);

        void addNewJournal();

        void openJournalDetails(@NonNull Journal requestedJournal);

        android.view.View.OnClickListener getOnClickListener();

        SwipeRefreshLayout.OnRefreshListener getOnRefreshListener();
    }
}
