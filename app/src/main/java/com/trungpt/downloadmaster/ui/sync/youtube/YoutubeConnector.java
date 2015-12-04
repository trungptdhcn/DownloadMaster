package com.trungpt.downloadmaster.ui.sync.youtube;

import android.content.Context;
import android.util.Log;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.trungpt.downloadmaster.R;
import com.trungpt.downloadmaster.ui.model.PageVideosInfo;
import com.trungpt.downloadmaster.ui.model.VideoItem;
import com.trungpt.downloadmaster.ui.model.YoutubeInfo;
import com.trungpt.downloadmaster.utils.Configs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung on 11/11/2015.
 */
public class YoutubeConnector
{
    private YouTube youtube;
    private YouTube.Search.List searchQuery;
    private YouTube.Videos.List mostPopularQuery;
    public static final String KEY
            = "AIzaSyCOjGPIXGYa0QIhW74WYx4vIWtjZXqXOXE";
    public static long MAX_RESULT = 10l;
    public static int START_ORDER = 0;

    public YoutubeConnector(Context context)
    {
        YouTube.Videos.List y;
        youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer()
        {
            @Override
            public void initialize(HttpRequest hr) throws IOException
            {
            }
        }).setApplicationName(context.getString(R.string.app_name)).build();

        try
        {
            mostPopularQuery = youtube.videos().list("id,snippet");
            mostPopularQuery.setKey(KEY);
            mostPopularQuery.setRegionCode("vn");
            mostPopularQuery.setMaxResults(30l);
        }
        catch (IOException e)
        {
            Log.d("YC", "Could not initialize query most popular: " + e);
        }

        try
        {
            searchQuery = youtube.search().list("id,snippet");
            searchQuery.setKey(KEY);
            searchQuery.setType("video");
//            searchQuery.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
        }
        catch (IOException e)
        {
            Log.d("YC", "Could not initialize search: " + e);
        }
    }

    public VideoItem getVideoItem(String id)
    {
        mostPopularQuery.setId(id);
        try
        {
            VideoListResponse response = mostPopularQuery.execute();
            List<Video> results = response.getItems();
            VideoItem item = new VideoItem();
            for (Video result : results)
            {
                item.setTitle(result.getSnippet().getTitle());
                item.setDescription(result.getSnippet().getDescription());
                item.setUrlThumbnail(result.getSnippet().getThumbnails().getMedium().getUrl());
                item.setUrl("https://www.youtube.com/watch?v=" + result.getId());
                item.setId(result.getId());
                item.setHost_name(Configs.HOST_NAME.YOUTUBE);
            }
//            PageVideosInfo pageVideosInfo = new PageVideosInfo(items);
            return item;
        }
        catch (IOException e)
        {
            Log.d("YC", "Could not search: " + e);
            return null;
        }
    }

    public PageVideosInfo search(YoutubeInfo youtubeInfo)
    {
//        searchQuery.setQ(youtubeInfo.getKeyWord());
        try
        {
            searchQuery.setQ(youtubeInfo.getKeyWord());
            searchQuery.setLocation(youtubeInfo.getLocation());
            searchQuery.setOrder(youtubeInfo.getOrder());
            searchQuery.setRegionCode(youtubeInfo.getRegionCode());
            searchQuery.setTopicId(youtubeInfo.getTopic());
            searchQuery.setType(youtubeInfo.getType());
            searchQuery.setVideoDefinition(youtubeInfo.getVideoDefinition());
            searchQuery.setVideoDimension(youtubeInfo.getVideoDimension());
            searchQuery.setVideoDuration(youtubeInfo.getVideoDuration());
            searchQuery.setVideoLicense(youtubeInfo.getVideoLicense());
            searchQuery.setVideoType(youtubeInfo.getVideoType());
            searchQuery.setMaxResults(MAX_RESULT);
            searchQuery.setPageToken(youtubeInfo.getPageToken());

            SearchListResponse response = searchQuery.execute();
            List<SearchResult> results = response.getItems();

            List<VideoItem> items = new ArrayList<VideoItem>();
            for (SearchResult result : results)
            {
                VideoItem item = new VideoItem();
                item.setTitle(result.getSnippet().getTitle());
                item.setDescription(result.getSnippet().getDescription());
                item.setUrlThumbnail(result.getSnippet().getThumbnails().getMedium().getUrl());
                item.setUrl("https://www.youtube.com/watch?v=" + result.getId().getVideoId());
                item.setHost_name(Configs.HOST_NAME.YOUTUBE);
                items.add(item);
            }
            PageVideosInfo pageVideosInfo = new PageVideosInfo(items);
            if (response.getPageInfo() != null)
            {
                if (response.getPageInfo().getTotalResults() < response.getPageInfo().getResultsPerPage())
                {
                    pageVideosInfo.setNextpage(false);
                }
                pageVideosInfo.setNextPageToken(response.getNextPageToken());
            }
            return pageVideosInfo;
        }
        catch (IOException e)
        {
            Log.d("YC", "Could not search: " + e);
            return null;
        }
    }

    public PageVideosInfo mostPopular(String regionCode)
    {
        mostPopularQuery.setChart(Configs.MOST_POPULAR);
        mostPopularQuery.setRegionCode(regionCode);
        try
        {
            VideoListResponse response = mostPopularQuery.execute();
            List<Video> results = response.getItems();
            List<VideoItem> items = new ArrayList<VideoItem>();
            for (Video result : results)
            {
                VideoItem item = new VideoItem();
                item.setTitle(result.getSnippet().getTitle());
                item.setDescription(result.getSnippet().getDescription());
                item.setUrlThumbnail(result.getSnippet().getThumbnails().getMedium().getUrl());
                item.setUrl("https://www.youtube.com/watch?v=" + result.getId());
                item.setId(result.getId());
                item.setHost_name(Configs.HOST_NAME.YOUTUBE);
                items.add(item);
            }
            PageVideosInfo pageVideosInfo = new PageVideosInfo(items);
            return pageVideosInfo;
        }
        catch (IOException e)
        {
            Log.d("YC", "Could not search: " + e);
            return null;
        }
    }
}
