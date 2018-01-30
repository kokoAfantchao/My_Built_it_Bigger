package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.push.jokeslibrary.JockesActivity;
import com.udacity.gradle.builditbigger.MainActivityFragment;


import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements EndpointsAsyncTask.JokesCallbacks {
    private static final int LOADER_ID = 200;
    private CustomIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) return true;
        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
       new EndpointsAsyncTask(this,getIdlingResource(), this )
               .execute(new Pair<Context, String>(this,"nexty"));
    }




    @VisibleForTesting
    @NonNull
    public CustomIdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new CustomIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    public void loadJokes(String messag){
        MainActivityFragment fragmentById = (MainActivityFragment) getSupportFragmentManager()
                  .findFragmentById(R.id.fragment);
        fragmentById.setmJokes(messag);
    }



}

