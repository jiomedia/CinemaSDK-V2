package com.jio.media.library.player;

import android.os.Bundle;

public interface MediaPlayerControl
{
    void createPlayer(boolean isToPrepare);

    void preparePlayer();

    void releasePlayer();

    void releaseAdsLoader();

    void updateVideoUrls(String... urls);

    void playerPause();

    void playerPlay();

    void playerNext();

    void playerPrevious();

    void seekTo(int windowIndex, long positionMs);

    void seekToDefaultPosition();

    void setExoPlayerEventsListener(MediaPlayerListener pExoPlayerListenerListener);

    void setExoAdListener(MediaAdsListener exoAdListener);

    void setExoThumbListener(MediaThumbListener exoThumbListener);

    void onActivityStart();

    void onActivityResume();

    void onActivityPause();

    void onActivityStop();

    void onActivityDestroy();

    void onSaveInstanceState(Bundle outState);

    void playerBlock();

    void playerUnBlock();
}
