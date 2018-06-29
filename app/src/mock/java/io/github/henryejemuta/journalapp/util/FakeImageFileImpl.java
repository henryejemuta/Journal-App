package io.github.henryejemuta.journalapp.util;

import java.io.IOException;

public class FakeImageFileImpl extends ImageFileImpl {

    @Override
    public void create(String name, String extension) throws IOException {
        // Do nothing
    }

    @Override
    public String getPath() {
        return "file:///android_asset/ejournal.png";
    }

    @Override
    public boolean exists() {
        return true;
    }
}
