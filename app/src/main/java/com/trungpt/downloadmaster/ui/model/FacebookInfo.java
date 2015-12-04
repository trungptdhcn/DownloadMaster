package com.trungpt.downloadmaster.ui.model;

/**
 * Created by Trung on 11/22/2015.
 */
public class FacebookInfo implements ModelInfo
{
    private String keyword;
    private String nextPage;

    public FacebookInfo(String keyword, String nextPage)
    {
        this.keyword = keyword;
        this.nextPage = nextPage;
    }

    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    public String getNextPage()
    {
        return nextPage;
    }

    public void setNextPage(String nextPage)
    {
        this.nextPage = nextPage;
    }
}
