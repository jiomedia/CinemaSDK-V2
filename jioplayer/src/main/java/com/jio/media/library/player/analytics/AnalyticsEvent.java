package com.jio.media.library.player.analytics;

import android.os.Build;
import android.text.TextUtils;

import com.jio.media.library.player.BuildConfig;
import com.jio.media.library.player.model.LoginData;
import com.jio.media.library.player.network.INetworkResultListener;
import com.jio.media.library.player.network.WebServiceConnector;
import com.jio.media.library.player.utils.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.Headers;
import okhttp3.HttpUrl;

import static com.jio.media.library.player.analytics.AnalyticsConstant.JCANALYTICSEVENT_LOGIN;
import static com.jio.media.library.player.analytics.AnalyticsConstant.JCANALYTICSEVENT_MEDIAEND;
import static com.jio.media.library.player.analytics.AnalyticsConstant.JCANALYTICSEVENT_MEDIAERROR;
import static com.jio.media.library.player.analytics.AnalyticsConstant.JCANALYTICSEVENT_MEDIASTART;
import static com.jio.media.library.player.analytics.AnalyticsConstant.JCANALYTICSEVENT_SDK_LAUNCH;
import static com.jio.media.library.player.analytics.AnalyticsConstant.JCANALYTICSEVENT_SDK_LOADED;
import static com.jio.media.library.player.analytics.AnalyticsConstant.JCANALYTICSEVENT_WEB_SERVICES;
import static com.jio.media.library.player.analytics.AnalyticsConstant.PLATFORM;

public class AnalyticsEvent implements INetworkResultListener
{
    private LoginData loginData;
    private static AnalyticsEvent analyticsEvent = null;
    private long currentTime = Calendar.getInstance().getTimeInMillis();

    public static AnalyticsEvent getInstance()
    {
        if (analyticsEvent == null) {
            analyticsEvent = new AnalyticsEvent();
        }
        return analyticsEvent;
    }

    public void setLoginData(LoginData loginData)
    {
        this.loginData = loginData;
    }

    public void getLoginData(HashMap<String, Object> finalHashMap)
    {
        if (loginData != null) {
            if (!TextUtils.isEmpty(loginData.getSubscriberId())) {
                finalHashMap.put("crmid", loginData.getSubscriberId());
            } else {
                finalHashMap.put("crmid", "");
            }

            if (!TextUtils.isEmpty(loginData.getUniqueId())) {
                finalHashMap.put("idamid", loginData.getUniqueId());
            } else {
                finalHashMap.put("idamid", "");
            }

            if (!TextUtils.isEmpty(loginData.getDeviceInfo().getInfo().getImsi())) {
                finalHashMap.put("imsi", loginData.getDeviceInfo().getInfo().getImsi());
            } else {
                finalHashMap.put("imsi", "");
            }

            if (!TextUtils.isEmpty(loginData.getName())) {
                finalHashMap.put("username", loginData.getName());
            } else {
                finalHashMap.put("username", "");
            }

            if (!TextUtils.isEmpty(loginData.getuId())) {
                finalHashMap.put("uid", loginData.getuId());//jioId
            } else {
                finalHashMap.put("uid", "");//jioId
            }
        }
    }

    //all default param
    public HashMap<String, Object> getFinalEventHashMap(HashMap<String, Object> proHashMap, String eventKey)
    {
        HashMap<String, Object> finalHashMap = new HashMap<>();
        finalHashMap.put("appname", Utils.getAppName());
        finalHashMap.put("akey", "109153001");
        finalHashMap.put("avn", BuildConfig.VERSION_CODE);
        finalHashMap.put("c", Utils.getCarrierName());
        getLoginData(finalHashMap);
        finalHashMap.put("did", Utils.getUDID());
        finalHashMap.put("dtpe", Utils.getDeviceType());
        finalHashMap.put("mnu", Build.MANUFACTURER);
        finalHashMap.put("mod", Build.MODEL);
        finalHashMap.put("nwk", Utils.getNetworkType());
        finalHashMap.put("osv", Build.VERSION.RELEASE);
        finalHashMap.put("pf", PLATFORM);
        finalHashMap.put("rtc", currentTime);
        finalHashMap.put("key", eventKey);
        finalHashMap.put("pro", proHashMap);

        Logger.d("getFinalEventHashMap" + new JSONObject(finalHashMap));
        return finalHashMap;
    }

