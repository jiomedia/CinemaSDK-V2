
package com.jio.media.library.player.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("androidId")
    @Expose
    private String androidId;

    @SerializedName("mac")
    @Expose
    private String mac;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("imsi")
    @Expose
    private String imsi;

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public Info withAndroidId(String androidId) {
        this.androidId = androidId;
        return this;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Info withMac(String mac) {
        this.mac = mac;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Info withType(String type) {
        this.type = type;
        return this;
    }

}
