package com.push.jokeslibrary;

import android.support.annotation.BinderThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.BindViews;

public class JockesActivity extends AppCompatActivity {
    public final static String JOKE_EXTRA ="joke_ extra";

    private TextView mJokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jockes);
        String mJokeExtra = getIntent().getStringExtra(JOKE_EXTRA);
        mJokeTextView =( TextView)findViewById(R.id.joke_tv);
        if(getIntent().hasExtra(JOKE_EXTRA)) mJokeTextView.setText(mJokeExtra);
    }
}
