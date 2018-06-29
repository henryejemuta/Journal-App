package io.github.henryejemuta.journalapp.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.util.Patterns;
import android.widget.Toast;

public class Utils {
    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPermissionGranted(String permission, Context context) {
        return (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED);
    }

    public static void Toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static Location initLocation(String journalTitle, double latitude, double longitude) {
        return initLocation(journalTitle, 0, latitude, longitude);
    }

    public static Location initLocation(String journalTitle, long time, double latitude, double longitude) {
        Location loc = new Location(journalTitle);
        loc.setTime(time);
        loc.setLatitude(latitude);
        loc.setLongitude(longitude);
        return loc;
    }
}
