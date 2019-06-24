package com.jio.media.library.player.model.playbackrights;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subscription {
    @SerializedName("isSubscribed")
    @Expose
    private Boolean isSubscribed;

    public Boolean getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(Boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "isSubscribed=" + isSubscribed +
                '}';
    }
}
