package com.demorss.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.demorss.BaseActivity;
import com.demorss.R;
import com.demorss.utils.PrefConnect;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * .
 * Created by DAYANITHI on 04-07-2017.
 */

public class LoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {
    Button loginButton;
    GoogleSignInOptions gso;
    GoogleApiClient mGoogleApiClient;
    public static final int RC_SIGN_IN = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadGoogleOptions();
        initView();
        setlistener();
    }

    private void loadGoogleOptions() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void initView() {
        loginButton = (Button) findViewById(R.id.btnLogin);
    }

    private void setlistener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }


        });
    }

    private void login() {


        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            showToast("Login Success");
            PrefConnect.writeBoolean(this, PrefConnect.isLoggedIn, true);
            Intent intent = new Intent(LoginActivity.this, SettingsActivity.class);
            startActivity(intent);
        } else {
            // Signed out, show unauthenticated UI.
            PrefConnect.writeBoolean(this, PrefConnect.isLoggedIn, false);

            showToast("Failed Try Again");
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if(connectionResult!=null)
        showToast(connectionResult.getErrorMessage());
    }
}
