package com.academy.keytone.model;

public class notice {


    public String title;
    public String subtitle;
    public String time;



    public notice(String title, String  subtitle,String time) {
        this.subtitle = subtitle;
        this.title = title;
        this.time = time;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}