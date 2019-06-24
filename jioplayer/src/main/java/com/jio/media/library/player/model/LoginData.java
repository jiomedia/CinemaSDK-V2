
package com.jio.media.library.player.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {

    @SerializedName("deviceInfo")
    @Expose
    private DeviceInfo deviceInfo;

    @SerializedName("ssoToken")
    @Expose
    private String ssoToken = "";

    @SerializedName("subscriberId")
    @Expose
    private String subscriberId = "";

    @SerializedName("uniqueId")
    @Expose
    private String uniqueId = "";

    @SerializedName("name")
    @Expose
    private String name = "";

    @SerializedName("loginType")
    @Expose
    private Integer loginType;

    @SerializedName("ssoExpired")
    @Expose
    private Boolean ssoExpired;

    @SerializedName("ssoLevel")
    @Expose
    private String ssoLevel = "";

    @SerializedName("uId")
    @Expose
    private String uId = "";

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public LoginData withDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
        return this;
    }

    public String getSsoToken() {
        return ssoToken;
    }

    public void setSsoToken(String ssoToken) {
        this.ssoToken = ssoToken;
    }

    public LoginData withSsoToken(String ssoToken) {
        this.ssoToken = ssoToken;
        return this;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public LoginData withSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public LoginData withUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LoginData withName(String name) {
        this.name = name;
        return this;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public LoginData withLoginType(Integer loginType) {
        this.loginType = loginType;
        return this;
    }

    public Boolean getSsoExpired() {
        return ssoExpired;
    }

    public void setSsoExpired(Boolean ssoExpired) {
        this.ssoExpired = ssoExpired;
    }

    public LoginData withSsoExpired(Boolean ssoExpired) {
        this.ssoExpired = ssoExpired;
        return this;
    }

    public String getSsoLevel() {
        return ssoLevel;
    }

    public void setSsoLevel(String ssoLevel) {
        this.ssoLevel = ssoLevel;
    }

    public LoginData withSsoLevel(String ssoLevel) {
        this.ssoLevel = ssoLevel;
        return this;
    }

}
