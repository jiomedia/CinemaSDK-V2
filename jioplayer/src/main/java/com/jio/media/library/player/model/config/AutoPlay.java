package com.jio.media.library.player.model.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AutoPlay
{
    @SerializedName("active")
    @Expose
    private boolean active;

    @SerializedName("bannerImage")
    @Expose
    private String bannerImage;

    @SerializedName("contentId")
    @Expose
    private String contentId;

    @SerializedName("cdnUrlPass")
    @Expose
    private String cdnUrlPass;

    @SerializedName("videoTitle")
    @Expose
    private String videoTitle;

    @SerializedName("videoSubTitle")
    @Expose
    private String videoSubTitle;

    @SerializedName("videoDesc")
    @Expose
    private String videoDesc;

    @SerializedName("JCredirect")
    @Expose
    private String JCredirect;

    @SerializedName("JCdownload")
    @Expose
    private String JCdownload;

    public String getJCredirect()
    {
        return JCredirect;
    }

    public void setJCredirect(String JCredirect)
    {
        this.JCredirect = JCredirect;
    }

    public String getJCdownload()
    {
        return JCdownload;
    }

    public void setJCdownload(String JCdownload)
    {
        this.JCdownload = JCdownload;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public String getBannerImage()
    {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage)
    {
        this.bannerImage = bannerImage;
    }

    public String getContentId()
    {
        return contentId;
    }

    public void setContentId(String contentId)
    {
        this.contentId = contentId;
    }

    public String getCdnUrlPass()
    {
        return cdnUrlPass;
    }

    public void setCdnUrlPass(String cdnUrlPass)
    {
        this.cdnUrlPass = cdnUrlPass;
    }

    public String getVideoTitle()
    {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle)
    {
        this.videoTitle = videoTitle;
    }

    public String getVideoSubTitle()
    {
        return videoSubTitle;
    }

    public void setVideoSubTitle(String videoSubTitle)
    {
        this.videoSubTitle = videoSubTitle;
    }

    public String getVideoDesc()
    {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc)
    {
        this.videoDesc = videoDesc;
    }
}
