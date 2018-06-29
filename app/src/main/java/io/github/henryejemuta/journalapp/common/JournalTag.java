package io.github.henryejemuta.journalapp.common;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "journal_tag", indices = {@Index("tags"),
        @Index(value = {"order_id", "item_id"})},
        foreignKeys = {@ForeignKey(entity = Journal.class, parentColumns = "id", childColumns = "journal_id"),
                @ForeignKey(entity = Tag.class, parentColumns = "id", childColumns = "tag_id")})
public class JournalTag {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long mId;

    @ColumnInfo(name = "journal_id")
    public long mJournalId;

    @ColumnInfo(name = "tag_id")
    public int mTagId;

    @Ignore
    public JournalTag(long journalId, int tagId) {
        mJournalId = journalId;
        mTagId = tagId;
    }

    public JournalTag(long id, long journalId, int tagId) {
        mId = id;
        mJournalId = journalId;
        mTagId = tagId;
    }

    public long getId() {
        return mId;
    }

    public long getJournalId() {
        return mJournalId;
    }

    public int getTagId() {
        return mTagId;
    }
}
