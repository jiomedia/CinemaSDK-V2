
package com.jio.media.library.player.model.zla;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ZLAUserData
{
    @SerializedName("jToken")
    @Expose
    private String jToken;

    @SerializedName("ssoLevel")
    @Expose
    private String ssoLevel;

    @SerializedName("ssoToken")
    @Expose
    private String ssoToken;

    @SerializedName("sessionAttributes")
    @Expose
    private SessionAttributes sessionAttributes;

    @SerializedName("lbCookie")
    @Expose
    private String lbCookie;

    public String getJToken()
    {
        return jToken;
    }

    public void setJToken(String jToken)
    {
        this.jToken = jToken;
    }

    public String getSsoLevel()
    {
        return ssoLevel;
    }

    public void setSsoLevel(String ssoLevel)
    {
        this.ssoLevel = ssoLevel;
    }

    public String getSsoToken()
    {
        return ssoToken;
    }

    public void setSsoToken(String ssoToken)
    {
        this.ssoToken = ssoToken;
    }

    public SessionAttributes getSessionAttributes()
    {
        return sessionAttributes;
    }

    public void setSessionAttributes(SessionAttributes sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public String getLbCookie()
    {
        return lbCookie;
    }

    public void setLbCookie(String lbCookie)
    {
        this.lbCookie = lbCookie;
    }

}
