package com.hidic.hidiccs;

/**
 * Created by curv3r on 8/9/16.
 */
public class ModelClass {

    private String title;
    private String subTitle;

    public ModelClass() {
    }

    public ModelClass(String title) {
        this.title = title;
    }

    public ModelClass(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
