package com.jio.media.library.player.model.loginviasubid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginDetail
{
    @SerializedName("messageCode")
    @Expose
    private Integer messageCode;

    @SerializedName("ssoToken")
    @Expose
    private String ssoToken;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("username")
    @Expose
    private String username = "";

    @SerializedName("subscriberId")
    @Expose
    private String subscriberId = "";

    @SerializedName("unique")
    @Expose
    private String unique;

    @SerializedName("ssoExpired")
    @Expose
    private boolean ssoExpired;

    public boolean isSsoExpired() {
        return ssoExpired;
    }

    public void setSsoExpired(boolean ssoExpired) {
        this.ssoExpired = ssoExpired;
    }

    public Integer getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(Integer messageCode) {
        this.messageCode = messageCode;
    }

    public String getSsoToken() {
        return ssoToken;
    }

    public void setSsoToken(String ssoToken) {
        this.ssoToken = ssoToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }


    @Override
    public String toString() {
        return "LoginDetail{" +
                "messageCode=" + messageCode +
                ", ssoToken='" + ssoToken + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                '}';
    }
}
