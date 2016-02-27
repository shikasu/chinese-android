package com.shikasu.playtime;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jul on 2/27/16.
 */
public class PhraseStore {

    Map<Integer, List<Phrase>> mPhraseByLengthMap;

    PhraseStore() {
        mPhraseByLengthMap = new HashMap<>();
    }

    void store(Phrase phrase) {
        List<Phrase> phrasesByLength = mPhraseByLengthMap.get(phrase.size());
        if (phrasesByLength == null) {
            phrasesByLength = new ArrayList<>();
        }
        phrasesByLength.add(phrase);
    }

    Phrase getPhrase(int maxLength) {
        return null;
    }

    Phrase getPhrase() {
       return null;
    }

    List<Phrase> getPhrases(int size, int maxLength) {
       return null;
    }

}
