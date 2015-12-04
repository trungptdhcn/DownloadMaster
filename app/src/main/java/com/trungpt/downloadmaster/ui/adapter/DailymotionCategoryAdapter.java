package com.trungpt.downloadmaster.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.trungpt.downloadmaster.R;
import com.trungpt.downloadmaster.ui.model.DailymotionCategory;
import com.trungpt.downloadmaster.ui.model.VimeoCategory;
import com.trungpt.downloadmaster.ui.sync.dailymotion.Dailymotion;
import com.trungpt.downloadmaster.utils.ResourceUtils;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung on 11/19/2015.
 */
public class DailymotionCategoryAdapter extends ArrayAdapter<DailymotionCategory>
{
    private List<DailymotionCategory> dailymotionCategories = new ArrayList<>();
    private Context context;
    private int layoutResourceId;

    public DailymotionCategoryAdapter(Context context, int layoutResourceId)
    {
        super(context, layoutResourceId);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        try
        {
            this.dailymotionCategories = ResourceUtils.getListDailymotionCategory(context);
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount()
    {
        return dailymotionCategories.size();
    }

    @Override
    public DailymotionCategory getItem(int position)
    {
        return dailymotionCategories.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
//        return getCustomView(position, convertView, parent);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.spinner_selector_item, parent, false);
        TextView tvTitle = (TextView) v.findViewById(R.id.spinner_selector_item_tvCountry);
        tvTitle.setText(dailymotionCategories.get(position).getName());
        return v;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent)
    {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(layoutResourceId, parent, false);
        TextView tvTitle = (TextView) v.findViewById(R.id.spinner_item_tvCountry);
        tvTitle.setText(dailymotionCategories.get(position).getName());
        return v;
    }
}
