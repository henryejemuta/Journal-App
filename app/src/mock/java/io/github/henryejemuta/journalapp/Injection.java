package io.github.henryejemuta.journalapp;

import io.github.henryejemuta.journalapp.util.FakeImageFileImpl;
import io.github.henryejemuta.journalapp.util.ImageFile;

public class Injection {

    public static ImageFile provideImageFile() {
        return new FakeImageFileImpl();
    }
//
//    public static NotesRepository provideNotesRepository() {
//        return NoteRepositories.getInMemoryRepoInstance(new FakeNotesServiceApiImpl());
//    }
}
