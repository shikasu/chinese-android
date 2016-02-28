package com.shikasu.playtime;

/**
 * Created by jul on 2/27/16.
 */
public class GameRound {
    private Phrase mPhrase;
    private GameRoundState mState;
    private int mMalusPoints;

    /**
     * Starts a GameRound with a given phrase with max number of points awarded
     * for perfect completion. malusPoints will be retrieved from points total
     * if player makes a mistake.
     * @param phrase
     * @param points
     * @param malusPoints
     */
    GameRound(Phrase phrase, int points, int malusPoints) {
        mPhrase = phrase;
        mMalusPoints = malusPoints;
        mState = new GameRoundState(phrase, 0, points);
    }

    /**
     * Play using the character c
     * @param c
     */
    boolean play(Character c) {
        // Which index is selected for discovery?
        int index = mState.lengthDiscovered();
        // Does it match the character of the phrase at that index?
        boolean matched = mPhrase.mChinese.charAt(index) == c.chineseChar();

        if (matched) {
            mState.incrementLengthDiscovered();
        } else {
            mState.decreasePointsBy(mMalusPoints);
            mState.resetLengthDiscovered(); // TODO make configurable?
        }
        return matched;
    }

    /** Returns the state of the game at the time of invocation
     *
     * @return The state of the game at the time of invocation
     */
    GameRoundState state() { return mState; }
    Phrase phrase() { return mPhrase; }
}
