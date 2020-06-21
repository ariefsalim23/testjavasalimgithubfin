package com.salimjavagithub.tes.model;

import com.google.gson.annotations.SerializedName;

public class Items {

    @SerializedName("login") private String name;
    @SerializedName("avatar_url") private String avatar;
    @SerializedName("url") private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
