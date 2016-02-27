package com.shikasu.playtime;

import java.util.List;

/**
 * Created by jul on 2/27/16.
 */
public class Phrase {
    List<Character> phrase;
    String chinese;
    String pinyin = null;
    String english = null;

    Phrase(String chinese) { this.chinese = chinese; }

    Phrase(String chinese, String pinyin) {
        this.chinese = chinese;
        this.pinyin = pinyin;
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
        if (this.pinyin == null) {
            String constructedPinyin = "";
            for (int i = 0; i < phrase.size(); i++) {
                constructedPinyin += phrase.get(i).pinyin() + " ";
            }
            return constructedPinyin.substring(0, constructedPinyin.length() - 1);
        }
        return this.pinyin;
    }

    String getEnglish() { return this.english; }
}
