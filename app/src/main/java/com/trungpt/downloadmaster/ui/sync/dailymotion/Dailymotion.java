package com.trungpt.downloadmaster.ui.sync.dailymotion;

import com.google.gson.Gson;
import com.trungpt.downloadmaster.common.StringUtils;
import com.trungpt.downloadmaster.ui.sync.common.Host;
import com.trungpt.downloadmaster.ui.sync.common.HostDirect;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Trung on 11/19/2015.
 */
public class Dailymotion implements Host
{
    @Override
    public String downloadWebpage(String urlStr) throws IOException
    {
        URL url = new URL("https://www.dailymotion.com/video/xsoaqk_l-appli-skyrock-com-en-bref_tech");
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("GET");
//        InputStream stream = con.getInputStream();
//        InputStreamReader reader = new InputStreamReader(stream);
//        StringBuffer buffer = new StringBuffer();
//        char[] buf = new char[262144];
//        int chars_read;
//        while ((chars_read = reader.read(buf, 0, 262144)) != -1)
//        {
//            buffer.append(buf, 0, chars_read);
//        }
        StringBuilder builder = new StringBuilder();
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(urlStr);
        try
        {
            HttpResponse execute = client.execute(httpGet);
            InputStream content = execute.getEntity().getContent();

            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
            String s = "";
            while ((s = buffer.readLine()) != null)
            {
                builder.append(s);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        String webpageStr = builder.toString();
        return webpageStr;
    }

    @Override
    public HostDirect extractHostDirect(String url) throws IOException
    {
        String webPage = downloadWebpage(url);
//        String regex = "buildPlayer\\(\\{.+?\\}\\);', r'playerV5\\s*=\\s*dmp\\.create\\([^,]+?,\\s*(\\{.+?\\})\\);";
        String regex = "buildPlayer\\(\\{.+?\\}\\);";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(webPage);
        while (matcher.find())
        {
            String json = matcher.group();
            String buildPlayer =json.substring(12,json.length() -2);
            DailymotionDirect directMetaData = new Gson().fromJson(buildPlayer, DailymotionDirect.class);
            if (directMetaData != null)
            {
                return directMetaData.getDailymotionDirectMetaData();
            }
        }

        return null;
    }
}
