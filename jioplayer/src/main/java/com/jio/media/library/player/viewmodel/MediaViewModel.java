package com.jio.media.library.player.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jio.media.library.player.analytics.AnalyticsEvent;
import com.jio.media.library.player.analytics.Utils;
import com.jio.media.library.player.model.DeviceInfo;
import com.jio.media.library.player.model.LoginData;
import com.jio.media.library.player.model.RefreshDeviceInfo;
import com.jio.media.library.player.model.config.AutoPlay;
import com.jio.media.library.player.model.config.ConfigResponse;
import com.jio.media.library.player.model.config.Url;
import com.jio.media.library.player.model.information.VideoInformation;
import com.jio.media.library.player.model.loginviasubid.LoginDetail;
import com.jio.media.library.player.model.playbackrights.PlayBackRights;
import com.jio.media.library.player.network.INetworkResultListener;
import com.jio.media.library.player.network.RefreshTokenData;
import com.jio.media.library.player.network.WebServiceConnector;
import com.jio.media.library.player.token.TokenController;
import com.jio.media.library.player.utils.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class MediaViewModel extends AndroidViewModel implements INetworkResultListener
{
    private LoginData loginData;
    private ConfigResponse configResponse;
    private MutableLiveData<VideoInformation> informationLiveData;

    private static final int GET_CONFIG = 100;
    private static final int LOGIN_VIA_SUB_ID = 101;
    private static final int PLAYBACK_RIGHTS = 102;
    private static final int REFRESH_SSO = 103;

    public MediaViewModel(@NonNull Application application)
    {
        super(application);
        informationLiveData = new MutableLiveData<>();
        Utils.fillData(application.getApplicationContext());
    }

    public MutableLiveData<VideoInformation> getInformationLiveData()
    {
        return informationLiveData;
    }

    public void startMediaPlayer(String loginInfo)
    {
        if (!TextUtils.isEmpty(loginInfo))
        {
            loginData = new Gson().fromJson(loginInfo, LoginData.class);
            if (loginData != null) {
                AnalyticsEvent.getInstance().setLoginData(loginData);
                AnalyticsEvent.getInstance().sendSdkLaunchEventForInternalAnalytics();
                String type = loginData.getLoginType() == 1 ? "unpw" : loginData.getLoginType() == 2 ? "zla" : "otp";
                AnalyticsEvent.getInstance().sendSdkLoadedEventForInternalAnalytics(true, true, type);
                getConfigDetails();
            }
        }
    }

    private void getConfigDetails() {
        Logger.d("Calling getConfig api");
        WebServiceConnector.getInstance().getConfig(this, GET_CONFIG);
    }

    private void checkLoginInfo(String contentId, String sid, String tokenId, int cdnExpiry, boolean hasEncryption)
    {
        if (loginData != null) {
            Logger.d("uniqueId: "+loginData.getUniqueId()+ " uid: "+loginData.getuId());
            if (!TextUtils.isEmpty(loginData.getUniqueId()) && !TextUtils.isEmpty(loginData.getuId())) {
                TokenController controller = TokenController.getInstance();
                controller.setSsoToken(loginData.getSsoToken());
                controller.setSid(sid);
                controller.setTokenId(tokenId);
                controller.setSecureRandomToken("Android");
                controller.setExpireTime(cdnExpiry);
                controller.setEncryption(hasEncryption);

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("uniqueId", loginData.getUniqueId());
                    jsonObject.put("bitrateProfile", "xxhdpi");
                    jsonObject.put("deviceType", "phone");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

                WebServiceConnector.getInstance().getPlayBackRightData(this, PLAYBACK_RIGHTS, jsonObject.toString(), contentId, loginData);
            } else {
                WebServiceConnector.getInstance().getLoginViaSubIdData(loginData.getSsoToken(), loginData.getSubscriberId(), loginData.getDeviceInfo().getInfo().getAndroidId(), this, LOGIN_VIA_SUB_ID);
            }
        }
    }

    private void configSuccess(String response) {
        if (!TextUtils.isEmpty(response)) {
            Logger.d("getConfig api success");
            configResponse = new Gson().fromJson(response, ConfigResponse.class);
            handlePlayBack(configResponse);
        } else {
            Logger.d("getConfig api null");
        }
    }

    private void handlePlayBack(ConfigResponse configResponse) {
        if (configResponse != null) {
            Url url = configResponse.getUrl();
            AutoPlay autoPlay = configResponse.getUrl().getAutoPlay();
            checkLoginInfo(autoPlay.getContentId(), autoPlay.getCdnUrlPass(), url.getTid(), url.getCdnUrlExpiry(), url.getCdnEncryptionFlag());
        }
    }

    private void loginViaSubIdSuccess(String response) {
        if (!TextUtils.isEmpty(response)) {
            Logger.d("loginViaSubId api success");
            LoginDetail loginDetail = new Gson().fromJson(response, LoginDetail.class);
            if (loginDetail != null) {
                if (loginData != null) {
                    loginData.setUniqueId(loginDetail.getUnique());
                    loginData.setuId(loginDetail.getUsername());
                    handlePlayBack(configResponse);
                }
            }
        } else {
            Logger.d("loginViaSubId api null");
        }
    }

    private void playbackRightsSuccess(String response) {
        if (!TextUtils.isEmpty(response)) {
            Logger.d("playbackRights api success");
            try {
                PlayBackRights playBackRights = new Gson().fromJson(response, PlayBackRights.class);
                if (playBackRights != null) {
                    VideoInformation videoInformation = new VideoInformation();
                    videoInformation.setName(playBackRights.getContentName());
                    videoInformation.setUrl(playBackRights.getVideo().getAuto());
                    if (configResponse != null) {
                        AutoPlay autoPlay = configResponse.getUrl().getAutoPlay();
                        if (autoPlay != null) {
                            videoInformation.setContentId(autoPlay.getContentId());
                            videoInformation.setVideoTitle(autoPlay.getVideoTitle());
                            videoInformation.setVideoSubTitle(autoPlay.getVideoSubTitle());
                            videoInformation.setVideoDescription(autoPlay.getVideoDesc());
                            videoInformation.setBannerImage(configResponse.getUrl().getImage()+autoPlay.getBannerImage());
                            videoInformation.setUrlDownload(autoPlay.getJCdownload());
                            videoInformation.setUrlRedirect(autoPlay.getJCredirect());
                        }
                    }
                    informationLiveData.setValue(videoInformation);
                }
            } catch (Exception ex) {
              ex.printStackTrace();
            }
        } else {
            Logger.d("playbackRights api null");
        }
    }

    private void refreshToken()
    {
        if (loginData != null) {
            refreshToken(loginData.getLoginType() == 2);
        }
    }

    private void refreshToken(boolean isZla) {
        Logger.d("Refresh SSO Request"+ getRefreshJsonData(isZla));
        WebServiceConnector.getInstance().getRefreshTokenData(this, REFRESH_SSO, getRefreshJsonData(isZla));
    }

    private String getRefreshJsonData(boolean isZla) {
        RefreshDeviceInfo info = new RefreshDeviceInfo();

        if (loginData != null) {
            DeviceInfo myJiodeviceInfo = loginData.getDeviceInfo();
            DeviceInfo deviceInfo = new DeviceInfo();

            deviceInfo.setJToken(myJiodeviceInfo.getJToken());
            deviceInfo.setConsumptionDeviceName(myJiodeviceInfo.getConsumptionDeviceName());
            deviceInfo.setInfo(myJiodeviceInfo.getInfo());

            if (isZla) {
                deviceInfo.getInfo().setImsi("");
            } else {
                deviceInfo.getInfo().setAndroidId("");
            }
            info.setDeviceInfo(deviceInfo);
        }
        return new Gson().toJson(info);
    }

    private void refreshTokenSuccess(String response) {
        if (!TextUtils.isEmpty(response)) {
            Logger.d("refresh token api success");
            RefreshTokenData refreshTokenData = new Gson().fromJson(response, RefreshTokenData.class);
            if (refreshTokenData != null) {
                loginData.setSsoToken(refreshTokenData.getSsoToken());
                if (configResponse != null) {
                    handlePlayBack(configResponse);
                } else {
                    getConfigDetails();
                }
            }
        } else {
            Logger.d("refresh token api null");
        }
    }

    @Override
    public void onSuccess(String response, Headers headers, int requestCode)
    {
        Logger.d(response);
        if (requestCode == GET_CONFIG) {
            Logger.d("ConfigAPIResponse: "+response);
            configSuccess(response);
        } else if (requestCode == LOGIN_VIA_SUB_ID) {
            loginViaSubIdSuccess(response);
        } else if (requestCode == PLAYBACK_RIGHTS) {
            playbackRightsSuccess(response);
        } else if (requestCode == REFRESH_SSO) {
            refreshTokenSuccess(response);
        }
    }

    @Override
    public void onFailed(String message, int responseCode, int requestCode)
    {
        Logger.d(message);
        if (requestCode == GET_CONFIG) {
            Logger.d("getConfig api failed");
        } else if (requestCode == LOGIN_VIA_SUB_ID) {
            Logger.d("loginviasubid api failed");
            if (responseCode == 400) {
                Logger.d("loginviasubid api failed refreshing sso token");
                refreshToken();
            }
        } else if (requestCode == PLAYBACK_RIGHTS) {
            Logger.d("playback rights api failed");
            if (responseCode == 419) {
                Logger.d("playback rights api failed refreshing sso token");
                refreshToken();
            }
        } else if (requestCode == REFRESH_SSO) {
            Logger.d("refresh api failed");
        }
    }
}