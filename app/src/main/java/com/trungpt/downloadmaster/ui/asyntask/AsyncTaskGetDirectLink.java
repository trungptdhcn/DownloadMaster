package com.trungpt.downloadmaster.ui.asyntask;

import android.os.AsyncTask;
import com.trungpt.downloadmaster.download.VideoModel;
import com.trungpt.downloadmaster.download.VideoNormalInformation;
import com.trungpt.downloadmaster.download.VideoVimeoInformation;
import com.trungpt.downloadmaster.download.VideoYoutubeInformation;
import com.trungpt.downloadmaster.download.info.VideoInfo;
import com.trungpt.downloadmaster.download.parser.VimeoInfo;
import com.trungpt.downloadmaster.download.parser.VimeoParser;
import com.trungpt.downloadmaster.download.parser.YouTubeParser;
import com.trungpt.downloadmaster.download.parser.YoutubeInfo;
import com.trungpt.downloadmaster.ui.listener.AsyncTaskGetLinkListener;
import com.trungpt.downloadmaster.ui.sync.common.HostDirect;
import com.trungpt.downloadmaster.ui.sync.dailymotion.Dailymotion;
import com.trungpt.downloadmaster.ui.sync.dailymotion.DailymotionDirectMetaData;
import com.trungpt.downloadmaster.ui.sync.dailymotion.DailymotionDirectQuatities;
import com.trungpt.downloadmaster.utils.Configs;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Trung on 11/16/2015.
 */
public class AsyncTaskGetDirectLink extends AsyncTask<Void, List<VideoModel>, List<VideoModel>>
{
    AsyncTaskGetLinkListener listener;
    String urlVideo;
    List<YouTubeParser.VideoDownload> listVideoYoutube;
    List<VimeoParser.VideoDownload> listVideoVimeo;
    private VideoInfo info;
    private Configs.HOST_NAME host_name;

    public AsyncTaskGetDirectLink(String urlVideo, Configs.HOST_NAME host_name)
    {
        this.urlVideo = urlVideo;
        this.host_name = host_name;

    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        listener.prepare();
    }

