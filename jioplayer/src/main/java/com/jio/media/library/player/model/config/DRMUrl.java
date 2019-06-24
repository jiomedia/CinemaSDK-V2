
package com.jio.media.library.player.model.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DRMUrl {

    @SerializedName("ios")
    @Expose
    private String ios;
    @SerializedName("android")
    @Expose
    private String android;
    @SerializedName("default")
    @Expose
    private String _default;

    public String getIos() {
        return ios;
    }

    public void setIos(String ios) {
        this.ios = ios;
    }

    public String getAndroid() {
        return android;
    }

    public void setAndroid(String android) {
        this.android = android;
    }

    public String getDefault() {
        return _default;
    }

    public void setDefault(String _default) {
        this._default = _default;
    }

}
