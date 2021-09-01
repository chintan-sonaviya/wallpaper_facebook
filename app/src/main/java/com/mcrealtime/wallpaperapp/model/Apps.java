package com.mcrealtime.wallpaperapp.model;

public class Apps {
    String url;
    String name;
    int icon;

    public Apps() {
    }

    public Apps(String url, String name, int icon) {
        this.url = url;
        this.name = name;
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
