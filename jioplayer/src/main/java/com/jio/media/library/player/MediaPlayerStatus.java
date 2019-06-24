package com.jio.media.library.player;

public interface MediaPlayerStatus
{
    boolean isPlayerVideoMuted();

    int getCurrentWindowIndex();

    long getCurrentPosition();

    long getDuration();

    boolean isPlayerCreated();

    boolean isPlayerPrepared();

    boolean isPlayingAd();
}
