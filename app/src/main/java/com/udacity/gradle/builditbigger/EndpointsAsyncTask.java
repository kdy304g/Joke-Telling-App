package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.jokeactivity.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    public interface AsyncTaskResponseListener {
        void onFinish(String result);
    }

    private AsyncTaskResponseListener asyncTaskResponseListener;

    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Context... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(context.getString(R.string.root_url))
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });

            myApiService = builder.build();
        }

        context = params[0];

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {

        if(asyncTaskResponseListener!=null){
            asyncTaskResponseListener.onFinish(result);
        }

        Intent i = new Intent(context, JokeActivity.class);
        i.putExtra("joke",result);
        context.startActivity(i);
    }

    public void setAsyncTaskResponseListener(AsyncTaskResponseListener asyncTaskResponseListener){
        this.asyncTaskResponseListener = asyncTaskResponseListener;
    }
}