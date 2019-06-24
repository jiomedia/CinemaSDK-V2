
package com.jio.media.library.player.model.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Url {

    @SerializedName("autoPlay")
    @Expose
    private AutoPlay autoPlay;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("wvProxyUrl")
    @Expose
    private String wvProxyUrl;

    @SerializedName("analytics")
    @Expose
    private String analytics;

    @SerializedName("tid")
    @Expose
    private String tid;

    @SerializedName("cdnencryption_flag")
    @Expose
    private Boolean cdnEncryptionFlag;

    @SerializedName("cdnUrlExpiry")
    @Expose
    private Integer cdnUrlExpiry;

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public AutoPlay getAutoPlay()
    {
        return autoPlay;
    }

    public void setAutoPlay(AutoPlay autoPlay)
    {
        this.autoPlay = autoPlay;
    }

    public String getWvProxyUrl() {
        return wvProxyUrl;
    }

    public void setWvProxyUrl(String wvProxyUrl) {
        this.wvProxyUrl = wvProxyUrl;
    }

    public String getAnalytics() {
        return analytics;
    }

    public void setAnalytics(String analytics) {
        this.analytics = analytics;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Boolean getCdnEncryptionFlag() {
        return cdnEncryptionFlag;
    }

    public void setCdnEncryptionFlag(Boolean cdnEncryptionFlag) {
        this.cdnEncryptionFlag = cdnEncryptionFlag;
    }

    public Integer getCdnUrlExpiry() {
        return cdnUrlExpiry;
    }

    public void setCdnUrlExpiry(Integer cdnUrlExpiry) {
        this.cdnUrlExpiry = cdnUrlExpiry;
    }
}
