package com.demorss.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.demorss.BaseActivity;
import com.demorss.R;
import com.demorss.ui.main.MainActivity;
import com.demorss.utils.PrefConnect;

/**
 * Created by DAYANITHI on .04-07-2017.
 */

public class SettingsActivity extends BaseActivity {
    SwitchCompat switch_reddit,
            switch_yahoo;
    boolean reditSubscribe = false, yahooSubScribe = false;
    Button btnDone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initViews();
        setListner();

    }

    private void setListner() {
        switch_reddit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    reditSubscribe = true;
                } else {
                    reditSubscribe = false;
                }
            }
        });
        switch_yahoo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    yahooSubScribe = true;
                } else {
                    yahooSubScribe = false;
                }
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (yahooSubScribe || reditSubscribe) {
                    PrefConnect.writeBoolean(SettingsActivity.this, PrefConnect.isYahooSubscribed, yahooSubScribe);
                    PrefConnect.writeBoolean(SettingsActivity.this, PrefConnect.isRedditSubscribed, reditSubscribe);
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    showToast("Please subscribe atleaset one feed");
                }
            }
        });
    }

    private void initViews() {
        switch_reddit = (SwitchCompat) findViewById(R.id.switch_reddit);
        switch_yahoo = (SwitchCompat) findViewById(R.id.switch_yahoo);
        btnDone = (Button) findViewById(R.id.btnDone);
    }
}
