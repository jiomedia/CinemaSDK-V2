package com.jio.media.library.player.network;

class RequestUtils extends DeviceUtils {
    static String appName = "RJIL_JioCinema";
    static String apiKey = "l7xx8bd12e956b7a47f48a4e56603bf85bae";

    final long REQUEST_TIMEOUT = 30;

    String getSecureBaseUrl() {
        return "https://api.jio.com";
    }

    String getProdBaseUrl() {
        return "https://prod.media.jio.com";
    }

    String getAnalyticsUrl() {
        return "https://collect.media.jio.com";
    }

}