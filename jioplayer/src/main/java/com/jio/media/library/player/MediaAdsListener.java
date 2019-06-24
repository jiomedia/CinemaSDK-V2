package com.jio.media.library.player;

public interface MediaAdsListener
{
    void onAdPlay();

    void onAdPause();

    void onAdResume();

    void onAdEnded();

    void onAdError();

    void onAdClicked();

    void onAdTapped();

    void onBuffering();
}
