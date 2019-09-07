package com.example.fajarramadhan.scrolllikeinstagram;

import android.util.Log;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public abstract class CallBackHelper<T> implements Callback<T> {

    public void on200(T value, Response response) {

    };

    public void on201(T value, Response response){

    };


    public void on204(T value, Response response){

    };

    public void onSuccessOthers(T value, Response response){

    };

    public void on400(RetrofitError error){

    };

    public void on401(RetrofitError error){

    };

    public void on403(RetrofitError error){

    };

    public void on404(RetrofitError error){

    };

    public void on409(RetrofitError error){

    };

    public void on500(RetrofitError error){

    };


    public void onNetworkError(RetrofitError error){

    };
    public void onHttpError(RetrofitError error){

    };

    public void onSocketTimeOutError(RetrofitError error){

    };

    public final void onSuccess(T value, Response response) {
        switch (response.getStatus()){
            case 200:
                on200(value, response);
                break;
            case 201:
                on201(value, response);
                break;
            case 204:
                on204(value, response);
                break;

            default:
                onSuccessOthers(value, response);
                break;
        }

    }

    public final void onFailure(RetrofitError error) {
        if (error!=null){
            Log.e("MYERROR",error.getKind().toString());
            switch (error.getKind()) {
                case NETWORK:
                    onNetworkError(error);
                    break;
                case UNEXPECTED:
                    Log.e("error", "unexpected");
                    break;
                case HTTP:
                    Log.e("error", "http");
                    onHttpError(error);
                    Response response = error.getResponse();
                    switch (response.getStatus()) {
                        case 400:
                            on400(error);
                            break;
                        case 401:
                            on401(error);
                            break;
                        case 403:
                            on403(error);
                            break;
                        case 404:
                            on404(error);
                            break;
                        case 409:
                            on409(error);
                            break;
                        case 500:
                            on500(error);
                            break;
                        default:
                            onSocketTimeOutError(error);
                            break;
                        //throw error;
                    }
                    break;
                default:
                    throw new IllegalStateException("Unknown error kind: " + error.getKind(), error);
            }
        }
        else {
            onSocketTimeOutError(null);
        }

    }
}
