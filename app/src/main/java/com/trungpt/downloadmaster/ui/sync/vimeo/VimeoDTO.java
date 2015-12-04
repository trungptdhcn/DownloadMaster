package com.trungpt.downloadmaster.ui.sync.vimeo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Trung on 11/13/2015.
 */
public class VimeoDTO
{
    private String uri;
    private String name;
    private String description;
    private String link;
    private Integer duration;
    @SerializedName("embed")
    private VimeoEmbedDTO vimeoEmbedDTO;
    private String created_time;
    private String modified_time;
    private String license;
    @SerializedName("privacy")
    private VimeoPrivacyDTO vimeoPrivacyDTO;
    @SerializedName("pictures")
    private VimeoPicturesDTO vimeoPicturesDTO;

    public String getUri()
    {
        return uri;
    }

    public void setUri(String url)
    {
        this.uri = url;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String links)
    {
        this.link = links;
    }

    public Integer getDuration()
    {
        return duration;
    }

    public void setDuration(Integer duration)
    {
        this.duration = duration;
    }

    public VimeoEmbedDTO getVimeoEmbedDTO()
    {
        return vimeoEmbedDTO;
    }

    public void setVimeoEmbedDTO(VimeoEmbedDTO vimeoEmbedDTO)
    {
        this.vimeoEmbedDTO = vimeoEmbedDTO;
    }

    public String getCreated_time()
    {
        return created_time;
    }

    public void setCreated_time(String created_time)
    {
        this.created_time = created_time;
    }

    public String getModified_time()
    {
        return modified_time;
    }

    public void setModified_time(String modified_time)
    {
        this.modified_time = modified_time;
    }

    public String getLicense()
    {
        return license;
    }

    public void setLicense(String license)
    {
        this.license = license;
    }

    public VimeoPrivacyDTO getVimeoPrivacyDTO()
    {
        return vimeoPrivacyDTO;
    }

    public void setVimeoPrivacyDTO(VimeoPrivacyDTO vimeoPrivacyDTO)
    {
        this.vimeoPrivacyDTO = vimeoPrivacyDTO;
    }

    public VimeoPicturesDTO getVimeoPicturesDTO()
    {
        return vimeoPicturesDTO;
    }

    public void setVimeoPicturesDTO(VimeoPicturesDTO vimeoPicturesDTO)
    {
        this.vimeoPicturesDTO = vimeoPicturesDTO;
    }
}
