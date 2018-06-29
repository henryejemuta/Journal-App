package io.github.henryejemuta.journalapp.common;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tag")
public class Tag {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "tag")
    private String mTag;

    @Ignore
    public Tag(String mTag) {
        this.mTag = mTag;
    }

    public Tag(int mId, String mTag) {
        this.mId = mId;
        this.mTag = mTag;
    }

    public int getId() {
        return mId;
    }

    public String getTag() {
        return mTag;
    }
}
