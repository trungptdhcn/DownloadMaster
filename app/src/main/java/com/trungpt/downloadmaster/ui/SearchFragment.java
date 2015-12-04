package com.trungpt.downloadmaster.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.rey.material.widget.Spinner;
import com.trungpt.downloadmaster.R;
import com.trungpt.downloadmaster.common.BaseFragment;
import com.trungpt.downloadmaster.ui.adapter.HostNameAdapter;
import com.trungpt.downloadmaster.ui.model.FacebookInfo;
import com.trungpt.downloadmaster.ui.model.VimeoInfo;
import com.trungpt.downloadmaster.ui.model.YoutubeInfo;
import com.trungpt.downloadmaster.ui.sync.dailymotion.DailymotionInfo;
import com.trungpt.downloadmaster.utils.Configs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Trung on 11/15/2015.
 */
public class SearchFragment extends BaseFragment implements TextView.OnEditorActionListener
{
    @Bind(R.id.search_fragment_frContainer)
    FrameLayout frContainer;
    @Bind(R.id.search_fragment_spinnerChoseHost)
    Spinner spChoseHost;
    @Bind(R.id.search_fragment_rlSearch)
    RelativeLayout rlSearch;
    @Bind(R.id.search_fragment_edtSearch)
    EditText edtSearch;
    BaseFragment youtubeSearchFragment;
    BaseFragment vimeoSearchFragment;
    BaseFragment dailymotionFragment;
    BaseFragment facebookFragment;
    private HostNameAdapter hostNameAdapter;
    YoutubeInfo youtubeInfo;
    VimeoInfo vimeoInfo;
    DailymotionInfo dailymotionInfo;
    FacebookInfo facebookInfo;
    private Configs.HOST_NAME host_name;

    @Override
    public int getLayout()
    {
        return R.layout.search_fragment;
    }

    @Override
    public void setDataToView(Bundle savedInstanceState)
    {
        hostNameAdapter = new HostNameAdapter(getActivity(),
                R.layout.spinner_item);
        hostNameAdapter.setDropDownViewResource(R.layout.spinner_selector_item);
        spChoseHost.setAdapter(hostNameAdapter);
        spChoseHost.setSelection(0);
        filterDataByHost();
        spChoseHost.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(Spinner spinner, View view, int i, long l)
            {
                filterDataByHost();
            }
        });
        edtSearch.setOnEditorActionListener(this);
    }

    private void filterDataByHost()
    {
        switch ((Configs.HOST_NAME) spChoseHost.getSelectedItem())
        {
            case YOUTUBE:
                host_name = Configs.HOST_NAME.YOUTUBE;
                rlSearch.setBackgroundColor(getResources().getColor(R.color.red_simi_press));
                youtubeSearchFragment = new YoutubeSearchFragment();
                FragmentTransaction transactionYoutube = getChildFragmentManager().beginTransaction();
                transactionYoutube.replace(R.id.search_fragment_frContainer, youtubeSearchFragment);
                transactionYoutube.commit();
                break;
            case VIMEO:
                host_name = Configs.HOST_NAME.VIMEO;
                rlSearch.setBackgroundColor(getResources().getColor(R.color.blue_vimeo));
                vimeoSearchFragment = new VimeoSearchFragment();
                FragmentTransaction transactionVimeo = getChildFragmentManager().beginTransaction();
                transactionVimeo.replace(R.id.search_fragment_frContainer, vimeoSearchFragment);
                transactionVimeo.commit();
                break;
            case DAILYMOON:
                host_name = Configs.HOST_NAME.DAILYMOON;
                rlSearch.setBackgroundColor(getResources().getColor(R.color.gray_21));
                dailymotionFragment = new DailymotionSearchFragment();
                FragmentTransaction transactionDailymotion = getChildFragmentManager().beginTransaction();
                transactionDailymotion.replace(R.id.search_fragment_frContainer, dailymotionFragment);
                transactionDailymotion.commit();
                break;
            case FACEBOOK:
//                host_name = Configs.HOST_NAME.FACEBOOK;
//                rlSearch.setBackgroundColor(getResources().getColor(R.color.blue_facebook));
//                facebookFragment = new FacebookFanpageFragment();
//                FragmentTransaction transaction4 = getChildFragmentManager().beginTransaction();
//                transaction4.replace(R.id.search_fragment_frContainer, facebookFragment);
//                transaction4.commit();
                startActivity(new Intent(getActivity().getApplicationContext(),FacebookFanpageActivty.class));
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        switch (v.getId())
        {
            case R.id.search_fragment_edtSearch:
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    if (host_name == Configs.HOST_NAME.YOUTUBE)
                    {
                        if (youtubeSearchFragment != null)
                        {
                            String keyword = edtSearch.getText().toString();
                            String id = null;
                            String regularExpressionYoutube = "(https?://)?(www\\.)?(m\\.)?(yotu\\.be/|youtube\\.com/)?((.+/)?(watch(\\?v=|.+&v=))?(v=)?)([\\w_-]{11})(&.+)?";
                            if (keyword.matches(regularExpressionYoutube))
                            {
                                {
                                    Pattern u1 = Pattern.compile("youtube.com/watch?.*v=([^&]*)");
                                    Matcher um = u1.matcher(keyword);
                                    if (um.find())
                                    {
                                        id = um.group(1);
                                    }
                                }

                                {
                                    Pattern u2 = Pattern.compile("youtube.com/v/([^&]*)");
                                    Matcher um = u2.matcher(keyword);
                                    if (um.find())
                                    {
                                        id = um.group(1);
                                    }
                                }

                            }
                            youtubeInfo = new YoutubeInfo
                                    .YoutubeBuilder(edtSearch.getText().toString())
                                    .id(id)
                                    .type("video")
                                    .build();
                            youtubeSearchFragment.reload(youtubeInfo);
                        }
                    }
                    else if (host_name == Configs.HOST_NAME.DAILYMOON)
                    {
                        dailymotionInfo = new DailymotionInfo
                                .DailymotionBuilder(edtSearch.getText().toString())
                                .fields("title,channel,country,description,duration,id,poster,thumbnail_720_url,url,live_publish_url")
                                .flags("no_live,no_premium")
                                .sort("visited")
                                .page(1l)
                                .limit(10l)
                                .build();
                        dailymotionFragment.reload(dailymotionInfo);
                    }
                    else if(host_name == Configs.HOST_NAME.VIMEO)
                    {
                        vimeoInfo = new VimeoInfo
                                .VimeoBuilder(edtSearch.getText().toString())
                                .build();
                        vimeoSearchFragment.reload(vimeoInfo);
                    }
//                    else
//                    {
////                        facebookInfo = new FacebookInfo(edtSearch.getText().toString(),"");
////                        facebookFragment.reload(facebookInfo);
//                    }
                }
                break;
        }
        return false;
    }


}
