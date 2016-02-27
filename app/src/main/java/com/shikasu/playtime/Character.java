package com.shikasu.playtime;

/**
 * Created by jul on 2/27/16.
 */
public class Character {
    String mChinese;
    String mEnglish;
    String mPinyin;

    Character(String chinese) {
        new Character(chinese, null);
    }

    Character(String chinese, String english) {
        this.mChinese = chinese;
        this.mEnglish = english;
    }
}
