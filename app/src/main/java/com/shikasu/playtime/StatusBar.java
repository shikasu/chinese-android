package com.shikasu.playtime;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by jul on 2/27/16.
 */
public class StatusBar {

    private final static String TAG = StatusBar.class.getSimpleName();
    private int mPoints;
    private int mMinutes;
    private int mSeconds;
    private int mDifficulty;
    private AppCompatActivity mActivity;

    private final static String FORMAT = "diff %d - points: %d - time %d:%d";

    // FIXME depend on the ActionBar itself
    StatusBar(AppCompatActivity a) {
        if (a == null) throw new NullPointerException("Do not pass null here");
        mActivity = a;
    }

    void refresh() {
        ActionBar bar = mActivity.getSupportActionBar();
        String title = String.format(FORMAT, mDifficulty, mPoints, mMinutes, mSeconds);
        if (bar != null) {
            Log.d(TAG, "Setting actionbar title to" + title);
            bar.setTitle(title);
        } else {
            Log.d(TAG, "Cannot set actionBar title, bar is null...");
        }
    }

    // Action SETTERS:

    StatusBar points(int points) {
        mPoints = points;
        refresh();
        return this;
    }

    StatusBar time(int minutes, int seconds) {
        mMinutes = minutes;
        mSeconds = seconds;
        refresh();
        return this;
    }

    int difficulty() { return mDifficulty; }
    int points() { return mPoints; }
    int timeMinutes() { return mMinutes; }
    int timeSeconds() { return mSeconds; }

}
