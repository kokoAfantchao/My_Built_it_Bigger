package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.push.jokeslibrary.JockesActivity;
import com.udacity.gradle.builditbigger.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private String mJokes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        return root;
    }

    public void setmJokes(String mJokes) {
        this.mJokes = mJokes;
        Intent intent = new Intent(getContext(),JockesActivity.class);
        intent.putExtra(JockesActivity.JOKE_EXTRA, mJokes);
        startActivity(intent);

    }
}