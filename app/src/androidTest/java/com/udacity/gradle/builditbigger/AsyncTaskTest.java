package com.udacity.gradle.builditbigger;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class AsyncTaskTest implements EndpointsAsyncTask.AsyncTaskResponseListener{
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testAsyncTaskResponse() {
        onView(withId(R.id.instructions_text_view)).perform(click());
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask();
        endpointsAsyncTask.setAsyncTaskResponseListener(this);
        endpointsAsyncTask.execute(activityTestRule.getActivity());
    }

    @Override
    public void onFinish(String result) {
//        Log.d("test",result);
        assertNotNull(result);
    }
}
