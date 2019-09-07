package com.example.fajarramadhan.scrolllikeinstagram;

import android.content.Context;

public abstract class ApiCalling {
    public Context ctx;
    public String uuid;

    public ApiCalling(Context context, boolean showProgressDialog, String progressTitle) {
        this.ctx = context;

    }
}
