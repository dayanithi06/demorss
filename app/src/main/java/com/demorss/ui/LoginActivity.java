package com.demorss.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.demorss.BaseActivity;
import com.demorss.R;

/**.
 * Created by DAYANITHI on 04-07-2017.
 */

public class LoginActivity extends BaseActivity {
    Button loginButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setlistener();
    }

    private void initView() {
        loginButton =(Button)findViewById(R.id.btnLogin);
    }

    private void setlistener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
