package com.trungpt.downloadmaster;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.*;
import butterknife.Bind;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.trungpt.downloadmaster.common.BaseActivity;
import com.trungpt.downloadmaster.common.BaseFragment;
import com.trungpt.downloadmaster.common.StringUtils;
import com.trungpt.downloadmaster.common.event.ChangedFragmentEvent;
import com.trungpt.downloadmaster.ui.*;
import com.trungpt.downloadmaster.ui.listener.OnBackPressedListener;
import com.trungpt.downloadmaster.ui.model.VideoItem;
import com.trungpt.downloadmaster.ui.sync.vimeo.VimeoConnector;
import com.trungpt.downloadmaster.ui.sync.youtube.YoutubeConnector;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends BaseActivity
{
    @Bind(R.id.activity_main_viewpager)
    ViewPager viewPager;

    @Bind(R.id.activity_main_tab)
    ViewGroup flTabContainer;
    private Tracker mTracker;
    FragmentPagerItems pages;
    FragmentPagerItemAdapter adapter;
    protected OnBackPressedListener onBackPressedListener;

    @Override
    public void setDataToView(Bundle savedInstanceState)
    {
        MyApplication application = (MyApplication) getApplication();
        mTracker = application.getDefaultTracker();
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null)
        {
            if ("text/plain".equals(type))
            {
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                if (StringUtils.isNotEmpty(sharedText))
                {
                    String regularExpressionVimeo = "(https?://)?(www.)?(player.)?vimeo.com/([a-z]*/)*([0-9]{6,11})[?]?.*";
                    String regularExpressionVideoFile = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp|mp4|flv|mp3|WEBM|GP3))$)";
                    String regularExpressionYoutube = "(https?://)?(www\\.)?(m\\.)?(yotu\\.be/|youtube\\.com/)?((.+/)?(watch(\\?v=|.+&v=))?(v=)?)([\\w_-]{11})(&.+)?";
                    String id = null;
                    if (sharedText.matches(regularExpressionYoutube))
                    {
                        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
                        Pattern compiledPattern = Pattern.compile(pattern);
                        Matcher matcher = compiledPattern.matcher(sharedText);
                        if (matcher.find())
                        {
                            id = matcher.group();
                        }
                        final YoutubeConnector youtubeConnector = new YoutubeConnector(this);
                        final String finalId = id;
                        new AsyncTask<Void, VideoItem, VideoItem>()
                        {
                            @Override
                            protected VideoItem doInBackground(Void... params)
                            {
                                VideoItem videoItem = youtubeConnector.getVideoItem(finalId);
                                return videoItem;

                            }

                            @Override
                            protected void onPostExecute(VideoItem videoItem)
                            {
                                super.onPostExecute(videoItem);
                                Intent intent = new Intent(MainActivity.this, VideoDetailActivity.class);
                                intent.putExtra("videoItem", videoItem);
                                startActivity(intent);
                            }
                        }.execute();
                    }
                    else if (sharedText.matches(regularExpressionVimeo))
                    {
                        if (sharedText.contains("http://"))
                        {
                            sharedText = sharedText.replace("http://", "https://");
                        }
                        String vimeoIdPattern = "vimeo.com.*/(\\d+)";
                        Pattern compiledPattern = Pattern.compile(vimeoIdPattern);
                        Matcher matcher = compiledPattern.matcher(sharedText);
                        if (matcher.find())
                        {
                            id = matcher.group(1);
                        }

                        final VimeoConnector vimeoConnector = new VimeoConnector();
                        final String finalId = id;
                        new AsyncTask<Void, VideoItem, VideoItem>()
                        {
                            @Override
                            protected VideoItem doInBackground(Void... params)
                            {
                                VideoItem videoItem = vimeoConnector.getVideoById(finalId);
                                return videoItem;
                            }

                            @Override
                            protected void onPostExecute(VideoItem videoItem)
                            {
                                super.onPostExecute(videoItem);
                                Intent intent = new Intent(MainActivity.this, VideoDetailActivity.class);
                                intent.putExtra("videoItem", videoItem);
                                startActivity(intent);
                            }
                        }.execute();
                    }
                }
            }
        }
        flTabContainer.addView(LayoutInflater.from(this).inflate(R.layout.tab_container, flTabContainer, false));
        SmartTabLayout tabLayout = (SmartTabLayout) findViewById(R.id.tab_indicator_menu_viewpagertab);
        final LayoutInflater inflater = LayoutInflater.from(this);
        final Resources res = tabLayout.getContext().getResources();
        viewPager.setOffscreenPageLimit(3);
        pages = new FragmentPagerItems(this);
        pages.add(FragmentPagerItem.of("HOME", HomeFragment.class));
        pages.add(FragmentPagerItem.of("SEARCH", SearchFragment.class));
        pages.add(FragmentPagerItem.of("TASK", TaskFragment.class));
        pages.add(FragmentPagerItem.of("ABOUT", AboutFragment.class));
        adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), pages);

        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);
    }

    @Override
    public int getLayout()
    {
        return R.layout.activity_main;
    }

    public void onEventMainThread(ChangedFragmentEvent event)
    {
        BaseFragment baseFragment = event.getBaseFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.enter, R.anim.exit);
        fragmentTransaction.replace(R.id.container, baseFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed()
    {
//        FragmentManager manager = getSupportFragmentManager();
//        if (manager.getBackStackEntryCount() > 1)
//        {
//            manager.popBackStack();
//        }
//        else
//        {
//            BaseFragment fragment = (BaseFragment) adapter.getItem(viewPager.getCurrentItem());
//            FragmentManager childManager = fragment.getChildFragmentManager();
//            if (childManager.getBackStackEntryCount() > 1)
//            {
//                childManager.popBackStack();
//            }
        if (onBackPressedListener != null)
        {
            onBackPressedListener.doBack();
        }
        else
        {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface arg0, int arg1)
                        {
                            moveTaskToBack(true);
                        }
                    }).show();
        }

//        }
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener)
    {
        this.onBackPressedListener = onBackPressedListener;
    }

    public void otherFragment(int position)
    {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        AppEventsLogger.activateApp(this);
        mTracker.setScreenName("Image~" + MainActivity.class);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments())
        {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