    @Override
    protected List<VideoModel> doInBackground(Void... params)
    {
        List<VideoModel> videoModels = new ArrayList<VideoModel>();
        try
        {
            Object o = getDirectLnk(urlVideo, host_name);
            if (o != null)
            {
                if (o instanceof VideoYoutubeInformation)
                {
                    listVideoYoutube = ((VideoYoutubeInformation) o).getList();
                    info = ((VideoYoutubeInformation) o).getYoutubeInfo();
                    if (listVideoYoutube != null && listVideoYoutube.size() > 0)
                    {
                        for (YouTubeParser.VideoDownload videoDownload : listVideoYoutube)
                        {
                            if (videoDownload.stream != null)
                            {
                                VideoModel videoModel = new VideoModel();
                                videoModel.setUrl(videoDownload.url);
                                videoModel.setTitle(info.getTitle());
                                videoModel.setUrlIcon(info.getIcon().toString());
                                videoModel.setHost_name(Configs.HOST_NAME.YOUTUBE);
                                if (videoDownload.stream.c == YoutubeInfo.Container.FLV)
                                {
                                    videoModel.setType("FLV");
                                    videoModel.setQuantity("FLV");
                                }
                                else if (videoDownload.stream.c == YoutubeInfo.Container.GP3)
                                {
                                    videoModel.setType("GP3");
                                    videoModel.setQuantity("GP3");
                                }
                                else if (videoDownload.stream.c == YoutubeInfo.Container.MP4)
                                {
                                    videoModel.setType("MP4");
                                    videoModel.setQuantity("MP4");
                                }
                                else if (videoDownload.stream.c == YoutubeInfo.Container.WEBM)
                                {
                                    videoModel.setType("WEBM");
                                    videoModel.setQuantity("WEBM");
                                }
                                videoModels.add(videoModel);
                            }
                        }
                    }

                }
                else if (o instanceof VideoVimeoInformation)
                {
                    listVideoVimeo = ((VideoVimeoInformation) o).getList();
                    info = ((VideoVimeoInformation) o).getVimeoInfo();
                    if (listVideoVimeo != null && listVideoVimeo.size() > 0)
                    {
                        for (VimeoParser.VideoDownload videoDownload : listVideoVimeo)
                        {
                            VideoModel videoModel = new VideoModel();
                            videoModel.setUrl(videoDownload.url);
                            videoModel.setTitle(info.getTitle());
                            videoModel.setTitle(info.getTitle());
                            videoModel.setUrlIcon(info.getIcon().toString());
                            videoModel.setType("MP4");
                            videoModel.setHost_name(Configs.HOST_NAME.VIMEO);
                            if (videoDownload.vq == VimeoInfo.VimeoQuality.pHi)
                            {
                                videoModel.setQuantity("HD");
                            }
                            else if (videoDownload.vq == VimeoInfo.VimeoQuality.pLow)
                            {
                                videoModel.setQuantity("SD");
                            }
                            videoModels.add(videoModel);
                        }
                    }
                }
                else if (o != null && o instanceof DailymotionDirectMetaData)
                {
                    DailymotionDirectMetaData directMetaData = (DailymotionDirectMetaData) o;
                    if (directMetaData.getDailymotionDirectQuatities() != null)
                    {
                        DailymotionDirectQuatities dailymotionDirectQuatities = directMetaData.getDailymotionDirectQuatities();
                        if (dailymotionDirectQuatities != null)
                        {
                            if (dailymotionDirectQuatities.get_auto() != null)
                            {
//                                VideoModel videoModel = new VideoModel();
//                                videoModel.setUrl(new URL(dailymotionDirectQuatities.get_auto().get(0).getUrl()));
//                                videoModel.setType("MP4");
//                                videoModel.setQuantity("NORMAL");
//                                videoModels.add(videoModel);
                            }
                            if (dailymotionDirectQuatities.get_240() != null
                                    && dailymotionDirectQuatities.get_240().size() > 0)
                            {
                                VideoModel videoModel = new VideoModel();
                                videoModel.setUrl(new URL(dailymotionDirectQuatities.get_240().get(0).getUrl()));
                                videoModel.setType("MP4");
                                videoModel.setQuantity("240");
                                videoModel.setTitle(directMetaData.getTitle());
                                videoModel.setUrlIcon(directMetaData.getPoster_url());
                                videoModel.setHost_name(Configs.HOST_NAME.DAILYMOON);
                                videoModels.add(videoModel);
                            }
                            if (dailymotionDirectQuatities.get_380() != null
                                    && dailymotionDirectQuatities.get_380().size() > 0)
                            {
                                VideoModel videoModel = new VideoModel();
                                videoModel.setUrl(new URL(dailymotionDirectQuatities.get_380().get(0).getUrl()));
                                videoModel.setType("MP4");
                                videoModel.setQuantity("380");
                                videoModel.setTitle(directMetaData.getTitle());
                                videoModel.setUrlIcon(directMetaData.getPoster_url());
                                videoModel.setHost_name(Configs.HOST_NAME.DAILYMOON);
                                videoModels.add(videoModel);
                            }
                            if (dailymotionDirectQuatities.get_480() != null
                                    && dailymotionDirectQuatities.get_480().size() > 0)
                            {
                                VideoModel videoModel = new VideoModel();
                                videoModel.setUrl(new URL(dailymotionDirectQuatities.get_480().get(0).getUrl()));
                                videoModel.setType("MP4");
                                videoModel.setQuantity("480");
                                videoModel.setTitle(directMetaData.getTitle());
                                videoModel.setUrlIcon(directMetaData.getPoster_url());
                                videoModel.setHost_name(Configs.HOST_NAME.DAILYMOON);
                                videoModels.add(videoModel);
                            }
                            if (dailymotionDirectQuatities.get_720() != null
                                    && dailymotionDirectQuatities.get_720().size() > 0)
                            {
                                VideoModel videoModel = new VideoModel();
                                videoModel.setUrl(new URL(dailymotionDirectQuatities.get_720().get(0).getUrl()));
                                videoModel.setType("MP4");
                                videoModel.setQuantity("720");
                                videoModel.setTitle(directMetaData.getTitle());
                                videoModel.setUrlIcon(directMetaData.getPoster_url());
                                videoModel.setHost_name(Configs.HOST_NAME.DAILYMOON);
                                videoModels.add(videoModel);
                            }
                        }

                    }

                }
//                else if (o instanceof VideoNormalInformation)
//                {
//                    videoNormalUrl = ((VideoNormalInformation) o).getUrl();
//                    info = ((VideoNormalInformation) o).getInfo();
//                    VideoModel videoModel = new VideoModel();
//                    videoModel.setUrl(new URL(videoNormalUrl));
//                    videoModel.setTitle(info.getTitle());
//                    videoModel.setBm(((VideoNormalInformation) o).getBmIcon());
//                    videoModel.setType(((VideoNormalInformation) o).getType());
//                    videoModel.setQuantity(((VideoNormalInformation) o).getQuantity());
//                    videoModels.add(videoModel);
//                }
            }
            else
            {
                return null;
            }
        }
        catch (MalformedURLException e)
        {
            return null;
        }
        return videoModels;
    }

