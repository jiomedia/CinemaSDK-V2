
package com.jio.media.library.player.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceInfo {

    @SerializedName("consumptionDeviceName")
    @Expose
    private String consumptionDeviceName;
    @SerializedName("jToken")
    @Expose
    private String jToken;
    @SerializedName("info")
    @Expose
    private Info info;

    public String getConsumptionDeviceName() {
        return consumptionDeviceName;
    }

    public void setConsumptionDeviceName(String consumptionDeviceName) {
        this.consumptionDeviceName = consumptionDeviceName;
    }

    public DeviceInfo withConsumptionDeviceName(String consumptionDeviceName) {
        this.consumptionDeviceName = consumptionDeviceName;
        return this;
    }

    public String getJToken() {
        return jToken;
    }

    public void setJToken(String jToken) {
        this.jToken = jToken;
    }

    public DeviceInfo withJToken(String jToken) {
        this.jToken = jToken;
        return this;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public DeviceInfo withInfo(Info info) {
        this.info = info;
        return this;
    }

}
