package com.example.se_2019;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Date;

public class Post implements Parcelable {
    private int profile_image;
    private String name;
    private String title;
    private Date write_date;
    private String content;
    private ImageButton imgbtn;
    ArrayList<String> info;

    public Post(int profile_image, String name, String title, Date write_date, String content) {
        this.profile_image = profile_image;
        this.name = name;
        this.title = title;
        this.write_date = write_date;
        this.content = content;
        info = new ArrayList<String>();
    }

    protected Post(Parcel in){
        profile_image = in.readInt();
        name = in.readString();
        title = in.readString();
        //date는 어떻게하죠오
        content = in.readString();
        info=in.createStringArrayList();
    }
    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(profile_image);
        dest.writeString(name);
        dest.writeString(title);
        //date
        dest.writeString(content);
        dest.writeStringList(info);
    }

    public ImageButton getImgbtn(){ return imgbtn; }

    public int getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(int profile_image) {
        this.profile_image = profile_image;
    }

    public String getName() {
        return name;
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
