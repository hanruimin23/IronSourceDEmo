package com.ironsource.ironsourcesdkdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.InitializationListener;
import com.ironsource.mediationsdk.sdk.InterstitialListener;

public class DemoActivity extends AppCompatActivity implements InterstitialListener {
    private final String TAG = "DemoActivity";
    private final String APP_KEY = "85460dcd";
    private Button mInterstitialShowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        IntegrationHelper.validateIntegration(this);
        initUIElements();
        startIronSourceInitTask();
        IronSource.getAdvertiserId(this);
        //Network Connectivity Status
        IronSource.shouldTrackNetworkState(this, true);
        loadInterstitialAction();
    }

    private void startIronSourceInitTask(){
        String advertisingId = IronSource.getAdvertiserId(DemoActivity.this);
        // we're using an advertisingId as the 'userId'
        initIronSource(APP_KEY, advertisingId);
    }


    private void initIronSource(String appKey, String userId) {
        // Be sure to set a listener to each product that is being initiated
        IronSource.setInterstitialListener(this);
        // set the IronSource user id
        IronSource.setUserId(userId);
        // init the IronSource SDK
        IronSource.init(this, appKey, new InitializationListener() {
            @Override
            public void onInitializationComplete() {
                System.out.println("ironSource SDK is successful initialized");
            }
        });
    }

    private void initUIElements() {
        mInterstitialShowButton = (Button) findViewById(R.id.is_button_1);
        mInterstitialShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IronSource.isInterstitialReady()) {
                    //show the interstitial
                    IronSource.showInterstitial();
                }
            }
        });
    }

    private void loadInterstitialAction() {
        IronSource.loadInterstitial();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // call the IronSource onResume method
        IronSource.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // call the IronSource onPause method
        IronSource.onPause(this);
    }

    // --------- IronSource Interstitial Listener ---------
    @Override
    public void onInterstitialAdClicked() {
        // called when the interstitial has been clicked
        Log.d(TAG, "onInterstitialAdClicked");
    }

    @Override
    public void onInterstitialAdReady() {
        // called when the interstitial is ready
        Log.d(TAG, "onInterstitialAdReady");
    }

    @Override
    public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {
        // called when the interstitial has failed to load
        // you can get the error data by accessing the IronSourceError object
        // IronSourceError.getErrorCode();
        // IronSourceError.getErrorMessage();
        Log.d(TAG, "onInterstitialAdLoadFailed" + " " + ironSourceError);
    }

    @Override
    public void onInterstitialAdOpened() {
        // called when the interstitial is shown
        Log.d(TAG, "onInterstitialAdOpened");
    }

    @Override
    public void onInterstitialAdClosed() {
        // called when the interstitial has been closed
        Log.d(TAG, "onInterstitialAdClosed");
    }

    @Override
    public void onInterstitialAdShowSucceeded() {
        // called when the interstitial has been successfully shown
        Log.d(TAG, "onInterstitialAdShowSucceeded");
    }

    @Override
    public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {
        // called when the interstitial has failed to show
        // you can get the error data by accessing the IronSourceError object
        // IronSourceError.getErrorCode();
        // IronSourceError.getErrorMessage();
        Log.d(TAG, "onInterstitialAdShowFailed" + " " + ironSourceError);
    }
}