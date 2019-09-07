package com.example.fajarramadhan.scrolllikeinstagram;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageInfo implements Parcelable {
    String href;
    ArrayList<Items> items;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public ArrayList<Items> getItems() {
        return items;
    }

    public void setItems(ArrayList<Items> items) {
        this.items = items;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
        dest.writeTypedList(this.items);
    }

    public MessageInfo() {
    }

    protected MessageInfo(Parcel in) {
        this.href = in.readString();
        this.items = in.createTypedArrayList(Items.CREATOR);
    }

    public static final Parcelable.Creator<MessageInfo> CREATOR = new Parcelable.Creator<MessageInfo>() {
        @Override
        public MessageInfo createFromParcel(Parcel source) {
            return new MessageInfo(source);
        }

        @Override
        public MessageInfo[] newArray(int size) {
            return new MessageInfo[size];
        }
    };
}
