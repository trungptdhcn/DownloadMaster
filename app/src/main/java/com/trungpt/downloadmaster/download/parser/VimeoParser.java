package com.trungpt.downloadmaster.download.parser;

import com.github.axet.wget.WGet;
import com.github.axet.wget.WGet.HtmlLoader;
import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.ex.DownloadError;
import com.google.gson.Gson;
import com.trungpt.downloadmaster.download.info.VGetParser;
import com.trungpt.downloadmaster.download.info.VideoInfo;
import com.trungpt.downloadmaster.download.info.VideoInfo.*;
import com.trungpt.downloadmaster.download.newmodel.*;
import com.trungpt.downloadmaster.download.parser.VimeoInfo.*;
import org.apache.commons.lang3.StringEscapeUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VimeoParser extends VGetParser
{

    static public class VideoDownload
    {
        public VimeoInfo.VimeoQuality vq;
        public URL url;

        public VideoDownload(VimeoInfo.VimeoQuality vq, URL u)
        {
            this.vq = vq;
            this.url = u;
        }
    }

    static public class VideoContentFirst implements Comparator<VideoDownload>
    {

        @Override
        public int compare(VideoDownload o1, VideoDownload o2)
        {
            Integer i1 = o1.vq.ordinal();
            Integer i2 = o2.vq.ordinal();
            Integer ic = i1.compareTo(i2);

            return ic;
        }

    }

    public static class VimeoData
    {
        public VimeoRequest request;
        public VimeoVideo video;
    }

    public static class VimeoVideo
    {
        public Map<String, String> thumbs;
        public String title;
    }

    public static class VimeoRequest
    {
        public String signature;
        public String session;
        public long timestamp;
        public long expires;
        public VimeoFiles files;
    }

    public static class VimeoFiles
    {
        public ArrayList<String> codecs;
        public VidemoCodec h264;
    }

    public static class VidemoCodec
    {
        public VideoDownloadLink hd;
        public VideoDownloadLink sd;
        public VideoDownloadLink mobile;
    }

    public static class VideoDownloadLink
    {
        public String url;
        public int height;
        public int width;
        public String id;
        public int bitrate;
    }

    public VimeoParser()
    {
    }

    public static boolean probe(URL url)
    {
        return url.toString().contains("vimeo.com");
    }

    public static String extractId(URL url)
    {
        // standard web url. format: "https://vimeo.com/49243107" or
        // "http://vimeo.com/channels/staffpicks/49243107"
        {
            Pattern u = Pattern.compile("vimeo.com.*/(\\d+)");
            Matcher um = u.matcher(url.toString());

            if (um.find())
            {
                return um.group(1);
            }
        }
        // rss feed url. format:
        // "http://vimeo.com/moogaloop.swf?clip_id=49243107"
        {
            Pattern u = Pattern.compile("vimeo.com.*=(\\d+)");
            Matcher um = u.matcher(url.toString());

            if (um.find())
            {
                return um.group(1);
            }
        }
        return null;
    }

    public List<VideoDownload> extractLinks(final VideoInfo info, final AtomicBoolean stop, final Runnable notify)
    {
        List<VideoDownload> list = new ArrayList<VideoDownload>();

        try
        {
            String id;
            String clip;
            {
                id = extractId(info.getWeb());
                if (id == null)
                {
                    throw new DownloadError("unknown url");
                }
                clip = "http://vimeo.com/" + id;
            }

            URL url = new URL(clip);

            String html = WGet.getHtml(url, new HtmlLoader()
            {
                @Override
                public void notifyRetry(int delay, Throwable e)
                {
                    info.setDelay(delay, e);
                    notify.run();
                }

                @Override
                public void notifyDownloading()
                {
                    info.setState(VideoInfo.States.EXTRACTING);
                    notify.run();
                }

                @Override
                public void notifyMoved()
                {
                    info.setState(VideoInfo.States.RETRYING);
                    notify.run();
                }
            }, stop);

            String config;
            {
                Pattern u = Pattern.compile("data-config-url=\"([^\"]+)\"");
                Matcher um = u.matcher(html);
                if (!um.find())
                {
                    throw new DownloadError("unknown config vimeo respond");
                }
                config = um.group(1);
            }

            config = StringEscapeUtils.unescapeHtml4(config);

            String htmlConfig = WGet.getHtml(new URL(config), new HtmlLoader()
            {
                @Override
                public void notifyRetry(int delay, Throwable e)
                {
                    info.setDelay(delay, e);
                    notify.run();
                }

                @Override
                public void notifyDownloading()
                {
                    info.setState(VideoInfo.States.EXTRACTING);
                    notify.run();
                }

                @Override
                public void notifyMoved()
                {
                    info.setState(States.RETRYING);
                    notify.run();
                }
            }, stop);

            VimeoData data = new Gson().fromJson(htmlConfig, VimeoData.class);

            String icon = data.video.thumbs.values().iterator().next();

            info.setTitle(data.video.title);

            if (data.request.files.h264.hd != null)
            {
                list.add(new VideoDownload(VimeoQuality.pHi, new URL(data.request.files.h264.hd.url)));
            }

            if (data.request.files.h264.sd != null)
            {
                list.add(new VideoDownload(VimeoQuality.pLow, new URL(data.request.files.h264.sd.url)));
            }

            info.setIcon(new URL(icon));
        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException(e);
        }

        return list;
    }

    public static List<VimeoParser.VideoDownload> extractLink(final VideoInfo info, final AtomicBoolean stop, final Runnable notify,String urlStr) throws MalformedURLException
    {
        URL url = new URL(urlStr);
        List<VimeoParser.VideoDownload> list = new ArrayList<VimeoParser.VideoDownload>();
        String html = WGet.getHtml(url, new WGet.HtmlLoader()
        {
            @Override
            public void notifyRetry(int delay, Throwable e)
            {
                info.setDelay(delay, e);
                notify.run();
            }

            @Override
            public void notifyDownloading()
            {
                info.setState(VideoInfo.States.EXTRACTING);
                notify.run();
            }

            @Override
            public void notifyMoved()
            {
                info.setState(VideoInfo.States.RETRYING);
                notify.run();
            }
        }, stop);

        String config;
        {
            Pattern u = Pattern.compile("data-config-url=\"([^\"]+)\"");
            Matcher um = u.matcher(html);
            if (!um.find())
            {
                throw new DownloadError("unknown config vimeo respond");
            }
            config = um.group(1);
        }

        config = StringEscapeUtils.unescapeHtml4(config);
        String htmlConfig = WGet.getHtml(new URL(config), new WGet.HtmlLoader()
        {
            @Override
            public void notifyRetry(int delay, Throwable e)
            {
                info.setDelay(delay, e);
                notify.run();
            }

            @Override
            public void notifyDownloading()
            {
                info.setState(VideoInfo.States.EXTRACTING);
                notify.run();
            }

            @Override
            public void notifyMoved()
            {
                info.setState(VideoInfo.States.RETRYING);
                notify.run();
            }
        }, stop);
        VimeoDirectLinkDTO vimeoDirectLinkDTO = new Gson().fromJson(htmlConfig, VimeoDirectLinkDTO.class);
        if (vimeoDirectLinkDTO != null)
        {
            if (vimeoDirectLinkDTO.getVimeoDirectLinkVideoDTO()!=null)
            {
                VimeoDirectLinkVideoDTO vimeoDirectLinkVideoDTO = vimeoDirectLinkDTO.getVimeoDirectLinkVideoDTO();
                info.setIcon(new URL(vimeoDirectLinkVideoDTO.getVimeoDirectLinkThumbDTO().get_960()));
                info.setTitle(vimeoDirectLinkVideoDTO.getTitle());
            }
            if(vimeoDirectLinkDTO.getVimeoDirectLinkRequestDTO()!= null)
            {
                VimeoDirectLinkRequestDTO vimeoDirectLinkRequestDTO = vimeoDirectLinkDTO.getVimeoDirectLinkRequestDTO();
                if(vimeoDirectLinkRequestDTO.getVimeoDirectLinkFilesDTO()!= null)
                {
                    VimeoDirectLinkFilesDTO vimeoDirectLinkFilesDTO = vimeoDirectLinkRequestDTO.getVimeoDirectLinkFilesDTO();
                    if(vimeoDirectLinkFilesDTO != null)
                    {
                        List<VimeoDirectLinkProgressiveDTO> vimeoDirectLinkProgressiveDTOs = vimeoDirectLinkFilesDTO.getVimeoDirectLinkProgressiveDTO();
                        for (VimeoDirectLinkProgressiveDTO vimeoDirectLinkProgressiveDTO : vimeoDirectLinkProgressiveDTOs)
                        {
                            if (vimeoDirectLinkProgressiveDTO.getQuality().equals("720p"))
                            {
                                list.add(new VimeoParser.VideoDownload(VimeoInfo.VimeoQuality.pHi
                                        , new URL(vimeoDirectLinkProgressiveDTO.getUrl())));
                            }else
                            {
                                list.add(new VimeoParser.VideoDownload(VimeoInfo.VimeoQuality.pLow
                                        , new URL(vimeoDirectLinkProgressiveDTO.getUrl())));
                            }
                        }
                    }
                }
            }
        }

        return list;
    }
    @Override
    public DownloadInfo extract(VideoInfo vinfo, AtomicBoolean stop, Runnable notify)
    {
        List<VideoDownload> sNextVideoURL = extractLinks(vinfo, stop, notify);

        Collections.sort(sNextVideoURL, new VideoContentFirst());

        for (int i = 0; i < sNextVideoURL.size(); )
        {
            VideoDownload v = sNextVideoURL.get(i);

            VimeoInfo yinfo = (VimeoInfo) vinfo;
            yinfo.setVideoQuality(v.vq);
            DownloadInfo info = new DownloadInfo(v.url);
            vinfo.setInfo(info);
            return info;
        }

        // throw download stop if user choice not maximum quality and we have no
        // video rendered by

        throw new DownloadError("no video with required quality found");
    }

    @Override
    public VideoInfo info(URL web)
    {
        return new VimeoInfo(web);
    }

}
