package com.udacity.gradle.builditbigger;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by nestorkokoafantchao on 12/10/17.
 */

public class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static final String API_BASE_URL = "http://10.0.2.2:8080/_ah/api/";
    private static MyApi MyApiService = null;
    private Context mContext;
    private CustomIdlingResource mIdlingResource;
    private ProgressDialog mProgressDialog;
    private JokesCallbacks mJokesCallbacks;

    public EndpointsAsyncTask(Context context,
                              CustomIdlingResource idlingResource,
                              JokesCallbacks jokesCallbacks) {
        mContext = context;
        mIdlingResource = idlingResource;
        mJokesCallbacks = jokesCallbacks;

    }

    @Override
    protected void onPreExecute() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage(mContext.getString(R.string.joke_load_msg));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
        mIdlingResource.setIdleState(false);

    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if (MyApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(API_BASE_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            MyApiService = builder.build();
        }

        //mContext = params[0].first;
        String name = params[0].second;

        try {
            return MyApiService.sayHi(name).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }

    }

    @Override
    protected void onPostExecute(String result) {
        if (mJokesCallbacks != null) mJokesCallbacks.loadJokes(result);

        mIdlingResource.setIdleState(true);
        mProgressDialog.dismiss();

    }

    public interface JokesCallbacks {
        public void loadJokes(String messag);
    }

}