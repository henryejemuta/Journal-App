package io.github.henryejemuta.journalapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.github.henryejemuta.journalapp.common.Journal;
import io.github.henryejemuta.journalapp.common.JournalTag;
import io.github.henryejemuta.journalapp.common.Tag;

@Dao
public interface JournalDAO {
    // Journal Queries Starts Here
    ////===================================================================
    @Query("SELECT * FROM journal ORDER BY date_last_modified")
    LiveData<List<Journal>> loadAllJournals();

    @Insert
    void insertJournal(Journal journalEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateJournal(Journal journalEntry);

    @Delete
    void deleteJournal(Journal taskEntry);

    @Query("SELECT * FROM journal WHERE id = :id")
    LiveData<Journal> loadJournalById(long id);

    @Query("SELECT * FROM journal WHERE unique_id = :uniqueID")
    LiveData<Journal> loadJournalByStringID(String uniqueID);

    @Query("SELECT * FROM journal WHERE lat = :lat AND lon = :lon  ORDER BY date_last_modified")
    LiveData<List<Journal>> loadJournalsByLonAndLat(long lat, long lon);

    @Query("SELECT * FROM journal WHERE lat = :lat ORDER BY date_last_modified")
    LiveData<List<Journal>> loadJournalsByLatitude(long lat);

    @Query("SELECT * FROM journal WHERE lon = :lon  ORDER BY date_last_modified")
    LiveData<List<Journal>> loadJournalsByLongitude(long lon);

    @Query("SELECT j.id, j.unique_id, j.title, j.description, j.image_url, j.priority_color, j.lat, j.lon, j.date_created, j.date_last_modified FROM journal j JOIN journal_tag jt ON jt.journal_id = j.id JOIN tag t ON t.id = jt.tag_id WHERE t.tag = :tag  ORDER BY j.date_last_modified")
    LiveData<List<Journal>> loadJournalsByTag(String tag);

    @Query("SELECT * FROM journal WHERE priority_color = :color  ORDER BY date_last_modified")
    LiveData<List<Journal>> loadJournalsByColor(int color);
//===================================================================


    //Tag Queries Starts Here
//===================================================================
    @Query("SELECT * FROM tag ORDER BY id")
    LiveData<List<Tag>> loadAllTags();

    @Insert
    void insertTag(Tag tagEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTag(Tag tagEntry);

    @Delete
    void deleteTag(Tag tagEntry);

    @Query("SELECT * FROM tag WHERE id = :id  ORDER BY id")
    LiveData<Tag> loadTagsById(int id);

    @Query("SELECT t.id, t.tag FROM tag t JOIN journal_tag jt ON jt.journal_id = t.id JOIN journal j ON j.id = jt.journal_id WHERE j.id = :id  ORDER BY t.id")
    LiveData<List<Tag>> loadTagsByJournalId(long id);
//===================================================================


    //JournalTag Queries Starts Here
//===================================================================
    @Query("SELECT * FROM journal_tag ORDER BY id")
    LiveData<List<JournalTag>> loadAllJournalTag();

    @Insert
    void insertJournalTag(JournalTag journalTagEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateJournalTag(JournalTag journalTagEntry);

    @Delete
    void deleteJournalTag(JournalTag journalTagEntry);

    @Query("SELECT * FROM journal_tag WHERE id = :id  ORDER BY id")
    LiveData<JournalTag> loadJournalTagById(int id);

//===================================================================
}
