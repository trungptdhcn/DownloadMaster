package com.trungpt.downloadmaster.ui.sync.dailymotion;

import com.trungpt.downloadmaster.ui.model.ModelInfo;

/**
 * Created by Trung on 11/19/2015.
 */
public class DailymotionInfo implements ModelInfo
{
    private final String keyWord;
    private final String fields;
    private final String flags;
    private final String sort;
    private Long page;
    private Long limit;

    public DailymotionInfo(DailymotionBuilder builder)
    {
        this.keyWord = builder.keyWord;
        this.fields = builder.fields;
        this.flags = builder.flags;
        this.sort = builder.sort;
        this.page = builder.page;
        this.limit = builder.limit;
    }
    public String getKeyWord()
    {
        return keyWord;
    }

    public String getFields()
    {
        return fields;
    }

    public String getFlags()
    {
        return flags;
    }

    public String getSort()
    {
        return sort;
    }

    public Long getPage()
    {
        return page;
    }

    public Long getLimit()
    {
        return limit;
    }

    public void setPage(Long page)
    {
        this.page = page;
    }

    public static class DailymotionBuilder
    {
        private final String keyWord;
        private String fields;
        private String flags;
        private String sort;
        private Long page;
        private Long limit;

        public DailymotionBuilder(String keyWord)
        {
            this.keyWord = keyWord;
        }

        public DailymotionBuilder fields(String fields)
        {
            this.fields = fields;
            return this;
        }

        public DailymotionBuilder sort(String sort)
        {
            this.sort = sort;
            return this;
        }
        public DailymotionBuilder flags(String flags)
        {
            this.flags = flags;
            return this;
        }
        public DailymotionBuilder page(Long page)
        {
            this.page = page;
            return this;
        }
        public DailymotionBuilder limit(Long limit)
        {
            this.limit = limit;
            return this;
        }
        public DailymotionInfo build()
        {
            return new DailymotionInfo(this);
        }

    }
}
