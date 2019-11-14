package com.example.se_2019;

import android.widget.ImageButton;

import java.util.Date;

public class Post {
    //목록에 띄워질 수 있게 만든 클래스
    private int profile_image;
    private String name;
    private String title;
    private Date write_date;
    private String content;
    private ImageButton imgbtn;

    public ImageButton getImgbtn(){
        return imgbtn;
    }

    public int getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(int profile_image) {
        this.profile_image = profile_image;
    }

    public String getName() {
        return name;
    }

    public Post(int profile_image, String name, String title, Date write_date, String content) {
        this.profile_image = profile_image;
        this.name = name;
        this.title = title;
        this.write_date = write_date;
        this.content = content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getWrite_date() {
        return write_date;
    }

    public void setWrite_date(Date write_date) {
        this.write_date = write_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