    @Override
    protected void onPostExecute(List<VideoModel> videoModels)
    {
        super.onPostExecute(videoModels);
        listener.complete(videoModels);
    }

    public Object getDirectLnk(String url, Configs.HOST_NAME host_name) throws MalformedURLException
    {
        String regularExpressionVimeo = "(https?://)?(www.)?(player.)?vimeo.com/([a-z]*/)*([0-9]{6,11})[?]?.*";
        String regularExpressionVideoFile = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp|mp4|flv|mp3|WEBM|GP3))$)";
        String regularExpressionYoutube = "(https?://)?(www\\.)?(m\\.)?(yotu\\.be/|youtube\\.com/)?((.+/)?(watch(\\?v=|.+&v=))?(v=)?)([\\w_-]{11})(&.+)?";
        if (host_name.equals(Configs.HOST_NAME.VIMEO))
        {
            VimeoInfo info = new VimeoInfo(new URL(url));
            VimeoParser vimeoParser = new VimeoParser();
            List<VimeoParser.VideoDownload> list = vimeoParser.extractLink(info, new AtomicBoolean(), new Runnable()
            {
                @Override
                public void run()
                {

                }
            }, url);
            return new VideoVimeoInformation(list, info);
        }
        if (host_name.equals(Configs.HOST_NAME.YOUTUBE))
        {
            if (url.contains("http://m."))
            {
                url = url.replace("http://m.", "https://m.");
            }
            YoutubeInfo info = new YoutubeInfo(new URL(url));
            YouTubeParser youTubeParser = new YouTubeParser();
            List<YouTubeParser.VideoDownload> list = youTubeParser.extractLinks(info);
            return new VideoYoutubeInformation(list, info);
        }
        if (host_name.equals(Configs.HOST_NAME.DAILYMOON))
        {
            Dailymotion dailymotion = new Dailymotion();
            try
            {
                HostDirect dailymotionHostDirectLink = dailymotion.extractHostDirect(url);
                return dailymotionHostDirectLink;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        if (url.matches(regularExpressionVideoFile))
        {
            VideoInfo info = new VideoInfo(new URL(url));
            String baseName = FilenameUtils.getBaseName(url);
            String extension = FilenameUtils.getExtension(url);
            return new VideoNormalInformation(url, info, extension, extension, null);
        }

        return null;
    }

    public AsyncTaskGetLinkListener getListener()
    {
        return listener;
    }

    public void setListener(AsyncTaskGetLinkListener listener)
    {
        this.listener = listener;
    }
}
