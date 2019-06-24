package com.jio.media.library.player.network;

import okhttp3.Headers;

public interface INetworkResultListener {

    void onSuccess(String response, Headers headers, int requestCode);

    void onFailed(String message, int responseCode, int requestCode);
}
