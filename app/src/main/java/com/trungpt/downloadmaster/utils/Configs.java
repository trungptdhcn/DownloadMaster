package com.trungpt.downloadmaster.utils;

/**
 * Created by Trung on 11/12/2015.
 */
public class Configs
{
    public enum LIST_MODE
    {
        SEARCH, MOST_POPULAR
    }
    public enum SEARCH_MODE
    {
        YOUTUBE, VIMEO, DAILYMOTION
    }

    public enum HOST_NAME
    {
        YOUTUBE("Youtube"), VIMEO("Vimeo"), DAILYMOON("Daillymoon"), FACEBOOK("Facebook");
        public String value;
        HOST_NAME(String value)
        {
            this.value = value;
        }
    }
    public static String MOST_POPULAR = "mostPopular";
    public static String VIMEO_BASE_URL = "https://api.vimeo.com";
    public static String DAILYMOTION_BASE_URL = "https://api.dailymotion.com";
    public static String VIMEO_ACCESSTOKEN = "b7cb6935fae643704ecf844b216cacb4";
    public static final String YOUTUBE_KEY = "AIzaSyCOjGPIXGYa0QIhW74WYx4vIWtjZXqXOXE";
}
