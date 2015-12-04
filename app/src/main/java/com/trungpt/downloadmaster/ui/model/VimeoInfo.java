package com.trungpt.downloadmaster.ui.model;

/**
 * Created by Trung on 11/15/2015.
 */
public class VimeoInfo implements ModelInfo
{
    private final String keyWord;
    private final String order;
    private final String category;
    private String pageToken;

    public VimeoInfo(VimeoBuilder builder)
    {
        this.keyWord = builder.keyWord;
        this.order = builder.order;
        this.category = builder.category;
        this.pageToken = builder.pageToken;
    }
    public String getKeyWord()
    {
        return keyWord;
    }

    public String getOrder()
    {
        return order;
    }

    public String getPageToken()
    {
        return pageToken;
    }

    public void setPageToken(String pageToken)
    {
        this.pageToken = pageToken;
    }

    public String getCategory()
    {
        return category;
    }

    public static class VimeoBuilder
    {
        private final String keyWord;
        private String category;
        private String order;
        private String pageToken;

        public VimeoBuilder(String keyWord)
        {
            this.keyWord = keyWord;
        }

        public VimeoBuilder category(String category)
        {
            this.category = category;
            return this;
        }

        public VimeoBuilder order(String order)
        {
            this.order = order;
            return this;
        }
        public VimeoInfo build()
        {
            return new VimeoInfo(this);
        }

    }
}
