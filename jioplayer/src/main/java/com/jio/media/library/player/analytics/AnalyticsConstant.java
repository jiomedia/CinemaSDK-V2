package com.jio.media.library.player.analytics;

public interface AnalyticsConstant {

    String PLATFORM                            = "A";
    
    //Analytics Events key
    String JCANALYTICSEVENT_SDK_LAUNCH         = "sdklaunch";
    String JCANALYTICSEVENT_SDK_LOADED         = "sdkloaded";
    String JCANALYTICSEVENT_WEB_SERVICES       = "web_services";
    String JCANALYTICSEVENT_LOGIN              = "login";
    String JCANALYTICSEVENT_MEDIASTART         = "media_start";
    String JCANALYTICSEVENT_MEDIAERROR         = "media_error";
    String JCANALYTICSEVENT_MEDIAEND           = "media_end";
    String JCANALYTICSEVENT_BANNER_REDIRECTION = "banner_redirection";

    //Analytics Events Url
    String JCANALYTICSEVENT_URL                = "https://collect.media.jio.com/postdata/event";
    String JCANALYTICSEVENT_URL_BEGIN          = "https://collect.media.jio.com/postdata/B";
    String JCANALYTICSEVENT_URL_END            = "https://collect.media.jio.com/postdata/E";

}