    //1.sdk launch enent
    public HashMap<String, Object> getSdkLaunchEventForInternalAnalytics(String sdklaunch)
    {
        HashMap<String, Object> eventHashMap = new HashMap<>();
        eventHashMap.put("sdklaunch", sdklaunch);

        Logger.d("sdk_launch_event" + new JSONObject(eventHashMap));
        return getFinalEventHashMap(eventHashMap, JCANALYTICSEVENT_SDK_LAUNCH);
    }

    public void sendSdkLaunchEventForInternalAnalytics()
    {
        HashMap<String, Object> eventHashMap = getSdkLaunchEventForInternalAnalytics("sdklaunch");
        WebServiceConnector.getInstance().sendAnalyticsAPIForBegin(this, 201, eventHashMap);
    }

    //2.sdkloaded events
    public HashMap<String, Object> getSdkLoadedEventForInternalAnalytics(boolean load, boolean isLoginInfoAvailable, String loginType)
    {
        HashMap<String, Object> eventHashMap = new HashMap<>();
        eventHashMap.put("load", load);
        eventHashMap.put("islogininfoavailable", isLoginInfoAvailable);
        eventHashMap.put("logintype", loginType);

        Logger.d("sdk_loaded_event" + new JSONObject(eventHashMap));
        return getFinalEventHashMap(eventHashMap, JCANALYTICSEVENT_SDK_LOADED);
    }

    public void sendSdkLoadedEventForInternalAnalytics(boolean load, boolean isLoginInfoAvailable, String loginType)
    {
        HashMap<String, Object> eventHashMap = getSdkLoadedEventForInternalAnalytics(load, isLoginInfoAvailable, loginType);
        WebServiceConnector.getInstance().sendEventForInternalAnalytics(this, 200, eventHashMap);
    }

    //3.media start event
    public HashMap<String, Object> getMediaStartEventForInternalAnalytics(String contentId, int from, String source)
    {
        HashMap<String, Object> eventHashMap = new HashMap<>();
        eventHashMap.put("cid", contentId);
        eventHashMap.put("from", from);
        eventHashMap.put("source", source);

        Logger.d("media_start_event" + new JSONObject(eventHashMap));
        return getFinalEventHashMap(eventHashMap, JCANALYTICSEVENT_MEDIASTART);
    }

    public void sendMediaStartEventForInternalAnalytics(String contentId, int from, String source)
    {
        HashMap<String, Object> eventHashMap = getMediaStartEventForInternalAnalytics(contentId, from, source);
        WebServiceConnector.getInstance().sendEventForInternalAnalytics(this, 200, eventHashMap);
    }

    //4.media end event
    public HashMap<String, Object> getMediaEndEventForInternalAnalytics(String bitrate, int bufferCount, long bufferDuration, String contentId, String title, int type, int epos, JSONArray genre, String contentp, String language, String playList, String rowPosition, String screenName, String source, int ts, boolean audioChanged, boolean subtitleChanged, String subtitleviewed, String quality, String defaultLang, ArrayList<String> displayLang)
    {

        HashMap<String, Object> eventHashMap = new HashMap<>();
        eventHashMap.put("bitrate", bitrate);
        eventHashMap.put("bc", bufferCount);
        eventHashMap.put("bd", bufferDuration);
        eventHashMap.put("cid", contentId);
        eventHashMap.put("title", title);
        eventHashMap.put("type", type);
        eventHashMap.put("epos", epos);
        eventHashMap.put("genre", toString(genre));
        eventHashMap.put("contentp", contentp);
        if (defaultLang != null && displayLang.size() == 0) {
            eventHashMap.put("language", language);
        }
        eventHashMap.put("Playlist", playList);
        eventHashMap.put("row_position", rowPosition);
        eventHashMap.put("screen_name", screenName);
        eventHashMap.put("source", source);
        eventHashMap.put("ts", ts);
        eventHashMap.put("audiochanged", audioChanged);
        eventHashMap.put("subtitlechanged", subtitleChanged);
        eventHashMap.put("subtitleviewed", subtitleviewed);
        eventHashMap.put("quality", quality);
        Logger.d("media_end_event" + new JSONObject(eventHashMap));
        return getFinalEventHashMap(eventHashMap, JCANALYTICSEVENT_MEDIAEND);

    }

