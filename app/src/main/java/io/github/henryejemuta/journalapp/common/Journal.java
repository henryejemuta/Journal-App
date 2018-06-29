package io.github.henryejemuta.journalapp.common;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "journal")
public class Journal {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private String mId;

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
        mId = UUID.randomUUID().toString();
        mTitle = title;
        mDescription = description;
        mImageUrl = imageUrl;
        mPriorityColor = priorityColor;
        mLatitude = latitude;
        mLongitude = longitude;
        mDateCreated = dateCreated;
        mDateLastUpdated = dateLastUpdated;
    }

    public Journal(String id, String title, @Nullable String description, @Nullable String imageUrl, int priorityColor, double latitude, double longitude, Date dateCreated, Date dateLastUpdated) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mImageUrl = imageUrl;
        mPriorityColor = priorityColor;
        mLatitude = latitude;
        mLongitude = longitude;
        mDateCreated = dateCreated;
        mDateLastUpdated = dateLastUpdated;
    }

    public String getId() {
        return mId;
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

    public int getmPriorityColor() {
        return mPriorityColor;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public Date getmDateCreated() {
        return mDateCreated;
    }

    public Date getmDateLastUpdated() {
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
