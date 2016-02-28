package com.shikasu.playtime;

import android.support.v7.app.ActionBar;

/**
 * Created by jul on 2/27/16.
 */
public class StatusBar {

    private final static String TAG = StatusBar.class.getSimpleName();
    private int mPoints;
    private int mMinutes;
    private int mSeconds;
    private int mDifficulty;
    private int mGames;
    private ActionBar mActionBar;

    private final static String FORMAT = "diff %d - points: %d - games: %d";

    StatusBar(ActionBar a) {
        if (a == null) throw new NullPointerException("Do not pass null here");
        mActionBar = a;
    }

    void refresh() {
        String title = String.format(FORMAT, mDifficulty, mPoints, mGames);
        mActionBar.setTitle(title);
    }

    // Action SETTERS:

    StatusBar games(int games) {
        mGames = games;
        refresh();
        return this;
    }

    StatusBar difficulty(int difficulty) {
        mDifficulty = difficulty;
        refresh();
        return this;
    }

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
