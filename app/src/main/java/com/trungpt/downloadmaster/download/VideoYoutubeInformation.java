package com.trungpt.downloadmaster.download;


import com.trungpt.downloadmaster.download.parser.YouTubeParser;
import com.trungpt.downloadmaster.download.parser.YoutubeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung on 10/31/2015.
 */
public class VideoYoutubeInformation
{
    private List<YouTubeParser.VideoDownload> list = new ArrayList<>();
    private YoutubeInfo youtubeInfo;

    public VideoYoutubeInformation(List<YouTubeParser.VideoDownload> list, YoutubeInfo youtubeInfo)
    {
        this.list = list;
        this.youtubeInfo = youtubeInfo;
    }

    public List<YouTubeParser.VideoDownload> getList()
    {
        return list;
    }

    public void setList(List<YouTubeParser.VideoDownload> list)
    {
        this.list = list;
    }

    public YoutubeInfo getYoutubeInfo()
    {
        return youtubeInfo;
    }

    public void setYoutubeInfo(YoutubeInfo youtubeInfo)
    {
        this.youtubeInfo = youtubeInfo;
    }
}
