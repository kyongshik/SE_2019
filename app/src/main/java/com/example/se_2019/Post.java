package com.example.se_2019;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable{
    //목록에 띄워질 수 있게 만든 클래스
    private String name;
    private String write_date;
    private String title;
    private String content;
    private String chklist;
    private String Dday;
    private int posi;
    private int num;
///전체 총괄할 수 있게 만들기

    public String getName() {
        return name;
    }
    protected Post(Parcel in){
        name = in.readString();
        write_date = in.readString();
        title = in.readString();
        content = in.readString();
        chklist = in.readString();
        Dday = in.readString();
        posi = in.readInt();
        num = in.readInt();

    }
    public Post(String name, String write_date, String title, String content,String chklist, String Dday, int posi, int num) {
        this.name = name;
        this.write_date = write_date;
        this.title = title;
        this.content = content;
        this.chklist = chklist;
        this.Dday = Dday;
        this.posi = posi;
        this.num = num;
    }
    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
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
        dest.writeString(name);
        dest.writeString(write_date);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(chklist);
        dest.writeString(Dday);
        dest.writeInt(posi);
        dest.writeInt(num);
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

    public String getWrite_date() {
        return write_date;
    }

    public void setWrite_date(String write_date) {
        this.write_date = write_date;
    }

    public int getNum() {
        return num;
    }
    public int getPosi() {return posi;}
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChklist() {
        return chklist;
    }

    public void setChklist(String chklist) {
        this.chklist = chklist;
    }

    public String getDday() {
        return Dday;
    }

    public void setDday(String dday) {
        Dday = dday;
    }



}
