package com.jio.media.library.player.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefreshTokenData
{
    @SerializedName("ssoToken")
    @Expose
    private String ssoToken;

    @SerializedName("lbCookie")
    @Expose
    private String lbCookie;

    public String getSsoToken() {
        return ssoToken;
    }

    public void setSsoToken(String ssoToken) {
        this.ssoToken = ssoToken;
    }

    public String getLbCookie() {
        return lbCookie;
    }

    public void setLbCookie(String lbCookie) {
        this.lbCookie = lbCookie;
    }
}
