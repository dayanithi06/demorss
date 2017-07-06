package com.demorss.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demorss.R;
import com.demorss.fragment.adapter.YahooAdapter;
import com.demorss.model.RssFeedModel;
import com.demorss.utils.Parser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by Dayanithi on 05-Jul-17.
 */

public class YahooFragment extends Fragment {
    private String urlLink="https://sports.yahoo.com/top/rss.xml";
    private List<RssFeedModel> mFeedModelList;;
RecyclerView recycleView;
    View convertView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        convertView=   inflater.inflate(R.layout.fragment_yahoo,container,false);
        intitViews(convertView);
        new FetchFeedTask().execute();

        return convertView;
    }

    private void intitViews(View convertView) {
        recycleView =(RecyclerView) convertView.findViewById(R.id.recycleView);
    }

    private class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {

        private String urlLink;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (TextUtils.isEmpty(urlLink))
                return false;

            try {
                if(!urlLink.startsWith("http://") && !urlLink.startsWith("https://"))
                    urlLink = "http://" + urlLink;

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

            if (success) {
              /*  mFeedTitleTextView.setText("Feed Title: " + mFeedTitle);
                mFeedDescriptionTextView.setText("Feed Description: " + mFeedDescription);
                mFeedLinkTextView.setText("Feed Link: " + mFeedLink);*/
                // Fill RecyclerView
                recycleView.setAdapter(new YahooAdapter(mFeedModelList));
            } else {
                Toast.makeText(getActivity(),
                        "Something went wrong",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
