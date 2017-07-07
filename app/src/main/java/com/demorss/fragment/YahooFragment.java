package com.demorss.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demorss.R;
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

public class YahooFragment extends Fragment {
    private String urlLink = "https://sports.yahoo.com/top/rss.xml";
    private List<RssFeedModel> mFeedModelList;
    RecyclerView recycleView;
    View convertView;
    Timer mTimer = new Timer();
    YahooReceiver receiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.fragment_yahoo, container, false);
        intitViews(convertView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(manager);

        return convertView;
    }



    private void intitViews(View convertView) {
        recycleView = (RecyclerView) convertView.findViewById(R.id.recycleView);
    }

    @Override
    public void onResume() {
        super.onResume();
        receiver = new YahooReceiver();
        startLoadService();
        getActivity().registerReceiver(receiver, new IntentFilter("com.demorss.yahoo"));
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
        intent.putExtra("isWhich", "yahoo");
        getActivity().startService(intent);
    }

    public class YahooReceiver extends BroadcastReceiver {
        public YahooReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            setAdapter(intent);

        }
    }

    public void setAdapter(Intent intent) {
        mFeedModelList = (List<RssFeedModel>) intent.getSerializableExtra("data");
        recycleView.setAdapter(new YahooAdapter(mFeedModelList));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }
}
