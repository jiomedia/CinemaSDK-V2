package com.jio.media.library.player;

public interface MediaPlayerListener
{
    void onLoadingStatusChanged(boolean isLoading, long bufferedPosition, int bufferedPercentage);

    void onPlayerPlaying(int currentWindowIndex);

    void onPlayerPaused(int currentWindowIndex);

    void onPlayerBuffering(int currentWindowIndex);

    void onPlayerStateEnded(int currentWindowIndex);

    void onPlayerStateIdle(int currentWindowIndex);

    void onPlayerError(String errorString);

    void createExoPlayerCalled(boolean isToPrepare);

    void releaseExoPlayerCalled();

    void onVideoResumeDataLoaded(int window, long position, boolean isResumeWhenReady);

    void onTracksChanged(int currentWindowIndex, int nextWindowIndex, boolean isPlayBackStateReady);

    void onMuteStateChanged(boolean isMuted);

    void onVideoTapped();

    boolean onPlayBtnTap();

    boolean onPauseBtnTap();

    void onFullScreenBtnTap();
}
