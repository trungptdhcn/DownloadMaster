package com.trungpt.downloadmaster.ui.sync.vimeo;

/**
 * Created by Trung on 11/13/2015.
 */
public class VimeoPrivacyDTO
{
    private String view;
    private String embed;
    private Boolean download;
    private Boolean add;
    private String comments;

    public String getView()
    {
        return view;
    }

    public void setView(String view)
    {
        this.view = view;
    }

    public String getEmbed()
    {
        return embed;
    }

    public void setEmbed(String embed)
    {
        this.embed = embed;
    }

    public Boolean getDownload()
    {
        return download;
    }

    public void setDownload(Boolean download)
    {
        this.download = download;
    }

    public Boolean getAdd()
    {
        return add;
    }

    public void setAdd(Boolean add)
    {
        this.add = add;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }
}
