package com.example.se_2019;

import android.os.Parcel;
import android.os.Parcelable;


public class Schedule implements Parcelable{

    private String name;
    private String date;
    private String title;
    private String content;
    private String Dday;
    private int posi = 2;

    public Schedule(String name, String date, String title, String content, String Dday){
        this.name = name;
        this.date = date;
        this.title = title;
        this.content = content;
        this.Dday = Dday;
        posi = 2;

        //date하나더 만들어야함
        //지금 저장한건 작성한 날짜
    }

    protected Schedule(Parcel in){
        name = in.readString();
        date = in.readString();
        title = in.readString();
        content = in.readString();
        Dday = in.readString();
        posi = 2;
    }



    public static final Parcelable.Creator<Schedule> CREATOR = new Parcelable.Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(Dday);
    }


    public String getDday() {
        return Dday;
    }

    public void setDday(String dday) {
        Dday = dday;
    }


    public int getPosi() {
        return posi;
    }


    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }




}
