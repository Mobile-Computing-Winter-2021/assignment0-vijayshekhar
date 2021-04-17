package com.example.helloworld;

public class Model {

    int image;
    String ssid;
    String bssid;
    int rss,strength;

    public Model(int image, String ssid, String bssid, int rss) {
        this.image = image;
        this.ssid = ssid;
        this.bssid = bssid;
        this.rss = rss;

    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public int getRss() {
        return rss;
    }

    public void setRss(int rss) {
        this.rss = rss;
    }

}
