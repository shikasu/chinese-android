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
    private Context mContext;

    private int MIN_PHRASE_LEN = 3;
    private int MAX_PHRASE_LEN = 12;

    PhraseStore(Context c) {
        mPhraseByLengthMap = new HashMap<>();
        mContext = c;
        loadFromAssets("phrases.txt");
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
        List<Phrase> phrasesByLength = mPhraseByLengthMap.get(phrase.size());
        if (phrasesByLength == null) {
            phrasesByLength = new ArrayList<>();
            mPhraseByLengthMap.put(phrase.size(), phrasesByLength);
        }
        phrasesByLength.add(phrase);
    }

    /**
     *
     * @return a random Phrase from the PhraseStore.
     */
    public Phrase getPhrase() {
        return getPhrase(MAX_PHRASE_LEN);
    }

    public Phrase getPhrase(int maxLength) {
        List<Phrase> phraseList;
        // FIXME potential infinite loop if mPhraseByLengthMap holds nothing
        do {
            int i = ThreadLocalRandom.current().nextInt(MIN_PHRASE_LEN, maxLength + 1);

            phraseList = mPhraseByLengthMap.get(i);
            Log.d(TAG, "phraseMap is null for index " + i);
        } while (phraseList == null);

        int j = ThreadLocalRandom.current().nextInt(0, phraseList.size());
        return phraseList.get(j);
    }

    public List<Phrase> getPhrases(int size, int maxLength) {
       return null;
    }
}
