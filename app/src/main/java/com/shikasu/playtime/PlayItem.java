package com.shikasu.playtime;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by jul on 10/07/15.
 */
public class PlayItem extends TextView implements TextToSpeech.OnInitListener {

    private int mEditMode; //change to int tracking action_editmode_* TODO
    private MediaPlayer mMediaPlayer;
    private TextToSpeech engine;

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

    void init() {
        engine = new TextToSpeech(getContext(), this);
        setEditMode(R.id.action_editmode_none);
        int mSoundId = R.raw.ak47;
        mMediaPlayer = MediaPlayer.create(this.getContext(), mSoundId);
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //mp.release();
            }
        });
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                switch (mEditMode) {
                    case R.id.action_editmode_background_color:
                    case R.id.action_editmode_background_image:
                        Drawable drawable = v.getBackground();
                        int initialColor = Color.BLACK;
                        if (drawable instanceof BitmapDrawable) {
                        } else {
                            ColorDrawable colorDrawable = (ColorDrawable) v.getBackground();
                            initialColor = colorDrawable.getColor();
                        }
                        break;
                    case R.id.action_editmode_none:
                        //mMediaPlayer.start();
                        engine.speak(getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
                        break;
                    case R.id.action_editmode_text:
                        showChangeTextDialog();
                    default:
                        break;
                }
            }
        });
    }

    private void showChangeTextDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.change_text);

        final EditText input = new EditText(getContext());
        input.append(getText());
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
                setText(input.getText().toString());
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

    public int editMode() {
        return mEditMode;
    }

    public void setEditMode(int editMode) {
        mEditMode = editMode;
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