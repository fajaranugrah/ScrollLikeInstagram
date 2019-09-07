package com.example.fajarramadhan.scrolllikeinstagram;

import android.app.Application;
import android.content.res.AssetManager;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.FontRequestEmojiCompatConfig;
import android.support.v4.provider.FontRequest;

import com.squareup.okhttp.OkHttpClient;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.JacksonConverter;

public class MyApplication extends Application{

    public static RapidApi api;
    public static RapidApi getApi() {
        return api;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            final OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setReadTimeout(20, TimeUnit.SECONDS);
            okHttpClient.setConnectTimeout(20, TimeUnit.SECONDS);
            //add User-Agent ex : Android OS 8.1.0 Samsung NF960
            final String userAgent = "(Android;OS " + android.os.Build.VERSION.RELEASE + ";" + android.os.Build.MANUFACTURER + ";" + android.os.Build.MODEL + ")";
            okHttpClient.networkInterceptors().add(new UserAgentInterceptor(userAgent));
            okHttpClient.interceptors().add(new UserAgentInterceptor(userAgent));
            //okHttpClient.setSslSocketFactory(context.getSocketFactory());
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://api.petbacker.com")
                    .setConverter(new JacksonConverter())
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setClient(new OkClient(okHttpClient))
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            request.addHeader("User-Agent", userAgent);
                        }
                    })
                    .build();


            api = restAdapter.create(RapidApi.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try {
            FontRequest fontRequest = new FontRequest(
                    "com.example.fontprovider",
                    "com.example",
                    "emoji compat Font Query",
                    1);
            EmojiCompat.Config config = new FontRequestEmojiCompatConfig(this, fontRequest);
            EmojiCompat.init(config);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
