package com.demorss.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.demorss.BaseActivity;
import com.demorss.R;

/**
 * Created by DAYANITHI on .04-07-2017.
 */

public class SettingsActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initViews();

    }

    private void initViews() {

    }
}
