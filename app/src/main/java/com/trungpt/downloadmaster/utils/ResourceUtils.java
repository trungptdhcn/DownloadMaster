package com.trungpt.downloadmaster.utils;

import android.content.Context;
import android.content.res.XmlResourceParser;
import com.trungpt.downloadmaster.R;
import com.trungpt.downloadmaster.ui.model.CountryCode;
import com.trungpt.downloadmaster.ui.model.DailymotionCategory;
import com.trungpt.downloadmaster.ui.model.VimeoCategory;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung on 11/12/2015.
 */
public class ResourceUtils
{
    public static List<CountryCode> getListCountryCode(Context context) throws XmlPullParserException, IOException
    {
        XmlResourceParser xmlResourceParser = context.getResources().getXml(R.xml.countrycode);
        int eventType = xmlResourceParser.getEventType();
        List<CountryCode> countryCodes = new ArrayList<>();

        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            if (eventType == XmlPullParser.START_TAG)
            {
                if (xmlResourceParser.getName().equals("country"))
                {
                    CountryCode countryCode = new CountryCode();
                    countryCode.setCode(xmlResourceParser.getAttributeValue(0));
                    countryCode.setName(xmlResourceParser.getAttributeValue(2));
                    countryCodes.add(countryCode);
                }
            }
            eventType = xmlResourceParser.next();
        }
        return countryCodes;
    }
    public static List<VimeoCategory> getListVimeoCategory(Context context) throws XmlPullParserException, IOException
    {
        XmlResourceParser xmlResourceParser = context.getResources().getXml(R.xml.vimeocategory);
        int eventType = xmlResourceParser.getEventType();
        List<VimeoCategory> vimeoCategories = new ArrayList<>();
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            if (eventType == XmlPullParser.START_TAG)
            {
                if (xmlResourceParser.getName().equals("category"))
                {
                    VimeoCategory vimeoCategory = new VimeoCategory(xmlResourceParser.getAttributeValue(0),xmlResourceParser.getAttributeValue(1));
                    vimeoCategories.add(vimeoCategory);
                }
            }
            eventType = xmlResourceParser.next();
        }
        return vimeoCategories;
    }

    public static List<DailymotionCategory> getListDailymotionCategory(Context context) throws XmlPullParserException, IOException
    {
        XmlResourceParser xmlResourceParser = context.getResources().getXml(R.xml.dailymotioncategory);
        int eventType = xmlResourceParser.getEventType();
        List<DailymotionCategory> dailymotionCategories = new ArrayList<>();
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            if (eventType == XmlPullParser.START_TAG)
            {
                if (xmlResourceParser.getName().equals("category"))
                {
                    DailymotionCategory dailymotionCategory = new DailymotionCategory(xmlResourceParser.getAttributeValue(0),xmlResourceParser.getAttributeValue(1));
                    dailymotionCategories.add(dailymotionCategory);
                }
            }
            eventType = xmlResourceParser.next();
        }
        return dailymotionCategories;
    }
}
