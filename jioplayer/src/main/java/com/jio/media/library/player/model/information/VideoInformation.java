package com.jio.media.library.player.model.information;

import java.io.Serializable;

public class VideoInformation implements Serializable
{
    private String url;
    private String name;
    private String contentId;
    private String videoTitle;
    private String videoSubTitle;
    private String videoDescription;
    private String bannerImage;
    private String urlRedirect;
    private String urlDownload;

    public String getContentId()
    {
        return contentId;
    }

    public void setContentId(String contentId)
    {
        this.contentId = contentId;
    }

    public String getUrlRedirect()
    {
        return urlRedirect;
    }

    public void setUrlRedirect(String urlRedirect)
    {
        this.urlRedirect = urlRedirect;
    }

    public String getUrlDownload()
    {
        return urlDownload;
    }

    public void setUrlDownload(String urlDownload)
    {
        this.urlDownload = urlDownload;
    }

    public String getBannerImage()
    {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage)
    {
        this.bannerImage = bannerImage;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
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

    public String getVideoDescription()
    {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription)
    {
        this.videoDescription = videoDescription;
    }
}
