package io.github.henryejemuta.journalapp.journals;

import io.github.henryejemuta.journalapp.common.Journal;

public interface JournalItemListener {
    void onJournalClick(Journal clickedJournal);
}
