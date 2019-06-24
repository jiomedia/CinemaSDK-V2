package com.jio.media.library.player.model.playbackrights;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PlayBackRights
{
    @SerializedName("video")
    @Expose
    private Video video;

    @SerializedName("contentName")
    @Expose
    private String contentName;

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }
}
