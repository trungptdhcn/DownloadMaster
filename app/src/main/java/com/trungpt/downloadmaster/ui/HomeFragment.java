package com.trungpt.downloadmaster.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.Bind;
import com.rey.material.widget.Spinner;
import com.trungpt.downloadmaster.R;
import com.trungpt.downloadmaster.common.BaseFragment;
import com.trungpt.downloadmaster.ui.adapter.CountryCodeAdapter;
import com.trungpt.downloadmaster.ui.adapter.DailymotionCategoryAdapter;
import com.trungpt.downloadmaster.ui.adapter.HostNameAdapter;
import com.trungpt.downloadmaster.ui.adapter.VimeoCategoryAdapter;
import com.trungpt.downloadmaster.ui.model.CountryCode;
import com.trungpt.downloadmaster.ui.model.DailymotionCategory;
import com.trungpt.downloadmaster.ui.model.VimeoCategory;
import com.trungpt.downloadmaster.utils.Configs;

/**
 * Created by Trung on 11/11/2015.
 */
public class HomeFragment extends BaseFragment
{
    @Bind(R.id.youtube_list_header_spinnerChoseHost)
    Spinner spHostName;
    @Bind(R.id.youtube_list_header_spYoutubeOption)
    Spinner spYoutubeOption;
    @Bind(R.id.youtube_list_header_spVimeoOption)
    Spinner spVimeoOption;
    @Bind(R.id.youtube_list_header_spDailymotionOption)
    Spinner spDailymotionOption;
    @Bind(R.id.youtube_list_header)
    RelativeLayout rlHeader;
    @Bind(R.id.youtube_list_header_ivLogo)
    ImageView ivLogo;
    HostNameAdapter hostNameAdapter;
    CountryCodeAdapter countryCodeAdapter;
    VimeoCategoryAdapter vimeoCategoryAdapter;
    DailymotionCategoryAdapter dailymotionCategoryAdapter;
    BaseFragment youtubeFragmentFragment;
    BaseFragment vimeoFragmentFragment;
    BaseFragment dailymotionFragment;
    BaseFragment facebookFragment;

    @Override
    public int getLayout()
    {
        return R.layout.home_fragment;
    }

    @Override
    public void setDataToView(Bundle savedInstanceState)
    {
        hostNameAdapter = new HostNameAdapter(getActivity(),
                R.layout.spinner_item);
        hostNameAdapter.setDropDownViewResource(R.layout.spinner_selector_item);
        spHostName.setAdapter(hostNameAdapter);
        spHostName.setSelection(0);
        filterDataByHost();
        spHostName.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(Spinner spinner, View view, int i, long l)
            {
                filterDataByHost();
            }
        });
    }

    public void filterDataByHost()
    {
        switch ((Configs.HOST_NAME) spHostName.getSelectedItem())
        {
            case YOUTUBE:
                ivLogo.setImageResource(R.drawable.youtube_logo);
                rlHeader.setBackgroundColor(getResources().getColor(R.color.red_simi_press));
                spYoutubeOption.setVisibility(View.VISIBLE);
                spVimeoOption.setVisibility(View.GONE);
                spDailymotionOption.setVisibility(View.GONE);
                countryCodeAdapter = new CountryCodeAdapter(getActivity(),
                        R.layout.spinner_item);
                countryCodeAdapter.setDropDownViewResource(R.layout.spinner_selector_item);
                spYoutubeOption.setAdapter(countryCodeAdapter);
                spYoutubeOption.setSelection(195);

                youtubeFragmentFragment = new YoutubeFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.home_fragment_frYoutube, youtubeFragmentFragment);
                transaction.commit();

                spYoutubeOption.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(Spinner spinner, View view, int i, long l)
                    {
                        youtubeFragmentFragment.reload(((CountryCode) spinner.getSelectedItem()).getCode());
                    }
                });
                break;
            case VIMEO:
                ivLogo.setImageResource(R.drawable.vimeo_logo);
                rlHeader.setBackgroundColor(getResources().getColor(R.color.blue_vimeo));
                spYoutubeOption.setVisibility(View.GONE);
                spDailymotionOption.setVisibility(View.GONE);
                spVimeoOption.setVisibility(View.VISIBLE);
                vimeoCategoryAdapter = new VimeoCategoryAdapter(getActivity(),
                        R.layout.spinner_item);
                vimeoCategoryAdapter.setDropDownViewResource(R.layout.spinner_selector_item);
                spVimeoOption.setAdapter(vimeoCategoryAdapter);
                spVimeoOption.setSelection(0);

                vimeoFragmentFragment = new VimeoFragment();
                FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
                transaction2.replace(R.id.home_fragment_frYoutube, vimeoFragmentFragment);
                transaction2.commit();
                spVimeoOption.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(Spinner spinner, View view, int i, long l)
                    {
                        vimeoFragmentFragment.reload(((VimeoCategory) spinner.getSelectedItem()).getId());
                    }
                });
                break;
            case DAILYMOON:
                ivLogo.setImageResource(R.drawable.dailymotion_logo);
                rlHeader.setBackgroundColor(getResources().getColor(R.color.gray_21));
                spYoutubeOption.setVisibility(View.GONE);
                spVimeoOption.setVisibility(View.GONE);
                spDailymotionOption.setVisibility(View.VISIBLE);
                dailymotionCategoryAdapter = new DailymotionCategoryAdapter(getActivity(),
                        R.layout.spinner_item);
                dailymotionCategoryAdapter.setDropDownViewResource(R.layout.spinner_selector_item);
                spDailymotionOption.setAdapter(dailymotionCategoryAdapter);
                spDailymotionOption.setSelection(0);

                dailymotionFragment = new DailymotionFragment();
                FragmentTransaction transaction3 = getChildFragmentManager().beginTransaction();
                transaction3.replace(R.id.home_fragment_frYoutube, dailymotionFragment);
                transaction3.commit();
                spDailymotionOption.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(Spinner spinner, View view, int i, long l)
                    {
                        dailymotionFragment.reload(((DailymotionCategory) spinner.getSelectedItem()).getId());
                    }
                });
                break;
            case FACEBOOK:
                startActivity(new Intent(getActivity().getApplicationContext(), FacebookFanpageActivty.class));
                break;


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getChildFragmentManager().getFragments())
        {
            if (fragment != null)
            {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }

    }
}
