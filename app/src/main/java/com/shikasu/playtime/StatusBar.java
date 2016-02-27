package com.shikasu.playtime;

import android.app.ActionBar;
import android.app.Activity;

/**
 * Created by jul on 2/27/16.
 */
public class StatusBar {
    private int mPoints;
    private int mMinutes;
    private int mSeconds;
    private int mDifficulty;
    private Activity mActivity;

    private final static String FORMAT = "diff %d - points: %d - time %d:%d";

    StatusBar(Activity a) {
        if (a == null) throw new NullPointerException("Do not pass null here");
        mActivity = a;
    }

    void refresh() {
        ActionBar bar = mActivity.getActionBar();
        if (bar != null) {
            bar.setTitle(String.format(FORMAT, mDifficulty, mPoints, mMinutes, mSeconds));
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
