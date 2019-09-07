package com.example.fajarramadhan.scrolllikeinstagram;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.fajarramadhan.scrolllikeinstagram.JsonUtil.toModel;

public abstract class RetrieveMessageTask extends ApiCalling {
    public MessageInfo info;
    public String JobId;

    public int responseCode;
    public int StatusCode;

    public int totalPg, totalCount;
    HashMap<String,String> hashMap2;
    private final String DEBUG_TAG = "[RapidAssign/RetrieveMessageSupportTask]";

    public RetrieveMessageTask(Context context, boolean showProgressDialog) {
        super(context, showProgressDialog, "loading");
    }

    @SuppressLint("LongLogTag")
    public void callApi(String... params){

        String id = params[0];
        String page = params[1];

        hashMap2 = new HashMap<>();
        hashMap2.put("page", page);
        hashMap2.put("per_page", "20");
        try{
            ApiServices.setDebugTag(DEBUG_TAG);

            CallBackHelper cb = new CallBackHelper<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    onSuccess(response, response2);
                }

                @Override
                public void on200(Response value, Response response) {
                    super.on200(value, response);
                    try {
                        JSONObject res = ApiServices.getJSON(value);
                        try {
                            Log.d(DEBUG_TAG, "Receive: \n" + res.toString(2));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String responseInfo = res.getJSONObject("messageInfo").toString();
                        info = (MessageInfo) toModel(responseInfo, MessageInfo.class);


                        totalPg = ApiServices.getTotalPage(value);
                        totalCount = ApiServices.getTotalCount(value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    responseCode = value.getStatus();
                    OnApiResult(responseCode, 1, "");
                }

                @Override
                public void on204(Response value, Response response) {
                    super.on204(value, response);
                    try {
                        JSONObject res = ApiServices.getJSON(value);
                        //String json =  new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
                        Log.e("failure", res.toString(2));
                        responseCode = value.getStatus();
                        OnApiResult(responseCode, 2, ApiServices.ErrorMessage(res));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void failure(RetrofitError error) {
                    onFailure(error);
                    try {
                        JSONObject res = ApiServices.getJSON(error.getResponse());
                        //String json =  new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
                        Log.e("failure", res.toString(2));
                        responseCode = error.getResponse().getStatus();
                        OnApiResult(responseCode, -1, ApiServices.ErrorMessage(res));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void on404(RetrofitError error) {
                    super.on404(error);
                    try {
                        JSONObject res = ApiServices.getJSON(error.getResponse());
                        //String json =  new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
                        Log.e("failure", res.toString(2));
                        responseCode = error.getResponse().getStatus();
                        OnApiResult(responseCode, 2, ApiServices.ErrorMessage(res));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            https://api.petbacker.com/v5/users/c2efe3ab-de06-11e6-83b5-42010af00aa4/requests/82912/responses/8697786/messages?per_page=20&page=1
            MyApplication.getApi().requestChatUrl(ApiServices.getAuthenticationStringRequest(), "c2efe3ab-de06-11e6-83b5-42010af00aa4",
                    "82912", "8697786", hashMap2, cb);

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public abstract void OnApiResult(int responseCode, int statusCode, String failReason);


    public int getTotalPg() {
        return totalPg;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public String getJobId() {
        return JobId;
    }

    public MessageInfo getInfo() {
        return info;
    }
}
