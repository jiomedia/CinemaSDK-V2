
package com.jio.media.library.player.model.zla;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User
{
    @SerializedName("commonName")
    @Expose
    private String commonName;

    @SerializedName("subscriberId")
    @Expose
    private String subscriberId;

    @SerializedName("preferredLocale")
    @Expose
    private String preferredLocale;

    @SerializedName("ssoLevel")
    @Expose
    private String ssoLevel;

    @SerializedName("uid")
    @Expose
    private String uId;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getCommonName()
    {
        return commonName;
    }

    public void setCommonName(String commonName)
    {
        this.commonName = commonName;
    }

    public String getSubscriberId()
    {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId)
    {
        this.subscriberId = subscriberId;
    }

    public String getPreferredLocale()
    {
        return preferredLocale;
    }

    public void setPreferredLocale(String preferredLocale) {
        this.preferredLocale = preferredLocale;
    }

    public String getSsoLevel()
    {
        return ssoLevel;
    }

    public void setSsoLevel(String ssoLevel)
    {
        this.ssoLevel = ssoLevel;
    }
}
