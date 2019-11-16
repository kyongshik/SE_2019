package com.example.se_2019;


import android.os.Parcel;
import android.os.Parcelable;


public class Note implements Parcelable{


    private String name;
    private String date;
    private String title;
    private String content;



    public Note(String name, String date, String title, String content){
        this.name = name;
        this.date = date;
        this.title = title;
        this.content = content;
    }

    protected Note(Parcel in){
        name = in.readString();
        date = in.readString();
        title = in.readString();
        content = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
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
