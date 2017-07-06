package com.demorss.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.demorss.BaseActivity;
import com.demorss.R;
import com.demorss.fragment.RedditFragment;
import com.demorss.fragment.YahooFragment;
import com.demorss.utils.PrefConnect;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    MyPageAdapter pageAdapter;
    ViewPager pager;
    List<Fragment> fragments;
    boolean isRedditSubscribed = false,
            isYahooSubscribed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadPrefs();
        fragments = getFragments();
        initViews();
        setAdapter();
    }

    private void loadPrefs() {
        PrefConnect.readBoolean(this, PrefConnect.isRedditSubscribed, false);
        PrefConnect.readBoolean(this, PrefConnect.isYahooSubscribed, false);
    }

    private void initViews() {
        pager = (ViewPager) findViewById(R.id.pager);
    }

    private void setAdapter() {
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(pageAdapter);
    }


    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();
        if (isRedditSubscribed) {
            RedditFragment fragment = new RedditFragment();

            fList.add(fragment);

        }
        if (isYahooSubscribed) {
            YahooFragment fragment = new YahooFragment();

            fList.add(fragment);
        }

        return fList;
    }
}
