/**
 * Copyright (c) 2014-present, Facebook, Inc. All rights reserved.
 *
 * You are hereby granted a non-exclusive, worldwide, royalty-free license to use,
 * copy, modify, and distribute this software in source code or binary form for use
 * in connection with the web services and APIs provided by Facebook.
 *
 * As with any software that integrates with the Facebook platform, your use of
 * this software is subject to the Facebook Developer Principles and Policies
 * [http://developers.facebook.com/policy/]. This copyright notice shall be
 * included in all copies or substantial portions of the software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.trungpt.downloadmaster.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.facebook.*;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.melnykov.fab.FloatingActionButton;
import com.trungpt.downloadmaster.MyApplication;
import com.trungpt.downloadmaster.R;
import com.trungpt.downloadmaster.ui.adapter.FacebookFanpageAdapter;
import com.trungpt.downloadmaster.ui.model.FacebookInfo;
import com.trungpt.downloadmaster.ui.model.VimeoInfo;
import com.trungpt.downloadmaster.ui.model.YoutubeInfo;
import com.trungpt.downloadmaster.ui.sync.dailymotion.DailymotionInfo;
import com.trungpt.downloadmaster.utils.Configs;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FacebookFanpageActivty extends FragmentActivity implements TextView.OnEditorActionListener
{
    private final String PENDING_ACTION_BUNDLE_KEY =
            "com.example.hellofacebook:PendingAction";
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;

    @Bind(R.id.profilePicture)
    ProfilePictureView profilePictureView;
    @Bind(R.id.greeting)
    TextView greeting;
    @Bind(R.id.search_fragment_edtSearch)
    EditText edtSearch;
    @Bind(R.id.facebook_fanpage_activity_frContainer)
    FrameLayout frContainer;
    @Bind(R.id.search_fragment_rlSearch)
    RelativeLayout rlSearch;
    FacebookFanpageFragment facebookFanpageFragment;
    FacebookInfo facebookInfo;
    private Tracker mTracker;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        MyApplication application = (MyApplication) getApplication();
        mTracker = application.getDefaultTracker();
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>()
                {
                    @Override
                    public void onSuccess(LoginResult loginResult)
                    {
                        updateUI();
                    }

                    @Override
                    public void onCancel()
                    {
                        updateUI();
                    }

                    @Override
                    public void onError(FacebookException exception)
                    {
                        updateUI();
                    }

                    private void showAlert()
                    {
                        new AlertDialog.Builder(FacebookFanpageActivty.this)
                                .setTitle(R.string.cancelled)
                                .setMessage(R.string.permission_not_granted)
                                .setPositiveButton(R.string.ok, null)
                                .show();
                    }
                });
        setContentView(R.layout.facebook_fanpage_activity);
        facebookFanpageFragment = new FacebookFanpageFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.facebook_fanpage_activity_frContainer, facebookFanpageFragment);
        transaction.commit();
        ButterKnife.bind(this);
        profileTracker = new ProfileTracker()
        {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile)
            {
                updateUI();
            }
        };
        edtSearch.setOnEditorActionListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        AppEventsLogger.activateApp(this);
        updateUI();
        super.onResume();
        mTracker.setScreenName("Image~" + FacebookFanpageActivty.class);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999)
        {
            finish();
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        profileTracker.stopTracking();
    }

    private void updateUI()
    {
        boolean enableButtons = AccessToken.getCurrentAccessToken() != null;
        Profile profile = Profile.getCurrentProfile();
        if (enableButtons && profile != null)
        {
            profilePictureView.setProfileId(profile.getId());
            greeting.setText(getString(R.string.hello_user, profile.getFirstName()));
        }
        else
        {
            profilePictureView.setProfileId(null);
            greeting.setText(null);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        switch (v.getId())
        {
            case R.id.search_fragment_edtSearch:
                if (facebookFanpageFragment != null)
                {
                    facebookInfo = new FacebookInfo(edtSearch.getText().toString(), "");
                    facebookFanpageFragment.reload(facebookInfo);
                }

                break;
        }
        return false;
    }
}
