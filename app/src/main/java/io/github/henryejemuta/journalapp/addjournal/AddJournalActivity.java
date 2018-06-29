package io.github.henryejemuta.journalapp.addjournal;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.github.henryejemuta.journalapp.geotagging.GetLocation;

public class AddJournalActivity extends AppCompatActivity {
    private Location mCurrentLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new GetLocation.LocationResult(){
            @Override
            public void gotLocation(Location location){
                mCurrentLocation = location;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Any UI related code
                    }
                });
            }
        };

    }
}
