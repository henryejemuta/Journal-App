package io.github.henryejemuta.journalapp.common;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "journal")
public class Journal {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @NonNull
    @ColumnInfo(name = "unique_id")
    private String mUniqueId;

    @ColumnInfo(name = "title")
    private final String mTitle;

    @Nullable
    @ColumnInfo(name = "description")
    private final String mDescription;

    @Nullable
    @ColumnInfo(name = "image_url")
    private String mImageUrl;

    @ColumnInfo(name = "priority_color")
    private int mPriorityColor;

    @ColumnInfo(name = "lat")
    private double mLatitude = 0.0;

    @ColumnInfo(name = "lon")
    private double mLongitude = 0.0;

    @ColumnInfo(name = "date_created")
    private final Date mDateCreated;

    @ColumnInfo(name = "date_last_modified")
    private Date mDateLastUpdated;

    @Ignore
    public Journal(String title, @Nullable String description, @Nullable String imageUrl, int priorityColor, double latitude, double longitude, Date dateCreated, Date dateLastUpdated) {
        mUniqueId = UUID.randomUUID().toString();
        mTitle = title;
        mDescription = description;
        mImageUrl = imageUrl;
        mPriorityColor = priorityColor;
        mLatitude = latitude;
        mLongitude = longitude;
        mDateCreated = dateCreated;
        mDateLastUpdated = dateLastUpdated;
    }

    public Journal(long id, @NonNull String uniqueId, String title, @Nullable String description, @Nullable String imageUrl, int priorityColor, double latitude, double longitude, Date dateCreated, Date dateLastUpdated) {
        mId = id;
        mUniqueId = uniqueId;
        mTitle = title;
        mDescription = description;
        mImageUrl = imageUrl;
        mPriorityColor = priorityColor;
        mLatitude = latitude;
        mLongitude = longitude;
        mDateCreated = dateCreated;
        mDateLastUpdated = dateLastUpdated;
    }

    public long getId() {
        return mId;
    }

    public String getUniqueId() {
        return mUniqueId;
    }

    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    @Nullable
    public String getImageUrl() {
        return mImageUrl;
    }

    public int getPriorityColor() {
        return mPriorityColor;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public Date getDateCreated() {
        return mDateCreated;
    }

    public Date getDateLastUpdated() {
        return mDateLastUpdated;
    }

    public boolean isEmpty() {
        return mTitle.isEmpty();
    }

    /*@Override
    public boolean equals(Object o) {
        boolean isEqual = false;
        if (o instanceof Journal) {
            if (((Journal) o).getId().equals(getId()))
                isEqual = true;
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mTitle, mDescription, mImageUrl);
    }*/
}
