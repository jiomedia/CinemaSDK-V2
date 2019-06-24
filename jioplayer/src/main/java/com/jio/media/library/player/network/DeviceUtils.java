package com.jio.media.library.player.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import org.json.JSONException;
import org.json.JSONObject;

class DeviceUtils
{
    final String DEVICE_TYPE = "phone";
    final String DEVICE_NAME = "android";

    String getUnpwJsonString(Context context, String username, String password)
    {
        try {
            JSONObject json = new JSONObject();
            json.put("identifier", username);
            json.put("password", password);

            json.put("rememberUser", "T");
            json.put("upgradeAuth", "Y");
            json.put("returnSessionDetails", "T");

            JSONObject deviceInfo = new JSONObject();
            deviceInfo.put("consumptionDeviceName", getDeviceName());
            deviceInfo.put("info", getDeviceInformation(context));
            json.put("deviceInfo", deviceInfo);
            return json.toString();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    String getSendOTPJsonString(String mobileNumber)
    {
        try {
            JSONObject json = new JSONObject();
            json.put("identifier", mobileNumber);
            json.put("otpIdentifier", mobileNumber);
            json.put("action", "otpbasedauthn");
            return json.toString();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    String getVerifyOTPJsonString(Context context, String mobileNumber, String otpNumber)
    {
        try {
            JSONObject json = new JSONObject();
            json.put("identifier", mobileNumber);
            json.put("otp", otpNumber);

            json.put("rememberUser", "T");
            json.put("upgradeAuth", "Y");
            json.put("returnSessionDetails", "T");

            JSONObject deviceInfo = new JSONObject();
            deviceInfo.put("consumptionDeviceName", getDeviceName());
            deviceInfo.put("info", getDeviceInformation(context));
            json.put("deviceInfo", deviceInfo);
            return json.toString();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    JSONObject getDeviceInformation(String deviceId) throws JSONException
    {
        JSONObject info = new JSONObject();
        info.put("type", "android");
        info.put("androidId", deviceId);

        return info;
    }

    private JSONObject getDeviceInformation(Context context) throws JSONException
    {
        JSONObject info = new JSONObject();
        info.put("type", "android");
        info.put("androidId", getDeviceId(context));

        return info;
    }

    @SuppressLint("HardwareIds")
    String getDeviceId(Context context)
    {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    String getDeviceName()
    {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        } else {
            return manufacturer + " " + model;
        }
    }
}
