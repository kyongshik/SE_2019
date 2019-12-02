package com.example.se_2019;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Vote implements  Parcelable{
    private String name;
    private String date;
    private String title;
    private String content;
    //private ArrayList<CheckBox> chklist; //스트링으로 받아오기
    private ArrayList<String> chklist; //스트링으로 받아오기
    private int posi = 1;



    public Vote(String name, String date, String title, String content, ArrayList<String> chklist){
        this.name = name;
        this.date = date;
        this.title = title;
        this.content = content;
        this.chklist = chklist;
        posi = 1;
    }

    protected Vote(Parcel in){
        name = in.readString();
        date = in.readString();
        title = in.readString();
        content = in.readString();
        //chklist = in.createTypedArrayList(chkCREATOR);
        chklist = in.createStringArrayList();
        posi = 1;
    }



//    ////////////////////////////////여기 안에 함수 고쳐야함
//    public static final Creator<CheckBox> chkCREATOR = new Creator<CheckBox>() {
//        @Override
//        public CheckBox createFromParcel(Parcel source) {
////            return new CheckBox(source);
//            return null;
//        }
//
//        @Override
//        public CheckBox[] newArray(int size) {
//            return new CheckBox[size];
//        }
//    };

    public static final Creator<Vote> CREATOR = new Creator<Vote>() {
        @Override
        public Vote createFromParcel(Parcel in) {
            return new Vote(in);
        }

        @Override
        public Vote[] newArray(int size) {
            return new Vote[size];
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
        dest.writeStringList(chklist);
    }

    public String getchklist(int i){
        return chklist.get(i);
    }

    public int getchklist_size(){
        return chklist.size();
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

    public void setChklist(ArrayList<String> item) {
        this.chklist = item;
    }

    public int getPosi() {
        return posi;
    }

}
