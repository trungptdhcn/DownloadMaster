package com.trungpt.downloadmaster.download.parser;

/**
 * Created by Trung on 10/30/2015.
 */
public class InfoDownload
{
    String speed;
    String percentage;

    public InfoDownload(String speed, String percentage)
    {
        this.speed = speed;
        this.percentage = percentage;
    }

    public String getPercentage()
    {
        return percentage;
    }

    public void setPercentage(String percentage)
    {
        this.percentage = percentage;
    }

    public String getSpeed()
    {
        return speed;
    }

    public void setSpeed(String speed)
    {
        this.speed = speed;
    }
}
