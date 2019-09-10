package com.jio.media.library.player.network;

import android.content.Context;

import com.google.gson.Gson;
import com.jio.media.library.player.model.LoginData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class WebServiceConnector extends WebServiceClient {
    private static WebServiceConnector serviceConnector;

    private WebServiceConnector() {
        super();
    }

    public static WebServiceConnector getInstance() {
        if (serviceConnector == null) {
            serviceConnector = new WebServiceConnector();
        }

        return serviceConnector;
    }

    public void getZlaLoginData(final INetworkResultListener apiResultListener, final int requestCode, Context context) {
        ApiInterface service = getRetrofit().create(ApiInterface.class);
        service.zlaLoginResponse(appName, getDeviceName(), DEVICE_NAME, getDeviceId(context)).enqueue(new APICallback(requestCode, apiResultListener));
    }

    public void getLoginViaSubIdData(final String ssoToken, final String subscriberId, final String deviceId, final INetworkResultListener apiResultListener, final int requestCode) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("subscriberId", subscriberId);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        ApiInterface service = getRetrofitProd().create(ApiInterface.class);
        service.loginViaSubIdResponse(ssoToken, deviceId, DEVICE_TYPE, body).enqueue(new APICallback(requestCode, apiResultListener));
    }

    public void getRefreshTokenData(final INetworkResultListener apiResultListener, final int requestCode, String deviceInfo) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (deviceInfo));

        ApiInterface service = getRetrofitSecure().create(ApiInterface.class);
        service.refreshTokenResponse(body).enqueue(new APICallback(requestCode, apiResultListener));
    }

    public void getConfig(final INetworkResultListener apiResultListener, final int requestCode) {
        ApiInterface service = getRetrofitProd().create(ApiInterface.class);
        service.getConfig().enqueue(new APICallback(requestCode, apiResultListener));
    }

    public void getPlayBackRightData(final INetworkResultListener apiResultListener, final int requestCode, String requestBody, String contentId, LoginData loginData) {
        if (loginData != null) {
            String ssoToken = loginData.getSsoToken();
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (requestBody));

            ApiInterface service = getRetrofitProd().create(ApiInterface.class);
            service.getPlayBackRightData(ssoToken, contentId, body).enqueue(new APICallback(requestCode, apiResultListener));
        }
    }

    public void getMataMoreData(final INetworkResultListener apiResultListener, final int requestCode, String uniqueId, String contentId) {
        ApiInterface service = getRetrofitProd().create(ApiInterface.class);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), (uniqueId));
        service.getMetaMore(true, contentId, body).enqueue(new APICallback(requestCode, apiResultListener));

    }

    public void sendEventForInternalAnalytics(final INetworkResultListener apiResultListener, final int requestCode,
                                              HashMap<String, Object> map) {
        ApiInterface service = getRetrofitAnalytics().create(ApiInterface.class);

        Gson gson = new Gson();
        String mapStr = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), mapStr);

        service.analyticsAPIForEvent(body).enqueue(new APICallback(requestCode, apiResultListener));
    }

    public void sendAnalyticsAPIForBegin(final INetworkResultListener apiResultListener, final int requestCode,
                                         HashMap<String, Object> map) {
        ApiInterface service = getRetrofitAnalytics().create(ApiInterface.class);

        Gson gson = new Gson();
        String mapStr = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), mapStr);

        service.analyticsAPIForBegin(body).enqueue(new APICallback(requestCode, apiResultListener));
    }

    public void sendAnalyticsAPIForEnd(final INetworkResultListener apiResultListener, final int requestCode,
                                       HashMap<String, Object> map) {
        ApiInterface service = getRetrofitAnalytics().create(ApiInterface.class);

        Gson gson = new Gson();
        String mapStr = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), mapStr);

        service.analyticsAPIForEnd(body).enqueue(new APICallback(requestCode, apiResultListener));
    }

}
