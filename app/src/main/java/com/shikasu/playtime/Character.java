package com.shikasu.playtime;

import android.util.Log;

import com.github.promeg.pinyinhelper.Pinyin;

/**
 * Created by jul on 2/27/16.
 */
public class Character {

    private String TAG = Character.class.getSimpleName();
    private String mChinese;
    private String mEnglish;
    private String mPinyin;

    public Character chinese(String chinese) {
        mChinese = chinese;
        return this;
    }

    public Character english(String english) {
        mEnglish = english;
        return this;
    }

    public Character pinyin(String pinyin) {
        mPinyin = pinyin;
        return this;
    }

    public char chineseChar() {
        return mChinese.charAt(0);
    }

    public String chinese() {
        return mChinese;
    }

    public String english() {
        return mEnglish;
    }

    public String pinyin() {
        if (mPinyin == null) {
            // fallback to return pinyin from tinypinyin
            if (mChinese.length() > 1) {
                Log.w(TAG, "Received a Character which chinese member is not of size 1 but " +
                        mChinese.length());
                Log.w(TAG, "In case it is longer than 1, Will only convert to pinyin first character");
                Log.w(TAG, "In case it is 0, the pinyin will be empty string too");
            } else if (mChinese.length() == 0) {
                return "";
            }
            return Pinyin.toPinyin(mChinese.charAt(0));
        }
        return mPinyin;
    }
}
