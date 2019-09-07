package com.example.fajarramadhan.scrolllikeinstagram;

import java.util.Map;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.QueryMap;

public interface RapidApi {

    @GET("/v5/users"+"/{uuid}"+"/support")
    void getMessageSupport(
            @Header("Authorization") String token,
            @Path("uuid") String uuid,
            @QueryMap Map<String, String> options,
            Callback<Response> res);

    //request chat url
    @GET("/v5/users"+"/{uuid}"+"/requests/{reqid}/responses/{selectedresid}/messages")
    void requestChatUrl(
            @Header("Authorization") String token,
            @Path("uuid") String uuid,
            @Path("reqid") String reqId,
            @Path("selectedresid") String selectedResId,
            @QueryMap Map<String, String> options,
            Callback<Response> res);
}
