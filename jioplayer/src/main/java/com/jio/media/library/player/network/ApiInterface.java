package com.jio.media.library.player.network;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface ApiInterface
{
    @GET("/v2/users/me")
    Call<ResponseBody> zlaLoginResponse(@Query("app-name") String name, @Header("x-consumption-device-name") String consumptionDeviceName, @Header("x-device-type") String deviceType, @Header("x-android-id") String deviceId);

    @POST("/apis/common/v3/login/loginviasubid")
    Call<ResponseBody> loginViaSubIdResponse(@Header("ssotoken") String ssoToken, @Header("deviceid") String deviceId, @Header("devicetype") String deviceType, @Body RequestBody body);

    @POST("/v3/dip/user/authtoken/verify")
    Call<ResponseBody> refreshTokenResponse(@Body RequestBody body);

    @POST("/sdk/apis/common/v2.7/playbackrights/get/{contentID}")
    Call<ResponseBody> getPlayBackRightData(@Header("ssotoken") String ssoToken, @Path("contentID") String contentId, @Body RequestBody body);

    @GET("/v1/network/check")
    Call<ResponseBody> checkNetwork(@Query("app-name") String name);

    @POST("/postdata/event")
    Call<ResponseBody> analyticsAPIForEvent(@Body RequestBody body);

    @POST("/postdata/B")
    Call<ResponseBody> analyticsAPIForBegin(@Body RequestBody body);

    @POST("/postdata/E")
    Call<ResponseBody> analyticsAPIForEnd(@Body RequestBody body);

    @GET("sdk/apis/common/v2.7/getconfig/geturl/39ee6ded40812c593ed8")
    Call<ResponseBody> getConfig();

    @POST("apis/common/v2.7/metamore/get/{contentID}")
    Call<ResponseBody> getMetaMore(@Header("x-multilang") boolean multiLang,@Path("contentID") String contentId,@Body RequestBody uniqueId);
}