    private String toString(JSONArray array)
    {
        String s = "";
        if (array == null || array.length() == 0) {
            return s;
        }
        for (int a = 0; a < array.length(); a++) {
            try {
                if (s.length() == 0) {
                    s = array.getString(a);
                } else {
                    s = s + "," + array.getString(a);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return s;
    }

    public void sendMediaEndEventForInternalAnalytics(String bitrate, int bufferCount, long bufferDuration, String contentId, String title, int type, int epos, JSONArray genre, String contentp, String language, String playList, String rowPosition, String screenName, String source, int ts, boolean audioChanged, boolean subtitleChanged, String subtitleviewed, String quality, String defaultLang, ArrayList<String> displayLang)
    {
        HashMap<String, Object> eventHashMap = getMediaEndEventForInternalAnalytics(bitrate, bufferCount, bufferDuration, contentId, title, type, epos, genre, contentp, language, playList, rowPosition, screenName, source, ts, audioChanged, subtitleChanged, subtitleviewed, quality, defaultLang, displayLang);
        WebServiceConnector.getInstance().sendEventForInternalAnalytics(this, 200, eventHashMap);
    }

    //6.web service event (failure and success of url)
    public HashMap<String, Object> getWebServicesEventForInternalAnalytics(String date, long duration, long endTime, long startTime, int statusCode, String success, HttpUrl url)
    {
        HashMap<String, Object> eventHashMap = new HashMap<>();
        eventHashMap.put("date", date);
        eventHashMap.put("duration", duration);
        eventHashMap.put("endTime", endTime);
        eventHashMap.put("startTime", startTime);
        eventHashMap.put("statusCode", statusCode);
        eventHashMap.put("success", success);
        eventHashMap.put("url", url.uri().toString());
        Logger.d("web_services_event" + new JSONObject(eventHashMap));
        return getFinalEventHashMap(eventHashMap, JCANALYTICSEVENT_WEB_SERVICES);
    }

    public void sendWebServicesEventForInternalAnalytics(String date, long duration, long endTime, long startTime, int statusCode, String success, HttpUrl url)
    {
        HashMap<String, Object> eventHashMap = getWebServicesEventForInternalAnalytics(date, duration, endTime, startTime, statusCode, success, url);
        WebServiceConnector.getInstance().sendEventForInternalAnalytics(this, 200, eventHashMap);
    }

    //7.login event (when SDK call internal ZLA)
    public HashMap<String, Object> getLoginEventForInternalAnalytics(boolean iszla, String errormessage, int errorcode)
    {
        HashMap<String, Object> eventHashMap = new HashMap<>();
        eventHashMap.put("iszla", iszla);
        eventHashMap.put("errormessage", errormessage);
        eventHashMap.put("errorcode", errorcode);
        Logger.d("login_event" + new JSONObject(eventHashMap));
        return getFinalEventHashMap(eventHashMap, JCANALYTICSEVENT_LOGIN);
    }

    public void sendLoginEventForInternalAnalytics(boolean iszla, String errormessage, int errorcode)
    {
        HashMap<String, Object> eventHashMap = getLoginEventForInternalAnalytics(iszla, errormessage, errorcode);
        WebServiceConnector.getInstance().sendEventForInternalAnalytics(this, 200, eventHashMap);
    }

    //10. media_error event
    public HashMap<String, Object> getMediaErrorEventForInternalAnalytics(String bitrate, String quality, int type, String cid, int code, String msg, String desc, String failure)
    {
        HashMap<String, Object> eventHashMap = new HashMap<>();
        eventHashMap.put("bitrate", bitrate);
        eventHashMap.put("quality", quality);
        eventHashMap.put("type", type);
        eventHashMap.put("cid", cid);
        eventHashMap.put("code", code);
        eventHashMap.put("msg", msg);
        eventHashMap.put("desc", desc);
        eventHashMap.put("failure", failure);
        Logger.d("media_error_event" + new JSONObject(eventHashMap));
        return getFinalEventHashMap(eventHashMap, JCANALYTICSEVENT_MEDIAERROR);
    }

    public void sendMediaErrorEventForInternalAnalytics(String bitrate, String quality, int type, String cid, int code, String msg, String desc, String failure)
    {
        HashMap<String, Object> eventHashMap = getMediaErrorEventForInternalAnalytics(bitrate, quality, type, cid, code, msg, desc, failure);
        WebServiceConnector.getInstance().sendEventForInternalAnalytics(this, 200, eventHashMap);
    }

    @Override
    public void onSuccess(String response, Headers headers, int requestCode)
    {
        switch (requestCode) {
            case 200:
                Logger.d("Internal analytics success");
                break;
            case 201:
                Logger.d("Internal analytics begin success");
                break;
            case 202:
                Logger.d("Internal analytics end success");
                break;
        }
    }

    @Override
    public void onFailed(String message, int responseCode, int requestCode)
    {
        switch (requestCode) {
            case 200:
                Logger.d("Internal analytics failed");
                break;
            case 201:
                Logger.d("Internal analytics begin failed");
                break;
            case 202:
                Logger.d("Internal analytics end failed");

                break;
        }
    }
}
