package io.github.henryejemuta.journalapp.repo;

import android.support.annotation.NonNull;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class JournalRepositories {

    private JournalRepositories() {
        // no instance
    }

    private static JournalsRepository repository = null;

    public synchronized static JournalsRepository getInMemoryRepoInstance(@NonNull JournalsServiceApi journalsServiceApi) {
        checkNotNull(journalsServiceApi);
        if (null == repository) {
//            repository = new InMemoryJournalsRepository(journalsServiceApi);
        }
        return repository;
    }
}
