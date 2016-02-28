package com.shikasu.playtime;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by jul on 2/27/16.
 */
public class PhraseStore {

    private final static String TAG = PhraseStore.class.getSimpleName();

    Map<Integer, List<Phrase>> mPhraseByLengthMap;
    List<Phrase> mPhraseList;
    private Context mContext;

    public static final int STRATEGY_MAPLIST = 0;
    public static final int STRATEGY_LIST = 1;

    private int mStrategy = STRATEGY_MAPLIST;
    private int mCurrentIndex;

    public static final int MIN_PHRASE_LEN = 2;
    public static final int MAX_PHRASE_LEN = 12;

    PhraseStore(Context c) { this(c, STRATEGY_MAPLIST); }
    PhraseStore(Context c, int strategy) { this(c, strategy, "phrases.txt"); }
    PhraseStore(Context c, int strategy, String filename) {
        mStrategy = strategy;
        mPhraseByLengthMap = new HashMap<>();
        mPhraseList = new ArrayList<>();
        mContext = c;
        loadFromAssets(filename);
    }

    private void loadFromAssets(String filename) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open(filename)));

            String line;
            while ((line = reader.readLine()) != null) {
                Log.d(TAG, "adding line from " + filename + ": " + line);
                store(Phrase.fromLine(line, ","));
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }

    void store(Phrase phrase) {
        if (mStrategy == STRATEGY_MAPLIST) {
            List<Phrase> phrasesByLength = mPhraseByLengthMap.get(phrase.size());
            if (phrasesByLength == null) {
                phrasesByLength = new ArrayList<>();
                mPhraseByLengthMap.put(phrase.size(), phrasesByLength);
            }
            phrasesByLength.add(phrase);
        } else if (mStrategy == STRATEGY_LIST) {
            mPhraseList.add(phrase);
        }
    }

    public Phrase getPhrase() {
        Phrase ret = null;
        if (mPhraseList != null && mPhraseList.size() > 0) {
            ret = mPhraseList.get(mCurrentIndex++);
            if (mCurrentIndex == mPhraseList.size()) mCurrentIndex = 0;
        }
        return ret;
    }

    /**
     *
     * @return a random Phrase from the PhraseStore.
     */
    public Phrase getRandomPhrase() {
        return getRandomPhrase(MAX_PHRASE_LEN);
    }

    public Phrase getRandomPhrase(int maxLength) {
        List<Phrase> phraseList = null;
        // FIXME potential infinite loop if mPhraseByLengthMap holds nothing
        do {
            if (mStrategy == STRATEGY_MAPLIST) {
                int i = ThreadLocalRandom.current().nextInt(MIN_PHRASE_LEN, maxLength + 1);

                phraseList = mPhraseByLengthMap.get(i);
                Log.d(TAG, "phraseMap is null for index " + i);
            } else if (mStrategy == STRATEGY_LIST) {
                phraseList = mPhraseList;
            }
        } while (phraseList == null);

        int j = ThreadLocalRandom.current().nextInt(0, phraseList.size());
        return phraseList.get(j);
    }

    public List<Phrase> getPhrases(int size, int maxLength) {
       return null;
    }
}
