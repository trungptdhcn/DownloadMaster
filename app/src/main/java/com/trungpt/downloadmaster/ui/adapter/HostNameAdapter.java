package com.trungpt.downloadmaster.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.trungpt.downloadmaster.R;
import com.trungpt.downloadmaster.utils.Configs;

/**
 * Created by Trung on 11/13/2015.
 */
public class HostNameAdapter extends ArrayAdapter<Configs.HOST_NAME>
{
//    private String[] hostnames;
    private Configs.HOST_NAME[] hostnames ={ Configs.HOST_NAME.VIMEO
        , Configs.HOST_NAME.YOUTUBE
        , Configs.HOST_NAME.DAILYMOON
        , Configs.HOST_NAME.FACEBOOK};
    private Context context;
    private int layoutResourceId;

    public HostNameAdapter(Context context, int layoutResourceId)
    {
        super(context, layoutResourceId);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return hostnames.length;
    }

    @Override
    public Configs.HOST_NAME getItem(int position)
    {
        return hostnames[position];
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
        tvTitle.setText(hostnames[position].value);
        return v;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent)
    {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(layoutResourceId, parent, false);
        TextView tvTitle = (TextView) v.findViewById(R.id.spinner_item_tvCountry);
        tvTitle.setText(hostnames[position].value);
        return v;
    }
}
