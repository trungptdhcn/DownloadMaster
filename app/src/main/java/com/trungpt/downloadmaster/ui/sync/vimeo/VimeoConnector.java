package com.trungpt.downloadmaster.ui.sync.vimeo;

import android.os.StrictMode;
import com.trungpt.downloadmaster.ui.model.PageVideosInfo;
import com.trungpt.downloadmaster.ui.model.VideoItem;
import com.trungpt.downloadmaster.ui.model.VimeoInfo;
import com.trungpt.downloadmaster.ui.sync.common.RestfulService;
import com.trungpt.downloadmaster.utils.Configs;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Trung on 11/12/2015.
 */
public class VimeoConnector
{
    public VimeoCategoriesDTO getCategories()
    {
        return RestfulService.getInstance(Configs.HOST_NAME.VIMEO).getCategories();
    }

    public PageVideosInfo getVideosOfCategory(String category)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);
        VimeoInfoDTO vimeoInfoDTO = RestfulService.getInstance(Configs.HOST_NAME.VIMEO).getVideoOfCategory(category);
        String nextPage = vimeoInfoDTO.getVimeoPaging().getNextPage();
        List<VideoItem> videoItems = new ArrayList<>();
        List<VimeoDTO> vimeoDTOs = vimeoInfoDTO.getVimeoDTOList();
        for (VimeoDTO vimeoDTO : vimeoDTOs)
        {
            VideoItem videoItem = new VideoItem();
            String uri = vimeoDTO.getUri();
            String regex = "[0-9].+$";
            String vimeo_id="";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(uri);
            while (matcher.find())
            {
                vimeo_id =  matcher.group(0);
            }
            videoItem.setUrl("https://vimeo.com/"+vimeo_id);

            videoItem.setUrlThumbnail(vimeoDTO.getVimeoPicturesDTO().getVimeoPicturesSizeDTO().get(3).getLink());
            videoItem.setTitle(vimeoDTO.getName());
            videoItem.setDescription(vimeoDTO.getDescription());
            videoItem.setHost_name(Configs.HOST_NAME.VIMEO);
            videoItems.add(videoItem);
        }
        PageVideosInfo pageVideosInfo = new PageVideosInfo(videoItems);
        return pageVideosInfo;
    }

    public PageVideosInfo search(VimeoInfo vimeoInfo)
    {
        VimeoInfoDTO vimeoInfoDTO = RestfulService.getInstance(Configs.HOST_NAME.VIMEO).search("videos", vimeoInfo.getKeyWord()
                , vimeoInfo.getOrder(), 5, vimeoInfo.getPageToken());
        List<VideoItem> videoItems = new ArrayList<>();
        if (vimeoInfoDTO != null && vimeoInfoDTO.getVimeoDTOList() != null
                && vimeoInfoDTO.getVimeoDTOList().size() > 0)
        {
            List<VimeoDTO> vimeoDTOs = vimeoInfoDTO.getVimeoDTOList();
            for (VimeoDTO vimeoDTO : vimeoDTOs)
            {
                VideoItem videoItem = new VideoItem();
                String uri = vimeoDTO.getUri();
                String regex = "[0-9].+$";
                String vimeo_id="";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(uri);
                while (matcher.find())
                {
                    vimeo_id =  matcher.group(0);
                }
                videoItem.setUrl("https://vimeo.com/"+vimeo_id);
                videoItem.setUrlThumbnail(vimeoDTO.getVimeoPicturesDTO().getVimeoPicturesSizeDTO().get(3).getLink());
                videoItem.setTitle(vimeoDTO.getName());
                videoItem.setDescription(vimeoDTO.getDescription());
                videoItem.setHost_name(Configs.HOST_NAME.VIMEO);
                videoItems.add(videoItem);
            }
        }
        PageVideosInfo pageVideosInfo = new PageVideosInfo(videoItems);
        return pageVideosInfo;
    }

    public VideoItem getVideoById(String video_id)
    {
        VimeoDTO vimeoDTO = RestfulService.getInstance(Configs.HOST_NAME.VIMEO).getVideoById(video_id);
        VideoItem videoItem = new VideoItem();
        if (vimeoDTO != null)
        {
            String uri = vimeoDTO.getUri();
            String regex = "[0-9].+$";
            String vimeo_id="";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(uri);
            while (matcher.find())
            {
                vimeo_id =  matcher.group(0);
            }
            videoItem.setUrl("https://vimeo.com/"+vimeo_id);

            videoItem.setUrlThumbnail(vimeoDTO.getVimeoPicturesDTO().getVimeoPicturesSizeDTO().get(3).getLink());
            videoItem.setTitle(vimeoDTO.getName());
            videoItem.setDescription(vimeoDTO.getDescription());
            videoItem.setHost_name(Configs.HOST_NAME.VIMEO);
        }

        return videoItem;
    }
}
