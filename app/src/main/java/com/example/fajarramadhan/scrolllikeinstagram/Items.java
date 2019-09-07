package com.example.fajarramadhan.scrolllikeinstagram;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Items implements Parcelable {
    String href;
    int id;
    String content;
    String contentType;
    int source;
    int read;
    String createdDate;
    UserInfo userInfo;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
        dest.writeInt(this.id);
        dest.writeString(this.content);
        dest.writeString(this.contentType);
        dest.writeInt(this.source);
        dest.writeInt(this.read);
        dest.writeString(this.createdDate);
        dest.writeParcelable(this.userInfo, flags);
    }

    public Items() {
    }

    protected Items(Parcel in) {
        this.href = in.readString();
        this.id = in.readInt();
        this.content = in.readString();
        this.contentType = in.readString();
        this.source = in.readInt();
        this.read = in.readInt();
        this.createdDate = in.readString();
        this.userInfo = in.readParcelable(UserInfo.class.getClassLoader());
    }

    public static final Parcelable.Creator<Items> CREATOR = new Parcelable.Creator<Items>() {
        @Override
        public Items createFromParcel(Parcel source) {
            return new Items(source);
        }

        @Override
        public Items[] newArray(int size) {
            return new Items[size];
        }
    };
}