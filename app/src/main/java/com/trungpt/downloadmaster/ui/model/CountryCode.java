package com.trungpt.downloadmaster.ui.model;

/**
 * Created by Trung on 11/12/2015.
 */
public class CountryCode
{
    private String name;
    private String code;

    public CountryCode()
    {
    }

    public CountryCode(String name, String code)
    {
        this.name = name;
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }
}
