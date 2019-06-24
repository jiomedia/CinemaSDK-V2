package com.jio.media.library.player.network;

import androidx.annotation.NonNull;

import com.jio.media.library.player.utils.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

class WebServiceClient extends RequestUtils
{
    private Retrofit retrofit;
    private Retrofit retrofitProd;
    private Retrofit retrofitSecure;

    private Retrofit retrofitAnalytics;

    WebServiceClient()
    {
        retrofit = new Retrofit.Builder().baseUrl(getUnSecureBaseUrl()).client(getOkHttpClient()).build();
        retrofitSecure = new Retrofit.Builder().baseUrl(getSecureBaseUrl()).client(getOkHttpClient()).build();
        retrofitProd = new Retrofit.Builder().baseUrl(getProdBaseUrl()).client(getOkHttpClient()).build();
        retrofitAnalytics = new Retrofit.Builder().baseUrl(getAnalyticsUrl()).client(getOkHttpClientForAnalytics()).build();
    }

    private OkHttpClient getOkHttpClientForAnalytics()
    {
        return new OkHttpClient.Builder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    private OkHttpClient getOkHttpClient()
    {
        Interceptor headerInterceptor = new Interceptor()
        {
            @Override
            public okhttp3.Response intercept(@NonNull Chain chain) throws IOException
            {
                Request request = chain.request()
                        .newBuilder()
                        .removeHeader("user-agent")
                        .addHeader("app-name", RequestUtils.appName)
                        .addHeader("x-api-key", RequestUtils.apiKey)
                        .addHeader("os", "android")
                        .addHeader("user-agent", "Android")
                        .addHeader("deviceType", "phone")
                        .addHeader("appkey", "06758e99be484fca56fb")
                        .addHeader("applicationidentifier", "904ea48a-588b-4d32-a16e-8e163bfa55ef")
                        .build();
                return chain.proceed(request);
            }
        };

        return new OkHttpClient.Builder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(headerInterceptor)
                .build();
    }

    Retrofit getRetrofitSecure()
    {
        return retrofitSecure;
    }

    Retrofit getRetrofit()
    {
        return retrofit;
    }

    public Retrofit getRetrofitAnalytics()
    {
        return retrofitAnalytics;
    }


    Retrofit getRetrofitProd()
    {
        return retrofitProd;
    }

    private void validateResponse(@NonNull Response<ResponseBody> response, INetworkResultListener apiResultListener, int requestCode, HttpUrl url)
    {
        try {
            ResponseBody data = response.body();
            ResponseBody error = response.errorBody();
            Headers headers = response.headers();

            if (response.isSuccessful()) {
                if (data != null) {
                    String responseString = data.string();
                    Logger.d("Ankit: "+responseString);
                    apiResultListener.onSuccess(responseString, headers, requestCode);
                }
            } else {
                if (error != null) {
                    apiResultListener.onFailed(error.string(), response.code(), requestCode);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void validateError(Throwable throwable, INetworkResultListener apiResultListener, int requestCode, HttpUrl url)
    {
        if (apiResultListener != null) {
            apiResultListener.onFailed(throwable.getMessage(), requestCode, requestCode);
        }
    }

    class APICallback implements Callback<ResponseBody>
    {
        private int requestCode;
        private INetworkResultListener apiResultListener;

        APICallback(int requestCode, INetworkResultListener apiResultListener)
        {
            this.requestCode = requestCode;
            this.apiResultListener = apiResultListener;
        }

        @Override
        public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response)
        {
            validateResponse(response, apiResultListener, requestCode, call.request().url());
        }

        @Override
        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable throwable)
        {
            validateError(throwable, apiResultListener, requestCode, call.request().url());
        }
    }
}