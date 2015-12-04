package com.trungpt.downloadmaster.ui.adapter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.trungpt.downloadmaster.R;
import com.trungpt.downloadmaster.ui.VideoDetailActivity;
import com.trungpt.downloadmaster.ui.YoutubeFragment;
import com.trungpt.downloadmaster.ui.model.VideoItem;
import com.trungpt.downloadmaster.utils.Configs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung on 11/11/2015.
 */
public class CommonAdapter extends BaseAdapter
{
    private List<VideoItem> videoItems = new ArrayList<>();
    private FragmentActivity activity;
    private Fragment fragment;

    public CommonAdapter(List<VideoItem> videoItems, FragmentActivity activity)
    {
        this.videoItems = videoItems;
        this.activity = activity;
    }

    @Override
    public int getCount()
    {
        return videoItems.size();
    }

    @Override
    public Object getItem(int position)
    {
        return videoItems.get(position);
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
        final VideoItem videoItem = videoItems.get(position);
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
        holder.title.setText(videoItem.getTitle());
        Glide.with(activity)
                .load(videoItem.getUrlThumbnail())
                .centerCrop()
                .placeholder(R.drawable.no_thumbnail)
                .crossFade()
                .into(holder.ivThumbnail);
        holder.rlContainer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(activity, VideoDetailActivity.class);
                intent.putExtra("videoItem", videoItem);
                if (videoItem.getHost_name().equals(Configs.HOST_NAME.FACEBOOK))
                {
                    activity.startActivityForResult(intent, 999);
                }
                else
                {
                    activity.startActivity(intent);
                }
            }
        });
        holder.ivThumbnail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                YouTubeInitializationResult errorReason =
                        YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(activity);
//                if (errorReason.isUserRecoverableError()) {
//                    errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
//                } else if (errorReason != YouTubeInitializationResult.SUCCESS) {
//                    String errorMessage =
//                            String.format(getString(R.string.error_player), errorReason.toString());
//                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
//                }
//                YoutubeFragment.YoutubeVideoFragment youtubeVideoFragment =
//                        (YoutubeFragment.YoutubeVideoFragment) fragment.getChildFragmentManager().findFragmentById(R.id.video_fragment_container);
//                youtubeVideoFragment.setVideoId(videoItem.getId());
            }
        });
        return convertView;
    }

    public List<VideoItem> getVideoItems()
    {
        return videoItems;
    }

    public void setVideoItems(List<VideoItem> videoItems)
    {
        this.videoItems = videoItems;
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
