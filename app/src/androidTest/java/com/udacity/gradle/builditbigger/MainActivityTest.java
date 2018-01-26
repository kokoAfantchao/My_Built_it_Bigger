package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.core.AllOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

/**
 * Created by nestorkokoafantchao on 1/20/18.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity>  activityIntentsTestRule = new IntentsTestRule<MainActivity>(MainActivity.class);
    private CustomIdlingResource  mIdlingResource;
    private static final  String PACKAGE_NAME= "com.udacity.gradle.builditbigger";

    @Before
    public void subAllInternalIntent() throws Exception {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Before
    public void registerIdlingResource(){
        mIdlingResource = activityIntentsTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @After
    public  void unRegisterIdlingResource(){
        if (mIdlingResource != null) Espresso.unregisterIdlingResources(mIdlingResource);
    }


    @Test
    public  void AsyncJokesLoadTest(){
        ViewInteraction viewInteraction = Espresso.onView(ViewMatchers.withId(R.id.button_action_show_jockes));
        viewInteraction.perform(ViewActions.click());
        intended(AllOf.allOf(hasExtraWithKey("joke_ extra"),toPackage(PACKAGE_NAME)));
    }

}