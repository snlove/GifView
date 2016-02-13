package com.aacer.gifview.widget;

/**
 * Created by acer on 2016/2/9.
 */
public class DataInfo {
    private String title;
    private String conten;

    public DataInfo(String conten, String title) {
        this.conten = conten;
        this.title = title;
    }

    public String getConten() {
        return conten;
    }

    public String getTitle() {
        return title;
    }
}
