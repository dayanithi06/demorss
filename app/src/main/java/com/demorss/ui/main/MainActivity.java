package com.demorss.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.demorss.BaseActivity;
import com.demorss.R;
import com.demorss.fragment.RedditFragment;
import com.demorss.fragment.YahooFragment;
import com.demorss.ui.SettingsActivity;
import com.demorss.utils.PrefConnect;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    MyPageAdapter pageAdapter;
    ViewPager pager;
    List<Fragment> fragments;
    boolean isRedditSubscribed = false,
            isYahooSubscribed = false;
    TabLayout tab_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadPrefs();
        initViews();
        fragments = getFragments();
        setAdapter();
        setListner();
    }

    private void setListner() {
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));
        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void loadPrefs() {
        isRedditSubscribed = PrefConnect.readBoolean(this, PrefConnect.isRedditSubscribed, false);
        isYahooSubscribed = PrefConnect.readBoolean(this, PrefConnect.isYahooSubscribed, false);
    }

    private void initViews() {
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(0);
        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        tab_layout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void setAdapter() {
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(pageAdapter);
    }


    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();
        if (isYahooSubscribed) {
            YahooFragment fragment = new YahooFragment();
            tab_layout.addTab(tab_layout.newTab().setText("Yahoo"));
            fList.add(fragment);
        }
        if (isRedditSubscribed) {
            RedditFragment fragment = new RedditFragment();
            tab_layout.addTab(tab_layout.newTab().setText("Reddit"));
            fList.add(fragment);

        }


        return fList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
