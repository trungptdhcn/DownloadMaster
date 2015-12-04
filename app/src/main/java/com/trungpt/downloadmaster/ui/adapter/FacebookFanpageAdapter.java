package com.trungpt.downloadmaster.ui.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.trungpt.downloadmaster.R;
import com.trungpt.downloadmaster.ui.FacebookVideoFragment;
import com.trungpt.downloadmaster.ui.VideoDetailActivity;
import com.trungpt.downloadmaster.ui.model.VideoItem;
import com.trungpt.downloadmaster.ui.sync.facebook.FacebookFanpage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung on 11/22/2015.
 */
public class FacebookFanpageAdapter extends BaseAdapter
{

    private List<FacebookFanpage> facebookFanpages = new ArrayList<>();
    private FragmentActivity activity;
    private Fragment fragment;

    public FacebookFanpageAdapter(List<FacebookFanpage> facebookFanpages, FragmentActivity activity)
    {
        this.facebookFanpages = facebookFanpages;
        this.activity = activity;
    }

    @Override
    public int getCount()
    {
        return facebookFanpages.size();
    }

    @Override
    public Object getItem(int position)
    {
        return facebookFanpages.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        final FacebookFanpage facebookFanpage = facebookFanpages.get(position);
        LayoutInflater inflater = activity.getLayoutInflater();
        if (convertView != null)
        {
            holder = (ViewHolder) convertView.getTag();
        }
        else
        {
            convertView = inflater.inflate(R.layout.youtube_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.title.setText(facebookFanpage.getName());
        holder.tvDes.setText(facebookFanpage.getDescription());
        Glide.with(activity)
                .load(facebookFanpage.getFacebookFanpagePicture().getFanpagePictureData().getUrl())
                .centerCrop()
                .placeholder(R.drawable.no_thumbnail)
                .crossFade()
                .into(holder.ivThumbnail);
        holder.rlContainer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Bundle bundle = new Bundle();
                bundle.putString("page_id",facebookFanpage.getId());
                FacebookVideoFragment facebookFragment = new FacebookVideoFragment();
                facebookFragment.setArguments(bundle);
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.facebook_fanpage_activity_frContainer, facebookFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return convertView;
    }

    public List<FacebookFanpage> getFacebookFanpage()
    {
        return facebookFanpages;
    }

    public void setFacebookFanpage(List<FacebookFanpage> facebookFanpages)
    {
        this.facebookFanpages = facebookFanpages;
    }

    public Fragment getFragment()
    {
        return fragment;
    }

    public void setFragment(Fragment fragment)
    {
        this.fragment = fragment;
    }

    static class ViewHolder
    {
        @Bind(R.id.youtube_list_item_tvTitle)
        TextView title;
        @Bind(R.id.youtube_list_item_tvDes)
        TextView tvDes;
        @Bind(R.id.youtube_list_item_ivImage)
        ImageView ivThumbnail;
        @Bind(R.id.youtube_list_item_rlContainer)
        RelativeLayout rlContainer;

        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }

}
