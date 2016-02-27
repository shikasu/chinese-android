package com.shikasu.playtime;

/**
 * Created by jul on 2/27/16.
 */
public class GameRoundState {
    Phrase mPhrase;
    private int mPoints;
    private int mLengthDiscovered;

    GameRoundState(Phrase phrase, int lengthDiscovered, int points) {
        this.mPhrase = phrase;
        this.mLengthDiscovered = lengthDiscovered;
        this.mPoints = points;
    }

    int lengthDiscovered() {
        return mLengthDiscovered;
    }

    int lengthUndiscovered() {
        return mPhrase.size() - mLengthDiscovered;
    }

    Phrase phrase() {
        return mPhrase;
    }

    int points() {
        return mPoints;
    }
}
