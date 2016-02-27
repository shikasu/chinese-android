package com.shikasu.playtime;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private final static String TAG = MainActivityFragment.class.getSimpleName();
    private GridLayout mGridLayout;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    void setEditModeForAllItems(int editMode) {
        int count = mGridLayout.getChildCount();
        for(int i = 0 ; i <count ; i++){
            PlayItem child = (PlayItem) mGridLayout.getChildAt(i);
            child.setEditMode(editMode);
        }
    }

    void setRandomColorBackgroundForAllItems() {
        int count = mGridLayout.getChildCount();
        for(int i = 0 ; i <count ; i++){
            PlayItem child = (PlayItem) mGridLayout.getChildAt(i);
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            child.setBackgroundColor(color);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mGridLayout = (GridLayout) view.findViewById(R.id.gridLayout);
        setRandomColorBackgroundForAllItems();
        return view;
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
