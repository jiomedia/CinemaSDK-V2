package com.jio.media.library.player.analytics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.jio.media.library.player.R;

public class Utils
{
    private static String udid;
    private static String ntwrkType;
    private static String appName;
    private static String carrierName;
    private static String deviceType;

    public static void fillData(Context context)
    {
        setUdid(context);
        setNetworkType(context);
        setAppName(context);
        setCarrierName(context);
        setDeviceType(context);
    }

    public static String getUDID()
    {
        return udid;
    }

    @SuppressLint("HardwareIds")
    private static void setUdid(Context context)
    {
        udid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getNetworkType()
    {
        return ntwrkType;
    }

    private static void setNetworkType(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            ntwrkType = "offline";
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            ntwrkType = "wifi";
        } else {
            ntwrkType = "mobile";
        }
    }

    public static String getAppName()
    {
        return appName;
    }

    private static void setAppName(Context context)
    {
        appName = context.getResources().getString(R.string.app_name);
    }

    public static String getCarrierName()
    {
        return carrierName;
    }

    private static void setCarrierName(Context context)
    {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        carrierName = manager.getSimOperatorName();
    }

    public static String getDeviceType()
    {
        return deviceType;
    }

    private static void setDeviceType(Context context)
    {
        if (isTablet(context)) {
            deviceType = "T";
        } else {
            deviceType = "S";
        }
    }

    public static boolean isTablet(Context context)
    {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String getAnalyticsDescription(String desc)
    {
        return desc.replaceAll("=", "/");
    }
}
