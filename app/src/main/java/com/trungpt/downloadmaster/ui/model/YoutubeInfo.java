package com.trungpt.downloadmaster.ui.model;

/**
 * Created by Trung on 11/12/2015.
 */
public class YoutubeInfo implements ModelInfo
{
    private final String keyWord;
    private final String location;
    private final String order;
    private final String regionCode;
    private final String topic;
    private final String type;
    private final String videoDefinition;
    private final String videoDimension;
    private final String videoDuration;
    private final String videoLicense;
    private final String videoType;
    private final String id;
    private String pageToken;

    public YoutubeInfo(YoutubeBuilder builder)
    {
        this.keyWord = builder.keyWord;
        this.location = builder.location;
        this.order = builder.order;
        this.regionCode = builder.regionCode;
        this.topic = builder.topic;
        this.type = builder.type;
        this.videoDefinition = builder.videoDefinition;
        this.videoDimension = builder.videoDimension;
        this.videoDuration = builder.videoDuration;
        this.videoLicense = builder.videoLicense;
        this.videoType = builder.videoType;
        this.pageToken = builder.pageToken;
        this.id = builder.id;
    }


    public String getKeyWord()
    {
        return keyWord;
    }

    public String getLocation()
    {
        return location;
    }

    public String getOrder()
    {
        return order;
    }

    public String getRegionCode()
    {
        return regionCode;
    }

    public String getTopic()
    {
        return topic;
    }

    public String getType()
    {
        return type;
    }

    public String getVideoDefinition()
    {
        return videoDefinition;
    }

    public String getVideoDimension()
    {
        return videoDimension;
    }

    public String getVideoDuration()
    {
        return videoDuration;
    }

    public String getVideoLicense()
    {
        return videoLicense;
    }

    public String getVideoType()
    {
        return videoType;
    }

    public String getPageToken()
    {
        return pageToken;
    }

    public String getId()
    {
        return id;
    }

    public void setPageToken(String pageToken)
    {
        this.pageToken = pageToken;
    }

    public static class YoutubeBuilder
    {
        private final String keyWord;
        private String location;
        private String order;
        private String regionCode;
        private String topic;
        private String type;
        private String videoDefinition;
        private String videoDimension;
        private String videoDuration;
        private String videoLicense;
        private String videoType;
        private String pageToken;
        private String id;

        public YoutubeBuilder(String keyWord)
        {
            this.keyWord = keyWord;
        }

        public YoutubeBuilder location(String location)
        {
            this.location = location;
            return this;
        }

        public YoutubeBuilder id(String id)
        {
            this.id = id;
            return this;
        }
        public YoutubeBuilder order(String order)
        {
            this.order = order;
            return this;
        }

        public YoutubeBuilder regionCode(String regionCode)
        {
            this.regionCode = regionCode;
            return this;
        }

        public YoutubeBuilder topic(String topic)
        {
            this.topic = topic;
            return this;
        }

        public YoutubeBuilder type(String type)
        {
            this.type = type;
            return this;
        }

        public YoutubeBuilder videoDefinition(String videoDefinition)
        {
            this.videoDefinition = videoDefinition;
            return this;
        }

        public YoutubeBuilder videoDimension(String videoDimension)
        {
            this.videoDimension = videoDimension;
            return this;
        }

        public YoutubeBuilder videoDuration(String videoDuration)
        {
            this.videoDuration = videoDuration;
            return this;
        }

        public YoutubeBuilder videoLicense(String videoLicense)
        {
            this.videoLicense = videoLicense;
            return this;
        }

        public YoutubeBuilder videoType(String videoType)
        {
            this.videoType = videoType;
            return this;
        }

        public YoutubeBuilder pageToken(String pageToken)
        {
            this.pageToken = pageToken;
            return this;
        }

        public YoutubeInfo build()
        {
            return new YoutubeInfo(this);
        }

    }
}
