package com.shikasu.playtime;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sFragment = this;
        mPhraseStore = new PhraseStore(getContext());
        setHasOptionsMenu(true);
    }

    void setEditModeForAllItems(int editMode) {
        int count = mGridLayout.getChildCount();
        for(int i = 0 ; i <count ; i++){
            PlayItem child = (PlayItem) mGridLayout.getChildAt(i);
            child.setEditMode(editMode);
        }
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
        mGameRound = new GameRound(mPhraseStore.getPhrase(), 100, 20);
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
                Toast.makeText(getContext(), toToast, Toast.LENGTH_LONG).show();

                triggerNewRound();
            }
        } else {
            resetAllTilesColor();
        }
        return matched;
    }

    void refreshStatusBar() {
        // FIXME the activity passed will be changed to ActionBar
        StatusBar statusBar = new StatusBar((AppCompatActivity)getActivity());
        statusBar.points(mPointsTotal).games(mGames).refresh();
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int editMode = item.getItemId();
        if (editMode == R.id.action_settings) return false;
        else {
            boolean newMode = !item.isChecked();
            setEditModeForAllItems(editMode);
            item.setChecked(newMode);
            return true;
        }
    }
}
