package com.shikasu.playtime;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by jul on 10/07/15.
 */
public class PlayItem extends RelativeLayout implements TextToSpeech.OnInitListener {

    private TextToSpeech engine;

    private TextView mChineseTextView;
    private TextView mPinyinTextView;

    private Character mCharacter;

    private static String TAG = PlayItem.class.getSimpleName();

    public PlayItem(Context context) {
        super(context);
        init();
    }

    public PlayItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PlayItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    String chinese() {
        if (mChineseTextView == null) return null;
        return mChineseTextView.getText().toString();
    }

    private void chinese(String chinese) {
        if (mChineseTextView != null) {
            mChineseTextView.setText(chinese);
        }
    }

    String pinyin() {
        if (mPinyinTextView == null) return null;
        return mPinyinTextView.getText().toString();
    }

    private void pinyin(String pinyin) {
        if (mPinyinTextView != null) {
            mPinyinTextView.setText(pinyin);
        }
    }

    Character character() { return mCharacter; }
    void setCharacter(Character c) {
        mCharacter = c;
        if (c == null) {
            chinese("");
            pinyin("");
        } else {
            chinese(c.chinese());
            pinyin(c.pinyin());
        }
    }

    void showPinyin(boolean show) {
        if (mCharacter != null) {
            if (show) {
                pinyin("");
            } else {
                pinyin(mCharacter.pinyin());
            }
        }
    }

    void inflateAndLoadElements() {
        View view = inflate(getContext(), R.layout.tile, this);
        mChineseTextView = (TextView) view.findViewById(R.id.chinese);
        mPinyinTextView = (TextView) view.findViewById(R.id.pinyin);
    }

    void init() {
        inflateAndLoadElements();
        engine = new TextToSpeech(getContext(), this);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                //mMediaPlayer.start();
                if (mCharacter != null) {
                    engine.speak(chinese(), TextToSpeech.QUEUE_FLUSH, null, null);
                    // play
                    MainActivityFragment.sFragment.play(PlayItem.this);
                }
            }
        });
    }

    void setPushed(boolean pushed) {
        if (pushed) {
            setBackgroundColor(getResources().getColor(R.color.tile_pushed_bg));
            mChineseTextView.setTextColor(getResources().getColor(R.color.tile_pushed_text));
            mPinyinTextView.setTextColor(getResources().getColor(R.color.tile_pushed_text));
        } else {
            setBackgroundColor(getResources().getColor(R.color.tile_not_pushed_bg));
            mChineseTextView.setTextColor(getResources().getColor(R.color.tile_not_pushed_text));
            mPinyinTextView.setTextColor(getResources().getColor(R.color.tile_not_pushed_text));
        }
    }

    private void showChangeTextDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.change_text);

        final EditText input = new EditText(getContext());
        input.append(chinese());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                input.post(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager inputMethodManager =
                                (InputMethodManager) getContext()
                                        .getSystemService(
                                                Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.showSoftInput(input,
                                InputMethodManager.SHOW_IMPLICIT);
                    }
                });
            }
        });
        input.setSelectAllOnFocus(true);
        input.requestFocus();
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chinese(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    /**
     * Called to signal the completion of the TextToSpeech engine initialization.
     *
     * @param status {@link TextToSpeech#SUCCESS} or {@link TextToSpeech#ERROR}.
     */
    @Override
    public void onInit(int status) {
        Log.d(TAG, "OnInit - Status ["+status+"]");

        if (status == TextToSpeech.SUCCESS) {
            Log.d(TAG, "Success!");
            engine.setLanguage(Locale.CHINA);
        }
    }
}
