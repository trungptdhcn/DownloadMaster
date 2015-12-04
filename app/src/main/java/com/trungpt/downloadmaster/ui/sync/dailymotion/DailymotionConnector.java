package com.trungpt.downloadmaster.ui.sync.dailymotion;

import com.trungpt.downloadmaster.ui.model.PageVideosInfo;
import com.trungpt.downloadmaster.ui.model.VideoItem;
import com.trungpt.downloadmaster.ui.sync.common.RestfulService;
import com.trungpt.downloadmaster.utils.Configs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung on 11/19/2015.
 */
public class DailymotionConnector
{
    public PageVideosInfo getMostPopular(Long page, Long limit)
    {
        DailymotionDTO dailymotionDTO = RestfulService.getInstance(Configs.HOST_NAME.DAILYMOON)
                .getMostPopularVideoDailymotion("title,channel,country,description,duration,id,poster,thumbnail_720_url,url,"
                        , "no_live,no_premium", "visited", page, limit);
        List<VideoItem> videoItems = new ArrayList<>();
        if (dailymotionDTO != null)
        {
            if (dailymotionDTO.getDailymotionDetailDTOs() != null && dailymotionDTO.getDailymotionDetailDTOs().size() > 0)
            {
                List<DailymotionDetailDTO> dailymotionDetailDTOList = dailymotionDTO.getDailymotionDetailDTOs();
                for (DailymotionDetailDTO dailymotionDetailDTO : dailymotionDetailDTOList)
                {
                    VideoItem videoItem = new VideoItem();
                    videoItem.setId(dailymotionDetailDTO.getId());
                    videoItem.setTitle(dailymotionDetailDTO.getTitle());
                    videoItem.setUrl(dailymotionDetailDTO.getUrl());
                    videoItem.setUrlThumbnail(dailymotionDetailDTO.getThumbnail_720_url());
                    videoItem.setDescription(dailymotionDetailDTO.getDescription());
                    videoItem.setHost_name(Configs.HOST_NAME.DAILYMOON);
                    videoItems.add(videoItem);
                }
            }
        }
        PageVideosInfo pageVideosInfo = new PageVideosInfo(videoItems);
        return pageVideosInfo;
    }

    public PageVideosInfo getVideoOfChannel(String id_channel)
    {
        DailymotionDTO dailymotionDTO = RestfulService.getInstance(Configs.HOST_NAME.DAILYMOON)
                .getVideosOfChannel(id_channel
                        , "title,channel,country,description,duration,id,poster,thumbnail_720_url,url"
                        , "visited", 1l, 50l);
        List<VideoItem> videoItems = new ArrayList<>();
        if (dailymotionDTO != null)
        {
            if (dailymotionDTO.getDailymotionDetailDTOs() != null && dailymotionDTO.getDailymotionDetailDTOs().size() > 0)
            {
                List<DailymotionDetailDTO> dailymotionDetailDTOList = dailymotionDTO.getDailymotionDetailDTOs();
                for (DailymotionDetailDTO dailymotionDetailDTO : dailymotionDetailDTOList)
                {
                    VideoItem videoItem = new VideoItem();
                    videoItem.setId(dailymotionDetailDTO.getId());
                    videoItem.setTitle(dailymotionDetailDTO.getTitle());
                    videoItem.setUrl(dailymotionDetailDTO.getUrl());
                    videoItem.setUrlThumbnail(dailymotionDetailDTO.getThumbnail_720_url());
                    videoItem.setDescription(dailymotionDetailDTO.getDescription());
                    videoItem.setHost_name(Configs.HOST_NAME.DAILYMOON);
                    videoItems.add(videoItem);
                }
            }
        }
        PageVideosInfo pageVideosInfo = new PageVideosInfo(videoItems);
        return pageVideosInfo;
    }
    public PageVideosInfo search(DailymotionInfo dailymotionInfo)
    {
        DailymotionDTO dailymotionDTO = RestfulService.getInstance(Configs.HOST_NAME.DAILYMOON).searchDailymotion(
                dailymotionInfo.getKeyWord()
                , dailymotionInfo.getFields()
                , dailymotionInfo.getFlags()
                , dailymotionInfo.getSort()
                , dailymotionInfo.getPage()
                , dailymotionInfo.getLimit());
        List<VideoItem> videoItems = new ArrayList<>();
        if (dailymotionDTO != null)
        {
            if (dailymotionDTO.getDailymotionDetailDTOs() != null && dailymotionDTO.getDailymotionDetailDTOs().size() > 0)
            {
                List<DailymotionDetailDTO> dailymotionDetailDTOList = dailymotionDTO.getDailymotionDetailDTOs();
                for (DailymotionDetailDTO dailymotionDetailDTO : dailymotionDetailDTOList)
                {
                    VideoItem videoItem = new VideoItem();
                    videoItem.setId(dailymotionDetailDTO.getId());
                    videoItem.setTitle(dailymotionDetailDTO.getTitle());
                    videoItem.setUrl(dailymotionDetailDTO.getUrl());
                    videoItem.setUrlThumbnail(dailymotionDetailDTO.getThumbnail_720_url());
                    videoItem.setDescription(dailymotionDetailDTO.getDescription());
                    videoItem.setHost_name(Configs.HOST_NAME.DAILYMOON);
                    videoItems.add(videoItem);
                }
            }
        }
        PageVideosInfo pageVideosInfo = new PageVideosInfo(videoItems);
        return pageVideosInfo;
    }

}
