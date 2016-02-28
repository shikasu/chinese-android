package com.shikasu.playtime;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private final static String TAG = MainActivityFragment.class.getSimpleName();
    private GridLayout mGridLayout;

    // TODO move for speed optim
    private PhraseStore mPhraseStore;
    // FIXME (maybe) is static a good thing here?
    static MainActivityFragment sFragment;

    private GameRound mGameRound;
    private int mPointsTotal = 0;
    private int mGames = 0;
    private int mDifficulty = PhraseStore.MIN_PHRASE_LEN;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sFragment = this;
        mPhraseStore = new PhraseStore(getContext());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mGridLayout = (GridLayout) view.findViewById(R.id.gridLayout);
        resetAllTilesColor();
        triggerNewRound();
        return view;
    }

    public void triggerNewRound() {
        // FIXME: this is a short term hack for multi phrasefile support. (This means we loaded poem.txt)
        if (mPhraseStore.mPhraseList != null && mPhraseStore.mPhraseList.size() > 0) {
            mGameRound = new GameRound(mPhraseStore.getPhrase(), 100, 10);
        } else {
            mGameRound = new GameRound(mPhraseStore.getRandomPhrase(mDifficulty), 100, 20);
        }
        refresh(mGameRound);
    }

    boolean play(PlayItem playItem) {
       boolean matched = mGameRound.play(playItem.character());
        if (matched) {
            playItem.setPushed(true);
            if (mGameRound.state().lengthRemaining() == 0) {
                resetAllTiles();
                mGames += 1;
                mPointsTotal += mGameRound.state().points();
                refreshStatusBar();
                String toToast = mGameRound.phrase().chinese() + " - " +
                        mGameRound.phrase().english();
                Toast.makeText(getContext(), toToast, Toast.LENGTH_SHORT).show();

                triggerNewRound();
            }
        } else {
            resetAllTilesColor();
        }
        return matched;
    }

    void refreshStatusBar() {
        StatusBar statusBar = new StatusBar(
                ((AppCompatActivity)getActivity()).getSupportActionBar());
        statusBar.points(mPointsTotal)
                .games(mGames)
                .difficulty(mDifficulty)
                .refresh();
    }

    void showPinyin(boolean show) {
        int count = mGridLayout.getChildCount();
        for(int i = 0 ; i < count ; i++) {
            PlayItem child = (PlayItem) mGridLayout.getChildAt(i);
            child.showPinyin(show);
        }
    }

    void resetAllTiles() {
        int count = mGridLayout.getChildCount();
        for(int i = 0 ; i < count ; i++) {
            PlayItem child = (PlayItem) mGridLayout.getChildAt(i);
            child.setPushed(false);
            child.setCharacter(null);
        }
    }

    void resetAllTilesColor() {
        int count = mGridLayout.getChildCount();
        for(int i = 0 ; i < count ; i++) {
            PlayItem child = (PlayItem) mGridLayout.getChildAt(i);
            child.setPushed(false);
        }
    }

    void refresh(GameRound gameRound) {
        int count = mGridLayout.getChildCount();
        List<Character> phrase = gameRound.phrase().phrase;
        List<Character> shuffledPhrase = new ArrayList<Character>(phrase);

        Collections.shuffle(shuffledPhrase);
        for(int i = 0 ; i < shuffledPhrase.size() ; i++) {
            PlayItem playItem = (PlayItem) mGridLayout.getChildAt(i);
            playItem.setCharacter(shuffledPhrase.get(i));
        }
    }

    private void showSetDifficultyDialog() {
        NumberPicker myNumberPicker = new NumberPicker(getContext());
        myNumberPicker.setMinValue(PhraseStore.MIN_PHRASE_LEN);
        myNumberPicker.setMaxValue(PhraseStore.MAX_PHRASE_LEN);

        myNumberPicker.setValue(mDifficulty);

        NumberPicker.OnValueChangeListener myValChangedListener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mDifficulty = newVal;
            }
        };

        myNumberPicker.setOnValueChangedListener(myValChangedListener);

        new AlertDialog.Builder(getContext()).setView(myNumberPicker).show();
    }

    void showSetPhrasefileDialog() {
        NumberPicker myNumberPicker = new NumberPicker(getContext());
        myNumberPicker.setMinValue(0);
        myNumberPicker.setMaxValue(1);

        final String[] values = new String[]{"phrases.txt", "poem.txt"};
        myNumberPicker.setDisplayedValues(values);

        NumberPicker.OnValueChangeListener myValChangedListener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // FIXME # this is totally a hax, the way the values are passed
                // FIXME # and the creation of a huge obj out of a scroll change ;D
                mPhraseStore = new PhraseStore(getContext(), newVal, values[newVal]);
            }
        };

        myNumberPicker.setOnValueChangedListener(myValChangedListener);

        new AlertDialog.Builder(getContext()).setView(myNumberPicker).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_settings) return false;
        else if (itemId == R.id.action_show_pinyin) {
            boolean newMode = !item.isChecked();
            item.setChecked(newMode);
            showPinyin(newMode);
            return true;
        } else if (itemId == R.id.action_set_difficulty) {
            showSetDifficultyDialog();
            return true;
        } else if (itemId == R.id.action_set_phrasefile) {
            showSetPhrasefileDialog();
            return true;
        }
        return false;
    }
}
