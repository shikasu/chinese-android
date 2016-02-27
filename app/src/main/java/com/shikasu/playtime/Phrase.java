package com.shikasu.playtime;

import java.util.List;

/**
 * Created by jul on 2/27/16.
 */
public class Phrase {
    List<Character> phrase;
    String mChinese;
    String mPinyin = null;
    String mEnglish = null;

    Phrase(String chinese, String pinyin, String english) {
        mChinese = chinese;
        mPinyin = pinyin;
        mEnglish = english;
    }

    Phrase(String chinese) {
        this(chinese, null, null);
    }

    Phrase(String chinese, String pinyin) {
        this(chinese, pinyin, null);
    }

    static Phrase fromLine(String line, String delim) {
        String[] splitted = line.split(delim);
        if (splitted.length == 1) {
            return new Phrase(splitted[0]);
        } else if (splitted.length == 2) {
            return new Phrase(splitted[0], splitted[1]);
        } else if (splitted.length == 3) {
            return new Phrase(splitted[0], splitted[1], splitted[2]);
        }
        return null;
    }

    int size() { return phrase.size(); }

    String getChinese() {
        String constructedChinese = "";
        for (int i = 0; i < phrase.size(); i++) {
            constructedChinese += phrase.get(i).chinese();
        }
        return constructedChinese;
    }

    String getPinyin() {
        if (this.mPinyin == null) {
            String constructedPinyin = "";
            for (int i = 0; i < phrase.size(); i++) {
                constructedPinyin += phrase.get(i).pinyin() + " ";
            }
            return constructedPinyin.substring(0, constructedPinyin.length() - 1);
        }
        return this.mPinyin;
    }

    String getEnglish() { return this.mEnglish; }
}
