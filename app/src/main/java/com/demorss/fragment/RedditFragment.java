package com.demorss.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demorss.R;
import com.demorss.fragment.adapter.RedditAdapter;
import com.demorss.fragment.adapter.YahooAdapter;
import com.demorss.model.RssFeedModel;
import com.demorss.service.TimerService;
import com.demorss.utils.Parser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by Dayanithi on 05-Jul-17.
 */


/**
 * Created by Dayanithi on 05-Jul-17.
 */

public class RedditFragment extends Fragment {
    private String urlLink = "http://www.rediff.com/rss/usrss.xml";
    private List<RssFeedModel> mFeedModelList;
    RecyclerView recycleView;
    View convertView;
    Timer mTimer = new Timer();
    RedditReceiver redditReceiver;
    SwipeRefreshLayout swipeRefreshLayout;
Handler handler = new Handler();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        convertView = inflater.inflate(R.layout.fragment_reddit, container, false);
        intitViews(convertView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(manager);
        registerReciver();
        setListner();
        return convertView;
    }
    private void setListner() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                startLoadService();
            }
        });
    }
    private void intitViews(View convertView) {
        recycleView = (RecyclerView) convertView.findViewById(R.id.recycleView);
        swipeRefreshLayout = (SwipeRefreshLayout) convertView.findViewById(R.id.swipeLayout);
    }

    private void registerReciver() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                redditReceiver = new RedditFragment.RedditReceiver();
                getActivity().registerReceiver(redditReceiver, new IntentFilter("com.demorss.reditt"));
                startLoadService();
            }
        },20000);

    }

    @Override
    public void onResume() {
        super.onResume();
        registerReciver();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                startLoadService();
            }
        };
        mTimer.schedule(timerTask, 0, 1000 * 60);
    }

    private void startLoadService() {

        Intent intent = new Intent(getActivity(), TimerService.class);
        intent.putExtra("urlLink", urlLink);
        intent.putExtra("isWhich", "reditt");
        getActivity().startService(intent);
    }

    public class RedditReceiver extends BroadcastReceiver {
        public RedditReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            swipeRefreshLayout.setRefreshing(false);
            mFeedModelList = (List<RssFeedModel>) intent.getSerializableExtra("data");
            recycleView.setAdapter(new RedditAdapter(mFeedModelList));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(redditReceiver!=null)
        getActivity().unregisterReceiver(redditReceiver);
    }
}
