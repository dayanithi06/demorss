package com.demorss.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.demorss.fragment.adapter.RedditAdapter;
import com.demorss.model.RssFeedModel;
import com.demorss.utils.Parser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by Dayanithi on 07-Jul-17.
 */

public class TimerService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    String urlLink, isWhich;
    List<RssFeedModel> mFeedModelList;
boolean isLoading= false;
    public TimerService() {
        super("TimerService");
    }

    public TimerService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(!isLoading)
        urlLink = intent.getStringExtra("urlLink");
        isWhich = intent.getStringExtra("isWhich");
        connectToApi();
    }

    private void connectToApi() {
        new FetchFeedTask().execute();

    }

    class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            isLoading= true;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {


            try {
                if (!urlLink.startsWith("http://") && !urlLink.startsWith("https://")) {
                    urlLink = "http://" + urlLink;

                }

                URL url = new URL(urlLink);
                InputStream inputStream = url.openConnection().getInputStream();
                mFeedModelList = Parser.parseFeed(inputStream);
                return true;
            } catch (IOException e) {
                Log.e(TAG, "Error", e);
            } catch (XmlPullParserException e) {
                Log.e(TAG, "Error", e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
isLoading=false;
            if (success) {
                if (isWhich.equalsIgnoreCase("yahoo")&&urlLink.contains("yahoo")) {
                    Intent intent = new Intent();
                    intent.putExtra("data", (Serializable) mFeedModelList);
                    intent.setAction("com.demorss.yahoo");
                    sendBroadcast(intent);
                } else if (isWhich.equalsIgnoreCase("reditt")&&urlLink.contains("rediff")) {
                    Intent intent = new Intent();
                    intent.putExtra("data", (Serializable) mFeedModelList);
                    intent.setAction("com.demorss.reditt");
                    sendBroadcast(intent);
                }
            } else {
                Toast.makeText(TimerService.this,
                        "Something went wrong",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
